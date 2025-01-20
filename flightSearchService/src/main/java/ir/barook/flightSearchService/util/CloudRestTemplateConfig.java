package ir.barook.flightSearchService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Configuration
public class CloudRestTemplateConfig {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("${application.rest.client.connection_timeout}")
    private Integer connectionTimeout;

    @Value("${application.rest.client.read_timeout}")
    private Integer readTimeout;

    @Bean
    public RestTemplate restTemplate(@Autowired(required = false) RestTemplateBuilder builder) {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(connectionTimeout);
        simpleClientHttpRequestFactory.setReadTimeout(readTimeout);

        RestTemplate restTemplate;
        BufferingClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory);
        restTemplate = new RestTemplate(requestFactory);
        restTemplate.setInterceptors(Arrays.asList(getHttpRequestInterceptorForMicro()));

        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestInterceptor getHttpRequestInterceptorForMicro() {
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {

            ClientHttpResponse response;
            Long currentMilliSecond = System.currentTimeMillis();

            try {
                traceRequest(request, body);
                response = execution.execute(request, body);

                Long finalMilliSecond = System.currentTimeMillis() - currentMilliSecond;
                traceResponse(request.getURI(), response, finalMilliSecond);
                return response;

            } catch (RestClientResponseException e) {

                StringBuilder inputStringBuilder = logWhenRaisedError(request);
                inputStringBuilder.append("[Status : " + e.getRawStatusCode() + " ] ");
                inputStringBuilder.append("[Status text : " + e.getStatusText() + "] ");
                logger.severe(inputStringBuilder.toString());
                throw e;
            } catch (ResourceAccessException | SocketTimeoutException | ConnectException e) {

                StringBuilder inputStringBuilder = logWhenRaisedError(request);
                inputStringBuilder.append("[Status : Connection timed out ] ");
                inputStringBuilder.append("[Status text : " + e.getMessage() + "] ");
                logger.severe(inputStringBuilder.toString());

                throw e;
            }

        };
        return interceptor;
    }

    private StringBuilder logWhenRaisedError(HttpRequest request) {
        StringBuilder inputStringBuilder = new StringBuilder();
        inputStringBuilder.append("Micro RestClient response: ");
        inputStringBuilder.append("[URI : " + request.getURI() + "] ");
        return inputStringBuilder;
    }


    private void traceRequest(HttpRequest request, byte[] body) {
        try {
            StringBuilder inputStringBuilder = new StringBuilder();
            inputStringBuilder.append("Micro RestClient request: ");
            inputStringBuilder.append("[Method : " + request.getMethod() + "] ");
            inputStringBuilder.append("[URI : " + request.getURI() + "] ");
            inputStringBuilder.append("[Headers : " + request.getHeaders() + "] ");
            inputStringBuilder.append("[Request body : " + new String(body, "UTF-8") + "] ");
            logger.info(inputStringBuilder.toString());
        } catch (Exception ex) {
            logger.severe("Cannot log Micro restClient request. [Message: " + ex.getMessage() + "] [Method: " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]");
            ex.printStackTrace();
        }
    }

    private void traceResponse(URI url, ClientHttpResponse response, Long finalMilliSecond) {
        try {

            StringBuilder inputStringBuilder = new StringBuilder();
            inputStringBuilder.append("Micro RestClient response: ");
            String rspBody = getResponseBody(response, true);
            inputStringBuilder.append("[URL : " + url.toString() + "] ");
            inputStringBuilder.append("[Response body : " + rspBody + "] ");
            inputStringBuilder.append("[Status code : " + response.getStatusCode() + "] ");
            inputStringBuilder.append("[Status text : " + response.getStatusText() + "] ");
            inputStringBuilder.append("[Consumed Time : " + finalMilliSecond + "] ");

            logger.info(inputStringBuilder.toString());

        } catch (Exception ex) {
            logger.severe("Cannot log Micro restClient response. [Message: " + ex.getMessage() + "] [Line Number: " + Thread.currentThread().getStackTrace()[2].getMethodName() + "]");
            ex.printStackTrace();
        }
    }

    private String getResponseBody(ClientHttpResponse response, boolean retry) {
        String rspBody = "";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            rspBody = bufferedReader.lines().collect(Collectors.joining("\n"));
            if (!rspBody.isEmpty()) {
                rspBody = rspBody.replaceAll("[\\u0000-\\u001f]", "");
            }
        } catch (IOException e) {
            if (retry) { // nested retry (sometimes body output get in second time)
                return getResponseBody(response, false);
            }
            e.printStackTrace();
        }
        return rspBody;
    }

}
