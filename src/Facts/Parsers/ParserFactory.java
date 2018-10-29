package Facts.Parsers;

public class ParserFactory {
    public static IParser getParser(String... source) throws Exception {
        switch (source.length) {
            case 1:
                if (source[0].endsWith(".xml")) {
                    return new ParserXml();
                } else {
                    return new ParserFile();
                }
            default:
                return new ParserDB();
        }
    }
}
