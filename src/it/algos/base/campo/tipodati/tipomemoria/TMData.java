/**
 * Title:        TMData.java
 * Package:      it.algos.base.campo.tipomemoria
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 20 ottobre 2003 alle 12.11
 */

package it.algos.base.campo.tipodati.tipomemoria;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.Date;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare il <i>design pattern</i> <b>Singleton</b>  <br>
 * B - Definire un modello dati per il tipo Memoria Data <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 ottobre 2003 alle 12.11
 */
public final class TMData extends TMBase implements TipoMemoria {

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private final static TMData ISTANZA = new TMData();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    private TMData() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            this.setClasse(Date.class);
            this.setValoreVuoto(Lib.Data.getVuota());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


    /**
     * Controlla se un campo e' stato modificato
     * per questo tipo specifico di dati Memoria
     * Se si arriva qui, gli oggetti valore Memoria e Backup sono entrambi
     * non nulli, della stessa classe e della classe di questo tipo Memoria
     *
     * @param unCampo da controllare
     *
     * @return true se e' modificato
     */
    private boolean isModificatoSpecifico(Campo unCampo) {

        /** variabili e costanti locali di lavoro */
        boolean modificato = false;
        Object unOggettoMemoria = null;
        Object unOggettoBackup = null;
        java.util.Date unValoreMemoria = null;
        java.util.Date unValoreBackup = null;

        /** recupera il valore della memoria */
        unOggettoMemoria = unCampo.getCampoDati().getMemoria();

        /** recupera il valore del backup */
        unOggettoBackup = unCampo.getCampoDati().getBackup();

        /** controlla se sono diversi */
        unValoreMemoria = (java.util.Date)unOggettoMemoria;
        unValoreBackup = (java.util.Date)unOggettoBackup;
        if (!unValoreMemoria.equals(unValoreBackup)) {
            modificato = true;
        } /* fine del blocco if */

        return modificato;
    } /* fine del metodo */


    /**
     * Controlla se un campo e' modificato
     * confronta il valore memoria col valore backup
     *
     * @param unCampo da controllare
     *
     * @return true se e' modificato
     */
    public boolean isCampoModificato(Campo unCampo) {

        /** variabili e costanti locali di lavoro */
        boolean modificato = false;
        int unCodice = 0;

        /** controlla se e' modificato nella superclasse
         *  (controlla eventuali valori nulli)*/
        unCodice = super.isModificato(unCampo);

        /** selezione */
        switch (unCodice) {
            case NON_MODIFICATO:
                modificato = false;
                break;
            case MODIFICATO:
                modificato = true;
                break;
            case CONTROLLO_SPECIFICO:
                // controlla per il tipo specifico
                if (this.isModificatoSpecifico(unCampo)) {
                    modificato = true;
                } /* fine del blocco if */
                break;
            case ERRORE_NON_CONGRUO:
                break;
            default:
                break;
        } /* fine del blocco switch */

        /** valore di ritorno */
        return modificato;
    } /* fine del metodo */


    /**
     * metodo getter per ottenere il valore della variabile statica privata
     */
    public static TMData getIstanza() {
        return ISTANZA;
    } /* fine del metodo getter */

}// fine della classe