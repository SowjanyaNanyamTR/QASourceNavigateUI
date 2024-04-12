package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.SECTION_PROPERTIES;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.SUBMIT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements.FIND_TEXT_FIELD_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.SECTION_ROW;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static java.lang.String.format;

public class SNGeneralSectionPropertiesPageTests extends SourceNavigateAngularAssertions {
    SourceDatapodObject datapodAPVObject;
    SourceDatapodObject datapodPREPObject;
    Connection connection;
    String renditionAPVUuid;
    String renditionPREPUuid;

    @BeforeEach
    public void mockData(TestInfo testInfo) {
        if (!(testInfo.getDisplayName().equals("verifySectionGroupTabClearFilterTest()"))) {
            datapodAPVObject = SourceDataMockingNew.Iowa.Small.APV.insert();
            renditionAPVUuid = datapodAPVObject.getRenditions().get(0).getRenditionUUID();
            datapodPREPObject = SourceDataMockingNew.Iowa.Small.PREP.insert();
            renditionPREPUuid = datapodPREPObject.getRenditions().get(0).getRenditionUUID();
            connection = CommonDataMocking.connectToDatabase(environmentTag);
        }
    }

    @AfterEach
    public void deleteMockedData() {
        if (datapodAPVObject != null) {
            // sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodAPVObject.delete();
        }
        if (datapodPREPObject != null) {
            // sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodPREPObject.delete();
        }
        disconnect(connection);
    }

    @Test
    @CustomAnnotations.BrowserAnnotations.EDGE
    @CustomAnnotations.UserAnnotations.LEGAL
    @CustomAnnotations.LogAnnotations.LOG
    public void verifySectionPropertiesUserInterface() {
        //Storing APV and PREP Rendition UUIDs
        String[] renditionUUIDs = {renditionAPVUuid, renditionPREPUuid};

        ArrayList<String> tabs = new ArrayList<>(Arrays.asList("Section properties", "Proposed/Approved Tracking Information",
                "PREP Tracking Information", "System Properties"));

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        for (String uuid : renditionUUIDs) {
            sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", uuid);
            sourceNavigateAngularLeftSidePanePage().clickFindButton();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularPage().rightClickRenditions();

            //Section tab clicking and selecting first row
            sourceNavigateAngularTabsPage().click(SECTION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Verifying Display of Header and Buttons
            assertThatDisplayOfHeadersAndButtons();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

            //Verifying Display of 4 tabs
            for (String tab : tabs)
                assertThatDisplayOfSectionPropertiesTabs(tab);

            //Closing Section Properties Pop Up
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            sourceNavigateAngularPage().clickCancel();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            sourceNavigateAngularPage().clickConfirm();

            //Navigating to Rendition Tab
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            sourceNavigateAngularLeftSidePanePage().clear(String.format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID"));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        }
    }

    @Test
    @CustomAnnotations.BrowserAnnotations.EDGE
    @CustomAnnotations.UserAnnotations.LEGAL
    @CustomAnnotations.LogAnnotations.LOG
    public void verifySectionPropertiesTabUIContentAndFunctionality() {
        //Storing APV and PREP Rendition UUIDs
        String[] renditionUUIDs = {renditionAPVUuid, renditionPREPUuid};

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        for (String uuid : renditionUUIDs) {
            sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", uuid);
            sourceNavigateAngularLeftSidePanePage().clickFindButton();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularPage().rightClickRenditions();

            //Section tab clicking and selecting first row
            sourceNavigateAngularTabsPage().click(SECTION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Verifying fields in Section Properties Tab
            assertThatFieldsViewMode("Section Number ", "Section Delta Count ");
            assertThatTextFieldsAndCalendarOptions("Effective Date", "Assigned Date");

            sourceNavigateAngularPage().clickCancel();

            //Closing Section Properties Pop Up
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", SOURCE_NAV_ASSIGN_USER_CANCEL, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

            sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);
            assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");
//            assertThatDisplayOfPopUpCloseUIButton("Confirmation");
            sourceNavigateAngularPage().click(CANCEL_BUTTON);
            assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

            sourceNavigateAngularPage().click(CONFIRM_BUTTON);

            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().click(CANCEL_BUTTON);
            assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
            sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, "Confirmation"));
            assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

            String headerText = sourceNavigateAngularDeltaPage().getElementsText(HEADER);
            sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
            assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
            sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);

            sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
            assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
            sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, "Confirmation"));
            assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

            sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
            assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
            sourceNavigateAngularPage().click(CONFIRM_BUTTON);
            assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

            //Navigating to Rendition Tab
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            sourceNavigateAngularLeftSidePanePage().clear(String.format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID"));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        }
    }

    @Test
    @CustomAnnotations.BrowserAnnotations.EDGE
    @CustomAnnotations.UserAnnotations.LEGAL
    @CustomAnnotations.LogAnnotations.LOG
    public void verifySystemPropertiesTabUIContentAndFunctionality() {
        String[] renditionUUIDs = {renditionAPVUuid, renditionPREPUuid};
        String[] systemPropertiesTabFields = {"Section UUID ", "Source Rendition UUID ", "Bill ID ", "Content Type ID ", "Document UUID ",
                "Legislation Type ID ", "Serial Number ", "Rendition Status ID ", "Source Lineage UUID ", "Content Set ID ", "Correlation ID ",
                "Doc Type ID ", "Jurisdiction ID ", "Session ID "};

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        for (String uuid : renditionUUIDs) {
            sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", uuid);
            sourceNavigateAngularLeftSidePanePage().clickFindButton();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularPage().rightClickRenditions();

            //Section tab clicking and selecting first row
            sourceNavigateAngularTabsPage().click(SECTION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "System Properties"));
            for (String filed : systemPropertiesTabFields)
                assertThatInputFieldsViewMode(filed);

            sourceNavigateAngularPage().click(SUBMIT);

            //Navigating to Rendition Tab
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            sourceNavigateAngularLeftSidePanePage().clear(String.format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID"));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        }

    }
}
