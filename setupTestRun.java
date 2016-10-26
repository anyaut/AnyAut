package V1;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class setupTestRun extends ExcelUtilities{


	String xlPath, xlRes;

	int xRows_Mod, xCols_Mod, xRows_TC, xCols_TC, xRows_TS, xCols_TS, xRows_HLK, xCols_HLK, xRows_EID, xCols_EID, xRows_TD, xCols_TD, xRows_DummyRes, xCols_DummyRes;
	String[][] xlMod, xlTC, xlTS, xlHLK, xlEID, xlTD, xlDummyRes;

	String vStepDetail, vLLK, vHLK_EID, vHLK_TD;											
	String vTSID, vKW, vKWType, vEID, vTD;
	String vTS_Res, vTC_Res;
	String vTCModuleID, vTCID, vTCExecute;
	String vModuleID, vModExecute;
	int runnerTSRowCount = 0;

	@Before
	public void myBefore() throws Exception{
		//xlPath = "ExcelFiles//Implementation.xls";
		//xlRes = "ExcelFiles//SetupTestRun1.xls";

		xlPath = "//users//mac//Documents//AnyAut//Implementation.xls";
		xlRes = "//users//mac//Documents//AnyAut//SetupTestRun1.xls";
		
		// 1	Read the Mod, TC, TS, HLK, EID and TD excel sheet into our program and put into a 2D array of type String

		xlMod = readXL(xlPath, "Modules");
		xlTC = readXL(xlPath, "TestCases");
		xlTS = readXL(xlPath, "TestSteps");
		xlHLK = readXL(xlPath, "HighLevelKeywords");
		xlEID = readXL(xlPath, "EID");
		xlTD = readXL(xlPath, "TestData");

		xRows_Mod = xlMod.length;
		xCols_Mod = xlMod[0].length;

		System.out.println("Module Rows are " + xRows_Mod);
		System.out.println("Module Cols are " + xCols_Mod);

		xRows_TC = xlTC.length;
		xCols_TC = xlTC[0].length;
		System.out.println("TC Rows are " + xRows_TC);
		System.out.println("TC Cols are " + xCols_TC);		


		xRows_TS = xlTS.length;
		xCols_TS = xlTS[0].length;
		System.out.println("TS Rows are " + xRows_TS);
		System.out.println("TS Cols are " + xCols_TS);

		xRows_HLK = xlHLK.length;
		xCols_HLK = xlHLK[0].length;
		System.out.println("HLK Rows are " + xRows_HLK);
		System.out.println("HLK Cols are " + xCols_HLK);

		xRows_EID = xlEID.length;
		xCols_EID = xlEID[0].length;
		System.out.println("EID Rows are " + xRows_EID);
		System.out.println("EID Cols are " + xCols_EID);

		xRows_TD = xlTD.length;
		xCols_TD = xlTD[0].length;
		System.out.println("TD Rows are " + xRows_TD);
		System.out.println("TD Cols are " + xCols_TD);		

	}


	@Test	
	public void mainTest() throws Exception {
		
		xlDummyRes = new String[1][xCols_TS];
		// header details for SetupTestRun excel file
		for (int x =0; x < xCols_TS; x++){
			xlDummyRes[0][x] = xlTS[0][x];
			System.out.println(xlDummyRes[0][x]);
		}
		
		int a = 1;
		//2 Go to each row in Mod array
		for(int k = 1; k < xRows_Mod; k++){
			vModuleID = xlMod[k][1];
			vModExecute = xlMod[k][3];

			// 3 Check if execute is Y or N
			if (vModExecute.equals("Y")){
			
				// 4	Go to each row in TC array		
				for (int i = 1; i <xRows_TC; i++){
					runnerTSRowCount = 0;
					vTCModuleID = xlTC[i][0];
					vTCID = xlTC[i][1];
					vTCExecute = xlTC[i][3];
					// 5	Check if Module ID matches with the module ID of TestCases worksheet and also check if the corresponding execute is Y or N
					if ((vModuleID.equals(vTCModuleID)) && (vTCExecute.equals("Y"))){
					
						// 6	If match occurs and execute flag is yes then go to each row in TS array
						for(int j = 1; j< xRows_TS; j++){
							String vTS_TCID = xlTS[j][1];
							// 7	Check if the TCID matches with the TSID
							if (vTCID.equals(vTS_TCID)){		
								runnerTSRowCount = 0;
								vStepDetail = xlTS[j][2];
								vKW =	xlTS[j][4];
								vKWType = xlTS[j][5];
								vEID =	xlTS[j][6];
								vTD =	xlTS[j][7];									
								System.out.println("KW is : " + vKW);
								if(vKWType.equals("HLK")){
									// Go to each corresponding row in HighLevelKeywords
									for (int l = 1; l < xRows_HLK; l++){
										String vHLK = xlHLK[l][0];
										// Check if the keyword column in test steps worksheet match the high level keyword column in HighLevelKeyWords Worksheet
										if (vKW.equals(vHLK)){
											runnerTSRowCount++;
											vStepDetail =	xlHLK[l][1];
											vKW =	xlHLK[l][2];
											vEID =	xlHLK[l][3];
											vTD =	xlHLK[l][4];	
											updateTestRunnerArray(a, j);
											a++;
										}
									}
								}

								else{
									runnerTSRowCount++;
									// If yes, then Get Element Xpath, Get Test Data, 
									vEID = getEID(vEID);
									vTD = getTD(vTD);	
									updateTestRunnerArray(a, j);
									a++;
								}
								// If yes, then Get Element Xpath, Get Test Data, 
								vEID = getEID(vEID);
								vTD = getTD(vTD);	
							}
						}
					}
					else{
						System.out.println("TC not ready for execution:" + xlTC[i][1]);
					}

				}



			}
			else{
				System.out.println("Mod not ready for execution:" + xlMod[k][1]);
			}

		}		
	}


	@After
	public void myAfterTest() throws Exception{	
		// 8	Publish results back to an Excel
		writeXL(xlRes, "Output", xlDummyRes);

	}

	public void updateTestRunnerArray(int fA, int fJ){
		System.out.println("XL Rows are : " + xlDummyRes.length);
		xlDummyRes = Arrays.copyOf(xlDummyRes, xlDummyRes.length + 2);
		System.out.println("XL Rows are : " + xlDummyRes.length);
		System.out.println(xlDummyRes[0][0]);
		System.out.println(xlDummyRes[1][0]);

		System.out.println("Now updating row number : " + fA);

		xlDummyRes[fA][0] = vModuleID;										
		xlDummyRes[fA][1] = vTCID;
		xlDummyRes[fA][2] = xlTS[fJ][1] + "_Step_" + runnerTSRowCount;										
		xlDummyRes[fA][3] = vStepDetail;
		xlDummyRes[fA][4] = vKW;
		xlDummyRes[fA][5] = vEID;
		xlDummyRes[fA][6] = vTD;
		
		xlDummyRes[fA][7] =	xlTS[fJ][8];
		xlDummyRes[fA][8] =	xlTS[fJ][9];
		xlDummyRes[fA][9] = xlTS[fJ][10];
		xlDummyRes[fA][10] = xlTS[fJ][11];

	}



	public String getEID(String fEID){
		// Go to each row in PageObjects
		for (int m=1; m< xRows_EID ; m++){
			// Check if the EID's match
			if (fEID.equals(xlEID[m][0])){
				// Return the xPath
				return xlEID[m][1];
			}
		}
		// If EID's do not match any one, then return xPath as is
		return fEID;
	}

	public String getTD(String fTD){
		// Go to each row in PageObjects
		for (int n=1; n< xRows_TD ; n++){
			// Check if the EID's match
			if (fTD.equals(xlTD[n][0])){
				// Return the xPath
				return xlTD[n][1];
			}
		}
		// If EID's do not match any one, then return xPath as is
		return fTD;
	}

}
