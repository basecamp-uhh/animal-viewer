package uhh_lt.webserver;
import java.io.*;
import org.json.*;
import org.json.JSONObject;
import org.json.JSONArray;
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
 * Import json
 ** **/
public class JsonImport {


    public static void main(String[] args)

    {
        JSONObject obj = new JSONObject("resources/mietrechtexport1000-2.json");

        JSONArray arr = obj.getJSONArray("data");

        for (int i = 0; i < arr.length(); i++)
        {
            int topic_id = arr.getJSONObject(i).getInt("Topic_id");
            String topic_date = arr.getJSONObject(i).getString("T_Date");

            String topic_subject = arr.getJSONObject(i).getString("T_Subject");
            int topic_price= arr.getJSONObject(i).getInt("T_Price");

            String topic_message= arr.getJSONObject(i).getString("T_Message");
            String topic_summary = arr.getJSONObject(i).getString("T_Summary");

            String topic_summary = arr.getJSONObject(i).getString("R_posted");
            String topic_summary = arr.getJSONObject(i).getString("R_Message");

            String Tags = arr.getJSONObject(i).getString("Tags");
            String empfehlung  = arr.getJSONObject(i).getString("Empfehlungen");

        }
    }
}
