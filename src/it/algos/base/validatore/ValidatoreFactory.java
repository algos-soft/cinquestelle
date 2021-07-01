/**
 * Title:     ValidatoreFactory
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      31-mar-2006
 */
package it.algos.base.validatore;

import it.algos.base.errore.Errore;

import java.util.Date;

/**
 * Factory per la creazione di validatori standard.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0
 */
public abstract class ValidatoreFactory {

    /**
     * Crea un generico validatore di testo.
     * <p/>
     */
    public static Validatore testo() {
        return new ValidatoreTesto();
    }


    /**
     * Crea un generico validatore numerico.
     * <p/>
     */
    public static Validatore num() {
        return new ValidatoreNumero();
    }


    /**
     * Crea un validatore di testo con lunghezza minima e massima fissa.
     * <p/>
     *
     * @param len la lunghezza fissa
     */
    public static Validatore testoFisso(int len) {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.testo();
        val.setLunghezzaMinima(len);
        val.setLunghezzaMassima(len);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore di testo non vuoto.
     * <p/>
     */
    public static Validatore testoNonVuoto() {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.testo();
        val.setAccettaTestoVuoto(false);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore di testo con lunghezza minima.
     * <p/>
     *
     * @param len la lunghezza minima
     */
    public static Validatore testoLunMin(int len) {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.testo();
        val.setLunghezzaMinima(len);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore di testo con lunghezza massima.
     * <p/>
     *
     * @param len la lunghezza minima
     */
    public static Validatore testoLunMax(int len) {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.testo();
        val.setLunghezzaMassima(len);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore per codice fiscale.
     * <p/>
     */
    public static Validatore codiceFiscale() {
        return new ValidatoreCF();
    }


    /**
     * Crea un validatore per partita IVA.
     * <p/>
     */
    public static Validatore partitaIVA() {
        return new ValidatorePI();
    }


    /**
     * Crea un validatore numerico intero.
     * <p/>
     */
    public static Validatore numInt() {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.num();
        val.setAccettaDecimali(false);
        val.setValoreMassimo(Integer.MAX_VALUE);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore numerico intero positivo.
     * <p/>
     */
    public static Validatore numIntPos() {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.numInt();
        val.setAccettaNegativi(false);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore numerico intero positivo maggiore di zero.
     * <p/>
     */
    public static Validatore numIntPosMaggZero() {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.numIntPos();
        val.setAccettaZero(false);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore numerico reale.
     * <p/>
     */
    public static Validatore numReal() {
        /* variabili e costanti locali di lavoro */
        Validatore val;

        val = ValidatoreFactory.num();
        val.setAccettaDecimali(true);
        val.setValoreMinimo(-Double.MAX_VALUE);
        val.setValoreMassimo(Double.MAX_VALUE);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore numerico reale positivo.
     * <p/>
     */
    public static Validatore numRealPos() {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.numReal();
        val.setAccettaNegativi(false);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore numerico reale con un numero massimo di decimali.
     * <p/>
     *
     * @param dec il massimo numero di decimali
     */
    public static Validatore numDec(int dec) {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.numReal();
        val.setMaxCifreDecimali(dec);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore numerico reale con al massimo 2 decimali.
     * <p/>
     */
    public static Validatore numDec2() {
        /* variabili e costanti locali di lavoro */
        Validatore val = null;

        val = ValidatoreFactory.numDec(2);

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore che accetta solo date future
     * <p/>
     *
     * @param oggi true per accettare anche il giorno di oggi
     *
     * @return il validatore creato
     */
    public static ValidatoreData dateFuture(boolean oggi) {
        /* variabili e costanti locali di lavoro */
        ValidatoreData val = null;

        try { // prova ad eseguire il codice
            val = new ValidatoreData(true, 0, oggi);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore che accetta solo date passate
     * <p/>
     *
     * @param oggi true per accettare anche il giorno di oggi
     *
     * @return il validatore creato
     */
    public static ValidatoreData datePassate(boolean oggi) {
        /* variabili e costanti locali di lavoro */
        ValidatoreData val = null;

        try { // prova ad eseguire il codice
            val = new ValidatoreData(false, 0, oggi);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore che accetta solo date future entro un dato numero di giorni
     * <p/>
     *
     * @param maxGiorni il massimo numero di giorni di differenza accettabile
     * @param oggi true per accettare anche il giorno di oggi
     *
     * @return il validatore creato
     */
    public static ValidatoreData dateFutureMax(int maxGiorni, boolean oggi) {
        /* variabili e costanti locali di lavoro */
        ValidatoreData val = null;

        try { // prova ad eseguire il codice
            val = new ValidatoreData(true, maxGiorni, oggi);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore che accetta solo date passate entro un dato numero di giorni
     * <p/>
     *
     * @param maxGiorni il massimo numero di giorni di differenza accettabile
     * @param oggi true per accettare anche il giorno di oggi
     *
     * @return il validatore creato
     */
    public static ValidatoreData datePassateMax(int maxGiorni, boolean oggi) {
        /* variabili e costanti locali di lavoro */
        ValidatoreData val = null;

        try { // prova ad eseguire il codice
            val = new ValidatoreData(false, maxGiorni, oggi);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore che accetta solo le date nel range specificato (comprese)
     * <p/>
     *
     * @param dataMin la data minima (null per non specificare)
     * @param dataMax la tada minima (null per non specificare)
     *
     * @return il validatore creato
     */
    public static ValidatoreData dateRange(Date dataMin, Date dataMax) {
        /* variabili e costanti locali di lavoro */
        ValidatoreData val = null;

        try { // prova ad eseguire il codice
            val = new ValidatoreData(dataMin, dataMax);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore che accetta solo le date dopo una data specificata (compresa)
     * <p/>
     *
     * @param dataMin la data minima
     *
     * @return il validatore creato
     */
    public static ValidatoreData dateDopo(Date dataMin) {
        /* variabili e costanti locali di lavoro */
        ValidatoreData val = null;

        try { // prova ad eseguire il codice
            val = new ValidatoreData(dataMin, null);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore che accetta solo le date prima di una data specificata (compresa)
     * <p/>
     *
     * @param dataMax la data minima
     *
     * @return il validatore creato
     */
    public static ValidatoreData datePrima(Date dataMax) {
        /* variabili e costanti locali di lavoro */
        ValidatoreData val = null;

        try { // prova ad eseguire il codice
            val = new ValidatoreData(null, dataMax);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return val;
    }


    /**
     * Crea un validatore che accetta tutte le ore
     * <p/>
     *
     * @return il validatore creato
     */
    public static ValidatoreOra ora() {
        /* variabili e costanti locali di lavoro */
        ValidatoreOra val = null;

        try { // prova ad eseguire il codice
            val = new ValidatoreOra(0, 0);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return val;
    }



} // fine della classe
