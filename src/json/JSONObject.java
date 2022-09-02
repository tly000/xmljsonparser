package json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import xml.XMLNode;

public class JSONObject extends JSONNode {
    public Map<String, JSONNode> children = new LinkedHashMap<>();

    @Override
    public String toString() {
        return "{" + children.entrySet().stream().map(e -> "\"" + e.getKey() + "\" : " + e.getValue()).collect(Collectors.joining(",")) + "}";
    }

    public XMLNode toXML(String key) {
        // rule 1
        boolean isValueObject = children.containsKey("#" + key);
        // rule 2
        isValueObject &= children.entrySet().stream()
                .allMatch(e -> e.getKey().length() > 1 && (e.getKey().equals("#" + key) || e.getKey().startsWith("@")));
        // rule 3
        isValueObject &= children.entrySet().stream()
                .filter(e -> e.getKey().startsWith("@"))
                .map(e -> e.getValue())
                .allMatch(v -> v instanceof JSONNumber
                        || v instanceof JSONNull
                        || v instanceof JSONBoolean
                        || v instanceof JSONString);

        if (isValueObject) {
            JSONNode valueNode = children.get("#" + key);
            XMLNode node = valueNode.toXML(key);
            children.entrySet().stream()
                    .filter(e -> e.getKey().startsWith("@"))
                    .forEach(e -> {
                        String attribKey = e.getKey().substring(1);
                        String attribValue;
                        if (e.getValue() instanceof JSONNull) {
                            attribValue = "";
                        } else if (e.getValue() instanceof JSONString) {
                            attribValue = ((JSONString) e.getValue()).value;
                        } else {
                            attribValue = e.getValue().toString();
                        }
                        node.attributes.put(attribKey, attribValue);
                    });
            return node;
        }

        // not special value object, just treat each key as a normal object
        XMLNode node = new XMLNode();
        node.tag = key;
        children.forEach((childKey, value) -> {
            String tag = childKey.startsWith("@") ||childKey.startsWith("#") ? childKey.substring(1) : childKey;
            if (tag.isEmpty())
                return;

            node.children.add(value.toXML(tag));
        });
        return node;
    }
}
