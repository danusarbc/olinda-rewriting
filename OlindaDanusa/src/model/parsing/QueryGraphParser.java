package model.parsing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.entities.Dataset;
import model.query.QueryGraph;
import model.query.QueryGraphEdge;
import model.query.QueryGraphFilter;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.sparql.core.TriplePath;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.core.VarExprList;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementFilter;
import com.hp.hpl.jena.sparql.syntax.ElementOptional;
import com.hp.hpl.jena.sparql.syntax.ElementPathBlock;
import com.hp.hpl.jena.sparql.syntax.ElementVisitorBase;
import com.hp.hpl.jena.sparql.syntax.ElementWalker;


public class QueryGraphParser {

        public QueryGraph parseQueryGraph(String queryString, Dataset dataset){

                Query query = QueryFactory.create(queryString);
                Element queryPattern = query.getQueryPattern();
                
                QueryPatternVisitor queryPatternVisitor = new QueryPatternVisitor();
                ElementWalker.walk(queryPattern, queryPatternVisitor);
                
                List<QueryGraphEdge> edges = queryPatternVisitor.getQueryEdges();
                List<QueryGraphFilter> filters = queryPatternVisitor.getQueryFilters();
                
                QueryGraph queryGraph = new QueryGraph(dataset, edges);
                queryGraph.setFilters(filters);
                
                // SELECT Statement
                VarExprList projectedVariables = query.getProject();
                String selectStatement = "SELECT";
                
                if(query.isDistinct()){
                	selectStatement += " DISTINCT";
                }
                
                for(Var projectedVariable : projectedVariables.getVars()){
                	selectStatement += " " + projectedVariable;
                }
                
                queryGraph.setSelect(selectStatement);
                
                return queryGraph;
        }

}

class QueryPatternVisitor extends ElementVisitorBase{

        private List<QueryGraphEdge> queryEdges;
        private List<QueryGraphFilter> queryFilters;

        public QueryPatternVisitor(){
                queryEdges = new ArrayList<QueryGraphEdge>();
                queryFilters = new ArrayList<QueryGraphFilter>();
        }
        
        public List<QueryGraphEdge> getQueryEdges() {
            return queryEdges;
        }

        public List<QueryGraphFilter> getQueryFilters() {
			return queryFilters;
		}

        public void visit(ElementPathBlock el) {

                Iterator<TriplePath> iterator = el.getPattern().iterator();
                while (iterator.hasNext()) {
                        TriplePath triplePath = iterator.next();
                        QueryGraphEdge queryEdge = new QueryGraphEdge(triplePath.getSubject(), triplePath.getPredicate(), triplePath.getObject()); 

                        queryEdges.add(queryEdge);
                }
                super.visit(el);
        }
        
        public void visit(ElementOptional el) {
    		
    		QueryPatternVisitor queryPatternVisitor = new QueryPatternVisitor();
    		ElementWalker.walk(el.getOptionalElement(), queryPatternVisitor);
    		
    		QueryGraphEdge optionalQueryEdge = queryPatternVisitor.getQueryEdges().get(0);
    		
    		for (QueryGraphEdge queryEdge : queryEdges){
    			if (queryEdge.getSubject().equals(optionalQueryEdge.getSubject()) &&
    				queryEdge.getPredicate().equals(optionalQueryEdge.getPredicate()) &&	
    				queryEdge.getObject().equals(optionalQueryEdge.getObject())){
    					queryEdge.setOptional(true);
    			}
    		}
    		
    		super.visit(el);
    	}
        
        public void visit(ElementFilter el){
        	
        	Expr expression = el.getExpr();
        	Set<Var> vars = el.getExpr().getVarsMentioned();
        	
        	Node var = null;
        	
        	if(vars.size() > 0){
        		var = (Node) vars.toArray()[0];
        	}
        	
        	QueryGraphFilter queryFilter = new QueryGraphFilter(expression, var);
        	queryFilters.add(queryFilter);
        }
}