package Test_Cases;

import PostMan_Requests.PostRequest;
import PostMan_Requests.UserRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateUserTest {

    String currentId = "";
    @Test
    public void CreateUserTest()
    {
        String Name = "";
        int random = (int)(Math.random() * 50 +1);
        String Reqname = "sohilaabdalla";
        String gender = "female";
        String status = "Active";
        String email = "test"+random+"@gmail.com";
        Response response = UserRequest.CreateUser(Reqname, email,gender,status);
        // check the creation status code
        response.then().statusCode(201);
        //respose.prettyPrint();
        JsonPath jsonpath  = response.jsonPath();
        currentId = jsonpath.get("id").toString();
        Name = jsonpath.get("name").toString();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Name,Reqname,"Name is not as expected");
        softAssert.assertNotNull(jsonpath.get("id").toString(),"Is Is Null");
        softAssert.assertEquals(jsonpath.get("gender").toString(),gender,"gender is not the same");

        String responseBody = response.getBody().toString();
//        if (responseBody.isEmpty()) {
//            System.out.println("Entire JSON response is empty");
//        } else {
//            System.out.println("Entire JSON response is not empty");
//        }
        softAssert.assertEquals(PostRequest.getUserPosts(currentId).getBody().asString(),"[]");

        softAssert.assertAll();

        UserRequest.deleteSingleUser(currentId);
    }

    @Test
    public void vaildateOfAllField()
    {
        String[] FieldsNames = {"email", "name","gender", "status"};
        int i = 0;
        Response response = UserRequest.CreateUser("","","","");
        response.then().statusCode(422);
        JsonPath jsonpath = response.jsonPath();
        SoftAssert softAssert = new SoftAssert();
      for (i = 0; i<4;i++) {
          softAssert.assertEquals(jsonpath.getString("["+i+"].field"),FieldsNames[i]);
      }
        for(i = 0; i < 4; i++) {
            softAssert.assertTrue(jsonpath.getString("[" + i + "].message").contains("can't be blank"));
        }
        softAssert.assertAll();
    }

    @Test
    public void vaildateNameIsNotNull()
    {
        int i = 0;
        Response response = UserRequest.CreateUser("","s@gmail","female","active");
        response.then().statusCode(422);
        JsonPath jsonpath = response.jsonPath();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(jsonpath.getString("[0].field"),"name");
        softAssert.assertEquals(jsonpath.getString("[0].message"), "can't be blank");

        softAssert.assertAll();
    }

}
