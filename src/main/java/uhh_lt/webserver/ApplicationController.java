package uhh_lt.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;


//@RestController
@Controller
@EnableAutoConfiguration
@SpringBootApplication
public class ApplicationController extends SpringBootServletInitializer {

    private static HashSet<Animal> animals = new HashSet<>();
    private static Random random = new Random();

    /**
     * Runs the RESTful server.
     *
     * @param args execution arguments
     */
    public static void main(String[] args) {

        SpringApplication.run(ApplicationController.class, args);

    }

    @PostConstruct
    private void init() {
        animals.add(new Animal(111111, "Kuh"));
        animals.add(new Animal(111122, "noch eine Kuh"));

        addImage(111111, "a", "Kuh auf dem Bauernhof");

        addImage(111122, "a", "Kuh im Stall");
    }

    private static void addImage(int id, String type, String description) {
        Animal animal = getAnimal(id);
        animal.addImage(type, description);
    }


    @RequestMapping("/random")
    public String random(HttpServletResponse httpResponse) throws IOException {
        int index = random.nextInt(animals.size());
        Iterator<Animal> iter = animals.iterator();
        for (int i = 0; i < index; i++) {
            iter.next();
        }
        httpResponse.sendRedirect("./showimage?id=" + iter.next().getId());
        return null;
    }

    @RequestMapping("/showimage")
    public String showimage(Model model, @RequestParam(value = "id", defaultValue = "") String id) {
        id = id.trim();
        if (id.trim().isEmpty()) {
            return "showimages";
        }
        int animalId = Integer.parseInt(id);

        Animal a = getAnimal(animalId);

        if (a != null) {
            Image image = a.getRandomImage();
            model.addAttribute("image", "basecamp-tier-" + animalId + "-" + image.getType() +".jpg");

            model.addAttribute("title", "tier " + id);
            model.addAttribute("tier", a.getName());
            model.addAttribute("description", image.getDescription());
        }

        return "showimage"; //view
    }

    private boolean animalExists(int id) {
        for (Animal a : animals) {
            if (a.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private static Animal getAnimal(int id) {
        for (Animal a : animals) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }


}