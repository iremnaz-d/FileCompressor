package SRPs;

import DataStructures.HuffmanNode;
import Interfaces.IDecoder;

public class Decoder implements IDecoder {

    @Override
    public String decodeText(String binaryData, HuffmanNode root) {

        StringBuilder decodedText = new StringBuilder();

        HuffmanNode currentNode = root;

        for (int i = 0; i < binaryData.length(); i++) {
            char bit = binaryData.charAt(i);

            if (bit == '0') {
                currentNode = currentNode.getLeft();
            } else if (bit == '1') {
                currentNode = currentNode.getRight();
            }

            if (currentNode.getLeft() == null && currentNode.getRight() == null) { //leaf node ise

                decodedText.append(currentNode.getCharacter()); //decodedText'e bulunan charı ekledik
                currentNode = root; //tekrar roota döndük, sonraki charı decode etcez
            }
        }
        return decodedText.toString();
    }
}
