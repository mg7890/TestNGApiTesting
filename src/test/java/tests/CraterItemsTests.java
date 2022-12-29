package tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CraterCommonPage;
import pages.CraterItemsPage;
import pages.CraterLoginPage;
import utils.BrowserUtils;
import utils.DButils;
import utils.Driver;
import utils.TestDataReader;

public class CraterItemsTests {
	
	CraterCommonPage commonpage;
	CraterItemsPage itemsPage;
	BrowserUtils utils;
	CraterLoginPage loginPage;
	DButils dbutils;
	
	String newItemName = "Baseball hat";
	
	@Test
	public void createItem() {
		/*
		 * Create an item on UI.
           Then go to database, and query from the items table using select * to get the information
    	   Then verify the information that you have provided on UI is correct. 
    	   Then update your Item on the UI, come back to database and verify the update is in effect.
           Then delete the Item on the UI,  come back to database and verify the estimate no longer exist. 
		 */
		commonpage = new CraterCommonPage();
		itemsPage  = new CraterItemsPage();
		utils = new BrowserUtils();
		
		Assert.assertTrue(commonpage.commonPageItemsLink.isDisplayed());
		// click on the items tab
		commonpage.commonPageItemsLink.click();
		// verify that user is on the Add Items page
		Assert.assertTrue(itemsPage.addItemButton.isDisplayed());
		// click on the Add Item button
		itemsPage.addItemButton.click();
		// verify that the add item modal displays
		Assert.assertTrue(itemsPage.newItemHeaderText.isDisplayed());
		// provide item information
		newItemName = newItemName + utils.randomNumber();
		itemsPage.addItemNameField.sendKeys(newItemName);
		itemsPage.addItemPriceField.sendKeys("2800");
		itemsPage.addItemUnitField.click();
		// wait until the pc unit is display
		utils.waitUntilElementVisible(itemsPage.pc_unit);
		// click on the pc unit
		itemsPage.pc_unit.click();
		
		itemsPage.addItemDescription.sendKeys("Very pretty baseball hat!");
		itemsPage.saveItemButton.click();
		
		// wait until the item create success message displays
		utils.waitUntilElementVisible(itemsPage.itemCreateSuccessMessage);
		Assert.assertTrue(itemsPage.itemCreateSuccessMessage.isDisplayed());
		
		WebElement newItem = Driver.getDriver().findElement(By.xpath("//a[text()='"+newItemName+"']"));
		Assert.assertTrue(newItem.isDisplayed());
		
		// go to database and select the item that just created. 
		System.out.println("New coffee mug with number: " + newItemName);
		dbutils = new DButils();
		String query = "SELECT * FROM items WHERE name='"+newItemName+"';";
		List<String> itemsData = dbutils.selectArecord(query);
		System.out.println("ITEM ID: " + itemsData.get(0));
		System.out.println("ITEM NAME: " + itemsData.get(1));
		
		Assert.assertEquals(itemsData.get(1), newItemName);
		
		// Then update your Item in the UI
		// come back to the database and verify the update is in effect.
		
		Driver.getDriver().findElement(By.xpath("//a[text()='"+newItemName+"']")).click();
		//Verify that user is on the edit item page
		Assert.assertTrue(itemsPage.editItemHeaderText.isDisplayed());
		//Edit the item description
		String itemNewDescription = "Very good looking baseball hat!";
		//First clear the existing message.
		utils.clearTextOfTheFieldWindows(itemsPage.addItemDescription);
		//Send new description.
		itemsPage.addItemDescription.sendKeys(itemNewDescription);
		//Click on the update item button.
		itemsPage.updateItemButton.click();
		//Wait for the update success message banner
		utils.waitUntilElementVisible(itemsPage.itemUpdatedSuccessMessage);
		//verify the message banner displays
		Assert.assertTrue(itemsPage.itemUpdatedSuccessMessage.isDisplayed());
		//Select query to get the updated item data from the DB
		String updateQuery = "SELECT * FROM items WHERE name='"+newItemName+"';";
		List<String> udpatedData = dbutils.selectArecord(updateQuery);
		System.out.println("ITEM ID: " + udpatedData.get(0));
		System.out.println("ITEM NAME: " + udpatedData.get(1));
		System.out.println("ITEM DESCRIPTION: " + udpatedData.get(2));		
		//Verify that the updated description matches to the expected value.
		Assert.assertEquals(udpatedData.get(2),itemNewDescription);
		
		//Then delete the item in the UI
		//come back to the database and verify the item no longer exists.
		Driver.getDriver().findElement(By.xpath("(//a[text()='"+newItemName+"']//parent::td)//following-sibling::td[4]//button")).click();
		//(//a[text()='"+newItemName+"']//parent::td)//following-sibling::td[4]//button
		
		//Wait for and Click on the delete button
		utils.waitUntilElementVisible(itemsPage.deleteItemButton);
		itemsPage.deleteItemButton.click();
		//Wait for the Ok button to be visible
		utils.waitUntilElementVisible(itemsPage.deleteOkButton);
		itemsPage.deleteOkButton.click();
		//Wait and verify the delete success message is displayed
		utils.waitUntilElementVisible(itemsPage.itemDeletedSuccessMessage);
		Assert.assertTrue(itemsPage.itemDeletedSuccessMessage.isDisplayed());
		
		//Database query and verify the list is empty
		String deletedQuery = "SELECT * FROM items WHERE name='"+newItemName+"';";
		List<String> deletedData = dbutils.selectArecord(deletedQuery);
		Assert.assertTrue(deletedData.isEmpty());
		Assert.assertTrue(deletedData.size()==0);
		
		
	}
	
	@BeforeMethod
	public void setup() throws InterruptedException {
		loginPage = new CraterLoginPage();
		Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
		Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginPage.useremail.sendKeys(TestDataReader.getProperty("craterValidUserEmail"));
    	Thread.sleep(1000);
		loginPage.password.sendKeys(TestDataReader.getProperty("craterValidPassword"));
		loginPage.loginButton.click();
		
	}

	@AfterMethod
	public void wrapup() {
		Driver.quitDriver();
	}
}