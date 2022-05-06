package com.finbourne.insights.extensions;

import com.finbourne.insights.ApiClient;
import com.finbourne.insights.ApiException;
 import com.finbourne.insights.api.AccessEvaluationsApi;
import com.finbourne.insights.extensions.auth.FinbourneTokenException;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

 public class ApiExceptionTests {

     @Test
     public void thrown_exception_tostring_contains_requestid() throws ApiConfigurationException, FinbourneTokenException {

         ApiConfiguration apiConfiguration = new ApiConfigurationBuilder().build(CredentialsSource.credentialsFile);
         ApiClient apiClient = new ApiClientBuilder().build(apiConfiguration);

         AccessEvaluationsApi accessEvaluationsApi = new AccessEvaluationsApi(apiClient);

         try {
             accessEvaluationsApi.getAccessEvaluationLog("doesntExist");
         }
         catch (ApiException e) {

             String message = e.toString();

             assertNotNull("Null exception message", message);

             String[] parts = message.split("\\r?\\n");

             assertThat(parts.length, is(greaterThanOrEqualTo(1)));

             //  of the format 'LUSID request id = 000000000:AAAAAAA'
             String[] idParts = parts[0].split(" = ");

             assertThat("missing requestId", idParts.length, is(equalTo(2)));
         }
         catch (Exception e) {
             fail("Unexpected exception of type " + e.getClass());
         }


     }
 }
