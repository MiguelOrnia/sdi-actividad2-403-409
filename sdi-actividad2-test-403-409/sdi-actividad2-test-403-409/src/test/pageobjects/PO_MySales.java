package test.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_MySales extends PO_View {

	public static void goToPage(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free",
				"//a[contains(@href, 'sales/list')]");
		elementos.get(0).click();
	}

	public static boolean deleteItem(WebDriver driver, int index) {
		List<WebElement> deleteLink = PO_View.checkElement(driver, "class",
				"deleteSale");
		WebElement we = deleteLink.get(index);
		deleteLink.get(index).click();
		return checkDeleted(driver, we);
	}

	public static int checkNumberOfDeleteableItems(WebDriver driver) {
		List<WebElement> deleteableItems = new ArrayList<WebElement>();
		deleteableItems = PO_View.checkElement(driver, "class", "deleteSale");
		return deleteableItems.size();
	}

	public static void checkItem(WebDriver driver, int index) {
		List<WebElement> deleteLink = PO_View.checkElement(driver, "class",
				"deleteSale");
		WebElement we = deleteLink.get(index);
		System.out.println(we.toString());
	}

	private static boolean checkDeleted(WebDriver driver, WebElement we) {
		return !PO_View.checkElement(driver, "class", "deleteSale")
				.contains(we);
	}

}
