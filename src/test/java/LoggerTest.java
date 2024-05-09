import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Logger;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

class LoggerTest {

    Logger logger = new Logger();

    @BeforeEach
    void setUp() throws IOException {

    }

    @Test
    void 단일함수_대량테스트() throws IOException {
        for (int i = 0; i < 100000; i++) {
            String print = logger.print(String.valueOf(i), new Object() {
            }.getClass());

            logger.manageLogFile(print);
        }
    }

    @Test
    void logger_print_검증() throws IOException {
        logger.print("hello", new Object(){}.getClass());
        logger.print("sdfsdf", new Object(){}.getClass());
        logger.print("sdasd", new Object(){}.getClass());
    }

    @Test
    void makeLatestFileIfNotPresent() throws IOException {
        logger.makeLatestFileIfNotPresent();
    }

    @Test
    void getFileSize() throws IOException {
        System.out.println(logger.getFileKBSize());
    }

    @Test
    void getLocalDateTime(){
        System.out.println(logger.getNowTime(DateTimeFormatter.ofPattern("yyMMdd_HH'h'_mm'm'_ss's'")));

        System.out.println(logger.getNowTime(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm")));

    }

    @Test
    void getFileCntExceptLatestFile() {
        System.out.println(logger.getFileCntExceptLatestFile());
    }

    @Test
    void getOldestFileName() {
        System.out.println(logger.getOldestLogFileName());
    }

    @Test
    void addLogLine() {
        logger.addLogLine("hello");
    }

    @Test
    void renameFileLogToLog(){
        logger.renameFile("latest.log", "until_xxxxx.log");
    }

    @Test
    void renameFileLogToZip(){
        logger.renameFile("until_240508_16h_58m_51s.log", "11122.zip");
    }
}