package com.finbourne.insights.extensions;

import com.finbourne.insights.ApiClient;
import com.finbourne.insights.api.RequestsApi;
import com.finbourne.insights.model.AccessControlledResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ApiFactoryTest {

    private ApiFactory apiFactory;
    private ApiClient apiClient;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        apiClient = mock(ApiClient.class);
        apiFactory = new ApiFactory(apiClient);
    }

    // General Cases

     @Test
     public void build_ForRequestsApi_ReturnRequestsApi(){
         RequestsApi requestsApi = apiFactory.build(RequestsApi.class);
         assertThat(requestsApi, instanceOf(RequestsApi.class));
     }

     @Test
     public void build_ForAnyApi_SetsTheApiFactoryClientAndNotTheDefault(){
         RequestsApi requestsApi = apiFactory.build(RequestsApi.class);
         assertThat(requestsApi.getApiClient(), equalTo(apiClient));
     }

     // Singleton Check Cases

     @Test
     public void build_ForSameApiBuiltAgainWithSameFactory_ReturnTheSameSingletonInstanceOfApi(){
         RequestsApi requestsApi = apiFactory.build(RequestsApi.class);
         RequestsApi requestsApiSecond = apiFactory.build(RequestsApi.class);
         assertThat(requestsApi, sameInstance(requestsApiSecond));
     }

     @Test
     public void build_ForSameApiBuiltWithDifferentFactories_ReturnAUniqueInstanceOfApi(){
         RequestsApi requestsApi = apiFactory.build(RequestsApi.class);
         RequestsApi requestsApiSecond = new ApiFactory(mock(ApiClient.class)).build(RequestsApi.class);
         assertThat(requestsApi, not(sameInstance(requestsApiSecond)));
     }

     // Error Cases

     @Test
     public void build_ForNonApiPackageClass_ShouldThrowException(){
         thrown.expect(UnsupportedOperationException.class);
         thrown.expectMessage("com.finbourne.insights.model.AccessControlledResource class is not a supported API class. " +
                 "Supported API classes live in the " + ApiFactory.API_PACKAGE + " package.");
         apiFactory.build(AccessControlledResource.class);
     }



}
