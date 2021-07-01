/**
 * Title:     TMReale
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      30-mar-2006
 */
package it.algos.base.campo.tipodati.tipomemoria;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare il <i>design pattern</i> <b>Singleton</b>  <br>
 * B - Definire un modello dati per il tipo Memoria Intero <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 ottobre 2003 alle 12.11
 */
public final class TMReale extends TMNumero {

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private final static TMReale ISTANZA = new TMReale();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    private TMReale() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        }
    }


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setClasse(Double.class);
        this.setValoreVuoto(new Double(0));
    }


    /**
     * metodo getter per ottenere il valore della variabile statica privata
     */
    public static TMReale getIstanza() {
        return TMReale.ISTANZA;
    }


    /**
     * Controlla se un campo e' stato modificato
     * per questo tipo specifico di dati Memoria
     * Se si arriva qui, gli oggetti valore Memoria e Backup sono entrambi
     * non nulli, della stessa classe e della classe di questo tipo Memoria
     *
     * @param unCampo il campo da controllare
     *
     * @return true se e' modificato
     */
    protected boolean isModificatoSpecifico(Campo unCampo) {

        /** variabili e costanti locali di lavoro */
        boolean modificato = false;
        Object unOggettoMemoria = null;
        Object unOggettoBackup = null;
        Double unValoreMemoria = null;
        Double unValoreBackup = null;

        try { // prova ad eseguire il codice

            /** recupera il valore della memoria */
            unOggettoMemoria = unCampo.getCampoDati().getMemoria();

            /** recupera il valore del backup */
            unOggettoBackup = unCampo.getCampoDati().getBackup();

            /** controlla se sono diversi */
            unValoreMemoria = (Double)unOggettoMemoria;
            unValoreBackup = (Double)unOggettoBackup;
            if (!unValoreMemoria.equals(unValoreBackup)) {
                modificato = true;
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modificato;
    } /* fine del metodo */


}// fine della classe
