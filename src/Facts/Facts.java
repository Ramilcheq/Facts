package Facts;

import Facts.JAXB.JAXBImpl;
import Facts.Parsers.IParser;
import Facts.Parsers.ParserDB;
import Facts.Parsers.ParserFactory;
import Facts.Parsers.ParserXml;

import java.io.File;
import java.util.*;

public class Facts {
    private static final String usageMessage = "Usage: facts.jar [file] [dbName dbUrl user password]";

    public static void main(String args[]) {
        if (!(args.length == 1 || args.length == 4)) {
            System.out.println(usageMessage);
            return;
        }

        try {
            IParser parser = ParserFactory.getParser(args);
            Model model = parser.parse(args);
            Set<String> allFacts = model.solve();
            System.out.println(allFacts);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}