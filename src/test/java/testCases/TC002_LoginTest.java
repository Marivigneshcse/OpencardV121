package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups={"Sanity","Master"})
	public void Login() throws InterruptedException{
		try {
		logger.info("****-----Starting Login TC ----****");
		HomePage hm=new HomePage(driver);
		hm.clickMyAccount();
		hm.clickLogin();
		
		logger.info(" Inside login account page");		
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		logger.info("Inside the MyAccount page");
		MyAccountPage map=new MyAccountPage(driver);
		boolean val=map.isMyAccountPageExists();
		logger.info("Validating whether myaccount page is exist or not");
		Assert.assertTrue(val,"Login Failed");
		
		}
		catch(AssertionError e)
		{
			logger.error("Test case failed due to exception: " + e.getMessage(), e);
	        Assert.fail("Exception occurred during account registration: " + e.getMessage());
		}
		
		logger.info("****-----Finished TC002_Login_TC----****");
		}

}
