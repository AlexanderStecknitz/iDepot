package de.stecknitz.backend.infrastructure.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import de.stecknitz.backend.core.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration( proxyBeanMethods = false )
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(authorize ->
                                    authorize.anyRequest().authenticated()
                )
                .oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }

    @Bean
    @Order(1)
    SecurityFilterChain filterLogoutChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .securityMatchers(requestMatcherConfigurer -> requestMatcherConfigurer.requestMatchers("/auth/logout"))
                .logout(logout -> logout.logoutSuccessUrl("/api/logout"))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain filterLoginChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .securityMatchers(requestMatcherConfigurer -> requestMatcherConfigurer.requestMatchers("/auth/login"))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    @Order(3)
    SecurityFilterChain filterRegisterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .securityMatchers(requestMatcherConfigurer -> requestMatcherConfigurer.requestMatchers("/auth/register"))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RSAKey rsaKey() throws NoSuchAlgorithmException {
        var generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        var keypair = generator.generateKeyPair();
        return new RSAKey.Builder((RSAPublicKey) keypair.getPublic())
                .privateKey(keypair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }


    @Bean
    JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey())
                .build();
    }
    @Bean
    JwtEncoder jwtEncoder(RSAKey rsaKey) {
        return new NimbusJwtEncoder(
                new ImmutableJWKSet<>(new JWKSet(rsaKey))
        );
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return email ->
                userRepository.findByEmail(email)
                        .map(user -> User.withUsername(user.getEmail())
                                .password(user.getPassword())
                                .roles(user.getRole().toString())
                                .build())
                        .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Bean
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        CustomDaoAuthenticationProvider authenticationProvider = new CustomDaoAuthenticationProvider(passwordEncoder, userRepository);
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}
