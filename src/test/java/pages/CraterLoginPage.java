package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.Driver;

public class CraterLoginPage {
	
	public CraterLoginPage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}

	@FindBy(xpath="//input[@name='email']")  //xpath="//input[@name='email']"  //xpath="//div[@name='email']/input"
	public WebElement emailField;
	
	@FindBy(xpath="//input[@name='password']")
	public WebElement passwordField;
	
	@FindBy(linkText="Forgot Password?")
	public WebElement forgotPasswordLink;
	
	@FindBy(xpath="//button[text()='Login']")
	public WebElement loginButton;
	
	@FindBy(xpath="//p[contains(text(), 'Copyright @')]")
	public WebElement copyrightText;
	
	@FindBy(xpath="//h1[text()= 'Simple Invoicing for Individuals Small Businesses']")
	public WebElement businessTagline;
	
	@FindBy(xpath="//p[text()= 'Crater helps you track expenses, record payments & generate beautiful invoices & estimates.']")
	public WebElement businessSubtext;
	
	@FindBy(xpath="//p[text()= 'These credentials do not match our records.']")
	public WebElement invalidUserErrorMessage;
	
	@FindBy(xpath="//span[text()= 'Field is required']")
	public WebElement FieldRequiredMessage;

}
