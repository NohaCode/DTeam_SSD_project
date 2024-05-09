import app.SSD;
import app.Shell;
import command.scenario.Scenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScenarioTest {

    @Spy
    private Shell shell;

    @Mock
    private SSD ssd;

    @Test
    void testScenario() throws Exception {
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
    }
}
