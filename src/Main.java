import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Main {
    public static WebDriver startChromeDriver(String strFileNameDriver, WebDriver webdriver) {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\" + strFileNameDriver);
        webdriver = new ChromeDriver();
        webdriver.manage().window().maximize();
        webdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        return webdriver;
    }


    public static void main(String[] args) {
        String strFileNameDriver = "chromedriver118.exe";
        WebDriver webdriver = null;

        /*
        form login
         */
        String strUsername = "standard_user";
        String strPassword = "secret_sauce";

        String[] strProdukName = {"Sauce Labs Backpack",
                "Sauce Labs Bike Light",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Fleece Jacket"};

        /*
        form checkout
         */
        String strFirstName = "Steven";
        String strLastName = "Geo";
        String strZipCode = "32333";


        webdriver = startChromeDriver(strFileNameDriver, webdriver);

        webdriver.get("https://www.saucedemo.com/");

        webdriver.findElement(By.xpath("//input[@name='user-name']")).sendKeys(strUsername);
        webdriver.findElement(By.xpath("//input[@name='password']")).sendKeys(strPassword);
        webdriver.findElement(By.xpath("//input[@name='login-button']")).click();

        Boolean loginFailed = false;
        int intSizeWrong = webdriver.findElements(By.xpath("//h3[@data-test='error']")).size();
        if (intSizeWrong > 0)
            loginFailed = true;

        Assert.assertFalse("Wrong username or password\n", loginFailed);

        /*
        click all add to chart produk by value
         */
        for (int i = 0; i < strProdukName.length; i++) {
            webdriver.findElement(By.xpath("//div//a//div[text()='" + strProdukName[i] + "']//parent::a//parent::div//following-sibling::div//button")).click();
        }

        webdriver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
        webdriver.findElement(By.xpath("//button[@id='checkout']")).click();


        webdriver.findElement(By.xpath("//input[@id='first-name']")).sendKeys(strFirstName);
        webdriver.findElement(By.xpath("//input[@id='last-name']")).sendKeys(strLastName);
        webdriver.findElement(By.xpath("//input[@id='postal-code']")).sendKeys(strZipCode);

        webdriver.findElement(By.xpath("//input[@id='continue']")).click();
        webdriver.findElement(By.xpath("//button[@id='finish']")).click();

        String expectedURL = "https://www.saucedemo.com/checkout-complete.html";
        String getCurrentURL = webdriver.getCurrentUrl();

        Assert.assertTrue("URL not match\n", expectedURL.equals(getCurrentURL));
        System.out.println("Done");

        webdriver.close();
    }
}