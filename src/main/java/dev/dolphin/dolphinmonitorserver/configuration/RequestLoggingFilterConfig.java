package dev.dolphin.dolphinmonitorserver.configuration;

import dev.dolphin.dolphinmonitorserver.filters.MonitorServerRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestLoggingFilterConfig {

	@Bean
	public MonitorServerRequestFilter loggingFilter() {
		MonitorServerRequestFilter filter = new MonitorServerRequestFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludeClientInfo(true);
		filter.setIncludeHeaders(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(10000);
		return filter;
	}
}
