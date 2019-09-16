package com.sample.encryptApplication.config.rest;

import lombok.RequiredArgsConstructor;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfiguration {

	private static final String TIMEOUT = "timeout";
	private static final int MILLI_UNIT = 1000;
	private static final String UTF_8 = "UTF-8";
	private final RestTemplateConfigurationProperties configurationProperties;

	@Bean
	public RestTemplate restTemplate() {
		final RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.getMessageConverters()
				.add(0, new StringHttpMessageConverter(Charset.forName(UTF_8)));
		restTemplate.setErrorHandler(
				new ResponseErrorHandler() {
					@Override
					public boolean hasError(final ClientHttpResponse response) throws IOException {
						return response.getStatusCode().is5xxServerError();
					}

					@Override
					public void handleError(final ClientHttpResponse response) throws IOException {
						// do nothing
					}
				}
		);
		return restTemplate;
	}

	@Bean
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		return (HttpResponse response, HttpContext context) -> {
			final HeaderElementIterator it = new BasicHeaderElementIterator
					(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
			while (it.hasNext()) {
				final HeaderElement he = it.nextElement();
				final String param = he.getName();
				final String value = he.getValue();

				if (value != null && param.equalsIgnoreCase(TIMEOUT)) {
					return Long.parseLong(value) * MILLI_UNIT;
				}
			}
			return configurationProperties.getKeepAliveTimeMillis();
		};
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		final RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(configurationProperties.getConnectionTimeout())
				.setConnectionRequestTimeout(configurationProperties.getRequestTimeout())
				.setSocketTimeout(configurationProperties.getSocketTimeout())
				.build();
		final PoolingHttpClientConnectionManager connManager
				= new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(configurationProperties.getMaxTotal());
		connManager.setDefaultMaxPerRoute(configurationProperties.getMaxPerRoute());
		final CloseableHttpClient client = HttpClientBuilder.create()
				.setDefaultRequestConfig(config)
				.setConnectionManager(connManager)
				.setKeepAliveStrategy(connectionKeepAliveStrategy())
				.build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}

}
