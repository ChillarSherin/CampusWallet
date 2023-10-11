package com.chillarcards.campuswallet.BusTracking;

public class StudentStopDetails {

    private String route,  routeID,  startstopID,  startstop,  startstopOrder,  startlatitude,  startlongitude,
    starttime,  endstopID,  endstop,  endstopOrder,  endlatitude,  endlongitude,  endtime;

    public boolean isInOut() {
        return InOut;
    }

    public void setInOut(boolean inOut) {
        InOut = inOut;
    }

    private boolean InOut;
    public StudentStopDetails(boolean inOut,String route, String routeID, String startstopID,
                              String startstop, String startlatitude, String startlongitude,
                              String starttime, String endstopID, String endstop, String endlatitude,
                              String endlongitude, String endtime) {
      // super();
        this.InOut=inOut;
        this.route = route;
        this.routeID = routeID;
        this.startstopID = startstopID;
        this.startstop = startstop;
        this.startstopOrder = startstopOrder;
        this.startlatitude = startlatitude;
        this.startlongitude = startlongitude;
        this.starttime = starttime;
        this.endstopID = endstopID;
        this.endstop = endstop;
        this.endstopOrder = endstopOrder;
        this.endlatitude = endlatitude;
        this.endlongitude = endlongitude;
        this.endtime = endtime;
    }
    public StudentStopDetails()
    {

    }
    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getStartstopID() {
        return startstopID;
    }

    public void setStartstopID(String startstopID) {
        this.startstopID = startstopID;
    }

    public String getStartstop() {
        return startstop;
    }

    public void setStartstop(String startstop) {
        this.startstop = startstop;
    }

    public String getStartstopOrder() {
        return startstopOrder;
    }

    public void setStartstopOrder(String startstopOrder) {
        this.startstopOrder = startstopOrder;
    }

    public String getStartlatitude() {
        return startlatitude;
    }

    public void setStartlatitude(String startlatitude) {
        this.startlatitude = startlatitude;
    }

    public String getStartlongitude() {
        return startlongitude;
    }

    public void setStartlongitude(String startlongitude) {
        this.startlongitude = startlongitude;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndstopID() {
        return endstopID;
    }

    public void setEndstopID(String endstopID) {
        this.endstopID = endstopID;
    }

    public String getEndstop() {
        return endstop;
    }

    public void setEndstop(String endstop) {
        this.endstop = endstop;
    }

    public String getEndstopOrder() {
        return endstopOrder;
    }

    public void setEndstopOrder(String endstopOrder) {
        this.endstopOrder = endstopOrder;
    }

    public String getEndlatitude() {
        return endlatitude;
    }

    public void setEndlatitude(String endlatitude) {
        this.endlatitude = endlatitude;
    }

    public String getEndlongitude() {
        return endlongitude;
    }

    public void setEndlongitude(String endlongitude) {
        this.endlongitude = endlongitude;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

}
