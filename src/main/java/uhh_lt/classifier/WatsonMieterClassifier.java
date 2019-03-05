package uhh_lt.classifier;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

public class WatsonMieterClassifier implements ClassifierInterface {

    NaturalLanguageClassifier naturalLanguageClassifier;
    Classification classification;


public WatsonMieterClassifier() {
    IamOptions options = new IamOptions.Builder()
            .apiKey("nqeYBC1Rp7M7CpUpAwmr-cFBiQVHndCzNMz07-Yw3lKF")
            .build();

    naturalLanguageClassifier = new NaturalLanguageClassifier(options);
}

    @Override
    public Double classify(String neueFrage) {

        neueFrage = neueFrage.substring(0, Math.min(neueFrage.length(), 1000));

    ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
            .classifierId("1e5b70x507-nlc-653")
            .text(neueFrage)
            .build();
    classification = naturalLanguageClassifier.classify(classifyOptions).execute();
//System.out.println(classification);
        for (ClassifiedClass mClass : classification.getClasses()) {
            if (mClass.getClassName().compareTo("Mieter") == 0) {
                return mClass.getConfidence();
            }
        }

        return 0.0;
    }


    @Override
    public boolean istMieter()
    {
        if(classification.getTopClass().compareTo("Mieter") == 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public Object istMieter(String text) {
        if(classification.getTopClass().compareTo("Mieter") == 0)
        {
            return true;
        }
        return false;
    }

}
