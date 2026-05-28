package hooks;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.commands.TakeScreenshot;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;

public class Hooks {

  private static final Logger log = LoggerFactory.getLogger(Hooks.class);

  @BeforeAll
  public static void setup() {
    Hooks.log.info("Установка конфигурации браузера");
    Configuration.browser = "chrome";
    Configuration.browserSize = "1920x1080";
    Configuration.timeout = 10000;
    Configuration.headless = false;

    SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
        .screenshots(true)
        .savePageSource(false)
        .includeSelenideSteps(true));
  }

  @After
  public void addScreenshotOnFailure(Scenario scenario) {
    if (scenario.isFailed()) {
      if (WebDriverRunner.hasWebDriverStarted()) {
        try {
          byte[] screenshot = Selenide.screenshot(OutputType.BYTES);

          Allure.getLifecycle().addAttachment(
              "Screenshot on failure",
              "image/png",
              "png",
              new ByteArrayInputStream(screenshot)
          );
        } catch (Exception e) {
          log.error("Failed to take a screenshot", e);
        }
      }
    }
  }

  @AfterAll
  public static void afterAll() {
    log.info("Закрытие веб-драйвера");
    SelenideLogger.removeListener("AllureSelenide");
    Selenide.closeWebDriver();
  }

}
