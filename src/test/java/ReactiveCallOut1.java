import files.CallOut_Payload;
import files.reUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReactiveCallOut1 {

    private static final String  BASE_URL ="http://192.168.132.176:54040/api.vixen";

    @Test(dataProvider = "JobCallOutData")
    public void jobCallOut(String siteAccount, String description)
        {

        RestAssured.baseURI = BASE_URL;

//Create JOB Callout (POST)
        String responseJC =
                given().log().all().header("Content-Type", "application/json")
                        .body(CallOut_Payload.jobCallOutAPI(siteAccount, description))  // With Data provider Dynamically control data  when payload is concatenated.  Objects in json can be changed here  or remove to use original payload
                        .when().post("jobs/reactive").
                        then().assertThat().statusCode(201)
                        .body("body[0].orderNumber", equalTo("DODDS-REF4"))
                        .body("body[0].siteAccount", equalTo("ADE004"))
                        .extract().response().asString();

        System.out.println(responseJC); // body response wrapped in response variable

//        JsonPath js = new JsonPath(response); // for parsing json body response
        JsonPath jsPut = reUsableMethods.rawToJson(responseJC);

        String jobId = jsPut.getString("body[0].jobId"); // convert json to string and get value in jobId
//        String engineerName = jsPut.getString("body[0].engineer");
//        String href = jsPut.getString("body[0].links");



//READ(GET) Job Callout  created  "jobs/reactive/id/"+jobId+""
        String responseReadJc =
                given()
                        .when().log().all()
                        .get(CallOut_Payload.getSpecificJob() + jobId + "") //  path param for updating specific job  +  jobId
                        .then()
                        .assertThat().statusCode(200)
                        .contentType("application/hal+json;charset=UTF-8")
                        .body("jobId", equalTo(jobId))
                        .body("contact", equalTo("John"))
                        .log().all().extract().response().asString();

        JsonPath jsRead = new JsonPath(responseReadJc);
        String orderNumber = jsRead.getString("orderNumber");

        Assert.assertEquals("DODDS-REF4", orderNumber); //  NEWWWWWWWWWWWWW assert

//UPDATE JOB Callouts :                                                        http://192.168.132.176:54040/api.vixen/jobs/id/01M00095XXX   ID is updated by path param url
        given().log().all().header("Content-Type", "application/json")
                .body(CallOut_Payload.jobUpdateAPI("Bugatiny", "Here is the test for PUT description ")) //  when payload is concatenated.  Objects in json can be changed here  or remove to use original payload
//                .log().all()
                .when().put(CallOut_Payload.updateSpecificJob() + jobId + "") //  path param for updating specific job  +  jobId
                .then().assertThat().statusCode(200)
                .body("body.jobId", equalTo(jobId));

        //DELETE  URL available on swagger but not working --crashing data when used

//       given().
//                when().log().all().delete(callOut_Payload.updateSpecificJob() + jobId)
//                .then().assertThat().statusCode(204);

    }

    @DataProvider (name= "JobCallOutData")
    public Object[][] getData() {
        //multi dimensional array
        return new Object[][]{
                {"ADE004", "2nd CCTV call out"},
                {"ADE004", "3rd CCTV call out"},
                {"ADE004", "4nd CCTV call out"},
                {"ADE004", "5th CCTV call out"}};    //  Run multiple data sets @Test
    }


}

