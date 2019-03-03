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

    public SolrConnect() { // für ssh  : localhost , sonst ltdemos:8983/solr/fea-schema-less-2
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

    /**
     * Wenn der Mieter- oder Vermieterbutton gedrückt wurde, wird entweder ein neues Feld "Rechtsexperten_istmieter" oder
     * "Rechtsexperten_istmieter2" angelegt und mit dem entsprechenden Wert gefüllt oder es wird nichts getan
     * @param docID  Die ID, den Primärschlüssel, als String
     * @param istMieter  Wenn es sich um einen Mieter handelt true, sonst false
     */
    public void MieterButtonsPushed(String docID, Object istMieter)
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

        Collection<String> feldnamensliste = oldDoc.getFieldNames();
        ArrayList<String> list = new ArrayList<String>();

        for (String str:feldnamensliste)
        {
            list.add(str);
        }

        String feld = "Rechtsexperten_istmieter";
        String feld2 = "Rechtsexperten_istmieter2";

        if(!list.contains(feld))
        {
            addRechtsexpertenfeldMieter(docID, istMieter);
        }

        else if(list.contains(feld))
        {
            if(!list.contains(feld2))
            {
                addRechtsexpertenfeldMieter2(docID, istMieter);
            }

            else
            {

            }
        }

        else if(list.contains(feld2))
        {

        }
    }


    /**
     * Fügt in Solr das neue Feld "Problemfall" hinzu und setzt dieses auf den eingegebenen Wert
     * @param docID Die DokumentenID, der Primärschlüssel
     */
    public void MieterProblemfallButtonPushed(String docID)
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

        Collection<String> feldnamensliste = oldDoc.getFieldNames();
        ArrayList<String> list = new ArrayList<String>();

        for (String str:feldnamensliste)
        {
            list.add(str);
        }

        String feld = "Problemfall";

        if(!list.contains(feld))
        {
            addField(docID, "Problemfall", true);
        }

        else
        {

        }
    }


    /**
     * Der SolrUpdater fügt der Datenbank eine neues Feld hinzu und füllt dieses mit den eingegebenen Daten
     * @param  docID Die ID, den Primärschlüssel, als String
     * @param  istMieter Wenn es sich um einen Mieter handelt true, sonst false
     */
    public void addRechtsexpertenfeldMieter(String docID, Object istMieter)
    {
        addField(docID, "Rechtsexperten_istmieter", istMieter);
    }

    /**
     * Der SolrUpdater fügt der Datenbank eine neues Feld hinzu und füllt dieses mit den eingegebenen Daten
     * @param  docID Die ID, den Primärschlüssel, als String
     * @param  istMieter Wenn es sich um einen Mieter handelt true, sonst false
     */
    public void addRechtsexpertenfeldMieter2(String docID, Object istMieter)
    {
        addField(docID, "Rechtsexperten_istmieter2", istMieter);
    }

    /**
     *Es wird ein neues Feld in Solr erzeugt und mit einem eingegebenen Wert gefüllt
     * @param docID Die DokumentenID, der Primärschlüssel
     * @param fieldName Der Name des Feldes als String
     * @param object Der Wert, der dem Feld hinzugefügt werden soll
     */
    public void addField(String docID, String fieldName, Object object)
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

        Collection<String> feldnamensliste = oldDoc.getFieldNames();
        ArrayList<String> list = new ArrayList<String>();

        for (String str:feldnamensliste)
        {
            list.add(str);
        }

        for (int i=0; i<list.size();i++)
        {
            inputDocument.addField(list.get(i), oldDoc.getFieldValue(list.get(i)));
        }

        //HashMap<String, Object> map = new HashMap<String, Object>();
        //map.put("set", object);
        inputDocument.addField(fieldName, object);

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

    public void ChangeValueByFieldMieter(String docID, String fieldName, Object object)
    {
        SolrQuery query = new SolrQuery();
        query.set("q", "id:"+docID);
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

        for (String str:feldnamensliste)
        {
            list.add(str);
        }

        for (int i=0; i<list.size();i++)
        {
            inputDocument.addField(list.get(i), oldDoc.getFieldValue(list.get(i)));
        }

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("set", object);

        //inputDocument.addField(fieldName, map);
        inputDocument.getField(fieldName).setValue(object, 1.0f);


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
     * Anhand einer ID wird das JSON-Objekt aus Solr gelöscht
     * @param docID Eine ID als String
     */
    public void SolrDeleteByID(String docID)
    {
        try {
            client.deleteById(docID);
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
     *
     */
    public String DauerPreisComparer() {
        SolrQuery query = new SolrQuery();
        query.set("q", "*:*");
        query.setStart(0);
        query.setSort("t_time", SolrQuery.ORDER.asc);
        QueryResponse response = null;
        try {
            response = client.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SolrDocumentList results = response.getResults();
        //SolrDocument doc = new SolrDocument();
        Object doc = "";
        ArrayList<Object> array1 = new ArrayList<Object>();
        ArrayList<Object> array2 = new ArrayList<Object>();
        for (SolrDocument document : results) {
            doc = document.getFieldValue("t_time");
            array1.add(doc);
        }
        for (SolrDocument document : results) {
            doc = document.getFieldValue("price");
            array2.add(doc);
        }

        String eingabe = "";
        for (int i = 0; array1.size() < i; i++)
        {
            Object obj1 = array1.get(i);
            Object obj2 = array2.get(i);
            eingabe = "[" + obj1 + ":" + obj2 + "],";
        }
        return eingabe;
    }
}