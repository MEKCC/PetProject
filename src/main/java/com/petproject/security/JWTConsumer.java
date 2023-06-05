package com.petproject.security;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JWTConsumer {
    private static final String BEARER_HEADER = "Bearer ";
    private static final String BEARER_LOWERCASE_HEADER = "bearer ";
    private String m_token;
    private String m_encodedHeader;
    private String m_encodedPayload;
    private String m_signature;
    private String m_payload;
    private JsonElement m_payloadJson;
    private JsonElement m_currentPayload;
    private boolean m_isValid;
    private String m_invalidReason;
    public static final String NOT_VALIDATED_YET = "Not yet validated.";
    public static final String ALGO_ISSUE = "Fauled to get Signature Algorithm";
    public static final String INVALID_STRUCTURE = "Token does not have a valid JWT structure";
    public static final String BAD_HEADER_JSON = "The header's Base64-decoded JSON is invalid";
    public static final String NOT_JWT_TOKEN = "The header does not declare this token as JWT";
    public static final String NOT_SUPPORTED = "Unsupported algorithm or missing decoding secret/key";
    public static final String SIGNATURE_CHECK_FAILED = "JWT signature check failed";
    public static final String BAD_PAYLOAD_JSON = "The payload's Base64-decoded JSON is invalid";
    public static final String NO_EXPIRATION_DATE = "The payload MUST have an expiration date for this application";
    public static final String TOKEN_EXPIRED = "The token is expired";
    public static final String TOKEN_NOT_YET_VALID = "The token is not yet valid";
    public static final String PASSED = "Basic validation passed";
    private static Map<String, String> sm_secData = new HashMap();
    private static final String HMACSHA256 = "HmacSHA256";

    public JWTConsumer(String jwtToken) {
        if (jwtToken.startsWith("Bearer ") || jwtToken.startsWith("bearer ")) {
            jwtToken = jwtToken.substring("Bearer ".length());
        }

        this.m_token = jwtToken;
        this.m_isValid = false;
        this.m_invalidReason = "Not yet validated.";
    }

    public boolean validate() {
        String[] parts = this.m_token.split("\\.");
        if (3 != parts.length) {
            this.m_invalidReason = "Token does not have a valid JWT structure";
            return this.m_isValid;
        } else {
            this.m_encodedHeader = parts[0];
            this.m_encodedPayload = parts[1];
            this.m_signature = parts[2];
            String header = new String(Base64.getUrlDecoder().decode(this.m_encodedHeader.getBytes()));

            String algorithm;
            String tokenType;
            JsonElement expJson;
            try {
                expJson = (new JsonParser()).parse(header);
                algorithm = expJson.getAsJsonObject().get("alg").getAsString();
                tokenType = expJson.getAsJsonObject().get("typ").getAsString();
            } catch (JsonSyntaxException var13) {
                this.m_invalidReason = "The header's Base64-decoded JSON is invalid";
                return this.m_isValid;
            }

            if (!"JWT".equals(tokenType)) {
                this.m_invalidReason = "The header does not declare this token as JWT";
                return this.m_isValid;
            } else if ("HS256".equals(algorithm) && sm_secData.containsKey(algorithm)) {
                if (!this.verifyHS256()) {
                    this.m_invalidReason = "JWT signature check failed";
                    return this.m_isValid;
                } else {
                    this.m_payload = new String(Base64.getUrlDecoder().decode(this.m_encodedPayload.getBytes()));

                    try {
                        this.m_payloadJson = (new JsonParser()).parse(this.m_payload);
                        this.m_currentPayload = this.m_payloadJson;
                        expJson = this.m_currentPayload.getAsJsonObject().get("exp");
                        if (null == expJson) {
                            this.m_invalidReason = "The payload MUST have an expiration date for this application";
                            return this.m_isValid;
                        }

                        long exp = expJson.getAsLong();
                        long now = System.currentTimeMillis() / 1000L;
                        if (now > exp) {
                            this.m_invalidReason = "The token is expired";
                            return this.m_isValid;
                        }

                        JsonElement nbfJson = this.m_currentPayload.getAsJsonObject().get("nbf");
                        if (null != nbfJson) {
                            long nbf = nbfJson.getAsLong();
                            if (now < nbf) {
                                this.m_invalidReason = "The token is not yet valid";
                                return this.m_isValid;
                            }
                        }
                    } catch (Exception var14) {
                        this.m_invalidReason = "The payload's Base64-decoded JSON is invalid";
                        return this.m_isValid;
                    }

                    this.m_invalidReason = "Basic validation passed";
                    this.m_isValid = true;
                    return this.m_isValid;
                }
            } else {
                this.m_invalidReason = "Unsupported algorithm or missing decoding secret/key";
                return this.m_isValid;
            }
        }
    }

    public boolean isValid() {
        return this.m_isValid;
    }

    public String getFieldAsString(String name) {
        this.throwIfInvalid();
        JsonElement namedField = this.getNamedField(name);
        return null == namedField ? null : namedField.getAsString();
    }

    public Long getFieldAsLong(String name) {
        this.throwIfInvalid();
        JsonElement namedField = this.getNamedField(name);
        return null == namedField ? null : namedField.getAsLong();
    }

    public Integer getFieldAsInt(String name) {
        this.throwIfInvalid();
        JsonElement namedField = this.getNamedField(name);
        return null == namedField ? null : namedField.getAsInt();
    }

    public Boolean getFieldAsBoolean(String name) {
        this.throwIfInvalid();
        JsonElement namedField = this.getNamedField(name);
        return null == namedField ? null : namedField.getAsBoolean();
    }

    public String[] getFieldAsArrayOfString(String name) {
        this.throwIfInvalid();
        JsonElement namedField = this.getNamedField(name);
        if (null == namedField) {
            return null;
        } else if (!namedField.isJsonArray()) {
            return null;
        } else {
            JsonArray ja = namedField.getAsJsonArray();
            String[] ret = new String[ja.size()];

            for (int i = 0; i < ja.size(); ++i) {
                try {
                    ret[i] = ja.get(i).getAsString();
                } catch (Exception var7) {
                    ret[i] = null;
                }
            }

            return ret;
        }
    }

    public Long[] getFieldAsArrayOfLong(String name) {
        this.throwIfInvalid();
        JsonElement namedField = this.getNamedField(name);
        if (null == namedField) {
            return null;
        } else if (!namedField.isJsonArray()) {
            return null;
        } else {
            JsonArray ja = namedField.getAsJsonArray();
            Long[] ret = new Long[ja.size()];

            for (int i = 0; i < ja.size(); ++i) {
                try {
                    ret[i] = ja.get(i).getAsLong();
                } catch (Exception var7) {
                    ret[i] = null;
                }
            }

            return ret;
        }
    }

    public Integer[] getFieldAsArrayOfInt(String name) {
        this.throwIfInvalid();
        JsonElement namedField = this.getNamedField(name);
        if (null == namedField) {
            return null;
        } else if (!namedField.isJsonArray()) {
            return null;
        } else {
            JsonArray ja = namedField.getAsJsonArray();
            Integer[] ret = new Integer[ja.size()];

            for (int i = 0; i < ja.size(); ++i) {
                try {
                    ret[i] = ja.get(i).getAsInt();
                } catch (Exception var7) {
                    ret[i] = null;
                }
            }

            return ret;
        }
    }

    public Boolean[] getFieldAsArrayOfBoolean(String name) {
        this.throwIfInvalid();
        JsonElement namedField = this.getNamedField(name);
        if (null == namedField) {
            return null;
        } else if (!namedField.isJsonArray()) {
            return null;
        } else {
            JsonArray ja = namedField.getAsJsonArray();
            Boolean[] ret = new Boolean[ja.size()];

            for (int i = 0; i < ja.size(); ++i) {
                try {
                    ret[i] = ja.get(i).getAsBoolean();
                } catch (Exception var7) {
                    ret[i] = null;
                }
            }

            return ret;
        }
    }

    public boolean setFieldAsCurrentObject(String name) {
        this.throwIfInvalid();
        JsonElement newRoot = this.getNamedField(name);
        if (newRoot.isJsonObject()) {
            this.m_currentPayload = newRoot;
            return true;
        } else {
            return false;
        }
    }

    public boolean setArrayEntryAsCurrentObject(String name, int index) {
        this.throwIfInvalid();
        JsonElement namedField = this.getNamedField(name);
        if (null == namedField) {
            return false;
        } else if (!namedField.isJsonArray()) {
            return false;
        } else {
            JsonArray array = namedField.getAsJsonArray();
            if (0 <= index && array.size() > index) {
                JsonElement newRoot = array.get(index);
                if (newRoot.isJsonObject()) {
                    this.m_currentPayload = newRoot;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    public void setCurrentObjectToRoot() {
        this.m_currentPayload = this.m_payloadJson;
    }

    private JsonElement getNamedField(String name) {
        return this.m_currentPayload.getAsJsonObject().get(name);
    }

    public String getInvalidReason() {
        return this.m_invalidReason;
    }

    private void throwIfInvalid() {
        if (!this.isValid()) {
            throw new IllegalStateException(this.m_invalidReason);
        }
    }

    private boolean verifyHS256() {
        Mac sha256Mac = null;

        try {
            sha256Mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(((String) sm_secData.get("HS256")).getBytes(), "HmacSHA256");
            sha256Mac.init(key);
        } catch (Exception var4) {
            this.m_invalidReason = "Fauled to get Signature Algorithm";
            return false;
        }

        String hashMe = this.m_encodedHeader + "." + this.m_encodedPayload;
        String hash = Base64.getUrlEncoder().withoutPadding().encodeToString(sha256Mac.doFinal(hashMe.getBytes()));
        return hash.equals(this.m_signature);
    }

    public static void setSec(String type, String sec) {
        sm_secData.put(type, sec);
    }
}