package com.example.demo.Config;
import jakarta.annotation.PreDestroy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebDriverConfig {
    private WebDriver driver;

    @Bean
    public WebDriver webDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;
    }

    @PreDestroy
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
