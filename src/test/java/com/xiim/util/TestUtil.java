package com.xiim.util;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.xiim.base.TestBase;



public class TestUtil extends TestBase{
	
	// finds if the test suite is runnable 
		public static boolean isSuiteRunnable(Xls_Reader xls , String suiteName){
			boolean isExecutable=false;
			for(int i=2; i <= xls.getRowCount("Test Suite") ;i++ ){
			
				if(xls.getCellData("Test Suite", "TSID", i).equalsIgnoreCase(suiteName)){
					if(xls.getCellData("Test Suite", "Runmode", i).equalsIgnoreCase("Y")){
						isExecutable=true;
					}else{
						isExecutable=false;
					}
				}
			}
			xls=null; // release memory
			return isExecutable;
		}
		
				// returns true if runmode of the test is equal to Y
		public static boolean isTestCaseRunnable(Xls_Reader xls, String testCaseName){
			boolean isExecutable=false;
			for(int i=2; i<= xls.getRowCount("Test Cases") ; i++){
				if(xls.getCellData("Test Cases", "TCID", i).equalsIgnoreCase(testCaseName)){
					if(xls.getCellData("Test Cases", "Runmode", i).equalsIgnoreCase("Y")){
						isExecutable= true;
					}else{
						isExecutable= false;
					}
				}
			}
			return isExecutable;
		}
		
		// return the test data from a test in a 2 dim array
		public static Object[][] getData(Xls_Reader xls , String testCaseName){
			// if the sheet is not present
			if(! xls.isSheetExist(testCaseName)){
				xls=null;
				return new Object[1][0];
			}
			
			int rows=xls.getRowCount(testCaseName);
			int cols=xls.getColumnCount(testCaseName);
			System.out.println("-----ROW and COLUMN COUNT is-----\n");
			System.out.println("Rows are -- "+ rows);
			System.out.println("Cols are -- "+ cols);
			
		    Object[][] data =new Object[rows-1][cols-3];
			for(int rowNum=2;rowNum<=rows;rowNum++){
				for(int colNum=0;colNum<cols-3;colNum++){
					data[rowNum-2][colNum] = xls.getCellData(testCaseName, colNum, rowNum);
				}
			}
			return data;
		}
		
		// checks RUnmode for dataSet
		public static String[] getDataSetRunmodes(Xls_Reader xlsFile,String sheetName){
			String[] runmodes=null;
			if(!xlsFile.isSheetExist(sheetName)){
				xlsFile=null;
				sheetName=null;
				runmodes = new String[1];
				runmodes[0]="Y";
				xlsFile=null;
				sheetName=null;
				return runmodes;
			}
			runmodes = new String[xlsFile.getRowCount(sheetName)-1];
			for(int i=2;i<=runmodes.length+1;i++){
				runmodes[i-2]=xlsFile.getCellData(sheetName, "Runmode", i);
			}
			xlsFile=null;
			sheetName=null;
			return runmodes;
		}

		// update results for a particular data set	
		public static void reportDataSetResult(Xls_Reader xls, String testCaseName, int rowNum,String result){	
			xls.setCellData(testCaseName, "Results", rowNum, result);
		}
		
		// update errors for a particular data set	
		public static void reportDataSetError(Xls_Reader xls, String testCaseName, int rowNum,String error){	
			xls.setCellData(testCaseName, "Error", rowNum, error);
		}
		
		// return the row num for a test
		public static int getRowNum(Xls_Reader xls, String id){
			for(int i=2; i<= xls.getRowCount("Test Cases") ; i++){
				String tcid=xls.getCellData("Test Cases", "TCID", i);
				
				if(tcid.equals(id)){
					xls=null;
					return i;
				}
			}
			return -1;
		}
		
		public static void selectFromDropdown(String xpathkey, String value) throws Exception {
			
			try{
				WebElement table = driver.findElement(By.xpath(OR.getProperty(xpathkey)));
				List<WebElement> tds = table.findElements(By.tagName("option"));
				for (WebElement option : tds) {
					if (option.getText().equals(value)) {
						Thread.sleep(2000L);
						option.click();
						System.out.println(value+"\t value is selected");
						break;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void fluentWait(String xpathy) {
			new FluentWait<WebDriver>(driver)
		       .withTimeout(60, TimeUnit.SECONDS)
		       .pollingEvery(5, TimeUnit.SECONDS)
		       .ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathy)));
		}
		
		public static String generateRandomNumber(String medicaID){
			return medicaID + String.valueOf(1000 + new Random().nextInt(9999));
		}
		
		public static String getDisabledDropdownValue(String tagNameAttr, String tagID){
			
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			String toenable = "document.getElementsByName('"+tagNameAttr+"')[0].removeAttribute('disabled');";
			javascript.executeScript(toenable);

			Select value = new Select(driver.findElement(By.id(tagID)));
			System.out.println("First selected Election Type is: "+value.getFirstSelectedOption().getText().trim());
			return value.getFirstSelectedOption().getText().trim();
		}

		public static void selectCheckbox(String Xpath, String isActive) {
			fluentWait(OR.getProperty(Xpath));
			if(isActive.equalsIgnoreCase(isActive)){
				getObject(Xpath).click();
				System.out.println("\""+Xpath+"\"\t check box is selected");
			}else{
				System.out.println("\""+Xpath+"\"\t check box is NOT selected");
			}
		}
		
		public static String generate10digitRandomNumber(){
			
			Random random = new Random();
	        char[] digits = "0123456789".toCharArray();
	        int index = digits.length;
            while (index > 1) {
                int pos = random.nextInt(index--);
                char tmp = digits[pos];
                digits[pos] = digits[index];
                digits[index] = tmp;
            }
            return new String(digits);
		}
		
		public static String changeDateFormat(String asDate) throws Exception{
			
			String[] temp = asDate.split(" ")[0].split("-");
			System.out.println(temp[2]+"-"+temp[1]+"-"+temp[0]);
			return temp[2]+"-"+temp[1]+"-"+temp[0];
		}
		
		//To click sublink from menu list
		public static void clickSubLinks(String Xpath1, String Xpath2, String subMenu) {
			
			try{
				WebElement td2 = driver.findElement(By.xpath(Xpath1));
				List<WebElement> allOpts2 = td2.findElements(By.tagName("div"));
				System.out.println("Total DIVs -> " + allOpts2.size());

				for (int j = 1; j <= allOpts2.size(); j = j + 1) {
					WebElement val1 = driver.findElement(By.xpath(Xpath1 + "/div[" + j + Xpath2));
					List<WebElement> allOpts3 = val1.findElements(By.tagName("li"));
					System.out.println("Total LIs -> " + allOpts3.size());

					for (int k = 1; k <= allOpts3.size(); k = k + 1) {
						//System.out.println(Xpath1 + "/div[" + j + Xpath2+ "/li["+ k + "]/a");
						String name = driver.findElement(By.xpath(Xpath1 + "/div[" + j + Xpath2+ "/li["+ k + "]/a")).getText().trim();
						System.out.println(name);
						if (name.equalsIgnoreCase(subMenu)) {
							fluentWait(Xpath1 + "/div[" + j + Xpath2+ "/li["+ k + "]/a");
							driver.findElement(By.xpath(Xpath1 + "/div[" + j + Xpath2+ "/li["+ k + "]/a")).click();
							break;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//To click sublink from menu list
		public static void clickSubLinksFrmProvider(String Xpath1, String Xpath2, String subMenu) {
			
			try{
				WebElement td2 = driver.findElement(By.xpath(Xpath1));
				List<WebElement> allOpts2 = td2.findElements(By.tagName("li"));
				System.out.println("Total LIs -> " + allOpts2.size());

				for (int j = 1; j <= allOpts2.size(); j = j + 1) {
					//System.out.println(Xpath1 + "/li[" + j + Xpath2);
					String name = driver.findElement(By.xpath(Xpath1 + "/li[" + j + Xpath2)).getText().trim();
					System.out.println(name);
					if (name.equalsIgnoreCase(subMenu)) {
						fluentWait(Xpath1 + "/li[" + j + Xpath2);
						driver.findElement(By.xpath(Xpath1 + "/li[" + j + Xpath2)).click();
						break;
					}else{
						System.out.println(subMenu+"\tis NOT found");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void clickProviderSubLinksFrmEditScreen(String Xpath1, String Xpath2, String linkName, String subLinkName) {
			
			try{
				WebElement td2 = driver.findElement(By.xpath(Xpath1));
				List<WebElement> allOpts2 = td2.findElements(By.tagName("li"));
				System.out.println("Total LIs -> " + allOpts2.size());

				for (int j = 1; j <= allOpts2.size(); j = j + 1) {
					String name = driver.findElement(By.xpath(Xpath1 + "/li[" + j + Xpath2)).getText().trim();
					System.out.println(name);
					if (name.equalsIgnoreCase(linkName)) {
						fluentWait(Xpath1 + "/li[" + j + Xpath2);
						Thread.sleep(2000L);
						driver.findElement(By.xpath(Xpath1 + "/li[" + j + Xpath2)).click();
						System.out.println("Actual \t:"+Xpath1 + "/li[" + j + "]/ul");
						Thread.sleep(2000L);
						WebElement td3 = driver.findElement(By.xpath(Xpath1 + "/li[" + j + "]/ul"));
						List<WebElement> allOpts3 = td3.findElements(By.tagName("li"));
						System.out.println("Total LIs -> " + allOpts3.size());
						for(int k=1; k<=allOpts3.size(); k++){
							String subName = driver.findElement(By.xpath(Xpath1 + "/li[" + j + "]/ul" + "/li[" + k + "]/a")).getText().trim();
							System.out.println(subName);
							if(subName.equalsIgnoreCase(subLinkName)){
								Thread.sleep(2000L);
								fluentWait(Xpath1 + "/li[" + j + "]/ul" + "/li[" + k + "]/a");
								driver.findElement(By.xpath(Xpath1 + "/li[" + j + "]/ul" + "/li[" + k + "]/a")).click();
								Thread.sleep(3000L);
								break;
							}else{
								System.out.println(subLinkName+"\tis NOT found");
							}
						}
					
						break;
					}else{
						System.out.println(linkName+"\tis NOT found");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void clickProviderLinksFrmEditScreen(String Xpath1, String Xpath2, String linkName) {
			
			try{
				WebElement td2 = driver.findElement(By.xpath(Xpath1));
				List<WebElement> allOpts2 = td2.findElements(By.tagName("li"));
				System.out.println("Total LIs -> " + allOpts2.size());

				for (int j = 1; j <= allOpts2.size(); j = j + 1) {
					String name = driver.findElement(By.xpath(Xpath1 + "/li[" + j + Xpath2)).getText().trim();
					System.out.println(name);
					if (name.equalsIgnoreCase(linkName)) {
						fluentWait(Xpath1 + "/li[" + j + Xpath2);
						Thread.sleep(2000L);
						driver.findElement(By.xpath(Xpath1 + "/li[" + j + Xpath2)).click();
						break;
					}else{
						System.out.println(linkName+"\tis NOT found");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void createProviderGroup(String ParentIPA, String PODIPA, String Pgroup, String PsubGrup, String GroupID, 
				String GrupMedicaID, String GrupMedicare, String EffectiveDT, String TerminateDT){
			try{
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvGroup.ParentIPA.DD"));
				selectFromDropdown("ProvGroup.ParentIPA.DD", ParentIPA);
				fluentWait(OR.getProperty("ProvGroup.PODIPA.DD"));
				selectFromDropdown("ProvGroup.PODIPA.DD", PODIPA);
				fluentWait(OR.getProperty("ProvGroup.PG.DD"));
				selectFromDropdown("ProvGroup.PG.DD", Pgroup);
				fluentWait(OR.getProperty("ProvGroup.PSG.DD"));
				selectFromDropdown("ProvGroup.PSG.DD", PsubGrup);
				getObject("ProvGroup.GroupID.tf").clear();
				getObject("ProvGroup.GroupID.tf").sendKeys(GroupID);
				getObject("ProvGroup.GMID.tf").clear();
				getObject("ProvGroup.GMID.tf").sendKeys(GrupMedicaID);
				getObject("ProvGroup.GMCare.tf").clear();
				getObject("ProvGroup.GMCare.tf").sendKeys(GrupMedicare);
				getObject("ProvGroup.EffDate.tf").clear();
				getObject("ProvGroup.EffDate.tf").sendKeys(EffectiveDT);
				//getObject("ProvGroup.TermDate.tf").sendKeys(TerminateDT);
				fluentWait(OR.getProperty("ProvGroup.Save.btn"));
				getObject("ProvGroup.Save.btn").click();
				System.out.println("Provider Group is Created Successfully");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void editProviderGroup(String PODIPA, String Pgroup, String PsubGrup, String GroupID, 
				String GrupMedicaID, String GrupMedicare){
			try{
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvGroup.PODIPA.DD"));
				selectFromDropdown("ProvGroup.PODIPA.DD", PODIPA);
				fluentWait(OR.getProperty("ProvGroup.PG.DD"));
				selectFromDropdown("ProvGroup.PG.DD", Pgroup);
				fluentWait(OR.getProperty("ProvGroup.PSG.DD"));
				selectFromDropdown("ProvGroup.PSG.DD", PsubGrup);
				getObject("ProvGroup.GMID.tf").clear();
				getObject("ProvGroup.GMID.tf").sendKeys(GrupMedicaID);
				getObject("ProvGroup.GMCare.tf").clear();
				getObject("ProvGroup.GMCare.tf").sendKeys(GrupMedicare);
				fluentWait(OR.getProperty("ProvGroup.Save.btn"));
				getObject("ProvGroup.Save.btn").click();
				System.out.println("Provider Group is Updated Successfully");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
				
		public static void enterDataInTF(String Xpath, String Value){
			try{
				fluentWait(OR.getProperty(Xpath));
				getObject(Xpath).clear();
				getObject(Xpath).sendKeys(Value);
				System.out.println("\""+Value+"\"\t is entered");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void selectDegreeOptions(String Xpath1, String Xpath2, String opt) {
			
			try{
				WebElement val1 = driver.findElement(By.xpath(Xpath1));
				List<WebElement> allOpts3 = val1.findElements(By.tagName("li"));
				System.out.println("Total LIs -> " + allOpts3.size());

				for (int k = 1; k <= allOpts3.size(); k = k + 1) {
					//System.out.println(Xpath1 + "/div[" + j + Xpath2+ "/li["+ k + "]/a");
					String name = driver.findElement(By.xpath(Xpath1 + "/li[" + k + Xpath2)).getText().trim();
					System.out.println(name);
					if (name.equalsIgnoreCase(opt)) {
						fluentWait(Xpath1 + "/li[" + k + Xpath2);
						driver.findElement(By.xpath(Xpath1 + "/li[" + k + Xpath2)).click();
						break;
					}
				}
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void addAdverseAction(String Sanction, String AAStartDT, String AAEndDT, String ClaimAction, String Reason){
			try{
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("Provider.AdverseClaim.DD"));
				selectFromDropdown("Provider.AdverseClaim.DD", ClaimAction);
				fluentWait(OR.getProperty("Provider.Adverse.StartDt"));
				enterDataInTF("Provider.Adverse.StartDt", AAStartDT);
				fluentWait(OR.getProperty("Provider.Adverse.EndDt"));
				enterDataInTF("Provider.Adverse.EndDt", AAEndDT);
				fluentWait(OR.getProperty("Provider.Sanction.DD"));
				selectFromDropdown("Provider.Sanction.DD", Sanction);
				Thread.sleep(1000L);
				fluentWait(OR.getProperty("Provider.AdverseReason.DD"));
				selectFromDropdown("Provider.AdverseReason.DD", Reason);
				Thread.sleep(1000L);
				fluentWait(OR.getProperty("Provider.AdverseAddToList.Btn"));
				getObject("Provider.AdverseAddToList.Btn").click();
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void filterProviderByPIDandVerify(String provLabel, String provID){
			try{
				selectFromDropdown("ProvSearch.SBy.dd", provLabel);
				getObject("ProvSearch.SFor.tf").clear();
				getObject("ProvSearch.SFor.tf").sendKeys(provID);
				getObject("ProvSearch.btn").click();
				Thread.sleep(3000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static String verifyProvider(String Xpath1, String Xpath2, String Value) {
			
			String Name = null;
			WebElement tr1 = driver.findElement(By.xpath(Xpath1));
			List<WebElement> allTRs = tr1.findElements(By.tagName("tr"));
			System.out.println("Total TRs -> "+ allTRs.size());

			for (int i=1; i<=allTRs.size(); i=i+1){
				System.out.println("------------------");
				Name = driver.findElement(By.xpath(Xpath1+"/tr["+ i + Xpath2)).getText().trim();
				System.out.println(Name);
				if(Name.split(" ")[0].equalsIgnoreCase(Value)){
					System.out.println("------------------");
					System.out.println("Newly Created Provider is FOUND i.e.\t"+Name.split(" ")[0]);
				}else{
					System.out.println(Name+"\t is NOT FOUND");
				}
			}
			return Name;
		}
		
		public static void clickDeleteFrmSearchList(String Xpath1, String Xpath2, String Xpath3, String Value) throws Exception {
			
			String Name = null;
			WebElement tr1 = driver.findElement(By.xpath(Xpath1));
			
			List<WebElement> allTRs = tr1.findElements(By.tagName("tr"));
			System.out.println("Total TRs -> "+ allTRs.size());

			for (int i=1; i<=allTRs.size(); i=i+1){
				System.out.println("------------------");
				Name = driver.findElement(By.xpath(Xpath1+"/tr["+ i + Xpath2)).getText().trim();
				System.out.println(Name);
				if(Name.equalsIgnoreCase(Value)){
					driver.findElement(By.xpath(Xpath1+"/tr["+ i + Xpath2 + Xpath3)).click();
					System.out.println(Name+"\t is removed");
				}
				System.out.println("------------------");
			}
			driver.switchTo().alert().accept();
			Thread.sleep(2000L);
		}
		
		public static void clickEditFrmSearchList(String Xpath1, String Xpath2, String Xpath3, String Value) throws Exception {
			
			String Name = null;
			WebElement tr1 = driver.findElement(By.xpath(Xpath1));
			
			List<WebElement> allTRs = tr1.findElements(By.tagName("tr"));
			System.out.println("Total TRs -> "+ allTRs.size());

			for (int i=1; i<=allTRs.size(); i=i+1){
				Thread.sleep(2000L);
				System.out.println("------------------");
				Name = driver.findElement(By.xpath(Xpath1+"/tr["+ i + Xpath2)).getText().trim();
				System.out.println(Name);
				if(Name.equalsIgnoreCase(Value)){
					Thread.sleep(2000L);
					driver.findElement(By.xpath(Xpath1+"/tr["+ i + Xpath2 + Xpath3)).click();
					System.out.println(Name+"\t is Found");
					break;
				}
				System.out.println("------------------");
			}
		}
		
		public static void updateProviderDetails(String TaxID, String SSN, String Ext, String MPhone, String TTYPhone, 
				String Pager, String Email, String FirstName, String LastName, String MiddleName, String DBA, 
				String StateLicNo, String MedicareID, String UpinID, String RRID, String MedicaID, String NewID, String BlueXID, 
				String TaxonomyID, String CalOptimaID, String DEA){
			try{
				enterDataInTF("Provider.Tax.tf", TaxID);
				enterDataInTF("Provider.SSN.tf", SSN);
				
				Ext = generate10digitRandomNumber();
				enterDataInTF("Provider.WPhoneExt.tf", Ext);
				System.out.println(Ext+"\tis selected from Ext field");
				
				MPhone = generate10digitRandomNumber();
				enterDataInTF("Provider.MPhone.tf", MPhone);
				System.out.println(MPhone+"\tis selected from Mobile Phone field");
				
				TTYPhone = generate10digitRandomNumber();
				enterDataInTF("Provider.TTYPhone.tf", TTYPhone);
				System.out.println(TTYPhone+"\tis selected from TTY Phone field");
				
				Pager = generate10digitRandomNumber();
				enterDataInTF("Provider.Pager.tf", Pager);
				System.out.println(Pager+"\tis selected from Pager field");
				
				String Mail = generateRandomNumber(Email)+"@gmail.com";
				enterDataInTF("Provider.Email.tf", Mail);
				System.out.println(Mail+"\tis entered in the Email field");
				
				enterDataInTF("Provider.KnownLN.tf", FirstName);
				enterDataInTF("Provider.KnownFN.tf", LastName);
				enterDataInTF("Provider.KnownMN.tf", MiddleName);
				enterDataInTF("Provider.DBA.tf", DBA);
				
				enterDataInTF("Provider.StaticLicNo.tf", StateLicNo);
				enterDataInTF("Provider.MedicareID.tf", MedicareID);
				enterDataInTF("Provider.UpinID.tf", UpinID);
				enterDataInTF("Provider.RRID.tf", RRID);
				enterDataInTF("Provider.MedicaidID.tf", MedicaID);
				enterDataInTF("Provider.NewID.tf", NewID);
				enterDataInTF("Provider.BXID.tf", BlueXID);
				enterDataInTF("Provider.TaxonomyID.tf", TaxonomyID);
				enterDataInTF("Provider.CalOptimaID.tf", CalOptimaID);
				enterDataInTF("Provider.DEA.tf", DEA);
				
				Thread.sleep(3000L);
				fluentWait(OR.getProperty("Provider.Save.Upbtn"));
				getObject("Provider.Save.Upbtn").click();
				Thread.sleep(5000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void createProvOtherID(String IDType, String Location, String ID, String Description){
			try{
				Thread.sleep(2000L);
				
				fluentWait(OR.getProperty("ProvOID.ID.tf"));
				enterDataInTF("ProvOID.ID.tf", ID);
				
				fluentWait(OR.getProperty("ProvOID.Desc.tf"));
				enterDataInTF("ProvOID.Desc.tf", Description);
				
				fluentWait(OR.getProperty("ProvOID.IDType.DD"));
				selectFromDropdown("ProvOID.IDType.DD", IDType);
				
				fluentWait(OR.getProperty("ProvOID.Loc.DD"));
				selectFromDropdown("ProvOID.Loc.DD", Location);
				
				fluentWait(OR.getProperty("ProvOID.Save.btn"));
				getObject("ProvOID.Save.btn").click();
				System.out.println("Provider Other ID is Created Successfully");
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void updateProvOIDDetails(String EffectiveDT){
			try{
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvOID.EffDt.tf"));
				enterDataInTF("ProvOID.EffDt.tf", EffectiveDT);
				fluentWait(OR.getProperty("ProvOID.Save.btn"));
				getObject("ProvOID.Save.btn").click();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void createProvCommLog(String ComType, String Inquirer, String InqName, String LogDate, String Source){
			try{
				Thread.sleep(2000L);
				
				fluentWait(OR.getProperty("ProvCLog.InqName.tf"));
				enterDataInTF("ProvCLog.InqName.tf", InqName);
				
				fluentWait(OR.getProperty("ProvCLog.LogDt.tf"));
				enterDataInTF("ProvCLog.LogDt.tf", LogDate);
				
				fluentWait(OR.getProperty("ProvCLog.ComType.DD"));
				selectFromDropdown("ProvCLog.ComType.DD", ComType);
				
				fluentWait(OR.getProperty("ProvCLog.Inquirer.DD"));
				selectFromDropdown("ProvCLog.Inquirer.DD", Inquirer);
				
				fluentWait(OR.getProperty("ProvCLog.Src.DD"));
				selectFromDropdown("ProvCLog.Src.DD", Source);
				
				fluentWait(OR.getProperty("ProvCLog.Save.btn"));
				getObject("ProvCLog.Save.btn").click();
				System.out.println("Provider Comm. Log is Created Successfully");
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void filterProvCommLogAndVerify(String ComType, String Inquirer){
			try{
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCLog.SComType.DD"));
				selectFromDropdown("ProvCLog.SComType.DD", ComType);
				fluentWait(OR.getProperty("ProvCLog.SInquirer.DD"));
				selectFromDropdown("ProvCLog.SInquirer.DD", Inquirer);
				fluentWait(OR.getProperty("ProvCLog.Search.btn"));
				getObject("ProvCLog.Search.btn").click();
				Thread.sleep(3000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static String addSecondstoTime(String LogTime){
			String timeVal[] = LogTime.split(" ");
			if(timeVal[1].equalsIgnoreCase("PM")){
				String temp[] = timeVal[0].split(":");
				timeVal[0] = String.valueOf((Integer.parseInt(temp[0]) + 12))+":"+temp[1];
			}else{
				timeVal[0] = timeVal[0];
			}
			//return timeVal[0]+":00"+" "+timeVal[1];
			return timeVal[0];
		}
		
		public static String appendLogDateAndTime(String LogDate, String LogTime){
			
			String Value = addSecondstoTime(LogTime);
			System.out.println("After Converting the Date format:\t"+LogDate+" "+Value);
			return LogDate+" "+Value;
			
		}
		
		public static void clickEditProvCommLog(String Xpath1, String Xpath2, String Value) throws Exception {
			
			String Name = null;
			WebElement tr1 = driver.findElement(By.xpath(Xpath1));
			
			List<WebElement> allTRs = tr1.findElements(By.tagName("tr"));
			System.out.println("Total TRs -> "+ allTRs.size());

			for (int i=1; i<=allTRs.size(); i=i+1){
				Thread.sleep(2000L);
				System.out.println("------------------");
				Name = driver.findElement(By.xpath(Xpath1+"/tr["+ i + Xpath2)).getText().trim();
				System.out.println(Name);
				if(Name.equalsIgnoreCase(Value)){
					Thread.sleep(2000L);
					System.out.println("------------------");
					driver.findElement(By.xpath(Xpath1+"/tr["+ i + "]/td[1]/a")).click();
					System.out.println("Clicked on Edit icon from Provider Comm. Log Table");
				}else{
					System.out.println(Name+"\t is NOT FOUND");
				}
			}
		}
		
		public static void updateProvCommLog(String Phone, String CommType, String Dept){
			try{
				Thread.sleep(2000L);
				
				fluentWait(OR.getProperty("ProvCLog.Phone.tf"));
				enterDataInTF("ProvCLog.Phone.tf", Phone);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCLog.CommType.tf"));
				enterDataInTF("ProvCLog.CommType.tf", CommType);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCLog.Dept.tf"));
				enterDataInTF("ProvCLog.Dept.tf", Dept);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCLog.Save.btn"));
				getObject("ProvCLog.Save.btn").click();
				System.out.println("Provider Comm. Log is updated Successfully");
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void selectProviderFrmSeachModel(String ProviderID, String Xpath1, String Xpath2, String Xpath3){
			try{
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvRel.SearchBy.DD"));
				selectFromDropdown("ProvRel.SearchBy.DD", "Provider ID");
				
				fluentWait(OR.getProperty("ProvRel.SearchFor.tf"));
				enterDataInTF("ProvRel.SearchFor.tf", ProviderID);
				
				fluentWait(OR.getProperty("ProvRel.Search.btn"));
				getObject("ProvRel.Search.btn").click();
				Thread.sleep(2000L);
				
				clickEditFrmSearchList(Xpath1, Xpath2, Xpath3, ProviderID);
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void createProvRelationship(String HealthPlan, String LOB, String Type, String PayUsing, String EffectiveDT, 
				String ProviderID, String Xpath1, String Xpath2, String Xpath3){
			try{
				Thread.sleep(2000L);
				
				selectCheckbox("ProvRel.isActive.Chkbox", "Y");
				
				fluentWait(OR.getProperty("ProvRel.HPlan.DD"));
				selectFromDropdown("ProvRel.HPlan.DD", HealthPlan);
				
				fluentWait(OR.getProperty("ProvRel.Type.DD"));
				selectFromDropdown("ProvRel.Type.DD", Type);
				
				fluentWait(OR.getProperty("ProvRel.PayUse.DD"));
				selectFromDropdown("ProvRel.PayUse.DD", PayUsing);
				
				fluentWait(OR.getProperty("ProvRel.EffDt.tf"));
				enterDataInTF("ProvRel.EffDt.tf", EffectiveDT);
				
				fluentWait(OR.getProperty("ProvRel.SelectProv.btn"));
				getObject("ProvRel.SelectProv.btn").click();
				Thread.sleep(2000L);
				selectProviderFrmSeachModel(ProviderID, Xpath1, Xpath2, Xpath3);
				fluentWait(OR.getProperty("ProvRel.LOB.DD"));
				selectFromDropdown("ProvRel.LOB.DD", LOB);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvRel.Save.btn"));
				getObject("ProvRel.Save.btn").click();
				System.out.println("Provider Relationship is Created Successfully");
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void updateProviderRelationshipDetails(String TerminateDT){
			try{
				fluentWait(OR.getProperty("ProvRel.TermDt.tf"));
				enterDataInTF("ProvRel.TermDt.tf", TerminateDT);
				
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvRel.Update.btn"));
				getObject("ProvRel.Update.btn").click();
				Thread.sleep(3000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void createProvAssignment(String LOB, String EffectiveDT, String Network, String CEIPA, 
				String ContractStatus, String ContractType, String FeeSchedule, String NPI, String VendorID, 
				String Xpath1, String Xpath2, String Xpath3){
			try{
				Thread.sleep(2000L);
				
				selectCheckbox("ProvAssign.PCP.Chkbox", "Y");
				selectCheckbox("ProvAssign.Spec.Chkbox", "Y");
				selectCheckbox("ProvAssign.Basic.Chkbox", "Y");
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvAssign.EffDt.tf"));
				enterDataInTF("ProvAssign.EffDt.tf", EffectiveDT);
				
				fluentWait(OR.getProperty("ProvAssign.NPI.tf"));
				enterDataInTF("ProvAssign.NPI.tf", NPI);
				
				fluentWait(OR.getProperty("ProvAssign.LOB.DD"));
				selectFromDropdown("ProvAssign.LOB.DD", LOB);
				
				fluentWait(OR.getProperty("ProvAssign.Network.DD"));
				selectFromDropdown("ProvAssign.Network.DD", Network);
				
				fluentWait(OR.getProperty("ProvAssign.CEIPA.DD"));
				selectFromDropdown("ProvAssign.CEIPA.DD", CEIPA);
				
				fluentWait(OR.getProperty("ProvAssign.ContractStatus.DD"));
				selectFromDropdown("ProvAssign.ContractStatus.DD", ContractStatus);
				
				fluentWait(OR.getProperty("ProvAssign.ContractType.DD"));
				selectFromDropdown("ProvAssign.ContractType.DD", ContractType);
				
				fluentWait(OR.getProperty("ProvAssign.FeeSchedule.DD"));
				selectFromDropdown("ProvAssign.FeeSchedule.DD", FeeSchedule);
				
				fluentWait(OR.getProperty("ProvAssign.VSelect.btn"));
				getObject("ProvAssign.VSelect.btn").click();
				Thread.sleep(2000L);
				selectVendorFrmAssignModel(VendorID, Xpath1, Xpath2, Xpath3);
				fluentWait(OR.getProperty("ProvAssign.Save.btn"));
				getObject("ProvAssign.Save.btn").click();
				System.out.println("Provider Assignment is Created Successfully");
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void selectVendorFrmAssignModel(String VendorID, String Xpath1, String Xpath2, String Xpath3){
			try{
				Thread.sleep(3000L);
				
				fluentWait(OR.getProperty("ProvAssign.VendorPopup.Searchbtn"));
				getObject("ProvAssign.VendorPopup.Searchbtn").click();
				Thread.sleep(2000L);
				
				clickEditFrmSearchList(Xpath1, Xpath2, Xpath3, VendorID);
				
				if(!getObject("ProvAssign.Vendor.DD").getAttribute("value").trim().isEmpty()){
					System.out.println(new Select(getObject("ProvAssign.Vendor.DD")).getFirstSelectedOption().getText());
				}
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void updateProvAssignment(String TerminateDT, String FacilityType, String Spec1){
			try{
				Thread.sleep(2000L);
				
				fluentWait(OR.getProperty("ProvAssign.TermDt.tf"));
				enterDataInTF("ProvAssign.TermDt.tf", TerminateDT);
				
				fluentWait(OR.getProperty("ProvAssign.FType.DD"));
				selectFromDropdown("ProvAssign.FType.DD", FacilityType);
				
				fluentWait(OR.getProperty("ProvAssign.AddSpec1.DD"));
				selectFromDropdown("ProvAssign.AddSpec1.DD", Spec1);
								
				fluentWait(OR.getProperty("ProvAssign.Save.btn"));
				getObject("ProvAssign.Save.btn").click();
				System.out.println("Provider Assignment is updated Successfully");
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void createProviderCapitation(String CEIPA, String Vendor, String Topayer, String Month,
				String Amount, String HealthPlan){
			try{
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCap.Amount.tf"));
				enterDataInTF("ProvCap.Amount.tf", Amount);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCap.IPA.DD"));
				selectFromDropdown("ProvCap.IPA.DD", CEIPA);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCap.Topay.tf"));
				enterDataInTF("ProvCap.Topay.tf", Topayer);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCap.Month.DD"));
				selectFromDropdown("ProvCap.Month.DD", Month);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCap.Vendor.DD"));
				selectFromDropdown("ProvCap.Vendor.DD", Vendor);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCap.HealthPlan.DD"));
				selectFromDropdown("ProvCap.HealthPlan.DD", HealthPlan);
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCap.Save.btn"));
				getObject("ProvCap.Save.btn").click();
				System.out.println("Provider Capitation is Created Successfully");
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void updateProviderCapitation(String Notes){
			try{
				Thread.sleep(2000L);
				fluentWait(OR.getProperty("ProvCap.Notes.tf"));
				enterDataInTF("ProvCap.Notes.tf", Notes);
				Thread.sleep(2000L);
				
				fluentWait(OR.getProperty("ProvCap.Save.btn"));
				getObject("ProvCap.Save.btn").click();
				System.out.println("Provider Capitation is Created Successfully");
				Thread.sleep(2000L);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void createWorkLocation(String EffectiveDT, String TerminateDT, String FirstName, String Address1, 
				String Address2, String City, String Zipcode, String State, String Country, String County, String NPI, 
				String WPhone, String Ext, String Fax, String TTYPhone, String Locality, String FacilityType, String Validate){
			try{
				Thread.sleep(2000L);
				
				fluentWait(OR.getProperty("Pwl.NPI.tf"));
				enterDataInTF("Pwl.NPI.tf", NPI);
				
				fluentWait(OR.getProperty("Pwl.WPhone.tf"));
				enterDataInTF("Pwl.WPhone.tf", WPhone);
				
				fluentWait(OR.getProperty("Pwl.Ext.tf"));
				enterDataInTF("Pwl.Ext.tf", Ext);
				
				fluentWait(OR.getProperty("Pwl.Fax.tf"));
				enterDataInTF("Pwl.Fax.tf", Fax);
				
				fluentWait(OR.getProperty("Pwl.TTYPh.tf"));
				enterDataInTF("Pwl.TTYPh.tf", TTYPhone);
				
				fluentWait(OR.getProperty("Pwl.Add1.tf"));
				enterDataInTF("Pwl.Add1.tf", Address1);
				
				fluentWait(OR.getProperty("Pwl.Add2.tf"));
				enterDataInTF("Pwl.Add2.tf", Address2);
				
				fluentWait(OR.getProperty("Pwl.City.tf"));
				enterDataInTF("Pwl.City.tf", City);
				
				fluentWait(OR.getProperty("Pwl.Zcode.tf"));
				enterDataInTF("Pwl.Zcode.tf", Zipcode);
				
				fluentWait(OR.getProperty("Pwl.Ctry.tf"));
				enterDataInTF("Pwl.Ctry.tf", Country);
				
				fluentWait(OR.getProperty("Pwl.Cty.tf"));
				enterDataInTF("Pwl.Cty.tf", County);
				
				fluentWait(OR.getProperty("Pwl.EffDt.tf"));
				enterDataInTF("Pwl.EffDt.tf", EffectiveDT);
				
				fluentWait(OR.getProperty("Pwl.TermDt.tf"));
				enterDataInTF("Pwl.TermDt.tf", TerminateDT);
				
				fluentWait(OR.getProperty("Pwl.State.DD"));
				selectFromDropdown("Pwl.State.DD", State);
				
				fluentWait(OR.getProperty("Pwl.Loc.DD"));
				selectFromDropdown("Pwl.Loc.DD", Locality);
				
				fluentWait(OR.getProperty("Pwl.Ftype.DD"));
				selectFromDropdown("Pwl.Ftype.DD", FacilityType);
				
				fluentWait(OR.getProperty("Pwl.Save.btn"));
				getObject("Pwl.Save.btn").click();
				System.out.println("Provider Working Location is Created Successfully");
				Thread.sleep(2000L);
				
				System.out.println(getObject("").getText().trim());
				Assert.assertEquals(Validate, getObject("").getText().trim());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public static void createProvLocation(String AAStartDT, String AAEndDT, String EffectiveDT, String LOB, String IPA, String PanelStatus, String Spec1, String Validate){
			try{
				Thread.sleep(2000L);
				
				fluentWait(OR.getProperty("Pwl.LREffDt.tf"));
				enterDataInTF("Pwl.LREffDt.tf", AAStartDT);
				
				fluentWait(OR.getProperty("Pwl.LRTermDt.tf"));
				enterDataInTF("Pwl.LRTermDt.tf", AAEndDT);
				
				fluentWait(OR.getProperty("Pwl.PanelStatusDate.tf"));
				enterDataInTF("Pwl.PanelStatusDate.tf", EffectiveDT);
				
				fluentWait(OR.getProperty("Pwl.LRLOB.DD"));
				selectFromDropdown("Pwl.LRLOB.DD", LOB);
				
				fluentWait(OR.getProperty("Pwl.LRIPA.DD"));
				selectFromDropdown("Pwl.LRIPA.DD", IPA);
				
				fluentWait(OR.getProperty("Pwl.PanelStatus.DD"));
				selectFromDropdown("Pwl.PanelStatus.DD", PanelStatus);
				
				fluentWait(OR.getProperty("Pwl.Spec1.DD"));
				selectFromDropdown("Pwl.Spec1.DD", Spec1);
				
				fluentWait(OR.getProperty("Pwl.LRSave.btn"));
				getObject("Pwl.LRSave.btn").click();
				System.out.println("Provider Location is Created Successfully");
				Thread.sleep(2000L);
				
				System.out.println(getObject("").getText().trim());
				Assert.assertEquals(Validate, getObject("").getText().trim());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}
