package ua.abond.lab4.core.context.prop;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.Prop;
import ua.abond.lab4.core.annotation.Value;

@Component
@Prop("prop.test.properties")
public class PropTestClass {
    @Value("test")
    private String string;

    public String getString() {
        return string;
    }
}
