package test.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import test.utils.SeleniumUtils;

public class PO_AddSale extends PO_View {

	public static void goToPage(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free",
				"//a[contains(@href, 'sales/add')]");
		elementos.get(0).click();
	}

	public static void addSale(WebDriver driver, String title, String text,
			Double price) {
		WebElement titleForm = driver.findElement(By.name("title"));
		titleForm.click();
		titleForm.clear();
		titleForm.sendKeys(title);
		WebElement textForm = driver.findElement(By.name("details"));
		textForm.click();
		textForm.clear();
		textForm.sendKeys(text);
		WebElement priceForm = driver.findElement(By.name("price"));
		priceForm.click();
		priceForm.clear();
		priceForm.sendKeys(String.format("%.2f", price));
		// Pulsar el boton de crear.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1,
			String textIdiom2, int locale1, int locale2) {
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.add.add", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.title", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.details", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.price", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.add.submit", locale1), getTimeout());
		// Cambiamos a segundo idioma
		PO_NavView.changeIdiom(driver, textIdiom2);
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.title", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.details", locale2), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.price", locale2), getTimeout());
		// Volvemos a Español.
		PO_NavView.changeIdiom(driver, textIdiom1);
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.add.add", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.title", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.details", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.price", locale1), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				p.getString("sales.add.submit", locale1), getTimeout());
	}
}
