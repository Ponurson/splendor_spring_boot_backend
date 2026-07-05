package pl.coderslab.splendor_angular_connection;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.nio.charset.StandardCharsets;

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

    @Test
    void dataSqlDoesNotDependOnGeneratedColumnOrder() throws Exception {
        try (var dataSql = getClass().getClassLoader().getResourceAsStream("data.sql")) {
            assertThat(dataSql).isNotNull();
            assertThat(new String(dataSql.readAllBytes(), StandardCharsets.UTF_8))
                    .doesNotContainPattern("(?m)^INSERT INTO\\s+[^()\\n]+\\s+VALUES");
        }
    }

}
