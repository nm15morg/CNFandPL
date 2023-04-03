import java.io.File;
import java.util.Scanner;
public class cnfConverter{
    //Replace all biconditionals(a = b) with (a > b) & (b > a)
    //Replace all implies(a > b) with ~a | b
    //Apply DeMorgan's Law to move not(~) inwards
    //Distribute or(|) over and(&)

    public static void main(String[]args) {
        try {
            File file = new File("./placeHolder.txt");
            Scanner sc = new Scanner(file);
            String input = sc.nextLine();
            input = input.replaceAll(" ", "");
            System.out.println(input);


            sc.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
} 