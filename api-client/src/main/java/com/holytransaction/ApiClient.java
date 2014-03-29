package com.holytransaction;

import options.ApiUrl;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class ApiClient implements HTApi {
    private final String apiId;
    private final String apiKey;
    final Logger LOG = Logger.getLogger(ApiClient.class);


    public ApiClient(String apiId, String apiKey) {
        this.apiId = apiId;
        this.apiKey = apiKey;
        new ApiUrl();
        logConstructor(apiId, apiKey);
    }

    public ApiClient(String apiId, String apiKey, String apiLink) {
        this.apiId = apiId;
        this.apiKey = apiKey;
        new ApiUrl(apiLink);
        logConstructor(apiId, apiKey);
    }

    private void logConstructor(String apiId, String apiKey) {
        System.out.println("Api client constructed");
        LOG.info(ApiUrl.URL);
        LOG.info("ID = " + apiId);
        LOG.info("Key = " + apiKey);
    }

    @Override
    public String executeRequest(String restType, String apiFunction, String content) throws NoSuchAlgorithmException, SignatureException, IOException, NoSuchApiFunctionException {
        if (!(ApiUrl.paths.containsKey(apiFunction))) throw new NoSuchApiFunctionException();
        HttpResponse response;
        if (restType.equals("GET")) {
            response = RequestSigner.sendGetRequest(apiFunction, content, apiKey, apiId);
        } else {
            response = RequestSigner.sendPostRequest(apiFunction, content, apiKey, apiId);
        }
        ResponseHandler<String> handler = new BasicResponseHandler();
        return handler.handleResponse(response);
    }

}
