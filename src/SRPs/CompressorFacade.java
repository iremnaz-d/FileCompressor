package SRPs;

import DataStructures.HuffmanNode;
import Interfaces.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class CompressorFacade { //oop beni çok değiştirdi

    private final IFileReader reader;
    private final IFileWriter writer;
    private final ITreeBuilder treeBuilder;
    private final IEncoder encoder;
    private final IDecoder decoder;
    private final FrequencyAnalyzer frequencyAnalyzer;
    private final IFileCalculator calculator;

    public CompressorFacade(IFileReader reader, IFileWriter writer, ITreeBuilder treeBuilder, IEncoder encoder, IDecoder decoder, FrequencyAnalyzer frequencyAnalyzer, IFileCalculator calculator) {
        this.calculator = calculator;
        this.reader = reader;
        this.writer = writer;
        this.treeBuilder = treeBuilder;
        this.encoder = encoder;
        this.decoder = decoder;
        this.frequencyAnalyzer = frequencyAnalyzer;
    }

    public double getFileSizeKB(String path){
        return this.calculator.getFileSizeKB(path);
    }

    public double getCompressionEfficiency(String inputPath, String outputPath){
        return this.calculator.getCompressionEfficiency(inputPath,outputPath);
    }

    public String verifyCompression(String inputFilePath, String compressedFilePath, String decompressedFilePath){
        return this.calculator.verifyCompression(inputFilePath,compressedFilePath,decompressedFilePath);
    }

    public void compress(String inputPath, String outputPath) throws IOException {

        Map<Character, Integer> frequencyMap = this.frequencyAnalyzer.getFrequencyMap(inputPath); //frequencyMap oluşturulur

        HuffmanNode root = this.treeBuilder.buildTree(frequencyMap); //tree oluşturulur, root alınır

        Map<Character, String> codeMap = new HashMap<>();
        this.encoder.generateCodeMap(root, "", codeMap); //codeMap oluşturulur

        String encodedText = this.encoder.encodeText(inputPath, codeMap); //dosyanın codeMap'e göre yazılmış hali oluşturulur

        this.writer.writeCompressedFile(outputPath, frequencyMap, encodedText);
    }

    public void decompress(String compressedPath, String decompressedPath){
        try (ObjectInputStream ois = this.reader.getBinaryReader(compressedPath)) {
            @SuppressWarnings("unchecked")
            Map<Character, Integer> frequencyMap = (Map<Character, Integer>) ois.readObject(); //dosyanın başındaki frequencyMapi alıyoruz

            HuffmanNode root = this.treeBuilder.buildTree(frequencyMap); //aynı tree oluşturulur

            int bitLength = ois.readInt();
            byte[] packedBytes = (byte[]) ois.readObject();
            String bitString = unpackBits(packedBytes, bitLength);

            String decodedText = this.decoder.decodeText(bitString, root); //decode

            try (BufferedWriter bw = this.writer.getTextWriter(decompressedPath)) {
                bw.write(decodedText); //decoded file yazıldı
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    private String unpackBits(byte[] packedBytes, int validBitLength) {
        StringBuilder bitString = new StringBuilder();

        for (int i = 0; i < validBitLength; i++) { //padding bitleri almadan okuma yapıyoruz
            int byteIndex = i / 8;           // kaçıncı byte
            int bitIndex = i % 8;            // byteın kaçıncı biti
            int shiftAmount = 7 - bitIndex;

            if ((packedBytes[byteIndex] & (1 << shiftAmount)) != 0) { //biti kaydırıp 1mi diye bakıyoruz
                bitString.append('1');
            } else {
                bitString.append('0');
            }
        }
        return bitString.toString();
    }
}
