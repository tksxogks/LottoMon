package kr.co.htssoft.lottomon;

public class MapItem {

    String storeName;
    String phoneNumber;
    String address;
    Double lat;
    Double lon;

    public MapItem(String storeName, String phoneNumber, String address, Double lat, Double lon) {
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
