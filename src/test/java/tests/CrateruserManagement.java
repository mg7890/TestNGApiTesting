package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.CraterDashboardPage;
import pages.CraterLoginPage;
import utils.BrowserUtils;
import utils.Driver;
import utils.TestDataReader;

public class CraterUserManagement {
	
	@Test	
	public void validLogin() {
		
		Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
		CraterLoginPage  loginPage = new CraterLoginPage();
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(),5);
		loginPage.emailField.sendKeys(TestDataReader.getProperty("craterValidUserEmail"));
		loginPage.passwordField.sendKeys(TestDataReader.getProperty("craterValidPassword")); 
		loginPage.loginButton.click();
		
		//Verify the amount due is displayed
		CraterDashboardPage dashboardPage = new CraterDashboardPage();
		Assert.assertTrue(dashboardPage.amountDueText.isDisplayed());
		
		//Verify the url contains dashboard
		String dashboardUrl = Driver.getDriver().getCurrentUrl();
		Assert.assertTrue(dashboardUrl.contains("dashboard"));	
	}
	
	@Test	(dataProvider = "credentials")
	public void InvalidLogin(String useremail, String password) {
		
		Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
		CraterLoginPage  loginPage = new CraterLoginPage();
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(),5);
		BrowserUtils utils = new BrowserUtils();
		
		if(useremail.isBlank() || password.isBlank()) {
			loginPage.emailField.sendKeys(useremail);
			loginPage.passwordField.sendKeys(password); 
			loginPage.loginButton.click();	
			
			utils.waitUntilElementVisible(loginPage.FieldRequiredMessage);
			Assert.assertTrue(loginPage.FieldRequiredMessage.isDisplayed());		
		} else {
			loginPage.emailField.sendKeys(useremail);
			loginPage.passwordField.sendKeys(password); 
			loginPage.loginButton.click();	
			
			utils.waitUntilElementVisible(loginPage.invalidUserErrorMessage);
			Assert.assertTrue(loginPage.invalidUserErrorMessage.isDisplayed());
			
			
		}
			
		//Verify user has not logged in
		String currentUrl = Driver.getDriver().getCurrentUrl();
		Assert.assertTrue(loginPage.loginButton.isDisplayed());
		Assert.assertEquals(currentUrl, TestDataReader.getProperty("craterUrl"));
	}
	
	
	@DataProvider
	public String [][] credentials() {
		String[][] credentials = new String [5][2];
	
	credentials[0][0] ="mtlgunduz@gmail.com";
	credentials[0][1]= "error";
	
	credentials[1][0] ="invalid@gmail.com";
	credentials[1][1]= "Password1234";
	
	credentials[2][0] ="";
	credentials[2][1]= "Password1234";
	
	credentials[3][0] ="mtlgunduz@gmail.com";
	credentials[3][1]= "";
	
	credentials[4][0] ="mtlgunduz@gmail.com";
	credentials[4][1]= "";
	
	return credentials;
}
	
	@BeforeMethod
	public void setup() {
		Driver.getDriver().manage().window().maximize();
		Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
	}
	
	@AfterMethod
	public void tearDown() {
		Driver.quitDriver();
		
	}
	

}
