import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class plResolutionTest {
    public static void main(String[] args) {
        List<String> list1 = Arrays.asList("x", "~y", "z");
        List<String> list2 = Arrays.asList("~x", "y", "w");
        ArrayList<List<String>> fullList = new ArrayList<>();
        fullList.add(list1);
        fullList.add(list2);
        String searchString = "~x";
        List<String> testList = plResolution.mergeLines(list1, list2, searchString);
        // for (String string : testList) {
        //     System.out.println(string);
        // }
        //boolean output = plResolution.isTautology(testList);
        //System.out.println(output);

        ArrayList<String> fullListAsString = plResolution.convertToString(fullList);
        for (String string : fullListAsString) {
            System.out.println(string);
        }
    }
}
