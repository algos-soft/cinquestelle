/**
 * Title:        Renderer.java
 * Package:      it.algos.base.tavola.renderer
 * Description:  Superclasse dei singoli renderer
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 22 dicembre 2002 alle 18.18
 */
package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.TavolaModello;
import it.algos.base.tavola.Tavola;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Questa classe astratta e' responsabile di:<br>
 * A - Funzionare da superclasse per le classi di renderer <br>
 * B - Mantiene alcune variabili comuni alle sottoclassi <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  22 dicembre 2002 ore 18.18
 */
public abstract class RendererBase extends DefaultTableCellRenderer {

    /**
     * distanza (in caratteri) dal bordo sinistro o destro della colonna
     */
    protected static final String BORDO = " ";

    /**
     * campo della colonna da restituire
     */
    protected Campo campo = null;


    /**
     * Costruttore completo
     *
     * @param campo di riferimento
     */
    public RendererBase(Campo campo) {

        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.setCampo(campo);

    } /* fine del metodo costruttore completo */


    /**
     * Metodo chiamato internamente dalla JTable ogni volta che deve
     * disegnare una cella della colonna
     */
    public void setValue(Object unValore) {
        super.setValue(this.rendValue(unValore));
    } /* fine del metodo */


    /**
     * Effettua il rendering di un valore.
     * <p/>
     *
     * @param objIn valore in ingresso
     *
     * @return valore in uscita
     */
    public Object rendValue(Object objIn) {
        /* variabili e costanti locali di lavoro */
        Object objOut = null;

        try {    // prova ad eseguire il codice
            objOut = objIn;
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


    /**
     * Ritorna il codice del record relativo a una riga della JTable.
     * <p/>
     * @param riga numero della riga
     * @param table jTable di riferimento
     * @return il codice chiave della riga
     */
    protected int getChiaveRiga(int riga, JTable table) {
        /* variabili e costanti locali di lavoro */
        int chiave = 0;
        Tavola tavola;
        TavolaModello modello;

        try {    // prova ad eseguire il codice
            if ((table != null) && (table instanceof Tavola)) {
                tavola = (Tavola)table;
                modello = tavola.getModello();
                chiave = modello.getChiave(riga);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    public Campo getCampo() {
        return campo;
    }


    private void setCampo(Campo campo) {
        this.campo = campo;
    }

}// fine della classe
