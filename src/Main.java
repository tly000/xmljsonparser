import json.JSONNode;
import json.JSONObject;
import xml.XMLNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileContent = Files.readString(Paths.get(args[0]));
        if (args[0].endsWith(".xml")) {
            XMLNode node = XMLParser.parse(fileContent);
            System.out.println(node);
            JSONObject root = new JSONObject();
            node.toJSON(root);
            System.out.println(root);
        } else {
            JSONNode node = JSONParser.parse(fileContent);
            System.out.println(node);
            XMLNode xml = node.toXML("root");
            if (node instanceof JSONObject && ((JSONObject) node).children.size() == 1) {
                xml = xml.children.get(0);
            }
            System.out.println(xml);
        }
    }
}
