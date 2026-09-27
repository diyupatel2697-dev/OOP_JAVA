public class ChatFilter {

    public static void filter(String[] logs, String keyword) {

        int count = 0;
        StringBuilder report = new StringBuilder();

        for (String line : logs) {

            // Split into maximum 3 parts
            String[] parts = line.split(" ", 3);

            // Skip malformed line
            if (parts.length < 3)
                continue;

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            // Check keyword
            if (message.toLowerCase().contains(keyword.toLowerCase())) {

                count++;

                report.append(time)
                      .append(" ")
                      .append(user)
                      .append(": ")
                      .append(message)
                      .append("\n");
            }
        }

        System.out.println("Matches: " + count);
        System.out.println(report);
    }
}
