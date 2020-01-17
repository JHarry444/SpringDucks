package com.qa.duck.test.rest;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SeleniumDuck {

	@LocalServerPort
	private int port;
	private WebDriver driver;

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		ChromeOptions options = new ChromeOptions();
//		options.setHeadless(true);
		this.driver = new ChromeDriver(options);
		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void testy() throws InterruptedException {
		this.driver.get("localhost:" + port);
		assertEquals("Bloop", "bloop", this.driver.findElement(By.id("fuck")).getText());
	}
	
	@After
	public void tearDown() {
		this.driver.close();
	}

}
