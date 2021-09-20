package code.academy;

import code.academy.client.PeopleApiClient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import javax.net.ssl.SSLContext;


public class InitialTestFile {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;

    @Test
    public void initialTest() throws Exception {
        // nekakov REQUEST do PEOPLE API
        response = peopleApiClient.getWelcomeRequest();

        String body = EntityUtils.toString(response.getEntity());

        // People API mi vrakja nekakov response

        // nekakva proverka na bodyto shto go ima vo toj response
        // body === "message": "Welcome to People API"

    }
}
