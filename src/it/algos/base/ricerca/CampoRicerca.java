/**
 * Title:     CampoRicerca
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      16-ago-2005
 */
package it.algos.base.ricerca;

import it.algos.base.azione.adapter.AzAdapterFocus;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutFlusso;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Campo per il dialogo di ricerca.
 * </p>
 * Gestisce un campo singolo o una coppia di campi con
 * range di ricerca. <br>
 * Gestisce la logica e il componente video. <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 16-ago-2005 ore 14.36.34
 */
public class CampoRicerca extends PannelloFlusso {

    /* ricerca di riferimento */
    private RicercaBase ricerca = null;

    /* etichetta del campo di ricerca */
    private String etichetta = null;

    /* campo originale sul quale si effettua la ricerca */
    private Campo campoOriginale;

    /* campo principale di ricerca presentato all'utente */
    private Campo campo1 = null;

    /* secondo campo di ricerca presentato all'utente (quando si usa il range) */
    private Campo campo2 = null;

    /* flag - indica se usa campo singolo o range */
    private boolean usaRange = false;

    /* pannello interno contenitore del campo singolo o della coppia di campi */
    private PannelloFlusso pannelloCampi = null;

    /* popup di selezione dell'operatore di ricerca (solo campo singolo)*/
    private Campo popOperatori;

    /* popup di selezione della condizione di unione con la ricerca precedente */
    private Campo popUnioni;

    /* operatore di ricerca - supera il default del campo */
    private String operatore;

    /* bottone di aggiunta condizione */
    private JButton botAdd;

    /* bottone di rimozione condizione */
    private JButton botRemove;

    /* pannello contenente i bottoni di aggiunta / rimozione condizione */
    private Pannello panBottoni;


    /* larghezza del campo nella scheda (se range vale per ognuno dei due campi) */
    private int larghezza;

    /**
     * se usa il popup unione con altri Campi di ricerca
     * di default lo usa
     * se non lo usa il popup viene disabilitato e la condizione è sempre AND
     */
    private boolean usaPopUnioni=true;

    /**
     * Costruttore completo con parametri.
     * <p/>
     * Ricerca su campo singolo con operatore
     *
     * @param ricerca di riferimento
     * @param campo da ricercare (nome o oggetto campo)
     * @param operatore da utilizzare per la ricerca (eventualmente modificabile da popup)
     * @param usaRange true per usare range di ricerca
     *
     * @see Operatore
     */
    public CampoRicerca(RicercaBase ricerca, Campo campo, String operatore, boolean usaRange) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setRicerca(ricerca);
        this.setCampoOriginale(campo);
        this.setCampo1(campo);
        this.setOperatore(operatore);
        this.setUsaRange(usaRange);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
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
        /* variabili e costanti locali di lavoro */
        Campo campo1;
        Campo campo2;
        Campo campo;
        Campo campoOriginale;
        PannelloFlusso panCampi;
        Campo campoPopOp;

        try { // prova ad eseguire il codice

            /* regola l'etichetta dal campo ricerca */
            this.regolaEtichetta();

            /* crea e regola i pannelli */
            this.creaPannelli();

            /* crea il campo popup operatori di ricerca disponibili */
            this.creaPopOperatori();

            /* crea il campo popup unioni */
            this.creaPopUnioni();

            /* se non usa il pop unioni lo disabilita */
            if (!this.isUsaPopUnioni()) {
                Campo popUnioni = this.getPopUnioni();
                if (popUnioni != null) {
                    popUnioni.setModificabile(false);
                }// fine del blocco if
            }// fine del blocco if


            /* crea il pannello con i bottoni di aggiunta e rimozione */
            this.creaPanBottoni();

            /* recupera il riferimento al campo originale passato nel costruttore */
            campoOriginale = this.getCampo1();

            if (campoOriginale != null) {

                /* crea sempre il primo campo di ricerca */
                /* crea eventualmente il secondo campo di ricerca */
                campo1 = this.creaPrimoCampo();
                campo2 = this.creaSecondoCampo();

                /* avvia i due campi
                 * fatto dopo che entrambi sono stati creati perche' nei
                 * booleani la creazione del secondo campo
                 * influisce anche sul primo! */
                if (campo1 != null) {
                    campo1.inizializza();
                }// fine del blocco if

                if (campo2 != null) {
                    campo2.inizializza();
                }// fine del blocco if

            }// fine del blocco if

            /* Recupera i campi e li aggiunge al pannello campi interno. */
            panCampi = this.getPannelloCampi();

            /* aggiunge l'eventuale popup operatori di ricerca */
            campoPopOp = this.getPopOperatori();
            if (campoPopOp != null) {
                panCampi.add(this.getPopOperatori());
            }// fine del blocco if

            /* aggiunge il primo campo */
            campo = this.getCampo1();
            if (campo != null) {
                panCampi.add(campo);
            }// fine del blocco if

            /* aggiunge l'eventuale secondo campo */
            campo = this.getCampo2();
            if (campo != null) {
                panCampi.add(campo);
            }// fine del blocco if

            /* aggiunge il pannello campi a questo contenitore */
            this.add(this.getPopUnioni());
            this.add(panCampi);

            /* aggiunge il pannello bottoni di aggiunta e rimozione */
            this.add(this.getPanBottoni());

            /* resetta l'oggetto */
            this.reset();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public void avvia() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            campo = this.getCampo1();
            if (campo != null) {
                campo.avvia();
            }// fine del blocco if

            campo = this.getCampo2();
            if (campo != null) {
                campo.avvia();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo avvia


    /**
     * Regola l'etichetta del CampoRicerca.
     * <p/>
     */
    private void regolaEtichetta() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Campo campo;
        String testo;
        String testoEtichetta = "";
        String testoCampo;
        Modulo moduloRicerca;
        Modulo moduloCampo;
        CampoDB campodb;
        Campo campoLinkato;

        try {    // prova ad eseguire il codice

            /* se una etichetta e' gia' stata assegnata non la modifica */
            if (Lib.Testo.isValida(this.getEtichetta())) {
                continua = false;
            }// fine del blocco if

            /* recupera e registra il testo per l'etichetta */
            if (continua) {
                campo = this.getCampo1();

                /* se e' un campo linkato, lo sostituisce
                 * con il campo valori linkato */
                if (campo.getCampoDB().isLinkato()) {
                    campodb = campo.getCampoDB();
                    campoLinkato = campodb.getCampoValoriLinkato();
                    if (campoLinkato != null) {
                        campo = campoLinkato;
                    }// fine del blocco if
                }// fine del blocco if

                /* se e' un booleano usa il testo del componente
                 * se no usa il testo dell'etichetta */
                if (campo.isBooleano()) {
                    testo = campo.getCampoVideo().getTestoComponente();
                } else {
                    testo = campo.getTestoEtichetta();
                }// fine del blocco if-else

                if (Lib.Testo.isValida(testo)) {
                    testoEtichetta = testo;
                } else {
                    testoEtichetta = campo.getNomeInterno();
                }// fine del blocco if-else

                /* se il campo non è dello stesso modulo della ricerca
                 * costruisce l'etichetta come "nomeModulo (nomeCampo)" */
                moduloRicerca = this.getModulo();
                moduloCampo = campo.getModulo();
                if (!moduloRicerca.equals(moduloCampo)) {
                    testoCampo = testoEtichetta;
                    testoEtichetta = moduloCampo.getNomeModulo();
                    testoEtichetta += " (";
                    testoEtichetta += testoCampo;
                    testoEtichetta += ")";
                }// fine del blocco if

                this.setEtichetta(testoEtichetta);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Resetta il campo ricerca.
     * <p/>
     * Resetta i due campi
     */
    public void reset() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Campo campoPop;
        String operatore;
        ArrayList valori;
        String stringa;
        int i = 0;
        int pos = 0;

        try {    // prova ad eseguire il codice

            /* resetta il valore del primo campo */
            campo = this.getCampo1();
            if (campo != null) {
                campo.reset();
            }// fine del blocco if

            /* resetta il valore del secondo campo */
            campo = this.getCampo2();
            if (campo != null) {
                campo.reset();
            }// fine del blocco if

            /* se esiste il popup, regola il valore del popup operatori */
            campoPop = this.getPopOperatori();
            if (campoPop != null) {

                /* se e' stato registrato un operatore lo usa,
                 * altrimenti usa l'operatore di default del campo */
                if (this.getOperatore() != null) {
                    operatore = this.getOperatore();
                } else {
                    campo = this.getCampo1();
                    operatore = campo.getOperatoreRicercaDefault();
                }// fine del blocco if-else

                /* seleziona il valore nel popup */
                valori = campoPop.getCampoDati().getValoriInterni();
                for (Object valore : valori) {
                    i++;
                    if (valore instanceof String) {
                        stringa = (String)valore;
                        if (stringa.equals(operatore)) {
                            pos = i;
                            break;
                        }// fine del blocco if
                    }// fine del blocco if
                }  // fine del blocco for-each

                /* se non ha trovato quelllo di default usa il primo */
                if (pos == 0) {
                    pos = 1;
                }// fine del blocco if

                campoPop.setValore(pos);

            }// fine del blocco if

            /* regola il valore del popup unioni */
            campoPop = this.getPopUnioni();
            if (campoPop != null) {
                campoPop.setValore(1);  //AND
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea e regola i pannelli.
     * <p/>
     * Crea e regola il layout di questo oggetto <br>
     * Crea e regola il pannello interno contenente i campi <br>
     */
    private void creaPannelli() {

        /* variabili e costanti locali di lavoro */
        PannelloFlusso panCampi = null;
        Layout layout = null;
        int gap = 0;

        try {    // prova ad eseguire il codice

            /* crea il layout di questo pannello */
            layout = new LayoutFlusso(this, Layout.ORIENTAMENTO_ORIZZONTALE);
            layout.setUsaGapFisso(true);
            layout.setGapPreferito(10);
            layout.setAllineamento(Layout.ALLINEA_CENTRO);
            layout.setUsaScorrevole(false);
            this.setLayout(layout);

            /* crea e regola il pannello per i campi */
            if (this.isCampoPrincipaleBooleano()) {
                gap = 10;
            } else {
                gap = 20;
            }// fine del blocco if-else
            panCampi = new PannelloFlusso(this.getRicerca(), Layout.ORIENTAMENTO_ORIZZONTALE);
            panCampi.setUsaGapFisso(true);
            panCampi.setGapPreferito(gap);  // distanza tra un campo e l'altro
            panCampi.setUsaScorrevole(false);
            panCampi.creaBordo(5, this.getEtichetta());
            this.setPannelloCampi(panCampi);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea e registra il campo popup operatori di ricerca.
     * <p/>
     * Viene creato solo se la ricerca non è booleana e non usa il range
     *
     * @return il campo creato
     */
    private Campo creaPopOperatori() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Campo campoPop = null;
        Campo campo = null;
        ArrayList<String> listaOperatori = null;
        String chiave;


        try {    // prova ad eseguire il codice

            /* controlla che non usi il range */
            if (continua) {
                continua = !this.isUsaRange();
            }// fine del blocco if

            /* recupera la lista degli operatori disponibili */
            if (continua) {
                campo = this.getCampo1();
                listaOperatori = campo.getOperatoriRicerca();
                continua = listaOperatori.size() > 0;
            }// fine del blocco if

            /* crea il campo popup */
            if (continua) {
//                chiave = this.getCampo1().getChiaveCampo();
                chiave = "PopOp";
                campoPop = CampoFactory.comboInterno(chiave);
                campoPop.setLarScheda(80);
                campoPop.setValoriInterni(listaOperatori);
                campoPop.setUsaNuovo(false);
                campoPop.setUsaNonSpecificato(false);
                campoPop.decora().eliminaEtichetta();
                campoPop.setFocusable(false);
                campoPop.avvia();
            }// fine del blocco if

            /* registra il campo (puo' essere nullo)*/
            this.setPopOperatori(campoPop);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoPop;
    }


    /**
     * Crea e registra il campo popup operatori di ricerca.
     * <p/>
     * Viene creato solo se la ricerca non è booleana e non usa il range
     *
     * @return il campo creato
     */
    private Campo creaPopUnioni() {
        /* variabili e costanti locali di lavoro */
        String chiave;
        Campo campoPop = null;
        ArrayList<String> unioni;

        try { // prova ad eseguire il codice
//            chiave = this.getCampo1().getChiaveCampo();
            chiave = "PopUni";
            campoPop = CampoFactory.comboInterno(chiave);
            campoPop.setLarScheda(65);

            unioni = new ArrayList<String>();
            unioni.add(Filtro.Op.AND);
            unioni.add(Filtro.Op.OR);
            unioni.add(Filtro.Op.AND_NOT);

            campoPop.setValoriInterni(unioni);
            campoPop.setUsaNuovo(false);
            campoPop.setUsaNonSpecificato(false);
            campoPop.decora().eliminaEtichetta();
            campoPop.setFocusable(false);
            campoPop.avvia();

            /* registra il campo */
            this.setPopUnioni(campoPop);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoPop;
    }


    /**
     * Crea e registra il pannello bottoni di aggiunta e rimozione.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanBottoni() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;


        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(0);

            this.creaBottoni();

            pan.add(this.getBotRemove());
            pan.add(this.getBotAdd());

            this.setPanBottoni(pan);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;

    }


    /**
     * Crea e registra i bottoni di aggiunta e rimozione.
     * <p/>
     */
    private void creaBottoni() {
        /* variabili e costanti locali di lavoro */
        JButton b;

        try {    // prova ad eseguire il codice
            b = creaBottone(false);
            this.setBotRemove(b);
            b = creaBottone(true);
            this.setBotAdd(b);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea un singolo bottone.
     * <p/>
     *
     * @param flag true per bottone aggiungi false per bottone rimuovi
     *
     * @return il bottone creato
     */
    private JButton creaBottone(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JButton b = null;
        String nomeIcona;
        String nomeIconaPressed;
        String tooltip;
        Icon icona;
        ActionListener listener;

        try { // prova ad eseguire il codice

            if (flag) {
                nomeIcona = "addTondo16";
                nomeIconaPressed = "addTondoPress16";
                tooltip = "aggiunge una nuova condizione di ricerca";
                listener = new AzioneAggiungi();
            } else {
                nomeIcona = "removeTondo16";
                nomeIconaPressed = "removeTondoPress16";
                tooltip = "rimuove questa condizione di ricerca";
                listener = new AzioneRimuovi();
            }// fine del blocco if-else

            /* regola il bottone */
            b = new JButton();
            b.setMargin(new Insets(0, 0, 0, 0));
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setFocusable(false);
            b.setToolTipText(tooltip);

            /* assegna le icone */
            icona = Lib.Risorse.getIconaBase(nomeIcona);
            b.setIcon(icona);
            icona = Lib.Risorse.getIconaBase(nomeIconaPressed);
            b.setPressedIcon(icona);

            /* aggiunge l'azione */
            b.addActionListener(listener);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return b;
    }


    private class AzioneAggiungi implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            getRicerca().addCampoRicerca();
        }
    }


    private class AzioneRimuovi implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            getRicerca().removeCampoRicerca(getQuesto());
        }
    }


    /**
     * Ritorna questo stesso oggetto.
     * <p/>
     *
     * @return questo oggetto
     */
    private CampoRicerca getQuesto() {
        return this;
    }


    /**
     * Crea e registra il primo campo di ricerca.
     * <p/>
     * Invocato dal ciclo Inizializza
     *
     * @return il campo creato (non inizializzato)
     */
    private Campo creaPrimoCampo() {
        /* variabili e costanti locali di lavoro */
        Campo campoOriginale;
        Campo campoClone = null;
        UscitaPrimoCampo uscita;
        Component comp;

        try {    // prova ad eseguire il codice

            /* recupera il riferimento al campo originale passato nel costruttore */
            campoOriginale = this.getCampo1();

            /* Clona il primo campo e sostituisce il riferimento
             * se è un Timestamp ricercabile solo sulla porzione Data
             * crea un nuovo campo di tipo Data */
            if (campoOriginale.isTimestamp()) {
                if (campoOriginale.getCampoDati().isRicercaSoloPorzioneData()) {
                    campoClone = CampoFactory.data(campoOriginale.getNomeInterno());
                    campoClone.setModulo(campoOriginale.getModulo());
                }// fine del blocco if
            }// fine del blocco if
            if (campoClone == null) {
                campoClone = campoOriginale.clonaCampo();
            }// fine del blocco if

            this.setCampo1(campoClone);

            /* associa un listener per l'uscita dal campo */
            if (this.isUsaRange()) {
                uscita = new UscitaPrimoCampo();
                comp = this.getCampo1().getCampoVideo().getComponente();
                comp.addFocusListener(uscita);
            }// fine del blocco if

            /* regolazioni fisse per tutti i campi di ricerca */
            campoClone.setInizializzato(false);
            campoClone.setValidatore(null);
            campoClone.setInit(null);
            campoClone.setUsaNuovo(false);
            campoClone.setUsaNonSpecificato(true);
            campoClone.setAbilitato(true);
            campoClone.setModificabile(true);
            campoClone.decora().eliminaObbligatorio();
            campoClone.decora().eliminaEtichetta();
            campoClone.decora().eliminaLegenda();

            /* regolazioni per i soli campi testo */
            if (campoClone.isTesto()) {
                campoClone.setLarScheda(200);
                campoClone.setNumeroRighe(1); // per campi testo area
            }// fine del blocco if

            /* regolazioni per i soli campi numero */
            if (campoClone.isNumero()) {
                if (campoClone.getCampoDB().isLinkato()) {
                    campoClone.setLarScheda(200);
                } else {
                    campoClone.setLarScheda(70);
                }// fine del blocco if-else
            }// fine del blocco if

            /* regolazioni per i soli campi booleani */
            if (campoClone.isBooleano()) {
                campoClone.setLarScheda(50);
            }// fine del blocco if

            /* se usa il range aggiuge l'etichetta "da" */
            if (this.isUsaRange()) {
                campoClone.decora().etichettaSinistra("da");
            }// fine del blocco if

            /* regola ulteriormente la larghezza se specificata */
            if (this.getLarghezza()>0) {
                campoClone.setLarScheda(this.getLarghezza());
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoClone;

    }


    /**
     * Crea eventualmente il secondo campo di ricerca.
     * <p/>
     * Se il campo principale e' booleano, crea i due campi
     * indipendentemente dall'uso del range. <br>
     * altrimenti, controlla l'uso del range. <br>
     *
     * @return il campo creato (non inizializzato)
     */
    private Campo creaSecondoCampo() {
        /* variabili e costanti locali di lavoro */
        Campo campoOriginale = null;
        Campo campoClone = null;
        JToggleButton bottone1 = null;
        JToggleButton bottone2 = null;
        Sincronizzatore sincronizzatore = null;

        try {    // prova ad eseguire il codice
            campoOriginale = this.getCampo1();

            if (this.isCampoPrincipaleBooleano()) {

                campoClone = this.registraClone();
                campoOriginale.setTestoComponente("Si");
                campoClone.setTestoComponente("No");

                bottone1 = campoOriginale.getCampoVideo().getComponenteToggle();
                bottone2 = campoClone.getCampoVideo().getComponenteToggle();

                sincronizzatore = new Sincronizzatore(campoOriginale);
                bottone1.addChangeListener(sincronizzatore);

                sincronizzatore = new Sincronizzatore(campoClone);
                bottone2.addChangeListener(sincronizzatore);

            } else {

                if (this.isUsaRange()) {

                    /* crea un clone del primo campo e lo assegna al secondo campo */
                    campoClone = this.registraClone();

                    /* aggiunge l'etichetta "a" */
                    campoClone.decora().eliminaEtichetta();
                    campoClone.decora().etichettaSinistra("a");

                }// fine del blocco if
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoClone;
    }


    /**
     * Crea e registra il secondo campo.
     * <p/>
     * Crea un clone del primo campo, ne modifica la chiave, ne resetta
     * il valore e lo registra
     *
     * @return il secondo campo creato
     */
    private Campo registraClone() {
        /* variabili e costanti locali di lavoro */
        Campo campoOriginale = null;
        Campo campoClone = null;

        try {    // prova ad eseguire il codice

            campoOriginale = this.getCampo1();
            campoClone = campoOriginale.clonaCampo();
            this.setCampo2(campoClone);
            this.modificaChiaveCampo(campoClone);
            campoClone.setInizializzato(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoClone;
    }


    /**
     * Controlla se il campo principale e' di tipo booleano.
     * <p/>
     *
     * @return true se e' di tipo booleano
     */
    private boolean isCampoPrincipaleBooleano() {
        return this.getCampo1().isBooleano();
    }


    /**
     * Modifica il nome del secondo campo.
     * <p/>
     * Aggiunge "_2" alla fine del nome originale.
     *
     * @param campo del quale modificare la chiave
     */
    private void modificaChiaveCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String nomeOriginale = "";
        String nomeNuovo = "";

        try {    // prova ad eseguire il codice
            nomeOriginale = campo.getNomeInterno();
            nomeNuovo = nomeOriginale + "2";
            campo.setNomeInternoCampo(nomeNuovo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna l'elenco di tutti i campi
     * contenuti in questo CampoRicerca.
     * <p/>
     *
     * @return l'elenco dei campi
     */
    public ArrayList<Campo> getCampi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = null;

        try {    // prova ad eseguire il codice
            campi = new ArrayList<Campo>();
            if (this.getCampo1() != null) {
                campi.add(this.getCampo1());
            }// fine del blocco if
            if (this.getCampo2() != null) {
                campi.add(this.getCampo2());
            }// fine del blocco if
            if (this.getPopUnioni() != null) {
                campi.add(this.getPopUnioni());
            }// fine del blocco if
            if (this.getPopOperatori() != null) {
                campi.add(this.getPopOperatori());
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Ritorna il filtro corrispondente a questo CampoRicerca.
     * <p/>
     *
     * @return il filtro
     *         (null se non sono state impostate condizioni di ricerca)
     */
    public Filtro getFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try {    // prova ad eseguire il codice

            if (this.isCampoPrincipaleBooleano()) {
                filtro = this.creaFiltroBooleano();
            } else {
                if (this.isUsaRange()) {
                    filtro = this.creaFiltroRange();
                } else {
                    filtro = this.creaFiltroSingolo();
                }// fine del blocco if-else
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna la condizione di unione con la ricerca precedente
     * <p/>
     *
     * @return la condizione di unione
     */
    public String getUnione() {
        /* variabili e costanti locali di lavoro */
        String unione = Filtro.Op.AND;

        try {    // prova ad eseguire il codice
            unione = (String)this.getPopUnioni().getValoreElenco();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unione;
    }


    /**
     * Crea un filtro di ricerca su campo booleano.
     * <p/>
     *
     * @return il filtro creato
     *         (null se non sono state impostate condizioni di ricerca)
     */
    private Filtro creaFiltroBooleano() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Campo campo1 = null;
        Campo campo2 = null;
        Object valore1 = null;
        Object valore2 = null;
        boolean bool1 = false;
        boolean bool2 = false;
        String operatore = "";
        Object valore = null;

        try {    // prova ad eseguire il codice

            campo1 = this.getCampo1();
            valore1 = this.getValoreFiltro(campo1, false);
            bool1 = Libreria.getBool(valore1);

            campo2 = this.getCampo2();
            valore2 = this.getValoreFiltro(campo2, true);
            bool2 = Libreria.getBool(valore2);

            operatore = Operatore.UGUALE;

            /* selezionato SI */
            if (bool1) {
                valore = true;
                filtro = new Filtro(this.getCampoOriginale(), operatore, valore);
            }// fine del blocco if

            /* selezionato NO */
            if (bool2) {
                valore = false;
                filtro = new Filtro(this.getCampoOriginale(), operatore, valore);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea un filtro di ricerca su range di campi.
     * <p/>
     *
     * @return il filtro creato
     *         (null se non sono state impostate condizioni di ricerca)
     */
    private Filtro creaFiltroRange() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        boolean continua = true;
        Campo campo1 = null;
        Campo campo2 = null;
        Filtro filtro1 = null;
        Filtro filtro2 = null;
        String operatore = null;
        Object valore = null;

        try {    // prova ad eseguire il codice
            campo1 = this.getCampo1();
            campo2 = this.getCampo2();

            /* se entrambi i campi sono vuoti, non procede */
            if (continua) {
                if (campo1.isVuoto()) {
                    if (campo2.isVuoto()) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {

                /* crea il filtro per il campo 1*/
                if (!campo1.isVuoto()) {
                    operatore = Operatore.MAGGIORE_UGUALE;
                    valore = this.getValoreFiltro(campo1, false);
                    filtro1 = new Filtro(this.getCampoOriginale(), operatore, valore);
                }// fine del blocco if

                /* crea il filtro per il campo 2*/
                if (!campo2.isVuoto()) {
                    operatore = Operatore.MINORE_UGUALE;
                    valore = this.getValoreFiltro(campo2, true);
                    filtro2 = new Filtro(this.getCampoOriginale(), operatore, valore);
                }// fine del blocco if

                /* combina i filtri */
                filtro = new Filtro();
                if (filtro1 != null) {
                    filtro.add(filtro1);
                }// fine del blocco if
                if (filtro2 != null) {
                    filtro.add(filtro2);
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna il valore da usare per il filtro
     * relativamente a un dato campo utente.
     * <p/>
     *
     * @param campo campo utente del quale recuperare il valore filtro
     * @param rangeEnd true se è il secondo campo di un range
     * @return il valore da usare nel filtro
     */
    private Object getValoreFiltro(Campo campo, boolean rangeEnd) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Campo campoOriginale;
        Date data;
        long time;
        Timestamp ts;

        try {    // prova ad eseguire il codice

            /* recupera il valore filtro dal campo */
            valore = campo.getValoreFiltro();

            /* se il campo originale è Timestamp e si sta usando
             * solo la porzione data, recupera il valore Timestamp appropriato */
            campoOriginale = this.getCampoOriginale();
            if (campoOriginale.isTimestamp()) {
                if (campoOriginale.getCampoDati().isRicercaSoloPorzioneData()) {
                    if (valore instanceof Date) {
                        data = (Date)valore;
                        time = data.getTime();
                        ts = new Timestamp(time);
                        // se è il range end, va alle 23:59:59
                        if (rangeEnd) {
                            ts = Lib.Data.add(ts, 86399);
                        }// fine del blocco if-else
                        valore = ts;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Crea un filtro di ricerca su campo singolo.
     * <p/>
     *
     * @return il filtro creato
     *         (null se non sono state impostate condizioni di ricerca)
     */
    private Filtro creaFiltroSingolo() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Campo campo = null;
        String operatore = null;
        Object valore = null;

        try {    // prova ad eseguire il codice

            /* recupera il campo */
            campo = this.getCampo1();

            /* controlla che sia valorizzato */
            if (!campo.isVuoto()) {

                /* recupera il valore */
                valore = this.getValoreFiltro(campo, false);

                /* recupera l'operatore di ricerca */
                operatore = this.getOperatoreRicerca();

                /* costruisce il filtro */
                filtro = new Filtro(campo, operatore, valore);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Recupera il modulo di riferimento dalla ricerca.
     * <p/>
     *
     * @return il modulo di riferimento
     */
    public Modulo getModulo() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo = null;
        RicercaBase ricerca = null;

        try {    // prova ad eseguire il codice
            ricerca = this.getRicerca();
            if (ricerca != null) {
                modulo = ricerca.getModulo();
            }// fine del blocco if
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modulo;
    }


    /**
     * Ritorna true se in questo CampoRicerca e' stata inserito
     * almeno un valore di ricerca.
     * <p/>
     *
     * @return true se valorizzato
     */
    public boolean isValorizzato() {
        /* variabili e costanti locali di lavoro */
        boolean valorizzato = false;
        Campo campo;

        try {    // prova ad eseguire il codice

            /* controlla se il campo principale e' valorizzato
             * in tal caso il campo ricerca e' valorizzato */
            campo = this.getCampo1();
            if (campo != null) {
                if (!campo.isVuoto()) {
                    valorizzato = true;
                }// fine del blocco if
            }// fine del blocco if

            /* se il primo non e' valorizzato, controlla anche l'eventuale secondo campo
             * se questo e' valorizzato, il campo ricerca e' valorizzato*/
            if (!valorizzato) {
                campo = this.getCampo2();
                if (campo != null) {
                    if (!campo.isVuoto()) {
                        valorizzato = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valorizzato;
    }


    /**
     * Disabilita mutualmente i due bottoni.
     * <p/>
     *
     * @param premuto il campo premuto
     */
    private void sincronizza(Campo premuto) {
        /* variabili e costanti locali di lavoro */
        Campo campo1 = null;
        Campo campo2 = null;
        JToggleButton bottone1 = null;
        JToggleButton bottone2 = null;
        boolean campo1Acceso = false;
        boolean campo2Acceso = false;

        try {    // prova ad eseguire il codice
            campo1 = this.getCampo1();
            campo2 = this.getCampo2();

            bottone1 = campo1.getCampoVideo().getComponenteToggle();
            bottone2 = campo2.getCampoVideo().getComponenteToggle();

            campo1Acceso = bottone1.isSelected();
            campo2Acceso = bottone2.isSelected();

            if (campo1Acceso && campo2Acceso) {
                if (premuto == campo1) {
                    bottone2.setSelected(false);
                } else {
                    bottone1.setSelected(false);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Copia il valore del primo campo nel secondo campo.
     * <p/>
     * Il valore viene copiato solo se il secondo campo e' vuoto
     */
    private void copiaValore() {
        /* variabili e costanti locali di lavoro */
        Campo campo1, campo2;

        try { // prova ad eseguire il codice
            campo1 = this.getCampo1();
            if (campo1 != null) {
                campo2 = this.getCampo2();
                if (campo2 != null) {
                    if (campo2.isVuoto()) {
                        campo2.setValore(campo1.getValore());
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna l'operatore di ricerca.
     * <p/>
     * Vale solo per campo singolo
     *
     * @return - se c'e' il popup, l'operatore selezionato nel popup
     *         - se non c'e' il popup, l'operatore eventualmente passato nel costruttore
     *         - se questo e' nullo, l'operatore di default del campo
     */
    private String getOperatoreRicerca() {
        /* variabili e costanti locali di lavoro */
        String operatore = null;
        Campo campo;
        Campo campoPop;
        Object valore;

        try {    // prova ad eseguire il codice

            /* ultima risorsa - usa il default del campo */
            campo = this.getCampo1();
            operatore = campo.getOperatoreRicercaDefault();

            /* selezione da popup o da operatore registrato */
            campoPop = this.getPopOperatori();
            if (campoPop != null) {
                valore = campoPop.getValoreElenco();
                if (Lib.Testo.isValida(valore)) {
                    operatore = (String)valore;
                }// fine del blocco if
            } else {
                if (this.getOperatore() != null) {
                    operatore = this.getOperatore();
                }// fine del blocco if-else
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return operatore;
    }


    /**
     * Abilita o disabilita il popup unioni per questo campo.
     * <p/>
     * Ha effetto solo se il campo usa il pop unioni
     *
     * @param flag di controllo
     */
    public void setUnioneAbilitata(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Campo popUnioni = null;

        try {    // prova ad eseguire il codice
            if (this.isUsaPopUnioni()) {
                popUnioni = this.getPopUnioni();
                if (popUnioni != null) {
                    popUnioni.setModificabile(flag);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna un testo esplicativo delle condizioni di ricerca impostate.
     * <p/>
     * @return il testo esplicativo
     */
    public String getTestoRicerca() {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        Campo campo;
        String stringa;

        try { // prova ad eseguire il codice
            testo = this.getEtichetta()+": ";

            if (this.isCampoPrincipaleBooleano()) {
                campo = this.getCampo1();
                boolean flag = campo.getBool();
                if (flag) {
                    testo+="SI";
                } else {
                    testo+="NO";
                }// fine del blocco if-else
            } else {
                if (this.isUsaRange()) {
                    campo = this.getCampo1();
                    stringa = Lib.Testo.getStringa(campo.getValore());
                    testo+=" da "+stringa;
                    campo = this.getCampo2();
                    stringa = Lib.Testo.getStringa(campo.getValore());
                    testo+=" a "+stringa;

                } else {
                    campo = this.getCampo1();
                    if (campo.getCampoDB().isLinkato()) {
                    stringa = Lib.Testo.getStringa(campo.getValoreElenco());
                    } else {
                        stringa = Lib.Testo.getStringa(campo.getValore());
                    }// fine del blocco if-else



                    //                    if (campo.is) {
//                        ;
//                    } else {
//                        ;
//                    }// fine del blocco if-else


//                            stringa = Lib.Testo.getStringa(campo.getValoreFiltro());
//                    stringa = Lib.Testo.getStringa(campo.getValoreElenco());
                    testo+=stringa;
                }// fine del blocco if-else
            }// fine del blocco if-else



        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;

    }



    private RicercaBase getRicerca() {
        return this.ricerca;
    }


    private void setRicerca(RicercaBase ricerca) {
        this.ricerca = ricerca;
    }


    private String getEtichetta() {
        return etichetta;
    }


    public void setEtichetta(String etichetta) {
        this.etichetta = etichetta;
    }


    private Campo getCampoOriginale() {
        return campoOriginale;
    }


    private void setCampoOriginale(Campo campoOriginale) {
        this.campoOriginale = campoOriginale;
    }


    public Campo getCampo1() {
        return campo1;
    }


    private void setCampo1(Campo campo1) {
        this.campo1 = campo1;
    }


    public Campo getCampo2() {
        return campo2;
    }


    private void setCampo2(Campo campo2) {
        this.campo2 = campo2;
    }


    private String getOperatore() {
        return operatore;
    }


    private void setOperatore(String operatore) {
        this.operatore = operatore;
    }


    private boolean isUsaRange() {
        return usaRange;
    }


    public void setUsaRange(boolean usaRange) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        /* non si puo' attivare il range su alcuni tipi di campo */
        if (usaRange) {
            campo = this.getCampo1();
            if (campo != null) {
                if (campo.isBooleano()) {
                    usaRange = false;
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        this.usaRange = usaRange;
    }


    private PannelloFlusso getPannelloCampi() {
        return pannelloCampi;
    }


    private void setPannelloCampi(PannelloFlusso pannelloCampi) {
        this.pannelloCampi = pannelloCampi;
    }


    private Campo getPopOperatori() {
        return popOperatori;
    }


    private void setPopOperatori(Campo popOperatori) {
        this.popOperatori = popOperatori;
    }


    private Campo getPopUnioni() {
        return popUnioni;
    }


    private void setPopUnioni(Campo popUnioni) {
        this.popUnioni = popUnioni;
    }


    private JButton getBotAdd() {
        return botAdd;
    }


    private void setBotAdd(JButton botAdd) {
        this.botAdd = botAdd;
    }


    private JButton getBotRemove() {
        return botRemove;
    }


    private void setBotRemove(JButton botRemove) {
        this.botRemove = botRemove;
    }


    private Pannello getPanBottoni() {
        return panBottoni;
    }


    private void setPanBottoni(Pannello panBottoni) {
        this.panBottoni = panBottoni;
    }


    private int getLarghezza() {
        return larghezza;
    }

    /**
     * Assegna la larghezza ai campi di ricerca
     */
    public void setLarghezza(int larghezza) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            this.larghezza = larghezza;

            /* se i campi sono già stati creati li regola subito */
            campo = this.getCampo1();
            if (campo!=null) {
                campo.setLarScheda(larghezza);
            }// fine del blocco if

            campo = this.getCampo2();
            if (campo!=null) {
                campo.setLarScheda(larghezza);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private boolean isUsaPopUnioni() {
        return usaPopUnioni;
    }


    public void setUsaPopUnioni(boolean usaPopUnioni) {
        this.usaPopUnioni = usaPopUnioni;
    }


    /**
     * Ritorna la stringa base da usare per le chiavi dei campi nel dialogo.
     * <p/>
     *
     * @return la stringa base per le chiavi
     */
    public String getChiaveBase() {
        /* variabili e costanti locali di lavoro */
        String chiave = "";

        try {    // prova ad eseguire il codice
            chiave = this.getCampo1().getChiaveCampo();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    class Sincronizzatore implements ChangeListener {

        Campo campoPremuto = null;


        /**
         * Costruttore completo con parametri.
         *
         * @param campoPremuto il campo premuto
         */
        public Sincronizzatore(Campo campoPremuto) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.campoPremuto = campoPremuto;
        }


        public void stateChanged(ChangeEvent unEvento) {
            sincronizza(campoPremuto);
        }
    }


    class UscitaPrimoCampo extends AzAdapterFocus {

        /**
         * focusLost, da FocusListener.
         * <p/>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void focusLost(FocusEvent unEvento) {
            copiaValore();
        }
    }

}
