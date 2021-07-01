/**
 * Title:     Info
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      29-set-2004
 */
package it.algos.base.navigatore.info;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA INTERFACCIA .
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di interfacce presenti
 * nel package.</li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 29-set-2004 ore 17.19.17
 */
public interface Info {

    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return vero se viene inizializzato adesso;
     *         falso se era gi&agrave; stato inizializzato
     */
    public boolean inizializza();


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Regola le variabili del pacchetto in funzione dello stato corrente <br>
     */
    public abstract void avvia();


}// fine della interfaccia
