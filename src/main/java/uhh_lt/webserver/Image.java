package uhh_lt.webserver;

public class Image {

    private String type;
    private String description;

    public Image(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public void setImageDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }
}
