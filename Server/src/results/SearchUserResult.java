package results;

import constants.SearchUserStatus;

import java.io.Serializable;

public class SearchUserResult implements Serializable {


    ShortUserDetail shortUserDetail;
    SearchUserStatus searchUserStatus;
    public SearchUserResult(ShortUserDetail shortUserDetail, SearchUserStatus searchUserStatus)
    {
        this.shortUserDetail=shortUserDetail;
        this.searchUserStatus=searchUserStatus;
    }

    public SearchUserResult(SearchUserStatus searchUserStatus) {
        this.searchUserStatus = searchUserStatus;
    }

    public ShortUserDetail getShortUserDetail() {
        return shortUserDetail;
    }

    public SearchUserStatus getSearchUserStatus() {
        return searchUserStatus;
    }
}
