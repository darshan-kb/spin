package com.spin.game.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${jwksuri}")
    String jwksUri;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http.cors(c->{
//            CorsConfigurationSource source = s ->{
//                CorsConfiguration cc = new CorsConfiguration();
//                cc.setAllowCredentials(true);
//                cc.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:3006"));
//                cc.setAllowedHeaders(List.of("*"));
//                cc.setAllowedMethods(List.of("*"));
//                return cc;
//            };
//            c.configurationSource(source);
//        });
        http.oauth2ResourceServer(
                r -> r.jwt((j)-> {
                    j.jwkSetUri(jwksUri);
                    j.jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter());
                }
                )

        );
//        http.oauth2Client();
        http.authorizeHttpRequests((a) -> {
            a.requestMatchers("/check/admin").hasRole("ADMIN");
            a.requestMatchers("/sse").permitAll();
            a.requestMatchers("/ws").permitAll();
            a.requestMatchers("/hello/server").permitAll();
            a.requestMatchers("/ws/**").permitAll();
//            a.requestMatchers("/countdown").permitAll();
//            a.requestMatchers("/websocket").permitAll();
            a.anyRequest().authenticated();
        });
        return http.build();

    }
}
