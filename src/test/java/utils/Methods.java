package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Methods {
    WebDriver driver;

    public Methods(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Reads a value from config.xml using the given tag name (keyName).
     */
    public static String getData(String keyName) throws ParserConfigurationException, IOException, SAXException {
        File configXmlFile = new File("config.xml");
        if (!configXmlFile.exists()) {
            throw new IOException("Config file not found at path: " + configXmlFile.getAbsolutePath());
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(configXmlFile);
        doc.getDocumentElement().normalize();

        if (doc.getElementsByTagName(keyName).getLength() == 0) {
            throw new IllegalArgumentException("Key '" + keyName + "' not found in config.xml");
        }

        return doc.getElementsByTagName(keyName).item(0).getTextContent();
    }

    /**
     * Captures a screenshot of the current browser window and saves it as a PNG file.
     */
    public String takeScreenShot(String imagesPath) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(imagesPath + ".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
        return destinationFile.getPath();
    }
}
