package com.owczarczak.footballers;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase(
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES, provider = ZONKY,
        refresh = AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationTestBasedClass {
}
