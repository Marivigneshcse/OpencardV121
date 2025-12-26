package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
public static WebDriver driver;
public Logger logger;
public Properties p;
	
	@BeforeClass(groups= {"Sanity","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException
	{
		//Loading config.properties file
		
		FileReader file=new FileReader("./src/test/resources/config.properties");
		p=new Properties();
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass()); // fetch the log4j2 xml file automatically
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities cap=new DesiredCapabilities();
			
			if(os.equalsIgnoreCase("windows"))
				cap.setPlatform(Platform.WIN11);
			else if(os.equalsIgnoreCase("linux"))
				cap.setPlatform(Platform.LINUX);
			else if(os.equalsIgnoreCase("mac"))
				cap.setPlatform(Platform.MAC);
			else
			{
				System.out.println("Invalid OS");
				return;
			}
			
			
			
			switch (br.toLowerCase())
			{
			case "chrome": cap.setBrowserName("chrome"); break;
			case "edge" : cap.setBrowserName("MicrosoftEdge"); break;
			case "firefox": cap.setBrowserName("firefox"); break;
			case "safari": cap.setBrowserName("safari"); break;
				default : System.out.println("Invalid browser"); return;
			
			}
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
		}
		
		else if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch (br.toLowerCase())
			{
			case "chrome": driver = new ChromeDriver(); break;
			case "edge" : driver = new EdgeDriver(); break;
			case "firefox": driver = new FirefoxDriver(); break;
			default : System.out.println("Invalid browser"); return;
			
			}
		}
		else
		{
			System.out.println("Invalid execution environment");
		}
		
		
		
		

		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));		
		driver.get(p.getProperty("url"));
		driver.manage().window().maximize();		
	}
	
	@AfterClass(groups= {"Sanity","Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}
	
	public String randomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}
	
	public String randomAlphaNumeric() {
		return RandomStringUtils.randomAlphabetic(5)+RandomStringUtils.randomNumeric(3);
	}
	
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"/screenshots/" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		//sourceFile.renameTo(targetFile);
		
		FileUtils.copyFile(sourceFile,targetFile);
			
		return targetFilePath;

	}
}
