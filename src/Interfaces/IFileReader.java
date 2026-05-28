package Interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public interface IFileReader {

   BufferedReader getTextReader(String filePath) throws IOException;
   ObjectInputStream getBinaryReader(String filePath) throws IOException;
}
