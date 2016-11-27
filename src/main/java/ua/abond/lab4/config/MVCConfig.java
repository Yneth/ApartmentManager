package ua.abond.lab4.config;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.ComponentScan;

@Component
@ComponentScan({"ua.abond.lab4.service.impl", "ua.abond.lab4.web"})
public class MVCConfig {

}
