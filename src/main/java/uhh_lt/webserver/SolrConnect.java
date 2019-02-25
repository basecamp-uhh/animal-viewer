package uhh_lt.webserver;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SolrConnect {

    static SolrClient client;

    public SolrConnect() {
         client = new HttpSolrClient.Builder("http://ltdemos:8983/solr/fea-schema-less-2").build();

    }


    public void store(JSONObject object) {
       store(object, true);

    }

    public void store(JSONObject object, boolean commit) {
        SolrInputDocument inputDocument = new SolrInputDocument();
        inputDocument.addField("id", object.get("Topic_id"));
        String tDate = (String) object.get("T_Date");
        inputDocument.addField("t_date", tDate);
        inputDocument.addField("t_subject", object.get("T_Subject"));
        inputDocument.addField("price", object.get("T_Price"));
        inputDocument.addField("t_message", object.get("T_Message"));
        inputDocument.addField("t_summary", object.get("T_Summary"));
        inputDocument.addField("a_date", object.get("R_posted"));
        inputDocument.addField("a_message", object.get("R_Message"));
        try {
            client.add(inputDocument);
            if (commit) {
                client.commit();
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void commit() {
        try {
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String search(String searchTerm) throws IOException {

            SolrQuery query = new SolrQuery();

            query.setQuery(searchTerm);
            // query.addFilterQuery("cat:electronics","store:amazon.com");
            query.setFields("id");
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

        FileWriter fw = new FileWriter("resources/out.txt");

        SolrDocumentList results = response.getResults();
        for (int i = 0; i < results.size(); ++i) {
            System.out.println(results.get(i));
            fw.write(String.valueOf(results.get(i).get("id")));
            fw.write("\n");
            }

        fw.close();
        return  "";
    }
}
