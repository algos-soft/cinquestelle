/**
 * Package:      it.algos.base.toolbar
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 6 ottobre 2005 alle 10.09
 */
package it.algos.base.validatore;

import javax.swing.*;

/**
 * Validatore di campo.
 * </p>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  6 ottobre 2005 ore 10.09
 */
public interface Validatore {

    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Viene chiamato una volta sola <p>
     *
     * @param componente di riferimento
     */
    public abstract void inizializza(JComponent componente);


    /**
     * Metodo chiamato direttamente da un componente
     * quando necessita di validazione.
     * <p/>
     *
     * @param componente da validare
     *
     * @return true se il componente e' valido
     */
    public abstract boolean verify(JComponent componente);


    /**
     * Controlla se il valore contenuto in un componente e' valido.
     * <p/>
     * Non evidenzia il componente e non visualizza messaggi.
     *
     * @param componente componente contenente il valore da validare
     *
     * @return true se e' valido, false se non e' valido
     */
    public abstract boolean isValido(JComponent componente);


    /**
     * Controlla se il valore contenuto in un componente e' valido.
     * <p/>
     * Eventualmente evidenzia il componente e mostra un messaggio.
     * Se non e' valido:
     * - se il parametro evidenza e' true
     * - evidenzia il componente colorando lo sfondo
     * - mostra un messaggio con la motivazione
     *
     * @param componente contenente il valore da validare
     * @param uscitaCampo - true se la chiamata e' dovuta all'uscita dal campo
     * (evidenzia sempre e mostra messaggio se non valido)
     * - false se la chiamata e' originata dall'inserimento di un carattere
     * (evidenzia e mostra messaggio in base alle
     * impostazioni del flag validaKeystroke di questo validatore)
     *
     * @return true se e' valido
     */
    public abstract boolean checkValido(JComponent componente, boolean uscitaCampo);


    /**
     * regola il messaggio da visualizzare in caso di validazione fallita.
     *
     * @param messaggio da visualizzaare
     */
    public abstract void setMessaggio(String messaggio);


    /**
     * Regular expression da soddisfare.
     *
     * @param espressione da soddisfare
     */
    public abstract void setEspressione(String espressione);


    /**
     * Controllo accettazione testo vuoto.
     *
     * @param accettaVuoto true per accettare il testo vuoto
     */
    public abstract void setAccettaTestoVuoto(boolean accettaVuoto);


    /**
     * Controllo lunghezza minima del testo.
     *
     * @param lunghezzaMinima del testo
     */
    public abstract void setLunghezzaMinima(int lunghezzaMinima);


    /**
     * Controllo lunghezza massima del testo.
     *
     * @param lunghezzaMassima del testo
     */
    public abstract void setLunghezzaMassima(int lunghezzaMassima);


    /**
     * Controllo accettazione dello zero.
     *
     * @param accettaZero true per accettare lo zero
     */
    public abstract void setAccettaZero(boolean accettaZero);


    /**
     * Controllo accettazione numeri negativi.
     *
     * @param accettaNegativi true per accettare i numeri negativi
     */
    public abstract void setAccettaNegativi(boolean accettaNegativi);


    /**
     * Controllo accettazione numeri positivi.
     *
     * @param accettaPositivi true per accettare i numeri positivi
     */
    public abstract void setAccettaPositivi(boolean accettaPositivi);


    /**
     * Controllo accettazione numeri decimali.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param accettaDecimali true per accettare i numeri decimali
     */
    public abstract void setAccettaDecimali(boolean accettaDecimali);


    /**
     * Restituisce il massimo numero di cifre decimali.
     *
     * @return il massimo numero di cifre decimali accettate
     */
    public abstract int getMaxCifreDecimali();


    /**
     * Controllo massimo numero di cifre decimali.
     *
     * @param maxCifreDecimali il massimo numero di cifre decimali accettate
     */
    public abstract void setMaxCifreDecimali(int maxCifreDecimali);


    /**
     * Controllo del valore minimo.
     *
     * @param valoreMinimo accettabile
     */
    public abstract void setValoreMinimo(double valoreMinimo);


    /**
     * Controllo del valore massimo.
     *
     * @param valoreMassimo accettabile
     */
    public abstract void setValoreMassimo(double valoreMassimo);

//    /**
//     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
//     */
//    public abstract Validatore clona();

}// fine della interfaccia
