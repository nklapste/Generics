import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GenericRedBlackTreeTest {
    private GenericRedBlackTree<Integer, String> rbt;
    private int TEST_CASE_SIZE = 10;
    private int[] keys = new int[TEST_CASE_SIZE];

    @Before public void setUp() {
        rbt = new GenericRedBlackTree<>();
        for (int i = 0; i < TEST_CASE_SIZE; i++) {
            keys[i] = (int) (Math.random() * 200);
            System.out.println(String.format("%2d Insert: %-3d ", i+1, keys[i]));
            rbt.insert(keys[i], "\"" + keys[i] + "\"");
        } // for (int i = 0; i < 10; i++)
    }

    @After public void tearDown() throws Exception {
    }

    /**
     * Convert a GenericRedBlack tree String value to a integer correctly
     * @param value {@code String}
     * @return {@code int}
     */
    private int convertValue(String value){
        return Integer.parseInt(value.replace("\"", ""));
    }

    @Test public void test_printRBT(){
        System.out.println(rbt);
    }

    @Test public void test_findOne() {
        assertEquals(keys[0], convertValue(rbt.find(keys[0])));
    }

    @Test public void test_findAll(){
        for (int i = 0; i < TEST_CASE_SIZE; i++) {
            assertEquals(keys[i], convertValue(rbt.find(keys[i])));
        }
    }

    @Test public void test_findFail(){
        assertEquals(rbt.find(-3), null);
    }

    @Test public void test_removeOne(){
        System.out.println(String.format("Removing key %d", keys[0]));
        System.out.println(String.format("Before: %s", rbt));

        assertEquals(keys[0], convertValue(rbt.remove(keys[0])));

        System.out.println(String.format("After: %s", rbt));
        System.out.println();
    }

    @Test public void test_removeAll(){
        for (int i = 0; i < TEST_CASE_SIZE; i++) {
            System.out.println(String.format("Removing key %d", keys[i]));
            System.out.println(String.format("Before: %s", rbt));

            // check if proper value has been removed
            assertEquals(keys[i], convertValue(rbt.remove(keys[i])));

            System.out.println(String.format("After: %s", rbt));
            System.out.println();
        }

        // check if rbt is empty
        assertEquals("N", rbt.toString());
    }

}