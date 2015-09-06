import java.io.File;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Oscilloscope {
    private WebDriver driver;
    private java.lang.String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://www.google.com.ua/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testOscilloscope() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.id("lst-ib")).clear();
        driver.findElement(By.id("lst-ib")).sendKeys("осциллограф");
        driver.findElement(By.xpath("//*[@id='sblsbb']/button")).click();
        // Warning: verifyTextPresent may require manual changes
        boolean appearance=true; 
        int number = 1;
        while(!driver.getPageSource().contains("vit.ua")){
            	try{
                    driver.findElement(By.xpath("//*[@id='pnnext']")).click();
                    number++;
                   }
            	catch (NoSuchElementException e){
                    appearance=false;
                    break;
                    }
            	 //This loop is created via Selenium IDE for waitForTextPresent command
                 // Warning: waitForTextPresent may require manual changes 
                   for (int second = 0;; second++) {
                    	if (second >= 60) fail("timeout");
                    	try { if (driver.findElement(By.cssSelector("#resultStats")).isDisplayed()) break; } catch (Exception e) {}
                    	Thread.sleep(1000);
                    }
            }
        
        if(appearance){
        	System.out.println("Number = "+ number);
        	File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("C:\\Selenium\\Screenshots\\Screen01.png"));
        }
        else
        	System.out.println("The last page is reached!");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        java.lang.String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
