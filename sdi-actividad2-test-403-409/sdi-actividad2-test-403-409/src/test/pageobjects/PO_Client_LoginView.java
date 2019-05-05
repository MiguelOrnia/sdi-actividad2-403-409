package test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_Client_LoginView extends PO_NavView {

	public static void fillForm(WebDriver driver, String usernamep,
			String passwordp) {
		WebElement username = driver.findElement(By.name("email"));
		username.click();
		username.clear();
		username.sendKeys(usernamep);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		// Pulsar el boton de Alta.
		By boton = By.id("boton-login");
		driver.findElement(boton).click();
	}

}
