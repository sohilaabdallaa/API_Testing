package PostMan_Requests;

import Utills.constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserRequest {
    public static Response CreateUser(String name, String email, String gender, String status)
    {
        Response response = RestAssured.given().log().all()
                .headers(constants.getHeaders())
                .contentType(ContentType.JSON)
                .body(" {\n" +
                        "        \"name\": \""+name+"\",\n" +
                        "        \"email\": \""+email+"\",\n" +
                        "        \"gender\": \""+gender+"\",\n" +
                        "        \"status\": \""+status+"\"\n" +
                        "    }").post(constants.BaseURL+constants.Endpoint);
        return  response;
    }

    public static Response getSingleUser(String id)
    {
        Response response = RestAssured.given().log().all()
                .headers(constants.getHeaders())
                .contentType(ContentType.JSON).get(constants.BaseURL+constants.Endpoint+id);
        response.prettyPrint();
        return response;
    }
    public static Response deleteSingleUser(String id)
    {
        Response response = RestAssured.given().log().all()
                .headers(constants.getHeaders())
                .contentType(ContentType.JSON).delete(constants.BaseURL+constants.Endpoint+id);
        return response;
    }


    public static Response CreatePost(String title, String body, String UserId)
    {
        Response response = RestAssured.given().log().all()
                .headers(constants.getHeaders())
                .contentType(ContentType.JSON)
                .body(" {\n" +
                        "        \"title\": \""+title+"\",\n" +
                        "        \"body\": \""+body+"\"\n" +
                        "    }").post(constants.BaseURL+constants.Endpoint+UserId+constants.PostsEndPoint);
        return  response;
    }
}
