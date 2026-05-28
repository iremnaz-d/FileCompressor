package SRPs;

import DataStructures.HuffmanNode;
import Interfaces.ITreeBuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public class TreeManager implements ITreeBuilder {

    @Override
    public HuffmanNode buildTree(Map<Character, Integer> frequencyMap) {

        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        Iterator<Character> itKey = frequencyMap.keySet().iterator();
        Iterator<Integer> itValue = frequencyMap.values().iterator();

        while(itKey.hasNext()){ //priority queue oluşturma
            HuffmanNode node = new HuffmanNode(itKey.next(), itValue.next());
            queue.add(node);
        }

        while(queue.size() > 1){
            HuffmanNode node1 = queue.poll();
            HuffmanNode node2 = queue.poll();
            HuffmanNode newNode = new HuffmanNode('-', node1.getFrequency() + node2.getFrequency());

            newNode.setLeft(node1);
            newNode.setRight(node2);
            queue.add(newNode);
        }

        return queue.poll(); //return root
    }
}
