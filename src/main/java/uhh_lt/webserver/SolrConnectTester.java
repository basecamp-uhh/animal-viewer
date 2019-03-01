package uhh_lt.webserver;
import org.json.simple.JSONObject;

//import org.json.*;
import uhh_lt.webserver.SolrConnect;

import java.io.IOException;

public class SolrConnectTester {

    public static void main(String[] args)
    {
        SolrConnect connect = new SolrConnect();
        connect.DauerLängeComparer();

        /**
        SolrConnect connect = new SolrConnect();
        JSONObject obj = new JSONObject();//"{\"T_Date\": \"12039230921039213\", \"Topic_id\": \"123\", \"T_Subject\": \"hallo du\", \"T_Price\": \"654341\", \"T_Message\": \"gfhshkz\", \"T_Summary\": \"gfhfghjghjfhtg\", \"R_posted\": \"fgdhgfhgdfhfg\", \"R_Message\": \"jdfghkjfgd\"}");
        obj.put("Topic_id", "1234");
        obj.put("T_Date", "2017-11-20 11:34:35");
        obj.put("R_posted", "2017-11-20 12:37:46");
        obj.put("price", 676);
        obj.put("T_Message", "Blablabla");

        connect.store(obj);
        **/
   }
}
