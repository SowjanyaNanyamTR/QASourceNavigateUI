package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;

public class SourceNavigateAngularSectionPageElements {
    public static final String SOURCE_NAV_SECTIONS_AG_GRID = "";
    public static final String FIRST_RENDITION_ROW = "(" + SOURCE_NAV_SECTIONS_AG_GRID + "//div[@row-id='0'])[2]";
    public static final String TOTAL_SECTIONS_NUMBERS = "//span[contains(text(),'Total sections:')]";
    public static final String TOTAL_SECTIONS = "//span[contains(text(),'Total sections: %s')]";
    public static final String TOTAL_SECETION_NUMBERS = "//span[contains(text(),'Total sections: ')]";
    public static final String CLEAR_FILTERS_SECTION_TAB = SOURCE_NAV_SECTION + CLEAR_FILTERS;
    public static final String EDIT_SECTION_INSTRUCTION_NOTES = "Edit Section Instruction Notes";
    public static final String SECTION_LOCKED_BY = "//p[contains(text(),' This Section is currently locked by ')]";
    public static final String VIEW_SECTION_INSTRUCTION_NOTES = "View Section Instruction Notes";
    public static final String NOTES_VALUE_OF_SECTION = "(//button[@class='grid-btn'])[2]";
    public static final String SECTION_PROPERTIES_BOX_LABEL = "//label[text()='%s']";
    public static final String SECTION_PROPERTIES_INPUT_FIELD = SECTION_PROPERTIES_BOX_LABEL + "/input";
    public static final String SYSTEM_PROPERTIES_INPUT_FIELD = "//source-nav-section-properties" + SECTION_PROPERTIES_INPUT_FIELD;
    public static final String SECTION_PROPERTIES_TEXT_FIELD = SECTION_PROPERTIES_BOX_LABEL + "/following::input[1]";
    public static final String CALENDAR_OPTION = SECTION_PROPERTIES_BOX_LABEL + "/following::button[@aria-label='Open calendar'][1]";
    public static final String LABEL_TEXT_FIELD = SECTION_PROPERTIES_BOX_LABEL + "/following::input[1]";
    public static final String OWNER_DATA = SECTION_PROPERTIES_BOX_LABEL + "/following-sibling::input[1]";
    public static final String SECTION_INSTRUCTIONS = SECTION_PROPERTIES_BOX_LABEL+"/textarea";
    public static final String PREP_TRACKING_FIRST_HALF = "//div[@class='properties-container mb-3']/div[1]"+SECTION_PROPERTIES_BOX_LABEL;
    public static final String PREP_TRACKING_SECOND_HALF = "//div[@class='properties-container mb-3']/div[2]"+SECTION_PROPERTIES_BOX_LABEL;
    public static final String SECTION_PROPERTIES_DIFFICULTY_LEVEL_DROPDOWN="//source-nav-single-select-combobox[@id='difficultyLevels']";
    public static final String DIFFICULTY_LEVEL_DROPDOWN_VALUE="//div[text()='%s']";

    @FindBy(how = How.ID, using = "sect-note-text-area")
    public static WebElement sectionLevelinstructions;
}
