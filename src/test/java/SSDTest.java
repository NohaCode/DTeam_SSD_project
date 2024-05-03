import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class SSDTest {

    @Test
    public void write_SSD_데이터_없는_곳에_쓰기_성공() {
    }

    @Test
    public void write_SSD_데이터_있는_곳에_쓰기_성공() {
    }

    @Test
    public void write_SSD_알맞은_범위에_쓰기_성공() {
    }

    @Test
    public void write_SSD_알맞은_값으로_쓰기_성공() {
    }

    @Test
    public void write_SSD_알맞지_않은_범위에_쓰기_실패() {
    }

    @Test
    public void write_SSD_알맞지_않은_값으로_쓰기_실패() {
    }

    @Test
    public void write_SSD_범위가_누락되어_쓰기_실패() {
    }

    @Test
    public void write_SSD_값이_누락되어_쓰기_실패() {
    }

    @Test
    public void read_SSD_Write하지않은상태로Read(){
        //ssd R 0
        SSD ssd = new SSD();
        String readedValue = ssd.read(10);
        assertThat(readedValue).isEqualTo("0x00000000");
    }

    @Test
    public void read_SSD_Write한주소를Read(){
        //ssd W 0 0xFFFFFFFF
        //ssd R 0
    }

    @Test
    public void read_SSD_NandFile없는경우Read(){
        //ssd R 0
    }

    @Test
    public void read_SSD_resultFile없는경우Read(){
        //ssd R 0
    }

    @Test
    public void read_SSD_이상한주소값Read(){
        //ssd R -1
        //ssd R 111
    }

    @Test
    public void read_SSD_같은주소여러번Read(){
        //ssd R 0
        //ssd R 0
        //ssd R 0
    }

    @Test
    public void read_SSD_다른주소연속read(){
        //ssd R 0
        //ssd R 1
        //ssd R 2
    }

    @Test
    public void read_SSD_Format못맞춘경우read(){
        //ssd R
        //ssd R 2 0xFFFFFFFF
    }
}