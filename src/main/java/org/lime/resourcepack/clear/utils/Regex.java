package org.lime.resourcepack.clear.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Regex {
    private static final ConcurrentHashMap<String, Pattern> patterns = new ConcurrentHashMap<>();

    private static final Pattern NONE_PATTERN = Pattern.compile("^#^");

    private static Pattern compileRegex(String regex) {
        try {
            return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            return NONE_PATTERN;
        }
    }

    public static boolean compareRegex(String input, String regex) {
        Pattern pattern = patterns.getOrDefault(regex, null);
        if (pattern == null) patterns.put(regex, pattern = compileRegex(regex));
        return pattern.matcher(input).matches();
    }
}
