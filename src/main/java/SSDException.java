public class SSDException extends RuntimeException{
    public SSDException(String msg){
        super(msg);
    }

    public SSDException(Exception e) {
        super(e.getMessage());
    }
}
