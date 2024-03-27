package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class TabularPrintPreviewPageElements
{
	public static final String TABULAR_PRINT_PREVIEW_PAGE_TITLE = "Tabular Print Preview";

	@FindBy(how = How.ID, using = "pageForm:okButton")
	public static WebElement okButton;

	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:outputMsg']/a")
	public static WebElement workflowIDLink;
}
