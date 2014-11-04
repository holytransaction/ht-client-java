package com.holytransaction;

import options.ApiUrl;
import options.Auth;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestSigner {
    static final Logger LOG = Logger.getLogger(RequestSigner.class);

    private static String calculateHMAC(String canonicalString, String apiKey) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(apiKey.getBytes(), Auth.HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(Auth.HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(canonicalString.getBytes());
            return new String(Base64.encodeBase64(rawHmac));
        } catch (Exception e) {
            LOG.error("Unexpected error while creating hash: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    private static String md5AsBase64(String content) throws NoSuchAlgorithmException {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Can't calculate MD5\n" + e.getMessage());
        }
        digest.update(content.getBytes());
        return new String(Base64.encodeBase64(digest.digest()));
    }

    private static String getCanonicalString(String requestMethod, String apiFunction, String contentMd5, String nonce) {
        return requestMethod + "," + "/api/v1/" + apiFunction + ',' + contentMd5 + "," + nonce;
    }

    private static Map getHeaders(String requestMethod, String apiFunction, String content, String apiKey, String apiId) throws NoSuchAlgorithmException, SignatureException {
        Map<String, String> result = new HashMap<>();
        result.put("Content-Type", "application/json");
        result.put("Accept", "application/json");
        String mstime = String.valueOf(System.currentTimeMillis());
        result.put("X-Hmac-Id", apiId);
        result.put("X-Hmac-Nonce", mstime);
        String canonicalString = getCanonicalString(requestMethod, apiFunction, md5AsBase64(content), result.get("X-Hmac-Nonce"));
        LOG.debug("Cannonical String = " + canonicalString);
        String requestSign = calculateHMAC(canonicalString, apiKey);
        result.put("X-Hmac-Signature", requestSign);
        return result;
    }

    public static HttpResponse sendPostRequest(String apiFunction, String content, String apiKey, String apiId) throws NoSuchAlgorithmException, SignatureException, IOException {
        HttpPost post = new HttpPost(ApiUrl.paths.get(apiFunction));
        Map<String, String> headers = getHeaders("POST", apiFunction, content, apiKey, apiId);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            post.addHeader(header.getKey(), header.getValue());
        }
        post.setEntity(new StringEntity(content));
        HttpClient client = new DefaultHttpClient();
        return client.execute(post);
    }

    public static HttpResponse sendGetRequest(String apiFunction, String content, String apiKey, String apiId) throws NoSuchAlgorithmException, SignatureException, IOException {
        HttpGet get = new HttpGet(ApiUrl.paths.get(apiFunction));
        Map<String, String> headers = getHeaders("GET", apiFunction, content, apiKey, apiId);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            get.addHeader(header.getKey(), header.getValue());
        }
        HttpClient client = new DefaultHttpClient();
        return client.execute(get);
    }
}