/**
 * Title:     StatusBar
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      21-feb-2005
 */
package it.algos.base.lista;

import it.algos.base.azione.adapter.AzAdapterItem;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.GestioneEventi;
import it.algos.base.evento.portale.PortaleListaPopAz;
import it.algos.base.evento.portale.PortaleListaPopEve;
import it.algos.base.evento.portale.PortaleListaPopLis;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.Portale;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.wrapper.TestoAlgos;
import it.algos.base.wrapper.WrapFiltri;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

/**
 * Barra di stato informativa della lista.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 21-feb-2005 ore 10.30.30
 */
public final class ListaStatusBar extends PannelloFlusso implements GestioneEventi {

    /**
     * Colore di sfondo per la status bar
     */
    private static final Color SFONDO_BAR = Color.LIGHT_GRAY;

    /**
     * Colore per il segnale di modificato acceso
     */
    private static final Color MODIFICATO = Color.RED;

    /**
     * Lista di riferimento.
     * <p/>
     */
    private Lista lista = null;

    /**
     * Componente per il display dei records visibili e disponibili.
     * <p/>
     */
    private JLabel infoRecords = null;

    /**
     * Componente per il display dello stato navigatore modificato.
     * <p/>
     */
    private JPanel infoModificato = null;

    /**
     * Componente per il display di oggetti specifici
     */
    private Pannello panCustom = null;

    /**
     * Componente per il display dei combo boxes selettori
     */
    private Pannello panCombo = null;

    /**
     * Combobox per i filtri.
     */
    ArrayList<JComboBox> comboFiltri = null;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;

//    /**
//     * flag per aggiungere una riga al popup dei filtri.
//     */
//    private boolean flagFiltroPopTutti;

    private AzionePopup azPopup;


    /**
     * Costruttore base senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public ListaStatusBar() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param lista la lista di riferimento
     */
    public ListaStatusBar(Lista lista) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_ORIZZONTALE);

        /* regola le variabili di istanza coi parametri */
        this.setLista(lista);

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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JLabel label;
        Pannello pan;


        try { // prova ad eseguire il codice

            this.setAzPopup(new AzionePopup());
            this.setComboFiltri(new ArrayList<JComboBox>());

            this.setAllineamento(Layout.ALLINEA_CENTRO);
            this.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
            this.setBackground(SFONDO_BAR);
            this.setOpaque(true);

            /* crea e regola il componente per il numero di records */
            label = new JLabel();
            this.setInfoRecords(label);
            TestoAlgos.setLegenda(label);
//            label.setBackground(SFONDO_BAR);
            label.setOpaque(false);
            this.add(label);

            /* assegna subito un testo per regolare l'altezza del componente */
            this.getInfoRecords().setText(" ");

            /* crea e aggiunge il componente per gli oggetti Custom */
            pan = PannelloFactory.orizzontale(null);
            this.setPanCustom(pan);
            pan.setOpaque(false);
            this.add(Box.createHorizontalGlue());
            this.add(pan);

            /* crea e aggiunge il componente per i Combo Selettori */
            pan = PannelloFactory.orizzontale(null);
            this.setPanCombo(pan);
            pan.setOpaque(false);
            this.add(Box.createHorizontalGlue());
            this.add(pan);

            /* non pone limiti alla dimensione di questo componente */
            this.sbloccaDimMax();

            /* crea e regola il componente per l'indicatore modificato */
//            pan = new JPanel();
//            pan.setPreferredSize(new Dimension(10, 10));
//            pan.setSize(new Dimension(10, 10));
//            pan.setMaximumSize(new Dimension(10, 10));
//            pan.setMinimumSize(new Dimension(10, 10));
//            pan.setOpaque(true);
//            this.setInfoModificato(pan);
//            this.add(this.getInfoModificato(), BorderLayout.EAST);

            /* lista dei propri eventi */
            this.setListaListener(new EventListenerList());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
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
//            super.inizializza();

            /* crea i filtri pop */
            if (this.getLista().isUsaFiltriPop()) {
                this.creaFiltriPop();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della status bar.
     * <p/>
     * Chiamato dalla sincronizzazione della lista.<br>
     * Aggiorna il display dei numeri records.
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        int quantiVisualizzati;
        int quantiDisponibili;
        Lista lista;
        String testo;
        String parola;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            quantiVisualizzati = lista.getNumRecordsVisualizzati();
            quantiDisponibili = lista.getNumRecordsDisponibili();

            if (quantiVisualizzati == quantiDisponibili) {
                if (quantiVisualizzati != 0) {
                    if (quantiVisualizzati == 1) { // uno
                        parola = "record";
                    } else {  // piu' di uno
                        parola = "records";
                    }// fine del blocco if-else
                    testo = quantiVisualizzati + " " + parola;

                } else {    // nessun record visualizzato o disponibile
                    testo = "nessun record";
                }// fine del blocco if-else
            } else {  // visualizzatti diverso da disponibili
                testo = quantiVisualizzati + "/" + quantiDisponibili + " records";
            }// fine del blocco if-else

            this.getInfoRecords().setText(testo);

//            /*
//            * Se il navigatore e' modificato visualizza un segnale rosso
//            */
//            navModificato = this.getNavigatore().isModificato();
//            if (navModificato) {
//                this.getInfoModificato().setBackground(MODIFICATO);
//            } else {
//                this.getInfoModificato().setBackground(SFONDO_BAR);
//            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea e aggiunge i filtri pop.
     * <p/>
     */
    private void creaFiltriPop() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<WrapFiltri> collezioneListeFiltri;
        WrapFiltri locale;
        ArrayList<JComboBox> comboFiltri;
        JComboBox combo;
        ComboBoxModel modello;
        Pannello pan;
        int pos;

        try {    // prova ad eseguire il codice
            /** aggiunge i filtri pop */
//            pan = PannelloFactory.orizzontale(null);
//            pan.getLayoutAlgos().setAllineamento(Layout.ALLINEA_DX); //todo non funziona gac/31-3

            comboFiltri = this.getComboFiltri();
            collezioneListeFiltri = this.getLista().getModulo().getModello().getFiltriPop();

            /* traverso tutta la collezione */
            for (WrapFiltri listaFiltri : collezioneListeFiltri) {

                locale = new WrapFiltri();

                /* traverso tutta la collezione */
                for (Filtro wrap : listaFiltri) {
                    locale.add(wrap);
                } // fine del ciclo for-each

                continua = locale.size() > 0;

                if (continua) {
                    pos = listaFiltri.getPos();
                    if (pos > 0) {
                        pos--;
                    }// fine del blocco if
                    if (listaFiltri.isUsaTutti()) {
                        Filtro filtroTutti = new Filtro();
                        filtroTutti.setNome(listaFiltri.getTesto());

                        if (listaFiltri.isTuttiFinale()) {
                            locale.add(listaFiltri.size(), filtroTutti);
                            if (pos == -1) {
                                pos = locale.size() - 1;
                            }// fine del blocco if
                        } else {
                            locale.add(0, filtroTutti);
                            if (pos == -1) {
                                pos = 0;
                            }// fine del blocco if
                        }// fine del blocco if-else
                    }// fine del blocco if

                    modello = new DefaultComboBoxModel(locale.toArray());

                    combo = new JComboBox();
                    combo.setModel(modello);

                    /* blocca la larghezza massima del combo */
                    Lib.Comp.bloccaLarMax(combo);

                    combo.setToolTipText(listaFiltri.getTitolo());
                    if (pos >= 0) {
                        combo.setSelectedIndex(pos);
                    }// fine del blocco if
                    combo.setVisible(true);
                    TestoAlgos.setCombo(combo);
                    combo.addItemListener(this.getAzPopup());
                    combo.addPopupMenuListener(new AzionePopupMenu());

                    /* aggiunge graficamente al pannelo dei combo */
                    this.getPanCombo().add(combo);
                    comboFiltri.add(combo);
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Visualizza un testo nel componente custom (centrale).
     * <p/>
     *
     * @param testo da visualizzare nel componente custom (centrale)
     */
    public void setCustom(String testo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Pannello pan;
        JLabel label;

        try { // prova ad eseguire il codice
            pan = this.getPanCustom();
            continua = (pan != null);

            if (continua) {
                pan.removeAll();

                label = new JLabel(testo);
                TestoAlgos.setLegenda(label);
                label.setForeground(Color.red);
                pan.add(label);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna la lista proprietaria di questa Status Bar.
     * <p/>
     *
     * @return la lista proprietaria
     */
    public Lista getLista() {
        return lista;
    }


    private void setLista(Lista lista) {
        this.lista = lista;
    }


    private JLabel getInfoRecords() {
        return infoRecords;
    }


    private void setInfoRecords(JLabel infoRecords) {
        this.infoRecords = infoRecords;
    }


    private JPanel getInfoModificato() {
        return infoModificato;
    }


    private void setInfoModificato(JPanel infoModificato) {
        this.infoModificato = infoModificato;
    }


    public Pannello getPanCustom() {
        return panCustom;
    }


    private void setPanCustom(Pannello panCustom) {
        this.panCustom = panCustom;
    }


    private Pannello getPanCombo() {
        return panCombo;
    }


    private void setPanCombo(Pannello panCombo) {
        this.panCombo = panCombo;
    }


    public ArrayList<JComboBox> getComboFiltri() {
        return comboFiltri;
    }


    private void setComboFiltri(ArrayList<JComboBox> comboFiltri) {
        this.comboFiltri = comboFiltri;
    }


    /**
     * Ritorna il navigatore che gestisce la lista.
     * <p/>
     *
     * @return il navigatore che gestisce la lista
     */
    private Navigatore getNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Portale portale;
        Lista lista;

        try {    // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                portale = lista.getPortale();
                if (portale != null) {
                    nav = portale.getNavigatore();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Ritorna il filtro correntemente selezionato nei popup.
     * <p/>
     *
     * @return filtro selezionato
     */
    public Filtro getFiltroPops() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        Filtro filtroTot = null;
        ArrayList<JComboBox> comboFiltri;

        try {    // prova ad eseguire il codice

            /* recupera tutti i popup dei filtri */
            comboFiltri = this.getComboFiltri();

            filtroTot = new Filtro();

            /* traverso tutta la collezione */
            for (JComboBox combo : comboFiltri) {
                filtro = (Filtro)combo.getSelectedItem();
                filtroTot.add(filtro);
            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }

//    public boolean isFlagFiltroPopTutti() {
//        return flagFiltroPopTutti;
//    }
//
//
//    public void setFlagFiltroPopTutti(boolean flagFiltroPopTutti) {
//        this.flagFiltroPopTutti = flagFiltroPopTutti;
//    }


    public EventListenerList getListaListener() {
        return listaListener;
    }


    public void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    public AzionePopup getAzPopup() {
        return azPopup;
    }


    private void setAzPopup(AzionePopup azPopup) {
        this.azPopup = azPopup;
    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Evento.addLocale(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(Eventi evento, BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            evento.add(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rimuove un listener.
     * <p/>
     * Rimuove lo specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void removeListener(BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Evento.removeLocale(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi unEvento) {
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fireFiltro(Eventi unEvento) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Lib.Eventi.fire(listaListener, unEvento, Filtro.class, new Filtro());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Azione di modifica del popup filtri.
     */
    public class AzionePopup extends AzAdapterItem {


        @Override public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                fireFiltro(Evento.popModificato);
            }// fine del blocco if
        }
    } // fine della classe interna


    /**
     * Azione di attivazione di un popup filtro.
     */
    private class AzionePopupMenu implements PopupMenuListener {

        public void popupMenuWillBecomeVisible(PopupMenuEvent event) {
//            System.out.print("popup menu will become visible\n");
        }


        public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
//            System.out.print("popup menu will become invisible\n");
        }


        public void popupMenuCanceled(PopupMenuEvent event) {
//            System.out.print("popup menu canceled\n");
        }

    } // fine della classe interna


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che possono essere lanciati dalla status bar <br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        /* qualsiasi cosa accade nel campo */
        popModificato(PortaleListaPopLis.class,
                PortaleListaPopEve.class,
                PortaleListaPopAz.class,
                "portaleListaPopAz");


        /**
         * interfaccia listener per l'evento
         */
        private Class listener;

        /**
         * classe evento
         */
        private Class evento;

        /**
         * classe azione
         */
        private Class azione;

        /**
         * metodo
         */
        private String metodo;


        /**
         * Costruttore completo con parametri.
         *
         * @param listener interfaccia
         * @param evento classe
         * @param azione classe
         * @param metodo nome metodo azione
         */
        Evento(Class listener, Class evento, Class azione, String metodo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setListener(listener);
                this.setEvento(evento);
                this.setAzione(azione);
                this.setMetodo(metodo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public static void addLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public static void removeLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public void add(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public void remove(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        public Class getListener() {
            return listener;
        }


        private void setListener(Class listener) {
            this.listener = listener;
        }


        public Class getEvento() {
            return evento;
        }


        private void setEvento(Class evento) {
            this.evento = evento;
        }


        public Class getAzione() {
            return azione;
        }


        private void setAzione(Class azione) {
            this.azione = azione;
        }


        public String getMetodo() {
            return metodo;
        }


        private void setMetodo(String metodo) {
            this.metodo = metodo;
        }


    }// fine della classe


    /**
     * Layout manager della Status Bar della lista.
     * </p>
     *
     * @author Guido Andrea Ceresa, Alessandro Valbonesi
     * @author alex
     * @version 1.0    / 21-feb-2005 ore 13.30.12
     */
    private final class LayoutStatusBar extends BorderLayout {

        /**
         * Status bar di riferimento
         */
        private ListaStatusBar statusBar = null;


        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param sb la status bar di riferimento
         */
        public LayoutStatusBar(ListaStatusBar sb) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setStatusBar(sb);

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
        }// fine del metodo inizia


        /**
         * Ritorna la dimensione preferita della Lista.
         * <p/>
         * La larghezza e' la larghezza della lista.<br>
         * L'altezza viene calcolata in base al contenuto.<br>
         *
         * @return la dimensione preferita della Status Bar
         */
        public Dimension preferredLayoutSize(Container cont) {
            /* variabili e costanti locali di lavoro */
            Dimension dim = null;
            int h;
            int w;

            try {    // prova ad eseguire il codice

                /* non devo chiamare getPreferredSize() della lista, se no va in loop!
                 * qui mi basta la dimensione attuale della lista.*/
                w = this.getStatusBar().getLista().getListaBase().getSize().width;
                h = super.preferredLayoutSize(cont).height;
                dim = new Dimension(w, h);

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return dim;
        }


        private ListaStatusBar getStatusBar() {
            return statusBar;
        }


        private void setStatusBar(ListaStatusBar statusBar) {
            this.statusBar = statusBar;
        }

    }// fine della classe


}// fine della classe
