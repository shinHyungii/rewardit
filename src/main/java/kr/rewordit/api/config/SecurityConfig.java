package kr.rewordit.api.config;

import kr.rewordit.api.security.JwtAuthFilter;
import kr.rewordit.api.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtProvider provider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(HttpBasicConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JwtAuthFilter(provider), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(
                authorize -> authorize
                    .requestMatchers("/member/google/signup").permitAll()
                    .requestMatchers("/member/google/login").permitAll()
                    .requestMatchers("/campaign/participate/**").authenticated()
                    .requestMatchers("/qr/use").hasRole("SHOP")
                    .requestMatchers("/campaign/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/shop").permitAll()
                    .anyRequest().authenticated()
            );

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
