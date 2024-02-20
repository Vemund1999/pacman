package com.mygdx.game.Dijkstras;

import com.mygdx.game.NodeTree.Node;
import com.mygdx.game.NodeTree.NodeTreeSingelton;

import java.util.*;

public class Dijkstras {


    static List<Node> vertex;
    static int[] distances;
    static Node[] previousVertex;

    static List<Node> visited;
    static List<Node> unvisited;








    private Dijkstras() {
        List<Node> nodes = NodeTreeSingelton.getInstance().getAllNodesAsListed();

        visited = new ArrayList<>();
        distances = new int[nodes.size()];
        previousVertex = new Node[nodes.size()];
        unvisited = new ArrayList<>(nodes);
        vertex = new ArrayList<>(nodes);


    }





    private static void printGraph(Node startingNode, Node destination)
    {
        for (int i = 0; i < vertex.size(); i++)
        {
            System.out.print(vertex.get(i) + "  |   ");
            System.out.print(distances[i] + "   |   ");
            System.out.println(previousVertex[i]);
            System.out.println("");
        }


    }


    public static List<Node> findPath(Node startNode, Node destination, boolean forShortestPath) {

        Node currentNode = destination; //starter fra destinasjonen
        List<Node> path = new ArrayList<>();

        path.add(destination);
        while(currentNode != startNode) //looper helt til man kommer til startnoden
        {
            int index;
            for (index = 0; index < vertex.size(); index++) //looper igjennom alle nodene
            {
                if (vertex.get(index) == currentNode) //hvis noden man ser på, er den samme som currentNode, så lagrer man indeksen for den noden
                    break;
            }
            path.add(previousVertex[index]); //man legger til forløpende node i path
            currentNode = previousVertex[index]; //så gjør man currentNode til den forløpende noden.
            //så looper man igjen, der man hele tiden finner forløpende node, helt til man er tilbake til startnoden.
        }


        return path;
    }


    public static List<Node> findLongestPath(Node startNode, Node destination) {
        resetGraph();
        makeGraph(startNode, false);

        return findPath(startNode, destination, false);
    }


    public static List<Node> findShortestPath(Node startNode, Node destination) {
        resetGraph();
        makeGraph(startNode, true);

        return findPath(startNode, destination, true);
    }






    private static void resetGraph()
    {
        new Dijkstras();
    }





    private static void makeGraph(Node startingNode, boolean findShortestDistance)
    {
        //setter startnode til 0, og de andre nodene til infinity (-1)
        int i = 0;
            for (Node node : vertex)
            {
                if (node == startingNode)
                    distances[i] = 0;
                else
                    distances[i] = Integer.MAX_VALUE;
                i++;
            }









        while (unvisited.size() != 0)
        {
            //finner node med minst distanse
            Map.Entry<Node, Integer> currentNodeAtIndex = null;
            //TODO: HERERERER
                currentNodeAtIndex = findSmallestDistance();

            Node currentNode = currentNodeAtIndex.getKey();
            int atIndex = currentNodeAtIndex.getValue();

            Map<Node, Integer> neighbours = currentNode.getNeighbours(); //naboene til currentNode
            int index = 0;

            for (Node node : vertex) //ser på alle noder, index refererer til indeksen til den noden man ser på
            {
                for (Map.Entry<Node, Integer> entry : neighbours.entrySet()) //ser på naboene til currentNode
                {
                    if (entry.getKey() == node && !visited.contains(node)) //hvis currentNode har noden som nabo, og noden ikke er besøkt
                    {
                            if (distances[index] > (entry.getValue() + distances[atIndex])) //hvis naboen sin distanse fra startnoden, er større enn (naboen sin vekt) + (distansen til currentNode)
                            {
                                distances[index] = entry.getValue() + distances[atIndex]; //så oppdaterer man distansen til naboen
                                previousVertex[index] = currentNode;  //og setter previous node, til noden man kom fra
                            }

                    }
                }
                index++;
            }



            //legger til  nåværendeNode i besøkte
            visited.add(currentNode);
            unvisited.remove(currentNode);
        }
    }









    public static Map.Entry<Node, Integer> getNodeAtIndex(int atIndex) {
        final int finalAtIndex = atIndex;
        final Map.Entry<Node, Integer> nodeAtIndex = new Map.Entry<Node, Integer>() {
            @Override
            public Node getKey() {
                return vertex.get(finalAtIndex);
            }

            @Override
            public Integer getValue() {
                return finalAtIndex;
            }

            @Override
            public Integer setValue(Integer value) {
                return null;
            }
        };

        return nodeAtIndex;
    }


    //TODO: HERERERER
    private static Map.Entry<Node, Integer> findLongestDistance() {
        int atIndex = 0;
        int maxDistance = -Integer.MAX_VALUE;

        for (int i = 0; i < distances.length; i++)
        {
            if (distances[i] < maxDistance && !visited.contains(vertex.get(i)))
            {
                maxDistance = distances[i];
                atIndex = i;
            }
        }



        return getNodeAtIndex(atIndex);
    }


    //må endre på denne metoden for å få spøkelsene til å løpe
    //Ser på alle nodene, og finner den noden med minst vei så langt, av nodene som ikke er besøkt
    private static Map.Entry<Node, Integer> findSmallestDistance()
    {
        int atIndex = 0;
        int maxDistance = Integer.MAX_VALUE;

        for (int i = 0; i < distances.length; i++)
        {
            if (distances[i] < maxDistance && !visited.contains(vertex.get(i)))
            {
                maxDistance = distances[i];
                atIndex = i;
            }
        }



        return getNodeAtIndex(atIndex);
    }




    private boolean checkIfAllVisited()
    {
        for (Node node : visited)
            if (node == null)
                return false;
        return true;
    }





}
