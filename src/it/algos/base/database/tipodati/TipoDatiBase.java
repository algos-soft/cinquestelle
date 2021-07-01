/**
 * Title:     TipoDatiBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */

package it.algos.base.database.tipodati;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;

import java.lang.reflect.Array;

/**
 * Descrizione astratta di un tipo di dati per il Db
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 13.51.14
 */
public abstract class TipoDatiBase extends Object implements TipoDati {

    /**
     * database proprietario di questo oggetto
     */
    private Db database = null;

    /**
     * La classe Java che rappresenta i valori a livello
     * di business logic per questo tipo di dati.
     * I valori in entrata devono essere di questa classe,
     * altrimenti la conversione fallisce con errore.
     * I valori in uscita sono sempre di questa classe.
     */
    private Class classeBl = null;

    /**
     * valore considerato 'vuoto' per il database
     */
    private Object valoreVuotoDb = null;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param db il database proprietario di questo oggetto
     */
    protected TipoDatiBase(Db db) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola il riferimento al database */
        this.database = db;

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() throws Exception {
        this.valoreVuotoDb = null;
    } /* fine del metodo inizia */


    /**
     * Converte un valore da livello Business Logic
     * a livello Database per questo tipo di dati e per un dato Campo.
     * <p/>
     * Questo metodo tratta oggetti o arrays
     * Se è un oggetto, ritorna il valore convertito
     * Se è un array, converte ogni valore dell'array e ritorna l'array convertito
     * @param valoreIn il valore a livello di Business Logic da convertire (oggetto o array)
     * @param campo il campo a fronte del quale effettuare la conversione
     *
     * @return il valore convertito al livello di Database
     */
    public Object bl2db(Object valoreIn, Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valoreOut = null;
        Class classeIn;
        Object objIn;
        Object objOut;

        try {    // prova ad eseguire il codice

            classeIn = valoreIn.getClass();
            if (classeIn.isArray()) {
                int len = Array.getLength(valoreIn);
                for (int k = 0; k < len; k++) {
                    objIn = Array.get(valoreIn, k);
                    objOut = this.convertiValore(objIn, campo);
                    Array.set(valoreIn, k, objOut);
                } // fine del ciclo for
                valoreOut = valoreIn;
            } else {
                valoreOut = this.convertiValore(valoreIn, campo);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valoreOut;
    }


    /**
     * Converte un valore da livello Business Logic
     * a livello Database per questo tipo di dati e per un dato Campo.
     * <p/>
     *
     * @param valoreIn il valore a livello di Business Logic da convertire
     * @param campo il campo a fronte del quale effettuare la conversione
     *
     * @return il valore convertito al livello di Database
     */
    private Object convertiValore(Object valoreIn, Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valoreOut = null;
        Object valoreVuotoBl;
        Object valoreVuotoDb;
        String messaggio;

        try {    // prova ad eseguire il codice

            /**
             * Controlla che il valore in ingresso sia della classe di Business
             * Logic appropriata per questi tipo dati<br>
             * Se non lo e', o se e' nullo, solleva una eccezione e ritorna null
             */
            if (!this.isIstanzaClasseBl(valoreIn)) {
                messaggio = campo.getChiaveCampo();
                messaggio += "\nValore di business logic nullo o non compatibile.";
                throw new Exception(messaggio);
            }// fine del blocco if

            /* recupera il valore vuoto di business logic del campo */
            valoreVuotoBl = campo.getCampoDati().getValoreArchivioVuoto();

            /* se il valore da convertire e' il valore vuoto di BL del campo, usa il
             * corrispondente vuoto del Db, altrimenti usa il valore in ingresso */
            if (valoreIn.equals(valoreVuotoBl)) {
                valoreVuotoDb = this.getValoreVuotoDb();
                valoreOut = valoreVuotoDb;
            } else {
                valoreOut = valoreIn;
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valoreOut;
    }


    /**
     * Converte un valore da livello Database
     * a livello Business Logic per questo tipo di dati e per un dato Campo.
     * <p/>
     *
     * @param valoreIn il valore a livello di Database da convertire
     * @param campo il campo a fronte del quale effettuare la conversione
     *
     * @return il valore convertito al livello di Business Logic
     */
    public Object db2bl(Object valoreIn, Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valoreOut = null;
        Object valoreVuotoBl = null;
        Object valoreVuotoDb = null;

        try {    // prova ad eseguire il codice

            /* recupera il valore vuoto di business logic del campo */
            valoreVuotoBl = campo.getCampoDati().getValoreArchivioVuoto();

            /* se il valore db in ingresso e' nullo, usa il valore vuoto di
             * business logic del campo, altrimenti effettua la conversione */
            if (valoreIn == null) {
                valoreOut = valoreVuotoBl;
            } else {

                /* recupera il valore vuoto di database */
                valoreVuotoDb = this.getValoreVuotoDb();

                /* se il valore in ingresso e' il valore vuoto di Db, usa
                 * il valore vuoto di Bl del campo, altrimenti effettua la conversione */
                if (valoreIn.equals(valoreVuotoDb)) {
                    valoreOut = valoreVuotoBl;
                } else {
                    valoreOut = valoreIn;
                }// fine del blocco if-else

            }// fine del blocco if-else

            /* Controlla che il valore ottenuto sia della classe di BL appropriata
             * Se non lo e', o se e' nullo, solleva una eccezione e ritorna null */
            if (this.isIstanzaClasseBl(valoreOut) == false) {
                valoreOut = null;
                throw new Exception("Valore di business logic nullo o non compatibile.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valoreOut;
    }


    /**
     * Verifica se un oggetto e' compatibile con la classe
     * che rappresenta la business logic di questo tipo.<p>
     * Oggetti nulli non sono compatibili.
     *
     * @param oggetto l'oggetto da testare
     *
     * @return true se l'oggetto e' una istanza della classe
     */
    public boolean isIstanzaClasseBl(Object oggetto) {
        /* variabili e costanti locali di lavoro */
        boolean isIstanza = false;
        Class classeBl = null;

        try { // prova ad eseguire il codice
            classeBl = this.getClasseBl();
            if (classeBl.isInstance(oggetto)) {
                isIstanza = true;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return isIstanza;

    } /* fine del metodo */


    /**
     * Ritorna il valore 'vuoto' per il database
     * <p/>
     *
     * @return un oggetto rappresentante il valore
     */
    public Object getValoreVuotoDb() {
        return this.valoreVuotoDb;
    } /* fine del metodo setter */


    /**
     * Assegna il valore 'vuoto' per il database
     * <p/>
     *
     * @param valoreVuotoDb il valore 'vuoto'
     */
    public void setValoreVuotoDb(Object valoreVuotoDb) {
        this.valoreVuotoDb = valoreVuotoDb;
    }


    /**
     * Ritorna una stringa che rappresenta
     * un Valore per questo tipo di dato
     *
     * @param valore un oggetto valore
     *
     * @return una stringa rappresentante il valore
     */
    public String stringaValore(Object valore) {
        return valore.toString();
    } /* fine del metodo setter */


    /**
     * Ritorna true se questo tipo dati e' di tipo testo.
     * <p/>
     * E' di tipo Testo se la classe di business logic e' String.
     *
     * @return true se e' di tipo testo
     */
    public boolean isTipoTesto() {
        /* variabili e costanti locali di lavoro */
        boolean isTesto = false;
        Class classeTesto = null;

        try {    // prova ad eseguire il codice
            classeTesto = new String().getClass();
            if (this.getClasseBl() == classeTesto) {
                isTesto = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return isTesto;
    }


    /**
     * Ritorna la classe di business logic per questo tipo dati
     * <p/>
     *
     * @return la classe di business logic
     */
    public Class getClasseBl() {
        return classeBl;
    }


    /**
     * Assegna la classe di business logic per questo tipo dati
     * <p/>
     *
     * @param classe la classe di business logic
     */
    public void setClasseBl(Class classe) {
        this.classeBl = classe;
    }


    protected Db getDatabase() {
        return this.database;
    }


}// fine della classe