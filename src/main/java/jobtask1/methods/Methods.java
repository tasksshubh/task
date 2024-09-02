package jobtask1.methods;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jobtask1.constants.EndPoints;
import jobtask1.pojo.UserPojo;
import jobtask1.utils.Utilities;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Methods {

    String name;
    String email;
    Response response;
    public Response getfulldata(){
        return given().when().get(EndPoints.GET_ALL_DATA);
    }

    public Response createdata(String _name,String _email, String _gender,String _status){
        name=_name;
        email=Utilities.getRandomValue()+_email;

        UserPojo user=new UserPojo();
        user.setName(name);
        user.setEmail(email);
        user.setGender(_gender);
        user.setStatus(_status);


        return given().header("Authorization","Bearer 308c024bf3ff48b1c6195778fea23f9d5eefd420e4dc617fafabc1de4d63719d")
                .contentType(ContentType.JSON).body(user).when().post(EndPoints.POST_DATA);
    }


    public void fetchdata(){
        HashMap<String,Object> userinfo= SerenityRest.given()
                .when().get(EndPoints.GET_ALL_DATA).then()
                .statusCode(200).extract().path("findAll{it.name='"+name+"'}.get(0)");
        System.out.println(userinfo);

    }

    public void getdetailsbyid(int id){
       response= given().pathParam("id",id).when().get(EndPoints.GET_DATA_BYID);
       /* System.out.println(response);*/
    }

    public void validateGetByIdStatusCode(){
        response.then().statusCode(200);
    }

    public Response updatethedata(int id,String _uname,String _uemail,String _ustatus){
        String uname=_uname+Utilities.getRandomValue();
        String uemail=Utilities.getRandomValue()+_uemail;
        String status=_ustatus;

        UserPojo userpojo1=new UserPojo();
        userpojo1.setName(uname);
        userpojo1.setEmail(uemail);
        userpojo1.setStatus(status);


        Response response=given().pathParam("id",id).contentType(ContentType.JSON)
                .header("Authorization","Bearer 308c024bf3ff48b1c6195778fea23f9d5eefd420e4dc617fafabc1de4d63719d")
                .body(userpojo1).when().patch(EndPoints.UPDATE_BY_ID);
        System.out.println(response);
        return response;
    }
    public Response delete(int id){
        return given().pathParam("id",id)
                .header("Authorization","Bearer 308c024bf3ff48b1c6195778fea23f9d5eefd420e4dc617fafabc1de4d63719d")
                .when().delete(EndPoints.UPDATE_BY_ID);

    }
    public Response getafterdelete(int id){
       Response response= given().pathParam("id",id).when().get(EndPoints.GET_DATA_BYID);

        return response;
    }
     public Response getdatawithouttoken(){
        return given().when().get(EndPoints.DATA_WITHOUT_AUTH);

     }

     public Response postwithunsufficientdata(String _name,String _email,String _status){
        String email=Utilities.getRandomValue()+_email;
        UserPojo user=new UserPojo();
        user.setName(_name);
        user.setEmail(email);
        user.setStatus(_status);

        return given().header("Authorization","Bearer 308c024bf3ff48b1c6195778fea23f9d5eefd420e4dc617fafabc1de4d63719d")
                .contentType(ContentType.JSON).body(user).post(EndPoints.POST_DATA);
     }
      public void printthelastid(int id){
          System.out.println(id);
      }
      public void printforbiddenmessage(){
          System.out.println("People can access data just by id without authentification\" +\n" +
                "\"in real life scenario it should not e the case\")");
      }
}
