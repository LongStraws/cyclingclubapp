package com.example.cyclingclubapp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class GameDetails implements Serializable {

    public String curUserName;

    public String gameId;

    public String gameDescribe;

    public String gameDistance;

    public String gameHeight;
    public String gameLandMark;

    public String gameLevel;
    public String gameRouterDes;

    public String gameCost;

    public String gameParticipantNum;



    public String eventName;
    public String eventId;

    public List<User> registerUsers;


    public GameDetails(String curUserId,String gameId, String gameDescribe, String gameDistance,
                       String gameHeight, String gameLandMark, String gameLevel, String gameRouterDes,
                       String gameCost, String gameParticipantNum, String eventName,String eventId, List<User> registerUsers) {
        this.curUserName = curUserId;
        this.gameId = gameId;
        this.gameDescribe = gameDescribe;
        this.gameDistance = gameDistance;
        this.gameHeight = gameHeight;
        this.gameLandMark = gameLandMark;
        this.gameLevel = gameLevel;
        this.gameRouterDes = gameRouterDes;
        this.gameCost = gameCost;
        this.gameParticipantNum = gameParticipantNum;

        this.eventName = eventName;
        this.eventId = eventId;
        this.registerUsers = registerUsers;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public GameDetails() {
    }

    public String getCurUserName() {
        return curUserName;
    }

    public void setCurUserName(java.lang.String curUserName) {
        this.curUserName = curUserName;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameDescribe() {
        return gameDescribe;
    }

    public void setGameDescribe(String gameDescribe) {
        this.gameDescribe = gameDescribe;
    }

    public String getGameDistance() {
        return gameDistance;
    }

    public void setGameDistance(String gameDistance) {
        this.gameDistance = gameDistance;
    }

    public String getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(String gameHeight) {
        this.gameHeight = gameHeight;
    }

    public String getGameLandMark() {
        return gameLandMark;
    }

    public void setGameLandMark(String gameLandMark) {
        this.gameLandMark = gameLandMark;
    }

    public String getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }

    public String getGameRouterDes() {
        return gameRouterDes;
    }

    public void setGameRouterDes(String gameRouterDes) {
        this.gameRouterDes = gameRouterDes;
    }

    public String getGameCost() {
        return gameCost;
    }

    public void setGameCost(String gameCost) {
        this.gameCost = gameCost;
    }

    public String getGameParticipantNum() {
        return gameParticipantNum;
    }

    public void setGameParticipantNum(String gameParticipantNum) {
        this.gameParticipantNum = gameParticipantNum;
    }

    public void setRegisterUsers(List<User> registerUsers){
        this.registerUsers = registerUsers;
    }

    public List<User> getRegisterUsers(){
        return registerUsers;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameDetails)) return false;
        GameDetails that = (GameDetails) o;
        return Objects.equals(getCurUserName(), that.getCurUserName())&& Objects.equals(getGameDescribe(), that.getGameDescribe()) && Objects.equals(getGameDistance(), that.getGameDistance()) && Objects.equals(getGameHeight(), that.getGameHeight()) && Objects.equals(getGameLandMark(), that.getGameLandMark()) && Objects.equals(getGameLevel(), that.getGameLevel()) && Objects.equals(getGameRouterDes(), that.getGameRouterDes()) && Objects.equals(getGameCost(), that.getGameCost()) && Objects.equals(getGameParticipantNum(), that.getGameParticipantNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurUserName(), getGameDescribe(), getGameDistance(), getGameHeight(), getGameLandMark(), getGameLevel(), getGameRouterDes(), getGameCost(), getGameParticipantNum());
    }
}
