/**
 * Title:        Flag.java
 * Package:      it.algos.base.flag
 * Description:  Variabili glogali del programma
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 24 novembre 2002 alle 18.37
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.flag;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.util.HashMap;
import java.util.LinkedHashMap;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Mantenere dei flags generali del programma (variabili booleane) <br>
 * B - Mantenere delle variabili globali <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  24 novembre 2002 ore 18.37
 */
public abstract class Flag {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "Flag";

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    /**
     * flag per creare una volta sola il set di comandi programma
     */
    private static boolean isComandiProgrammaCreati = false;

    /**
     * flag per creare una volta sola il set di azioni programma
     */
    private static boolean isAzioniProgrammaCreate = false;


    /**
     * flag per usare solo i caratteri 'a,b,c' e '1,2,3' nel campo testo
     */
    private static boolean isSoloCaratteri = true;

    /**
     * collezione chiave-valore per i comandi del programma
     */
    private static HashMap comandiProgramma = null;

    /**
     * collezione chiave-valore per le azioni del programma
     */
    private static HashMap azioniProgramma = null;

    /**
     * collezione chiave-valore per i moduli del programma
     */
    private static LinkedHashMap moduliProgramma = null;

    /**
     * collezione chiave-valore per le tabelle del programma
     */
    private static HashMap tabelleProgramma = null;

    /**
     * nome dell'utente corrente
     */
    private static String utenteCorrente = "";
    //-------------------------------------------------------------------------
    // Metodi statici della classe       (alla creazione)       (class methods)
    //-------------------------------------------------------------------------
    /** regolazione dei valori iniziali delle variabili */
    /**
     * Metodo statico.
     * <p/>
     * Invocato la prima volta che la classe statica viene richiamata nel programma <br>
     */
    static {
        /** crea la collezionechiave-valore di comandi programma (vuota) */
        comandiProgramma = new HashMap();

        /** crea la collezionechiave-valore di azioni programma (vuota) */
        azioniProgramma = new HashMap();

        /** crea la collezione chiave-valore di moduli programma (vuota) */
        moduliProgramma = new LinkedHashMap();

        /** crea la collezionechiave-valore di tabelle programma (vuota) */
        tabelleProgramma = new HashMap();
    } /* fine del metodo statico */


    //-------------------------------------------------------------------------
    // Metodi statici della classe    (a richiesta)             (class methods)
    //-------------------------------------------------------------------------
    /**
     * aggiunge un modulo alla collezione statica
     *
     * @return aggiunto vero se il modulo non esisteva nella collezione
     *         ed e' stato aggiunto
     */
    public static boolean addModulo(Object unModulo) {
        /** variabili e costanti locali di lavoro */
        boolean aggiunto = false;
        String unaChiave = "";
        Modulo unVeroModulo = null;

        /** controlla che l'oggetto sia del tipo giusto */
        if (unModulo instanceof Modulo) {
            /** controlla se esiste gia' */
            try {    // prova ad eseguire il codice
                /** casting alla classe corretta */
                unVeroModulo = (Modulo)unModulo;

                /** recupera il nome chiave del modulo */
                unaChiave = unVeroModulo.getNomeChiave();

                /** invoca il metodo delegato */
                addModulo(unaChiave, unModulo);
            } catch (Exception unErrore) {    // intercetta l'errore
                /** mostra il messaggio di errore */
                Errore.crea(unErrore);
            } /* fine del blocco try-catch */
        } /* fine del blocco if */

        /** valore di ritorno */
        return aggiunto;
    } /* fine del metodo statico */


    /**
     * aggiunge un modulo alla collezione statica
     *
     * @return aggiunto vero se il modulo non esisteva nella collezione
     *         ed e' stato aggiunto
     */
    public static boolean addModulo(String unaChiave, Object unModulo) {
        /** variabili e costanti locali di lavoro */
        boolean aggiunto = false;

        /** controlla che l'oggetto sia del tipo giusto */
        if (unModulo instanceof Modulo) {
            /** controlla se esiste gia' */
            try {    // prova ad eseguire il codice
                if (isEsisteModulo(unaChiave) == false) {
                    moduliProgramma.put(unaChiave, unModulo);
                    aggiunto = true;
                } /* fine del blocco if */
            } catch (Exception unErrore) {    // intercetta l'errore
                /** mostra il messaggio di errore */
                Errore.crea(unErrore);
            } /* fine del blocco try-catch */
        } /* fine del blocco if */

        /** valore di ritorno */
        return aggiunto;
    } /* fine del metodo statico */


    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * metodo setter per regolare il valore della variabile privata
     */
    public static void isComandiProgrammaCreati(boolean unValore) {
        isComandiProgrammaCreati = unValore;
    } /* fine del metodo setter */


    /**
     * metodo setter per regolare il valore della variabile privata
     */
    public static void isAzioniProgrammaCreate(boolean unValore) {
        isAzioniProgrammaCreate = unValore;
    } /* fine del metodo setter */


    /**
     * metodo setter per regolare il valore della variabile privata
     */
    public static void isSoloCaratteri(boolean unValore) {
        isSoloCaratteri = unValore;
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public static void setUtente(String unUtenteCorrente) {
        unUtenteCorrente = unUtenteCorrente;
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * controlla l'esistenza di un modulo
     *
     * @return esiste vero se esiste nella collezione
     */
    public static boolean isEsisteModulo(String unaChiave) {
        /** controlla se esiste */
        if (moduliProgramma.containsKey(unaChiave)) {
            return true;
        } else {
            return false;
        } /* fine del blocco if/else */
    } /* fine del metodo statico */


    /**
     * restituisce un modulo
     *
     * @return unModulo modulo richiesto
     */
    public static Modulo getModulo(String unaChiave) {
        /** valore di ritorno col casting al modulo */
        return (Modulo)moduliProgramma.get(unaChiave);
    } /* fine del metodo statico */


    /**
     * restituisce la collezione dei moduli (ordinati)
     */
    public static LinkedHashMap getModuliProgramma() {
        return moduliProgramma;
    } /* fine del metodo getter */


    /**
     * flag per creare una volta sola il set di comandi programma
     */
    public static boolean isComandiProgrammaCreati() {
        return isComandiProgrammaCreati;
    } /* fine del metodo getter */


    /**
     * flag per creare una volta sola il set di azioni programma
     */
    public static boolean isAzioniProgrammaCreate() {
        return isAzioniProgrammaCreate;
    } /* fine del metodo getter */

//    /** puntatore all'oggetto che ha creato il primo set di comandi */
//    public static GestoreOld getPrimoGestoreComandi() {
//	return primoGestoreComandi;
//    } /* fine del metodo getter */

//    /** puntatore all'oggetto che ha creato il primo set di azioni */
//    public static GestoreOld getPrimoGestoreAzioni() {
//	return primoGestoreAzioni;
//    } /* fine del metodo getter */


    /**
     * flag per usare solo i caratteri 'a,b,c' e '1,2,3' nel campo testo
     */
    public static boolean isSoloCaratteri() {
        return isSoloCaratteri;
    } /* fine del metodo getter */


    /**
     * metodo statico getter per ottenere il valore della variabile privata
     */
    public static HashMap getComandiProgramma() {
        return comandiProgramma;
    } /* fine del metodo getter */


    /**
     * metodo statico getter per ottenere il valore della variabile privata
     */
    public static HashMap getAzioniProgramma() {
        return azioniProgramma;
    } /* fine del metodo getter */


    /**
     * metodo statico getter per ottenere il valore della variabile privata
     */
    public static HashMap getTabelleProgramma() {
        return tabelleProgramma;
    } /* fine del metodo getter */


    /**
     * nome visibile del programma in esecuzione
     */
    public static String getUtente() {
        return utenteCorrente;
    } /* fine del metodo getter */
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.flag.Flag.java
//-----------------------------------------------------------------------------

