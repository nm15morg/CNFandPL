import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
public class Tree {
    
    Node headNode;

    public Tree(String input){
        input = input.replaceAll(" ", "");
        char[] inputArray = input.toCharArray();
        Stack<Character> charStack = new Stack<Character>();
        ArrayList<Node> holdList = new ArrayList<Node>();
        for (int i = 0; i < inputArray.length; i++) {
            if(inputArray[i] != ')'){
                charStack.add(inputArray[i]);
            }else{
                String content = "";
                while(charStack.peek() != '('){
                    content = charStack.pop() + content;
                }
                charStack.pop();
                holdList.add(createNode(content, holdList));
                char placement = Character.forDigit(holdList.size() - 1, 10);
                charStack.add(placement);
            }
        }
        
        headNode = holdList.get(holdList.size() - 1);
        if(!charStack.isEmpty()){
            charStack.pop();
        }
        while(!charStack.isEmpty()){
            char not = charStack.pop();
            if(not == '~'){
                headNode.setNot(!headNode.getNot());
            }
        }
    }

    public Node getHeadNode(){
        return headNode;
    }

    public static boolean isOperator(char c){
        if(c == '|'){
            return true;
        }else if(c == '>'){
            return true;
        }else if(c == '&'){
            return true;
        }else if(c == '='){
            return true;
        }else{
            return false;
        }
    }

    public static Node createNode(String nodeContents, ArrayList<Node> nodePool){
        boolean modifier = false;
        Node oppNode = new Node(true, null);
        for (int i = 0; i < nodeContents.length(); i++) {
            char currentChar = nodeContents.charAt(i);
            if(currentChar == '~'){
                modifier = !modifier;
            }else if(isOperator(currentChar)){
                oppNode.setContent(String.valueOf(currentChar));
            }else if(!Character.isDigit(currentChar)){
                if(oppNode.getLeftChild() == null){
                    Node leftChild = new Node(false, "" + currentChar);
                    leftChild.setNot(modifier);
                    leftChild.setParent(oppNode);
                    oppNode.setLeftChild(leftChild);
                    leftChild.setNot(modifier);
                }else{
                    Node rightChild = new Node(false, "" + currentChar);
                    rightChild.setParent(oppNode);
                    oppNode.setRightChild(rightChild);
                    rightChild.setNot(modifier);
                }
                modifier = false;
            }else{
                if(modifier){
                    oppNode.setNot(true);
                    modifier = false;
                }
                Node myNode = nodePool.get(Character.getNumericValue(currentChar));
                if(oppNode.getLeftChild() == null){
                    oppNode.setLeftChild(myNode);
                }else{
                    oppNode.setRightChild(myNode);
                }
                myNode.setParent(oppNode);
            }
            
        }
        return oppNode;
    }

    public void biConditionalConversion(Node parentNode){
        Node leftChild = parentNode.getLeftChild();
        Node rightChild = parentNode.getRightChild();
        if(leftChild != null){
            biConditionalConversion(leftChild);
        }
        if(rightChild != null){
            biConditionalConversion(rightChild);
        }
        if(parentNode.getIsOperator() && parentNode.getContent().equals("=")){
            parentNode.setContent("&");
            leftChild = parentNode.getLeftChild();
            rightChild = parentNode.getRightChild();

            Node newLeft = new Node(true, ">");
            newLeft.setParent(parentNode);
            newLeft.setLeftChild(leftChild);
            leftChild.setParent(newLeft);
            parentNode.setLeftChild(newLeft);
            Node copyOfRight = new Node(rightChild, newLeft);
            newLeft.setRightChild(copyOfRight);

            Node newRight = new Node(true, ">");
            newRight.setParent(parentNode);
            newRight.setLeftChild(rightChild);
            rightChild.setParent(newRight);
            parentNode.setRightChild(newRight);
            Node copyOfLeft = new Node(leftChild, newRight);
            newRight.setRightChild(copyOfLeft);
        }


    }

    public void impliesConversion(Node parentNode){
        if(parentNode.getLeftChild() != null){
            impliesConversion(parentNode.getLeftChild());
        }
        if(parentNode.getRightChild() != null){
            impliesConversion(parentNode.getRightChild());
        }
        if(parentNode.getIsOperator() && parentNode.getContent().equals(">")){
            parentNode.getLeftChild().setNot(true);
            parentNode.setContent("|");
        }
    }

    public void applyDeMorgan(Node parentNode){
        Node leftChild = parentNode.getLeftChild();
        Node rightChild = parentNode.getRightChild();
        if(parentNode.getIsOperator() && parentNode.getNot()){
            leftChild.setNot(!leftChild.getNot());
            rightChild.setNot(!rightChild.getNot());
            if(parentNode.getContent().equals("&")){
                parentNode.setContent("|");
            }else{
                parentNode.setContent("&");
            }
            parentNode.setNot(false);
        }

        if(leftChild != null){
            applyDeMorgan(leftChild);
        }
        if(rightChild != null){
            applyDeMorgan(rightChild);
        }
    }

    public void distributionOverAnd(Node parentNode){
        Node leftChild = parentNode.getLeftChild();
        Node rightChild = parentNode.getRightChild();
        if(parentNode.getIsOperator() && parentNode.getContent().equals("|")){
            if((leftChild.getIsOperator() && leftChild.getContent().equals("&")) || (rightChild.getIsOperator() && rightChild.getContent().equals("&"))){
                if(leftChild.getContent().equals("&")){
                    Node rightSideOne = new Node(rightChild, leftChild);
                    Node rightSideTwo = new Node(rightChild, rightChild);
                    Node leftRight = new Node(leftChild.getRightChild(), rightChild);
                    parentNode.setContent("&");
                    leftChild.setContent("|");
                    rightChild.setContent("|");
                    rightChild.setIsOperator(true);
                    leftChild.setRightChild(rightSideOne);
                    rightChild.setLeftChild(leftRight);
                    rightChild.setRightChild(rightSideTwo);
                }else{
                    Node leftSideOne = new Node(leftChild, rightChild);
                    Node leftSideTwo = new Node(leftChild, leftChild);
                    Node rightLeft = new Node(rightChild.getLeftChild(), leftChild);
                    parentNode.setContent("&");
                    leftChild.setContent("|");
                    rightChild.setContent("|");
                    leftChild.setIsOperator(true);
                    rightChild.setLeftChild(leftSideOne);
                    leftChild.setRightChild(rightLeft);
                    leftChild.setLeftChild(leftSideTwo);
                }
            }
        }

        if(leftChild != null){
            distributionOverAnd(leftChild);
        }
        if(rightChild != null){
            distributionOverAnd(rightChild);
        }
    }

    public void printTree(Node parentNode){
        if(parentNode.getNot()){
            System.out.println("~" + parentNode.getContent());
        }else{
            System.out.println(parentNode.getContent());
        }
        if(parentNode.getLeftChild() != null){
            printTree(parentNode.getLeftChild());
        }
        if(parentNode.getRightChild() != null){
            printTree(parentNode.getRightChild());
        }
    }

    public String toOutput(Node parentNode){
        if(!parentNode.getIsOperator()){
            if(parentNode.getNot()){
                return "~" + parentNode.getContent();
            }else{
                return parentNode.getContent();
            }
        }

        return toOutput(parentNode.getLeftChild()) + parentNode.getContent() + toOutput(parentNode.getRightChild());

    }

    public ArrayList<String> alphabetizeString(String string){
        Pattern pattern = Pattern.compile("&");
        ArrayList<String> outputArray = new ArrayList<>();
        List<String> treeaAsLine = Arrays.asList(pattern.split(string));
        for (String myString : treeaAsLine) {
            Pattern pattern2 = Pattern.compile("|");
            List<String> eachLine = Arrays.asList(pattern2.split(myString));
            Collections.sort(eachLine);
            String outputString = "";
            for (String string2 : eachLine) {
                outputString = outputString + string2 + ",";
            }
            outputString = outputString.substring(0, outputString.length() - 1);
            outputString = "(" + outputString + ")";
            outputArray.add(outputString);
        }
        Collections.sort(outputArray);
        return outputArray;

    }
    
}
