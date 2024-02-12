package Utills;

import java.util.HashMap;

public class constants {

    public static String BaseURL = BaseURL = "https://gorest.co.in/public/v2";
    public static String Endpoint = "/users/";
    public static String PostsEndPoint = "/posts";
    public static HashMap<String, String> getHeaders()
    {
        HashMap<String,String> headerKeys = new HashMap<>();
        headerKeys.put("Authorization","Bearer 4e524e66f188ae98f9eeb7eff74ffeeb64b1a016b78263a1da799e75f5cdd50d");
        return headerKeys;
    }
}
