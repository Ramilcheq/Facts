public class Facts {
    public static void main(String args[]) {
        String fileName;
        if (args.length >= 1) {
            fileName = args[0];
        } else {
            System.out.println("Parameter is required");
            return;
        }
        try {
            System.out.println(new Solver().getAllFacts(new ReadFile(fileName).getStringsFromFile()));
        } catch (IncorrectLineException e) {
            System.out.println(e.getMessage() + e.getLineNumber());
        }
    }
}