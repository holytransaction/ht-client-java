package com.holytransaction;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

interface HTApi {
    public HttpResponse executeRequest(String RestType, String apiFunction, String content) throws NoSuchAlgorithmException, SignatureException, IOException, WrongRestCommandException, NoSuchApiFunctionException;
}
