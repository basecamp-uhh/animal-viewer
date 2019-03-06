package uhh_lt.webserver;

public class GetRecommendedPriceTester {

    public static void main(String args[]) {
        try {
            GetRecommendedPrice.getPrice("Ich bin Mieter in Hamburg.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}