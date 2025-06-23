package ch.bbw.bloggingplattform;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.bbw.bloggingplattform.blogs.Blog;
import ch.bbw.bloggingplattform.blogs.BlogRepository;
import ch.bbw.bloggingplattform.user.BlogUser;
import ch.bbw.bloggingplattform.user.UserRepository;

@SpringBootApplication
public class BloggingplattformApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloggingplattformApplication.class, args);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 nicht verfügbar", e);
        }
    }

    @Bean
    public CommandLineRunner init(BlogRepository blogRepositoryDb, UserRepository userRepository) {
        return args -> {
            BlogUser user1 = new BlogUser("Loris", hashPassword("123"));
            BlogUser user2 = new BlogUser("Joris", hashPassword("password1234"));
            BlogUser user3 = new BlogUser("Anna", hashPassword("securePass1"));
            BlogUser user4 = new BlogUser("Ben", hashPassword("qwertz987"));
            BlogUser user5 = new BlogUser("Carla", hashPassword("pass1234"));
            BlogUser user6 = new BlogUser("David", hashPassword("admin2024"));
            BlogUser user7 = new BlogUser("Elena", hashPassword("mySecret!"));
            BlogUser user8 = new BlogUser("Felix", hashPassword("testUser77"));
            BlogUser user9 = new BlogUser("Greta", hashPassword("pw123456"));
            BlogUser user10 = new BlogUser("Hannes", hashPassword("letmein99"));
            BlogUser user11 = new BlogUser("Irina", hashPassword("sunshine55"));
            BlogUser user12 = new BlogUser("Jonas", hashPassword("welcome@1"));
            BlogUser user13 = new BlogUser("Miriam", hashPassword("mySecretPasswort1634?!"));
            BlogUser user14 = new BlogUser("Markus", hashPassword("WinterthurerGhost@1"));
            BlogUser user15 = new BlogUser("Silvana", hashPassword("4667hd@1"));
            BlogUser user16 = new BlogUser("Valentin", hashPassword("qwerty101213"));
            BlogUser user17 = new BlogUser("TypeLoopOfficial", hashPassword("1234"));

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);
            userRepository.save(user9);
            userRepository.save(user10);
            userRepository.save(user11);
            userRepository.save(user12);
            userRepository.save(user13);
            userRepository.save(user14);
            userRepository.save(user15);
            userRepository.save(user16);
            userRepository.save(user17);

            blogRepositoryDb.save(new Blog("Kaffetrinken mit Mama",
                    "Heute Morgen bin ich zusammen mit meiner Mutter im Hardrockkaffee Berlin Mittagessen gegangen! Es hat mich sehr gefreut dich wiedereinmal zu sehen, Mama!",
                    user7));
            blogRepositoryDb.save(new Blog("Einkaufen gehen mit Wuffi",
                    "Heute bin ich gemeinsam mit meinem Hund im Migros Bern einkaufen gegangen!",
                    user2));

            blogRepositoryDb.save(new Blog("Spaziergang im Park",
                    "Heute war ich mit meiner Schwester im Englischen Garten in München spazieren. Die Sonne hat so schön geschienen!",
                    user3));

            blogRepositoryDb.save(new Blog("Kuchen backen mit Oma",
                    "Ich habe heute mit meiner Großmutter einen Apfelkuchen gebacken. Es war wie früher – richtig schön!",
                    user4));

            blogRepositoryDb.save(new Blog("Abendessen mit Freunden",
                    "Gestern Abend war ich mit meinen alten Schulfreunden in einem kleinen Restaurant in Köln essen. Die Gespräche waren wunderbar!",
                    user1));

            blogRepositoryDb.save(new Blog("Museumstag in Dresden",
                    "Ich habe heute die Gemäldegalerie Alte Meister besucht – die Ausstellung war beeindruckend!",
                    user5));

            blogRepositoryDb.save(new Blog("Yoga im Stadtpark",
                    "Der frühe Morgen im Stadtpark ist perfekt für Yoga. Die Ruhe, das Zwitschern der Vögel – herrlich!",
                    user6));

            blogRepositoryDb.save(new Blog("Wanderung in den Alpen",
                    "Ein Tag in der Natur! Ich war heute mit meiner Cousine in den Berner Alpen wandern. Die Aussicht war traumhaft!",
                    user9));

            blogRepositoryDb.save(new Blog("Kaffee und Buch im Café",
                    "Heute habe ich mir im Lieblingscafé ein Buch geschnappt und einen Cappuccino getrunken. Entspannung pur!",
                    user2));

            blogRepositoryDb.save(new Blog("Spieleabend mit der Familie",
                    "Ein richtig lustiger Abend: Brettspiele, Pizza und ganz viel Lachen mit der Familie!",
                    user10));

            blogRepositoryDb.save(new Blog("Flohmarktbesuch am Sonntag",
                    "Ich habe heute auf dem Flohmarkt ein paar echte Schätze entdeckt – darunter ein antikes Teeservice!",
                    user7));

            blogRepositoryDb.save(new Blog("Tag am See",
                    "Ich war heute am Wannsee schwimmen. Das Wasser war erfrischend und das Picknick danach war super lecker!",
                    user8));

        };
    }
}
