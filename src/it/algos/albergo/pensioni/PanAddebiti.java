/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      13-giu-2006
 */
package it.algos.albergo.pensioni;

import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.CLCalcGiorni;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.costante.CostanteColore;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioErrore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloMemoria;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreL;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.SetValori;
import it.algos.base.wrapper.WrapErrori;
import it.algos.base.wrapper.WrapListino;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Pannello di selezione degli addebiti fissi.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 13-giu-2006 ore 10.47.10
 */
public final class PanAddebiti extends PannelloFlusso {

    private ArrayList<Riga> righe;

    /**
     * numero di persone da considerare per la proposta della quantità
     */
    private int numPersone;

    private Pannello panRighe;

    /**
     * data iniziale di riferimento per i giorni
     */
    private Date dataInizioGiorni;

    /**
     * data finale di riferimento per i giorni
     */
    private Date dataFineGiorni;

    /**
     * etichetta campo totale prezzi
     */
    private JLabel etiTotale;

    /**
     * modulo memoria unico che mantiene i dati
     * di tutte le celle di addebito
     */
    private Modulo modMemoriaAddebiti;

    /**
     * modulo memoria che mantiene i dati dei totali
     */
    private Modulo modMemoriaTotali;

    /**
     * Wrapper contenente i dati in entrata/uscita
     */
    private WrapAddebiti wrapDati;


    /**
     * Costruttore senza parametri
     * <p/>
     */
    public PanAddebiti() {
        this(null, null, 0);
    }// fine del metodo costruttore completo


    /**
     * Costruttore con date inizio e fine e dati vuoti
     * <p/>
     *
     * @param dataInizio data di inizio per i giorni
     * @param dataFine data di fine per i giorni
     * @param persone numero di persone
     */
    public PanAddebiti(Date dataInizio, Date dataFine, int persone) {
        this(dataInizio, dataFine, persone, null);
    }// fine del metodo costruttore completo


    /**
     * Costruttore con dati iniziali forniti dall'esterno
     * <p/>
     *
     * @param wrapDati con i dati da precaricare
     */
    public PanAddebiti(WrapAddebiti wrapDati) {
        this(null, null, 0, wrapDati);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dataInizio data di inizio per i giorni
     * @param dataFine data di fine per i giorni
     * @param persone numero di persone
     * @param wrapDati con i dati da precaricare
     * se viene passato l'oggetto wrapDati
     * le date di inizio e fine e le persone vengono sovrascritte
     */
    private PanAddebiti(Date dataInizio, Date dataFine, int persone, WrapAddebiti wrapDati) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

        this.setDataInizioGiorni(dataInizio);
        this.setDataFineGiorni(dataFine);
        this.setNumPersone(persone);
        this.setWrapAddebitiIngresso(wrapDati);

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
        JScrollPane scorrevole;
        Pannello panRighe;
        Pannello panTotale;
        JLabel etiTotale;
        Navigatore nav;


        try { // prova ad eseguire il codice

            this.setGapPreferito(2);
            this.setAllineamento(Layout.ALLINEA_SX);

            /* crea la lista vuota delle righe */
            this.setRighe(new ArrayList<Riga>());

            /* crea il pannello delle righe e lo registra */
            panRighe = PannelloFactory.verticale(null);
            this.setPanRighe(panRighe);
            panRighe.setGapMinimo(0);
            panRighe.setGapPreferito(0);
            panRighe.setGapMassimo(5);

            /* Crea le righe e le aggiunge al pannello delle righe */
            this.creaRighe();

            /* crea uno scorrevole contenente il pannello delle righe */
            scorrevole = new JScrollPane(this.getPanRighe().getPanFisso());
            scorrevole.setOpaque(false);
            scorrevole.getViewport().setOpaque(false);
            scorrevole.setPreferredSize(new Dimension(0, 300));
            scorrevole.setBorder(null);
            scorrevole.setPreferredSize(panRighe.getPreferredSize());

            /* crea e registra l'oggetto gestore dei totali */
            panTotale = PannelloFactory.verticale(null);
            nav = this.creaMemoriaTotali();
            panTotale.add(nav);

            /* crea l'etichetta per il campo totale */
            etiTotale = new JLabel();
            this.setEtiTotale(etiTotale);

            Pannello pan2 = PannelloFactory.orizzontale(null);
            pan2.add(Box.createHorizontalGlue());
            pan2.add(panTotale);

            /* aggiunge lo scorrevole a questo pannello */
            this.add(scorrevole);

            /* aggiunge il pannello totale a questo pannello */
            this.add(pan2);

            /* crea il modulo memoria per il dettaglio addebiti */
            this.creaMemoriaAddebiti();

            /* regolazioni dinamiche dell'oggetto */
            this.regola();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     *
     * @param wrapper contenente i dati di inizializzazione
     */
    public void inizializza(WrapAddebiti wrapper) {
        try { // prova ad eseguire il codice
            this.setWrapAddebitiIngresso(wrapper);
            this.regola();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     */
    public void regola() {
        /* variabili e costanti locali di lavoro */
        WrapAddebiti wrapper;
        Date data;
        ArrayList<Riga> righe;

        try { // prova ad eseguire il codice

            /* resetta le righe */
            righe = this.getRighe();
            for (Riga riga : righe) {
                riga.reset();
            } // fine del ciclo for-each

            /* recupera e registra le date generali di inizio e fine
             * e il numero di persone, se forniti con il wrapper */
            wrapper = this.getWrapAddebitiIngresso();
            if (wrapper != null) {
                this.setDataInizioGiorni(wrapper.getDataInizio());
                this.setDataFineGiorni(wrapper.getDataFine());
                this.setNumPersone(wrapper.getPersone());
            }// fine del blocco if

            /**
             * regola la data di fine addebiti in funzione
             * della data di fine periodo (data fine periodo -1)
             */
            data = this.getDataFineGiorni();

            /* crea il bordo del pannello con titolo */
            this.regolaBordo();

            /* regola l'oggetto con i dati eventualmente forniti */
            this.regolaDatiIniziali();

            /* allinea i totali */
            this.syncTotali();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * crea il modulo memoria per il dettaglio addebiti.
     * <p/>
     */
    private void creaMemoriaAddebiti() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = new ArrayList<Campo>();
        Campo unCampo;
        Modulo mem;
        Navigatore nav;
        Campo campo;
        CampoLogica cl;

        try { // prova ad eseguire il codice

            /* campo codice chiave della riga se proveniente da database */
            unCampo = CampoFactory.intero(Nomi.chiaveRigaOrigine.get());
            unCampo.setVisibileVistaDefault(false);
            campi.add(unCampo);

            /* campo codice listino */
            unCampo = CampoFactory.intero(Nomi.codListino.get());
            unCampo.setVisibileVistaDefault(false);
            campi.add(unCampo);

            /* campo codice riga di listino */
            unCampo = CampoFactory.intero(Nomi.codRigaListino.get());
            unCampo.setVisibileVistaDefault(false);
            campi.add(unCampo);

            /* campo data inizio validità */
            unCampo = CampoFactory.data(Nomi.inizioValidita.get());
            unCampo.setInit(null);
            unCampo.setVisibileVistaDefault(true);
            campi.add(unCampo);

            /* campo data fine validità */
            unCampo = CampoFactory.data(Nomi.fineValidita.get());
            unCampo.setInit(null);
            unCampo.setVisibileVistaDefault(true);
            campi.add(unCampo);

            /* campo quantita */
            unCampo = CampoFactory.intero(Nomi.quantita.get());
            unCampo.setTitoloColonna("q.tà");
            unCampo.setVisibileVistaDefault(false);
            campi.add(unCampo);

            /* campo prezzo listino */
            unCampo = CampoFactory.valuta(Nomi.prezzoListino.get());
            unCampo.setModificabileLista(false);
            unCampo.setVisibileVistaDefault(true);
            campi.add(unCampo);

            /* campo prezzo specifico */
            unCampo = CampoFactory.valuta(Nomi.prezzo.get());
            unCampo.setModificabileLista(true);
            unCampo.setVisibileVistaDefault(true);
            campi.add(unCampo);

            /* campo importo */
            unCampo = CampoFactory.calcola(Nomi.importo.get(),
                    CampoLogica.Calcolo.prodottoValuta,
                    Nomi.quantita.get(),
                    Nomi.prezzo.get());
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setVisibileVistaDefault(false);
            campi.add(unCampo);

            /* campo giorni (differenza date specializzata) */
            unCampo = CampoFactory.intero(Nomi.giorni.get());
            cl = new CLCalcGiorni(unCampo, Nomi.inizioValidita, Nomi.fineValidita);
            unCampo.setCampoLogica(cl);
            unCampo.setVisibileVistaDefault(false);
            campi.add(unCampo);

            /* campo valore */
            unCampo = CampoFactory.calcola(Nomi.valore.get(),
                    CampoLogica.Calcolo.prodottoValuta,
                    Nomi.giorni.get(),
                    Nomi.importo.get());
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setVisibileVistaDefault(false);
            campi.add(unCampo);

            mem = this.setModMemoriaAddebiti(new ModuloMemoria(campi, false));

            nav = new NavigatoreL(mem);
            nav.setUsaFinestra(false);
            nav.setUsaStatusBarLista(false);
            nav.setUsaStampaLista(false);
            nav.setUsaFrecceSpostaOrdineLista(false);
            nav.setUsaElimina(false);
            nav.setUsaNuovo(false);
            nav.setUsaSelezione(false);
            nav.setUsaRicerca(false);
            nav.setUsaModifica(false);
            nav.setRigheLista(4);
            nav.getLista().setOrdinabile(false);
            nav.setModificabile(true);

            mem.addNavigatoreCorrente(nav);

            mem.inizializza();    // crea qui i campi

            /* modifica l'ordine della lista dopo l'inizializzazione */
            campo = mem.getCampo(Nomi.inizioValidita.get());
            nav.getLista().setOrdine(new Ordine(campo));

            mem.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea il modulo memoria per i totali .
     * <p/>
     *
     * @return il navigatore del modulo creato
     */
    private Navigatore creaMemoriaTotali() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        ArrayList<Campo> campi = new ArrayList<Campo>();
        Campo unCampo;
        Modulo mem;
        Campo campo;
        CampoLogica cl;

        try { // prova ad eseguire il codice

            /* campo data inizio validità */
            unCampo = CampoFactory.data(Totale.inizio);
            unCampo.setVisibileVistaDefault(true);
            campi.add(unCampo);

            /* campo data fine validità */
            unCampo = CampoFactory.data(Totale.fine);
            unCampo.setVisibileVistaDefault(true);
            campi.add(unCampo);

            /* campo giorni (differenza date specializzata) */
            unCampo = CampoFactory.intero(Totale.giorni.get());
            cl = new CLCalcGiorni(unCampo, Totale.inizio, Totale.fine);
            unCampo.setCampoLogica(cl);
            unCampo.setVisibileVistaDefault(true);
            campi.add(unCampo);

            /* campo prezzo  */
            unCampo = CampoFactory.valuta(Totale.prezzoGiorno.get());
            unCampo.setVisibileVistaDefault(true);
            unCampo.setTitoloColonna("prezzo/giorno");
            campi.add(unCampo);

            /* campo importo */
            unCampo = CampoFactory.calcola(Totale.importo.get(),
                    CampoLogica.Calcolo.prodottoValuta,
                    Totale.giorni.get(),
                    Totale.prezzoGiorno.get());
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setVisibileVistaDefault(true);
            unCampo.setTitoloColonna("tot. periodo");
            unCampo.setTotalizzabile(true);
            campi.add(unCampo);

            mem = new ModuloMemoria(campi, false);

            nav = new NavigatoreL(mem);
            nav.setUsaTotaliLista(true);
            nav.setUsaFinestra(false);
            nav.setUsaStatusBarLista(false);
            nav.setUsaToolBarLista(false);
            nav.setRigheLista(3);
            nav.getLista().setOrdinabile(false);
            nav.setModificabile(false);
            mem.addNavigatoreCorrente(nav);

            mem.inizializza();    // crea qui i campi

            /* modifica l'ordine della lista dopo l'inizializzazione */
            campo = mem.getCampo(Totale.inizio.get());
            nav.getLista().setOrdine(new Ordine(campo));

            mem.avvia();

            /* registra la variabile */
            this.setModMemoriaTotali(mem);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Se sono stati forniti dei dati iniziali dall'esterno,
     * li traferisce nel database memoria.
     * <p/>
     */
    private void regolaDatiIniziali() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        WrapAddebiti wrapper;
        Dati dati = null;
        Modulo modMem = null;
        SetValori sv;
        ArrayList<CampoValore> valori;
        int chiaveRigaOrigine;
        int codListino;
        int codRigaListino;
        Date data1;
        Date data2;
        int quantita = 0;
        double prezzoListino;
        double prezzo;
        boolean fornito;
        Filtro filtro;
        ArrayList listaQta;
        int unaQta;
        Object ogg;

        try { // prova ad eseguire il codice

            /* recupera i dati forniti dall'esterno */
            wrapper = this.getWrapAddebitiIngresso();
            continua = wrapper != null;

            if (continua) {
                dati = wrapper.getDati();
                continua = dati != null;
            }// fine del blocco if

            if (continua) {
                modMem = this.getModMemoriaAddebiti();
                continua = modMem != null;
            }// fine del blocco if

            /* svuota i dati in memoria */
            if (continua) {
                modMem.query().eliminaRecords();
            }// fine del blocco if

            /* spazzola i dati e riempie il DB Memoria */
            if (continua) {
                for (int k = 0; k < dati.getRowCount(); k++) {


                    chiaveRigaOrigine = dati.getIntAt(k, 0);
                    codListino = dati.getIntAt(k, 1);
                    codRigaListino = dati.getIntAt(k, 2);
                    data1 = dati.getDataAt(k, 3);
                    data2 = dati.getDataAt(k, 4);
                    quantita = dati.getIntAt(k, 5);
                    prezzo = dati.getDoubleAt(k, 6);
                    prezzoListino = ListinoModulo.getPrezzo(codListino, data1);

                    sv = new SetValori(modMem);
                    sv.add(Nomi.chiaveRigaOrigine.get(), chiaveRigaOrigine);
                    sv.add(Nomi.codListino.get(), codListino);
                    sv.add(Nomi.codRigaListino.get(), codRigaListino);
                    sv.add(Nomi.inizioValidita.get(), data1);
                    sv.add(Nomi.fineValidita.get(), data2);
                    sv.add(Nomi.quantita.get(), quantita);
                    sv.add(Nomi.prezzoListino.get(), prezzoListino);
                    sv.add(Nomi.prezzo.get(), prezzo);

                    valori = sv.getListaValori();
                    modMem.query().nuovoRecord(valori);

                } // fine del ciclo for

            }// fine del blocco if

            /**
             * Spazzola tutte le righe del pannello
             * Se i dati della riga provengono dall'esterno:
             * - accende il check
             * - regola la quantità
             * - regola il flag dettagliInterni a false
             */
            if (continua) {
                ArrayList<Riga> righe = getRighe();
                for (Riga riga : righe) {
                    codListino = riga.getCodListino();
                    fornito = modMem.query().isEsisteRecord(Nomi.codListino.get(), codListino);

                    if (fornito) {

                        /* recupera la quantità - devono essere tutte uguali, se no usa zero */
                        filtro = riga.getPanImporti().getFiltroDettagli();
                        listaQta = modMem.query().valoriCampo(Nomi.quantita.get(), filtro);
                        for (int k = 0; k < listaQta.size(); k++) {
                            ogg = listaQta.get(k);
                            unaQta = Libreria.getInt(ogg);
                            if (k == 0) {
                                quantita = unaQta;
                            } else {
                                if (unaQta != quantita) {
                                    quantita = 0;
                                    break;
                                }// fine del blocco if
                            }// fine del blocco if-else
                        } // fine del ciclo for

                        riga.setDettagliInterni(false);
                        riga.setQuantita(quantita);
                        riga.setSelezionata(true);
                    }// fine del blocco if
                }
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Costruisce e ritorna il titolo del bordo del pannello.
     * <p/>
     *
     * @return il titolo per il bordo
     */
    private String getTitolo() {
        /* variabili e costanti locali di lavoro */
        String titolo = "";
        int giorni;
        int persone;
        String txtGiorni;
        String txtPersone;

        try {    // prova ad eseguire il codice

            giorni = this.getNumGiorni();
            persone = this.getNumPersone();
            txtGiorni = "giorni";
            if (giorni == 1) {
                txtGiorni = "giorno";
            }// fine del blocco if
            txtPersone = "persone";
            if (persone == 1) {
                txtPersone = "persona";
            }// fine del blocco if

            titolo = " Addebiti";
            titolo += " dal " + Lib.Data.getStringa(this.getDataInizioGiorni());
            titolo += " al " + Lib.Data.getStringa(this.getDataFineGiorni());
            titolo += " - " + giorni + " " + txtGiorni + " " + persone + " " + txtPersone + " ";

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return titolo;
    }


    /**
     * Regola il titolo del bordo del pannello.
     * <p/>
     * Assegna un bordo con titolo al pannello
     */
    private void regolaBordo() {
        /* variabili e costanti locali di lavoro */
        String titolo;

        try {    // prova ad eseguire il codice

            titolo = this.getTitolo();

            /* crea un bordo con titolo rosso */
            Border bordo = this.creaBordo(titolo);
            if (bordo instanceof CompoundBorder) {
                CompoundBorder cBordo = (CompoundBorder)bordo;
                Border outBordo = cBordo.getOutsideBorder();
                if (outBordo instanceof TitledBorder) {
                    TitledBorder tBordo = (TitledBorder)outBordo;
                    tBordo.setTitleColor(CostanteColore.ROSSO);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea le righe e le aggiunge al pannello delle righe.
     * </p>
     */
    private void creaRighe() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> lista;
        Riga riga;
        ListinoModulo mod;

        try { // prova ad eseguire il codice

            /* lista delle righe di listino disponibili per addebiti giornalieri */
            mod = ListinoModulo.get();
            lista = mod.getGiornalieri();

            /* traverso tutta la collezione */
            for (int cod : lista) {
                riga = new Riga(this, cod);
                this.getRighe().add(riga);
                this.getPanRighe().add(riga);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Ritorna la lista delle righe con check attivato.
     * <p/>
     *
     * @return la lista delle righe correntemente spuntate
     */
    public ArrayList<Riga> getRigheSelezionate() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Riga> righeSelezionate = null;
        ArrayList<Riga> righe;

        try {    // prova ad eseguire il codice
            righe = this.getRighe();
            righeSelezionate = new ArrayList<Riga>();
            /* traverso tutta la collezione */
            for (Riga riga : righe) {
                if (riga.isSelezionata()) {
                    righeSelezionate.add(riga);
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return righeSelezionate;
    }


    /**
     * Ritorna il numero di righe selezionate.
     * <p/>
     *
     * @return il numero di righe selezionate
     */
    public int getNumRigheSelezionate() {
        return this.getRigheSelezionate().size();
    }


    /**
     * Ritorna true se ci sono righe selezionate.
     * <p/>
     *
     * @return true se ci sono righe selezionate
     */
    public boolean isRigheSelezionate() {
        return (this.getNumRigheSelezionate() > 0);
    }


    /**
     * Controlla se e' il listino e' in grado di fornire
     * dati completi e congrui per le righe attualmente
     * selezionate e per il periodo specificato.
     * <p/>
     * Se non e' in grado visualizza un messaggio di errore.
     *
     * @param d1 data inizio periodo
     * @param d2 data fine periodo
     *
     * @return true se il listino e' in grado di fornire i dati.
     */
    public boolean checkListino(Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        ListinoModulo modListino;
        int codListino;
        String descListino;
        ArrayList<WrapListino> lista;
        String errore;
        ArrayList<String> errori;

        try {    // prova ad eseguire il codice

            modListino = ListinoModulo.get();
            errori = new ArrayList<String>();

            /* traverso tutta la collezione delle righe selezionate nel pannello
             * e per ognuna richiedo al listino la lista dei prezzi per il periodo */
            for (Riga riga : this.getRigheSelezionate()) {

                codListino = riga.getCodListino();
                lista = ListinoModulo.getPrezzi(codListino, d1, d2);

                /* se la lista e' nulla il listino ha fallito
                 * aggiungo il messaggio di errore */
                if (lista == null) {
                    continua = false;
                    descListino = modListino.query().valoreStringa(Listino.Cam.descrizione.get(),
                            codListino);
                    errori.add(descListino);
                }// fine del blocco if

            } // fine del ciclo for-each

            /* se fallito visualizza l'errore */
            if (!continua) {

                errore =
                        "Il listino non e' in grado di fornire i prezzi" +
                                "\nper il periodo richiesto!";
                errore += "\n\n";
                errore += "Listini incompleti o errati:";

                for (String stringa : errori) {
                    errore += "\n- " + stringa;
                }

                errore += "\n\n";
                errore += "Controllare nel listino:";
                errore += "\n- che tutto il periodo richiesto sia coperto";
                errore += "\n- che non ci siano periodi sovrapposti";
                errore += "\n";

                new MessaggioErrore(errore);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Aggiorna i totali delle righe del Pannello Servizi
     * <p/>
     */
    private void refreshTotali() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Riga> righe;
        String testo = "";
        Date data;
        JLabel label;

        try {    // prova ad eseguire il codice

            /* rinfresca ogni riga */
            righe = this.getRighe();
            if (righe != null) {
                for (Riga riga : righe) {
                    riga.refreshTotale();
                }
            }// fine del blocco if

            /* rinfresca l'etichetta del totale */
            data = this.getDataInizioGiorni();
            if (!Lib.Data.isVuota(data)) {
                testo = "Totale del giorno " + Lib.Data.getStringa(data) + ":";
            }// fine del blocco if
            label = this.getEtiTotale();
            if (label != null) {
                label.setText(testo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Mappa di valori per ogni giorno, nella forma giorno/valore.
     * <p/>
     * Spazzola tutte le righe di addebito e per ognuna spazzola tutti i periodi <br>
     * Per ogni singolo periodo, crea una mappa con giorno e prezzo <br>
     * Aggiunge al valore precedente il prezzo di ogni giorno trovato <br>
     * Alla fine ho una mappa con tutti i giorni ed il prezzo totale per quel giorno <br>
     *
     * @return la mappa dei valori per giorno
     */
    private LinkedHashMap<Date, Double> getMappa() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<Date, Double> mappa = null;
        WrapAddebiti wrapDati;

        try { // prova ad eseguire il codice
            wrapDati = new WrapAddebiti();
            this.riempiWrapper(wrapDati);
            mappa = this.getMappa(wrapDati);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mappa;
    }


    /**
     * Mappa di valori per ogni giorno, nella forma giorno/valore.
     * <p/>
     * Spazzola tutte le righe di addebito e per ognuna spazzola tutti i periodi <br>
     * Per ogni singolo periodo, crea una mappa con giorno e prezzo <br>
     * Aggiunge al valore precedente il prezzo di ogni giorno trovato <br>
     * Alla fine ho una mappa con tutti i giorni ed il prezzo totale per quel giorno <br>
     *
     * @param wrapDati wrapper con i dati degli addebiti
     *
     * @return la mappa dei valori per giorno
     */
    private LinkedHashMap<Date, Double> getMappa(WrapAddebiti wrapDati) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<Date, Double> mappa = null;
        boolean continua;
        ArrayList<WrapAddebiti.Elemento> elementi = null;
        ArrayList<WrapAddebiti.Dettaglio> dettagli;
        Date dataInizio;
        Date dataFine;
        int quantita;
        double prezzo;
        double prezzoMedio;
        double prezzoTemp;
        Date[] giorni;
        Date giorno;

        try { // prova ad eseguire il codice

            mappa = new LinkedHashMap<Date, Double>();

            /* controllo di congruità */
            continua = (wrapDati != null);

            if (continua) {
                elementi = wrapDati.getElementi();
                continua = (elementi != null) && (elementi.size() > 0);
            }// fine del blocco if

            /* Spazzola tutte le righe di addebito e per ognuna spazzola tutti i periodi */
            if (continua) {
                /* Singolo addebito */
                for (WrapAddebiti.Elemento elemento : elementi) {
                    dettagli = elemento.getDettagli();

                    if (dettagli != null) {
                        /* Singolo periodo */
                        for (WrapAddebiti.Dettaglio dettaglio : dettagli) {
                            dataInizio = dettaglio.getData1();
                            dataFine = dettaglio.getData2();
                            prezzo = dettaglio.getPrezzo();
                            quantita = dettaglio.getQuantita();
                            prezzoMedio = quantita * prezzo;
                            giorni = Lib.Data.getDateComprese(dataInizio, dataFine);

                            /* Per ogni giorno di questo periodo, mi metto da parte la data ed il valore
                             * Se il giorno non esiste nella mappa, lo creo
                             * Se il giorno esiste, aggiungo il valore */
                            for (int k = 0; k < giorni.length; k++) {
                                giorno = giorni[k];
                                if (mappa.containsKey(giorno)) {
                                    prezzoTemp = mappa.get(giorno);
                                    prezzoTemp += prezzoMedio;
                                    mappa.remove(giorno);
                                    mappa.put(giorno, prezzoTemp);
                                } else {
                                    mappa.put(giorno, prezzoMedio);
                                }// fine del blocco if-else
                            } // fine del ciclo for
                        } // fine del ciclo for-each
                    }// fine del blocco if
                } // fine del ciclo for-each
                continua = (mappa.size() > 0);
            }// fine del blocco if

            /* ordina cronologicamente le date */
            if (continua) {
                mappa = Lib.Data.ordinaMappa(mappa);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mappa;
    }


    /**
     * Sincronizza i valori dei totali .
     * <p/>
     * Spazzola tutte le righe di addebito e per ognuna spazzola tutti i periodi <br>
     * Per ogni singolo periodo, crea una mappa con giorno e prezzo <br>
     * Aggiunge al valore precedente il prezzo di ogni giorno trovato <br>
     * Alla fine ho una mappa con tutti i giorni ed il prezzo totale per quel giorno <br>
     * Li passo tutti e costruisco un record ogni volta che cambia il valore <br>
     */
//    private void syncTotali(LinkedHashMap<Date, Double> mappa) {
    private void syncTotali() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mem;
        int cod = 0;
        double prezzoMedio;
        Date giorno;
        double oldPrezzo = 0.0;
        LinkedHashMap<Date, Double> mappa;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
//            continua = (mappa != null && mappa.size() > 0);

            mem = this.getModMemoriaTotali();
            continua = (mem != null);

            if (continua) {
                mem.query().eliminaRecords();
                mem.caricaTutti();
//                continua = (mappa != null);
            }// fine del blocco if

            /* spazzolo la mappa e costruisco un record ogni volta che cambia il valore */
            if (continua) {

                mappa = this.getMappa();
                for (Map.Entry map : mappa.entrySet()) {
                    giorno = (Date)map.getKey();
                    prezzoMedio = (Double)map.getValue();

                    if (prezzoMedio != oldPrezzo) {
                        cod = mem.query().nuovoRecord();
                        mem.query().registra(cod, Totale.inizio.get(), giorno);
                        mem.query().registra(cod, Totale.fine.get(), giorno);
                        mem.query().registra(cod, Totale.prezzoGiorno.get(), prezzoMedio);
                    } else {
                        mem.query().registra(cod, Totale.fine.get(), giorno);
                    }// fine del blocco if-else
                    oldPrezzo = prezzoMedio;
                } // fine del ciclo for-each

                mem.caricaTutti();

                this.fire(PannelloBase.Evento.modifica);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il numero di giorni intercorrenti tra la date di inizio e di fine
     * <p/>
     *
     * @return il numero di giorni
     */
    private int getNumGiorni() {
        /* variabili e costanti locali di lavoro */
        int giorni = 0;
        Date dataIni;
        Date dataEnd;

        try { // prova ad eseguire il codice
            dataIni = this.getDataInizioGiorni();
            dataEnd = this.getDataFineGiorni();
            giorni = Lib.Data.diff(dataEnd, dataIni);
            giorni++;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return giorni;
    }


    /**
     * Crea un nuovo wrapper addebiti di periodo corrisponente alla
     * situazione attualmente impostata nel pannello
     * <p/>
     * Il wrapper viene costruito al momento in base
     * ai dati attualmente selezionati
     *
     * @return il wrapper addebiti creato
     */
    public WrapAddebitiPeriodo creaWrapAddebitiPeriodo() {
        /* variabili e costanti locali di lavoro */
        WrapAddebitiPeriodo wrapper = null;
        Date dataIni;
        Date dataFine;
        int persone;

        try { // prova ad eseguire il codice
            /* crea l'oggetto wrapper di base */
            dataIni = this.getDataInizioGiorni();
            dataFine = this.getDataFineGiorni();
            persone = this.getNumPersone();
            wrapper = new WrapAddebitiPeriodo(dataIni, dataFine, persone, 0, null);
            this.riempiWrapper(wrapper);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return wrapper;

    }


    /**
     * Crea un nuovo wrapper addebiti di conto corrisponente alla
     * situazione attualmente impostata nel pannello
     * <p/>
     * Il wrapper viene costruito al momento in base
     * ai dati attualmente selezionati
     *
     * @return il wrapper addebiti creato
     */
    public WrapAddebitiConto creaWrapAddebitiConto() {
        /* variabili e costanti locali di lavoro */
        WrapAddebitiConto wrapper = null;
        Date dataIni;
        Date dataFine;
        int persone;

        try { // prova ad eseguire il codice
            /* crea l'oggetto wrapper di base */
            dataIni = this.getDataInizioGiorni();
            dataFine = this.getDataFineGiorni();
            persone = this.getNumPersone();
            wrapper = new WrapAddebitiConto(dataIni, dataFine, persone, 0, null);
            this.riempiWrapper(wrapper);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return wrapper;
    }


    /**
     * Riempie il wrapper fornito con i dati attualmente selezionati nel pannello
     * <p/>
     *
     * @param wrapper da riempire
     */
    private void riempiWrapper(WrapAddebiti wrapper) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Riga> righe;
        Dati dati;
        int codListino;
        WrapAddebiti.Elemento elemento;
        int codChiave;
        Date data1;
        Date data2;
        int quantita;
        double prezzo;
        int codRiga;

        try {    // prova ad eseguire il codice

            /* aggiunge un elemento con relativi dettagli per ogni riga selezionata  */
            righe = this.getRigheSelezionate();
            for (Riga riga : righe) {

                /* recupera il codice di listino */
                codListino = riga.getCodListino();

                /* crea un elemento del wrapper per questo codice listino */
                elemento = wrapper.addElemento(codListino);

                /* recupera i dati della riga e aggiunge le righe di dettaglio all'elemento */
                dati = riga.getDatiRiga();
                for (int k = 0; k < dati.getRowCount(); k++) {
                    codChiave = dati.getIntAt(k, Nomi.chiaveRigaOrigine.get());
                    data1 = dati.getDataAt(k, Nomi.inizioValidita.get());
                    data2 = dati.getDataAt(k, Nomi.fineValidita.get());
                    quantita = dati.getIntAt(k, Nomi.quantita.get());
                    prezzo = dati.getDoubleAt(k, Nomi.prezzo.get());
                    codRiga = dati.getIntAt(k, Nomi.codRigaListino.get());
                    elemento.addDettaglio(codChiave, data1, data2, quantita, prezzo, codRiga);
                } // fine del ciclo for
                dati.close();
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    private ArrayList<Riga> getRighe() {
        return righe;
    }


    private void setRighe(ArrayList<Riga> righe) {
        this.righe = righe;
    }


    private int getNumPersone() {
        return numPersone;
    }


    public void setNumPersone(int numPersone) {
        this.numPersone = numPersone;
        this.regolaBordo();
        this.syncTotali();        
    }


    private Pannello getPanRighe() {
        return panRighe;
    }


    private void setPanRighe(Pannello panRighe) {
        this.panRighe = panRighe;
    }


    private Date getDataInizioGiorni() {
        return dataInizioGiorni;
    }


    public void setDataInizioGiorni(Date dataInizioGiorni) {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.dataInizioGiorni = dataInizioGiorni;
            this.regolaBordo();
            this.refreshTotali();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private Date getDataFineGiorni() {
        return dataFineGiorni;
    }


    public void setDataFineGiorni(Date dataFineGiorni) {
        try { // prova ad eseguire il codice
            this.dataFineGiorni = dataFineGiorni;
            this.regolaBordo();
            this.syncTotali();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private JLabel getEtiTotale() {
        return etiTotale;
    }


    private void setEtiTotale(JLabel etiTotale) {
        this.etiTotale = etiTotale;
    }


    public boolean isConfermabile() {
        return this.getErrore().isValido();
    }


    /**
     * Ritorna un eventuale errore/incompletezza nei dati.
     * <p/>
     * @return l'oggetto descrittivo dell'errore
     */
    public WrapErrori getErrore() {
        /* variabili e costanti locali di lavoro */
        WrapErrori errori = new WrapErrori();
        Modulo mem;
        boolean continua;
        Lista lista = null;
        int num;

        try {    // prova ad eseguire il codice

            /**
             * controlla che siano stati selezionati degli addebiti
             */
            mem = this.getModMemoriaTotali();
            continua = (mem != null);

            if (continua) {
                lista = mem.getLista();
                continua = (lista != null);
            }// fine del blocco if

            if (continua) {
                num = lista.getNumRecordsVisualizzati();
                if (num<=0) {
                    errori.add("Non sono stati selezionati addebiti");
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return errori;
    }



    private Modulo getModMemoriaAddebiti() {
        return modMemoriaAddebiti;
    }


    private Modulo setModMemoriaAddebiti(Modulo modMemoria) {
        this.modMemoriaAddebiti = modMemoria;
        return this.getModMemoriaAddebiti();
    }


    public Modulo getModMemoriaTotali() {
        return modMemoriaTotali;
    }


    public Modulo setModMemoriaTotali(Modulo modMemoriaTotali) {
        this.modMemoriaTotali = modMemoriaTotali;
        return this.getModMemoriaTotali();
    }


    /**
     * Ritorna il wrapper addebiti in ingresso
     * fornito nel costruttore
     * <p/>
     *
     * @return il wrapper addebiti in ingresso
     */
    private WrapAddebiti getWrapAddebitiIngresso() {
        return wrapDati;
    }


    private void setWrapAddebitiIngresso(WrapAddebiti wrapDati) {
        this.wrapDati = wrapDati;
    }


    /**
     * Singola riga di impostazione addebito fisso
     * </p>
     */
    private final class Riga extends PannelloFlusso {

        /* Pannello addebiti parente di riferimento */
        private PanAddebiti panAddebiti;

        /* codice di listino della riga */
        private int codListino;

        /* campo check */
        private Campo campoCheck;

        /* pannello gestione importi della riga */
        private PanImporti panImporti;

        /* flag - indica se i dati di dettaglio della riga sono stati
         * creati dalla riga stessa o sono stati forniti dall'esterno */
        private boolean dettagliInterni;


        /**
         * Costruttore completo con parametri.
         *
         * @param panAddebiti panAddebiti di riferimento
         * @param codListino codice del listino
         */
        public Riga(PanAddebiti panAddebiti, int codListino) {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_ORIZZONTALE);

            /* regola le variabili di istanza coi parametri */
            this.setPanAddebiti(panAddebiti);
            this.setCodListino(codListino);

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
            /* variabili e costanti locali di lavoro */
            int cod;
            String descrizione;
            Campo campoCheck;
            PanImporti panImporti;
            Modulo mod;
            boolean disattivato;

            try { // prova ad eseguire il codice
                this.setAllineamento(Layout.ALLINEA_CENTRO);
                this.setUsaGapFisso(true);
                this.setGapPreferito(5);
                this.setRidimensionaComponenti(false);
                this.getLayoutAlgos().setConsideraComponentiInvisibili(true);

                this.setDettagliInterni(true);

                /* recupera la descrizione del listino */
                cod = this.getCodListino();
                descrizione = this.getDescrizioneListino();

                /* crea il campo check con la descrizione */
                campoCheck = CampoFactory.checkBox("campoCheck" + cod);
                this.setCampoCheck(campoCheck);
                campoCheck.setTestoComponente(descrizione);
                campoCheck.setLarScheda(260);
                campoCheck.avvia();

//                campoCheck.getPannelloCampo().setOpaque(true);
//                campoCheck.getPannelloCampo().setBackground(Color.yellow);

                campoCheck.addListener(new CheckModificato());

                /* crea e registra il pannello Importi */
                panImporti = new PanImporti(this);
                this.setPanImporti(panImporti);

                /* aggiunge gli oggetti grafici */
                this.add(campoCheck);
                this.add(panImporti);

                /* se la voce di listino è disattivata il campo check non è modificabile */
                mod = ListinoModulo.get();
                disattivato = mod.query().valoreBool(Listino.Cam.disattivato.get(),
                        this.getCodListino());
                campoCheck.setAbilitato(!disattivato);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }


        /**
         * Resetta (svuota) la riga.
         * <p/>
         */
        private void reset() {
            try {    // prova ad eseguire il codice
                this.setSelezionata(false);
                this.setDettagliInterni(true);
                this.getPanImporti().reset();
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Azione di modifica del valore del check box
         */
        private class CheckModificato extends CampoMemoriaAz {

            public void campoMemoriaAz(CampoMemoriaEve unEvento) {
                checkModificato();
            }
        } // fine della classe interna


        /**
         * Campo check modificato.
         * <p/>
         * Se acceso regola la quantita' in base al sottoconto e alla camera
         * Regola la visibilita' dei campi
         * Rende validi o meno i dati di dettaglio
         */
        private void checkModificato() {
            /* variabili e costanti locali di lavoro */
            boolean selezionato;
            Object valore;
            int quantita;

            try { // prova ad eseguire il codice

                /* recupera il valore del campo check */
                valore = this.getCampoCheck().getValore();
                selezionato = Libreria.getBool(valore);

                /* regola la quantita' */
                if (selezionato) {
                    if (this.isDettagliInterni()) {
                        quantita = this.getQuantitaProposta();
                        this.getPanImporti().setQuantita(quantita);
                    }// fine del blocco if
                    this.validaDettagli();
                } else {
                    this.invalidaDettagli();
                }// fine del blocco if-else

                /* abilita o disabilita il pannello importi */
                this.getPanImporti().setAbilitato(selezionato);

                /* aggiorna il valore e la visibilita' del totale */
                this.refreshTotale();

                /* aggiorna i totali del pannello principale */
                syncTotali();

                /* lancia un evento verso l'esterno */
                this.getPanAddebiti().fire(PannelloBase.Evento.modifica);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Convalida il dettaglio righe quando viene acceso il check.
         * <p/>
         * Se la riga è a creazione interna, crea i record
         * Altrimenti non fa nulla
         */
        private void validaDettagli() {
            /* variabili e costanti locali di lavoro */
            boolean continua;
            boolean interna;
            Modulo mod = null;
            int codListino = 0;
            int codRigaListino;
            int qtaRiga = 0;
            Date dataIniPeriodo = null;
            Date dataFineAddebiti = null;
            int cod;
            ArrayList<WrapListino> listino = null;
            Date dataIniListino;
            Date dataFineListino;
            double prezzo;

            try {    // prova ad eseguire il codice

                interna = this.isDettagliInterni();
                continua = interna;

                if (continua) {
                    mod = getModMemoriaAddebiti();
                    continua = mod != null;
                }// fine del blocco if

                if (continua) {
                    codListino = this.getCodListino();
                    continua = codListino > 0;
                }// fine del blocco if

                if (continua) {
                    qtaRiga = getQuantitaProposta();
                    continua = qtaRiga > 0;
                }// fine del blocco if

                if (continua) {
                    dataIniPeriodo = getDataInizioGiorni();
                    dataFineAddebiti = getDataFineGiorni();
                    continua = (Lib.Data.isValida(dataIniPeriodo) && Lib.Data
                            .isValida(dataFineAddebiti));
                }// fine del blocco if

                if (continua) {
                    listino = ListinoModulo.getPrezzi(codListino, dataIniPeriodo, dataFineAddebiti);
                    if (listino == null) {
                        new MessaggioAvviso("Attenzione! Il listino non copre il periodo richiesto.");
                    }// fine del blocco if
                    continua = listino != null;
                }// fine del blocco if

                if (continua) {
                    for (WrapListino riga : listino) {
                        dataIniListino = riga.getPrimaData();
                        dataFineListino = riga.getSecondaData();

                        codRigaListino = riga.getCodRiga();

                        /* recupera il prezzo dalla riga */
                        prezzo = riga.getPrezzo();

                        /* crea un nuovo record in memoria */
                        cod = mod.query().nuovoRecord();
                        mod.query().registra(cod, Nomi.codListino.get(), codListino);
                        mod.query().registra(cod, Nomi.codRigaListino.get(), codRigaListino);
                        mod.query().registra(cod, Nomi.inizioValidita.get(), dataIniListino);
                        mod.query().registra(cod, Nomi.fineValidita.get(), dataFineListino);
                        mod.query().registra(cod, Nomi.prezzoListino.get(), prezzo);
                        mod.query().registra(cod, Nomi.prezzo.get(), prezzo);
                        mod.query().registra(cod, Nomi.quantita.get(), qtaRiga);

                    } // fine del ciclo for-each

                }// fine del blocco if


            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Invalida i dettagli righe quando viene spento il check.
         * <p/>
         * Se sono stati creati internamente, cancella i dettagli
         * Se sono stati forniti dall'esterno, non fa nulla
         */
        private void invalidaDettagli() {
            /* variabili e costanti locali di lavoro */
            boolean continua;
            boolean interna;
            int codListino = 0;
            Filtro filtro;
            Modulo moduloMem = null;

            try {    // prova ad eseguire il codice

                interna = this.isDettagliInterni();
                continua = interna;

                if (continua) {
                    moduloMem = getModMemoriaAddebiti();
                    continua = moduloMem != null;
                }// fine del blocco if

                if (continua) {
                    codListino = this.getCodListino();
                    continua = codListino > 0;
                }// fine del blocco if

                if (continua) {
                    filtro = FiltroFactory.crea(Nomi.codListino.get(), codListino);
                    moduloMem.query().eliminaRecords(filtro);
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Recupera la quantita' proposta per la riga.
         * <p/>
         *
         * @return la quantita' proposta
         */
        private int getQuantitaProposta() {
            /* variabili e costanti locali di lavoro */
            int qtaProposta = 0;
            boolean continua;
            int codListino;

            try {    // prova ad eseguire il codice

                /* controlla se il prezzo del listino e' a persona */
                codListino = this.getCodListino();
                continua = Listino.TipoPrezzo.isPerPersona(codListino);

                /* recupera il numero di persone */
                if (continua) {
                    qtaProposta = getNumPersone();
                }// fine del blocco if

                /* se la q.ta proposta e' zero la pone automaticamente a 1*/
                if (qtaProposta == 0) {
                    qtaProposta = 1;
                }// fine del blocco if


            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return qtaProposta;
        }


        /**
         * Esegue il refresh del totale della riga
         * <p/>
         * Ricalcola il totale in base alla data di riferimento
         * rende visibile o invisibile il campo totale
         */
        private void refreshTotale() {
            /* variabili e costanti locali di lavoro */

            try {    // prova ad eseguire il codice

//                /* invoca il metodo della classe esterna che deve regolare il totale */
//                syncTotali();

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Ritorna la descrizione della voce di listino associata.
         * <p/>
         *
         * @return la descrizione
         */
        private String getDescrizioneListino() {
            /* variabili e costanti locali di lavoro */
            String descrizione = "";
            int cod;
            ListinoModulo mod;

            try { // prova ad eseguire il codice

                /* recupera la descrizione del listino */
                cod = this.getCodListino();
                mod = ListinoModulo.get();
                descrizione = mod.query().valoreStringa(Listino.Cam.descrizione.get(), cod);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return descrizione;
        }


        /**
         * Assegna una quantità al campo interno della riga.
         * <p/>
         *
         * @param quantita da assegnare
         */
        private void setQuantita(int quantita) {
            /* variabili e costanti locali di lavoro */
            PanImporti panImporti;

            try {    // prova ad eseguire il codice
                panImporti = this.getPanImporti();
                if (panImporti != null) {
                    panImporti.setQuantita(quantita);
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Ritorna un oggetto dati relativo alla riga.
         * <p/>
         * Contiene tutti i campi.
         *
         * @return l'oggetto dati
         */
        private Dati getDatiRiga() {
            /* variabili e costanti locali di lavoro */
            Dati dati = null;
            Modulo moduloMem;
            PanImporti panImp;
            Filtro filtro;
            Query query;
            ArrayList<Campo> campi;
            Campo campoOrdine;

            try {    // prova ad eseguire il codice
                panImp = this.getPanImporti();
                filtro = panImp.getFiltroDettagli();
                moduloMem = getModMemoriaAddebiti();
                query = new QuerySelezione(moduloMem);
                campi = moduloMem.getCampiFisici();
                query.setCampi(campi);
                query.setFiltro(filtro);
                campoOrdine = moduloMem.getCampo(Nomi.inizioValidita.get());
                query.setOrdine(new Ordine(campoOrdine));
                dati = moduloMem.query().querySelezione(query);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return dati;
        }


        private PanAddebiti getPanAddebiti() {
            return panAddebiti;
        }


        private void setPanAddebiti(PanAddebiti panAddebiti) {
            this.panAddebiti = panAddebiti;
        }


        private int getCodListino() {
            return codListino;
        }


        private void setCodListino(int codListino) {
            this.codListino = codListino;
        }


        private Campo getCampoCheck() {
            return campoCheck;
        }


        private void setCampoCheck(Campo campoCheck) {
            this.campoCheck = campoCheck;
        }


        private PanImporti getPanImporti() {
            return panImporti;
        }


        private void setPanImporti(PanImporti panImporti) {
            this.panImporti = panImporti;
        }


        private boolean isDettagliInterni() {
            return dettagliInterni;
        }


        private void setDettagliInterni(boolean dettagliInterni) {
            this.dettagliInterni = dettagliInterni;
        }


        private boolean isSelezionata() {
            return (Boolean)this.getCampoCheck().getValore();
        }


        private void setSelezionata(boolean flag) {
            this.getCampoCheck().setValore(flag);
        }

//        /**
//         * Ritorna l'importo totale del soggiorno relativo alla riga
//         * <p/>
//         * comprende giorni e persone
//         *
//         * @return il totale soggiorno
//         */
//        private double getTotSoggiorno() {
//            /* variabili e costanti locali di lavoro */
//            double totale = 0d;
//            boolean continua;
//            Modulo mem;
//            Filtro filtro = null;
//            Number numero;
//
//            try { // prova ad eseguire il codice
//                mem = getModMemoriaAddebiti();
//                continua = (mem != null);
//
//                if (continua) {
//                    filtro = this.getPanImporti().getFiltroDettagli();
//                    continua = (filtro != null);
//                }// fine del blocco if
//
//                if (continua) {
//                    numero = mem.query().somma(Nomi.valore.get(), filtro);
//                    totale = Libreria.getDouble(numero);
//                }// fine del blocco if
//
//            } catch (Exception unErrore) { // intercetta l'errore
//                Errore.crea(unErrore);
//            }// fine del blocco try-catch
//
//            /* valore di ritorno */
//            return totale;
//        }

    } // fine della classe 'interna'


    /**
     * Pannello specializzato per gestire gli importi della riga
     * </p>
     */
    private final class PanImporti extends PannelloFlusso {

        /* riga di riferimento */
        private Riga riga;

        /* campo quantità */
        private Campo campoQuantita;

        /* campo importo unitario */
        private Campo campoUnitario;

        /* campo importo totale */
        private Campo campoTotale;

        /* campo riassunto prezzi */
        private Campo campoSunto;

        /* bottone apri dettagli */
        private JButton botDettagli;

        /* pannello switch per campi prezzi o sunto */
        private Pannello panSwitch;

        /**
         * larghezza fissa per i campo unitario o sunto, che sono
         * visualizzati alternativamente nel pannello switch
         */
        private int LAR_UNITARIO = 60;


        /**
         * Costruttore completo con parametri.
         *
         * @param riga di riferimento
         */
        public PanImporti(Riga riga) {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_ORIZZONTALE);

            /* regola le variabili di istanza coi parametri */
            this.setRiga(riga);

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
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
            JButton bottone;
            Pannello panSwitch;
            int hPannello;

            try { // prova ad eseguire il codice

                /* regola il layout */
                this.setAllineamento(Layout.ALLINEA_CENTRO);
                this.setUsaGapFisso(true);
                this.setGapPreferito(5);
                this.setRidimensionaComponenti(false);

                /* crea e registra i campi interni */
                this.creaCampi();

                /* recupera l'altezza del pannello
                 * usa l'altezza del campo quantità (uno qualsiasi....)*/
                hPannello = this.getCampoQuantita()
                        .getPannelloComponenti()
                        .getPreferredSize().height;

                /* crea e registra il pannello switch prezzi/sunto */
                panSwitch = PannelloFactory.orizzontale(null);
                this.setPanSwitch(panSwitch);
                panSwitch.setPreferredSize(LAR_UNITARIO, hPannello);
                panSwitch.bloccaDim();

                /* crea e registra il bottone dettagli */
                bottone = new JButton("...");
                this.setBotDettagli(bottone);
                Lib.Comp.setPreferredWidth(bottone, 30);
                bottone.addActionListener(new AzBottone());

                /* aggiunge gli opggetti grafici */
                this.add(this.getCampoQuantita().getPannelloComponenti());
                this.add(panSwitch);
                this.add(this.getCampoTotale().getPannelloComponenti());
                this.add(bottone);

                /* fissa l'altezza del pannello pari all'altezza del campo quantità */
                Lib.Comp.setPreferredHeigth(this, hPannello);

                /* regola la visibilità iniziale dell'oggetto */
                this.setVisible(this.getRiga().isSelezionata());

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Crea e registra i campi interni.
         * <p/>
         */
        private void creaCampi() {
            /* variabili e costanti locali di lavoro */
            Campo campo;

            try {    // prova ad eseguire il codice

                /* crea il campo quantità */
                campo = CampoFactory.intero("quantita");
                this.setCampoQuantita(campo);
                campo.setAllineamento(SwingConstants.RIGHT);
                campo.decora().eliminaEtichetta();
                campo.setLarScheda(20);
                campo.avvia();
                campo.addListener(new CampoModificato());

                /* crea il campo importo unitario */
                campo = CampoFactory.valuta("unitario");
                this.setCampoUnitario(campo);
                campo.setAllineamento(SwingConstants.RIGHT);
                campo.decora().eliminaEtichetta();
                campo.setLarScheda(LAR_UNITARIO);
                campo.avvia();
                campo.addListener(new CampoModificato());

                /* crea il campo importo totale (non calcolato automaticamente) */
                campo = CampoFactory.valuta("totale");
                this.setCampoTotale(campo);
                campo.setAllineamento(SwingConstants.RIGHT);
                campo.decora().eliminaEtichetta();
                campo.setLarScheda(48);
                campo.setAbilitato(false);
                campo.avvia();

                /* crea il campo riassunto dettagli */
                campo = CampoFactory.testo("sunto");
                this.setCampoSunto(campo);
                campo.setAllineamento(SwingConstants.CENTER);
                campo.decora().eliminaEtichetta();
                campo.setLarScheda(LAR_UNITARIO);
                campo.setAbilitato(false);
                campo.avvia();


            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Resetta (svuota) i dati.
         * <p/>
         */
        private void reset() {
            try {    // prova ad eseguire il codice
                this.setQuantita(0);
                this.setPrezzo(0);
                this.setTotale(0);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * E' stata modificata la quantità.
         * <p/>
         */
        private void quantitaModificata() {
            this.registraCampoQuantita();
            this.syncTotale();
        }


        /**
         * E' stato modificato l'importo unitario.
         * <p/>
         */
        private void unitarioModificato() {
            this.registraCampoUnitario();
            this.syncTotale();
        }


        /**
         * Registra nel db memoria il valore corrente del campo quantità.
         * <p/>
         * Modifica la quantità di tutte le righe di dettaglio
         */
        private void registraCampoQuantita() {
            /* variabili e costanti locali di lavoro */
            int quantita;
            Modulo moduloMem;
            Filtro filtro;
            int[] righe;

            try {    // prova ad eseguire il codice
                moduloMem = getModMemoriaAddebiti();
                quantita = this.getQuantita();
                filtro = this.getFiltroDettagli();
                righe = moduloMem.query().valoriChiave(filtro);
                for (int cod : righe) {
                    moduloMem.query().registra(cod, Nomi.quantita.get(), quantita);
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Registra nel db memoria il valore corrente del campo prezzo unitario.
         * <p/>
         * Modifica il prezzo unitario di tutte le righe di dettaglio
         */
        private void registraCampoUnitario() {
            /* variabili e costanti locali di lavoro */
            double unitario;
            Modulo moduloMem;
            Filtro filtro;
            int[] righe;
            try {    // prova ad eseguire il codice
                moduloMem = getModMemoriaAddebiti();
                unitario = this.getUnitario();
                filtro = this.getFiltroDettagli();
                righe = moduloMem.query().valoriChiave(filtro);
                for (int cod : righe) {
                    moduloMem.query().registra(cod, Nomi.prezzo.get(), unitario);
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Sincronizza il campo sunto.
         * <p/>
         */
        private void syncSunto() {
            /* variabili e costanti locali di lavoro */
            Campo campo;
            String sunto;

            try {    // prova ad eseguire il codice
                campo = this.getCampoSunto();
                if (campo != null) {
                    sunto = this.getSunto();
                    campo.setValore(sunto);
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Sincronizza il campo importo totale.
         * <p/>
         */
        private void syncTotale() {
            /* variabili e costanti locali di lavoro */
            Number numero;
            double importo = 0;
            Double valore;
            Modulo moduloMem;
            Filtro filtro;

            try {    // prova ad eseguire il codice

                moduloMem = getModMemoriaAddebiti();
                filtro = this.getFiltroDettagli();
                numero = moduloMem.query().somma(Nomi.valore.get(), filtro);
                valore = Libreria.getDouble(numero);
                int giorni = getNumGiorni();
                if (giorni != 0) {
                    importo = valore / giorni;
                    importo = Lib.Mat.arrotonda(importo, 2);
                }// fine del blocco if

                /* assegna i valori ai campi interni */
                this.setTotale(importo);

                /* allinea i totali generali */
                syncTotali();

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Apre un dialogo contenente i dettagli.
         * <p/>
         */
        public void apriDettagli() {
            /* variabili e costanti locali di lavoro */
            boolean continua;
            Modulo mod;
            Navigatore nav = null;
            Dialogo dialogo;
            Filtro filtro;
            String titolo;
            String messaggio;

            try {    // prova ad eseguire il codice

                mod = getModMemoriaAddebiti();
                continua = mod != null;

                if (continua) {
                    nav = mod.getNavigatoreCorrente();
                    continua = nav != null;
                }// fine del blocco if

                if (continua) {

                    filtro = this.getFiltroDettagli();
                    nav.setFiltroCorrente(filtro);
                    nav.aggiornaLista();

                    titolo = "Periodi con prezzi diversi";
                    messaggio = this.getRiga().getDescrizioneListino();

                    dialogo = new DialogoAnnullaConferma(titolo);
                    dialogo.setMessaggio(messaggio);
                    dialogo.addComponente(nav.getPortaleNavigatore());

                    /* apre la transazione */
                    nav.getConnessione().startTransaction();

                    dialogo.avvia();

                    /* conclude la transazione */
                    if (dialogo.isConfermato()) {
                        nav.getConnessione().commit();
                        this.syncSunto();
                        this.syncTotale();
                    } else {
                        nav.getConnessione().rollback();
                    }// fine del blocco if-else


                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Abilita o disabilita queesto oggetto
         * <p/>
         *
         * @param flag di abilitazione
         * se è abilitato è visibile altrimenti è invisibile
         */
        private void setAbilitato(boolean flag) {

            try { // prova ad eseguire il codice

                /* visibilità dell'intero oggetto */
                this.setVisible(flag);

                /* abilitazione effettiva */
                if (flag) {
                    this.abilita();
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Abilita questo oggetto.
         * <p/>
         * quando viene abilitato controlla l'abilitazione del bottone
         * dettagli e dei campi di inserimento in funzione del numero
         * di righe di dettaglio presenti
         */
        private void abilita() {
            /* variabili e costanti locali di lavoro */
            int quanteRighe;
            boolean abilitaDettaglio = false;
            JButton botDettagli;
            int quantita = 0;
            double prezzo = 0;
            Modulo moduloMem;
            Dati dati;
            Filtro filtro;
            QuerySelezione query;
            Pannello panSwitch;

            try {    // prova ad eseguire il codice

                /* recupera il numero di righe di dettaglio */
                quanteRighe = this.getNumRigheDettaglio();

                /* se contiene una sola riga (o nessuna)
                 * recupera i valori dal db memoria e li mette
                 * nei campi di questo oggetto */
                if (quanteRighe <= 1) {
                    moduloMem = getModMemoriaAddebiti();
                    filtro = this.getFiltroDettagli();
                    query = new QuerySelezione(moduloMem);
                    query.addCampo(Nomi.quantita.get());
                    query.addCampo(Nomi.prezzo.get());
                    query.setFiltro(filtro);
                    dati = moduloMem.query().querySelezione(query);
                    if (dati != null) {
                        quantita = dati.getIntAt(Nomi.quantita.get());
                        prezzo = dati.getDoubleAt(Nomi.prezzo.get());
                        dati.close();
                    }// fine del blocco if

                    /* assegna i valori ai campi interni */
                    this.setQuantita(quantita);
                    this.setPrezzo(prezzo);

                }// fine del blocco if-else

                /**
                 * controlla la visibilità del bottone dettagli:
                 * visibile  solo se esiste più di 1 riga
                 */
                botDettagli = this.getBotDettagli();
                if (botDettagli != null) {
                    abilitaDettaglio = (quanteRighe > 1);
                    botDettagli.setVisible(abilitaDettaglio);
                }// fine del blocco if

                /**
                 * Regola il contenuto del pannello Switch:
                 * - se ci sono più righe fa vedere un riassunto dei prezzi unitari (non modificabile)
                 * - se c'è una riga sola fa vedere il prezzo unitario (modificabile)
                 */
                panSwitch = this.getPanSwitch();
                if (panSwitch != null) {
                    panSwitch.removeAll();
                    if (abilitaDettaglio) {
                        this.syncSunto();
                        panSwitch.add(this.getCampoSunto().getPannelloComponenti());
                    } else {
                        panSwitch.add(this.getCampoUnitario().getPannelloComponenti());
                    }// fine del blocco if-else
                }// fine del blocco if

                /* allinea il totale della riga */
                this.syncTotale();

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch


        }


        /**
         * Ritorna un riassunto sotto forma di testo
         * dei prezzi in tabella.
         * <p/>
         *
         * @return il testo riassuntivo dei prezzi
         */
        private String getSunto() {
            /* variabili e costanti locali di lavoro */
            String sunto = "";
            boolean continua;
            int codListino;
            Modulo mem = null;
            Filtro filtro = null;
            Ordine ordine;
            Campo campo;
            ArrayList valori = null;
            int prezzo;
            String tag = "-";

            try {    // prova ad eseguire il codice
                codListino = this.getRiga().getCodListino();
                continua = (codListino > 0);

                if (continua) {
                    mem = getModMemoriaAddebiti();
                    continua = (mem != null);
                }// fine del blocco if

                if (continua) {
                    filtro = this.getFiltroDettagli();
                    continua = (filtro != null);
                }// fine del blocco if

                if (continua) {
                    campo = mem.getCampo(Nomi.inizioValidita.get());
                    ordine = new Ordine(campo);
                    valori = mem.query().valoriCampo(Nomi.prezzo.get(), filtro, ordine);
                    continua = (valori != null && valori.size() > 0);
                }// fine del blocco if

                if (continua) {
                    for (Object ogg : valori) {
                        prezzo = Libreria.getInt(ogg);
                        sunto += prezzo;
                        sunto += tag;
                    } // fine del ciclo for-each
                    sunto = Lib.Testo.levaCoda(sunto, tag);
                }// fine del blocco if


            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return sunto;
        }


        /**
         * Ritorna la quantità dal campo interno Quantità.
         * <p/>
         *
         * @return la quantita
         */
        private int getQuantita() {
            /* variabili e costanti locali di lavoro */
            int quantita = 0;
            Object valore;
            Campo campo;

            try { // prova ad eseguire il codice
                campo = this.getCampoQuantita();
                valore = campo.getValore();
                quantita = Libreria.getInt(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return quantita;
        }


        /**
         * Assegna un valore al campo interno Quantità.
         * <p/>
         *
         * @param quantita da assegnare
         */
        private void setQuantita(int quantita) {
            /* variabili e costanti locali di lavoro */
            Campo campo;

            try { // prova ad eseguire il codice
                campo = this.getCampoQuantita();
                campo.setValore(quantita);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Assegna un valore al campo interno Prezzo unitario.
         * <p/>
         *
         * @param prezzo da assegnare
         */
        private void setPrezzo(double prezzo) {
            /* variabili e costanti locali di lavoro */
            Campo campo;

            try { // prova ad eseguire il codice
                campo = this.getCampoUnitario();
                campo.setValore(prezzo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Ritorna il prezzo unitario dal campo interno Unitario.
         * <p/>
         *
         * @return il prezzo unitario
         */
        private double getUnitario() {
            /* variabili e costanti locali di lavoro */
            double unitario = 0;
            Object valore;
            Campo campo;

            try { // prova ad eseguire il codice
                campo = this.getCampoUnitario();
                valore = campo.getValore();
                unitario = Libreria.getDouble(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return unitario;
        }


        /**
         * Assegna un valore al campo interno importo totale.
         * <p/>
         *
         * @param valore da assegnare
         */
        private void setTotale(double valore) {
            /* variabili e costanti locali di lavoro */
            Campo campo;

            try { // prova ad eseguire il codice
                campo = this.getCampoTotale();
                campo.setValore(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Ritorna il numero delle righe di dettaglio associate a questo oggetto.
         * <p/>
         *
         * @return il numero delle righe di dettaglio
         */
        private int getNumRigheDettaglio() {
            /* variabili e costanti locali di lavoro */
            int numRighe = 0;
            Modulo modMemoria;
            Filtro filtro;

            try {    // prova ad eseguire il codice
                modMemoria = getModMemoriaAddebiti();
                filtro = this.getFiltroDettagli();
                numRighe = modMemoria.query().contaRecords(filtro);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return numRighe;
        }


        /**
         * Ritorna un filtro per il DB memoria che isola tutte
         * le righe di dettaglio relative a questo oggetto.
         * <p/>
         *
         * @return il filtro per isolare le righe
         */
        private Filtro getFiltroDettagli() {
            /* variabili e costanti locali di lavoro */
            int codListino;
            Filtro filtro = null;
            try {    // prova ad eseguire il codice
                codListino = this.getRiga().getCodListino();
                filtro = FiltroFactory.crea(Nomi.codListino.get(), codListino);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return filtro;

        }


        private Riga getRiga() {
            return riga;
        }


        private void setRiga(Riga riga) {
            this.riga = riga;
        }


        private Campo getCampoQuantita() {
            return campoQuantita;
        }


        private void setCampoQuantita(Campo campoQuantita) {
            this.campoQuantita = campoQuantita;
        }


        private Campo getCampoUnitario() {
            return campoUnitario;
        }


        private void setCampoUnitario(Campo campoUnitario) {
            this.campoUnitario = campoUnitario;
        }


        private Campo getCampoTotale() {
            return campoTotale;
        }


        private void setCampoTotale(Campo campoTotale) {
            this.campoTotale = campoTotale;
        }


        private Campo getCampoSunto() {
            return campoSunto;
        }


        private void setCampoSunto(Campo campoSunto) {
            this.campoSunto = campoSunto;
        }


        private JButton getBotDettagli() {
            return botDettagli;
        }


        private void setBotDettagli(JButton botDettagli) {
            this.botDettagli = botDettagli;
        }


        private Pannello getPanSwitch() {
            return panSwitch;
        }


        private void setPanSwitch(Pannello panSwitch) {
            this.panSwitch = panSwitch;
        }


        /**
         * Azione di modifica del valore di un campo
         */
        private class CampoModificato extends CampoMemoriaAz {

            public void campoMemoriaAz(CampoMemoriaEve unEvento) {
                Campo campo;
                campo = unEvento.getCampo();

                if (campo.equals(getCampoQuantita())) {
                    quantitaModificata();
                }// fine del blocco if

                if (campo.equals(getCampoUnitario())) {
                    unitarioModificato();
                }// fine del blocco if
            }
        } // fine della classe interna


        /**
         * Listener per il bottone dettagli premuto
         * </p>
         */
        private final class AzBottone implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                apriDettagli();
            }
        } // fine della classe 'interna'


    } // fine della classe 'interna'


    /**
     * Nomi dei campi del modulo memoria.
     */
    public enum Nomi implements Campi {

        chiaveRigaOrigine("chiaverigaorigine"),
        codListino("cod"),
        codRigaListino("codrigalistino"),
        inizioValidita("dal"),
        fineValidita("al"),
        giorni("giorni"),
        quantita("quantita"),
        prezzoListino("listino"),
        prezzo("prezzo"),
        importo("importo"),
        valore("valore"),;

        /**
         * titolo da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         */
        Nomi(String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String get() {
            return titolo;
        }


        public String getTitoloColonna() {
            return titolo;
        }


        public String getEtichettaScheda() {
            return titolo;
        }


        public String getLegenda() {
            return titolo;
        }


        public boolean isVisibileLista() {
            return false;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }

    }// fine della classe


    /**
     * Nomi dei campi del modulo memoria.
     */
    public enum Totale implements Campi {

        inizio("dal"),
        fine("al"),
        giorni("giorni"),
        prezzoGiorno("prezzo"),
//        persone("persone"),
//        quantita("quantita"),
        importo("importo");

        /**
         * titolo da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         */
        Totale(String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String get() {
            return titolo;
        }


        public String getTitoloColonna() {
            return titolo;
        }


        public String getEtichettaScheda() {
            return titolo;
        }


        public String getLegenda() {
            return titolo;
        }


        public boolean isVisibileLista() {
            return false;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }

    }// fine della classe

}// fine della classe