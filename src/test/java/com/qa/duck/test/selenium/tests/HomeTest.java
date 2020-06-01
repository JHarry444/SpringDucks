package com.qa.duck.test.selenium.tests;

import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.duck.test.selenium.pages.HomePage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomeTest {

	private WebDriver driver;

	@LocalServerPort
	private int port;

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
		home.getCreateHabitat().sendKeys("Disney World");
		home.getCreateButton().click();
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.textToBePresentInElement(home.getCreateOutput(), "name"));
		assertFalse(home.getCreateOutput().getText().isBlank());
	}

}
