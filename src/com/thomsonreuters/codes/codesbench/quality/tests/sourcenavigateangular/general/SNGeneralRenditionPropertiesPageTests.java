package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements.FIND_TEXT_FIELD_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.FIRST_RENDITION_ROW;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.COMBO_BOX_LIST;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.RENDITION_TAB;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.SOURCE_NAV_ASSIGN_USER_CANCEL;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ColumnIds.PREP_STARTED;
import static java.lang.String.format;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ColumnIds.ATTORNEY_WORK_STARTED;


public class SNGeneralRenditionPropertiesPageTests extends SourceNavigateAngularAssertions {

    SourceDatapodObject datapodAPVObject;
    SourceDatapodObject datapodPREPObject;
    Connection connection;
    static String renditionAPVUuid;
    static String renditionPREPUuid;

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
//             sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodPREPObject.delete();
        }
        disconnect(connection);
    }

    /**
     * Test Case ID:724121_TC1
     * Description:Rendition Properties: verify the box user interface
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideRenditionUUID")
    @EDGE
    @LEGAL
    @LOG
    public void verifyRenditionPropertiesBoxUserInterface(String uuid) {
        //Storing APV and PREP Rendition UUIDs
        if (uuid.equals("APV"))
            uuid = renditionAPVUuid;
        else if (uuid.equals("PREP"))
            uuid = renditionPREPUuid;
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().rightClick(SourceNavigateAngularSectionPageElements.FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        assertThatRenditionPropertiesLabels();

    }

    /**
     * Test Case ID:724121_TC2
     * Description:Rendition Properties: Rendition properties tab: verify the tab UI content and functionality
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideRenditionUUID")
    @EDGE
    @LEGAL
    @LOG
    public void verifyRenditionPropertiesTabUIContentAndFunctionality(String uuid) {
        if (uuid.equals("APV"))
            uuid = renditionAPVUuid;
        else if (uuid.equals("PREP"))
            uuid = renditionPREPUuid;
        //Storing APV and PREP Rendition UUIDs

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClick(SourceNavigateAngularSectionPageElements.FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        //Section tab clicking and selecting first row
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verifying fields in Rendition Properties Tab
        List<String> renditionViewModeLabels = Arrays.asList("Class Name ", "Delta Count ", "Document Subtype ", "Earliest Effective Date ", "Legislation Type ",
                "Rendition Section Group ","First Title ","Content Type ","Document Type ","Document Number ");

        assertThatRenditionFieldsViewOnlyMode(renditionViewModeLabels);
        assertThatTextFieldsAndCalendarOptions("Effective Date", "Approval Date");
        assertThatDisplayOfDropdowns("Assigned User", "Difficulty Level");
        assertThatDisplayOfTextArea("Rendition Instructions ");

           String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();
       sourceNavigateAngularPage().click(format(CALENDAR_OPTION, "Effective Date"));
        sourceNavigateAngularPage().sendTextToTextbox(format(LABEL_TEXT_FIELD, "Effective Date"), currentDate);
       DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(CALENDAR_OPTION, "Approval Date"));
        sourceNavigateAngularPage().sendTextToTextbox(format(LABEL_TEXT_FIELD, "Approval Date"), currentDate);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
//
        // Selection User:TLE TCBA-BOT from Assigned USer Dropdown
           sourceNavigateAngularPage().click(format(LABEL_TEXT_FIELD, "Assigned User"));
        sourceNavigateAngularDeltaPage().sendKeys("TLE TCBA-BOT");
        sourceNavigateAngularLockReportPage().pressEnter();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        // Selecting Difficulty level
        sourceNavigateAngularDeltaPage().click(SECTION_PROPERTIES_DIFFICULTY_LEVEL_DROPDOWN);
        sourceNavigateAngularDeltaPage().click(String.format(DIFFICULTY_LEVEL_DROPDOWN_VALUE, "E1"));
        sourceNavigateAngularPage().waitForPageLoaded();

       // Entering text in Section Instructions Test area
        sourceNavigateAngularPage().sendTextToTextbox(format(SECTION_INSTRUCTIONS, "Rendition Instructions "), "This is testing data for Rendition Instructions");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        sourceNavigateAngularPage().click(SUBMIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Closing Rendition Properties Pop Up
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", SOURCE_NAV_ASSIGN_USER_CANCEL, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, "Confirmation"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        String headerText = sourceNavigateAngularDeltaPage().getElementsText(HEADER);
        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, "Confirmation"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(CONFIRM_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

    }

    /**
     * Test Case ID:724121_TC4
     * Description:Rendition Properties: Proposed/Approved Tracking Information tab: verify the tab UI content and functionality
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyProposedApprovedTrackingInformationContentAndFunctionality() {
        //Storing APV and PREP Rendition UUIDs
        String loggedUserName = "TLE TCBA-BOT";

        List<String> proposedViewModeOnlyLabels = Arrays.asList("slipLoadDate", "editDate", "uploadedDate", "indexEntryCompletedDate", "sourceLoadDate",
                "initialSrcLoadDate", "superquickLoadDate", "transferToBTS", "cpdDate");
        List<String> proposedApprovedCalendarOptions = Arrays.asList("Compare Date", "Chapter/Approval Received", "APV Started", "APV Completed", "Topical Heading Needed",
                "Topical Heading Completed", "Images Sent Out", "Images Completed", "Tabular Requested",
                "Tabular Received Hardcopy", "Tabular Started", "Tabular Completed", "APV Review Started",
                "APV Review Completed", "Ready for Advance Sheet", "Published in Advance Sheet");

        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionAPVUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "Proposed/Approved Tracking Information"));
        assertThatRenditionProposedFieldsViewOnlyMode(proposedViewModeOnlyLabels);

        assertThatProposedApprovedTrackingInformationTabContainsColumns("Attribute", "Date", "Owner");

        for (String labelName : proposedApprovedCalendarOptions)
            assertThatTabTextFieldAndCalendarOption(labelName);
        for (String labelName : proposedApprovedCalendarOptions) {
            sourceNavigateAngularDeltaPage().click(format(CALENDAR_OPTION, labelName));
            sourceNavigateAngularPage().sendTextToTextbox(format(LABEL_TEXT_FIELD, labelName), currentDate);
        }

        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().click(SUBMIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().waitForPageLoaded();
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);

        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "Proposed/Approved Tracking Information"));

        String currentDateWithDelimeter = DateAndTimeUtils.getCurrentDateMMddyyyy();
        for (String labelName : proposedApprovedCalendarOptions) {
            assertThatCurrentDateSaved(labelName, currentDateWithDelimeter);
            assertThatColumnPopulatedWithOwnerData(labelName, loggedUserName);
        }

        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);

        //Closing Section Properties Pop Up
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickConfirm();
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);

        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "PREP Tracking Information"));

        assertThatPREPTrackingInputFieldsAreReadOnly();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        sourceNavigateAngularPage().click(SUBMIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLeftSidePanePage().accessColumnsInterface("RENDITION");

        for (String columnName : proposedApprovedCalendarOptions)
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("RENDITION", columnName);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLeftSidePanePage().accessColumnsInterface("RENDITION");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }


    /**
     * Test Case ID:724121_TC5
     * Description:Rendition Properties: PREP Tracking Information: verify the tab UI content and functionality
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyPREPTrackingInformationTabContentAndFunctionality() {
        List<String> attributeLabelNames = Arrays.asList(
                "Attorney Work Started", "Attorney Work Completed", "Run Auto-Integrate & Audit",
                "Versioning Work Started", "Versioning Work Completed",
                "PREP Started", "PREP Completed", "Ready for Integration", "Integration Started",
                "Integration Completed", "Integration 2 Started", "Integration 2 Completed", "Ready to Resolve Auto-Integrate Errors",
                "Resolve Auto-Integrate Errors Started", "Resolve Auto-Integrate Errors Completed", "Audit Review Requested", "Audit Review Started",
                "Audit Review Completed", "Audit Corrections Started", "Audit Corrections Completed",
                "Corrections Review Completed", "Corrections Audit Requested", "Corrections Audit Started",
                "Corrections Audit Completed", "Clean", "Released To Westlaw");

        String[] checkBoxLabelIDs = {"integrationQueryStarted", "integrationQueryCompleted", "correctionsAuditRequired"};
        String dropdownLabelName = "Corrections Audit Required";

        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();
        String loggedUserName = "TLE TCBA-BOT";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionPREPUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);

        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "PREP Tracking Information"));
        assertThatPREPTrackingFirstHalfColumns("Attribute", "Value", "Owner");
        assertThatPREPTrackingSecondHalfColumns("Attribute", "Value", "Owner");

        for (String labelName : attributeLabelNames)
            assertThatTabTextFieldAndCalendarOption(labelName);

        for (String id : checkBoxLabelIDs)
            assertThatCheckboxesEnabledOrNot(id);

        assertThatDisplayOfPREPTrackingDropdownValues(dropdownLabelName, "EAGAN", "MANILA");

        for (String labelName : attributeLabelNames) {
            sourceNavigateAngularDeltaPage().click(format(CALENDAR_OPTION, labelName));
            sourceNavigateAngularPage().sendTextToTextbox(format(LABEL_TEXT_FIELD, labelName), currentDate);
        }

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);

        for (String id : checkBoxLabelIDs) {
            sourceNavigateAngularPage().checkCheckbox(format(CHECKBOX_INPUT_FIELD, id));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        }

        sourceNavigateAngularPage().click(format(PREP_TRACKING_COMBO_BOX, dropdownLabelName));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(format(COMBO_BOX_LIST, "EAGAN"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(SUBMIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().waitForPageLoaded();

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);

        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "PREP Tracking Information"));
        String currentDateWithDelimeter = DateAndTimeUtils.getCurrentDateMMddyyyy();
        for (String labelName : attributeLabelNames) {
            assertThatCurrentDateSaved(labelName, currentDateWithDelimeter);
            assertThatColumnPopulatedWithOwnerData(labelName, loggedUserName);
        }

        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickConfirm();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().waitForPageLoaded();

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "Proposed/Approved Tracking Information"));

        assertThatProposedApprovedTrackingInformationInputFieldsAreReadOnly();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        sourceNavigateAngularPage().click(SUBMIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularLeftSidePanePage().accessColumnsInterface("RENDITION");
        for (String columnName : attributeLabelNames)
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("RENDITION", columnName);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLeftSidePanePage().accessColumnsInterface("RENDITION");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        sourceNavigateAngularTabsPage().click(RENDITION_TAB);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);


        //  Assertions.assertTrue(driver().findElement(By.xpath(String.format("//div[@class='ag-center-cols-container']/div[@aria-selected='true']/div[@col-id='" +  + "']",col)).isDisplayed());

    }

    private static Stream<Arguments> provideRenditionUUID() {
        return Stream.of(
                Arguments.of("APV"),
                Arguments.of("PREP")
        );
    }

    /**
     * Test Case ID:724121_TC6
     * Description:Rendition Properties: System Properties: verify the tab UI content and functionality
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideRenditionUUID")
    @EDGE
    @LEGAL
    @LOG
    public void verifySystemPropertiesTabUIContentAndFunctionality(String value) {
        if (value.equals("APV")) {
            value = renditionAPVUuid;
        } else if (value.equals(("PREP"))) {
            value = renditionPREPUuid;
        }
        List<String> systemPropertiesTabFields = Arrays.asList("Rendition UUID ", "Bill ID ", "Document UUID ", "Content Type ID ", "Legislation Type ID ",
                "Serial Number ", "Rendition Status ID ", "Lineage UUID ", "Tagalong UUID ", "Content Set ID ", "Correlation ID ", "Doc Type ID ", "Jurisdiction ID ",
                "Session ID ", "Class Prefix ", "Manually Loaded By ", "Last Deleted By ", "Last Deleted Date ");

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", value);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        sourceNavigateAngularPage().click(SECTION_PROPERTIES_TAB);

        for (String field : systemPropertiesTabFields) {
            assertThatRenditionSystemInputFieldsViewMode(field);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        }
        sourceNavigateAngularPage().click(SUBMIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

    }


}
