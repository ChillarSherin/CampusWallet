package com.chillarcards.campuswallet.BusTracking;

public class InOutModel {
    public StudentStopDetails getGoingIn() {
        return GoingIn;
    }

    public void setGoingIn(StudentStopDetails goingIn) {
        GoingIn = goingIn;
    }

    public StudentStopDetails getGoingOut() {
        return GoingOut;
    }

    public void setGoingOut(StudentStopDetails goingOut) {
        GoingOut = goingOut;
    }

    private StudentStopDetails GoingIn,GoingOut;

    public InOutModel(StudentStopDetails goingIn, StudentStopDetails goingOut) {
        GoingIn = goingIn;
        GoingOut = goingOut;
    }

    public InOutModel() {
    }
}
