package Interfaces;

public interface IFileCalculator {

    double getFileSizeKB(String path);
    double getCompressionEfficiency(String inputPath, String outputPath);
    boolean compareFiles(String filePath1, String filePath2);
    boolean isCompressionEfficient(String originalFilePath, String compressedFilePath);
    String verifyCompression(String inputFilePath, String compressedFilePath, String decompressedFilePath);
}
