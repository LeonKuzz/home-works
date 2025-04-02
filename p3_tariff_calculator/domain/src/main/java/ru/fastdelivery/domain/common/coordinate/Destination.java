package ru.fastdelivery.domain.common.coordinate;

public record Destination (Latitude latitude, Longitude longitude) {
    public double getLatitude() {
        return latitude.latitude();
    }
    public double getLongitude() {
        return longitude.longitude();
    }
}
