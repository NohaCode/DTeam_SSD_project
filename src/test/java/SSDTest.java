import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class SSDTest {

    @Test
    void shell_exit_함수호출시_shell_종료확인() {
    }

    @Test
    void shell_help_함수호출시_사용방법_출력확인() {
    }

    void write_SSD_데이터_없는_곳에_쓰기_성공() {
    }

    @Test
    void write_SSD_데이터_있는_곳에_쓰기_성공() {
    }

    @Test
    void write_SSD_알맞은_범위에_쓰기_성공() {
    }

    @Test
    void write_SSD_알맞은_값으로_쓰기_성공() {
    }

    @Test
    void write_SSD_알맞지_않은_범위에_쓰기_실패() {
    }

    @Test
    void write_SSD_알맞지_않은_값으로_쓰기_실패() {
    }

    @Test
    void write_SSD_범위가_누락되어_쓰기_실패() {
    }

    @Test
    void write_SSD_값이_누락되어_쓰기_실패() {
    }
  
    public void read_NandFile없는경우Read(){
        //ssd R 0
    }

    @Test
    public void read_resultFile없는경우Read(){
        //ssd R 0
    }

    @Test
    public void read_Write하지않은상태로Read(){
        //ssd R 0
    }

    @Test
    public void read_Write한주소를Read(){
        //ssd W 0 0xFFFFFFFF
        //ssd R 0
    }

    @Test
    public void read_이상한주소값Read(){
        //ssd R -1
        //ssd R 111
    }

    @Test
    public void read_같은주소여러번Read(){
        //ssd R 0
        //ssd R 0
        //ssd R 0
    }

    @Test
    public void read_다른주소연속read(){
        //ssd R 0
        //ssd R 1
        //ssd R 2
    }

    @Test
    public void read_Format못맞춘경우read(){
        //ssd R
        //ssd R 2 0xFFFFFFFF
    }
}