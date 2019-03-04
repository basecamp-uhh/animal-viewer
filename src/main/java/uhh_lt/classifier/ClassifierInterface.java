package uhh_lt.classifier;

public interface ClassifierInterface
{
    Double classify(String text);

    /**
     * Gibt zurück, ob es sich um einen Mieter handelt
     * @return true wenn Mieter, false wenn Vermieter
     */
    boolean istMieter();


    Object istMieter(String text);
}
