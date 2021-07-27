import files.reUsableMethods;
import files.CallOut_Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class ReactiveCallOut {
    @Test
    public static void main(String[] args) {

        RestAssured.baseURI = "http://192.168.132.176:54040/api.vixen";

//Create JOB Callout (POST)

        String reactivePath = "jobs/reactive";
        String responseJC =
                given().log().all().header("Content-Type", "application/json")
                        .body(CallOut_Payload.jobCallOutAPI("ADE004", "First cctv callout -  screen not working "))
                        .when().post(reactivePath)
                        .then().assertThat().statusCode(201)
                        .body("body[0].orderNumber", equalTo("DODDS-REF4"))
                        .body("body[0].siteAccount", equalTo("ADE004"))
                        .extract().response().asString();


        System.out.println(responseJC); // body repsonse wrapped in response variable

//        JsonPath js = new JsonPath(response); // for parsing json body response
        JsonPath jsPut = reUsableMethods.rawToJson(responseJC);

        String jobId = jsPut.getString("body[0].jobId"); // convert json to string and get value in jobId
        String engineerName = jsPut.getString("body[0].engineer");
        String href = jsPut.getString("body[0].links");

        System.out.println(System.lineSeparator());
        System.out.println("JOBID Extracted=      " + jobId);
        System.out.println("ENGR NAME Extracted=  " + engineerName);
        System.out.println("HREF Extracted=  " + href);
        System.out.println(System.lineSeparator());

//READ(GET) Job Callout  created  "jobs/reactive/id/"+jobId+""
        String reponseReadJc =
                given()
                        .when().log().all()
                        .get(CallOut_Payload.getSpecificJob() + jobId + "") //  path param for updating specific job  +  jobId
                        .then()
                        .assertThat().statusCode(200)
                        .contentType("application/hal+json;charset=UTF-8")
                        .body("jobId", equalTo(jobId))
                        .body("contact", equalTo("John"))
                        .log().all().extract().response().asString();

        JsonPath jsRead = new JsonPath(reponseReadJc);
        String orderNumber = jsRead.getString("orderNumber");

        Assert.assertEquals("DODDS-REF4", orderNumber); //  NEWWWWWWWWWWWWW

//UPDATE JOB Callout :                                                        http://192.168.132.176:54040/api.vixen/jobs/id/01M00095XXX   ID is updated by path param url
        given().log().all().header("Content-Type", "application/json")
                .body(CallOut_Payload.jobUpdateAPI("contact", "pat")) //  when payload is concatenated.  Objects in json can be changed here  or remove to use original payload
                .log().all()
                .when().put(CallOut_Payload.updateSpecificJob() + jobId + "") //  path param for updating specific job  +  jobId
                .then().assertThat().statusCode(200)
                .contentType("application/json;charset=UTF-8")
                .body("body.jobId", equalTo(jobId)).log().all();

        //DELETE  URL available on swagger but not working --crashing data when used
//
//        given().
//                when().log().all().delete(callOut_Payload.updateSpecificJob() + jobId)
//                .then().assertThat().statusCode(204);


    }

}
