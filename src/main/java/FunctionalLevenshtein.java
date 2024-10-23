import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionalLevenshtein {
    private static int MAX_LENGTH_DISTANCE = 2;
    private static int MAX_LIST_LENGTH = 10;
    public static void calculate(List<String> strings, String word) {
        List<String> result = strings.parallelStream()
                .filter(str -> Math.abs(str.length() - word.length()) <= MAX_LENGTH_DISTANCE)
                .map(str -> new ScoredStr(str, distance(str, word)))
                .filter(scoredStr -> scoredStr.score <= MAX_LENGTH_DISTANCE)
                .sorted(Comparator.comparing(scoredStr -> scoredStr.score))
                .limit(MAX_LIST_LENGTH)
                .map(scoredStr -> scoredStr.str)
                .collect(Collectors.toList());

        result.forEach(System.out::println);
    }

    public static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase(); // i == 0
        int [] costs = new int [b.length() + 1];

        for (int j = 0; j < costs.length; j++) costs[j] = j;

        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;

            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(
                        1 + Math.min(costs[j], costs[j - 1]),
                        a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1
                );

                nw = costs[j]; costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
}
