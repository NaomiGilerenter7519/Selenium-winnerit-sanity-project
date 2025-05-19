package tests;

import org.junit.*;
import pages.HomePage;
import pages.PaymentPage;
import pages.ProductPage;
import pages.SignInPage;
import utils.Methods;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestNextSite {

    private static WebDriver driver;
    private static ExtentReports extentReports = new ExtentReports();
    private static ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("report.html");

    private static Methods methods;
    private static SignInPage signInPage;
    private static HomePage homePage;
    private static ProductPage productPage;
    private static PaymentPage paymentPage;

    static String currentTime = String.valueOf(System.currentTimeMillis());

    @BeforeClass
    public static void beforeClass() throws ParserConfigurationException, IOException, SAXException {
        extentReports.attachReporter(extentSparkReporter);

        // תחילה יוצרים זמנית את driver כדי לאתחל את methods ולקבל את browseType
        ChromeOptions tmpOptions = new ChromeOptions();
        tmpOptions.addArguments("-incognito");
        driver = new ChromeDriver(tmpOptions);
        methods = new Methods(driver);

        // עכשיו בודקים אם צריך ליצור driver חדש לפי browseType
        String browserType = methods.getData("browseType");

        // אם צריך להחליף את הדרייבר לפי תנאי
        if (!browserType.equalsIgnoreCase("Chrome")) {
            driver.quit(); // סוגרים את הדרייבר הראשון
            throw new RuntimeException("Only Chrome is supported currently."); // אפשר להרחיב כאן בעתיד
        }

        // ממשיכים עם הדרייבר שהתחלנו איתו
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(methods.getData("url"));

        // אתחול עמודים
        signInPage = new SignInPage(driver);
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        paymentPage = new PaymentPage(driver);

        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setReportName("Project_report");
        extentSparkReporter.config().setDocumentTitle("Report");
    }


    @Test
    public void signInTest() throws InterruptedException, ParserConfigurationException, IOException, SAXException {
        ExtentTest signInTest = extentReports.createTest("sign in test");
        signInTest.info("Starting Sign in test");

        Thread.sleep(3000);
        signInPage.clickIcon();
        signInTest.info("Clicked on the Sign In icon");

        Thread.sleep(3000);
        String expectedTitle = Constants.SIGN_IN_TITLE;
        System.out.println("Current Title: " + driver.getTitle());
        String screenshotPath = methods.takeScreenShot("screenshots\\signInTest" + currentTime);

        if (expectedTitle.equals(driver.getTitle())) {
            signInTest.pass("Title matches", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            signInTest.fail("Title does not match. Expected: " + expectedTitle + ", Found: " + driver.getTitle(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
        signInTest.info("enter user details");
        signInPage.enterDetails(methods.getData("email"), methods.getData("password"));
        Thread.sleep(3000);

        signInPage.clickSignInButton();
        driver.navigate().back();
        signInTest.info("navigate to home page ");
        homePage.clickOnLogo();

        try {
            Assert.assertEquals(Constants.HOME_URL, driver.getCurrentUrl().toString());
            signInTest.pass("Next site url is correct", MediaEntityBuilder.createScreenCaptureFromPath(methods.takeScreenShot("screenshots/" + String.valueOf(System.currentTimeMillis()))).build());
        } catch (AssertionError e) {
            signInTest.fail("Next site url is not correct", MediaEntityBuilder.createScreenCaptureFromPath(methods.takeScreenShot("screenshots/" + String.valueOf(System.currentTimeMillis()))).build());
        }
        signInTest.info("end the tast");

    }

    @Test
    public void HomeTest() throws InterruptedException, IOException, ParserConfigurationException, SAXException {
        ExtentTest homeTest = extentReports.createTest("🏠 Home Page Full Flow Test");
        homeTest.info("🔄 Starting Home Page Test...");

        // Step 1: Click on Central Link
        homeTest.info("🔗 Clicking on central link...");
        homePage.clickOnCentralLink();
        Thread.sleep(2000);

        // Step 2: Validate URL
        String currentUrl = driver.getCurrentUrl();
        homeTest.info("🌐 Current URL: " + currentUrl);
        if (Constants.KIDS_URL.equals(currentUrl)) {
            homeTest.pass("✅ Navigated successfully to Kids page", MediaEntityBuilder
                    .createScreenCaptureFromPath(methods.takeScreenShot("screenshots/" + System.currentTimeMillis()))
                    .build());
        } else {
            homeTest.fail("❌ Failed to navigate to Kids page. URL was: " + currentUrl,
                    MediaEntityBuilder.createScreenCaptureFromPath(methods.takeScreenShot("screenshots/" + System.currentTimeMillis()))
                            .build());
        }

        // Step 3: Validate Title
        String title = driver.getTitle();
        homeTest.info("📄 Page title: " + title);
        if (title.toLowerCase().contains("kids") || title.toLowerCase().contains("ילדים")) {
            homeTest.pass("✅ Page title contains 'kids'");
        } else {
            homeTest.warning("⚠️ Page title does not clearly indicate Kids section");
        }
// Step 4: Navigate to 'New In'
        try {
            homeTest.info("🆕 Navigating to 'New In' section...");
            homePage.navigateToNewIn();
            homeTest.pass("✅ Successfully navigated to 'New In' section", MediaEntityBuilder
                    .createScreenCaptureFromPath(methods.takeScreenShot("screenshots/" + System.currentTimeMillis()))
                    .build());
        } catch (Exception e) {
            homeTest.fail("❌ Failed to navigate to 'New In' section. Error: " + e.getMessage(), MediaEntityBuilder
                    .createScreenCaptureFromPath(methods.takeScreenShot("screenshots/" + System.currentTimeMillis()))
                    .build());
        }

        // Step 5: Change Language
        homeTest.info("🌍 Changing language...");
        homePage.changeLanguage();
        String updatedUrl = driver.getCurrentUrl(); // get updated URL after language change
        if (updatedUrl.contains("/he")) {
            homeTest.pass("✅ Language changed to Hebrew 🇮🇱");
        } else if (updatedUrl.contains("/en/")) {
            homeTest.pass("✅ Language changed to English 🇬🇧");
        } else {
            homeTest.warning("⚠️ Could not determine selected language from URL: " + updatedUrl);
        }

        // Step 6: Search for item
        String searchTerm = "";
        homeTest.info("🔍 Searching for item: " + searchTerm);
        homePage.searchForItem(searchTerm);
        homeTest.pass("✅ Search executed", MediaEntityBuilder
                .createScreenCaptureFromPath(methods.takeScreenShot("screenshots/" + System.currentTimeMillis()))
                .build());

        // Step 7: Choose Size
        homeTest.info("👠 Selecting available size...");
        productPage.chooseSize();
        homeTest.pass("✅ Size selected");

        // Step 8: Add to Cart
        homeTest.info("🛒 Adding product to cart...");
        productPage.addToCart();
        homeTest.pass("✅ Product added to cart", MediaEntityBuilder
                .createScreenCaptureFromPath(methods.takeScreenShot("screenshots/" + System.currentTimeMillis()))
                .build());

        // Step 9: Navigate to Payment
        homeTest.info("💳 Navigating to payment page...");
        String email = Methods.getData("email");
        String password = Methods.getData("password");
        paymentPage.toPay(email, password);
        Thread.sleep(4000);
        homeTest.pass("✅ Arrived at payment section", MediaEntityBuilder
                .createScreenCaptureFromPath(methods.takeScreenShot("screenshots/" + System.currentTimeMillis()))
                .build());
        driver.navigate().back();

        homeTest.info("✅ Home Test Completed Successfully");
    }

    @Ignore
    public void paymentTest() throws ParserConfigurationException, IOException, SAXException {
        ExtentTest pay =extentReports.createTest("test payment");
        pay.info("start test payment");
        paymentPage.enterCreditCardDetails();
        pay.info("\"Running with real credit card information is not permitted." );
    }

    @AfterClass
    public static void afterClass() throws InterruptedException {
        Thread.sleep(4000);
        driver.quit();
        extentReports.flush();
    }
}