package uhh_lt.classifier;

public interface ClassifierInterface
{
    Double classify(String text);

    /**
     * Gibt "true" zurück, wenn es sich um einen Mieter handelt, sonst "false"
     * @return true wenn Mieter, false wenn Vermieter
     */
    boolean istMieter();

    /**
     * Wenn es sich mit einer Wahrscheinlichkeit von über 50% um einen Mieter handelt wird "true" ausgegeben, bei unter
     * 50% "false" und bei genau 50% "unknown"
     * @param text Ein String
     * @return true wenn Mieter, false wenn Vermieter
     */
    Object istMieter(String text);
}
