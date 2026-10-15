
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzer
{
    public static void main(String[] args) throws IOException
    {
        Path file1 = Paths.get("log1.txt");
        Path file2 = Paths.get("log2.txt");

        Files.write(file1, List.of(
            "Application started",
            "Error: file not found",
            "Application running"
        ));

        Files.write(file2, List.of(
            "User logged in",
            "Error: connection failed",
            "Application stopped"
        ));

        Path[] files = {file1, file2};
        String keyword = "Error";
        int totalLines = 0;
        int matchingLines = 0;

        for (Path file : files)
        {
            int fileLines = 0;
            int fileMatches = 0;

            for (String line : Files.readAllLines(file))
            {
                fileLines++;

                if (line.contains(keyword))
                {
                    fileMatches++;
                }
            }

            BasicFileAttributes attributes =
                Files.readAttributes(file, BasicFileAttributes.class);

            System.out.println("File: " + file.getFileName());
            System.out.println("Size: " + attributes.size() + " bytes");
            System.out.println("Lines: " + fileLines);
            System.out.println("Matching lines: " + fileMatches);
            System.out.println();

            totalLines += fileLines;
            matchingLines += fileMatches;
        }

        System.out.println("Total lines: " + totalLines);
        System.out.println("Total matching lines: " + matchingLines);
    }
}
