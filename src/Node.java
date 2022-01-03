import java.util.ArrayList;
import java.util.*;

public class Node implements Comparable<Node>{
    private Pair pos;
    public final int f_cost, h_cost, g_cost;
    private Node parent;

    Node(Pair position,
         Node parent,
         Pair target)
    {
        pos = position;
        this.parent = parent;
        if (target != null)
        {
            h_cost = target.distanceFrom(pos);
        }
        else 
        {
            h_cost = 0;
        }
        if (parent == null)
        {
            g_cost = 0;
        }
        else
        {
            g_cost = parent.g_cost + 1;
        }
        f_cost = g_cost + h_cost;
    }

    @Override
    public String toString()
    {
        if (parent == null)
        {
            return pos.toString() + " parent" +
               "(null)" + ": " +
               String.format("f_cost=%d=%d+%d", 
               f_cost, g_cost, h_cost);
        }
        return pos.toString() + " parent" +
               parent.getPos().toString() + ": " +
               String.format("f_cost=%d=%d+%d", 
               f_cost, g_cost, h_cost);
    }

    // In terms of costs
    @Override
    public int compareTo(Node that)
    {
        if (this.f_cost > that.f_cost)
        {
            return 1;
        }
        else if (this.f_cost < that.f_cost)
        {
            return -1;
        }
        else 
        {
            if (this.h_cost > that.h_cost)
            {
                return 1;
            }
            else if (this.h_cost < that.h_cost)
            {
                return -1;
            }
            return 0;
        }
    }

    // In terms of costs
    public boolean isEqualTo(Node that)
    {
        return (this.compareTo(that) == 0);
    }

    // In terms of costs
    public boolean isLessThan(Node that)
    {
        return (this.compareTo(that) < 0);
    }

    // In terms of costs
    public boolean isGreaterThan(Node that)
    {
        return (this.compareTo(that) > 0);
    }

    public int distanceFrom(Node that)
    {
        return this.pos.distanceFrom(that.pos);
    }

    public Pair getPos()
    {
        return pos;
    }

    public Node getParent()
    {
        return parent;
    }



    public static void main(String[] args) {
        Pair target = new Pair(32, 19);
        Node start = new Node(new Pair(24, 25),
                              null, target);
        Node n1 = new Node(new Pair(25, 25),
                           start, target);
        Node n2 = new Node(new Pair(26, 25),
                           n1, target);
        Node n3 = new Node(new Pair(27, 25),
                           n2, target);
        Node n4 = new Node(new Pair(28, 25),
                           n3, target);
        Node n5 = new Node(new Pair(29, 25),
                           n4, target);
        Node n6 = new Node(new Pair(30, 25),
                           n5, target);
        Node n7 = new Node(new Pair(31, 25),
                           n6, target);
        Node n8 = new Node(new Pair(32, 25),
                           n7, target);
        Node n9 = new Node(new Pair(32, 24),
                           n8, target);
        Node n10 = new Node(new Pair(32, 23),
                           n9, target);
        Node n11 = new Node(new Pair(32, 22),
                           n10, target);
        Node n12 = new Node(new Pair(32, 21),
                           n11, target);
        Node n13 = new Node(new Pair(32, 20),
                           n12, target);
        Node n14 = new Node(new Pair(33, 20),
                           n13, target);
        Node n15 = new Node(new Pair(33, 19),
                           n14, target);
        Node n16 = new Node(new Pair(32, 19),
                           n15, target);

        PriorityQueue<Node> nodes = new PriorityQueue<>();
        nodes.add(start);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);
        nodes.add(n5);
        nodes.add(n6);
        nodes.add(n7);
        nodes.add(n8);
        nodes.add(n9);
        nodes.add(n10);
        nodes.add(n11);
        nodes.add(n12);
        nodes.add(n13);
        nodes.add(n14);
        nodes.add(n15);
        nodes.add(n16);

        int size = nodes.size();
        for (int i = 0; i < size; i++)
        {
            System.out.println(nodes.poll());
        }

        //System.out.println(n12.isLessThan(n14));

    }

}
