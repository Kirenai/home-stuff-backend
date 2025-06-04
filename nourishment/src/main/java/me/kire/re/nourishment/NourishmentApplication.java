package me.kire.re.nourishment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "me.kire.re"
        }
)
public class NourishmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(NourishmentApplication.class, args);
    }
}
