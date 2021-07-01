/**
 * Title:        ComboBase.java
 * Package:      it.algos.base.combo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 17 novembre 2003 alle 16.31
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.combo;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterItem;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.campo.dati.CDElenco;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.elemento.ESeparatore;
import it.algos.base.campo.elemento.EVuoto;
import it.algos.base.campo.elemento.Elemento;
import it.algos.base.campo.video.CVCombo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFont;
import it.algos.base.libreria.Libreria;
import it.algos.base.portale.Portale;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Estende la classe JComboBox <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  17 novembre 2003 ore 16.31
 */
public final class ComboLista extends ComboBase {

    /* oggetto grafico di riferimento */
    private JComboBox comboBox;

    /**
     * valore di selezione nulla del combo box
     */
    private static final int SELEZIONE_NULLA = -1;


    /**
     * Costruttore base
     * <p/>
     *
     * @param campoVideo di riferimento
     */
    public ComboLista(CVCombo campoVideo) {
        /** rimanda al costruttore della superclasse */
        super(campoVideo);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        int altezza;

        try { // prova ad eseguire il codice

            /* crea il combobox */
            this.setComboBox(new JComboBox());

            /* regola colore e font del componente */
            TestoAlgos.setCombo(this.getComboBox());

            /* assegna la larghezza di default */
            this.setLarghezza(LARGHEZZA_DEFAULT);

            /* assegna l'altezza di default */
            altezza = this.getAltezzaDefault();
            this.setAltezza(altezza);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


    /**
     * Aggiunge i Listeners al combo.
     * <p/>
     *
     * @param portale contenente le azioni
     */
    public void aggiungeListener(Portale portale) {
        /* variabili e costanti locali di lavoro */
        JComboBox combo;
        FocusListener azEntrataCampo;
        FocusListener azUscitaCampo;
        PopupMenuListener azEntrataPop;
        PopupMenuListener azUscitaPop;


        try { // prova ad eseguire il codice

            if (portale != null) {
                azEntrataCampo = portale.getAzFocus(Azione.ENTRATA_CAMPO);
                azUscitaCampo = portale.getAzFocus(Azione.USCITA_CAMPO);
                azEntrataPop = portale.getAzPopupMenu(Azione.ENTRATA_POPUP);
                azUscitaPop = portale.getAzPopupMenu(Azione.USCITA_POPUP);
            } else {
                azEntrataCampo = Progetto.getAzFocus(Azione.ENTRATA_CAMPO);
                azUscitaCampo = Progetto.getAzFocus(Azione.USCITA_CAMPO);
                azEntrataPop = Progetto.getAzPopupMenu(Azione.ENTRATA_POPUP);
                azUscitaPop = Progetto.getAzPopupMenu(Azione.USCITA_POPUP);
            }// fine del blocco if-else

            /* recupera il componente */
            combo = this.getComboBox();
            combo.addFocusListener(azEntrataCampo);
            combo.addFocusListener(azUscitaCampo);
            combo.addItemListener(new AzionePop());
            combo.addPopupMenuListener(azEntrataPop);
            combo.addPopupMenuListener(azUscitaPop);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea il modello dei dati <br>
     * <p/>
     * Incapsula ogni oggetto ricevuto in un'istanza di classe ComboData,
     * per gestire le informazioni specifiche <br>
     * Crea il modello interno del combo box passandogli gli oggetti <br>
     */
    private void creaModello(Object[] valori) {
        /** variabili e costanti locali di lavoro */
        int dim = 0;
        Object unValore;
        ComboData[] dati;
        ComboModello unModelloDati;
        Elemento unElemento;

        try {    // prova ad eseguire il codice
            /* dimensione degli array */
            if (valori != null) {
                dim = valori.length;
            }// fine del blocco if

            /* crea un array di dati per adesso vuoto */
            dati = new ComboData[dim];

            /** ... */
            for (int k = 0; k < dim; k++) {
                unValore = valori[k];
                dati[k] = new ComboData();

                if (unValore instanceof Elemento) {
                    unElemento = (Elemento)unValore;
                    dati[k].setElementoSpeciale(true);
                    dati[k].setValore(unElemento);
                    dati[k].setIcona(unElemento.getIcona());

                    if (unValore instanceof ESeparatore) {
                        dati[k].setSeparatore(true);
                    } /* fine del blocco if */

                    if (unValore instanceof EVuoto) {
                        dati[k].setNonSpecificato(true);
                    } /* fine del blocco if */
                } else {
                    dati[k].setValore(unValore);
                } /* fine del blocco if/else */

            } /* fine del blocco for */

            /* crea un'istanza del modello dati customizzato */
            unModelloDati = new ComboModello(dati);

            /* installa il modello dati customizzato */
            this.getComboBox().setModel(unModelloDati);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Regola la larghezza del componente selezionabile
     * o editabile del combo
     * <p/>
     * In un ComboLista, assegna la larghezza al JComboBox <br>
     * In un ComboTavola, assegna la larghezza al componente editabile <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param larghezza da assegnare
     */
    public void setLarghezza(int larghezza) {
        /* variabili e costanti locali di lavoro */
        JComponent combobox;

        try { // prova ad eseguire il codice
            combobox = this.getComboBox();
            Lib.Comp.setPreferredWidth(combobox, larghezza);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regola l'altezza del componente selezionabile
     * o editabile del combo
     * <p/>
     * In un ComboLista, assegna l'altezza al JComboBox <br>
     * In un ComboTavola, assegna l'altezza al componente editabile <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param altezza da assegnare
     */
    public void setAltezza(int altezza) {
        /* variabili e costanti locali di lavoro */
        JComponent combobox;

        try { // prova ad eseguire il codice
            combobox = this.getComboBox();
            Lib.Comp.setPreferredHeigth(combobox, altezza);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna l'altezza di default per il JComboBox.
     * <p/>
     * L'altezza è calcolata in funzione del font
     * e ulteriormente aumentata di un po' per lasciare aria
     *
     * @return l'altezza di default
     */
    private int getAltezzaDefault() {
        /* variabili e costanti locali di lavoro */
        JComboBox comp;
        int hRiga;
        Font unFont;
        double fattoreAria = .5d;
        int hReale = 0;

        try { // prova ad eseguire il codice

            /* recupera il componente */
            comp = this.getComboBox();

            if (comp != null) {

                /* determina l'altezza della riga in funzione del font utilizzato */
                unFont = comp.getFont();
                hRiga = LibFont.getAltezzaFont(unFont);

                /* determina l'altezza reale aumentando l'altezza
                 * teorica di un po' per lasciare aria
                 * l'aumento e' proporzionale all'altezza della riga */
                hReale = hRiga + (int)(hRiga * fattoreAria);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return hReale;
    }


    /**
     * Aggiorna la GUI col valore video.
     * <p/>
     * Recupera il codice dal campo video<br>
     * Regola il componente GUI del campoVideo con il valore <br>
     * (questo metodo va implementato qui) <br>
     */
    public void aggiornaGUI() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        CampoDati unCampoDati;
        CDElenco unCDElenco;
        int pos;
        Object[] valori;
        Object video;

        try {    // prova ad eseguire il codice

            /* valore vuoto di default se qualcosa non funziona */
            valori = new String[1];
            valori[0] = "vuoto";

            /* recupera la posizione della selezione */
            video = this.getCampoDati().getVideo();
            pos = Libreria.getInt(video);

            /* conversione - la pop-lista parte da zero */
            pos--;

            /* recupera il campo dati */
            unCampoDati = this.getCampoDati();
            continua = unCampoDati != null;

            /* controlla che il campo dati sia della classe appropriata */
            if (continua) {
                continua = (unCampoDati instanceof CDElenco);
            }// fine del blocco if

            /* recupera la lista valori dal campo dati */
            if (continua) {
                unCDElenco = (CDElenco)unCampoDati;
                valori = unCDElenco.getArrayValori();
            }// fine del blocco if

            /* passa i valori al modello interno del combo box */
            if (continua) {
                this.setValori(valori);
            }// fine del blocco if

            if (continua) {
                /* regola il popup */
                if ((pos >= 0) && (pos < this.getItemCount())) {
                    this.setSelectedIndex(pos);
                } else {
                    this.setSelectedIndex(SELEZIONE_NULLA);
                } /* fine del blocco if-else */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Ritorna il campo Dati del campo di riferimento.
     * <p/>
     *
     * @return il campo dati di riferimento
     */
    private CampoDati getCampoDati() {
        /* variabili e costanti locali di lavoro */
        CampoDati cd = null;

        try { // prova ad eseguire il codice
            cd = this.getCampoVideo().getCampoParente().getCampoDati();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cd;
    }


    public void addItemListener(ItemListener unListener) {
        this.getComboBox().addItemListener(unListener);
    }


    public void addPopupMenuListener(PopupMenuListener unListener) {
        this.getComboBox().addPopupMenuListener(unListener);
    }


    public void addActionListener(ActionListener unListener) {
        this.getComboBox().addActionListener(unListener);
    }


    public void addMouseListener(MouseListener unListener) {
        this.getComboBox().addMouseListener(unListener);
    }


    public void addKeyListener(KeyListener unListener) {
        this.getComboBox().addKeyListener(unListener);
    }


    public void removeItemListener(ItemListener unListener) {
        this.getComboBox().removeItemListener(unListener);
    }


    public void removePopupMenuListener(PopupMenuListener unListener) {
        this.getComboBox().removePopupMenuListener(unListener);
    }


    public void removeActionListener(ActionListener unListener) {
        this.getComboBox().removeActionListener(unListener);
    }


    /**
     * passa i valori al modello interno del combo box
     */
    public void setValori(Object[] valori) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            if (valori != null) {
                this.creaModello(valori);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo setter */


    public void setSelectedItem(Object unOggetto) {
        this.getComboBox().setSelectedItem(unOggetto);
    }


    public void setSelectedIndex(int unIndice) {
        this.getComboBox().setSelectedIndex(unIndice);
    }


    public Object getSelectedItem() {
        return this.getComboBox().getSelectedItem();
    }


    public int getSelectedIndex() {
        return this.getComboBox().getSelectedIndex();
    }


    public int getItemCount() {
        return this.getComboBox().getItemCount();
    }


    /**
     * restituisce questo oggetto concreto
     */
    public ComboLista getComboLista() {
        return this;
    } /* fine del metodo getter */


    public JComponent getComponente() {
        return this.getComboBox();
    }


    /**
     * Ritorna il componente video di selezione (JComboBox)
     * <p/>
     *
     * @return il componente selezionabile o editabile del combo
     */
    public JComponent getComponenteSelettore() {
        return this.getComponente();
    }


    public JComboBox getComboBox() {
        return comboBox;
    }


    protected void setComboBox(JComboBox comboBox) {
        this.comboBox = comboBox;
    }


    /**
     * Inner class per gestire l'azione.
     */
    private class AzionePop extends AzAdapterItem {

        private ComboData precedente = null;


        /**
         * itemStateChanged, da ItemListener.
         * <p/>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void itemStateChanged(ItemEvent unEvento) {
            /* variabili e costanti locali di lavoro */
            Campo campo;
            ComboData dato;
            JComboBox combo;
            int tipoEvento;

            try { // prova ad eseguire il codice

                campo = getCampoVideo().getCampoParente();
                tipoEvento = unEvento.getStateChange();
                dato = this.getComboData(unEvento);

                /* se l'evento è DESELECTED, memorizza l'ultimo
                 * elemento selezionato */
                if (tipoEvento == ItemEvent.DESELECTED) {
                    if (dato != null) {
                        if (!dato.isElementoSpeciale()) {
                            precedente = dato;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                /* se l'evento è SELECTED, controlla la validità
                 * dell' elemento selezionato.
                 * Se è un separatore, ripristina la selezione precedente
                 * e non rilancia l'evento.
                 * Altrimenti, rilancia l'evento */
                if (tipoEvento == ItemEvent.SELECTED) {
                    if (dato != null) {
                        if (!dato.isSeparatore()) {
                            campo.fire(CampoBase.Evento.GUIModificata);
                        } else {
                            combo = this.getComboBox(unEvento);
                            combo.setSelectedItem(precedente);
                        }// fine del blocco if-else
                    }// fine del blocco if
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * .
         * <p/>
         */
        private JComboBox getComboBox(ItemEvent unEvento) {
            /* variabili e costanti locali di lavoro */
            JComboBox combo = null;
            Object oggetto;

            try {    // prova ad eseguire il codice
                oggetto = unEvento.getSource();
                if (oggetto instanceof JComboBox) {
                    combo = (JComboBox)oggetto;
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return combo;
        }


        /**
         * Ritorna l'oggetto ComboData relativo a un evento.
         * <p/>
         *
         * @param unEvento l'evento
         *
         * @return l'oggetto ComboData
         */
        private ComboData getComboData(ItemEvent unEvento) {
            /* variabili e costanti locali di lavoro */
            ComboData comboData = null;
            JComboBox combo;
            Object elemento;

            try {    // prova ad eseguire il codice

                combo = this.getComboBox(unEvento);
                elemento = combo.getSelectedItem();
                if (elemento != null) {
                    if (elemento instanceof ComboData) {
                        comboData = (ComboData)elemento;
                    }// fine del blocco if
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return comboData;
        }


    } // fine della classe interna


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     * Per fare una copia completa di questo oggetto occorre:
     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
     * gli stessi valori delle variabili originarie
     * Secondo copiare tutte le variabili che sono puntatori ad altri
     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     * originale (in genere tutti gli oggetti che vengono creati nella
     * classe col comando new)
     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     * esistenti nelle sottoclassi stesse
     */
    public Combo clona() {
        /* variabili e costanti locali di lavoro */
        Combo unCombo = null;

        try { // prova ad eseguire il codice
            unCombo = ComboFactory.creaLista(this.getCampoVideo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCombo;
    }

}// fine della classe it.algos.base.combo.ComboBase.java

