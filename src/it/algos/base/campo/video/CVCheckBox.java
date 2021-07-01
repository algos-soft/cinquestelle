/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 6 luglio 2003 alle 14.18
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import java.awt.Dimension;

/**
 * Componente video di tipo checkbox singolo.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il componente GUI specifico di questa classe </li>
 * <li> Regola l'aspetto grafico del componente specifico </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  6 luglio 2003 ore 14.18
 */
public final class CVCheckBox extends CVBox {

    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVCheckBox(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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
        this.creaComponentiInterni();
    }

    @Override
    public void inizializza() {
        super.inizializza();
    }

    @Override
    public void avvia() {
        super.avvia();
    }

    /**
     * Crea i componenti interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia o inizializza, a seconda
     * delle esigenze del tipo specifico di campo <br>
     * Sovrascrive il metodo della superclasse <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    public void creaComponentiInterni() {
        /* variabili e costanti locali di lavoro */
        JCheckBox comp;

        try {    // prova ad eseguire il codice

            /* crea il componente principale di tipo checkbox */
            comp = new JCheckBox();

            /* regola le caratteristiche del testo del componente */
            TestoAlgos.setCheckBox(comp);

            /* registra il riferimento al componente singolo */
            this.setComponente(comp);

            /* rimanda al metodo della superclasse */
            super.creaComponentiInterni();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    public boolean isSelezionato() {
        /* variabili e costanti locali di lavoro */
        boolean selezionato = false;
        JCheckBox comp;

        try { // prova ad eseguire il codice
            comp = (JCheckBox)this.getComponente();
            if (comp != null) {
                selezionato = comp.isSelected();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return selezionato;
    }


    /**
     * Regola la larghezza dei singoli componenti
     * interni al PannelloComponenti
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * E' responsabilita' di questo metodo assegnare la larghezza
     * preferita a tutti i componenti interni al pannello componenti.<br>
     */
    protected void regolaLarghezzaComponenti() {
            //per il checkbox non fa nulla, la larghezza è data dal testo del componente
    }


}// fine della classe