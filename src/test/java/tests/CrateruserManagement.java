package tests;

import org.testng.annotations.Test;
import pages.CraterDashboardPage;
import pages.CraterLoginPage;
import utils.BrowserUtils;
import utils.Driver;
import utils.TestDataReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class CraterUserManagement {
	
	
  @Test
  public void validLogin() throws InterruptedException {
	  /*
	   * Scenario: Successful log in
         Given user is on the login page
         When user enters valid username and password
         And click login button
         Then user should be on the dashboard page
	   */
	  
	  Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
	  CraterLoginPage loginpage = new CraterLoginPage();
	  Thread.sleep(1000);
	  loginpage.useremail.sendKeys(TestDataReader.getProperty("craterValidUserEmail"));
	  Thread.sleep(1000);
	  loginpage.password.sendKeys(TestDataReader.getProperty("craterValidPassword"));
	  loginpage.loginButton.click();
	  
	  // verify the amount due element display
	  CraterDashboardPage dashboardPage = new CraterDashboardPage();
	  Assert.assertTrue(dashboardPage.amountDueText.isDisplayed()); 
	  
	  // verify the url contains dashboard
	  String dashboardUrl = Driver.getDriver().getCurrentUrl();
	  Assert.assertTrue(dashboardUrl.contains("dashboard"));
  }
  
  
  @Test (dataProvider = "credential")
  public void invalidLogin(String useremail, String password) throws InterruptedException {
	  /*
	   * Scenario: Invalid login attempts
         Given user is on the login page
         When user enters invalid username and password
         And click login button
         Then an error message appears
         And user is not logged in
	   */
	  
	  Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
	  CraterLoginPage loginpage = new CraterLoginPage();
	  Thread.sleep(1000);
	  BrowserUtils utils = new BrowserUtils();
	  
	  if (useremail.isBlank() || password.isBlank()) {
		  loginpage.useremail.sendKeys(useremail);
		  loginpage.password.sendKeys(password);
		  loginpage.loginButton.click();
		  utils.waitUntilElementVisible(loginpage.fieldRequired);
		  Assert.assertTrue(loginpage.fieldRequired.isDisplayed());
	  } else {
		  loginpage.useremail.sendKeys(useremail);
		  loginpage.password.sendKeys(password);
		  loginpage.loginButton.click();
		  utils.waitUntilElementVisible(loginpage.invalidUserErrorMessage);
		  Assert.assertTrue(loginpage.invalidUserErrorMessage.isDisplayed());
		  Assert.assertTrue(loginpage.loginButton.isDisplayed());
	  }
  }
  
  @DataProvider
	public String[][] credential(){
		String[][] names = new String[4][2];
		names[0][0] = "mtlgunduz@gmail.com";
		names[0][1] = "standardhhfvajk";
		
		names[1][0] = "nothing@primetechschool.com";
		names[1][1] = "Testing123";
		
		names[2][0] = "";
		names[2][1] = "Testing123";
		
		names[3][0] = "nothing@primetechschool.com";
		names[3][1] = "";
		return names;
		
		// row 0 | 0 | 1 |
		// row 1 | 0 | 1 |		
	}
  
  @BeforeMethod
  public void setup() {
	  Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @AfterMethod
  public void tearDown() {
	  Driver.quitDriver();
  }

}
