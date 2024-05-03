public class Shell {
    private SSD ssd;

    public Shell(SSD ssd) {
        this.ssd = ssd;
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
