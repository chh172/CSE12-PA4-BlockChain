public class TritonBlock {
	/*Class variables, all the attributes of the block*/
    private int index;
    private long timestamp;
    private TritonData data;
    private String prev_hash;
    private String self_hash;

    /*Constructor, builds a block with passed in variables, then creates a hash for curr block*/
    public TritonBlock(int index, long timestamp, TritonData data, String prev_hash){
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.prev_hash = prev_hash;
        if (this.data.getTransactions() != null) {
          this.self_hash = this.hashBlock();
        }
        else {
          this.self_hash = ""+1111;
        }
    }

    private String hashBlock(){
        int hash = 0;
        for (int i = 0; i < this.data.getTransactions().size() ;i++ ) {
          hash = 10 * hash + (int)this.data.getTransactions().get(i).charAt(0);
        }
        return ""+hash;
    }

    /*Get index*/
    public int getIndex(){
        return this.index;
    }

    /*Get timestamp*/
    public long getTimestamp(){
        return this.timestamp;
    }

    /*Get data block*/
    public TritonData getData(){
        return this.data;
    }

    /*Get previous hash*/
    public String getPrev_hash(){
        return this.prev_hash;
    }

    /*Get current hash*/
    public String getSelf_hash(){
        return this.self_hash;
    }

    /*Print the block*/
    public String toString(){
        return "\n"+"TritonBlock "+this.getIndex()+
        "\n"+"index: "+this.getIndex()+
        "\n"+"Timestamp: "+this.getTimestamp()+
        "\n"+"Prev Hash: "+this.getPrev_hash()+
        "\n"+"Hash: "+this.getSelf_hash()+
        "\n"+this.data.toString();
    }
}
