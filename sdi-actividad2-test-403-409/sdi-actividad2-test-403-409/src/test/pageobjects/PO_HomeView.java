package test.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import test.utils.SeleniumUtils;

public class PO_HomeView extends PO_NavView {

	static public void checkWelcome(WebDriver driver, int language) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("welcome.message", language), getTimeout());
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1,
			String textIdiom2, int locale1, int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkWelcome(driver, locale1);
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("home.authenticated", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("home.money", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("home.sales", locale1), getTimeout());
		// Cambiamos a segundo idioma
		PO_HomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_HomeView.checkWelcome(driver, locale2);
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("home.authenticated", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("home.money", locale2), getTimeout());
		// Volvemos a Español.
		PO_HomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkWelcome(driver, locale1);
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("home.authenticated", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("home.money", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("home.sales", locale1), getTimeout());
	}

	public static void goToPage(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//a[contains(@href, '/')]");
		elementos.get(0).click();
	}

	public static double getUserBalance(WebDriver driver) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "balance", getTimeout());
		String etiquetaDinero = elementos.get(0).getText();
		String[] dinero = etiquetaDinero.split(":");
		return Double
				.parseDouble(dinero[1].substring(0, dinero[1].length() - 1));
	}
}
