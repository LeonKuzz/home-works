package ru.fastdelivery.domain.delivery.shipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency,
        Destination destination,
        Departure departure
) {
    private static Logger logger = LoggerFactory.getLogger(Shipment.class.getName());
    public Weight weightAllPackages() {
        logger.debug("Расчёт общего веса груза");
        Weight totalWeight =packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
        String st = totalWeight.kilograms().setScale(3, RoundingMode.HALF_UP).toString();
        logger.debug("Общий вес груза - {} кг", st);
        return totalWeight;}
    public BigDecimal volumeAllPackages () {
        logger.debug("Расчёт общего объёма груза");
        BigDecimal totalVolume = packages.stream()
                .map(Pack::volume)
                .map(Volume::getCubicMeter)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String st = totalVolume.toString();
        logger.debug("Общий объём груза - {} кубических метров", st);
        return totalVolume;}

    @Override
    public String toString(){
        StringBuilder builder =new StringBuilder();
        builder.append("Упаковки: [ \n");
        packages.stream()
                .forEach(pack -> builder.append(pack.toString()).append("\n"));
        builder.append("Валюта: " + currency.getCode() + "\n");
        builder.append("Точка отправления: " + departure.getLatitude() + " : " + departure.getLongitude() + "\n");
        builder.append("Точка назначения: " + destination.getLatitude() + " : " + destination.getLongitude()+ "\n ]" );
        return builder.toString();
    }
}
