/**
 * Title:        FiltroData.java
 * Package:      it.algos.base.filtri
 * Description:  Filtro e controlli di inserimento delle date
 * Copyright:    Copyright (c) 2002,2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 26 gennaio 2003 alle 13.26
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002,2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------

package it.algos.base.filtro;

import it.algos.base.costante.CostanteData;
import it.algos.base.errore.ErroreInizia;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Sovrascrivere il metodo InsertString della classe PlainDocument per <br>
 * intercettare l'inserimento dei caratteri in un campo di tipo Data.
 * B - Implementare i controlli durante l'inserimento dei caratteri <br>
 * (caratteri ammessi, inserimento automatico separatori, controllo
 * lunghezza massima del campo)
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  26 gennaio 2003 ore 13.26
 */
public class FiltroData extends FiltroAlgos implements CostanteData {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "FiltroData";

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * formato per le date
     */
    private String formatoData = null;

    /**
     * posizioni dei due separatori all'interno della data
     */
    private int posizioneSeparatore1 = 0;

    private int posizioneSeparatore2 = 0;

    /**
     * separatore di default per le date
     */
    private String separatore = null;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public FiltroData() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            new ErroreInizia(NOME_CLASSE, unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        // Regola le variabili di istanza in base alle costanti di default
        formatoData = FORMATO_DATA_DEFAULT;
        separatore = SEPARATORE_DATA_DEFAULT;

        // Impostazioni per formato GGMMAAAA
        if (formatoData.equals(FORMATO_DATA_GGMMAAAA)) {
            posizioneSeparatore1 = 2;
            posizioneSeparatore2 = 5;
            return;
        } /* fine del blocco if */
        // Impostazioni per formato AAAAMMGG
        if (formatoData.equals(FORMATO_DATA_AAAAMMGG)) {
            posizioneSeparatore1 = 4;
            posizioneSeparatore2 = 7;
            return;
        } /* fine del blocco if */

        // Nessun formato data riconosciuto - lancia una Exception generica
        throw new Exception();

    } /* fine del metodo inizia */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * Filtra la stringa da inserire nella data, eliminando tutti i <br>
     * caratteri che non siano numeri o separatori validi.
     * Parametri: una stringa da filtrare.
     * Ritorna: una stringa filtrata.
     */
    private String filtraStringaInput(String unaStringaEntrante) {
        String unaStringaUscente = "";
        for (int k = 0; k < unaStringaEntrante.length(); k++) {
            char unCarattere = unaStringaEntrante.charAt(k);
            if (Character.isDigit(unCarattere) | this.isSeparatoreData(unCarattere)) {
                unaStringaUscente += unCarattere;
            }
        }
        return unaStringaUscente;
    }


    /**
     * Determina se va inserito un carattere separatore <br>
     * in fondo al buffer corrente.
     * Parametri:
     * String bufferCorrente: il contenuto attuale della data.
     * String unaStringaInput: la stringa da inserire.
     * Ritorna: true se e' possibile inserire un separatore <br>
     * prima della stringa da inserire
     */
    private boolean possoInserireSeparatore(String bufferCorrente, String unaStringaInput) {
        boolean posso = false;
        char unCarattere = '0'; // un valore qualsiasi
        if ((bufferCorrente.length() == posizioneSeparatore1) |
                (bufferCorrente.length() == posizioneSeparatore2)) {
            // Consente solo se si inserisce 1 carattere per volta
            if (unaStringaInput.length() == 1) {
                unCarattere = unaStringaInput.charAt(0);
                // Se il carattere da inserire è già un separatore, non consente
                if (isSeparatoreData(unCarattere) == false) {
                    // Se l'ultimo carattere della stringa bufferCorrente e' gia' un separatore, non consente
                    unCarattere = bufferCorrente.charAt(bufferCorrente.length() - 1);
                    if (isSeparatoreData(unCarattere) == false) {
                        // Se il buffer corrente contiene gia' 2 o piu' separatori, non consente
                        if (contaSeparatori(bufferCorrente) < 2) {
                            posso = true;
                        } /* fine del blocco if */
                    } /* fine del blocco if */
                } /* fine del blocco if */
            } /* fine del blocco if */
        }

        return posso;
    }


    /**
     * Conta i separatori di data presenti in una stringa <br>
     * Parametri: String unaStringa: stringa da controllare
     * Ritorna: int numero di separatori presenti nella stringa.
     */
    private int contaSeparatori(String unaStringa) {
        int numeroSeparatori = 0;
        char unCarattere = '0'; // un valore qualsiasi
        for (int k = 0; k < unaStringa.length(); k++) {
            unCarattere = unaStringa.charAt(k);
            if (isSeparatoreData(unCarattere)) {
                ++numeroSeparatori;
            } /* fine del blocco if */
        }   /* fine del blocco for */
        return numeroSeparatori;
    }


    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    public void insertString(int offset, String unaStringaInput, AttributeSet a) throws
            BadLocationException {

        if (unaStringaInput == null) {
            return;
        }

        /** Filtra la stringa da inserire tenendo solo i caratteri consentiti.
         * (Quando l'input è da tastiera, normalmente si tratta di un solo <br>
         * carattere, ma eseguendo l'input con incolla, con macro di tastiera <br>
         * o per via procedurale, potrebbe trattarsi di una stringa piu' lunga)
         */
        String unaStringaFiltrata = filtraStringaInput(unaStringaInput);

        /** Aggiunge eventualmente un separatore prima della stringa da inserire*/
        String bufferCorrente = this.getText(0, getLength());
        if (this.possoInserireSeparatore(bufferCorrente, unaStringaFiltrata) == true) {
            unaStringaFiltrata = separatore + unaStringaFiltrata;
        }

        /** Inizio controlli */
        boolean controlliPassati = true;

        /** Controlla che il buffer non contenga già due separatori, <br>
         * in tal caso non si possono piu' aggiungere altri separatori.*/
        if (contaSeparatori(bufferCorrente) >= 2) {
            if (isSeparatoreData(unaStringaFiltrata.charAt(0))) {
                controlliPassati = false;
            } /* fine del blocco if */
        } /* fine del blocco if */

        /** Prima di inserire, controlla che il buffer non diventi piu' <br>
         * lungo del massimo consentito per una data (10 caratteri).*/
        if (bufferCorrente.length() >= 10) {
            controlliPassati = false;
        }

        /** Se tutti i controlli sono passati, inserisce la stringa */
        if (controlliPassati) {
            super.insertString(offset, new String(unaStringaFiltrata), a);
        } /* fine del blocco if */

    }


    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    /**
     * Determina se un dato carattere e' un separatore di data valido.
     * Parametri: char unCarattere: il carattere da esaminare.
     * Ritorna: true se e' un separatore valido, altrimenti false.
     */
    public boolean isSeparatoreData(char unCarattere) {
        return (SEPARATORI_DATA_VALIDI.indexOf(unCarattere) != -1);
    }
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.filtri.FiltroData.java
//-----------------------------------------------------------------------------
