import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class plResolution {
    public static void main(String[] args) {
        try {
            String nextLine;
            String[] inputArray;
            String searchString;
            String[] line;
            Pattern pattern = Pattern.compile(",");
            File file = new File("./plExample.txt");
            Scanner sc = new Scanner(file);
            ArrayList<String[]> inputLines = new ArrayList<String[]>();
            while (sc.hasNext()) {
                nextLine = sc.nextLine();
                nextLine = nextLine.replaceAll(" ", "");
                inputArray = pattern.split(nextLine);
                inputLines.add(inputArray);
            }

            int i = 0;
            while (i < inputLines.size() - 1) {
                line = inputLines.get(i);
                for (String element : line) {
                    if (element.length() == 1) {
                        searchString = "~" + element;
                    } else {
                        searchString = element.substring(1);
                    }
                    for (int j = i + 1; j < inputLines.size(); j++) {
                        if (Arrays.asList(inputLines.get(j)).contains(searchString)) {
                            inputLines.set(i, mergeLines(line, inputLines.get(j)));
                            inputLines.remove(j);
                            j--;

                        }
                    }
                }
                i++;
            }

            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String[] mergeLines(String[] lineOne, String[] lineTwo) {
        List<String> returnList = Arrays.asList(lineOne);
        String logicalOpp;
        for (String addString : lineTwo) {
            if(addString.length() == 1){
                logicalOpp = "~" + addString;
            }else{
                logicalOpp = addString.substring(1);
            }

            if(returnList.contains(logicalOpp)){
                returnList.remove(logicalOpp);
            }else{
                if(!returnList.contains(addString)){
                    returnList.add(addString);
                }
            }
        }
        Collections.sort(returnList);
        for (String string : returnList) {
            System.out.println(string);
        }
        String[] returnArray = (String[]) returnList.toArray();
        return returnArray;
    }

}
