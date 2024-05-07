import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

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

    @Mock
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

        verify(ssd, times(1)).write(INCORRECT_WRITE_INDEX_BIG, CORRECT_WRITE_VALUE);
        verify(ssd, times(1)).write(INCORRECT_WRITE_INDEX_SMALL, CORRECT_WRITE_VALUE);
    }

    @Test
    public void write_SSD_알맞지_않은_값으로_쓰기_실패() {
        ssd.write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_START);
        ssd.write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_ALPHA);
        ssd.write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_LENGTH);
        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_START);
        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_ALPHA);
        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_LENGTH);
    }

    @Test
    public void write_SSD_값이_누락되어_쓰기_실패() {
        ssd.write(CORRECT_WRITE_INDEX, NULL_WRITE_VALUE);
        ssd.write(CORRECT_WRITE_INDEX, EMPTY_WRITE_VALUE);

        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, NULL_WRITE_VALUE);
        verify(ssd, times(1)).write(CORRECT_WRITE_INDEX, EMPTY_WRITE_VALUE);
    }

    @Test
    public void read_SSD_Write하지않은상태로Read() {
        //ssd R 0
        SSD ssd = new SSD();
        String readedValue = ssd.read(10);
        assertThat(readedValue).isEqualTo("0x00000000");
    }

    @Test
    public void read_SSD_Write한주소를Read() {
        //ssd W 1 0xFFFFFFFF
        //ssd R 1

        SSD ssd = mock(SSD.class);
        ssd.write(1, "0xFFFFFFFF");
        when(ssd.read(1)).thenReturn("0xFFFFFFFF");

        assertThat(ssd.read(1)).isEqualTo("0xFFFFFFFF");
    }

    @Test
    public void read_SSD_NandFile없는경우Read() {
        //ssd R 0
        SSD ssd = new SSD();
        if (!ssd.isValidFile("nand.txt")) {
            fail();
        }

        assertDoesNotThrow(() -> {
            String readedData = ssd.read(10);
            assertThat(readedData).isEqualTo("0x00000000");
        });
    }

    @Test
    public void read_SSD_resultFile없는경우Read() {
        //ssd R 0
        SSD ssd = new SSD();
        if (!ssd.isValidFile("result.txt")) {
            fail();
        }

        assertDoesNotThrow(() -> {
            String readedData = ssd.read(10);
            assertThat(readedData).isEqualTo("0x00000000");
        });
    }

    @Test
    public void read_SSD_이상한주소값Read() {
        //ssd R -1
        //ssd R 111
        SSD ssd = new SSD();
        String data = ssd.read(-1);
        assertThat(data).isEqualTo("Invalid Address");

        data = ssd.read(111);
        assertThat(data).isEqualTo("Invalid Address");
    }

    @Test
    public void read_SSD_같은주소여러번Read() {
        SSD ssd = mock(SSD.class);
        ssd.write(1, "0xFFFFFFFF");
        when(ssd.read(1))
                .thenReturn("0xFFFFFFFF")
                .thenReturn("0xFFFFFFFF")
                .thenReturn("0xFFFFFFFF");

        assertThat(ssd.read(1)).isEqualTo("0xFFFFFFFF");
    }

    @Test
    public void read_SSD_다른주소연속read() {
        SSD ssd = mock(SSD.class);
        ssd.write(1, "0xFFFFFFFA");
        ssd.write(2, "0xFFFFFFFB");
        ssd.write(3, "0xFFFFFFFF");

        when(ssd.read(1)).thenReturn("0xFFFFFFFA");
        when(ssd.read(2)).thenReturn("0xFFFFFFFB");
        when(ssd.read(3)).thenReturn("0xFFFFFFFF");

        assertThat(ssd.read(1)).isEqualTo("0xFFFFFFFA");
        assertThat(ssd.read(2)).isEqualTo("0xFFFFFFFB");
        assertThat(ssd.read(3)).isEqualTo("0xFFFFFFFF");
    }

    @Test
    public void read_SSD_Format못맞춘경우read() {
        //ssd R
        //ssd R 2 0xFFFFFFFF
    }
}