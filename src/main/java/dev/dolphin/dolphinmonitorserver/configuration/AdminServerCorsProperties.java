package dev.dolphin.dolphinmonitorserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "admin.server.cors")
public record AdminServerCorsProperties(
		@Value("#{${allowed-origins}.split(',')}") List<String> allowedOrigins,
		@Value("#{${allowed-methods}.split(',')}") List<String> allowedMethods
) {
}