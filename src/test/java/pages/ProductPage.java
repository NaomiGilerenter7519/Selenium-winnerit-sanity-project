package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class ProductPage {
    WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    By drop = By.xpath("//div[text()='בחירת מידה']");
    By add = By.cssSelector("[data-testid='item-form-addToBag-button']");
    By cart = By.cssSelector("a[data-testid='shopping-bag-link-button']");
    By viewInCart = By.xpath("//a[text()='לצפייה בסל']");
    By plus = By.className("qty-plus");


    public void chooseSize() {
        driver.findElement(drop).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("li[role='option']")
        ));

        for (WebElement option : options) {
            String text = option.getText();
            if (!text.contains("לא זמין כרגע")&&!text.contains("אזל מהמלאי")&&!text.contains("אזל המלאי")) {
                option.click();
                break;
            }
        }
    }

    public void addToCart() {
        driver.findElement(add).click();
        driver.findElement(cart).click();
        driver.findElement(viewInCart).click();
        driver.findElement(plus).click();
    }
}
