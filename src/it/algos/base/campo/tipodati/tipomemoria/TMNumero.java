/**
 * Title:     TMNumero
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      30-mar-2006
 */
package it.algos.base.campo.tipodati.tipomemoria;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

/**
 * Tipo memoria astratto per tipi numerici
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi
 * @version 1.0  /  30-mar-2006 alle 12.11
 */
public abstract class TMNumero extends TMBase implements TipoMemoria {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    protected TMNumero() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Controlla se un campo e' modificato
     * confronta il valore memoria col valore backup
     *
     * @param unCampo il campo da controllare
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
     * Controlla se un campo e' stato modificato
     * per il tipo specifico di dati Memoria
     * Se si arriva qui, gli oggetti valore Memoria e Backup sono entrambi
     * non nulli, della stessa classe e della classe di questo tipo Memoria
     * Sovrascritto dalle sottclassi
     *
     * @param unCampo il campo da controllare
     *
     * @return true se e' modificato
     */
    protected boolean isModificatoSpecifico(Campo unCampo) {
        /* valore di ritorno */
        return false;
    } /* fine del metodo */


}// fine della classe
