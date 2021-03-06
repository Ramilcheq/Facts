package test.java;

import Facts.Facts;
import Facts.Parsers.ParserFile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashSet;
import java.util.Set;

public class MainTests {
    private final String pathXml = "src/test/java/Files/xml_files/";
    private final String pathFile = "src/test/java/Files/txt_files/";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private PrintStream originalErr;

    @Before
    public void setUpStreams() {
        originalOut = System.out;
        originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // Xml file tests
    @Test
    public void XmlTest1() {
        Facts.main(new String[]{"-f", pathXml + "1.xml"});
        String expected = "_a3, A, a3, A3, Aa3, a_3, ____________________a, _________a3, Aaaaa3";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlTest2() {
        Facts.main(new String[]{"-f", pathXml + "2.xml"});
        String expected = "q";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlTest3() {
        Facts.main(new String[]{"-f", pathXml + "3.xml"});
        String expected = "_a3, A, a3, A3, Aa3, a_3, ____________________a, _________a3, Aaaaa3";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlTest4() {
        Facts.main(new String[]{"-f", pathXml + "4.xml"});
        String expected = "Aa______32aaa3";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlExceptionTest1() {
        Facts.main(new String[]{"-f", pathXml + "Exception1.xml"});
        String expected = "cvc-complex-type.2.4.b: The content of element 'Facts' is not complete. One of '{Fact}' is expected.\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlExceptionTest2() {
        Facts.main(new String[]{"-f", pathXml + "Exception2.xml"});
        String expected = "cvc-complex-type.2.4.b: The content of element 'Rule' is not complete. One of '{Or, And, Fact}' is expected.\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlExceptionTest3() {
        Facts.main(new String[]{"-f", pathXml + "Exception3.xml"});
        String expected = "cvc-elt.1: Cannot find the declaration of element 'Facts'.\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlExceptionTest4() {
        Facts.main(new String[]{"-f", pathXml + "Exception4.xml"});
        String expected = "cvc-pattern-valid: Value '1Aa3' is not facet-valid with respect to pattern '\\_*[A-Za-z]+[A-Za-z0-9_]*' for type 'Fact'.\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlExceptionTest5() {
        Facts.main(new String[]{"-f", pathXml + "Exception5.xml"});
        String expected = "cvc-complex-type.2.4.b: The content of element 'And' is not complete. One of '{Fact}' is expected.\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlExceptionTest6() {
        Facts.main(new String[]{"-f", pathXml + "Exception6.xml"});
        String expected = "cvc-complex-type.2.4.b: The content of element 'Or' is not complete. One of '{And, Fact}' is expected.\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlExceptionTest7() {
        Facts.main(new String[]{"-f", pathXml + "Exception7.xml"});
        String expected = "cvc-elt.1: Cannot find the declaration of element 'Rules_Facts'.\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlExceptionTest8() {
        Facts.main(new String[]{"-f", pathXml + "Exception8.xml"});
        String expected = "cvc-complex-type.4: Attribute 'fact' must appear on element 'Fact'.\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void XmlExceptionTest9() {
        Facts.main(new String[]{"-f", pathXml + "Exception9.xml"});
        String expected = "cvc-complex-type.2.4.b: The content of element 'Rule' is not complete. One of '{Result}' is expected.\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    // Regular file tests
    @Test
    public void FileNegativeTest1() throws Exception {
        Facts.main(new String[]{"-f", pathFile + "NoExistentFile.txt"});
        String expected = "src\\test\\java\\Files\\txt_files\\NoExistentFile.txt (Не удается найти указанный файл)\r\n";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileNegativeTest2() throws Exception {
        FileChannel fileChannel = new RandomAccessFile(
                new File(pathFile + "NotAvailableFile.txt"), "rw").getChannel();
        FileLock lock = fileChannel.lock();

        Facts.main(new String[]{"-f", pathFile + "NotAvailableFile.txt"});
        String expected = "Процесс не может получить доступ к файлу, так как часть этого файла заблокирована другим процессом\r\n";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileNegativeTest3() {
        Facts.main(new String[]{"-f", pathFile + "IncorrectExceptionFile1.txt"});
        String expected = "Error in line #4\r\n";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileNegativeTest4() {
        Facts.main(new String[]{"-f", pathFile + "IncorrectExceptionFile2.txt"});
        String expected = "Error in fact, line #8\r\n";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileNegativeTest5() {
        Facts.main(new String[]{"-f", pathFile + "IncorrectExceptionFile3.txt"});
        String expected = "Error in fact, line #6\r\n";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileNegativeTest6() {
        Facts.main(new String[]{"-f", pathFile + "IncorrectExceptionFile4.txt"});
        String expected = "Error in line #7: facts already read\r\n";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    //
    @Test
    public void FileTest1() {
        Facts.main(new String[]{"-f", pathFile + "6.txt"});
        String expected = "A, B, C, d, D";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileTest2() {
        Facts.main(new String[]{"-f", pathFile + "1.txt"});
        String expected = "_a3, A, a3, A3, Aa3, a_3, ____________________a, _________a3, Aaaaa3";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileTest3() {
        Facts.main(new String[]{"-f", pathFile + "2.txt"});
        String expected = "a1, A, a, C, D, b_1_";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileTest4() {
        Facts.main(new String[]{"-f", pathFile + "3.txt"});
        String expected = "_a_, _b_, _c3, d2";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileTest5() {
        Facts.main(new String[]{"-f", pathFile + "4.txt"});
        String expected = "b, C";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FileTest6() {
        Facts.main(new String[]{"-f", pathFile + "5.txt"});
        String expected = "a, b, c, d, e, f, g";
        String actual = outContent.toString();
        Assert.assertEquals(expected, actual);
    }
}