/**
 * Title:     CVDBottone2stati
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      12-giu-2006
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;

import javax.swing.*;

/**
 * Decoratore grafico del campo video.
 * <p/>
 * Aggiunge al campo un bottone a 2 stati
 * Il bottone e' trasparente
 * Per ogni stato visualizza una diversa icona
 * Quando viene premuto cambia lo stato
 * e viene lanciato un evento
 * generico per il campo.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  12-giu-2006 ore 9.58
 */
public class CVDBottone2stati extends CVDBottone {


    /**
     * flag che mantiene lo stato corrente
     * quando visualizza la prima icona è false
     * quando visualizza la seconda icona è true
     */
    private boolean stato;

    /**
     * icona per il primo stato
     */
    private Icon iconaStato0 = null;

    /**
     * icona per il secondo stato
     */
    private Icon iconaStato1 = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo da decorare
     * @param icona0 icona del bottone per il primo stato
     * @param icona1 icona del bottone per il secondo stato
     */
    public CVDBottone2stati(CampoVideo campoVideo, Icon icona0, Icon icona1) {
        /* rimanda al costruttore della superclasse */
        super(campoVideo);

        this.setIconaStato0(icona0);
        this.setIconaStato1(icona1);

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

        try { // prova ad eseguire il codice

            /* regola lo stato iniziale */
            this.setStato(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    @Override public void inizializza() {
        super.inizializza();
        this.setStato(this.getStato());
        this.esegui();
    }


    /**
     * Inserisce nel bottona l'icona corrispondente allo stato corrente
     */
    private void usaIcona() {
        /* variabili e costanti locali di lavoro */
        Icon icona;

        try { // prova ad eseguire il codice
            if (this.getStato()) {
                icona = this.getIconaStato1();
            } else {
                icona = this.getIconaStato0();
            }// fine del blocco if-else

            this.getBottone().setIcon(icona);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo invocato quando si preme il bottone.
     * <p/>
     */
    protected void esegui() {
    }


    public boolean getStato() {
        return stato;
    }


    public void setStato(boolean stato) {
        this.stato = stato;
        this.usaIcona();
    }


    private Icon getIconaStato0() {
        return iconaStato0;
    }


    protected void setIconaStato0(Icon iconaStato0) {
        this.iconaStato0 = iconaStato0;
    }


    private Icon getIconaStato1() {
        return iconaStato1;
    }


    protected void setIconaStato1(Icon iconaStato1) {
        this.iconaStato1 = iconaStato1;
    }


    /**
     * Metodo invocato quando il bottone viene premuto.
     * <p/>
     * Sovrascrive il metodo della superclasse
     */
    protected void bottonePremuto() {
        try {    // prova ad eseguire il codice

            /* inverte lo stato */
            setStato(!getStato());

            this.esegui();

            /* rimanda alla superclasse */
            super.bottonePremuto();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


}// fine della classe
