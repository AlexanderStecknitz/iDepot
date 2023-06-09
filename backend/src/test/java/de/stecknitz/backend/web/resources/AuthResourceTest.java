package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.UserRepository;
import de.stecknitz.backend.core.service.UserService;
import de.stecknitz.backend.web.resources.dto.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AuthResourceTest {

    private final String ENDPOINT = "/auth";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    MockMvc mockMvc;

    @Container
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer<>();

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add( "spring.datasource.url", postgresqlContainer::getJdbcUrl );
        dynamicPropertyRegistry.add( "spring.datasource.username", postgresqlContainer::getUsername );
        dynamicPropertyRegistry.add( "spring.datasource.password", postgresqlContainer::getPassword );
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @AfterAll
    public static void afterAll() {
        postgresqlContainer.close();
    }

    @Test
    void registerTest() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .email("alex@alex.de")
                .firstname("alexander")
                .lastname("stecknitz")
                .password("admin")
                .build();

        mockMvc.perform(post(ENDPOINT + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        User user = userRepository.findByEmail(userDTO.getEmail()).orElse(null);
        Assertions.assertThat(user.getEmail()).isEqualTo(userDTO.getEmail());
        Assertions.assertThat(user.getFirstname()).isEqualTo(userDTO.getFirstname());
        Assertions.assertThat(user.getLastname()).isEqualTo(userDTO.getLastname());

    }

    @Test
    void loginTest() throws Exception {
        mockMvc.perform(post(ENDPOINT + "/login")
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void logoutTest() throws Exception {

        mockMvc.perform(get(ENDPOINT + "/logout")
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin")))
                .andExpect(status().isOk());

    }
}
