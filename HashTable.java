import java.util.*;
import java.io.*;
public class HashTable implements IHashTable {

	//You will need a HashTable of LinkedLists.

	private int nelems;  //Number of element stored in the hash table
	private int expand;  //Number of times that the table has been expanded
	private int collision;  //Number of collisions since last expansion
	private String statsFileName;     //FilePath for the file to write statistics upon every rehash
	private boolean printStats = false;   //Boolean to decide whether to write statistics to file or not after rehashing
  private LinkedList<String>[] workArray;
	private double loadFactor;
	private int longestChain;
	//private File statsFile;
	//You are allowed to add more :)

	/**
	 * Constructor for hash table
	 * @param Initial size of the hash table
	 */
	public HashTable(int size) {

		//Initialize
		this.nelems = 0;
		this.expand = 0;
		this.loadFactor = 0.0;
		this.workArray = new LinkedList[size];
		for (int i = 0; i < size ; i++) {
			workArray[i] = new LinkedList<String>();
		}
		this.statsFileName = "statFollowing.txt";
	}

	/**
	 * Constructor for hash table
	 * @param Initial size of the hash table
	 * @param File path to write statistics
	 */
	public HashTable(int size, String fileName){

		//Set printStats to true and statsFileName to fileName
		this.nelems = 0;
		this.expand = 0;
		this.loadFactor = 0.0;
		this.statsFileName = fileName;
		this.workArray = new LinkedList[size];
		for (int i = 0; i < size ; i++) {
			workArray[i] = new LinkedList<String>();
		}
	}

	@Override
	public boolean insert(String value) {
		if (value == null) { throw new NullPointerException();}
		if (this.contains(value)) {return false;}
		else {
			int imageOfInput = this.rehash(value);
			if (!workArray[imageOfInput].isEmpty()) {
				this.collision++;
			}
			this.nelems++;
			return workArray[imageOfInput].add(value);
		}
	}

	@Override
	public boolean delete(String value) {
		if (value == null) { throw new NullPointerException();}
		if (!this.contains(value)) {return false;}
		else {
			int imageOfInput = this.rehash(value);
			this.nelems--;
			return workArray[imageOfInput].remove(value);
		}
	}

	@Override
	public boolean contains(String value) {
		if (value == null) { throw new NullPointerException();}
		int imageOfInput = this.rehash(value);
		return workArray[imageOfInput].contains(value);
	}

	@Override
	public void printTable() {
		for (int i = 0; i < workArray.length ;i++ ) {
			String toPrint = i + ":";
			for (int j = 0; j < workArray[i].size() ;j++ ) {
				toPrint = toPrint + workArray[i].get(j) + ", ";
			}
			toPrint = toPrint.substring(0,toPrint.length() - 3);
			System.out.println(toPrint);
		}
	}

	@Override
	public int getSize() {
		return this.nelems;
	}
	private int hash(String value, int NumBuckets) {
		int c = value.charAt(0);
		int imageOfInput = (c * (c+3))% NumBuckets;
		return imageOfInput;
	}
  private int rehash(String value) {
    //TODO: map the input element to a corresponding image. !consider the initial case of size 0
		int count = 0;
		for (int i = 0;i < workArray.length ;i++ ) {
			if (!workArray[i].isEmpty()) {
				count++;
			}
		}
		loadFactor = (double)count/ workArray.length;
		if (loadFactor < 0.67 ) {
			return this.hash(value,workArray.length);
		}
		else {
			int longest = workArray[0].size();
			LinkedList<String>[] newWorkArray = new LinkedList[2 * workArray.length];
			for (int i = 0; i < 2 * workArray.length ; i++) {
				newWorkArray[i] = new LinkedList<String>();
			}
			for (int i = 0;i < workArray.length ; i++) {
				if (longest < workArray[i].size()) {
					longest = workArray[i].size();
				}
				for (int j = 0;j < workArray[i].size() ;j++ ) {
					if (!workArray[i].isEmpty()) {
						int newImage = this.hash(workArray[i].get(j),newWorkArray.length);
						newWorkArray[newImage].add(workArray[i].get(j));
					}
				}
			}
			this.printStatistics();
			System.out.println((int)expand + " resizes, load factor " + loadFactor + ", " + collision + " collisions, "+longestChain+" longest chain" + "\n");
			expand++;
			this.workArray = newWorkArray;
			this.collision = 0;
			return this.hash(value,newWorkArray.length);
		}
	}

	private void printStatistics() {
    try {
			BufferedWriter out = new BufferedWriter(new FileWriter(this.statsFileName,true));
			out.write(expand + " resizes, load factor " + loadFactor + ", " + collision + " collisions, "+longestChain+" longest chain" + "\n");
			out.close();
		}
		catch (IOException e) {
			System.out.println("exception occured" + e);
		}
	}
	public static void main(String[] args) {
		try {
			Scanner scan = new Scanner(new File("simple.txt"));
			HashTable table = new HashTable(10,"statFollow.txt");
			while (scan.hasNextLine()) {
				table.insert(scan.nextLine());
			}
			scan.close();
			table.printTable();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//TODO - Helper methods can make your code more efficient and look neater.
	//We expect AT LEAST the follwoing helper methods in your code
	//rehash() to expand and rehash the items into the table when load factor goes over the threshold.
	//printStatistics() to print the statistics after each expansion. This method will be called from insert/rehash only if printStats=true

}
