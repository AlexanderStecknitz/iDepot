package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.UserRepository;
import de.stecknitz.backend.core.service.UserService;
import de.stecknitz.backend.web.resources.dto.RegisterUserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthResourceTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    private final String ENDPOINT = "/auth";

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @AfterAll
    public static void afterAll() {
        postgres.close();
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgres::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void registerTest() throws Exception {
        RegisterUserDTO registerUserDTO = RegisterUserDTO.builder()
                .email(TestUtil.SECOND_USER_EMAIL)
                .firstname(TestUtil.SECOND_USER_FIRST_NAME)
                .lastname(TestUtil.SECOND_USER_LAST_NAME)
                .password(TestUtil.SECOND_USER_PASSWORD)
                .build();

        mockMvc.perform(post(ENDPOINT + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(registerUserDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        User user = userRepository.findByEmail(registerUserDTO.getEmail()).get();
        Assertions.assertThat(user.getEmail()).isEqualTo(registerUserDTO.getEmail());
        Assertions.assertThat(user.getFirstname()).isEqualTo(registerUserDTO.getFirstname());
        Assertions.assertThat(user.getLastname()).isEqualTo(registerUserDTO.getLastname());

    }

    @Test
    void loginTest() throws Exception {
        mockMvc.perform(post(ENDPOINT + "/login")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(TestUtil.USER_EMAIL, TestUtil.USER_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void logoutTest() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/logout")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(TestUtil.USER_EMAIL, TestUtil.USER_PASSWORD)))
                .andExpect(status().isOk());

    }

    @WithMockUser
    @Test
    void findUserTest() throws Exception {
        final String email = TestUtil.USER_EMAIL;
        mockMvc.perform(get(ENDPOINT + "/user/" + email))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
