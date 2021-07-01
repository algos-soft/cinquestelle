/**
 * Title:     CollapsiblePanelTest
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      7-apr-2009
 */
package it.algos.base.pannello.pancollassabile;

import it.algos.base.layout.Layout;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.*;

/**
 * Test della classe PanCollassabile
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 7-apr-2009 ore 9.17.28
 */
public final class CollapsiblePanelTest {

    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public CollapsiblePanelTest() {

        JDialog dialogo = new JDialog();

        Pannello pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
        pan.add(new JLabel("Riga uno"));
        pan.add(new JLabel("Riga due"));
        pan.add(new JLabel("Riga tre"));
        pan.getPanFisso().setBorder(BorderFactory.createTitledBorder("Pannello"));


        CollapsiblePanel cp = new  CollapsiblePanel(pan.getPanFisso());
//        cp.setBorder(BorderFactory.createTitledBorder("Pannello"));
//        cp.collapse();


        dialogo.add(pan.getPanFisso());


        dialogo.pack();
        dialogo.setVisible(true);

    }// fine del metodo costruttore base


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso
     */
    public static void main(String[] argomenti) {
        new CollapsiblePanelTest();
    }// fine del metodo main

}// fine della classe
