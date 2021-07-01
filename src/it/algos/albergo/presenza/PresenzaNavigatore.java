/**
 * Title:     NavPresenze
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-lug-2008
 */
package it.algos.albergo.presenza;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.ricerca.RicercaBase;

/**
 * Navigatore delle Presenze
 * </p>
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-lug-2008 ore 9.51.20
 */
final class PresenzaNavigatore extends NavigatoreLS {

    /**
     * Costruttore base senza parametri.
     * <p/>
     * Utilizzato solo per debug
     */
    public PresenzaNavigatore() {
        super();
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public PresenzaNavigatore(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setUsaPannelloUnico(true);
            this.addSchedaCorrente(new PresenzaScheda(this.getModulo()));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.<br>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se non
     * riescono a portare a termine la propria inizializzazione specifica.<br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice

            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    @Override
    public void apreRicerca() {

        RicercaBase ricerca = new PresenzaRicerca();
        this.setRicerca(ricerca);

        super.apreRicerca();
    }


    /**
     * Visualizza nella statusBar i totali delle presenze in funzione del filtro selezionato.
     * </p>
     */
    private void regolaTotaliPresenze() {
        /* variabili e costanti locali di lavoro */
        String testo;
        int adulti;
        int bambini;

        try { // prova ad eseguire il codice
            adulti = getAdulti();
            bambini = getBambini();

            testo = "Persone: ";
            if (bambini>0) {
                testo+=(adulti+bambini);
                testo+=" ("+adulti+"+"+bambini+")";
            } else {
                testo +=adulti;
            }// fine del blocco if-else


            this.getLista().setCustom(testo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il totale di adulti presenti oggi.
     * <p/>
     *
     * @return numero di adulti presenti
     */
    private int getAdulti() {
        /* variabili e costanti locali di lavoro */
        int adulti = 0;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

            filtro = this.getLista().getFiltro();
            filtro.add(FiltroFactory.creaFalso(Presenza.Cam.bambino));
            modPresenza = PresenzaModulo.get();
            adulti = modPresenza.query().contaRecords(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return adulti;
    }


    /**
     * Ritorna il totale di bambini presenti oggi.
     * <p/>
     *
     * @return numero di bambini presenti
     */
    private int getBambini() {
        /* variabili e costanti locali di lavoro */
        int bambini = 0;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

            filtro = this.getLista().getFiltro();
            filtro.add(FiltroFactory.creaVero(Presenza.Cam.bambino));
            modPresenza = PresenzaModulo.get();
            bambini = modPresenza.query().contaRecords(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bambini;
    }



    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di conseguenza <br>
     */
    @Override public void sincronizza() {

        super.sincronizza();

        /* regola i totali presenze visualizzati nella status bar */
        this.regolaTotaliPresenze();
    }



}
