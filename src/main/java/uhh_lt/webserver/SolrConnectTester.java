package uhh_lt.webserver;

import org.json.*;
import uhh_lt.webserver.SolrConnect;

public class SolrConnectTester {

    public static void main(String[] args) {
        SolrConnect connect = new SolrConnect();


        JSONObject obj = new JSONObject("{\"T_date\": \"12039230921039213\"}");
        connect.store(obj);
    }
}
