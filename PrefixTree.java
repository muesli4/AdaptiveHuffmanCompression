
public abstract class PrefixTree {

    // parent of this tree
    PrefixTree parent;

    // weight of this tree (w)
    int weight;
    
    // number of this tree (l)
    int number;
    
    /**
        Constructor with parent.
    */
    public PrefixTree(PrefixTree parent, int weight, int number) {
    
        this.parent = parent;
        this.weight = weight;
        this.number = number;
    }

    /**
        Constructor without parent.
    */
    public PrefixTree(int weight, int number) {
    
        this.parent = null;
        this.weight = weight;
        this.number = number;
    }

    /**
        Check whether this tree has a parent.
    */
    public boolean isRoot() {
    
        return parent == null;
    }
    
}

