import app.SSD;
import app.Shell;
import command.scenario.Scenario;
import exception.SSDException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ScenarioTest {

    ByteArrayOutputStream outputStream;
    PrintStream originalOut;

    @Spy
    private Shell shell;

    @Test
    public void test() throws Exception {
        Shell shell = new Shell();
        shell.run("run_list.lst");
    }

    @Test
    public void test1() throws Exception {
        Shell shell = new Shell();
        shell.run("TestApp3");
    }
}
