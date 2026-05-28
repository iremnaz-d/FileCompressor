package Interfaces;

import DataStructures.HuffmanNode;

public interface IDecoder {

    String decodeText(String binaryData, HuffmanNode root);
}
