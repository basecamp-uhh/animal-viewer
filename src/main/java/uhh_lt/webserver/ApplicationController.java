package uhh_lt.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @RequestMapping("/")
    String home(@RequestParam(value = "text", defaultValue = "") String text, @RequestParam(value = "format", defaultValue = "text") String format)
    {

        return "hello world";
    }
}