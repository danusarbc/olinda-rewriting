import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

/*nada só para testar o commit*/


public class Dissertation {
                
        final static String esquemaMediacao = "http://dblp.rkbexplorer.com/sparql";          
 
        public static void main(String[] args) {
                String queryString;
            queryString = ""
                    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                    + "PREFIX akt: <http://www.aktors.org/ontology/portal#> "
                    + "PREFIX bibo:    <http://purl.org/ontology/bibo/>"
                    + "PREFIX foaf:    <http://xmlns.com/foaf/0.1/>"
                    + "PREFIX dc:      <http://purl.org/dc/elements/1.1/>"
                    + "PREFIX dcterms: <http://purl.org/dc/terms/>"
                    + "SELECT DISTINCT ?titulo WHERE { "
                    + "{SERVICE <http://dblp.rkbexplorer.com/sparql>{"        
                    + "SELECT DISTINCT ?titulo WHERE { "
                    + "     ?artigo akt:has-title ?titulo . "
                    + "     ?artigo akt:has-author ?author ."
                    + "     ?author akt:full-name 'Katja Hose' .}limit 1000}}"
                    
                    +"union"
                                        
                    + "{SERVICE <http://oai.rkbexplorer.com/sparql>{"
                    + "SELECT DISTINCT ?title WHERE {"
                    + "?publication dc:title ?title ."
                    + "?publication dcterms:creator ?author."
                    + "?author foaf:name 'Katja Hose' }limit 1000}} "
                    
                    + "} limit 1000";
               
                                          
                //executando a consulta em cada um dos SPARQL endpoints:
               
        Query query = QueryFactory.create(queryString);  // exception happens here
        QueryExecution qexec = QueryExecutionFactory.sparqlService(esquemaMediacao,query);

    try {
        ResultSet rs = qexec.execSelect();
        if ( rs.hasNext() ) {
            // show the result, more can be done here
            System.out.println(ResultSetFormatter.asText(rs));
        }
    } 
    catch(Exception e) { 
        System.out.println(e.getMessage());
    }
    finally {
        qexec.close();
    }
    System.out.println("\nall done.");
}

}
