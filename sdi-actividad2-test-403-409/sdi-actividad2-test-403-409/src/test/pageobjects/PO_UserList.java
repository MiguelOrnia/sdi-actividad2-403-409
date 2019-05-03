package test.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import test.utils.SeleniumUtils;

public class PO_UserList extends PO_View {

	public static void goToPage(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free",
				"//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
	}

	public static void deleteUser(WebDriver driver, int... indexes) {
		List<WebElement> checkboxes = PO_View.checkElement(driver, "class",
				"checkBox");
		for (int i : indexes)
			checkboxes.get(i).click();
		List<WebElement> btnDelete = PO_View.checkElement(driver, "class",
				"delete");
		btnDelete.get(0).click();
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1,
			String textIdiom2, int locale1, int locale2) {
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("user.title", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("user.system", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("delete.button", locale1), getTimeout());
		// Cambiamos a segundo idioma
		PO_NavView.changeIdiom(driver, textIdiom2);
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("user.title", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("user.system", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("delete.button", locale2), getTimeout());
		// Volvemos a Español.
		PO_NavView.changeIdiom(driver, textIdiom1);
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("user.title", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("user.system", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("delete.button", locale1), getTimeout());
	}
}
