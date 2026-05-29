package pages.frames;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;

import com.codeborne.selenide.SelenideElement;
import java.util.Map;
import org.openqa.selenium.By;
import pages.BasePage;
import pages.Page;

public class FramesPage extends BasePage implements Page {

  private final By resultText = By.id("result");
  private final Map<String, By> frameLabels = Map.of(
      "Outer Frame", By.cssSelector("iframe#frame-outer"),  // или By.name("frame-outer")
      "Inner Frame", By.cssSelector("iframe#frame-inner")   // или By.name("frame-inner")
  );

  private final Map<String, By> buttons = Map.of(
      "Edit", By.xpath("//button[text()='Edit']"),
      "Submit", By.xpath("//button[text()='Submit']"),
      "Click me", By.xpath("//button[text()='Click me']"),
      "Primary", By.xpath("//button[text()='Primary']")
  );

  private void switchToFrame(String frameKey) {
    By frameLocator = frameLabels.get(frameKey);
    if (frameLocator == null) {
      throw new IllegalArgumentException("Frame not found: " + frameKey);
    }
    if ("Inner Frame".equals(frameKey)) {
      switchTo().defaultContent();
      SelenideElement outerFrame = $(frameLabels.get("Outer Frame")).shouldBe(visible);
      switchTo().frame(outerFrame);
      SelenideElement innerFrame = $(frameLocator).shouldBe(visible);
      switchTo().frame(innerFrame);
    } else {
      switchTo().defaultContent();
      SelenideElement frame = $(frameLocator).shouldBe(visible);
      switchTo().frame(frame);
    }
  }

  private void switchToDefaultContent() {
    switchTo().defaultContent();
  }

  @Override
  public void fillField(String fieldName, String value) {
    // Для фреймов не используется
    throw new UnsupportedOperationException("fillField not supported for FramesPage");
  }

  @Override
  public void clickButton(String buttonName) {
    // Для фреймов нужно указать, в каком фрейме кликать
    throw new UnsupportedOperationException("Use clickButtonInFrame(frameLabel, buttonName) instead");
  }

  @Override
  public void checkText(String fieldName, String value) {
    // Для фреймов нужно указать, в каком фрейме проверять
    throw new UnsupportedOperationException("Use checkTextInFrame(frameLabel, value) instead");
  }

  @Override
  public void checkTextByAttributeValue(String fieldName, String value) {
    throw new UnsupportedOperationException("checkTextByAttributeValue not supported for FramesPage");
  }

  @Override
  public void selectDropdown(String fieldName, String value) {
    throw new UnsupportedOperationException("selectDropdown not supported for FramesPage");
  }

  public void clickButtonInFrame(String frameLabel, String buttonName) {
    try {
      switchToFrame(frameLabel);
      By buttonLocator = buttons.get(buttonName);
      if (buttonLocator == null) {
        throw new IllegalArgumentException("Button not found: " + buttonName);
      }
      click(buttonLocator);
    } finally {
      switchToDefaultContent();
    }
  }

  public void checkTextInFrame(String frameLabel, String expectedText) {
    try {
      switchToFrame(frameLabel);
      shouldHaveText(resultText, "Button pressed: " + expectedText);
    } finally {
      switchToDefaultContent();
    }
  }

  public String getResultFromFrame(String frameLabel) {
    try {
      switchToFrame(frameLabel);
      return $(resultText).getText();
    } finally {
      switchToDefaultContent();
    }
  }

}
