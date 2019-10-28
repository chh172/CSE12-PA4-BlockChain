import org.junit.*;
import static org.junit.Assert.*;
public class HashtableTester {
	private HashTable testHashTable1;
	@Before
	public void setUp()
	{
		testHashTable1 = new HashTable(1);
	}
	@Test
	public void testInsert()
	{
		assertEquals("checking insert",true,testHashTable1.insert("abc"));
		assertEquals("Checking contains after insert",true,testHashTable1.contains("abc"));
	}
	@Test
	public void testDelete()
	{
     	testHashTable1.insert("abc");
		assertEquals("Checking delete",true,testHashTable1.delete("abc"));
		assertEquals("Checking contains after delete",false,testHashTable1.contains("abc"));
	}
	@Test
	public void testGetSize()
	{
		testHashTable1.insert("abc");
		testHashTable1.insert("pqr");
		testHashTable1.insert("xyz");
		assertEquals("Checking getSize",new Integer(3),new Integer(testHashTable1.getSize()));
	}
}
