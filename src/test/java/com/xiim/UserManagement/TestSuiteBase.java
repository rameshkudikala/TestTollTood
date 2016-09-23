package com.xiim.UserManagement;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import com.xiim.base.TestBase;
import com.xiim.util.TestUtil;


public class TestSuiteBase extends TestBase {
	// check if the suite ex has to be skiped
	@BeforeSuite
	public void checkSuiteSkip() throws Exception {
		initialize();
		
		APP_LOGS.debug("Checking Runmode of UserProfile Suite");
		if (!TestUtil.isSuiteRunnable(suiteXls, "UserProfile Suite")) {
			APP_LOGS.debug("Skipped UserProfile Suite as the runmode was set to NO");
			throw new SkipException(
					"Runmode of UserProfile Suite set to no. So Skipping all tests in UserProfile Suite");
		}

	}

}