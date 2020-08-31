package br.com.softdesign;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RegrasCadastroTest {

    private WebDriver webDriver;
    private Utilitarios utilitario;
    private CampoTreinamentoPage pagina;

    @Parameterized.Parameter
    public String nome;

    @Parameterized.Parameter(value = 1)
    public String sobrenome;

    @Parameterized.Parameter(value = 2)
    public String sexo;

    @Parameterized.Parameter(value = 3)
    public List<String> listaComidas;

    @Parameterized.Parameter(value = 4)
    public String[] esportes;

    @Parameterized.Parameter(value = 5)
    public String mensagem;


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

    @Parameterized.Parameters
    public static Collection<Object[]> getCollection() {

        return Arrays.asList(new Object[][]{
                {"", "", "", Arrays.asList(), new String[]{}, "Nome eh obrigatorio"},
                {"Henrique", "", "", Arrays.asList(), new String[]{}, "Sobrnome eh obrigatorio"}
        });
    }

    @Test
    public void validarEsporteIndeciso() {

        pagina.setNome(nome);
        pagina.setSobrenome(sobrenome);

        if (sexo.equals("Masculino"))
            pagina.setSexoMasculino();

        if(sexo.equals("Feminino"))
            pagina.setSexoFeminino();

        if (listaComidas.contains("Carne"))
            pagina.setComidaFavoritaCarne();

        if (listaComidas.contains("Frango"))
            pagina.setComidaFavoritaFrango();

        if (listaComidas.contains("Vegetariano"))
            pagina.setComidaFavoritaVegetal();

        pagina.clicarBotaoCadastrar();
        assertEquals(mensagem, utilitario.pegarTextoAlertaAceitar());
    }
}
