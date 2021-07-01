/**
 * Title:        MessaggioDialogo.java
 * Package:      it.algos.base.messaggio
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 settembre 2002 alle 13.53
 */

package it.algos.base.messaggio;

import it.algos.base.errore.Errore;

import javax.swing.*;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Mostrare un messaggio di dialogo <br>
 * B - Estende le funzionalita della classe <code>JOptionPane</code> <br>
 * C - Riceve come parametro la chiave della risorsa testo da mostrare <br>
 * D - Visualizza un voce della finestra, recuperando dalle risorse quella
 * col nome <strong>xxx0</strong> (se non esiste usa il voce di default) <br>
 * E - Visualizza un numero di bottoni variabile, andando a cercare nelle
 * risorse tutti quelli che hanno il nome uguale alla chiave seguito da
 * un numero progressivo (<strong>xxx1, xxx2,</strong> ...) <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 settembre 2002 ore 13.53
 */
public class MessaggioDialogo extends MessaggioBase {

    /**
     * voce della finestra di dialogo
     */
    public static final String TITOLO_DI_DEFAULT = "Messaggio di conferma";


    /**
     * Costruttore base senza parametri <br>
     * Rimanda al costruttore completo utilizzando i valori di default
     */
    public MessaggioDialogo() {
        /** rimanda al costruttore di questa classe */
        this("", "", ' ');
    } /* fine del metodo costruttore base */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per il testo completo del messaggio
     */
    public MessaggioDialogo(String unaChiave) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, "", ' ');
    } /* fine del metodo costruttore */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per il testo iniziale del messaggio
     * @param unAggiunta testo aggiuntivo scritto al volo
     */
    public MessaggioDialogo(String unaChiave, String unAggiunta) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, unAggiunta, ' ');
    } /* fine del metodo costruttore */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per la prima parte del testo del messaggio
     * @param unCodice carattere aggiuntivo per la seconda parte del
     * messaggio, variabile secondo le condizioni
     */
    public MessaggioDialogo(String unaChiave, char unCodice) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, "", unCodice);
    } /* fine del metodo costruttore */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per la prima parte del testo del messaggio
     * @param unNumero valore da inserire tra le due parti del testo
     * @param unCodice carattere aggiuntivo per la seconda parte del
     * messaggio, variabile secondo le condizioni
     */
    public MessaggioDialogo(String unaChiave, int unNumero, char unCodice) {
        /* rimanda al costruttore di questa classe */
        this(unaChiave, Integer.toString(unNumero), unCodice);
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo
     *
     * @param unaChiave chiave per la prima parte del testo del messaggio
     * @param unTestoIntermedio testo aggiunto al volo nel mezzo
     * @param unCodice carattere aggiuntivo per la seconda parte del
     * messaggio, variabile secondo le condizioni
     */
    public MessaggioDialogo(String unaChiave, String unTestoIntermedio, char unCodice) {
        /* rimanda al costruttore della superclasse */
        super(unaChiave, unTestoIntermedio, unCodice);

        /* Operazioni alla partenza ed eventuale interfaccia utente */
        this.partenza();
    } /* fine del metodo costruttore completo */


    /**
     * Operazioni alla partenza ed eventuale interfaccia utente
     * Cambia il testo del bottone standard
     */
    private void partenza() {
        /**
         *  crea un array di oggetti (di tipo testo), di lunghezza uno e prende
         *  il valore del testo dalla classe specifica
         */
        String[] valori = super.recuperaBottoni();

        /* recupera il voce dalle risorse */
        super.recuperaTitolo(TITOLO_DI_DEFAULT);

        /* mostra il messaggio */
        this.risposta = this.showOptionDialog(null,
                unTesto,
                titoloDialogo,
                YES_NO_OPTION,
                WARNING_MESSAGE,
                null,
                valori,
                valori[1]);
    } /* fine del metodo */


    /**
     * Ritorna true se il dialogo è stato confermato.
     * <p/>
     * #return true se il dialogo è stato confermato
     */
    public boolean isConfermato() {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;

        try {    // prova ad eseguire il codice
            if (this.getRisposta() == JOptionPane.YES_OPTION) {
                confermato = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


}// fine della classe

