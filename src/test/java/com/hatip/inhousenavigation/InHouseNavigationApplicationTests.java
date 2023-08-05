package com.hatip.inhousenavigation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class InHouseNavigationApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true, "Context should load successfully");
    }

}
