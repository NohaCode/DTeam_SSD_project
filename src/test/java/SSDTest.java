import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SSDTest {

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

    @Test
    public void write_SSD_데이터_없는_곳에_쓰기_성공() {
        ssd.write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);

        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);
    }

    @Test
    public void write_SSD_데이터_있는_곳에_쓰기_성공() {
        ssd.write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);

        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);
    }

    @Test
    public void write_SSD_알맞은_범위에_쓰기_성공() {
        ssd.write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);

        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);
    }

    @Test
    public void write_SSD_알맞은_값으로_쓰기_성공() {
        ssd.write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);

        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE);
    }

    @Test
    public void write_SSD_알맞지_않은_범위에_쓰기_실패() {
        ssd.write(INCORRECT_WRITE_INDEX_BIG, CORRECT_WRITE_VALUE);
        ssd.write(INCORRECT_WRITE_INDEX_SMALL, CORRECT_WRITE_VALUE);

        verify(ssd, times(2)).printError();
    }

    @Test
    public void write_SSD_알맞지_않은_값으로_쓰기_실패() {
        ssd.write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_START);
        ssd.write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_ALPHA);
        ssd.write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_LENGTH);
        verify(ssd, times(3)).printError();
    }

    @Test
    public void write_SSD_값이_누락되어_쓰기_실패() {
        ssd.write(CORRECT_WRITE_INDEX, NULL_WRITE_VALUE);
        ssd.write(CORRECT_WRITE_INDEX, EMPTY_WRITE_VALUE);

        verify(ssd, times(2)).printError();

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