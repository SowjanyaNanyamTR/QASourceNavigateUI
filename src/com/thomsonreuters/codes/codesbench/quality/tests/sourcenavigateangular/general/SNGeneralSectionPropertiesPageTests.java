package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.SECTION_PROPERTIES;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.CLOSE_UI_BUTTON;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.HEADER;
import static java.lang.String.format;

public class SNGeneralSectionPropertiesPageTests extends SourceNavigateAngularAssertions {
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    @BeforeEach
    public void mockData(TestInfo testInfo) {
        if (!(testInfo.getDisplayName().equals("verifySectionGroupTabClearFilterTest()"))) {
            datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
            renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();
            connection = CommonDataMocking.connectToDatabase(environmentTag);
        }
    }

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifySectionPropertiesUserInterface() {

        //Finding the Rendition with Rendition UUID with APV Rendition Status
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();


        //Section tab clicking and selecting first row
        sourceNavigateAngularTabsPage().click(SECTION_TAB);
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);

        //Verifying Display of Header
        sourceNavigateAngularPage().isElementDisplayed(HEADER);
        sourceNavigateAngularPage().isElementDisplayed(Cancel_Button);
        sourceNavigateAngularPage().isElementDisplayed(Submit_Button);
        String headerText = sourceNavigateAngularDeltaPage().getElementsText(HEADER);
        System.out.println("Text displayed is" + headerText);
        sourceNavigateAngularPage().isElementDisplayed(format(CLOSE_UI_BUTTON, headerText));

        //Verifying Display of 4 tabs
        sourceNavigateAngularPage().isElementDisplayed(format(ANY_TAB_NAME, "Rendition properties"));
        sourceNavigateAngularPage().isElementDisplayed(format(ANY_TAB_NAME, "Proposed/Approved Tracking Information"));
        sourceNavigateAngularPage().isElementDisplayed(format(ANY_TAB_NAME, "PREP Tracking Information"));
        sourceNavigateAngularPage().isElementDisplayed(format(ANY_TAB_NAME, "System Properties"));

//        ArrayList<String> tabs = new ArrayList<>(Arrays.asList("Rendition properties", "Proposed/Approved Tracking Information",
//                        "PREP Tracking Information", "System Properties"));
//        for (String tab : tabs)
//            assertDisplayOfSectionPropertiesTabs(tab);

        //Closing Section Properties Pop Up
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickConfirm();

        //Navigating to Rendition Tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);

    }
}