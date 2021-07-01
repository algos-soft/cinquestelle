/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 15 agosto 2003 alle 21.04
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;

/**
 * Decoratore calcolato della classe CampoVideo.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Disegna una legenda (descrizione) nel pannello campo del CampoVideo,
 * dopo il pannello componenti </li>
 * <li> Il calcolato può essere posizionato sotto o a destra </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 agosto 2003 ore 21.04
 */
public class CVDCalcolato extends CVDecoratoreBase {


    /**
     * riferimento al campo da osservare
     */
    private String campoOsservato = "";


    /**
     * riferimento all'azione da associare al campo
     */
    private AzioneCalcolata azione = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideoDecorato oggetto da decorare
     */
    public CVDCalcolato(CampoVideo campoVideoDecorato) {
        /* rimanda al costruttore della superclasse */
        super(campoVideoDecorato);

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
        /** di default il calcolato viene posizionato
         *  nella parte bassa del pannelloCampo */
        this.setPos(Pos.SOTTO);

    } /* fine del metodo inizia */


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            /* crea l'azione da associare al campo osservato */
            this.setAzione(new AzioneCalcolata());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa)
     * ogni volta che questo oggetto deve <i>ripartire</i>,
     * per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
        /* invoca il metodo sovrascritto della superclasse */
        super.avvia();

        /* regola l'azione */
        this.regolaAzione();
    }


    /**
     * Crea gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche più di uno <br>
     * Gli elementi vengono aggiunti al pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #regolaElementi()
     */
    protected void creaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        JLabel label;
        Pos pos;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaComponentiGUI();

            /* recupera l'oggetto label creato nella superclasse */
            label = this.getLabel();

            /* recupera la posizione */
            pos = this.getPos();

            /* aggiunge l'etichetta al pannelloCampo */
            this.addComponente(label, pos);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola le caratteristiche grafiche degli elementi GUI.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Regola font, colori, bordi e sfondi di tutti gli elementi GUI
     * del pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.campo.video.CVBase#inizializza()
     */
    public void regolaFontColori() {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try {    // prova ad eseguire il codice
            /* recupera la label */
            label = this.getLabel();

            /* regola colore e font della legenda */
            if (label != null) {
                TestoAlgos.setFieldExtra(this.getLabel());
            }// fine del blocco if

            /* test */
            if (this.isDebug()) {
                if (this.getLabel() != null) {
                    this.getLabel().setOpaque(true);
                }// fine del blocco if
            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Regola l'azione.
     * <p/>
     * Metodo invocato dal ciclo avvia <br>
     * Associa l'azione al componente del campo osservato <br>
     */
    public void regolaAzione() {
        /* variabili e costanti locali di lavoro */
        String nome;
        AzioneCalcolata azione;
        Campo campoOsservato;

        try {    // prova ad eseguire il codice
            /* recupera il nome */
            nome = this.getCampoOsservato();

            /* recupera il campo */
            campoOsservato = this.getCampoForm(nome);

            /* recupera l'azione interna */
            azione = this.getAzione();

            /* regola il listener */
            if (campoOsservato != null) {
                campoOsservato.addListener(azione);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Esegue l'azione generata dall'evento.
     * <p/>
     * Metodo invocato dalla classe interna <br>
     *
     * @param campo da regolare
     */
    protected void esegui(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        CampoDati campoDati = null;
        String testo = "";

        try { // prova ad eseguire il codice
            continua = campo != null;

            if (continua) {
                campoDati = campo.getCampoDati();
                continua = campoDati != null;
            }// fine del blocco if

            if (continua) {
                testo = (String)campoDati.getMemoria();
            }// fine del blocco if

            this.regolaTesto(testo);

            this.pack();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected void regolaTesto(String testo) {
        this.getLabel().setText(testo);
    }


    private String getCampoOsservato() {
        return campoOsservato;
    }


    public void setCampoOsservato(String campoOsservato) {
        this.campoOsservato = campoOsservato;
    }


    private AzioneCalcolata getAzione() {
        return azione;
    }


    private void setAzione(AzioneCalcolata azione) {
        this.azione = azione;
    }


    /**
     * Inner class per gestire l'azione.
     */
    private class AzioneCalcolata extends CampoMemoriaAz {

        /**
         * campoAz, da CampoLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(unEvento.getCampo());
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


}// fine della classe