package org.example;

import org.example.annotation.Config;
import org.example.annotation.Key;
import org.example.exception.ConfigurationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConfigTest {

    @Config("src/test/resources/test.properties")
    private interface TestInterface {
        String address();
        String port();
        @Key("user.login")
        String getLogin();
        @Key("user.password")
        String getPassword();
    }

    @Test
    void TestCreateConfig() {
        TestInterface config = ConfigFactory.createConfig(TestInterface.class);
        assertThat(config).isNotNull();
        assertThat(config.address()).isEqualTo("192.168.0.4");
        assertThat(config.port()).isEqualTo("8080");
        assertThat(config.getLogin()).isEqualTo("admin");
        assertThat(config.getPassword()).isEqualTo("qwerty123");
    }

    private interface NotAnnotated {
        String address();
        String port();
        @Key("user.login")
        String getLogin();
        @Key("user.password")
        String getPassword();
    }

    @Test
    void TestInvalidInterface() {
        assertThatThrownBy(
                () -> ConfigFactory.createConfig(NotAnnotated.class)
        ).isExactlyInstanceOf(ConfigurationException.class);
    }
}
