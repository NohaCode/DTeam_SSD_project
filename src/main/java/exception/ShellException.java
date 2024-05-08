package exception;

public class ShellException extends RuntimeException{
    public ShellException(String msg){
        super(msg);
    }

    public ShellException(Exception e) {
        super(e.getMessage());
    }

    public ShellException() {
        super();
    }
}
