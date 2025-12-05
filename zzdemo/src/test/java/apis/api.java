package apis;

import java.util.HashMap;

import org.hamcrest.Matcher;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class api {
	
	@Test(dataProvider = "data")
	public void login(String username, String password, int id) {
//		import static org.hamcrest.Matchers.*;  -- different - library
		
//		what is query params? Query parameters are the key–value pairs added after the ? in a URL
		
		String baseuri = "https://restful-booker.herokuapp.com";
		
//		JSONObject data = new JSONObject();  // json-path
//		data.put("username", "admin");
//		data.put("password", "password123");
		
//		HashMap<String, String> map = new HashMap<>();  // jackson-databind
//		map.put("username", "admin");
//		map.put("password", "password123");
		
		HashMap<String, String> map = new HashMap<>();  // jackson-databind // can use with query params too
		map.put("username", username);
		map.put("password", password);
		
	Response res =
		RestAssured
			.given()
//				.contentType(ContentType.JSON)
				.contentType("application/json")
				.baseUri(baseuri)
//				.body(data.toString())
				.body(map)
				.pathParam("userid", id)  //-- /{userid}
				.queryParam("page", 2, "limit", 10)  // -- /users?page=2&limit=10	
			.when()
				.post("/auth")
			.then()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.contentType(ContentType.JSON)
				.log().status()
				.log().body()
			.extract()
				.response();
		
		String token = res.path("token");
		System.out.println("token is : " + token);
	}
	
	@DataProvider(name = "data")
	public Object[][] logindata(){
		return new Object[][] {
			{"admin", "password123"},
			{"admin123", "password"}
		};
	}
}
