package me.parzibyte.sistemaventasspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String login() {
        logger.info("Accessing login page.");
        return "login/login"; // Asegúrate de que apunta a la plantilla dentro de la carpeta login
    }
}
