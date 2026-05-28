package Interfaces;

import DataStructures.HuffmanNode;
import java.util.Map;

public interface ITreeBuilder {

    HuffmanNode buildTree(Map<Character, Integer> frequencies);
}
