package SRPs;

import Interfaces.IFileCalculator;
import Interfaces.IFileReader;
import Interfaces.IFileWriter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Map;

public class FileHandler implements IFileReader, IFileWriter, IFileCalculator {

    @Override
    public BufferedReader getTextReader(String filePath) {
        try {
            return new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ObjectInputStream getBinaryReader(String filePath) throws IOException {
        return new ObjectInputStream(new FileInputStream(filePath));
    }


    @Override
    public BufferedWriter getTextWriter(String filePath) throws IOException {
        return new BufferedWriter(new FileWriter(filePath));
    }

    @Override
    public void writeCompressedFile(String filePath, Map<Character, Integer> frequencyMap, String bitString) throws IOException {
        //bitString.length() int döndürüyo, o yüzden çokçok büyük dosyalar için bu ödev çalışmaz muhtemelen
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {

            oos.writeObject(frequencyMap); //frequencyMap dosyanın en başına gömülür

            byte[] packedBytes = packBits(bitString); //packlenmiş byte dizisini aldık

            oos.writeInt(bitString.length()); //dosyanın padding bitler olmadan bit uzunluğunu da yazdık

            oos.writeObject(packedBytes); //packedBytesı da yazdık

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    private byte[] packBits(String bitString) {
        int byteCount = (bitString.length() + 7) / 8; //kaç byte gerektiği
        byte[] bytes = new byte[byteCount];

        for (int i = 0; i < bitString.length(); i++) {
            if (bitString.charAt(i) == '1') {
                int byteIndex = i / 8; //kaçıncı byte
                int bitIndex = i % 8;  //byte'ın kaçıncı biti

                int shiftAmount = 7 - bitIndex;  //biti nereye koyacaksak 7-bitIndex kadar kaydırılması gerekiyo
                int bitMask = 1 << shiftAmount;  //sadece gereken bitin 1 olduğu bitMask (örn: soldan 3e konulacaksa 00100000)

                bytes[byteIndex] = (byte) (bytes[byteIndex] | bitMask); //orlayarak biti yazıyoz
            }
        }
        return bytes;
    }

    @Override
    public double getFileSizeKB(String path) {
        File file = new File(path);
        return  file.length() / 1024.0; //file.length() byte size döndürüyo o yüzden 1024e böldük
    }

    @Override
    public double getCompressionEfficiency(String inputPath, String outputPath) {
        File originalFile = new File(inputPath);
        File compressedFile = new File(outputPath);

        double originalSize = originalFile.length();
        double compressedSize = compressedFile.length();

        return ((originalSize - compressedSize) / originalSize) * 100; //kurtarılan alanı yüzdelik olarak döndürür
    }

    @Override
    public boolean compareFiles(String filePath1, String filePath2) {
        try {
            Path path1 = Paths.get(filePath1);
            Path path2 = Paths.get(filePath2);

            long mismatchIndex = Files.mismatch(path1, path2); //mismatch filelar aynıysa -1 döndürür

            if (mismatchIndex == -1L) {
                return true;
            }
            return false;
        } catch (IOException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    @Override
    public boolean isCompressionEfficient(String originalFilePath, String compressedFilePath) {
        double originalSize = this.getFileSizeKB(originalFilePath);
        double compressedSize = this.getFileSizeKB(compressedFilePath);
        return originalSize > compressedSize;
    }

    @Override
    public String verifyCompression(String inputFilePath, String compressedFilePath, String decompressedFilePath) {
        boolean isSame = this.compareFiles(inputFilePath,decompressedFilePath);
        boolean isCompressionEfficient = this.isCompressionEfficient(inputFilePath,compressedFilePath);

        if(isSame && isCompressionEfficient){
            return "Verification: SUCCESS (Decompressed matches original plain text and compressed file is smaller.)";
        }
        else if(isSame && !isCompressionEfficient){
            return "Verification: FAILURE (Compressed file is bigger than original file.)";
        }
        else if(!isSame && isCompressionEfficient){
            return "Verification: FAILURE (Decompressed does not match original plain text)";
        }
        else{
            return "Verification: FAILURE (Everything is wrong)";
        }
    }
}
