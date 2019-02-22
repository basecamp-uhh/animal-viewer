package uhh_lt.webserver;

import org.json.*;
import uhh_lt.webserver.SolrConnect;

public class SolrConnectTester {

    public static void main(String[] args) {
        SolrConnect connect = new SolrConnect();


        JSONObject obj = new JSONObject("{\"T_Date\": \"12039230921039213\", \"Topic_id\": \"56343543541\", \"T_Subject\": \"werfdgthjkhgfrd\", \"T_Price\": \"654341\", \"T_Message\": \"gfhshkz\", \"T_Summary\": \"gfhfghjghjfhtg\", \"R_posted\": \"fgdhgfhgdfhfg\", \"R_Message\": \"jdfghkjfgd\"}");
        connect.store(obj);
    }
}
