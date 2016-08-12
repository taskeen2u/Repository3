package Pakage3;



import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;

public class Class3 {
	public WebDriver driver;
	String ExpCheck1 = "The Census website is unavailable";
	PerfDB myDB = new PerfDB();
    public StopWatch LaunchCensus = new StopWatch();	
	
	
	
	@Test
  public void Function3() {
  
		driver = new FirefoxDriver();
		  
		   System.out.println("New Census");	
		   driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			LaunchCensus.start();
			
			driver.get("http://www.abs.gov.au/census");
			
			LaunchCensus.stop();
			
			double res_time_sec = (double) LaunchCensus.getTime();
			res_time_sec = res_time_sec/1000;
			
			String strTime = Double.toString(res_time_sec);
			
			if (driver.findElement(By.id("dynamicContent2Title")).getText().equals(ExpCheck1)){
			System.out.println("Census site is still down");
			
			RecordScriptExecInfo("Census Status", "Census Website", "Fail");
			RecordStepExecInfo("Census Status", "Census Website", "Homepage", "Fail", "Site down", strTime );
			
			driver.close();
			}
				//myDB.recordStepExecFailure(site_name, script_name, step_name, runtime_step_name, status, response_text).recordStepResponseTime(site_name, script_name, step_name, runtime_step_name, status, response_text, res_time_sec)
			else
			{
			System.out.println("Census site is up and running");
			
			RecordScriptExecInfo("Census Status", "Census Website", "Pass");
			RecordStepExecInfo("Census Status", "Census Website", "Homepage", "Pass", "Site up & running", strTime );

			driver.close();
			}
		  
		  }
		  
		  public void RecordScriptExecInfo(String ScriptName, String SiteName, String ScriptStatusValue) 
		  {
			  
			  driver.get("http://atnsw-bench006:880/script_execution_info.php");
			  driver.findElement(By.id("script_name")).sendKeys(ScriptName);
			  driver.findElement(By.id("site_name")).sendKeys(SiteName);
			  driver.findElement(By.id("step_status_value")).sendKeys(ScriptStatusValue);
			  driver.findElement(By.name("Submit")).click();
		  }
		 
		  public void RecordStepExecInfo(String ScriptName, String SiteName, String StepName, String StepStatusValue, String ResponseText, String ResponseTime)
		  {
			  driver.get("http://atnsw-bench006:880/step_execution_info.php");
			  driver.findElement(By.id("script_name")).sendKeys(ScriptName);
			  driver.findElement(By.id("site_name")).sendKeys(SiteName);
			  driver.findElement(By.id("step_name")).sendKeys(StepName);
			  driver.findElement(By.id("step_status_value")).sendKeys(StepStatusValue);
			  driver.findElement(By.id("response_text")).sendKeys(ResponseText);
			  driver.findElement(By.id("response_time")).sendKeys(ResponseTime);
			  driver.findElement(By.name("Submit")).click();
	}
  
	@AfterMethod
  public void afterMethod() {
  driver.quit();
	}

}
