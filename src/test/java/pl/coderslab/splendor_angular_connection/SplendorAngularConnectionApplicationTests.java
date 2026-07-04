package pl.coderslab.splendor_angular_connection;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;

class SplendorAngularConnectionApplicationTests {

    @Test
    void corsUsesConfiguredOrigins() {
        CorsConfigurationSource source = new SplendorAngularConnectionApplication()
                .corsConfigurationSource("https://app.example, https://admin.example");

        CorsConfiguration configuration = source.getCorsConfiguration(new MockHttpServletRequest("GET", "/login"));

        assertThat(configuration).isNotNull();
        assertThat(configuration.getAllowedOrigins()).containsExactly("https://app.example", "https://admin.example");
        assertThat(configuration.getAllowCredentials()).isTrue();
    }

}
