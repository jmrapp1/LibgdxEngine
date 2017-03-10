package com.cc.engine.gpgs;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/9/2017
 * Time: 8:41 PM
 */
public interface IAchievementHandler {

    boolean isSignedInGSPS();

    void signInGSPS();

    void unlockAchievementGPGS(String achievementId);

    void showAhchievementsGPGS();

}
