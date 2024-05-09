package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import static java.lang.Thread.sleep;

public class Logger {
    public static final String PRODUCTION = "PRODUCTION";
    public static final String TEST = "TEST";
    private static Logger productionLogger;
    private static Logger testLogger;

    private String mode = "PRODUCTION";

    private Logger(String mode) {
        this.mode = mode;
    }

    public static Logger get() {
        if (productionLogger == null)
            productionLogger = new Logger(PRODUCTION);
        return productionLogger;
    }

    public static Logger get(String mode) {
        if (mode.equals(PRODUCTION)) {
            if (productionLogger == null)
                productionLogger = new Logger(PRODUCTION);
            return productionLogger;
        }
        if (testLogger == null)
            testLogger = new Logger(TEST);
        return testLogger;
    }

    public void log(String message, Class<? extends Object> clazz) {
        if (mode.equals(PRODUCTION)) {
            print(message, clazz);
        }
        makeLogMessage(message, clazz);
    }

    public String makeLogMessage(String message, Class<? extends Object> clazz) {
        String logMessage = "[" +
                getNowTime(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm")) +
                "] " +
                getClassName(clazz)+
                "." +
                addSpace(getMethodName(clazz)) +
                "() : " +
                message;

        return logMessage;
    }

    public String print(String message, Class<? extends Object> clazz) {
        String logLine = makeLogMessage(message, clazz);

        System.out.println(logLine); //출력

        return logLine;
    }

    public void manageLogFile(String logLine) throws IOException, InterruptedException {
        //파일 체크 없으면  새로 생성
        makeLatestFileIfNotPresent();

        //로그 라인추가
        addLogLine(logLine);

        sleep(150);

        // 파일 사이즈 체크 &&  10KB 넘어가면 이름 변경

        if(getFileKBSize() >= 10){
            renameFile(FileHandler.LOG_FILE, getNowTimeFileName()); //latest.log -> until_xxx.log
        }

        // 로그파일 개수 2개 이상이면 가장 오래된거 이름 변경
        if (getFileCntExceptLatestFile() >= 2) {
            //압축
            String oldestFileName = getOldestLogFileName();
            renameFile(oldestFileName, convertLogToZip(oldestFileName)); //until_xx.log -> until_xx.zip
        }

    }

    public String convertLogToZip(String logFile) {
        return logFile.replace("log", "zip");
    }

    public void addLogLine(String logLine) {
        String filePath = FileHandler.LOG_FILE_PATH; //경로

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(logLine);
            writer.newLine(); // 새 줄로 이동
        } catch (IOException e) {
            System.out.println("파일에 문자열을 추가하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    public void renameFile(String currentFileName, String newFileName) {
        String currentFilePath = FileHandler.LOG_PATH + currentFileName;

        // 파일 객체 생성
        File currentFile = new File(currentFilePath);
        File newFile = new File(currentFile.getParent(), newFileName);

        try {
            currentFile.renameTo(newFile);
        } catch (Exception e) {
            System.out.println("이름 변경 실패");
        }
    }

    public String getOldestLogFileName() {
        // 특정 경로 설정
        String directoryPath = FileHandler.LOG_PATH;

        // 해당 경로의 파일 목록 가져오기
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        Optional<File> oldestFile = Arrays.stream(files)
                .filter(file -> !file.getName().equals("latest.log")
                        && file.getName().startsWith("until")
                        && file.getName().endsWith(".log"))
                .min(Comparator.comparing(File::getName));

        return oldestFile.map(File::getName).orElse(null);
    }

    public String getNowTimeFileName() {
        String time = getNowTime(DateTimeFormatter.ofPattern("yyMMdd_HH'h'_mm'm'_ss's'"));
        return "until_" + time + ".log";
    }

    public int getFileCntExceptLatestFile() {
        String logPath = FileHandler.LOG_PATH;// 파일 경로

        File dir = new File(logPath);
        File[] files = dir.listFiles();

        return (int) Arrays.stream(files)
                .filter(file -> file.getName().startsWith("until")
                        && file.getName().endsWith(".log"))
                .count();
    }

    public void makeLatestFileIfNotPresent() {
        String logFilePath = FileHandler.LOG_FILE_PATH;// 파일 경로
        File file = new File(logFilePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.out.println("파일 생성 실패");
            }
        }
    }

    public long getFileKBSize() {
        String logFilePath = FileHandler.LOG_FILE_PATH;
        ; // 파일 경로
        Path path = Paths.get(logFilePath);

        long sizeInBytes = 0; // 파일 크기 (바이트 단위)
        try {
            sizeInBytes = Files.size(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return sizeInBytes / 1024;
    }

    public String getMethodName(Class<? extends Object> clazz) {
        String methodName = clazz.getEnclosingMethod().getName(); //new Object() {}.getClass();
        return methodName;
    }

    public String getClassName(Class<? extends Object> clazz) {
        String className = clazz.getEnclosingClass().getName(); //new Object() {}.getClass();
        return className;
    }

    public String getNowTime(DateTimeFormatter formatter) {
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

    public String addSpace(String methodName) {
        return String.format("%-" + 30 + "s", methodName);
    }
}
