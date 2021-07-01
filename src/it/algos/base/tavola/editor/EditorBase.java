/**
 * Title:     EditorBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      11-mar-2005
 */
package it.algos.base.tavola.editor;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.TavolaModello;
import it.algos.base.tavola.Tavola;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * Superclasse astratta per gli editor delle celle nella tavola.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 11-mar-2005 ore 9.13.35
 */
public abstract class EditorBase extends AbstractCellEditor implements TableCellEditor {

    /**
     * Costruttore base senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public EditorBase() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     */
    public EditorBase(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia

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
    
}// fine della classe
