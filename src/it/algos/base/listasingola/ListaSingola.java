/**
 * Title:     ListaSingola
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      02.03.2006
 */
package it.algos.base.listasingola;

import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.listasingola.*;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloBase;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Implementazione di un pannello grafico contenente una lista a colonna singola
 * inserita in uno scorrevole.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-feb-2006 ore 22.56.29
 */
public final class ListaSingola extends PannelloBase {

    /* modello dati della lista */
    private ListaSingolaModello modello;

    /* lista di tipo JList */
    private JList lista;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public ListaSingola() {

        /* rimanda al costruttore della classe */
        this(null);

    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param modello il modello dati per l'albero
     */
    public ListaSingola(ListaSingolaModello modello) {

        /* rimanda al costruttore della superclasse */
        super();

        this.setModello(modello);

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
        JList lista;

        try { // prova ad eseguire il codice

            if (this.getModello() == null) {
                this.setModello(new ListaSingolaModello());
            }// fine del blocco if

            /* lista dei propri eventi */
            this.setListaListener(new EventListenerList());

            /* regola il layout */
            this.setLayout(new BorderLayout());

            /* crea e registra la JList */
            lista = new JList();
            this.setLista(lista);

            /* aggiunge un Selection Listener alla JList */
            this.getLista().addListSelectionListener(new SelectionListener());

            /* regola la JList */
            /* se non la faccio opaco si vede la differenza tra sfondo e testi */
            lista.setOpaque(true);

            /* assegna il modello dati alla JList */
            lista.setModel(this.getModello());

            /* inserisce la lista in uno scorrevole */
            JScrollPane scorrevole = new JScrollPane(lista);

            /* inserisce lo scorrevole in questo pannello */
            this.add(scorrevole);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Assegna il renderer per gli elementi della lista.
     * <p/>
     *
     * @param renderer da assegnare
     */
    public void setCellRenderer(ListCellRenderer renderer) {
        this.getLista().setCellRenderer(renderer);
    }


    /**
     * Ritorna un array contenente tutti
     * gli oggetti selezionati nella lista.
     * <p/>
     *
     * @return un array contenente gli oggetti selezionati.
     */
    public ArrayList<Object> getOggettiSelezionati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> oggettiSelezionati = null;
        Object[] oggetti;
        JList lista;

        try {    // prova ad eseguire il codice

            oggettiSelezionati = new ArrayList<Object>();
            lista = this.getLista();
            oggetti = lista.getSelectedValues();
            for (Object oggetto : oggetti) {
                oggettiSelezionati.add(oggetto);
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return oggettiSelezionati;
    }


    /**
     * Recupera il modello dati dalla lista
     * <p/>
     *
     * @return il modello dati della lista
     */
    public ListaSingolaModello getModello() {
        return modello;
    }


    /**
     * Assegna il modello dati alla lista
     * <p/>
     *
     * @param modello il modello dati per la lista
     */
    public void setModello(ListaSingolaModello modello) {
        /* variabili e costanti locali di lavoro */
        JList lista;

        try { // prova ad eseguire il codice

            /* assegna il modello */
            this.modello = modello;

            /* aggiunge il listener dei dati al modello */
            if (this.modello != null) {
                this.modello.addListDataListener(new DataListener());
            }// fine del blocco if

            /**
             * Se esiste gia' il componente grafico JList
             * gli assegna il nuovo modello
             */
            lista = this.getLista();
            if (lista != null) {
                lista.setModel(modello);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Inserisce i valori nel modello della lista.
     * <p/>
     *
     * @param valori da inserire
     */
    public void setValori(Object[] valori) {
        /* variabili e costanti locali di lavoro */
        ListaSingolaModello modello;

        try { // prova ad eseguire il codice
            modello = this.getModello();

            modello.removeAllElements();

            for (Object valore : valori) {
                modello.addElement(valore);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @param listener da aggiungere
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
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * E' responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi unEvento) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Lib.Eventi.fire(listaListener, unEvento, ListaSingola.class, this);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public int getSelectedIndex() {
        return this.getLista().getSelectedIndex();
    }


    public JList getLista() {
        return lista;
    }


    private void setLista(JList lista) {
        this.lista = lista;
    }


    public EventListenerList getListaListener() {
        return listaListener;
    }


    private void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    /**
     * Listener per la modifica dei dati nel modello.
     * </p>
     */
    private final class DataListener implements ListDataListener {

        public void contentsChanged(ListDataEvent e) {
            fire(ListaSingola.Evento.datiModificati);
        }


        public void intervalAdded(ListDataEvent e) {
            fire(ListaSingola.Evento.datiModificati);
        }


        public void intervalRemoved(ListDataEvent e) {
            fire(ListaSingola.Evento.datiModificati);
        }

    } // fine della classe 'interna'


    /**
     * Listener per la modifica della selezione nella lista.
     * </p>
     */
    private final class SelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                fire(ListaSingola.Evento.selezioneModificata);
            }// fine del blocco if
        }

    } // fine della classe 'interna'


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che possono essere lanciati dalla lista<br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        datiModificati(ListaDatiModLis.class,
                ListaDatiModEve.class,
                ListaDatiModAz.class,
                "listaDatiModAz"),
        selezioneModificata(ListaSelModLis.class,
                ListaSelModEve.class,
                ListaSelModAz.class,
                "listaSelModAz");


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


}// fine della classe
