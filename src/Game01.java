import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

public class Game01
{
    private Node head;

    private Node tail;

    int allowance;

    public Game01()
    {
        String filePath = "in.txt";

        inputFileToOrderedList(filePath);

        findHighFromNode(head);
    }

    public static void main(String[] args)
    {
        new Game01();
    }

    private void inputFileToOrderedList(String filePath)
    {
        try
        {
            File file = new File(filePath);

            BufferedReader br = new BufferedReader(new FileReader(file));

            String currentString;
            int readCount = 0;
            while ((currentString = br.readLine()) != null)
            {
                if (readCount == 0)
                {

                    allowance = Integer.parseInt(currentString);
                    System.out.println("Allowance set to " + allowance);
                }
                else
                {
                    int points = 0;
                    int cost = 0;

                    StringTokenizer st = new StringTokenizer(currentString);
                    points = Integer.parseInt(st.nextToken());
                    cost = Integer.parseInt(st.nextToken());
                    double ratio = (double)points / (double)cost;
                    System.out.println("Added card p " + points + " cost " + cost + " ratio " + ratio);
                    addCardToList(points, cost);

                }

                readCount++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void addCardToList(Integer points, Integer cost)
    {
        Node node = new Node(cost, points);

        if (head == null)
        {
            head = node;
            tail = node;
        }
        else
        {
            tail.setNextRef(node);
            tail = node;
        }
    }

    private int findMaximumScoreWithAllowance()
    {
        //Find highest ratio card first
        Node high = findHighFromNode(head);
        Node nextHigh = null;

        int currentBalance = allowance;
        int totalPoints = 0;
        boolean actionOccurred = true;
        boolean handleDuplicate = false;

        while(actionOccurred)
        {
            actionOccurred = false;
            handleDuplicate = false;

            double highRatio = high.getRatio();

            Node node = head;

            while (node != null)
            {
                if (node.getRatio() == high.getRatio())
                {
                    //Add all of same ratio and point value
                    if (node.points == high.points)
                    {
                        if (node.cost <= currentBalance)
                        {
                            totalPoints += node.points;
                            currentBalance -= node.cost;
                            node = node.next;
                        }
                    }
                    //Add all of same ratio and lesser point value
                    else if (node.points < high.points)
                    {
                        if (nextHigh == null)
                        {
                            nextHigh = node;
                            handleDuplicate = true;
                        }
                        else
                        {
                            //Always choose the highest point value from sets of duplicate ratios with lower points than the high
                            if (node.points > nextHigh.points)
                            {
                                nextHigh = null;
                                handleDuplicate = true;
                            }
                        }

                    }
                }
                else
                {
                    if (handleDuplicate == false)
                    {

                    }
                }
            }
        }

    }

    private Node findHighFromNode(Node node)
    {
        Node high = node;
        Node current = head;

        while (current.next != null)
        {

            double currentRatio = current.getRatio();
            double highRatio = high.getRatio();

            //If the current card ratio is higher than the high, set high to current
            if (currentRatio > highRatio)
            {
                high = current;
            }
            //If the current card ratio is the same as the high, but has higher points, set high to current
            else if (currentRatio == highRatio)
            {
                if (current.next.points > high.points)
                {
                    high = current;
                }
            }

            current = current.next;
        }

        System.out.println("The high is " + high.points + " " + high.cost);

        return high;
    }

    static class Node
    {
        int cost;
        int points;
        Node next = null;

        public Node(Integer cost, Integer numPoints)
        {
            this.cost = cost;
            this.points = numPoints;
        }

        public void setNextRef(Node node)
        {
            this.next = node;
        }

        public double getRatio()
        {
            return (double)cost / (double)points;
        }
    }
}
