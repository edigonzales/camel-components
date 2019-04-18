package ch.so.agi.camel.predicates;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.interlis2.validator.Validator;

import ch.ehi.basics.settings.Settings;

public class IlivalidatorPredicate implements Predicate {

    @Override
    public boolean matches(Exchange exchange) {
        File dataFile = exchange.getIn().getBody(File.class);
        
        Settings settings = new Settings();
        settings.setValue(Validator.SETTING_ILIDIRS, Validator.SETTING_DEFAULT_ILIDIRS);

        boolean valid = Validator.runValidation(dataFile.getAbsolutePath(), settings);

        return valid;
    }
}
