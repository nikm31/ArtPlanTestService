package ru.artplan.artplantest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ru.artplan.artplantest.dtos.AuthResponse;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullServerRunTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void fullRestTest() {

        AuthResponse authResponse = restTemplate.postForObject("/api/v1/user/check_login", "{\"username\": \"nik\"}", AuthResponse.class);
        assertThat(authResponse).isNotNull();
    }
}