package it.algos.albergo.test;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class HelloWorld {

    private static void test2() {
        /* variabili e costanti locali di lavoro */
        Map map;

        JGraph graph = createGraph();
        graph.setGridEnabled(true);
        graph.setGridVisible(true);
        graph.setGridMode(JGraph.LINE_GRID_MODE);
        graph.setGridSize(20);
//        graph.setGridColor(Color.lightGray);
        graph.setAntiAliased(true);
        graph.setAutoResizeGraph(true);
//        graph.setBackground(Color.lightGray);
//        graph.setBackgroundComponent(new JButton("pippo"));
//        graph.setScale(0.6);

        ArrayList<GraphCell> lista = new ArrayList<GraphCell>();

        GraphCell cell1;
        cell1 = new DefaultGraphCell(new String("Rossi Mario"));
        map = cell1.getAttributes();
        GraphConstants.setBounds(map, new Rectangle2D.Double(20, 20, 120, 20));
        GraphConstants.setOpaque(map, true);
        GraphConstants.setBackground(map, Color.orange);
        GraphConstants.setBorder(map, BorderFactory.createLineBorder(Color.blue, 2));
        GraphConstants.setEditable(map, false);
        GraphConstants.setSizeable(map, true);
        GraphConstants.setSizeableAxis(map, 1);
        lista.add(cell1);

        GraphCell cell2;
        cell2 = new DefaultGraphCell(new String("Bianchi Edoardo"));
        map = cell2.getAttributes();
        GraphConstants.setBounds(map, new Rectangle2D.Double(140, 140, 40, 20));
        GraphConstants.setGradientColor(map, Color.red);
        GraphConstants.setOpaque(map, true);
        lista.add(cell2);

        graph.getGraphLayoutCache().insert(lista.toArray());

        displayGraph(graph);

        /* cell editing */
        Map nested = new Hashtable();
        Map attributeMap1 = new Hashtable();
        GraphConstants.setBackground(attributeMap1 , Color.green);
        nested.put(cell1, attributeMap1 );
        graph.getGraphLayoutCache().edit(nested, null, null, null);

        int a = 87;
    }


    private static void test1() {

        JGraph graph = createGraph();

        DefaultGraphCell[] cells = new DefaultGraphCell[2];

        cells[0] = new DefaultGraphCell(new String("Rossi Mario"));

        GraphConstants.setBounds(cells[0].getAttributes(), new Rectangle2D.Double(20, 20, 120, 20));
        GraphConstants.setGradientColor(cells[0].getAttributes(), Color.orange);
        GraphConstants.setOpaque(cells[0].getAttributes(), true);

//        DefaultPort port0 = new DefaultPort();
//        cells[0].add(port0);

        cells[1] = new DefaultGraphCell(new String("Bianchi Edoardo"));

        GraphConstants.setBounds(
                cells[1].getAttributes(), new Rectangle2D.Double(140, 140, 40, 20));
        GraphConstants.setGradientColor(cells[1].getAttributes(), Color.red);
        GraphConstants.setOpaque(cells[1].getAttributes(), true);

//        DefaultPort port1 = new DefaultPort();
//        cells[1].add(port1);

//        DefaultEdge edge = new DefaultEdge();
//        edge.setSource(cells[0].getChildAt(0));
//        edge.setTarget(cells[1].getChildAt(0));
//        cells[2] = edge;
//        int arrow = GraphConstants.ARROW_CLASSIC;
//        GraphConstants.setLineEnd(edge.getAttributes(), arrow);
//        GraphConstants.setEndFill(edge.getAttributes(), true);

        graph.getGraphLayoutCache().insert(cells);

        displayGraph(graph);

    }


    private static JGraph createGraph() {
        GraphModel model = new DefaultGraphModel();
        GraphLayoutCache view = new GraphLayoutCache(model, new DefaultCellViewFactory());
        JGraph graph = new JGraph(model, view);
        return graph;
    }


    private static void displayGraph(JGraph graph) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(graph));
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        test2();
    }
}