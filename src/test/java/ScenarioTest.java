import command.scenario.Scenario;
import exception.ShellException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScenarioTest {

    @Spy
    private Scenario scenario;

    @Test
    void testScenario() {
        scenario.create("write 55 0x0000000A");
        scenario.read(0);
        scenario.insert(0, "read 55");
        scenario.create("write 55 0x0000000A");
        scenario.update(0, "write 55 0x0000000A");
        scenario.create("write 57 0x0000000A");
        scenario.create("write 59 0x0000000A");
        scenario.deleteAll();
        scenario.create("write 55 0x0000000A");
        scenario.create("write 57 0x0000000A");
        scenario.create("write 59 0x0000000A");
        scenario.insert(2, "read 55");
    }
}
