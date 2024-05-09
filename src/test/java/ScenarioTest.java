import app.Shell;
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
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        assertDoesNotThrow(() -> {
            shell.run("scenario_read_all");
        });

        String originString = outputStream.toString();
        System.setOut(originalOut);


        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        assertDoesNotThrow(() -> {
            shell.run("scenario_read");
            shell.run("scenario_append write 0x0000000A");
            shell.run("scenario_append write 59");
            shell.run("scenario_insert 2 read");
            shell.run("scenario_delete");
            shell.run("scenario_read_all");
        });
        String noChangeString = outputStream.toString();
        System.setOut(originalOut);


        assertThat(originString).isEqualTo(noChangeString);
    }
}
