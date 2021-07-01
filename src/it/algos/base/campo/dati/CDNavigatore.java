/**
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 28 geb 2005 alle 19.22
 */

package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.navigatore.NavStatoAz;
import it.algos.base.evento.navigatore.NavStatoEve;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.scheda.Scheda;

/**
 * Campo dati per un campo Navigatore.
 * <p/>
 */
public class CDNavigatore extends CDLogico {


    private static final TipoMemoria TIPO_MEMORIA = null;

    private static final TipoVideo TIPO_VIDEO = null;

    /**
     * Flag - indica se il Navigatore e' stato modificato.
     * <p/>
     * Disattivato ad ogni avvio.<br>
     * Attivato non appena si aggiunge, modifica o elimina un record.<br>
     */
    private boolean modificato = false;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDNavigatore() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDNavigatore(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente, TIPO_MEMORIA, TIPO_VIDEO);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    @Override public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        super.inizializza();

        try { // prova ad eseguire il codice
            nav = this.getCampoParente().getNavigatore();

            /* aggiunge i listeners per ascoltare le modifiche al navigatore */
            nav.addListener(new AzioneNavModificato());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa)
     * ogni volta che questo oggetto deve <i>ripartire</i>,
     * per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    @Override public void avvia() {
        super.avvia();
        this.setModificato(false);
    }


    /**
     * Determina se il campo e' modificato.
     * <p/>
     * Chiede al navigatore <br>
     *
     * @return true se il campo e' modificato
     */
    public boolean isModificato() {
        /* variabili e costanti locali di lavoro */
        boolean mod = false;
        boolean continua = true;
        Navigatore nav;
        Scheda scheda;

        try { // prova ad eseguire il codice

            /* se il flag modificato è stato acceso, ritorna true */
            if (this.modificato) {
                mod = true;
                continua = false;
            }// fine del blocco if

            /* se il flag modificato è spento, chiede alla scheda */
            if (continua) {
                nav = this.getCampoParente().getNavigatore();
                if (nav != null) {
                    scheda = nav.getScheda();
                    if (scheda != null) {
                        mod = scheda.isModificata();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mod;
    }


    /**
     * Controlla se il contenuto del campo e' valido.
     * <p/>
     * Un Campo Navigatore è valido se la sua scheda corrente è valida
     * (o se non ha una scheda aperta)
     *
     * @return true se valido, false se non valido.
     */
    public boolean isValido() {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        Navigatore nav;
        Scheda scheda;

        try { // prova ad eseguire il codice
            nav = this.getNavigatore();
            scheda = nav.getScheda();
            if (scheda != null) {
                if (scheda.getCodice() != 0) {
                    valido = scheda.isValida();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Ritorna il Navigatore gestito dal campo.
     * <p/>
     *
     * @return il Navigatore
     */
    protected Navigatore getNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoParente();
            nav = campo.getNavigatore();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Allinea le variabili del Campo: da Memoria verso Video.
     * </p>
     * Il campo Navigatore non gestisce valori
     */
    public void memoriaVideo() {
    }


    private void setModificato(boolean modificato) {
        this.modificato = modificato;
    }


    /**
     * Azione di cambio stato GUI del campo
     */
    private class AzioneNavModificato extends NavStatoAz {

        /**
         * navStatoAz, da NavStatoLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void navStatoAz(NavStatoEve unEvento) {
            setModificato(true);
            getCampoParente().fire(CampoBase.Evento.memoriaModificata);
        }
    } // fine della classe interna

}// fine della classe