package com.example.nativebc;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;

@SpringBootApplication
public class NativeBcApplication {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private static final String CRYPTO_ALGO = "RSA/None/PKCS1Padding";

    private static byte[] encrypt(PublicKey publicKey, byte[] plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        var cipher = Cipher.getInstance(CRYPTO_ALGO, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText);
    }

    private static byte[] decrypt(PrivateKey privateKey, byte[] cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        var cipher = Cipher.getInstance(CRYPTO_ALGO, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(cipherText);
    }

    private static PublicKey decodePublicKey(String keyPath) throws IOException {
        try (var pemParser = new PEMParser(new FileReader(keyPath))) {
            var converter = new JcaPEMKeyConverter()
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME);
            var publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
            return converter.getPublicKey(publicKeyInfo);
        }
    }

    private static PrivateKey decodePrivateKey(String keyPath) throws IOException {
        try (var pemParser = new PEMParser(new FileReader(keyPath))) {
            var converter = new JcaPEMKeyConverter()
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME);
            return converter.getPrivateKey((PrivateKeyInfo) pemParser.readObject());
        }
    }

    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        SpringApplication.run(NativeBcApplication.class, args);

        var publicKey = decodePublicKey("./openssl/pub.key");
        System.out.println(publicKey);
        System.out.println("=====================");
        var cipherText = encrypt(publicKey, "Hello World".getBytes());
        System.out.println("cipher text: " + Hex.toHexString(cipherText));
        System.out.println();
        var privateKey = decodePrivateKey("./openssl/priv.key");
        System.out.println(privateKey);
        System.out.println("=====================");

        var plainText = decrypt(privateKey, cipherText);
        System.out.println("plain text: " + new String(plainText));
    }
}
