/**
 * Title:     Pagina
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      5-ago-2004
 */
package it.algos.base.pagina;

import it.algos.base.campo.base.Campo;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.errore.Errore;
import it.algos.base.form.Form;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutFlusso;
import it.algos.base.libreria.Lib;
import it.algos.base.libro.Libro;
import it.algos.base.modello.Modello;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.scheda.Scheda;
import it.algos.base.wrapper.WrapCampo;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * </p>
 * Questa classe: <ul>
 * Rappresenta il pannello piu' interno di una Scheda.
 * <li>Contiene i componenti visualizzati nella GUI della scheda</li>
 * <li>Una istanza di Pagina si trova sempre all'interno di un Libro</li>
 * <li>La Pagina e' trasparente, lo sfondo visualizzato e' quello del primo<br>
 * contenitore non trasparente nella gerarchia dei contenitori</li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 5-ago-2004 ore 14.38.04
 */
public final class Pagina extends PannelloFlusso {

    /**
     * disegna uno sfondo colorato per debug
     */
    protected static final boolean DEBUG = false;

    /**
     * riferimento al Libro che contiene la pagina
     */
    private Libro libro = null;

    /**
     * titolo della Pagina visualizzato nel JTabbedPane del Libro
     */
    private String titolo = "";

    /**
     * flag - identifica la pagina Programmatore
     */
    private boolean paginaProgrammatore = false;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public Pagina() {
        /* rimanda al costruttore di questa classe */
        this(null, null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param titolo titolo del tabulatore della pagina
     * @param libro libro che contiene la pagina
     */
    public Pagina(String titolo, Libro libro) {

        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setTitolo(titolo);
        this.setLibro(libro);

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
        Layout layout;
        Color sfondoForm;

        try { // prova ad eseguire il codice

            /* crea un'istanza di layout pannello */
            layout = new LayoutFlusso(this, Layout.ORIENTAMENTO_VERTICALE);

            /* la pagina tenta di ridimensionare i componenti */
            layout.setRidimensionaComponenti(true);

            /* la pagina usa l'allineamento definito nel componente */
            layout.setAllineamento(Layout.ALLINEA_DA_COMPONENTI);

            /* la pagina usa lo scorrevole */
            layout.setUsaScorrevole(true);
            layout.setScorrevoleBordato(false);

            /* gap tra i componenti nella pagina */
            layout.setUsaGapFisso(false);
            layout.setGapMinimo(10);
            layout.setGapPreferito(20);
            layout.setGapMassimo(40);

            /* assegna il layout */
            this.setLayout(layout);

            /*
             * imposta il contenitore come focus cycle root
             * la navigazione resta nell'ambito della pagina
             */
            this.setFocusCycleRoot(true);

            /* pone la dimensione minima della pagina a zero */
            Lib.Comp.sbloccaDimMin(this);

            /* assegna il margine di default */
            this.setMargine(Margine.normale);

            /* regola il colore di sfondo */
            this.regolaSfondo();
            /* la pagina e' opaca e ha lo stesso colore di fondo
             * del form che la contiene */
            this.setOpaque(true);

            sfondoForm = this.getLibro().getForm().getPanFisso().getBackground();
            this.setBackground(sfondoForm);

            /* se e' attivo il debug usa sfondo, bordi e colori */
            if (DEBUG) {
                this.setOpaque(true);
                this.setBackground(Color.blue);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia

//    /**
//     * Regolazioni di ri-avvio.
//     * <p/>
//     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
//     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
//     * Viene eseguito tutte le volte che necessita <br>
//     * <p/>
//     * Sovrascritto nelle sottoclassi
//     */
//    public void avvia() {
//    }// fine del metodo avvia


    /**
     * Regola il colore di fondo della pagina.
     * <p/>
     * la pagina e' opaca e ha lo stesso colore di fondo
     * del form che la contiene
     */
    private void regolaSfondo() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Libro libro;
        Form form = null;
        Color sfondoForm;

        try {    // prova ad eseguire il codice

            this.setOpaque(true);

            libro = this.getLibro();
            continua = (libro != null);

            if (continua) {
                form = libro.getForm();
                continua = (form != null);
            }// fine del blocco if

            if (continua) {
                sfondoForm = form.getPanFisso().getBackground();
                this.setBackground(sfondoForm);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }

//    /**
//     * Aggiunge il componente nella posizione indicata.
//     * <p/>
//     *
//     * @param componente componente da aggiungere
//     * Component - un singolo componente (Bottone od altro)
//     * Campo - un singolo Campo
//     * String - un nome di campo singolo
//     * (di solito un campo oppure il nome di un campo)
//     * @param x coordinata del componente
//     * @param y coordinata del componente
//     */
//    public void addCampo(Object componente, int x, int y) {
//        /* variabili e costanti locali di lavoro */
//        Campo campo = null;
//
//        try { // prova ad eseguire il codice
//            /* se il campo deve essere posizionato manualmente non va usato il layout pagina */
//            this.setLayout(null);
//
//            if (componente instanceof String) {
//                /* recupera il campo dalla scheda */
//                campo = this.getSchedaPannello().getCampo((String)componente);
//
//                /* regola le coordinate */
//                campo.setPosizionePannelloScheda(x, y);
//
//                /* invoca il metodo delegato */
//                this.aggiungeComponenti(campo);
//            } /* fine del blocco if */
//
//            if (componente instanceof Campo) {
//                /* regola le coordinate */
//                ((Campo)componente).setPosizionePannelloScheda(x, y);
//
//                /* invoca il metodo delegato */
//                this.aggiungeComponenti(componente);
//            } /* fine del blocco if */
//
//            if (componente instanceof Component) {
//                /* regola le coordinate */
//                ((Component)componente).setLocation(x, y);
//
//                /* invoca il metodo delegato */
//                this.aggiungeComponenti(componente);
//            } /* fine del blocco if */
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }

//    /**
//     * Recupera i pannelli campo.
//     * <br>
//     *
//     * @param campi lista di oggetti Campo
//     *
//     * @return lista dei pannelli campo <br>
//     */
//    private ArrayList estraePannelli(ArrayList campi) {
//        /* variabili e costanti locali di lavoro */
//        ArrayList unaListaPannelliCampo = null;
//        Campo unCampo = null;
//        JPanel unPannello = null;
//
//        try {	// prova ad eseguire il codice
//
//            unaListaPannelliCampo = new ArrayList();
//
//            for (int k = 0; k < campi.size(); k++) {
//                unCampo = (Campo)campi.get(k);
//                unPannello = unCampo.getPannelloVideo();
////// todo provvisorio - da mettere in Campo
//                unPannello.setPreferredSize(unPannello.getSize());
////// todo provvisorio - da mettere in Campo
//                unaListaPannelliCampo.add(unPannello);
//            } /* fine del blocco for */
//        } catch (Exception unErrore) {	// intercetta l'errore
//            /* mostra il messaggio di errore */
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return unaListaPannelliCampo;
//    } /* fine del metodo */

//    /**
//     * Recupera i campi.
//     * <br>
//     *
//     * @param nomiCampi i campi da disporre nella pagina <br>
//     *
//     * @return lista dei campi <br>
//     */
//    private ArrayList estraeCampi(ArrayList nomiCampi) {
//        /* variabili e costanti locali di lavoro */
//        ArrayList unaListaCampi = null;
//        String unNome = "";
//        Campo unCampo = null;
//
//        try {	// prova ad eseguire il codice
//
//            unaListaCampi = new ArrayList();
//
//            for (int k = 0; k < nomiCampi.size(); k++) {
//                unNome = (String)nomiCampi.get(k);
//                unCampo = this.libro.getCampo(unNome);
//                unaListaCampi.add(unCampo);
//            } /* fine del blocco for */
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            /* mostra il messaggio di errore */
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return unaListaCampi;
//    } /* fine del metodo */


    /**
     * Recupera i campi del set.
     * <br>
     * Recupera un set di campi dal Modello <br>
     * Prepara un array di campi della Scheda, individuandoli da quelli del Modello <br>
     *
     * @param nome il nome di un Set di campi <br>
     *
     * @return lista dei campi - nullo se non trovato <br>
     */
    private ArrayList estraeSet(String nome) {
        /* variabili e costanti locali di lavoro */
        ArrayList listaScheda = null;
        ArrayList listaModello = null;
        Campo campo = null;
        String nomeChiave = "";

        try {    // prova ad eseguire il codice
            listaScheda = new ArrayList();

            listaModello = this.getModello().getSetCampiScheda(nome);

            for (int k = 0; k < listaModello.size(); k++) {
                campo = (Campo)listaModello.get(k);
                nomeChiave = campo.getNomeInterno();
                campo = this.getSchedaPannello().getCampo(nomeChiave);
                listaScheda.add(campo);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return listaScheda;
    }

//    /**
//     * Recupera i nomi dei campi.
//     * <br>
//     *
//     * @param nome i campi da disporre nella pagina; puo' essere:
//     * un singolo nome di un campo,
//     * una lista di nomi separati da virgole
//     * il nome di un Set di campi <br>
//     *
//     * @return lista di nomi dei campi <br>
//     */
//    private ArrayList estraeNomiCampi(String nome) {
//        /* variabili e costanti locali di lavoro */
//        ArrayList unaListaNomi = null;
//        ArrayList unaListaCampi = null;
//        Campo unCampo = null;
//        String unNome = "";
//        String nomi = "";
//        ArrayList lista = null;
//
//        try {	// prova ad eseguire il codice
//            /* recupera gli originali set dal modello col nome */
////            unaListaCampi = this.getLibro().getScheda().get;
//
//            lista = this.getModello().getSetCampiScheda(nome);
//
//            if (unaListaCampi == null) {
//                unaListaNomi = Libreria.estrae(nome);
//            } else {
//                unaListaNomi = new ArrayList();
//                for (int k = 0; k < unaListaCampi.size(); k++) {
//                    unCampo = (Campo)unaListaCampi.get(k);
//                    unNome = unCampo.getNomeInternoCampo();
//                    unaListaNomi.add(unNome);
//                } /* fine del blocco for */
//            } /* fine del blocco if/else */
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            /* mostra il messaggio di errore */
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return unaListaNomi;
//    } /* fine del metodo */


    /**
     * Recupera i componenti.
     * <br>
     *
     * @param componenti oggetti da disporre nella pagina; puo' essere:
     * Component - un singolo componente (Campo od altro)
     * String - un nome set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo <br>
     *
     * @return lista di oggetti Campo o Component <br>
     */
    private ArrayList estraeComponenti(Object componenti) {
        /* variabili e costanti locali di lavoro */
        String unaStringaNome;
        ArrayList unaListaOggetti = null;
        ArrayList unaLista;
        ArrayList unaListaNomiCampi;
        ArrayList unaListaCampi;
        Campo unCampo;
        String unNomeCampo;

        try {    // prova ad eseguire il codice
            /* Controllo base di nullita' */
            if (componenti != null) {

                unaListaOggetti = new ArrayList();

                if (componenti instanceof String) {
                    unaStringaNome = (String)componenti;
                    unaListaCampi = this.estraeSet(unaStringaNome);

                    if (unaListaCampi != null) {
                        unaListaOggetti.addAll(unaListaCampi);
                    } else {
                        unaListaNomiCampi = Lib.Array.creaLista(unaStringaNome);

                        for (int k = 0; k < unaListaNomiCampi.size(); k++) {
                            unNomeCampo = (String)unaListaNomiCampi.get(k);
                            unCampo = this.getModello().getCampo(unNomeCampo);
                            unaListaOggetti.add(unCampo);
                        } // fine del ciclo for
                    }// fine del blocco if-else
                } /* fine del blocco if */

                if (componenti instanceof ArrayList) {
                    unaLista = (ArrayList)componenti;

                    if (unaLista.get(0) instanceof String) {
                        for (int k = 0; k < unaLista.size(); k++) {
                            unNomeCampo = (String)unaLista.get(k);
                            unCampo = this.getModello().getCampo(unNomeCampo);
                            unaListaOggetti.add(unCampo);
                        } // fine del ciclo for
                    } /* fine del blocco if */

                    if (unaLista.get(0) instanceof Campo) {
                        unaListaOggetti.addAll(unaLista);
                    } /* fine del blocco if */

                    if (unaLista.get(0) instanceof Component) {
                        unaListaOggetti.addAll(unaLista);
                    } /* fine del blocco if */
                } /* fine del blocco if */

                if (componenti instanceof Component) {
                    unaListaOggetti.add(componenti);
                } /* fine del blocco if */

                if (componenti instanceof Campo) {
                    unaListaOggetti.add(componenti);
                } /* fine del blocco if */

            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaListaOggetti;
    } /* fine del metodo */


    /**
     * Aggiunge i componenti alla pagina.
     * <br>
     * Recupera dalla lista di oggetti, i componenti <br>
     *
     * @param componenti oggetti da disporre nella pagina; puo' essere:
     * Component - un singolo componente (Campo od altro)
     * String - un set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di oggetti pannello Campo <br>
     */
    public void aggiungeComponenti(Object componenti) {
        /* variabili e costanti locali di lavoro */
        ArrayList unaListaComponenti;
        Object oggetto;
        boolean trovato;
        Campo unCampo;
        Campo unCampoScheda;
        Campo unCampoClone;
        String chiave;
        JComponent c;
        Scheda scheda;

        try {    // prova ad eseguire il codice

            /* Recupera dalla lista di oggetti, i componenti */
            unaListaComponenti = this.estraeComponenti(componenti);

            if (unaListaComponenti != null) {

                /* aggiunge alla pagina tutti i pannelli campo dei campi */
                for (int k = 0; k < unaListaComponenti.size(); k++) {
                    oggetto = unaListaComponenti.get(k);
                    trovato = false;

                    if (oggetto instanceof Campo) {
                        unCampo = (Campo)oggetto;

                        //@todo nome chiave
//                        chiave = unCampo.getChiaveCampo();
                        chiave = unCampo.getNomeInterno();

                        /* se esiste una scheda, recupera le istanze dei campi dalla scheda */
                        scheda = this.getSchedaPannello();
                        if (scheda != null) {
                            /* tenta di recuperare il campo dall scheda per vedere se c'e' gia' */
                            unCampoScheda = this.getSchedaPannello().getCampo(chiave);

                            /* se non c'e', lo aggiunge ora (come clone) alla
                             * collezione generale dei campi scheda */
                            if (unCampoScheda == null) {
                                unCampoClone = unCampo.clonaCampo();
                                HashMap collezione = this.getSchedaPannello().getCampi();
                                collezione.put(chiave, new WrapCampo(unCampoClone));

                            }// fine del blocco if

                            /* recupera il campo dalla scheda (ora c'e' per forza)*/
                            unCampo = this.getSchedaPannello().getCampo(chiave);
                        }// fine del blocco if

//                        if (unCampo.getCampoLogica() instanceof CLNavigatore) {
//                            unCampo.getCampoLogica().getNavigatore().setCampoPilota(
//                                    unCampo);
//                        }// fine del blocco if

                        /* aggiunge il pannello campo alla pagina */
                        c = unCampo.getCampoVideoNonDecorato().getPannelloBaseCampo();
                        this.add(c);

                        Component[] comti = this.getComponents();

                        trovato = true;
                    }// fine del blocco if

                    if (oggetto instanceof Component) {
                        this.add((Component)oggetto);
                        trovato = true;
                    }// fine del blocco if

                    if (!trovato) {
                        throw new Exception("componente non corretto");
                    }// fine del blocco if

                } /* fine del blocco for */
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    private Scheda getSchedaPannello() {
        /* variabili e costanti locali di lavoro */
        Scheda unaScheda = null;
        Form unForm;

        try { // prova ad eseguire il codice
            unForm = this.getLibro().getForm();
            if (unForm != null) {
                if (unForm instanceof Scheda) {
                    unaScheda = (Scheda)unForm;
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unaScheda;
    }


    public Dialogo getDialogo() {
        /* variabili e costanti locali di lavoro */
        Dialogo unDialogo = null;
        Form unForm = null;

        try { // prova ad eseguire il codice
            unForm = this.getLibro().getForm();
            if (unForm != null) {
                if (unForm instanceof Dialogo) {
                    unDialogo = (Dialogo)unForm;
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unDialogo;
    }


    /**
     * Controlla se questa pagina e' vuota.
     * <p/>
     * La pagina e' vuota se non ha inserito alcun componente.
     *
     * @return true se la pagina e' vuota
     */
    public boolean isVuota() {
        /* variabili e costanti locali di lavoro */
        boolean vuota = true;
        int numComponenti = 0;

        try {    // prova ad eseguire il codice
            numComponenti = this.getComponentCount();
            if (numComponenti > 0) {
                vuota = false;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return vuota;
    }


    public Libro getLibro() {
        return libro;
    }


    public void setLibro(Libro libro) {
        this.libro = libro;
    }


    public String getTitolo() {
        return titolo;
    }


    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    public Modello getModello() {
        return this.getNavigatore().getModulo().getModello();
    }


    public Navigatore getNavigatore() {
        return this.getSchedaPannello().getPortale().getNavigatore();
    }


    /**
     * flag - identifica la pagina Programmatore.
     * <p/>
     *
     * @return true se e' la pagina Programmatore.
     */
    public boolean isPaginaProgrammatore() {
        return paginaProgrammatore;
    }


    /**
     * flag - identifica la pagina Programmatore.
     * <p/>
     *
     * @param flag true per contrassegnare la pagina come pagina Programmatore.
     */
    public void setPaginaProgrammatore(boolean flag) {
        this.paginaProgrammatore = flag;
    }


    /**
     * Assegna il margine interno alla pagina.
     * <p/>
     *
     * @param margine da utilizzare
     */
    public void setMargine(Margine margine) {
        /* variabili e costanti locali di lavoro */
        Insets insets;
        Border bordo;
        int top;
        int left;
        int bottom;
        int right;

        try { // prova ad eseguire il codice

            insets = margine.getMargine();

            if (insets != null) {
                top = insets.top;
                left = insets.left;
                bottom = insets.bottom;
                right = insets.right;
                bordo = BorderFactory.createEmptyBorder(top, left, bottom, right);

                this.setBorder(bordo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Margine {

        normale(new Insets(20, 20, 20, 20)),
        piccolo(new Insets(6, 6, 6, 6)),
        nessuno(new Insets(0, 0, 0, 0));

        /**
         * margini
         */
        private Insets margine;


        /**
         * Costruttore completo con parametri.
         *
         * @param margine costruito da utilizzare
         */
        Margine(Insets margine) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setMargine(margine);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public Insets getMargine() {
            return margine;
        }


        private void setMargine(Insets margine) {
            this.margine = margine;
        }


    }// fine della classe

}// fine della classe
