package ec.tourismvisitplanner.core.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleConfig extends AcceptHeaderLocaleResolver {

    private Locale spanishLocale = new Locale("es");

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(spanishLocale);
        resolver.setSupportedLocales(List.of(spanishLocale));
        return resolver;
    }
}