import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        long startTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("program initialized");

        List<String> dictionary = Files.readString(Path.of("diccionario.ES.txt"))
                .lines()
                .filter(line -> !line.isBlank())
                .filter(line -> line.contains("="))
                .map(line -> line.split("=")[0].toLowerCase())
                .collect(Collectors.toList());

        String str = "pedo";
        FunctionalLevenshtein.calculate(dictionary, str);
        System.err.println("Seconds elapsed: " + (Calendar.getInstance().getTimeInMillis() - startTime)/1000d);
    }
}
