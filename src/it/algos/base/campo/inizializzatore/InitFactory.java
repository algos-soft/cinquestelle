/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-feb-2005
 */
package it.algos.base.campo.inizializzatore;

import it.algos.base.campo.base.Campo;
import it.algos.base.modulo.Modulo;

import java.util.ArrayList;

/**
 * Factory per la creazione di oggetti del package Inizializzatore.
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> <br>
 * <li> Fornisce metodi <code>statici</code> per la  creazione degli oggetti di questo
 * package </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-feb-2005 ore 11.29.07
 */
public abstract class InitFactory extends Object {

    /**
     * Costruttore completo senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public InitFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore completo


    /**
     * Crea un inizializzatore con valore fisso oggetto.
     * <p/>
     * Questo inizializzatore ritorna un valore fisso di tipo oggetto.
     * <br>
     *
     * @param oggetto il valore fisso di inizializzazione
     *
     * @return l'inizializzatore creato
     */
    public static Init oggetto(Object oggetto) {
        return new InitObject(oggetto);
    }


    /**
     * Crea un inizializzatore con valore fisso lista di booleani.
     * <p/>
     * Questo inizializzatore ritorna un valore fisso array list di booleani.
     * <br>
     *
     * @param valore il valore fisso di inizializzazione
     *
     * @return l'inizializzatore creato
     */
    public static Init boolArrayList(ArrayList<Boolean> valore) {
        return new InitBoolArrayList(valore);
    }


    /**
     * Crea un inizializzatore con valore fisso booleano.
     * <p/>
     * Questo inizializzatore ritorna un valore fisso booleano.
     * <br>
     *
     * @param valore il valore fisso di inizializzazione
     *
     * @return l'inizializzatore creato
     */
    public static Init bool(boolean valore) {
        return new InitBool(valore);
    }


    /**
     * Crea un inizializzatore con la data attuale di sistema.
     * <p/>
     * L'inizializzatore ritorna la data attuale di sistema.<br>
     *
     * @return l'inizializzatore creato
     */
    public static Init dataAttuale() {
        return new InitDataAttuale();
    }


    /**
     * Crea un inizializzatore standard.
     * <p/>
     * L'inizializzatore ritorna il valore memoria vuoto per il campo.<br>
     *
     * @param campo il campo di riferimento
     *
     * @return l'inizializzatore creato
     */
    public static Init standard(Campo campo) {
        return new InitDefault(campo);
    }


    /**
     * Crea un inizializzatore con valore fisso intero.
     * <p/>
     * Questo inizializzatore ritorna un valore fisso intero.
     * <br>
     *
     * @param valore il valore fisso di inizializzazione
     *
     * @return l'inizializzatore creato
     */
    public static Init intero(int valore) {
        return new InitInt(valore);
    }


    /**
     * Crea un inizializzatore sequenziale.
     * <p/>
     * Questo inizializzatore ritorna un valore progressivo pari al massimo
     * valore esistente sul DB per il campo, incrementato di 1
     * <br>
     *
     * @param campo il campo di riferimento
     *
     * @return l'inizializzatore creato
     */
    public static Init sequenziale(Campo campo) {
        return new InitSequenziale(campo);
    }


    /**
     * Crea un inizializzatore da contatore.
     * <p/>
     * Questo inizializzatore ritorna un valore progressivo
     * basato su un contatore esterno
     * <br>
     *
     * @param modulo il campo di riferimento
     *
     * @return l'inizializzatore creato
     */
    public static Init contatore(Modulo modulo) {
        return new InitContatore(modulo);
    }


    /**
     * Crea un inizializzatore con valore fisso di testo.
     * <p/>
     * Questo inizializzatore ritorna un valore fisso di testo.
     * <br>
     *
     * @param valore il valore fisso di inizializzazione
     *
     * @return l'inizializzatore creato
     */
    public static Init testo(String valore) {
        return new InitTesto(valore);
    }


    /**
     * Crea un inizializzatore con il timestamp attuale di sistema.
     * <p/>
     * Questo inizializzatore ritorna il timestamp attuale di sistema.
     * <br>
     *
     * @return l'inizializzatore creato
     */
    public static Init timestampAttuale() {
        return new InitTimestampAttuale();
    }


    /**
     * Crea un inizializzatore con l'id dell'utente corrente.
     * <p/>
     * Questo inizializzatore ritorna l'id dell' utente corrente del programma.
     * <br>
     *
     * @return l'inizializzatore creato
     */
    public static Init idUtenteCorrente() {
        return new InitIdUtenteCorrente();
    }


}// fine della classe
