import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.Keys.DELETE;
import static org.openqa.selenium.Keys.HOME;

public class CardDeliveryTest {

    @Test
    void shouldSendForm() {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Чебоксары");
        $("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, HOME)+(DELETE));
        $("[placeholder='Дата встречи']").setValue(LocalDate.now().plusDays(7).format(formatter));
        $("[name='name']").setValue("Иван Петров");
        $("[name='phone']").setValue("+79990805555");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
