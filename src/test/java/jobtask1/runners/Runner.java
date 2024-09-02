package jobtask1.runners;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/resources/feature"
                    ,glue="jobtask1.steps"
                   ,monochrome = true
                    , plugin = {"pretty", "html:target/cucumberreports/"
                    ,"json:target/RunCuke/cucumber.json"}
                    /*,tags="@negative"*/
)
public class Runner {
}
