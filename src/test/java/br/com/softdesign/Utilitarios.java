package br.com.softdesign;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class Utilitarios {

    private final WebDriver webDriver;

    public Utilitarios(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement pesquisarElemento(By tipoPesquisa) {
        return webDriver.findElement(tipoPesquisa);
    }

    public WebElement pesquisarElemento(String idElemento) {
        return pesquisarElemento(By.id(idElemento));
    }

    public String pegarValorElemento(String idElemento) {
        return pesquisarElemento(idElemento).getAttribute("value");
    }

    public String pegarTextoElemento(String idElmento) {
        return pesquisarElemento(idElmento).getText();
    }

    public String pegarTextoElemento(By tipoPesquisa) {
        return pesquisarElemento(tipoPesquisa).getText();
    }

    public boolean saberItemSelecionado(String idElemento) {
        return pesquisarElemento(idElemento).isSelected();
    }

    public boolean saberItemSelecionado(By tipoPesquisa) {
        return pesquisarElemento(tipoPesquisa).isSelected();
    }

    public String pegarValorElemento(By tipoPesquisa) {
        return pesquisarElemento(tipoPesquisa).getAttribute("value");
    }

    public void inserirTextoCampo(By tipoPeqsuisa, String textoEscrito) {
        pesquisarElemento(tipoPeqsuisa).sendKeys(textoEscrito);
    }

    public void inserirTextoCampo(String idElemento, String textoEscrito) {
        pesquisarElemento(idElemento).sendKeys(textoEscrito);
    }

    public void clicarElemento(String idElemento) {
        pesquisarElemento(idElemento).click();
    }

    public void clicarElemento(By tipoPesquisa) {
        pesquisarElemento(tipoPesquisa).click();
    }

    public void selecionarComboBox(String idElemento, String valorVisivel) {
        new Select(pesquisarElemento(idElemento)).selectByVisibleText(valorVisivel);
    }

    public void selecionarComboBox(By tipoPesquisa, String valorVisivel) {
        new Select(pesquisarElemento(tipoPesquisa)).selectByVisibleText(valorVisivel);
    }

    public boolean verificarRadioMarcado(String idElemento) {
        return pesquisarElemento(idElemento).isSelected();
    }

    public boolean verificarRadioMarcado(By tipoPesquisa) {
        return pesquisarElemento(tipoPesquisa).isSelected();
    }

    public void mudarDeJanela(String tituloJanela) {
        webDriver.switchTo().window(tituloJanela);
    }

    public String pegarTextoAlertaAceitar() {
        Alert alerta = webDriver.switchTo().alert();
        String textoAlert = alerta.getText();
        alerta.accept();

        return textoAlert;
    }

    public void clicarLink(String textoLink) {
        pesquisarElemento(By.linkText(textoLink)).click();
    }


    public int obterQuantidadeOpcoesCombo(String idElemento) {

        WebElement element = pesquisarElemento(idElemento);
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();
        return options.size();
    }

    public List<String> obterValoresCombo(String idElemento) {
        WebElement elemento = pesquisarElemento(idElemento);
        Select combo = new Select(elemento);

        List<WebElement> opcoesSelecionadas = combo.getAllSelectedOptions();
        List<String> valores = new ArrayList<>();

        for (WebElement opcao : opcoesSelecionadas)
            valores.add(opcao.getText());

        return valores;
    }

    public boolean verificarOpcaoCombo(String idElemento, String opcao) {

        WebElement element = pesquisarElemento(idElemento);
        Select combo = new Select(element);
        List<WebElement> opcoes = combo.getOptions();

        return opcoes.stream().anyMatch(item -> item.getText().equalsIgnoreCase(opcao));
    }
}
