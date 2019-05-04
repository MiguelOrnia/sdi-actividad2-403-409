package test.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import test.utils.SeleniumUtils;

public class PO_SearchView extends PO_View {

	public static void goToPage(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free",
				"//a[contains(@href, 'sales/search')]");
		elementos.get(0).click();
	}

	public static void searchForSale(WebDriver driver, String title) {
		SeleniumUtils.EsperaCargaPagina(driver, "class", "form-control", getTimeout());
		WebElement inputSearch = driver.findElement(By.name("searchText"));
		inputSearch.click();
		inputSearch.sendKeys(title);
		List<WebElement> btnSearch = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "search", getTimeout());
		btnSearch.get(0).click();
	}

	public static void buySale(WebDriver driver, String title) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"free",
				String.format(
						"/html/body/div/section[2]/table/tbody/tr/td[5]/a",
						title),
				getTimeout());
		elementos.get(0).click();
	}

	public static String getMessageError(WebDriver driver) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"free", "/html/body/div/section[2]/div", getTimeout());
		return elementos.get(0).getText();
	}
	
	public static void sendMessageToSale(WebDriver driver, String title) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"free",
				String.format(
						"//td[contains(text(), '%s')]/following-sibling::*[3]",
						title),
				getTimeout());
		elementos.get(0).click();
	}

}
