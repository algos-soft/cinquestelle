/**
 * Title:        DialogoBase.java
 * Package:      it.algos.base.dialogo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 2 novembre 2003 alle 7.53
 */

package it.algos.base.dialogo;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.WrapTextArea;
import it.algos.base.componente.bottone.Bottone;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.componente.bottone.BottoneFactory;
import it.algos.base.costante.CostanteColore;
import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;
import it.algos.base.form.FormBase;
import it.algos.base.gestore.GestoreDialogo;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libro.Libro;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleDialogo;
import it.algos.base.pref.PrefTipo;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Gestire un dialogo <br>
 * B - Non estende direttamente la classe <code>JDialog</code>, ma mantiene un
 * riferimento interno ad un oggetto <code>JDialog</code> <br>
 * <p/>
 * Il dialogo è graficamente costituito da:
 * - un pannello contenitore principale (questo stesso oggetto)
 * che contiene tutti gli altri pannelli
 * - un pannello superiore per il messaggio
 * - un pannello centrale per i contenuti
 * - un pannello inferiore contenente:
 * - a sinistra, un pannello per eventuali comandi specifici
 * - a destra, un pannello per i bottoni
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  2 novembre 2003 ore 7.53
 */
public class DialogoBase extends FormBase implements Dialogo {

    /**
     * true per attivare il debug (colora i vari pannelli)
     */
    private static final boolean DEBUG = false;

    /**
     * tipologia dimensionamento bottoni (default)
     */
    private static final int DIMENSIONAMENTO_BOTTONI_DEFAULT = DIMENSIONE_PROPORZIONALE;

    /**
     * misura bottoni per dimensionamento fisso (default)
     */
    private static final int LARGHEZZA_BOTTONI_DEFAULT = 100;

    /**
     * voce di default
     */
    private static final String TITOLO = "Dialogo";

    /**
     * bordo interno di rispetto nei pannelli (default)
     */
    private static final int BORDO = 0;

    /**
     * colore sfondo area dialogo (messaggio)
     */
    protected static final Color SFONDO_MESSAGGIO = CostanteColore.VERDE_CHIARO_UNO;

    /**
     * colore sfondo area dialogo (contenuto)
     */
    protected static final Color SFONDO_DIALOGO = CostanteColore.VERDE_CHIARO_DUE;

    /**
     * colore sfondo area comandi
     */
    protected static final Color SFONDO_COMANDI = CostanteColore.VERDE_CHIARO_UNO;

    /**
     * colore testo messaggio in alto
     */
    protected static final Color COLORE_TESTO_MESSAGGIO = CostanteColore.BLU;

    /**
     * dialogo
     */
    protected JDialog dialogo = null;

    /**
     * pannello messaggio (superiore)
     */
    private Pannello pannelloMessaggio = null;

    /**
     * pannello contenuto (centrale)
     */
    private Pannello pannelloContenuto = null;

    /**
     * pannello comandi (inferiore)
     */
    private Pannello pannelloComandi = null;

    /**
     * pannello bottoni (inferiore)
     */
    private Pannello pannelloBottoni = null;

    /**
     * voce della finestra di dialogo
     */
    private String titolo = "";

    /**
     * eventuale testo di informazione/avviso nella parte superiore del dialogo
     */
    private String messaggio = "";

    /**
     * nome del bottone premuto (testo visibile del bottone)
     */
    private String bottonePremuto = "";

    /**
     * codifica per il dimensionamento dei bottoni
     */
    private int dimensionamentoBottoni = 0;

    /**
     * larghezza dei bottoni, se regolata dall'esterno
     */
    private int larghezzaBottoni = 0;

    /**
     * collezione dei bottoni del dialogo
     */
    private LinkedHashMap<String, BottoneDialogo> bottoni = null;

    /**
     * flag - dialogo confermato o annullato
     */
    private boolean confermato = false;

    /**
     * bordo interno di rispetto nei pannelli
     */
    private int bordo = 0;

    /**
     * gestore specifco delle azioni
     */
    private GestoreDialogo gestore;

    /**
     * bottone annulla
     */
    private BottoneDialogo bottoneAnnulla;

    /**
     * bottone conferma
     */
    private BottoneDialogo bottoneConferma;

    /**
     * bottone cancella
     */
    private BottoneDialogo bottoneCancella;

    /**
     * bottone registra
     */
    private BottoneDialogo bottoneRegistra;

    /**
     * bottone chiude
     */
    private BottoneDialogo bottoneChiude;

    /**
     * bottone stampa
     */
    private BottoneDialogo bottoneStampa;

    /**
     * ordinamento fiso dei bottoni
     */
    private boolean ordineFisso;


    /**
     * Costruttore base senza parametri
     * <p/>
     */
    public DialogoBase() {
        /* invoca il costruttore coi parametri */
        this((Modulo)null);
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce un dialogo con modulo
     *
     * @param modulo di riferimento
     */
    public DialogoBase(Modulo modulo) {
        /* invoca il costruttore coi parametri */
        this(modulo, "");
    } /* fine del metodo costruttore  */


    /**
     * Costruttore con parametri.
     * <p/>
     *
     * @param titolo della finestra del dialogo
     */
    public DialogoBase(String titolo) {
        /* invoca il costruttore coi parametri */
        this(null, titolo);
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo.
     * <p/>
     * Costruisce un dialogo con modulo
     *
     * @param modulo di riferimento
     * @param titolo della finestra del dialogo
     */
    public DialogoBase(Modulo modulo, String titolo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try {
            /* regola le variabili di istanza coi parametri */
            this.setTitolo(titolo);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione <br>
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Pagina primaPagina;
        int b;
        JDialog dialogo;
        String titolo;
        Pannello pan;
        PortaleDialogo portale;
        Border bordo;

        try { // prova ad eseguire il codice

            /* gestore delle azioni */
            this.setGestore(new GestoreDialogo(this));

            /* si crea un portale */
            portale = new PortaleDialogo(this);
            this.setPortale(portale);

            /* regolazioni di default */
            this.setDimensionamentoBottoni(DIMENSIONAMENTO_BOTTONI_DEFAULT);
            this.setLarghezzaBottoni(LARGHEZZA_BOTTONI_DEFAULT);
            this.setBordo(BORDO);

            /* regolazioni del layout
             * va direttamente al layout perche' i metodi sono sovrascritti
             * in FormBase per comodita' */
            this.getLayoutAlgos().setUsaScorrevole(false);
            this.getLayoutAlgos().setUsaGapFisso(true);
            this.getLayoutAlgos().setGapPreferito(0);
            this.getLayoutAlgos().setRidimensionaComponenti(true);
            this.setOpaque(false);

            b = this.getBordo();
            this.setBorder(BorderFactory.createEmptyBorder(b, b, b, b));

            /* rimuove tutti gli eventuali componenti gia' esistenti
             * (essendo un Form, avrebbe gia' il libro graficamente
             * inserito di default) */
            this.removeAll();

            /* crea il pannello messaggio e lo aggiunge */
            pan = this.creaPannelloMessaggio();
            if (pan != null) {
                this.setPannelloMessaggio(pan);
                this.add(pan);
                if (DEBUG) {
                    pan.getPanFisso().setOpaque(true);
                    pan.getPanFisso().setBackground(Color.YELLOW);
                }// fine del blocco if
            }// fine del blocco if

            /* crea il pannello contenuti e lo aggiunge*/
            pan = this.creaPannelloContenuti();
            if (pan != null) {
                this.setPannelloContenuto(pan);
                this.add(pan);
                if (DEBUG) {
                    pan.getPanFisso().setOpaque(true);
                    pan.getPanFisso().setBackground(Color.RED);
                }// fine del blocco if
            }// fine del blocco if

            /* crea il pannello comandi e lo aggiunge*/
            pan = this.creaPannelloComandi();
            if (pan != null) {
                this.setPannelloComandi(pan);
//                this.add(pan);
                if (DEBUG) {
                    pan.getPanFisso().setOpaque(true);
                    pan.getPanFisso().setBackground(Color.CYAN);
                }// fine del blocco if
            }// fine del blocco if

            /* crea il pannello bottoni e lo aggiunge*/
            pan = this.creaPannelloBottoni();
            if (pan != null) {
                this.setPannelloBottoni(pan);
//                this.add(pan);
                if (DEBUG) {
                    pan.getPanFisso().setOpaque(true);
                    pan.getPanFisso().setBackground(Color.GREEN);
                }// fine del blocco if
            }// fine del blocco if

            /* crea il pannello inferiore con pannello comandi e bottoni
             * e lo aggiunge */
            PannelloFlusso panInf = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panInf.setUsaGapFisso(false);
            panInf.setGapPreferito(0);
            panInf.setGapMinimo(0);
            panInf.setAllineamento(Layout.ALLINEA_CENTRO);
            bordo = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            panInf.setBackground(SFONDO_COMANDI);
            panInf.setOpaque(true);
            panInf.getPanFisso().setBorder(bordo);
            panInf.add(this.getPannelloComandi());
            panInf.add(this.getPannelloBottoni());
            this.add(panInf);

            /* aggiunge una prima pagina di default */
            primaPagina = this.addPagina("generale", null);
            primaPagina.setBackground(SFONDO_DIALOGO);

            /* crea una collezione per contenere i bottoni */
            this.setBottoni(new LinkedHashMap());

            /* crea l'istanza del dialogo */
            titolo = this.getTitolo();
            dialogo = new JDialog(new JFrame(), titolo, true);

            /* normalmente la finestra dialogo non puo' essere ridimensionata */
            this.setResizable(false);

            /* regola la risposta alla chiusura della finestra (non fa nulla) */
            dialogo.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            this.setDialogo(dialogo);

            /* ordinamento fisso dei bottoni (default) */
            this.setOrdineFisso(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


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
        /* variabili e costanti locali di lavoro */
        JDialog dialogo;
        String titolo;

        try { // prova ad eseguire il codice

            if (!this.isInizializzato()) {

                /* inizializza nella superclasse */
                super.inizializza();

                /* registra nei campi il valore di backup */
                for (Campo campo : this.getCampi().values()) {
                    campo.setValoreIniziale(campo.getValore());
                }

                /* recupera il JDialog */
                dialogo = this.getDialogo();

                /* regola il titolo della finestra */
                titolo = this.getTitolo();
                if (Lib.Testo.isVuota(titolo)) {
                    titolo = this.setTitolo(TITOLO);
                }// fine del blocco if

                dialogo.setTitle(titolo);

                this.getPortale().inizializza();

//                /* inizializza il Libro */
//                this.getLibro().inizializza();

                /* regola tutti i pannelli del dialogo (inserisce i contenuti) */
                this.regolaPannelli();

                this.regolaBottoni();

                /* aggiunge il pannello principale al dialogo */
                dialogo.getContentPane().removeAll();
                dialogo.getContentPane().add(this.getPanFisso());

                /* associa i bottoni standard ai tasti */
                this.regolaAzioni();

                /* marca come inizializzato */
                this.setInizializzato(true);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */



    /**
     * Avvia e rende visibile.
     * <p/>
     */
    public void avvia() {

        try { // prova ad eseguire il codice

            this.avvia(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo avvia



    /**
     * Avvia e rende o meno visibile.
     * <p/>
     * @param rendiVisibile per rendere visibile il dialogo
     */
    public void avvia(boolean rendiVisibile) {

        try { // prova ad eseguire il codice

            /* inizializza il dialogo (se non già inizializzato)*/
            if (!this.isInizializzato()) {
                this.inizializza();
            }// fine del blocco if

            /* rimanda alla superclasse */
            super.avvia();

            this.getPortale().avvia();

            /* ridisegna il dialogo */
            this.getDialogo().pack();

            /* accende il flag di attivazione */
            this.setAttivo(true);

            /* rende il dialogo visibile */
            if (rendiVisibile) {
                this.rendiVisibile();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo avvia


    /**
     * Assegno i tasti ai bottoni standard.
     * <p/>
     * Escape <br>
     * Enter <br>
     */
    protected void regolaAzioni() {
        /* variabili e costanti locali di lavoro */
        JButton bottone;
//        InputMap iMap;
//        KeyStroke ks;
//        String chiave = "chiave";
        Action azioneJava;

        try { // prova ad eseguire il codice

            bottone = this.getBottoneAnnulla();
            if (bottone != null) {
                azioneJava = bottone.getAction();
                Lib.Risorse.addTasto(bottone, KeyEvent.VK_ESCAPE, azioneJava);
            }// fine del blocco if

            bottone = this.getBottoneConferma();
            if (bottone != null) {
                azioneJava = bottone.getAction();
                Lib.Risorse.addTasto(bottone, KeyEvent.VK_ENTER, azioneJava);
            }// fine del blocco if

            bottone = this.getBottoneRegistra();
            if (bottone != null) {
                azioneJava = bottone.getAction();
                Lib.Risorse.addTasto(bottone, KeyEvent.VK_ENTER, azioneJava);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }




    /**
     * Rende il dialogo visibile.
     * <p/>
     * Può essere sovrascritto per effettuare operazioni
     * subito prima che il dialogo diventi visibile
     */
    public void rendiVisibile() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            this.vaiCampoPrimo();
            this.getDialogo().pack();
            Lib.Gui.fitWindowToScreen(this.getDialogo());
            Lib.Gui.centraWindow(this.getDialogo(), null);
            this.getDialogo().setVisible(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna la finestra di questo dialogo.
     * <p/>
     *
     * @return la finestra del dialogo
     */
    private Finestra getFinestra() {
        /* variabili e costanti locali di lavoro */
        Portale portale;
        Finestra finestra = null;

        try {    // prova ad eseguire il codice
            portale = this.getPortale();
            if (portale != null) {
                finestra = portale.getFinestra();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return finestra;
    }


    /**
     * Sincronizzazione della scheda/dialogo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean abilita;
        BottoneDialogo bottoneCancella;
        BottoneDialogo bottoneConferma;
        BottoneDialogo bottoneRegistra;

        try { // prova ad eseguire il codice

            bottoneCancella = this.getBottoneCancella();
            bottoneConferma = this.getBottoneConferma();
            bottoneRegistra = this.getBottoneRegistra();

            abilita = this.isRegistrabile();

            if (bottoneCancella != null) {
                bottoneCancella.setEnabled(this.isCancellabile());
            }// fine del blocco if

            if (bottoneConferma != null) {
                bottoneConferma.setEnabled(abilita);
            }// fine del blocco if

            if (bottoneRegistra != null) {
                bottoneRegistra.setEnabled(abilita);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il pannello superiore della finestra.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     *
     * @return il pannello creato
     */
    protected Pannello creaPannelloMessaggio() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        int bSopra;
        int bSotto;
        int bSx;
        int bDx;

        try { // prova ad eseguire il codice

            /* bordi */
            bSopra = 10;
            bSotto = 10;
            bSx = 5;
            bDx = 5;

            /* crea il pannello per il messaggio */
            pan = new PannelloFlusso();
//            pan.setMaximumSize(1000,200);
//            pan = new PannelloBase();
            pan.getPanFisso().setBorder(BorderFactory.createEmptyBorder(bSopra, bSx, bSotto, bDx));

            pan.getPanFisso().setOpaque(true);
            pan.getPanFisso().setBackground(SFONDO_MESSAGGIO);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello centrale della finestra.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     *
     * @return il pannello creato
     */
    protected Pannello creaPannelloContenuti() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try { // prova ad eseguire il codice

            /* crea il pannello per i contenuti */
            pan = new PannelloBase();
            pan.getPanFisso().setLayout(new BorderLayout());
            pan.getPanFisso().setOpaque(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;

    }


    /**
     * Crea il pannello inferiore della finestra.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     *
     * @return il pannello creato
     */
    protected Pannello creaPannelloBottoni() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        int gapOrizzontale = 20;
        Border bordo;

        try { // prova ad eseguire il codice

            pan = new PannelloBase();
            pan.getPanFisso().setLayout(new LayoutBottoni(gapOrizzontale));
            bordo = BorderFactory.createEmptyBorder(0, 0, 0, 0);
            pan.getPanFisso().setBorder(bordo);
            pan.getPanFisso().setOpaque(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello comandi.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     *
     * @return il pannello creato
     */
    protected PannelloFlusso creaPannelloComandi() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        int gapDx;
        Border bordo;

        try { // prova ad eseguire il codice
            gapDx = 0;

            /* crea il pannello per i comandi */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            bordo = BorderFactory.createEmptyBorder(gapDx, gapDx, gapDx, gapDx);
            pan.getPanFisso().setBorder(bordo);
            pan.getPanFisso().setOpaque(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Regola tutti i pannelli del dialogo.
     * <p/>
     * Inserisce in ogni pannello i contenuti appropriati
     * Metodo chiamato da inizializza
     */
    private void regolaPannelli() {

        try {    // prova ad eseguire il codice

            this.regolaPannelloContenuti();
            this.regolaPannelloBottoni();

            /* regola le dimensioni del pannello messaggio
            * va fatto dopo la regolazione di tutti gli altri pannelli
            * perche' si basa sulle loro dimensioni */
            this.regolaPannelloMessaggio();


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il pannello messaggio del dialogo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Costruisce una label col testo del messaggio <br>
     * Regola font e colore della label <br>
     * Aggiunge la label al pannello <br>
     */
    protected void regolaPannelloMessaggio() {
        /** variabili e costanti locali di lavoro */
        Pannello pan;
        WrapTextArea area;
        String stringa;
        Dimension dim;
        int wDialogo;

        try {    // prova ad eseguire il codice

            /* recupera il pannello */
            pan = this.getPannelloMessaggio();

            if (pan != null) {

                /* se esiste il testo del messaggio, lo inserisce
                 * in una JTextArea e inserice l'area nel pannello */
                stringa = this.getMessaggio();

                if (Lib.Testo.isValida(stringa)) {
                    /* Costruisce una area col testo del messaggio */
                    area = new WrapTextArea(stringa);
                    area.setLineWrap(true);
                    area.setWrapStyleWord(true);
                    area.setEditable(false);
                    area.setDisabledTextColor(COLORE_TESTO_MESSAGGIO);
                    area.setEnabled(false);

                    /* regola colore e font */
                    TestoAlgos.setTesto(area);

                    /* recupera la larghezza attuale del dialogo */
                    wDialogo = this.getPreferredSize().width;

                    /* assegna la sola larghezza preferita */
                    dim = new Dimension(wDialogo, 0);
                    area.setPreferredSize(dim);

                    /* assegna l'altezza preferita per contenere tutto
                     * il testo. Mantiene la larghezza preferita. */
                    area.setOptimalHeight();
//                    Lib.Comp.setAreaOptimalHeight(area);

                    /* Aggiunge la text area al pannello */
                    pan.add(area);
                    pan.sbloccaDimMax();
                    pan.bloccaAltMax();

                } else {

                    if (pan.getComponentiEffettivi().length == 0) {
                        /* riduce le dimensioni del pannello a zero
                         * e ne blocca le dimensioni */
                        pan.setPreferredSize(0, 0);
                        pan.bloccaDim();
                    }// fine del blocco if

                }// fine del blocco if-else

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Regola il pannello contenuti del dialogo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge il Libro al pannello <br>
     */
    protected void regolaPannelloContenuti() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        JPanel pan;
        Libro libro;

        try {    // prova ad eseguire il codice

            /* Recupera il pannello contenuti */
            pan = this.getJPanelContenuto();
            continua = (pan != null);

            /* Aggiunge il libro al pannello
             * (solo se il libro non e' vuoto)*/
            if (continua) {
                libro = this.getLibro();
                if (libro != null) {
                    if (!libro.isVuoto()) {
                        pan.add(libro);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Regola il pannello bottoni del dialogo.
     * <p/>
     * Aggiunge i comandi al pannello comandi del dialogo.<p>
     * Aggiunge ad ogni bottone un listener per lanciare un evento e
     * sapere quale bottone e' stato premuto <br>
     * Regola il nome di ogni bottone, in modo che sia possibile
     * identificare il bottone premuto <br>
     * Aggiunge il/i bottone/i al pannello <br>
     */
    private void regolaPannelloBottoni() {
        /* variabili e costanti locali di lavoro */
        BottonePremuto unListener = null;
        Pannello pan;
        LinkedHashMap<String, BottoneDialogo> mappa;

        try {    // prova ad eseguire il codice
            pan = this.getPannelloBottoni();

            if (pan != null) {

                Dimension dp0 = pan.getPreferredSize();
                int a = 87;

                /* se non sono stati aggiunti bottoni, aggiunge i bottoni di default*/
                if (this.getBottoni().size() == 0) {
                    this.creaBottoniDefault();
                }// fine del blocco if


                Dimension dp1 = pan.getPreferredSize();
                int b = 87;

                /* costruisce un Listener uguale per tutti i bottoni */
//                unListener = new BottonePremuto();

                if (this.isOrdineFisso()) {
                    this.regolaOrdineBottoni();
                }// fine del blocco if

                Dimension dp2 = pan.getPreferredSize();
                int c = 87;

                /* inserisce tutti i bottoni (ordinati) */
                mappa = this.getBottoni();
                for (Bottone bottone : mappa.values()) {
                    bottone.getBottone().addActionListener(unListener);

                    Dimension db = bottone.getBottone().getPreferredSize();

                    pan.add(bottone.getBottone());
                } // fine del ciclo for-each


                Dimension dp4 = pan.getPreferredSize();
                int e = 87;


            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Regola le dimensioni dei bottoni.
     * <p/>
     * Metodo chiamato da inizializza
     */
    private void regolaBottoni() {
        /* variabili e costanti locali di lavoro */
        int larMax;

        try {    // prova ad eseguire il codice

            /*  */
            switch (this.getDimensionamentoBottoni()) {
                case Dialogo.DIMENSIONE_FISSA:
                    larMax = this.getLarghezzaBottoni();
                    this.regolaLarghezzaBottoni(larMax);
                    break;
                case Dialogo.DIMENSIONE_MASSIMA:
                    larMax = this.recuperaMaxLarghezzaBottone();
                    this.regolaLarghezzaBottoni(larMax);
                    break;
                case Dialogo.DIMENSIONE_PROPORZIONALE:
                    this.controllaLarghezzaBottoni();
                    break;
                default: // caso non definito
                    this.controllaLarghezzaBottoni();
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera la larghezza del bottone più largo.
     * <p/>
     * Metodo chiamato da inizializza
     *
     * @return larghezza del bottone più largo
     */
    private int recuperaMaxLarghezzaBottone() {
        /* variabili e costanti locali di lavoro */
        int max = 0;
        int lar;
        Bottone bottone;
        Iterator unGruppo;

        try {    // prova ad eseguire il codice
            unGruppo = this.getBottoni().values().iterator();

            while (unGruppo.hasNext()) {
                bottone = (Bottone)unGruppo.next();
                lar = bottone.getBottone().getPreferredSize().width;
                max = Math.max(lar, max);
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return max;
    }


    /**
     * Forza la larghezza di tutti i bottoni.
     * <p/>
     *
     * @param lar da applicare a tutti i bottoni
     */
    private void regolaLarghezzaBottoni(int lar) {
        /* variabili e costanti locali di lavoro */
        int alt;
        Bottone bottone;
        Iterator unGruppo;

        try {    // prova ad eseguire il codice
            unGruppo = this.getBottoni().values().iterator();

            while (unGruppo.hasNext()) {
                bottone = (Bottone)unGruppo.next();
                alt = bottone.getBottone().getHeight();
                bottone.getBottone().setPreferredSize(new Dimension(lar, alt));
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla la larghezza di tutti i bottoni.
     * <p/>
     * Aggiunge un pixel alla larghezza di tutti i bottoni <br>
     * Ci deve essere un bug da qualche parte, perché la larghezza calcolata
     * in funzione del testo è sbagliata se la prima lettera è maiuscola <br>
     */
    private void controllaLarghezzaBottoni() {
        /* variabili e costanti locali di lavoro */
        int alt;
        int lar;
        Bottone bottone;
        Iterator unGruppo;

        try {    // prova ad eseguire il codice
            unGruppo = this.getBottoni().values().iterator();

            while (unGruppo.hasNext()) {
                bottone = (Bottone)unGruppo.next();
                alt = bottone.getBottone().getPreferredSize().height;
                lar = bottone.getBottone().getPreferredSize().width;
                lar += 1;
                bottone.getBottone().setPreferredSize(new Dimension(lar, alt));
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Riordina i bottoni.
     * <p/>
     * Modifica l'ordinamento dei bottoni <br>
     * Ricrea la collezione, ponendo in fondo i botttoni automatici <br>
     * L'ordinamento di annulla e conferma è fisso <br>
     * (questo per gestire meglio i metodi nel DialogoFactory che
     * crea prima Conferma e dopo, eventualmente, Annulla) <br>
     */
    private void regolaOrdineBottoni() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, BottoneDialogo> mappaNew;
        BottoneDialogo bottone;
        String testo;
        String chiave;
        Iterator unGruppo;
        Object oggetto;
        Map.Entry entry;

        try {    // prova ad eseguire il codice

            mappaNew = new LinkedHashMap<String, BottoneDialogo>();

            unGruppo = this.getBottoni().entrySet().iterator();
            while (unGruppo.hasNext()) {
                oggetto = unGruppo.next();
                entry = (Map.Entry)oggetto;
                bottone = (BottoneDialogo)entry.getValue();
                testo = bottone.getText();
                if (Lib.Testo.isValida(testo)) {
                    chiave = (String)entry.getKey();
                    if ((chiave.equals(TESTO_AZIONE_ANNULLA) == false) && (chiave.equals(
                            TESTO_AZIONE_CONFERMA) == false)) {
                        mappaNew.put(chiave, bottone);
                    }// fine del blocco if
                }// fine del blocco if

            } /* fine del blocco while */

            /* reinserisce PRIMA il bottone annulla */
            bottone = this.getBottone(TESTO_AZIONE_ANNULLA);
            if (bottone != null) {
                mappaNew.put(TESTO_AZIONE_ANNULLA, bottone);
            }// fine del blocco if

            /* reinserisce DOPO il bottone conferma */
            bottone = this.getBottone(TESTO_AZIONE_CONFERMA);
            if (bottone != null) {
                mappaNew.put(TESTO_AZIONE_CONFERMA, bottone);
            }// fine del blocco if

            /* ricrea la collezione */
            this.setBottoni(mappaNew);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea i bottoni di default.
     * <p/>
     * La classe base crea il solo bottone Conferma.
     */
    private void creaBottoniDefault() {
        try {    // prova ad eseguire il codice
            this.addBottoneChiudi();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Inserimento di un carattere a video in un campo testo.
     * </p>
     * Metodo invocato dal Gestore Eventi <br>
     * Controlla se il carattere e' compatibile col Campo <br>
     * Sincronizza il dialogo <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void carattereTestoPremuto(Campo unCampo) {
        try { // prova ad eseguire il codice
            if (unCampo.isValido()) {
                unCampo.aggiornaCampo();
                this.getPortale().sincronizza();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento del carattere Enter in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Controlla se il carattere e' compatibile col Campo <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void carattereEnterPremuto(Campo unCampo) {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento di un carattere
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void caratterePremuto(Campo unCampo) {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Annullamento del dialogo
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void annullaDialogo() {
        try { // prova ad eseguire il codice
            this.chiudiDialogo();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione generica del dialogo
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void azioneDialogo(Azione unAzione) {
        try { // prova ad eseguire il codice
            this.chiudiDialogo();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Cerca di chiudere il dialogo.
     * <p/>
     *
     * @return true se il dialogo è stato effettivamente chiuso
     */
    protected boolean chiudiDialogo() {
        /* variabili e costanti locali di lavoro */
        boolean chiuso = false;
        Frame frame;

        try { // prova ad eseguire il codice

            if (this.isChiudibile()) {

                /* spegne il flag di attivazione */
                this.setAttivo(false);

                /* chiude il frame */
                frame = (Frame)this.getDialogo().getParent();
                frame.dispose();

                chiuso = true;

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiuso;
    }


    /**
     * Stampa il dialogo.
     * <p/>
     */
    protected void stampaDialogo() {
        /* variabili e costanti locali di lavoro */
        Frame frame;

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ripristino del dialogo alla situazione iniziale
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void ripristinaDialogo() {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Verifica se il dialogo e' chiudibile.
     * <p/>
     *
     * @return true se e' chiudibile
     */
    protected boolean isChiudibile() {
        return true;
    }


    /**
     * Aggiunge un campo ad una pagina.
     * <p/>
     * Aggiunge il campo alla collezione campi <br>
     * Avvia il campo <br>
     * Aggiunge il campo alla pagina <br>
     *
     * @param unCampo da aggiungere
     * @param unaPagina a cui aggiungerlo
     *
     * @return true se aggiunto
     */
    protected boolean addCampo(Campo unCampo, Pagina unaPagina) {
        /* variabili e costanti locali di lavoro */
        boolean aggiunto = false;
        String nome;
        LinkedHashMap<String, Campo> campi;

        try { // prova ad eseguire il codice

            /* recupera il nome chiave del campo */
            nome = unCampo.getNomeInterno();

            /* aggiunge alla collezione solo se non già esistente */
            if (!isEsisteCampo(nome)) {

                /* aggiunge il campo alla collezione del dialogo */
                campi = this.getCampi();
                if (campi != null) {
                    campi.put(nome, unCampo);
                    aggiunto = true;
                }// fine del blocco if
            }// fine del blocco if

            /* aggiunge il campo alla pagina */
            unaPagina.aggiungeComponenti(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return aggiunto;
    }


    /**
     * Aggiunge un campo alla prima pagina.
     * <p/>
     * Aggiunge un singolo Campo alla pagina iniziale <br>
     * Recupera la pagina iniziale <br>
     * Invoca il metodo sovrascritto della classe <br>
     *
     * @param unCampo da aggiungere
     *
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public void addCampo(Campo unCampo) {
        this.addCampo(unCampo, this.getPrimaPagina());
    }


    /**
     * Crea un campo e lo aggiunge al dialogo.
     * <p/>
     * Recupera il campo dal modello di questo dialogo <br>
     * Aggiunge il campo <br>
     *
     * @param nomeCampo da creare ed aggiungere
     *
     * @return campo creato ed aggiunto
     */
    protected Campo addCampoModulo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            unCampo = this.copiaCampo(nomeCampo);
            this.addCampo(unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo per il dialogo.
     * <p/>
     * Clona il campo dal modulo di questo dialogo<br>
     *
     * @param nomeCampo da copiare
     *
     * @return campo clonato non inizializzato
     */
    protected Campo copiaCampo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        Modulo modulo;

        try { // prova ad eseguire il codice
            modulo = this.getModulo();
            if (modulo != null) {
                unCampo = modulo.getCloneCampo(nomeCampo);
                unCampo.setInizializzato(false);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo testo e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#testo(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public Campo creaCampo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.testo(nomeCampo);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo testo e lo aggiunge alla prima pagina, col valore indicato.
     * <p/>
     * Invoca il metodo sovrascritto della classe <br>
     * Regola il valore di default <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#testo(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public Campo creaCampo(String nomeCampo, String valore) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            campo = this.creaCampo(nomeCampo);

            /* regola il valore iniziale */
            campo.setValoreIniziale(valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo testo e lo aggiunge ad una pagina.
     * <p/>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param unaPagina a cui aggiungerlo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#testo(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public Campo creaCampo(String nomeCampo, Pagina unaPagina) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.testo(nomeCampo);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo, unaPagina);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo intero e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#intero(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public Campo creaCampoIntero(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.intero(nomeCampo);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo intero e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola il valore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#intero(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public Campo creaCampoIntero(String nomeCampo, Object valore) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.intero(nomeCampo);

            /* regola il valore iniziale */
            campo.setValoreIniziale(valore);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo check e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#checkBox(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public Campo creaCampoCheck(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.checkBox(nomeCampo);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo check e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola il valore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#checkBox(String)
     */
    public Campo creaCampoCheck(String nomeCampo, Object valore) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.checkBox(nomeCampo);

            /* regola il valore iniziale */
            campo.setValoreIniziale(valore);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo data e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#data(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public Campo creaCampoData(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.data(nomeCampo);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo data e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola il valore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#data(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public Campo creaCampoData(String nomeCampo, Object valore) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.data(nomeCampo);

            /* regola il valore iniziale */
            campo.setValoreIniziale(valore);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo combo e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#comboInterno(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public Campo creaCampoCombo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.comboInterno(nomeCampo);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo combo e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola il valore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     * @param lista di valori del combo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#comboInterno(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public Campo creaCampoCombo(String nomeCampo, Object valore, ArrayList<PrefTipo> lista) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.comboInterno(nomeCampo);

            /* regola il valore iniziale */
            campo.setValoreIniziale(valore);
            campo.setValoriInterni(lista);
            campo.setUsaNuovo(false);
            campo.setUsaNonSpecificato(false);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo radioBox e lo aggiunge ad una pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param unaPagina a cui aggiungerlo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#radioBox(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public Campo creaCampoRadio(String nomeCampo, Pagina unaPagina) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.radioBox(nomeCampo);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo, unaPagina);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo radioBox e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#radioBox(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public Campo creaCampoRadio(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.radioBox(nomeCampo);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo di radio bottoni e lo aggiunge ad una pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola i valori hardcoded <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param elencoValori stringa di valori separati da virgola
     * @param unaPagina a cui aggiungerlo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#radioInterno(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public Campo creaCampoRadioGruppo(String nomeCampo, String elencoValori, Pagina unaPagina) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.radioInterno(nomeCampo);

            /* Regola i valori hardcoded */
            campo.setValoriInterni(elencoValori);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo, unaPagina);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo di radio bottoni e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola i valori hardcoded <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param elencoValori stringa di valori separati da virgola
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#radioInterno(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public Campo creaCampoRadioGruppo(String nomeCampo, String elencoValori) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* crea il campo */
            campo = CampoFactory.radioInterno(nomeCampo);

            /* Regola i valori hardcoded */
            campo.setValoriInterni(elencoValori);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo testo area e lo aggiunge alla prima pagina, col valore indicato.
     * <p/>
     * Invoca il metodo sovrascritto della classe <br>
     * Regola il valore di default <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#testo(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public Campo creaCampoArea(String nomeCampo, String valore) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            campo = CampoFactory.testoArea(nomeCampo);

            /* regola il valore iniziale */
            campo.setValoreIniziale(valore);

            /* invoca il metodo delegato della classe */
            this.addCampo(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Elimina i campi.
     * <p/>
     * Elimina i campi dalla collezione interna <br>
     * Elimina i componenti video nella pagina del libro <br>
     */
    protected Pagina eliminaCampi() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;
        boolean continua;
        Libro libro;

        try {    // prova ad eseguire il codice
            libro = this.getLibro();
            continua = (libro != null);

            if (continua) {
                pagina = libro.getPagina(0);
                continua = (pagina != null);
            }// fine del blocco if

            if (continua) {
                pagina.removeAll();
                super.setCampi(new LinkedHashMap<String, Campo>());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pagina;
    }


    /**
     * Aggiunge un bottone.
     * <p/>
     * Recupera icona e testo <br>
     * Crea il bottone con icona, testo ed azione <br>
     *
     * @param flagDismetti se deve dismettere il dialogo quando premuto
     * @param flagConferma se all'uscita deve impostare il flag confermato
     * del dialogo a true
     * (significativo solo se flagDismetti=true)
     */
    protected BottoneDialogo addBottoneBase(String testo,
                                            String nomeIcona,
                                            boolean flagDismetti,
                                            boolean flagConferma,
                                            Azione azione) {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;
        ImageIcon icona;

        try { // prova ad eseguire il codice
            bottone = this.addBottone(testo, flagDismetti, flagConferma);
            bottone.setAction(azione);
            bottone.setText(testo);
            icona = Lib.Risorse.getIconaBase(nomeIcona);
            bottone.setIcon(icona);
            bottone.setFocusable(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Aggiunge il bottone annulla.
     * <p/>
     * Questo botttone dismette il dialogo
     *
     * @return il bottone aggiunto
     */
    protected BottoneDialogo addBottoneAnnulla() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            bottone = this.addBottoneBase("Annulla", "Undo24", true, false, new AzioneAnnulla());

            this.setBottoneAnnulla(bottone);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Aggiunge il bottone conferma.
     * <p/>
     * Questo botttone dismette il dialogo
     *
     * @return il bottone aggiunto
     */
    protected BottoneDialogo addBottoneConferma() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            bottone = this.addBottoneBase("Conferma",
                    "Conferma24",
                    true,
                    true,
                    new AzioneConferma());

            this.setBottoneConferma(bottone);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;

    }


    /**
     * Aggiunge il bottone cancella.
     * <p/>
     * Questo botttone non dismette il dialogo
     *
     * @return il bottone aggiunto
     */
    protected BottoneDialogo addBottoneCancella() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            bottone = this.addBottoneBase("Cancella",
                    "Refresh24",
                    false,
                    false,
                    new AzioneCancella());

            bottone.setEnabled(false);
            this.setBottoneCancella(bottone);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;

    }


    /**
     * Aggiunge il bottone registra.
     * <p/>
     * Questo botttone non dismette il dialogo
     *
     * @return il bottone aggiunto
     */
    protected BottoneDialogo addBottoneRegistra() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            bottone = this.addBottoneBase("Registra",
                    "Registra24",
                    false,
                    false,
                    new AzioneRegistra());
            bottone.setEnabled(false);
            bottone.setConferma(true);
            this.setBottoneRegistra(bottone);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Aggiunge il bottone chiude.
     * <p/>
     * Questo botttone dismette il dialogo
     *
     * @return il bottone aggiunto
     */
    protected BottoneDialogo addBottoneChiudi() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            bottone = this.addBottoneBase("Chiudi", "Esci24", false, false, new AzioneChiude());

            this.setBottoneChiude(bottone);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;

    }


    /**
     * Aggiunge il bottone Stampa.
     * <p/>
     * Questo botttone non dismette il dialogo
     *
     * @return il bottone aggiunto
     */
    protected BottoneDialogo addBottoneStampa() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            bottone = this.addBottoneBase("Stampa", "Print24", false, false, new AzioneStampa());

            this.setBottoneStampa(bottone);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Invocato quando si preme il bottone annulla.
     * <p/>
     */
    protected void eventoAnnulla() {
        try {    // prova ad eseguire il codice
            this.setConfermato(false);
            this.chiudiDialogo();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato quando si preme il bottone cancella.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void eventoCancella() {
        try {    // prova ad eseguire il codice

            /* rimette in memoria il valore del backup */
            for (Campo campo : this.getCampi().values()) {
                campo.setValore(campo.getCampoDati().getBackup());
            } // fine del ciclo for-each

            /* posiziona il fuoco nel primo campo  */
            //@todo commentato, perché non funzionava gac/15-2-07
//            for (Campo campo : this.getCampi().values()) {
//                campo.grabFocus();
//                break;
//            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato quando si preme il bottone conferma.
     * <p/>
     */
    public void eventoConferma() {
        try {    // prova ad eseguire il codice
            this.setConfermato(true);
            this.chiudiDialogo();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato quando si preme il bottone registra.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void eventoRegistra() {
    }


    /**
     * Invocato quando si preme il bottone conferma o registra.
     * <p/>
     */
    public void confermaRegistra() {
        Bottone botConferma;
        Bottone botRegistra;

        try {    // prova ad eseguire il codice
            botConferma = this.getBottoneConferma();
            botRegistra = this.getBottoneRegistra();

            if (botRegistra != null) {
                this.eventoRegistra();
            }// fine del blocco if

            if (botConferma != null) {
                this.eventoConferma();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato quando si preme il bottone chiude.
     * <p/>
     */
    protected void eventoChiudi() {
        try {    // prova ad eseguire il codice
            this.chiudiDialogo();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato quando si preme il bottone stampa.
     * <p/>
     */
    protected void eventoStampa() {
        try {    // prova ad eseguire il codice
            this.stampaDialogo();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un bottone al dialogo.
     * <p/>
     * Recupera il testo del bottone e lo usa come chiave della collezione <br>
     *
     * @param unBottone da aggiungere
     */
    public void addBottone(BottoneDialogo unBottone) {
        /* variabili e costanti locali di lavoro */
        String testo;

        try { // prova ad eseguire il codice
            /* recupera il testo del bottone */
            testo = unBottone.getBottone().getText();

            /* aggiunge il bottone, con la chiave, alla collezione interna del dialogo */
            this.bottoni.put(testo, unBottone);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch/
    }


    /**
     * Costruisce un bottone con azione associata.
     * <p/>
     * Costruisce un bottone da BottoneFactory <br>
     * Utilizza l'azione interna come listener standard per i bottoni <br>
     * Regola il flag di conferma <br>
     *
     * @param testo visibile del bottone
     * @param flagDismetti se deve dismettere il dialogo quando premuto
     * @param flagConferma se all'uscita deve impostare il flag confermato
     * del dialogo a true (significativo solo se flagDismetti=true)
     *
     * @see it.algos.base.componente.bottone.BottoneFactory#creaDialogo(String)
     */
    public BottoneDialogo addBottone(String testo, boolean flagDismetti, boolean flagConferma) {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;
        BottonePremuto unListener;

        try { // prova ad eseguire il codice

            /* costruisce un Listener uguale per tutti i bottoni */
            unListener = new BottonePremuto();

            /* crea il bottone col testo */
            bottone = BottoneFactory.creaDialogo(testo);

            /* aggiunge il listener */
            bottone.addActionListener(unListener);

            /* regola il flag di dismissione */
            bottone.setDismetti(flagDismetti);

            /* regola il flag di conferma */
            bottone.setConferma(flagConferma);

            /* invoca il metodo delegato della classe */
            this.addBottone(bottone);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch/

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Aggiunge un componente al pannello comandi.
     * <p/>
     *
     * @param comp da aggiungere
     */
    public void addComponenteComando(Component comp) {
        try {    // prova ad eseguire il codice
            this.getPannelloComandi().add(comp);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un componente al pannello superiore.
     * <p/>
     *
     * @param comp da aggiungere
     */
    public void addSopra(Component comp) {
        try {    // prova ad eseguire il codice
            this.getJPanelMessaggio().add(comp);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un componente al pannello centrale.
     * <p/>
     *
     * @param componente da aggiungere
     */
    public void addContenuto(Component componente) {
        /* variabili e costanti locali di lavoro */
        JPanel pan;

        try {    // prova ad eseguire il codice
            pan = this.getJPanelContenuto();
            if (pan != null) {
                pan.add(componente);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un pannello al pannello centrale.
     * <p/>
     *
     * @param pannello da aggiungere
     */
    protected void addContenuto(Pannello pannello) {
        /* variabili e costanti locali di lavoro */
        JPanel pan;

        try {    // prova ad eseguire il codice
            pan = this.getJPanelContenuto();
            if (pan != null) {
                pan.add(pannello.getPanFisso());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sostituisce un pannello al pannello centrale.
     * <p/>
     *
     * @param pannello da aggiungere
     */
    protected void setContenuto(Pannello pannello) {
        /* variabili e costanti locali di lavoro */
        JPanel pan;

        try {    // prova ad eseguire il codice
            pan = this.getJPanelContenuto();

            if (pan != null) {
                pan.removeAll();
                pan.add(pannello.getPanFisso());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge al libro (del dialogo) una pagina vuota .
     * <p/>
     *
     * @param titolo il voce della pagina
     *
     * @see Libro#addPagina(String)
     */
    public Pagina addPagina(String titolo) {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;

        try { // prova ad eseguire il codice
            if (this.getLibro() != null) {

                /* aggiunge la pagina al libro */
                pagina = this.getLibro().addPagina(titolo);

                /* normalmente le pagine di un dialogo non usano lo scorrevole */
                pagina.setUsaScorrevole(false);

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pagina;
    }


    /**
     * Aggiunge al libro (del dialogo) una pagina con un contenuto .
     * <p/>
     *
     * @param titolo il voce della pagina
     * @param contenuto l'oggetto con i contenuti
     *
     * @see Libro#addPagina(String,Object)
     */
    public Pagina addPagina(String titolo, Object contenuto) {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;

        try { // prova ad eseguire il codice
            if (this.getLibro() != null) {
                pagina = this.addPagina(titolo);
                pagina.add(contenuto);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pagina;
    }


    /**
     * Restituisce uno specifico bottone del dialogo .
     * <p/>
     * <p/>
     * Controlla che la collezione bottoni non sia vuota <br>
     * Effettua il casting alla classe del bottone <br>
     *
     * @param nome del bottone
     *
     * @return il bottone recuperato
     */
    protected BottoneDialogo getBottone(String nome) {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;
        Object oggetto;

        try { // prova ad eseguire il codice
            if (this.getBottoni() != null) {
                oggetto = this.getBottoni().get(nome);
                bottone = (BottoneDialogo)oggetto;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Indentifica, tramite l'evento, il bottone che e' stato premuto <br>
     * I bottoni sono numerati da sinistra a destra, partendo da 1 <br>
     */
    private void bottonePremuto(ActionEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        Object unOggetto;
        BottoneDialogo bottone = null;
        String nome;

        /* recupera chi ha lanciato l'evento */
        unOggetto = unEvento.getSource();

        /* invoca il metodo protetto eventualmente sovrascritto
         * dalle sottoclassi per intercettare la pressione
         * del bottone */
        if (unOggetto instanceof BottoneDialogo) {
            bottone = (BottoneDialogo)unOggetto;
            this.bottonePremuto(bottone);
        }// fine del blocco if

        /* resetta il valore del bottone */
        this.bottonePremuto = "";

        /* controlla che l'evento provenga dai bottoni */
        if (unOggetto instanceof JButton) {
            try {    // prova ad eseguire il codice

                /* recupera il nome identificativo del bottone */
                nome = bottone.getName();
                this.setBottonePremuto(nome);
                this.setConfermato(bottone.isConferma());

                // todo patch alex 15-06-06
                // todo i bottoni non invocavano i rispettivi metodi
                // todo e quindi non funzionava la sovrascrittura
                boolean patch = false;
                if (bottone.equals(this.getBottoneConferma())) {
//                    this.conferma();
                    patch = true;
                }// fine del blocco if
                if (bottone.equals(this.getBottoneRegistra())) {
//                    this.registra();
                    patch = true;
                }// fine del blocco if
                if (bottone.equals(this.getBottoneAnnulla())) {
//                    this.annulla();
                    patch = true;
                }// fine del blocco if
                // todo patch end

                // todo eseguo questo solo se non ho applicato la patch
                if (!patch) {
                    if (bottone.isDismetti()) {
                        this.chiudiDialogo();
                    }// fine del blocco if
                }// fine del blocco if


            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } /* fine del blocco try-catch */
        } /* fine del blocco if */
    }


    /**
     * Metodo invocato quando si preme un bottone nel dialogo.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param bottone premuto
     */
    protected void bottonePremuto(BottoneDialogo bottone) {
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo cambiato
     */
    protected void eventoMemoriaModificata(Campo campo) {
        try { // prova ad eseguire il codice
            super.eventoMemoriaModificata(campo);

            this.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public JDialog getDialogo() {
        return this.dialogo;
    }


    private void setDialogo(JDialog dialogo) {
        this.dialogo = dialogo;
    }


    public String getMessaggio() {
        return this.messaggio;
    }


    public String getBottonePremuto() {
        return this.bottonePremuto;
    }


    public Object getRisposta() {
// @todo bo ?
        return "";
    }


    protected Pannello getPannelloMessaggio() {
        return pannelloMessaggio;
    }


    private void setPannelloMessaggio(Pannello pannelloMessaggio) {
        this.pannelloMessaggio = pannelloMessaggio;
    }


    public Pannello getPannelloContenuto() {
        return pannelloContenuto;
    }


    protected void setPannelloContenuto(Pannello pannelloContenuto) {
        this.pannelloContenuto = pannelloContenuto;
    }


    protected Pannello getPannelloComandi() {
        return pannelloComandi;
    }


    private void setPannelloComandi(Pannello pannelloComandi) {
        this.pannelloComandi = pannelloComandi;
    }


    protected Pannello getPannelloBottoni() {
        return pannelloBottoni;
    }


    private void setPannelloBottoni(Pannello pannelloBottoni) {
        this.pannelloBottoni = pannelloBottoni;
    }


    public JPanel getJPanelMessaggio() {
        /* variabili e costanti locali di lavoro */
        JPanel pannello = null;
        Pannello panTemp;

        try { // prova ad eseguire il codice
            panTemp = this.getPannelloMessaggio();
            if (panTemp != null) {
                pannello = panTemp.getPanFisso();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    protected JPanel getJPanelContenuto() {
        /* variabili e costanti locali di lavoro */
        JPanel pannello = null;
        Pannello panTemp;

        try { // prova ad eseguire il codice
            panTemp = this.getPannelloContenuto();
            if (panTemp != null) {
                pannello = panTemp.getPanFisso();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    protected JPanel getJPanelBottoni() {
        /* variabili e costanti locali di lavoro */
        JPanel pannello = null;
        Pannello panTemp;

        try { // prova ad eseguire il codice
            panTemp = this.getPannelloBottoni();
            if (panTemp != null) {
                pannello = panTemp.getPanFisso();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    private Azione getAzione(String nomeChiave) {
        return this.getPortale().getAzione(nomeChiave);
    }


    protected String getTitolo() {
        return this.titolo;
    }


    public String setTitolo(String titolo) {
        this.titolo = titolo;
        return this.getTitolo();
    }


    /**
     * Controlla la ridimensionabilita' della finestra dialogo
     * <p/>
     *
     * @param flag true per rendere la finestra ridimensionabile
     */
    public void setResizable(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JDialog dialogo;

        try {    // prova ad eseguire il codice
            dialogo = this.getDialogo();
            if (dialogo != null) {
                dialogo.setResizable(flag);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna una dimensione fissa al dialogo.
     * <p/>
     * La dimensione si riferisce al contenuto della intera finestra.
     *
     * @param larghezza la larghezza
     * @param altezza l'altezza
     */
    public void setPreferredSize(int larghezza, int altezza) {
        super.setPreferredSize(larghezza, altezza);
    }


    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }


    private LinkedHashMap<String, BottoneDialogo> getBottoni() {
        return this.bottoni;
    }


    private void setBottoni(LinkedHashMap<String, BottoneDialogo> bottoni) {
        this.bottoni = bottoni;
    }


    private int getDimensionamentoBottoni() {
        return dimensionamentoBottoni;
    }


    /**
     * Regola il tipo di dimensionamento dei bottoni.
     * <p/>
     * Codifica in Dialogo <br>
     *
     * @param dimensionamentoBottoni
     */
    public void setDimensionamentoBottoni(int dimensionamentoBottoni) {
        this.dimensionamentoBottoni = dimensionamentoBottoni;
    }


    private int getLarghezzaBottoni() {
        return larghezzaBottoni;
    }


    public void setLarghezzaBottoni(int larghezzaBottoni) {
        this.larghezzaBottoni = larghezzaBottoni;
    }


    private void setBottonePremuto(String bottonePremuto) {
        this.bottonePremuto = bottonePremuto;
    }


    /**
     * Determina se il dialogo e' confermabile o registrabile.
     * <p/>
     *
     * @return true se confermabile / registrabile
     */
    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = true;
        ArrayList<Campo> lista;

        try {    // prova ad eseguire il codice

            /* recupera i campi visibili */
            lista = this.getCampiPannello();

            /* controlla che tutti siano validi */
            for (Campo campo : lista) {
                confermabile = campo.isValido();
                if (!confermabile) {
                    break;
                }// fine del blocco if
            }

            /* controlla che tutti gli obbligatori non siano vuoti */
            if (confermabile) {
                for (Campo campo : lista) {
                    if (campo.isObbligatorio()) {
                        if (campo.isVuoto()) {
                            confermabile = false;
                            break;
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    public boolean isVisible() {
        return this.getDialogo().isVisible();
    }


    /**
     * Determina se il form e' registrabile.
     * <p/>
     * Nel dialogo e' sinonimo di isConfermabile
     *
     * @return true se registrabile / confermabile
     */
    public boolean isRegistrabile() {
        return this.isConfermabile();
    }


    /**
     * ritorna true se il dialogo e' stato confermato
     */
    public boolean isConfermato() {
        return confermato;
    }


    protected void setConfermato(boolean confermato) {
        this.confermato = confermato;
    }


    private int getBordo() {
        return bordo;
    }


    private void setBordo(int bordo) {
        this.bordo = bordo;
    }


    protected BottoneDialogo getBottoneAnnulla() {
        return bottoneAnnulla;
    }


    private void setBottoneAnnulla(BottoneDialogo bottoneAnnulla) {
        this.bottoneAnnulla = bottoneAnnulla;
    }


    protected BottoneDialogo getBottoneConferma() {
        return bottoneConferma;
    }


    private void setBottoneConferma(BottoneDialogo bottoneConferma) {
        this.bottoneConferma = bottoneConferma;
    }


    protected BottoneDialogo getBottoneCancella() {
        return bottoneCancella;
    }


    protected void setBottoneCancella(BottoneDialogo bottoneCancella) {
        this.bottoneCancella = bottoneCancella;
    }


    protected BottoneDialogo getBottoneRegistra() {
        return bottoneRegistra;
    }


    protected void setBottoneRegistra(BottoneDialogo bottoneRegistra) {
        this.bottoneRegistra = bottoneRegistra;
    }


    protected BottoneDialogo getBottoneChiude() {
        return bottoneChiude;
    }


    private void setBottoneChiude(BottoneDialogo bottoneChiude) {
        this.bottoneChiude = bottoneChiude;
    }


    protected BottoneDialogo getBottoneStampa() {
        return bottoneStampa;
    }


    private void setBottoneStampa(BottoneDialogo bottoneStampa) {
        this.bottoneStampa = bottoneStampa;
    }


    private boolean isOrdineFisso() {
        return ordineFisso;
    }


    protected void setOrdineFisso(boolean ordineFisso) {
        this.ordineFisso = ordineFisso;
    }


    public void setRidimensionabile(boolean ridimensionabile) {
        this.getDialogo().setResizable(ridimensionabile);
    }


    public GestoreDialogo getGestore() {
        return gestore;
    }


    protected void setGestore(GestoreDialogo gestore) {
        this.gestore = gestore;
    }


    /**
     * Classe interna di tipo <code>listener</code><br>
     * Viene aggiunta ad ogni bottone
     */
    private class BottonePremuto extends AbstractAction {

        public void actionPerformed(ActionEvent unEvento) {
            bottonePremuto(unEvento);
        } /* fine del metodo */
    } /* fine della classe */


    /**
     * Listener invocato quando si clicca sul bottone annulla.
     */
    private class AzioneAnnulla extends AzAdapterAction {

        public void actionPerformed(ActionEvent e) {
            eventoAnnulla();
        }
    }


    /**
     * Listener invocato quando si clicca sul bottone conferma.
     */
    private class AzioneConferma extends AzAdapterAction {

        public void actionPerformed(ActionEvent e) {
            confermaRegistra();
        }
    }


    /**
     * Listener invocato quando si clicca sul bottone cancella.
     */
    private class AzioneCancella extends AzAdapterAction {

        public void actionPerformed(ActionEvent e) {
            eventoCancella();
        }
    }


    /**
     * Listener invocato quando si clicca sul bottone Registra.
     */
    private class AzioneRegistra extends AzAdapterAction {

        public void actionPerformed(ActionEvent unEvento) {
            confermaRegistra();
        }
    }


    /**
     * Listener invocato quando si clicca sul bottone chiude.
     */
    private class AzioneChiude extends AzAdapterAction {

        public void actionPerformed(ActionEvent e) {
            eventoChiudi();
        }
    }


    /**
     * Listener invocato quando si clicca sul bottone chiude.
     */
    private class AzioneStampa extends AzAdapterAction {

        public void actionPerformed(ActionEvent e) {
            eventoStampa();
        }
    }


    /**
     * Layout del pannello bottoni
     * </p>
     * Centra verticalmente i componenti.<br>
     * Il normale FlowLayout li allinea tutti in alto.<br>
     */
    private final class LayoutBottoni extends FlowLayout {

        public LayoutBottoni(int hgap) {
            super(FlowLayout.RIGHT, hgap, 0);
        }


        /**
         * Sovrascrive il metodo
         * regola hGap per centrare verticalmente i bottoni
         */
        public void layoutContainer(Container container) {
            /* variabili e costanti locali di lavoro */
            Component[] componenti;
            int hComp;
            int hMax;
            int hCont;
            int hDisp;

            try { // prova ad eseguire il codice
                componenti = container.getComponents();
                hMax = 0;
                for (Component comp : componenti) {
                    hComp = (int)comp.getPreferredSize().getHeight();
                    hMax = Math.max(hMax, hComp);
                }
                hCont = container.getHeight();
                hDisp = hCont - hMax;
                this.setVgap(hDisp / 2);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            super.layoutContainer(container);

        }


    } // fine della classe 'interna'

}// fine della classe
