package uhh_lt.webserver;

import net.sf.json.JSONObject;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.FileWriter;
import java.io.IOException;

public class SolrConnect {

    static SolrClient client;

    public SolrConnect() { // für ssh  : localhost , sonst ltdemos
         client = new HttpSolrClient.Builder("http://localhost:8983/solr/fea-schema-less-2").build();
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

    public String search(String searchTerm) {

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
        StringBuilder out = new StringBuilder();
        SolrDocumentList results = response.getResults();
        for (int i = 0; i < results.size(); ++i) {
            out.append(results.get(i)).append("\n");
        }
        return out.toString();
    }

    public void IdSearch() throws IOException {

        // query.addFilterQuery("cat:electronics","store:amazon.com");
        // query.set("defType", "edismax");
        SolrQuery query = new SolrQuery();
        // alle 1000 Daten werden berucksichtight
        query.setQuery("*:*").setFields("id").setStart(0).setRows(10000);

        QueryResponse response = null;

        try {
            response = client.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SolrDocumentList results = response.getResults();
        FileWriter fw = new FileWriter("resources/outputID.txt");

        for (int i = 0; i < results.size(); ++i) {
            System.out.println(results.get(i));
            fw.write(String.valueOf(results.get(i).get("id")));
            fw.write("\n");
        }
        fw.close();

    }
    public void SetDate() throws IOException {

        // query.addFilterQuery("cat:electronics","store:amazon.com");
        // query.set("defType", "edismax");
        SolrQuery query = new SolrQuery();
        // alle 1000 Daten werden berucksichtight
        query.setQuery("*:*").setFields("id","t_date","a_date").setStart(0).setRows(10000);

        QueryResponse response = null;

        try {
            response = client.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SolrDocumentList results = response.getResults();
        FileWriter fw = new FileWriter("resources/dateId.txt");

        for (int i = 0; i < results.size(); ++i) {
            System.out.println(results.get(i));
            fw.write(String.valueOf(results.get(i).get("id")));
            fw.write(String.valueOf(results.get(i).get("t_date")));
            fw.write(String.valueOf(results.get(i).get("a_date")));
            fw.write("\n");
        }
        fw.close();

    }

}

