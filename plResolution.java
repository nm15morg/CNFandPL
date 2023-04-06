import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class plResolution {

  public static void main(String[] args) {
    try {
      String nextLine;
      String[] inputArray;
      String searchString;
      Pattern pattern = Pattern.compile(",");
      File file = new File(".///logic_test_cases//easy3_out.txt");
      Scanner sc = new Scanner(file);
      ArrayList<String[]> inputLines = new ArrayList<String[]>();
      while (sc.hasNext()) {
        nextLine = sc.nextLine();
        nextLine = nextLine.replaceAll(" ", "");
        inputArray = pattern.split(nextLine);
        inputLines.add(inputArray);
      }

      for (int i = 0; i < inputLines.size() - 1; i++) {
        String[] arr1 = inputLines.get(i);
        String[] arr2 = inputLines.get(i + 1);
        for (String string : arr1) {
          if (string.length() == 1) searchString =
            "~" + string; else searchString = string.substring(1);
          for (String string2 : arr2) {
            if (string2.equals(searchString)) {
              inputLines.set(i, mergeLines(arr1, arr2));
              inputLines.remove(arr2);
              i--;
              break;
            }
          }
        }
      }

      File myFile = new File("./plResolutionOutput.txt");
      FileWriter myWriter = new FileWriter("./plResolutionOutput.txt");
      for (String[] strings : inputLines) {
        Arrays.sort(strings);
        String printString = "";
        for (String string : strings) {
          printString = printString + ", " + string;
        }
        if (printString.length() > 0) {
          printString = printString.substring(2);
          myWriter.write(printString + "\n");
        }
      }

      myWriter.close();
      Scanner sc2 = new Scanner(myFile);
      if (!sc2.hasNextLine()) {
        FileWriter myWriter2 = new FileWriter("./plResolutionOutput.txt");
        myWriter2.write("Contradiction");
        myWriter2.close();
    }

      sc.close();
      sc2.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String[] mergeLines(String[] lineOne, String[] lineTwo) {
    List<String> returnList = new ArrayList<>(Arrays.asList(lineOne));
    String logicalOpp;
    for (String addString : lineTwo) {
      if (addString.length() == 1) {
        logicalOpp = "~" + addString;
      } else {
        logicalOpp = addString.substring(1);
      }

      if (returnList.contains(logicalOpp)) {
        returnList.remove(logicalOpp);
      } else {
        //System.out.println(addString);
        if (!returnList.contains(addString)) {
          returnList.add(addString);
        }
      }
    }
    String[] returnArray = returnList.toArray(new String[returnList.size()]);
    return returnArray;
  }
}
