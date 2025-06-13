package ee.mihkel.veebipood.config;

import ee.mihkel.veebipood.security.JwtFilter;
import ee.mihkel.veebipood.service.OrderService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
// @EnableScheduling
// @EnableAuditing
// @EnableCaching
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                    .requestMatchers(HttpMethod.GET, "/v3/**","/swagger-ui/**","/swagger-ui.html", "/categories", "/products-by-category",
                            "/search-from-products", "/public-persons", "supplier1", "supplier2", "supplier3").permitAll()
                    .requestMatchers(HttpMethod.GET, "products").hasAnyAuthority("ADMIN", "SUPERADMIN")
                    .requestMatchers(HttpMethod.GET, "persons").hasAuthority("SUPERADMIN")
                    .requestMatchers(HttpMethod.POST, "/login", "/signup").permitAll()
                    .anyRequest().authenticated());
        return http.build();
    }

}
