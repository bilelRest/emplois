package org.iset.emplois.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; 

@Configuration
public class SecurityConfig {

 
 
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/css/**", "/js/**", "/webjars/**", "/images/**").permitAll()
                    .requestMatchers("/users/delete/**").hasRole("ADMIN") 
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/users", true)
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                )
                .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/delete/**", "/logout", "/detailsaus/delete/**")
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
    
            return http.build();
        }
    
        @Bean
        public UrlBasedCorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin("*"); // Autoriser tous les domaines
            configuration.addAllowedMethod("*"); // Autoriser toutes les méthodes (GET, POST, etc.)
            configuration.addAllowedHeader("*"); // Autoriser tous les en-têtes
            configuration.setAllowCredentials(true);
    
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
   
    

    

     @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
