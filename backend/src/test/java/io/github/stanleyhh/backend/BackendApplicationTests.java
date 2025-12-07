package io.github.stanleyhh.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main_runs() {
        try (var mocked = mockStatic(SpringApplication.class)) {
            BackendApplication.main(new String[]{});
            mocked.verify(() -> SpringApplication.run(BackendApplication.class, new String[]{})
            );
        }
    }
}
