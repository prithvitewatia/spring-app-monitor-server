package dev.dolphin.dolphinmonitorserver.configuration;

import dev.dolphin.dolphinmonitorserver.filters.CustomCsrfFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private AdminServerCorsProperties adminServerCorsProperties;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests(
						auth -> auth
								.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
								.anyRequest().authenticated()
				)
				.cors(cors->cors.configurationSource(corsConfiguration()))
				.httpBasic(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults());

		// adding our custom csrf filter
		http.addFilterAfter(new CustomCsrfFilter(),BasicAuthenticationFilter.class)
				.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
						.ignoringRequestMatchers(
								new AntPathRequestMatcher("/instances",HttpMethod.POST.name()),
								new AntPathRequestMatcher("/instances/*",HttpMethod.DELETE.name()),
								new AntPathRequestMatcher("/actuator/**")));

		// adding security for remember me
		http.rememberMe(rememberMe -> rememberMe.key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600));

		return http.build();
	}

	public CorsConfigurationSource corsConfiguration(){
		CorsConfiguration cors = new CorsConfiguration();
		cors.setAllowedOrigins(adminServerCorsProperties.allowedOrigins());
		cors.setAllowedMethods(adminServerCorsProperties.allowedMethods());
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cors);
		return source;
	}
}
