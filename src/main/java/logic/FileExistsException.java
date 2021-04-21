package logic;

public class FileExistsException extends Exception {
    public FileExistsException(String errorMessage) {
        super(errorMessage);
    }
}