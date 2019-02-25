package uhh_lt.webserver;

public class WatsonMieterClassifierTester
{
    public static void main(String[] args){
        WatsonMieterClassifier MC = new WatsonMieterClassifier();
        System.out.println(MC.classify("Ich bin Mieter."));
    }
}
