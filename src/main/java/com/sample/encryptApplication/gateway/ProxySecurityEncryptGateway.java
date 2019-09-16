package com.sample.encryptApplication.gateway;

import com.sample.encryptApplication.exception.EncryptException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProxySecurityEncryptGateway {

	public static final String CHARSET_UTF_8 = ";charset=UTF-8";

	private final RestTemplate restTemplate;

	@Value("${feign.proxySecurity.url:}")
	private String encryptUrl;

	@Retryable(value = {EncryptException.class})
	public String encrypt(final String value){
		try {
			final String resourceUrl =
					format("%s/api/crypto/encrypt", encryptUrl);
			return executeCall(value, resourceUrl);
		}catch (final Exception e){
			log.error(format("Error on encrypt value %s", e.getMessage()), e);

			throw new EncryptException();
		}
	}

	public String decrypt(final String value){
		try {
			final String resourceUrl =
					format("%s/api/crypto/decrypt", encryptUrl);
			return executeCall(value, resourceUrl);
		}catch (final Exception e){
			log.error(format("Error on decrypt value %s", e.getMessage()) , e);

			throw new EncryptException();
		}
	}


	private String executeCall(final String value, final String resourceUrl) {
		final HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.TEXT_PLAIN_VALUE + CHARSET_UTF_8);
		final HttpEntity entity = new HttpEntity(value, headers);

		final ResponseEntity<String> response =
				restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, String.class);

		return response.getBody();
	}
}
