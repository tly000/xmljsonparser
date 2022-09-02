package json;

import xml.XMLNode;

import java.util.Optional;

public class JSONBoolean extends JSONNode{
    public boolean value;

    public JSONBoolean(boolean b) {
        this.value = b;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public XMLNode toXML(String key) {
        XMLNode node = new XMLNode();
        node.tag = key;
        node.value = Optional.of(toString());
        return node;
    }
}
