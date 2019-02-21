package uhh_lt.webserver;
import java.io.*;
import org.json.*;
import org.json.JSONObject;

import org.json.JSONArray;

/**
 *
 ** **/
public class JsonImport {


    public static void main(String[] args)

    {
        JSONObject obj = new JSONObject("resources/mietrechtexport1000-2.json");

        JSONArray arr = obj.getJSONArray("data");

        for (int i = 0; i < arr.length(); i++)
        {
            String topic_id = arr.getJSONObject(i).getString("Topic_id");
            System.out.print("ID:"+""+ topic_id);
            String topic_Message = arr.getJSONObject(i).getString("T_Message");
            String Reply_Message = arr.getJSONObject(i).getString("R_Message");#
            String topic_Message = arr.getJSONObject(i).getString("T_Message");
            String topic_Message = arr.getJSONObject(i).getString("T_Message");
            String topic_Message = arr.getJSONObject(i).getString("T_Message");


        }
    }
}
