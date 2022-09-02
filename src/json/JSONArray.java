package json;

import xml.XMLNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JSONArray extends JSONNode {
    public List<JSONNode> children = new LinkedList<>();

    @Override
    public String toString() {
        return "[" + children.stream().map(Objects::toString).collect(Collectors.joining(",")) + "]";
    }

    @Override
    public XMLNode toXML(String key) {
        XMLNode node = new XMLNode();
        node.tag = key;
        node.children = children.stream().map(e -> e.toXML("element")).toList();
        return node;
    }
}
