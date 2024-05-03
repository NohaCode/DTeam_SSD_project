import org.junit.jupiter.api.Test;

class ShellTest {

    @Test
    public void write_3번LBA에Write_통과() {
        //write  3  0xAAAABBBB
        // • 3번 LBA 에 0xAAAABBB 를 기록한다.
        // • ssd 에 명령어를 전달한다.
    }

    @Test
    public void write_100번LBA에Write_실패() {
        //write  100  0xAAAABBBB
        //LBA 범위는 0 ~ 99
    }

    @Test
    public void write_3번LBA에_범위초과Write_실패() {
        //write  3  0xGAAABBBB
        //A ~ F, 0 ~ 9 까지 숫자 범위만 허용
    }

    @Test
    public void read_3번LBA를Read_통과() {
        //read  3
        // • 3번 LBA 를 읽는다.
        // • ssd 에 명령어를 전달한다.
        // • result.txt 에 적힌 결과를 화면에 출력한다.
    }

    @Test
    public void read_100번LBA를Read_실패() {
        //read  100
        //LBA 범위는 0 ~ 99
    }

    @Test
    public void listen_없는명령어_실패() {
        //listen  3
        //• 없는 명령어를 수행하는 경우 "INVALID COMMAND"을 출력
        //• 어떠한 명령어를 입력하더라도 Runtime Error가 나오면 안된다.
    }




}