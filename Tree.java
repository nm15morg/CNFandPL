import java.util.ArrayList;
import java.util.Stack;
public class Tree {
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
                    content = content + charStack.pop();
                }
                charStack.pop();
                holdList.add(createNode(content, holdList));
                charStack.add((char)(holdList.size() - 1));
            }
        }
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
                modifier = true;
            }else if(!Character.isDigit(currentChar)){
                if(modifier){
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
                }
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
            parentNode.setContent("|");
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

    }
}
