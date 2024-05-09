import app.Shell;
import exception.SSDException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ScenarioTest {

    ByteArrayOutputStream outputStream;
    PrintStream originalOut;

    @Spy
    private Shell shell;

    @Test
    void testScenarioWithValidCommand() throws Exception {
        assertDoesNotThrow(() -> {
            shell.run("scenario_append write 55 0x0000000A");
            shell.run("scenario_read 0");
            shell.run("scenario_insert 0 read 55");
            shell.run("scenario_append write 55 0x0000000A");
            shell.run("scenario_update 0 write 55 0x0000000A");
            shell.run("scenario_append write 57 0x0000000A");
            shell.run("scenario_append write 59 0x0000000A");
            shell.run("scenario_delete_all");
            shell.run("scenario_append write 55 0x0000000A");
            shell.run("scenario_append write 57 0x0000000A");
            shell.run("scenario_append write 59 0x0000000A");
            shell.run("scenario_insert 2 read 55");
            shell.run("scenario_delete 2");
            shell.run("scenario_read_all");
        });
    }

    @Test
    void testScenarioWithNotValidCommand() throws Exception {
        assertDoesNotThrow(() -> {
            shell.run("scenario_read_all");
        });
        assertThrows(Exception.class, () -> {
            shell.run("scenario_read");
        });
        assertThrows(Exception.class, () -> {
            shell.run("scenario_append write 0x0000000A");
        });
        assertThrows(Exception.class, () -> {
            shell.run("scenario_append write 59");
        });
        assertThrows(Exception.class, () -> {
            shell.run("scenario_insert 2 read");
        });
        assertThrows(Exception.class, () -> {
            shell.run("scenario_insert read");
        });
        assertThrows(Exception.class, () -> {
            shell.run("scenario_delete");
        });
        assertDoesNotThrow(() -> {
            shell.run("scenario_read_all");
        });
    }
}
