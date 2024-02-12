package PostMan_Requests;

import Utills.constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostRequest {

    public static Response getUserPosts(String UserId)
    {
        Response response = RestAssured.given().log().all()
                .headers(constants.getHeaders())
                .contentType(ContentType.JSON).get(constants.BaseURL+constants.Endpoint+ UserId+constants.PostsEndPoint);
        return response;
    }
}
