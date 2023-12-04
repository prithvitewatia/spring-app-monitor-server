package dev.dolphin.dolphinmonitorserver.filters;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

public class MonitorServerRequestFilter extends AbstractRequestLoggingFilter {
	@Override
	protected void beforeRequest(HttpServletRequest request,String message) {
		this.logger.debug(message);
	}

	@Override
	protected void afterRequest(HttpServletRequest request,String message) {
		// not to log request again
	}
}
