import app.SSD;
import exception.SSDException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import util.FileHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static util.FileHandler.DEFAULT_VALUE;

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
        fileHandler.initFile();
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
            ssd.run("F");
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(null);
            ssd.run("F");
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(SSD.COMMAND_SEPARATOR + CORRECT_WRITE_VALUE + SSD.COMMAND_SEPARATOR);
            ssd.run("F");
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
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    public void write_SSD_부족한_파라미터_읽기_실패() {
        assertThatThrownBy(() -> {
            ssd.run(SSD.READ_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR);
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);
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
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(INCORRECT_WRITE_INDEX_SMALL, CORRECT_WRITE_VALUE));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    public void write_SSD_알맞지_않은_값으로_쓰기_실패() {
        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_START));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_ALPHA));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, INCORRECT_WRITE_VALUE_LENGTH));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    public void write_SSD_값이_누락되어_쓰기_실패() {
        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, NULL_WRITE_VALUE));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);

        assertThatThrownBy(() -> {
            ssd.run(getWriteCommandArgument(CORRECT_WRITE_INDEX, EMPTY_WRITE_VALUE));
        }).isInstanceOf(SSDException.class).hasMessageContaining(SSD.INVALID_COMMAND_MESSAGE);
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

        ssd.run("R 1");
        String data = fileHandler.readRESULT(1);
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
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);

        e = assertThrows(SSDException.class, () -> {
            ssd.run("R 111");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    public void read_SSD_같은주소여러번Read() {
        ssd.run("W 1 0xFFFFFFFF");
        ssd.run("F");

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

        ssd.run("R 1");
        String data = fileHandler.readRESULT(1);
        assertThat(data).isEqualTo("0xFFFFFFFA");

        ssd.run("R 2");
        data = fileHandler.readRESULT(2);
        assertThat(data).isEqualTo("0xFFFFFFFB");

        ssd.run("R 3");
        data = fileHandler.readRESULT(3);
        assertThat(data).isEqualTo("0xFFFFFFFF");
    }

    @Test
    public void read_SSD_Format못맞춘경우read() {
        //ssd R
        //ssd R 2 0xFFFFFFFF
    }

    @Test
    void erase_SSD_Write한값_1개_지우기_성공() {
        ssd.run("W 1 0xFFFFFFFF");
        ssd.run("F");

        String data = fileHandler.readNAND(1);
        assertThat(data).isEqualTo("0xFFFFFFFF");

        ssd.run("E 1 1");
        ssd.run("F");

        data = fileHandler.readNAND(1);
        assertThat(data).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    void erase_SSD_Write한값들_10개_지우기_성공() {
        String data;
        for(int i = 0; i < 10; i++){
            ssd.run("W " + i + " 0xFFFFFFFF");
            ssd.run("F");
            data = fileHandler.readNAND(i);
            assertThat(data).isEqualTo("0xFFFFFFFF");
        }

        ssd.run("E 0 10");
        ssd.run("F");

        for(int i = 0; i < 10; i++){
            data = fileHandler.readNAND(i);
            assertThat(data).isEqualTo(DEFAULT_VALUE);
        }
    }

    @Test
    void erase_SSD_Write한값들_마지막5개_지우기_성공() {
        String data;
        for(int i = 90; i < 100; i++){
            ssd.run("W " + String.valueOf(i) + " 0xFFFFFFFF");
            ssd.run("F");
            data = fileHandler.readNAND(i);
            assertThat(data).isEqualTo("0xFFFFFFFF");
        }

        ssd.run("E 95 10");
        ssd.run("F");

        for(int i = 90; i < 95; i++){
            data = fileHandler.readNAND(i);
            assertThat(data).isEqualTo("0xFFFFFFFF");
        }

        for(int i = 95; i < 100; i++){
            data = fileHandler.readNAND(i);
            assertThat(data).isEqualTo(DEFAULT_VALUE);
        }
    }

    @Test
    void erase_SSD_명령어위반_부족한_파라미터_실패() {
        SSDException e = assertThrows(SSDException.class, () -> {
            ssd.run("E 5");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    void erase_SSD_명령어위반_잘못된명령어_실패() {
        SSDException e = assertThrows(SSDException.class, () -> {
            ssd.run("E ABC 5");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);

        e = assertThrows(SSDException.class, () -> {
            ssd.run("E 1 ABC");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    void erase_SSD_명령어위반_알맞지_않은_주소값_실패() {
        SSDException e = assertThrows(SSDException.class, () -> {
            ssd.run("E -1 5");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);

        e = assertThrows(SSDException.class, () -> {
            ssd.run("E 111 5");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    void erase_SSD_명령어위반_알맞지_않은_사이즈_실패() {
        SSDException e = assertThrows(SSDException.class, () -> {
            ssd.run("E 1 -1");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);

        e = assertThrows(SSDException.class, () -> {
            ssd.run("E 1 11");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);
    }

    @Test
    void flush_SSD_명령어위반_알맞지_않은_사이즈_실패(){
        SSDException e = assertThrows(SSDException.class, () -> {
            ssd.run("F 1");
        });
        assertThat(e.getMessage()).isEqualTo(SSD.INVALID_COMMAND_MESSAGE);
    }
}