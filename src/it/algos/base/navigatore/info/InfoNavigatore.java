/**
 * Title:     InfoNavigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      2-apr-2004
 */
package it.algos.base.navigatore.info;

import it.algos.base.errore.Errore;
import it.algos.base.navigatore.Navigatore;

/**
 * Wrapper per le informazioni sullo stato di un Navigatore.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-apr-2004 ore 17.48.15
 */
public final class InfoNavigatore extends Object implements Info {

    /**
     * Navigatore di riferimento
     */
    private Navigatore navigatore = null;

    /**
     * flag per indicare quando si puo' chiudere la finestra
     */
    private boolean isPossoChiudereFinestra = false;


    /**
     * Costruttore completo. <br>
     *
     * @param navigatore la lista di riferimento
     */
    public InfoNavigatore(Navigatore navigatore) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            /* regola le variabili di istanza coi parametri */
            this.setNavigatore(navigatore);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     * Disabilita tutte le azioni <br>
     * Determina le Azioni possibili <br>
     * Abilita solo le Azioni congruenti con lo stato attuale <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


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
    public boolean inizializza() {
        /* valore di ritorno */
        return true;
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Regola le variabili del pacchetto in funzione dello stato corrente <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */

        /* resetta tutti i flag */
        isPossoChiudereFinestra = false;

        /* regola i flag in base allo stato */
        try { // prova ad eseguire il codice

            /* controlla dal Navigatore se si puo' chiudere la finestra lista */
            this.setPossoChiudereFinestra(getNavigatore().isNavigatoreMain() == false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo lancia


    private Navigatore getNavigatore() {
        return navigatore;
    }


    private void setNavigatore(Navigatore navigatore) {
        this.navigatore = navigatore;
    }


    public boolean isPossoChiudereFinestra() {
        return isPossoChiudereFinestra;
    }


    public void setPossoChiudereFinestra(boolean possoChiudereFinestra) {
        isPossoChiudereFinestra = possoChiudereFinestra;
    }

}// fine della classe
