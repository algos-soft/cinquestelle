/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      4-ott-2006
 */
package it.algos.base.combo;

import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.azione.adapter.AzAdapterKey;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.campo.video.CVCombo;
import it.algos.base.campo.video.CVNumero;
import it.algos.base.campo.video.CVTestoField;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleScheda;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.tavola.Tavola;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.CampoValore;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Componente selettore dei valori da modulo linkato.
 * </p>
 * E' costituito da un campo editabile, una lista di scelta
 * e tre bottoni per aggiungere valori, modificare il valore
 * corrente e visualizzare la lista
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 4-ott-2006 ore 11.47.24
 */
public final class ComboTavola extends ComboBase {

    /* numero di righe visualizzate nella lista */
    private static final int NUM_RIGHE = 8;

    /* navigatore per visualizzare la lista */
    private Navigatore navigatore;

    /* campo interno utilizzato per editare il testo
     * il pannello componenti di questo campo e' inserito
     * nel pannello componenti del campo combo */
    private Campo campoInterno;

    /* bottone Nuovo Record */
    private JButton botNuovo;

    /* bottone Modifica Record */
    private JButton botModifica;

    /* bottone Mostra Lista */
    private JButton botLista;

    /* Componente grafico da visualizzare
     * contiene il campo editabile e i bottoni */
    private JComponent comp;

    /* finestra contenente la lista */
    private Window finestra;

    /* acceso quando il valore visualizzato è valido
     * (corrispondente al codice presente nel campo video)*/
    private boolean valido;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo che contiene questo oggetto grafico
     */
    public ComboTavola(CVCombo campoVideo) {
        /* rimanda al costruttore della superclasse */
        super(campoVideo);

        /* regolazioni iniziali di riferimenti e variabili */
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
        try { // prova ad eseguire il codice

            /* usa nuovo e modifica dal campo video di riferimento */
            this.setUsaNuovo(this.getCampoVideo().isUsaNuovo());
            this.setUsaModifica(this.getCampoVideo().isUsaModifica());

            /* crea e registra i bottoni */
            this.creaBottoni();

            /* crea e registra il campo interno */
            this.creaCampoInterno();

            /* crea, assembla e registra il componente video */
            this.creaComponente();

            /* Crea e registra il Navigatore per la gestione della lista */
            this.creaNavigatore();

            /* regola la larghezza di default */
            this.setLarghezza(LARGHEZZA_DEFAULT);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


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
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        int larghezza;

        super.inizializza();

        try { // prova ad eseguire il codice

            /* regola la larghezza del componente editabile
             * pari alla larghezza scheda del campo di riferimento  */
            larghezza = this.getCampoRif().getCampoScheda().getLarghezzaComponenti();
            this.setLarghezza(larghezza);

            /* inizializza il campo interno */
            this.getCampoInterno().inizializza();

            /* inizializza e avvia il navigatore */
            nav = this.getNavigatore();
            nav.inizializza();
            nav.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void avvia() {
        this.getCampoInterno().avvia();
        super.avvia();
    }


    public void aggiungeListener(Portale portale) {
        /* variabili e costanti locali di lavoro */
        JComponent compTesto;
        JButton bot;

        try { // prova ad eseguire il codice

            /* aggiunge i listener al campo interno */
            compTesto = this.getCompTesto();
            compTesto.addKeyListener(new AzioneCarattereInterno());

            /* azioni dei bottoni */
            bot = this.getBotNuovo();
            bot.addActionListener(new AzioneBotNuovo());
            bot = this.getBotModifica();
            bot.addActionListener(new AzioneBotModifica());
            bot = this.getBotLista();
            bot.addActionListener(new AzioneBotLista());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Crea e registra il campo interno.
     * <p/>
     * Se il campo linkato è testo crea un campo testo
     * Se il campo linkato è numero crea un campo intero
     * Metodo invocato dal ciclo inizia del costruttore <br>
     */
    private void creaCampoInterno() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Campo campoLink;
        boolean continua;
        CampoVideo cv = null;
        String nomeInterno = "internocombotavola";

        try {    // prova ad eseguire il codice

            /* recupera il campo linkato */
            campoLink = this.getCampoLink();
            continua = campoLink != null;

            if (continua) {

                /* se testo, crea un campo testo */
                if (campoLink.isTesto()) {
                    campo = CampoFactory.testo(nomeInterno);
                    cv = new CVCampoInternoTesto(campo);
                }// fine del blocco if

                /* se numero, crea un campo numero */
                if (campoLink.isNumero()) {
                    campo = CampoFactory.intero(nomeInterno);
                    cv = new CVCampoInternoNumero(campo);
                }// fine del blocco if

                /* sostituisce il campo video */
                campo.setCampoVideo(cv);

                /* registra il campo interno */
                this.setCampoInterno(campo);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea e registra i bottoni.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     */
    private void creaBottoni() {
        /* variabili e costanti locali di lavoro */
        JButton botNuovo;
        JButton botModifica;
        JButton botLista;

        try { // prova ad eseguire il codice

            /* crea i bottoni con le icone piccole */
            botNuovo = Lib.Risorse.getBottoneSmall("Nuovo16");
            botNuovo.setToolTipText("Crea un nuovo record");
            botModifica = Lib.Risorse.getBottoneSmall("Edit16");
            botModifica.setToolTipText("Apre la scheda del record");
            botLista = Lib.Risorse.getBottoneSmall("FrecciaGiu16");
            botLista.setToolTipText("Mostra la lista di scelta");

            this.regolaBottone(botNuovo);
            this.regolaBottone(botModifica);
            this.regolaBottone(botLista);

            this.setBotNuovo(botNuovo);
            this.setBotModifica(botModifica);
            this.setBotLista(botLista);

            botModifica.setEnabled(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola graficamente un bottone.
     * <p/>
     *
     * @param bottone da regolare
     */
    private void regolaBottone(JButton bottone) {

        try {    // prova ad eseguire il codice
            bottone.setBorderPainted(false);
            bottone.setContentAreaFilled(false);
            bottone.setMargin(new Insets(0, 0, 0, 0));
            bottone.setFocusable(false);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * crea, assembla e registra il componente video
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     */
    private void creaComponente() {
        /* variabili e costanti locali di lavoro */
        Pannello pan;
        JComponent campoEdit;
        JButton botNuovo;
        JButton botModifica;
        JButton botLista;

        try {    // prova ad eseguire il codice

            campoEdit = this.getCampoInterno().getPannelloComponenti();
            botNuovo = this.getBotNuovo();
            botModifica = this.getBotModifica();
            botLista = this.getBotLista();

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setGapPreferito(2);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.add(campoEdit);

            /* aggiunge il bottone Nuovo */
            if (this.isUsaNuovo()) {
                pan.add(botNuovo);
            }// fine del blocco if

            /* aggiunge il bottone Modifica */
            if (this.isUsaModifica()) {
                pan.add(botModifica);
            }// fine del blocco if

            /* aggiunge il bottone Lista */
            pan.add(botLista);

            /* registra il componente */
            this.setComp(pan.getPanFisso());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea e registra il Navogatore per la gestione della lista.
     * <p/>
     */
    private void creaNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try {    // prova ad eseguire il codice
            nav = new NavCombo();
            this.setNavigatore(nav);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea e registra la finestra per visualizzare la lista.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Inizialmente è invisibile <br>
     *
     * @return la finestra creata
     */
    private Window creaFinestraPop() {
        /* variabili e costanti locali di lavoro */
        Window finestra = null;
        Window parente = null;
        JComponent comp;
        Container cont;

        try { // prova ad eseguire il codice

            /* recupera il contenitore più esterno che
             * contiene componente editabile */
            comp = this.getCompTesto();
            cont = comp.getTopLevelAncestor();

            /* controlla che non sia nullo e che sia una window */
            if (cont != null) {
                if (cont instanceof Window) {
                    parente = (Window)cont;
                }// fine del blocco if
            }// fine del blocco if

            /* crea la finestra figlia della finestra esterna */
            finestra = new Window(parente);
            finestra.setAlwaysOnTop(true);
            finestra.setVisible(false);
            finestra.setFocusableWindowState(false);
            this.setFinestraPop(finestra);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return finestra;
    }


    /**
     * Sincronizza gli oggetti della GUI.
     * <p/>
     */
    private void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean abilita;

        try {    // prova ad eseguire il codice

            /* bottone modifica
             * deve esserci un valore valido e non vuoto */
            abilita = false;
            if (this.isValido()) {
                if (Lib.Testo.isValida(this.getTesto())) {
                    abilita = true;
                }// fine del blocco if
            }// fine del blocco if
            this.getBotModifica().setEnabled(abilita);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public JComponent getComponente() {
        return this.getComp();
    }


    /**
     * Ritorna il componente video di selezione (JTextField)
     * <p/>
     *
     * @return il componente selezionabile o editabile del combo
     */
    public JComponent getComponenteSelettore() {
        return this.getCompTesto();
    }


    /**
     * Calcola il filtro in base al testo presente.
     * <p/>
     * Legge il contenuto del campo edit <br>
     * Crea un filtro corrispondente <br>
     *
     * @return filtro
     */
    private Filtro getFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        Filtro filtroTot = null;
        Object valore;
        Campo campoLink;

        try { // prova ad eseguire il codice

            /* crea un filtro completo */
            filtroTot = new Filtro();

            /* aggiunge l'eventuale filtro del campo di riferimento */
            filtro = this.getCampoRif().getCampoDB().getFiltro();
            if (filtro != null) {
                filtroTot.add(filtro);
            }// fine del blocco if

            /* aggiunge il filtro del selettore */
            valore = this.getTesto();
            campoLink = this.getCampoLink();

            if (campoLink.isTesto()) {
                filtro = FiltroFactory.crea(campoLink, Filtro.Op.COMINCIA, valore);
            }// fine del blocco if-else

            if (campoLink.isNumero()) {
                filtro = FiltroFactory.crea(campoLink, Libreria.getInt(valore));
            }// fine del blocco if

            filtroTot.add(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }


    /**
     * Mostra un dialogo per inserire una nuova scheda della lista.
     * <p/>
     */
    private void mostraNuovo() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        int rec;
        Campo campoLink;
        Object valore;
        CampoValore cv;
        ArrayList<CampoValore> campi = new ArrayList<CampoValore>();

        try { // prova ad eseguire il codice

            /* chiude la lista se aperta */
            this.chiudiLista();

            /* crea una lista di valori preimpostati per il nuovo record,
             * con il valore presente nel campo edit */
            campoLink = this.getCampoRif().getCampoDB().getCampoValoriLinkato();
            if (campoLink != null) {
                valore = this.getTesto();
                cv = new CampoValore(campoLink, valore);
                campi.add(cv);
            }// fine del blocco if

            /* crea e presenta il nuovo record in un dialogo modale */
            mod = this.getModuloLinkato();
            rec = mod.getModuloBase().nuovoRecord(campi);

            /* controlla il ritorno */
            if (rec != -1) {
                this.setValoreValido(rec);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mostra un dialogo per modificare una scheda della lista.
     * <p/>
     */
    private void mostraModifica() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        int rec;
        boolean modificato;

        try { // prova ad eseguire il codice

            rec = this.getCodice();
            mod = this.getModuloLinkato();
            modificato = mod.presentaRecord(rec);
            if (modificato) {
                this.aggiornaGUI();
            }// fine del blocco if

            /* lancia un evento di avvvenuta presentazione di record */
            this.getCampoRif().fire(CampoBase.Evento.presentatoRecord);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mostra una finestra contenente la lista di scelta.
     * <p/>
     * Recupera il filtro dal contenuto del campo <br>
     * Regola il navigatore <br>
     * Avvia la lista del navigatore <br>
     * Mostra il navigatore <br>
     */
    private void apriLista() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        Window finestra;

        try { // prova ad eseguire il codice

            /* crea la finestra popup figlia della finestra corrente */
            finestra = this.getFinestraPop();
            if (finestra == null) {
                finestra = this.creaFinestraPop();
            }// fine del blocco if


            if (!finestra.isVisible()) {

                /* elimina la seleziona nella lista */
                lista = this.getLista();
                lista.eliminaSelezione();

                /* inserisce la lista nella finestra e la rende visibile */
                finestra = this.getFinestraPop();
                finestra.removeAll();
                finestra.add(this.getNavigatore().getPortaleLista());
                finestra.pack();
                finestra.setLocation(this.getPunto());
                finestra.setVisible(true);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rende invisibile la finestra che visualizza la lista
     * <p/>
     */
    private void chiudiLista() {
        /* variabili e costanti locali di lavoro */
        Window finestra;

        try { // prova ad eseguire il codice

            if (this.isFinestraPopVisibile()) {
                finestra = this.getFinestraPop();
                finestra.setVisible(false);

                /* ad ogni chiusura annullo la finestra
                * di modo che la successiva riapertura crei una
                * finestra posseduta dalla finestra correntemente attiva
                * se no rischi che la finestra attiva sia cambiata
                * e se uso la vecchia finestra non è più focusable */
                this.setFinestraPop(null);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Tasto Freccia su nel campo edit.
     * <p/>
     * Se la lista non è visibile, non fa nulla
     * Se la lista non ha righe selezionate, non fa nulla
     * Se la lista ha una riga selezionata, seleziona la riga precedente
     * Se la riga selezionata è la prima, chiude la finestra
     */
    private void frecciaSu() {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;
        int riga;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* controlla la finestra è visibile */
            if (continua) {
                continua = this.isFinestraPopVisibile();
            }// fine del blocco if

            /* controlla se una riga è selezionata */
            if (continua) {
                lista = this.getLista();
                continua = (lista.getQuanteRigheSelezionate() == 1);
            }// fine del blocco if

            if (continua) {
                riga = lista.getRigaSelezionata();
                if (riga > 0) {
                    riga--;
                    lista.setRigaVisibileSelezionata(riga);
                } else {
                    chiudiLista();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Tasto Freccia giu nel campo edit.
     * <p/>
     * Apre la lista se non già aperta
     * Se la lista non ha righe selezionate, seleziona la prima riga
     * Se la lista ha una riga selezionata, seleziona la riga successiva
     */
    private void frecciaGiu() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        int riga;

        try { // prova ad eseguire il codice

            /* apre la lista */
            if (!this.isFinestraPopVisibile()) {
                apriLista();
            }// fine del blocco if

            lista = this.getLista();
            if (lista.getQuanteRigheSelezionate() > 0) {
                riga = lista.getRigaSelezionata();
                riga++;
                if (lista.getNumRecordsVisualizzati() > riga) {
                    lista.setRigaVisibileSelezionata(riga);
                }// fine del blocco if
            } else {
                lista.setRigaSelezionata(0);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Tasto Enter nel campo edit.
     * <p/>
     * Se la lista è aperta e ha una riga selezionata
     * conferma la selezione
     */
    private void enter() {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            if (this.isFinestraPopVisibile()) {
                lista = this.getLista();
                if (lista.getQuanteRigheSelezionate() == 1) {
                    this.confermaSelezione();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Tasto Home nel campo edit.
     * <p/>
     * Se la lista è aperta va alla prima riga
     */
    private void home() {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            if (this.isFinestraPopVisibile()) {
                lista = this.getLista();
                if (lista.getNumRecordsVisualizzati() > 0) {
                    lista.setRigaVisibileSelezionata(0);
                }// fine del blocco if
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Tasto End nel campo edit.
     * <p/>
     * Se la lista è aperta va all'ultima riga
     */
    private void end() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        int righe;

        try { // prova ad eseguire il codice
            if (this.isFinestraPopVisibile()) {
                lista = this.getLista();
                righe = lista.getNumRecordsVisualizzati();
                if (righe > 0) {
                    lista.setRigaVisibileSelezionata(righe - 1);
                }// fine del blocco if
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se la finestra popup è esistente e visibile.
     * <p/>
     *
     * @return true se è esistente e visibile
     */
    private boolean isFinestraPopVisibile() {
        /* variabili e costanti locali di lavoro */
        boolean visibile = false;
        Window win;

        try {    // prova ad eseguire il codice
            win = getFinestraPop();
            if (win != null) {
                if (win.isVisible()) {
                    visibile = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return visibile;
    }


    /**
     * Conferma il record correntemente selezionato in lista
     * trasporta il valore selezionato in memoria
     * chiude la finestra della lista
     * <p/>
     */
    private void confermaSelezione() {
        /* variabili e costanti locali di lavoro */
        int codice;

        try { // prova ad eseguire il codice
            codice = getCodiceSelezionato();
            if (codice > 0) {
                setValoreValido(codice);
                chiudiLista();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna la GUI con il valore dal video.
     * <p/>
     * Recupera il valore video (codice) dal campo di riferimento<br>
     * Cerca il valore (testo) del campo linkato nel database <br>
     * Inserisce il valore testo nel campo edit<br>
     */
    public void aggiornaGUI() {
        /* variabili e costanti locali di lavoro */
        int codice;
        Object valore;
        String stringa;
        CampoDati cd;

        try { // prova ad eseguire il codice

            cd = this.getCampoDati();
            valore = cd.getVideo();
            if (valore != null) {
                codice = Libreria.getInt(valore);
                valore = this.getCampoDB().getValoreCampoLink(codice);
                stringa = Lib.Testo.getStringa(valore);
                this.setTesto(stringa);
                this.setValido(true);
                this.sincronizza();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Registra nel campo un valore valido
     * <p/>
     *
     * @param codice da registrare
     */
    private void setValoreValido(int codice) {

        try {    // prova ad eseguire il codice

            /* registra in memoria e risale alla GUI */
            this.getCampoRif().setValore(codice);
            this.setValido(true);
            this.sincronizza();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il codice correntemente
     * selezionato nella lista
     * <p/>
     *
     * @return il codice selezionato
     */
    private int getCodiceSelezionato() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Lista lista;

        try {    // prova ad eseguire il codice
            lista = this.getLista();
            codice = lista.getChiaveSelezionata();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Posizione (alto a sinistra) del navigatore.
     * <p/>
     * Sotto il campo edit <br>
     *
     * @return posizione
     */
    private Point getPunto() {
        /* variabili e costanti locali di lavoro */
        Point punto = null;
        Point puntoComp;
        Pannello panNav;
        int xComp;
        int yComp;
        int x1Nav;
        int y1Nav;
        int x2Nav;
        int y2Nav;
        int altComp;
        int altNav;
        int larNav;
        int wScreen;
        int hScreen;
        Dimension dimScreen;
        int marg = 20;

        try { // prova ad eseguire il codice

            puntoComp = this.getCompTesto().getLocationOnScreen();
            xComp = puntoComp.x;
            yComp = puntoComp.y;
            altComp = this.getCompTesto().getHeight();
            panNav = this.getNavigatore().getPortaleNavigatore();
            altNav = panNav.getPanFisso().getHeight();
            larNav = panNav.getPanFisso().getWidth();
            dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
            wScreen = dimScreen.width;
            hScreen = dimScreen.height;

            /* posizione ottimale (sotto al componente) */
            x1Nav = xComp;
            y1Nav = yComp + altComp;

            /* se va troppo vicino o fuori schermo in basso,
             * lo mette sopra al componente */
            y2Nav = y1Nav + altNav;
            if (y2Nav > hScreen - marg) {
                y1Nav = yComp - altNav;
            }// fine del blocco if

            /* se va troppo vicino o fuori schermo a destra,
             * lo sposta a sinistra quanto basta */
            x2Nav = x1Nav + larNav;
            if (x2Nav > wScreen - marg) {
                x1Nav = wScreen - larNav - marg;
            }// fine del blocco if

            /* se va troppo vicino o fuori schermo a sinistra,
             * lo sposta a destra quanto basta */
            if (x1Nav < marg) {
                x1Nav = marg;
            }// fine del blocco if

            punto = new Point(x1Nav, y1Nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return punto;
    }


    /**
     * Recupera il campo db del campo di riferimento.
     * <p/>
     *
     * @return il campo db di riferimento
     */
    private CampoDB getCampoDB() {
        /* variabili e costanti locali di lavoro */
        CampoDB campoDB = null;

        try { // prova ad eseguire il codice
            campoDB = this.getCampoRif().getCampoDB();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoDB;
    }


    /**
     * Recupera il campo linkato da visualizzare.
     * <p/>
     *
     * @return campo linkato da visualizzare
     */
    private Campo getCampoLink() {
        /* variabili e costanti locali di lavoro */
        Campo campoLink = null;
        CampoDB campoDb = null;

        try { // prova ad eseguire il codice
            campoDb = this.getCampoDB();
            if (campoDb != null) {
                campoLink = campoDb.getCampoValoriLinkato();

                if (campoLink == null) {
                    String nomeCampo = campoDb.getNomeCampoValoriLinkato();
                    String nomeMod = campoDb.getNomeModuloLinkato();
                    Modulo mod = Progetto.getModulo(nomeMod);
                    campoLink = mod.getCampo(nomeCampo);
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoLink;
    }


    /**
     * Ritorna il componente di testo visualizzato
     * <p/>
     *
     * @return il componente di testo
     */
    private JFormattedTextField getCompTesto() {
        /* variabili e costanti locali di lavoro */
        JFormattedTextField campoTesto = null;
        Campo campo;
        JComponent comp;
        try { // prova ad eseguire il codice
            campo = this.getCampoInterno();
            comp = campo.getComponenteVideo();
            if (comp instanceof JFormattedTextField) {
                campoTesto = (JFormattedTextField)comp;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return campoTesto;
    }


    /**
     * Ritorna il valore di testo presente nella GUI
     * <p/>
     *
     * @return il testo presente nella GUI
     */
    private String getTesto() {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        JTextComponent comp;

        try { // prova ad eseguire il codice

            comp = this.getCompTesto();
            if (comp != null) {
                testo = comp.getText();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Assegna un valore testo al campo edit.
     * <p/>
     *
     * @param testo da assegnare
     */
    private void setTesto(String testo) {
        /* variabili e costanti locali di lavoro */
        JTextComponent comp;
        Campo campoInterno;

        try {    // prova ad eseguire il codice

            campoInterno = this.getCampoInterno();
            campoInterno.setValore(testo);

            comp = this.getCompTesto();
            if (comp != null) {
                comp.getCaret().setDot(testo.length()); // cursore a destra
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il codice del record attualmente presente nel video
     *
     * @return il codice
     */
    private int getCodice() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        CampoDati cd;

        try { // prova ad eseguire il codice
            cd = this.getCampoRif().getCampoDati();
            codice = (Integer)cd.getVideo();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Restituisce la lista.
     * <p/>
     *
     * @return la lista gestita dal Navigatore
     */
    private Lista getLista() {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;
        Navigatore nav;

        try {    // prova ad eseguire il codice
            nav = this.getNavigatore();
            if (nav != null) {
                lista = nav.getLista();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Abilita/disabilita il combo.
     * <p/>
     * Se disabilitato sbiadisce tutti gli elementi
     * e impedisce di modificare il valore
     *
     * @param flag true per abilitare, false per disabilitare
     */
    public void setAbilitato(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            campo = this.getCampoInterno();
            campo.setModificabile(flag);
            this.getBotNuovo().setEnabled(flag);
            this.getBotModifica().setEnabled(flag);
            this.getBotLista().setEnabled(flag);
            this.chiudiLista();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la larghezza del componente selezionabile
     * o editabile del combo
     * <p/>
     * In un ComboLista, assegna la larghezza al JComboBox <br>
     * In un ComboTavola, assegna la larghezza al campo edit interno <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param larghezza da assegnare
     */
    public void setLarghezza(int larghezza) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampoInterno();
            if (campo != null) {
                campo.setLarScheda(larghezza);
            }// fine del blocco if

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
        JComponent comp;

        try { // prova ad eseguire il codice
            comp = this.getCompTesto();
            Lib.Comp.setPreferredHeigth(comp, altezza);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Invocato quando il combo ha
     * acquisito il fuoco in maniera permanente.
     * <p/>
     * trasferisce il fuoco al campo interno
     *
     * @param e l'evento fuoco
     */
    public void focusGainedComponente(FocusEvent e) {
        this.getCampoInterno().grabFocus();
    }


    /**
     * Invocato quando il combo ha
     * perso il fuoco in maniera permanente.
     * <p/>
     * Gira la chiamata al campo interno
     *
     * @param e l'evento fuoco
     */
    public void focusLost(FocusEvent e) {
        this.getCampoInterno().getCampoVideo().uscitaCampo(e);
    }


    private Campo getCampoRif() {
        return this.getCampoVideo().getCampoParente();
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
            cd = this.getCampoRif().getCampoDati();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cd;
    }


    private Navigatore getNavigatore() {
        return navigatore;
    }


    private void setNavigatore(Navigatore navigatore) {
        this.navigatore = navigatore;
    }


    private Campo getCampoInterno() {
        return campoInterno;
    }


    private void setCampoInterno(Campo campoInterno) {
        this.campoInterno = campoInterno;
    }


    private JButton getBotNuovo() {
        return botNuovo;
    }


    private void setBotNuovo(JButton botNuovo) {
        this.botNuovo = botNuovo;
    }


    private JButton getBotModifica() {
        return botModifica;
    }


    private void setBotModifica(JButton botModifica) {
        this.botModifica = botModifica;
    }


    private JButton getBotLista() {
        return botLista;
    }


    private void setBotLista(JButton botLista) {
        this.botLista = botLista;
    }


    private JComponent getComp() {
        return comp;
    }


    private void setComp(JComponent comp) {
        this.comp = comp;
    }


    private Window getFinestraPop() {
        return finestra;
    }


    private void setFinestraPop(Window finestra) {
        this.finestra = finestra;
    }


    private boolean isValido() {
        return valido;
    }


    private void setValido(boolean valido) {
        this.valido = valido;
    }


    /**
     * Ritorna il modulo linkato.
     * <p/>
     *
     * @return il modulo linkato
     */
    private Modulo getModuloLinkato() {
        /* variabili e costanti locali di lavoro */
        Modulo mod = null;

        try {    // prova ad eseguire il codice
            mod = getCampoDB().getModuloLinkato();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mod;
    }


    /**
     * Modifica del testo nel campo editabile.
     * <p/>
     * Invocato dall'evento Memoria Modificata del campo editabile
     */
    private void edit() {
        /* variabili e costanti locali di lavoro */
        String valore;
        Lista lista;
        int quanti;

        try { // prova ad eseguire il codice

            /* aggiorna la lista in base al valore presente nel campo */
            this.aggiornaFiltro();

            /* se c'è un valore:
               - se esistono dei record corrispondenti, apre la lista
               - se non esistono dei record corrispondenti, chiude la lista
               se non c'è un valore:
               - chiude la lista*/
            valore = getTesto();
            if (Lib.Testo.isValida(valore)) {
                lista = this.getLista();
                quanti = lista.getNumRecordsVisualizzati();
                if (quanti > 0) {
                    apriLista();
                    if (quanti == 1) {
                        lista.setRigaSelezionata(0);
                    }// fine del blocco if
                } else {
                    chiudiLista();
                }// fine del blocco if-else
            } else {
                setValoreValido(0);
                setValido(false);
                chiudiLista();
            }// fine del blocco if-else

            sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna il filtro della lista in base al valore
     * attualmente contenuto nel campo edit.
     * <p/>
     */
    private void aggiornaFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        Navigatore nav;

        try {    // prova ad eseguire il codice
            /* aggiorna la lista in base al valore presente nel campo */
            filtro = this.getFiltro();
            nav = this.getNavigatore();
            nav.setFiltroCorrente(filtro);
            nav.getLista().eliminaSelezione();
            nav.aggiornaLista();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Azione di gestione dei tasti premuti nel componente edit.
     * <p/>
     * Se è un carattere speciale (cursore) esegue l'azione corrispondente
     * Altrimenti lo considera un testo ed esegue il metodo edit()
     */
    private class AzioneCarattereInterno extends AzAdapterKey {

        /**
         * keyReleased, da KeyListener.
         * <p/>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void keyReleased(KeyEvent unEvento) {
            int cod;
            boolean continua = true;

            try { // prova ad eseguire il codice

                cod = unEvento.getKeyCode();
                unEvento.consume();

                if (cod == KeyEvent.VK_UP) {
                    frecciaSu();
                    continua = false;
                }// fine del blocco if
                if (cod == KeyEvent.VK_DOWN) {
                    frecciaGiu();
                    continua = false;
                }// fine del blocco if
                if (cod == KeyEvent.VK_ENTER) {
                    enter();
                    continua = false;
                }// fine del blocco if
                if (cod == KeyEvent.VK_HOME) {
                    home();
                    continua = false;
                }// fine del blocco if
                if (cod == KeyEvent.VK_END) {
                    end();
                    continua = false;
                }// fine del blocco if
                if (cod == KeyEvent.VK_ESCAPE) {
                    continua = false;
                }// fine del blocco if
                if (cod == KeyEvent.VK_PAGE_UP) {
                    continua = false;
                }// fine del blocco if
                if (cod == KeyEvent.VK_PAGE_DOWN) {
                    continua = false;
                }// fine del blocco if
                if (cod == KeyEvent.VK_SHIFT) {
                    continua = false;
                }// fine del blocco if

                /* se arriva qui si considera un testo */
                if (continua) {
                    edit();
                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


    } // fine della classe interna


    /**
     * Runnable invocato quando un componente del combo prende il fuoco
     * </p>
     * Se il fuoco non proviene da un altro componente del combo
     * effettua la procedura di entrata definitiva dal campo
     */
    private final class Checkin implements Runnable {

        public void run() {

            try { // prova ad eseguire il codice

                getCampoRif().getCampoVideo().entrataCampo();

                sincronizza();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }

    } // fine della classe 'interna'


    /**
     * Runnable invocato quando un componente del combo perde il fuoco
     * </p>
     * Se il fuoco non è andato a un altro componente del combo
     * effettua l'uscita definitiva dal campo
     */
    private final class Checkout implements Runnable {

        public void run() {

            try { // prova ad eseguire il codice

                if (isFinestraPopVisibile()) {
                    chiudiLista();
                }// fine del blocco if

                getCampoRif().getCampoVideo().uscitaCampo();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }

    } // fine della classe 'interna'


    /**
     * Inner class per gestire l'azione.
     */
    private class AzioneBotNuovo extends AzAdapterAction {

        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato di questa classe */
                mostraNuovo();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzioneBotModifica extends AzAdapterAction {

        /**
         * Costruttore completo senza parametri.<br>
         */
        public AzioneBotModifica() {
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
        }


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato di questa classe */
                mostraModifica();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzioneBotLista extends AzAdapterAction {

        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            /* variabili e costanti locali di lavoro */

            try { // prova ad eseguire il codice
                if (isFinestraPopVisibile()) {
                    chiudiLista();
                } else {
                    aggiornaFiltro();
                    getCampoInterno().getCampoVideo().selectAll();
                    apriLista();
                }// fine del blocco if-else
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Navigatore interno del campo combo.
     * </p>
     */
    private final class NavCombo extends NavigatoreLS {

        private static final boolean USA_BOTTONI = false;


        /**
         * Costruttore completo senza parametri.<br>
         */
        public NavCombo() {
            /* rimanda al costruttore della superclasse */
            super(getModuloLinkato());

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
            Vista vista;

            try { // prova ad eseguire il codice

                this.setUsaPannelloUnico(true);
                this.setUsaFinestra(false);
                this.getPortaleLista().setUsaStatusBar(true);
                this.getPortaleLista().getToolBar().setTipoIcona(ToolBar.ICONA_PICCOLA);
                this.setRigheLista(NUM_RIGHE);
                this.setUsaCarattereFiltro(false);
                this.getLista().getTavola().setTableHeader(null);
                this.getLista().getTavola().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                this.getLista().setUsaFiltriPop(false);

                if (USA_BOTTONI) {
                    this.setUsaDuplicaRecord(false);
                    this.setUsaElimina(false);
                    this.setUsaFrecceSpostaOrdineLista(false);
                    this.setUsaRicerca(false);
                    this.setUsaSelezione(false);
                    this.setUsaStampaLista(false);
                } else {
                    this.getPortaleLista().setPosToolbar(ToolBar.Pos.nessuna);
                }// fine del blocco if-else

                /* crea la vista con i campi e la assegna alla lista */
                vista = this.creaVista();
                this.getLista().setVista(vista);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public void inizializza() {
            /* variabili e costanti locali di lavoro */
            Lista lista;
            Tavola tavola;

            try { // prova ad eseguire il codice

                int a = 87;

                super.inizializza();

                /* rende sempre la lista non editabile */
                lista = this.getLista();
                if (lista != null) {
                    tavola = lista.getTavola();
                    if (tavola != null) {
                        tavola.setModificabile(false);
                    }// fine del blocco if
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }// fine del metodo inizializza


        public void avvia() {
            /* variabili e costanti locali di lavoro */
            Lista lista;
            Connessione conn;


            try { // prova ad eseguire il codice

                /**
                 * todo qui faceva super.avvia() e basta
                 * todo ho ricopiato tutto quello chhe sta sopra per poterlo
                 * todo disabilitare selettivamente - al termine dei test
                 * todo cancellare tutto e rimettere super.avvia()
                 * todo alex 26-03-08
                 */
                super.avvia();

//                /* un navigatore LS all'avvio chiude sempre la scheda */
//                this.richiediChiusuraSchedaNoDialogo(false, true);
//
//
//                /* se non è inizializzato lo inizializza ora */
//                if (!this.isInizializzato()) {
//                    this.inizializza();
//                }// fine del blocco if
//
//                /* apre la connessione se non già aperta */
//                conn = this.getConnessione();
//                if (conn != null) {
//                    if (!conn.isOpen()) {
//                        conn.open();
//                    }// fine del blocco if
//                }// fine del blocco if
//
//                /* avvia il portale lista */
//                if (this.getPortaleLista() != null) {
//                    this.getPortaleLista().avvia();
//                }// fine del blocco if
//
//                /* avvia il portale scheda */
//                if (this.getPortaleScheda() != null) {
//                    this.getPortaleScheda().avvia();
//                }// fine del blocco if
//
//                /* avvia il portale navigatore */
//                if (this.getPortaleNavigatore() != null) {
//                    this.getPortaleNavigatore().avvia();
//                }// fine del blocco if
//
//                /* toglie l'eventuale filtro corrente alla lista */
//                lista = this.getLista();
//                if (lista != null) {
//                    lista.setFiltroCorrente(null);
//                }// fine del blocco if
//
//                /* all'avvio forza un evento di selezione modificata
//                 * per aggiornare la lista */
//                this.selezioneModificata();
//
//                /* se usa pannello unico, all'avvio visualizza sempre
//                 * il componente A */
//                if (this.isUsaPannelloUnico()) {
//                    this.visualizzaComponenteA();
//                }// fine del blocco if
//
//                /* sincronizza il navigatore */
//                this.sincronizza();
//
//                /* genera un evento del modulo */
//                this.getModulo().fire(ModuloBase.Evento.mostra);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }// fine del metodo


        /**
         * Crea la vista per il Navigatore.
         * <p/>
         * Aggiunge alla vista il campo principale di selezione
         * e gli eventuali campi aggiuntivi discriminanti
         *
         * @return la Vista creata
         */
        private Vista creaVista() {
            /* variabili e costanti locali di lavoro */
            Vista vista = null;
            ArrayList<Campo> campiAggiuntivi;

            try {    // prova ad eseguire il codice

                /* crea la vista col campo principale di selezione */
                vista = new Vista(this.getModulo());
                vista.addCampo(getCampoDB().getCampoValoriLinkato());

                /* aggiunge alla vista i campi aggiuntivi */
                campiAggiuntivi = getCampoVideo().getCampiAggiuntivi();
                for (Campo campo : campiAggiuntivi) {
                    vista.addCampo(campo);
                }

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return vista;
        }


        /**
         * Override della creazione standard del portale Scheda.
         * <p/>
         * Questo navigatore non usa il portale scheda e quindi non lo crea.
         */
        protected PortaleScheda creaPortaleScheda() {
            return null;
        } // fine del metodo


        /**
         * Azione modifica record in una <code>Lista</code>.
         * <p/>
         * Metodo invocato dal Gestore Eventi <br>
         * Sincronizza lo stato della GUI <br>
         * (la sincronizzazione avviene DOPO, ma viene lanciata da qui) <br>
         * Controlla e chiude una eventuale scheda aperta <br>
         * Chiede la chiave alla lista master <br>
         * Avvia la scheda, passandogli il codice-chiave <br>
         *
         * @return true se eseguito correttamente
         */
        @Override
        protected boolean modificaRecord() {
            try { // prova ad eseguire il codice
                confermaSelezione();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            return false;
        }

    } // fine della classe 'interna'


    /**
     * Campo video del campo interno (per editing di testo).
     * </p>
     */
    private final class CVCampoInternoTesto extends CVTestoField {

        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param campoParente il campo parente
         */
        public CVCampoInternoTesto(Campo campoParente) {
            /* rimanda al costruttore della superclasse */
            super(campoParente);
        }


        public void entrataCampo() {
            super.entrataCampo();
            SwingUtilities.invokeLater(new Checkin());
        }


        public void uscitaCampo() {
            super.uscitaCampo();
            SwingUtilities.invokeLater(new Checkout());
        }


        /**
         * Blocco il fire di fuoco acquisito per il campo interno<br>
         * L'evento non deve uscire al di fuori del combo<br>
         */
        protected void fireFuocoPreso() {
        }


        /**
         * Blocco il fire di fuoco perso per il campo interno<br>
         * L'evento non deve uscire al di fuori del combo<br>
         */
        protected void fireFuocoPerso() {
        }


    } // fine della classe 'interna'


    /**
     * Campo video del campo interno (per editing di numeri).
     * <p/>
     */
    private final class CVCampoInternoNumero extends CVNumero {

        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param campoParente il campo parente
         */
        public CVCampoInternoNumero(Campo campoParente) {
            /* rimanda al costruttore della superclasse */
            super(campoParente);
        }


        public void entrataCampo() {
            super.entrataCampo();
            SwingUtilities.invokeLater(new Checkin());
        }


        public void uscitaCampo() {
            super.uscitaCampo();
            SwingUtilities.invokeLater(new Checkout());
        }


        /**
         * Blocco il fire di fuoco acquisito per il campo interno<br>
         * L'evento non deve uscire al di fuori del combo<br>
         */
        protected void fireFuocoPreso() {
        }


        /**
         * Blocco il fire di fuoco perso per il campo interno<br>
         * L'evento non deve uscire al di fuori del combo<br>
         */
        protected void fireFuocoPerso() {
        }


    } // fine della classe 'interna'


}// fine della classe
