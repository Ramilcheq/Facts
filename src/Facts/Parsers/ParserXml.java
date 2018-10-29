package Facts.Parsers;

import Facts.*;
import Facts.JAXB.JAXBImpl;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParserXml implements IParser {
    @Override
    public Model parse(String... fileName) throws Exception {
        validateXml(fileName[0]);
        JAXBImpl jaxb = new JAXBImpl();
        RulesFacts rulesFacts = (RulesFacts) jaxb.getObject(new File(fileName[0]), RulesFacts.class);
        List<Rule> rules = rulesFacts.getRules();
        Set<String> facts = rulesFacts.getFacts();
        return new Model(rules, facts);
    }

    // Проверка xml-файла на валидность
    private void validateXml(String xml) throws SAXException, IOException {
        SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema()
                .newValidator()
                .validate(new StreamSource(new File(xml)));
    }

    // Вспомогательный класс для анмаршалинга правил и фактов
    @XmlRootElement(name = "Rules_Facts")
    public static class RulesFacts {
        private List<Rule> rules = new ArrayList<>();
        private Set<String> facts = new HashSet<>();

        public RulesFacts() {
        }

        public RulesFacts(List<Rule> rules, Set<String> facts) {
            this.rules = rules;
            this.facts = facts;
        }

        @XmlElementWrapper(name = "Rules")
        @XmlElement(name = "Rule")
        public List<Rule> getRules() {
            return rules;
        }

        @XmlElementWrapper(name = "Facts")
        @XmlElement(name = "Fact")
        public Set<String> getFacts() {
            return facts;
        }
    }
}
