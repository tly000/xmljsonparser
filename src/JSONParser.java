import json.*;
import json.JSONNode;

public class JSONParser {
    public static JSONNode parse(String input) {
        return parseImpl(new ParseStringHelper(input));
    }

    private static JSONNode parseImpl(ParseStringHelper p) {
        p.skipWS();
        if (p.consumeIfFound("{")) {
            JSONObject node = new JSONObject();
            p.skipWS();
            if (p.consumeIfFound("}"))
                return node;

            while (true) {
                p.skipWS();
                String key = p.match("\"[^\"]*\"");
                p.skipWS();
                p.expect(":");
                p.skipWS();
                JSONNode value = parseImpl(p);
                p.skipWS();
                node.children.put(key.substring(1, key.length()-1), value);
                if (p.consumeIfFound("}"))
                    return node;

                p.expect(",");
            }
        } else if (p.consumeIfFound("[")) {
            JSONArray node = new JSONArray();
            p.skipWS();
            if (p.consumeIfFound("]"))
                return node;

            while (true) {
                p.skipWS();
                JSONNode value = parseImpl(p);
                p.skipWS();
                node.children.add(value);
                if (p.consumeIfFound("]"))
                    return node;

                p.expect(",");
            }
        } else if (p.consumeIfFound("true")) {
            return new JSONBoolean(true);
        } else if (p.consumeIfFound("false")) {
            return new JSONBoolean(false);
        } else if (p.consumeIfFound("null")) {
            return new JSONNull();
        } else if (p.lookingAt("\"")) {
            String s = p.match("\"[^\"]*\"");
            return new JSONString(s.substring(1, s.length()-1));
        } else {
            // this must be a number
            String number = p.match("-?[0-9]*(\\.[0-9]+)?([eE][+-]?[0-9])?");
            return new JSONNumber(Double.parseDouble(number));
        }
    }
}
