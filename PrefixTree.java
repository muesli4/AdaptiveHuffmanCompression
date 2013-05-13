import java.io.EOFException;
import java.io.IOException;
import java.util.BitSet;

// TODO outdeg = 0 or 2 ? always?

public class PrefixTree {

    
    private int currentNumber = 512;
    private Node[] nodes = new Node[513];

    public PrefixTree() {

        nodes[512] = new Node(null, 0, 512, (char)0);
    }

    private int getHighestNodeWithWeight(int weight) {
        
        for (int i = 512; nodes[i] != null && i >= 0; --i) {

            if (weight == nodes[i].weight) {
                return i;
            }
        }
        
        // TODO error
        return -1;
    }

    private void update(int nodeNumber, char c) {
    
        Node p = nodes[nodeNumber];
    
        // NYT
        if (nodeNumber == currentNumber)
        {
        	p.weight = 1;
            currentNumber = currentNumber - 1;

            // new symbol node
            p.right = new Node(p, 1, currentNumber, c);
            nodes[currentNumber] = p.right;
            
            currentNumber = currentNumber - 1;
            
            // new nyt node
            p.left = new Node(p, 0, currentNumber, (char)0);
            nodes[currentNumber] = p.left;
            
            // FIXME
            if (p.parent == null) {
            	return;
            }
            else {
            	p = p.parent;
            }
        }
        
        // 
        while (p.parent != null) {

        	// is not sibling to nyt
        	if (p.parent != nodes[currentNumber].parent) {
        		
        		Node q = nodes[getHighestNodeWithWeight(p.weight)];
        		
        		// p is not the highest numbered node in this block
        		if (q != p) {

        			// swap
        			p.swapContents(q);
        			
        			// continue with q
        			p = q;
        		}
        	}
        	
        	p.weight = p.weight + 1;
        	p = p.parent;
        }
        
        // increase weight of root node
        p.weight = p.weight + 1;
    }

    public void encode(char c, BitOutputStream bos) throws IOException {

        int nodeNumber = currentNumber;

        for (int i = 512; nodes[i] != null && i >= 0; --i) {

            // found leaf with symbol
            if (c == nodes[i].symbol && nodes[i].left == null) {

                nodeNumber = i;
                break;
            }
        }
        
        BitSet reverseEncoding = new BitSet();
        int length = 0;
        
        // found nyt node, append reverse encoding of c
        if (nodeNumber == currentNumber) {
            
            for (int i = 0; i < 8; ++i) {
                reverseEncoding.set(7 - i, (c & (1 << i)) != 0);
            }

            length = 8;
        }

        Node currentNode = nodes[nodeNumber];
        
        // ascend the tree unitl the root node is found and add encoding
        while (currentNode.parent != null) {
            if (currentNode.parent.right == currentNode) {
                reverseEncoding.set(length);
            }
            
            currentNode = currentNode.parent;
            length = length + 1;
        }
        
        // reverse encoding
        BitSet encoding = new BitSet(length);
        
        for (int i = 0; i < length; ++i) {
            if (reverseEncoding.get(length - i - 1)) {
                encoding.set(i);
            }
        }
        
        update(nodeNumber, c);
        
        bos.write(encoding, length);
    }

    public char decode(BitInputStream bis) throws IOException {
    
        // root node
        Node currentNode = nodes[512];
        
        while (currentNode.left != null) {
        
            int ch = bis.read();
            
            if (ch == -1) {
                throw new EOFException("missing data");
            }
            else if (ch == 0) {
                currentNode = currentNode.left;
            }
            else {
                currentNode = currentNode.right;
            }
        }
        
        // decode NYT character
        if (currentNode.number == currentNumber) {
            
            char c = 0;
            
            for (int i = 0; i < 8; ++i) {
            
            	int ch = bis.read();
            	
                if (ch == -1) {
                    throw new EOFException("missing data");
                }
                else if (ch != 0) {
                    c = (char) (c | (char)(1 << i));
                }
            }
            
            update(currentNumber, c);
            
            return c;
        }
        else {
        
            update(currentNode.number, currentNode.symbol);
        
            return currentNode.symbol;
        }
    }
}
