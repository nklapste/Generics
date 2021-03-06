import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericRedBlackTreeTest {
    private GenericRedBlackTree<Integer, String> rbt;
    private int TEST_CASE_SIZE = 7;
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
        int actual_size = rbt.size();

        System.out.println(String.format("Removing key %d", keys[0]));
        System.out.println(String.format("Before: %s", rbt));

        assertEquals(keys[0], convertValue(rbt.remove(keys[0])));

        // check if size went down by 1
        actual_size--;
        assertEquals(actual_size, rbt.size());

        System.out.println(String.format("After: %s", rbt));
        System.out.println();
    }

    @Test public void test_removeAll(){
        // ensure that we don't repeat deleting keys would raise an error!
        List<Integer> deletedKeys = new ArrayList<>();
        int actual_size = rbt.size();

        for (int i = 0; i < TEST_CASE_SIZE; i++) {
            if (!deletedKeys.contains(keys[i])){
                System.out.println(String.format("Removing key %d", keys[i]));
                System.out.println(String.format("Before: %s", rbt));

                // check if proper value has been removed
                assertEquals(keys[i], convertValue(rbt.remove(keys[i])));

                // check if size went down by 1
                actual_size--;
                assertEquals(actual_size, rbt.size());
                System.out.println(String.format("After: %s", rbt));
                System.out.println();

                deletedKeys.add(keys[i]);
            }
        }

        // check if rbt is empty
        assertEquals("N", rbt.toString());
    }

}