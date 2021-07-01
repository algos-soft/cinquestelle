/**
 * Title:        TMBase.java
 * Package:      it.algos.base.campo.tipomemoria
 * Description:  Tipo Dati Memoria Astratto
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 20 ottobre 2003 alle 12.57
 */
package it.algos.base.campo.tipodati.tipomemoria;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.tipodati.TDBase;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Implementare un oggetto astratto che descrive il tipo dati Memoria (e Backup)
 * da associare al campo <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 ottobre 2003 alle 12.57
 */
public abstract class TMBase extends TDBase implements TipoMemoria {

    /**
     * valore per campo non modificato
     */
    protected static final int NON_MODIFICATO = 0;

    /**
     * valore per campo modificato
     */
    protected static final int MODIFICATO = 1;

    /**
     * valore per indicare che il confronto va effettuato nella classe specifica
     */
    protected static final int CONTROLLO_SPECIFICO = 2;

    /**
     * valore per indicare che i valori non sono congrui e il confronto
     * non puo' essere effettuato
     */
    protected static final int ERRORE_NON_CONGRUO = 3;

    /**
     * flag per messaggio di errore valore non congruo
     */
    private static final boolean mostraErroreNonCongruo = true;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    protected TMBase() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


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
     * @return un codice intero con la seguente codifica:
     *         NON_MODIFICATO se non e' modificato,
     *         MODIFICATO se e' modificato,
     *         CONTROLLO_SPECIFICO se il confronto va effettuato nella classe specifica
     *         ERRORE_NON_CONGRUO se i due valori sono della stessa classe ma questa
     *         non e' congrua con la classe del tipo dati del campo
     */
    protected int isModificato(Campo unCampo) {

        /** variabili e costanti locali di lavoro */
        int codiceModificato = 0;
        CampoDati campoDati;
        Object unOggettoMemoria = null;
        Object unOggettoBackup = null;
        String unNomeClasseValori = null;
        String unNomeClasseTipo = null;

        /** confronta gli oggetti */
        try {    // prova ad eseguire il codice

            campoDati = unCampo.getCampoDati();
            if (campoDati != null) {
                /** recupera il valore della memoria */
                unOggettoMemoria = campoDati.getMemoria();

                /** recupera il valore del backup */
                unOggettoBackup = campoDati.getBackup();
            }// fine del blocco if

            /** se entrambi i valori sono nulli, non e' modificato
             *  se solo uno dei due valori e' nullo, e' modificato
             *  se entrambi i valori non sono nulli, li deve confrontare
             *     nella classe specifica */
            if (isCoppiaConfrontabile(unOggettoMemoria, unOggettoBackup)) {

                /** entrambi sono non-nulli e della stessa classe
                 *  controlla che la classe dei valori corrisponda
                 *  alla classe del tipo dati */
                unNomeClasseValori = unOggettoMemoria.getClass().getName();
                unNomeClasseTipo = this.getClasse().getName();

                /** Se la classe dei valori e' uguale alla classe
                 *  del tipo dati del campo, esegue il controllo nel tipo specifico
                 *  Se e' diversa, e' un errore. */
                if (unNomeClasseValori.equals(unNomeClasseTipo)) {

                    /** il controllo va eseguito dal tipo specifico */
                    codiceModificato = CONTROLLO_SPECIFICO;

                } else {

                    /** errore - il controllo non puo' essere eseguito */
                    codiceModificato = ERRORE_NON_CONGRUO;

                    /** se previsto visualizza un messaggio */
                    if (mostraErroreNonCongruo) {
                        String unTesto = "Valore non congruo con il tipo dati\n";
                        unTesto += "Campo: " + unCampo.getNomeInterno() + "\n";
                        unTesto += "Tipo atteso: " + unNomeClasseTipo + "\n";
                        unTesto += "Tipo trovato: " + unNomeClasseValori;
                        new MessaggioAvviso(unTesto);
                    } /* fine del blocco if */

                } /* fine del blocco if-else */

            } else {    // uno solo o entrambi nulli

                /** uno solo o entrambi sono nulli */
                if (this.isUnoNullo(unOggettoMemoria, unOggettoBackup)) {

                    /** uno solo dei due e' nullo */
                    codiceModificato = MODIFICATO;

                } else {

                    /** sono entrambi nulli */
                    codiceModificato = NON_MODIFICATO;

                } /* fine del blocco if/else */

            } /* fine del blocco if/else */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return codiceModificato;

    } /* fine del metodo */


    /**
     * Esamina una coppia di valori per determinare se sono confrontabili
     * (entrambi non sono nulli e sono della stessa classe)
     * Si limita a esaminare lo stato nullo/non nullo,
     * il confronto effettivo dei valori viene effettuato
     * dal tipo Memoria specifico del campo.
     *
     * @param unValoreMemoria il valore Memoria
     * @param unValoreBackup il valore Backup
     *
     * @return true se i due oggetti sono confrontabili
     */
    private static boolean isCoppiaConfrontabile(Object unValoreMemoria, Object unValoreBackup) {

        /** variabili e costanti locali di lavoro */
        boolean isConfrontabili = false;
        String unaClasseMemoria = null;
        String unaClasseBackup = null;

        try {    // prova ad eseguire il codice

            /** se entrambi i valori sono nulli, non e' confrontabile
             *  se entrambi i valori non sono nulli, li confronta */
            if ((unValoreMemoria != null) && (unValoreBackup != null)) {

                /** Recupera le classi dei due oggetti */
                unaClasseMemoria = unValoreMemoria.getClass().getName();
                unaClasseBackup = unValoreBackup.getClass().getName();

                /** se sono della stessa classe sono confrontabili*/
                if (unaClasseMemoria.equals(unaClasseBackup)) {
                    isConfrontabili = true;
                } /* fine del blocco if */

            } /* fine del blocco if/else */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return isConfrontabili;
    } /* fine del metodo */


    /**
     * verifica se uno solo di due oggetti e' nullo
     *
     * @param unOggettoMemoria il primo oggetto
     * @param unOggettoBackup il secondo oggetto
     *
     * @return true se uno solo dei due e' nullo
     */
    private static boolean isUnoNullo(Object unOggettoMemoria, Object unOggettoBackup) {
        /** variabili e costanti locali di lavoro */
        boolean isUnoNullo = false;

        try {    // prova ad eseguire il codice
            if ((unOggettoMemoria != null) || (unOggettoBackup != null)) {
                isUnoNullo = true;
            } /* fine del blocco if/else */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return isUnoNullo;
    } /* fine del metodo */


}// fine della classe

