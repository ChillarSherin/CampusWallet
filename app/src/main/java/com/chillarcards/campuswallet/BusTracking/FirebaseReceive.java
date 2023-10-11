package com.chillarcards.campuswallet.BusTracking;

public class FirebaseReceive {
    String Uid,Longitude,Lattitude,Bus_ID,RouteID;

    public FirebaseReceive() {
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLattitude() {
        return Lattitude;
    }

    public void setLattitude(String lattitude) {
        Lattitude = lattitude;
    }

    public String getBus_ID() {
        return Bus_ID;
    }

    public void setBus_ID(String bus_ID) {
        Bus_ID = bus_ID;
    }

    public String getRouteID() {
        return RouteID;
    }

    public void setRouteID(String routeID) {
        RouteID = routeID;
    }



    public FirebaseReceive(String uid, String longitude, String lattitude, String busID, String routeID) {
        Uid = uid;
        Longitude = longitude;
        Lattitude = lattitude;
        Bus_ID = busID;
        RouteID = routeID;
    }
}
