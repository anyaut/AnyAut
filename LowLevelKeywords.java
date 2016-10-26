package V1;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

public class LowLevelKeywords {
	
	//Global Variables
	public WebDriver myD;
	
	public LowLevelKeywords(WebDriver myD){
		this.myD = myD; 
	}

	///**********************Reusable function library*****************\\\

	public void openBrowser(String vType) {
		//Purpose: Open a Browser, Timeout
		//I/P : which Browser
		//o/p : N/A
		switch(vType){
		case "Firefox":
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\A774\\AnyAut\\AnyAutV1\\AnyAutDemo_1\\src\\geckodriver.exe");
			myD = new FirefoxDriver();
			myD.manage().window().maximize();
			break;
		case "Chrome":
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\A774\\AnyAut\\AnyAutV1\\AnyAutDemo_1\\src\\chromedriver.exe");
			myD = new ChromeDriver();
			myD.manage().window().maximize();
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver", "C:\\Users\\A774\\AnyAut\\AnyAutV1\\src\\v1_0\\IEDriverServer.exe");
			myD = new InternetExplorerDriver();
			myD.manage().window().maximize();
			break;
		default :
			myD = new FirefoxDriver();
			myD.manage().window().maximize();
		}
		myD.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
	public void navigateBrowser(String vURL){
		//Purpose: Navigates a browser
		//IP: URL
		//OP: N/A
		myD.navigate().to(vURL);
	}
	public void maximizeBrowser(){
		myD.manage().window().maximize();
	}
	public void closeBrowser() {
		//Purpose: Closes a Browser.
		//IP: N/A
		//OP: N/A
		//myD.close();
		myD.quit();
	}
	public void quitBrowser() {
		//Purpose: Closes all webdriver Browsers.
		//IP: N/A
		//OP: N/A
		myD.quit();
	}
	public void selectList(String vXP, String vItem){
		//Purpose: Select an item from a DDLB
		//IP: Xpath of the DDLB, Item to select
		//OP: N/A
		Select myO = new Select(myD.findElement(By.xpath(vXP)));
		myO.selectByVisibleText(vItem);
	}
	public void clickLink(String fLinkText){
		//Purpose: Click on a specified Link
		//IP: Link Text
		//OP: N/A
		myD.findElement(By.linkText(fLinkText)).click();
	}
	public void typeText(String vXP, String vText){
		//Purpose: Type a text into edit field
		//IP: Xpath of the element and the text to enter
		//OP: N/A
		myD.findElement(By.xpath(vXP)).clear();
		myD.findElement(By.xpath(vXP)).sendKeys(vText);
	}
	public boolean verifyTitle(String vTitle){
		//Purpose: Verify the title of the page
		//IP: Title
		//OP: Return True or False
		if(vTitle.equals(myD.getTitle())){
			return true;
		}else {
			return false;
		}
	}
	public String verifyText(String vXP, String vText){
		//Purpose: Verifies if a text is present
		//IP: xpath of the element and the text to verify
		//OP: true or false
		if(vText.equals(myD.findElement(By.xpath(vXP)).getText())){
			return "Pass";
		}else{
			return "Fail";
		}
	}
	public void clickButton(String vXP){
		//Purpose: Clicks a button
		//IP: Xpath of the button
		//OP: N/A
		myD.findElement(By.xpath(vXP)).click();
	}
	public void clickElement(String vXP){
		//Purpose: Clicks an element
		//IP: Xpath of the button
		//OP: N/A
		myD.findElement(By.xpath(vXP)).click();
	}
	public boolean ImageURL(String vURL){
		//Purpose: Verifies if an image is present or not using the Image's xpath
		if(myD.findElement(By.xpath(vURL)).isDisplayed()){
			return true;
		}else{
			return false;
		}
	}
	public boolean verifyLink(String xP){
		//Purpose: Verifies if an image is present or not using the Image's xpath
		if(myD.findElement(By.linkText(xP)).isDisplayed()){
			return true;
		}else
		{
			return false;
		}

	}
	public void enterKeyboard(String xP){
		//Purpose: Clicks enter using keyboard
		//IP: Xpath of the element
		//OP: N/A
		myD.findElement(By.xpath(xP)).sendKeys(Keys.ENTER);
	}

	public String readText(String vXP) {
		//Purpose: Reads a text from an edit field
		//IP: xpath of the element
		//OP: Text of type String
		String vOutput = myD.findElement(By.xpath(vXP)).getAttribute("value");
		return vOutput;
	}


}
