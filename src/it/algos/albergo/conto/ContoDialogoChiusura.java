/**
 * Title:     ContoDialogoChiusura
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      10-giu-2006
 */
package it.algos.albergo.conto;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.conto.pagamento.Pagamento;
import it.algos.albergo.conto.pagamento.PagamentoModulo;
import it.algos.albergo.conto.sconto.Sconto;
import it.algos.albergo.conto.sconto.ScontoModulo;
import it.algos.albergo.conto.sospeso.Sospeso;
import it.algos.albergo.conto.sospeso.SospesoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.bottone.Bottone;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.TestoAlgos;
import it.algos.gestione.anagrafica.cliente.Cliente;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di inserimento degli addebiti.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 15.00.06
 */
public final class ContoDialogoChiusura extends DialogoAnnullaConferma {

    /* chiave campo data arrivo */
    private static final String CAMPO_DATA_ARRIVO = Conto.Cam.validoDal.get();

    /* chiave campo numero persone */
    public static final String CAMPO_NUM_PERSONE = Conto.Cam.numPersone.get();

    /* chiave campo data chiusura conto */
    private static final String CAMPO_DATA_CHIUSURA = Conto.Cam.dataChiusura.get();

    /* chiave campo totale conto */
    private static final String CAMPO_TOT_CONTO = "totale conto";

    /* chiave campo totale già pagato */
    private static final String CAMPO_TOT_GIA_PAGATO = "totale già pagato";

    /* chiave campo sconto */
    private static final String CAMPO_SCONTO = "sconto";

    /* chiave campo totale dovuto */
    private static final String CAMPO_TOT_DOVUTO = "totale dovuto";

    /* chiave campo totale a pagare */
    private static final String CAMPO_TOT_PAGARE = "totale a pagare";

    /* chiave campo importo pagato */
    private static final String CAMPO_IMPORTO_PAGATO = "importo pagato";

    /* chiave campo importo non pagato */
    private static final String CAMPO_IMPORTO_NON_PAGATO = "importo non pagato";

    /* chiave campo check saldato */
    private static final String CAMPO_SALDATO = "saldato";

    /* chiave campo mezzo pagamento */
    private static final String CAMPO_MEZZO = "a mezzo";

    /* chiave campo n. ricevuta */
    private static final String CAMPO_RICEVUTA = "ricevuta";

    /* codice del conto da chiudere */
    private int codiceConto;

    /* pannello situazione conto */
    private ContoPanTotali panTotali;

    /* label alert sospeso */
    private JLabel labelSospeso;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param codice del conto da chiudere
     */
    public ContoDialogoChiusura(int codice) {
        /* rimanda al costruttore della superclasse */

        this.setModulo(Albergo.Moduli.conto.getModulo());
        this.setCodiceConto(codice);

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
        Pannello pan;

        try { // prova ad eseguire il codice

            this.setResizable(true);

            /* regola il titolo della finestra */
            this.setTitolo("Chiusura conto");

            pan = this.creaPanCliente();
            this.addPannello(pan);

            pan = this.creaPanDate();
            this.addPannello(pan);

            pan = this.creaPanTotali();
            this.addPannello(pan);

            this.getBottoneConferma().setText("Chiudi conto");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Modifica il testo dei bottoni e ne aggiunge uno.
     * <p/>
     */
    public void setChisuraPartenza() {
        /* variabili e costanti locali di lavoro */
        Icon icona;
        Bottone bottone;

        try { // prova ad eseguire il codice
            this.getBottoneAnnulla().setText(ContoLogica.Chiusura.nonChiuso.getTitolo());
            this.getBottoneConferma().setText(ContoLogica.Chiusura.confermato.getTitolo());

            icona = this.getBottoneAnnulla().getIcon();
            bottone = this.addBottone(ContoLogica.Chiusura.interrotto.getTitolo(), true, false);
            bottone.getBottone().setName(ContoLogica.Chiusura.interrotto.toString());
            bottone.getBottone().setIcon(icona);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo


    /**
     * Crea il pannello con le date e lo aggiunge al dialogo.
     * <p/>
     *
     * @return pannello appena creato
     */
    private Pannello creaPanDate() {
        Pannello pannello = null;
        Campo campoDataArrivo;
        Campo campoNumPersone;
        Campo campoDataChiusura;
        Date dataArrivo;
        Modulo modConto;
        int codConto;

        try { // prova ad eseguire il codice

            modConto = Albergo.Moduli.Conto();
            codConto = this.getCodiceConto();

            /* recupera la data di arrivo */
            dataArrivo = modConto.query().valoreData(Conto.Cam.validoDal.get(), codConto);

            /* campo data arrivo */
            campoDataArrivo = this.copiaCampo(Conto.Cam.validoDal.get());
            campoDataArrivo.decora().eliminaLegenda();
            campoDataArrivo.decora().etichetta("data arrivo");
            campoDataArrivo.setModificabile(false);
            campoDataArrivo.setValore(dataArrivo);

            /* campo numero persone */
            campoNumPersone = this.copiaCampo(Conto.Cam.numPersone.get());
            campoNumPersone.setModificabile(false);
            campoNumPersone.setValore(this.getNumPersone());

            /* campo data chiusura conto */
            campoDataChiusura = this.copiaCampo(Conto.Cam.dataChiusura.get());
            campoDataChiusura.decora().etichetta("data chiusura");
            campoDataChiusura.decora().obbligatorio();
            campoDataChiusura.setValore(AlbergoLib.getDataProgramma());

            /* crea il pannello */
            pannello = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pannello.creaBordo();

            /* aggiunge i campi */
            pannello.add(campoDataArrivo);
            pannello.add(campoNumPersone);
            pannello.add(campoDataChiusura);

            pannello.sbloccaLarMax();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea il pannello cliente e lo aggiunge al dialogo.
     * <p/>
     *
     * @return pannello appena creato
     */
    private Pannello creaPanCliente() {
        Pannello pannello = null;
        int codCliente;
        Modulo mod;
        EstrattoBase estratto;
        String stringa;
        String nomeCliente;

        try { // prova ad eseguire il codice

            /* crea il pannello */
            pannello = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pannello.setGapPreferito(0);
            pannello.creaBordo("conto");

            pannello.setPreferredSize(400, 100);

            /* aggiunge la sigla del conto */
            mod = Albergo.Moduli.Conto();
            stringa = mod.query().valoreStringa(Conto.Cam.sigla.get(), this.getCodiceConto());
            pannello.add(new JLabel(stringa));

            /* aggiunge il nome del cliente */
            codCliente = this.getModulo().query().valoreInt(Conto.Cam.pagante.get(),
                    this.getCodiceConto());
            mod = Albergo.Moduli.Cliente();
            estratto = mod.getEstratto(Cliente.Estratto.descrizione, codCliente);
            nomeCliente = estratto.getStringa();
            pannello.add(new JLabel(nomeCliente));

            pannello.sbloccaLarMax();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea e registra il pannello totali del conto.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanTotali() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        ContoPanTotali panTot;
        Pannello panPagato;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);

            /* pannello situazione conto */
            panTot = this.creaPanSituazione();
            pan.add(panTot);

            /* pannello pagamento */
            panPagato = this.creaPanPagamento();
            pan.add(panPagato);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea e registra il pannello situazione conto.
     * <p/>
     *
     * @return il pannello creato
     */
    private ContoPanTotali creaPanSituazione() {
        /* variabili e costanti locali di lavoro */
        ContoPanTotali pan = null;

        try {    // prova ad eseguire il codice
            pan = new ContoPanTotali();
            this.setPanTotali(pan);
            pan.avvia(this.getCodiceConto());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello pagamento.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanPagamento() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Campo campo;
        ContoPanTotali panTotali;
        double importo;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(10);
            pan.getPanFisso().setMaximumSize(1000, 1000);
            pan.creaBordo(20, 40, 20, 20, "Pagamento");

            /* campo totale dovuto */
            campo = CampoFactory.valuta(CAMPO_TOT_DOVUTO);
            campo.setModificabile(false);
            campo.setBackgroundColor(new Color(189, 140, 191));
            panTotali = this.getPanTotali();
            importo = panTotali.getTotaleDovuto();
            campo.setValore(importo);
            pan.add(campo);

            /* campo sconto */
            campo = CampoFactory.valuta(CAMPO_SCONTO);
            campo.setFont(FontFactory.creaScreenFont(Font.BOLD));
            pan.add(campo);

            /* campo totale a pagare */
            campo = CampoFactory.valuta(CAMPO_TOT_PAGARE);
            campo.setModificabile(false);
            pan.add(campo);

            /* pannello pagamento a saldo */
            pan.add(this.creaPanSaldo());

            /* pannello importo non pagato */
            pan.add(this.creaPanNonPagato());

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello importo pagato/saldato.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanSaldo() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Campo campo;
        Modulo modPagamento;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(10);

            modPagamento = PagamentoModulo.get();

            campo = CampoFactory.valuta(CAMPO_IMPORTO_PAGATO);
            campo.setFont(FontFactory.creaScreenFont(Font.BOLD));
            pan.add(campo);

            campo = CampoFactory.checkBox(CAMPO_SALDATO);
//            campo.setLarScheda(70);
            pan.add(campo);

            campo = modPagamento.getCloneCampo(Pagamento.Cam.mezzo.get());
            campo.setNomeInternoCampo(CAMPO_MEZZO);
            pan.add(campo);

            campo = modPagamento.getCloneCampo(Pagamento.Cam.ricevuta.get());
            campo.setNomeInternoCampo(CAMPO_RICEVUTA);
            pan.add(campo);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello importo non pagato.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanNonPagato() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Campo campo;
        JLabel label;
        Icon icona;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(10);
            pan.setAllineamento(Layout.ALLINEA_BASSO);

            /* campo importo non pagato */
            campo = CampoFactory.valuta(CAMPO_IMPORTO_NON_PAGATO);
            campo.setModificabile(false);
            pan.add(campo);

            /* etichetta alert sospeso */
            label = new JLabel("verrà generato un sospeso!");
            this.setLabelSospeso(label);
            TestoAlgos.setLegenda(label);
            icona = Lib.Risorse.getIconaBase("Danger16");
            label.setIcon(icona);
            pan.add(label);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Ritorna il numero di persone registrato nel conto
     * <p/>
     *
     * @return il numero di persone
     */
    private int getNumPersone() {
        /* variabili e costanti locali di lavoro */
        int quante = 0;
        int codConto;
        Modulo modConto;

        try { // prova ad eseguire il codice
            codConto = this.getCodiceConto();
            modConto = ContoModulo.get();
            quante = modConto.query().valoreInt(Conto.Cam.numPersone.get(), codConto);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quante;
    }


    /**
     * Crea le registrazioni di pagamento conseguenti alla chiusura conto.
     * <p/>
     *
     * @return true se riuscito
     */
    public boolean creaPagamenti() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        double importo;
        Pagamento.TitoloPagamento titolo;
        int mezzo;
        int ricevuta;

        try {    // prova ad eseguire il codice

            /* cancella tutti i sospesi precedenti */
            if (continua) {
                continua = this.eliminaSospesi();
            }// fine del blocco if

            /* crea la registrazione di sconto */
            if (continua) {
                importo = this.getImportoSconto();
                if (importo != 0) {
                    continua = this.creaSconto(importo);
                }// fine del blocco if
            }// fine del blocco if

            /* crea la registrazione di pagamento */
            if (continua) {
                importo = this.getImportoPagato();
                titolo = Pagamento.TitoloPagamento.saldo;
                mezzo = this.getMezzoPagamento();
                ricevuta = this.getRicevuta();
                if (importo != 0) {
                    continua = this.creaPagamento(titolo, importo, mezzo, ricevuta);
                }// fine del blocco if
            }// fine del blocco if

            /* crea la registrazione di sospeso */
            if (continua) {
                importo = this.getImportoNonPagato();
                if (importo != 0) {
                    continua = this.creaSospeso(importo);
                }// fine del blocco if
            }// fine del blocco if

            /* valore di ritorno */
            riuscito = continua;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Elimina tutti i sospesi esistenti per il conto.
     * <p/>
     *
     * @return true se riuscito
     */
    private boolean eliminaSospesi() {
        /* variabili e costanti locali di lavoro */
        Modulo modSospeso;
        int codConto;
        Filtro filtro;
        int[] chiavi;
        int chiave;
        boolean riuscito = true;

        try {    // prova ad eseguire il codice

            modSospeso = SospesoModulo.get();
            codConto = this.getCodiceConto();
            filtro = FiltroFactory.crea(Sospeso.Cam.conto.get(), codConto);
            chiavi = modSospeso.query().valoriChiave(filtro);
            for (int k = 0; k < chiavi.length; k++) {
                chiave = chiavi[k];
                riuscito = modSospeso.query().eliminaRecord(chiave);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea una singola registrazione di pagamento.
     * <p/>
     * La registrazione viene creata solo se l'importo è diverso da zero.
     *
     * @param titolo del pagamento
     * @param importo pagato
     * @param mezzo di pagamento
     * @param ricevuta di pagamento emessa
     *
     * @return true se riuscito
     */
    private boolean creaPagamento(Pagamento.TitoloPagamento titolo,
                                  double importo,
                                  int mezzo,
                                  int ricevuta) {
        /* variabili e costanti locali di lavoro */
        Modulo modPagamento;
        int codConto;
        Date data;
        CampoValore cv;

        try {    // prova ad eseguire il codice

            if (importo != 0) {

                modPagamento = PagamentoModulo.get();
                data = this.getDataChiusura();
                codConto = this.getCodiceConto();
                ArrayList<CampoValore> valori;
                valori = new ArrayList<CampoValore>();

                cv = new CampoValore(Pagamento.Cam.data.get(), data);
                valori.add(cv);

                cv = new CampoValore(Pagamento.Cam.conto.get(), codConto);
                valori.add(cv);

                cv = new CampoValore(Pagamento.Cam.titolo.get(), titolo.getCodice());
                valori.add(cv);

                cv = new CampoValore(Pagamento.Cam.importo.get(), importo);
                valori.add(cv);

                cv = new CampoValore(Pagamento.Cam.mezzo.get(), mezzo);
                valori.add(cv);

                cv = new CampoValore(Pagamento.Cam.ricevuta.get(), ricevuta);
                valori.add(cv);

                modPagamento.query().nuovoRecord(valori);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return true;
    }


    /**
     * Crea una singola registrazione di sconto.
     * <p/>
     * La registrazione viene creata solo se l'importo è diverso da zero.
     *
     * @param importo pagato
     *
     * @return true se riuscito
     */
    private boolean creaSconto(double importo) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Modulo modSconto;
        int codConto;
        Date data;
        CampoValore cv;
        int codice;

        try {    // prova ad eseguire il codice

            if (importo != 0) {

                modSconto = ScontoModulo.get();
                data = this.getDataChiusura();
                codConto = this.getCodiceConto();

                ArrayList<CampoValore> valori;
                valori = new ArrayList<CampoValore>();

                cv = new CampoValore(Sconto.Cam.data.get(), data);
                valori.add(cv);

                cv = new CampoValore(Sconto.Cam.conto.get(), codConto);
                valori.add(cv);

                cv = new CampoValore(Sconto.Cam.importo.get(), importo);
                valori.add(cv);

                codice = modSconto.query().nuovoRecord(valori);
                if (codice > 0) {
                    riuscito = true;
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea una singola registrazione di sospeso.
     * <p/>
     * La registrazione viene creata solo se l'importo è diverso da zero.
     *
     * @param importo pagato
     *
     * @return true se riuscito
     */
    private boolean creaSospeso(double importo) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Modulo modSospeso;
        int codConto;
        Date data;
        CampoValore cv;
        int codice;

        try {    // prova ad eseguire il codice

            if (importo != 0) {

                modSospeso = SospesoModulo.get();
                data = this.getDataChiusura();
                codConto = this.getCodiceConto();

                ArrayList<CampoValore> valori;
                valori = new ArrayList<CampoValore>();

                cv = new CampoValore(Sospeso.Cam.data.get(), data);
                valori.add(cv);

                cv = new CampoValore(Sospeso.Cam.conto.get(), codConto);
                valori.add(cv);

                cv = new CampoValore(Sospeso.Cam.importo.get(), importo);
                valori.add(cv);

                codice = modSospeso.query().nuovoRecord(valori);
                if (codice > 0) {
                    riuscito = true;
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Ritorna la data di chiusura conto inserita nel dialogo.
     * <p/>
     *
     * @return la data di chiusura conto
     */
    public Date getDataChiusura() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Object valore;

        /* valore di ritorno */
        try {    // prova ad eseguire il codice
            valore = this.getValore(CAMPO_DATA_CHIUSURA);
            data = Libreria.getDate(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna l'importo pagato.
     * <p/>
     *
     * @return l'importo pagato
     */
    private double getImportoPagato() {
        return this.getImporto(CAMPO_IMPORTO_PAGATO);
    }


    /**
     * Ritorna l'importo dello sconto.
     * <p/>
     *
     * @return l'importo dello sconto
     */
    private double getImportoSconto() {
        return this.getImporto(CAMPO_SCONTO);
    }


    /**
     * Ritorna l'importo non pagato.
     * <p/>
     *
     * @return l'importo pagato
     */
    private double getImportoNonPagato() {
        return this.getImporto(CAMPO_IMPORTO_NON_PAGATO);
    }


    /**
     * Ritorna il codice del mezzo di pagamento.
     * <p/>
     *
     * @return il codice del mezzo di pagamento
     */
    private int getMezzoPagamento() {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        Object valore;

        try {    // prova ad eseguire il codice
            valore = this.getValore(CAMPO_MEZZO);
            cod = Libreria.getInt(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * Ritorna il numero della ricevuta di pagamento.
     * <p/>
     *
     * @return il numero della ricevuta di pagamento
     */
    private int getRicevuta() {
        /* variabili e costanti locali di lavoro */
        int num = 0;
        Object valore;

        try {    // prova ad eseguire il codice
            valore = this.getValore(CAMPO_RICEVUTA);
            num = Libreria.getInt(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return num;
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo cambiato
     */
    @Override protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valore;
        boolean flag;

        super.eventoMemoriaModificata(campo);

        try { // prova ad eseguire il codice

            /* modifica campo check saldato */
            if (campo == this.getCampo(CAMPO_SALDATO)) {
                valore = this.getValore(campo);
                flag = Libreria.getBool(valore);
                if (flag) {   // messo check
                    valore = this.getValore(CAMPO_TOT_PAGARE);
                    this.setValore(CAMPO_IMPORTO_PAGATO, valore);
                } else {     // tolto check
                    this.setValore(CAMPO_IMPORTO_PAGATO, 0);
                }// fine del blocco if-else
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Object valore;
        boolean flag;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* sincronizza i campi dei totali */
            this.syncTotali();

            /* regola la modificabilità delo campo importo pagato */
            valore = this.getValore(CAMPO_SALDATO);
            flag = Libreria.getBool(valore);
            campo = this.getCampo(CAMPO_IMPORTO_PAGATO);
            campo.setModificabile(!flag);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizza i valori nei pannelli dei totali.
     * <p/>
     */
    private void syncTotali() {
        /* variabili e costanti locali di lavoro */
        double totDovuto;
        double totSconto;
        double totPagare;
        double totPagato;
        double totNonPagato;
        JLabel label;


        try {    // prova ad eseguire il codice

            /* sincronizza il totale a pagare (totale dovuto - sconto) */
            totDovuto = this.getImporto(CAMPO_TOT_DOVUTO);
            totSconto = this.getImporto(CAMPO_SCONTO);
            totPagare = totDovuto - totSconto;
            totPagare = Lib.Mat.arrotonda(totPagare, 2);
            this.setImporto(CAMPO_TOT_PAGARE, totPagare);

            /* sincronizza il totale non pagato (tot a pagare - tot pagato)*/
            totPagato = this.getImporto(CAMPO_IMPORTO_PAGATO);
            totNonPagato = totPagare - totPagato;
            totNonPagato = Lib.Mat.arrotonda(totNonPagato, 2);
            this.setImporto(CAMPO_IMPORTO_NON_PAGATO, totNonPagato);

            /* visibilità della label alert sospeso - solo se non pagato != 0*/
            label = this.getLabelSospeso();
            label.setVisible(totNonPagato != 0);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato quando si preme il bottone conferma o registra.
     * <p/>
     * Controlla se la chiusura determina la generazione di un sospeso
     * In tal caso chiede conferma prima di procedere
     */
    public void confermaRegistra() {
        /* variabili e costanti locali di lavoro */
        double importo;
        String avviso;
        MessaggioDialogo dialogo;
        boolean continua = true;


        try { // prova ad eseguire il codice
            importo = this.getImportoNonPagato();

            if (importo != 0) {

                avviso = "Attenzione: la chiusura di questo conto comporta\n";
                avviso += "la registrazione di un pagamento sospeso!\n";
                avviso += "Vuoi continuare?";
                dialogo = new MessaggioDialogo(avviso);

                /* procede solo dopo conferma esplicita */
                if (dialogo.getRisposta() == JOptionPane.NO_OPTION) {
                    continua = false;
                }// fine del blocco if

            }// fine del blocco if

            if (continua) {
                super.confermaRegistra();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna l'importo data la chiave del campo.
     * <p/>
     *
     * @param chiaveCampo la chiave del campo
     *
     * @return l'importo del campo
     */
    private double getImporto(String chiaveCampo) {
        /* variabili e costanti locali di lavoro */
        double importo = 0;
        Campo campo;
        Object valore;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(chiaveCampo);
            valore = campo.getValore();
            importo = Libreria.getDouble(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return importo;
    }


    /**
     * Assegna un importo a un campo.
     * <p/>
     *
     * @param chiaveCampo la chiave del campo
     * @param importo da assegnare
     */
    private void setImporto(String chiaveCampo, double importo) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(chiaveCampo);
            campo.setValore(importo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    private int getCodiceConto() {
        return codiceConto;
    }


    private void setCodiceConto(int codiceConto) {
        this.codiceConto = codiceConto;
    }


    private ContoPanTotali getPanTotali() {
        return panTotali;
    }


    private void setPanTotali(ContoPanTotali panTotali) {
        this.panTotali = panTotali;
    }


    private JLabel getLabelSospeso() {
        return labelSospeso;
    }


    private void setLabelSospeso(JLabel labelSospeso) {
        this.labelSospeso = labelSospeso;
    }

}// fine della classe
