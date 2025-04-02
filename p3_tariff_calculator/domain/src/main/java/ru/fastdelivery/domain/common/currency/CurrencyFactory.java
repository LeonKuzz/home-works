package ru.fastdelivery.domain.common.currency;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Создание валюты с проверками
 */
@RequiredArgsConstructor
public class CurrencyFactory {

    private static Logger logger = LoggerFactory.getLogger(CurrencyFactory.class.getName());

    private final CurrencyPropertiesProvider propertiesProvider;

    public Currency create(String code) {
        logger.trace("получена валюта {}", code);
        if (code == null || !propertiesProvider.isAvailable(code)) {
            logger.warn("Currency code {} contains not available value", code);
            throw new IllegalArgumentException("Currency code contains not available value");
        }
        logger.trace("{} есть в списке. Валюта создана", code);
        return new Currency(code);
    }
}
