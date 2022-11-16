package sel_test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSel {

	public static void main(String[] args) throws InterruptedException {
		//Set the path of executable browser driver
		System.setProperty("webdriver.chrome.driver", "G:\\SelJars\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver(); //Parent p=new Child (WebDriver is Parent & ChromeDriver is Child)
		
		driver.get("http://localhost:4200/"); //open browser with url
		driver.findElement(By.className("uniGrade")).click();
		
		Thread.sleep(5000);

		
		System.out.println(driver.getCurrentUrl());
		
		String got = driver.getCurrentUrl();
		String expected = "http://localhost:4200/main-component";
		
		if(got == expected) {
			System.out.println("Good!");
		} else {
			System.out.println("Bad!");
		}
		driver.quit(); //close browser
		
		
		
	}

}
