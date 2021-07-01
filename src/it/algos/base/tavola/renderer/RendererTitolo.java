/**
 * Title:        RendererTitolo.java
 * Package:      it.algos.base.tavola.renderer
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 24 dicembre 2002 alle 16.43
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.tavola.renderer;

import it.algos.base.errore.Errore;
import it.algos.base.tavola.Tavola;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Questa classe concreta e' responsabile di:<br>
 * A - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  24 dicembre 2002 ore 16.43
 */
public class RendererTitolo extends JPanel implements TableCellRenderer {


    /**
     * colore della scritta del titolo quando selezionata
     */
    private Color coloreSelezione = null;


    /**
     * Costruttore completo senza parametri <br>
     */
    public RendererTitolo() {
        /* rimanda al costruttore della superclasse */
        super();
        /* regolazioni iniziali di riferimenti e variabili */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo */


    /**
     * ...
     */
    public Component getTableCellRendererComponent(JTable unaTavola,
                                                   Object unValore,
                                                   boolean isColonnaSelezionata,
                                                   boolean haFuoco,
                                                   int unaRiga,
                                                   int unaColonna) {

        /* variabili e costanti locali di lavoro */
        JLabel unaLabel = null;
        String stringa = "";
        Tavola tavola;

        /* recupera il titolo dal campo della Vista */
        if (unaTavola instanceof Tavola) {
            tavola = (Tavola)unaTavola;
            stringa = tavola.getVisibleColumnName(unaColonna);
        }// fine del blocco if

        /* crea una JLabel con titolo */
        unaLabel = new JLabel(stringa, JLabel.CENTER);
        unaLabel.setFont(unaTavola.getTableHeader().getFont());
        unaLabel.setBackground(unaTavola.getTableHeader().getBackground());
        unaLabel.setOpaque(true);

        /** regola il colore */
        if (coloreSelezione == null) {
            unaLabel.setForeground(unaTavola.getTableHeader().getForeground());
        } else {
            unaLabel.setForeground(coloreSelezione);
        } /* fine del blocco if/else */

        /* aggiunge la label al pannello */
        this.setLayout(new BorderLayout());
        this.removeAll();
        this.add(unaLabel);

        /** crea un bordo */
        this.setBorder(new EmptyBorder(1, 1, 1, 1));

//        todo vecchio sistema disabilitato alex 23-06-07
//        todo ora usa il titolo del campo dalla vista
//
//        JLabel unaLabel = null;
//        StringTokenizer unToken = null;
//        int quanteRighe = 0;
//        String stringa="";
//
//        this.removeAll();
//
//        unToken = new StringTokenizer((String)unValore, "\r\n");
//        quanteRighe = unToken.countTokens();
//
//        setLayout(new GridLayout(quanteRighe, 1));
//
//        while (unToken.hasMoreElements()) {
//            stringa = (String)unToken.nextElement();
//            unaLabel = new JLabel(stringa, JLabel.CENTER);
//            unaLabel.setFont(unaTavola.getTableHeader().getFont());
//            unaLabel.setBackground(unaTavola.getTableHeader().getBackground());
//            unaLabel.setOpaque(true);
//
//            /** */
//            if (coloreSelezione == null) {
//                unaLabel.setForeground(unaTavola.getTableHeader().getForeground());
//            } else {
//                unaLabel.setForeground(coloreSelezione);
//            } /* fine del blocco if/else */
//
//            /* aggiunge la label al pannello */
//            this.add(unaLabel);
//
//        } /* fine del blocco while */
//
//        /** crea un bordo */
//        this.setBorder(new EmptyBorder(1, 1, 1, 1));

        /** ritorna se stesso */
        return this;
    } /* fine del metodo */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setColoreSelezione(Color coloreSelezione) {
        this.coloreSelezione = coloreSelezione;
    } /* fine del metodo setter */

    //-------------------------------------------------------------------------
}// fine della classe

//-----------------------------------------------------------------------------

