package test.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_BoughtView extends PO_View {

	public static void goToPage(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free",
				"//a[contains(@href, 'sales/purchased')]");
		elementos.get(0).click();
	}

	public static int checkNumberOfItems(WebDriver driver) {
		List<WebElement> items = PO_View.checkElement(driver, "class",
				"table-light");
		return items.size();
	}
}
