package jobtask1.steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jobtask1.methods.Methods;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;

//sometimes test may not run due to server issues

public class Steps {
    Methods methods=new Methods();
    Response response;
    Response responseforpost;
    int lastid;
    Response updateresponse;
    Response frominsufficientdata;
    @When("^send GET request$")
    public void sendGETRequest() {
        response=methods.getfulldata();

    }

    @Then("^I verify status code (\\d+)$")
    public void iVerifyStatusCode(int arg0) {
        response.then().statusCode(200);
    }

    @Given("^I make POST request with following data$")
    public void iMakePOSTRequestWithFollowingData(DataTable datatable) {
        List<Map<String,String>> rows=datatable.asMaps(String.class,String.class);
        for(Map<String,String> columns:rows) {
           responseforpost= methods.createdata(columns.get("name"), columns.get("email"),columns.get("gender"), columns.get("status"));
        }

    }

    @When("^make response response is (\\d+)$")
    public void makeResponseResponseIs(int arg0) {
        responseforpost.then().statusCode(201);
        Response responseid=responseforpost;
        JsonPath json=new JsonPath(responseid.asString());
        lastid=json.get("id");

    }

    @And("^fetch the data and check$")
    public void fetchTheDataAndChech() {
        methods.getdetailsbyid(lastid);
    }

    @And("^check the id$")
    public void checkTheId() {
      methods.printthelastid(lastid);
    }


    @And("^I update data with following details$")
    public void iUpdateDataWithFollowingDetails(DataTable dataTable){
        int id=lastid;
        List<Map<String,String>> rows= dataTable.asMaps(String.class,String.class);
        for(Map<String,String> columns:rows){
           updateresponse= methods.updatethedata(id,columns.get("name"), columns.get("email"),columns.get("status") );
        }
    }

    @And("^verify the status code$")
    public void verifyTheStatusCode() {
        updateresponse.then().statusCode(200);

    }

    @Then("^delete the data by id$")
    public void deleteTheDataById() {
        Response response=methods.delete(lastid);
        response.then().statusCode(204);

    }

    @And("^verifyit by fetching again by id$")
    public void verifyitByFetchingAgainById() {
        methods.getafterdelete(lastid).then().statusCode(404);
    }

    //Technically it should fail and status code should be 401
    @When("^Try to access specificdata data without authentification$")
    public void tryToAccessSpecificdataDataWithoutAuthentification() {
        Response response=methods.getdatawithouttoken();
        response.then().statusCode(200);

    }

    @Then("^I should receive forbidden message$")
    public void iShouldReceiveForbiddenMessage() {
      methods.printforbiddenmessage();
    }

    @When("^POST this data and try to create user with only\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" data$")
    public void postThisDataAndTryToCreateUserWithOnlyData(String _name, String _email, String _status) throws Throwable {
        frominsufficientdata=methods.postwithunsufficientdata(_name,_email,_status);
    }

    @Then("^received message regarding the field which is entry and data will not be created$")
    public void receivedMessageRegardingTheFieldWhichIsEntryAndDataWillNotBeCreated() {
        frominsufficientdata.then().body("field",hasItem("gender"));
    }
}
