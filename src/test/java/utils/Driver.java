package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.github.bonigarcia.wdm.managers.EdgeDriverManager;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;

public class Driver {
	public static WebDriver driver;

	public static WebDriver getDriver() {
		
		String browser = System.getProperty("browser");
		if(browser == null) {
			browser = TestDataReader.getProperty("browser");
		}
		
		if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {     //|| ((RemoteWebDriver) driver).getSessionId() == null
			switch(browser) {
			case "chrome":
//				System.setProperty("webdriver.chrome.driver",
//						"C:\\Users\\mutlu\\Desktop\\SDET\\Tools\\chromedriver_win32\\chromedriver.exe");
				ChromeDriverManager.chromedriver().setup(); //Using Bonigarcia WebDriverManager
				driver = new ChromeDriver(); 
				break;

			case "chrome-headless":
				ChromeDriverManager.chromedriver().setup(); //Using Bonigarcia WebDriverManager
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--headless");
				driver = new ChromeDriver(chromeOptions); 
				break;				
				
			case "firefox":
//				System.setProperty("webdriver.gecko.driver",
//						"C:\\Users\\mutlu\\Desktop\\SDET\\Tools\\geckodriver-v0.32.0-win64\\geckodriver.exe");
				FirefoxDriverManager.firefoxdriver().setup(); //Using Bonigarcia WebDriverManager
				driver = new FirefoxDriver();	
				break;
				
			case "firefox-headless":
				FirefoxDriverManager.firefoxdriver().setup(); //Using Bonigarcia WebDriverManager
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.setHeadless(true);
				driver = new FirefoxDriver(firefoxOptions);	//Using Bonigarcia WebDriverManager
				break;

			case "safari":
				driver = new SafariDriver();
				break;
				
			case "edge":
				EdgeDriverManager.edgedriver().setup(); //Using Bonigarcia WebDriverManager
				driver = new EdgeDriver();	
				break;

			default:
//				System.setProperty("webdriver.chrome.driver",
//						"C:\\Users\\mutlu\\Desktop\\SDET\\Tools\\chromedriver_win32\\chromedriver.exe");
				ChromeDriverManager.chromedriver().setup();	//Using Bonigarcia WebDriverManager
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				driver = new ChromeDriver(options);
				break;
			}
		}
		return driver;
	}
	
	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
		
	}

}
