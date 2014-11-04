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
    private String apiKey = "0b9d4e2ad89c3458458c7667d9cacae83d8f96af1f2cd50288ef31ac787eed65b887229af83165426eb0fa8f38be1cf1959167977e4d3d9ca49189753035737e";
    private String apiId = "917";
    private ApiClient apiClient = new ApiClient(apiId, apiKey);

    private String balancesStub = "{\"BTC\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Bitcoin\",\"website\":\"https://bitcoin.org/\",\"precision\":8,\"display_precision\":4,\"confirmation_time\":20},\"BC\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Blackcoin\",\"website\":\"http://www.blackcoin.co/\",\"precision\":8,\"display_precision\":4,\"confirmation_time\":5},\"DRK\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Darkcoin\",\"website\":\"https://www.darkcoin.io/\",\"precision\":8,\"display_precision\":4,\"confirmation_time\":10},\"DOGE\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Dogecoin\",\"website\":\"http://dogecoin.com/\",\"precision\":8,\"display_precision\":4,\"confirmation_time\":4},\"LTC\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Litecoin\",\"website\":\"https://litecoin.org/\",\"precision\":8,\"display_precision\":4,\"confirmation_time\":3},\"PPC\":{\"value\":\"0.0\",\"unconfirmed_value\":\"0.0\",\"name\":\"Peercoin\",\"website\":\"http://peercoin.net/\",\"precision\":8,\"display_precision\":4,\"confirmation_time\":10}}";

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

//    @Test
//    public void testExecuteGetRequestWrongContent() throws IOException, NoSuchAlgorithmException, WrongRestCommandException, SignatureException, NoSuchApiFunctionException, InterruptedException {
//        //arrange
////        Thread.wait(1000);
//        Thread.sleep(1000);
//       // ApiClient apiClient = new ApiClient(apiId, apiKey);
//
//        //act
//        HttpResponse response = apiClient.executeRequest("GET", "balances", "dsds");
//        ResponseHandler<String> handler = new BasicResponseHandler();
//        String responseJson = handler.handleResponse(response);
//
//        //assert
//        assertEquals(balancesStub, responseJson);
//    }

    @Test(expected = WrongRestCommandException.class)
    public void testExecuteRequestWrongRestType() throws IOException, NoSuchAlgorithmException, WrongRestCommandException, SignatureException, NoSuchApiFunctionException {
        //act
        HttpResponse response = apiClient.executeRequest("deaeeeqqd", "accounts/is_email_used", "");

        //assert
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

//    @Test
//    public void testExecuteRequestPostIsEmailUsed() throws IOException, NoSuchAlgorithmException, WrongRestCommandException, SignatureException, NoSuchApiFunctionException {
//        //arrange
//        String id = "5";
//        String key = "7x4reF6QmsMIvqQZm6m0dDS6RKdtZPvS3bq/apltLdvRv1XnBINieDJIfmwDD3ompdiAT7VYKXe5x7V082Kllg==";
//        String content = "{\"email\":\"dafefdfd\"}";
//        //String content = "{\"email\":\"nihuhoid@gmail.com\"}";
//        ApiClient apiClient = new ApiClient(id, key);
//
//        //act
//        HttpResponse response = apiClient.executeRequest("POST", "accounts/is_email_used", content);
//        ResponseHandler<String> handler = new BasicResponseHandler();
//        String responseJson = handler.handleResponse(response);
//
//        //assert
//        assertEquals("{\"used\":false}", responseJson);
//    }

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
