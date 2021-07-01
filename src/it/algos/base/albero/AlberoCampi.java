/**
 * Title:     AlberoCampi
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-ago-2006
 */
package it.algos.base.albero;

import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteFont;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFont;
import it.algos.base.modulo.Modulo;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * Albero per la rappresentazione di una gerarchia di tavole e campi.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-ago-2006 ore 14.24.21
 */
public final class AlberoCampi extends Albero {

    /**
     * Font per i nomi dei campi in lista e albero
     */
    private static final Font FONT_CAMPI = CostanteFont.FONT_STANDARD;

    /**
     * Font per i nomi delle tavole nell'albero
     */
    private static final Font FONT_TAVOLE = CostanteFont.FONT_STANDARD_BOLD;

    /**
     * Colore di primo piano dei campi fissi in albero e lista
     */
    private static final Color COLORE_CAMPI_FISSI = Color.GRAY;

    /**
     * Altezza fissa delle righe per lista e albero
     */
    private static final int ALTEZZA_RIGA = LibFont.getAltezzaFont(FONT_CAMPI) + 2;


    /**
     * icona per tavola nell'albero
     */
    private Icon iconaTavola = Lib.Risorse.getIconaBase("Tavola");


    /**
     * Costruttore completo.
     */
    public AlberoCampi() {
        /* rimanda al costruttore della superclasse */
        super(null);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JTree tree;
        RendererNodo renderer;

        try {    // prova ad eseguire il codice

            /* regola graficamente l'albero */
            tree = this.getTree();
            tree.setRowHeight(ALTEZZA_RIGA);

            /* Assegna un renderer personalizzato per
             * disegnare le celle dell'albero. */
            renderer = new RendererNodo();
            this.setCellRenderer(renderer);

            /* rende invisibile il nodo root */
            this.getTree().setRootVisible(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Carica nell'albero i campi relativi a un dato modulo.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public void caricaCampi(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        AlberoModello modello;

        try {    // prova ad eseguire il codice
            modello = modulo.getAlberoCampi();
            this.setModelloAlbero(modello);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rimuove i campi fissi dal modello dell'albero
     * <p/>
     */
    public void rimuoviCampiFissi() {
        /* variabili e costanti locali di lavoro */
        AlberoModello modello;
        ArrayList<AlberoNodo> nodi;
        Object oggetto;
        Campo campo;

        try {    // prova ad eseguire il codice

            modello = this.getModelloAlbero();
            nodi = modello.getNodi();

            for (AlberoNodo nodo : nodi) {
                oggetto = nodo.getUserObject();
                if (oggetto != null) {
                    if (oggetto instanceof Campo) {
                        campo = (Campo)oggetto;
                        if (campo.getCampoDB().isFissoAlgos()) {
                            modello.removeNodeFromParent(nodo);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge i campi fissi al modello dell'albero
     * <p/>
     */
    public void aggiungiCampiFissi() {
        /* variabili e costanti locali di lavoro */
        AlberoModello modello;
        ArrayList<AlberoNodo> nodi;
        Object oggetto;
        Modulo modulo;
        ArrayList<Campo> campiFissi;
        AlberoNodo nodoCampo;
        int index;

        try {    // prova ad eseguire il codice

            modello = this.getModelloAlbero();
            nodi = modello.getNodi();

            for (AlberoNodo nodo : nodi) {
                oggetto = nodo.getUserObject();
                if (oggetto != null) {
                    if (oggetto instanceof Modulo) {
                        modulo = (Modulo)oggetto;
                        campiFissi = modulo.getCampiFissi();
                        index = 0;
                        for (Campo campo : campiFissi) {

                            /* crea il nuovo nodo col campo e lo inserisce
                             * sotto al nodo corrente */
                            nodoCampo = new AlberoNodo(campo);
                            modello.insertNodeInto(nodoCampo, nodo, index);
                            index++;
                        }
                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna l'elenco dei campi correntemente
     * selezionati nell'albero.
     * <p/>
     *
     * @return l'elenco dei campi selezionati
     */
    public ArrayList<Campo> getCampiSelezionati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = null;
        ArrayList<Object> oggettiSelezionati;
        Campo campo;

        try {    // prova ad eseguire il codice
            oggettiSelezionati = this.getOggettiSelezionati();
            campi = new ArrayList<Campo>();
            for (Object oggetto : oggettiSelezionati) {
                if (oggetto != null) {
                    if (oggetto instanceof Campo) {
                        campo = (Campo)oggetto;
                        campi.add(campo);
                    }// fine del blocco if
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    private Icon getIconaTavola() {
        return iconaTavola;
    }


    /**
     * Renderer specifico per i nodi dell'albero
     */
    private class RendererNodo extends DefaultTreeCellRenderer {

        /**
         * Intercetto il metodo per ottenere informazioni
         * sul nodo e regolare il testo del renderer
         */
        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object value,
                                                      boolean sel,
                                                      boolean expanded,
                                                      boolean leaf,
                                                      int row,
                                                      boolean hasFocus) {

            /* variabili e costanti locali di lavoro */
            Component comp;
            AlberoNodo nodo;
            Object oggetto;
            Campo campo;
            Modulo modulo;
            String testo = "";
            Icon icona = null;
            Font font = FONT_CAMPI;
            Color colore = Color.BLACK;

            comp = super.getTreeCellRendererComponent(tree,
                    value,
                    sel,
                    expanded,
                    leaf,
                    row,
                    hasFocus);

            nodo = (AlberoNodo)value;
            oggetto = nodo.getUserObject();
            if (oggetto != null) {

                /* se campo, il nome interno del campo */
                if (oggetto instanceof Campo) {
                    campo = (Campo)oggetto;
                    icona = campo.getIcona();
                    if (campo.getCampoDB().isFissoAlgos()) {
                        colore = COLORE_CAMPI_FISSI;
                    }// fine del blocco if-else
                    testo = campo.getNomeInterno();
                }// fine del blocco if

                /* se modulo, il nome del modulo */
                if (oggetto instanceof Modulo) {
                    icona = getIconaTavola();
                    modulo = (Modulo)oggetto;
                    testo = modulo.getNomeChiave();
                    font = FONT_TAVOLE;
                }// fine del blocco if

            }// fine del blocco if

            /* regolo il testo per il renderer */
            this.setText(testo);
            this.setForeground(colore);
            this.setFont(font);
            this.setIcon(icona);

            /* valore di ritorno */
            return comp;

        }

    }


}// fine della classe
