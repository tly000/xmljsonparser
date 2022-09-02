package json;

import xml.XMLNode;

public class JSONNull extends JSONNode {
    @Override
    public String toString() {
        return "null";
    }

    @Override
    public XMLNode toXML(String key) {
        XMLNode node = new XMLNode();
        node.tag = key;
        return node;
    }
}
