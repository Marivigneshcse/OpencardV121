package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test(groups={"Regression","Sanity","Master"})
	public void verify_account_registration(){
		try {
		logger.info("****-----Starting TC001_verify account registration TC ----****");
		HomePage hm=new HomePage(driver);
		hm.clickMyAccount();
		logger.info("Clicked on my account");
		hm.clickRegister();
		logger.info("Clicked on register");
		
		AccountRegistrationPage reg=new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details");
		reg.setFirstName(randomString().toLowerCase());
		reg.setLastName(randomString().toLowerCase());
		reg.setEmail(randomString()+"@gmail.com");
		reg.setTelephone(randomNumber());
		
		String pwd=randomAlphaNumeric();
		reg.setPassword(pwd);
		reg.setConfirmPasswrod(pwd);
		
		reg.setPrivacyPolicy();
		reg.clickContinue();
		
		String msg=reg.getConfirmationMsg();
		logger.info("Validating expected msg");
		Assert.assertEquals(msg,"Your Account Has Been Created!");
		}
		catch (AssertionError e) {
	        logger.error("Test case failed due to exception: " + e.getMessage(), e);
	        logger.debug("Debug logs--");
	        Assert.fail("Exception occurred during account registration: " + e.getMessage());
	    }

		logger.info("****-----Finished verify account registration TC ----****");
	}
	
	

}
