package com.qa.duck.test.selenium.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.web.server.LocalServerPort;

import com.qa.duck.test.selenium.pages.HomePage;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomeTest {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@BeforeEach
	void init() {
		ChromeOptions opts = new ChromeOptions();
		opts.setHeadless(true);
		driver = new ChromeDriver(opts);
	}

//	@Test
	void testCreate() {
		driver.manage().window().maximize();
		driver.get("http://localhost:" + port);
		HomePage home = PageFactory.initElements(driver, HomePage.class);

		home.createDuck("Donald", "White", "Disney World");

		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.textToBePresentInElement(home.getCreateOutput(), "name"));
		assertThat(home.getCreateOutput().getText().isBlank()).isFalse();
	}

//	@Test
	void testRead() {
		driver.manage().window().maximize();
		driver.get("http://localhost:" + port);
		HomePage home = PageFactory.initElements(driver, HomePage.class);

		final String name = "Daffy";
		home.createDuck(name, "Black", "WB Studios");
		home.readDucks();
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.textToBePresentInElement(home.getReadOutput(), "name"));
		assertThat(home.getReadOutput().getText().contains("\"name\":\"" + name + "\"")).isTrue();
	}

}
