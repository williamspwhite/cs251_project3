public class RLRBT<Key extends Comparable<Key>, Value> 
{
    private Node root;
    private int N;

    // CONSTRUCTOR 
    public RLRBT() 
    {
        this.root = null;
        this.N = 0;
    }

    // PUBLIC METHODS 

    //
    // insert a new (key, val) into tree
    // or replace value of existing key
    //
    public void insert(Key key, Value val) 
    {
        if (isEmpty()) {
            root = put(key, val, root);
            root.isRed = false;
            return;
        }

        put(key, val, root);
        checkHeights(root);
 //       System.out.println(toString());
     //   System.out.println("\n");



        return;
    }
    
    //
    // get the value associated with the given key;
    // return null if key doesn't exist
    //
    public Value get(Key key) 
    {
        Node currentNode = root;
        while (true) {
            if (currentNode == null) {
                return null;
            } else if (key.compareTo(currentNode.key) < 0) {
                currentNode = currentNode.left;
            } else if (key.compareTo(currentNode.key) == 0) {
                return currentNode.val;
            } else {
                currentNode = currentNode.right;
            }
        }
    }

    //
    // return true if the tree
    // is empty and false 
    // otherwise
    //
    public boolean isEmpty() 
    {
	    return root == null;
    }

    //
    // return the number of Nodes
    // in the tree
    //
    public int size() 
    {
	    return N;
    }

    //
    // returns the height of the tree
    //
    public int height() 
    {
	    return height(root);
    }

    //
    // returns the height of node 
    // with given key in the tree;
    // return -1 if the key does
    // not exist in the tree
    //
    public int height(Key key) 
    {
        Node currentNode = root;
        while (true) {
            if (currentNode == null) {
                return -1;
            } else if (key.compareTo(currentNode.key) < 0) {
                currentNode = currentNode.left;
            } else if (key.compareTo(currentNode.key) == 0) {
                return currentNode.height;
            } else {
                currentNode = currentNode.right;
            }
        }
    }

    //                                                                        
    // return String representation of tree                                      
    // level by level                                                            
    //
    public String toString() 
    {
        String ret = "Level 0: ";
        Pair x = null;
        Queue<Pair> queue = new Queue<Pair>(N);
        int level = 0;
        queue.enqueue(new Pair(root, level));
        
        while(!queue.isEmpty()) 
        {
            x = queue.dequeue();
            Node n = x.node;

            if(x.depth > level) 
            {
                level++;
                ret += "\nLevel " + level + ": ";
            }
            
            if(n != null) 
            {
                ret += "|" + n.toString() + "|";
                queue.enqueue(new Pair(n.left, x.depth + 1));
                queue.enqueue(new Pair(n.right, x.depth + 1));
            }
            else
                ret += "|null|";
        }

        ret += "\n";
        return ret;
    }


    //
    // return the black height of the tree
    //
    public int blackHeight() 
    {
        if (isEmpty()) {
            return 0;
        }

        int blackheight = 0;
        Node currentNode = root;
        while (currentNode != null) {
            if (!currentNode.isRed) {
                blackheight++;
            }
            if (currentNode.right == null) {
                currentNode = currentNode.left;
            } else if (currentNode.left == null) {
                currentNode = currentNode.right;
            } else if (currentNode.right.height > currentNode.left.height) {
                currentNode = currentNode.right;
            } else {
                currentNode = currentNode.left;
            }
        }
        return --blackheight;
    }

    // PRIVATE METHODS
    private Node put(Key key, Value val, Node n) {
        if (n == null) {
            Node returnNode = new Node(key, val);
            returnNode.isRed = true;
            returnNode.height = 0;
            N++;
            return returnNode;
        }
        if (key.compareTo(n.key) < 0) {
            n.left = put(key, val, n.left);
        } else if (key.compareTo(n.key) == 0) {
            n.val = val;
        } else {
            n.right = put(key, val, n.right);
        }

        if ((n.left != null) && n.left.isRed) {
            if (n.equals(root)) {
                //System.out.println("changing root");
                n = rotateRight(n);
                root = n;
                root.isRed = false;
            } else {
                n = rotateRight(n);
            }
        }
        if (((n.right != null) && (n.right.right != null))
            && (n.right.isRed && n.right.right.isRed)) {
            if (n.equals(root)) {
                //System.out.println("changing root");
                n = rotateLeft(n);
                root = n;
                root.isRed = false;
            } else {
                n = rotateLeft(n);
            }
        }
        if (((n.left != null) && (n.right != null)) &&
                (n.left.isRed && n.right.isRed)) {
            colorFlip(n);
        }


        root.isRed = false;
        return n;
    }

    private int checkHeights(Node node) {
        if (node == null) {
            return -1;
        }
        node.height = Math.max(checkHeights(node.left), checkHeights(node.right)) + 1;
        return node.height;
    }

    private int depth(Key key) {
        Node currentNode = root;
        int depth = 0;
        while (true) {
            if (currentNode == null) {
                //System.out.println("DEPTH 0F " + key + ": " + depth);
                return depth;
            } else if (key.compareTo(currentNode.key) == 0) {
                if (currentNode.height == 1) {
                    //System.out.println("RESULT OF LEFT ROTATE");
                    if (currentNode.right != null) {
                        if (currentNode.left != null) {
                            currentNode.left.height = 0;
                        }
                        currentNode.right.height = 0;
                        depth++;
                    } else if (currentNode.left != null) {
                        currentNode.left.height = 0;
                        depth++;
                    }
                }
                //System.out.println("DEPTH 0F " + key + ": " + depth);

                return depth;
            } else if (key.compareTo(currentNode.key) < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
            depth++;
        }
    }
    //
    // swap colors of two Nodes
    //
    private void swapColors(Node x, Node y) 
    {
        if(x.isRed == y.isRed)
            return;
        
        boolean temp = x.isRed;
        x.isRed = y.isRed;
        y.isRed = temp;
    }

    private void increaseHeight(Key key, int height) {
        Node currentNode = root;
        while (true) {
            if (currentNode == null) {
                return;
            } else if (key.compareTo(currentNode.key) < 0) {
                if (currentNode.height < height) {
                    currentNode.height = height;
                }
                currentNode = currentNode.left;
            } else {
                if (currentNode.height < height) {
                    currentNode.height = height;
                }
                currentNode = currentNode.right;
            }
            height--;
        }
    }

    //
    // rotate a link to the right
    //
    private Node rotateRight(Node x) 
    {
        Node temp = x.left;
        x.left = temp.right;
        temp.right = x;
        swapColors(x, temp);
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        temp.height = Math.max(height(temp.left), x.height) + 1;
        return temp;
    }

    //
    // rotate a link to the left
    //
    private Node rotateLeft(Node x) 
    {
        Node temp = x.right;
        x.right = temp.left;
        temp.left = x;
        swapColors(x, temp);
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        temp.height = Math.max(height(temp.right), x.height) + 1;
        return temp;
    }

    //
    // color flip
    //
    private Node colorFlip(Node x) 
    {
        if(x.left == null || x.right == null)
            return x;
        
        if(x.left.isRed == x.right.isRed) 
        {
            x.left.flip();
            x.right.flip();
            x.flip();
        }
        
        return x;
    }

    //
    // return the neight of Node x
    // or -1 if x is null
    //
    private int height(Node x) 
    {
        if(x == null)
            return -1;
        
        return x.height;
    }

    // NODE CLASS 
    public class Node 
    {
        Key key;
        Value val;
        Node left, right;
        int height;
        boolean isRed;

        public Node(Key key, Value val) 
        {
            this.key = key;
            this.val = val;
            this.isRed = true;
        }
        
        public String toString() 
        {
            return "(" + key + ", " + val + "): " 
            + height + "; " + (this.isRed?"R":"B");
        }

        public void flip() 
        {
            this.isRed = !this.isRed;
        }
    }

    // PAIR CLASS 
    public class Pair 
    {
        Node node;
        int depth;
        
        public Pair(Node node, int depth) 
        {
            this.node = node;
            this.depth = depth;
        }
    }

}
