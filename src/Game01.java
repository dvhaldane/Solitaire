import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

public class Game01
{
    private Node head;

    private Node tail;

    int allowance;

    int listLength = 0;

    int highScore = 0;

    public Game01()
    {
        String filePath = "C:\\Users\\Haldane\\IdeaProjects\\Solitaire\\src\\in.txt";

        inputFileToLinkedList(filePath);

        Node temp = head;
        for (int i = 0; i < listLength; i ++)
        {
            recursiveTree(temp, allowance, 0);
            temp = temp.next;
        }
        System.out.println(highScore);
    }

    public static void main(String[] args)
    {
        new Game01();
    }

    private void inputFileToLinkedList(String filePath)
    {
        try
        {
            File file = new File(filePath);

            BufferedReader br = new BufferedReader(new FileReader(file));

            String currentString;
            boolean firstLineWasRead = false;
            while ((currentString = br.readLine()) != null)
            {
                if (firstLineWasRead == false)
                {
                    allowance = Integer.parseInt(currentString);
                    firstLineWasRead = true;
                }
                else
                {
                    int points = 0;
                    int cost = 0;

                    StringTokenizer st = new StringTokenizer(currentString);
                    points = Integer.parseInt(st.nextToken());
                    cost = Integer.parseInt(st.nextToken());
                    addCardToList(points, cost);
                    listLength++;
                }
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

    private void recursiveTree(Node node, int allowance, int score)
    {
        Node temp = node;
        int currentAllowance = allowance;
        int currentScore = score;

        //Do the math on the card

        if (node.cost < currentAllowance)
        {
            currentAllowance -= node.cost;
            currentScore += node.points;
        }
        else if (node.cost == currentAllowance)
        {
            currentAllowance -= node.cost;
            currentScore += node.points;
        }
        else
        {
            if (currentScore > highScore)
            {
                highScore = currentScore;
                return;
            }
            else
            {
                return;
            }
        }

        while (temp.next != null)
        {
            temp = temp.next;
            recursiveTree(temp, currentAllowance, currentScore);
        }
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
    }
}
