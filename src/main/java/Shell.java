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

    void fullwrite(String value) throws Exception {
        if(value.length() != 10 && !value.matches("^0x[0-9A-F]{8}$")){
            System.out.println("10자리 16진수만 입력 가능합니다.");
            return;
        }


        for (int addreess = 0; addreess < 100; addreess++) {
            try{
                ssd.write(addreess, value);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


}
