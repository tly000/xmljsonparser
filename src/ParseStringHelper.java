import java.util.regex.Pattern;

public class ParseStringHelper {
    private String input;
    private int index = 0;

    ParseStringHelper(String input) {
        this.input = input;
    }

    void skipWS() {
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) {
            index++;
        }
    }

    String readIdentifier() {
        return match("[A-Za-z_][A-Za-z0-9_]*");
    }

    boolean lookingAt(String s) {
        return input.startsWith(s, index);
    }

    boolean consumeIfFound(String s) {
        if (lookingAt(s)) {
            index += s.length();
            return true;
        }
        return false;
    }

    void expect(String s) {
        if (!lookingAt(s))
            throw new RuntimeException("expected '" + s + "' but got " + input.substring(index, Math.min(index + s.length(), input.length())));

        index += s.length();
    }

    String match(String regex) {
        var matcher = Pattern.compile(regex).matcher(input).region(index, input.length());
        if (!matcher.lookingAt())
            throw new RuntimeException("mismatch");

        String result = matcher.group();
        index += result.length();
        return result;
    }


}
