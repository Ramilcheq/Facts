package Facts.Parsers;

import Facts.Model;

public interface IParser {
    static final String FACT_PATTERN = "(\\_*[A-Za-z]+\\w*)";
    Model parse(String source[]) throws Exception;
}
