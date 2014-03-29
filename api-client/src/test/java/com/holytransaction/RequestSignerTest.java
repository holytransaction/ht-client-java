package com.holytransaction;

import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class RequestSignerTest {
    @Test
    public void testSendRequest() throws NoSuchAlgorithmException, SignatureException, IOException {
        String apiKey = "gTg2WjqGz5kFhmvx3Gi+zNkC2hG+RHtMuTbK60CiKTjAHUqvgMlEoAqdiYpJo3iKGznwGund1tJVLhbkAXfm3w==";
        String apiId = "97";
        String apiFunction = "invoices";
        String content = "{\"invoice\":{\"type\":\"DEPOSIT_ADDRESS\",\"currency\":\"BTC\",\"expires\":3600,\"invoiced_amount\":13.12345,\"callback_url\":\"\"}}";
        RequestSigner.sendPostRequest(apiFunction, content, apiKey, apiId);
    }

}
