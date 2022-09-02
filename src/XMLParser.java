import xml.XMLNode;

import java.util.Optional;

public class XMLParser {
    public static XMLNode parse(String s) {
        return parseImpl(new ParseStringHelper(s));
    }

    private static XMLNode parseImpl(ParseStringHelper p) {
        var node = new XMLNode();

        p.skipWS();
        p.expect("<");
        p.skipWS();
        node.tag = p.readIdentifier();
        p.skipWS();
        // read attributes
        while (true) {
            if (p.consumeIfFound("/>")) {
                // element is directly closed.
                return node;
            }
            if (p.consumeIfFound(">")) {
                break;
            }
            String key = p.readIdentifier();
            p.skipWS();
            p.expect("=");
            p.skipWS();
            String quote = p.match("\"|'");
            String value = p.match("[^\"]*");
            p.expect(quote);
            p.skipWS();
            node.attributes.put(key, value);
        }
        // read children
        while (true) {
            p.skipWS();
            if (p.consumeIfFound("</")) {
                // closing tag
                p.skipWS();
                p.expect(node.tag);
                p.skipWS();
                p.expect(">");
                break;
            }
            if (p.lookingAt("<")) {
                // child node
                node.children.add(parseImpl(p));
            } else {
                // value
                String value = p.match("[^<>]+");
                if (node.value.isPresent()) {
                    throw new RuntimeException("more than one value found");
                }
                node.value = Optional.of(value);
            }
        }
        return node;
    }
}
