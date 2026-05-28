package SRPs;

import Interfaces.IFileReader;
import Interfaces.IFileWriter;

import java.io.*;
import java.util.Map;

public class FileHandler implements IFileReader, IFileWriter {

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

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {

            oos.writeObject(frequencyMap); //frequencyMap dosyanın en başına gömülür

            byte[] packedBytes = packBits(bitString); //packlenmiş byte dizisini aldık

            // 3. Geçerli bit uzunluğunu yaz (Sondaki padding/boşluk bitlerini ayırt edebilmek için kritik)
            oos.writeInt(bitString.length());

            // 4. Paketlenmiş byte'ları yaz
            oos.writeObject(packedBytes);

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
                bytes[i / 8] |= (1 << (7 - (i % 8)));
            }
        }
        return bytes;
    }
}
