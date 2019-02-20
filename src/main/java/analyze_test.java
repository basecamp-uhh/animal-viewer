package uhh_lt.webserver;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;

public class analyze_test
{
    public static void main(String[] args) throws IOException, SolrServerException
    {
        SolrClient client = new HttpSolrClient.Builder("http://ltdemos:8983/solr/fea-schema-less-2").build();

        for(int i=0;i<10;++i)
        {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("type", "question");
            doc.addField("id", "que-" + i);
            client.add(doc);
        }
        client.commit();
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
