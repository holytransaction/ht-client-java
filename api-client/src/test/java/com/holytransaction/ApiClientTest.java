package com.holytransaction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ApiClientTest {
    private String apiKey = "gTg2WjqGz5kFhmvx3Gi+zNkC2hG+RHtMuTbK60CiKTjAHUqvgMlEoAqdiYpJo3iKGznwGund1tJVLhbkAXfm3w==";
    private String apiId = "97";

    @Test
    public void testExecuteGetRequestWrongApi() throws Exception {
        ApiClient apiClient = new ApiClient(apiId, apiKey);
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
}
