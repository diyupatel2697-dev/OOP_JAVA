import java.util.*;

public class Leaderboard
{
    public static void main(String[] args)
    {
        Map<String, Integer> scores = new HashMap<>();

        scores.put("Amit", 85);
        scores.put("Neha", 95);
        scores.put("Raj", 85);
        scores.put("Priya", 90);
        scores.put("Karan", 75);

        List<Map.Entry<String, Integer>> leaderboard =
            new ArrayList<>(scores.entrySet());

        leaderboard.sort((a, b) ->
        {
            int result = b.getValue().compareTo(a.getValue());
            return result != 0 ? result : a.getKey().compareTo(b.getKey());
        });

        int topN = 3;

        System.out.println("Top " + topN + " Players:");

        for (int i = 0; i < Math.min(topN, leaderboard.size()); i++)
        {
            Map.Entry<String, Integer> player = leaderboard.get(i);
            System.out.println(player.getKey() + " : " + player.getValue());
        }
    }
}