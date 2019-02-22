package uhh_lt.webserver;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import uhh_lt.webserver.SolrConnect;

//Data [
//{
//"Topic_id"
//"T_Date"
//"T_Subject"
//"T_Price":
//"T_Message
//"T_Summary"
//"R_posted
//"R_Message"
//}]
/**
 * Read Json file - show it
 ** **/
public class JsonImport {


    public static void main(String[] args)

    {   JSONParser parser = new JSONParser();
        SolrConnect connect = new SolrConnect();

        Object obj = null;
        try {
            obj = parser.parse(new FileReader("resources/mietrechtexport1000-2.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray arr = (JSONArray) jsonObject.get("data");

        for (int i = 0; i < arr.size(); i++)
        {
            JSONObject objekt  = (JSONObject) arr.get(i);

            System.out.println(objekt.get("Topic_id"));
            System.out.println(objekt.get("T_Date"));
            System.out.println(objekt.get("T_Message"));

           // connect.store(objekt);
        }


    }
}
