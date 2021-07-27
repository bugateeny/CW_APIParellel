import files.reUsableMethods;
import files.jobEventsEndPoint;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class JobEvents {

// sprint VIXF-14783 https://causewayjira.atlassian.net/browse/VIXF-14783

    public static void main(String[] args) {

        RestAssured.baseURI = "http://192.168.132.176:54040/api.vixen";

        String jeResponse =
                given().queryParam("company", "01").queryParam("count", "50")
                        .log().all()
                        .when()
                        .get(jobEventsEndPoint.jobEvents())
                        .then().assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println(jeResponse);

        JsonPath jsGet = reUsableMethods.rawToJson(jeResponse);

        String orderNo = jsGet.getString("jobEvents[46].orderNumber");
        String eventId = jsGet.getString("jobEvents[46].eventsId");

        Assert.assertEquals(orderNo, "DODDS00");
        Assert.assertEquals(eventId, "01ADE00100000007");


    }

}
