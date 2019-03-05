package uhh_lt.classifier;

public interface ClassifierInterface
{
    /**
     * Gibt eine Zahl zurück, die die Wahrscheinlichkeit dafür, dass es sich um einen Mieter handelt
     * ausgibt
     *
     * @param text Einen String
     * @return float Die Mieterwahrscheinlichkeit
     */
    Double classify(String text);

    /**
     * Gibt "true" zurück, wenn es sich um einen Mieter handelt, sonst "false"
     * @return true wenn Mieter, false wenn Vermieter
     */
    boolean istHauptklasse();

    /**
     * Wenn es sich mit einer Wahrscheinlichkeit von über 50% um einen Mieter handelt wird "true" ausgegeben, bei unter
     * 50% "false" und bei genau 50% "unknown"
     * @param text Ein String
     * @return true wenn Mieter, false wenn Vermieter
     */
    Object istHauptklasse(String text);
}
