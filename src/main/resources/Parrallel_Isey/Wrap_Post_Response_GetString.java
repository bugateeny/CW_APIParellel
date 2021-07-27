
public class Basics {


    public static void main (String[] args)
    {

        RestAssured.baseURI = "http://192.168.132.176:54040/api.vixen";

                String response =given().log().all().header("Content-Type", "application/json" )
                        .body(payload.JobCallOutAPI()).

                        when().post("jobs/callout").

                        then().assertThat().statusCode(201)

                        .body("body[0].orderNumber",equalTo ("DODDS-REF4"))
                        .body("body[0].siteAccount",equalTo ("ADE004"))
                        .extract().response().asString();
        System.out.println(response); // body repsonse wrapped in response variable

        JsonPath js = new JsonPath(response); // for parsing json body response
        String jobId = js.getString("body[0].jobId"); // covert jso to string and get value in jobId

        System.out.println("JOBID" + jobId);
    }