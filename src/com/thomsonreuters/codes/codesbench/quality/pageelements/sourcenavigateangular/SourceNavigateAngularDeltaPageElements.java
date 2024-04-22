package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;

public class SourceNavigateAngularDeltaPageElements
{
    public static final String TOTAL_DELTAS_NUMBERS = "//span[contains(text(),'Total deltas:')]";
    public static final String TOTAL_DELTA_NUMBERS= "//span[contains(text(),' Total deltas: ')]";
    public static final String TOTAL_DELTAS= "//span[contains(text(),' Total deltas: %s')]";
    public static final String CLEAR_FILTERS_DELTA_TAB =  SOURCE_NAV_DELTAS + CLEAR_FILTERS;
    public static final String EDIT_DELTA_INSTRUCTION_NOTES = "Edit Delta Instruction Notes";
    public static final String DELTA_LOCKED_BY ="//p[contains(text(),' This Delta is currently locked by ')]";
    public static final String VIEW_DELTA_INSTRUCTION_NOTES = "View Delta Instruction Notes";

    @FindBy(how = How.ID, using = "delta-note-text-area")
    public static WebElement deltaLevelinstructions;

    public static final String DELTA_PROPERTIES_HEADER = "//div[@class='modal-header modal-header-override']";
    public static final String DELTA_CANCEL_BUTTON = "//button[text()='Cancel']";
    public static final String DELTA_SUBMIT_BUTTON = "//button[contains(text(),' Submit ')]";
    public static final String DELTA_CLOSE_BUTTON = "//button[@class='close-btn']";
    public static final String DELTA_PROPERTIES_DROPDOWN_ELEMENTS ="//div[contains(@class,'group')]/label[text()='%s']";
    public static final String DELTA_PROPERTIES_VIEWMODE_ELEMENTS= "//label[text()='%s']//input[@class='form-control']";
    public static final String ASSIGNED_USER_DROPDOWN="//source-nav-assign-user-selector[@id='assigned-user']";
    public static final String EFFECTIVE_CALENDER= "//div/input[@id='effectiveDate']/following-sibling::button[@title='Open calendar']";
    public static final String DELTA_DIFFICULTY_LEVEL_DROPDOWN="//source-nav-single-select-combobox[@id='difficultyLevels']";
    public static final String ASSIGNED_CALENDER= "//div/input[@id='assignedToDate']/following-sibling::button[@title='Open calendar']";
    public static final String DELTA_PROPERTIES_DESCRIPTION="//textarea[contains(@class,'instructions-area full-width ng-untouched ng-pristine')]";
    public static final String DIFFICULTY_LEVEL_DROPDOWN_VALUE="//div[text()='%s']";
    public static final String DELTA_PROPERTIES_LOCKED_HEADER = "//div[@class='alert bento-alert alert-danger']";
}
