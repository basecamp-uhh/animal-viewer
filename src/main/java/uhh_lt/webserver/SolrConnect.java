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

        public String search(String searchTerm)
        {

            SolrQuery query = new SolrQuery();

            query.setQuery(searchTerm);
            // query.addFilterQuery("cat:electronics","store:amazon.com");
            query.setFields("id","Q_date","Q_subject","price","Q_message", "T_summary", "A_date", "A_message");
            query.setStart(0);
            // query.set("defType", "edismax");

            QueryResponse response = null;
            try {
                response = client.query(query);
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SolrDocumentList results = response.getResults();
            for (int i = 0; i < results.size(); ++i) {
                System.out.println(results.get(i));
            }
            return "";
        }
    }
