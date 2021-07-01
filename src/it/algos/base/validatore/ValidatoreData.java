/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      21-apr-2008
 */
package it.algos.base.validatore;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.progetto.Progetto;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Validatore per i campi Data.
 * <p>
 * Questo validatore ha due comportamenti diversi a seconda del costruttore utilizzato.
 * - Il primo costruttore crea un validatore con controllo dinamico della data
 * in base alla data del giorno corrente (al momento del controllo).
 * E' possibile specificare se accettare date passate o future, se il giorno di oggi
 * deve essere accettato, e qual'è il massimo numero di giorni di differenza accettabile.
 * - Il secondo costruttore crea un validatore con controllo statico della data
 * in base a una o due date di riferimento fornite.
 * Gli estremi sono sempre compresi.
 * E' possibile omettere una delle due date per non effettuare il controllo sull'estremo
 * corrispondente.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 21-apr-2008 ore 10.42.18
 */

public class ValidatoreData extends ValidatoreBase {

    /* flag - indica se il confronto date è statico (rispetto a una o due date fisse fornite)
     * o dinamico (rispetto alla data di oggi)*/
    private boolean dinamico;

    /* data minima (se compilata, deve essere posteriore o uguale a questa) (solo statico)*/
    private Date dataMin;

    /* data massima (se compilata, deve essere precedente o uguale a questa) (solo statico)*/
    private Date dataMax;

    /* se accetta date future o date passate (solo dinamico)*/
    private boolean accettaFuturo;

    /* massimo numero accettabile di giorni di differenza da oggi (solo dinamico)*/
    private int deltaMax;

    /* true se deve accettare anche la data di oggi (solo dinamico)*/

    private boolean accettaOggi;


    /**
     * Costruttore per confronto dinamico con la data di oggi
     * <p/>
     *
     * @param accettaFuturo true se accetta solo date nel futuro, false se accetta solo date nel passato
     * @param deltaMax massimo numero accettabile di giorni di differenza da oggi (0 = non specificato)
     * @param accettaOggi true se deve accettare anche la data di oggi
     */
    public ValidatoreData(boolean accettaFuturo, int deltaMax, boolean accettaOggi) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setDinamico(true);
        this.setAccettaFuturo(accettaFuturo);
        this.setDeltaMax(deltaMax);
        this.setAccettaOggi(accettaOggi);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Costruttore per confronto statico con data minima e massima (comprese)
     * <p/>
     *
     * @param dataMin la data minima
     * @param dataMax la data massima
     */
    public ValidatoreData(Date dataMin, Date dataMax) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setDinamico(false);
        this.setDataMin(dataMin);
        this.setDataMax(dataMax);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setValidaKeystroke(false);
    }


    /**
     * Implementazione della logica di validazione.
     * <p/>
     * Implementare la logica di validazione in questo metodo.
     * Ritorna true se il dato e' valido, false se non e' valido.
     * All'interno della validazione e'anche possibile modificare
     * il messaggio in caso di dato non valido usando setMessaggio().
     *
     * @param testo da validare
     * @param comp componente di riferimento
     *
     * @return true se valido, false se non valido.
     */
    protected boolean validate(String testo, JComponent comp) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        Date data;
        SimpleDateFormat format;
        String mex = "";
        Object oggetto;

        try { // prova ad eseguire il codice

            format = Progetto.getShortDateFormat();
            try { // prova ad eseguire il codice
                oggetto = format.parseObject(testo);

                /* procede solo se è una data "valida" */
                if ((oggetto != null) && (oggetto instanceof Date)) {

                    /* recupera la data */
                    data = (Date)oggetto;

                    /* esegue il controllo statico o dinamico */
                    if (this.isDinamico()) {
                        mex = this.validateDinamico(data);
                    } else {
                        mex = this.validateStatico(data);
                    }// fine del blocco if-else

                    /* controlla il ritorno e mostra il messaggio */
                    if (Lib.Testo.isValida(mex)) {
                        valido = false;
                        this.setMessaggio("Data non valida!\n" + mex);
                    }// fine del blocco if


                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                // parse non riuscito
            }// fine del blocco try-catch
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Validazione con controllo dinamico.
     * <p/>
     *
     * @param data da validare
     *
     * @return stringa vuota se valida, messaggio di errore se non valida
     */
    private String validateDinamico(Date data) {
        /* variabili e costanti locali di lavoro */
        String mex = "";
        Date dataOggi;
        int deltaMax;
        Date dataVicina;
        Date dataLontana;

        try {    // prova ad eseguire il codice

            dataOggi = Lib.Data.getCorrente();
            deltaMax = this.getDeltaMax();
            dataVicina = dataOggi;

            if (this.isAccettaFuturo()) {     // accetta solo il futuro

                /* se non accetta oggi, sposta la data vicina di 1 giorno nel futuro */
                if (!this.isAccettaOggi()) {
                    dataVicina = Lib.Data.add(dataOggi, 1);
                }// fine del blocco if

                if (deltaMax > 0) {    // specificato un limite nel futuro

                    dataLontana = Lib.Data.add(dataOggi, deltaMax);
                    if (!Lib.Data.isCompresaUguale(dataVicina, dataLontana, data)) {
                        mex =
                                "Deve essere compresa tra il " +
                                        Lib.Data.getStringa(dataVicina) +
                                        " e il " +
                                        Lib.Data.getStringa(dataLontana);
                    }// fine del blocco if

                } else {     // non specificato un limite nel futuro

                    if (!Lib.Data.isPosterioreUguale(dataVicina, data)) {
                        if (!this.isAccettaOggi()) {
                            mex = "Deve essere posteriore a oggi";
                        } else {
                            mex = "Deve essere posteriore o uguale a oggi";
                        }// fine del blocco if-else

                    }// fine del blocco if

                }// fine del blocco if-else


            } else {      // accetta solo il passato

                /* se non accetta oggi, sposta la data vicina di 1 giorno nel passato */
                if (!this.isAccettaOggi()) {
                    dataVicina = Lib.Data.add(dataOggi, -1);
                }// fine del blocco if

                if (deltaMax > 0) {    // specificato un limite nel passato

                    dataLontana = Lib.Data.add(dataOggi, -deltaMax);
                    if (!Lib.Data.isCompresaUguale(dataLontana, dataVicina, data)) {
                        mex =
                                "Deve essere compresa tra il " +
                                        Lib.Data.getStringa(dataLontana) +
                                        " e il " +
                                        Lib.Data.getStringa(dataVicina);
                    }// fine del blocco if

                } else {     // non specificato un limite nel passato

                    if (!Lib.Data.isPrecedenteUguale(dataVicina, data)) {
                        if (!this.isAccettaOggi()) {
                            mex = "Deve essere precedente a oggi";
                        } else {
                            mex = "Deve essere precedente o uguale a oggi";
                        }// fine del blocco if-else

                    }// fine del blocco if

                }// fine del blocco if-else

            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mex;
    }


    /**
     * Validazione con controllo statico.
     * <p/>
     *
     * @param data da validare
     *
     * @return stringa vuota se valida, messaggio di errore se non valida
     */
    private String validateStatico(Date data) {
        /* variabili e costanti locali di lavoro */
        String mex = "";
        boolean valido = true;
        Date dataMin;
        Date dataMax;

        try {    // prova ad eseguire il codice

            /* controllo sul minimo */
            dataMin = this.getDataMin();
            if (dataMin != null) {
                if (Lib.Data.isPrecedente(dataMin, data)) {
                    mex = "Deve essere posteriore o uguale al " + Lib.Data.getStringa(dataMin);
                    valido = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se passato, controllo sul massimo */
            if (valido) {
                dataMax = this.getDataMax();
                if (dataMax != null) {
                    if (Lib.Data.isPosteriore(dataMax, data)) {
                        mex = "Deve essere precedente o uguale al " + Lib.Data.getStringa(dataMax);
                        valido = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mex;
    }


    private boolean isDinamico() {
        return dinamico;
    }


    private void setDinamico(boolean dinamico) {
        this.dinamico = dinamico;
    }


    private Date getDataMin() {
        return dataMin;
    }


    private void setDataMin(Date dataMin) {
        this.dataMin = dataMin;
    }


    private Date getDataMax() {
        return dataMax;
    }


    private void setDataMax(Date dataMax) {
        this.dataMax = dataMax;
    }


    private boolean isAccettaFuturo() {
        return accettaFuturo;
    }


    private void setAccettaFuturo(boolean accettaFuturo) {
        this.accettaFuturo = accettaFuturo;
    }


    private int getDeltaMax() {
        return deltaMax;
    }


    private void setDeltaMax(int deltaMax) {
        this.deltaMax = deltaMax;
    }


    private boolean isAccettaOggi() {
        return accettaOggi;
    }


    private void setAccettaOggi(boolean accettaOggi) {
        this.accettaOggi = accettaOggi;
    }
}// fine della classe