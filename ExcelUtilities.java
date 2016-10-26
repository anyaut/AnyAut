package V1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtilities {
	
	// Method to read XL
			public static String[][] readXL(String fPath, String fSheet) throws Exception{
				// Inputs : XL Path and XL Sheet name
				// Output : 
				String[][] xData;  
				int xRows, xCols;

				File myxl = new File(fPath);                                
				FileInputStream myStream = new FileInputStream(myxl);                                
				HSSFWorkbook myWB = new HSSFWorkbook(myStream);                                
				HSSFSheet mySheet = myWB.getSheet(fSheet);                                 
				xRows = mySheet.getLastRowNum()+1;                                
				xCols = mySheet.getRow(0).getLastCellNum();   
				//System.out.println("Total Rows in Excel are " + xRows);
				//System.out.println("Total Cols in Excel are " + xCols);
				xData = new String[xRows][xCols];        
				for (int i = 0; i < xRows; i++) {                           
					HSSFRow row = mySheet.getRow(i);
					for (int j = 0; j < xCols; j++) {                               
						HSSFCell cell = row.getCell(j);
						String value = "-";
						if (cell!=null){
							value = cellToString(cell);
						}
						xData[i][j] = value;      
						System.out.print(value);
						System.out.print("----");
					}        
					System.out.println("");
				}    
				myxl = null; // Memory gets released
				return xData;
			}

			//Change cell type
			public static String cellToString(HSSFCell cell) { 
				// This function will convert an object of type excel cell to a string value
				int type = cell.getCellType();                        
				Object result;                        
				switch (type) {                            
				case HSSFCell.CELL_TYPE_NUMERIC: //0                                
					result = cell.getNumericCellValue();                                
					break;                            
				case HSSFCell.CELL_TYPE_STRING: //1                                
					result = cell.getStringCellValue();                                
					break;                            
				case HSSFCell.CELL_TYPE_FORMULA: //2                                
					throw new RuntimeException("We can't evaluate formulas in Java");  
				case HSSFCell.CELL_TYPE_BLANK: //3                                
					result = "%";                                
					break;                            
				case HSSFCell.CELL_TYPE_BOOLEAN: //4     
					result = cell.getBooleanCellValue();       
					break;                            
				case HSSFCell.CELL_TYPE_ERROR: //5       
					throw new RuntimeException ("This cell has an error");    
				default:                  
					throw new RuntimeException("We don't support this cell type: " + type); 
				}                        
				return result.toString();      
			}

			// Method to write into an XL
			public static void writeXL(String fPath, String fSheet, String[][] xData) throws Exception{

				File outFile = new File(fPath);
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet osheet = wb.createSheet(fSheet);
				int xR_TS = xData.length;
				int xC_TS = xData[0].length;
				for (int myrow = 0; myrow < xR_TS; myrow++) {
					HSSFRow row = osheet.createRow(myrow);
					for (int mycol = 0; mycol < xC_TS; mycol++) {
						HSSFCell cell = row.createCell(mycol);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(xData[myrow][mycol]);
					}
					FileOutputStream fOut = new FileOutputStream(outFile);
					wb.write(fOut);
					fOut.flush();
					fOut.close();
				}
			}



}
