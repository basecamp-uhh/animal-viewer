package uhh_lt.webserver;

import uhh_lt.classifier.MieterClassifier;

public class MieterClassifierTester
{
    public static void main(String[] args)
    {
        MieterClassifier MC = new MieterClassifier();
        System.out.println(MC.classify("Ich bin Vermieter."));
    }

}
