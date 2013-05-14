import java.util.TreeSet;


/**
 * Node class for the lazy.
 */
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

    /**
     * Swap everything including the parent of the children, but do not swap number and weight.
     * @param q The node to swap with.
     */
    public void swapContents(Node q) {
        
        Node tmp = q.left;
        q.left = this.left;
        this.left = tmp;
        
        // also set parents
        if (left != null) {
            
            left.parent = this;
        }
        
        if (q.left != null) {
            
            q.left.parent = q;
        }
        
        tmp = q.right;
        q.right = this.right;
        this.right = tmp;
        
        // also set parents
        if (right != null) {
            
            right.parent = this;
        }
        
        if (q.right != null) {
            
            q.right.parent = q;
        }
        
        char c = q.symbol;
        q.symbol = this.symbol;
        this.symbol = c;
    }
    
    private static void printIndent(int indent) {
        
        for (int i = 0; i < indent; ++i) {
            System.out.print("    ");
        }
    }
    
    /**
     * Output the current node and all of its children.
     * @param indent The used indentation.
     */
    public void print(int indent, String repr) {
        
        printIndent(indent);
        
        System.out.println("node(" + number + ", " + weight +
        					   ", " + (symbol != 0?symbol:"0") + ", parent = " + (parent == null? "-" : parent.number) + ")" +
        					   ", repr = " + repr);
        
        if (left != null) left.print(indent + 1, repr + "0");
        if (right != null) right.print(indent + 1, repr + "1");
        
        
    }
    
    public void prettyPrint(int ident, boolean putLeft, TreeSet<Integer> verticalLines, String repr)
    {

    	if (left != null)
	        if (putLeft) {
	            
	            left.prettyPrint(ident + 1, true, verticalLines, repr + "0");
	        }
	        else {
	            
	            TreeSet<Integer> leftVerticalLines = new TreeSet<Integer>(verticalLines);
	
	            leftVerticalLines.add(ident - 1);
	            
	            left.prettyPrint(ident + 1, true, leftVerticalLines, repr + "0");
	        }
        


        
        for (int i = 0; i < ident - 1; ++i) {
            if (verticalLines.contains(i)) {
                System.out.print("|   ");
            }
            else {
                System.out.print("    ");
            }
        }
        
        if (ident >= 1) { 
            System.out.print((putLeft ? "," : "'") + "---");
        }
        
        System.out.println(number + "-" + weight + " " + (symbol != 0?symbol + " '" + repr + "'":""));
        
        if (right != null)
	        if (putLeft) {
	            
	            TreeSet<Integer> rightVerticalLines = new TreeSet<Integer>(verticalLines);
	            
	            rightVerticalLines.addAll(verticalLines);
	            rightVerticalLines.add(ident - 1);
	            
	            right.prettyPrint(ident + 1, false, rightVerticalLines, repr + "1");
	        }
	        else {
	            right.prettyPrint(ident + 1, false, verticalLines, repr + "1");
	        }
    }
}

