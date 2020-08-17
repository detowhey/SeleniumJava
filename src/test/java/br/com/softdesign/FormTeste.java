package br.com.softdesign;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FormTeste {

    @BeforeClass
    public static void configurarParametros() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/henrique.almeida/Documents/henrique/drivers/chromedriver.exe");

    }

    @Test
    public void testarPaginaHTML() {
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");
        driver.quit();
    }
}
