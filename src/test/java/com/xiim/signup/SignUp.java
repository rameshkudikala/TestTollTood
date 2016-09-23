package com.xiim.signup;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.xiim.util.TestUtil;

public class SignUp extends TestSuiteBase {

	String runmodes[] = null;
	static int count = -1;
	public boolean fail = false;
	public boolean skip = false;
	public boolean isTestPass = false;
	public boolean isAfterTestFail = false;
	public boolean isAfterTestSkip = true;
	public String errormsg1 = null;

	@BeforeClass
	public void checkTestSkip() {

		if (!TestUtil.isTestCaseRunnable(suite_xiim_xls, this.getClass().getSimpleName())) {
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}
		// load the runmodes off the tests
		runmodes = TestUtil.getDataSetRunmodes(suite_xiim_xls, this.getClass().getSimpleName());
		
	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "getTestData")
	public void SignUp_Scenario(String Scenario, String FirstName, String LastName, String Email, String Password,
			String confpwd) throws Throwable {

		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			skip = true;
			throw new SkipException("Runmode for test set data set to no " + count);
		} else {
			skip = false;
		}
		// webdriver
		openBrowser();

		// APP_LOGS.info("Browser Opened Successfully");
		driver.get(CONFIG.getProperty("testSiteName"));
		Thread.sleep(1000);
		// fluentWait("SignUp_Btn");
		// APP_LOGS.info("SignUp button found");

		getObject("ebay_register").click();

		fluentWait("ebay_register_reg_btn");
		// Description: Sign up Registration with Empty Data.
		if (Scenario.equalsIgnoreCase("SC1")) {

			try {

				// Create An Account Text Verification

				String Create_Account_Text = getObject("ebay_register_createacc_msg").getText().trim();
				System.out.println(Create_Account_Text);
				Assert.assertEquals("start a business account", Create_Account_Text);

				// Click on Create account button
				getObject("ebay_register_reg_btn").click();
				fluentWait("ebay_register_FN_valmsg");
				// First Name Validation Message
				String fname = getObject("ebay_register_FN_valmsg").getText().trim();
				// APP_LOGS.info(fname);
				System.out.println(fname);
				// Last Name Validation Message
				fluentWait("ebay_register_LN_valmsg");
				String lname = getObject("ebay_register_LN_valmsg").getText().trim();
				// APP_LOGS.info(lname);
				System.out.println(lname);
				// EmailID Validation Message
				fluentWait("ebay_register_email_valmsg");
				String EmailId = getObject("ebay_register_email_valmsg").getText().trim();
				// APP_LOGS.info(EmailId);
				System.out.println(EmailId);
				// Confirm EmailID Validation Message
				fluentWait("ebay_register_pwd_valmsg");
				String password = getObject("ebay_register_pwd_valmsg").getText().trim();
				// APP_LOGS.info(CEmailID);
				System.out.println(password);
				// Password Validation Message
				fluentWait("ebay_register_confpwd_valmsg");
				String confirmpwd = getObject("ebay_register_confpwd_valmsg").getText().trim();
				System.out.println(confirmpwd);
				// APP_LOGS.info(Password1);

				// Compare First Name
				Assert.assertEquals("Please enter your first name.", fname);
				// Compare Last Name
				Assert.assertEquals("Please enter your last name.", lname);
				// Compare EmailId
				Assert.assertEquals("Please enter your email address.", EmailId);
				// Compare Confirm EmailId
				Assert.assertEquals("Please enter your password.", Password);
				// Comapre Password
				Assert.assertEquals("Please enter your password again.", confpwd);

				isTestPass = true;
			} catch (AssertionError a) {
				isTestPass = false;
				errormsg = a.getMessage();

				System.out.println(errormsg);
				Assert.fail();
			} catch (Throwable a) {
				isTestPass = false;
				errormsg = a.getMessage();

				APP_LOGS.info(errormsg);
				System.out.println(errormsg);
				Assert.fail();
			}
		}
		// Descritption: Sign up Registration with Invalid Password which is
		// having no Special chars and rest are valid data.
		else if (Scenario.equalsIgnoreCase("SC2")) {

			try {
				driver.navigate().refresh();
				getObject("ebay_register_FN_txt").sendKeys(FirstName);
				getObject("ebay_register_LN_txt").sendKeys(LastName);
				getObject("ebay_register_email_txt").sendKeys(Email);
				getObject("ebay_register_createpwd_txt").sendKeys(Password);
				getObject("ebay_register_confpwd_txt").sendKeys(confpwd);
				getObject("ebay_register_reg_btn").click();
				Thread.sleep(2000);

				isTestPass = true;
			} catch (AssertionError a) {
				isTestPass = false;
				errormsg = a.getMessage();

				System.out.println(errormsg);
				Assert.fail();
			}

			catch (Throwable a) {
				isTestPass = false;
				errormsg = a.getMessage();

				// APP_LOGS.info(errormsg);
				System.out.println(errormsg);
				Assert.fail();
			}
		}
	}

	/*
	 * // Verify the labels within the signup screen else
	 * if(Scenario.equalsIgnoreCase("Labels")){
	 * 
	 * try{ String
	 * fname_label=getObject("FirstName_label_signup").getText().trim();
	 * APP_LOGS.info(fname_label);
	 * 
	 * String lname_label=getObject("LastName_label_signup").getText().trim();
	 * APP_LOGS.info(lname_label);
	 * 
	 * String Email_label=getObject("Email_label_signup").getText().trim();
	 * APP_LOGS.info(Email_label);
	 * 
	 * String
	 * ConfirmEmail_label=getObject("ConfirmEmail_label_signup").getText().trim(
	 * ); APP_LOGS.info(ConfirmEmail_label);
	 * 
	 * String
	 * Password_label=getObject("Password_label_signup").getText().trim();
	 * APP_LOGS.info(Password_label);
	 * 
	 * String
	 * ConfirmPassword_label=getObject("ConfirmPassword_label_signup").getText()
	 * .trim(); APP_LOGS.info(ConfirmPassword_label);
	 * 
	 * String
	 * CantReadThis_label=getObject("CantReadThis_link_label_signup").getText().
	 * trim(); APP_LOGS.info(CantReadThis_label);
	 * 
	 * String
	 * CreateAccount_button_label=getObject("CreatAccount_button_label_signup").
	 * getAttribute("value"); APP_LOGS.info(CreateAccount_button_label);
	 * 
	 * String
	 * Cancel_button_label=getObject("Cancel_button_label_signup").getAttribute(
	 * "value"); APP_LOGS.info(Cancel_button_label);
	 * 
	 * String
	 * UserAgreement_label=getObject("UserAgreemnt_label_signup").getText().trim
	 * (); APP_LOGS.info(UserAgreement_label);
	 * 
	 * String
	 * UserPrivacy_label=getObject("UserPrivacy_label_signup").getText().trim();
	 * APP_LOGS.info(UserPrivacy_label);
	 * 
	 * Assert.assertEquals("*First Name",fname_label); APP_LOGS.info(
	 * "First name Label verified"); Assert.assertEquals("*Last Name"
	 * ,lname_label); APP_LOGS.info("Last name Label verified");
	 * Assert.assertEquals("*Email",Email_label); APP_LOGS.info(
	 * "Email Label verified"); Assert.assertEquals("*Confirm Email"
	 * ,ConfirmEmail_label); APP_LOGS.info("Confirm Email Label verified");
	 * Assert.assertEquals("*Password",Password_label); APP_LOGS.info(
	 * "Password Label verified"); Assert.assertEquals("*Confirm Password"
	 * ,ConfirmPassword_label); APP_LOGS.info("Confirm Password Label verified"
	 * ); Assert.assertEquals("Cant read this?",CantReadThis_label);
	 * APP_LOGS.info("Can't read Label verified"); Assert.assertEquals(
	 * "Create an Account",CreateAccount_button_label); APP_LOGS.info(
	 * "Create An account Label verified");
	 * Assert.assertEquals("Cancel",Cancel_button_label); APP_LOGS.info(
	 * "Cancel Label verified"); Assert.assertEquals("User Agreement"
	 * ,UserAgreement_label); APP_LOGS.info("User Agreement Label verified");
	 * Assert.assertEquals("Privacy Policy",UserPrivacy_label); APP_LOGS.info(
	 * "Privacy Policy Label verified"); isTestPass=true; }catch (AssertionError
	 * a ) { isTestPass = false; errormsg=a.getMessage();
	 * 
	 * System.out.println(errormsg); Assert.fail(); }
	 * 
	 * catch(Throwable a){ isTestPass = false; errormsg = a.getMessage();
	 * APP_LOGS.info(errormsg);
	 * 
	 * System.out.println(errormsg); Assert.fail(); }
	 * 
	 * }
	 * 
	 * // Verify the Broken links within the Signup screen else
	 * if(Scenario.equalsIgnoreCase("links")){ // Get all the links url
	 * List<WebElement> ele = driver.findElements(By.tagName("a"));
	 * System.out.println("size:" + ele.size()); boolean isValid = false; for
	 * (int i = 0; i < ele.size(); i++) { //
	 * System.out.println(ele.get(i).getAttribute("href")); isValid =
	 * getResponseCode(ele.get(i).getAttribute("href")); if (isValid) {
	 * System.out.println("ValidLinks:" + ele.get(i).getAttribute("href")); }
	 * else { System.out.println("InvalidLinks:" +
	 * ele.get(i).getAttribute("href")); } } } }
	 */

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
			TestUtil.reportDataSetResult(suite_xiim_xls, "Test Cases",
					TestUtil.getRowNum(suite_xiim_xls, this.getClass().getSimpleName()), "SKIP");
		} else {
			if (!isAfterTestFail) {
				TestUtil.reportDataSetResult(suite_xiim_xls, "Test Cases",
						TestUtil.getRowNum(suite_xiim_xls, this.getClass().getSimpleName()), "PASS");
			} else {
				TestUtil.reportDataSetResult(suite_xiim_xls, "Test Cases",
						TestUtil.getRowNum(suite_xiim_xls, this.getClass().getSimpleName()), "FAIL");
			}
		}
		closeBrowser();
	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suite_xiim_xls, this.getClass().getSimpleName());
	}

}
