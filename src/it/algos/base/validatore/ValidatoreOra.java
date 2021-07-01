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
 * Validatore per i campi Ora.
 * <p>
 * Questo validatore ha due comportamenti diversi a seconda del costruttore utilizzato.
 * - Il primo costruttore crea un validatore con controllo dinamico dell'ora
 * in base all'ora corrente (al momento del controllo).
 * E' possibile specificare se accettare ore passate o future, se l'ora corrente
 * deve essere accettata, e qual'è il massimo numero di ore di differenza accettabile.
 * - Il secondo costruttore crea un validatore con controllo statico dell'ora
 * in base a una o due ore di riferimento fornite.
 * Gli estremi sono sempre compresi.
 * E' possibile omettere una delle due ore o entrambe per non effettuare il controllo
 * sull'estremo corrispondente.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 21-apr-2008 ore 10.42.18
 */

public class ValidatoreOra extends ValidatoreBase {

    /* flag - indica se il confronto date è statico (rispetto a una o due date fisse fornite)
     * o dinamico (rispetto alla data di oggi)*/
    private boolean dinamico;

    /* ora minima (se compilata, deve essere posteriore o uguale a questa) (solo statico)*/
    private int oraMin;

    /* ora massima (se compilata, deve essere precedente o uguale a questa) (solo statico)*/
    private int oraMax;

    /* se accetta ore future o ore passate (solo dinamico)*/
    private boolean accettaFuturo;

    /* massimo numero accettabile di ore di differenza da adesso (solo dinamico)*/
    private int deltaMax;

    /**
     * Costruttore per confronto dinamico con la ora corrente
     * <p/>
     *
     * @param accettaFuturo true se accetta solo ore nel futuro, false se accetta solo ore nel passato
     * @param deltaMax massimo numero accettabile di secondi di differenza da adesso (0 = non specificato)
     */
    public ValidatoreOra(boolean accettaFuturo, int deltaMax) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setDinamico(true);
        this.setAccettaFuturo(accettaFuturo);
        this.setDeltaMax(deltaMax);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Costruttore per confronto statico con ora minima e massima (comprese)
     * <p/>
     *
     * @param oraMin ora minima
     * @param oraMax ora massima
     */
    public ValidatoreOra(int oraMin, int oraMax) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setDinamico(false);
        this.setOraMin(oraMin);
        this.setOraMax(oraMax);

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

            format = Progetto.getShortOraFormat();

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
                        this.setMessaggio("Ora non valida!\n" + mex);
                    }// fine del blocco if


                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                // parse non riuscito
            }// fine del blocco try-catch
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
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
        int secAdesso;
        int deltaMax;
        Date dataVicina;
        Date dataLontana;

        try {    // prova ad eseguire il codice

//            secAdesso = (int)System.currentTimeMillis()/1000;
//            deltaMax = this.getDeltaMax();



        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return "";
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

//            /* controllo sul minimo */
//            dataMin = this.getOraMin();
//            if (dataMin != null) {
//                if (Lib.Data.isPrecedente(dataMin, data)) {
//                    mex = "Deve essere posteriore o uguale al " + Lib.Data.getStringa(dataMin);
//                    valido = false;
//                }// fine del blocco if
//            }// fine del blocco if
//
//            /* se passato, controllo sul massimo */
//            if (valido) {
//                dataMax = this.getOraMax();
//                if (dataMax != null) {
//                    if (Lib.Data.isPosteriore(dataMax, data)) {
//                        mex = "Deve essere precedente o uguale al " + Lib.Data.getStringa(dataMax);
//                        valido = false;
//                    }// fine del blocco if
//                }// fine del blocco if
//            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return "";
    }


    private boolean isDinamico() {
        return dinamico;
    }


    private void setDinamico(boolean dinamico) {
        this.dinamico = dinamico;
    }


    private int getOraMax() {
        return oraMax;
    }


    private void setOraMax(int oraMax) {
        this.oraMax = oraMax;
    }


    private int getOraMin() {
        return oraMin;
    }


    private void setOraMin(int oraMin) {
        this.oraMin = oraMin;
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


}// fine della classe