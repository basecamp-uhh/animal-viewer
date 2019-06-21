package uhh_lt.webserver;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Animal {

    private int id;

    private String name;

    private List<Image> images = new LinkedList<>();

    private Random rand = new Random();

    public Animal(int id) {
        this.id = id;
    }

    public Animal(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addImage(String type, String description) {
        Image image = new Image(type, description);
        images.add(image);
    }

    public Image getRandomImage() {
        if (images.isEmpty()) {
            return null;
        }
        int index = rand.nextInt(images.size());
        return images.get(index);
    }


}
