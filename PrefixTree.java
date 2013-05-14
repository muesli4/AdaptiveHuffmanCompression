import java.io.EOFException;
import java.io.IOException;
import java.util.BitSet;

// TODO outdeg = 0 or 2 ? always?

/**
 * 
 * A PrefixTree wrapper class, which provides basic operations for encoding, decoding, updating and printing.
 * 
 *
 */
public class PrefixTree {

    // the numbering source for nodes and NYT indicator
    private int currentNumber = 512;
    
    // for every node: nodes[node.number] == node
    private Node[] nodes = new Node[513];

    /**
     * Create new prefix tree with NYT as root node.
     */
    public PrefixTree() {

        nodes[512] = new Node(null, 0, 512, (char)0);
    }

    /**
     * Get some text representation of the tree.
     */
    public void print() {
    	
    	// print the root node recursively
    	nodes[512].print(0);
    }
    
    private int getMaxBlockIndex(int weight) {
        
        for (int i = 512; i >= currentNumber; --i) {
        	
            if (weight == nodes[i].weight) {
            	// since we count down the numbers, the first value found is the one with the biggest number 
            	return i;
            }
        }
        
        // error: but we will never reach it when called correctly
        return -1;
    }

    private void update(int nodeNumber, char c) {

        Node p = nodes[nodeNumber];

        // is p the NYT node?
        if (nodeNumber == currentNumber)
        {
        	p.weight = 1;
            currentNumber = currentNumber - 1;

            // put new symbol node right with weight 1
            p.right = new Node(p, 1, currentNumber, c);
            nodes[currentNumber] = p.right;
            
            currentNumber = currentNumber - 1;
            
            // put new nyt node left with weight 0
            p.left = new Node(p, 0, currentNumber, (char)0);
            nodes[currentNumber] = p.left;

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
        		
        		Node q = nodes[getMaxBlockIndex(p.weight)];
        		
        		// p is not the highest numbered node in this block
        		if (q != p) {

        			// swap contents, and also set parents accordingly
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

    /**
     * Encode a character and write it to the bit output stream.
     * @param c The character to encode.
     * @param bos The stream to write to.
     * @throws IOException
     */
    public void encode(char c, BitOutputStream bos) throws IOException {

        int nodeNumber = currentNumber;

        for (int i = 512; i >= currentNumber; --i) {

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
        
        // reverse the encoding
        BitSet encoding = new BitSet(length);
        
        for (int i = 0; i < length; ++i) {
            if (reverseEncoding.get(length - i - 1)) {
                encoding.set(i);
            }
        }
        
        update(nodeNumber, c);
                
        bos.write(encoding, length);
    }

    /**
     * Decode a character from the bit input stream.
     * @param bis The stream to read from.
     * @return The decoded character.
     * @throws IOException
     */
    public int decode(BitInputStream bis) throws IOException {
    
        // root node
        Node currentNode = nodes[512];
        
        // while we haven't reached a leaf
        while (currentNode.left != null) {
        
            int ch = bis.read();
            
            if (ch == -1) {
            	// end of stream
            	if (currentNode.number == 512) {
            		return -1;
            	}
            	else {
            		throw new EOFException("stream ended unexpected");
            	}
            }
            else if (ch == 0) {
                currentNode = currentNode.left;
            }
            else {
                currentNode = currentNode.right;
            }
        }
        
        // we found the node for NYT, decode the character
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
        // we found a leaf different from NYT
        else {
        
        	// make sure we save our symbol before we update
        	char c = currentNode.symbol;
        	
            update(currentNode.number, currentNode.symbol);
        
            return c;
        }
    }
}
