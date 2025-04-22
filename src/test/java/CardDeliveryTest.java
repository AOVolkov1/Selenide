import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.Keys.DELETE;

public class CardDeliveryTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no sandbox");
//        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");

    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendForm() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        List<WebElement> inputs = formElement.findElements(By.cssSelector("input"));
        inputs.get(0).sendKeys("Чебоксары");
        driver.findElement(By.cssSelector("[data-test-id='date']")).click();
        inputs.get(1).click();
        inputs.get(1).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        inputs.get(1).sendKeys(DELETE);
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        inputs.get(1).sendKeys(LocalDate.now().plusDays(7).format(formatter));
        inputs.get(2).sendKeys("Иван Петров");
        inputs.get(3).sendKeys("+79990805555");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("#root > div > div > div.notification__content"));
        assertEquals("Встреча успешно забронирована на " + LocalDate.now().plusDays(5).format(formatter), resultElement.getText().trim());
        assertTrue(resultElement.isDisplayed());
    }
}
