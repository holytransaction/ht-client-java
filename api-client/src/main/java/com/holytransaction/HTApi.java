package com.holytransaction;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

interface HTApi {
    public String executeRequest(String RestType, String apiFunction, String content) throws NoSuchAlgorithmException, SignatureException, IOException, NoSuchApiFunctionException;
}
