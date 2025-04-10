package com.ploy.bubble_server_v3.domain.machine.util;

import com.ploy.bubble_server_v3.domain.machine.exception.OAuthSignatureGenerationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Component
public class OAuthSignatureUtil {

    @Value("${hmac.algorithm}")
    private String hmacSha1Algorithm;

    public String getSignature(String message, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

            Mac hmac = Mac.getInstance(hmacSha1Algorithm);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, hmacSha1Algorithm);
            hmac.init(secretKey);

            byte[] rawHmac = hmac.doFinal(messageBytes);
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new OAuthSignatureGenerationException();
        }
    }
}