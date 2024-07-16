/*Application.java
Programado por Edward Pinzon  
*/
package me.parzibyte.sistemaventasspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "me.parzibyte.sistemaventasspringboot",
    "me.parzibyte.conectorpluginv3",
    "me.parzibyte.prueba"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
