package json;

import xml.XMLNode;

import java.util.Optional;

public class JSONString extends JSONNode{
    public String value;

    public JSONString(String string) {
        this.value = string;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public XMLNode toXML(String key) {
        XMLNode node = new XMLNode();
        node.tag = key;
        node.value = Optional.of(value);
        return node;
    }
}
