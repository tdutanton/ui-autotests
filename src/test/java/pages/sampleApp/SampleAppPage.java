package pages.sampleApp;

import java.util.Map;
import org.openqa.selenium.By;
import pages.BasePage;
import pages.Page;

public class SampleAppPage extends BasePage implements Page {

  private final Map<String, By> fields = Map.of(
      "UserName", By.name("UserName"),
      "Password", By.name("Password")
  );

  private final Map<String, By> buttons = Map.of(
      "BtnLogIm", By.xpath("//*[@id=\"login\"]")
  );

  private final Map<String, By> labels = Map.of(
      "LblLoginStatus", By.xpath("//*[@id=\"loginstatus\"]")
  );

  @Override
  public void fillField(String fieldName, String value) {
    By locator = fields.get(fieldName);
    fillField(locator, value);
  }

  @Override
  public void clickButton(String buttonName) {
    By locator = buttons.get(buttonName);
    click(locator);
  }

  @Override
  public void checkText(String fieldName, String value) {
    By locator = labels.get(fieldName);
    shouldHaveText(locator, value);
  }

  @Override
  public void checkTextByAttributeValue(String fieldName, String value) {
    By locator = fields.get(fieldName);
    shouldHaveTextByAttributeValue(locator, value);
  }

  @Override
  public void selectDropdown(String fieldName, String value) {
    throw new UnsupportedOperationException("selectDropdown not supported for SampleApp");
  }
}

