package club.imaginears.dashboard.utils.chat;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author Innectic
 * @since 6/15/2017
 */
public class JaroWinkler implements StringSimilarity, StringDistance {

    private final double DEFAULT_THRESHOLD = 0.7;
    private final int THREE = 3;
    private final double JW_COEF = 0.1;
    @Getter private final double threshold;

    public JaroWinkler() {
        this.threshold = DEFAULT_THRESHOLD;
    }

    public JaroWinkler(final double threshold) {
        this.threshold = threshold;
    }

    @Override
    public final double similarity(String first, String second) {
        if (first == null) {
            throw new NullPointerException("first must not be null");
        }

        if (second == null) {
            throw new NullPointerException("second must not be null");
        }

        if (first.equals(second)) {
            return 1;
        }

        int[] mtp = matches(first, second);
        float m = mtp[0];
        if (m == 0) {
            return 0f;
        }
        double j = ((m / first.length() + m / second.length() + (m - mtp[1]) / m))
                / THREE;
        double jw = j;

        if (j > getThreshold()) {
            jw = j + Math.min(JW_COEF, 1.0 / mtp[THREE]) * mtp[2] * (1 - j);
        }
        return jw;
    }

    @Override
    public final double distance(final String s1, final String s2) {
        return 1.0 - similarity(s1, s2);
    }

    private int[] matches(final String s1, final String s2) {
        String max, min;
        if (s1.length() > s2.length()) {
            max = s1;
            min = s2;
        } else {
            max = s2;
            min = s1;
        }
        int range = Math.max(max.length() / 2 - 1, 0);
        int[] match_indexes = new int[min.length()];
        Arrays.fill(match_indexes, -1);
        boolean[] match_flags = new boolean[max.length()];
        int matches = 0;
        for (int mi = 0; mi < min.length(); mi++) {
            char c1 = min.charAt(mi);
            for (int xi = Math.max(mi - range, 0),
                 xn = Math.min(mi + range + 1, max.length());
                 xi < xn;
                 xi++) {
                if (!match_flags[xi] && c1 == max.charAt(xi)) {
                    match_indexes[mi] = xi;
                    match_flags[xi] = true;
                    matches++;
                    break;
                }
            }
        }
        char[] ms1 = new char[matches];
        char[] ms2 = new char[matches];
        for (int i = 0, si = 0; i < min.length(); i++) {
            if (match_indexes[i] != -1) {
                ms1[si] = min.charAt(i);
                si++;
            }
        }
        for (int i = 0, si = 0; i < max.length(); i++) {
            if (match_flags[i]) {
                ms2[si] = max.charAt(i);
                si++;
            }
        }
        int transpositions = 0;
        for (int mi = 0; mi < ms1.length; mi++) {
            if (ms1[mi] != ms2[mi]) {
                transpositions++;
            }
        }
        int prefix = 0;
        for (int mi = 0; mi < min.length(); mi++) {
            if (s1.charAt(mi) == s2.charAt(mi)) {
                prefix++;
            } else {
                break;
            }
        }
        return new int[]{matches, transpositions / 2, prefix, max.length()};
    }
}
