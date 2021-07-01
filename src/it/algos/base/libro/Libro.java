/**
 * Title:     Libro
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      5-ago-2004
 */
package it.algos.base.libro;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.form.Form;
import it.algos.base.libreria.Libreria;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.PannelloBase;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Contenitore per le pagine della Scheda
 * <p/>
 * Questa classe: <ul>
 * <li> Rappresenta il contenitore delle Pagine della Scheda <br>
 * <li> Una istanza di Libro si trova sempre all'interno di una Scheda</li>
 * <li> Se le Pagine sono piu' di una, le inserisce in un JTabbedPane</li>
 * <li> Se e' una sola Pagina, la inserisce in un JPanel</li>
 * <li> In ogni caso, si presenta all'esterno come un JPanel</li>
 * <li> Il Libro e' trasparente, lo sfondo visualizzato e' quello del primo<br>
 * contenitore non trasparente nella gerarchia dei contenitori</li>
 * </ul>
 * <br>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 5-ago-2004 ore 11.41.15
 */
public class Libro extends PannelloBase {

    /* usata in fase di sviluppo per vedere l'oggetto facilmente */
    protected static final boolean DEBUG = false;

    /**
     * Collezione delle pagine contenute nel Libro (oggetti Pagina)
     */
    private LinkedHashMap<String, Pagina> pagine = null;

    /**
     * contenitore interno della pagina nel caso di piu' pagine
     */
    private JTabbedPane pannelloTab = null;

    /**
     * dialogo proprietario di questo libro
     */
    private Form form = null;


    /**
     * Costruttore completo senza parametri.
     */
    public Libro() {
        /* rimanda al costruttore della superclasse */
        super();

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
        /* variabili e costanti locali di lavoro */
        JTabbedPane pane;

        try { // prova ad eseguire il codice

            /* crea la collezione delle Pagine */
            this.setPagine(new LinkedHashMap<String, Pagina>());

            /* assegna il layout al libro */
            this.setLayout(new LayoutLibro(this));

            /* crea il contenitore da usare quando ci sono piu' pagine */
            pane = new JTabbedPane();
            this.setPannelloTab(pane);
            pane.setFocusable(false);
            pane.setOpaque(false);

            // questo è un colore "trasparente"
            // così funziona anche su Aqua
            // (ma controlla solo il background dei tab e non delle aree)
            // bug di JTabbedPane 
            // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4685800
            pane.setBackground(new Color(0, true));

            /* registra un nuovo ascoltatore per intercettare i cambiamenti
            * di pagina (classe interna) */
            pane.addChangeListener(new TabChangeListener(this));

            /* il Libro e' trasparente */
            this.setOpaque(false);

            /* se e' attivo il debug usa sfondo, bordi e colori */
            if (DEBUG) {
                this.getPannelloTab().setOpaque(true);
                this.getPannelloTab().setBackground(Color.orange);
                this.setOpaque(true);
                this.setBackground(Color.pink);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

//        this.setFocusable(false);

    }// fine del metodo inizia


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    public void avvia() {
        try {    // prova ad eseguire il codice

            /* impagina il Libro (aggiunge il contenitore con le pagine)*/
            this.impagina();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo avvia


    /**
     * Crea una singola pagina. <br>
     * <p/>
     * Crea la pagina; aggiunge un elenco di Componenti <br>
     *
     * @param titolo titolo del tabulatore della pagina e chiave della
     * Pagina nella collezione pagine del Libro<br>
     * @param componenti oggetti da disporre nella pagina; puo' essere:
     * - Component - un singolo componente (Campo od altro)
     * - String - un set, un nome di campo singolo, una lista di nomi
     * - ArrayList - di nomi, di oggetti Campo, di oggetti pannello Campo <br>
     *
     * @return la pagina creata
     */
    private Pagina creaPagina(String titolo, Object componenti) {
        /* variabili e costanti locali di lavoro */
        Pagina unaPagina = null;

        try {    // prova ad eseguire il codice

            /* crea la nuova pagina */
            unaPagina = new Pagina(titolo, this);

            /* assegna alla pagina il contenitore campi di riferimento */
            unaPagina.setContenitoreCampi(this.getForm());

            /* Aggiunge i campi alla pagina appena creata */
            if (componenti != null) {
                unaPagina.add(componenti);
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaPagina;
    } /* fine del metodo */


    /**
     * Aggiunge una singola pagina al libro.
     * <br>
     * Crea una nuova pagina con i componenti <br>
     * Aggiunge la pagina alle pagine gia' esistenti nel libro <br>
     *
     * @param titolo titolo del tabulatore della pagina e chiave della
     * Pagina nella collezione pagine del Libro<br>
     * @param componenti oggetti da disporre nella pagina; puo' essere:
     * Component - un singolo componente (Campo od altro)
     * String - un set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di oggetti pannello Campo <br>
     *
     * @return la posizione della pagina appena creata nella tabbed pane <br>
     */
    public Pagina addPagina(String titolo, Object componenti) {
        /* variabili e costanti locali di lavoro */
        Pagina unaPagina = null;

        try {    // prova ad eseguire il codice

            /* crea una nuova pagina con i componenti*/
            unaPagina = this.creaPagina(titolo, componenti);

            /*
             * aggiunge la Pagina alla collezione del Libro.
             * usa il titolo della Pagina come chiave
             * e l'oggetto Pagina come valore
             */
            this.getPagine().put(unaPagina.getTitolo(), unaPagina);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unaPagina;
    }


    /**
     * Aggiunge una singola pagina vuota al libro.
     * <br>
     * Aggiunge la pagina alle pagine gia' esistenti nel libro <br>
     *
     * @param titolo titolo del tabulatore della pagina e chiave della
     * Pagina nella collezione pagine del Libro<br>
     *
     * @return la posizione della pagina appena creata nella tabbed pane <br>
     */
    public Pagina addPagina(String titolo) {
        /* valore di ritorno */
        return this.addPagina(titolo, null);
    }


    /**
     * Esegue la reimpaginazione di questo Libro. <br>
     * Svuota i contenitori delle pagine
     * Se contiene una sola Pagina, la dispone nel JPanel<br>
     * Se contiene piu' Pagine, le dispone nel JTabbedPane<br>
     * Aggiunge al Libro il contenitore di pagine appropriato
     * (JPanel o JTabbedPane)<br>
     *
     * @return true se riuscito
     */
    private boolean impagina() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        JTabbedPane pannelloTab;
        Pagina pagina;
        int quantePag;
        int numPagina;
        String titolo;

        try {    // prova ad eseguire il codice

            /* recupera il pannello tab */
            pannelloTab = this.getPannelloTab();

            /* memorizza il corrente numero di pagina */
            numPagina = this.getNumeroPagina();

            /* svuota il pannello tab */
            pannelloTab.removeAll();

            /* svuota il Libro */
            this.removeAll();

            quantePag = this.getQuantePagine();

            if (quantePag > 0) {

                /* se e' una pagina sola la aggiunge direttamente al libro
                 * se sono piu' pagine le aggiunge al pannello tab */
                if (this.getQuantePagine() == 1) {

                    /* recupera la prima e unica pagina */
                    pagina = this.getPaginaSingola();

                    /* aggiunge la pagina al Libro */
                    this.add(pagina);

                    /* invalida la pagina, altrimenti il libro non riporta
                     * le dimensioni corrette */
                    pagina.invalidate();

                } else {

                    /* aggiunge le pagine al pannello tab */
                    for (Pagina pag : this.getPagine().values()) {

                        /* recupera il titolo */
                        titolo = pag.getTitolo();

                        /* la aggiunge al pannello tab */
                        pannelloTab.add(pag);
                        pannelloTab.setTitleAt(pannelloTab.getTabCount() - 1, titolo);

                        /* invalida la pagina, altrimenti il libro non riporta
                         * le dimensioni corrette */
                        pag.invalidate();

                    }

                    /* aggiunge il pannello tab al Libro */
                    this.add(pannelloTab);

                }// fine del blocco if-else

            }// fine del blocco if

            /* ripristina la corrente pagina del tab */
            if (numPagina >= 0) {
                if (this.getQuantePagine() > numPagina) {
                    this.vaiPagina(numPagina);
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Ritorna il numero di pagine in questo Libro. <br>
     *
     * @return il numero di pagine nel Libro
     */
    private int getQuantePagine() {
        /* variabili e costanti locali di lavoro */
        int quantePagine = 0;

        if (this.getPagine() != null) {
            quantePagine = this.getPagine().size();
        }// fine del blocco if

        /* valore di ritorno */
        return quantePagine;
    } // fine del metodo


    /**
     * Ritorna il numero della pagina correntemente visualizzata.
     * <p/>
     *
     * @return il numero della pagina correntemente visualizzata
     *         (0 per la prima)
     */
    public int getNumeroPagina() {
        /* variabili e costanti locali di lavoro */
        int pagina = 0;
        int quantePagine;
        JTabbedPane pannelloTab;

        try {    // prova ad eseguire il codice
            quantePagine = this.getQuantePagine();
            if (quantePagine > 1) {
                pannelloTab = this.getPannelloTab();
                if (pannelloTab != null) {
                    pagina = pannelloTab.getSelectedIndex();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pagina;
    }


    /**
     * Visualizza una data pagina del Libro.
     * <p/>
     *
     * @param pagina numero della pagina (0 per la prima)
     */
    public void vaiPagina(int pagina) {
        /* variabili e costanti locali di lavoro */
        int quantePagine;

        try {    // prova ad eseguire il codice
            quantePagine = this.getQuantePagine();
            if (quantePagine > 1) {
                if (pagina < quantePagine) {
                    this.getPannelloTab().setSelectedIndex(pagina);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la prima e unica pagina della collezione.
     * <p/>
     *
     * @return la prima e unica pagina
     */
    private Pagina getPaginaSingola() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;
        LinkedHashMap pagine;
        List listaPagine;

        try {    // prova ad eseguire il codice
            pagine = this.getPagine();
            listaPagine = Libreria.hashMapToArrayList(pagine);
            if (listaPagine.size() > 0) {
                pagina = (Pagina)listaPagine.get(0);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pagina;
    }


    /**
     * Ritorna la pagina corrente.
     * <p/>
     *
     * @return la pagina corrente
     */
    public Pagina getPaginaCorrente() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;
        int num;
        Map collezione;
        List lista;

        try {    // prova ad eseguire il codice
            num = this.getNumeroPagina();
            collezione = this.getPagine();
            lista = Libreria.mapToList(collezione);
            if (num >= 0) {
                if (lista.size() > num) {
                    pagina = (Pagina)lista.get(num);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pagina;
    }


    /**
     * Ritorna una pagina del Libro.
     * <p/>
     *
     * @param numero il numero della pagina (0 per la prima)
     *
     * @return la pagina richiesta
     */
    public Pagina getPagina(int numero) {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;
        int num;
        Map collezione;
        List lista;

        try {    // prova ad eseguire il codice
            num = numero;
            collezione = this.getPagine();
            lista = Libreria.mapToList(collezione);
            if (num >= 0) {
                if (lista.size() > num) {
                    pagina = (Pagina)lista.get(num);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pagina;
    }


    /**
     * Posizionamento del cursore sul campo della pagina corrente
     * individuato dalla posizione.
     * <p/>
     * Recupera la pagina corrente <br>
     * Recupera il nome del campo dal numero <br>
     * Invoca il metodo sovrascritoo <br>
     *
     * @param pos posizione nella lista dei soli campi della pagina corrente
     * 0 per il primo campo
     */
    public void posizionaCursore(int pos) {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Pagina pag;

        try { // prova ad eseguire il codice

            pag = this.getPaginaCorrente();
            if (pag != null) {
                campo = pag.getCampoPannello(pos);
                if (campo != null) {
                    campo.grabFocus();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Abilita o disabilita il componente.
     * <p/>
     *
     * @param flag per abilitare o disabilitare
     */
    public void setAbilitato(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JTabbedPane pannelloTab;

        try {    // prova ad eseguire il codice

            /* abilita/disabilita il tabbed pane */
            pannelloTab = this.getPannelloTab();
            if (pannelloTab != null) {
                pannelloTab.setEnabled(flag);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public JTabbedPane getPannelloTab() {
        return pannelloTab;
    }


    private void setPannelloTab(JTabbedPane pannelloTab) {
        this.pannelloTab = pannelloTab;
    }


    private LinkedHashMap<String, Pagina> getPagine() {
        return pagine;
    }


    /**
     * Ritorna le pagine contenute nel libro.
     * <p/>
     *
     * @return le pagine del libro
     */
    public Pagina[] getPagineLibro() {
        /* variabili e costanti locali di lavoro */
        Pagina[] pagine = null;
        ArrayList listaPagine;
        Pagina pagina;

        try {    // prova ad eseguire il codice
            listaPagine = Libreria.hashMapToArrayList(this.getPagine());
            pagine = new Pagina[listaPagine.size()];
            for (int k = 0; k < listaPagine.size(); k++) {
                pagina = (Pagina)listaPagine.get(k);
                pagine[k] = pagina;
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pagine;
    }


    /**
     * Controlla se il libro e' vuoto.
     * <p/>
     * Il Libro e' vuoto se tutte le pagine non hanno
     * inserito alcun componente.
     *
     * @return true se il Libro e' vuoto
     */
    public boolean isVuoto() {
        /* variabili e costanti locali di lavoro */
        boolean vuoto = true;
        Pagina[] pagine;

        try {    // prova ad eseguire il codice
            pagine = this.getPagineLibro();

            for (Pagina pag : pagine) {
                if (!pag.isVuota()) {
                    vuoto = false;
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return vuoto;
    }


    /**
     * Regola il bordo di tutte le pagine.
     * <p/>
     *
     * @param bordo da applicare a tutte le pagine
     */
    public void setBordoPagine(Border bordo) {
        /* variabili e costanti locali di lavoro */
        Pagina[] pagine;

        try {    // prova ad eseguire il codice

            pagine = this.getPagineLibro();
            if (pagine != null) {
                for (Pagina pagina : pagine) {
                    pagina.setBorder(bordo);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    private void setPagine(LinkedHashMap<String, Pagina> pagine) {
        this.pagine = pagine;
    }


    public Form getForm() {
        return form;
    }


    public void setForm(Form form) {
        this.form = form;
    }


    /**
     * Ascoltatore degli eventi nel JTabbedPane del Libro.
     * <p/>
     * Classe interna<br>
     *
     * @author Guido Andrea Ceresa, Alessandro Valbonesi
     * @author alex
     * @version 1.0    / 8-lug-2005 ore 00.24.15
     */
    private class TabChangeListener implements ChangeListener {

        /* Libro che contiene il JTabbedPane ascoltato */
        private Libro libro = null;


        /**
         * Costruttore completo.
         * <p/>
         *
         * @param libro di riferimento
         */
        private TabChangeListener(Libro libro) {
            super();
            this.setLibro(libro);
        }


        /**
         * Metodo invocato ad ogni cambiamento.
         * <p/>
         *
         * @param e evento
         */
        public void stateChanged(ChangeEvent e) {
            try { // prova ad eseguire il codice

                /* posiziona il fuoco sul primo campo della pagina corrente */
                this.getLibro().posizionaCursore(0);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        private Libro getLibro() {
            return libro;
        }


        private void setLibro(Libro libro) {
            this.libro = libro;
        }

    }// fine della classe


    /**
     * Classe interna - layout manager del libro.
     * </p>
     * Il layout del libro e' un BorderLayout. <br>
     * La differenza e' che vengono trascurate le dimensioni delle eventuali
     * pagine Programmatore.
     *
     * @author Guido Andrea Ceresa, Alessandro Valbonesi
     * @author alex
     * @version 1.0    / 04-02-2005 ore 17.05.26
     */
    private class LayoutLibro extends BorderLayout {

        /**
         * Libro di riferimento
         */
        private Libro libro = null;

        /**
         * Elenco delle pagine da nascondere
         */
        List<Pagina> pagineNascoste = null;


        /**
         * Costruttore completo.
         * <p/>
         * Indispensabile anche se non viene utilizzato
         * (anche solo per compilazione in sviluppo) <br>
         *
         * @param libro il Libro di riferimento.
         */
        public LayoutLibro(Libro libro) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setLibro(libro);

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
            this.setPagineNascoste(new ArrayList<Pagina>());
        }// fine del metodo inizia


        /**
         * Ritorna la dimensione preferita del Libro.
         * <p/>
         *
         * @param contenitore il contenitore
         */
        public Dimension preferredLayoutSize(Container contenitore) {
            /* variabili e costanti locali di lavoro */
            Dimension dim = null;

            try { // prova ad eseguire il codice
                this.nascondiPagine();
                dim = super.preferredLayoutSize(contenitore);
                this.ripristinaPagine();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dim;

        }


        /**
         * Ritorna la dimensione minima del Libro.
         * <p/>
         *
         * @param contenitore il contenitore
         */
        public Dimension minimumLayoutSize(Container contenitore) {
            /* variabili e costanti locali di lavoro */
            Dimension dim = null;

            try { // prova ad eseguire il codice
                this.nascondiPagine();
                dim = super.minimumLayoutSize(contenitore);
                this.ripristinaPagine();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dim;

        }


        /**
         * Ritorna la massima minima del Libro.
         * <p/>
         *
         * @param contenitore il contenitore
         */
        public Dimension maximumLayoutSize(Container contenitore) {
            /* variabili e costanti locali di lavoro */
            Dimension dim = null;

            try { // prova ad eseguire il codice
                this.nascondiPagine();
                dim = super.maximumLayoutSize(contenitore);
                this.ripristinaPagine();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dim;

        }


        /**
         * Fissa a zero la dimensione delle eventuali pagine Programmatore.
         * <p/>
         */
        private void nascondiPagine() {
            /* variabili e costanti locali di lavoro */
            Libro libro;
            List<Pagina> pagineNascoste;
            HashMap<String, Pagina> mappaPagine;

            try {    // prova ad eseguire il codice
                pagineNascoste = this.getPagineNascoste();
                pagineNascoste.clear();
                libro = this.getLibro();
                mappaPagine = libro.getPagine();
                if (mappaPagine != null) {
                    for (Pagina pag : mappaPagine.values()) {
                        if (pag.isPaginaProgrammatore()) {
                            pag.setPreferredSize(0, 0);
                            pagineNascoste.add(pag);
                        }// fine del blocco if
                    }
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Ripristina l'automatismo della dimensione delle
         * eventuali pagine Programmatore.
         * <p/>
         */
        private void ripristinaPagine() {
            /* variabili e costanti locali di lavoro */
            List<Pagina> pagine;

            try {    // prova ad eseguire il codice
                pagine = this.getPagineNascoste();
                for (Pagina pag : pagine) {
                    pag.setPreferredSize(null);
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

        }


        private Libro getLibro() {
            return this.libro;
        }


        private void setLibro(Libro libro) {
            this.libro = libro;
        }


        private List<Pagina> getPagineNascoste() {
            return pagineNascoste;
        }


        private void setPagineNascoste(List<Pagina> pagineNascoste) {
            this.pagineNascoste = pagineNascoste;
        }

    }// fine della classe interna


}// fine della classe
