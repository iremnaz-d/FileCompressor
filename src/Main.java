import SRPs.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new FileHandler();
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        TreeManager treeManager = new TreeManager();
        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();

        CompressorFacade facade = new CompressorFacade(fileHandler, fileHandler, treeManager, encoder, decoder, frequencyAnalyzer, fileHandler);

        Scanner scan = new Scanner(System.in);
        File inputFile = null;
        String inputFileName = "", outputFileName = "";

        mainLoop:
        while(true){
            System.out.println("1. test.txt\n2. wikipedia.txt\n3. Exit");
            System.out.print("Select a file: "); String choice = scan.nextLine();
            System.out.println();

            if(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")){
                System.out.println("Please enter a valid input."); continue;
            }

            switch (choice){
                case "1":
                    inputFile = new File("src/Files/test.txt");
                    break;
                case "2":
                    inputFile = new File("src/Files/wikipedia.txt");
                    break;
                case "3":
                    break mainLoop;
            }

            File compressedFile = new File("src/Files/encoded.bin");
            File decompressedFile = new File("src/Files/decoded.txt");

            System.out.println("Plain Text File: " + inputFile.getName());

            facade.compress(inputFile.getPath(), compressedFile.getPath());
            System.out.println("Compressed File: " + compressedFile.getName());

            facade.decompress(compressedFile.getPath(), decompressedFile.getPath());
            System.out.println("Decompressed File: " + decompressedFile.getName());

            System.out.printf("\nOriginal size: %.2f KB", facade.getFileSizeKB(inputFile.getPath()));
            System.out.printf("\nCompressed size: %.2f KB", facade.getFileSizeKB(compressedFile.getPath()));
            System.out.printf("\nEfficiency: %.2f%% space saved\n", facade.getCompressionEfficiency(inputFile.getPath(), compressedFile.getPath()));

            System.out.println(facade.verifyCompression(inputFile.getPath(), compressedFile.getPath(), decompressedFile.getPath()));
        }
    }
}
