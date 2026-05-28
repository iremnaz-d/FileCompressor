package SRPs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrequencyAnalyzer {

    public Map<Character, Integer> getFrequencyMap(String filePath) throws IOException {


        Map<Character, Integer> map = new HashMap<>();
        BufferedReader reader = new FileHandler().getTextReader(filePath);

        int c = 0;
        while((c = reader.read()) != -1){
            char character = (char) c;

            int before = 0;
            try{
                before = map.get(c); //önceki sayısını alıyo
            } catch(NullPointerException ignored){ //önceden yoksa before = 0
            }

            map.put(character, before+1);
        }
        return map;
    }
}
