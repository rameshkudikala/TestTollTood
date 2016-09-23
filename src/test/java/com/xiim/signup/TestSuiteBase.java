package com.xiim.signup;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import com.xiim.base.TestBase;
import com.xiim.util.TestUtil;


public class TestSuiteBase extends TestBase {
	// check if the suite ex has to be skiped
	@BeforeSuite
	public void checkSuiteSkip() throws Exception {
		initialize();
		
		APP_LOGS.debug("Checking Runmode of SignUp Suite");
		if (!TestUtil.isSuiteRunnable(suiteXls, "SignUp Suite")) {
			APP_LOGS.debug("Skipped SignUp Suite as the runmode was set to NO");
			throw new SkipException(
					"Runmode of SignUp Suite set to no. So Skipping all tests in SignUp");
		}

	}

}