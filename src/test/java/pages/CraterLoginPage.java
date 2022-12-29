package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.Driver;
import utils.TestDataReader;

public class CraterLoginPage {
	
	public CraterLoginPage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}
	
	@FindBy (xpath = "//div[@name='email']/input")
	public WebElement useremail;
	
	@FindBy (xpath = "//div[@name='password']/input")
	public WebElement password;
	
	@FindBy (linkText = "Forgot Password?")
	public WebElement forgotPasswordLink;
	
	@FindBy (xpath = "//button[text()='Login']")
	public WebElement loginButton;
	
	@FindBy (xpath = "//p[contains(text(), 'Copyright @')]")
	public WebElement copyRightText;
	
	@FindBy (xpath = "//h1[contains(text(), 'Simple Invoicing for')]")
	public WebElement businessTagline;
	
	@FindBy (xpath = "//p[contains(text(), 'Crater helps you track expenses')]")
	public WebElement businessSubtext;
	
	@FindBy (xpath = "//p[contains(text(), 'These credentials do not match our records.')]")
	public WebElement invalidUserErrorMessage;
	
	@FindBy (xpath = "//span[text()='Field is required']")
	public WebElement fieldRequired;

	
//	CraterLoginPage loginPage = new CraterLoginPage();
//	
//	public void login() throws InterruptedException {
//		  loginPage.useremail.sendKeys(TestDataReader.getProperty("craterValidUserEmail"));
//		  Thread.sleep(1000);
//		  loginPage.password.sendKeys(TestDataReader.getProperty("craterValidPassword"));
//		  loginPage.loginButton.click();
//	}
}
