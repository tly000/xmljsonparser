package xml;

import json.*;

import java.util.*;
import java.util.stream.Collectors;

public class XMLNode {
    public Map<String, String> attributes = new HashMap<>();
    public Optional<String> value = Optional.empty();
    public String tag;
    public List<XMLNode> children = new LinkedList<>();

    public String toString() {
        String attributesString = attributes.entrySet().stream()
                .map(e -> e.getKey() + " = \"" + e.getValue() + "\"")
                .collect(Collectors.joining(" "));
        String childrenString = children.stream().map(XMLNode::toString).collect(Collectors.joining());

        return "<" + tag + (attributesString.isEmpty() ? "" : " ") + attributesString + ">"
                + value.orElse("")
                + childrenString
                + "</" + tag + ">";
    }

    public void toJSON(JSONObject parent) {
        if (attributes.isEmpty() && children.isEmpty()) {
            parent.children.put(tag, value.isEmpty() ? new JSONNull() : new JSONString(value.get()));
        } else {
            JSONObject obj = new JSONObject();
            attributes.forEach((key, value) -> obj.children.put("@" + key, new JSONString(value)));
            children.forEach(c -> c.toJSON(obj));
            value.ifPresent(v -> obj.children.put("#" + tag, new JSONString(v)));
            parent.children.put(tag, obj);
        }
    }
}
