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
}
