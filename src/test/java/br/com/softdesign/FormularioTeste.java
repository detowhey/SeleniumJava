package br.com.softdesign;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FormularioTeste {

    private WebDriver webDriver;
    private Utilitarios utilitario;
    private CampoTreinamentoPage pagina;

    @BeforeClass
    public static void configurarParametros() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/henrique.almeida/Documents/henrique/drivers/chromedriver.exe");
    }

    @Before
    public void inserirConfiguracaoInicial() {
        webDriver = new ChromeDriver();
        webDriver.get(System.getProperty("user.dir") + "/src/test/resources/componentes.html");
        utilitario = new Utilitarios(webDriver);
        pagina = new CampoTreinamentoPage(webDriver);
    }

    @After
    public void fecharWebDriver() {
        webDriver.quit();
    }

    @Test
    public void validarSobrenomeObrigatorio() {

        pagina.setNome("Henrique");
        pagina.clicarBotaoCadastrar();
        assertThat(pagina.obterTextoAlertaConfirmar(), equalToIgnoringCase("sobrenome eh obrigatorio"));
    }

    @Test
    public void clicarCheckBox() {

        WebElement elemento = utilitario.pesquisarElemento("elementosForm:comidaFavorita:2");
        assertTrue(elemento.isEnabled());
    }

    @Test
    public void selecionarOpcaoComboBox() {

        WebElement elemento = utilitario.pesquisarElemento("elementosForm:escolaridade");
        Select comboBox = new Select(elemento);
        comboBox.selectByVisibleText("Mestrado");
        assertEquals("Mestrado", comboBox.getFirstSelectedOption().getText());
    }

    @Test
    public void verificarValorOpcaoComboBox() {

        WebElement elemento = utilitario.pesquisarElemento("elementosForm:escolaridade");

        Select comboBox = new Select(elemento);
        List<WebElement> listaElementos = comboBox.getOptions();

        assertEquals(8, listaElementos.size());
        assertTrue(listaElementos.stream().anyMatch(e -> e.getText().equals("Mestrado")));
    }

    @Test
    public void clicarBotao() {

        WebElement botao = utilitario.pesquisarElemento("buttonSimple");
        botao.click();
        assertEquals("Obrigado!", utilitario.pegarValorElemento("buttonSimple"));
    }

    @Test
    public void clicarLink() {

        WebElement link = webDriver.findElement(By.linkText("Voltar"));
        link.click();
        assertEquals("Voltou!", utilitario.pesquisarElemento("resultado").getText());
    }

    @Test
    public void verificarTextoDaPagina() {

        assertEquals("campo de treinamento", webDriver.findElement(By.tagName("h3")).getText().toLowerCase());
        assertEquals("cuidado onde clica, muitas armadilhas...",
                utilitario.pesquisarElemento(By.className("facilAchar")).getText().toLowerCase());
    }

    @Test
    public void aceitarAlert() {

        utilitario.pesquisarElemento("alert").click();
        Alert alert = webDriver.switchTo().alert();

        String textoAlert = alert.getText();
        alert.accept();
        utilitario.inserirTextoCampo("elementosForm:nome", textoAlert);
        assertEquals("Alert Simples", textoAlert);
    }

    @Test
    public void confirmarCancelarAlert() {

        utilitario.pesquisarElemento("confirm").click();
        Alert alert = webDriver.switchTo().alert();
        assertEquals("Confirm Simples", alert.getText());
        alert.accept();
        assertEquals("Confirmado", alert.getText());
        alert.accept();

        utilitario.pesquisarElemento("confirm").click();
        alert = webDriver.switchTo().alert();
        assertEquals("Confirm Simples", alert.getText());
        alert.dismiss();
        assertEquals("Negado", alert.getText());
        alert.accept();
    }

    @Test
    public void enviarTextoParaAlerta() {

        utilitario.pesquisarElemento("prompt").click();
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

        pagina.setNome("Henrique");
        pagina.setSobrenome("Almeida");
        pagina.setSexoMasculino();
        pagina.setComidaFavoritaFrango();
        pagina.setEscolaridade("Superior");
        pagina.setEsporte("Karate");
        pagina.inserirTextoSugestoes("Java e Kotlin são legais!");
        pagina.clicarBotaoCadastrar();

        assertTrue(pagina.obterResultadoCadastro().startsWith("Cadastrado!"));
        assertTrue(pagina.obterNomeCadastro().endsWith("Henrique"));
        assertTrue(pagina.obterSobrenomeCadastro().endsWith("Almeida"));
        assertEquals("sobrenome: almeida", pagina.obterSobrenomeCadastro().toLowerCase());
        assertEquals("comida: frango", pagina.obterComidaFrangoCadastro().toLowerCase());
        assertEquals("escolaridade: superior", pagina.obterEscolaridadeSuperiorCadastro().toLowerCase());
        assertEquals("esportes: karate", pagina.obterEsporteKarateCadastro().toLowerCase());
        assertEquals("sugestoes: java e kotlin são legais!", pagina.obterTextoSugestao().toLowerCase());
        assertThat(pagina.obterTextoSugestao(),
                equalToIgnoringCase("sugestoes: java e kotlin são legais!"));
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
