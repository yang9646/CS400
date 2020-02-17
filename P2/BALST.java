//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project name: Balanced Search Trees
// Files: BALST.java, BALSTTest.java, BSTNode.java
// Course: CS 400 Fall 2019
//
// Author: Ye Ji Kim
// Email: ykim762@wisc.edu
// Lecture number: 001
// Lecturer's Name: Debra Deppeler
//
//////////////////////////// 80 columns wide ///////////////////////////////////
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 
 * Class to implement a BalanceSearchTree. Can be of type AVL or Red-Black. Note
 * which tree you implement here and as a comment when you submit.
 * 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {

    private BSTNode<K, V> root; // root of the tree
    private int numKeys; // number of nodes in the tree

    /**
     * This is the constructor of the BALST class.
     * Initialize the root to null and numKey to 0.
     */
    public BALST() {
        root = null;
        numKeys = 0;
    }

    /**
     * Returns the key that is in the root node of this BST. If root is null,
     * returns null.
     * 
     * @return key found at root node, or null
     */
    @Override
    public K getKeyAtRoot() {
        if (root != null) {
            return root.key;
        }
        return null;
    }

    /**
     * Tries to find a node with a key that matches the specified key. If a matching
     * node is found, it returns the key that is in the left child. If the left
     * child of the found node is null, returns null.
     * 
     * @param key A key to search for
     * @return The key that is in the left child of the found key
     * 
     * @throws IllegalNullKeyException if key argument is null
     * @throws KeyNotFoundException    if key is not found in this BST
     */
    @Override
    public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
        if (key == null) // key argument is null
            throw new IllegalNullKeyException();
        
        if (!contains(key)) // key is not found in this BST
            throw new KeyNotFoundException();
        
        // get the node with the given key 
        BSTNode<K, V> keyNode = get(key, root);

        return keyNode.left.key; // return its left 
    }

    /**
     * Tries to find a node with a key that matches the specified key. If a matching
     * node is found, it returns the key that is in the right child. If the right
     * child of the found node is null, returns null.
     * 
     * @param key A key to search for
     * @return The key that is in the right child of the found key
     * 
     * @throws IllegalNullKeyException if key is null
     * @throws KeyNotFoundException    if key is not found in this BST
     */
    @Override
    public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
        if (key == null) // key is null
            throw new IllegalNullKeyException();
        
        if (!contains(key)) // key is not found in this BST
            throw new KeyNotFoundException();
        
        // get the node with the given key
        BSTNode<K, V> keyNode = get(key, root);

        return keyNode.right.key; // return its right
    }

    /**
     * Returns the height of this BST. H is defined as the number of levels in the
     * tree.
     * 
     * If root is null, return 0 If root is a leaf, return 1 Else return 1 + max(
     * height(root.left), height(root.right) )
     * 
     * Examples: A BST with no keys, has a height of zero (0). A BST with one key,
     * has a height of one (1). A BST with two keys, has a height of two (2). A BST
     * with three keys, can be balanced with a height of two(2) or it may be linear
     * with a height of three (3) ... and so on for tree with other heights
     * 
     * @return the number of levels that contain keys in this BINARY SEARCH TREE
     */
    @Override
    public int getHeight() {
        return getHeight(root); // call private helper method
    }

    /**
     * This is the private helper method to find the height of given node recursively.
     * 
     * @param current - current node that we are looking for its height
     * @return height of the node
     */
    private int getHeight(BSTNode<K, V> current) {
        int height = 1; // height of root
        int right = 0;
        int left = 0;

        if (current == null)
            return 0;
        if (current.right != null)
            right = getHeight(current.right); // height of right part
        if (current.left != null)
            left = getHeight(current.left); // height of left part

        // compare the height of left and right and add bigger one
        if (right > left)
            height += right;
        else
            height += left;

        return height;
    }

    /**
     * This is the method to looking for the given node's balance factor.
     * 
     * @param node - node that we want to look for its balance factor
     * @return balance factor of node
     */
    private int balancefactor(BSTNode<K, V> node) {
        // initialize left and right heights as 0
        int leftHeight = 0;
        int rightHeight = 0;
        
        if (node == null)
            return 0;
        if (node.left != null)
            leftHeight = getHeight(node.left); // get left height
        if (node.right != null)
            rightHeight = getHeight(node.right); // get right height

        return leftHeight - rightHeight;
    }

    /**
     * Returns the keys of the data structure in sorted order. In the case of binary
     * search trees, the visit order is: L V R
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in-order
     */
    @Override
    public List<K> getInOrderTraversal() {
        List<K> inorder = inOrderTraversal(root); // call private helper
        return inorder;
    }

    /**
     * This is the private helper method to get the in-order traversal list
     * 
     * @param node - current node that is traversing
     * @return List of keys in-order
     */
    private List<K> inOrderTraversal(BSTNode<K, V> node) {
        List<K> output = new ArrayList<K>(); // List to return
        List<K> left = null;
        List<K> right = null;

        if (node != null) {
            if (node.left != null) {
                // get ArrayList of left part and store to the output ArrayList
                left = inOrderTraversal(node.left);
                output.addAll(left);
            }
            output.add(node.key); // add current node
            if (node.right != null) {
                // get ArrayList of right part and store to the output ArrayList
                right = inOrderTraversal(node.right);
                output.addAll(right);
            }
        }
        return output;
    }

    /**
     * Returns the keys of the data structure in pre-order traversal order. In the
     * case of binary search trees, the order is: V L R
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in pre-order
     */
    @Override
    public List<K> getPreOrderTraversal() {
        List<K> preorder = preOrderTraversal(root); // call private helper
        return preorder;
    }

    /**
     * This is the private helper method to get the pre-order traversal list
     * 
     * @param node - current node that is traversing
     * @return List of keys pre-order
     */
    private List<K> preOrderTraversal(BSTNode<K, V> node) {
        List<K> output = new ArrayList<K>(); // List to return
        List<K> left = null;
        List<K> right = null;

        if (node != null) {
            output.add(node.key); // add current node
            if (node.left != null) {
                // get ArrayList of left part and store to the output ArrayList
                left = preOrderTraversal(node.left);
                output.addAll(left);
            }
            if (node.right != null) {
                // get ArrayList of right part and store to the output ArrayList
                right = preOrderTraversal(node.right);
                output.addAll(right);
            }
        }
        return output;
    }

    /**
     * Returns the keys of the data structure in post-order traversal order. In the
     * case of binary search trees, the order is: L R V
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in post-order
     */
    @Override
    public List<K> getPostOrderTraversal() {
        List<K> postorder = postOrderTraversal(root); // call private helper
        return postorder;
    }

    /**
     * This is the private helper method to get the post-order traversal list
     * 
     * @param node - current node that is traversing
     * @return List of keys post-order
     */
    private List<K> postOrderTraversal(BSTNode<K, V> node) {
        List<K> output = new ArrayList<K>(); // List to return
        List<K> left = null;
        List<K> right = null;

        if (node != null) {
            if (node.left != null) {
                // get ArrayList of left part and store to the output ArrayList
                left = postOrderTraversal(node.left);
                output.addAll(left);
            }
            if (node.right != null) {
                // get ArrayList of right part and store to the output ArrayList
                right = postOrderTraversal(node.right);
                output.addAll(right);
            }
            output.add(node.key); // add current node
        }
        return output;
    }

    /**
     * Returns the keys of the data structure in level-order traversal order.
     * 
     * The root is first in the list, then the keys found in the next level down,
     * and so on.
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in level-order
     */
    @Override
    public List<K> getLevelOrderTraversal() {
        ArrayList<K> levelorder = new ArrayList<K>(); // ArrayList to return
        
        if(root==null) // if tree is empty
            return levelorder; // empty list
        
        // Queue to traverse with
        Queue<BSTNode<K,V>> queue = new LinkedList<BSTNode<K,V>>();
        queue.add(root);//Add the root to queue
        
        while (!queue.isEmpty()) {
            BSTNode<K, V> node = queue.remove();
            levelorder.add(node.key); // add node in queue to ArrayList

            if (node.left != null)
                queue.add(node.left); // enqueue node's left child

            if (node.right != null)
                queue.add(node.right); // enqueue node's right child

        }

        return levelorder;
    }

    /**
     * This method rotates nodes to the left.
     * Counter-clockwise about right-child of node
     * 
     * @param rotNode - node to rotate
     * @return new node to reference to tree
     */
    private BSTNode<K, V> rotateLeft(BSTNode<K, V> rotNode) {
        // set the name to each node
        BSTNode<K, V> grand = rotNode; // node is grandparent
        BSTNode<K, V> parent = grand.right;
        BSTNode<K, V> child = parent.right;
        
        // rotate
        grand.right = parent.left; // parent's left child will be grand's right child
        parent.left = grand; // grand is parent's left child
        
        return parent; // return rotated parent
    }

    /**
     * This method rotates nodes to the right.
     * Clockwise about right-child of node
     * 
     * @param rotNode - node to rotate
     * @return new node to reference to tree
     */
    private BSTNode<K, V> rotateRight(BSTNode<K, V> rotNode) {
        // set the name to each node
        BSTNode<K, V> grand = rotNode; // node is grandparent
        BSTNode<K, V> parent = grand.left;
        BSTNode<K, V> child = parent.left;
        
        // rotate
        grand.left = parent.right; // parent's left child will be grand's right child
        parent.right = grand; // grand is parent's left child
        
        return parent; // return rotated parent
    }

    /**
     * This method re-balance the tree by its balancing factor
     * 
     * @param cur - current node that is working to be re-balanced
     * @return current node after re-balanced 
     */
    private BSTNode<K, V> rebalance(BSTNode<K, V> cur) {

        if (cur.left != null)
            cur.left = rebalance(cur.left); // re-balance the left side

        if (cur.right != null)
            cur.right = rebalance(cur.right); // re-balance the right side

        int bf = balancefactor(cur); // get balance factor of cur node

        if (bf < -1 && balancefactor(cur.right) < 0) { // -2 -1
            return rotateLeft(cur);
        } else if (bf > 1 && balancefactor(cur.left) > 0) { // +2 +1 
            return rotateRight(cur);
        } else if (bf < -1 && balancefactor(cur.right) > 0) { // -2 +1
            cur.right = rotateRight(cur.right); // rotate to right first
            return rotateLeft(cur);
        } else if (bf > 1 && balancefactor(cur.left) < 0) { // +2 -1
            cur.left = rotateLeft(cur.left); // rotate to left first
            return rotateRight(cur);
        } else {
            return cur;
        }
    }

    /**
     * This method will update height of every node in the tree by recursively
     * 
     * @param node - node that is working to find the updated height
     * @return the node with updated height
     */
    private BSTNode<K, V> updateHeight(BSTNode<K, V> node) {
        if (node == null)
            return null;

        if (node.left != null)
            node.left = updateHeight(node.left); // update the height of left side
        
        node.height = getHeight(node); // update the node's height
        
        if (node.right != null)
            node.right = updateHeight(node.right); // update the height of right side

        return node;
    }

    /**
     * Add the key,value pair to the data structure and increase the number of keys.
     * If key is null, throw IllegalNullKeyException; If key is already in data
     * structure, throw DuplicateKeyException(); Do not increase the num of keys in
     * the structure, if key,value pair is not added.
     */
    @Override
    public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
        if (key == null)
            throw new IllegalNullKeyException();
        if (contains(key)) // key is already in data structure
            throw new DuplicateKeyException();

        BSTNode<K, V> newNode = new BSTNode<K, V>(key, value);
        // newNode.balanceFactor = 0;

        if (root == null) {
            root = newNode; // add new node to the root
            numKeys++;
        } else {
            insert(newNode, root); // call helper method
        }

        root = updateHeight(root);
        root = rebalance(root); // re-balance the tree
        root = updateHeight(root); // update the height again
    }

    /**
     * This is the private helper method to insert new node on the tree
     * 
     * @param newNode - new node to add
     * @param curNode - current node that is working with
     */
    private void insert(BSTNode<K, V> newNode, BSTNode<K, V> curNode) {
        int compare = curNode.key.compareTo(newNode.key);

        if (compare < 0) { // current < newNode
            if (curNode.right != null) {
                insert(newNode, curNode.right); // recursive
            } else {
                curNode.right = newNode; // add newNode to the right
                numKeys++;
            }
        } else if (compare > 0) { // current > newNode
            if (curNode.left != null) {
                insert(newNode, curNode.left); // recursive
            } else {
                curNode.left = newNode; // add newNode to the left
                numKeys++;
            }
        }
    }

    /**
     * If key is found, remove the key,value pair from the data structure and
     * decrease num keys. If key is not found, do not decrease the number of keys in
     * the data structure. If key is null, throw IllegalNullKeyException If key is
     * not found, throw KeyNotFoundException().
     * 
     * @param key - key to find which node to delete
     */
    @Override
    public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException { 
        if (key == null)
            throw new IllegalNullKeyException();
        
        if (contains(key)) { // key is on the tree
            root = remove(root, key); // call helper method
            root = updateHeight(root); // update height after removing
            root = rebalance(root); // re-balance the tree
            root = updateHeight(root); // update the height again
            numKeys--;
        } else { // key is not found
            throw new KeyNotFoundException();
        }
        return true;
    }

    /**
     * This is the private helper method to remove the node from the tree
     * 
     * @param node - current node that is working with
     * @param key - key to find which node to delete
     * @return current node to reference
     */
    private BSTNode<K, V> remove(BSTNode<K, V> node, K key) {
        if (node == null)
            return node;

        // compare key of node with given key
        int compare = node.key.compareTo(key);

        if (compare > 0) { // node > key
            node.left = remove(node.left, key); // remove helper to node on the left
            return node;
        } else if (compare < 0) { // node < key
            node.right = remove(node.right, key); // remove helper to node on the left
            return node;
        } else { // node = key
            if (node.left == null && node.right == null)
                return null; // remove node that doesn't have children
            else if (node.left == null)
                return node.right; // reference to node's right
            else if (node.right == null)
                return node.left; // reference to node's left
            else {
                // node to replace - find in order successor of the node
                BSTNode<K, V> replace = inorderSuccessor(node, root);
                
                node = remove(node, replace.key); // remove in-order successor node
                // replace the node with its in-order successor
                node.key = replace.key; 
                node.value = replace.value;
                
                return node;
            }
        }
    }

    /**
     * Find the in-order successor of the given node
     * 
     * @param target - the node that we want to find its in-order successor
     * @param node - current node that is working with
     * @return in-order successor of the node
     */
    private BSTNode<K, V> inorderSuccessor(BSTNode<K, V> target, BSTNode<K, V> node) {
        target = target.right; // successor will be on the right side

        while (target.left != null) { // go to farthest left of right side
            target = target.left;
        }

        return target;
    }

    /**
     * Returns the value associated with the specified key
     *
     * Does not remove key or decrease number of keys If key is null, throw
     * IllegalNullKeyException If key is not found, throw KeyNotFoundException()
     * 
     * @param key - key of node to looking for
     * @return return the value of found key
     */
    @Override
    public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
        if (key == null)
            throw new IllegalNullKeyException();
        
        if (!contains(key)) // key not on tree
            throw new KeyNotFoundException();
        
        return get(key, root).value; // call helper method
    }

    /**
     * This is the private helper method for "get" method
     * 
     * @param key - key of node to looking for
     * @param cur - current node that is working with
     * @return the value of found key
     * @throws KeyNotFoundException - if key not found
     */
    private BSTNode<K, V> get(K key, BSTNode<K, V> cur) throws KeyNotFoundException {
        int compare = cur.key.compareTo(key); // compare keys

        if (compare < 0) { // current < key
            if (cur.right != null)
                return get(key, cur.right);
            else // not found
                throw new KeyNotFoundException();
        } else if (compare > 0) { // current > key
            if (cur.left != null)
                return get(key, cur.left);
            else // not found
                throw new KeyNotFoundException();
        } else { // current = key
            return cur;
        }
    }

    /**
     * Returns true if the key is in the data structure If key is null, throw
     * IllegalNullKeyException Returns false if key is not null and is not present
     * 
     * @param key - key to find through the tree
     */
    @Override
    public boolean contains(K key) throws IllegalNullKeyException {
        if (key == null)
            throw new IllegalNullKeyException();
        
        if (root == null) // key cannot be on tree
            return false;

        return contains(key, root); // call private helper
    }

    /**
     * This is the private helper method for "contains"
     * This method will recursively find if there a node has same key 
     * 
     * @param key - key to find through the tree
     * @param cur - current node that is working with
     * @return true if node with key is on the tree
     */
    private boolean contains(K key, BSTNode<K, V> cur) {
        int compare = cur.key.compareTo(key); // compare keys

        if (compare < 0) { // current < key
            if (cur.right != null)
                return contains(key, cur.right); // look up the right part
            else
                return false; // s is not in this dictionary
        } else if (compare > 0) { // current > key
            if (cur.left != null)
                return contains(key, cur.left); // look up the left part
            else
                return false; // key is not in this tree
        } else { // current = key
            return true;
        }
    }

    /**
     * Returns the number of key,value pairs in the data structure
     */
    @Override
    public int numKeys() {
        return this.numKeys;
    }

    /**
     * Print the tree.
     *
     * For our testing purposes: all keys that we insert in the tree will have a
     * string length of exactly 2 characters. example: numbers 10-99, or strings aa
     * - zz, or AA to ZZ
     *
     * This makes it easier for you to not worry about spacing issues.
     *
     * You can display in any of a variety of ways, but we should see a tree that we
     * can identify left and right children of each node
     *
     * For example:
     * 
     * | |-------50 |-------40 | |-------35 30 |-------20 | |-------10
     * 
     * Look from bottom to top. Inorder traversal of above tree (10,20,30,35,40,50)
     * 
     * Or, you can display a tree of this kind.
     * 
     * 30 /\ / \ 20 40 / /\ / / \ 10 35 50
     * 
     * Or, you can come up with your own orientation pattern, like this.
     * 
     * 10 20 30 35 40 50
     * 
     * The connecting lines are not required if we can interpret your tree.
     * 
     */
    @Override
    public void print() {
        printTree(root, getHeight()); // call helper method
    }
    
    /**
     * This is the private helper method to print the tree recursively
     * 
     * @param node - cur node that is working now
     * @param level - level of tree
     */
    private void printTree(BSTNode<K, V> node, int level) {
        if (node == null)
            return;
        
        // increase level to indent
        printTree(node.right, level + 1); // print right side
        if (level != 0) {
            for (int i = getHeight(); i < level; i++)
                System.out.print("|\t"); //whitespace
            System.out.println("|-------" + node.key); // print tree node
        } else { //empty tree
            System.out.println(node.key);
        }
        //increase level to indent
        printTree(node.left, level + 1); // print left side
    }
//    public static void main(String[] args) {
//        BALST<Integer, Integer> tree = new BALST<>();
//        try {
//            tree.insert(4, 4);
//            tree.insert(3, 3);
//            tree.insert(2, 2);
//            tree.remove(3);
//            tree.insert(5, 5);
//            tree.insert(3, 3);
//            tree.insert(1, 1);
//            
//            try {
//                tree.insert(4, 4);
//                System.out.println("1");
//            }catch (DuplicateKeyException e) {
//                // TODO: handle exception
//                System.out.println("2");
//            }catch (Exception e) {
//                // TODO: handle exception
//                System.out.println("3");
//            }            
//            
//        }catch (Exception e) {
//            // TODO: handle exception
//            System.out.println("4");
//        }
//    }
}
