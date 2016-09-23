package com.xiim.UserManagement;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.xiim.util.TestUtil;
public class UserProfile extends TestSuiteBase{
	
	String runmodes[] = null;
	static int count = -1;
	public boolean fail = false;
	public boolean skip = false;
	public boolean isTestPass = false;
	public boolean isAfterTestFail = false;
	public boolean isAfterTestSkip = true;
	public String errormsg1 = null;
	
	@BeforeTest
	public void checkTestSkip() {

		if (!TestUtil.isTestCaseRunnable(suite_xiim_xls, this.getClass()
				.getSimpleName())) {
			APP_LOGS.debug("Skipping Test Case"
					+ this.getClass().getSimpleName() + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case"
					+ this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}
		// load the runmodes off the tests
		runmodes = TestUtil.getDataSetRunmodes(suite_xiim_xls, this.getClass().getSimpleName());
	}
	
	public void dataSelection(String Date, String Month, String Year){
		 WebElement divs2 = driver.findElement(By.xpath("//*[@id='dayDateOfBirth']"));
		   List<WebElement> option1 = divs2.findElements(By.tagName("option"));
		   System.out.println(option1.size());
		   WebElement divs3 = driver.findElement(By.xpath("//*[@id='monthDateOfBirth']"));
		   List<WebElement> option2 = divs3.findElements(By.tagName("option"));
		   System.out.println(option2.size());
		   WebElement divs4 = driver.findElement(By.xpath("//*[@id='yearDateOfBirth']"));
		   List<WebElement> option3 = divs4.findElements(By.tagName("option"));
		  System.out.println(option3.size());
		   // Date Selection
		   for(int i=1; i<=option1.size();i++){
			   String Date_drpdwn = driver.findElement(By.xpath("//*[@id='dayDateOfBirth']/option["+i+"]")).getText();
			   /*int Date_int = Integer.parseInt(Date_drpdwn);*/
			   APP_LOGS.info(Date_drpdwn);
			   if(Date_drpdwn.contains(Date)){
				   driver.findElement(By.xpath("//*[@id='dayDateOfBirth']/option["+i+"]")).click();
				   break;
			   }
		   }
		   
		   // Month Selection
		   
		   for(int i=1; i<=option1.size();i++){
			   String Month_drpdwn = driver.findElement(By.xpath("//*[@id='monthDateOfBirth']/option["+i+"]")).getText();
			   APP_LOGS.info(Month_drpdwn);
			   if(Month_drpdwn.contains(Month)){
				   driver.findElement(By.xpath("//*[@id='monthDateOfBirth']/option["+i+"]")).click();
				   break;
			   }
		   }
		   
		   // Year Selection
		   for(int i=1; i<=option1.size();i++){
			   String Year_drpdwn = driver.findElement(By.xpath("//*[@id='yearDateOfBirth']/option["+i+"]")).getText();
			   APP_LOGS.info(Year_drpdwn);
			   if(Year_drpdwn.contains(Year)){
				   driver.findElement(By.xpath("//*[@id='yearDateOfBirth']/option["+i+"]")).click();
				   break;
			   }
		   }
		
	}
	@SuppressWarnings("deprecation")
	@Test(dataProvider="getTestData")
	public void UserProfile_Scenarios(String Scenario,String Email, String Password, String Title,String Location,
			String Gender, String AboutMe,String Privacy,String Date,String Month,String Year) throws Exception{
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			skip = true;
			throw new SkipException("Runmode for test set data set to no "+ count);
		} else {
			skip = false;
		}
		// webdriver
		openBrowser();
		
		APP_LOGS.info("Browser Opened Successfully");
		driver.get(CONFIG.getProperty("testSiteName"));
		Thread.sleep(1000);
		
		// Navigate to login page
		getObject("login_link").click();
		APP_LOGS.info("Successfully Navigated to Login Page");	
		String login = getObject("login_label").getText().trim();
		APP_LOGS.info(login);
		Assert.assertEquals("Log In", login);
		APP_LOGS.info("Login Label is Verified");
		
		getObject("login_Email_text").sendKeys(Email);
		getObject("login_Pwd_text").sendKeys(Password);
		getObject("login_btn").click();
		Thread.sleep(2000);
		getObject("UserSettings").click();
		Thread.sleep(1000);
		getObject("MyProfile_Link").click();
		Thread.sleep(1000);
		
		
		if(Scenario.equalsIgnoreCase("SC1")){
			try{
			// Enter Title
			getObject("Title_Text").clear();
			getObject("Title_Text").sendKeys(Title);
			// Enter About Me
			getObject("Aboutme_Text").clear();
			getObject("Aboutme_Text").sendKeys(AboutMe);
			// Select Privacy Type
			WebElement divs = driver.findElement(By.xpath("//*[@id='privacyId']"));
			//WebElement divs = getObject("Privacy_Type_id");  
			List<WebElement> option = divs.findElements(By.tagName("option"));
			   
			   for(int i=1; i<option.size();i++){
				  String privacy_Type = driver.findElement(By.xpath("//*[@id='privacyId']/option["+i+"]")).getText().trim();
			 	   APP_LOGS.info(privacy_Type);
				   if(Privacy.equalsIgnoreCase("Public")){
					   driver.findElement(By.xpath("//*[@id='privacyId']/option["+i+"]")).click();
					   break;
				   }
			   }
			   //Enter Location
			   getObject("Location_Text").clear();
			   getObject("Location_Text").sendKeys(Location);
			   
			   // Select Gender
			   WebElement divs1 = driver.findElement(By.xpath("//*[@id='profileInformationEditMode']/div[2]/div[7]/div"));
			   List<WebElement> labels = divs1.findElements(By.tagName("label"));
			   
			   for(int j=1; j<labels.size(); j++){
				   String gender =driver.findElement(By.xpath("//*[@id='profileInformationEditMode']/div[2]/div[7]/div/label["+j+"]")).getText().trim();
				   APP_LOGS.info(gender);
				   if(Gender.equalsIgnoreCase(gender)){
					   driver.findElement(By.xpath("//*[@id='profileInformationEditMode']/div[2]/div[7]/div/label["+j+"]")).click();
					   break;
				   }
			   }
			   	   
			  dataSelection(Date, Month, Year);
			   
			 /*  Thread.sleep(2000);
			   driver.findElement(By.xpath("//*[@id='yearDateOfBirth']")).sendKeys(Keys.TAB);
			   driver.findElement(By.xpath("//*[@id='yearDateOfBirth']")).sendKeys(Keys.TAB);
			   driver.findElement(By.xpath("//*[@id='yearDateOfBirth']")).sendKeys(Keys.ENTER);*/
			
			  getObject("About_Cancel").click();
			   Thread.sleep(2000);
			   getObject("Sidebar_Expand").click();
			   getObject("Sidebar_profile").click();
			   getObject("Sidebar_Expand").click();
			   String TitleEmpty = getObject("Title_Text").getText();
			   String LocationEmpty = getObject("Location_Text").getText();
			   String AboutMeEmpty = getObject("Aboutme_Text").getText();
			   String DateEmpty = getObject("Date_Empty").getText();
			   System.out.println(DateEmpty);
			   String MonthEmpty = getObject("Month_Empty").getText();
			   String YearEmpty = getObject("Year_Empty").getText();
			   
			   if(TitleEmpty == null && LocationEmpty == null && AboutMeEmpty == null){
				 
					 APP_LOGS.info("Filds are cleared after clicking on cancel button");				   
			   }if(DateEmpty.contains("Day") && MonthEmpty.contains("Month") && YearEmpty.contains("Year")){
				  
				   APP_LOGS.info("Date Cleared");
			   }else{
				   Assert.fail();
			   }
			   isTestPass=true;
		}catch (AssertionError a ) {
			isTestPass = false;
			errormsg=a.getMessage();
			
			System.out.println(errormsg);
			Assert.fail();
		}
		catch(Throwable a){
			isTestPass = false;
			errormsg = a.getMessage();
			
			APP_LOGS.info(errormsg);
			System.out.println(errormsg);
			Assert.fail();
		}
		}
		
		else if(Scenario.equalsIgnoreCase("SC2")){
			try{
			// Enter Title
			getObject("Title_Text").clear();
			getObject("Title_Text").sendKeys(Title);
			// Enter About Me
			getObject("Aboutme_Text").clear();
			getObject("Aboutme_Text").sendKeys(AboutMe);
			// Select Privacy Type
			WebElement divs = driver.findElement(By.xpath("//*[@id='privacyId']"));
			//WebElement divs = getObject("Privacy_Type_id");  
			List<WebElement> option = divs.findElements(By.tagName("option"));
			   
			   for(int i=1; i<option.size();i++){
				  String privacy_Type = driver.findElement(By.xpath("//*[@id='privacyId']/option["+i+"]")).getText().trim();
			 	   APP_LOGS.info(privacy_Type);
				   if(Privacy.equalsIgnoreCase("Public")){
					   driver.findElement(By.xpath("//*[@id='privacyId']/option["+i+"]")).click();
					   break;
				   }
			   }
			   //Enter Location
			   getObject("Location_Text").clear();
			   getObject("Location_Text").sendKeys(Location);
			   
			   // Select Gender
			   WebElement divs1 = driver.findElement(By.xpath("//*[@id='profileInformationEditMode']/div[2]/div[7]/div"));
			   List<WebElement> labels = divs1.findElements(By.tagName("label"));
			   
			   for(int j=1; j<labels.size(); j++){
				   String gender =driver.findElement(By.xpath("//*[@id='profileInformationEditMode']/div[2]/div[7]/div/label["+j+"]")).getText().trim();
				   APP_LOGS.info(gender);
				   if(Gender.equalsIgnoreCase(gender)){
					   driver.findElement(By.xpath("//*[@id='profileInformationEditMode']/div[2]/div[7]/div/label["+j+"]")).click();
					   break;
				   }
			   }
			   dataSelection(Date, Month, Year);
			    Thread.sleep(2000);
			   // Click on Save button
			   getObject("About_Save_btn").click();
			  Thread.sleep(2000);
			    fluentWait(OR.getProperty("About_Success_msg"));
			   // capture success message
			   String success_msg= getObject("About_Success_msg").getText().trim();
			   APP_LOGS.info(success_msg);
			   if(success_msg.contains("Success! Your profile details saved successfully.")){
				  
				   APP_LOGS.info("Success Message when click on Save changes -->" +success_msg);
			   }else{
				   Assert.fail();
			   }
		isTestPass=true;
		}catch (AssertionError a ) {
			isTestPass = false;
			errormsg=a.getMessage();
		
			System.out.println(errormsg);
			Assert.fail();
		}
		catch(Throwable a){
			isTestPass = false;
			errormsg = a.getMessage();
		
			APP_LOGS.info(errormsg);
			System.out.println(errormsg);
			Assert.fail();
		}   	   
			  
		}	
	}
	@AfterMethod
	public void reportDataSetResult() {
		if (skip) {
			TestUtil.reportDataSetResult(suite_xiim_xls, this.getClass().getSimpleName(), count + 2, "SKIP");
		} else {
			isAfterTestSkip = false;
			if (!isTestPass) {
				isAfterTestFail = true;
				TestUtil.reportDataSetResult(suite_xiim_xls, this.getClass().getSimpleName(), count + 2, "FAIL");
				TestUtil.reportDataSetError(suite_xiim_xls, this.getClass().getSimpleName(), count + 2, errormsg);
				
			} else {
				TestUtil.reportDataSetResult(suite_xiim_xls, this.getClass().getSimpleName(), count + 2, "PASS");
				TestUtil.reportDataSetError(suite_xiim_xls, this.getClass().getSimpleName(), count + 2, "No Errors");
				isTestPass = false;
			}
		}
	}

	@AfterTest
	public void reportTestResult() {
		if (isAfterTestSkip) {
			TestUtil.reportDataSetResult(suite_xiim_xls, "Test Cases", TestUtil.getRowNum(suite_xiim_xls, this.getClass()
							.getSimpleName()), "SKIP");
		} else {
			if (!isAfterTestFail) {
				TestUtil.reportDataSetResult(suite_xiim_xls, "Test Cases", TestUtil.getRowNum(suite_xiim_xls,
								this.getClass().getSimpleName()), "PASS");
			} else {
				TestUtil.reportDataSetResult(suite_xiim_xls, "Test Cases", TestUtil.getRowNum(suite_xiim_xls,
								this.getClass().getSimpleName()), "FAIL");
			}
		}
	//closeBrowser();
	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suite_xiim_xls, this.getClass().getSimpleName());
	}


	}



