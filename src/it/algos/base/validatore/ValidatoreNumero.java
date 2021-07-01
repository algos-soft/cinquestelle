/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-set-2005
 */
package it.algos.base.validatore;

import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostCaratteri;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloCampo;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Validatore astratto specializzato per valori numerici.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 27-set-2005 ore 10.36.10
 */

public class ValidatoreNumero extends ValidatoreTesto {

    /**
     * Flag - controllo accettazione dello zero
     */
    private boolean accettaZero;

    /**
     * Flag - controllo accettazione numeri negativi
     */
    private boolean accettaNegativi;

    /**
     * Flag - controllo accettazione numeri positivi
     */
    private boolean accettaPositivi;

    /**
     * Flag - controllo accettazione numeri decimali
     */
    private boolean accettaDecimali;

    /**
     * Numero massimo di cifre decimali (0=non limitate)
     */
    private int maxCifreDecimali;

    /**
     * Valore minimo (0=non limitato).
     */
    private double valoreMinimo;

    /**
     * Valore massimo (0=non limitato).
     */
    private double valoreMassimo;

    /**
     * Formato da utilizzare per effettuare il parsing da stringa a numero
     */
    private NumberFormat formato;


    /**
     * Costruttore completo senza parametri.
     */
    public ValidatoreNumero() {
        /* rimanda al costruttore della superclasse */
        super();

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

        /* crea il formato da utilizzare per il parsing */
        this.setFormato(NumberFormat.getNumberInstance());

        /* regolazioni di default per il testo */
        this.setEspressione(CostCaratteri.NUMERI);
        this.setAccettaTestoVuoto(false);

        /* regolazioni di default per il numero */
        this.setAccettaZero(true);
        this.setAccettaNegativi(true);
        this.setAccettaPositivi(true);
        this.setAccettaDecimali(false);
        this.setMaxCifreDecimali(0);  // nessuna limitazione
        this.setValoreMinimo(0);  // nessuna limitazione
        this.setValoreMassimo(0);  // nessuna limitazione

    }


    /**
     * Implementazione della logica di validazione.
     * <p/>
     * Implementare la logica di validazione in questo metodo.
     * Ritorna true se il dato e' valido, false se non e' valido.
     * All'interno della validazione e'anche possibile modificare
     * il messaggio in caso di dato non valido usando setMessaggio().
     *
     * @param testo da validare.
     * @param comp componente di riferimento
     *
     * @return true se valido, false se non valido.
     */
    protected boolean validate(String testo, JComponent comp) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        double doppio = 0;
        NumberFormat formato;
        Number numero;

        try { // prova ad eseguire il codice

            /**
             * Se la stringa e' vuota, assegna al campo parente
             * il valore zero.
             * Poi prosegue i controlli usando la stringa "0"
             */
            if (!Lib.Testo.isValida(testo)) {
                this.setValoreZero(comp);
                testo = "0";
            }// fine del blocco if

            /* valida il testo nella superclasse */
            valido = super.validate(testo, comp);

            /* recupera il numero da validare come double */
            if (valido) {
                if (Lib.Testo.isValida(testo)) {
                    try { // prova ad eseguire il codice
                        formato = this.getFormato();
                        numero = formato.parse(testo);
                        doppio = numero.doubleValue();
                    } catch (Exception unErrore) { // intercetta l'errore
                        this.setMessaggio("Numero non valido");
                        valido = false;
                    }// fine del blocco try-catch
                }// fine del blocco if

            }// fine del blocco if

            /* controllo accettazione dello zero */
            if (valido) {
                valido = this.checkAccettaZero(doppio);
            }// fine del blocco if

            /* controllo accettazione numeri negativi */
            if (valido) {
                valido = this.checkAccettaNegativi(doppio);
            }// fine del blocco if

            /* controllo accettazione numeri positivi */
            if (valido) {
                valido = this.checkAccettaPositivi(doppio);
            }// fine del blocco if

            /* controllo accettazione numeri decimali */
            if (valido) {
                valido = this.checkAccettaDecimali(doppio);
            }// fine del blocco if

            /* controllo massimo numero di cifre decimali */
            if (valido) {
                valido = this.checkMaxCifreDecimali(doppio);
            }// fine del blocco if

            /* controllo del valore minimo */
            if (valido) {
                valido = this.checkValoreMinimo(doppio);
            }// fine del blocco if

            /* controllo del valore massimo */
            if (valido) {
                valido = this.checkValoreMassimo(doppio);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Assegna il valore numerico Zero al campo parente.
     * <p/>
     * Fa risalire il valore fino alla GUI.
     *
     * @param comp componente di riferimento
     */
    private void setValoreZero(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        Component pan;
        PannelloCampo pc;
        Campo campo;

        try {    // prova ad eseguire il codice
            pan = Lib.Comp.risali(comp, PannelloCampo.class);
            if (pan != null) {
                pc = (PannelloCampo)pan;
                campo = pc.getCampo();
                campo.setMemoria(0);
                campo.getCampoLogica().memoriaGui();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla accettazione dello zero.
     * <p/>
     *
     * @param valore da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkAccettaZero(double valore) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;

        try {    // prova ad eseguire il codice
            if (!this.isAccettaZero()) {
                if (valore == 0) {
                    valido = false;
                    this.setMessaggio("Non può essere zero");
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla accettazione numeri negativi.
     * <p/>
     *
     * @param valore da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkAccettaNegativi(double valore) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;

        try {    // prova ad eseguire il codice
            if (!this.isAccettaNegativi()) {
                if (valore < 0) {
                    valido = false;
                    this.setMessaggio("Non può essere negativo");
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla accettazione numeri negativi.
     * <p/>
     *
     * @param valore da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkAccettaPositivi(double valore) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;

        try {    // prova ad eseguire il codice
            if (!this.isAccettaPositivi()) {
                if (valore > 0) {
                    valido = false;
                    this.setMessaggio("Non può essere positivo");
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla accettazione numeri frazionali.
     * <p/>
     *
     * @param valore da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkAccettaDecimali(double valore) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;

        try {    // prova ad eseguire il codice
            if (!this.isAccettaDecimali()) {

                if (!this.isIntero(valore)) {
                    valido = false;
                    this.setMessaggio("Deve essere intero");
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla massimo numero di cifre decimali.
     * <p/>
     *
     * @param valore da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkMaxCifreDecimali(double valore) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        int maxCifre;
        int numCifre = 0;

        try {    // prova ad eseguire il codice

            maxCifre = this.getMaxCifreDecimali();
            if (maxCifre > 0) {
                numCifre = Lib.Mat.contaCifreDecimali(valore);
                if (numCifre > maxCifre) {
                    valido = false;
                    this.setMessaggio("Massimo " + maxCifre + " cifre decimali");
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla che il valore sia uguale o superiore al minimo.
     * <p/>
     *
     * @param valore da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkValoreMinimo(double valore) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        double min = 0;

        try {    // prova ad eseguire il codice
            min = this.getValoreMinimo();
            if (min != 0) {
                if (valore < min) {
                    valido = false;
                    this.setMessaggio("Il minimo è " + min);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla che il valore sia inferiore o uguale al massimo.
     * <p/>
     *
     * @param valore da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkValoreMassimo(double valore) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        double max = 0;

        try {    // prova ad eseguire il codice
            max = this.getValoreMassimo();
            if (max != 0) {
                if (valore > max) {
                    valido = false;
                    this.setMessaggio("Il massimo è " + max);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla se un numero è intero.
     * <p/>
     *
     * @param numero da controllare
     *
     * @return true se intero
     */
    private boolean isIntero(double numero) {
        /* variabili e costanti locali di lavoro */
        boolean intero = true;
        double down;

        try {    // prova ad eseguire il codice
            down = Math.floor(numero);  // Round to floor
            if (numero > down) {
                intero = false;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return intero;
    }


    private boolean isAccettaZero() {
        return accettaZero;
    }


    /**
     * Controllo accettazione dello zero.
     *
     * @param accettaZero true per accettare lo zero
     */
    public void setAccettaZero(boolean accettaZero) {
        this.accettaZero = accettaZero;
    }


    private boolean isAccettaNegativi() {
        return accettaNegativi;
    }


    /**
     * Controllo accettazione numeri negativi.
     *
     * @param accettaNegativi true per accettare i numeri negativi
     */
    public void setAccettaNegativi(boolean accettaNegativi) {
        this.accettaNegativi = accettaNegativi;
    }


    private boolean isAccettaPositivi() {
        return accettaPositivi;
    }


    /**
     * Controllo accettazione numeri positivi.
     *
     * @param accettaPositivi true per accettare i numeri positivi
     */
    public void setAccettaPositivi(boolean accettaPositivi) {
        this.accettaPositivi = accettaPositivi;
    }


    private boolean isAccettaDecimali() {
        return accettaDecimali;
    }


    /**
     * Controllo accettazione numeri decimali.
     *
     * @param accettaDecimali true per accettare i numeri decimali
     */
    public void setAccettaDecimali(boolean accettaDecimali) {
        this.accettaDecimali = accettaDecimali;
    }


    /**
     * Restituisce il massimo numero di cifre decimali.
     *
     * @return il massimo numero di cifre decimali accettate
     */
    public int getMaxCifreDecimali() {
        return maxCifreDecimali;
    }


    /**
     * Controllo massimo numero di cifre decimali.
     *
     * @param maxCifreDecimali il massimo numero di cifre decimali accettate
     */
    public void setMaxCifreDecimali(int maxCifreDecimali) {
        this.maxCifreDecimali = maxCifreDecimali;
    }


    private double getValoreMinimo() {
        return valoreMinimo;
    }


    /**
     * Controllo del valore minimo.
     *
     * @param valoreMinimo accettabile
     */
    public void setValoreMinimo(double valoreMinimo) {
        this.valoreMinimo = valoreMinimo;
    }


    private double getValoreMassimo() {
        return valoreMassimo;
    }


    /**
     * Controllo del valore massimo.
     *
     * @param valoreMassimo accettabile
     */
    public void setValoreMassimo(double valoreMassimo) {
        this.valoreMassimo = valoreMassimo;
    }


    private NumberFormat getFormato() {
        return formato;
    }


    protected void setFormato(NumberFormat formato) {
        this.formato = formato;
    }


}// fine della classe
