package com.thomsonreuters.codes.codesbench.quality.utilities.enums;

public enum ColumnIds {


    PREP_STARTED("prepStartedDate"),
    ATTORNEY_WORK_STARTED("attorneyWorkStartedDate");

    private final String id;



    ColumnIds(String id) {
        this.id=id;

    }
    public String getId()
    {
        return id;
    }



}
