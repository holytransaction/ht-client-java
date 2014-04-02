package com.holytransaction;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApiClientTest {
    private String apiKey = "gTg2WjqGz5kFhmvx3Gi+zNkC2hG+RHtMuTbK60CiKTjAHUqvgMlEoAqdiYpJo3iKGznwGund1tJVLhbkAXfm3w==";
    private String apiId = "97";
    private ApiClient apiClient = new ApiClient(apiId, apiKey);

    private String balancesStub = "{\"BTC\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Bitcoin\",\"website\":\"https://bitcoin.org/\",\"precision\":8,\"display_precision\":4,\"confirmation_time\":20},\"LTC\":{\"value\":\"10.291\",\"unconfirmed_value\":\"0.0\",\"name\":\"Litecoin\",\"website\":\"https://litecoin.org/\",\"precision\":8,\"display_precision\":4,\"confirmation_time\":3},\"PPC\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Peercoin\",\"website\":\"http://peercoin.net/\",\"precision\":8,\"display_precision\":3,\"confirmation_time\":10},\"DOGE\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Dogecoin\",\"website\":\"http://dogecoin.com/\",\"precision\":8,\"display_precision\":4,\"confirmation_time\":4},\"XRP\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Ripple\",\"website\":\"https://ripple.com/currency/\",\"precision\":6,\"display_precision\":4,\"confirmation_time\":1}}";

    @Test
    public void testExecuteGetRequestWrongApi() throws Exception {
        assertNotNull(apiClient.executeRequest("GET", "balances", ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteGetRequestWrongId() throws Exception {
        ApiClient apiClient = new ApiClient("43", "");
        apiClient.executeRequest("GET", "balances", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteGetRequestWrongKey() throws Exception {
        ApiClient apiClient = new ApiClient("97", "");
        apiClient.executeRequest("GET", "balances", "");
    }

    @Test(expected = IllegalStateException.class)
    public void testExecuteGetRequestWrongAddress() throws Exception {
        ApiClient apiClient = new ApiClient(apiId, apiKey, "rere");
        apiClient.executeRequest("GET", "balances", "");
    }

    @Test
    public void testExecuteGetRequestValidAddress() throws IOException, NoSuchAlgorithmException, WrongRestCommandException, SignatureException, NoSuchApiFunctionException {
        //arrange
        ApiClient apiClient = new ApiClient(apiId, apiKey, "https://staging.holytransaction.com");

        //act
        HttpResponse response = apiClient.executeRequest("GET", "balances", "");
        ResponseHandler<String> handler = new BasicResponseHandler();
        String responseJson = handler.handleResponse(response);

        //assert
        assertEquals(balancesStub, responseJson);
    }

    @Test
    public void testExecuteGetRequestBalances() throws IOException, NoSuchAlgorithmException, WrongRestCommandException, SignatureException, NoSuchApiFunctionException {
        //act
        HttpResponse response = apiClient.executeRequest("GET", "balances", "");
        ResponseHandler<String> handler = new BasicResponseHandler();
        String responseJson = handler.handleResponse(response);

        //assert
        assertEquals(balancesStub, responseJson);
    }

    @Test
    public void testExecuteGetRequestWrongContent() throws IOException, NoSuchAlgorithmException, WrongRestCommandException, SignatureException, NoSuchApiFunctionException {
        //arrange
        ApiClient apiClient = new ApiClient(apiId, apiKey);

        //act
        HttpResponse response = apiClient.executeRequest("GET", "balances", "dsds");
        ResponseHandler<String> handler = new BasicResponseHandler();
        String responseJson = handler.handleResponse(response);

        //assert
        assertEquals(balancesStub, responseJson);
    }

    @Test(expected = WrongRestCommandException.class)
    public void testExecuteRequestWrongRestType() throws IOException, NoSuchAlgorithmException, WrongRestCommandException, SignatureException, NoSuchApiFunctionException {
        //act
        HttpResponse response = apiClient.executeRequest("deaeeeqqd", "accounts/is_email_used", "");

        //assert
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testExecuteRequestPostIsEmailUsed() throws IOException, NoSuchAlgorithmException, WrongRestCommandException, SignatureException, NoSuchApiFunctionException {
        //arrange
        String id = "3";
        String key = "/57C4EXYYi9MB9sl8pcYtCD8T3EDejx45OOYTRXH+t0yrRLy+/dbiwsFV4ZW\\njQqu+OPpx7oipmFRhHCp/lBy8Q==";
        String content = "{\"email\":\"dafefdfd\"}";
        //String content = "{\"email\":\"nihuhoid@gmail.com\"}";
        ApiClient apiClient = new ApiClient(id, key);

        //act
        HttpResponse response = apiClient.executeRequest("POST", "accounts/is_email_used", content);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String responseJson = handler.handleResponse(response);

        //assert
        assertEquals("{\"used\":false}", responseJson);
    }

    @Test
    public void testExecuteRequestPostInvoices() throws IOException, NoSuchAlgorithmException, WrongRestCommandException, SignatureException, NoSuchApiFunctionException {
        //arrange
        String content = "{\"invoice\":{\"type\":\"DEPOSIT_ADDRESS\",\"currency\":\"BTC\",\"expires\":3600,\"invoiced_amount\":13.12345,\"callback_url\":\"\"}}";

        //act
        HttpResponse response = apiClient.executeRequest("POST", "invoices", content);

        //assert
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    //TODO test all api-functions
}
