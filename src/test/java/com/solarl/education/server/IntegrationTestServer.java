package com.solarl.education.server;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.solarl.education.Application;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = Application.class,
        webEnvironment = RANDOM_PORT)
@ContextConfiguration(
        classes = IntegrationTestServer.TestApplicationConfig.class
)
@TestPropertySource(
        properties = {
                "spring.jpa.show-sql=true",
                "spring.jpa.properties.hibernate.format_sql=true",
                "logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE"
        }
)
@RunWith(SpringRunner.class)
@Slf4j
public class IntegrationTestServer {

    @Configuration
    @NoArgsConstructor
    public static class TestApplicationConfig {

        private static EmbeddedPostgres embeddedPostgres;

        @Bean
        @SneakyThrows
        public DataSource dataSource() {
            embeddedPostgres = EmbeddedPostgres.builder()
                    .setImage(DockerImageName.parse("postgres:14.1"))
                    .start();

            return embeddedPostgres.getPostgresDatabase();
        }

    }

    @SneakyThrows
    protected void holdServer() {
        Thread.sleep(9999_999);
    }

}
