package com.example.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class LoginAutomationTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set up the WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe"); // Adjust path as needed
        driver = new ChromeDriver();
    }

    @Test
    public void testLogin() {
        try {
            // Navigate to the login page
            driver.get("https://example.com/login");

            // Locate the username and password fields
            WebElement usernameField = driver.findElement(By.id("username"));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("loginButton"));

            // Perform login
            usernameField.sendKeys("testUser");
            passwordField.sendKeys("testPassword");
            loginButton.click();

            // Validate successful login
            String expectedTitle = "Dashboard";
            String actualTitle = driver.getTitle();
            assertEquals(expectedTitle, actualTitle, "Login test failed: Titles do not match.");
        } catch (Exception e) {
            System.err.println("Error during login test: " + e.getMessage());
            throw e;
        }
    }

    @AfterEach
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
