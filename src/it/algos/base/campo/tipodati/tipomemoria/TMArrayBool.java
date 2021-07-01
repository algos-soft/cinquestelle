/**
 * Title:        TMIntero.java
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

import java.util.ArrayList;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare il <i>design pattern</i> <b>Singleton</b>  <br>
 * B - Definire un modello dati per il tipo Memoria Array di Booleani <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 ottobre 2003 alle 12.11
 */
public final class TMArrayBool extends TMBase implements TipoMemoria {

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private final static TMArrayBool ISTANZA = new TMArrayBool();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    private TMArrayBool() {
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
        this.setClasse((new ArrayList<Boolean>()).getClass());
        this.setValoreVuoto(new ArrayList<Boolean>());
    } /* fine del metodo inizia */


    /**
     * metodo getter per ottenere il valore della variabile statica privata
     */
    public static TMArrayBool getIstanza() {
        return ISTANZA;
    } /* fine del metodo getter */


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
    private boolean isModificatoSpecifico(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        boolean modificato = false;
        Object unOggettoMemoria;
        Object unOggettoBackup;
        ArrayList<Boolean> listaMemoria;
        ArrayList<Boolean> listaBackup;
        boolean mem;
        boolean back;

        try { // prova ad eseguire il codice

            /* recupera il valore della memoria */
            unOggettoMemoria = unCampo.getCampoDati().getMemoria();
            listaMemoria = (ArrayList<Boolean>)unOggettoMemoria;

            /* recupera il valore del backup */
            unOggettoBackup = unCampo.getCampoDati().getBackup();
            listaBackup = (ArrayList<Boolean>)unOggettoBackup;

//            /*
//            * Controlla se i valori sono diversi
//            * Spazzola la lista memoria
//            * Se trova un corrispondente elemento nella
//            * lista di backup, lo confronta
//            * Altrimenti, assume che il backup sia falso
//            */
//            for (int k = 0; k < listaMemoria.size(); k++) {
//                mem = listaMemoria.get(k);
//                back = false;
//                if (listaBackup.size() >= k) {
//                    back = listaBackup.get(k);
//                }// fine del blocco if
//                if (mem != back) {
//                    modificato = true;
//                    break;
//                }// fine del blocco if
//            } // fine del ciclo for

            /* se hanno dimensioni diverse e' modificato
             * se hanno la stessa dimensione, confronta
             * i singoli elementi */
            if (listaMemoria.size() != listaBackup.size()) {
                modificato = true;
            } else {
                for (int k = 0; k < listaMemoria.size(); k++) {
                    mem = listaMemoria.get(k);
                    back = listaBackup.get(k);
                    if (mem != back) {
                        modificato = true;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modificato;
    }


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
        int unCodice;

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
    }

}// fine della classe
