package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignInPage {

    WebDriver driver;
    //constructor
    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }

    //locator
    By icon= By.cssSelector("a[data-testid='header-adaptive-my-account-icon-container-link']");
    By email= By.id("username");
    By password= By.id("password");
    By button = By.cssSelector("button[data-action-button-primary='true']");

    public void clickIcon(){driver.findElement(icon).click();}
    public void enterDetails(String userEmail,String userPassword){
        driver.findElement(email).sendKeys(userEmail);
        driver.findElement(password).sendKeys(userPassword);
//        driver.findElement(signInButton).click();

    }
    public void clickSignInButton() throws InterruptedException {
        WebElement signInButton = driver.findElement(button);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signInButton);
        Thread.sleep(500); // תן זמן לדף להתייצב
        signInButton.click();


    }

}
