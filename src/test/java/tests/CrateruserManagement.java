package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CraterDashboardPage;
import pages.CraterLoginPage;
import utils.Driver;
import utils.TestDataReader;

public class CrateruserManagement {
	
	@Test
	
	public void validLogin() {
		
		Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
		CraterLoginPage  loginPage = new CraterLoginPage();
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(),5);
		//waituntil(ExpectedConditions.element)
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
