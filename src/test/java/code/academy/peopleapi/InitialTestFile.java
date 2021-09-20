package code.academy.peopleapi;


import code.academy.client.PeopleApiClient;
import code.academy.model.requests.PostNewPersonRequest;
import code.academy.model.response.PostNewPersonResponce;
import code.academy.payloads.PostNewPersonPayload;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static code.academy.utils.ConversionUtils.objectToJsonString;
import static org.apache.http.HttpStatus.SC_CREATED;


public class InitialTestFile {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    PostNewPersonRequest postNewPersonRequest = new PostNewPersonRequest();

    public InitialTestFile() throws Exception {
    }


    @Test
    public void welcomeMessagePeopleApiTest() throws Exception {
        String expectedMessage = "Welcome to People API";
        // nekakov REQUEST do PEOPLE API
        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/");

        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(messageAsString, expectedMessage);
    }

    @Test
    public void getSinglePersonTest() throws Exception {
        String expectedMessage = "Person succesfully fetched";
        // nekakov REQUEST do PEOPLE API
        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/person/612ba20357744c30dc7e6fe7");

        // cel response sme go pretvorile vo String
        String body = EntityUtils.toString(response.getEntity());
        // cel toj JSON string sme go pretvorile vo objekt
        JSONObject bodyAsObject = new JSONObject(body);

        // person sum go zemal kako string
        String personDataAsString = bodyAsObject.get("person1").toString();
        // person stringot go pretvoram vo objekt
        JSONObject personData = new JSONObject(personDataAsString);
        // get name
        String name = personData.get("name").toString();

        Assert.assertEquals(name, "Deni");
    }

    @Test
    public void getAllPeopleTest() throws Exception {
        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");

        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);
        String messageAsString = bodyAsObject.get("message").toString();
    }

    @Test
    public void postPersonTest() throws Exception {
        JSONObject payloadAsObject = new JSONObject();
        payloadAsObject.put("name", "Pero");
        payloadAsObject.put("surname", "Blazevski");
        payloadAsObject.put("age", 56);
        payloadAsObject.put("isEmployed", true);
        payloadAsObject.put("location", "Skopje");

//        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", payloadAsObject);
        String body = EntityUtils.toString(response.getEntity());
    }

    @Test
    public void postPersonTestWithoutName() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewpersonPayload();

        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);
        PostNewPersonResponce.PostNewPersonResponse postNewPersonResponse;

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponce.PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), "P200");
        Assert.assertEquals(postNewPersonResponse.getPersonData().getName(), "Vlado");
    }

    @Test
    public void updatePersonLocationTest() throws Exception {
        JSONObject payloadAsObject = new JSONObject();
        payloadAsObject.put("location", "Bitola");

        String payloadAsString = payloadAsObject.toString();

        response = peopleApiClient.httpPut("https://people-api1.herokuapp.com/api/person/612ba20357744c30dc7e6fe7",
                payloadAsString);

        String body = EntityUtils.toString(response.getEntity());
    }

    @Test
    public void deletePersonTest() throws Exception {


        HttpResponse postResponse = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person",
                objectToJsonString(postNewPersonPayload.createNewpersonPayload()));

        String postResponseBodyAsString = EntityUtils.toString(postResponse.getEntity());
        PostNewPersonResponce.PostNewPersonResponse postNewPersonResponse = jsonStringToObject(postResponseBodyAsString, PostNewPersonResponce.PostNewPersonResponse.class);

        String createdPersonId = postNewPersonResponse.getPersonData().getId();

        response = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + createdPersonId);

        String body = EntityUtils.toString(response.getEntity());
    }
}