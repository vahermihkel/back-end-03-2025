package ee.mihkel.veebipood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableScheduling
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class VeebipoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeebipoodApplication.class, args);
	}

}

// 1. Controller, Entity, Repository, andmebaasiga ühendamine
// 2. create-drop, @ColumnDefault, tabelite sidumine: @ManyToOne, custom repo päringud
// 3. 24.03 E kell 9.00
// 4. 26.03 K kell 9.00
// 5. 31.03 E kell 14.00
// 6. 03.04 K kell 14.00 Angulari algus
// 7. 07.04 E kell 14.00
// 8. 10.04 N kell 14.00
// 14.02.05
// 15.05.05 E kell 14.00
// 16.08.05 N kell 10.15-12.00
//16b.10.05 L kell 10.00-11.15
// 17.15.05 N kell 09 .00
// 18.26.05 E kell 14.00-15.30

// Shell Scripting+++
// DTO+++
// ModelMapper+++
// Bean+++
// RestTemplate+++
// 		- Tarnija+++
//		- Pakiautomaat+++
//		- Makse+++
// CRON (e-mail - tule oma tellimusele järele) (meeldetuletus - sul on homme broneering)
// Emaili saatmine
// Cache
// Profiles -> testkeskkond, live keskkond+++
// application.properties muutujad+++

// 22.mai --> allkirjalehele viimane

// cvkeskus, cvonline --> jäämäe pealne tipp
// LinkedIn
// MeetFrank