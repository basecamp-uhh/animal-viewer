package uhh_lt.webserver;
import org.json.simple.JSONObject;

//import org.json.*;
import uhh_lt.webserver.SolrConnect;

import java.io.IOException;

public class SolrConnectTester {

    public static void main(String[] args) throws IOException {
        SolrConnect connect = new SolrConnect();
        connect.IdSearch();

        //JSONObject obj = new JSONObject();//"{\"T_Date\": \"12039230921039213\", \"Topic_id\": \"123\", \"T_Subject\": \"hallo du\", \"T_Price\": \"654341\", \"T_Message\": \"gfhshkz\", \"T_Summary\": \"gfhfghjghjfhtg\", \"R_posted\": \"fgdhgfhgdfhfg\", \"R_Message\": \"jdfghkjfgd\"}");
        //connect.store(obj);


   }
}
