
public class Node {
    boolean isOperator;
    Node leftChild;
    Node rightChild;
    Node parent;
    boolean isNot;
    String content;

    public Node(boolean isOperator, String content){
        this.isOperator = isOperator;
        this.leftChild = null;
        this.rightChild = null;
        isNot = false;
        this.content = content;
        parent = null;
    }

    public Node(Node node, Node parent){
        this.isOperator = node.getIsOperator();
        this.content = node.getContent();
        this.leftChild = node.getLeftChild();
        this.rightChild = node.getRightChild();
        this.isNot = node.getNot();
        this.parent = parent;
        if(node.getLeftChild() != null){
            Node newLeftChild = new Node(node.getLeftChild(), this);
        }
        if(node.getRightChild() != null){
            Node newRightChild = new Node(node.getRightChild(), this);
        }
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

    public Node getParent(){
        return parent;
    }

    public void setIsOperator(boolean isOperator){
        this.isOperator = isOperator;
    }
}
