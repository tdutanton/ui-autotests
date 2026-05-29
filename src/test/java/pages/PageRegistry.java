package pages;

import static com.codeborne.selenide.Selenide.open;

import java.util.Map;
import java.util.function.Supplier;

public class PageRegistry {

  private static final Map<String, String> routes = Map.of(
      "SampleAppPage", "http://www.uitestingplayground.com/sampleapp",
      "FramesPage", "http://www.uitestingplayground.com/frames"
  );

  private static final Map<String, Supplier<Page>> pages = Map.of(
      "SampleAppPage", SampleAppPage::new
//      "FramesPage", FramesPage::new
  );

  public static Page load(String pageName) {
    System.out.println("Open the page: " + routes.get(pageName));
    open(routes.get(pageName));
    return pages.get(pageName).get();
  }

}
