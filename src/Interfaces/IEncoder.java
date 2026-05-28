package Interfaces;

import DataStructures.HuffmanNode;
import java.util.Map;

public interface IEncoder {

    void generateCodeMap(HuffmanNode root, String currentCode, Map<Character, String> codeMap);
    String encodeText(String text, Map<Character, String> codes);
}
