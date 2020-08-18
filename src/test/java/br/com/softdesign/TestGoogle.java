package br.com.softdesign;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestGoogle {

    @BeforeClass
    public static void configurarParametros() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/henrique.almeida/Documents/henrique/drivers/chromedriver.exe");
    }

    @Test
    public void testarGoogle() {

        WebDriver driver = new ChromeDriver();

        driver.get("http://www.google.com");
//        driver.manage().window().setPosition(new Point(100, 100));
        driver.manage().window().maximize();
        driver.quit();
    }
}
