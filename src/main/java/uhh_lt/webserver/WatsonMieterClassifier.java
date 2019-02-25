package uhh_lt.webserver;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

public class WatsonMieterClassifier {

    NaturalLanguageClassifier naturalLanguageClassifier;

public WatsonMieterClassifier() {
    IamOptions options = new IamOptions.Builder()
            .apiKey("")
            .build();

    naturalLanguageClassifier = new NaturalLanguageClassifier(options);
}

    float classify(String neueFrage) {

    ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
            .classifierId("1db512x504-nlc-194")
            .text(neueFrage)
            .build();
    Classification classification = naturalLanguageClassifier.classify(classifyOptions).execute();
System.out.println(classification);
return 0;
    }
}
