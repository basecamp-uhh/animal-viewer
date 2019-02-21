package uhh_lt.webserver;

import org.apache.tools.ant.taskdefs.Tstamp;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Dictionary;
import java.util.HashMap;


public class MieterClassifier
{
    private HashMap<String, Integer> vermieterTerms = new HashMap<>();
    private HashMap<String, Integer> mieterTerms = new HashMap<>();


    public MieterClassifier()
    {
        DateiEinleser("Mieter", mieterTerms);
        DateiEinleser("Vermieter", vermieterTerms);
    }

    private void DateiEinleser(String Filename, HashMap<String, Integer> Dictionary) {
        //try (InputStream input = getClass().getClassLoader().getResourceAsStream("resources/" + "Mieter"))
        System.out.println("loading: " +Filename);


        BufferedReader TSVFile = null;
        try {
            TSVFile = new BufferedReader(
                    new InputStreamReader(new FileInputStream(Filename)));
            String dataRow = null; // Read first line

                dataRow = TSVFile.readLine();
                while (dataRow != null) {
                    //String[] dataArray = dataRow.split("\t");
                    String data = dataRow.trim();
                    //for (String item : data)
                    //{
                    if (!data.isEmpty()) {
                        Dictionary.put(data, 1);
                    }

                    //}

                    dataRow = TSVFile.readLine(); // Read next line of data.
                }
                //System.out.println(Dictionary);
                TSVFile.close();

            } catch (FileNotFoundException e) {
                System.err.println("Die Datei konnte nicht geöffnet werden");
            } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("...done");
    }


    public float classify(String text)
    {
        return (float) 0.5;
    }


}
