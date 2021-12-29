package com.nbdragon;

import com.android.apksig.ApkVerifier;
import com.android.apksig.apk.ApkFormatException;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        File apkFile = getInputFile(args);
        Map<String, Object> executeResult = new HashMap<>();
        if (apkFile != null && apkFile.exists()) {
            Map<String, Map<String, String>> signatureInfo = getSignatureInfo(apkFile);
            if (signatureInfo != null) {
                executeResult.put("success", true);
                executeResult.put("detail", signatureInfo);
            } else {
                executeResult.put("success", false);
                executeResult.put("detail", "Unknown Error.");
            }
        } else {
            executeResult.put("success", false);
            executeResult.put("detail", "File Not Exists.");
        }
        System.out.println(JSONObject.fromObject(executeResult));
    }

    private static File getInputFile(String[] inputArgs) {
        if (inputArgs.length == 0) {
            return null;
        } else {
            return new File(inputArgs[0]);
        }
    }

    private static Map<String, Map<String, String>> getSignatureInfo(File apkPath) {
        ApkVerifier.Builder builder = new ApkVerifier.Builder(apkPath);
        ApkVerifier apkVerifier = builder.build();
        Map<String, Map<String, String>> certResult = new HashMap<>();
        try {
            ApkVerifier.Result result = apkVerifier.verify();
            if (result.isVerifiedUsingV1Scheme()) {
                X509Certificate certificate = result.getV1SchemeSigners().get(0).getCertificate();
                certResult.put("V1", handleCertificate(certificate));
            }
            if (result.isVerifiedUsingV2Scheme()) {
                X509Certificate certificate = result.getV2SchemeSigners().get(0).getCertificate();
                certResult.put("V2", handleCertificate(certificate));
            }
            if (result.isVerifiedUsingV3Scheme()) {
                X509Certificate certificate = result.getV3SchemeSigners().get(0).getCertificate();
                certResult.put("V3", handleCertificate(certificate));
            }
//            if (result.isVerifiedUsingV4Scheme()) {
//                X509Certificate certificate = result.getV4SchemeSigners().get(0).getCertificate();
//                certResult.put("V4", handleCertificate(certificate));
//            }
            return certResult;
        } catch (ApkFormatException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, String> handleCertificate(X509Certificate certificate) {
        try {
            Map<String, String> signature_info = calcSignature(certificate.getEncoded());
            signature_info.put("Algorithm", certificate.getSigAlgName());
            signature_info.put("Owner", certificate.getSubjectX500Principal().getName());
            return signature_info;
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private static Map<String, String> calcSignature(byte[] content) {
        Map<String, String> map = new HashMap<>();
        map.put("MD5", getEncryptString(content, "MD5"));
        map.put("SHA1", getEncryptString(content, "SHA-1"));
        map.put("SHA256", getEncryptString(content, "SHA-256"));
        return map;
    }

    private static String getEncryptString(byte[] content, String type) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            byte[] digest = MessageDigest.getInstance(type).digest(content);
            for (byte m_byte : digest) {
                stringBuilder.append(String.format("%02X:", m_byte));
            }
            return stringBuilder.subSequence(0, stringBuilder.length() - 1).toString();
        } catch (NoSuchAlgorithmException e) {
            return "ERROR HASH WITH " + type;
        }
    }
}
