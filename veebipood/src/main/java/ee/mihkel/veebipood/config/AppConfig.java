package ee.mihkel.veebipood.config;

import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeValidator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

//    @Bean
//    public EstonianPersonalCodeValidator getEstonianPersonalCodeValidator() {
//        return new EstonianPersonalCodeValidator();
//    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        //return new RestTemplate();
        builder.connectTimeout(Duration.ofSeconds(30));
        return builder.build();
    }
}
