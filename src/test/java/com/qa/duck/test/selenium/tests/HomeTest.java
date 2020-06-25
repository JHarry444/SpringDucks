package com.qa.duck.test.selenium.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentLoggerReporter;
import com.qa.duck.test.selenium.pages.HomePage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomeTest {

	@LocalServerPort
	private int port;

	private static ExtentReports report;

	private WebDriver driver;

	private ExtentTest test;

	@Autowired
	private Environment env;

	@Value("${extent-reports.location}")
	private static String reportLocation;

	@Rule
	public TestWatcher watchman = new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {
			test.fail(e);
			super.failed(e, description);
		}

		@Override
		protected void succeeded(Description description) {
			test.pass("yay");
			super.succeeded(description);
		}
	};

	static {
		report = new ExtentReports();
		report.attachReporter(new ExtentHtmlReporter(reportLocation + File.separator + "extentReport.html"),
				new ExtentLoggerReporter(reportLocation + File.separator + "extentReport.log"));
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@AfterClass
	public static void afterClass() {
		report.flush();
	}

	@Before
	public void init() {
		ChromeOptions opts = new ChromeOptions();
		opts.setHeadless(true);
		driver = new ChromeDriver(opts);
	}

	@Test
	public void testCreate() {
		test = report.createTest("Test CREATE");
		driver.manage().window().maximize();
		driver.get("http://localhost:" + port);
		HomePage home = PageFactory.initElements(driver, HomePage.class);

		home.createDuck("Donald", "White", "Disney World");

		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.textToBePresentInElement(home.getCreateOutput(), "name"));
		assertFalse("Output is blank", home.getCreateOutput().getText().isBlank());

	}

	@Test
	public void testRead() {
		test = report.createTest("Test READ");
		driver.manage().window().maximize();
		driver.get("http://localhost:" + port);
		HomePage home = PageFactory.initElements(driver, HomePage.class);

		final String name = "Daffy";
		home.createDuck(name, "Black", "WB Studios");
		home.readDucks();
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.textToBePresentInElement(home.getReadOutput(), "name"));
		assertTrue("Daffy is missing", home.getReadOutput().getText().contains("\"name\":\"" + name + "\""));
	}

}
