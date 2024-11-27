package ru.skillbox.currency.exchange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.skillbox.currency.exchange.controller.CurrencyController;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;
import ru.skillbox.currency.exchange.service.CurrencyService;
import ru.skillbox.currency.exchange.xml.JAXBXMLHandler;
import ru.skillbox.currency.exchange.xmlobj.ValCurs;
import ru.skillbox.currency.exchange.xmlobj.Valute;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		CurrencyService service = context.getBean(CurrencyService.class);
		service.updateDatabase();
		System.out.println(Instant.now());
		System.out.println(service.address);
//		File file = new File("src/main/resources/data/information.xml");
//		updateDatabase(file);
		new Thread(()-> {
			while (true) {
				try {
					Thread.sleep(60 * 60 * 1000); //min * sec * millis
					service.updateDatabase();
					System.out.println(Instant.now());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}).run();








	}
	public static File getCBRCurrenciesList(){
		try {
			URL url = new URL("https://cbr.ru/scripts/XML_daily.asp");
			String fileName = "information.xml";
			Path outputPath = Path.of("src/main/resources/data/" + fileName);

			try (InputStream in = url.openStream()) {
				Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
			}
			File file = new File("src/main/resources/data/information.xml");
			List<Valute> valutes = JAXBXMLHandler.unmarshal(file);

//			for (Valute valute : valutes) {
//				System.out.println(valute.toString());
//			}
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


//	public ValCurs unmarshall() throws JAXBException, IOException {
//		JAXBContext context = JAXBContext.newInstance(ValCurs.class);
//		return (ValCurs) context.createUnmarshaller()
//				.unmarshal(new FileReader("./book.xml"));
//
//
//
//public static List<Valute> unmarshal(File importFile) throws JAXBException {
//		JAXBContext context = JAXBContext.newInstance(ValCurs.class);
//		Unmarshaller um = context.createUnmarshaller();
//		ValCurs curs = (ValCurs) um.unmarshal(importFile);
//
//	return curs.getCurrencies();
//}

}
