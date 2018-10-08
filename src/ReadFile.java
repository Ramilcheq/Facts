import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ReadFile {
    private String fileName;

    ReadFile(String fileName) {
        this.fileName = fileName;
    }

    List<String> getStringsFromFile() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName))) {

            List<String> result = new ArrayList<>();
            String line;
            while((line=reader.readLine())!=null){
                result.add(line);
            }
            return result;
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found");
        } catch (IOException e) {
            System.out.println("Can't read file " + fileName);
        }
        return null;
    }
}
