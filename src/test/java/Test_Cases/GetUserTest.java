package Test_Cases;

import PostMan_Requests.UserRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetUserTest {
    String id = "";

    @BeforeClass
    // create user first to use it for the get req
    public void preconditios() {
        int random = (int)(Math.random() * 50 +1);
        id = UserRequest.CreateUser("sohila","s"+random+"@gmail","female","active")
                .jsonPath().get("id").toString();
    }
    @Test
    public void getSingleUserSuccess()
    {
        Response response = UserRequest.getSingleUser(id);

        // Valid Endpoint
        response.then().statusCode(200);

        Assert.assertEquals(response.jsonPath().get("id").toString(),id,"Actual id does not equal the expected id!!");

    }
    @AfterClass
    public void DeleteTheCreatedUser()
    {

        UserRequest.deleteSingleUser(id);
    }

}
