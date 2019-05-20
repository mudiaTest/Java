package my.com.pl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "conn")
@Configuration
public class ConnectionEnv {
	String connectTimeout;
	String readTimeout;
	/*public Integer getConnectTimeout() {
		return Integer.getInteger(connectTimeout);
	}
	public Integer getReadTimeout() {
		return Integer.getInteger(readTimeout);
	}*/
}
