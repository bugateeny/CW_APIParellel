package files;

import io.restassured.path.json.JsonPath;

public class reUsableMethods
{

    public static JsonPath rawToJson(String responseJC){

        JsonPath js = new JsonPath(responseJC); // for parsing json body response
        return js;
    }
}
