import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Overlapper {

    public static void main(String[] args) throws IOException {
        Overlapper overlapper = new Overlapper();
        String fileName = args[0];
        Files.lines(Paths.get(fileName)).forEach(overlapper::run);
    }

    private void run(String file) {
        List<String> fragments = new ArrayList<>(Arrays.asList(file.split(";")));
        String finalVersion = "";
        while (fragments.size() > 1) {
            String current = fragments.get(0);
            fragments.remove(current);
            Pair<String, String> mostCommonStringOverlapPair = getMaximalOverlap(current, fragments);
            String mostSimilarFragment = mostCommonStringOverlapPair.getKey();
            String commonSubstring = mostCommonStringOverlapPair.getValue();
            fragments.remove(mostSimilarFragment);
            if (commonSubstring == null) {
                continue;
            }
            String newFragment;
            if (mostSimilarFragment.startsWith(commonSubstring)) {
                newFragment = current + mostSimilarFragment.replace(commonSubstring, "");
            } else {
                newFragment = mostSimilarFragment.replace(commonSubstring, "") + current;
            }
            finalVersion = newFragment;
            fragments.add(0, newFragment);
        }
        System.out.println(finalVersion);
    }

    private Pair<String, String> getMaximalOverlap(String currentFragment, List<String> remainingFragments) {
        List<String> remainingFragmentsCopy = new ArrayList<>(remainingFragments);
        int highestSimilarity = 0;
        int substringSimilarity = 0;
        String currentComparingFragment = null;
        String mostSimilarFragment = null;
        String commonSubstring = null;
        boolean found = false;
        while (!found) {
            for (String remainingFragment : remainingFragmentsCopy) {
                currentComparingFragment = remainingFragment;
                substringSimilarity = getLengthOfCommonSubstring(currentFragment, remainingFragment);
                if (substringSimilarity > highestSimilarity) {
                    highestSimilarity = substringSimilarity;
                    mostSimilarFragment = remainingFragment;
                }
            }

            if (mostSimilarFragment == null) {
                mostSimilarFragment = currentComparingFragment;
                commonSubstring = null;
                break;
            }

            Optional<String> optionalCommonSubstring = getCommonSubstring(currentFragment, mostSimilarFragment, highestSimilarity);
            if (optionalCommonSubstring.isPresent()) {
                commonSubstring = optionalCommonSubstring.get();
                found = true;
            } else {
                highestSimilarity = substringSimilarity;
                remainingFragmentsCopy.remove(mostSimilarFragment);
                mostSimilarFragment = null;
            }
        }
        return new Pair<>(mostSimilarFragment, commonSubstring);
    }

    private static int getLengthOfCommonSubstring(String first, String second) {
        int maxLength = 0;
        int firstLength = first.length();
        int secondLength = second.length();

        int[][] lengths = new int[firstLength][secondLength];

        for (int i = 0; i < firstLength; i++) {
            for (int j = 0; j < secondLength; j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    if (i == 0 || j == 0) {
                        lengths[i][j] = 1;
                    } else {
                        lengths[i][j] = lengths[i - 1][j - 1] + 1;
                    }
                    if (lengths[i][j] > maxLength) {
                        maxLength = lengths[i][j];
                    }
                }
            }
        }
        return maxLength;
    }

    private static Optional<String> getCommonSubstring(String currentFragment, String mostSimilarFragment, int length) {
        String substring = mostSimilarFragment.substring(0, length);
        if (currentFragment.endsWith(substring)) {
            return Optional.of(substring);
        }
        int l = mostSimilarFragment.length();
        int dif = Math.max(length, l) - Math.min(length, l);
        substring = mostSimilarFragment.substring(dif);
        if (currentFragment.startsWith(substring)) {
            return Optional.of(substring);
        }
        return Optional.empty();
    }

}
