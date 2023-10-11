package com.chillarcards.campuswallet.BusTracking;

public class StopLocations {
    private String route;
    private String routeID;
    private String stopID;
    private String stop;
    private String inOrOut;
    private String latitude;
    private String longitude;
    private String busNo;



    private String Status;
    public StopLocations()
    {

    }
    public StopLocations(String route, String routeID, String stopID, String stop, String inOrOut, String latitude, String longitude, String busNo, String status) {
        super();
        this.route = route;
        this.routeID = routeID;
        this.stopID = stopID;
        this.stop = stop;
        this.inOrOut = inOrOut;
        this.latitude = latitude;
        this.longitude = longitude;
        this.busNo = busNo;
        this.Status=status;
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

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
