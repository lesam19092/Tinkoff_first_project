package edu.java.scrapper;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ExampleIntegrationTest extends IntegrationTest {



    @Test
    public void testLiquibaseMigration() throws SQLException, FileNotFoundException, LiquibaseException {
        // Verify that Liquibase migrations have been applied successfully
        System.out.println("dsadsada");
        Database database = IntegrationTest.runMigrations(POSTGRES);

        assertThat(database).isNotNull();
        // Add assertions as needed
    }
}
