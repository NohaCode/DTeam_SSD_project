
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
    @Spy
    SSD ssd;
    Shell shell;

    @BeforeEach
    void setUP() {
        ssd = mock(SSD.class);
        shell = new Shell(ssd);
    }

    @BeforeEach
    void setUp() {
        shell = new Shell(ssd);
    }

    @Test
    public void write_3번LBA에Write_통과() {
        //write  3  0xAAAABBBB
        // • 3번 LBA 에 0xAAAABBB 를 기록한다.
        // • ssd 에 명령어를 전달한다.
        shell.write(3, "0xAAAABBBB");
        verify(ssd, times(1)).write(3, "0xAAAABBBB");
    }

    @Test
    public void write_100번LBA에Write_실패() {
        //write  100  0xAAAABBBB
        //LBA 범위는 0 ~ 99
        shell.write(100, "0xAAAABBBB");
        verify(ssd, times(0)).write(100, "0xAAAABBBB");

    }

    @Test
    public void write_3번LBA에_범위초과Write_실패() {
        //write  3  0xGAAABBBB
        //A ~ F, 0 ~ 9 까지 숫자 범위만 허용
        shell.write(3, "0xGAAABBBB");
        verify(ssd, times(0)).write(3, "0xGAAABBBB");

    }

    @Test
    public void read_3번LBA를Read_통과() {
        //read  3
        // • 3번 LBA 를 읽는다.
        // • ssd 에 명령어를 전달한다.
        // • result.txt 에 적힌 결과를 화면에 출력한다.
        shell.read(3);
        verify(ssd, times(1)).read(3);
    }

    @Test
    public void read_100번LBA를Read_실패() {
        //read  100
        //LBA 범위는 0 ~ 99
        assertThat(shell.read(100)).isEqualTo("Invalid Address");

    }

    @Test
    public void listen_없는명령어_실패() {
        //listen  3
        //• 없는 명령어를 수행하는 경우 "INVALID COMMAND"을 출력
        //• 어떠한 명령어를 입력하더라도 Runtime Error가 나오면 안된다.
        assertThat(shell.listen(3)).isEqualTo("INVALID COMMAND");
    }

    @Test
    public void shell_exit_함수호출시_shell_종료확인() {
    }

    @Test
    public void shell_help_함수호출시_사용방법_출력확인() {
        shell.help();
    }

    @Test
    public void 전체파일읽기_통과(){

    }

    @Test
    public void 전체파일읽기_실패(){

    }

    @Test
    public void 전체파일쓰기_10자리_입력_통과() throws Exception {
        //arrange
        SSD mockSSD = mock(SSD.class);
        Shell shell = new Shell(mockSSD);
        String inputValue = "0x00000000";

        //act
        shell.fullwrite(inputValue);

        //assert
        verify(mockSSD, times(100)).write(anyInt(),eq(inputValue));

    }

    @Test
    public void 전체파일쓰기_10자리아님_입력_실패(){

    }
}