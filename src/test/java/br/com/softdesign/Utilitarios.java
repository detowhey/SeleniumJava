package br.com.softdesign;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utilitarios {

    private final WebDriver webDriver;

    public Utilitarios(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void escreverTextoPeloId(String idElemento, String textoEscrito) {
        webDriver.findElement(By.id(idElemento)).sendKeys(textoEscrito);
    }

    public String pegarValorCampo(String idElemento) {
        return webDriver.findElement(By.id(idElemento)).getAttribute("value");
    }

    public WebElement pesquisarElementoPeloId(String elementoID) {
        return webDriver.findElement(By.id(elementoID));
    }
}
