package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.TestContext;

public class FramesSteps {
  @Когда("во фрейме {string} нажимаю кнопку {string}")
  public void clickButtonInFrame(String frameLabel, String buttonName) {
    ((pages.frames.FramesPage) TestContext.getCurrentPage()).clickButtonInFrame(frameLabel, buttonName);
  }

  @Тогда("во фрейме {string} отображается текст {string}")
  public void checkTextInFrame(String frameLabel, String expectedText) {
    ((pages.frames.FramesPage) TestContext.getCurrentPage()).checkTextInFrame(frameLabel, expectedText);
  }

  @Тогда("во фрейме {string} результат {string}")
  public void verifyFrameResult(String frameLabel, String result) {
    String actualResult = ((pages.frames.FramesPage) TestContext.getCurrentPage()).getResultFromFrame(frameLabel);
    if (!actualResult.contains(result)) {
      throw new AssertionError(
          String.format("Expected result to contain '%s', but got '%s'", result, actualResult)
      );
    }
  }

}
