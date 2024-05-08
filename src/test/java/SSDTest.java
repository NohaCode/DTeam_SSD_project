import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
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

    @Spy
    SSD ssd;

    FileHandler fileHandler;

    @BeforeEach
    void setUp() {
        fileHandler = FileHandler.get();
    }

    private static String getWriteCommandArgument(int index, String value) {
        return SSD.WRITE_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR + index + SSD.COMMAND_SEPARATOR + value;
    }

    private static String getReadCommandArgument(int index) {
        return SSD.READ_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR + index;
    }

    @Test
    public void write_SSD_잘못된_요청_실패() {
        assertThatThrownBy(() -> {
            ssd.run(SSD.COMMAND_SEPARATOR);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(SSD.COMMAND_SEPARATOR + CORRECT_WRITE_VALUE + SSD.COMMAND_SEPARATOR);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);

    }

    @Test
    public void write_SSD_없는_커맨드_실패() {
        assertThatThrownBy(() -> {
            ssd.run(SSD.COMMAND_SEPARATOR + CORRECT_WRITE_INDEX);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    public void write_SSD_부족한_파라미터_쓰기_실패() {
        assertThatThrownBy(() -> {
            ssd.run(SSD.WRITE_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR + CORRECT_WRITE_INDEX);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_LENGTH_PARAMETER_MESSAGE);
    }

    @Test
    public void write_SSD_부족한_파라미터_읽기_실패() {
        assertThatThrownBy(() -> {
            ssd.run(SSD.READ_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_LENGTH_PARAMETER_MESSAGE);
    }

    @Test
    public void write_SSD_데이터_있는_곳에_쓰기_성공() {
        assertDoesNotThrow(() -> {
            ssd.run(getReadCommandArgument(CORRECT_WRITE_INDEX));
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE));
        });
    }

    @Test
    public void write_SSD_알맞은_범위와_값에_쓰기_성공() {
        assertDoesNotThrow(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, CORRECT_WRITE_VALUE));
        });
    }

    @Test
    public void write_SSD_알맞지_않은_범위에_쓰기_실패() {
        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(INCORRECT_WRITE_INDEX_BIG, CORRECT_WRITE_VALUE));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_INDEX_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(INCORRECT_WRITE_INDEX_SMALL, CORRECT_WRITE_VALUE));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_INDEX_MESSAGE);
    }

    @Test
    public void write_SSD_알맞지_않은_값으로_쓰기_실패() {
        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_START));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_VALUE_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_ALPHA));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_VALUE_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_LENGTH));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_VALUE_MESSAGE);
    }

    @Test
    public void write_SSD_값이_누락되어_쓰기_실패() {
        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, NULL_WRITE_VALUE));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_VALUE_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, EMPTY_WRITE_VALUE));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_LENGTH_PARAMETER_MESSAGE);
    }

    @Test
    public void read_SSD_Write하지않은상태로Read() {
        assertDoesNotThrow(() -> {
            ssd.run("R 10");
        });

        String data = fileHandler.readNAND(10);
        assertThat(data).isEqualTo("0x00000000");
    }

    @Test
    public void read_SSD_Write한주소를Read() {
        ssd.run("W 1 0xFFFFFFFF");

        String data = fileHandler.readNAND(1);
        assertThat(data).isEqualTo("0xFFFFFFFF");
    }

    @Test
    public void read_SSD_NandFile없는경우Read() {
        if (!fileHandler.checkNANDFile()) {
            fail();
        }

        assertDoesNotThrow(() -> {
            String data = fileHandler.readNAND(10);
            assertThat(data).isEqualTo("0x00000000");
        });
    }

    @Test
    public void read_SSD_resultFile없는경우Read() {
        if (!fileHandler.checkResultFile()) {
            fail();
        }

        assertDoesNotThrow(() -> {
            ssd.run("R 10");
            String data = fileHandler.readNAND(10);
            assertThat(data).isEqualTo("0x00000000");
        });
    }

    @Test
    public void read_SSD_이상한주소값Read() {
        SSDException e = assertThrows(SSDException.class, () -> {
            ssd.run("R -1");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_INDEX_MESSAGE);

        e = assertThrows(SSDException.class, () -> {
            ssd.run("R 111");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_INDEX_MESSAGE);
    }

    @Test
    public void read_SSD_같은주소여러번Read() {
        ssd.run("W 1 0xFFFFFFFF");

        String data = fileHandler.readNAND(1);
        assertThat(data).isEqualTo("0xFFFFFFFF");

        data = fileHandler.readNAND(1);;
        assertThat(data).isEqualTo("0xFFFFFFFF");

        data = fileHandler.readNAND(1);;
        assertThat(data).isEqualTo("0xFFFFFFFF");
    }

    @Test
    public void read_SSD_다른주소연속read() {
        ssd.run("W 1 0xFFFFFFFA");
        ssd.run("W 2 0xFFFFFFFB");
        ssd.run("W 3 0xFFFFFFFF");

        String data = fileHandler.readNAND(1);
        assertThat(data).isEqualTo("0xFFFFFFFA");

        data = fileHandler.readNAND(2);
        assertThat(data).isEqualTo("0xFFFFFFFB");

        data = fileHandler.readNAND(3);
        assertThat(data).isEqualTo("0xFFFFFFFF");
    }

    @Test
    public void read_SSD_Format못맞춘경우read() {
        //ssd R
        //ssd R 2 0xFFFFFFFF
    }
}