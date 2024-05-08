import app.SSD;
import app.Shell;
import command.shell.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestAppTest {
    private static final String TESTAPP1 = "testapp1";
    private static final String TESTAPP2 = "testapp2";

    ArrayList<String> arrayList;
    ByteArrayOutputStream outputStream;
    FileHandler fileHandler = FileHandler.get();

    @BeforeEach
    void setUp() {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.clear();
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
        String firstFullWriteValue = "0xABCDFFFF";
        SSD ssd = mock(SSD.class);

        arrayList.add("fullwrite");
        arrayList.add(firstFullWriteValue);
        fullWriteShellCommand.run(ssd, arrayList);
        arrayList.clear();

        arrayList.add("fullread");
        fullReadShellCommand.run(ssd, arrayList);
        arrayList.clear();

        verify(fullReadShellCommand, times(1)).run(ssd, arrayList);
        verify(fullWriteShellCommand, times(1)).run(ssd, arrayList);
    }

    @Test
    void test_runner_testapp2_호출시_shell_command() {
        ShellCommand shellReadCommand = spy(ShellReadCommand.class);
        ShellCommand shellWriteCommand = spy(ShellWriteCommand.class);
        SSD ssd = mock(SSD.class);
        String firstWriteValue = "0xAAAABBBB";
        String overwriteValue = "0x12345678";

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 6; j++) {
                arrayList.add("write");
                arrayList.add("" + j);
                arrayList.add(firstWriteValue);
                shellWriteCommand.run(ssd, arrayList);
                arrayList.clear();
            }
        }

        for (int i = 0; i < 6; i++) {
            arrayList.add("write");
            arrayList.add("" + i);
            arrayList.add(overwriteValue);
            shellWriteCommand.run(ssd, arrayList);
            arrayList.clear();
        }

        for (int i = 0; i < 6; i++) {
            arrayList.add("read");
            arrayList.add("" + i);
            shellReadCommand.run(ssd, arrayList);
            arrayList.clear();
        }

        // 0 ~ 5 번 LBA 에 0xAAAABBBB 값으로 총 30번 Write를 수행한다. = 6 * 30 = 180
        // 0 ~ 5 번 LBA 에 0x12345678 값으로 1 회 Over Write를 수행한다. = 1 * 6 = 6
        verify(shellWriteCommand, times(186)).run(ssd, arrayList);
        // 0 ~ 5 번 LBA Read 했을 때 정상적으로 값이 읽히는지 확인한다 = 1 * 6 = 6
        verify(shellReadCommand, times(6)).run(ssd, arrayList);


    }

    @Test
    void test_runner_testapp1_호출시_실제_파일입출력_shell_command() {
        Shell shell = spy(Shell.class);
        SSD ssd = shell.getSsd();
        String firstFullWriteValue = "0xABCDFFFF";

        assertDoesNotThrow(() -> {
            shell.run(TESTAPP1);
        });

        for (int i = 0; i < 100; i++) {
            ssd.run("R " + i);
            assertThat(fileHandler.readRESULT(i)).isEqualTo(firstFullWriteValue);
        }
    }

    @Test
    void test_runner_testapp2_호출시_실제_파일입출력_shell_command() {
        Shell shell = spy(Shell.class);
        SSD ssd = shell.getSsd();
        String overwriteValue = "0x12345678";

        assertDoesNotThrow(() -> {
            shell.run(TESTAPP2);
        });

        for (int i = 0; i < 6; i++) {
            ssd.run("R " + i);
            assertThat(fileHandler.readRESULT(i)).isEqualTo(overwriteValue);
        }

    }
}