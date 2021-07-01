/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-feb-2005
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CDElencoInterno;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.elemento.ENuovo;
import it.algos.base.campo.elemento.ESeparatore;
import it.algos.base.campo.elemento.EVuoto;
import it.algos.base.campo.elemento.Elemento;
import it.algos.base.campo.video.decorator.CVDecoratore;
import it.algos.base.combo.Combo;
import it.algos.base.combo.ComboData;
import it.algos.base.combo.ComboFactory;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;

/**
 * Campo video di tipo Combo per gestione di un
 * numero limitato di valori interni.
 * </p>
 * Utilizza sempre un componente video di tipo JComboBox (ComboLista)
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-feb-2005 ore 15.47.44
 */
public final class CVComboLista extends CVCombo {

    /**
     * valore di selezione nulla del combo box
     */
    private static final int SELEZIONE_NULLA = -1;


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVComboLista(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Crea i componenti interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia o inizializza o avvia, a seconda
     * delle esigenze del tipo specifico di campo <br>
     * Metodo sovrascritto dalle sottoclassi <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - aggiungere i listener ai componenti GUI
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    public void creaComponentiInterni() {
        /* variabili e costanti locali di lavoro */
        Combo combo;


        try { // prova ad eseguire il codice

            /* crea e registra l'oggetto GUI principale */
            combo = ComboFactory.creaLista(this);
            super.assegnaCombo(combo);

            /* invoca il metodo sovrascritto nella superclasse */
            super.creaComponentiInterni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     *
     * @return valore video per il CampoDati
     */
    public Object recuperaGUI() {
        /* variabili e costanti locali di lavoro */
        Integer posizione = null;
        int valore;
        Object valVuoto;
        Campo campoParente;

        try {    // prova ad eseguire il codice

            /* recupera la posizione selezionata nel combo */
            valore = this.getCombo().getSelectedIndex();

            /* controlla la selezione nulla */
            if (valore == SELEZIONE_NULLA) {
                campoParente = this.getCampoParente();
                valVuoto = campoParente.getCampoDati().getValoreVideoVuoto();
                valore = (Integer)valVuoto;
            } else {
                /* la pop-lista parte da zero, mentre posizione inizia da 1 */
                valore++;
            } /* fine del blocco if/else */

            /* crea l'oggetto da restituire */
            posizione = valore;

            /* mostra la legenda del valore selezionato a destra (opzionale) */
            this.mostraLegenda(posizione);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return posizione;
    }


    /**
     * Se esiste, mostra una legenda a destra del valore selezionato del popup.
     * <p/>
     */
    private void mostraLegenda(int pos) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String legenda = "";
        JLabel label;
        CampoDati unCampoDati;

        try { // prova ad eseguire il codice
            unCampoDati = this.getCampoDati();
            continua = (unCampoDati instanceof CDElencoInterno);

            if (continua) {
                legenda = ((CDElencoInterno)unCampoDati).getLegenda(--pos);
                continua = (Lib.Testo.isValida(legenda));
            }// fine del blocco if

            if (continua) {
                label = new JLabel(legenda);
                TestoAlgos.setFieldExtra(label);
                this.addComponente(label, CVDecoratore.Pos.DESTRA);
                this.getPannelloBaseCampo().updateUI();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Metodo invocato quando si modifica la selezione del JComboBox <br>
     * Controlla se l'elemento selezionato e' un elemento 'normale';
     * in caso affermativo rilancia l'evento in modo che possa essere
     * intercettato da chi di dovere come normale modifica del campo <br>
     * Se l'elemento selezionato e' 'speciale', invoca il metodo delegato
     * a trattare la specifica funzionalita' <br>
     */
    protected void comboModificato() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Object unaRigaSelezionata;
        ComboData comboData;

        try { // prova ad eseguire il codice
            /* Controlla se e' una riga speciale:
             * se si rimanda al metodo interno delegato;
             * se no rinvia al metodo generale di campo modificato */
            if (this.rigaSelezionataSpeciale()) {
                /* recupera l'oggetto della riga selezionata */
                unaRigaSelezionata = this.getCombo().getSelectedItem();

                if (unaRigaSelezionata instanceof ComboData) {
                    comboData = (ComboData)unaRigaSelezionata;
                    /* Controlla se e' una riga vuota */
                    if (comboData.getValore() instanceof EVuoto) {
                        continua = this.esegueSelezionaVuoto();
                    } /* fine del blocco if */

                    /* Controlla se e' una riga separatore */
                    if (comboData.getValore() instanceof ESeparatore) {
                        continua = this.esegueSelezionaSeparatore();
                    } /* fine del blocco if */

                    /* Controlla se e' una riga nuovo */
                    if (comboData.getValore() instanceof ENuovo) {
                        continua = this.esegueSelezionaNuovo();
                    } /* fine del blocco if */
                } /* fine del blocco if */
            } /* fine del blocco if/else */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * ...
     */
    private boolean rigaSelezionataSpeciale() {
        /** variabili e costanti locali di lavoro */
        boolean risposta = false;
        Object unaRigaSelezionata;
        ComboData comboData;

        try {    // prova ad eseguire il codice

            /* recupera l'oggetto della riga selezionata */
            unaRigaSelezionata = this.getCombo().getSelectedItem();

            /* Controlla se e' una riga speciale */
            if (unaRigaSelezionata instanceof ComboData) {
                comboData = (ComboData)unaRigaSelezionata;
                if (comboData.getValore() instanceof Elemento) {
                    risposta = true;
                } /* fine del blocco if */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return risposta;
    } /* fine del metodo */


    /**
     * ...
     */
    private boolean esegueSelezionaVuoto() {
        /* valore di ritorno */
        return true;
    }


    /**
     * ...
     */
    private boolean esegueSelezionaSeparatore() {
        /* valore di ritorno */
        return true;
    }


    /**
     * ...
     */
    private boolean esegueSelezionaNuovo() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Modulo modLinkato;
        int posPrecedente;
        int codice;

        try { // prova ad eseguire il codice

            posPrecedente = (Integer)this.getCampoDati().getValore();
            modLinkato = this.getCampoParente().getCampoDB().getModuloLinkato();

            codice = modLinkato.getModuloBase().nuovoRecord();

            if (codice > 0) {
                this.getCampoDati().avvia();
                this.getCampoParente().setValore(codice);
            } else {
                this.getCampoParente().setValore(posPrecedente);
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


}// fine della classe
