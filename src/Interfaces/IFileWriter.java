package Interfaces;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public interface IFileWriter {

    BufferedWriter getTextWriter(String filePath) throws IOException;
    void writeCompressedFile(String filePath, Map<Character, Integer> headerMap, String bitString) throws IOException;
}
