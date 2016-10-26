package V1;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class driverScript extends ExcelUtilities{

	//Global Variables
	WebDriver myD;
	String xlPath, xlRes;
	int xRows_SUTR, xCols_SUTR;
	String[][] xlSUTR;
	String vKW, vEID, vTD;	
	String vTC_Res;
	
	@Before
	public void myBefore() throws Exception{
		
		xlPath = "C:\\Users\\A774\\AnyAut\\AnyAutV1\\src\\ExcelFiles\\RunSetup_6.xls";
	    xlRes = "C:\\Users\\A774\\AnyAut\\AnyAutV1\\src\\ExcelFiles\\RunResult_6.xls";
 

		
		// 1	Read the output excel sheet into and put into a 2D array of type String
		
	    xlSUTR = readXL(xlPath, "Output");		
		
	    xRows_SUTR = xlSUTR.length;
	    xCols_SUTR = xlSUTR[0].length;
		System.out.println("Rows are " + xRows_SUTR);
		System.out.println("Cols are " + xCols_SUTR);	
		
	}	
	
	@Test
	public void mainTest() throws Exception {
		// TODO Auto-generated method stub
		LowLevelKeywords lowLevelKeywords = new LowLevelKeywords(myD); 
		int row = 1;
				
		// 2	Go to each row in TC array
		for (int i = 1; i <xRows_SUTR; i++){
				vTC_Res = "Pass";
				String vKW, vEID, vTD;
				vKW =	xlSUTR[i][4];
				vEID =	xlSUTR[i][5];
				vTD =	xlSUTR[i][6];					
						
				System.out.println("KW: " + vKW);
				System.out.println("Element ID: " + vEID);
				System.out.println("Test Data: " + vTD);
				try{
					long vStartTime = System.currentTimeMillis();
					
					executeKW(lowLevelKeywords, vKW, vEID, vTD);
					
					long vStopTime = System.currentTimeMillis();
				    long vElapsedTime = vStopTime - vStartTime;
				    vElapsedTime = vElapsedTime/1000;
				    String vExecutionTime = Long.toString(vElapsedTime);
				    
				    
				    // The below code is to get time as an example: 0 mins, 23 secs
				    /*String vExecutionTime = String.format("%d min, %d sec", 
				    	    TimeUnit.MILLISECONDS.toMinutes(vElapsedTime),
				    	    TimeUnit.MILLISECONDS.toSeconds(vElapsedTime) - 
				    	    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(vElapsedTime))
				    	);*/
				    xlSUTR[i][9] = vExecutionTime;

					if(!vTC_Res.equals("Pass")){
						vTC_Res = "Verification Failed";
						takeScreenshot("C:\\Users\\A774\\AnyAut\\AnyAutV1\\Screenshots\\" + xlSUTR[i][1] +"_row_" + row + ".jpg");
						xlSUTR[i][11] = "Look at Screenshot: "+ xlSUTR[i][1]+"_row" + row + ".jpg";
						
					}
						}
						catch(Exception ex){
							System.out.println("Error : " + ex);
							vTC_Res = "Fail";
							xlSUTR[i][10] = "Error : " + ex;
							takeScreenshot("C:\\" + xlSUTR[i][1] +"_row_" + row + ".jpg");
							xlSUTR[i][11] = "Look at Screenshot: "+ xlSUTR[i][1]+"_row" + row + ".jpg";
														
						}
				xlSUTR[i][8] = vTC_Res;	
				
											
				
			}		
			
		}


	
	@After
	public void myAfterTest() throws Exception{		
		// 8	Publish results back to an Excel
		writeXL(xlRes, "Output", xlSUTR);		
	}
	
	public void takeScreenshot(String fPath) throws Exception{
		File scrFile = ((TakesScreenshot)myD).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		FileUtils.copyFile(scrFile, new File(fPath));
	}
	
	///**********************Keywords*****************\\\
	
	public void executeKW(LowLevelKeywords lowLevelKeywords, String fKW, String fEID, String fTD){
		
		switch(fKW){
		case "openBrowser":
			lowLevelKeywords.openBrowser(fTD);
			break;
		case "closeBrowser":
			lowLevelKeywords.closeBrowser();
			break;
		case "navigateBrowser":
			lowLevelKeywords.navigateBrowser(fTD);
			break;
		case "typeEdit":
			lowLevelKeywords.typeText(fEID, fTD);
			break;
		case "selectList":
			lowLevelKeywords.selectList(fEID, fTD);
			break;
		case "clickButton":
			lowLevelKeywords.clickElement(fEID);
			break;
		case "verifyText":
			vTC_Res = lowLevelKeywords.verifyText(fEID, fTD);
			break;
		case "enterKeyboard":
			lowLevelKeywords.enterKeyboard(fEID);
			break;
		case "sendKeys":
			lowLevelKeywords.selectList(fEID, fTD);
			break;
		case "checkCheckbox":
			lowLevelKeywords.clickElement(fEID);
			break;
		case "uncheckCheckbox":
			lowLevelKeywords.clickElement(fEID);
			break;
		case "clickImage":
			lowLevelKeywords.ImageURL(fEID);
			break;			
		case "readEdit":
			vTC_Res = lowLevelKeywords.readText(fEID);
			break;
		case "clickLink":
			lowLevelKeywords.clickLink(fTD);
			break;
		default : 
			System.out.println("Keyword is missing : " + fKW);

		}
	}

}

