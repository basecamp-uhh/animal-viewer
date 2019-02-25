package uhh_lt.webserver;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;

/**
 * Der MieterClassifier stellt auf Grundlage von eingelesenen Wortlisten fest, ob ein eingegebenener Text
 * aus Sicht eines Vermieters oder Mieters geschrieben wurde. Hierfür wird eine Wahrscheinlichkeit ausgegeben.
 */

public class MieterClassifier
{
    private HashMap<String, Integer> vermieterTerms = new HashMap<>();
    private HashMap<String, Integer> mieterTerms = new HashMap<>();
    private int vermieterScore;
    private int mieterScore;
    private float mieterWahrscheinlichkeit;


    MieterClassifier()
    {
        DateiEinleser("Mieter", mieterTerms);
        DateiEinleser("Vermieter", vermieterTerms);
        vermieterScore = 0;
        mieterScore = 0;
    }

    /**
     * Der Dateieinleser liest Textdateien aus dem resources folder ein
     *
     * @param Filename Den Filenamen als String
     * @param Dictionary Eine HashMap
     */

    private void DateiEinleser(String Filename, HashMap<String, Integer> Dictionary) {
        System.out.println("loading: " +Filename);

        InputStream input = getClass().getClassLoader().getResourceAsStream(Filename);


        BufferedReader TSVFile = null;
        try {
            TSVFile = new BufferedReader(
                    new InputStreamReader(input));
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

    /**
     * Gibt eine Zahl zurück, die die Wahrscheinlichkeit dafür, dass es sich um einen Mieter handelt
     * ausgibt
     *
     * @param text Einen String
     * @return float Die Mieterwahrscheinlichkeit
     */

    float classify(String text)
    {
        text = text.toLowerCase();
        vermieterScore = 0;
        mieterScore = 0;
        mieterWahrscheinlichkeit = 0;

        for(String key : mieterTerms.keySet() ) {
            int count = StringUtils.countMatches(text, key);
            if (text.contains(key)) {
                mieterScore += mieterTerms.get(key) * count;
            }
        }

        for(String key : vermieterTerms.keySet() ) {
            int count = StringUtils.countMatches(text, key);
            if (text.contains(key)) {
                vermieterScore += vermieterTerms.get(key) * count;
            }
        }

        mieterWahrscheinlichkeit = (float)mieterScore / (mieterScore + vermieterScore);
        return mieterWahrscheinlichkeit;
    }

    /**
     * Gibt eine Zahl zurück, die die Wahrscheinlichkeit dafür, dass es sich um einen Vermieter handelt
     * ausgibt
     * @return float Die Vermieterwahrscheinlichkeit
     */

    public float getVermieterwahrscheinlichkeit()
    {
        return 1-mieterWahrscheinlichkeit;
    }

    /**
     * Gibt die Mieterwahrscheinlichkeit in einem kurzen Text eingebettet zurück.
     * @return Einen String
     */

    String getMieterwahrscheinlichkeitAsString()
    {
        String mieterwahrscheinlichkeitString = "";

        if (mieterWahrscheinlichkeit > 0.5)
        {
            return "Analyse: Mieter mit einer Wahrscheinlichkeit von " + mieterWahrscheinlichkeit;
        }

        else if (mieterWahrscheinlichkeit < 0.5)
        {
            return "Analyse: Vermieter mit einer Wahrscheinlichkeit von " + (1-mieterWahrscheinlichkeit);
        }

        else
        {
            return "Es konnte anhand der Frage nicht ermittelt werden, ob Sie ein Mieter oder ein Vermieter sind.";
        }
    }

    /**
     * Gibt den Vermieterscore zurück
     * @return Vermieterscore als int
     */

    public int getVermieterScore()
    {
        return vermieterScore;
    }

    /**
     * Gibt den Mieterscore zurück
     * @return Mieterscore als int
     */

    public int getMieterScore()
    {
        return mieterScore;
    }

}