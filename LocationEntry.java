
public class LocationEntry {

    private PrefixTree t;
    private BitSet b

    public LocationEntry(PrefixTree t, BitSet b) {
    
        this.t = t;
        this.b = b;
    }

    public PrefixTree getPrefixTree() {
        return t;
    }
    
    public BitSet getBitSet() {
        return b;
    }
}

