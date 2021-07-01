/**
 * Title:     SchedaBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-lug-2004
 */
package it.algos.base.scheda;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CDNavigatore;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.costante.CostanteColore;
import it.algos.base.costante.CostanteModulo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.form.FormBase;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.libro.Libro;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.info.Info;
import it.algos.base.navigatore.info.InfoScheda;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleScheda;
import it.algos.base.progetto.Progetto;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.CampoValore;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Scheda - form specializzato per la gestione dei campi del database
 * </p>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-lug-2004 ore 14.10.12
 */
public abstract class SchedaBase extends FormBase implements Scheda {

    /* usata in fase di sviluppo per vedere l'oggetto facilmente */
    protected static final boolean DEBUG = false;

    /**
     * nome interno della scheda
     */
    private String nomeChiave = "";

    /**
     * codice chiave del record in uso dalla scheda
     */
    private int codice = 0;

    /**
     * flag - abilita l'uso della status bar
     */
    private boolean isUsaStatusBar = false;

    /**
     * Status bar della scheda
     */
    private SchedaStatusBar statusBar = null;

    /**
     * JLabel con il testo di riferimento per la status bar
     */
    private JLabel labelRifStato;

    /* indicatore di avvenuta registrazione del record
     * spento all'avvio, acceso dopo aver registrato*/
    private boolean registrato;

//    /* indicatore di avvenuto abbandono del record
//     * spento all'avvio, acceso dopo aver abbandonato*/
//    private boolean abbandonato;

    /* flag - indica se la scheda sta presentando un nuovo record */
    private boolean nuovoRecord;

    /* connessione da utilizzare per il recupero dei dati dal db */
    private Connessione connessione;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public SchedaBase(Modulo modulo) {

        /* rimanda al costruttore della superclasse */
        super(modulo);

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
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try { // prova ad eseguire il codice

            /* regolazioni del layout
             * va direttamente al layout perche' i metodi sono sovrascritti
             * in FormBase per comodita' */
            this.getLayoutAlgos().setUsaGapFisso(true);
            this.getLayoutAlgos().setGapPreferito(0);
            this.getLayoutAlgos().setRidimensionaComponenti(true);
            this.getLayoutAlgos().setUsaScorrevole(false);
            this.getLayoutAlgos().setAllineamento(Layout.ALLINEA_SX);

            this.setNomeChiave(this.getClass().getName());

            /* crea la label di riferimento per la status bar */
            label = new JLabel();
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(FontFactory.creaScreenFont(Font.BOLD));
            label.setForeground(CostanteColore.ROSSO);
            this.setLabelRifStato(label);

            /* crea la status bar */
            this.setStatusBar(new SchedaStatusBar(this));

            /* di default usa la status bar */
            this.setUsaStatusBar(true);

            this.setColoreSpiaModificato(SchedaStatusBar.SPIA_ROSSA);

            /* il portale scheda e' opaco e ha un colore di sfondo */
            this.setOpaque(true);
            this.setBackground(CostanteColore.VERDE_CHIARO_DUE);

            /* se e' attivo il debug usa sfondo e colore */
            if (DEBUG) {
                this.setOpaque(true);
                this.setBackground(Color.green);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);

        }// fine del blocco try-catch

    }


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

            if (!this.isInizializzato()) {

                /* crea i campi della scheda */
                this.creaCampi();

                /* creazione delle pagine specifiche */
                this.creaPagine();

                /* creazione delle pagine speciali */
                this.creaPagineAggiuntive();

                /* inizializza nella superclasse */
                super.inizializza();

                /* Assegna la connessione ai Navigatori interni */
                this.regolaConnessioniNav();

                /* abilita la scheda */
                this.setModificabile(true);

                /* aggiunge la eventuale status bar alla scheda */
                if (this.isUsaStatusBar()) {
                    this.add(this.getStatusBar());
                }// fine del blocco if

//                this.getLibro().inizializza();

                /* associa i bottoni standard ai tasti */
                this.regolaAzioni();

                /* marca come inizializzato */
                this.setInizializzato(true);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Assegno i tasti ai bottoni standard.
     * <p/>
     * Invocato dal ciclo Inizializza
     * Escape <br>
     * Enter <br>
     */
    protected void regolaAzioni() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Portale portale;
        JButton botEscape;
        JButton botEnter;
        ToolBar toolBar = null;
        Action azioneJava;

        try { // prova ad eseguire il codice
            portale = this.getPortale();
            continua = (portale != null);

            if (continua) {
                toolBar = portale.getToolBar();
                continua = (toolBar != null);
            }// fine del blocco if

            if (continua) {

                botEscape = toolBar.getBotEscape();
                azioneJava = portale.getAzione(Azione.CHIUDE_SCHEDA).getAzione();
//                iMap = botEscape.getInputMap(WHEN_IN_FOCUSED_WINDOW);
//                ks = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
//                iMap.put(ks, chiaveEsc);
//                botEscape.getActionMap().put(chiaveEsc, azioneJava);
                Lib.Risorse.addTasto(botEscape, KeyEvent.VK_ESCAPE, azioneJava);

                botEnter = toolBar.getBotEnter();
                azioneJava = portale.getAzione(Azione.REGISTRA_SCHEDA).getAzione();
//                iMap = botEnter.getInputMap(WHEN_IN_FOCUSED_WINDOW);
//                ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
//                iMap.put(ks, chiaveEnt);
//                botEnter.getActionMap().put(chiaveEnt, azioneJava);
                Lib.Risorse.addTasto(botEnter, KeyEvent.VK_ENTER, InputEvent.CTRL_MASK, azioneJava);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Recupera il db <br>
     * Chiede al db i dati del record <br>
     * Regola i dati della scheda <br>
     */
    public void avvia(int codice) {
        /* variabili e costanti locali di lavoro */
        Connessione conn;
        Dati dati;
        boolean abilita;
        Portale portale;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* se non ancora inizializzato, inizializza ora */
            if (!this.isInizializzato()) {
                this.inizializza();
            }// fine del blocco if

            /* spegne il flag di attivazione durante l'avvio */
            this.setAttivo(false);

            /* regola il codice della scheda */
            this.setCodice(codice);

            /* regola lo stato iniziale di abilitazione della scheda */
            abilita = this.checkModificabileIniziale();
            this.setModificabile(abilita);

            /* carica i valori del record */
            if (this.getCodice() > 0) {

                /* spegne i flag Registrato e Abbandonato */
                this.setRegistrato(false);
//                this.setAbbandonato(false);

                /* recupera la connessione */
                conn = this.getConnessione();

                /* recupera i dati */
                dati = this.getModulo().query().caricaRecord(this.getCodice(), conn);

                /* carica i valori nei campi */
                this.caricaValori(dati);

                /* chiude i dati */
                dati.close();

            }// fine del blocco if-else

            /* invoca il metodo sovrascritto nella superclasse
             * (avvia campi e libro) */
            super.avvia();

            /* assegna il valore pilota a tutti i campi che
             * contengono un Navigatore */
            LinkedHashMap<String, Campo> campi;
            campi = this.getCampi();
            for (Campo campo : campi.values()) {
                campo.setValorePilota(codice);
            }

            /* posizionamento iniziale del cursore sul
             * primo campo della pagina corrente */
            this.posizionaCursore(0);

            /* sincronizza il portale */
            portale = this.getPortale();
            if (portale != null) {
                portale.sincronizza();
            }// fine del blocco if

            /* porta il fuoco sul primo campo editabile */
            this.vaiCampo();

            /* forza il ridisegno della scheda */
            this.revalidate();

            /* se c'è un codice record, accende il flag di attivazione */
            if (codice > 0) {
                this.setAttivo(true);
            }// fine del blocco if

            /* sincronizza la scheda */
            this.sincronizza();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se la scheda deve essere inizialmente modificabile.
     * <p/>
     *
     * @return true se deve essere inizialmente modificabile
     */
    private boolean checkModificabileIniziale() {
        /* variabili e costanti locali di lavoro */
        boolean modificabile = true;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            /* il codice deve essere > 0*/
            if (modificabile) {
                if (this.getCodice() <= 0) {
                    modificabile = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se la scheda è contenuta in un Navigatore, il
            Navigatore deve essere modificabile */
            if (modificabile) {
                nav = this.getNavigatore();
                if (nav != null) {
                    if (!nav.isModificabile()) {
                        modificabile = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modificabile;
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    public void avvia() {
        /* invoca il metodo sovrascritto nella stessa classe */
        this.avvia(codice);
    }


    /**
     * Sincronizzazione della scheda.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la scheda. <br>
     * Sincronizza la status bar <br>
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        SchedaStatusBar sb;
        Navigatore nav;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* regola l'icona del bottone Registra */
            this.regolaIconaRegistra();

            /* sincronizza la propria status bar */
            if (this.isUsaStatusBar()) {
                sb = this.getStatusBar();
                if (sb != null) {
                    sb.sincronizza();
                }// fine del blocco if
            }// fine del blocco if

            /* sincronizza il navigatore che contiene questa scheda */
            nav = this.getNavigatore();
            if (nav != null) {
                nav.sincronizza();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regola l'icona del bottone Registra.
     * <p/>
     * Se la scheda non è attiva, usa l'icona Registra normale
     * Se la scheda è valida, usa l'icona Registra normale
     * Se la scheda non è valida, usa l'icona Registra con segnale di divieto
     */
    private void regolaIconaRegistra() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Portale portale = null;
        ToolBar tb = null;
        JButton bot = null;
        String iconaStandard;
        String iconaAlert;
        String iconaFinale;
        Icon icona;

        try {    // prova ad eseguire il codice

            /* recupera il portale */
            if (continua) {
                portale = this.getPortale();
                continua = (portale != null);
            }// fine del blocco if

            /* recupera la toolbar */
            if (continua) {
                tb = portale.getToolBar();
                continua = (tb != null);
            }// fine del blocco if

            /* recupera il bottone Registra */
            if (continua) {
                bot = tb.getBotEnter();
                continua = (bot != null);
            }// fine del blocco if

            /* determina i nomi delle icone da urilizzare */
            if (continua) {
                switch (tb.getTipoIcona()) {
                    case ToolBar.ICONA_PICCOLA:
                        iconaStandard = "Registra16";
                        iconaAlert = "Registra16div";
                        break;
                    case ToolBar.ICONA_MEDIA:
                        iconaStandard = "Registra24";
                        iconaAlert = "Registra24div";
                        break;
                    case ToolBar.ICONA_GRANDE:
                        iconaStandard = "Registra24";    // per ora uguali
                        iconaAlert = "Registra24div";
                        break;
                    default: // caso non definito
                        iconaStandard = "Registra24";
                        iconaAlert = "Registra24div";
                        break;
                } // fine del blocco switch

                if (this.isAttivo()) {
                    if (this.isValida()) {
                        iconaFinale = iconaStandard;
                    } else {
                        iconaFinale = iconaAlert;
                    }// fine del blocco if-else
                } else {
                    iconaFinale = iconaStandard;
                }// fine del blocco if-else

                icona = Lib.Risorse.getIconaBase(iconaFinale);
                bot.setIcon(icona);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Sicronizzazione della visibilità di un campo.
     * <p/>
     * Rende visibile il campo visibile, a secondfa del valore del campo flag <br>
     *
     * @param campoFlag di tipo checkbox
     * @param campoVisibile di qualunque tipo
     */
    protected void sincroVisibilita(Campi campoFlag, Campi campoVisibile) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        boolean flag = false;
        Campo unCampoFlag = null;
        Campo unCampoVisibile = null;
        Object ogg;

        try {    // prova ad eseguire il codice
            continua = (campoFlag != null && campoVisibile != null);

            if (continua) {
                unCampoFlag = this.getCampo(campoFlag.get());
                unCampoVisibile = this.getCampo(campoVisibile.get());
                continua = (unCampoFlag != null && unCampoVisibile != null);
            }// fine del blocco if

            if (continua) {
                ogg = unCampoFlag.getValore();
                if (ogg instanceof Boolean) {
                    flag = (Boolean)ogg;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                unCampoVisibile.setModificabile(flag);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Primo campo editabile.
     * Metodo invocato dal ciclo avvia <br>
     */
    protected void vaiCampo() {
        /* variabili e costanti locali di lavoro */
        String campo;

        try {    // prova ad eseguire il codice
            campo = this.getModello().getPrimoCampo();
            this.vaiCampo(campo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Crea la collezione di campi della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampoOriginale;
        Campo unCampoNuovo;
        Iterator unGruppo;
        LinkedHashMap campi;
        String nomeChiave;

        try {    // prova ad eseguire il codice

            /* recupera tutti i campi del Modello */
            campi = this.getModello().getCampiModello();

            /* traversa tutta la collezione */
            unGruppo = campi.values().iterator();
            while (unGruppo.hasNext()) {

                /* clona il campo */
                unCampoOriginale = (Campo)unGruppo.next();
                unCampoNuovo = unCampoOriginale.clonaCampo();
                unCampoNuovo.setInizializzato(false);

                /* aggiunge il campo clonato alla collezione */
                nomeChiave = unCampoOriginale.getNomeInterno();
                this.getCampi().put(nomeChiave, unCampoNuovo);

            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Crea una pagina vuota, senza titolo.
     *
     * @return la pagina appena creata
     */
    protected Pagina addPagina() {
        /* invoca il metodo sovrascritto */
        return this.addPagina("");
    }


    /**
     * Crea una pagina completa, con titolo e campi.
     *
     * @param titolo titolo del tabulatore della pagina
     * @param campi i campi da disporre nella pagina; puo' essere:
     * String - un set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di oggetti pannello Campo <br>
     *
     * @return la pagina appena creata
     */
    public Pagina addPagina(String titolo, Object campi) {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;

        try {    // prova ad eseguire il codice

            pagina = this.addPagina(titolo);
            pagina.add(campi);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return pagina;
    }


    /**
     * Crea una pagina vuota, con titolo.
     *
     * @param titolo titolo del tabulatore della pagina
     *
     * @return la pagina appena creata
     */
    protected Pagina addPagina(String titolo) {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;
        Libro libro;

        try { // prova ad eseguire il codice

            libro = this.getLibro();
            if (libro != null) {
                pagina = libro.addPagina(titolo);
                pagina.setContenitoreCampi(this);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pagina;
    }


    /**
     * Crea un pannello.
     * <p/>
     * Vanno regolati:
     * Posizione
     * Dimensione
     * Titolo
     * Bordo
     * Verticale (default)
     *
     * @param campi i campi da disporre nella pagina; puo' essere:
     * String - un set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di oggetti pannello Campo <br>
     *
     * @return il pannello appena creato
     */
    protected Pannello creaPannello(Object campi) {
        /* variabili e costanti locali di lavoro */
        Pannello pannello = null;

        try {    // prova ad eseguire il codice
            pannello = PannelloFactory.verticale(this, campi);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea un pannello vuoto, senza titolo.
     *
     * @return il pannello appena creato
     */
    protected Pannello creaPannello() {
        /* invoca il metodo delegato della classe */
        return this.creaPannello(null);
    }


    /**
     * Crea le pagine aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Pagina programmatore, note od altro <br>
     */
    protected void creaPagineAggiuntive() {
        /* variabili e costanti locali di lavoro */
        String nomeSet;
        Pagina pagina;

        try { // prova ad eseguire il codice

            /* aggiunge eventualmente una pagina con il set del Programmatore */
            if (Progetto.isProgrammatore()) {
                nomeSet = CostanteModulo.SET_PROGRAMMATORE;
                pagina = this.addPagina("programmatore", nomeSet);
                pagina.setPaginaProgrammatore(true);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Assegna ai Navigatori di tutti i campi Navigatore
     * la connessione da utilizzare per l'accesso al database.
     * <p/>
     * Per tutti i Navigatori interni, usa la connessione del
     * navigatore padre (quello che contiene la scheda)
     * in modo che le eventuali transazioni operino sulla stessa
     * connessione.
     */
    protected void regolaConnessioniNav() {
        /* variabili e costanti locali di lavoro */
        Portale portale;
        HashMap<String, Campo> campi;
        Navigatore navPadre = null;
        Navigatore navFiglio;
        boolean continua;

        try {    // prova ad eseguire il codice

            /* recupera il portale */
            portale = this.getPortale();
            continua = portale != null;

            /* recupera il navigatore padre
             * (quello che contiene la scheda)*/
            if (continua) {
                navPadre = portale.getNavigatore();
                continua = navPadre != null;
            }// fine del blocco if

            /* spazzola i campi e assegna la connessione
             * agli eventuali navigatori interni */
            if (continua) {
                campi = this.getCampi();
                for (Campo campo : campi.values()) {
                    navFiglio = campo.getNavigatore();
                    if (navFiglio != null) {
                        navFiglio.setConnessione(navPadre.getConnessione());
                    }// fine del blocco if
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Carica valori.
     * <p/>
     * Metodo invocato dal ciclo avvia <br>
     * Carica nella variabile archivio di ogni campo il rispettivo valore <br>
     * Fa risalire il valore fino alla Memoria <br>
     *
     * @param dati wrapper ai dati proveniente dal database
     */
    private void caricaValori(Dati dati) {
        /* variabili e costanti locali di lavoro */
        Iterator unGruppo = null;
        Campo campo = null;
        Object valoreArchivio;
        Object valoreMemoria = null;
        int cont = 0;
        CampoDati campoDati = null;

        try {    // prova ad eseguire il codice

            if (dati != null) {
                unGruppo = this.getCampi().values().iterator();
                cont = 0;
                while (unGruppo.hasNext()) {
                    campo = (Campo)unGruppo.next();
                    if (campo.getCampoDB().isCampoFisico()) {

                        /*
                         * recupera il valore memoria, lo fa convertire in valore
                         * archivio dal campo, lo registra in archivio, lo fa risalire
                         * fino a memoria (regola il backup)
                         */
                        valoreMemoria = dati.getValueAt(0, cont);
                        campoDati = campo.getCampoDati();
                        valoreArchivio = campoDati.getArchivioDaMemoria(valoreMemoria);
                        campoDati.setArchivio(valoreArchivio);
                        campoDati.archivioMemoria();

                        cont++;

//                        disabilitato alex 12-12-06
//                        l'evento parte già quando la memoria di un campo cambia
//                        this.eventoMemoriaModificata(campo);

                    }// fine del blocco if

                } // fine del blocco while

            }// fine del blocco if

            int a = 87;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * @return lista di valori memoria
     *         <p/>
     *         todo NON Funziona!    gac 24.1.05
     *         Restituisce i valori memoria dei campi della scheda.
     *         <p/>
     *         Assume che la variabile dati sia allineata <br>
     *         Carica nella variabile memoria di ogni campo il rispettivo valore <br>
     */
    public ArrayList getValoriAlex() {
        /* variabili e costanti locali di lavoro */
        ArrayList unaLista = null;
        Iterator unGruppo = null;
        Campo campo = null;
        Object valore = null;

        try {    // prova ad eseguire il codice
            unaLista = new ArrayList();

            if (this.getCampi().size() > 0) {
                unGruppo = this.getCampi().values().iterator();
                while (unGruppo.hasNext()) {
                    campo = (Campo)unGruppo.next();
                    valore = campo.getCampoDati().getMemoria();
                    unaLista.add(valore);
                } /* fine del blocco while */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unaLista;
    }

//    /**
//     * Allinea le variabili del Campo: da GUI verso Memoria.
//     * <p/>
//     * Partendo dalla componente GUI di CampoVideo, regola di conseguenza le
//     * variabili Video e Memoria di CampoDati <br>
//     * Recupera il valore attuale del componente GUI in CampoVideo <br>
//     * Aggiorna il valore del video e della memoria in CampoDati <br>
//     * La variabile memoria e' allineata per calcoli varii <br>
//     */
//    private void guiMemoria() {
//        /* traversa tutta la collezione */
//        Campo unCampo = null;
//
//        try { // prova ad eseguire il codice
//            Iterator unGruppo = this.getCampi().values().iterator();
//            while (unGruppo.hasNext()) {
//                unCampo = (Campo)unGruppo.next();
//                unCampo.getCampoLogica().guiMemoria();
//            } /* fine del blocco while */
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Posizionamento del cursore sul campo della pagina corrente
     * individuato dalla posizione.
     * <p/>
     * Recupera la pagina corrente <br>
     * Recupera il nome del campo dal numero <br>
     * Invoca il metodo sovrascritto <br>
     *
     * @param pos posizione nella lista dei soli campi della pagina corrente
     * 0 per il primo campo
     */
    public void posizionaCursore(int pos) {
        try { // prova ad eseguire il codice

            this.getLibro().posizionaCursore(pos);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Posizionamento del cursore sul campo della scheda
     * individuato dal nome.
     * <p/>
     *
     * @param nome del campo
     */
    public void posizionaCursore(String nome) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try { // prova ad eseguire il codice
            campo = this.getCampo(nome);
            if (campo != null) {
                campo.grabFocus();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Controlla lo stato della scheda prima della registrazione.
//     * <p/>
//     * Trasporta i valori dei campi da video ad archivio <br>
//     * Controlla i campi obbligatori <br>
//     * Controlla i campi validi <br>
//     * Controlla la validit� della scheda nel suo complesso <br>
//     * Chiede all'utente eventuale conferma per la registrazione <br>
//     *
//     * @return vero se tutti i controlli sono positivi
//     */
//    public boolean controlloRegistrazione() {
//        /* variabili e costanti locali di lavoro */
//        boolean riuscito = false;
//
//        try {    // prova ad eseguire il codice
//            riuscito = true;
//        } catch (Exception unErrore) {    // intercetta l'errore
//            /* mostra il messaggio di errore */
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return riuscito;
//    }


    /**
     * Abilita il flag visibile del record.
     * <p/>
     * Quando viene creato un nuovo record, il flag visibile e posto
     * uguale a false <br>
     */
    public void setRecordVisibile() {
        /* variabili e costanti locali di lavoro */
        String chiave = null;
        Campo campoVisibile = null;

        try {    // prova ad eseguire il codice
            chiave = this.getModello().getCampoVisibile().getNomeInterno();
            campoVisibile = this.getCampo(chiave);
            campoVisibile.setValore(true);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se la scheda e' valida.
     * </p>
     * Utilizzato per selezionare l'icona del bottone registra.
     * Tutti i campi visibili devono avere un valore valido (o  vuoto).
     * I campi obbligatori non devono essere vuoti.
     * E' condizione necessaria (ma non sufficiente) perche'
     * la scheda sia registrabile.
     *
     * @return true se i campi sono tutti validi <br>
     */
    public boolean isValida() {
        /* variabili e costanti locali di lavoro */
        boolean valida = true;
        ArrayList<Campo> lista;
        Controlli[] controlli;

        try { // prova ad eseguire il codice

            /* recupera i campi visibili */
            lista = this.getCampiPannello();

            /* controlla che tutti siano validi */
            for (Campo campo : lista) {
                valida = campo.isValido();
                if (!valida) {
                    break;
                }// fine del blocco if
            }

            /* controlla che tutti quelli obbligatori non siano vuoti */
            if (valida) {
                for (Campo campo : lista) {
                    if (campo.isObbligatorio()) {
                        valida = !campo.isVuoto();
                        if (!valida) {
                            break;
                        }// fine del blocco if
                    }// fine del blocco if
                }
            }// fine del blocco if

            /* Richiama i controlli della scheda specifica */
            if (valida) {
                controlli = this.getControlli();

                if (controlli != null) {
                    for (Controlli cont : controlli) {
                        if (!cont.isValido(this)) {
                            valida = false;
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for-each
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }


    /**
     * Richiama i controlli della scheda specifica.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected Controlli[] getControlli() {
        return null;
    }


    /**
     * Ritorna il motivo per il quale la scheda non è valida.
     * <p/>
     *
     * @return il motivo per il quale la scheda non è valida
     */
    public String getMotivoNonValida() {
        /* variabili e costanti locali di lavoro */
        String motivo = "";
        ArrayList<Campo> lista;
        Controlli[] controlli;

        try {    // prova ad eseguire il codice
            /* recupera i campi visibili */
            lista = this.getCampiPannello();

            /* controlla che tutti i campi siano validi usando il validatore */
            for (Campo campo : lista) {
                if (!campo.isValido()) {
                    if (Lib.Testo.isValida(motivo)) {
                        motivo += "\n";
                    }// fine del blocco if
                    motivo += "- Il campo " + campo.getTestoEtichetta() + " non è valido";
                }// fine del blocco if
            }

            /* controlla che tutti i campi obbligatori non siano vuoti */
            for (Campo campo : lista) {
                if (campo.isObbligatorio()) {
                    if (campo.isVuoto()) {
                        if (Lib.Testo.isValida(motivo)) {
                            motivo += "\n";
                        }// fine del blocco if
                        motivo += "- Il campo " + campo.getTestoEtichetta() + " è obbligatorio";
                    }// fine del blocco if
                }// fine del blocco if
            }

            /* aggiunge i messaggi dei controlli specifici */
            controlli = this.getControlli();
            if (controlli != null) {
                for (Controlli cont : controlli) {
                    if (!cont.isValido(this)) {
                        motivo = Lib.Testo.concatReturn(motivo, "- " + cont.getMessaggio(this));
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return motivo;
    }


    /**
     * Verifica se la scheda e' teoricamente registrabile.
     * <p/>
     * Perche' la scheda sia registrabile
     * deve avere codice > 0
     * e, se è contenuta in un Navigatore, questo deve
     * essere modificabile.
     * <p/>
     * Non effettua controlli sulla validità della scheda, quindi
     * non è detto che la scheda possa essere effettivamente registrata
     *
     * @return true se e' teoricamente registrabile
     */
    public boolean isRegistrabile() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        InfoScheda infoScheda;

        try { // prova ad eseguire il codice

            infoScheda = this.getInfoStatoScheda();

            if (continua) {
                continua = (infoScheda != null);
            }// fine del blocco if

            if (continua) {
                continua = infoScheda.isPossoRegistrare();
            }// fine del blocco if

//            if (continua) {
//                continua = this.isValida();
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;

    }


    /**
     * Verifica se la scheda e' chiudibile.
     * <p/>
     *
     * @return true se e' chiudibile
     */
    public boolean isChiudibile() {
        return !this.isModificata();
    }


    /**
     * Ritorna il portale scheda che contiene questa scheda.
     * <p/>
     *
     * @return il portale scheda
     */
    private PortaleScheda getPortaleScheda() {
        /* variabili e costanti locali di lavoro */
        PortaleScheda portaleScheda = null;
        Portale portale;

        try {    // prova ad eseguire il codice
            portale = this.getPortale();
            if (portale != null) {
                if (portale instanceof PortaleScheda) {
                    portaleScheda = (PortaleScheda)portale;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return portaleScheda;
    }


    /**
     * Ritorna il pacchetto di informazioni sullo stato della scheda.
     * <p/>
     *
     * @return le informazioni di stato della scheda
     */
    private InfoScheda getInfoStatoScheda() {
        /* variabili e costanti locali di lavoro */
        InfoScheda infoScheda = null;
        Info info = null;
        PortaleScheda portaleScheda = null;

        try {    // prova ad eseguire il codice
            portaleScheda = this.getPortaleScheda();
            if (portaleScheda != null) {
                info = portaleScheda.getInfoStato();
                if (info != null) {
                    if (info instanceof InfoScheda) {
                        infoScheda = (InfoScheda)info;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return infoScheda;
    }


    /**
     * Ritorna l'elenco dei campi fisici modificati.
     * <p/>
     *
     * @return l'elenco dei campi fisici modificati
     */
    public ArrayList<Campo> getCampiFisiciModificati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiMod = new ArrayList<Campo>();
        ArrayList<Campo> campiFisiciMod = new ArrayList<Campo>();

        try {    // prova ad eseguire il codice

            campiMod = this.getCampiModificati();
            for (Campo campo : campiMod) {
                if (campo.getCampoDB().isCampoFisico()) {
                    campiFisiciMod.add(campo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campiFisiciMod;
    }


    /**
     * Registra la scheda.
     * <p/>
     * Registra solo i campi fisici modificati.
     * Controlla se la scheda è valida.
     * Se la scheda è contenuta in un Navigatore, delega la registrazione
     * al Navigatore (che può così essere intercettata dalle sottoclassi
     * del Navigatore)
     * Se non è contenuta in un Navigatore, registra direttamente.
     *
     * @return true se registrata
     */
    protected boolean registraRecord() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Navigatore nav;
        String messaggio;

        try { // prova ad eseguire il codice

            /** controlla se la scheda è valida, in caso contrario
             * visualizza un messaggio con la spiegazione */
            if (continua) {
                if (!this.isValida()) {
                    messaggio = "La scheda non è registrabile.\n";
                    messaggio += this.getMotivoNonValida();
                    new MessaggioAvviso(messaggio);
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* effettua la registrazione */
            if (continua) {
                nav = this.getNavigatore();
                if (nav != null) {
                    continua = nav.registraRecord();
                } else {
                    continua = this.registraRecordDiretto();
                }// fine del blocco if-else
                this.setRegistrato(continua);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Registra il record direttamente senza passare dal Navigatore.
     * <p/>
     *
     * @return true se riuscito
     */
    private boolean registraRecordDiretto() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        ArrayList<Campo> campi = null;
        ArrayList<CampoValore> listaCV = new ArrayList<CampoValore>();
        Modulo modulo = null;
        int codice = 0;
        Connessione conn = null;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            /* recupera il modulo */
            if (continua) {
                modulo = this.getModulo();
                continua = modulo != null;
            }// fine del blocco if

            /* recupera il codice del record */
            if (continua) {
                codice = this.getCodice();
                continua = codice != 0;
            }// fine del blocco if

            /* recupera la lista dei campi fisici modificati */
            if (continua) {
                campi = this.getCampiFisiciModificati();
                if (campi.size() == 0) {
                    continua = false;
                    riuscito = true;
                }// fine del blocco if-else
            }// fine del blocco if

            /* crea la lista dei campiValore da registrare */
            if (continua) {
                for (Campo campo : campi) {
                    listaCV.add(new CampoValore(campo));
                }
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = this.getConnessione();
                continua = conn != null;
            }// fine del blocco if

            /* effettua la registrazione sul database */
            if (continua) {
                riuscito = modulo.query().registraRecordValori(codice, listaCV, conn);
            }// fine del blocco if

            /**
             * giro ricorsivamente il comando a tutti gli eventuali campi
             * Navigatore contenuti nella scheda, per registrare le eventuali
             * schede aperte
             */
            campi = this.getCampiNavigatore();
            for (Campo campo : campi) {
                nav = campo.getNavigatore();
                riuscito = nav.registraRecord();
                if (!riuscito) {
                    break;
                }// fine del blocco if
            }// fine del blocco for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Ricarica la scheda con i valori presenti sul database.
     * <p/>
     *
     * @return true se riuscito
     */
    public boolean ricaricaScheda() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;

        try { // prova ad eseguire il codice

            this.avvia();
            eseguito = true;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Chiude la scheda con un eventuale dialogo di conferma per la registrazione.
     * <p/>
     * Il dialogo viene presentato solo se la scheda è modificata.
     *
     * @param bottoneDefault codice del bottone preselezionato nel dialogo
     * @param dismetti Se true, dopo la chiusura dismette la scheda
     * (carica tutti i campi con valori vuoti)
     *
     * @return codice di chiusura della scheda:
     *         - Scheda.ANNULLA = chiusura non avvenuta
     *         - Scheda.ABBANDONA = chiusura avvenuta con abbandono delle eventuali modifiche
     *         - Scheda.REGISTRA = chiusura avvenuta con registrazione delle eventuali modifiche
     */
    public int richiediChiusuraConDialogo(int bottoneDefault, boolean dismetti) {
        /* variabili e costanti locali di lavoro */
        int codUscita = 0;
        boolean registrato = false;
        int azione;

        codUscita = Scheda.ABBANDONA;

        if (this.getCodice() > 0) {

            if (this.isModificata()) {
                azione = this.dialogoChiusura(bottoneDefault);
            } else {
                azione = Scheda.ABBANDONA;
            }// fine del blocco if-else

            switch (azione) {
                case Scheda.ANNULLA:
                    codUscita = Scheda.ANNULLA;
                    break;

                case Scheda.ABBANDONA:
                    codUscita = Scheda.ABBANDONA;
                    break;

                case Scheda.REGISTRA:

                    if (this.isRegistrabile()) {
                        registrato = this.registraRecord();
                        if (registrato) {
                            codUscita = Scheda.REGISTRA;
                        } else {
                            codUscita = Scheda.ANNULLA;
                        }// fine del blocco if-else
                    }// fine del blocco if

                    break;

                default: // caso non definito
                    break;
            } // fine del blocco switch

            /* Se la chiusura e' riuscita, se richiesto
             * dismette la scheda */
            if (registrato) {
                if (dismetti) {
                    this.dismetti();
                }// fine del blocco if
            }// fine del blocco if

            // se la chiusura non è stata annullata, lancia un evento di editing terminato
            if (codUscita!=Scheda.ANNULLA) {
                fireFinished(this.getCodice(), registrato);
    		}

        } else {    // codice = 0
            registrato = true;
        }// fine del blocco if-else
        
        
        /* valore di ritorno */
        return codUscita;
    }


    /**
     * Chiude la scheda registrando o abbandonando il record
     * senza dialogo di conferma per la registrazione se è abbandonata modificata.
     * <p/>
     *
     * @param registra true per registrare il record, false per abbandonare
     * @param dismetti Se true, dopo la chiusura dismette la scheda
     * (carica tutti i campi con valori vuoti)
     *
     * @return true se la scheda e' stata chiusa
     */
    public boolean richiediChiusuraNoDialogo(boolean registra, boolean dismetti) {
        /* variabili e costanti locali di lavoro */
        boolean registrato = false;

        if (this.getCodice() > 0) {

            if (registra) {
                if (this.isRegistrabile()) {
                    registrato = this.registraRecord();
                }// fine del blocco if
            } else {
                registrato = true;
            }// fine del blocco if-else

            /* Se la chiusura e' riuscita, se richiesto
             * dismette la scheda */
            if (registrato) {
                if (dismetti) {
                    this.dismetti();
                }// fine del blocco if
                
            }// fine del blocco if
            
            // lancia un evento di editing terminato
            fireFinished(this.getCodice(), registrato);


        } else {    // codice = 0
            registrato = true;
        }// fine del blocco if-else

        /* valore di ritorno */
        return registrato;
    }


//    /**
//     * DISATTIVATO ALEX 06/05/2009
//     * SEMBRA NON SERVIRE A NIENTE
//     * MENTRE DA' NOIA MODIFICANDO LA MEMORIA DEI CAMPI
//     * QUANDO NON INTERESSA PIU' INTERCETTARE LE MODIFICHE
//     * PERCHE' STO ABBANDONANDO SENZA REGISTRARE.
//     *
//     * Abbandona le eventuali modifiche.
//     * <p/>
//     * Se nuovo record, elimina il record.
//     * Ripristina i valori di backup.
//     *
//     * @return true se riuscito
//     */
//    private boolean abbandonaModifiche() {
//        /* variabili e costanti locali di lavoro */
//        boolean riuscito = false;
//
//        try {    // prova ad eseguire il codice
//
//            /* ripristina i valori dal backup */
////            this.restoreBackupScheda();
//
//            /* valore di ritonno */
//            riuscito = true;
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return riuscito;
//    }


    /**
     * Chiusura scheda con dialogo.
     * <p/>
     * Presenta un dialogo con tre bottoni di uscita:
     * - Annulla (l'azione)
     * - Abbandona le modifiche
     * - Registra le modifiche
     *
     * @param bottoneDefault codice del bottone preselezionato nel dialogo
     *
     * @return ANNULLA, ABBANDONA, REGISTRA
     *         costanti interne della classe
     */
    private int dialogoChiusura(int bottoneDefault) {
        /* variabili e costanti locali di lavoro */
        int azione = 0;
        String testo;
        String titolo;
        int standard = 0;
        int risposta;

        testo = "La scheda aperta è stata modificata";
        titolo = "Registrazione";

        /* bottoni in basso */
        String[] bottoni = {"Annulla", "Abbandona", "Registra"};

//        /* bottoni in basso */
//        if (this.isValida()) {
//            String[] bottoni = {"Annulla", "Abbandona", "Registra"};
//        } else {
//            String[] bottoni = {"Annulla", "Abbandona","pippo"};
//        }// fine del blocco if-else

        try {    // prova ad eseguire il codice
            switch (bottoneDefault) {
                case BOTTONE_ANNULLA:
                    standard = 0;
                    break;
                case BOTTONE_ABBANDONA:
                    standard = 1;
                    break;
                case BOTTONE_REGISTRA:
                    standard = 2;
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            /* presenta un dialogo ed ottiene una risposta */
            risposta = JOptionPane.showOptionDialog(
                    null,
                    testo,
                    titolo,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    bottoni,
                    bottoni[standard]);

            /* elabora la risposta */
            switch (risposta) {
                case 0:
                    azione = ANNULLA;
                    break;
                case 1:
                    azione = ABBANDONA;
                    break;
                case 2:
                    azione = REGISTRA;
//
//                    if (this.isValida()) {
//                    azione = REGISTRA;
//                    } else {
//                        new MessaggioAvviso("La scheda non e' registrabile.");
//                        azione = ANNULLA;
//                    }// fine del blocco if-else
                    break;
                default: // caso non definito
                    azione = ANNULLA;
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return azione;
    }


    /**
     * Ricarica la scheda con i valori di backup.
     * <p/>
     */
    public void restoreBackupScheda() {
        /* variabili e costanti locali di lavoro */
        ArrayList campi = null;
        Campo campo = null;

        try { // prova ad eseguire il codice
            campi = Libreria.hashMapToArrayList(this.getCampi());
            for (int k = 0; k < campi.size(); k++) {
                campo = (Campo)campi.get(k);
                campo.getCampoDati().restoreBackup();
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Dismette la scheda.
     * <p/>
     * - Resetta tutti i campi con valori vuoti.
     * - Pone il codice scheda a zero.
     * @param codice - il codice del record nella scheda
     * @param registrato - true se è stato registrato, false se abbandonato
     */
    private void dismetti() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            /* spegne il flag di attivazione */
            this.setAttivo(false);

            /* azzera tutti i valori dei campi */
            this.resetCampi();

//            /* Pone il codice scheda a zero */
//            this.setCodice(0);

            /* disabilita la scheda */
            this.setModificabile(false);
            
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }
    
    /**
     * Lancia un evento di editing terminato (tramite il portale).
     * <p/>
     * @param codice - il codice del record nella scheda
     * @param registrato - true se è stato registrato, false se abbandonato
     */
    private void fireFinished(int cod, boolean reg){
        // lancia un evento di editing completato dal portale
        PortaleScheda portale = getPortaleScheda();
        if (portale!=null) {
			portale.fireEditingFinished(cod, reg);
		}
    }


    /**
     * Ritorna il numero della pagina correntemente visualizzata.
     * <p/>
     *
     * @return il numero della pagina correntemente visualizzata (0 per la prima)
     */
    public int getNumeroPagina() {
        return this.getLibro().getNumeroPagina();
    }


    /**
     * Regola il colore della spia modificato.
     * <p/>
     *
     * @param codice il codice del colore
     * puo' essere SPIA_ROSSA, SPIA_VERDE, SPIA_GIALLA
     *
     * @see SchedaStatusBar
     */
    public void setColoreSpiaModificato(int codice) {
        /* variabili e costanti locali di lavoro */
        SchedaStatusBar sb = null;

        try {    // prova ad eseguire il codice
            sb = this.getStatusBar();
            if (sb != null) {
                sb.setColoreSpia(codice);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Visualizza una data pagina della scheda.
     * <p/>
     *
     * @param pagina numero della pagina (0 per la prima)
     */
    public void vaiPagina(int pagina) {
        this.getLibro().vaiPagina(pagina);
    }


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Dimension getDimensione() {
        return this.getSize();
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public JComponent getScheda() {
        return this;
    } /* fine del metodo getter */


    public int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }


    protected Modello getModello() {
        /* variabili e costanti locali di lavoro */
        Modello modello = null;
        Modulo modulo = null;

        try { // prova ad eseguire il codice
            modulo = this.getModulo();
            if (modulo != null) {
                modello = modulo.getModello();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modello;
    }


    protected Navigatore getNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore = null;

        try { // prova ad eseguire il codice
            if (this.getPortale() != null) {
                navigatore = this.getPortale().getNavigatore();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return navigatore;
    }


    /**
     * Ritorna la connessione che la scheda deve utilizzare per il recupero dei dati.
     * <p/>
     * Se è stata assegnata una connessione, usa la connessione assegnata
     * Se non è stata assegnata una connessione, usa la connessione del Navigatore che la contiene
     * Altrimenti, usa la connessione del modulo di riferimento
     *
     * @return la connessione da utilizzare
     */
    public Connessione getConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            conn = this.connessione;
            if (conn == null) {
                nav = this.getNavigatore();
                if (nav != null) {
                    conn = nav.getConnessione();
                } else {
                    conn = this.getModulo().getConnessione();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    /**
     * Assegna alla scheda una connessione da utilizzare
     * per la lettura e la scrittura sul database
     * <p/>
     *
     * @param connessione da utilizzare
     */
    public void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }


    /**
     * Controlla se i campi della scheda sono stati modificati.
     * </p>
     * Prende in considerazione solo i campi fisici.
     *
     * @return modificati true se anche uno solo campo e' stato modificato <br>
     */
    public boolean isModificata() {
        /* variabili e costanti locali di lavoro */
        boolean modificati = false;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* il test si fa solo se il codice non è zero */
            if (this.getCodice() == 0) {
                continua = false;
            }// fine del blocco if

            /* traversa tutta la collezione */
            if (continua) {
                for (Campo campo : this.getCampi().values()) {
                    continua = false;

                    /* ha senso solo per i campi fisici ed i campi navigatore */
                    if (campo.getCampoDB().isCampoFisico()) {
                        continua = true;
                    } else {
                        if (campo.getCampoDati() instanceof CDNavigatore) {
                            continua = true;
                        }// fine del blocco if
                    }// fine del blocco if-else

                    if (continua) {
                        if (campo.getCampoDati().isModificato()) {
                            modificati = true;
                            break;
                        }// fine del blocco if
                    }// fine del blocco if
                }
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modificati;
    }


    /**
     * Recupera il componente di riferimento visualizzato
     * su tutte le pagine della scheda nella status bar.
     * <p/>
     * Chiamato dalla StatusBar quando necessita di aggiornamento.
     * Questo metodo non può essere sovrascritto
     * Eventualmente sovrascrivere getComponenteRiferimento()
     *
     * @return il componente di riferimento
     */
    public final JComponent getCompSB() {
        /* variabili e costanti locali di lavoro */
        JComponent comp = null;
        try {    // prova ad eseguire il codice
            if (this.getCodice() > 0) {
                comp = this.getComponenteRiferimento();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Recupera il componente di riferimento visualizzato
     * su tutte le pagine della scheda nella status bar.
     * <p/>
     * Chiamato dalla StatusBar quando necessita di aggiornamento.
     * Sovrascritto dalle sottoclassi
     * Nella classe base rende una JLabel con il testo di riferimento
     * Sovrascrivere se si vuole utilizzare un altro componente
     *
     * @return il componente di riferimento
     */
    protected JComponent getComponenteRiferimento() {
        /* variabili e costanti locali di lavoro */
        JComponent comp = null;
        try {    // prova ad eseguire il codice
            comp = this.getLabelRiferimento();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Restituisce testo di riferimento visualizzato
     * su tutte le pagine della scheda nella status bar.
     * <p/>
     * Sovrascritto dalle sottoclassi<br>
     * Nella classe base ritorna stringa vuota <br>
     * Sovrascrivere per personalizzare il testo mostrato nella
     * statusBar della scheda<br>
     *
     * @return il testo di riferimento
     */
    protected String getTestoRiferimento() {
        return "";
    }


    /**
     * Restituisce la Jlabel di riferimento visualizzato
     * su tutte le pagine della scheda nella status bar.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @return il testo di riferimento
     */
    private JLabel getLabelRiferimento() {
        /* variabili e costanti locali di lavoro */
        JLabel label = null;
        String stringa;

        try {    // prova ad eseguire il codice
            stringa = this.getTestoRiferimento();
            label = this.getLabelRifStato();
            label.setText(stringa);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }


    /**
     * Indica se la scheda sta presentando un nuovo record
     * <p/>
     *
     * @return true se la scheda sta presentando un nuovo record
     */
    public boolean isNuovoRecord() {
//        /* variabili e costanti locali di lavoro */
//        boolean isNuovo = false;
//        Navigatore nav;
//
//        try { // prova ad eseguire il codice
//
//            nav = this.getNavigatore();
//            if (nav != null) {
//                isNuovo = nav.isNuovoRecord();
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return isNuovo;

        /* valore di ritorno */
        return this.nuovoRecord;
    }


    /**
     * Regola il flag di Nuovo Record della scheda
     * <p/>
     *
     * @param flag true se la scheda sta presentando un  nuovo record
     */
    public void setNuovoRecord(boolean flag) {
        this.nuovoRecord = flag;
    }


    /**
     * Flag - ritorna true se la scheda usa la status bar.
     * <p/>
     *
     * @return true se usa la status bar
     */
    public boolean isUsaStatusBar() {
        return isUsaStatusBar;
    }


    public void setUsaStatusBar(boolean usaStatusBar) {
        isUsaStatusBar = usaStatusBar;
    }


    /**
     * Ritorna la status bar della scheda.
     * <p/>
     *
     * @return la status bar della scheda
     */
    public SchedaStatusBar getStatusBar() {
        return statusBar;
    }


    private void setStatusBar(SchedaStatusBar statusBar) {
        this.statusBar = statusBar;
    }


    private JLabel getLabelRifStato() {
        return labelRifStato;
    }


    private void setLabelRifStato(JLabel labelRifStatus) {
        this.labelRifStato = labelRifStatus;
    }


    public String getNomeChiave() {
        return nomeChiave;
    }


    public void setNomeChiave(String nomeChiave) {
        this.nomeChiave = nomeChiave;
    }


    /*
     * Ritorna true se il record è stato registrato
     * <p>
     * @return true se registrato
     */
    public boolean isRegistrato() {
        return registrato;
    }


    private void setRegistrato(boolean registrato) {
        this.registrato = registrato;
    }


//    /*
//    * Ritorna true se il record è stato abbandonato
//    * <p>
//    * @return true se abbandonato
//    */
//    public boolean isAbbandonato() {
//        return abbandonato;
//    }
//
//
//    private void setAbbandonato(boolean abbandonato) {
//        this.abbandonato = abbandonato;
//    }


    /**
     * Ritorna la scheda base.
     * <p/>
     */
    public SchedaBase getSchedaBase() {
        return this;
    }

}// fine della classe
