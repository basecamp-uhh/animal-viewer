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
        DateiEinleser("resources/Mieter", mieterTerms);
        DateiEinleser("resources/Vermieter", vermieterTerms);
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
                        Dictionary.put(data.toLowerCase(), 1);
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
        text = text.toLowerCase();
        int vermieterScore = 0;
        int mieterScore = 0;

        for(String key : mieterTerms.keySet() ) {
            if (text.contains(key)) {
                mieterScore += mieterTerms.get(key);
            }
        }

        for(String key : vermieterTerms.keySet() ) {
            if (text.contains(key)) {
                vermieterScore += vermieterTerms.get(key);
            }
        }

        float mieterWahrscheinlichkeit = (float)mieterScore / (mieterScore + vermieterScore);

        return mieterWahrscheinlichkeit;
    }

    public float getVermieterwahrscheinlichkeit(float mieterwahrscheinlichkeit)
    {
        return 1-mieterwahrscheinlichkeit;
    }

    public String getMieterwahrscheinlichkeitAsString(float wahrscheinlichkeit)
    {
        String mieterwahrscheinlichkeitString = "";

        if (wahrscheinlichkeit > 0.5)
        {
            return "Analyse: Mieter mit einer Wahrscheinlichkeit von " + wahrscheinlichkeit;
        }

        else if (wahrscheinlichkeit < 0.5)
        {
            return "Analyse: Vermieter mit einer Wahrscheinlichkeit von " + (1-wahrscheinlichkeit);
        }

        else
        {
            return "Es konnte anhand der Frage nicht ermittelt werden, ob Sie ein Mieter oder ein Vermieter sind.";
        }
    }

}