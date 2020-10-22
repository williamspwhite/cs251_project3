public class BST<Key extends Comparable<Key>, Value> 
{
    private Node root;
    private int N;

    // CONSTRUCTOR 
    public BST() 
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
            root = new Node(key, val);
            N++;
            return;
        } else {
            int currentHeight = 0;
            Node currentNode = root;
            while (true) {
                if (key.compareTo(currentNode.key) < 0) {
                    currentHeight++;
                    if (currentNode.left == null) {
                        increaseHeight(key, currentHeight);

                        currentNode.left = new Node(key, val);
                        currentNode.left.height = 0;
                        N++;

                        System.out.println("-------------------------");
                        System.out.println(toString());
                        System.out.println("-------------------------");

                        return;
                    }

                    currentNode = currentNode.left;

                } else if (key.compareTo(currentNode.key) == 0) {
                    currentNode.val = val;

                    System.out.println("-------------------------");
                    System.out.println(toString());
                    System.out.println("-------------------------");

                    return;
                } else {
                    currentHeight++;
                    if (currentNode.right == null) {
                        increaseHeight(key, currentHeight);

                        currentNode.right = new Node(key, val);
                        currentNode.right.height = 0;
                        N++;

                        System.out.println("-------------------------");
                        System.out.println(toString());
                        System.out.println("-------------------------");

                        return;
                    }
                    currentNode = currentNode.right;
                }

            }
        }

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
	    //TO BE IMPLEMENTED
    }

    //
    // return a String version of the tree
    // level by level
    //
    public String toString() 
    {
        String str = "";
        Pair x = null;
        Queue<Pair> queue = new Queue<Pair>(N);
        int level = 0;
        queue.enqueue(new Pair(root, level));
        str += "Level 0: ";

        while(!queue.isEmpty()) 
        {
            x = queue.dequeue();
            Node n = x.node;

            if(x.depth > level) 
            {
                level++;
                str += "\nLevel " + level + ": ";
            }

            if(n != null) 
            {
                str += "|" + n.toString() + "|";
                queue.enqueue(new Pair(n.left, x.depth + 1));
                queue.enqueue(new Pair(n.right, x.depth + 1));
            } 
            else  
                str += "|null|";
        }

        str += "\n";
        return str;
    }

    public void increaseHeight(Key key, int height) {
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
    // PRIVATE METHODS

    //
    // return the height of x
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

        public Node(Key key, Value val) 
        {
            this.key = key;
            this.val = val;
        }
        
        public String toString() 
        {
            return "(" + key + ", " + val + "): " + height;
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
