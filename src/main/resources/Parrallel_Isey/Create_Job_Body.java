import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics {


    public static void main (String[] args)
    {

        RestAssured.baseURI = "http://192.168.132.176:54040/api.vixen";

                given().log().all().header("Content-Type", "application/json" )
                        .body("{\n" +
                                "    \"companyNumber\": \"01\",\n" +
                                "    \"contact\": \"John\",\n" +
                                "    \"description\": \"New Call Out\",\n" +
                                "    \"siteAccount\": \"ADE004\",\n" +
                                "    \"orderNumber\": \"DODDS-REF4\",\n" +
                                "    \"priority\": \"1\"\n" +
                                "}").

                        when().post("jobs/callout").

                        then().log().all().assertThat().statusCode(201)
                        .body("body[0].orderNumber",equalTo ("DODDS-REF4"))
                        .body("body[0].siteAccount",equalTo ("ADE004"));
    }

}


