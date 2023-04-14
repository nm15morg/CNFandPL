import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class plResolution{
    public static void main(String[] args) {
        File file = new File(".///logic_test_cases//easy3_CNF.txt");
        ArrayList<List<String>> printArray = new ArrayList<>();
        ArrayList<List<String>> inputLines = readInput(file);
        for (List<String> line : inputLines) {
            printArray.add(line);
        }
        propositionResolution(inputLines, printArray);
        ArrayList<String> printArrayAsString = convertToString(printArray);
        //System.out.println("Input Lines Size: " + inputLines.size());
        outputToFile(printArrayAsString);
    
    }

    public static ArrayList<List<String>> readInput(File myFile){
        ArrayList<List<String>> inputLines = new ArrayList<>();
        try{
            Scanner sc = new Scanner(myFile);
            Pattern pattern = Pattern.compile(",");
            while(sc.hasNext()){
                String nextLine = sc.nextLine();
                nextLine = nextLine.replaceAll(" ", "");
                List<String> inputArray = Arrays.asList(pattern.split(nextLine));
                inputLines.add(inputArray);
            }
            sc.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return inputLines;
    }

    public static ArrayList<List<String>> propositionResolution(ArrayList<List<String>> inputLines, ArrayList<List<String>> printArray){
        for (int i = 0; i < inputLines.size() - 1; i++) {
            List<String> line1 = inputLines.get(i);
            List<String> line2 = inputLines.get(i + 1);
            for (int j = 0; j < line1.size(); j++) {
                String searchString;
                if(line1.get(j).length() == 1){
                    searchString = "~" + line1.get(j);
                }else{
                    searchString = line1.get(j).substring(1);
                }
                for (int k = 0; k < line2.size(); k++) {
                    String checkString = line2.get(k);
                    if(checkString.equals(searchString)){
                        List<String> mergedLine = mergeLines(line1, line2, searchString);
                        inputLines.remove(line2);
                        if(isTautology(mergedLine)){
                            inputLines.remove(line1);
                        }else{
                            line1 = mergedLine;
                            printArray.add(line1);
                            i--;
                        }
                        break;
                    }
                }
            }
        }
        return printArray;
    }

    public static List<String> mergeLines(List<String> line1, List<String> line2, String searchString){
        String removeFromOne;
        List<String> returnList = new ArrayList<String>();
        for (String string: line2) {
            if(!string.equals(searchString)){
                returnList.add(string);
            }
        }
        if(searchString.length() == 1){
            removeFromOne = "~" + searchString;
        }else{
            removeFromOne = searchString.substring(1);
        }
        for (String string : line1) {
            if(!(returnList.contains(string) || string.equals(removeFromOne))){
                returnList.add(string);
            }
        }
        Collections.sort(returnList);
        return returnList;
    }

    public static ArrayList<String> convertToString(ArrayList<List<String>> printArray){
        ArrayList<String> outputList = new ArrayList<>();
        String eachLine = "";
        for (List<String> list : printArray) {
            Collections.sort(list);
            for (String string : list) {
                eachLine = eachLine + string + ",";
            }
            eachLine = eachLine.substring(0, eachLine.length() - 1);
            outputList.add(eachLine);
            eachLine = "";
        }
        return outputList;
    }

    public static boolean isTautology(List<String> mergedLine){
        for (String string : mergedLine) {
            String oppString;
            if(string.length() == 1){
                oppString = "~" + string;
            }else{
                oppString = string.substring(1);
            }
            for (String string2 : mergedLine) {
                if(string2.equals(oppString)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void outputToFile(ArrayList<String> outputList){
        Collections.sort(outputList);
        try{
            File myFile = new File("./plResolutionOutput.txt");
            FileWriter myWriter = new FileWriter("./plResolutionOutput.txt");
            for (String printString : outputList) {
                myWriter.write(printString + "\n");
            }

            myWriter.close();
            Scanner contradictionCheck = new Scanner(myFile);
            if(!contradictionCheck.hasNext()){
                FileWriter myWriter2 = new FileWriter("./plResolutionOutput.txt");
                myWriter2.write("Contradiction");
                myWriter2.close();
            }
            contradictionCheck.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}