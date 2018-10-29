package Facts;

import Facts.Parsers.IParser;
import Facts.Parsers.ParserFactory;

import java.util.*;

public class Facts {
    private static final String USAGE_MESSAGE = "Usage: facts.jar [-f fileName] [-d dbName dbUrl user password]";

    public static void main(String args[]) {
        if (!(args.length == 2 || args.length == 5)) {
            System.out.println(USAGE_MESSAGE);
            return;
        }

        try {
            IParser parser = ParserFactory.getParser(args);
            Model model = parser.parse(args);
            Set<String> allFacts = model.solve();
            System.out.print(allFacts.toString().replace("[", "").replace("]", ""));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}