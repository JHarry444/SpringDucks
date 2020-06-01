package com.qa.duck.test.selenium.tests;

import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.qa.duck.test.selenium.pages.HomePage;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomeTest {

	@LocalServerPort
	private int port;
	
	
	private WebDriver driver;

	@Before
	public void init() {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		this.driver = new ChromeDriver();
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void testCreate() {
		driver.get("http://localhost:" + port);
		HomePage home = PageFactory.initElements(driver, HomePage.class);
		home.getCreateName().sendKeys("Donald");
		home.getCreateColour().sendKeys("White");
		home.getCreateName().sendKeys("Disney World");
		home.getCreateButton().click();
		assertFalse(home.getCreateOutput().getText().isBlank());
	}

}
