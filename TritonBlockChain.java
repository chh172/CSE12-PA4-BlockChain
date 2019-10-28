import java.util.*;

public class TritonBlockChain {

	private static final String MINE_REWARD = "1";
    /*Blockchain clas variable*/
    private List<TritonBlock> blockchain;

    /*Constructor, takes in genesis block data to start the blockchain*/
    public TritonBlockChain(int index, long timestamp, TritonData data, String prev_hash) {
        this.blockchain = new ArrayList<TritonBlock>();
				TritonBlock genesis = new TritonBlock(index,timestamp,data,prev_hash);
				this.blockchain.add(genesis);
    }

    /*Makes the next block after the proof of work from mining is finished*/
    public TritonBlock makeNewBlock(TritonBlock lastBlock, TritonData newData) {
        return new TritonBlock(lastBlock.getIndex()+1, System.currentTimeMillis(), newData, lastBlock.getSelf_hash());
    }

    /*Mines the transaction and creates the block to add to the blockchain*/
    public boolean beginMine(List<String> curTransactions) {
        if (curTransactions.isEmpty()) {
        	return false;
        }
				else {
					int proofId = this.proofOfWork();
					String record = "Triton coined earned: "+ MINE_REWARD;
					curTransactions.add(record);
					TritonData newData = new TritonData(proofId,curTransactions);
					TritonBlock curBlock = this.makeNewBlock(this.blockchain.get(this.blockchain.size() - 1),newData);
					this.blockchain.add(curBlock);
					return true;
				}
    }

    /*Simple proof of work algorithm to prove cpu usage was used to mine block*/
    public int proofOfWork() {
			// TODO: GET THE LCM(13,lastProofId+1);
				int lastProofId = this.blockchain.get(this.blockchain.size() - 1).getData().getProofId();
				int workNum = lastProofId + 1;
				while (workNum % 13 != 0) {
					workNum++;
				}
				return workNum;
		}

    /*Prints current blockchain*/
    public String toString() {
        String work = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"\n"+
				"T R I T O N  B L O C K C H A I N"+"\n"+
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"\n";
				for (int i = 0;i < this.blockchain.size() ;i++ ) {
					work = work + this.blockchain.get(i).toString();
				}
				return work;
    }

    /*Validates each block in the chain looking for any hash pointer descrepancies, which can point to a tampering problem*/
    public boolean validateChain() {
        return this.blockchain.get(this.blockchain.size() - 2).getSelf_hash().equals(this.blockchain.get(this.blockchain.size() - 1).getPrev_hash());
    }
    /*Get blockchain*/
    public List<TritonBlock> getBlockchain() {
        return this.blockchain;
    }
}
