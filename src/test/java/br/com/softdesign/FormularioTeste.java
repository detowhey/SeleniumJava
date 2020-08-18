package br.com.softdesign;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FormularioTeste {

    @BeforeClass
    public static void configurarParametros() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/henrique.almeida/Documents/henrique/drivers/chromedriver.exe");
    }

    @Test
    public void validarValorTextField() {
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Escrita");

        assertEquals("Escrita", driver.findElement(By.id("elementosForm:nome")).getAttribute("value"));
        driver.quit();
    }

    @Test
    public void validarValorTextArea() {
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");
        driver.findElement(By.id("elementosForm:sugestoes")).sendKeys("Escrita, teste, henrique");

        assertEquals("Escrita, teste, henrique", driver.findElement(By.id("elementosForm:sugestoes")).getAttribute("value"));

        driver.quit();
    }

    @Test
    public void clicarRadioButton() {
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");

        driver.findElement(By.id("elementosForm:sexo:0")).click();
        assertTrue(driver.findElement(By.id("elementosForm:sexo:0")).isSelected());
        driver.quit();
    }

    @Test
    public void clicarCheckBox() {
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");

        driver.findElement(By.id("elementosForm:comidaFavorita:2")).click();
        assertTrue(driver.findElement(By.id("elementosForm:comidaFavorita:2")).isSelected());
        driver.quit();
    }

    @Test
    public void selecionarOpcaoComboBox() {
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");

        WebElement elemento = driver.findElement(By.id("elementosForm:escolaridade"));
        Select comboBox = new Select(elemento);
        //comboBox.selectByIndex(2) -> index;
        //comboBox.selectByValue("superior") -> value;
        comboBox.selectByVisibleText("Mestrado")/* -> valor mostrado*/;

        assertEquals("Mestrado", comboBox.getFirstSelectedOption().getText());
        driver.quit();
    }

    @Test
    public void verificarValorOpcaoComboBox() {
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");

        WebElement elemento = driver.findElement(By.id("elementosForm:escolaridade"));
        Select comboBox = new Select(elemento);
        List<WebElement> listaElementos = comboBox.getOptions();
        boolean encontrou = false;

        for (WebElement elementoHTML : listaElementos) {
            if (elementoHTML.getText().equals("Mestrado")) {
                encontrou = true;
                break;
            }
        }

        assertEquals(8, listaElementos.size());
        assertTrue(encontrou);
        driver.quit();
    }

    @Test
    public void verificarMultiplaEscolhaComboBox() {
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");

        WebElement elemento = driver.findElement(By.id("elementosForm:esportes"));
        Select comboBox = new Select(elemento);

        comboBox.selectByVisibleText("Natacao");
        comboBox.selectByVisibleText("Corrida");
        comboBox.selectByVisibleText("O que eh esporte?");

        List<WebElement> listaElemento = comboBox.getAllSelectedOptions();
        assertEquals(3, listaElemento.size());

        comboBox.deselectByVisibleText("Natacao");
        listaElemento = comboBox.getAllSelectedOptions();
        assertEquals(2, listaElemento.size());
        driver.quit();
    }

    @Test
    public void clicarBotao() {
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");

        WebElement botao = driver.findElement(By.id("buttonSimple"));
        botao.click();

        assertEquals("Obrigado!", botao.getAttribute("value"));
        driver.quit();
    }

    @Test
    public void clicarLink(){
        WebDriver driver = new ChromeDriver();
        driver.get("file://" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");

        WebElement link = driver.findElement(By.linkText("Voltar"));
        link.click();
        driver.quit();
    }
}
