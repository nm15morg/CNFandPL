
public class Node {
    boolean isOperator;
    Node leftChild;
    Node rightChild;
    String value;
    Node parent;
    boolean isNot;
    String content;

    public Node(boolean isOperator, String value){
        this.isOperator = isOperator;
        this.leftChild = null;
        this.rightChild = null;
        this.value = value;
        isNot = false;
        content = "";
        parent = null;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    public void setLeftChild(Node child){
        this.leftChild = child;
    }

    public void setNot(boolean isNot) {
        this.isNot = isNot;
    }

    public void setContent(String content){
        this.content = content;
    }

    public Node getLeftChild(){
        return leftChild;
    }

    public void setRightChild(Node rightChild){
        this.rightChild = rightChild;
    }

    public Node getRightChild(){
        return rightChild;
    }

    public String getContent(){
        return content;
    }

    public boolean getIsOperator(){
        return isOperator;
    }

    public boolean getNot(){
        return isNot;
    }
}
