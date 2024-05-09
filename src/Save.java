import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Save {

    public void write(String filePath, String content) {
        try {
            byte[] contentBytes = content.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, contentBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("File saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving the file.");
        }
    }

    public LinkedList<String> read(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            return new LinkedList<>(lines);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the file.");
            return null;
        }
    }

    public LinkedList<Integer> readIntegers(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            LinkedList<Integer> integers = new LinkedList<>();
            for (String line : lines) {
                String[] tokens = line.split("\\s+");
                for (String token : tokens) {
                    integers.add(Integer.parseInt(token));
                }
            }
            return integers;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the file.");
            return null;
        }
    }

    public LinkedList<LinkedList<Integer>> readIntegersArray(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            LinkedList<LinkedList<Integer>> allIntegers = new LinkedList<>();

            for (String line : lines) {
                String[] tokens = line.split("\\s+");
                LinkedList<Integer> integers = new LinkedList<>();
                for (String token : tokens) {
                    integers.add(Integer.parseInt(token));
                }
                allIntegers.add(integers);
            }
            return allIntegers;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the file.");
            return null;
        }
    }

}
