/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-feb-2005
 */
package it.algos.base.campo.video;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CDElenco;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.LibFont;
import it.algos.base.libreria.Libreria;
import it.algos.base.listasingola.ListaSingola;
import it.algos.base.pannello.Pannello;
import it.algos.base.portale.Portale;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Componente video di tipo JList.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il componente GUI specifico di questa classe </li>
 * <li> Aggiunge i Listener al componente specifico </li>
 * <li> Regola i font, la dimensione e la posizione del componente specifico </li>
 * <li> Regola le dimensioni del pannelloComponenti </li>
 * <li> Implementa i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati </li>
 * <li> L'attributo <i>video</i> del <code>CampoDati</code> associato,
 * <strong>deve</strong> essere di tipo testo </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-feb-2005 ore 15.47.44
 */
public final class CVLista extends CVBase {

    /**
     * valore di selezione nulla del combo box
     */
    private static final int SELEZIONE_NULLA = -1;

    /**
     * oggetto GUI principale
     */
    private ListaSingola lista = null;

    private ItemListener azModifica = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVLista(Campo unCampoParente) {
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

            /* svuota il pannello componenti */
            this.getPannelloComponenti().removeAll();

            /* crea il componente */
            this.creaComponentiInterni();

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


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
        Pannello pc;
        ListaSingola lista;


        try { // prova ad eseguire il codice

            /* crea e registra l'oggetto GUI principale */
            lista = new ListaSingola();
            this.setLista(lista);

            /* regola colore e font del componente */
            TestoAlgos.setCombo(lista);

            /* registra il componente grafico */
            this.setComponente(lista);

            /* aggiunge il componente principale al pannelloComponenti */
            pc = this.getPannelloComponenti();
            pc.add(lista);

            /* invoca il metodo sovrascritto nella superclasse */
            super.creaComponentiInterni();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
        JList componente;
        ListSelectionListener azSelezione = null;
        MouseListener azDoppioClick = null;


        super.aggiungeListener(portale);

        try {    // prova ad eseguire il codice

            if (portale != null) {
                azSelezione = portale.getAzListSelection(Azione.SELEZIONE_MODIFICATA);
                azDoppioClick = portale.getAzMouse(Azione.MOUSE_DOPPIO_CLICK);
            } else {
                azSelezione = Progetto.getAzListSelection(Azione.SELEZIONE_MODIFICATA);
                azDoppioClick = Progetto.getAzMouse(Azione.MOUSE_DOPPIO_CLICK);
            }// fine del blocco if-else

            /* recupera il componente e aggiunge le azioni */
            componente = this.getLista().getLista();
            componente.addListSelectionListener(azSelezione);
            componente.addMouseListener(azDoppioClick);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola l'altezza del componente.
     * <p/>
     * Sovrascrive il metodo della superclasse<br>
     * E' responsabilita' di questo metodo assegnare l'altezza
     * preferita ai componenti interni al pannello componenti.<br>
     * L'altezza viene regolata in funzione del numero di righe
     * da visualizzare e del font utilizzato
     */
    protected void regolaAltezzaComponenti() {
        /* variabili e costanti locali di lavoro */
        JComponent comp;
        int hRiga;
        Font unFont;
        double fattoreAria = .5d;
        int hReale;
        int lar;
        Dimension dim;

        try { // prova ad eseguire il codice

            /* recupera il componente */
            comp = this.getComponente();

            if (comp != null) {

                /* determina l'altezza della riga in funzione del font utilizzato */
                unFont = comp.getFont();
                hRiga = LibFont.getAltezzaFont(unFont);

                /* determina l'altezza reale aumentando l'altezza
                 * teorica di un po' per lasciare aria
                 * l'aumento e' proporzionale all'altezza della riga */
                hReale = hRiga + (int)(hRiga * fattoreAria);

                /* moltiplica per il numero effettivo di righe */
                hReale *= this.getNumeroRighe();

                /* assegna la nuova altezza al componente */
                lar = comp.getPreferredSize().width;
                dim = new Dimension(lar, hReale);
                comp.setPreferredSize(dim);

            }// fine del blocco if

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
        ListaSingola comp;

        try { // prova ad eseguire il codice
            componenti = new ArrayList<JComponent>();
            comp = this.getLista();
            componenti.add(comp);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return componenti;
    }


    /**
     * Aggiorna la GUI col valore video.
     * <p/>
     * Metodo invocato dal ciclo avvia di SchedaBase <br>
     * Regola il componente GUI del campoVideo con il valore <br>
     * (questo metodo va implementato qui) <br>
     *
     * @param unValore valore video proveniente dal CampoDati
     *
     * @see it.algos.base.scheda.SchedaBase#caricaValori
     * @see it.algos.base.campo.logica.CLBase#memoriaGui()
     */
    public void aggiornaGUI(Object unValore) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        CampoDati unCampoDati;
        int pos;
        Object[] valori;
        ListaSingola lista = null;

        try {    // prova ad eseguire il codice
            /* valore vuoto di default se qualcosa non funziona */
            valori = new String[1];
            valori[0] = "vuoto";

            /* recupera la posizione della selezione */
            pos = Libreria.getInt(unValore);

            /* conversione - la lista parte da zero */
            pos--;

            /* recupera il campo dati */
            unCampoDati = unCampoParente.getCampoDati();

            /* recupera la lista valori dal campo dati */
            valori = ((CDElenco)unCampoDati).getArrayValori();
            continua = (valori != null);

            /* passa i valori al modello interno del combo box */
            if (continua) {
                lista = this.getLista();
                continua = (lista != null);
            }// fine del blocco if

            if (continua) {
                lista.setValori(valori);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     * Metodo invocato da isModificata() e da modificaCampo() <br>
     *
     * @return valore video per il CampoDati
     *
     * @see it.algos.base.navigatore.NavigatoreBase#modificaCampo(it.algos.base.campo.base.Campo)
     * @see it.algos.base.scheda.SchedaBase#isModificata()
     * @see it.algos.base.campo.logica.CLBase#guiMemoria()
     */
    public Object recuperaGUI() {
        /* variabili e costanti locali di lavoro */
        Integer posizione = null;
        int valore;
        Object unValoreVideoVuoto;

        try {    // prova ad eseguire il codice

            /* recupera la posizione selezionata nel combo */
            valore = this.getLista().getSelectedIndex();

            /* controlla la selezione nulla */
            if (valore == SELEZIONE_NULLA) {
                unValoreVideoVuoto = unCampoParente.getCampoDati().getValoreVideoVuoto();
                valore = (Integer)unValoreVideoVuoto;
            } else {
                /* la pop-lista parte da zero, mentre posizione inizia da 1 */
                valore++;
            } /* fine del blocco if/else */

            /* crea l'oggetto da restituire */
            posizione = valore;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return posizione;
    }

//    /**
//     * Regola la larghezza del pannelloComponenti.
//     * <p/>
//     * Metodo invocato dal Layout quando deve posizionare il campo <br>
//     * Viene utilizzato per il dimensionamento dall'esterno <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     *
//     * @param larghezza da assegnare al pannelloComponenti
//     */
//    public void setLarghezzaPannelloComponenti(int larghezza) {
//        /* variabili e costanti locali di lavoro */
//        Dimension dim;
//
//        try { // prova ad eseguire il codice
//            /* invoca il metodo sovrascritto della superclasse */
//            super.setLarghezzaPannelloComponenti(larghezza);
//
//            /* regola le dimensioni del combo */
//            dim = this.getPannelloBaseComponenti().getSize();
//            this.getLista().setSize(dim);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }

//    /**
//     * This method is called when the user selects an item from the
//     * combo box's menu or, in an editable combo box,
//     * when the user presses Enter.
//     * <p/>
//     * Lancia un evento di GUI modificata per il campo
//     */
//    public void actionPerformed(ActionEvent e) {
//        this.getCampoParente().fire(CampoBase.Evento.GUIModificata);
//    }


    public ListaSingola getLista() {
        return lista;
    }


    public void setLista(ListaSingola lista) {
        this.lista = lista;
    }


}// fine della classe
