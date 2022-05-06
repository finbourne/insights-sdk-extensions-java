package com.finbourne.insights.extensions;

import com.finbourne.insights.ApiException;
import com.finbourne.insights.api.AccessEvaluationsApi;
import com.finbourne.insights.model.ResourceListWithHistogramOfAccessEvaluationLog;
import com.finbourne.insights.extensions.auth.FinbourneTokenException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

 public class ApiFactoryBuilderTests {

     @Rule
     public ExpectedException thrown = ExpectedException.none();

     @Test
     public void build_WithExistingConfigurationFile_ShouldReturnFactory() throws ApiException, ApiConfigurationException, FinbourneTokenException {
         ApiFactory apiFactory = ApiFactoryBuilder.build(CredentialsSource.credentialsFile);
         assertThat(apiFactory, is(notNullValue()));
         assertThatFactoryBuiltApiCanMakeApiCalls(apiFactory);
     }

     private static void assertThatFactoryBuiltApiCanMakeApiCalls(ApiFactory apiFactory) throws ApiException {
         AccessEvaluationsApi accessEvaluationsApi = apiFactory.build(AccessEvaluationsApi.class);
         ResourceListWithHistogramOfAccessEvaluationLog listWithHistogramOfRequestLog = accessEvaluationsApi.listAccessEvaluationLogs(null, null, 50, null, null);
         assertThat("Requests API created by factory should return list of requests"
                 , listWithHistogramOfRequestLog, is(notNullValue()));
         assertThat("List of requests contents types returned by the requests API should not be empty",
                 listWithHistogramOfRequestLog.getValues(), not(empty()));
     }

 }
