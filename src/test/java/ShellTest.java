import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
}