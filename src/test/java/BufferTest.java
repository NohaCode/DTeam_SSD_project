import app.SSD;
import command.ssd.SSDCommandBuffer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BufferTest {

    @Spy
    SSD ssd;

    ArrayList<ArrayList<String>> commandBuffer = SSDCommandBuffer.get();

    @Test
    void buffer_Command10개저장후Flush() {
        for (int i = 0; i < 20; i++) {
            ssd.run("W " + i + " 0x0000000" + i % 10);
        }

        for (int i = 0; i < 10; i++) {
            verify(ssd, times(1)).run("W " + i + " 0x0000000" + i % 10);
        }
    }

    @Test
    void buffer_IgnoreWrite_중복Write제거(){
        for (int i = 0; i < 5; i++) {
            ssd.run("W " + i + " 0x0000000" + i % 10);
        }

        for (int i = 0; i < 5; i++) {
            ssd.run("W " + i + " 0x0000000" + i % 10);
        }

        assertThat(commandBuffer.size()).isEqualTo(5);
    }

    @Test
    void buffer_merge_erase(){
        ssd.run("E 10 2");
        ssd.run("E 12 3");

        assertThat(commandBuffer.size()).isEqualTo(1);

        ssd.run("F");

        ssd.run("E 12 3");
        ssd.run("E 10 2");
        assertThat(commandBuffer.size()).isEqualTo(1);

        ssd.run("F");

        ssd.run("E 10 2");
        ssd.run("E 13 3");
        assertThat(commandBuffer.size()).isEqualTo(2);
    }
}
