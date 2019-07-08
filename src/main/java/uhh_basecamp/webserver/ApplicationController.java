package uhh_basecamp.webserver;

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
        // Krokodil
        animals.add(new Animal(111621, "Das Krokodil"));

        addImage(111621, "1", "Auf den Dächern Hamburgs");
        addImage(111621, "2", "Eisessen mit dem Hasen in der Speicherstadt");
        addImage(111621, "3", "Ausruhen in der Speicherstadt");
        addImage(111621, "4", "Speicherstadt mit Blick auf Elphi");
        addImage(111621, "5", "Reeperbahn");
        addImage(111621, "6", "Unter dem Fernsehturm");
        addImage(111621, "7", "Binnenalster");


        // Bär
        animals.add(new Animal(301922, "Der Bär"));

        addImage(301922, "1", "Auf den Dächern Hamburgs");
        addImage(301922, "2", "Hafencity mit Blick auf Elphi");
        addImage(301922, "3", "Eisessen mit dem Hund in der Speicherstadt");
        addImage(301922, "4", "Ausruhen in der Speicherstadt");
        addImage(301922, "5", "Landungsbrücken");
        addImage(301922, "6", "St. Pauli mit Blick auf den Fernsehturm");
        addImage(301922, "7", "Vor dem St. Pauli Stadion");
        addImage(301922, "8", "Gruppenbild unter dem Fernsehturm");


        // Maus
        animals.add(new Animal(464535, "Die Maus"));

        addImage(464535, "1", "Joggen in der HafenCity");
        addImage(464535, "2", "Hafencity mit Blick auf Elphi");
        addImage(464535, "3", "Landungsbrücken mit dem Hund");
        addImage(464535, "4", "An der Reeperbahn");
        addImage(464535, "5", "Vor dem St. Pauli Stadion");
        addImage(464535, "6", "Alterschiffe");
        addImage(464535, "7", "Binnenalster mit Blick auf das Rathaus");


        // Hund
        animals.add(new Animal(601532, "Der Hund"));

        addImage(601532, "1", "Eisessen mit dem Bär in der Speicherstadt");
        addImage(601532, "2", "Ausruhen in der Speicherstadt");
        addImage(601532, "3", "Hafencity mit Blick auf Elphi");
        addImage(601532, "4", "Landungsbrücken mit der Maus");
        addImage(601532, "5", "base.camp Sport an der Reeperbahn");
        addImage(601532, "6", "Vor dem St. Pauli Stadion");
        addImage(601532, "7", "Segelboote auf der Alster");


        // Hase
        animals.add(new Animal(649664, "Der Hase"));

        addImage(649664, "1", "Auf den Dächern Hamburgs");
        addImage(649664, "2", "Eisessen in der Speicherstadt");
        addImage(649664, "3", "Mittagsschläfchen nach dem Eis");
        addImage(649664, "4", "Ausruhen in der Speicherstadt mit dem Bär");
        addImage(649664, "5", "Hafencity mit Blick auf Elphi");
        addImage(649664, "6", "Landungsbrücken");
        addImage(649664, "7", "St. Pauli mit Blick auf den Fernsehturm");
        addImage(649664, "8", "Vor dem St. Pauli Stadion");
        addImage(649664, "9", "Gruppenbild unter dem Fernsehturm");


        // Löwe
        animals.add(new Animal(764197, "Der Löwe"));

        addImage(764197, "1", "Über den Dächern Hamburgs");
        addImage(764197, "2", "Eisessen mit dem Löwen in der Speicherstadt");
        addImage(764197, "3", "Hafencity mit Blick auf Elphi");
        addImage(764197, "4", "Landungsbrücken, Bewerbung auf <q>Der König der Löwen</q>");
        addImage(764197, "5", "Tanzende Türme an der Reeperbahn");
        addImage(764197, "6", "Vor dem St. Pauli Stadion");
        addImage(764197, "7", "Alsterblick von der Kennedy-Brücke");

        // Löwe 2
        animals.add(new Animal(248027, "Der Löwe"));

        addImage(248027, "1", "Über den Dächern Hamburgs");
        addImage(248027, "2", "Eisessen mit dem Löwen in der Speicherstadt");
        addImage(248027, "3", "Hafencity mit Blick auf Elphi");
        addImage(248027, "4", "Landungsbrücken, Bewerbung auf <q>Der König der Löwen</q>");
        addImage(248027, "5", "Tanzende Türme an der Reeperbahn");
        addImage(248027, "6", "Vor dem St. Pauli Stadion");
        addImage(248027, "7", "Alsterblick von der Kennedy-Brücke");


        // Fuchs
        animals.add(new Animal(839531, "Der Fuchs"));

        addImage(839531, "1", "Eisessen mit dem Löwen in der Speicherstadt");
        addImage(839531, "2", "Ausruhen in der Speicherstadt auf einer Fleetbrücke");
        addImage(839531, "3", "Hafencity mit Blick auf Elphi");
        addImage(839531, "4", "Landungsbrücken im Touri-Trubel");
        addImage(839531, "5", "base.camp Sport an der Reeperbahn");
        addImage(839531, "6", "Vor dem St. Pauli Stadion");
        addImage(839531, "7", "Unter dem Fernsehturm");


    }

    private static void addImage(int id, String type, String description) {
        Animal animal = getAnimal(id);
        animal.addImage(type, description);
    }


    @RequestMapping("/")
    public String index()  {
        return "index";
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
        int animalId = Integer.parseInt(id);
        if (id.isEmpty() || !animalExists(animalId)) {
            return "error";
        }

        Animal a = getAnimal(animalId);

        if (a != null) {
            Image image = a.getRandomImage();
            model.addAttribute("image", "basecamp-tier-" + animalId + "-" + image.getType() +".jpg");
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