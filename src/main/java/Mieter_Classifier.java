import java.io.BufferedReader;
import java.io.FileReader;


public class Mieter_Classifier
{
    public static void main(String[] arg) throws Exception {

        BufferedReader TSVFile =
                new BufferedReader(new FileReader("/informatik2/students/home/6hauptvo/Desktop/Mieter.tsv"));

        String dataRow = TSVFile.readLine(); // Read first line
        while (dataRow != null)
        {
            String[] dataArray = dataRow.split("\t");
            for (String item:dataArray)
            {

            }
            dataRow = TSVFile.readLine(); // Read next line of data.
        }
        TSVFile.close();
                                                            }
}
