package uhh_lt.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class ApplicationController  extends SpringBootServletInitializer {

    private static MieterClassifier mieterClassifier = new MieterClassifier();
    private static SolrConnect solrConnect = new SolrConnect();

    /**
     * Runs the RESTful server.
     *
     * @param args execution arguments
     */
    public static void main(String[] args) {

        SpringApplication.run(ApplicationController.class, args);
    }

    @RequestMapping("/classify")
    String classify(@RequestParam(value = "text", defaultValue = "") String text, @RequestParam(value = "format", defaultValue = "text") String format)
    {

        text = text.replace("\r", " ").replace("\n", " ").trim();
        format = format.replace("\r", " ").replace("\n", " ").trim();

        mieterClassifier.classify(text);

        return mieterClassifier.getMieterwahrscheinlichkeitAsString();
    }

    @RequestMapping("/search")
    String search(@RequestParam(value = "text", defaultValue = "") String text, @RequestParam(value = "format", defaultValue = "text") String format)
    {

        text = text.replace("\r", " ").replace("\n", " ").trim();
        format = format.replace("\r", " ").replace("\n", " ").trim();

        return solrConnect.search(text);
    }

    public String givenList_shouldReturnARandomElement(List<String> list) {
        Random rand = new Random();
        String randomElement = list.get(rand.nextInt(list.size()));
        return randomElement;
    }

    private List<String> readIdFile(String filename) {

        Scanner s = null;
        try {
            s = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> out = new ArrayList<>();
        while (s.hasNextLine()){
            out.add(s.nextLine());
        }
        s.close();

        return out;
    }

    @RequestMapping("/setMieter")
    public void setMieter(@RequestParam(value = "id", defaultValue = "") String id,  HttpServletResponse httpResponse) {
        System.out.println(id);
        SolrConnect sc = new SolrConnect();
        sc.MieterButtonsPushed(id, true);
        try {
            httpResponse.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/setVermieter")
    public void setVerMieter(@RequestParam(value = "id", defaultValue = "") String id,  HttpServletResponse httpResponse) {
        System.out.println(id);
        SolrConnect sc = new SolrConnect();
        sc.MieterButtonsPushed(id, false);
        try {
            httpResponse.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/setProblemfall")
    public void setProblemfall(@RequestParam(value = "id", defaultValue = "") String id,  HttpServletResponse httpResponse) {
        System.out.println(id);
        SolrConnect sc = new SolrConnect();
        sc.MieterProblemfallButtonPushed(id);
        try {
            httpResponse.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/")
    String home(@RequestParam(value = "", defaultValue = "") String text, @RequestParam(value = "format", defaultValue = "text") String format)
    {
        List<String> ids = readIdFile("resources/outputID.txt");
        StringBuilder sb = new StringBuilder();



        SolrConnect sc = new SolrConnect();
        String id = givenList_shouldReturnARandomElement(ids);
        String frage = sc.getFrage(id);

        sb.append("<html><body>");

        sb.append("<form action=\"/setMieter\" method=\"get\">\n")
                .append("<textarea name=\"id\" >")
                .append(id).append("</textarea><input type=\"submit\" value=\"Mieter\">\n" +
                "</form>");

        sb.append("<form action=\"/setVermieter\" method=\"get\">\n")
                .append("<textarea name=\"id\" >")
                .append(id).append("</textarea><input type=\"submit\" value=\"Vermieter\">\n" +
                "</form>");

        sb.append("<form action=\"/setProblemfall\" method=\"get\">\n")
                .append("<textarea name=\"id\" >")
                .append(id).append("</textarea><input type=\"submit\" value=\"Problemfall\">\n" +
                "</form>");


                sb.append("<p<ID: ").append(id).append("</p><p>Frage:</p><pre>");
        sb.append(frage);
        sb.append("</pre></body></html>");
        return sb.toString();
    }

    @RequestMapping("/stats")
    String stats()
    {
        StringBuilder sb = new StringBuilder();


        sb.append("<html><head>");

        sb.append("  <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>");

        sb.append("<script type=\"text/javascript\">" +
                        "google.charts.load('current', {packages: ['corechart', 'line']});\n" +
                        "google.charts.setOnLoadCallback(drawCurveTypes);\n" +
                        "\n" +
                        "function drawCurveTypes() {\n" +
                        "      var data = new google.visualization.DataTable();\n" +
                        "      data.addColumn('number', 'time');\n" +
                        "      data.addColumn('number', 'price');\n" +
                        "\n" +
                        "      data.addRows([\n" +
                        "      SolrConnect solrConnect = new SolrConnect();\n" +
                        "      solrConnect.DauerLängeComparer(); \n" +
                        "      ]);\n" +
                        "\n" +
                        "      var options = {\n" +
                        "        hAxis: {\n" +
                        "          title: 'Time'\n" +
                        "        },\n" +
                        "        vAxis: {\n" +
                        "          title: 'Popularity'\n" +
                        "        },\n" +
                        "        series: {\n" +
                        "          1: {curveType: 'function'}\n" +
                        "        }\n" +
                        "      };\n" +
                        "\n" +
                        "      var chart = new google.visualization.LineChart(document.getElementById('chart_div'));\n" +
                        "      chart.draw(data, options);\n" +
                        "    }" +
                "</script></head><body>\"  <div id='chart_div'></div>\"");
        sb.append("</body></html>");
        return sb.toString();
    }
}