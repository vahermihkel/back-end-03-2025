package ee.mihkel.veebipood.util;

import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeValidator;
import org.springframework.stereotype.Component;

@Component
public class EstonianPersonalCodeValidatorFactory {

    public static EstonianPersonalCodeValidator getValidator() {
        return new EstonianPersonalCodeValidator();
    }
}
