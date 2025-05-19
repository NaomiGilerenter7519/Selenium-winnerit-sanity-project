package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {
    WebDriver driver;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    By forPayment = By.xpath("//button[.//span[text()='לקופה']]");
    By userName=By.id("username");
    By password=By.id("password");
    By loginButton = By.xpath("//button[text()='התחבר']");
    By click=By.cssSelector("div[aria-labelledby='WorldPayIframe-label']");
    By cardNumber=By.id("cardNumber");
    By cardholderName=By.id("cardholderName");
    By expiryYear=By.id("expiryYear");
    By expiryMonth=By.id("expiryMonth");
    By securityCode=By.id("securityCode");
    By submitButton=By.id("submitButton");

    public void toPay(String name,String pass) throws InterruptedException {
        driver.findElement(forPayment).click();
        driver.findElement(userName).sendKeys(name);
        driver.findElement(password).sendKeys(pass);
//        driver.navigate().to("https://auth.next.co.il/authorize/resume?state=L7JnWEPcgC6Nk7Ac9tS4tGMNd2FBRh8b&ext-country=IL&ext-lang=he&QParam=");

    }

    public void enterCreditCardDetails(){
        driver.findElement(click).click();
        driver.findElement(cardNumber).sendKeys("1234123412341234");
        driver.findElement(cardholderName).sendKeys("Sara Levi");
        driver.findElement(expiryYear).sendKeys("2030");
        driver.findElement(expiryMonth).sendKeys("07");
        driver.findElement(securityCode).sendKeys("232");
        driver.findElement(submitButton).click();
    }
}
