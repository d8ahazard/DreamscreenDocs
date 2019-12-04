package com.amazonaws.http;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonServiceException.ErrorType;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.JsonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JsonErrorResponseHandler implements HttpResponseHandler<AmazonServiceException> {
    private static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;
    private static final String X_AMZN_ERROR_TYPE = "x-amzn-ErrorType";
    private final List<? extends JsonErrorUnmarshaller> unmarshallerList;

    public static final class JsonErrorResponse {
        private final String errorCode;
        private final Map<String, String> map;
        private final String message = get("message");
        private final int statusCode;

        private JsonErrorResponse(int statusCode2, String errorCode2, Map<String, String> map2) {
            this.statusCode = statusCode2;
            this.errorCode = errorCode2;
            this.map = map2;
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        public String getErrorCode() {
            return this.errorCode;
        }

        public String getMessage() {
            return this.message;
        }

        public String get(String key) {
            if (key == null || key.length() == 0) {
                return null;
            }
            String firstLetterLowercaseKey = StringUtils.lowerCase(key.substring(0, 1)) + key.substring(1);
            String firstLetterUppercaseKey = StringUtils.upperCase(key.substring(0, 1)) + key.substring(1);
            String str = "";
            if (this.map.containsKey(firstLetterUppercaseKey)) {
                return (String) this.map.get(firstLetterUppercaseKey);
            }
            if (this.map.containsKey(firstLetterLowercaseKey)) {
                return (String) this.map.get(firstLetterLowercaseKey);
            }
            return str;
        }

        public static JsonErrorResponse fromResponse(HttpResponse response) throws IOException {
            int statusCode2 = response.getStatusCode();
            Map<String, String> map2 = JsonUtils.jsonToMap((Reader) new BufferedReader(new InputStreamReader(response.getContent(), StringUtils.UTF8)));
            String errorCode2 = (String) response.getHeaders().get(JsonErrorResponseHandler.X_AMZN_ERROR_TYPE);
            if (errorCode2 != null) {
                int separator = errorCode2.indexOf(58);
                if (separator != -1) {
                    errorCode2 = errorCode2.substring(0, separator);
                }
            } else if (map2.containsKey("__type")) {
                String type = (String) map2.get("__type");
                errorCode2 = type.substring(type.lastIndexOf("#") + 1);
            }
            return new JsonErrorResponse(statusCode2, errorCode2, map2);
        }
    }

    public JsonErrorResponseHandler(List<? extends JsonErrorUnmarshaller> exceptionUnmarshallers) {
        this.unmarshallerList = exceptionUnmarshallers;
    }

    public AmazonServiceException handle(HttpResponse response) throws Exception {
        try {
            JsonErrorResponse error = JsonErrorResponse.fromResponse(response);
            AmazonServiceException ase = runErrorUnmarshallers(error);
            if (ase == null) {
                return null;
            }
            ase.setStatusCode(response.getStatusCode());
            if (response.getStatusCode() < 500) {
                ase.setErrorType(ErrorType.Client);
            } else {
                ase.setErrorType(ErrorType.Service);
            }
            ase.setErrorCode(error.getErrorCode());
            for (Entry<String, String> headerEntry : response.getHeaders().entrySet()) {
                if ("X-Amzn-RequestId".equalsIgnoreCase((String) headerEntry.getKey())) {
                    ase.setRequestId((String) headerEntry.getValue());
                }
            }
            return ase;
        } catch (IOException e) {
            throw new AmazonClientException("Unable to parse error response", e);
        }
    }

    private AmazonServiceException runErrorUnmarshallers(JsonErrorResponse error) throws Exception {
        for (JsonErrorUnmarshaller unmarshaller : this.unmarshallerList) {
            if (unmarshaller.match(error)) {
                return unmarshaller.unmarshall(error);
            }
        }
        return null;
    }

    public boolean needsConnectionLeftOpen() {
        return false;
    }
}
