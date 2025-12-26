package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_Login_DDT extends BaseClass{

	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven")//getting data from different package and class
	public void verify_Login_DDT(String email, String pwd, String exp) throws InterruptedException{
		try {
		logger.info("****-----Starting TC003_Login_DDTC ----****");
		HomePage hm=new HomePage(driver);
		hm.clickMyAccount();
		hm.clickLogin();
		
		logger.info(" Inside login account page");		
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		logger.info("Inside the MyAccount page");
		MyAccountPage map=new MyAccountPage(driver);
		boolean val=map.isMyAccountPageExists();
		
		
		if(exp.equalsIgnoreCase("Valid")) {
			if(val==true) {
				map.clickLogout();
				Assert.assertTrue(true);
			}
			else
				Assert.assertTrue(false);
		}
		if(exp.equalsIgnoreCase("Invalid")){
			
			if(val==false)
				Assert.assertTrue(true);
			else{
				map.clickLogout();
				Assert.assertTrue(false);
			}				
		}		
		}
		catch(AssertionError e)
		{
			logger.error("Test case failed due to exception: " + e.getMessage(), e);
	        Assert.fail("Exception occurred during Login with DDT: " + e.getMessage());
		}
		
		logger.info("****-----Finished TC003_Login_DDT----****");
		}
}
