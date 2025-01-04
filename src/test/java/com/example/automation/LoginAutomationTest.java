package com.example.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;

public class LoginAutomationTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set up the WebDriver (ensure chromedriver is in PATH or specify location)
        System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe"); // Adjust path as needed
        driver = new ChromeDriver();
    }

    @Test
    public void testLogin() {
        try {
            // Navigate to the login page using local file path
            driver.get("file:///C:/webdriver/index.html"); // Local file path to login page

            // Locate the username and password fields
            WebElement usernameField = driver.findElement(By.id("username"));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("loginButton"));

            // Perform login
            usernameField.sendKeys("testUser");
            passwordField.sendKeys("testPassword");
            loginButton.click();

            // Wait for the page to load after the login (check if it's the Dashboard)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.titleIs("Dashboard")); // Wait for the dashboard page title

            // Validate successful login by checking the page title
            String expectedTitle = "Dashboard";
            String actualTitle = driver.getTitle();
            assertEquals(expectedTitle, actualTitle, "Login test failed: Titles do not match.");

            // Optional: Check the page content to ensure it's correct
            WebElement successMessage = driver.findElement(By.tagName("h1"));
            assertEquals("Welcome to your Dashboard!", successMessage.getText(), "Login test failed: Unexpected page content.");
        } catch (Exception e) {
            // Handle any other exceptions
            System.err.println("Error during login test: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to fail the test
        }
    }

    @AfterEach
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
