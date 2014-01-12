package model.query;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.expr.Expr;

public class QueryGraphFilter {
	
	private Expr expression;
	private Node var;
	
	public QueryGraphFilter(Expr expression, Node var) {
		this.expression = expression;
		this.var = var;
	}
	
	public Expr getExpression() {
		return expression;
	}

	public void setExpression(Expr expression) {
		this.expression = expression;
	}

	public Node getVar() {
		return var;
	}

	public void setVar(Node var) {
		this.var = var;
	}

}
