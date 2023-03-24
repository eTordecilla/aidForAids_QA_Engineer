import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class busqueda {

    private WebDriver driver;
    //private WebDriver firefoxDriver;

    @Before
    public void abrirDriver() {
        System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
        //Agregamos esta option al nuestra instancia del driver debido a que la nueva actulización de Chrome nos muestra un error 403 Forbiden
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://www.google.com.co/");

        /*System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        //options.setCapability("marionette", false);
        firefoxDriver = new FirefoxDriver(options);
        firefoxDriver.manage().window().maximize();
        firefoxDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        firefoxDriver.get("https://www.google.com.co/");*/
    }

    @Test
    public void hacerUnaBusqueda() {
        WebElement searchBox = driver.findElement(By.name("q"));
        //WebElement searchBox = firefoxDriver.findElement(By.name("q"));
        searchBox.clear();
        searchBox.sendKeys("Aid for Aids");
        searchBox.submit();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //firefoxDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //assertEquals("Aid for Aids - Google Search", driver.getTitle()); //encontramos coincidencias en los títulos

        // Resultados de búsqueda y contar los que pertenecen a aidforaids.org
        List<WebElement> searchResults = driver.findElements(By.cssSelector("div.g"));
        int aidForAidsCount = 0;
        for (WebElement result : searchResults) {
            String url = result.findElement(By.cssSelector("a")).getAttribute("href");
            if (url.contains("aidforaids.org")) {
                aidForAidsCount++;
            }
        }
        System.out.println("Número de resultados de aidforaids.org: " + aidForAidsCount);

        // Validar que el número de resultados de aidforaids.org sea mayor a cero
        if (aidForAidsCount > 0) {
            System.out.println("El número de resultados de aidforaids.org es mayor a cero.");
        } else {
            System.out.println("No se encontraron resultados de aidforaids.org.");
        }

    }

    @After
    public void cerrarDriver() {
        driver.quit();
    }
}
