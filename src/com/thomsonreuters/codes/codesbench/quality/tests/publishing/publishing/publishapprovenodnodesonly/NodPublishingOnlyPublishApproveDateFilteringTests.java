package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovenodnodesonly;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

public class NodPublishingOnlyPublishApproveDateFilteringTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY: HALCYONST-12924 <br>
     * SUMMARY: This test verifies that on the publishing ui's where we can set a dilter dat, that all of the filter options work as they are expected to <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void publishingUiDatesFilteringTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String codeTitle = "TITLE XI";
        String keyword = "BL ";

        //This just gets the orginal values for clean up

        String contentUuid1 = "I365CD2101CEA11DC868395DC84A0FC63";
        String nodeValue1 = HierarchyDatabaseUtils.getNodeValue(contentUuid1, contentSetIowa, uatConnection);
        String orignalStartDate1 = "";
        String orignalEndDate1 =  "";
        String orignalModifiedDate1 = "17-Jun-07";

        String contentUuid2 = "I3682F7B01CEA11DC868395DC84A0FC63";
        String nodeValue2 = HierarchyDatabaseUtils.getNodeValue(contentUuid2, contentSetIowa, uatConnection);
        String orignalStartDate2 = "";
        String orignalEndDate2 = "";
        String orignalModifiedDate2 = "17-Jun-07";

        String contentUuid3 = "I36A59AE01CEA11DC868395DC84A0FC63";
        String nodeValue3 =  HierarchyDatabaseUtils.getNodeValue(contentUuid3, contentSetIowa, uatConnection);
        String orignalStartDate3 = "";
        String orignalEndDate3 = "";
        String orignalModifiedDate3 = "17-Jun-07";

        //set to our values to make sure test always stays the same
        //mock up for node 1 -
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid1, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid1, "11-Sep-01", uatConnection);
        HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid1, "21-Dec-12", uatConnection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUuid1, "21-Dec-12", uatConnection);

        //mock up for node 2-
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid2, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid2, "20-Jan-20", uatConnection);
        HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid2, "25-May-20", uatConnection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUuid2, "25-May-20", uatConnection);

        //mock up for node 3-
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid3, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid3, "25-May-08", uatConnection);
        HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid3, "2-Jan-21", uatConnection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUuid3, "2-Jan-21", uatConnection);

        try {
            homePage().goToHomePage();
            loginPage().logIn();

            //go to publishing -> publishing -> Nod Publishing Only
            boolean nodPUblishingOnlySelectionPageLoaded = publishingMenu().goToPublishApproveNodNodesOnly();
            Assertions.assertTrue(nodPUblishingOnlySelectionPageLoaded, "one step approval page loaded");
            gridPage().waitForGridLoaded();

            //Start Date -> Equals
            gridHeaderPage().openMenuForCodeColumn();
            gridHeaderFiltersPage().setFilterValue(codeTitle);

            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            gridPage().click("//button/span[text()='Columns']");
            gridHeaderPage().filterForColumnAndSelect("Start Date");
            gridHeaderPage().filterForColumnAndSelect("End Date");
            gridPage().click("//button/span[text()='Columns']");
            gridHeaderPage().openMenuForStartDate();
            gridHeaderFiltersPage().setDateFilterOption("Equals");
            gridHeaderFiltersPage().setFilterDateValue("09/11/2001");

            boolean nodeValue1IsInGridAfterEquals = gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue1);
            boolean nodeValue2IsNotInGridAfterEquals = !gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue2);
            boolean nodeValue3IsNotInGridAfterEquals = !gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue3);

            //End Date -> Not Equals
            toolbarPage().clickClearFiltersAndSorts();

            gridHeaderPage().openMenuForCodeColumn();
            gridHeaderFiltersPage().setFilterValue(codeTitle);

            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            gridHeaderPage().openMenuForEndDate();
            gridHeaderFiltersPage().setDateFilterOption("Not equal");
            gridHeaderFiltersPage().setFilterDateValue("1/2/2021");

            boolean nodeValue1IsInGridAfterNotEquals = gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue1);
            boolean nodeValue2IsNotInGridAfterNotEquals = gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue2);
            boolean nodeValue3IsNotInGridAfterNotEquals = !gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue3);

            //Start Date -> After
            toolbarPage().clickClearFiltersAndSorts();

            gridHeaderPage().openMenuForCodeColumn();
            gridHeaderFiltersPage().setFilterValue(codeTitle);

            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            gridHeaderPage().openMenuForStartDate();
            gridHeaderFiltersPage().setDateFilterOption("After");
            gridHeaderFiltersPage().setFilterDateValue("05/25/2008");

            boolean nodeValue1IsInGridAfterAfter = !gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue1);
            boolean nodeValue2IsNotInGridAfterAfter = gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue2);
            boolean nodeValue3IsNotInGridAfterAfter = !gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue3);

            //End Date -> before
            toolbarPage().clickClearFiltersAndSorts();
            gridHeaderPage().openMenuForCodeColumn();
            gridHeaderFiltersPage().setFilterValue(codeTitle);

            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            gridHeaderPage().openMenuForEndDate();
            gridHeaderFiltersPage().setDateFilterOption("Before");
            gridHeaderFiltersPage().setFilterDateValue("01/02/2021");

            boolean nodeValue1IsInGridAfterBefore = gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue1);
            boolean nodeValue2IsNotInGridAfterBefore = gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue2);
            boolean nodeValue3IsNotInGridAfterBefore = !gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue3);

            //Modified -> in between
            toolbarPage().clickClearFiltersAndSorts();

            gridHeaderPage().openMenuForCodeColumn();
            gridHeaderFiltersPage().setFilterValue(codeTitle);

            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            gridHeaderPage().openMenuForModifiedDateColumn();
            gridHeaderFiltersPage().setDateFilterOption("In between");
            gridHeaderFiltersPage().setInBetweenInputDateFields("05/23/2020", "05/28/2020");

            boolean nodeValue1IsInGridAfterBetween = !gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue1);
            boolean nodeValue2IsNotInGridAfterBetween = gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue2);
            boolean nodeValue3IsNotInGridAfterBetween = !gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue3);

            Assertions.assertAll
                    (
                            () -> Assertions.assertTrue(nodeValue1IsInGridAfterEquals, "node 1 should be in the grid After setting dropdown to Equals"),
                            () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterEquals, "node 2 should not be in the grid After setting dropdown to Equals"),
                            () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterEquals, "node 3 should not be in the grid After setting dropdown to Equals"),

                            () -> Assertions.assertTrue(nodeValue1IsInGridAfterNotEquals, "node 1 should not be in the grid After setting dropdown to Not Equals "),
                            () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterNotEquals, "node 2 should be in the grid After setting dropdown to Not Equals "),
                            () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterNotEquals, "node 3 should be in the grid After setting dropdown to Not Equals "),

                            () -> Assertions.assertTrue(nodeValue1IsInGridAfterAfter, "node 1 should be in the grid After setting dropdown to After"),
                            () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterAfter, "node 2 should not be in the grid After setting dropdown to After"),
                            () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterAfter, "node 3 should be in the grid After setting dropdown to After"),

                            () -> Assertions.assertTrue(nodeValue1IsInGridAfterBefore, "node 1 should not be in the grid After setting dropdown to Before"),
                            () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterBefore, "node 2 should not be in the grid After setting dropdown to Before"),
                            () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterBefore, "node 3 should not be in the grid After setting dropdown to Before"),

                            () -> Assertions.assertTrue(nodeValue1IsInGridAfterBetween, "node 1 should be in the grid After setting dropdown to Between"),
                            () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterBetween, "node 2 should not be in the grid After setting dropdown to Between"),
                            () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterBetween, "node 3 should not be in the grid After setting dropdown to Between")
                    );
        }
        finally {
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid1, orignalStartDate1, uatConnection);
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid1, orignalEndDate1, uatConnection);
            HierarchyDatabaseUtils.updateModifiedDate(contentUuid1, orignalModifiedDate1, uatConnection);

            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid2, orignalStartDate2, uatConnection);
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid2, orignalEndDate2, uatConnection);
            HierarchyDatabaseUtils.updateModifiedDate(contentUuid2, orignalModifiedDate2, uatConnection);

            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid3, orignalStartDate3, uatConnection);
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid3, orignalEndDate3, uatConnection);
            HierarchyDatabaseUtils.updateModifiedDate(contentUuid3, orignalModifiedDate3, uatConnection);
        }
    }
}