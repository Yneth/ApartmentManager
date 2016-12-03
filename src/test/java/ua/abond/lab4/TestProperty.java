package ua.abond.lab4;

import org.junit.Test;

import java.util.Properties;

public class TestProperty {

    @Test
    public void testProp() throws Exception {
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("prop.test.properties"));
        System.out.println(properties.get("test"));
    }
}
