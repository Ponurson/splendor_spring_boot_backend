package pl.coderslab.splendor_angular_connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.splendor_angular_connection.auth.Role;
import pl.coderslab.splendor_angular_connection.auth.RoleRepository;
import pl.coderslab.splendor_angular_connection.user.User;
import pl.coderslab.splendor_angular_connection.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class TokenAuthenticationTest {

    private static final String USERNAME = "alice";
    private static final String PASSWORD = "s3cret";

    @Autowired
    private org.springframework.web.context.WebApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();

        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Transient role: User.roles cascades ALL, so saving the user persists it.
        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setEnabled(1);
        user.setUserState("idle");
        user.setLastOnline(LocalDateTime.now());
        user.setRoles(new HashSet<>(Set.of(role)));
        userRepository.save(user);
    }

    private String basic() {
        return "Basic " + Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
    }

    @Test
    void loginReturnsBackendIssuedToken() throws Exception {
        String body = mockMvc.perform(get("/login").header("Authorization", basic()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Matcher matcher = Pattern.compile("\"token\"\\s*:\\s*\"([^\"]+)\"").matcher(body);
        assertThat(matcher.find()).as("response contains a token field").isTrue();
        String token = matcher.group(1);

        assertThat(token).isNotBlank();
        // Not the old base64(username:password) scheme.
        assertThat(token).isNotEqualTo(Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes()));

        // The issued token authenticates a protected endpoint.
        mockMvc.perform(get("/userList").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void protectedEndpointRejectedWithoutToken() throws Exception {
        mockMvc.perform(get("/userList"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpointRejectedWithInvalidToken() throws Exception {
        mockMvc.perform(get("/userList").header("Authorization", "Bearer not-a-real-token"))
                .andExpect(status().isUnauthorized());
    }
}
