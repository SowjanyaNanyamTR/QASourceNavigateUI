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
import java.util.ArrayList;
import java.util.Arrays;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.SECTION_PROPERTIES;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements.FIND_TEXT_FIELD_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;

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

    @Test
    @EDGE
    @LEGAL
    @LOG
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
}