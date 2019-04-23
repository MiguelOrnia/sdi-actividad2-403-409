package test.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import test.utils.SeleniumUtils;

public class PO_NavView extends PO_View {

	public static void clickOption(WebDriver driver, String textOption,
			String criterio, String textoDestino) {
		// CLickamos en la opción de registro y esperamos a que se cargue el
		// enlace de Registro.
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"@href", textOption, getTimeout());
		// Tiene que haber un sólo elemento.
		assertTrue(elementos.size() == 1);
		// Ahora lo clickamos
		elementos.get(0).click();
		// Esperamos a que sea visible un elemento concreto
		elementos = SeleniumUtils.EsperaCargaPagina(driver, criterio,
				textoDestino, getTimeout());
		// Tiene que haber un sólo elemento.
		assertTrue(elementos.size() == 1);
	}

	public static void changeIdiom(WebDriver driver, String textLanguage) {
		// clickamos la opción Idioma.
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "btnLanguage", getTimeout());
		elementos.get(0).click();
		// Esperamos a que aparezca el menú de opciones.
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"languageDropdownMenuButton", getTimeout());
		// SeleniumUtils.esperarSegundos(driver, 2);
		// CLickamos la opción Inglés partiendo de la opción Español
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", textLanguage,
				getTimeout());
		elementos.get(0).click();
	}

	public static void checkIsAdmin(WebDriver driver) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "admin", getTimeout());
		assertTrue(elementos.size() == 1);
	}

	public static void checkIsUser(WebDriver driver) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "users", getTimeout());
		assertTrue(elementos.size() == 2);
	}

	static public void checkChangeIdiomUser(WebDriver driver, String textIdiom1,
			String textIdiom2, int locale1, int locale2) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.home", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.add", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.list", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.search", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.bought", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("messages.title", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("logout.message", locale1), getTimeout());
		// Cambiamos a segundo idioma
		PO_NavView.changeIdiom(driver, textIdiom2);
		elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.home", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.add", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.list", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.search", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.bought", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("messages.title", locale2), getTimeout());
		// Volvemos a Español.
		PO_NavView.changeIdiom(driver, textIdiom1);
		elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.home", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.add", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.list", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.search", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.manage.sales.bought", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("messages.title", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("logout.message", locale1), getTimeout());
	}

	static public void checkChangeIdiomAdmin(WebDriver driver,
			String textIdiom1, String textIdiom2, int locale1, int locale2) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("messages.title", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("logout.message", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.list.users", locale1), getTimeout());
		// Cambiamos a segundo idioma
		PO_NavView.changeIdiom(driver, textIdiom2);
		elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("messages.title", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("logout.message", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.list.users", locale2), getTimeout());
		// Volvemos a Español.
		PO_NavView.changeIdiom(driver, textIdiom1);
		elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("messages.title", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("logout.message", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("nav.list.users", locale1), getTimeout());
	}
}
