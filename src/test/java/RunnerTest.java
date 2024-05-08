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
    ArrayList<String> arrayList;

    private static final String TESTAPP1 = "testapp1";
    private static final String TESTAPP2 = "testapp2";

    @BeforeEach
    void setUp() {
        arrayList = new ArrayList<>();
    }

    @Test
    void test_runner_testapp1_호출시_shell() throws Exception {
        Shell shell = mock(Shell.class);
        shell.run(TESTAPP1);
        verify(shell, times(1)).run(anyString());
    }

    @Test
    void test_runner_testapp2_호출시_shell() throws Exception {
        Shell shell = mock(Shell.class);
        shell.run(TESTAPP2);
        verify(shell, times(1)).run(anyString());
    }

    @Test
    void test_runner_testapp1_호출시_shell_command() {
        ShellCommand fullReadShellCommand = spy(ShellFullReadCommand.class);
        ShellCommand fullWriteShellCommand = spy(ShellFullWriteCommand.class);

        SSD ssd = mock(SSD.class);
        arrayList.add("fullwrite");
        arrayList.add("0xABCDFFFF");
        fullWriteShellCommand.run(ssd, arrayList);
        arrayList.clear();
        arrayList.add("fullread");
        fullReadShellCommand.run(ssd, arrayList);

        verify(fullReadShellCommand, times(1)).run(ssd, arrayList);
        verify(fullWriteShellCommand, times(1)).run(ssd, arrayList);
    }

    @Test
    void test_runner_testapp2_호출시_shell_command() {
        ShellCommand shellReadCommand = spy(ShellReadCommand.class);
        ShellCommand shellWriteCommand = spy(ShellWriteCommand.class);

        SSD ssd = mock(SSD.class);

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 6; j++) {
                arrayList.add("write");
                arrayList.add("" + j);
                arrayList.add("0xAAAABBBB");
                shellWriteCommand.run(ssd, arrayList);
                arrayList.clear();
            }
        }

        for (int i = 0; i < 6; i++) {
            arrayList.add("write");
            arrayList.add("" + i);
            arrayList.add("0x12345678");
            shellWriteCommand.run(ssd, arrayList);
            arrayList.clear();
        }

        for (int i = 0; i < 6; i++) {
            arrayList.add("read");
            arrayList.add("" + i);
            shellReadCommand.run(ssd, arrayList);
        }

        // 0 ~ 5 번 LBA 에 0xAAAABBBB 값으로 총 30번 Write를 수행한다. = 180
        // 0 ~ 5 번 LBA 에 0x12345678 값으로 1 회 Over Write를 수행한다. = 6
        verify(shellWriteCommand, times(186)).run(ssd, arrayList);
        // 0 ~ 5 번 LBA Read 했을 때 정상적으로 값이 읽히는지 확인한다
        verify(shellReadCommand, times(6)).run(ssd, arrayList);
    }
}