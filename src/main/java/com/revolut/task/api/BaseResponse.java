package com.revolut.task.api;

public class BaseResponse {
    private String message;
    private int status;

    public BaseResponse() {
    }

    public BaseResponse(String message) {
        this.message = message;
    }

    public BaseResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final class Builder {
        private String message;
        private int status;

        private Builder() {
        }

        public static Builder aBaseResponse() {
            return new Builder();
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withStatus(int status) {
            this.status = status;
            return this;
        }

        public BaseResponse build() {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage(message);
            baseResponse.setStatus(status);
            return baseResponse;
        }
    }
}
