import java.io.*;
import java.util.Objects;

public class Shell {
    private SSD ssd;

    public Shell(SSD ssd) {
        this.ssd = ssd;
    }

    public void exit() {
    }

    public void help() {
        try {
            ClassLoader classLoader = SSD.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("help.txt")).getFile());
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader.lines().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
