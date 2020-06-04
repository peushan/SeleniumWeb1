package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public WebDriver driver;

   @BeforeClass
    public void setupDriver(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");
        //ChromeOptions chromeOptions = new ChromeOptions();
       // chromeOptions.setExperimentalOption("useAutomationExtension", false);
       // chromeOptions.addArguments("--remote-debugging-port=9222");
        driver = new ChromeDriver();

    }
}
