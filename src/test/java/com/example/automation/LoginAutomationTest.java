package com.example.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            driver.get("http://localhost:8083");

            // Locate the username and password fields
            WebElement usernameField = driver.findElement(By.id("username"));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("loginButton"));

            // Perform login
            usernameField.sendKeys("testUser");
            passwordField.sendKeys("testPassword");
            loginButton.click();

            // Wait for the page to load after the login
            Thread.sleep(1000); // Wait for 1 second (or use WebDriverWait for better synchronization)

            // Validate successful login (check if the title of the page is "Dashboard")
            String expectedTitle = "Dashboard";
            String actualTitle = driver.getTitle();
            assertEquals(expectedTitle, actualTitle, "Login test failed: Titles do not match.");

            // Optional: You can also check the page content to ensure it's correct
            WebElement successMessage = driver.findElement(By.tagName("h1"));
            assertEquals("Welcome to your Dashboard!", successMessage.getText(), "Login test failed: Unexpected page content.");
        } catch (InterruptedException e) {
            // Handle interruption during Thread.sleep
            System.err.println("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Preserve interrupt status
        } catch (Exception e) {
            // Handle other exceptions
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
