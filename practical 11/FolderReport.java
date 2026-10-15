
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderReport
{
    public static void main(String[] args) throws IOException
    {
        Path folder = Paths.get("lab-NN");
        Path report = Paths.get("report.txt");

        try (var writer = Files.newBufferedWriter(report))
        {
            Files.walkFileTree(folder, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attributes) throws IOException
                {
                    String name = file.getFileName().toString();
                    long size = attributes.size();
                    String modified = attributes.lastModifiedTime().toString();

                    writer.write("Name: " + name);
                    writer.newLine();

                    writer.write("Size: " + size + " bytes");
                    writer.newLine();

                    writer.write("Modified: " + modified);
                    writer.newLine();

                    writer.newLine();

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file,
                    IOException error) throws IOException
                {
                    writer.write("Could not access: " + file);
                    writer.newLine();

                    return FileVisitResult.CONTINUE;
                }
            });
        }

        System.out.println("Folder report created successfully.");
        System.out.println("Report saved in: " + report.toAbsolutePath());
    }
}
