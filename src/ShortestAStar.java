import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class ShortestAStar {
    Matrix board;
    ArrayList<Snake> snakes;
    Pair target;
    PriorityQueue<Node> open;
    ArrayList<Node> closed;
    private int movingSnake;
    private int moveCost;
    private int move;
    private Boolean chaseTail;

    ShortestAStar(Matrix board,
          ArrayList<Snake> snakes,
          int snakeNum,
          Pair target,
          Boolean targetIsTail)
    {
        this.board = board;
        this.snakes = snakes;
        this.target = target;
        moveCost = -1;
        movingSnake = snakeNum;
        move = -1;
        chaseTail = targetIsTail;
        open = new PriorityQueue<>();
        closed = new ArrayList<>();

        this.board.setBarriers(snakes, snakeNum);
    }

    // Returns a list of neighbours for the given node
    // where each neighbour is traversable and
    // is not in closed
    private ArrayList<Node> getNeighbours(Node node)
    {
        Node left = new Node(new Pair(
                            node.getPos().x - 1, 
                            node.getPos().y),
                            node, target);
        Node right = new Node(new Pair(
                            node.getPos().x + 1, 
                            node.getPos().y),
                            node, target);
        Node up = new Node(new Pair(
                             node.getPos().x, 
                             node.getPos().y - 1),
                             node, target);
        Node down = new Node(new Pair(
                             node.getPos().x, 
                             node.getPos().y + 1),
                             node, target);
        
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

    // Returns a list of neighbours for the given node
    // where each neighbour is traversable
    private ArrayList<Node> getStartNeighbours(Node start)
    {
        Node left = new Node(new Pair(
                            start.getPos().x - 1, 
                            start.getPos().y),
                            start, target);
        Node right = new Node(new Pair(
                            start.getPos().x + 1, 
                            start.getPos().y),
                            start, target);
        Node up = new Node(new Pair(
                             start.getPos().x, 
                             start.getPos().y - 1),
                             start, target);
        Node down = new Node(new Pair(
                             start.getPos().x, 
                             start.getPos().y + 1),
                             start, target);
        
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

    private boolean isInClosed(Node node)
    {
        // for (int i = closed.size() - 1; i >= 0; i--)
        // {
        //     if (node.getPos().equals(
        //         closed.get(i).getPos()))
        //         return true;
        // }
        // return false;
        return (
            board.get(node.getPos()) < 0
        );
    }

    private Node getTwinInOpen(Node node)
    {
        Iterator<Node> it = open.iterator();
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
        Node twin = getTwinInOpen(node);
        if (twin != null)
        {
            // If node is has a smaller cost than twin
            if (node.compareTo(twin) < 0)
            {
                open.remove(twin);
            }
            else return;
            
        }
        open.add(node);
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

    private void move()
    {
        if (target.equals(new Pair(-1, -1)))
        {
            moveCost = Integer.MAX_VALUE;
            move = 5;
            return;
        }
        Pair startPos = snakes.get
            (movingSnake).getCoords().get(0);
        Node start = new Node(startPos,
            null, target);
        open.add(start);

        while (true)
        {
            Node current = open.poll();
            if (current == null) // No path to target
            {
                ArrayList<Node> neighbours =
                    getStartNeighbours(start);
                
                move = 5;
                moveCost = Integer.MAX_VALUE;
                if (neighbours.size() > 0)
                {
                    Node randomNeighbour = 
                        neighbours.get(
                        new Random().nextInt(
                        neighbours.size()));

                    Direction direction = 
                        Pair.getDirection(startPos, randomNeighbour.getPos());
                    move = Direction.getMoveFromDirection(direction);
                }

                return;
            }
            closeNode(current);

            if (current.getPos().
                equals(target))
            {
                break;
            } 

            ArrayList<Node> neighbours = 
                getNeighbours(current);
            
            for (Node n : neighbours)
            {
                addToOpen(n);
                if (n.getPos().equals(target) &&
                    chaseTail)
                {
                    break;
                }
            }
        }

        Node current, parent;
        current = closed.get(closed.size() - 1);
        parent = current.getParent();

        moveCost = current.f_cost;
        while (!parent.getPos().equals(startPos))
        {
            current = parent;
            parent = current.getParent();
        }

        Direction direction = Pair.getDirection(
            parent.getPos(), current.getPos());
    
        move = Direction.getMoveFromDirection(direction);

    }

    

    

    public int getMove() {
        if (move == -1)
        {
            move();
        }
        return move;
    }

    public int getMoveCost() {
        if (moveCost == -1)
        {
            move();
        }
        return moveCost;
    }

    // public static void main(String[] args) {
    //     Matrix m = new Matrix(50, 50);

    //     Snake s1, s2, s3, s4;
    //     s1 = new Snake("alive 26 2 10,12 15,12 15,7 5,7 5,2");
    //     s2 = new Snake("dead 6 6 14,17 19,17");
    //     s3 = new Snake("alive 2 1 31,13 31,14");
    //     s4 = new Snake("invisible 17 1 9 21,14 21,14 15,14 15,13");

    //     ArrayList<Snake> snakes = new ArrayList<>();
    //     snakes.add(s1);
    //     snakes.add(s2);
    //     snakes.add(s3);
    //     snakes.add(s4);
    //     m.setBarriers(snakes, 0);

    //     Log.init();
    //     Log.write(m);
    //     Log.close();


    //     AStar a = new AStar(m, snakes, 0,
    //                         new Pair(8, 16),
    //                         new Pair(-1, -1));
    //     Pair target = new Pair(32, 19);

    //     Node start = new Node(new Pair(24, 25),
    //                         null, target);
    //     Node n1 = new Node(new Pair(25, 25),
    //                     start, target);
    //     Node n2 = new Node(new Pair(26, 25),
    //                     n1, target);
    //     Node n3 = new Node(new Pair(27, 25),
    //                     n2, target);
    //     Node n4 = new Node(new Pair(28, 25),
    //                     n3, target);
    //     Node n5 = new Node(new Pair(29, 25),
    //                     n4, target);
    //     Node n6 = new Node(new Pair(30, 25),
    //                     n5, target);
    //     Node n7 = new Node(new Pair(31, 25),
    //                     n6, target);
    //     Node n8 = new Node(new Pair(32, 25),
    //                     n7, target);
    //     Node n9 = new Node(new Pair(32, 24),
    //                     n8, target);
    //     Node n10 = new Node(new Pair(32, 23),
    //                     n9, target);
    //     Node n11 = new Node(new Pair(32, 22),
    //                     n10, target);
    //     Node n12 = new Node(new Pair(32, 21),
    //                     n11, target);
    //     Node n13 = new Node(new Pair(32, 20),
    //                     n12, target);
    //     Node n14 = new Node(new Pair(33, 20),
    //                     n13, target);
    //     Node n15 = new Node(new Pair(32, 20),
    //                     n14, target);
    //     Node n16 = new Node(new Pair(32, 19),
    //                     n15, target);

    //     a.addToOpen(start);
    //     a.addToOpen(n1);
    //     a.addToOpen(n2);
    //     // a.addToOpen(n3);
    //     // a.addToOpen(n4);
    //     // a.addToOpen(n5);
    //     // a.addToOpen(n6);
    //     // a.addToOpen(n7);
    //     // a.addToOpen(n8);
    //     // a.addToOpen(n9);
    //     // a.addToOpen(n10);
    //     // a.addToOpen(n11);
    //     // a.addToOpen(n12);
    //     // a.addToOpen(n13);
    //     // a.addToOpen(n14);
    //     // a.addToOpen(n15);
    //     // a.addToOpen(n16); // debug this some more


    //     a.closeNode(n2);
    //     ArrayList<Node> neighbours = a.getNeighbours(n1);

    //     for (Node n : neighbours)
    //     {
    //         System.out.println(n);
    //     }

    //     // System.out.println(a.open.size());


    // }

}
