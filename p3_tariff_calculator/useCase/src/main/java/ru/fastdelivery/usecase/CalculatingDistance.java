package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@RequiredArgsConstructor
public class CalculatingDistance  {
    static Logger logger = LoggerFactory.getLogger(CalculatingDistance.class.getName());

    public double getDistanceFromLatLonInKm(Shipment shipment) { //Double lat1, Double lon1, Double lat2,Double lon2
        logger.trace("Расчёт расстояния доставки");
        Double lat1 = shipment.departure().getLatitude();
        Double lon1 = shipment.departure().getLongitude();
        Double lat2 = shipment.destination().getLatitude();
        Double lon2 = shipment.destination().getLongitude();
        var R = 6371; // Radius of the earth in km
        var dLat = deg2rad(lat2-lat1);  // deg2rad below
        var dLon = deg2rad(lon2-lon1);
        var a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c; // Distance in km

        return d;
    }

    private double deg2rad(Double deg) {
        return deg * (Math.PI/180);
    }
}
