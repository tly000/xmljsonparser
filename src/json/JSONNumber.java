package json;

import xml.XMLNode;

import java.util.Optional;

public class JSONNumber extends JSONNode{
    public double value;

    public JSONNumber(double number) {
        this.value = number;
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
