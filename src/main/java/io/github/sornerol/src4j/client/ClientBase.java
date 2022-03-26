package io.github.sornerol.src4j.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sornerol.src4j.Exception.Src4jException;
import io.github.sornerol.src4j.client.enums.ResponseCode;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

abstract class ClientBase {

    private final CloseableHttpClient httpClient;

    protected ClientBase() {
        httpClient = HttpClients.createDefault();
    }

    /**
     * Execute a GET request on the specified endpoint and deserialize the response into a domain object.
     *
     * @param endpoint The full API endpoint URL.
     * @param clazz    The class of the object to return.
     * @param <T>      Describes the type parameter.
     * @return The deserialized object of the type specified.
     * @throws IOException    if there is a problem connecting to speedrun.com
     * @throws Src4jException if the API returns a non-success response code
     */

    protected <T> T getRequest(String endpoint, Class<T> clazz) throws IOException, Src4jException {
        String responseJson = getRequest(endpoint);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseJson, clazz);
    }

    /**
     * Execute a GET request on the specified endpoint and return the response as a String.
     *
     * @param endpoint The full API endpoint URL.
     * @return The deserialized object of the type specified.
     * @throws IOException    if there is a problem connecting to speedrun.com
     * @throws Src4jException if the API returns a non-success response code
     */
    protected String getRequest(String endpoint) throws IOException, Src4jException {
        HttpGet request = new HttpGet(endpoint);

        String responseBody;
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            //TODO: Handle rate throttling more gracefully
            if (statusCode != ResponseCode.OK.getValue()) {
                throw new Src4jException("Error executing GET request: API returned status code " + statusCode);
            }
            InputStream inputStream = response.getEntity().getContent();
            responseBody = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
        return responseBody;
    }
}