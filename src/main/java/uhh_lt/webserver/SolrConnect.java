package uhh_lt.webserver;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONObject;

import java.io.IOException;

public class SolrConnect {

    SolrClient client;

    public SolrConnect() {
         client = new HttpSolrClient.Builder("http://ltdemos:8983/solr/fea-schema-less-2").build();

    }


    public void store(JSONObject object) {
        SolrInputDocument inputDocument = new SolrInputDocument();
        inputDocument.addField("id", object.get("Topic_id"));
        inputDocument.addField("Q_date", object.get("T_Date"));
        inputDocument.addField("Q_subject", object.get("T_Subject"));
        inputDocument.addField("price", object.get("T_Price"));
        inputDocument.addField("Q_message", object.get("T_Message"));
        inputDocument.addField("T_summary", object.get("T_Summary"));
        inputDocument.addField("A_date", object.get("R_posted"));
        inputDocument.addField("A_message", object.get("R_Message"));
        try {
            client.add(inputDocument);
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


   /* public class SolrJSearcher
    {
        public static void main(String[] args) throws IOException, SolrServerException
        {
            SolrClient client = new HttpSolrClient.Builder("http://ltdemos:8983/solr/#/~cores/fea-test").build();

            SolrQuery query = new SolrQuery();

            /*query.setQuery("sony digital camera");
            query.addFilterQuery("cat:electronics","store:amazon.com");
            query.setFields("id","price","merchant","cat","store");
            query.setStart(0);
            query.set("defType", "edismax");

            QueryResponse response = client.query(query);
            SolrDocumentList results = response.getResults();
            for (int i = 0; i < results.size(); ++i) {
                System.out.println(results.get(i));
            }
        }*/
}
