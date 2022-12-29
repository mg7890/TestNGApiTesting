package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.Driver;

public class CraterItemsPage {
	
	public CraterItemsPage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}
	
	@FindBy (xpath = "//h3[text()='Items']")
	public WebElement itemsHeaderText;
	
	@FindBy (xpath = "//button[text()=' Add Item']")
	public WebElement addItemButton;
	
	@FindBy (xpath = "//button[text()='Filter ']")
	public WebElement filterItemButton;
	
	@FindBy (xpath = "//div[@name='name']/input")
	public WebElement filterNameField;

	
	// add item window
	@FindBy (xpath = "//h3[text()='New Item']")
	public WebElement newItemHeaderText;
	
	// edit item window
	@FindBy (xpath = "//h3[text()='Edit Item']")
	public WebElement editItemHeaderText;
	
	@FindBy (xpath = "//div[@class='relative rounded-md shadow-sm font-base']/input")
	public WebElement addItemNameField;
	
	@FindBy (xpath = "//div[@class='flex flex-col mt-1']/input")
	public WebElement addItemPriceField;
	
	@FindBy (xpath = "(//div[@class='flex flex-col mt-1']/div/input)[2]")
	public WebElement addItemUnitField;
	
	@FindBy (xpath = "//textarea[@name='description']")
	public WebElement addItemDescription;
	
	@FindBy (xpath = "//button[text()=' Save Item']")
	public WebElement saveItemButton;
	
	@FindBy (xpath = "//button[text()=' Update Item']")
	public WebElement updateItemButton;
	
	@FindBy (xpath = "//a[text()=' Delete']")
	public WebElement deleteItemButton;
	
	@FindBy (xpath = "//button[text()='Ok']")
	public WebElement deleteOkButton;
	
	@FindBy (xpath = "//span[text()='pc']")
	public WebElement pc_unit;
	
	@FindBy (xpath = "//p[text()='Item created successfully']")
	public WebElement itemCreateSuccessMessage;
	
	@FindBy (xpath = "//p[text()='Item updated successfully']")
	public WebElement itemUpdatedSuccessMessage;
	
	@FindBy (xpath = "//p[text()='Item deleted successfully']")
	public WebElement itemDeletedSuccessMessage;
}
