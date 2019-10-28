import java.util.ArrayList;
import java.util.List;

public class TritonData {

    private List<String> transactions;
    private int proofId;

    /**
     * Triton Data Constructor
     * @param None
     */
    public TritonData(){

    }

    /*Constructor if specific values are specified*/
    public TritonData(int proofId, List<String> transactions){
        this.proofId = proofId;
        this.transactions = transactions;
    }

    /*Get transactions*/
    public List<String> getTransactions() {
        return this.transactions;
    }

    /*Get proofId*/
    public int getProofId() {
        return this.proofId;
    }

    /*Print the data block*/
    public String toString(){
      String work ="DATA Start--------------------------------\n" + "Proof of work: "
      +this.getProofId()+ "\n";
      if (this.getTransactions() != null) {
        for (int i = 0;i < this.getTransactions().size() ;i++ ) {
          work = work + "Transaction "+i+"\n"+"Transaction Content: "+this.getTransactions().get(i)+"\n";
        }
      }
      work = work + "DATA End --------------------------------"+"\n";
      return work;
    }
}
