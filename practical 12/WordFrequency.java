import java.util.*;

public class WordFrequency
{
    public static void main(String[] args)
    {
        String paragraph = "java is easy java is powerful java is useful";

        Map<String, Integer> frequency = new HashMap<>();

        for (String word : paragraph.toLowerCase().split("\\s+"))
        {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> words =
            new ArrayList<>(frequency.entrySet());

        words.sort((a, b) ->
        {
            int result = b.getValue().compareTo(a.getValue());
            return result != 0 ? result : a.getKey().compareTo(b.getKey());
        });

        for (Map.Entry<String, Integer> entry : words)
        {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}