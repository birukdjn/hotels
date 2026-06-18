package com.gobet.hotels.config;

import com.gobet.hotels.entity.*;
import com.gobet.hotels.entity.User;
import com.gobet.hotels.enums.UserRole;
import com.gobet.hotels.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HotelRepository hotelRepository;
    private final RegionRepository regionRepository;
    private final ZoneRepository zoneRepository;
    private final CityRepository cityRepository;

    @Bean
    public CommandLineRunner seedAll() {
        return args -> {
            seedHotelCities();
            seedUsers();
            seedLocationMetadata();
        };
    }

    // ─────────────────────────────────────────
    // Assign cities to pre-seeded hotels
    // ─────────────────────────────────────────
    private void seedHotelCities() {
        List<Hotel> hotels = hotelRepository.findAll();
        int index = 0;
        String[] cities = {"Hossana", "Addis Ababa", "Hawassa", "Bahir Dar", "Gondar"};
        for (Hotel hotel : hotels) {
            if (hotel.getCity() == null || hotel.getCity().trim().isEmpty()) {
                hotel.setCity(cities[index % cities.length]);
                hotelRepository.save(hotel);
                index++;
            }
        }
    }

    // ─────────────────────────────────────────
    // Seed default staff accounts
    // ─────────────────────────────────────────
    private void seedUsers() {
        createUserIfNotExists("Super",   "Admin",   "superadmin@gobet.org",   UserRole.SUPER_ADMIN);
        createUserIfNotExists("Hotel",   "Admin",   "hoteladmin@gobet.org",   UserRole.HOTEL_ADMIN);
        createUserIfNotExists("Front",   "Desk",    "receptionist@gobet.org", UserRole.RECEPTIONIST);
        createUserIfNotExists("Finance", "Officer", "accountant@gobet.org",   UserRole.ACCOUNTANT);
    }

    // ─────────────────────────────────────────
    // Seed Ethiopian Regions → Zones → Cities
    // ─────────────────────────────────────────
    private void seedLocationMetadata() {

        // Only seed once — check if regions already exist
        if (!regionRepository.findByDeletedFalse().isEmpty()) return;

        // Map: Region → Map<Zone → List<City>>
        Map<String, Map<String, List<String>>> data = new LinkedHashMap<>();

        data.put("Addis Ababa", Map.of(
            "Addis Ababa City", List.of("Arada", "Bole", "Kirkos", "Kolfe Keranio", "Lideta", "Nifas Silk-Lafto", "Akaky Kaliti", "Gulele", "Yeka", "Lemi Kura")
        ));

        data.put("Oromia", new LinkedHashMap<>() {{
            put("West Hararghe Zone",      List.of("Chiro", "Gelemso", "Miesso"));
            put("East Hararghe Zone",      List.of("Harar", "Dire Dawa", "Babile"));
            put("West Shewa Zone",         List.of("Ambo", "Bako", "Gedo"));
            put("North Shewa Zone",        List.of("Adama", "Asella", "Mojo", "Bishoftu"));
            put("South West Shewa Zone",   List.of("Woliso", "Butajira"));
            put("Jimma Zone",              List.of("Jimma", "Agaro", "Serbo"));
            put("Arsi Zone",               List.of("Asella", "Robe"));
            put("Borena Zone",             List.of("Yabelo", "Moyale"));
        }});

        data.put("Amhara", new LinkedHashMap<>() {{
            put("North Gondar Zone",       List.of("Gondar", "Debark", "Shire"));
            put("South Gondar Zone",       List.of("Debre Tabor", "Addis Zemen"));
            put("North Wollo Zone",        List.of("Woldiya", "Lalibela", "Kobo"));
            put("South Wollo Zone",        List.of("Dessie", "Kombolcha", "Haik"));
            put("West Gojam Zone",         List.of("Bahir Dar", "Debre Markos", "Finote Selam"));
            put("East Gojam Zone",         List.of("Debre Markos", "Gonder Town"));
            put("Awi Zone",                List.of("Injibara", "Dangila"));
        }});

        data.put("SNNPR", new LinkedHashMap<>() {{
            put("Sidama Zone",             List.of("Hawassa", "Yirgalem", "Leku"));
            put("Wolaita Zone",            List.of("Wolaita Sodo", "Areka", "Boditi"));
            put("Hadiya Zone",             List.of("Hossana", "Shashogo", "Soro"));
            put("Gurage Zone",             List.of("Wolkite", "Butajira", "Imdibir"));
            put("Kaffa Zone",              List.of("Bonga", "Chena"));
            put("Dawro Zone",              List.of("Tarcha", "Mareka"));
            put("Konso Zone",              List.of("Konso", "Karako"));
        }});

        data.put("Tigray", new LinkedHashMap<>() {{
            put("Central Tigray Zone",     List.of("Mekele", "Adwa", "Axum"));
            put("Eastern Tigray Zone",     List.of("Adigrat", "Wukro"));
            put("Western Tigray Zone",     List.of("Humera", "Sheraro"));
            put("Southern Tigray Zone",    List.of("Maychew", "Korem"));
        }});

        data.put("Somali", new LinkedHashMap<>() {{
            put("Fafan Zone",              List.of("Jijiga", "Babile"));
            put("Liban Zone",              List.of("Gode", "Dollo Ado"));
            put("Sitti Zone",              List.of("Shinile", "Dire Dawa (border)"));
        }});

        data.put("Afar", new LinkedHashMap<>() {{
            put("Zone 1 (Awsi Rasu)",      List.of("Logiya", "Mille"));
            put("Zone 3 (Gabi Rasu)",      List.of("Gewane", "Amibara"));
            put("Zone 5 (Hari Rasu)",      List.of("Samara", "Asaita"));
        }});

        data.put("Benishangul-Gumuz", new LinkedHashMap<>() {{
            put("Metekel Zone",            List.of("Gilgel Beles", "Mandura"));
            put("Asosa Zone",              List.of("Asosa", "Bambasi"));
            put("Kamashi Zone",            List.of("Kamashi", "Begi"));
        }});

        data.put("Gambella", new LinkedHashMap<>() {{
            put("Anuak Zone",              List.of("Gambella Town", "Itang"));
            put("Nuer Zone",               List.of("Akobo", "Wanthoa"));
        }});

        data.put("Harari", Map.of(
            "Harari City Administration",  List.of("Harar", "Erer", "Kombolcha")
        ));

        data.put("Dire Dawa", Map.of(
            "Dire Dawa City Administration", List.of("Dire Dawa", "Gende Kore", "Goro")
        ));

        data.put("Sidama", Map.of(
            "Sidama Zone", List.of("Hawassa", "Yirgalem", "Aleta Wendo", "Dale", "Boricha")
        ));

        data.put("Southwest Ethiopia", new LinkedHashMap<>() {{
            put("Keffa Zone",              List.of("Bonga", "Chena", "Decha"));
            put("Bench Sheko Zone",        List.of("Mizan-Aman", "Guraferda"));
            put("Dawro Zone",              List.of("Tarcha", "Mareka", "Loma"));
        }});

        // ── Persist the data ──
        for (Map.Entry<String, Map<String, List<String>>> regionEntry : data.entrySet()) {
            Region region = new Region();
            region.setName(regionEntry.getKey());
            region = regionRepository.save(region);

            for (Map.Entry<String, List<String>> zoneEntry : regionEntry.getValue().entrySet()) {
                Zone zone = new Zone();
                zone.setName(zoneEntry.getKey());
                zone.setRegion(region);
                zone = zoneRepository.save(zone);

                for (String cityName : zoneEntry.getValue()) {
                    City city = new City();
                    city.setName(cityName);
                    city.setZone(zone);
                    cityRepository.save(city);
                }
            }
        }

        System.out.println("✅ Location metadata seeded: Regions, Zones, Cities");
    }

    // ─────────────────────────────────────────
    // Helper
    // ─────────────────────────────────────────
    private void createUserIfNotExists(String firstName, String lastName, String email, UserRole role) {
        if (userRepository.findByEmail(email).isPresent()) return;

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode("Password@123"));
        user.setRole(role);
        userRepository.save(user);
    }
}