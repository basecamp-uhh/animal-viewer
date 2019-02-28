package uhh_lt.webserver;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import static junit.framework.Assert.assertEquals;

public class SolrConnect {

    private static SolrClient client;

    public SolrConnect() { // für ssh  : localhost , sonst ltdemos
         client = new HttpSolrClient.Builder("http://ltdemos:8983/solr/fea-schema-less-2").build();
    }


    public void store(JSONObject object) {
       store(object, true);

    }


    public void store(JSONObject object, boolean commit) {
        MieterClassifier mc = new MieterClassifier();
        WatsonMieterClassifier wmc = new WatsonMieterClassifier();
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
        inputDocument.addField("t_time", DatenDifferenz.Differenz((String)object.get("T_Date"),(String)object.get("R_posted")));
        inputDocument.addField("Expertensystem_istmieter", mc.istMieter((String)object.get("T_Message")));
        inputDocument.addField("Expertensystem_wert", mc.getMieterwahrscheinlichkeit());
        inputDocument.addField("Watson", wmc.classify((String)object.get("T_Message")));
        inputDocument.addField( "Watson istmieter", wmc.istMieter());
        inputDocument.addField("t_length", wordCount.countWord((String)object.get("T_Message")));

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

    public String getFrage(String id) {
    SolrQuery query = new SolrQuery();
    query.setQuery("id:" + id).setFields("t_message").setStart(0).setRows(10000);

    QueryResponse response = null;

        try {
            response = client.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SolrDocumentList results = response.getResults();
        return String.valueOf(results.get(0).get("t_message"));
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
        query.setQuery("*:*").setFields("id", "t_date", "a_date").setStart(0).setRows(10000);
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


    /**
     * Der SolrUpdater fügt der Datenbank eine neue Spalte hinzu und füllt diese mit den eingegebenen Daten
     * @param  docID Eine Datei mit Strings
     */
    public void addMieterlabel(String docID, boolean istMieter)
    {
        SolrQuery query = new SolrQuery();
        query.set("q", "id:"+ docID);
        QueryResponse response = null;
        try {
            response = client.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SolrDocumentList docList = response.getResults();
        assertEquals(docList.getNumFound(), 1);

        for (SolrDocument doc : docList)
        {
            assertEquals((String) doc.getFieldValue("id"), docID);
        }

        SolrDocument oldDoc = response.getResults().get(0);
        SolrInputDocument inputDocument = new SolrInputDocument();


        /**
        inputDocument.addField("id", oldDoc.getFieldValue("id"));
        inputDocument.addField("Q_date", oldDoc.getFieldValue("Q_Date"));
        inputDocument.addField("Q_subject", oldDoc.getFieldValue("Q_subject"));
        inputDocument.addField("price", oldDoc.getFieldValue("price"));
        inputDocument.addField("Q_message", oldDoc.getFieldValue("Q_message"));
        inputDocument.addField("T_summary", oldDoc.getFieldValue("T_message"));
        inputDocument.addField("A_date", oldDoc.getFieldValue("A_date"));
        inputDocument.addField("A_message", oldDoc.getFieldValue("A_message"));
        inputDocument.addField("_version_", oldDoc.getFieldValue("_version_"));
         **/

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("set", istMieter);
        inputDocument.addField("Rechtsexperten", map);

        try {
            client.add(inputDocument);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Es können gezielt Felder per ID in der Datenbank aufgerufen und verändert werden
     * @param fieldName Ein Feldnamen
     * @param docID Eine ID als String
     * @param object Eine zu setzende Änderunng
     **/

    public void SolrChanges(String fieldName, String docID, Object object)
    {

        object = false;
        SolrQuery query = new SolrQuery();
        query.set("q", ""+fieldName+":"+docID);
        QueryResponse response = null;
        try {
            response = client.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SolrDocumentList docList = response.getResults();
        assertEquals(docList.getNumFound(), 1);

        for (SolrDocument doc : docList)
        {
            assertEquals((String) doc.getFieldValue("id"), docID);
        }

        SolrDocument oldDoc = response.getResults().get(0);
        SolrInputDocument inputDocument = new SolrInputDocument();

        Collection<String> feldnamensliste = oldDoc.getFieldNames();
        ArrayList<String> list = new ArrayList<String>();

        /**
        Iterator it = feldnamensliste.iterator();

        while(it.hasNext()){
            list.add(it.toString());
            it.next();
        }
        **/

        for (String str:feldnamensliste)
        {
            list.add(str);
        }

        for (int i=0; i<=list.size();i++)
        {
            inputDocument.addField(list.get(i), oldDoc.getFieldValue(list.get(i)));
        }

        /**
        inputDocument.addField("id", oldDoc.getFieldValue("id"));
        inputDocument.addField("Q_date", oldDoc.getFieldValue("Q_Date"));
        inputDocument.addField("Q_subject", oldDoc.getFieldValue("Q_subject"));
        inputDocument.addField("price", oldDoc.getFieldValue("price"));
        inputDocument.addField("Q_message", oldDoc.getFieldValue("Q_message"));
        inputDocument.addField("T_summary", oldDoc.getFieldValue("T_message"));
        inputDocument.addField("A_date", oldDoc.getFieldValue("A_date"));
        inputDocument.addField("A_message", oldDoc.getFieldValue("A_message"));
        inputDocument.addField("_version_", oldDoc.getFieldValue("_version_"));
        inputDocument.addField("Rechtsexperten", oldDoc.getFieldValue("Rechtsexperten"));
         **/

        HashMap<String, Object> map = new HashMap<String, Object>();

        try {
            client.deleteByQuery(""+fieldName+":"+inputDocument.getFieldValue(fieldName));
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.put("set", object);

        inputDocument.addField(fieldName, map);

        try {
            client.add(inputDocument);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

/**
 *  Object t_date = doc.getFieldValue("t_date");
 *             Object price = doc.getFieldValue("price");
 *             Object t_message = doc.getFieldValue("t_message");
 *             Object a_date = doc.getFieldValue("a_date");
 *             Object a_message = doc.getFieldValue("a_message");
 *             Object t_time = doc.getFieldValue("t_time");
 *             Object exp_bool = doc.getFieldValue("");
 */
