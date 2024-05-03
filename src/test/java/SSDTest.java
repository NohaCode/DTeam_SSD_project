import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SSDTest {

    @Test
    public void write_SSD_데이터_없는_곳에_쓰기_성공() {
        SSD ssd = mock(SSD.class);
        int correctWriteIndex = 45;
        String correctWriteValue = "0x1298CDEF";

        ssd.write(correctWriteIndex, correctWriteValue);

        verify(ssd, times(1)).write(correctWriteIndex, correctWriteValue);
    }

    @Test
    public void write_SSD_데이터_있는_곳에_쓰기_성공() {
        SSD ssd = mock(SSD.class);
        int correctWriteIndex = 45;
        String correctWriteValue = "0x1298CDEF";

        ssd.write(correctWriteIndex, correctWriteValue);

        verify(ssd, times(1)).write(correctWriteIndex, correctWriteValue);
    }

    @Test
    public void write_SSD_알맞은_범위에_쓰기_성공() {
        SSD ssd = spy(SSD.class);
        int correctWriteIndex = 45;
        String correctWriteValue = "0x1298CDEF";

        ssd.write(correctWriteIndex, correctWriteValue);

        verify(ssd, times(1)).write(correctWriteIndex, correctWriteValue);
    }

    @Test
    public void write_SSD_알맞은_값으로_쓰기_성공() {
        SSD ssd = spy(SSD.class);
        int correctWriteIndex = 45;
        String correctWriteValue = "0x1298CDEF";

        ssd.write(correctWriteIndex, correctWriteValue);

        verify(ssd, times(1)).write(correctWriteIndex, correctWriteValue);
    }

    @Test
    public void write_SSD_알맞지_않은_범위에_쓰기_실패() {
        SSD ssd = spy(SSD.class);
        int incorrectWriteIndex = 103;
        String correctWriteValue = "0x1298CDEF";

        ssd.write(incorrectWriteIndex, correctWriteValue);

        verify(ssd, times(1)).printError();
    }

    @Test
    public void write_SSD_알맞지_않은_값으로_쓰기_실패() {
        SSD ssd = spy(SSD.class);
        int correctWriteIndex = 45;
        String incorrectWriteValue = "1x1298CDEF";

        ssd.write(correctWriteIndex, incorrectWriteValue);
        verify(ssd, times(1)).printError();
    }

    @Test
    public void write_SSD_값이_누락되어_쓰기_실패() {
        SSD ssd = spy(SSD.class);
        int correctWriteIndex = 45;
        String incorrectWriteValue = null;

        ssd.write(correctWriteIndex, incorrectWriteValue);
        verify(ssd, times(1)).printError();
    }

    @Test
    public void read_NandFile없는경우Read() {
        //ssd R 0
    }

    @Test
    public void read_resultFile없는경우Read() {
        //ssd R 0
    }

    @Test
    public void read_Write하지않은상태로Read() {
        //ssd R 0
    }

    @Test
    public void read_Write한주소를Read() {
        //ssd W 0 0xFFFFFFFF
        //ssd R 0
    }

    @Test
    public void read_이상한주소값Read() {
        //ssd R -1
        //ssd R 111
    }

    @Test
    public void read_같은주소여러번Read() {
        //ssd R 0
        //ssd R 0
        //ssd R 0
    }

    @Test
    public void read_다른주소연속read() {
        //ssd R 0
        //ssd R 1
        //ssd R 2
    }

    @Test
    public void read_Format못맞춘경우read() {
        //ssd R
        //ssd R 2 0xFFFFFFFF
    }
}