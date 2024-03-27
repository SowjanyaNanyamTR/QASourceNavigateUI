package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionClamshellMenuCreateBookmarkLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Creates a bookmark and checks if workflow completes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createBookmarkLegalTest()
    {
        String bookmarkName = "TEST Bookmark" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and create bookmark
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().clickCreateBookmarkButton();
        createBookmarkPage().setBookmarkName(bookmarkName);
        createBookmarkPage().clickSave();
        boolean bookmarkCreated = createBookmarkPage().checkBookmarkWasCreated();
        createBookmarkPage().clickClose();
        Assertions.assertTrue(bookmarkCreated,"Bookmark wasn't created when it should have been");

        //check bookmark is created an
        sourcePage().switchToSourceNavigatePage();
        homeMenu().goToMyHomePage();
        homePage().waitForPageLoaded();
        boolean isBookmarkDisplayed = homePage().bookmarkIsInMyBookmarks(bookmarkName);
        Assertions.assertTrue(isBookmarkDisplayed,"Bookmark is not displayed when it should be");

        //Verify Bookmark was deleted
        homePage().deleteBookmark(bookmarkName);
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,"Do you want to delete the bookmark named: " + bookmarkName + "?");
        Assertions.assertTrue(expectedAlertAppeared,"The expected alert didn't appear or was not accepted");
        homePage().waitForPageLoaded();
        boolean bookmarkDeleted = !(homePage().bookmarkIsInMyBookmarks(bookmarkName));
        Assertions.assertTrue(bookmarkDeleted,"The bookmark was deleted when it should have been");
    }

    @BeforeEach
    public void mockData()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
        renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(datapodObject != null)
        {
            sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
