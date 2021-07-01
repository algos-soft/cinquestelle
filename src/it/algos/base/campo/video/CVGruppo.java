/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      23-feb-2005
 */
package it.algos.base.campo.video;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutFlusso;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloCampo;
import it.algos.base.pannello.PannelloComponenti;
import it.algos.base.portale.Portale;
import it.algos.base.progetto.Progetto;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Componente video generico di tipo gruppo di bottoni toggle.
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
public abstract class CVGruppo extends CVBase {

    /**
     * oggetto GUI principale
     * (una serie di radio bottoni o di check box)
     */
    private ArrayList<JToggleButton> componentiGruppo = null;

    /**
     * Orientamento del flusso di componenti
     * Layout.ORIENTAMENTO_ORIZZONTALE o Layout.ORIENTAMENTO_VERTICALE
     */
    private int orientamento = 0;

    /**
     * oggetto di funzionalita' logica per la mutua esclusione dei bottoni
     */
    private ButtonGroup gruppoBottoni = null;

    /**
     * Gap verticale tra i componenti.
     */
    private int gapVerticale;

    /**
     * Gap orizzontale tra i componenti.
     */
    private int gapOrizzontale;


    /**
     * Costruttore completo con parametri.
     * (indispensabile perch� chiamato dalla sottoclasse concreta) <br>
     * (senza modificatore, cos� non pu� essere invocato fuori dal package) <br>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    CVGruppo(Campo unCampoParente) {
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

        /* crea e registra un nuovo gruppo logico di bottoni
         * per la eventuale mutua esclusione */
        this.setGruppoBottoni(new ButtonGroup());

        /* regola l'orientamento di default del layout del pannello componenti */
        this.setOrientamentoComponenti(Layout.ORIENTAMENTO_VERTICALE);

        /* gap di default */
        this.setGapVerticale(0);
        this.setGapOrizzontale(4);

        /* prepara i componenti */
        this.resetComponenti();

    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice

            /* svuota il pannello componenti */
            this.getPannelloComponenti().removeAll();

            /* Crea i componenti */
            this.creaComponentiInterni();

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Resetta i vari componenti del campo.
     * <p/>
     * Ricrea la lista dei componenti radio/check <br>
     * Rimuove il precendente pannello componenti dal pannello campo <br>
     * Crea e registra un nuovo pannello componenti specifico vuoto  <br>
     * Aggiunge il nuovo pannello componenti al centro del pannello campo  <br>
     */
    protected void resetComponenti() {
        /* variabili e costanti locali di lavoro */
        PannelloComponenti panComponenti;
        PannelloCampo panCampo;

        try {    // prova ad eseguire il codice

            /* ricrea la lista dei componenti toggle */
            this.setComponentiGruppo(new ArrayList<JToggleButton>());

            /* rimuove il precedente pannello componenti dal pannello campo */
            panComponenti = this.getPannelloComponenti();
            if (panComponenti != null) {
                this.getPannelloCampo().remove(panComponenti.getPanFisso());
            }// fine del blocco if

            /* crea e registra un nuovo pannello componenti specifico vuoto */
            panComponenti = this.creaPannelloComponentiSpecifico();
            this.setPannelloComponenti(panComponenti);

            /* aggiunge il nuovo pannello componenti al centro del pannello campo */
            panCampo = this.getPannelloCampo();
            panCampo.add(this.getPannelloBaseComponenti(), Layout.CENTRO);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
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
        ItemListener azioneItem;
        ArrayList<JToggleButton> componentiVideo;


        super.aggiungeListener(portale);

        try { // prova ad eseguire il codice


            componentiVideo = this.getComponentiGruppo();

            if (componentiVideo != null) {

                for (JToggleButton comp : componentiVideo) {

                    /* aggiunge l'item listener a ogni componente  */
                    if (portale != null) {
                        azioneItem = portale.getAzItem(Azione.ITEM_MODIFICATO);
                    } else {
                        azioneItem = Progetto.getAzItem(Azione.ITEM_MODIFICATO);
                    }// fine del blocco if-else

                    comp.addItemListener(azioneItem);

                }

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce l'elenco dei componenti video del campo.
     * <p/>
     * In questo caso e' una lista di JToggleButtons.<br>
     *
     * @return l'elenco dei componenti video
     */
    protected ArrayList<JComponent> getComponentiVideo() {
        /* variabili e costanti locali di lavoro */
        ArrayList<JComponent> componenti = null;
        ArrayList<JToggleButton> bottoni = null;

        try { // prova ad eseguire il codice
            componenti = new ArrayList<JComponent>();
            bottoni = this.getComponentiGruppo();
            if (bottoni != null) {
                for (JToggleButton bottone : bottoni) {
                    componenti.add(bottone);
                }
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return componenti;
    }


    /**
     * Restituisce il riferimento al singolo componente
     * contenuto nel pannelloComponenti.
     * <p/>
     * Nel caso di gruppo di Toggle, restituisce
     * il pannello componenti stesso contenente i bottoni <br>
     *
     * @return componente GUI singolo
     */
    public JComponent getComponente() {
        return this.getPannelloComponenti().getPanFisso();
    }

//    public void guiModificata() {
//        /* variabili e costanti locali di lavoro */
//        ArrayList<JToggleButton> toggles;
//        boolean remove = false;
//
//        try { // prova ad eseguire il codice
//            toggles = this.getComponentiGruppo();
//            for(JToggleButton toggle : toggles){
//                if (remove) {
//                    toggle.setSelected(false);
//                } else {
//                    if (toggle.isSelected()) {
//                        remove=true;
//                    }// fine del blocco if
//                }// fine del blocco if-else
//
//            }
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }


    /**
     * Invocato quando un componente del campo video ha
     * acquisito il fuoco in maniera permanente.
     * <p/>
     * Lancia l'evento focusGained dell'intero campo solo se
     * il fuoco non proviene da un altro componente
     * del gruppo stesso
     *
     * @param e l'evento fuoco
     */
    protected void focusGainedComponente(FocusEvent e) {
        /* variabili e costanti locali di lavoro */
        ArrayList<JToggleButton> bottoni;
        Component opposto;
        boolean mantieneFuoco = false;

        try { // prova ad eseguire il codice

            bottoni = this.getComponentiGruppo();
            opposto = e.getOppositeComponent();
            for (JToggleButton bottone : bottoni) {
                if (bottone == opposto) {
                    mantieneFuoco = true;
                    break;
                }// fine del blocco if
            }
            if (!(mantieneFuoco)) {
                super.fireFuocoPreso();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Invocato quando un componente del campo video ha
     * perso il fuoco in maniera permanente.
     * <p/>
     * Lancia l'evento focusLost dell'intero campo solo se
     * il fuoco non viene trasferito a un altro componente
     * del gruppo stesso
     *
     * @param e l'evento fuoco
     */
    public void focusLostComponente(FocusEvent e) {
        /* variabili e costanti locali di lavoro */
        ArrayList<JToggleButton> bottoni;
        Component opposto;
        boolean mantieneFuoco = false;

        try { // prova ad eseguire il codice

            bottoni = this.getComponentiGruppo();
            opposto = e.getOppositeComponent();
            for (JToggleButton bottone : bottoni) {
                if (bottone == opposto) {
                    mantieneFuoco = true;
                    break;
                }// fine del blocco if
            }
            if (!(mantieneFuoco)) {
                super.fireFuocoPerso();
            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un Pannello Componenti specifico per questo tipo di campo.
     * <p/>
     * Metodo invocato dal ciclo Inizializza()<br>
     * E' responsabilita' di questo metodo:<br>
     * - Creare un pannello adatto per contenere i componenti<br>
     * - Regolare il layout del pannello<br>
     *
     * @return il pannello creato
     */
    private PannelloComponenti creaPannelloComponentiSpecifico() {
        /* variabili e costanti locali di lavoro */
        PannelloComponenti pannello = null;
        Layout layout;
        int gap = 0;

        try { // prova ad eseguire il codice

            /*
             * regola il gap in funzione del'orientamento
             * se l'orientamento e' verticale, il gap e' zero, se e' orizzontale
             * il gap e' 4
             */
            switch (this.getOrientamentoComponenti()) {
                case Layout.ORIENTAMENTO_VERTICALE:
                    gap = this.getGapVerticale();
                    break;
                case Layout.ORIENTAMENTO_ORIZZONTALE:
                    gap = this.getGapOrizzontale();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

//            pannelloFlusso = new PannelloFlusso(this.getOrientamentoComponenti());
//            pannelloFlusso.setOpaque(false);
//            pannelloFlusso.setUsaGapFisso(true);
//            pannelloFlusso.setGapPreferito(gap);
//            pannelloFlusso.setAllineamento(Layout.ALLINEA_SX);
//            pannelloFlusso.setRidimensionaComponenti(false);
//            pannelloFlusso.setUsaScorrevole(false);

            /* assegna il layout al pannello componenti */
            pannello = this.getPannelloComponenti();
            pannello.removeAll();
            layout = new LayoutFlusso(pannello, this.getOrientamentoComponenti());
            pannello.setLayout(layout);
            pannello.setOpaque(false);
            layout.setUsaGapFisso(true);
            layout.setGapPreferito(gap);
            layout.setAllineamento(Layout.ALLINEA_SX);
            layout.setRidimensionaComponenti(false);
            layout.setUsaScorrevole(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }

//    /**
//     * Recupera lo stato corrente di abilitazione
//     * del componente GUI del campo per la scheda.
//     * <p/>
//     * Sovrascrive il metodo della superclasse<br>
//     *
//     * @return lo stato di abilitazione del componente GUI
//     */
//    public boolean isAbilitato() {
//        /* variabili e costanti locali di lavoro */
//        boolean abilitato = true;
//
//        try {    // prova ad eseguire il codice
//            /* spazzola tutti i componenti
//             * se anche uno solo non e' abilitato ritorna false */
//            for (JToggleButton comp : this.getComponentiGruppo()) {
//                if (!comp.isEnabled()) {
//                    abilitato = false;
//                }// fine del blocco if
//            } // fine del ciclo for-each
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return abilitato;
//    }


    /**
     * Abilita/disabilita il componente GUI del campo per la scheda.
     * <p/>
     * Sovrascrive il metodo della superclasse<br>
     *
     * @param flag true per abilitare, false per disabilitare
     */
    protected void regolaModificabile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        ArrayList<JToggleButton> compGruppo;
        boolean continua;

        try {    // prova ad eseguire il codice

            /* recupera i componenti del gruppo */
            compGruppo = this.getComponentiGruppo();
            continua = compGruppo != null;

            /* spazzola tutti i componenti e li abilita/disabilita */
            if (continua) {
                for (JToggleButton comp : compGruppo) {
                    if (comp != null) {
                        comp.setEnabled(flag);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna un singolo componente.
     * <p/>
     *
     * @param pos la posizione nella lista dei componento (0 per il primo)
     */
    protected JToggleButton getComponente(int pos) {
        /* variabili e costanti locali di lavoro */
        JToggleButton comp = null;
        ArrayList<JToggleButton> componenti;
        Object oggetto;

        try { // prova ad eseguire il codice
            componenti = this.getComponentiGruppo();
            if (componenti != null) {
                oggetto = componenti.get(pos);
                if (oggetto instanceof JToggleButton) {
                    comp = (JToggleButton)oggetto;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }

//    /**
//     * Invocato quando un toggle button del gruppo cambia di stato.
//     * <p>
//     * Lancia un evento di GUI modificata per il campo
//     */
//    public void itemStateChanged(ItemEvent e) {
//        this.getCampoParente().fire(CampoBase.Evento.GUIModificata);
//    }


    /**
     * Recupera la lista dei valori (oggetti di tipo Object).
     *
     * @return arrayList di valori
     */
    public ArrayList getListaValori() {
        /* variabili e costanti locali di lavoro */
        ArrayList listaValori = null;

        try { // prova ad eseguire il codice
            listaValori = this.getCampoDati().getListaValori();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaValori;
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
        /* variabili e costanti locali di lavoro */
        ArrayList<JToggleButton> listaComp;
        int lar;

        try { // prova ad eseguire il codice

            /* regola la larghezza di ogni componente */
            listaComp = this.getComponentiGruppo();
            lar = this.getLarghezzaComponenti();
            if (lar!=0) {
                for(JToggleButton toggle : listaComp){
                    Lib.Comp.setPreferredWidth(toggle, lar);
                }
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    protected int getOrientamentoComponenti() {
        return orientamento;
    }


    /**
     * Regola l'orientamento del layout del Pannello Componenti.
     * <p/>
     *
     * @param orientamento il codice di orientamento
     * Layout.ORIENTAMENTO_ORIZZONTALE o
     * Layout.ORIENTAMENTO_VERTICALE
     */
    public void setOrientamentoComponenti(int orientamento) {
        this.orientamento = orientamento;
    }


    protected ArrayList<JToggleButton> getComponentiGruppo() {
        return componentiGruppo;
    }


    private void setComponentiGruppo(ArrayList<JToggleButton> componentiGruppo) {
        this.componentiGruppo = componentiGruppo;
    }


    protected ButtonGroup getGruppoBottoni() {
        return gruppoBottoni;
    }


    private void setGruppoBottoni(ButtonGroup gruppoBottoni) {
        this.gruppoBottoni = gruppoBottoni;
    }


    public int getOrientamento() {
        return orientamento;
    }


    public void setOrientamento(int orientamento) {
        this.orientamento = orientamento;
    }


    private int getGapVerticale() {
        return gapVerticale;
    }


    private void setGapVerticale(int gapVerticale) {
        this.gapVerticale = gapVerticale;
    }


    private int getGapOrizzontale() {
        return gapOrizzontale;
    }


    private void setGapOrizzontale(int gapOrizzontale) {
        this.gapOrizzontale = gapOrizzontale;
    }


    /**
     * Regola il gap tra i componenti del gruppo (radio o check).
     *
     * @param gap la distanza tra i componenti
     */
    public void setGapGruppo(int gap) {
        try { // prova ad eseguire il codice
            switch (this.getOrientamentoComponenti()) {
                case Layout.ORIENTAMENTO_VERTICALE:
                    this.setGapVerticale(gap);
                    break;
                case Layout.ORIENTAMENTO_ORIZZONTALE:
                    this.setGapOrizzontale(gap);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }
}// fine della classe
