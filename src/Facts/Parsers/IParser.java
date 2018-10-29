package Facts.Parsers;

import Facts.Model;

public interface IParser {
    Model parse(String... source) throws Exception;
}
