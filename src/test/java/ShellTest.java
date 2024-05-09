import app.SSD;
import app.Shell;
import command.shell.ShellCommandFactory;
import command.shell.ShellRunnerCommand;
import exception.SSDException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShellTest {

    public static final int CORRECT_WRITE_INDEX = 45;
    public static final String CORRECT_WRITE_VALUE = "0x1298CDEF";
    public static final int INCORRECT_WRITE_INDEX_BIG = 100;
    public static final int INCORRECT_WRITE_INDEX_SMALL = -1;
    public static final String INCORRECT_WRITE_VALUE_START = "1x1298CDEF";
    public static final String INCORRECT_WRITE_VALUE_ALPHA = "0x129ZCDEF";
    public static final String INCORRECT_WRITE_VALUE_LENGTH = "0x1290CDE";
    public static final String NULL_WRITE_VALUE = null;
    public static final String EMPTY_WRITE_VALUE = "";

    public static final int CORRECT_ERASE_SIZE_ONE = 1;
    public static final int CORRECT_ERASE_SIZE_TEN = 10;
    public static final int CORRECT_ERASE_SIZE_ELEVEN = 11;
    public static final int CORRECT_ERASE_SIZE_HUNDRED = 100;
    public static final int INCORRECT_ERASE_SIZE_ZERO = 0;
    public static final int ERASE_INDEX_ONE = 1;
    public static final int ERASE_INDEX_ELEVEN = 11;
    public static final int ERASE_INDEX_FIFTY_ONE = 51;
    public static final int ERASE_INDEX_SEVEN = 7;
    public static final String RESOURCES_PATH = "src/main/resources/";
    public static final String TEST_RESOURCES_PATH = "src/test/resources/";
    public static final String RUN_LIST_FILE = "run_list.lst";
    public static final String RUN_LIST_FILE_PATH = RESOURCES_PATH + RUN_LIST_FILE;
    public static final String TEST_RUN_LIST_FILE_PATH = TEST_RESOURCES_PATH + RUN_LIST_FILE;

    @Spy
    SSD ssd;

    Shell shell;

    @BeforeEach
    void setUp() {

        shell = new Shell(ssd);
    }

    @Test
    public void write_Shell_정상케이스() throws Exception {
        //write  3  0xAAAABBBB
        // • 3번 LBA 에 0xAAAABBB 를 기록한다.
        // • ssd 에 명령어를 전달한다.
        String shellCommandLine = "write " + String.valueOf(CORRECT_WRITE_INDEX) + " " + CORRECT_WRITE_VALUE;
        String ssdCommandLine = "W " + String.valueOf(CORRECT_WRITE_INDEX) + " " + CORRECT_WRITE_VALUE;

        shell.run(shellCommandLine);

        verify(ssd, times(1)).run(ssdCommandLine);
    }

    @Test
    public void write_Shell_잘못된인덱스_범위밖_음수() throws Exception {
        //write  100  0xAAAABBBB
        //LBA 범위는 0 ~ 99
        String shellCommandLine = "write " + String.valueOf(INCORRECT_WRITE_INDEX_BIG) + " " + CORRECT_WRITE_VALUE;
        String ssdCommandLine = "W " + String.valueOf(INCORRECT_WRITE_INDEX_BIG) + " " + CORRECT_WRITE_VALUE;

        shell.run(shellCommandLine);

        verify(ssd, times(0)).run(ssdCommandLine);

    }


    @Test
    public void write_Shell_잘못된인덱스_범위밖_100이상() throws Exception {
        //write  100  0xAAAABBBB
        //LBA 범위는 0 ~ 99
        String shellCommandLine = "write " + String.valueOf(INCORRECT_WRITE_INDEX_BIG) + " " + CORRECT_WRITE_VALUE;
        String ssdCommandLine = "W " + String.valueOf(INCORRECT_WRITE_INDEX_BIG) + " " + CORRECT_WRITE_VALUE;

        shell.run(shellCommandLine);

        verify(ssd, times(0)).run(ssdCommandLine);

    }

    @Test
    public void write_Shell_비정상16진수_0X아님() {
        assertThatThrownBy(() -> {
            String shellCommandLine = "write " + CORRECT_WRITE_INDEX + " " + INCORRECT_WRITE_VALUE_START;
            shell.run(shellCommandLine);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    public void write_Shell_비정상16진수_잘못된알파벳() {
        assertThatThrownBy(() -> {
            String shellCommandLine = "write " + CORRECT_WRITE_INDEX + " " + INCORRECT_WRITE_VALUE_ALPHA;
            shell.run(shellCommandLine);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);

    }

    @Test
    public void write_Shell_비정상16진수_길이9() throws Exception {
        assertThatThrownBy(() -> {
            String shellCommandLine = "write " + String.valueOf(CORRECT_WRITE_INDEX) + " " + INCORRECT_WRITE_VALUE_LENGTH;
            shell.run(shellCommandLine);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    public void read_Shell_정상케이스() throws Exception {
        //read  3
        // • 3번 LBA 를 읽는다.
        // • ssd 에 명령어를 전달한다.
        // • result.txt 에 적힌 결과를 화면에 출력한다.
        String shellCommandLine = "read " + String.valueOf(CORRECT_WRITE_INDEX);
        String ssdCommandLine = "R " + String.valueOf(CORRECT_WRITE_INDEX);

        shell.run(shellCommandLine);
        verify(ssd, times(1)).run(ssdCommandLine);
    }

    @Test
    public void read_Shell_잘못된인덱스접근_음수() throws Exception {
        //read  100
        //LBA 범위는 0 ~ 99
        String shellCommandLine = "read " + String.valueOf(INCORRECT_WRITE_INDEX_SMALL);
        String ssdCommandLine = "R " + String.valueOf(INCORRECT_WRITE_INDEX_SMALL);

        shell.run(shellCommandLine);
        verify(ssd, times(0)).run(ssdCommandLine);
    }

    @Test
    public void read_Shell_잘못된인덱스접근_100이상() throws Exception {
        //read  100
        //LBA 범위는 0 ~ 99
        String[] tokens = {"read", String.valueOf(INCORRECT_WRITE_INDEX_BIG)};
        String shellCommandLine = "read " + String.valueOf(INCORRECT_WRITE_INDEX_BIG);
        String ssdCommandLine = "R " + String.valueOf(INCORRECT_WRITE_INDEX_BIG);

        shell.run(shellCommandLine);
        verify(ssd, times(0)).run(ssdCommandLine);
    }

    @Test
    public void listen_Shell_없는_명령어수행() throws Exception {
        //listen  3
        //• 없는 명령어를 수행하는 경우 "INVALID COMMAND"을 출력
        //• 어떠한 명령어를 입력하더라도 Runtime Error가 나오면 안된다.
        String shellCommandLine = "read " + String.valueOf(CORRECT_WRITE_INDEX) + " sdfsdf";
        String ssdCommandLine = "R " + String.valueOf(CORRECT_WRITE_INDEX) + " sdfsdf";

        shell.run(shellCommandLine);
        verify(ssd, times(0)).run(ssdCommandLine);
    }

    @Test
    public void exit_Shell_종료확인() throws Exception {
//        System.setSecurityManager(new SecurityManager() {
//            @Override
//            public void checkExit(int status) {
//                assertEquals(0, status);
//                throw new SecurityException("System.exit() called");
//            }
//        });
//
//        try {
//            String shellCommandLine = "exit";
//            shell.run(shellCommandLine);
//        } catch (SecurityException e) {
//            assertEquals("System.exit() called", e.getMessage());
//            return;
//        }
//
//        fail("System.exit() not called");
    }

    @Test
    public void help_Shell_사용방법_출력확인() throws Exception {
        String shellCommandLine = "help";
        String ssdCommandLine = "help";

        shell.run(shellCommandLine);
        verify(ssd, times(0)).run(ssdCommandLine);
    }

    @Test
    public void fullread_Shell_전체파일읽기_통과() throws Exception {

        String shellCommandLine = "fullread";

        shell.run(shellCommandLine);

        verify(ssd, times(100)).run(anyString());
    }

    @Test
    public void fullread_Shell_전체파일읽기_실패_잘못된_명령어() throws Exception {

        String shellCommandLine = "fullread WWW";
        shell.run(shellCommandLine);

        verify(ssd, times(0)).run(anyString());
    }


    @Test
    public void fullwrite_Shell_정상16진수_입력_통과() throws Exception {
        String shellCommandLine = "fullwrite " + String.valueOf(CORRECT_WRITE_VALUE);

        //act
        shell.run(shellCommandLine);

        //assert
        verify(ssd, times(100)).run(anyString());

    }

    @Test
    public void fullwrite_Shell_비정상16진수_9자리_실패() throws Exception {
        String shellCommandLine = "fullwrite " + String.valueOf(INCORRECT_WRITE_VALUE_LENGTH);

        shell.run(shellCommandLine);

        //assert
        verify(ssd, times(0)).run(anyString());
    }

    @Test
    public void fullwrite_Shell_비정상16진수_0X아님_실패() throws Exception {
        String shellCommandLine = "fullwrite " + String.valueOf(INCORRECT_WRITE_VALUE_START);

        shell.run(shellCommandLine);

        verify(ssd, times(0)).run(anyString());
    }

    @Test
    public void fullwrite_Shell_비정상16진수_잘못된알파벳() throws Exception {
        String shellCommandLine = "fullwrite " + String.valueOf(INCORRECT_WRITE_VALUE_ALPHA);

        shell.run(shellCommandLine);

        verify(ssd, times(0)).run(anyString());
    }

    @Test
    public void erase_Shell_정상케이스_사이즈_10_이하() throws Exception {
        String shellCommandLine = "erase " + String.valueOf(ERASE_INDEX_ONE) + " " + CORRECT_ERASE_SIZE_ONE;
        String ssdCommandLine = "E " + String.valueOf(ERASE_INDEX_ONE) + " " + CORRECT_ERASE_SIZE_ONE;

        shell.run(shellCommandLine);

        verify(ssd, times(1)).run(ssdCommandLine);

        shellCommandLine = "erase " + String.valueOf(ERASE_INDEX_ONE) + " " + CORRECT_ERASE_SIZE_TEN;
        ssdCommandLine = "E " + String.valueOf(ERASE_INDEX_ONE) + " " + CORRECT_ERASE_SIZE_TEN;

        shell.run(shellCommandLine);

        verify(ssd, times(1)).run(ssdCommandLine);
    }

    @Test
    public void erase_Shell_정상케이스_사이즈_11() throws Exception {
        String shellCommandLine = "erase " + String.valueOf(ERASE_INDEX_ONE) + " " + CORRECT_ERASE_SIZE_ELEVEN;

        shell.run(shellCommandLine);

        erase_range_Shell_Test(ERASE_INDEX_ONE, ERASE_INDEX_ONE + CORRECT_ERASE_SIZE_ELEVEN);
    }

    @Test
    public void erase_Shell_정상케이스_사이즈_100() throws Exception {
        String shellCommandLine = "erase " + String.valueOf(ERASE_INDEX_ONE) + " " + CORRECT_ERASE_SIZE_HUNDRED;

        shell.run(shellCommandLine);

        erase_range_Shell_Test(ERASE_INDEX_ONE, ERASE_INDEX_ONE + CORRECT_ERASE_SIZE_HUNDRED);
    }

    @Test
    public void erase_Shell_비정상케이스_사이즈_0() throws Exception {
        String shellCommandLine = "erase " + String.valueOf(ERASE_INDEX_ONE) + " " + INCORRECT_ERASE_SIZE_ZERO;
        assertThatThrownBy(() -> {
            shell.run(shellCommandLine);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    public void erase_range_Shell_정상케이스_1에서_51까지() throws Exception {
        String shellCommandLine = "erase_range " + String.valueOf(ERASE_INDEX_ONE) + " " + String.valueOf(ERASE_INDEX_FIFTY_ONE);

        shell.run(shellCommandLine);

        erase_range_Shell_Test(ERASE_INDEX_ONE, ERASE_INDEX_FIFTY_ONE);
    }

    @Test
    public void erase_range_Shell_정상케이스_1에서_7까지() throws Exception {
        String shellCommandLine = "erase_range " + String.valueOf(ERASE_INDEX_ONE) + " " + String.valueOf(ERASE_INDEX_SEVEN);

        shell.run(shellCommandLine);

        erase_range_Shell_Test(ERASE_INDEX_ONE, ERASE_INDEX_SEVEN);
    }

    @Test
    public void erase_range_Shell_정상케이스_1에서_11까지() throws Exception {
        String shellCommandLine = "erase_range " + String.valueOf(ERASE_INDEX_ONE) + " " + String.valueOf(ERASE_INDEX_ELEVEN);

        shell.run(shellCommandLine);

        erase_range_Shell_Test(ERASE_INDEX_ONE, ERASE_INDEX_ELEVEN);
    }

    private void erase_range_Shell_Test(int start, int end) {
        String ssdCommandLine;

        while (end - start > 10) {
            ssdCommandLine = "E " + String.valueOf(start) + " 10";
            verify(ssd, times(1)).run(ssdCommandLine);
            start += 10;
        }
        ssdCommandLine = "E " + String.valueOf(start) + " " + String.valueOf(end - start);
        verify(ssd, times(1)).run(ssdCommandLine);
    }


    @Test
    public void erase_range_Shell_잘못된인덱스_범위밖_100이상() throws Exception {
        String shellCommandLine = "erase_range " + String.valueOf(ERASE_INDEX_ONE) + " " + String.valueOf(INCORRECT_WRITE_INDEX_BIG);
        String ssdCommandLine = "E " + String.valueOf(ERASE_INDEX_ONE) + " 10";

        shell.run(shellCommandLine);

        verify(ssd, times(0)).run(ssdCommandLine);

    }

    @Test
    void runner_정상테스트케이스() throws Exception {
        shell.run("run_list.lst");
    }

    @Test
    void runner_정상케이스_runner_process수행() throws Exception {
        ShellRunnerCommand runnerCommand = (ShellRunnerCommand) ShellCommandFactory.of("run_list.lst");
        ArrayList<String> commandOptionList = new ArrayList<>(Arrays.asList("run_list.lst"));

        assertThat(runnerCommand.isValidCommand(commandOptionList)).isEqualTo(true);
        assertThat(runnerCommand.testRun(RUN_LIST_FILE_PATH)).isEqualTo("Pass");

    }

    @Test
    void runner_오류테스트케이스() {
        ShellRunnerCommand runnerCommand = (ShellRunnerCommand) ShellCommandFactory.of("run_list.lst");
        ArrayList<String> commandOptionList = new ArrayList<>(Arrays.asList("run_list.lst"));

        assertThat(runnerCommand.isValidCommand(commandOptionList)).isEqualTo(true);
        assertThat(runnerCommand.testRun(TEST_RUN_LIST_FILE_PATH)).isEqualTo("Fail");
    }
}