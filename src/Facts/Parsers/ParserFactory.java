package Facts.Parsers;

public class ParserFactory {
    public static IParser getParser(String source[]) throws Exception {
        switch (source[0]) {
            case "-f":
                if(source[1].endsWith(".xml"))
                    return new ParserXml();
                else if(source[1].endsWith(".txt"))
                    return new ParserFile();
                else
                    throw new Exception("Usage: facts.jar [-f fileName] [-d dbName dbUrl user password]");
            case "-d":
                return new ParserDB();
            default:
                throw new Exception("Usage: facts.jar [-f fileName] [-d dbName dbUrl user password]");
        }
    }
}
