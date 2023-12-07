package Helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Texture {
    public int[] readIntArrayFromFile(String filePath) {
        List<Integer> resultList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] stringValues = line.split(",");
                for (String stringValue : stringValues) {
                    resultList.add(Integer.parseInt(stringValue.trim()));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("An error occurred: " + e.getMessage());
            return new int[0];
        }

        return resultList.stream().mapToInt(Integer::intValue).toArray();
    }



}
