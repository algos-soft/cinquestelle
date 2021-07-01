/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-nov-2006
 */
package it.algos.gestione.fattura.riga;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.form.Form;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.fattura.FattBase;

/**
 * Navigatore specifico per le righe di fattura all'interno della fattura.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-feb-2005 ore 12.28.15
 */
public final class RigaFatturaNavFattura extends NavigatoreLS {


    /**
     * Costruttore base senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public RigaFatturaNavFattura() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo di riferimento
     */
    public RigaFatturaNavFattura(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        this.setUsaPannelloUnico(true);
        this.setRigheLista(10);
        this.setUsaFrecceSpostaOrdineLista(true);
        this.setUsaStampaLista(false);
        this.setUsaRicerca(false);
        this.setUsaProietta(false);
        this.setUsaSelezione(false);

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
    @Override public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        Lista lista;

        try { // prova ad eseguire il codice
            vista = this.getModulo().getModello().getVista(RigaFattura.Vis.fattura.toString());
            lista = this.getLista();
            lista.setVista(vista);
            lista.setOrdinabile(false);
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizializza


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     */
    @Override public void avvia() {
//        this.getPortaleLista().getLista().getTavola().regolaLarghezzaColonne();
//        this.getPortaleNavigatore().setPreferredSize(new Dimension(200,100));
        super.avvia();
    }// fine del metodo lancia


    protected boolean nuovoRecordAnte(SetValori set) {
        /* variabili e costanti locali di lavoro */
        String nomeCampo;
        Object valore;

        try { // prova ad eseguire il codice

            /* codice IVA dalla scheda fattura */
            nomeCampo = FattBase.Cam.codIva.get();
            valore = this.getValoreCampoScheda(nomeCampo);
            if (valore != null) {
                set.setValore(RigaFattura.Cam.codIva.get(), valore);
            }// fine del blocco if

            /* percentuale di sconto dalla scheda fattura */
            nomeCampo = FattBase.Cam.percSconto.get();
            valore = this.getValoreCampoScheda(nomeCampo);
            if (valore != null) {
                set.setValore(RigaFattura.Cam.percSconto.get(), valore);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    }


    /**
     * Recupera il valore corrente di un campo della scheda
     * nella quale si trova questo navigatore.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo da recuperare
     *
     * @return il valore corrente del campo
     */
    private Object getValoreCampoScheda(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Campo campoPilota;
        boolean continua;
        Form schedaFattura = null;

        try {    // prova ad eseguire il codice

            /* recupera il campo che pilota il navigatore */
            campoPilota = this.getCampoPilota();
            continua = campoPilota != null;

            /* recupera la scheda nella quale si trova il campo */
            if (continua) {
                schedaFattura = campoPilota.getForm();
                continua = schedaFattura != null;
            }// fine del blocco if

            /* recupera il valore del campo richiesto */
            if (continua) {
                valore = schedaFattura.getValore(nomeCampo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


}// fine della classe
