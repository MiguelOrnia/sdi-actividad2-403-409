package test.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_Messages extends PO_View {

	public static void goToPage(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "class",
				"messageButton");
		elementos.get(0).click();
	}

	public static void sendMessage(WebDriver driver, String message) {
		WebElement username = driver.findElement(By.id("inputMessage"));
		username.click();
		username.clear();
		username.sendKeys(message);

		driver.findElement(By.id("submitMessage")).click();
	}

	public static void goToChat(WebDriver driver, String id) {
		List<WebElement> elementos = PO_View.checkElement(driver, "class", "chatLink");
		elementos.get(0).click();
	}

	public static void deleteFirstMessage(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "class", "deleteConversation");
		elementos.get(0).click();
	}

	public static void deleteLastMessage(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "class", "deleteConversation");
		elementos.get(elementos.size()-1).click();
	}

}
