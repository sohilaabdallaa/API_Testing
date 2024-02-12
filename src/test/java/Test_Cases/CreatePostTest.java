package Test_Cases;

import PostMan_Requests.PostRequest;
import PostMan_Requests.UserRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreatePostTest {
    String userId = "";
    @BeforeClass
    public void precondition()
    {
        int random = (int)(Math.random() * 500 + 1);
        userId = UserRequest.CreateUser("sohila","s"+random+"@gmail","female","active").jsonPath().getString("id");
    }
    @Test
    public void createNewPostTest() {
        String title = "Totam cetera abeo";
        String body = "Deludo beatae administratio. Confido surgo adsuesco. Dignissimos " +
                "absconditus ea. Somnus similique vita. Ascit spectaculum sint.";

        int totalPostsLengthBefore = PostRequest.getUserPosts(userId).getBody().asString().length();
        Response response = UserRequest.CreatePost(title, body, userId);
        int totalPostsLengthAfter = PostRequest.getUserPosts(userId).getBody().asString().length();
        response.then().statusCode(201);
        JsonPath jsonpath = response.jsonPath();


        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(jsonpath.getString("user_id"),userId,"User Id not the same!");
        softAssert.assertNotNull(jsonpath.get("id"));
        //softAssert.assertNotEquals(totalPostsLengthBefore,totalPostsLengthAfter);

        softAssert.assertAll();

    }

    @Test
    public void checkDuplicationOfPost()
    {
        String firstCreationPostId = "";
        String duplicateCreationPostId = "";
        String title = "Totam cetera abeo";
        String body = "Deludo beatae administratio. Confido surgo adsuesco. Dignissimos absconditus ea. Somnus similique vita. Ascit spectaculum sint.";

        firstCreationPostId = UserRequest.CreatePost(title,body,userId).jsonPath().get("id").toString();
        duplicateCreationPostId = UserRequest.CreatePost(title,body,userId).jsonPath().get("id").toString();

        // check that duplicated post gets different id
        Assert.assertFalse(firstCreationPostId.equals(duplicateCreationPostId));
    }
    @Test
    public void createPostWithDoesNotExistUser()
    {
        UserRequest.deleteSingleUser(userId);
        System.out.println(userId);
        Response response = UserRequest.CreatePost(" hello World!", "first Java App", userId);
        response.then().statusCode(422); //unprocessable Entity
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(response.jsonPath().getString("[0].field"),"user");
        softAssert.assertEquals(response.jsonPath().getString("[0].message"),"must exist");

        softAssert.assertAll();
    }
    @AfterClass
    public void postcondition()
    {
        UserRequest.deleteSingleUser(userId);
    }
}