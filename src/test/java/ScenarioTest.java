import command.scenario.Scenario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScenarioTest {

    @Spy
    private Scenario scenario;

    @Test
    void testScenario() {
        scenario.append("write 55 0x0000000A");
        scenario.read(0);
        scenario.insert(0, "read 55");
        scenario.append("write 55 0x0000000A");
        scenario.update(0, "write 55 0x0000000A");
        scenario.append("write 57 0x0000000A");
        scenario.append("write 59 0x0000000A");
        scenario.deleteAll();
        scenario.append("write 55 0x0000000A");
        scenario.append("write 57 0x0000000A");
        scenario.append("write 59 0x0000000A");
        scenario.insert(2, "read 55");
        scenario.delete(2);
        scenario.readAll();
    }
}
