package br.com.softdesign;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CampoTreinamentoPage {

    private final Utilitarios utilitarios;

    public CampoTreinamentoPage(WebDriver webDriver) {
        utilitarios = new Utilitarios(webDriver);
    }

    public void setNome(String nome) {
        utilitarios.inserirTextoCampo("elementosForm:nome", nome);
    }

    public void setSobrenome(String sobrenome) {
        utilitarios.inserirTextoCampo("elementosForm:sobrenome", sobrenome);
    }

    public void setSexoMasculino() {
        utilitarios.clicarElemento("elementosForm:sexo:0");
    }

    public void setComidaFavoritaFrango() {
        utilitarios.clicarElemento("elementosForm:comidaFavorita:1");
    }

    public void setEscolaridade(String textoVisivel) {
        utilitarios.selecionarComboBox("elementosForm:escolaridade", textoVisivel);
    }

    public void setEsporte(String textoVisivel) {
        utilitarios.selecionarComboBox("elementosForm:esportes", textoVisivel);
    }

    public void inserirTextoSugestoes(String texto) {
        utilitarios.inserirTextoCampo("elementosForm:sugestoes", texto);
    }

    public void clicarBotaoCadastrar() {
        utilitarios.clicarElemento("elementosForm:cadastrar");
    }

    public String obterResultadoCadastro() {
        return utilitarios.pegarTextoElemento("resultado");
    }

    public String obterNomeCadastro() {
        return utilitarios.pegarTextoElemento("descNome");
    }

    public String obterSobrenomeCadastro() {
        return utilitarios.pegarTextoElemento("descSobrenome");
    }

    public String obterSexoCadastro() {
        return utilitarios.pegarTextoElemento("descSexo");
    }

    public String obterComidaFrangoCadastro() {
        return utilitarios.pegarTextoElemento("descComida");
    }

    public String obterEscolaridadeSuperiorCadastro() {
        return utilitarios.pegarTextoElemento("descEscolaridade");
    }

    public String obterEsporteKarateCadastro() {
        return utilitarios.pegarTextoElemento("descEsportes");
    }

    public String obterTextoSugestao() {
        return utilitarios.pegarTextoElemento("descSugestoes");
    }

    public String obterTextoAlertaConfirmar() {
        return utilitarios.pegarTextoAlertaAceitar();
    }
}
