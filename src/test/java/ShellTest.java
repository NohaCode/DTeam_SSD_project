
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;


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

    @Mock
    SSD ssd;

    Shell shell;

    @BeforeEach
    void setUp() {
        shell = new Shell(ssd);
    }

    @Test
    public void write_Shell_데이터_없는_곳에_쓰기_성공() {
        //write  3  0xAAAABBBB
        // • 3번 LBA 에 0xAAAABBB 를 기록한다.
        // • ssd 에 명령어를 전달한다.
        shell.write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);
        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);
    }

    @Test
    public void write_Shell_알맞지_않은_범위에_쓰기_실패() {
        //write  100  0xAAAABBBB
        //LBA 범위는 0 ~ 99
        shell.write(INCORRECT_WRITE_INDEX_BIG, CORRECT_WRITE_VALUE);
        verify(ssd, times(0)).write(INCORRECT_WRITE_INDEX_BIG, CORRECT_WRITE_VALUE);

        shell.write(INCORRECT_WRITE_INDEX_SMALL, CORRECT_WRITE_VALUE);
        verify(ssd, times(0)).write(INCORRECT_WRITE_INDEX_SMALL, CORRECT_WRITE_VALUE);

    }

    @Test
    public void write_Shell_알맞지_않은_값으로_쓰기_실패() {
        //write  3  0xGAAABBBB
        //A ~ F, 0 ~ 9 까지 숫자 범위만 허용
        shell.write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_ALPHA);
        verify(ssd, times(0)).write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_ALPHA);

        shell.write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_START);
        verify(ssd, times(0)).write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_START);

        shell.write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_LENGTH);
        verify(ssd, times(0)).write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_LENGTH);

    }

    @Test
    public void read_Shell_읽기_성공() {
        //read  3
        // • 3번 LBA 를 읽는다.
        // • ssd 에 명령어를 전달한다.
        // • result.txt 에 적힌 결과를 화면에 출력한다.
        shell.read(CORRECT_WRITE_INDEX);
        verify(ssd, times(1)).read(CORRECT_WRITE_INDEX);
    }

    @Test
    public void read_Shell_알맞지_않은_범위에_읽기_실패() {
        //read  100
        //LBA 범위는 0 ~ 99
        assertThat(shell.read(INCORRECT_WRITE_INDEX_BIG)).isEqualTo("Invalid Address");
        assertThat(shell.read(INCORRECT_WRITE_INDEX_SMALL)).isEqualTo("Invalid Address");

    }

    @Test
    public void listen_Shell_없는_명령어_실패() {
        //listen  3
        //• 없는 명령어를 수행하는 경우 "INVALID COMMAND"을 출력
        //• 어떠한 명령어를 입력하더라도 Runtime Error가 나오면 안된다.
        assertThat(shell.listen(CORRECT_WRITE_INDEX)).isEqualTo("INVALID COMMAND");
    }

    @Test
    public void exit_Shell_종료확인() {
    }

    @Test
    public void help_Shell_사용방법_출력확인() {
        //shell.help();
    }

    @Test
    public void fullread_Shell_전체파일읽기_통과() throws Exception {
        //acts
        shell.fullread();

        //assert
        verify(ssd, times(100)).read(anyInt());
    }


    @Test
    public void fullwrite_Shell_10자리_입력_통과() throws Exception {
        //act
        shell.fullwrite(CORRECT_WRITE_VALUE);

        //assert
        verify(ssd, times(100)).write(anyInt(),eq(CORRECT_WRITE_VALUE));

    }

    @Test
    public void fullwrite_Shell_비정상자리수_실패() throws Exception {
        //act
        shell.fullwrite(INCORRECT_WRITE_VALUE_LENGTH);

        //assert
        verify(ssd, times(0)).write(anyInt(),eq(INCORRECT_WRITE_VALUE_LENGTH));
    }

    @Test
    public void fullwrite_Shell_16진수아님_실패() throws Exception {
        //act
        shell.fullwrite(INCORRECT_WRITE_VALUE_ALPHA );

        //assert
        verify(ssd, times(0)).write(anyInt(),eq(INCORRECT_WRITE_VALUE_ALPHA ));
    }

    @Test
    public void fullwrite_Shell_자리수초과_실패() throws Exception {
        //act
        shell.fullwrite(INCORRECT_WRITE_VALUE_LENGTH );

        //assert
        verify(ssd, times(0)).write(anyInt(),eq(INCORRECT_WRITE_VALUE_LENGTH ));
    }

    @Test
    public void fullwrite_Shell_알파벳에러_실패() throws Exception {
        //act
        shell.fullwrite(INCORRECT_WRITE_VALUE_START );

        //assert
        verify(ssd, times(0)).write(anyInt(),eq(INCORRECT_WRITE_VALUE_START ));
    }
}