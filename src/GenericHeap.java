import java.util.Comparator;
import java.util.PriorityQueue;


class Entry<K extends Comparable<K>, V> implements Comparable<Entry<K,V>> {
    private K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(Entry<K, V> other) {
        return this.getKey().compareTo(other.getKey());
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }
}


/**
 * Lab 4: Generics <br />
 * The {@code GenericHeap} class
 */
public class GenericHeap<K extends Comparable<K>, V> extends PriorityQueue<Entry<K,V>>{

    /**
     * GenericHeap constructor
     */
    public GenericHeap(){
        super(new Comparator<>() {
            @Override
            public int compare(Entry<K, V> e1, Entry<K, V> e2) {
                return e1.getKey().compareTo(e2.getKey());
            }
        });
    }

    /**
     * Insert an new element to the heap
     * @param key       {@code K} the comparable key of the new element
     * @param value     {@code V} the actual value of the new element
     */
    public void insert(K key, V value) {
        // Lab 4 Part 2-1 -- GenericHeap, add new element
        this.add(new Entry<>(key, value));

    }

    /**
     * The heap sort procedure
     * @param array     {@code <E extends Comparable<E>>[]} the array to be sorted
     * @return          {@code <E extends Comparable<E>>[]} the sorted array
     */
    public static <E extends Comparable<E>> E[] heapSort(E[] array) {
        // Lab 4 Part 2-4 -- GenericHeap, return a sorted array

        GenericHeap<E, E> heap = new GenericHeap<>();

        // insert each item into the array
        for (E item : array){
            heap.insert(item, item);
        }

        for (int i=0; i<array.length; i++){
            Entry<E, E> entry = heap.poll();
            array[i] = entry.getKey();
        }
        return array;
    }

    /**
     * Main entry: test the HeapSort
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        // Sort an array of integers
        Integer[] numbers = new Integer[10];
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = (int) (Math.random() * 200);
        heapSort(numbers);
        for (int n: numbers)
            System.out.print(n + " ");
        System.out.println();

        // Sort an array of strings
        String[] strs = new String[10];
        for (int i = 0; i < strs.length; i++)
            strs[i] = String.format("%c", (int) (Math.random() * 26 + 65));
        heapSort(strs);
        for (String s: strs)
            System.out.print(s + " ");
        System.out.println();
    }

}
