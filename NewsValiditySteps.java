package Steps;

import Base.BaseUtil;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsValiditySteps extends BaseUtil{
    private BaseUtil base;
    private String firstNewsText;
    private List<WebElement> searchedArticles;
    private String[] firstNewsWords;
    private int matchCount;

    public NewsValiditySteps(BaseUtil base){
        this.base = base;
    }
    @When("^I read the first news displayed$")
    public void iReadTheFirstNewsDisplayed() throws Throwable {
        List<WebElement> newsList = base.Driver.findElements(By.xpath("//a[@data-link-name='article']"));
        System.out.println("Displayed article's count is :" +newsList.size());

        //Reading first article
        String firstNews = newsList.get(0).getAttribute("innerText");
        System.out.println("First article is :" +firstNews);
        this.firstNewsText = firstNews;
        firstNewsWords = firstNewsText.split(" ");

        System.out.println("individual words from 1st news : " + Arrays.toString(firstNewsWords));

    }

    @And("^I read the search result from google for same topic$")
    public void iReadTheSearchResultFromGoogleForSameTopic() throws Throwable {
        ((JavascriptExecutor) base.Driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(base.Driver.getWindowHandles());
        base.Driver.switchTo().window(tabs.get(1));
        base.Driver.navigate().to("https://www.google.com");
        base.Driver.findElement(By.xpath("//input[@class='gLFyf gsfi']")).click();
        base.Driver.findElement(By.xpath("//input[@class='gLFyf gsfi']")).sendKeys(firstNewsText);
        base.Driver.findElement(By.xpath("//input[@class='gLFyf gsfi']")).sendKeys(Keys.ENTER);

        List<WebElement> searchResultList = base.Driver.findElements(By.xpath("//h3[@class='LC20lb']/span"));

        this.searchedArticles = searchResultList;

    }

    @Then("^I should see google search displaying relevant information$")
    public void iShouldSeeGoogleSearchDisplayingRelevantInformation() throws Throwable{
        if (searchedArticles.size()>0) {
            int matchingLinksCount = 0;
            for (int i = 0; i < searchedArticles.size(); i++) {
               String searchResultString = searchedArticles.get(i).getAttribute("innerText");
               System.out.println("text for  " +i+ "th element is " + searchResultString);
               String[] wordsFromSearchResultString = searchResultString.split(" ");
               System.out.println("words from " +i+ "th result string are " +Arrays.toString(wordsFromSearchResultString));
               Arrays.sort(wordsFromSearchResultString);
               Arrays.sort(firstNewsWords);
               int matchCountResult = findMatchCount(wordsFromSearchResultString, firstNewsWords);
               System.out.println("matching words' count between news from The Guardian and google result for " +i+ "th seerch result string is :" +matchCountResult);
               //Assert.assertTrue("Count of matching words from displayed result is more than 2 ", matchCountResult>2);
               if (matchCountResult>0){
                   matchingLinksCount++;
               }
                System.out.println("Count of matching links matching the words from search string is : " +matchingLinksCount);
            }
            Assert.assertTrue("more than 2 matching links found: ", matchingLinksCount>2);

        }else {
            Assert.fail("***********FAILED: Mentioned article displayed 0 results**********");
        }
    }

    public int findMatchCount(final String[] a, final String[] b){
        matchCount = 0;
        for (int i=0; i< a.length; i++){
            for (int j=0; j<b.length; j++){
                if(a[i].equalsIgnoreCase(b[j])){
                    matchCount++;
                }
            }
        }
        return matchCount;
    }
}
