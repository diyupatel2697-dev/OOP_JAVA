import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TemplateFiller
 {

    public static String fill(String template,
                              String[] names,
                              String[] values) {

        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(template);

        StringBuilder result = new StringBuilder();

        int last = 0;

        while (matcher.find()) {

            // Add text before placeholder
            result.append(template.substring(last, matcher.start()));

            String placeholder = matcher.group(1);

            String value = "[?]";

            // Search placeholder in names array
            for (int i = 0; i < names.length; i++) {

                if (names[i].equals(placeholder)) {
                    value = values[i];
                    break;
                }
            }

            result.append(value);

            last = matcher.end();
        }

        // Add remaining text
        result.append(template.substring(last));

        return result.toString();
    }
}