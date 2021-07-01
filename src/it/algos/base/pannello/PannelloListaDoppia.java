/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.pannello;

import it.algos.base.bottone.BottoneAzione;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Pannello con due liste di stringhe.
 * </p>
 * Questa classe: <ul>
 * <li> La lista di destra è ordinabile </li>
 * <li> Mantiene i dati delle due liste </li>
 * <li> Il pannello principale è diviso verticalmente in due:
 * area titolo ed area contenuto </li>
 * <li> L'area contenuto è divisa orizzontalmente in tre:
 * listaSx, pannello bottoni e listaDx</li>
 * <li> Ogni lista è divisa verticalmente in tre:
 * area titolo, area contenuto, area bottoni comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public class PannelloListaDoppia extends PannelloFlusso {

    /**
     * pannello titoli generale
     */
    private Pannello panTitoli;

    /**
     * label del titolo
     */
    private JLabel etichetta;

    /**
     * pannello contenuti
     */
    private PannelloFlusso panContenuti;

    /**
     * pannello lista sinistra
     */
    private PannelloLista panListaSx;

    /**
     * pannello lista destra
     */
    private PannelloLista panListaDx;

    /**
     * pannello comandi
     */
    private PannelloFlusso panComandi;


    /**
     * bottone per spostare a destra un elemento
     */
    private BottoneAzione botSelezionati;

    /**
     * bottone per spostare a destra tutti gli elementi
     */
    private BottoneAzione botTutti;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public PannelloListaDoppia() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* regolazioni generali del pannello */
            this.regolaPannello();

            /* regola la sezione titolo */
            this.regolaSezioneTitolo();

            /* regola la sezione contenuto */
            this.regolaSezioneContenuti();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


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
        /* variabili e costanti locali di lavoro */
        PannelloLista panSx, panDx;

        try { // prova ad eseguire il codice

//            /* invoca il metodo sovrascritto della superclasse */
//            super.inizializza();

            /* recupera i due pannelli */
            panSx = this.getPanListaSx();
            panDx = this.getPanListaDx();

            /* inizializza il pannello di sinistra */
            if (panSx != null) {
                panSx.inizializza();
            }// fine del blocco if

            /* inizializza il pannello di destra */
            if (panDx != null) {
                panDx.inizializza();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il pannello.
     * <p/>
     * Regola il gestore <br>
     * Regola il bordo <br>
     */
    private void regolaPannello() {
        try { // prova ad eseguire il codice
            this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            this.setUsaScorrevole(false);
            this.setUsaGapFisso(true);
            this.setGapPreferito(10);
            this.setOpaque(false);

            this.setBackground(Color.RED);
            this.setOpaque(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la sezione titolo.
     * <p/>
     * Crea gli oggetti <br>
     * Inserisce la sezione nel pannello <br>
     */
    private void regolaSezioneTitolo() {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try { // prova ad eseguire il codice
            /* regola gli oggetti */
            this.setPanTitoli(new PannelloFlusso());
            this.setEtichetta(new JLabel());
            this.setTitolo("");

            /* aggiunge i pannelli alla sezione*/
            pan = this.getPanTitoli();
            pan.add(this.getEtichetta());

            /* aggiunge la sezione al pannello generale */
            this.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la sezione contenuti.
     * <p/>
     * Crea il pannello della sezione <br>
     * Crea il pannello per la lista sinistra <br>
     * Crea il pannello per i bottoni centrali <br>
     * Crea il pannello per la lista destra <br>
     * Inserisce la sezione nel pannello <br>
     */
    private void regolaSezioneContenuti() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan;
        PannelloLista panSx;
        JPanel panBot;
        PannelloLista panDx;

        try { // prova ad eseguire il codice

            /* crea e regola il pannello della sezione */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            this.setPanContenuti(pan);

            /* crea e regola il pannello per la lista sinistra */
            panSx = this.creaListaSx();

            /* crea e regola il pannello per i bottoni centrali */
            panBot = this.creaPanBottoni();

            /* crea e regola il pannello per la lista destra */
            panDx = this.creaListaDx();

            /* aggiunge i pannelli alla sezione*/
            pan.add(panSx);
            pan.add(panBot);
            pan.add(panDx);

            /* aggiunge la sezione al pannello generale */
            this.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il pannello per la lista sinistra.
     * <p/>
     * Crea il pannello <br>
     * Crea un bottone deseleziona, con azione associata <br>
     * Specifica il metodo target dell'evento <br>
     * Crea un bottone freccia semplice verso destra, con azione associata <br>
     * Specifica il metodo target dell'evento <br>
     * Crea un bottone freccia doppia verso destra, con azione associata <br>
     * Specifica il metodo target dell'evento <br>
     * Aggiunge i botttoni al pannello della lista <br>
     * I botttoni sono inizialmente disabilitati <br>
     */
    private PannelloLista creaListaSx() {
        /* variabili e costanti locali di lavoro */
        PannelloLista pan = null;
        String titolo = "Lista sorgente";
        String iconaSel = "Successivo24";
        String iconaTutti = "FastForward24";
        BottoneAzione botSel;
        BottoneAzione botTutti;
        String metodoSel = "spostaDestraSelezionati";
        String metodoTutti = "spostaDestraTutti";
        String toolTipSel = "Sposta a destra gli elementi selezionati";
        String toolTipTutti = "Sposta a destra tutti gli elementi";

        try { // prova ad eseguire il codice

            /* crea e regola il pannello per la lista sinistra */
            this.setPanListaSx(new PannelloLista(Lista.SINISTRA));
            pan = this.getPanListaSx();
            pan.setTitolo(titolo);
            pan.setPanDoppio(this);
            pan.setUsaDeselezione(true);
            pan.setUsaOrdinamento(false);
            pan.setUsaScorrevoleLista(true);

            /* crea il bottone freccia semplice - selezionati */
            botSel = this.getBottone(metodoSel, iconaSel, toolTipSel);
            pan.addBottone(botSel);
            this.setBotSelezionati(botSel);

            /* crea il bottone freccia doppia - tutti */
            botTutti = this.getBottone(metodoTutti, iconaTutti, toolTipTutti);
            botTutti.setEnabled(true);
            pan.addBottone(botTutti);
            this.setBotTutti(botTutti);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello per i bottoni centrali.
     * <p/>
     * Crea il pannello <br>
     */
    private JPanel creaPanBottoni() {
        /* variabili e costanti locali di lavoro */
        JPanel pan = null;

        try { // prova ad eseguire il codice
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello per la lista destra.
     * <p/>
     * Crea il pannello <br>
     * Seleziona l'opzione del bottone deseleziona <br>
     * Seleziona l'opzione del bottone taglia <br>
     * Seleziona l'opzione dei bottoni ordinamento <br>
     * I botttoni sono inizialmente disabilitati <br>
     */
    private PannelloLista creaListaDx() {
        /* variabili e costanti locali di lavoro */
        PannelloLista pan = null;
        String titolo = "Lista destinazione";

        try { // prova ad eseguire il codice

            /* crea e regola il pannello per la lista destra */
            this.setPanListaDx(new PannelloLista(Lista.DESTRA));
            pan = this.getPanListaDx();
            pan.setTitolo(titolo);
            pan.setPanDoppio(this);
            pan.setUsaDeselezione(true);
            pan.setUsaTaglia(true);
            pan.setUsaOrdinamento(true);
            pan.setUsaScorrevoleLista(true);

//            pan.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Sposta a destra gli elementi.
     * <p/>
     * Metodo invocato dall'evento del bottone <br>
     * Aggiunge nella lista di destra i valori, solo se non esistono giù <br>
     */
    private void spostaDestra(ArrayList<String> valoriSinistra) {
        /* variabili e costanti locali di lavoro */
        PannelloLista pan;
        ArrayList<String> valoriListaDestra;

        try { // prova ad eseguire il codice

            /* recupera i valori della lista di destra */
            pan = this.getPanListaDx();
            valoriListaDestra = pan.getValori();

            /* aggiunge i  valori solo se non già esistente */
            Lib.Array.addValoriUnici(valoriSinistra, valoriListaDestra);
            pan.setValori(valoriListaDestra);

            /* deseleziona la lista di sinistra*/
            pan = this.getPanListaSx();
            pan.deselezionaTutti();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sposta a destra gli elementi selezionati della lista di sinistra.
     * <p/>
     * Metodo invocato dall'evento del bottone <br>
     * Recupera i valori selezionati a sinistra <br>
     * Aggiunge nella lista di destra i valori, solo se non esistono già <br>
     */
    public void spostaDestraSelezionati() {
        /* variabili e costanti locali di lavoro */
        PannelloLista pan;
        ArrayList<String> valoriSelezionati;

        try { // prova ad eseguire il codice

            /* recupera i valori della lista di sinistra */
            pan = this.getPanListaSx();
            valoriSelezionati = pan.getSelezionati();

            /* invoca il metodo delegato della classe */
            this.spostaDestra(valoriSelezionati);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sposta a destra tutti gli elementi della lista di sinistra.
     * <p/>
     * Metodo invocato dall'evento del bottone <br>
     * Recupera tutti i valori della lista di sinistra <br>
     * Aggiunge nella lista di destra i valori, solo se non esistono già <br>
     */
    public void spostaDestraTutti() {
        /* variabili e costanti locali di lavoro */
        PannelloLista pan;
        ArrayList<String> valoriListaSinistra;

        try { // prova ad eseguire il codice
            /* recupera tutti i valori della lista di sinistra */
            pan = this.getPanListaSx();
            valoriListaSinistra = pan.getValori();

            /* invoca il metodo delegato della classe */
            this.spostaDestra(valoriListaSinistra);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizza la lista di sinistra.
     * <p/>
     * Se è selezionata almeno una riga, abilita il bottone spostaDestraUno <br>
     * Abilita sempre il bottone spostaDestraTutti <br>
     */
    public void sincronizzaSinistra() {
        /* variabili e costanti locali di lavoro */
        PannelloLista lista;
        boolean nessunaSelezione;
        BottoneAzione botDeseleziona;
        BottoneAzione botUno;
        BottoneAzione botTutti;

        try { // prova ad eseguire il codice
            /* recupera la lista sinistra */
            lista = this.getPanListaSx();

            nessunaSelezione = lista.isSelectionEmpty();

            /* recupera i riferimenti ai bottoni */
            botDeseleziona = lista.getBotDeseleziona();
            botUno = this.getBotSelezionati();
            botTutti = this.getBotTutti();

            if (nessunaSelezione) {
                botDeseleziona.setEnabled(false);
                botUno.setEnabled(false);
                botTutti.setEnabled(true);
            } else {
                botDeseleziona.setEnabled(true);
                botUno.setEnabled(true);
                botTutti.setEnabled(true);
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizza la lista di destra.
     * <p/>
     * Se è selezionata almeno una riga, abilita tutti i bottoni <br>
     * Se è selezionata piu di una riga, disabilita il su e giu <br>
     * Per la prima ed ultima riga, disabilita i relativi su e giu <br>
     */
    public void sincronizzaDestra() {
        /* variabili e costanti locali di lavoro */
        PannelloLista panLista;
        JList lista;
        boolean selezionati;
        boolean prima;
        boolean ultima;
        int[] intSel;
        int totSel;
        int totale;
        BottoneAzione botDes;
        BottoneAzione botTaglia;
        BottoneAzione botSu;
        BottoneAzione botGiu;

        try { // prova ad eseguire il codice
            /* recupera la lista destra */
            panLista = this.getPanListaDx();
            lista = panLista.getLista();

            /* recupera le informazioni dal modello dati */
            selezionati = !panLista.isSelectionEmpty();
            intSel = lista.getSelectedIndices();
            totSel = intSel.length;
            totale = lista.getModel().getSize();

            /* controlla prima ed ultima */
            prima = lista.getSelectedIndex() == 0;
            ultima = lista.getSelectedIndex() == totale - 1;

            /* recupera i riferimenti ai bottoni */
            botDes = panLista.getBotDeseleziona();
            botTaglia = panLista.getBotTaglia();
            botSu = panLista.getBottoneSu();
            botGiu = panLista.getBottoneGiu();

            /* regola i bottoni */
            botDes.setEnabled(selezionati);
            botTaglia.setEnabled(selezionati);
            botSu.setEnabled(selezionati);
            botGiu.setEnabled(selezionati);

            if (totSel > 1) {
                botSu.setEnabled(false);
                botGiu.setEnabled(false);
            }// fine del blocco if

            if (prima) {
                botSu.setEnabled(false);
            }// fine del blocco if

            if (ultima) {
                botGiu.setEnabled(false);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizza la lista.
     * <p/>
     * Metodo invocato da una modifica della selezione della lista <br>
     * Invoca il metodo delegato <br>
     */
    public void sincronizzaLista(PannelloListaDoppia.Lista sinistraDestra) {
        try { // prova ad eseguire il codice
            switch (sinistraDestra) {
                case SINISTRA:
                    sincronizzaSinistra();
                    break;
                case DESTRA:
                    sincronizzaDestra();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private JLabel getEtichetta() {
        return etichetta;
    }


    private void setEtichetta(JLabel etichetta) {
        this.etichetta = etichetta;
    }


    public void setTitolo(String titolo) {
        this.getEtichetta().setText(titolo);
        this.getPanTitoli().bloccaDim();
    }


    Pannello getPanTitoli() {
        return panTitoli;
    }


    void setPanTitoli(Pannello panTitoli) {
        this.panTitoli = panTitoli;
    }


    PannelloFlusso getPanContenuti() {
        return panContenuti;
    }


    void setPanContenuti(PannelloFlusso panContenuti) {
        this.panContenuti = panContenuti;
    }


    public PannelloLista getPanListaSx() {
        return panListaSx;
    }


    void setPanListaSx(PannelloLista panListaSx) {
        this.panListaSx = panListaSx;
    }


    public PannelloLista getPanListaDx() {
        return panListaDx;
    }


    void setPanListaDx(PannelloLista panListaDx) {
        this.panListaDx = panListaDx;
    }


    PannelloFlusso getPanComandi() {
        return panComandi;
    }


    void setPanComandi(PannelloFlusso panComandi) {
        this.panComandi = panComandi;
    }


    public void setValoriListaSx(ArrayList<String> valori) {
        this.getPanListaSx().setValori(valori);
    }


    public void setValoriListaDx(ArrayList<String> valori) {
        this.getPanListaDx().setValori(valori);
    }


    private BottoneAzione getBotSelezionati() {
        return botSelezionati;
    }


    private void setBotSelezionati(BottoneAzione botSelezionati) {
        this.botSelezionati = botSelezionati;
    }


    private BottoneAzione getBotTutti() {
        return botTutti;
    }


    private void setBotTutti(BottoneAzione botTutti) {
        this.botTutti = botTutti;
    }


    /**
     * Enumerazione.
     * <p/>
     * Costanti per identificare le due liste <br>
     */
    public enum Lista {

        SINISTRA,
        DESTRA
    }// fine della classe interna


} // fine della classe
