/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      23-feb-2005
 */
package it.algos.base.campo.video;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.pannello.Pannello;
import it.algos.base.portale.Portale;
import it.algos.base.progetto.Progetto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Componente video generico di tipo controllo booleano.
 * </p>
 * Questa classe astratta: <ul>
 * <li> Mantiene alcuni attributi e implementa i metodi
 * comuni delle sottoclassi concrete </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 23-feb-2005 ore 12.08.36
 */
public abstract class CVBox extends CVBase {


    /**
     * testo del componente (cosa diversa dall'etichetta)
     */
    private String testoComponente = "";


    /**
     * Costruttore completo con parametri.
     * (indispensabile perch� chiamato dalla sottoclasse concreta) <br>
     * (senza modificatore, cos� non pu� essere invocato fuori dal package) <br>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    CVBox(Campo unCampoParente) {
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
        /* regola di default il testo del componente (cosa diversa dall'etichetta) */
        this.regolaTestoComponente();
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
        JToggleButton comp;
        Pannello pc;
        String testo;


        try {    // prova ad eseguire il codice

            /* recupera il testo (cosa diversa dall'etichetta) */
            testo = this.getTestoComponente();

            /* recupera il componente che e' di tipo JToggleButton */
            comp = this.getComponenteToggle();

            /* assegna il testo al componente */
            comp.setText(testo);

            /* il componente e' trasparente */
            comp.setContentAreaFilled(false);

            /* regola il margine attorno al componente */
            comp.setMargin(new Insets(0, 0, 0, 0));

            /* aggiunge il componente al pannelloComponenti usando BorderLayout */
            pc = this.getPannelloBaseComponenti();
            pc.add(comp);

            /* invoca il metodo sovrascritto nella superclasse */
            super.creaComponentiInterni();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Aggiunge i <code>Listener</code>.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge ai componenti video di questo campo gli eventuali
     * ascoltatori delle azioni (eventi) <br>
     * Metodo invocato da SchedaBase.inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param portale il portale di riferimento del campo
     *
     * @see it.algos.base.scheda.SchedaBase#inizializza()
     */
    public void aggiungeListener(Portale portale) {
        /* variabili e costanti locali di lavoro */
        ItemListener azione;

        super.aggiungeListener(portale);

        try { // prova ad eseguire il codice

            if (portale != null) {
                azione = portale.getAzItem(Azione.ITEM_MODIFICATO);
            } else {
                azione = Progetto.getAzItem(Azione.ITEM_MODIFICATO);
            }// fine del blocco if-else

            this.getComponenteToggle().addItemListener(azione);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce l'elenco dei componenti video del campo.
     * <p/>
     * In questo caso e' un solo componente.<br>
     *
     * @return l'elenco dei componenti video
     */
    protected ArrayList<JComponent> getComponentiVideo() {
        /* variabili e costanti locali di lavoro */
        ArrayList<JComponent> componenti = null;
        JToggleButton comp;

        try { // prova ad eseguire il codice
            componenti = new ArrayList<JComponent>();
            comp = this.getComponenteToggle();
            componenti.add(comp);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return componenti;
    }


    /**
     * Regola di default il testo del componente.
     * <p/>
     * (cosa diversa dall'etichetta) <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     */
    private void regolaTestoComponente() {
        /* variabili e costanti locali di lavoro */
        String nomeCampo;

        try {    // prova ad eseguire il codice
            /* recupera il valore dal 'contenitore' di questo oggetto */
            nomeCampo = unCampoParente.getNomeInterno();

            /* regola il testo del componente */
            this.setTestoComponente(nomeCampo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Ritorna il componente unico di tipo JToggleButton.
     * <p/>
     *
     * @return il componente unico di tipo JToggleButton
     */
    public JToggleButton getComponenteToggle() {
        /* variabili e costanti locali di lavoro */
        JToggleButton toggle = null;
        JComponent comp;

        try {    // prova ad eseguire il codice
            comp = this.getComponente();
            if (comp != null) {
                if (comp instanceof JToggleButton) {
                    toggle = (JToggleButton)comp;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return toggle;
    }


    /**
     * Aggiorna la GUI col valore video.
     * <p/>
     * Metodo invocato dal ciclo avvia di SchedaBase <br>
     * Regola il componente GUI del campoVideo con il valore <br>
     *
     * @param unValore valore video proveniente dal CampoDati
     */
    public void aggiornaGUI(Object unValore) {
        /* variabili e costanti locali di lavoro */
        JToggleButton componente;
        boolean bool;

        try {    // prova ad eseguire il codice

            /* recupera il valore booleano */
            bool = Libreria.getBool(unValore);

            /* recupera il componente */
            componente = this.getComponenteToggle();

            /* regolazione effettiva del componente */
            componente.setSelected(bool);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     *
     * @return valore video per il CampoDati
     */
    public Object recuperaGUI() {
        /* variabili e costanti locali di lavoro */
        Boolean selezionato = null;
        JToggleButton componente;

        try {    // prova ad eseguire il codice

            /* recupera il componente */
            componente = this.getComponenteToggle();

            /* recupera lo stato del componente */
            selezionato = componente.isSelected();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return selezionato;
    }

//    /**
//     * Aggiunge i <code>Listener</code>.
//     * <p/>
//     * Aggiunge ai componenti video di questo campo gli eventuali
//     * ascoltatori delle azioni (eventi) <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     *
//     * @param azione azione da aggiungere
//     */
//    public void aggiungeListener(BaseListener azione) {
//        /* variabili e costanti locali di lavoro */
//        JToggleButton componente;
//
//        try { // prova ad eseguire il codice
//
//            componente = this.getComponenteToggle();
//            componente.addItemListener((ItemListener)azione);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }

//    /**
//     * Aggiunge i <code>Listener</code>.
//     * <p/>
//     * Metodo invocato dal ciclo inizializza <br>
//     * Aggiunge ai componenti video di questo campo gli eventuali
//     * ascoltatori delle azioni (eventi) <br>
//     * Metodo invocato da SchedaBase.inizializza <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     *
//     * @param portale il portale di riferimento del campo
//     *
//     * @see it.algos.base.scheda.SchedaBase#inizializza()
//     */
//    public void aggiungeListener(Portale portale) {
//        /* variabili e costanti locali di lavoro */
//        JToggleButton componente;
//        ItemListener unAzione;
//
//        try { // prova ad eseguire il codice
//
//            componente = this.getComponenteToggle();
//            unAzione = portale.getAzItem(Azione.ITEM_MODIFICATO);
//            componente.addItemListener(unAzione);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Regola il testo descrittivo del componente.
     * <p/>
     * (checkbox o radio bottone) <br>
     * (cosa diversa dall'etichetta) <br>
     *
     * @param testoComponente testo descrittivo
     */
    public void setTestoComponente(String testoComponente) {

        /* variabili e costanti locali di lavoro */
        JToggleButton toggle;

        try { // prova ad eseguire il codice

            this.testoComponente = testoComponente;

            /* assegna immediatamente il testo al componente grafico */
            toggle = this.getComponenteToggle();
            if (toggle != null) {

                /* assegna il nuovo testo */
                toggle.setText(this.getTestoComponente());

                /* fissa la nuova dimensione del pannello campo */
                this.pack();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il testo descrittivo del componente.
     * <p/>
     * (checkbox o radio bottone) <br>
     * (cosa diversa dall'etichetta) <br>
     *
     * @return il testo descrittivo del componente
     */
    public String getTestoComponente() {
        return testoComponente;
    } /* fine del metodo getter */


    /**
     * Regola la larghezza dei singoli componenti
     * interni al PannelloComponenti
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * E' responsabilita' di questo metodo assegnare la larghezza
     * preferita a tutti i componenti interni al pannello componenti.<br>
     */
    protected void regolaLarghezzaComponenti() {
    }

}// fine della classe
