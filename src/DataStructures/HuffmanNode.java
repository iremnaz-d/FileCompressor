package DataStructures;

public class HuffmanNode implements Comparable<HuffmanNode> {

    private char character;
    private int frequency;
    private HuffmanNode right;
    private HuffmanNode left;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public HuffmanNode(char character, int frequency, HuffmanNode right, HuffmanNode left) {
        this.character = character;
        this.frequency = frequency;
        this.right = right;
        this.left = left;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.frequency - node.frequency;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }
}
