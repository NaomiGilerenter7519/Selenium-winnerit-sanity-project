package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    By logo = By.cssSelector(".nx-icon.nav-nextlogo.white");
    By centralLink = By.xpath("//img[@alt='Kids love NEXT']");
    By homeLink = By.id("meganav-link-6");
    By newIn = By.cssSelector("a[title='New In']");
    By language=By.cssSelector("img[data-testid='header-country-lang-flag']");
    By hebrew=By.cssSelector("button[data-testid='country-selector-language-button-0']");
    By shopNow=By.cssSelector("button[data-testid='country-selector-CTA-button']");
    By search =By.id("header-big-screen-search-box");
    By selectItem = By.cssSelector("a[data-testid='product_summary_image_media']");
//methods
    public void clickOnLogo(){
        driver.findElement(logo).click();
    }
    public void clickOnCentralLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(centralLink));
        link.click();
    }

    public void navigateToNewIn(){
        driver.findElement(homeLink).click();
        driver.findElement(newIn).click();
    }


    public void changeLanguage(){
    driver.findElement(language).click();
    driver.findElement(hebrew).click();
    driver.findElement(shopNow).click();
    }
    public void searchForItem(String item){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(search));
        element.sendKeys(item + Keys.ENTER);
        //driver.findElement(search).sendKeys(item + Keys.ENTER);

        driver.findElement(selectItem).click();

    }
}
