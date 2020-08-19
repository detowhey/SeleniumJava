package br.com.softdesign;

import org.junit.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FormularioTeste {

    private WebDriver webDriver;

    @BeforeClass
    public static void configurarParametros() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/henrique.almeida/Documents/henrique/drivers/chromedriver.exe");
    }

    @Before
    public void inicializarConfiguracaoDoWebDriver() {
        webDriver = new ChromeDriver();
        webDriver.get(System.getProperty("user.dir") + "/src/test/resources/componentes.html");
    }

    @After
    public void fecharWebDriver() {
        webDriver.quit();
    }

    @Test
    public void validarValorTextField() {

        webDriver.findElement(By.id("elementosForm:nome")).sendKeys("Escrita");
        assertEquals("Escrita", webDriver.findElement(By.id("elementosForm:nome")).getAttribute("value"));
    }

    @Test
    public void validarValorTextArea() {

        webDriver.findElement(By.id("elementosForm:sugestoes")).sendKeys("Escrita, teste, henrique");
        assertEquals("Escrita, teste, henrique",
                webDriver.findElement(By.id("elementosForm:sugestoes")).getAttribute("value"));
    }

    @Test
    public void clicarRadioButton() {

        webDriver.findElement(By.id("elementosForm:sexo:0")).click();
        assertTrue(webDriver.findElement(By.id("elementosForm:sexo:0")).isSelected());
    }

    @Test
    public void clicarCheckBox() {

        webDriver.findElement(By.id("elementosForm:comidaFavorita:2")).click();
        assertTrue(webDriver.findElement(By.id("elementosForm:comidaFavorita:2")).isSelected());
    }

    @Test
    public void selecionarOpcaoComboBox() {

        WebElement elemento = webDriver.findElement(By.id("elementosForm:escolaridade"));
        Select comboBox = new Select(elemento);
        //comboBox.selectByIndex(2) -> index;
        //comboBox.selectByValue("superior") -> value;
        comboBox.selectByVisibleText("Mestrado")/* -> valor mostrado na página HTML*/;
        assertEquals("Mestrado", comboBox.getFirstSelectedOption().getText());
    }

    @Test
    public void verificarValorOpcaoComboBox() {

        webDriver.get(System.getProperty("user.dir") + "/src/test/resources/componentes.html");
        WebElement elemento = webDriver.findElement(By.id("elementosForm:escolaridade"));

        Select comboBox = new Select(elemento);
        List<WebElement> listaElementos = comboBox.getOptions();

        assertEquals(8, listaElementos.size());
        assertTrue(listaElementos.stream().anyMatch(e -> e.getText().equals("Mestrado")));
    }

    @Test
    public void verificarMultiplaEscolhaComboBox() {

        WebElement elemento = webDriver.findElement(By.id("elementosForm:esportes"));
        Select comboBox = new Select(elemento);

        comboBox.selectByVisibleText("Natacao");
        comboBox.selectByVisibleText("Corrida");
        comboBox.selectByVisibleText("O que eh esporte?");

        List<WebElement> listaElemento = comboBox.getAllSelectedOptions();
        assertEquals(3, listaElemento.size());

        comboBox.deselectByVisibleText("Natacao");
        listaElemento = comboBox.getAllSelectedOptions();
        assertEquals(2, listaElemento.size());
    }

    @Test
    public void clicarBotao() {

        WebElement botaoHTML = webDriver.findElement(By.id("buttonSimple"));
        botaoHTML.click();
        assertEquals("Obrigado!", botaoHTML.getAttribute("value"));
    }

    @Test
    @Ignore
    public void clicarLink() {

        WebElement link = webDriver.findElement(By.linkText("Voltar"));
        link.click();
        assertEquals("Voltou!", webDriver.findElement(By.id("resultado")).getText());
    }

    @Test
    public void verificarTextoDaPagina() {

        assertEquals("campo de treinamento", webDriver.findElement(By.tagName("h3")).getText().toLowerCase());
        assertEquals("cuidado onde clica, muitas armadilhas...", webDriver.findElement(By.className("facilAchar"))
                .getText().toLowerCase());
    }

    @Test
    public void aceitarAlert() {

        webDriver.findElement(By.id("alert")).click();
        Alert alert = webDriver.switchTo().alert();
        String textoAlert = alert.getText();
        alert.accept();
        webDriver.findElement(By.id("elementosForm:nome")).sendKeys(textoAlert);
        assertEquals("Alert Simples", textoAlert);
    }

    @Test
    public void confirmarCancelarAlert() {

        webDriver.findElement(By.id("confirm")).click();
        Alert alert = webDriver.switchTo().alert();
        assertEquals("Confirm Simples", alert.getText());
        alert.accept();
        assertEquals("Confirmado", alert.getText());
        alert.accept();

        webDriver.findElement(By.id("confirm")).click();
        alert = webDriver.switchTo().alert();
        assertEquals("Confirm Simples", alert.getText());
        alert.dismiss();
        assertEquals("Negado", alert.getText());
        alert.accept();
    }

    @Test
    public void enviarTextoParaAlerta() {

        webDriver.findElement(By.id("prompt")).click();
        Alert alert = webDriver.switchTo().alert();
        assertEquals("Digite um numero", alert.getText());
        alert.sendKeys("14");
        alert.accept();
        assertEquals("Era 14?", alert.getText());
        alert.accept();
        assertEquals(":D", alert.getText());
        alert.accept();
    }

    @Test
    public void cadastrarComSucesso() {

        webDriver.findElement(By.id("elementosForm:nome")).sendKeys("Henrique");
        webDriver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Almeida");
        webDriver.findElement(By.id("elementosForm:sexo:0")).click();
        webDriver.findElement(By.id("elementosForm:comidaFavorita:1")).click();

        Select selectEscolaridade = new Select(webDriver.findElement(By.id("elementosForm:escolaridade")));
        selectEscolaridade.selectByVisibleText("Superior");

        Select selectEsporte = new Select(webDriver.findElement(By.id("elementosForm:esportes")));
        selectEsporte.selectByVisibleText("Corrida");

        webDriver.findElement(By.id("elementosForm:sugestoes")).sendKeys("Eu gosto de estudar Java");
        webDriver.findElement(By.id("elementosForm:cadastrar")).click();

        assertTrue(webDriver.findElement(By.id("resultado")).getText().startsWith("Cadastrado!"));
        assertTrue(webDriver.findElement(By.id("descNome")).getText().endsWith("Henrique"));
    }

    @Test
    public void executarFrameHTML() {

        webDriver.switchTo().frame("frame1");
        webDriver.findElement(By.id("frameButton")).click();
        Alert alert = webDriver.switchTo().alert();
        String mensagemAlert = alert.getText();
        alert.accept();
        assertEquals("Frame OK!", mensagemAlert);
        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.id("elementosForm:nome")).sendKeys(mensagemAlert);
    }

    @Test
    public void executarOutraJanelaFacil() {

        webDriver.findElement(By.id("buttonPopUpEasy")).click();
        webDriver.switchTo().window("Popup");
        webDriver.findElement(By.tagName("textarea")).sendKeys("Selenium");
        webDriver.close();
        webDriver.switchTo().window("");
        webDriver.findElement(By.tagName("textarea")).sendKeys("Java com Selenium");
    }

    @Test
    public void executarOutraJanela() {

        webDriver.findElement(By.id("buttonPopUpHard")).click();
        webDriver.switchTo().window(webDriver.getWindowHandles().toArray()[1].toString());
        webDriver.findElement(By.tagName("textarea")).sendKeys("Java e Kotlin");
        webDriver.switchTo().window(webDriver.getWindowHandles().toArray()[0].toString());
        webDriver.findElement(By.tagName("textarea")).sendKeys("Java e Selenium");
    }
}
