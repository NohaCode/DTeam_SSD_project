public class SSD {
    public SSD() {
    }

    public void write(int index, String b){
    }

    public String read(int index){
        if(!(0 <= index && index <= 99))
            return "Invalid Address";
        return "0x00000000";
    }

    public static void main(String[] args) {

    }

    public boolean isValidFile(String file) {
        return true;
    }
}
