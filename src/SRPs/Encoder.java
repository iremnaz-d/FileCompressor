package SRPs;

import DataStructures.HuffmanNode;
import Interfaces.IEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class Encoder implements IEncoder {

    @Override
    //generateCodes(root,"", codeMap) şeklinde çağırcam
    public void generateCodes(HuffmanNode node, String currentCode, Map<Character, String> codeMap) {
        if (node == null) {
            return;
        }

        if (node.getLeft() == null && node.getRight() == null) { //leaf node ise kaydet
            codeMap.put(node.getCharacter(), currentCode);
            return;
        }

        generateCodes(node.getLeft(), currentCode + "0", codeMap); //left 0
        generateCodes(node.getRight(), currentCode + "1", codeMap); //right 1
    }

    @Override
    public String encodeText(String filePath, Map<Character, String> codeMap) {

        StringBuilder encodedString = new StringBuilder();
        FileHandler fileHandler = new FileHandler();
        BufferedReader reader = fileHandler.getTextReader(filePath);

        try {
            int c;

            while ((c = reader.read()) != -1) { //char -1 almaz
                char character = (char) c;
                encodedString.append(codeMap.get(character));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return encodedString.toString();
    }

}
