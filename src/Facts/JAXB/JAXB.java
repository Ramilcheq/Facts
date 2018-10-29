package Facts.JAXB;

import javax.xml.bind.JAXBException;
import java.io.File;

public interface JAXB {
    Object getObject(File file, Class c) throws JAXBException;
    void saveObject(File file, Object object) throws JAXBException;
}
