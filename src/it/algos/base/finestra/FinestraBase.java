/**
 * Title:     FinestraBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      11-mar-2004
 */
package it.algos.base.finestra;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.menu.barra.MenuBarra;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.portale.Portale;
import it.algos.base.statusbar.StatusBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * Superclasse astratta di <CODE>FinestraLista, FinestraScheda e FinestraDialogo</CODE>.
 * </p>
 * Questa classe astratta: <ul>
 * <li> Ogni finestra contiene e visualizza un <CODE>MenuOld</CODE>,
 * un <CODE>PannelloOld</CODE> ed una StatusBar </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 11-mar-2004 ore 15.12.19
 */
public class FinestraBase extends JFrame implements Finestra {


    /**
     * pannello principale ed unico della finestra - pu&ograve contenere:
     * una lista, una scheda, un dialogo, una lista ed una scheda
     */
    private Portale pannelloPrincipale = null;

    /**
     * menu della finestra - pu&ograve essere:
     * lista, scheda, listascheda, dialogo, specifico
     */
    protected MenuBarra menuBarra = null;

    /**
     * pannello basso con le informazioni sullo stato del programma
     */
    private StatusBar statusBar = null;

    /**
     * label della statusbar
     */
    private JLabel labelStatusBar = null;

    /**
     * la classe Portale esiste solo all'interno di un Navigatore
     */
    protected Navigatore navigatore = null;

    /**
     * delta verticale tra le dimensioni del portale e della finestra
     */
    private int deltaVerticale = 0;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public FinestraBase() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unNavigatore navigatore che gestisce questo pannello
     */
    public FinestraBase(Navigatore unNavigatore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.navigatore = unNavigatore;

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

        /* regola la modificabilita delle dimensioni della finestra
         *  (puo' sempre essere modificata dalle sottoclassi) */
        super.setResizable(Finestra.MODIFICABILE);

        /* regola la risposta alla chiusura della finestra (non fa nulla) */
        super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        /* crea la status bar */
        this.setStatusBar(new StatusBar());

        /* label per la scritta di testo inserita nel pannello */
        labelStatusBar = new JLabel();

        this.setVisible(false);

    }// fine del metodo inizia


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * <p/>
     * <p/>
     * Metodo chiamato dalla classe che crea questo oggetto dopo che sono
     * stati regolati dalla sottoclasse i parametri indispensabili <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice
            add(pannelloPrincipale.getPortale(), BorderLayout.CENTER);

            this.addStatusBar();
            this.creaBarraMenu();
            this.regolaBarraMenu();

            /* aggiunge le azioni della finestra */
            this.aggiungeListener();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizializza


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    public void avvia() {

        try { // prova ad eseguire il codice

            this.pack();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo avvia


    protected void processEvent(AWTEvent awtEvent) {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            if (!(awtEvent instanceof WindowEvent)) {
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        super.processEvent(awtEvent);
    }


    /**
     * Centra questa finestra sull'area disponibile dello schermo.
     * <p/>
     * La finestra non viene impacchettata.
     * La finestra non viene automaticamente resa visibile.
     */
    public void centra() {
        /* variabili e costanti locali di lavoro */
        Dimension dimEsterna;
        Dimension dimFinestra;
        GraphicsEnvironment env;
        Rectangle bounds;
        int xBase = 0;
        int yBase = 0;
        int xFinestra;
        int yFinestra;

        try { // prova ad eseguire il codice

            // recupera le dimensioni dell'area disponibile dello schermo
            // sul sistema operativo correntemente in uso
            env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            bounds = env.getMaximumWindowBounds();
            dimEsterna = new Dimension(bounds.width, bounds.height);

            /* recupera la dimensione della finestra */
            dimFinestra = this.getSize();

            /* regola l'ascissa */
            xFinestra = dimEsterna.width - dimFinestra.width;
            xFinestra /= 2;
            xFinestra += xBase;

            /* regola l'ordinata */
            yFinestra = dimEsterna.height - dimFinestra.height;
            yFinestra /= 2;
            yFinestra += yBase;

            /* sposta la finestra */
            this.setLocation(xFinestra, yFinestra);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i <code>Listener</code>.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge alla finestra gli eventuali
     * ascoltatori delle azioni (eventi) <br>
     */
    public void aggiungeListener() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Navigatore nav;
        Portale portale = null;
        WindowListener azChiusura;

        try {    // prova ad eseguire il codice
            /* recupera il portale con le azioni */
            nav = this.getNavigatore();
            continua = (nav != null);

            if (continua) {
                portale = nav.getPortaleNavigatore();
                continua = (portale != null);
            }// fine del blocco if

            /* recupera l'azione di chiusura */
            if (continua) {
                azChiusura = portale.getAzWindow(Azione.CHIUDE_FINESTRA);
                this.addWindowListener(azChiusura);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea i menu moduli e tabelle.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge ai menu Moduli e Tabelle i moduli desiderati da questo modulo <br>
     * Spazzola la lista dei moduli desiderati in menu da questo modulo.<br>
     * Aggiunge i moduli di tipo Tabella al menu Tabelle e gli altri <br>
     * al menu Moduli. <br>
     * Se i moduli desiderati in menu non sono presenti nel Progetto <br>
     * non vengono aggiunti ai menu. <br>
     */
    protected void creaMenuModuli() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Navigatore nav;
        Modulo modulo = null;
        ArrayList unaLista;

        try {    // prova ad eseguire il codice
            nav = this.getNavigatore();
            continua = (nav != null);

            if (continua) {
                modulo = nav.getModulo();
                continua = (modulo != null);
            }// fine del blocco if

            if (continua) {
                unaLista = modulo.getModuli();

                if (unaLista != null) {
                    if (unaLista.size() > 0) {
                        int a = 87;
                    }// fine del blocco if

                    this.getMenuBarra().getMenuBarra().creaMenuModuli(unaLista);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Regola i menu moduli e tabelle.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Regola (eventualmente cancellandoli) i menu dei moduli e delle tabelle
     */
    protected void regolaMenuModuli() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        MenuBarra menuBarra;
        it.algos.base.menu.menu.Menu moduli = null;
        it.algos.base.menu.menu.Menu tabelle = null;

        try {    // prova ad eseguire il codice
            menuBarra = this.getMenuBarra();
            continua = (menuBarra != null);

            if (continua) {
                moduli = menuBarra.getMenuModuli();
                tabelle = menuBarra.getMenuTabelle();
            }// fine del blocco if

            /* elimina il menu moduli (se vuoto) dalla barra di menu lista */
            if (moduli != null) {
                if (moduli.getMenu().getItemCount() == 0) {
                    menuBarra.getMenuBarra().remove(moduli.getMenu());
                } /* fine del blocco if */
            }// fine del blocco if

            /* elimina il menu tabelle (se vuoto) dalla barra di menu lista */
            if (tabelle != null) {
//                if (tabelle.getMenu().getItemCount() == 0) {
//                    menuBarra.getMenuBarra().remove(tabelle.getMenu());
//                } /* fine del blocco if */
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Crea la barra di menu della finestra.
     * <p/>
     * Metodo sovrascritto nella classe specifica <br>
     */
    protected void creaBarraMenu() {
    } /* fine del metodo regolaMenu */


    /**
     * Aggiunge la barra di menu alla finestra.
     * <p/>
     */
    private void regolaBarraMenu() {
        try {    // prova ad eseguire il codice
            if (menuBarra != null) {
                this.setJMenuBar(menuBarra.getMenuBarra());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * .
     * <p/>
     */
    protected void addStatusBar() {
        /* variabili e costanti locali di lavoro */
        StatusBar sb;

        try {    // prova ad eseguire il codice
            sb = this.getStatusBar();
            if (sb != null) {
                add(sb, BorderLayout.SOUTH);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Regola il testo della label della status bar.
     * <p/>
     *
     * @param testo da assegnare
     */
    public void setTestoStatusBar(String testo) {
        /* variabili e costanti locali di lavoro */
        StatusBar sb;

        try { // prova ad eseguire il codice
            sb = this.getStatusBar();
            if (sb != null) {
                sb.setTesto(testo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Sostituisce completamente il contenuto della StatusBar della finestra
     * <p/>
     *
     * @param comp componente da inserire nella StatusBar
     */
    public void setStatusBar(JComponent comp) {
        JComponent sb;

        try { // prova ad eseguire il codice
            sb = this.getStatusBar();
            if (sb != null) {
                sb.removeAll();
                sb.add(comp);
                this.validate();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public Navigatore getNavigatore() {
        return navigatore;
    }


    public void setNavigatore(Navigatore navigatore) {
        this.navigatore = navigatore;
    }


    public Portale getPannelloPrincipale() {
        return pannelloPrincipale;
    }


    public FinestraBase getFinestraBase() {
        return this;
    }


    public void setPannelloPrincipale(Portale pannelloPrincipale) {
        this.pannelloPrincipale = pannelloPrincipale;
    }


    /*
     * Ritorna la StatusBar della finestra
     * <p>
     * @return la StatusBar della finestra
     */
    public StatusBar getStatusBar() {
        return statusBar;
    }


    private void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }


    public int getDeltaVerticale() {
        return deltaVerticale;
    }


    public void setDeltaVerticale(int deltaVerticale) {
        this.deltaVerticale = deltaVerticale;
    }


    public MenuBarra getMenuBarra() {
        return menuBarra;
    }


    private void setMenuBarra(MenuBarra menuBarra) {
        this.menuBarra = menuBarra;
    }


    /**
     * Assegna una icona alla finestra
     * <p/>
     * L'icona viene visualizzata nella barra del titolo e nella
     * finestra minimizzata.<br>
     * - Non tutti i sistemi operativi supportano il concetto di minimizzare una finestra.<br>
     * - Non tutti i sistemi operativi mostrano l'icona nel titolo della finestra o
     * nella finestra minimizzata.<br>
     * In particolare:<br>
     * - Windows supporta entrambi i concetti<br>
     * - Mac OS X supporta la minimizzazione ma non supporta l'icona<br>
     *
     * @param icona da assegnare
     */
    public void setIcona(ImageIcon icona) {
        /* variabili e costanti locali di lavoro */
        Image image;

        try { // prova ad eseguire il codice
            if (icona != null) {
                image = icona.getImage();
                if (image != null) {
                    this.setIconImage(image);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegna un voce alla finestra.
     * <p/>
     *
     * @param titolo il voce
     */
    public void setTitolo(String titolo) {
        this.setTitle(titolo);
    }


}// fine della classe
