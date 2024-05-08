import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RunnerTest {
    @Mock
    private Shell shell;

    @Mock
    private SSD ssd;

    @Mock
    private ShellCommand shellCommand;

    ArrayList<String> arrayList;

    private static final String TESTAPP1 = "testapp1";
    private static final String TESTAPP2 = "testapp2";

    @BeforeEach
    void setUp() {
        arrayList = new ArrayList<>();
    }

    @Test
    void test_runner_testapp1_호출시_shell() throws Exception {
        shell.run(TESTAPP1);
        verify(shell, times(1)).run(anyString());
    }

    @Test
    void test_runner_testapp2_호출시_shell() throws Exception {
        shell.run(TESTAPP2);
        verify(shell, times(1)).run(anyString());
    }
}