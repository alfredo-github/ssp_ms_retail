package com.tenx.ms.util;

import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@ActiveProfiles(Profiles.TEST_NOAUTH)
@SuppressWarnings({"PMD.SystemPrintln", "PMD.AvoidRethrowingException"})
public abstract class AbstractSimpleIntegrationTest extends AbstractIntegrationTest {
    public void postAndCheck(String url, String payload, HttpStatus expectedStatus, Resource expectedResponseResource) throws IOException, URISyntaxException {
        executeAndCheck(HttpMethod.POST, url, new Resource(payload), expectedStatus, expectedResponseResource);
    }

    public void postAndCheck(String url, String payload, HttpStatus expectedStatus, String expectedResponseResource) throws IOException, URISyntaxException {
        executeAndCheck(HttpMethod.POST, url, new Resource(payload), expectedStatus, new Resource(expectedResponseResource));
    }

    public void postAndCheckEmpty(String url, String payload, HttpStatus expectedStatus) throws IOException, URISyntaxException {
        executeAndCheck(HttpMethod.POST, url, new Resource(payload), expectedStatus, null);
    }

    public void deleteAndCheckEmpty(String url, HttpStatus expectedStatus) throws IOException, URISyntaxException {
        executeAndCheck(HttpMethod.DELETE, url, null, expectedStatus, null);
    }

    public void getAndCheck(String url, HttpStatus expectedStatus, String expectedResponseResource) throws IOException, URISyntaxException {
        executeAndCheck(HttpMethod.GET, url, null, expectedStatus, new Resource(expectedResponseResource));
    }

    public void getAndCheckFail(String resourcePath, HttpStatus expectedStatus) throws IOException, URISyntaxException {
        try {
            executeAndCheck(HttpMethod.GET, resourcePath, null, expectedStatus, null);
            fail("failure expected, got success");
        } catch (HttpClientErrorException e) {
            assertEquals("Expected status mismatch", e.getStatusCode(), expectedStatus);
        }
    }

    public void postAndCheckFail(String url, String payload, HttpStatus expectedStatus, String expectedResponse) throws IOException, URISyntaxException {
        try {
            executeAndCheck(HttpMethod.POST, url, new Resource(payload), expectedStatus, null);
            fail("failure expected, got success");
        } catch (HttpClientErrorException e) {
            assertEquals("Expected status mismatch", e.getStatusCode(), expectedStatus);
            compareResponses(new Resource(expectedResponse), e.getResponseBodyAsString());
        }
    }

    private void executeAndCheck(HttpMethod method, String url, Resource payload, HttpStatus expectedStatus, Resource expectedResponse) throws IOException, URISyntaxException {
        try {
            String payloadText = payload == null ? null : payload.read();
            ResponseEntity<String> response = getJSONResponse(new RestTemplate(), url, payloadText,
                method);

            assertEquals("HTTP Status code incorrect for request", expectedStatus, response.getStatusCode());
            compareResponses(expectedResponse, response.getBody());
        } catch (HttpClientErrorException e) {
//            System.out.println("executeAndCheck got exception, content was:\n" + e.getResponseBodyAsString());
            throw e;
        }
    }

    private void compareResponses(Resource expectedResponse, String responseBody) throws IOException, URISyntaxException {
//        System.out.println("----- compare: " + responseBody);
        if (expectedResponse == null) {
            assertNull("response was expected to be empty", responseBody);
        } else {
            JSONAssert.assertEquals(expectedResponse.read(), responseBody, false);
        }
    }
}
