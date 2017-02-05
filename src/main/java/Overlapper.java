import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Overlapper {

    public static void main(String[] args) {
        Overlapper overlapper = new Overlapper();
        overlapper.run("m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, adipisci " +
                "velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem " +
                "ipsum qu;iquam quaerat vol;psum quia dolor sit amet, consectetur, a;ia " +
                "dolor sit amet, conse;squam est, qui do;Neque porro quisquam est, qu;aerat " +
                "voluptatem.;m eius modi tem;Neque porro qui;, sed quia non numquam ei;lorem " +
                "ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia non numq;unt ut " +
                "labore et dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us " +
                "modi tempora incid;Neque porro quisquam est, qui dolorem i;uam eius modi " +
                "tem;pora inc;am al");
        //Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam
        //eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.
        //overlapper.run("O draconia;conian devil! Oh la;h lame sa;saint!");
    }

    private void run(String file) {
        List<String> fragments = new ArrayList<>(Arrays.asList(file.split(";")));
        String finalVersion = "";
        while (fragments.size() > 1) {
            String current = fragments.get(0);
            fragments.remove(current);
            Pair<String, String> mostCommonStringOverlapPair = getMaximalOverlap(current, fragments);
            if (mostCommonStringOverlapPair == null) {
                fragments.add(current);
                continue;
            }
            String mostSimilarFragment = mostCommonStringOverlapPair.getKey();
            String commonSubstring = mostCommonStringOverlapPair.getValue();
            fragments.remove(mostSimilarFragment);
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
        String mostSimilarFragment = null;
        String commonSubstring = null;
        boolean found = false;
        while (!found) {
            for (String remainingFragment : remainingFragmentsCopy) {
                substringSimilarity = getLengthOfCommonSubstring(currentFragment, remainingFragment);
                if (substringSimilarity > highestSimilarity) {
                    highestSimilarity = substringSimilarity;
                    mostSimilarFragment = remainingFragment;
                }
            }
            commonSubstring = removeCommonSubstring(currentFragment, mostSimilarFragment);
            if (mostSimilarFragment.startsWith(commonSubstring) || mostSimilarFragment.endsWith(commonSubstring)) {
                found = true;
            } else {
                highestSimilarity = substringSimilarity;
                remainingFragmentsCopy.remove(mostSimilarFragment);
            }
        }
        return new Pair<>(mostSimilarFragment, commonSubstring);
    }

    private static int getLengthOfCommonSubstring(String first, String second) {
        int maxLen = 0;
        int firstLength = first.length();
        int secondLength = second.length();

        int[][] table = new int[firstLength+1][secondLength+1];

        for (int i = 1; i <= firstLength; i++) {
            for (int j = 1; j <= secondLength; j++) {
                if (first.charAt(i-1) == second.charAt(j-1)) {
                    table[i][j] = table[i - 1][j - 1] + 1;
                    if (table[i][j] > maxLen)
                        maxLen = table[i][j];
                }
            }
        }
        return maxLen;
    }

    private static String removeCommonSubstring(String s1, String s2) {
        int start = 0;
        int max = 0;
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                int x = 0;
                while (s1.charAt(i + x) == s2.charAt(j + x)) {
                    x++;
                    if (((i + x) >= s1.length()) || ((j + x) >= s2.length())) {
                        break;
                    }
                }
                if (x > max) {
                    max = x;
                    start = i;
                }
            }
        }
        return s1.substring(start, (start + max));
    }

}

