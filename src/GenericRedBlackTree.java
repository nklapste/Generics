/**
 * Lab 4: Generics <br />
 * The {@code GenericRedBlackTree} class <br />
 * Reference: <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree">
 *              https://en.wikipedia.org/wiki/Red%E2%80%93black_tree
 *            </a>
 */
public class GenericRedBlackTree<K extends Comparable<K>, V> {
    /**
     * Rotate globals
     * rotate left: true
     * rotate right: false
     */
    private static boolean ROTATE_LEFT = true;
    private static boolean ROTATE_RIGHT = false;

    /**
     * Root node of the red black tree
     */
    private Node root = null;

    /**
     * Size of the tree
     */
    private int size = 0;

    /**
     * Recursively search the tree to find if a key is contained within RBT
     * and return the key's node if key is found or null if key not found
     *
     * @param key {@code int} the Key to be found
     * @return {@code Node}
     */
    private Node getNode(Node node, K key) {
        // end of a branch pull out
        if (node == null || node.key == null)
            return null;

        // found the value pull out
        if (node.key.equals(key)) {
            return node;
        } else {
            // recursively check the left branches then
            // if not found check the right branches
            Node node_out = getNode(node.lChild, key);
            if (node_out != null) {
                return node_out;
            } else {
                return getNode(node.rChild, key);
            }
        }
    }

    /**
     * Search for the node by key, and return the corresponding value
     * @param key       {@code K} the key for searching
     * @return          {@code V} the value of the node, or {@code NULL} if not found
     */
    public V find(K key) {
        // Lab 4 Part 3-1 -- find an element from the tree
        Node node = getNode(root, key);
        if (node == null){
            return null;
        } else {
            return node.value;
        }
    }


    /**
     * Rotate the node left
     *
     * @param node {@code Node} the node to be rotated about
     */
    private void rotateTree(Node node, boolean direction) {
        Node parent = node.parent;
        Node grandParent = parent.parent;

        if (grandParent == null) {
            root = node;
        } else {
            if (parent.isLeftChild())
                grandParent.lChild = node;
            else
                grandParent.rChild = node;

        }
        node.parent = grandParent;
        parent.parent = node;

        if (direction == ROTATE_LEFT) {
            parent.rChild = node.lChild;
            if (node.lChild != null)
                node.lChild.parent = parent;

            node.lChild = parent;
        } else {
            parent.lChild = node.rChild;
            if (node.rChild != null)
                node.rChild.parent = parent;

            node.rChild = parent;
        }
    }

    /**
     * Check the node ensuring that all color cases are covered correctly
     *
     * @param node {@code Node} the node to be checked for color validity
     */
    private void fixInsertColor(Node node) {
        // case 1: if node is root set color to black and return
        if (node == root) {
            node.color = Node.BLACK;

        } else if (node.parent.color == Node.RED) {
            Node parent = node.parent;
            Node grandparent = parent.parent;
            Node uncle;

            if (parent.isLeftChild()) {
                uncle = grandparent.rChild;
            } else {
                uncle = grandparent.lChild;
            }

            // case 3
            if (parent.color == Node.RED && uncle.color == Node.RED) {
                parent.color = Node.BLACK;
                uncle.color = Node.BLACK;
                grandparent.color = Node.RED;
                fixInsertColor(grandparent);
                return;
            }

            // case 4
            if (parent.color == Node.RED && uncle.color == Node.BLACK) {
                if (node.isRightChild() && parent.isLeftChild()) {
                    rotateTree(node, ROTATE_LEFT);
                    node = node.lChild;
                } else if (node.isLeftChild() && parent.isRightChild()) {
                    rotateTree(node, ROTATE_RIGHT);
                    node = node.rChild;
                }
            }

            parent = node.parent;
            parent.color = Node.BLACK;
            grandparent.color = Node.RED;

            // case 5
            if (node.isLeftChild() && parent.isLeftChild())
                rotateTree(parent, ROTATE_RIGHT);
            else
                rotateTree(parent, ROTATE_LEFT);
        }
    }

    /**
     * Insert an element to the tree
     * @param key       {@code K} the key of the new element
     * @param value     {@code V} the value of the new element
     */
    public void insert(K key, V value) {
        // Lab 4 Part 3-2 -- insert an element into the tree
        Node curNode = root;
        while (curNode != null && curNode.key != null) {
            // go left if less than current node
            if (key.compareTo(curNode.key)==-1) {
                curNode = curNode.lChild;
                // go right if greater than current node
            } else if (key.compareTo(curNode.key)==1) {
                curNode = curNode.rChild;
                // update values of already key present
            } else {
                curNode.value = value;
                fixInsertColor(curNode);
                return;
            }

        }

        Node node = new Node(key, value);
        if (size == 0) {
            root = node;
        } else {
            Node parent = curNode.parent;
            node.parent = parent;
            if (curNode.isLeftChild()) {
                parent.lChild = node;
            } else {
                parent.rChild = node;
            }
        }
        size++;
        // initialize new node as red
        node.color = Node.RED;
        // fix red black trees color
        fixInsertColor(node);
    }

    /**
     * Check the node ensuring all colors are correctly set after a deletion
     * @param node
     */
    public void  fixDelColor(Node node){
        // case 1
        if (node.parent == null){
            node.color = Node.BLACK;
            return;
        }

        //case 2
        Node sibling;
        if (node.isLeftChild()){
            sibling = node.parent.rChild;
        } else {
            sibling = node.parent.lChild;
        }
        if (sibling.color == Node.RED){
            sibling.parent.color = Node.RED;
            sibling.color = Node.BLACK;

            // rotate sibling L/R if node is L/R child
            if(node.isLeftChild()){
                rotateTree(sibling, ROTATE_LEFT);
            } else {
                rotateTree(sibling, ROTATE_RIGHT);
            }

            // update sibling to nodes new sibling
            if (node.isLeftChild()){
                sibling = node.parent.rChild;
            } else {
                sibling =  node.parent.lChild;
            }
        }

        if(sibling.key == null){
            // case 3
            if (node.parent.color == Node.BLACK) {
                sibling.color = Node.RED;
                fixDelColor(node.parent);
            }

            //case 4
            if (node.parent.color == Node.RED) {
                sibling.color = Node.RED;
                node.parent.color = Node.BLACK;
            }
        } else {
            // case 3
            if (node.parent.color == Node.BLACK && sibling.color == Node.BLACK && sibling.lChild.color == Node.BLACK && sibling.rChild.color == Node.BLACK) {
                sibling.color = Node.RED;
                fixDelColor(node.parent);
            }

            //case 4
            if (node.parent.color == Node.RED && sibling.color == Node.BLACK && sibling.lChild.color == Node.BLACK && sibling.rChild.color == Node.BLACK) {
                sibling.color = Node.RED;
                node.parent.color = Node.BLACK;
            }
        }

        // case 5
        if(sibling.color == Node.BLACK){
            sibling.color = Node.RED;
            if(node.isLeftChild() && sibling.lChild.color == Node.RED && sibling.rChild.color == Node.BLACK){
                sibling.lChild.color = Node.BLACK;
                rotateTree(sibling.lChild, ROTATE_RIGHT);
            }
            if(node.isRightChild() && sibling.lChild.color == Node.BLACK && sibling.rChild.color == Node.RED){
                sibling.rChild.color = Node.BLACK;
                rotateTree(sibling.rChild, ROTATE_LEFT);
            }
        }

        // case 6
        sibling.color = node.parent.color;
        node.parent.color = Node.BLACK;

        if (node.isLeftChild()){
            if (sibling.rChild != null && sibling.rChild.key != null){
                sibling.rChild.color = Node.BLACK;
                rotateTree(sibling, ROTATE_LEFT);
            }
        }

        if (node.isRightChild()){
            if (sibling.lChild != null && sibling.lChild.key != null){
                sibling.lChild.color = Node.BLACK;
                rotateTree(sibling, ROTATE_RIGHT);
            }

        }

    }

    /**
     * Get the "leftmost" with a Nodes subtree
     * @param node {@code Node} Node to act as root to traverse to leftmost Node
     * @return
     */
    public Node getLeftMostNode(Node node){
        if (node.lChild != null && node.lChild.key != null){
            return getLeftMostNode(node.lChild);
        } else {
            return node;
        }
    }


    /**
     * Swap two Nodes (by values)
     * @param n1 {@code Node}
     * @param n2 {@code Node}
     */
    public void swapNode(Node n1, Node n2){

          K keyT = n1.key;
          V valueT = n1.value;
          boolean colorT = n1.color;

          n1.key = n2.key;
          n1.value = n2.value;
          n1.color = n2.color;

          n2.key = keyT;
          n2.value = valueT;
          n2.color = colorT;
          n2 = n1; // TODO check if useless in scope

    }

    /**
     * Follow Red Black tree behaviour in removing nodes
     * @param node
     */
    public V removeNode(Node node){
        if ((node.lChild != null && node.lChild.key != null) && (node.rChild != null && node.rChild.key != null)) {
            Node larger = getLeftMostNode(node.rChild);
            // larger needs one null child
            assert (larger.lChild.key == null) || (larger.rChild.key == null);

            //swapNode node with larger
            swapNode(larger, node);
            node = larger;
        }

        Node child;
        // set nodes new NIL child: set child to NIL's sibling (child may also be nill)
        if(node.lChild != null && node.lChild.key != null){
            child = node.lChild;
        } else {
            child = node.rChild;
        }

        // if node is is not root
        if (node.parent != null && node != root){
            //link child to parent
            child.parent = node.parent;

            if(node.isLeftChild()){
                node.parent.lChild = child;
            } else {
                node.parent.rChild = child;
            }
            // else set child to root
        } else if(child == null || child.key == null){
            root = null;
        } else {
            root = child;
            child.parent = null; //todo: ADDED CHECK
        }

        // if nodes color is black
        if (node.color == Node.BLACK){
            // if child's color is red  set child's color to black
            if (child.color == Node.RED){
                child.color = Node.BLACK;
            } else {
                // fix child's color
                 fixDelColor(child);
            }
        }
        return node.value;
    }


    /**
     * Remove an element from the tree
     * @param key       {@code K} the key of the element
     * @return          {@code V} the value of the removed element
     */
    public V remove(K key) {
        // TODO: Lab 4 Part 3-3 -- remove an element from the tree
        //validate that key is present in tree
        Node node = getNode(root, key);
        if(node != null){
            // iterate size down
            size--;
            //remove element
            return removeNode(node);
        } else {
            return null;
        }
    }


    /**
     * Get the size of the tree
     * @return          {@code int} size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * Recurse down the tree and generate a string describing it
     *
     * @return {@code treeString} string format of the tree
     */
    public String toStringRecursive(Node node) {
        // end of a branch or null value pull out and don't record
        if (node == null || node.value == null) {
            return "N";
        } else {
            // recursively get the left and right child's strings and return them
            return String.format(
                    "%s={%s%s}",
                    node.toString(),
                    toStringRecursive(node.lChild),
                    toStringRecursive(node.rChild)
            );
        }
    }


    /**
     * Cast the tree into a string
     * @return          {@code String} Printed format of the tree
     */
    @Override public String toString() {
        // Lab 4 Part 3-4 -- print the tree, where each node contains both value and color
        // You can print it by in-order traversal
        return toStringRecursive(root);
    }

    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {

//        this fails
//        1 Insert: 74
//        2 Insert: 15
//        3 Insert: 100


        GenericRedBlackTree<Integer, String> rbt = new GenericRedBlackTree<Integer, String>();

//        int TEST_CASE_SIZE = 3;
//        for (int i = 0; i < TEST_CASE_SIZE; i++) {
//            keys[i] = (int) (Math.random() * 200);
//            System.out.println(String.format("%2d Insert: %-3d ", i+1, keys[i]));
//            rbt.insert(keys[i], "\"" + keys[i] + "\"");
//        } // for (int i = 0; i < 10; i++)

        // TODO SPECIFIC FAIL TEST
//        int TEST_CASE_SIZE = 3;
//        int[]keys = new int[]{74, 15, 100};
        int TEST_CASE_SIZE = 4;
        int[] keys = new int[]{77, 2, 74, 55};
        for (int i = 0; i < TEST_CASE_SIZE; i++) {
            System.out.println(String.format("%2d Insert: %-3d ", i+1, keys[i]));
            rbt.insert(keys[i], "\"" + keys[i] + "\"");
        } // for (int i = 0; i < 10; i++)

        //NOTE CHANGED TO TRUE TO FIX STATIC REFERENCE ERROR
        assert rbt.root.color == true;
        System.out.println(rbt.root);                   // This helps to figure out the tree structure
        System.out.println(rbt);

        // test finding keys
        System.out.println("Testing find:");
        for (int i = 0; i < TEST_CASE_SIZE; i++) {
            System.out.print("Finding key: ");
            System.out.print(keys[i]);

            System.out.print("..  Key Found! ");
            System.out.println(rbt.find(keys[i]));
            assert Integer.parseInt(rbt.find(keys[i])) == keys[i];
        }
        assert rbt.find(-3) == null;
        System.out.println();

        //test delete functionality
        System.out.println("Testing Delete");
        for (int i = 0; i < TEST_CASE_SIZE; i++) {
            System.out.println(String.format("%2d Delete: %3d(%s)", i+1, keys[i], rbt.remove(keys[i])));
            System.out.println(rbt);
//            if ((i + 1) % 5 == 0) {
//                System.out.println(rbt);
//            } // if ((i + 1) % 5 == 0)
        } // for (int i = 0; i < 10; i++)
    }


    /**
     * The {@code Node} class for {@code GenericRedBlackTree}
     */
    private class Node {
        public static final boolean BLACK = true;
        public static final boolean RED = false;

        public K key;
        public V value;
        public boolean color = BLACK;
        public Node parent = null, lChild = null, rChild = null;

        public Node(K key, V value) {                   // By default, a new node is black with two NIL children
            this.key = key; //tODO
            this.value = value;
            if (value != null) {
                lChild = new Node(null, null);          // And the NIL children are both black
                lChild.parent = this;
                rChild = new Node(null, null);
                rChild.parent = this;
            }
        }
        /**
         * check if a node is a parents left child
         *
         * @return {@code boolean}
         */
        public boolean isLeftChild() {
            return this == this.parent.lChild;
        }

        /**
         * check if a node is a parents right child
         *
         * @return {@code boolean}
         */
        public boolean isRightChild() {
            return this == this.parent.rChild;
        }



        /**
         * Print the tree node: red node wrapped by "<>"; black node by "[]"
         * @return          {@code String} The printed string of the tree node
         */
        @Override public String toString() {
            if (value == null)
                return "";
            return (color == RED) ? "<" + value + "(" + key + ")>" : "[" + value + "(" + key + ")]";
        }
    }

}
