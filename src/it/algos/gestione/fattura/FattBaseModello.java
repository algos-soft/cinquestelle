/**
 * Title:     FatturaModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-feb-2004
 */
package it.algos.gestione.fattura;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.campo.video.decorator.CVDCongelato;
import it.algos.base.campo.video.decorator.CVDecoratore;
import it.algos.base.campo.video.decorator.VideoFactory;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.documento.DocumentoModello;
import it.algos.base.errore.Errore;
import it.algos.base.evento.db.DbTriggerAz;
import it.algos.base.evento.db.DbTriggerEve;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.CampoValore;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.fattura.fatt.FattModulo;
import it.algos.gestione.fattura.riga.RigaFattura;
import it.algos.gestione.fattura.riga.RigaFatturaModulo;
import it.algos.gestione.tabelle.contibanca.ContiBanca;
import it.algos.gestione.tabelle.iva.Iva;
import it.algos.gestione.tabelle.iva.IvaModulo;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamento;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamentoModulo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Modello dati del documento Fattura.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Mantiene il nome della tavola di archivo dove sono registrati tutti i
 * dati (records) del modello </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) </li>
 * <li> Un eventuale file di dati iniziali va regolato come percorso e nomi dei
 * campi presenti </li>
 * <li> Eventuali <strong>moduli e tabelle</strong> vanno creati nel metodo <code>
 * regolaModuli</code> </li>
 * <li> Regola i titoli delle finestre lista e scheda
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-feb-2004 ore 6.54.44
 */
public abstract class FattBaseModello extends DocumentoModello implements FattBase {

    private static final String DOC = "Fattura";

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public FattBaseModello() {
        /* rimanda al costruttore della superclasse */
        super(DOC);

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
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);

        this.setTriggerModificaAttivo(true, true);

    }// fine del metodo inizia


    public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        Modello modello;


        super.inizializza(unModulo);

        try { // prova ad eseguire il codice

            /* si registra presso il modello delle righe */
            /* per ricevere gli eventi trigger */
            modulo = RigaFatturaModulo.get();
            modello = modulo.getModello();
            modello.addListener(Modello.Evento.trigger, new AzTriggerRiga());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;

    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli
     * @see it.algos.base.modello.ModelloAlgos#creaCampi
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        CampoLogica cl;

        try { // prova ad eseguire il codice

            /**
             * invoca il metodo sovrascritto della superclasse<br>
             * Il modello DocumentoModello aggiunge automaticamente i campi:
             * - numero documento,
             * - data documento,
             * - riferimenti,
             * - confermato,
             * - dataconferma;
             */
            super.creaCampi();

            /* campo link al cliente */
            unCampo = CampoFactory.comboLinkSel(FattBase.Cam.cliente);
            unCampo.decora().obbligatorio();
            unCampo.setNomeModuloLinkato(Anagrafica.MODULO_CLIENTE);
            unCampo.setNomeVistaLinkata(Anagrafica.Vis.soggetto.toString());
            unCampo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
//            unCampo.decora().estrattoSotto(Cliente.Estratto.descrizione);
            unCampo.setLarScheda(150);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo partita iva / codice fiscale cliente */
            unCampo = CampoFactory.testo(FattBase.Cam.picf);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo etichetta con indirizzo del destinatario */
            unCampo = CampoFactory.testoArea(FattBase.Cam.destinatario);
            unCampo.setAbilitato(false);
            unCampo.setNumeroRighe(4);
            this.addCampo(unCampo);

            /* campo etichetta con indirizzo di destinazione merce
             * o di recapito del documento, se diverso dal destinatario */
            unCampo = CampoFactory.testoArea(FattBase.Cam.destinazione);
            this.addCampo(unCampo);

            /* campo riferimenti nostri */
            unCampo = CampoFactory.testo(FattBase.Cam.rifNostri);
            unCampo.getValidatore().setLunghezzaMassima(60);
            unCampo.setLarghezza(350);
            this.addCampo(unCampo);

            /* campo riferimenti cliente */
            unCampo = CampoFactory.testo(FattBase.Cam.rifCliente);
            unCampo.getValidatore().setLunghezzaMassima(60);
            unCampo.setLarghezza(350);
            this.addCampo(unCampo);

            /* campo tipo documento */
            unCampo = CampoFactory.comboInterno(FattBase.Cam.tipodoc);
            unCampo.decora().obbligatorio();
            unCampo.setValoriInterni(FattBase.TipoDoc.getLista());
            unCampo.setUsaNonSpecificato(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo flag estero */
            unCampo = CampoFactory.checkBox(FattBase.Cam.estero);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo link pagamento */
            unCampo = CampoFactory.comboLinkPop(FattBase.Cam.pagamento);
            unCampo.decora().obbligatorio();
            unCampo.decora().congelato(FattBase.Cam.pagamentofix.get());
            unCampo.setNomeModuloLinkato(TipoPagamento.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(TipoPagamento.CAMPO_DESCRIZIONE);
            unCampo.setNomeCampoValoriLinkato(TipoPagamento.CAMPO_SIGLA);
//            unCampo.decora().estrattoSotto(TipoPagamento.Estratto.descrizione);
            this.addCampo(unCampo);

            /* campo utilizzo banca */
            unCampo = CampoFactory.checkBox(FattBase.Cam.usaBanca);
            this.addCampo(unCampo);

            /* campo link conto bancario */
            unCampo = CampoFactory.comboLinkPop(FattBase.Cam.contoBanca);
            unCampo.setNomeModuloLinkato(ContiBanca.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(ContiBanca.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(ContiBanca.Cam.sigla.get());
//            unCampo.decora().estrattoSotto(ContiBanca.Estratto.nome);
            this.addCampo(unCampo);

            /* campo coordinate bancarie (nome banca + coordinate) */
            unCampo = CampoFactory.testo(FattBase.Cam.coordBanca);
            unCampo.getValidatore().setLunghezzaMassima(100);
            unCampo.setLarghezza(300);
            this.addCampo(unCampo);

            /* campo data scadenza */
            unCampo = CampoFactory.data(FattBase.Cam.dataScadenza);
            this.addCampo(unCampo);

            /* campo pagamento fissato a testo */
            unCampo = CampoFactory.testo(FattBase.Cam.pagamentofix);
            this.addCampo(unCampo);

            /* campo codice iva di default */
            unCampo = CampoFactory.comboLinkPop(FattBase.Cam.codIva);
            unCampo.setNomeModuloLinkato(Iva.NOME_MODULO);
            this.addCampo(unCampo);

            /* campo percentuale iva generale della fattura */
            unCampo = CampoFactory.percentuale(FattBase.Cam.percIva);
            this.addCampo(unCampo);

            /* campo percentuale di sconto di default */
            unCampo = CampoFactory.sconto(FattBase.Cam.percSconto);
            this.addCampo(unCampo);

            /* campo flag applica rivalsa INPS */
            unCampo = CampoFactory.checkBox(FattBase.Cam.applicaRivalsa);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo flag applica r.a. */
            unCampo = CampoFactory.checkBox(FattBase.Cam.applicaRA);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo percentuale di rivalsa inps */
            unCampo = CampoFactory.percentuale(FattBase.Cam.percRivalsa);
            this.addCampo(unCampo);

            /* campo percentuale di r.a. */
            unCampo = CampoFactory.percentuale(FattBase.Cam.percRA);
            this.addCampo(unCampo);

            /* campo righe */
            unCampo = CampoFactory.navigatore(FattBase.Cam.righe,
                    RigaFattura.NOME_MODULO,
                    RigaFattura.NAV_FATTURA);
            this.addCampo(unCampo);

            /* campo pagato */
            unCampo = CampoFactory.checkBox(FattBase.Cam.pagato);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo imponibile lordo
             * (somma del campo imponibile delle righe) */
            unCampo = CampoFactory.valuta(FattBase.Cam.imponibileLordo);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo importo rivalsa INPS
             * (imponibile lordo x perc. rivalsa se ApplicaRivasa è acceso) */
            unCampo = CampoFactory.valuta(FattBase.Cam.importoRivalsa);
            cl = new CLCalcProdCond(unCampo,
                    FattBase.Cam.imponibileLordo,
                    FattBase.Cam.percRivalsa,
                    FattBase.Cam.applicaRivalsa);
            unCampo.setCampoLogica(cl);
            this.addCampo(unCampo);

            /* campo imponibile netto
             * (imponibile lordo + rivalsa INPS) */
            unCampo = CampoFactory.calcola(FattBase.Cam.imponibileNetto,
                    CampoLogica.Calcolo.sommaValuta,
                    FattBase.Cam.imponibileLordo,
                    FattBase.Cam.importoRivalsa);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo importo IVA */
            unCampo = CampoFactory.valuta(FattBase.Cam.importoIva);
            unCampo.setAbilitato(false);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo totale lordo
     * (imponibile netto + importo iva netto) */
            unCampo = CampoFactory.calcola(FattBase.Cam.totaleLordo,
                    CampoLogica.Calcolo.sommaValuta,
                    FattBase.Cam.imponibileNetto,
                    FattBase.Cam.importoIva);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo importo r.a.
             * (imponibile netto x perc. r.a. se ApplicaRA è acceso) */
            unCampo = CampoFactory.valuta(FattBase.Cam.importoRA);
            cl = new CLCalcProdCond(unCampo,
                    FattBase.Cam.imponibileNetto,
                    FattBase.Cam.percRA,
                    FattBase.Cam.applicaRA);
            unCampo.setCampoLogica(cl);
            this.addCampo(unCampo);

            /* campo totale netto
             * (totale lordo - importo r. a.) */
            unCampo = CampoFactory.calcola(FattBase.Cam.totaleNetto,
                    CampoLogica.Calcolo.differenzaValuta,
                    FattBase.Cam.totaleLordo,
                    FattBase.Cam.importoRA);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>)
     * per individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli()
     * @see #regolaViste
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try { // prova ad eseguire il codice

            vista = new Vista(Vis.standard.toString(), this.getModulo());
            vista.addCampo(FattBase.Cam.numeroDoc.get());
            vista.addCampo(FattBase.Cam.dataDoc.get());
            vista.addCampo(FattBase.Cam.cliente.get());
            vista.addCampo(FattBase.Cam.imponibileNetto.get());
            vista.addCampo(FattBase.Cam.importoIva.get());
            vista.addCampo(FattBase.Cam.totaleNetto.get());
            vista.addCampo(FattBase.Cam.confermato.get());
            vista.addCampo(FattBase.Cam.dataScadenza.get());
            vista.addCampo(FattBase.Cam.pagato.get());
            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia dei
     * campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #creaViste
     */
    protected void regolaViste() {
        int a = 87;
    }


    /**
     * Ritorna la motivazione per cui un documento non è confermabile.
     * <p/>
     *
     * @param codice del record da controllare
     *
     * @return la motivazione, stringa vuota se confermabile
     */
    public String checkConfermabile(int codice) {
        /* variabili e costanti locali di lavoro */
        String spiega = "";
        Query query;
        Filtro filtro;
        Dati dati;
        int intero;
        Date data;
        boolean bool;
        String testo;

        try { // prova ad eseguire il codice

            /* la superclasse controlla che ci sia il numero documento */
            spiega = super.checkConfermabile(codice);

            query = new QuerySelezione(this.getModulo());
            filtro = FiltroFactory.codice(this.getModulo(), codice);
            query.setFiltro(filtro);
            query.addCampo(FattBase.Cam.dataDoc.get());
            query.addCampo(FattBase.Cam.cliente.get());
            query.addCampo(FattBase.Cam.pagamento.get());
            query.addCampo(FattBase.Cam.dataScadenza.get());
            query.addCampo(FattBase.Cam.codIva.get());
            query.addCampo(FattBase.Cam.usaBanca.get());
            query.addCampo(FattBase.Cam.coordBanca.get());
            dati = this.query().querySelezione(query);

            /* controlla che ci sia la data documento */
            data = dati.getDataAt(0, this.getCampo(FattBase.Cam.dataDoc.get()));
            if (Lib.Data.isVuota(data)) {
                spiega += "- Manca la data della fattura\n";
            }// fine del blocco if

            /* controlla che ci sia il cliente */
            intero = dati.getIntAt(0, this.getCampo(FattBase.Cam.cliente.get()));
            if (intero <= 0) {
                spiega += "- Manca il cliente\n";
            }// fine del blocco if

            /* controlla che ci sia il codice pagamento */
            intero = dati.getIntAt(0, this.getCampo(FattBase.Cam.pagamento.get()));
            if (intero <= 0) {
                spiega += "- Manca il codice di pagamento\n";
            }// fine del blocco if

            /* controlla che ci sia la data di scadenza */
            data = dati.getDataAt(0, this.getCampo(FattBase.Cam.dataScadenza.get()));
            if (Lib.Data.isVuota(data)) {
                spiega += "- Manca la data di scadenza\n";
            }// fine del blocco if

            /* controlla che ci sia il codice iva */
            intero = dati.getIntAt(0, this.getCampo(FattBase.Cam.codIva.get()));
            if (intero <= 0) {
                spiega += "- Manca il codice iva\n";
            }// fine del blocco if

            /* se la fattura usa la banca, controlla che ci siano le coordinate */
            bool = dati.getBoolAt(0, this.getCampo(FattBase.Cam.usaBanca.get()));
            if (bool) {
                testo = dati.getStringAt(0, this.getCampo(FattBase.Cam.coordBanca.get()));
                if (Lib.Testo.isVuota(testo)) {
                    spiega += "- Mancano le coordinate bancarie\n";
                }// fine del blocco if
            }// fine del blocco if

            /* controlla che ci siano delle righe */
            Modulo modRighe = RigaFatturaModulo.get();
            filtro = FiltroFactory.crea(RigaFattura.Cam.fattura.get(), codice);
            intero = modRighe.query().contaRecords(filtro);
            if (intero <= 0) {
                spiega += "- Non ci sono righe\n";
            }// fine del blocco if

            dati.close();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return spiega;
    }


    /**
     * Sincronizza i totali della fattura alla
     * creazione/modifica/eliminazione di una riga.
     * <p/>
     * Sincronizza imponibile lordo e importo iva lordo
     * L'imponibile lordo è la somma degli imponibili delle righe
     * L'importo IVA lordo è:
     * - in caso di IVA singola, l'imponibile netto per la percentuale IVA
     * - in caso di IVA mista, la somma dell'IVA delle righe più l'iva sulla rivalsa
     *
     * @param codFattura della fattura da sincronizzare
     * @param conn connessione da utilizzare
     */
    private void syncTotali(int codFattura, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Modulo modFat;
        Modulo modRighe;
        Query query;
        Filtro filtro;
        Dati dati;
        Double totImpoLordo;
        Double totIva;
        CampoValore cv;
        String nomeCampoLink;
        ArrayList<CampoValore> lista;
        ArrayList valori;
        Object unValore;
        int currcodiva;
        int oldcodiva = 0;
        boolean mista = false;
        int numDec;
        Campo campo;

        try {    // prova ad eseguire il codice

            modFat = this.getModulo();
            modRighe = RigaFatturaModulo.get();

            /* acquisisce alcuni dati dalla fattura */
            Campo campoUsaRivalsa = modFat.getCampo(FattBase.Cam.applicaRivalsa.get());
            Campo campoPercRivalsa = modFat.getCampo(FattBase.Cam.percRivalsa.get());
            Campo campoPercIva = modFat.getCampo(FattBase.Cam.percIva.get());
            Campo campoImportoRivalsa = modFat.getCampo(FattBase.Cam.importoRivalsa.get());
            query = new QuerySelezione(this.getModulo());
            query.addCampo(campoUsaRivalsa);
            query.addCampo(campoPercRivalsa);
            query.addCampo(campoPercIva);
            query.addCampo(campoImportoRivalsa);
            filtro = FiltroFactory.codice(this.getModulo(), codFattura);
            query.setFiltro(filtro);
            dati = this.query().querySelezione(query, conn);
            boolean usaRivalsa = dati.getBoolAt(0, campoUsaRivalsa);
            double percRivalsa = dati.getDoubleAt(0, campoPercRivalsa);
            double percIva = dati.getDoubleAt(0, campoPercIva);
            dati.close();

            /* determina il nuovo totale imponibile lordo
             * sommando gli imponibili delle righe */
            nomeCampoLink = RigaFattura.Cam.fattura.get();
            totImpoLordo = (Double)modRighe.query().somma(RigaFattura.Cam.imponibile.get(),
                    nomeCampoLink,
                    codFattura,
                    conn);

            /* determina il nuovo importo rivalsa */
            double importoRivalsa = 0;
            if (usaRivalsa) {
                importoRivalsa = totImpoLordo * percRivalsa;
                campo = modFat.getCampo(FattBase.Cam.importoRivalsa.get());
                numDec = campo.getCampoDati().getNumDecimali();
                importoRivalsa = Lib.Mat.arrotonda(importoRivalsa, numDec);
            }// fine del blocco if

            /* determina se l'iva è singola o mista */
            filtro = FiltroFactory.crea(RigaFattura.Cam.fattura.get(), codFattura);
            valori = modRighe.query().valoriCampo(RigaFattura.Cam.codIva.get(), filtro, conn);
            for (int k = 0; k < valori.size(); k++) {
                unValore = valori.get(k);
                if (unValore != null) {
                    if (unValore instanceof Integer) {
                        currcodiva = (Integer)unValore;

                        /* la prima volta è uguale */
                        if (k == 0) {
                            oldcodiva = currcodiva;
                        }// fine del blocco if

                        if (currcodiva != oldcodiva) {
                            mista = true;
                            break;
                        }// fine del blocco if
                        oldcodiva = currcodiva;

                    }// fine del blocco if
                }// fine del blocco if
            } // fine del ciclo for

            /**
             * Determina l'importo totale IVA
             * - Se l'iva è mista, la somma dell'iva delle righe più l'iva sulla rivalsa
             * - Se l'iva è singola, l'imponibile netto per la percentuale IVA
             */
            if (mista) {

                /* somma iva righe */
                double totIvaRighe =
                        (Double)modRighe.query().somma(RigaFattura.Cam.importoIva.get(),
                                nomeCampoLink,
                                codFattura,
                                conn);

                /* importo iva su rivalsa */
                double totIvaRivalsa = 0;
                if (usaRivalsa) {
                    totIvaRivalsa = importoRivalsa * percIva;
                    campo = modFat.getCampo(FattBase.Cam.importoIva.get());
                    numDec = campo.getCampoDati().getNumDecimali();
                    totIvaRivalsa = Lib.Mat.arrotonda(totIvaRivalsa, numDec);
                }// fine del blocco if

                totIva = totIvaRighe + totIvaRivalsa;

            } else { // iva singola

                /* determina il nuovo totale imponibile netto */
                double totImpoNetto = totImpoLordo + importoRivalsa;

                /* determina l'importo totale IVA */
                double importoIva = totImpoNetto * percIva;
                campo = modFat.getCampo(FattBase.Cam.importoIva.get());
                numDec = campo.getCampoDati().getNumDecimali();
                totIva = Lib.Mat.arrotonda(importoIva, numDec);

            }// fine del blocco if-else

            /* crea la lista dei totali da registrare */
            lista = new ArrayList<CampoValore>();
            cv = new CampoValore(FattBase.Cam.imponibileLordo.get(), totImpoLordo);
            lista.add(cv);
            cv = new CampoValore(FattBase.Cam.importoIva.get(), totIva);
            lista.add(cv);

            /* registra i totali */
            modFat.query().registraRecordValori(codFattura, lista, conn);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    @Override protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Campo campo;
        CampoValore cv;
        FattModulo modFattura;
        int intero;
        double percIvaDefault;
        double doppio;

        try { // prova ad eseguire il codice

            modFattura = (FattModulo)this.getModulo();

            /* imposta il tipo documento */
            campo = this.getCampo(FattBase.Cam.tipodoc.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                lista.add(cv);
            }// fine del blocco if-else
            cv.setValore(modFattura.getTipoDocumento().getValore());

            /* imposta il codice iva dal default */
            campo = this.getCampo(FattBase.Cam.codIva.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                lista.add(cv);
            }// fine del blocco if-else
            intero = IvaModulo.get().getRecordPreferito();
            cv.setValore(intero);

            /* imposta il codice pagamento dal default */
            campo = this.getCampo(FattBase.Cam.pagamento.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                lista.add(cv);
            }// fine del blocco if-else
            intero = TipoPagamentoModulo.get().getRecordPreferito();
            cv.setValore(intero);

            /* imposta sempre la percentuale iva di default */
            campo = this.getCampo(FattBase.Cam.percIva.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                lista.add(cv);
            }// fine del blocco if-else
            percIvaDefault = modFattura.getPercIvaDefault();
            cv.setValore(percIvaDefault);

            /* regola il flag applica Rivalsa dal default */
            campo = this.getCampo(FattBase.Cam.applicaRivalsa.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                lista.add(cv);
            }// fine del blocco if-else
            cv.setValore(FattBasePref.Fatt.applicaRivalsa.is());

            /* regola il flag applica R.A. dal default */
            campo = this.getCampo(FattBase.Cam.applicaRA.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                lista.add(cv);
            }// fine del blocco if-else
            cv.setValore(FattBasePref.Fatt.applicaRa.is());

            /* regola la percentuale rivalsa dal default */
            campo = this.getCampo(FattBase.Cam.percRivalsa.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                lista.add(cv);
            }// fine del blocco if-else
            doppio = (double)FattBasePref.Fatt.percRivalsa.intero();
            doppio = Lib.Mat.arrotonda(doppio / 100, 2);
            cv.setValore(doppio);

            /* regola la percentuale r.a. dal default */
            campo = this.getCampo(FattBase.Cam.percRA.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                lista.add(cv);
            }// fine del blocco if-else
            doppio = (double)FattBasePref.Fatt.percRa.intero();
            doppio = Lib.Mat.arrotonda(doppio / 100, 2);
            cv.setValore(doppio);

            /* rimanda alla superclasse */
            riuscito = super.nuovoRecordAnte(lista, conn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Metodo invocato prima della registrazione di un record esistente.
     * <p/>
     * Puo' modificare i valori che stanno per essere registrati<br>
     * Viene sovrascritto dalle classi specifiche <br>
     * Le eventuali modifiche vanno fatte sulla lista che viene
     * passata come parametro.
     *
     * @param codice del record
     * @param lista array coppia campo-valore contenente i
     * dati che stanno per essere registrati
     * @param conn la connessione utilizzata per effettuare la query
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    @Override protected boolean registraRecordAnte(int codice,
                                                   ArrayList<CampoValore> lista,
                                                   Connessione conn) {
        boolean riuscito = false;
        boolean continua = true;
        boolean confermato;
        Campo campoCodIva;
        Campo campoPercIvaFt;
        Campo campoPercIvaTab;

        Campo campoCodPagamento;
        Campo campoUsaBanca;
        int oldCodPaga;
        int currCodPaga;
        boolean usaBanca;

        IvaModulo modIva;
        TipoPagamentoModulo modPaga;
        int codIva;
        double percIva;
        CampoValore cv;
        Modulo modLink;
        Campo campoLink;
        int link;
        String testo;
        CampoVideo campoVideo;
        CVDecoratore decoratore;
        CVDCongelato dec;
        Campo campoAssociato;
        CampoValore campoValore;

        try { // prova ad eseguire il codice

            modIva = IvaModulo.get();
            modPaga = TipoPagamentoModulo.get();

            /* se la lista contiene il codice IVA, sincronizza la
             * percentuale iva della fattura */
            campoCodIva = this.getCampo(FattBase.Cam.codIva.get());
            cv = Lib.Camp.getCampoValore(lista, campoCodIva);
            if (cv != null) {
                codIva = (Integer)cv.getValore();
                campoPercIvaTab = modIva.getCampo(Iva.Cam.valore.get());
                percIva = modIva.query().valoreDouble(campoPercIvaTab, codIva, conn);
                campoPercIvaFt = this.getCampo(FattBase.Cam.percIva.get());
                cv = Lib.Camp.getCampoValore(lista, campoPercIvaFt);
                if (cv == null) {
                    cv = new CampoValore(campoPercIvaFt, null);
                    lista.add(cv);
                }// fine del blocco if
                cv.setValore(percIva);
            }// fine del blocco if-else

            /* se la lista contiene il codice pagamento, e questo è
             * cambiato, sincronizza il flag Usa Banca della fattura */
            campoCodPagamento = this.getCampo(FattBase.Cam.pagamento.get());
            cv = Lib.Camp.getCampoValore(lista, campoCodPagamento);
            if (cv != null) {
                oldCodPaga = (Integer)this.query().valoreCampo(campoCodPagamento, codice, conn);
                currCodPaga = (Integer)cv.getValore();
                if (currCodPaga != oldCodPaga) {
                    usaBanca = modPaga.query().valoreBool(TipoPagamento.Cam.usaBanca.get(),
                            currCodPaga,
                            conn);
                    campoUsaBanca = this.getCampo(FattBase.Cam.usaBanca.get());
                    cv = Lib.Camp.getCampoValore(lista, campoUsaBanca);
                    if (cv == null) {
                        cv = new CampoValore(campoUsaBanca, null);
                        lista.add(cv);
                    }// fine del blocco if
                    cv.setValore(usaBanca);
                }// fine del blocco if
            }// fine del blocco if

            /* esegue nella superclasse */
            if (continua) {
                continua = super.registraRecordAnte(codice, lista, conn);
                riuscito = continua;
            }// fine del blocco if

            /* controlla se la fattura e' confermata */
            if (continua) {
                confermato = this.query().valoreBool(FattBase.Cam.confermato.get(), codice);
                continua = !confermato;
            }// fine del blocco if


            if (continua) {

                /* traverso tutta la collezione */
                for (Campo campo : this.getCampiModello().values()) {
                    campoVideo = campo.getCampoVideo();

                    decoratore = VideoFactory.getDecoratore(campoVideo, CVDCongelato.class);
                    if (decoratore != null) {
                        dec = (CVDCongelato)decoratore;
                        modLink = campo.getCampoDB().getModuloLinkato();
                        campoLink = campo.getCampoDB().getCampoValoriLinkato();
                        link = this.query().valoreInt(campo, codice);
                        testo = modLink.query().valoreStringa(campoLink, link);
                        campoAssociato = dec.getCampoAssociato();
                        campoValore = Lib.Camp.getCampoValore(lista, campoAssociato);
                        if (campoValore == null) {
                            campoValore = new CampoValore(campoAssociato);
                            lista.add(campoValore);
                        }// fine del blocco if
                        campoValore.setValore(testo);
                    }// fine del blocco if
                } // fine del ciclo for-each

            }// fine del blocco if

            /* rimando alla superclasse */
            if (continua) {
                continua = super.registraRecordAnte(codice, lista, conn);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    protected boolean registraRecordPost(int codice,
                                         ArrayList<CampoValore> lista,
                                         ArrayList<CampoValore> listaPre,
                                         Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        CampoValore cv;
        boolean sync = false;

        /* se cambia l'importo della rivalsa sincronizza i totali */
        if (!sync) {
            campo = this.getCampo(FattBase.Cam.importoRivalsa.get());
            sync = this.isCampoModificato(campo, lista, listaPre);
        }// fine del blocco if

        /* se cambia la percentuale di rivalsa sincronizza i totali */
        if (!sync) {
            campo = this.getCampo(FattBase.Cam.percRivalsa.get());
            sync = this.isCampoModificato(campo, lista, listaPre);
        }// fine del blocco if

        /* se cambia la percentuale iva sincronizza i totali */
        if (!sync) {
            campo = this.getCampo(FattBase.Cam.percIva.get());
            sync = this.isCampoModificato(campo, lista, listaPre);
        }// fine del blocco if

        if (sync) {
            this.syncTotali(codice, conn);
        }// fine del blocco if

        /* valore di ritorno */
        return true;
    } // fine del metodo


    /**
     * Controlla se il valore di un campo è stato
     * modificato durante la registrazione.
     * <p/>
     *
     * @param campo da controllare
     * @param listaPost lista dei campi valore dopo la registrazione
     * @param listaPre lista dei campi valore prima della registrazione
     *
     * @return true se è stato modificato
     */
    private boolean isCampoModificato(Campo campo,
                                      ArrayList<CampoValore> listaPost,
                                      ArrayList<CampoValore> listaPre) {
        /* variabili e costanti locali di lavoro */
        boolean modificato = false;
        CampoValore cv;
        double valorePre = 0;
        double valorePost = 0;

        try {    // prova ad eseguire il codice
            cv = Lib.Camp.getCampoValore(listaPost, campo);
            if (cv != null) {
                valorePost = (Double)cv.getValore();
                cv = Lib.Camp.getCampoValore(listaPre, campo);
                if (cv != null) {
                    valorePre = (Double)cv.getValore();
                }// fine del blocco if
                modificato = (valorePre != valorePost);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modificato;
    }


    /**
     * Recupera il codice della fattura proprietaria di una riga.
     * <p/>
     *
     * @param codRiga codice della riga
     * @param conn la connessione da utilizzare
     *
     * @return il codice della fattura
     */
    private int getCodFattura(int codRiga, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codFattura = 0;
        Modulo modRighe;

        try {    // prova ad eseguire il codice
            modRighe = RigaFatturaModulo.get();
            codFattura = modRighe.query().valoreInt(RigaFattura.Cam.fattura.get(), codRiga, conn);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codFattura;
    }


    /**
     * Azione eseguita quando viene creata/modificata/cancellata
     * una riga di fattura.
     * <p/>
     */
    private class AzTriggerRiga extends DbTriggerAz {

        /**
         * Metodo invocato dal trigger.
         * <p/>
         *
         * @param evento evento che causa l'azione da eseguire <br>
         */
        public void dbTriggerAz(DbTriggerEve evento) {
            /* variabili e costanti locali di lavoro */
            Db.TipoOp tipo;
            int codiceRiga;
            int codiceFattura = 0;
            Connessione conn;
            ArrayList<CampoValore> valoriNew;
            ArrayList<CampoValore> valoriOld;

            Modulo modRighe;
            Campo campoImpo;
            Campo campoIva;
            Campo campoCodIva;
            Campo campoLinkFattura;
            CampoValore cv;
            double impoOld = 0;
            double impoNew = 0;
            double ivaOld = 0;
            double ivaNew = 0;
            int codIvaOld;
            int codIvaNew;
            double deltaImpo = 0;
            double deltaIva = 0;
            boolean aggiorna = false;


            try { // prova ad eseguire il codice

                /* recupera le informazioni dall'evento */
                tipo = evento.getTipo();
                valoriNew = evento.getValoriNew();
                valoriOld = evento.getValoriOld();
                codiceRiga = evento.getCodice();
                conn = evento.getConn();

                /**
                 * Decide se deve aggiornare i totali in fattura.
                 * I totali vanno aggionati se l'operazione determina
                 * una variazione di imponibile o iva.
                 */
                modRighe = RigaFatturaModulo.get();
                campoImpo = modRighe.getCampo(RigaFattura.Cam.imponibile.get());
                campoIva = modRighe.getCampo(RigaFattura.Cam.importoIva.get());
                campoCodIva = modRighe.getCampo(RigaFattura.Cam.codIva.get());
                campoLinkFattura = modRighe.getCampo(RigaFattura.Cam.fattura.get());

                /* determina i valori di variazione di imponibile e iva */
                switch (tipo) {

                    case nuovo:

                        cv = Lib.Camp.getCampoValore(valoriNew, campoImpo);
                        if (cv != null) {
                            impoNew = (Double)cv.getValore();
                        }// fine del blocco if

                        cv = Lib.Camp.getCampoValore(valoriNew, campoIva);
                        if (cv != null) {
                            ivaNew = (Double)cv.getValore();
                        }// fine del blocco if

                        codiceFattura = getCodFattura(codiceRiga, conn);
                        break;

                    case modifica:
                        cv = Lib.Camp.getCampoValore(valoriOld, campoImpo);
                        if (cv != null) {
                            impoOld = (Double)cv.getValore();
                        }// fine del blocco if
                        cv = Lib.Camp.getCampoValore(valoriNew, campoImpo);
                        if (cv != null) {
                            impoNew = (Double)cv.getValore();
                        }// fine del blocco if

                        cv = Lib.Camp.getCampoValore(valoriOld, campoIva);
                        if (cv != null) {
                            ivaOld = (Double)cv.getValore();
                        }// fine del blocco if
                        cv = Lib.Camp.getCampoValore(valoriNew, campoIva);
                        if (cv != null) {
                            ivaNew = (Double)cv.getValore();
                        }// fine del blocco if

                        codiceFattura = getCodFattura(codiceRiga, conn);
                        break;

                    case elimina:
                        cv = Lib.Camp.getCampoValore(valoriOld, campoImpo);
                        if (cv != null) {
                            impoOld = (Double)cv.getValore();
                        }// fine del blocco if
                        cv = Lib.Camp.getCampoValore(valoriOld, campoIva);
                        if (cv != null) {
                            ivaOld = (Double)cv.getValore();
                        }// fine del blocco if

                        /* la riga non esiste più, devo recuperala dai valori old  */
                        cv = Lib.Camp.getCampoValore(valoriOld, campoLinkFattura);
                        if (cv != null) {
                            codiceFattura = (Integer)cv.getValore();
                        }// fine del blocco if

                        break;

                    default: // caso non definito
                        break;
                } // fine del blocco switch

                deltaImpo = impoNew - impoOld;
                deltaIva = ivaNew - ivaOld;
                if ((deltaImpo != 0) || (deltaIva != 0)) {
                    aggiorna = true;
                }// fine del blocco if

                /**
                 * Solo in caso di modifica:
                 * Controlla sempre la eventuale variazione del codice iva.
                 * Se si cambia codice iva, va sempre rifatto il calcolo totali
                 * perché anche se il nuovo codice ha la stessa aliquota del
                 * precedente e quindi non determina direttamente una
                 * variazione di imponibile o iva, il cambio codice può modificare
                 * la situazione di iva unica/mista della fattura, dalla quale dipende
                 * il metodo di calcolo dei totali.
                 */
                if (tipo.equals(Db.TipoOp.modifica)) {
                    if (!aggiorna) {
                        cv = Lib.Camp.getCampoValore(valoriNew, campoCodIva);
                        if (cv != null) {
                            codIvaNew = (Integer)cv.getValore();
                            cv = Lib.Camp.getCampoValore(valoriOld, campoCodIva);
                            codIvaOld = (Integer)cv.getValore();
                            if (codIvaNew != codIvaOld) {
                                aggiorna = true;
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                /* aggiorna i totali se necessario */
                if (aggiorna) {
                    syncTotali(codiceFattura, conn);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }

    }


}// fine della classe
