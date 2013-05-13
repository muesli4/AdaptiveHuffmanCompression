

public class Node {

    public Node parent;

    public int weight;
    
    public int number;

    public char symbol;
    
    public Node left = null;
    public Node right = null;

    public Node(Node parent, int weight, int number, char symbol) {
    
        this.parent = parent;
        this.weight = weight;
        this.number = number;
        this.symbol = symbol;
    }

	public void swapContents(Node q) {
		
		Node tmp = q.left;
		q.left = this.left;
		this.left = tmp;
		
		tmp = q.right;
		q.right = this.right;
		this.right = tmp;
		
		char c = q.symbol;
		q.symbol = this.symbol;
		this.symbol = c;
	}
}

