package ua.abond.lab4.config.core.context.prop;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Prop;
import ua.abond.lab4.config.core.annotation.Value;

@Component
@Prop("prop.test.properties")
public class PropTestClass {
    @Value("test")
    private String string;

    public String getString() {
        return string;
    }
}
