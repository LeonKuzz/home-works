package ru.skillbox.currency.exchange.xmlobj;

import liquibase.pro.packaged.L;
import lombok.*;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;

import javax.xml.bind.annotation.*;
//import jakarta.xml.bind.annotation.*;

@XmlType (name =  "Valute")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Getter
@Setter

public class Valute {
//<Valute ID="R01020A">
//<NumCode>944</NumCode>
//<CharCode>AZN</CharCode>
//<Nominal>1</Nominal>
//    <Name>Азербайджанский манат</Name>
//<Value>60,3389</Value>
//<VunitRate>60,3389</VunitRate>
//</Valute>

    @XmlTransient
    private CurrencyRepository repository;

    @XmlAttribute (name = "ID")
    private String id;

    @XmlElement (name = "NumCode")
    private Long numCode;

    @XmlElement (name = "CharCode")
    private String charCode;

    @XmlElement (name = "Nominal")
    private Long nominal;

    @XmlElement (name = "Name")
    private String name;

    @XmlElement (name = "Value")
    private String value;

    @XmlElement (name = "VunitRate")
    private Double vunitRate;

    @Override
    public String toString () {
        return name + charCode + numCode + value + nominal;
    }
}
