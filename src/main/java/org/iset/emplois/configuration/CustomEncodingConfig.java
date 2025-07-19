package org.iset.emplois.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class CustomEncodingConfig {

    /**
     * Bean pour configurer le filtre d'encodage des caractères en UTF-8.
     */
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true); // Forcer l'encodage UTF-8 pour toutes les requêtes/réponses
        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setOrder(1); // Priorité du filtre
        return registrationBean;
    }

    /**
     * Bean pour configurer le résolveur de templates Thymeleaf avec UTF-8.
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/"); // Dossier des templates
        templateResolver.setSuffix(".html"); // Extension des fichiers templates
        templateResolver.setTemplateMode(TemplateMode.HTML); // Mode d'analyse HTML
        templateResolver.setCharacterEncoding("UTF-8"); // Assure l'encodage UTF-8 pour les fichiers templates
        templateResolver.setCacheable(false); // Désactiver le cache pendant le développement
        return templateResolver;
    }
}
