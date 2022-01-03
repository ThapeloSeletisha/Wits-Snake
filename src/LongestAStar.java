import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class LongestAStar {
    private ArrayList<Snake> snakes;
    private Matrix board;
    PriorityQueue<Node> options;
    private int mySnakeNum;
    private int move;
    ArrayList<Node> closed;

    LongestAStar(ArrayList<Snake> snakes, 
        Matrix board,
        int mySnakeNum)
    {
        this.snakes = snakes;
        this.board = board;
        this.mySnakeNum = mySnakeNum;
        closed = new ArrayList<>();
        options = new
            PriorityQueue<Node>(5, new LongestPathComparator());
        move = -1;
    }

    public void findLongestPath()
    {
        if (move == -1)
        {
            board.setBarriers(snakes, mySnakeNum);
            Pair startPos = 
                snakes.get(mySnakeNum).getCoords().get(0);
            Node start = new Node(startPos, null, null);
            options.add(start);

            while (true)
            {
                Node current = options.poll();
                if (current == null)
                {
                    break;
                }
                else if (current.f_cost >= 30)
                {
                    break;
                }

                
                closeNode(current);

                ArrayList<Node> neighbours = 
                getNeighbours(current);
            
                for (Node n : neighbours)
                {
                    addToOpen(n);
                }

            }
            Node current, parent;
            current = closed.get(closed.size() - 1);
            parent = current.getParent();

            if (parent == null)
            {
                move = 5; // Snake is dead :(
                return;
            }

            while (!parent.getPos().equals(startPos))
            {
                current = parent;
                parent = current.getParent();
                
            }

            Direction direction = Pair.getDirection(
                parent.getPos(), current.getPos());
        
            move = Direction.getMoveFromDirection(direction);

        }
    }
    private Node getTwinInOptions(Node node)
    {
        Iterator<Node> it = options.iterator();
        Node twin = null;
        while(it.hasNext())
        {
            twin = it.next();
            if (twin.getPos().
                equals(node.getPos()))
            {
                return twin;
            }
        }

        return null;    
    }

    // debug this some more
    private void addToOpen(Node node)
    {
        Node twin = getTwinInOptions(node);
        if (twin != null)
        {
            // If node is has a smaller cost than twin
            if (node.compareTo(twin) < 0)
            {
                options.remove(twin);
            }
            else return;
            
        }
        options.add(node);
    }
    // Returns a list of neighbours for the given node
    // where each neighbour is traversable and
    // is not in closed
    private ArrayList<Node> getNeighbours(Node node)
    {
        Node left = new Node(new Pair(
                            node.getPos().x - 1, 
                            node.getPos().y),
                            node, null);
        Node right = new Node(new Pair(
                            node.getPos().x + 1, 
                            node.getPos().y),
                            node, null);
        Node up = new Node(new Pair(
                             node.getPos().x, 
                             node.getPos().y - 1),
                             node, null);
        Node down = new Node(new Pair(
                             node.getPos().x, 
                             node.getPos().y + 1),
                             node, null);
        
        ArrayList<Node> neighbours = new ArrayList<>();
        if (traversable(left) && !isInClosed(left))
        {
            neighbours.add(left);
        }
        if (traversable(right) && !isInClosed(right))
        {
            neighbours.add(right);
        }
        if (traversable(up) && !isInClosed(up))
        {
            neighbours.add(up);
        }
        if (traversable(down) && !isInClosed(down))
        {
            neighbours.add(down);
        }
        return neighbours;
    }
    private boolean isInClosed(Node node)
    {
        return (
            board.get(node.getPos()) < 0
        );
    }
    private void closeNode(Node node)
    {
        if (node == null)
        {
            ;
        }
        board.markClosed(node.getPos().x, 
                         node.getPos().y);
        closed.add(node);
    }

    // Returns a list of neighbours for the given node
    // where each neighbour is traversable
    private ArrayList<Node> getStartNeighbours(Node start)
    {
        Node left = new Node(new Pair(
                            start.getPos().x - 1, 
                            start.getPos().y),
                            start, null);
        Node right = new Node(new Pair(
                            start.getPos().x + 1, 
                            start.getPos().y),
                            start, null);
        Node up = new Node(new Pair(
                             start.getPos().x, 
                             start.getPos().y - 1),
                             start, null);
        Node down = new Node(new Pair(
                             start.getPos().x, 
                             start.getPos().y + 1),
                             start, null);
        
        ArrayList<Node> neighbours = new ArrayList<>();
        if (traversable(left))
        {
            neighbours.add(left);
        }
        if (traversable(right))
        {
            neighbours.add(right);
        }
        if (traversable(up))
        {
            neighbours.add(up);
        }
        if (traversable(down))
        {
            neighbours.add(down);
        }
        return neighbours;
    }

    private boolean traversable(Node node)
    {
        try 
        {
            if (board.get(node.getPos().x, 
                node.getPos().y) < 33)
            {
                return true;
            }

            return false;
        } catch (ArrayIndexOutOfBoundsException e)
        {
            // e.printStackTrace();
            return false;
        }
    }
    public int getMove()
    {
        if (move == -1)
        {
            findLongestPath();
        }
        return move;
    }

    public static void main(String[] args) {
        // Matrix m = new Matrix(50, 50);

        // Snake s1 = new Snake(), s2 = new Snake(), s3 = new Snake(), s4 = new Snake();
        // s1.addDetails("alive 26 2 10,12 15,12 15,7 5,7 5,2");
        // s2.addDetails("dead 6 6 14,17 19,17");
        // s3.addDetails("alive 2 1 31,13 31,14");
        // s4.addDetails("invisible 17 1 9 21,14 21,14 15,14 15,13");

        // ArrayList<Snake> snakes = new ArrayList<>();
        // snakes.add(s1);
        // snakes.add(s2);
        // snakes.add(s3);
        // snakes.add(s4);
        // m.setBarriers(snakes, 0);

        // ***************************************************

        // Pair target = new Pair(32, 19);
        // Node start = new Node(new Pair(24, 25),
        //                       null, target);
        // Node n1 = new Node(new Pair(25, 25),
        //                    start, target);
        // Node n2 = new Node(new Pair(26, 25),
        //                    n1, target);
        // Node n3 = new Node(new Pair(27, 25),
        //                    n2, target);
        // Node n4 = new Node(new Pair(28, 25),
        //                    n3, target);
        // Node n5 = new Node(new Pair(29, 25),
        //                    n4, target);
        // Node n6 = new Node(new Pair(30, 25),
        //                    n5, target);
        // Node n7 = new Node(new Pair(31, 25),
        //                    n6, target);
        // Node n8 = new Node(new Pair(32, 25),
        //                    n7, target);
        // Node n9 = new Node(new Pair(32, 24),
        //                    n8, target);
        // Node n10 = new Node(new Pair(32, 23),
        //                    n9, target);
        // Node n11 = new Node(new Pair(32, 22),
        //                    n10, target);
        // Node n12 = new Node(new Pair(32, 21),
        //                    n11, target);
        // Node n13 = new Node(new Pair(32, 20),
        //                    n12, target);
        // Node n14 = new Node(new Pair(33, 20),
        //                    n13, target);
        // Node n15 = new Node(new Pair(33, 19),
        //                    n14, target);
        // Node n16 = new Node(new Pair(32, 19),
        //                    n15, target);

        // PriorityQueue<Node> options = new
        //     PriorityQueue<Node>(5, new LongestPathComparator());
        
        // options.add(start);
        // options.add(n1);
        // options.add(n2);
        // options.add(n3);
        // options.add(n4);
        // options.add(n5);
        // options.add(n6);
        // options.add(n7);
        // options.add(n8);
        // options.add(n9);
        // options.add(n10);
        // options.add(n11);
        // options.add(n12);
        // options.add(n13);
        // options.add(n14);
        // options.add(n15);
        // options.add(n16);


        // int size = options.size();
        // for (int i = 0; i < size; i++)
        // {
        //     System.out.println(options.poll());
        // }
    }


}

class LongestPathComparator implements Comparator<Node>
{
    public int compare(Node n1, Node n2)
    {
        if (n1.f_cost < n2.f_cost)
        {
            return 1;
        }
        else if (n1.f_cost > n2.f_cost)
        {
            return -1;
        }
        else 
        {
            if (n1.h_cost < n2.h_cost)
            {
                return 1;
            }
            else if (n1.h_cost > n2.h_cost)
            {
                return -1;
            }
            return 0;
        }
    }
}