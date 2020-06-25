package com.qa.duck.test.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	@FindBy(id = "createName")
	private WebElement createName;

	@FindBy(id = "createColour")
	private WebElement createColour;

	@FindBy(id = "createHabitat")
	private WebElement createHabitat;

	@FindBy(id = "createButton")
	private WebElement createButton;

	@FindBy(id = "createOutput")
	private WebElement createOutput;

	@FindBy(id = "readButton")
	private WebElement readButton;

	@FindBy(id = "readOutput")
	private WebElement readOutput;

	@FindBy(id = "deleteInput")
	private WebElement deleteInput;

	@FindBy(id = "deleteButton")
	private WebElement deleteButton;

	@FindBy(id = "deleteOuput")
	private WebElement deleteOutput;

	public void createDuck(String name, String colour, String habitat) {
		this.createName.sendKeys(name);
		this.createColour.sendKeys(colour);
		this.createHabitat.sendKeys(habitat);
		this.createButton.click();
	}

	public WebElement getCreateName() {
		return createName;
	}

	public WebElement getCreateColour() {
		return createColour;
	}

	public WebElement getCreateHabitat() {
		return createHabitat;
	}

	public WebElement getCreateButton() {
		return createButton;
	}

	public WebElement getCreateOutput() {
		return createOutput;
	}

	public void readDucks() {
		this.readButton.click();
	}

	public WebElement getReadOutput() {
		return readOutput;
	}

	public WebElement getDeleteInput() {
		return deleteInput;
	}

	public WebElement getDeleteButton() {
		return deleteButton;
	}

	public WebElement getDeleteOutput() {
		return deleteOutput;
	}

}
