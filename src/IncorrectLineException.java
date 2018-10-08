public class IncorrectLineException extends Exception {
    private int lineNumber;

    public int getLineNumber() {
        return lineNumber;
    }
    public IncorrectLineException(String message, int lineNum){
        super(message);
        lineNumber = lineNum;
    }
}
