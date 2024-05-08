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

    @Mock
    private Scenario scenario;

    @Test
    void readScenario_알맞은_위치_성공() {
        int index = 0;
        doReturn("write 55 0x0000000A").when(scenario).read(index);
        assertDoesNotThrow(() -> {
            scenario.read(index);
        });
    }

    @Test
    void readScenario_없는_위치_실패() {
        int index = -1;
        doThrow(ShellException.class).when(scenario).read(index);
        assertThrows(ShellException.class, () -> {
            scenario.read(index);
        });
    }

    @Test
    @Disabled
    void readAllScenario() {

    }

    @Test
    @Disabled
    void createScenario() {

    }

    @Test
    @Disabled
    void updateScenario() {

    }

    @Test
    @Disabled
    void deleteScenario() {

    }

    @Test
    @Disabled
    void deleteAllScenario() {

    }

}
