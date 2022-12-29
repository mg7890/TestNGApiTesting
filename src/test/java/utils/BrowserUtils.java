package utils;

import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserUtils {
	
	Alert alert;
	WebDriverWait letswait;
	Select letsSelect;
	Actions action;

	// this function waits 5 seconds for an alert to be present on the UI
	public void waitUntilAlertIsPresent() {
		letswait = new WebDriverWait(Driver.getDriver(), 5);
		letswait.until(ExpectedConditions.alertIsPresent());
	}

	// This function gets the text from the Alert and returns as a string
	public String alertGetText() {
		alert = Driver.getDriver().switchTo().alert();
		return alert.getText();
	}

	// This function accepts / confirms the Alert
	public void alertAccept() {
		alert = Driver.getDriver().switchTo().alert();
		alert.accept();
	}

	// This function dismisses the Alert
	public void alertDismiss() {
		alert = Driver.getDriver().switchTo().alert();
		alert.dismiss();
	}

	// This function accepts a string and sends it to the Alert
	public void sendKeysOnAlert(String text) {
		alert = Driver.getDriver().switchTo().alert();
		alert.sendKeys(text);
	}

	// This function accepts a web element and waits until it is visible for 5 seconds
	public void waitUntilElementVisible(WebElement element) {
		letswait = new WebDriverWait(Driver.getDriver(), 5);
		letswait.until(ExpectedConditions.visibilityOf(element));
	}
	
	// This function accepts a select webElement and a option to be selected 
	// and selects the option from the given dropdown
	public void selectByVisibleText(WebElement selectElement, String optionToSelect) {
		letsSelect = new Select(selectElement);
		letsSelect.selectByVisibleText(optionToSelect);
	}

	// This function accepts a web element and checks if the element exist in the DOM,
	// and return boolean. 
	public boolean isElementPresent(WebElement element) {
		try {
			element.getText();
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	// This function accepts a web element and moves the mouse cursor to that element. 
	// hover over to an element
	public void moveToElement(WebElement element) {
		action = new Actions(Driver.getDriver());
		action.moveToElement(element).perform();
	}
	
	public void sendKeysWithActionsClass(WebElement element) {
		action = new Actions(Driver.getDriver());
		
	}
	
	// drag the source element to the target element
	public void dragAndDrop(WebElement sourceElement, WebElement targetElement) {
		action = new Actions(Driver.getDriver());
		action.dragAndDrop(sourceElement, targetElement).perform();
	}

	// This method is for deleting the pre-populated value of an input field with
	// back space key events
	public void clearValueOnTheField(WebElement element) {
		String temp = element.getAttribute("value");
		for (int i = 0; i < temp.length(); i++) {
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	// This method is for deleting the pre-populated value of an input field with
	// control + a keys event for windows
	public void clearTextOfTheFieldWindows(WebElement element) {
		element.sendKeys(Keys.chord(Keys.CONTROL), "a");
		element.sendKeys(Keys.DELETE);
	}

	// This method is for deleting the pre-populated value of an input field with
	// command + a keys event for mac, linux
	public void clearTextOfAFieldMac(WebElement element) {
		element.sendKeys(Keys.chord(Keys.COMMAND), "a");
		element.sendKeys(Keys.DELETE);
	}
	
	
	//Random number generator
	public int randomNumber() {
		Random rand = new Random();
		int randNum = rand.nextInt((999-100)+1)+100;
		
		return randNum;

	}

}
