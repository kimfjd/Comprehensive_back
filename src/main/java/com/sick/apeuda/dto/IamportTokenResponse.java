package com.sick.apeuda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IamportTokenResponse {
    private int code;
    private String message;
    private Response response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        @JsonProperty("access_token")
        private String accessToken;
        private int now;
        @JsonProperty("expired_at")
        private int expiredAt;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getNow() {
            return now;
        }

        public void setNow(int now) {
            this.now = now;
        }

        public int getExpiredAt() {
            return expiredAt;
        }

        public void setExpiredAt(int expiredAt) {
            this.expiredAt = expiredAt;
        }
    }
}