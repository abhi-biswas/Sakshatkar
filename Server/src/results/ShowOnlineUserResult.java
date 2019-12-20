package results;

import constants.ShowOnlineUserStatus;

import java.util.ArrayList;

public class ShowOnlineUserResult
{

    private ArrayList<OnlineUserDetail> online;
    private String username;
    private String ip;
    private ShowOnlineUserStatus showOnlineUserStatus;
    public ShowOnlineUserResult(ArrayList<OnlineUserDetail> online, String ip, String username, ShowOnlineUserStatus showOnlineUserStatus){
        this.showOnlineUserStatus=showOnlineUserStatus;
        this.online=online;
        this.ip=ip;
        this.username=username;
    }
    public ArrayList<OnlineUserDetail> getOnline(){return this.online;}
    public String getUsername(){return this.username;}
    public String getIp(){return this.ip;}
    public ShowOnlineUserStatus getShowOnlineUserStatus(){return  this.showOnlineUserStatus;}









}
