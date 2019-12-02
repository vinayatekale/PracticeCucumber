package Steps;

import Base.BaseUtil;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Hook extends BaseUtil{
    private BaseUtil base;

    public Hook(BaseUtil base){
        this.base = base;
    }


    @Before
    public void InitializeTest(){
        System.out.println("Opening the browser");
        System.setProperty("webdriver.chrome.driver", "C:\\Interesting\\Auto\\AutoSample\\drivers\\chromedriver.exe");
        base.Driver = new ChromeDriver();
    }

    @After
    public void TearDownTest(Scenario scenario){
        if(scenario.isFailed()){
            System.out.println("Failed scenario is " +scenario.getName());
        }
        base.Driver.close();
        base.Driver.quit();
    }
}
