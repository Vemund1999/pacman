package com.mygdx.game.NodeTree;

import java.util.ArrayList;
import java.util.List;

public class NodeTreeSingelton {

    private static NodeTreeSingelton instance;
    private Node startingNode;
    private static List<Node> nodes = new ArrayList<>();

    private static List<Node> nodesInCorners = new ArrayList<>();



    private NodeTreeSingelton() {}
    public static NodeTreeSingelton getInstance() {
        if (instance == null)
            instance = new NodeTreeSingelton();
        return instance;
    }

    public Node getNodeTopLeft() {
        return nodesInCorners.get(0);
    }
    public Node getNodeBottomLeft() {
        return nodesInCorners.get(1);
    }
    public Node getNodeTopRight() {
        return nodesInCorners.get(2);
    }
    public Node getNodeBottomRight() {
        return nodesInCorners.get(3);
    }


    public void makeNodeTree() {
        //Col1 is the column furthest to the left
        //Row1 is the top row
        //======================================
        //COL 1
        Node col1row5 = new Node(100, 100);
        Node col1row3 = new Node(100, 400);
        Node col1row1 = new Node(100, 675);




        //======================================
        //COL 2 and COL 3
        Node col3row5 = new Node(350, 100);
        Node col3row4 = new Node(350, 250);
        Node col2row4 = new Node(250, 250);
        Node col2row3 = new Node(250, 400);
        Node col2row2 = new Node(250, 550);
        Node col3row2 = new Node(350, 550);
        Node col3row1 = new Node(350, 675);


        //======================================
        //COL 4
        Node col4row2 = new Node(500, 550);
        Node col4row3 = new Node(500, 400);
        Node col4row4 = new Node(500, 250);
        Node col4row5 = new Node(500, 100);
        Node col4row1 = new Node(500, 675);

        //======================================
        //COL 8
        Node col8row5 = new Node(1500 - 100, 100);
        Node col8row3 = new Node(1500 - 100, 400);
        Node col8row1 = new Node(1500 - 100, 675);




        //======================================
        //COL 6 and COL 7
        Node col6row5 = new Node(1500 - 350, 100);
        Node col6row4 = new Node(1500 - 350, 250);
        Node col7row4 = new Node(1500 - 250, 250);
        Node col7row3 = new Node(1500 - 250, 400);
        Node col7row2 = new Node(1500 - 250, 550);
        Node col6row2 = new Node(1500 - 350, 550);
        Node col6row1 = new Node(1500 - 350, 675);

        //======================================
        //COL 5
        Node col5row2 = new Node(1500 - 500, 550);
        Node col5row3 = new Node(1500 - 500, 400);
        Node col5row4 = new Node(1500 - 500, 250);
        Node col5row5 = new Node(1500 - 500, 100);
        Node col5row1 = new Node(1500 - 500, 675);

        //======================================

        //MIDDLE
        Node middle = new Node(740, 550);
        startingNode = middle;

        //======================================


        col1row3.setThisUpAgainstDown(col1row5, 4);
        col1row1.setThisUpAgainstDown(col1row3, 4);

        //======================================

        col1row5.setThisLeftAgainstRight(col3row5, 3);
        col3row4.setThisUpAgainstDown(col3row5, 2);
        col2row4.setThisLeftAgainstRight(col3row4, 1);
        col2row3.setThisUpAgainstDown(col2row4, 2);
        col2row2.setThisUpAgainstDown(col2row3, 2);
        col1row3.setThisLeftAgainstRight(col2row3, 1);
        col2row2.setThisLeftAgainstRight(col3row2, 1);
        col3row1.setThisUpAgainstDown(col3row2, 2);
        col1row1.setThisLeftAgainstRight(col3row1, 3);

        //======================================


        col3row4.setThisLeftAgainstRight(col4row4, 2);
        col2row3.setThisLeftAgainstRight(col4row3, 3);
        col3row2.setThisLeftAgainstRight(col4row2, 2);

        //======================================

        col4row1.setThisUpAgainstDown(col4row2, 2);
        col4row2.setThisUpAgainstDown(col4row3, 2);
        col4row3.setThisUpAgainstDown(col4row4, 2);
        col4row4.setThisUpAgainstDown(col4row5, 2);

        //======================================

        col8row3.setThisUpAgainstDown(col8row5, 4);
        col8row1.setThisUpAgainstDown(col8row3, 4);


        col6row5.setThisLeftAgainstRight(col8row5, 3);
        col6row4.setThisUpAgainstDown(col6row5, 2);
        col6row4.setThisLeftAgainstRight(col7row4, 1);

        col7row3.setThisUpAgainstDown(col7row4, 2);
        col7row3.setThisLeftAgainstRight(col8row3, 3);

        col7row2.setThisUpAgainstDown(col7row3, 2);
        col6row2.setThisLeftAgainstRight(col7row2, 1);
        col6row1.setThisUpAgainstDown(col6row2, 2);
        col6row1.setThisLeftAgainstRight(col8row1, 3);

        col5row4.setThisLeftAgainstRight(col6row4, 2);
        col5row3.setThisLeftAgainstRight(col7row3, 3);
        col5row2.setThisLeftAgainstRight(col6row2, 2);

        col5row1.setThisUpAgainstDown(col5row2, 2);
        col5row4.setThisUpAgainstDown(col5row5, 2);
        col5row2.setThisUpAgainstDown(col5row3, 2);
        col5row3.setThisUpAgainstDown(col5row4, 2);

        //======================================

        col4row2.setThisLeftAgainstRight(startingNode, 3);
        //col4row2.setThisLeftAgainstRight(col5row2,6);
        startingNode.setThisLeftAgainstRight(col5row2, 3);
        col4row4.setThisLeftAgainstRight(col5row4, 6);
        col4row5.setThisLeftAgainstRight(col5row5, 6);
        col4row1.setThisLeftAgainstRight(col5row1, 6);

        nodesInCorners.add(col1row1);
        nodesInCorners.add(col1row5);
        nodesInCorners.add(col8row1);
        nodesInCorners.add(col8row5);


        putNodesInListRecursive(startingNode);
    }









    // ================ GETTERS =====================

    public Node getRootNode() {
        return startingNode;
    }


    public List<Node> getAllNodesAsListed() {
        return nodes;
    }

    // ================ END GETTERS =====================









    // ================ DRAWING =====================

    private static List<Node> nodesToMark = new ArrayList<>();
    public static void setNodesToMark(List<Node> nodesToMark) {
        NodeTreeSingelton.nodesToMark = nodesToMark;
    }

    private static List<Node> startNodeandEndNodeToMark = new ArrayList<>();
    public static void setStartNodeandEndNodeToMark(List<Node> startNodeandEndNodeToMark) {
        NodeTreeSingelton.startNodeandEndNodeToMark = startNodeandEndNodeToMark;
    }

    public void drawNodes() {
        for (Node node : nodes)
            if (node != null)
                node.draw();

        for (Node node : nodesToMark)
            node.mark();

        if (startNodeandEndNodeToMark.get(0) != null)
            startNodeandEndNodeToMark.get(0).markAsStartnode();
        if (startNodeandEndNodeToMark.get(1) != null)
            startNodeandEndNodeToMark.get(1).markAsEndnode();
    }

    // ================ END DRAWING =====================









    // ================ NODES LIST =====================

      private void putNodesInListRecursive(Node node)
    {
        if (node == null)
            return;

        if (nodes.contains(node))
            return;

        nodes.add(node);
        for (int i = 0; i <= 4; i++)
        {
            if (i == 0)
                putNodesInListRecursive(node.getUp());
            else if (i == 1)
                putNodesInListRecursive(node.getRight());
            else if (i == 2)
                putNodesInListRecursive(node.getDown());
            else if (i == 3)
                putNodesInListRecursive(node.getLeft());
        }
    }



    // ================ END NODES LIST =====================






}
