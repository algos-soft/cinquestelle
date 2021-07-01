/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 4 luglio 2003 alle 11.36
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.Pannello;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;

/**
 * Decoratore etichetta della classe CampoVideo.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Disegna un'etichetta nel pannello campo del CampoVideo,
 * prima del pannello componenti </li>
 * <li> L'etichetta può essere posizionata sopra o a sinistra </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  4 luglio 2003 ore 11.36
 */
public final class CVDEtichetta extends CVDLabel {

    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideoDecorato oggetto da decorare
     */
    public CVDEtichetta(CampoVideo campoVideoDecorato) {
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
        /** di default l'etichetta viene posizionata
         *  nella parte alta del pannelloCampo */
        this.setPos(Pos.SOPRA);
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice
            /* Regola il testo di default della etichetta */
            this.regolaTesto();

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();
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
    }


    /**
     * Regola il testo di default della etichetta.
     * <p/>
     * Se alla creazione del campo nel Modello, non viene richiesto nessun testo
     * utilizza il nome interno del campo <br>
     */
    private void regolaTesto() {
        /* variabili e costanti locali di lavoro */
        String testo;

        try {    // prova ad eseguire il codice
            /* recupera il testo dal campo scheda */
            testo = this.getTestoLabel();

            /* regola il testo di una eventuale etichetta
             * se non e' stato regolato nessun testo dal cliente di questa
             * classe, utilizza il nome interno del campo parente */
            if (Lib.Testo.isVuota(testo)) {
                testo = this.unCampoParente.getNomeInterno();
            }// fine del blocco if

            /* registra il valore nel campo scheda */
            this.setTestoLabel(testo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
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
     *      <p/>
     *      elimina i componenti GUI del pannello
     */
    protected void regolaFontColori() {
        try {    // prova ad eseguire il codice
            /* regola colore e font dell'etichetta */
            TestoAlgos.setEtichetta(this.getLabel());

            /* regola il colore di fondo della label */
            super.regolaFontColori();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Ritorna il testo dell'etichetta
     * <p/>
     *
     * @return il testo dell'etichetta
     */
    public String getTestoEtichetta() {
        return this.getTestoLabel();
    }


    /**
     * Ritorna la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @return la larghezza in pixel dell'etichetta
     */
    public int getLarghezzaEtichetta() {
        /* variabili e costanti locali di lavoro */
        int larghezza = 0;
        JLabel label;

        try { // prova ad eseguire il codice
            label = super.getLabel();

            if (label != null) {
                larghezza = label.getPreferredSize().width;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return larghezza;
    }


    /**
     * Regola la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @param larghezza in pixel dell'etichetta
     */
    public void setLarghezzaEtichetta(int larghezza) {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try { // prova ad eseguire il codice
            label = super.getLabel();

            if (label != null) {
                Lib.Comp.setPreferredWidth(label, larghezza);
                Lib.Comp.bloccaDim(label);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola (sinistra o destra) l'allineamento del testo dell'etichetta
     * <p/>
     *
     * @param bandiera tipo di allineamento
     */
    public void setAllineamentoEtichetta(Pannello.Bandiera bandiera) {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try { // prova ad eseguire il codice
            label = super.getLabel();

            if (label != null) {
                label.setHorizontalAlignment(bandiera.getCostante());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se il campo ha l'etichetta e se questa è a sinistra.
     * <p/>
     *
     * @return vero se esiste l'etichetta ed è a sinistra
     */
    @Override
    public boolean isEtichettaSinistra() {
        /* variabili e costanti locali di lavoro */
        boolean sinistra = false;
        Pos pos;

        try { // prova ad eseguire il codice
            pos = this.getPos();

            sinistra = (pos == Pos.SINISTRA);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return sinistra;
    }


    /**
     * Regola il testo dell'etichetta
     * <p/>
     *
     * @param testo per l'etichetta
     */
    public void setTestoEtichetta(String testo) {
        this.setTestoLabel(testo);
    }


}// fine della classe