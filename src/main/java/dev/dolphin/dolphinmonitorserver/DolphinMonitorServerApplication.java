package dev.dolphin.dolphinmonitorserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import dev.dolphin.dolphinmonitorserver.configuration.AdminServerCorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAdminServer
@EnableConfigurationProperties(value = {AdminServerCorsProperties.class})
public class DolphinMonitorServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DolphinMonitorServerApplication.class,args);
	}

}
