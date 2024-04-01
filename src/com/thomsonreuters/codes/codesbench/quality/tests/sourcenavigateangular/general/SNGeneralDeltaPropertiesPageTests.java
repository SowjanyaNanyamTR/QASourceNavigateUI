package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.*;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.DELTA_PROPERTIES;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.DELTA_CLOSE_BUTTON;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements.FIND_TEXT_FIELD_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static java.lang.String.format;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

public class SNGeneralDeltaPropertiesPageTests extends SourceNavigateAngularAssertions {

    String renditionUuid;
    String renditionUuid1;
    SourceDatapodObject datapodObject;
    SourceDatapodObject datapodObject1;
    Connection connection;

    @BeforeEach
    public void mockData(TestInfo testInfo) {
        if (!(testInfo.getDisplayName().equals("verifyDeltaTabClearFilterTest()")) &&
                !(testInfo.getDisplayName().equals("assignUserAndDateOftheDocuments()"))) {
            datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
//            datapodObject1 = SourceDataMockingNew.Iowa.Small.PREP.insert();
            renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();
//            renditionUuid1 = datapodObject1.getRenditions().get(0).getRenditionUUID();
            connection = CommonDataMocking.connectToDatabase(environmentTag);
        }
    }

    @AfterEach
    public void deleteMockedData() {
        if (datapodObject != null) {
            // sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void NavigatingAndVerifyingDeltaProperties(String renditionStatus){
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionStatus);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));
        sourceNavigateAngularPage().rightClickRenditions();
        //Delta tab clicking and selecting first row
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularPage().click((format(DELTA_ROW, 0)));
        //assertThatBackgroundBlueForselectedRenditionRow((format(DELTA_ROW, 0)));
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        //Click on Delta Properties content Menu
        sourceNavigateAngularPage().clickContextMenuItem(DELTA_PROPERTIES);

    }

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyDeltaPropertiesUserInterface() {
        String[] renditionStatus = new String[]{renditionUuid, "I300E7A00658F11E28B049F1D7A89B350"};
        //I300E7A00658F11E28B049F1D7A89B350
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        //Applying "APV" Filter for rendition status and clicking on first row
        for (int i = 0; i < renditionStatus.length; i++) {
            NavigatingAndVerifyingDeltaProperties(renditionStatus[i]);
            //verifying display of header content,cancel,submit and close button of delta properties popup
            String headerText = sourceNavigateAngularDeltaPage().getElementsText(DELTA_PROPERTIES_HEADER);
            System.out.println("text displayed is" + headerText);
            sourceNavigateAngularDeltaPage().doesElementExist(DELTA_CANCEL_BUTTON);
            sourceNavigateAngularDeltaPage().doesElementExist(DELTA_SUBMIT_BUTTON);
            sourceNavigateAngularDeltaPage().doesElementExist(DELTA_CLOSE_BUTTON);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularDeltaPage().click(CANCEL);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            //Clicking on confirm button and verifying display of confirmation popup
            assertThatIfConfirmationWindowAppeared();
            sourceNavigateAngularPage().clickConfirm();
            sourceNavigateAngularPage().waitForPageLoaded();
            assertThatIfConfirmationWindowDisappeared();
            sourceNavigateAngularPage().click(RENDITION);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularLeftSidePanePage().clear(String.format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID"));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        }

    }

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyDeltaPropertiesTabUserInterface() {
        String[] renditionStatus = new String[]{renditionUuid, "I300E7A00658F11E28B049F1D7A89B350"};
        //I300E7A00658F11E28B049F1D7A89B350
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        //Applying "APV" Filter for rendition status and clicking on first row
        for (int i = 0; i < renditionStatus.length; i++) {
            NavigatingAndVerifyingDeltaProperties(renditionStatus[i]);
        }

    }
}

