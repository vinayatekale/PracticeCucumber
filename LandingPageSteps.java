package Steps;

import Base.BaseUtil;
import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LandingPageSteps extends BaseUtil {

    private BaseUtil base;

    public LandingPageSteps(BaseUtil base){
        this.base = base;
    }

    @Given("^I navigate to the Guardian website$")
    public void iNavigateToTheGuardianWebsite() {
        System.out.println(("I am on guardian page -- I navigate to method"));
        base.Driver.navigate().to("https://www.theguardian.com");
        base.Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @When("^I Accept the cookies message$")
    public void iAcceptTheCookiesMessage() {
        base.Driver.findElement(By.xpath("//span[contains(text(),'m OK with that')]")).click();
    }

    @Then("^I should see the Guardian website default page$")
    public void iShouldSeeTheGuardianWebsiteDefaultPage() {

        System.out.println(("I should see the Guardian landing page"));
        String windowTitle = base.Driver.getTitle();
        Assert.assertEquals(true, windowTitle.contains("The Guardian"));
    }
}
