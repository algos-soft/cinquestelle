package it.algos.albergo;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.righecameracompo.RCC;
import it.algos.albergo.camera.righecameracompo.RCCModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergoModulo;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebito.AddebitoModulo;
import it.algos.albergo.conto.movimento.Movimento;
import it.algos.albergo.conto.movimento.MovimentoModulo;
import it.algos.albergo.conto.pagamento.Pagamento;
import it.algos.albergo.conto.pagamento.PagamentoModulo;
import it.algos.albergo.conto.sconto.ScontoModulo;
import it.algos.albergo.conto.sospeso.SospesoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.ristorante.menu.Menu;
import it.algos.albergo.ristorante.menu.MenuModulo;
import it.algos.albergo.stampeobbligatorie.ps.Ps;
import it.algos.albergo.stampeobbligatorie.ps.PsModulo;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.ConnessioneJDBC;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibResultSet;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.categoria.CatAnagrafica;
import it.algos.gestione.anagrafica.categoria.CatAnagraficaModulo;
import it.algos.gestione.indirizzo.Indirizzo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * AlbergoRevisioni - Contenitore per le revisioni di Albergo.
 * <p/>
 * Uso una classe che estende Modulo, per usare i riferimenti interni <br>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @version 1.0 / 1-apr-2009
 */
public final class AlbergoRevisioni extends AlbergoModulo {

    private ConnessioneJDBC connessione;


    public ConnessioneJDBC getConnessione() {
        return connessione;
    }


    private void setConnessione(ConnessioneJDBC connessione) {
        this.connessione = connessione;
    }


    /**
     * Costruttore completo.
     *
     * @param conn - connessione ricevuta dal Modulo Albergo
     */
    public AlbergoRevisioni(ConnessioneJDBC conn) {
        this.setConnessione(conn);
        this.esegueRevisioni();
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     */
    private void esegueRevisioni() {
//        /* revisione una tantum ott-07*/
//        this.revisioneUnaTantum1007();

//        /* revisione una tantum apr-08*/
//        this.revisioneUnaTantum0408();

//        /* revisione una tantum 29-mag-08*/
//        this.revisioneUnaTantum290508();

//        /* revisione una tantum 05-giu-08*/
//        this.revisioneUnaTantum050608();

//        /* revisione una tantum 19-giu-08*/
//        this.revisioneUnaTantum190608();

//        /* revisione una tantum 24-giu-08*/
//        this.revisioneUnaTantum240608();

//        /* revisione una tantum 03-lug-08*/
//        this.revisioneUnaTantum030708();

//        /* revisione una tantum 04-lug-08*/
//        this.revisioneUnaTantum040708();

//        /* revisione una tantum 05-lug-08*/
//        this.revisioneUnaTantum050708();

//        /* revisione una tantum 14-lug-08*/
//        this.revisioneUnaTantum140708();

//        /* revisione una tantum 21-lug-08*/
//        this.revisioneUnaTantum210708();

//        /* revisione una tantum 23-lug-08*/
//        this.revisioneUnaTantum230708();

//        /* revisione una tantum 30-lug-08*/
//        this.revisioneUnaTantum300708();

//        /* revisione una tantum 18-set-08*/
//        this.revisioneUnaTantum180908();

//        /* revisione una tantum 09-ott-08*/
//        this.revisioneUnaTantum091008();

//        /* revisione una tantum 20-ott-08*/
//        this.revisioneUnaTantum201008();

//        /* revisione una tantum 5-mar-09*/
//        this.revisioneUnaTantum050309();

//        /* revisione una tantum 20-mar-09*/
//        this.revisioneUnaTantum200309();

//        /* revisione una tantum 24-mar-09*/
//        this.revisioneUnaTantum240309();

//        /* revisione una tantum 1-apr-09*/
//        this.revisioneUnaTantum010409();

//        /* revisione una tantum 8-apr-09 - link anagrafica nullo in indirizzi interni clienti */
//        this.revisioneUnaTantum080409();

//        /* revisione una tantum 10-apr-09 - composizioni possibili camere */
//        this.revisioneUnaTantum100409();

//        /* revisione una tantum 24-apr-09 - anno di nascita nei clienti */
//        this.revisioneUnaTantum240409();

//        /* revisione una tantum 24-apr-09 - progressivo righe di PS */
//        this.revisioneUnaTantum250509();

//        /* revisione una tantum 29-mag-09 - da tipo arrivo a arrivo con */
//        this.revisioneUnaTantum290509();

//        /* revisione una tantum 03-set-09 - data ultimo soggiorno nel Cliente */
//        this.revisioneUnaTantum030909();
//

    }


    /**
     * Revisione dati una tantum in occasione di aggiunta
     * totali sincronizzati nel record di conto e gestione gruppi anagrafica.
     * <p/>
     */
    private void revisioneUnaTantum1007() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;

        try {    // prova ad eseguire il codice

            /* modifica il nome della colonna addebito.totale in addebito.importo sul database*/
            conn = (ConnessioneJDBC)this.getConnessione();

            /* rileva se va eseguito l'update */

            if (conn.isEsisteColonna("addebito", "totale")) {

                /* revisione struttura */
                this.revisioneStruttura();

                /* revisione dati */
                this.revisioneDati();

                /* terminato */
                new MessaggioAvviso("Aggiornamento dati completato.");

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione della struttura.
     * <p/>
     */
    private void revisioneStruttura() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        String stringa;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* rename addebito.totale -> addebito.importo */
            if (conn.isEsisteColonna("addebito", "totale")) {
                if (conn.isEsisteColonna("addebito", "importo")) {
                    stringa = "ALTER TABLE addebito DROP COLUMN importo";
                    conn.esegueUpdate(stringa);
                }// fine del blocco if
                stringa = "ALTER TABLE addebito CHANGE totale importo DOUBLE";
                conn.esegueUpdate(stringa);
            }// fine del blocco if

            /* rename addebitofisso.totale -> addebitofisso.importo */
            if (conn.isEsisteColonna("addebitofisso", "totale")) {
                if (conn.isEsisteColonna("addebitofisso", "importo")) {
                    stringa = "ALTER TABLE addebitofisso DROP COLUMN importo";
                    conn.esegueUpdate(stringa);
                }// fine del blocco if
                stringa = "ALTER TABLE addebitofisso CHANGE totale importo DOUBLE";
                conn.esegueUpdate(stringa);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dei dati.
     * <p/>
     */
    private void revisioneDati() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            this.revisionePaga();
            this.revisioneAddebitoNote();
            this.revisioneAziendaMenu();
//            this.revisioneCapogruppo();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * trasferisce tutti i pagamenti a titolo di Sconto o Sospeso
     * dalla tabella Pagamenti alle rispettive tabelle
     * <p/>
     */
    private void revisionePaga() {
        /* variabili e costanti locali di lavoro */
        Modulo moduloPagamenti;
        Modello modelloPagamenti;
        Dati dati;
        Filtro filtroScSosp;
        Filtro unFiltro;
        QuerySelezione qs;
        Ordine ordine;

        Campo pagCamTitolo;
        Campo pagCamData;
        Campo pagCamConto;
        Campo pagCamImporto;
        Campo pagCamNote;
        Campo pagCamDataCrea;
        Campo pagCamUteCrea;
        Campo pagCamDataMod;
        Campo pagCamUteMod;

        int titolo;
        Date data;
        int conto;
        double importo;
        String note;
        Date dataCrea;
        int uteCrea;
        Date dataMod;
        int uteMod;

        ScontoModulo modSconto;
        SospesoModulo modSospeso;
        MovimentoModulo modMovi;

        ArrayList<CampoValore> valori;
        CampoValore cv;

        int codice;

        int[] codici;

        try {    // prova ad eseguire il codice

            moduloPagamenti = PagamentoModulo.get();
            modelloPagamenti = moduloPagamenti.getModello();
            modSconto = ScontoModulo.get();
            modSospeso = SospesoModulo.get();

            /* campi di uso comune */
            pagCamTitolo = modelloPagamenti.getCampo(Pagamento.Cam.titolo.get());
            pagCamData = modelloPagamenti.getCampo(Pagamento.Cam.data.get());
            pagCamConto = modelloPagamenti.getCampo(Pagamento.Cam.conto.get());
            pagCamImporto = modelloPagamenti.getCampo(Pagamento.Cam.importo.get());
            pagCamNote = modelloPagamenti.getCampo(Pagamento.Cam.note.get());
            pagCamDataCrea = modelloPagamenti.getCampoDataCreazione();
            pagCamUteCrea = modelloPagamenti.getCampoUtenteCreazione();
            pagCamDataMod = modelloPagamenti.getCampoDataModifica();
            pagCamUteMod = modelloPagamenti.getCampoUtenteModifica();

            filtroScSosp = new Filtro();
            unFiltro = FiltroFactory.crea(Pagamento.Cam.titolo.get(), 4);
            filtroScSosp.add(unFiltro);
            unFiltro = FiltroFactory.crea(Pagamento.Cam.titolo.get(), 5);
            filtroScSosp.add(Filtro.Op.OR, unFiltro);

            ordine = new Ordine();
            ordine.add(moduloPagamenti.getCampoChiave());

            qs = new QuerySelezione(moduloPagamenti);
            qs.addCampo(pagCamTitolo);
            qs.addCampo(pagCamData);
            qs.addCampo(pagCamConto);
            qs.addCampo(pagCamImporto);
            qs.addCampo(pagCamNote);
            qs.addCampo(pagCamDataCrea);
            qs.addCampo(pagCamUteCrea);
            qs.addCampo(pagCamDataMod);
            qs.addCampo(pagCamUteMod);

            qs.setFiltro(filtroScSosp);
            qs.setOrdine(ordine);

            dati = moduloPagamenti.query().querySelezione(qs);

            /* cancella tutti gli sconti e i sospesi eventualmente esistenti */
            modSconto.query().eliminaRecords();
            modSospeso.query().eliminaRecords();

            /* spazzolamento e trasferimento dai pagamenti di sconti e sospesi */
            for (int k = 0; k < dati.getRowCount(); k++) {

                titolo = dati.getIntAt(k, pagCamTitolo);
                data = dati.getDataAt(k, pagCamData);
                conto = dati.getIntAt(k, pagCamConto);
                importo = dati.getDoubleAt(k, pagCamImporto);
                note = dati.getStringAt(k, pagCamNote);
                dataCrea = dati.getDataAt(k, pagCamDataCrea);
                uteCrea = dati.getIntAt(k, pagCamUteCrea);
                dataMod = dati.getDataAt(k, pagCamDataMod);
                uteMod = dati.getIntAt(k, pagCamUteMod);

                /* recupera il modulo sul quale creare iil record */
                switch (titolo) {
                    case 4:
                        modMovi = modSconto;
                        break;
                    case 5:
                        modMovi = modSospeso;
                        break;
                    default: // caso non definito
                        throw new Exception("Titolo " + titolo + " non riconosciuto");
                } // fine del blocco switch

                valori = new ArrayList<CampoValore>();
                cv = new CampoValore(Movimento.Cam.data.get(), data);
                valori.add(cv);
                cv = new CampoValore(Movimento.Cam.conto.get(), conto);
                valori.add(cv);
                cv = new CampoValore(Movimento.Cam.importo.get(), importo);
                valori.add(cv);
                cv = new CampoValore(Movimento.Cam.note.get(), note);
                valori.add(cv);
                cv = new CampoValore(modMovi.getModello().getCampoDataCreazione(), dataCrea);
                valori.add(cv);
                cv = new CampoValore(modMovi.getModello().getCampoUtenteCreazione(), uteCrea);
                valori.add(cv);
                cv = new CampoValore(modMovi.getModello().getCampoDataModifica(), dataMod);
                valori.add(cv);
                cv = new CampoValore(modMovi.getModello().getCampoUtenteModifica(), uteMod);
                valori.add(cv);

                codice = modMovi.query().nuovoRecord(valori);
                if (codice <= 0) {
                    throw new Exception("Impossibile creare il record di movimento");
                }// fine del blocco if


            } // fine del ciclo for

            /* cancella dai pagamenti tutti i movimenti trasferiti */
            moduloPagamenti.query().eliminaRecords(filtroScSosp);

            /* sincronizza i totali di tutti i conti esistenti */
            ContoModulo modConto = ContoModulo.get();
            codici = modConto.query().valoriChiave();
            for (int k = 0; k < codici.length; k++) {
                codice = codici[k];
                modConto.updateTotale(codice, AddebitoModulo.get(), null);
                modConto.updateTotale(codice, PagamentoModulo.get(), null);
                modConto.updateTotale(codice, ScontoModulo.get(), null);
                modConto.updateTotale(codice, SospesoModulo.get(), null);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Trasferisce i dati dal campo addebito.descrizione al campo addebito.note
     * <p/>
     */
    private void revisioneAddebitoNote() {
        /* variabili e costanti locali di lavoro */
        boolean esegui = false;
        Modulo modAddebito;
        Query query;
        Dati dati;
        Campo camAddCodice;
        Campo camAddDesc;
        Campo camAddNote;
        int codice;
        String descrizione;
        ConnessioneJDBC conn;
        String stringa;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* opera solo se esiste la colonna addebito.descrizione sul database */
            if (conn.isEsisteColonna("addebito", "descrizione")) {
                esegui = true;
            }// fine del blocco if

            if (esegui) {
                modAddebito = AddebitoModulo.get();
                camAddCodice = modAddebito.getCampoChiave();
                camAddNote = modAddebito.getCampo(Addebito.Cam.note.get());

                /* creo un campo addebito.descrizione fittizio */
                camAddDesc = CampoFactory.testo("descrizione");
                camAddDesc.setModulo(modAddebito);
                camAddDesc.inizializza();
                camAddDesc.avvia();

                query = new QuerySelezione(modAddebito);
                query.addCampo(camAddCodice);
                query.addCampo(camAddDesc);

                dati = modAddebito.query().querySelezione(query);

                for (int k = 0; k < dati.getRowCount(); k++) {
                    codice = dati.getIntAt(k, camAddCodice);
                    descrizione = dati.getStringAt(k, camAddDesc);

                    if (Lib.Testo.isValida(descrizione)) {
                        modAddebito.query().registraRecordValore(codice, camAddNote, descrizione);
                    }// fine del blocco if

                } // fine del ciclo for

                dati.close();

                /* dopo il trasferimento, elimina la colonna descrizione */
                if (conn.isEsisteColonna("addebito", "descrizione")) {
                    stringa = "ALTER TABLE addebito DROP COLUMN descrizione";
                    conn.esegueUpdate(stringa);
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Mette il campo link Azienda pari a 2 (TEST) in tutti i menu.
     * <p/>
     */
    private void revisioneAziendaMenu() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        int[] codici;
        int codice;

        try {    // prova ad eseguire il codice
            modulo = MenuModulo.get();
            codici = modulo.query().valoriChiave();
            for (int k = 0; k < codici.length; k++) {
                codice = codici[k];
                modulo.query().registraRecordValore(codice, Menu.Cam.azienda.get(), 2);
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Mette il campo Preferito (capogruppo) = true in tutte le anagrafiche.
     * Mette il campo linkCapogruppo = codice del record stesso in tutte le anagrafiche.
     * <p/>
     */
    private void revisioneCapogruppo() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        int[] codici;
        int codice;
        Campo campoCapo;
        Campo campoLinkCapo;
        ArrayList<CampoValore> listaCV;
        CampoValore cv;

        try {    // prova ad eseguire il codice
            modulo = ClienteAlbergoModulo.get();
            campoCapo = modulo.getCampoPreferito();
            campoLinkCapo = modulo.getCampo(ClienteAlbergo.Cam.linkCapo);
            codici = modulo.query().valoriChiave();
            for (int k = 0; k < codici.length; k++) {
                codice = codici[k];
                listaCV = new ArrayList<CampoValore>();
                cv = new CampoValore(campoCapo, true);
                listaCV.add(cv);
                cv = new CampoValore(campoLinkCapo, codice);
                listaCV.add(cv);
                modulo.query().registraRecordValori(codice, listaCV);
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Revisione dati una tantum in occasione di aggiunta dei campi
     * adulti/bambini/presenze adulti/presenze bambini
     * <p/>
     */
    private void revisioneUnaTantum0408() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        ResultSet rs;
        int quanti;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* controlla se va eseguita la revisione
            * la revisione va eseguita se ci sono dei nulli nella colonna adulti */
            rs = conn.esegueSelect("select codice from periodi where adulti is null");
            quanti = LibResultSet.quanteRighe(rs);

            if (quanti > 0) {
                conn.esegueUpdate("UPDATE periodi SET adulti=0 WHERE adulti is null");
                conn.esegueUpdate("UPDATE periodi SET bambini=0 WHERE bambini is null");
                conn.esegueUpdate("UPDATE periodi SET adulti=persone WHERE adulti = 0");
                conn.esegueUpdate("UPDATE periodi SET presenzeadulti=adulti * giorni");
                conn.esegueUpdate("UPDATE periodi SET presenzebambini=bambini * giorni");
                conn.esegueUpdate("UPDATE periodi SET presenze=presenzeadulti + presenzebambini");
                new MessaggioAvviso("Revisione dati 0408 eseguita.");
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum in occasione di aggiunta dei campi
     * numero giorno di nascita e numero mese di nascita
     * <p/>
     */
    private void revisioneUnaTantum290508() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        ResultSet rs;
        int quantiNullGiorno;
        int quantiNullMese;
        boolean esegui = false;
        int[] codici;
        int cod;
        Date data;
        Modulo modClienti;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* controlla se va eseguita la revisione
            * la revisione va eseguita se ci sono dei nulli nelle colonne */
            rs = conn.esegueSelect("select codice from anagrafica where giornonato is null");
            quantiNullGiorno = LibResultSet.quanteRighe(rs);

            /* controlla se va eseguita la revisione
            * la revisione va eseguita se ci sono dei nulli nelle colonne */
            rs = conn.esegueSelect("select codice from anagrafica where mesenato is null");
            quantiNullMese = LibResultSet.quanteRighe(rs);

            if ((quantiNullGiorno > 0) || (quantiNullMese > 0)) {
                esegui = true;
            }// fine del blocco if

            /* registra nuovamente la data in modo da forzare il ricalcolo del giorno e del mese */
            if (esegui) {
                modClienti = ClienteAlbergoModulo.get();

                /* disattiva l'aggiornamento di data/utente modifica */
                modClienti.getModello().setAggiornaDataUtenteModifica(false);

                codici = modClienti.query().valoriChiave();
                for (int k = 0; k < codici.length; k++) {
                    cod = codici[k];
                    data = modClienti.query().valoreData(ClienteAlbergo.Cam.dataNato.get(), cod);

                    modClienti.query().registra(cod, ClienteAlbergo.Cam.dataNato, data);

                } // fine del ciclo for

                /* riattiva l'aggiornamento di data/utente modifica */
                modClienti.getModello().setAggiornaDataUtenteModifica(true);

                new MessaggioAvviso("Revisione dati 290508 eseguita.");
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 05-06-08
     * - modifica il nome della colonna Presenze.arrivo in Presenze.entrata
     * - modifica il nome della colonna Presenze.partenza in Presenze.uscita
     * - copia tutti i valori dalla colonna presenza.entrata alla colonna presenza.primoarrivo
     * <p/>
     */
    private void revisioneUnaTantum050608() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        boolean esegui;
        String stringa;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();
            esegui = conn.isEsisteColonna("presenza", "arrivo");

            if (esegui) {
                /* rename presenza.arrivo -> presenza.entrata */
                if (conn.isEsisteColonna("presenza", "arrivo")) {
                    if (conn.isEsisteColonna("presenza", "entrata")) {
                        stringa = "ALTER TABLE presenza DROP COLUMN entrata";
                        conn.esegueUpdate(stringa);
                    }// fine del blocco if
                    stringa = "ALTER TABLE presenza CHANGE arrivo entrata DATE";
                    conn.esegueUpdate(stringa);
                }// fine del blocco if

                /* rename presenza.partenza -> presenza.uscita */
                if (conn.isEsisteColonna("presenza", "partenza")) {
                    if (conn.isEsisteColonna("presenza", "uscita")) {
                        stringa = "ALTER TABLE presenza DROP COLUMN uscita";
                        conn.esegueUpdate(stringa);
                    }// fine del blocco if
                    stringa = "ALTER TABLE presenza CHANGE partenza uscita DATE";
                    conn.esegueUpdate(stringa);
                }// fine del blocco if

                /*  copia i valori della colonna entrata nella colonna primoarrivo */
                if (conn.isEsisteColonna("presenza", "entrata")) {
                    if (conn.isEsisteColonna("presenza", "primoarrivo")) {
                        stringa = "UPDATE presenza SET primoarrivo = entrata";
                        conn.esegueUpdate(stringa);
                    }// fine del blocco if
                }// fine del blocco if

                new MessaggioAvviso("Revisione dati 050608 eseguita.");

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 19-06-08
     * - pone a false tutti i valori del nuovo campo Listino.disattivato
     * <p/>
     */
    private void revisioneUnaTantum190608() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        boolean esegui;
        String stringa;
        ResultSet rs;
        int quanti;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* controllo se esiste la colonna */
            esegui = conn.isEsisteColonna("listino", "disattivato");

            /* controllo se contiene dei nulli */
            if (esegui) {
                rs = conn.esegueSelect("select disattivato from listino where disattivato is null");
                quanti = LibResultSet.quanteRighe(rs);
                esegui = (quanti > 0);
            }// fine del blocco if

            /* esegue - pone tutti i nulli a false */
            if (esegui) {
                stringa = "UPDATE listino SET disattivato = false WHERE disattivato IS NULL";
                conn.esegueUpdate(stringa);
                new MessaggioAvviso("Revisione dati 190608 eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 24-06-08
     * - pone a false tutti i valori del nuovo campo Presenza.partenzaoggi
     * <p/>
     */
    private void revisioneUnaTantum240608() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        boolean esegui;
        String stringa;
        ResultSet rs;
        int quanti;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* controllo se esiste la colonna */
            esegui = conn.isEsisteColonna("presenza", "partenzaoggi");

            /* controllo se contiene dei nulli */
            if (esegui) {
                rs = conn.esegueSelect(
                        "select partenzaoggi from presenza where partenzaoggi is null");
                quanti = LibResultSet.quanteRighe(rs);
                esegui = (quanti > 0);
            }// fine del blocco if

            /* esegue - pone tutti i nulli a false */
            if (esegui) {
                stringa = "UPDATE presenza SET partenzaoggi = false WHERE partenzaoggi IS NULL";
                conn.esegueUpdate(stringa);
                new MessaggioAvviso("Revisione dati 240608 eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 03-07-08
     * In occasione dell'aggiunta del campo Pensione alla tavola Presenza
     * - legge il tipo di pensione dal relativo periodo
     * - copia il dato nella presenza
     * <p/>
     */
    private void revisioneUnaTantum030708() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        boolean esegui;
        ResultSet rs;
        int quanti;
        Modulo modPresenza;
        Modulo modPeriodo;
        int[] presenze;
        int codPeriodo;
        int codPensione;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* controllo se esiste la colonna */
            esegui = conn.isEsisteColonna("presenza", "pensione");

            /* controllo se contiene dei nulli */
            if (esegui) {
                rs = conn.esegueSelect("select pensione from presenza where pensione is null");
                quanti = LibResultSet.quanteRighe(rs);
                esegui = (quanti > 0);
            }// fine del blocco if

            /* esegue - spazzola tutte le presenze e copia il dato dal periodo */
            if (esegui) {
                modPresenza = PresenzaModulo.get();
                modPeriodo = PeriodoModulo.get();
                presenze = modPresenza.query().valoriChiave();
                for (int codPres : presenze) {
                    codPensione = 0;
                    codPeriodo = modPresenza.query().valoreInt(Presenza.Cam.periodo.get(), codPres);
                    if (codPeriodo > 0) {
                        codPensione = modPeriodo.query().valoreInt(
                                Periodo.Cam.trattamento.get(),
                                codPeriodo);
                    }// fine del blocco if
                    modPresenza.query().registra(codPres, Presenza.Cam.pensione.get(), codPensione);
                }
                new MessaggioAvviso("Revisione dati 030708 eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 04-07-08
     * - pone a false tutti i valori del nuovo campo Presenza.bambino
     * - pone a 0 tutti i valori del nuovo campo Presenza.pasto
     * <p/>
     */
    private void revisioneUnaTantum040708() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        boolean esegui;
        String stringa;
        ResultSet rs;
        int quanti;
        String stringaWhere;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* REVISIONE "A" - BAMBINO */

            /* controllo se esiste la colonna */
            esegui = conn.isEsisteColonna("presenza", "bambino");

            /* controllo se contiene dei nulli */
            if (esegui) {
                rs = conn.esegueSelect("select bambino from presenza where bambino is null");
                quanti = LibResultSet.quanteRighe(rs);
                esegui = (quanti > 0);
            }// fine del blocco if

            /* esegue - pone tutti i nulli a false */
            if (esegui) {
                stringa = "UPDATE presenza SET bambino = false WHERE bambino IS NULL";
                conn.esegueUpdate(stringa);
                new MessaggioAvviso("Revisione dati 040708/A eseguita.");
            }// fine del blocco if

            /* REVISIONE "B" - PASTO */

            /* controllo se esiste la colonna */
            esegui = conn.isEsisteColonna("presenza", "pasto");

            /* controllo se contiene dei nulli */
            if (esegui) {
                rs = conn.esegueSelect("select pasto from presenza where pasto is null");
                quanti = LibResultSet.quanteRighe(rs);
                esegui = (quanti > 0);
            }// fine del blocco if

            /* esegue - pone tutti i nulli a zero */
            if (esegui) {
                stringa = "UPDATE presenza SET pasto = 0 WHERE pasto IS NULL";
                conn.esegueUpdate(stringa);
                new MessaggioAvviso("Revisione dati 040708/B eseguita.");
            }// fine del blocco if

            /* REVISIONE "C" - DATA PARTENZA EFFETTIVA NEI PERIODI CHIUSI CON CAMBIO USCITA */

            esegui = true;
            stringaWhere =
                    "where chiuso = true and causalepartenza = 2 and partenzaeffettiva is null";

            /* controllo se contiene dei nulli */
            if (esegui) {
                rs = conn.esegueSelect("select partenzaeffettiva from periodi " + stringaWhere);
                quanti = LibResultSet.quanteRighe(rs);
                esegui = (quanti > 0);
            }// fine del blocco if

            /* esegue - pone le partenze effettive pari alle previste */
            if (esegui) {
                stringa = "UPDATE periodi SET partenzaeffettiva = partenzaprevista " + stringaWhere;
                conn.esegueUpdate(stringa);
                new MessaggioAvviso("Revisione dati 040708/C eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 05-07-08
     * associa tutte le presenze senza link al periodo
     * (derivanti da vecchio sistema di arrivo manuale)
     * al periodo di competenza cercandolo per data di arrivo e camera
     * <p/>
     */
    private void revisioneUnaTantum050708() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito;
        Modulo modPresenza;
        Modulo modPeriodo;
        Filtro filtro;
        Filtro unFiltro;
        int[] presenze;
        Date dataArrivo;
        int codCamera;
        Campo campo;
        int[] periodi;
        int codPeriodo;
        int falliti = 0;

        try {    // prova ad eseguire il codice

            modPresenza = PresenzaModulo.get();
            modPeriodo = PeriodoModulo.get();

            filtro = FiltroFactory.crea(Presenza.Cam.periodo.get(), 0);
            presenze = modPresenza.query().valoriChiave(filtro);
            if (presenze.length > 0) {

                eseguito = true;

                for (int codPresenza : presenze) {
                    dataArrivo = modPresenza.query().valoreData(
                            Presenza.Cam.arrivo.get(),
                            codPresenza);
                    codCamera = modPresenza.query().valoreInt(
                            Presenza.Cam.camera.get(),
                            codPresenza);

                    filtro = new Filtro();
                    unFiltro = FiltroFactory.crea(
                            Periodo.Cam.arrivoEffettivo.get(),
                            Filtro.Op.MINORE_UGUALE,
                            dataArrivo);
                    filtro.add(unFiltro);
                    unFiltro = FiltroFactory.crea(
                            Periodo.Cam.partenzaPrevista.get(),
                            Filtro.Op.MAGGIORE_UGUALE,
                            dataArrivo);
                    filtro.add(unFiltro);
                    unFiltro = FiltroFactory.crea(Periodo.Cam.camera.get(), codCamera);
                    filtro.add(unFiltro);

                    campo = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.disdetta);
                    unFiltro = FiltroFactory.crea(campo, false);
                    filtro.add(unFiltro);

                    periodi = modPeriodo.query().valoriChiave(filtro);
                    if (periodi.length == 1) {
                        codPeriodo = periodi[0];
                        modPresenza.query().registra(codPresenza, Presenza.Cam.periodo, codPeriodo);
                    } else {
                        falliti++;
                        eseguito = false;
                    }// fine del blocco if-else

                }

                /* esegue - pone le partenze effettive pari alle previste */
                if (eseguito) {
                    new MessaggioAvviso("Revisione dati 050708 eseguita.");
                } else {
                    new MessaggioAvviso(
                            "Revisione dati 050708 eseguita - identificazioni periodi fallite: " +
                                    falliti);
                }// fine del blocco if-else

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 14-07-08
     * riscrive il campo Partenza Prevista in tutti i periodi che
     * non hanno il campo dataFineAddebiti valorizzato, in modo
     * da forzare il ricalcolo del campo calcolato dataFineAddebiti
     * <p/>
     */
    private void revisioneUnaTantum140708() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        Modulo modPeriodo;
        Modello modello;
        Filtro filtro;
        int[] periodi;
        Date dataPartenzaPrevista;
        boolean triggerMod;
        boolean usaPrec;

        try {    // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();
            modello = modPeriodo.getModello();

            filtro = FiltroFactory.crea(Periodo.Cam.dataFineAddebiti.get(), Lib.Data.getVuota());
            periodi = modPeriodo.query().valoriChiave(filtro);
            if (periodi.length > 0) {

                /* salva lo stato del trigger modifica */
                triggerMod = modello.isTriggerModificaAttivo();
                usaPrec = modello.isTriggerModificaUsaPrecedenti();

                /* disattiva il trigger modifica*/
                modello.setTriggerModificaAttivo(false, false);

                for (int codPeriodo : periodi) {
                    dataPartenzaPrevista =
                            modPeriodo.query().valoreData(
                                    Periodo.Cam.partenzaPrevista.get(),
                                    codPeriodo);
                    eseguito = modPeriodo.query().registra(
                            codPeriodo,
                            Periodo.Cam.partenzaPrevista.get(),
                            dataPartenzaPrevista);
                    if (!eseguito) {
                        break;
                    }// fine del blocco if
                }

                /* messaggio finale */
                if (eseguito) {
                    new MessaggioAvviso("Revisione dati 140708 eseguita.");
                } else {
                    new MessaggioAvviso("Revisione dati 140708 fallita.");
                }// fine del blocco if-else

                /* ripristina lo stato del trigger modifica */
                modello.setTriggerModificaAttivo(triggerMod, usaPrec);

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 21-07-08
     * riscrive il campo Uscita in tutte le Presenze che
     * non hanno il campo presenze valorizzato, in modo
     * da forzare il ricalcolo del campo calcolato presenze
     * <p/>
     */
    private void revisioneUnaTantum210708() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        boolean continua;
        boolean eseguiFase0 = false;
        Modulo modPresenza;
        Filtro filtro;
        Filtro unFiltro;
        int[] presenze = null;
        Date dataUscita;
        String stringa;
        ConnessioneJDBC conn;
        ResultSet rs;
        int quanti;

        try {    // prova ad eseguire il codice

            /* recupera la connessione */
            conn = (ConnessioneJDBC)this.getConnessione();

            /* controllo se esiste la colonna */
            continua = conn.isEsisteColonna("presenza", "presenze");

            /* controllo se contiene dei nulli */
            if (continua) {
                rs = conn.esegueSelect("select presenze from presenza where presenze is null");
                quanti = LibResultSet.quanteRighe(rs);
                eseguiFase0 = (quanti > 0);
            }// fine del blocco if

            /* esegue fase 0 - pone tutti i nulli a zero */
            if (continua) {
                if (eseguiFase0) {
                    stringa = "UPDATE presenza SET presenze = 0 WHERE presenze IS NULL";
                    quanti = conn.esegueUpdate(stringa);
                    if (quanti <= 0) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* recupera l'elenco dei record con data di uscita valorizzata e presenze = 0 */
            if (continua) {
                modPresenza = PresenzaModulo.get();
                filtro = new Filtro();
                unFiltro = FiltroFactory.crea(Presenza.Cam.presenze.get(), 0);
                filtro.add(unFiltro);
                unFiltro = FiltroFactory.crea(
                        Presenza.Cam.uscita.get(),
                        Filtro.Op.DIVERSO,
                        Lib.Data.getVuota());
                filtro.add(unFiltro);
                presenze = modPresenza.query().valoriChiave(filtro);
                continua = (presenze.length > 0);
            }// fine del blocco if

            /* riscrive il campo data di uscita per forzare il ricalcolo delle presenze */
            if (continua) {
                modPresenza = PresenzaModulo.get();
                for (int codPresenza : presenze) {
                    dataUscita = modPresenza.query().valoreData(
                            Presenza.Cam.uscita.get(),
                            codPresenza);
                    eseguito = modPresenza.query().registra(
                            codPresenza,
                            Presenza.Cam.uscita.get(),
                            dataUscita);
                    if (!eseguito) {
                        break;
                    }// fine del blocco if
                }

                /* messaggio finale */
                if (eseguito) {
                    new MessaggioAvviso("Revisione dati 210708 eseguita.");
                } else {
                    new MessaggioAvviso("Revisione dati 210708 fallita.");
                }// fine del blocco if-else

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


//    /**
//     * Revisione dati una tantum del 23-07-08
//     * - seleziona tutti i record di presenza con tipo arrivo nullo
//     * - pone tutti i nulli pari al tipoarrivo del conto di competenza
//     * <p/>
//     */
//    private void revisioneUnaTantum230708() {
//        /* variabili e costanti locali di lavoro */
//        boolean continua;
//        Modulo modPresenza;
//        Modulo modConto;
//        Filtro filtro;
//        int[] presenze = null;
//        ConnessioneJDBC conn;
//        int codConto;
//        int tipoArrivo;
//        boolean riuscito = true;
//
//        try {    // prova ad eseguire il codice
//
//            /* recupera la connessione */
//            conn = (ConnessioneJDBC)this.getConnessione();
//
//            /* controllo se esiste la colonna */
//            continua = conn.isEsisteColonna("presenza", "tipoarrivo");
//
//            /* recupera l'elenco dei record con tipoarrivo = 0 */
//            if (continua) {
//                modPresenza = PresenzaModulo.get();
//                filtro = FiltroFactory.crea(Presenza.Cam.tipoArrivo.get(), 0);
//                presenze = modPresenza.query().valoriChiave(filtro);
//                continua = (presenze.length > 0);
//            }// fine del blocco if
//
//            /* copia il valore del campo presenza.tipoarrivo
//             * dal conto di competenza */
//            if (continua) {
//                modConto = ContoModulo.get();
//                modPresenza = PresenzaModulo.get();
//                for (int codPresenza : presenze) {
//                    codConto = modPresenza.query().valoreInt(Presenza.Cam.conto.get(), codPresenza);
//                    if (codConto != 0) {
//                        tipoArrivo = modConto.query().valoreInt(
//                                Conto.Cam.tipoArrivo.get(),
//                                codConto);
//                        riuscito = modPresenza.query().registra(
//                                codPresenza,
//                                Presenza.Cam.tipoArrivo.get(),
//                                tipoArrivo);
//                    }// fine del blocco if
//                    if (!riuscito) {
//                        break;
//                    }// fine del blocco if
//                }
//
//                /* messaggio finale */
//                if (riuscito) {
//                    new MessaggioAvviso("Revisione dati 230708 eseguita.");
//                } else {
//                    new MessaggioAvviso("Revisione dati 230708 fallita.");
//                }// fine del blocco if-else
//
//            }// fine del blocco if
//
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//    }


    /**
     * Revisione dati una tantum del 30-07-08
     * - seleziona tutti i record di periodo con flag chiuso = true e partenzaeffettiva = null
     * - pone partenzaeffettiva pari a partenzaprevista
     * <p/>
     */
    private void revisioneUnaTantum300708() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modPeriodo;
        Filtro filtro;
        int[] periodi = null;
        ConnessioneJDBC conn;
        Date partPrevista;

        try {    // prova ad eseguire il codice

            /* recupera la connessione */
            conn = (ConnessioneJDBC)this.getConnessione();
            modPeriodo = PeriodoModulo.get();

            /* controllo se esistono le colonne */
            continua = conn.isEsisteColonna("periodi", "chiuso");

            if (continua) {
                continua = conn.isEsisteColonna("periodi", "partenzaprevista");
            }// fine del blocco if
            if (continua) {
                continua = conn.isEsisteColonna("periodi", "partenzaeffettiva");
            }// fine del blocco if

            /* recupera l'elenco dei periodi partiti ma senza partenza effettiva */
            if (continua) {
                filtro = new Filtro();
                filtro.add(FiltroFactory.creaVero(Periodo.Cam.partito.get()));
                filtro.add(
                        FiltroFactory.crea(
                                Periodo.Cam.partenzaEffettiva.get(), Lib.Data.getVuota()));
                periodi = modPeriodo.query().valoriChiave(filtro);
                continua = (periodi.length > 0);
            }// fine del blocco if

            /* copia il valore del campo periodo.partenzaprevista
             * nel campo periodo.partenzaeffettiva */
            if (continua) {
                for (int cod : periodi) {
                    partPrevista = modPeriodo.query()
                            .valoreData(Periodo.Cam.partenzaPrevista.get(), cod);
                    if (Lib.Data.isValida(partPrevista)) {
                        modPeriodo.query()
                                .registra(cod, Periodo.Cam.partenzaEffettiva.get(), partPrevista);
                    }// fine del blocco if
                }

                /* messaggio finale */
                new MessaggioAvviso("Revisione dati 300708 eseguita.");

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 18-09-08
     * Se il campo indiceGiornoNato contiene dei nulli,
     * spazzola tutti i clienti e riscrive il valore del campo data di nascita
     * per forzare l'aggiornamento del campo indiceGiornoNato (calcolato)
     * <p/>
     */
    private void revisioneUnaTantum180908() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modCliente;
        ConnessioneJDBC conn;
        ResultSet rs;
        int quanti;
        String nomeCampo;
        String stringa;

        try {    // prova ad eseguire il codice

            /* recupera la connessione */
            conn = (ConnessioneJDBC)this.getConnessione();

            /* cancella le vecchie colonne giornonato e mesenato */
            if (conn.isEsisteColonna("anagrafica", "giornonato")) {
                stringa = "ALTER TABLE anagrafica DROP COLUMN giornonato";
                conn.esegueUpdate(stringa);
            }// fine del blocco if
            if (conn.isEsisteColonna("anagrafica", "mesenato")) {
                stringa = "ALTER TABLE anagrafica DROP COLUMN mesenato";
                conn.esegueUpdate(stringa);
            }// fine del blocco if

            /* controllo se esistono le colonne */
            continua = conn.isEsisteColonna("anagrafica", "indiceGiornoNato");
            if (continua) {
                continua = conn.isEsisteColonna("anagrafica", "datanato");
            }// fine del blocco if

            /* controllo se il campo indiceGiornoNato contiene dei nulli */
            if (continua) {
                rs = conn.esegueSelect(
                        "select indiceGiornoNato from anagrafica where indiceGiornoNato is null");
                quanti = LibResultSet.quanteRighe(rs);
                continua = (quanti > 0);
            }// fine del blocco if

            if (continua) {
                modCliente = ClienteAlbergoModulo.get();
                modCliente.getModello().setAggiornaDataUtenteModifica(false);
                nomeCampo = ClienteAlbergo.Cam.dataNato.get();

                int[] codici = modCliente.query().valoriChiave();

                for (int cod : codici) {
                    Date data = modCliente.query().valoreData(nomeCampo, cod);
                    modCliente.query().registra(cod, nomeCampo, data);
                }

                modCliente.getModello().setAggiornaDataUtenteModifica(true);

                new MessaggioAvviso("Revisione dati 120908 eseguita.");

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 09-10-08
     * 1 - crea un record di testata stampa ISTAT in data 01-01-2008 (se non esiste già)
     * per evitare che a causa della importazione dello storico
     * il sistema delle stampe obbligatorie tenti di creare l'ISTAT
     * per date anteriori al 01-01-2008.
     * 2 - Se esistono dei record di testata stampe ISTAT anteriori al 31-12-2008
     * che non risultano stampati, li pone tutti come già stampati.
     * <p/>
     */
    private void revisioneUnaTantum091008() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        int quanti;
        Modulo modTesta;
        int codTipoIstat;
        Filtro filtro;
        Filtro filtroTot;
        Date primoGen2008;
        Date trentunDic2008;
        SetValori sv;
        int[] chiavi;

        try {    // prova ad eseguire il codice

            /* variabili di uso comune */
            primoGen2008 = Lib.Data.getPrimoGennaio(2008);
            trentunDic2008 = Lib.Data.getTrentunoDicembre(2008);
            codTipoIstat = TestaStampe.TipoRegistro.istat.getCodice();
            modTesta = TestaStampeModulo.get();

            /* controlla se esiste un record ISTAT datato 01-01-2008, se manca lo crea */
            filtroTot = new Filtro();
            filtro = FiltroFactory.crea(TestaStampe.Cam.tipo.get(), codTipoIstat);
            filtroTot.add(filtro);
            filtro = FiltroFactory.crea(TestaStampe.Cam.data.get(), primoGen2008);
            filtroTot.add(filtro);
            quanti = modTesta.query().contaRecords(filtroTot);
            if (quanti == 0) {
                sv = new SetValori(modTesta);
                sv.add(TestaStampe.Cam.azienda, 1);
                sv.add(TestaStampe.Cam.data, primoGen2008);
                sv.add(TestaStampe.Cam.stampato, true);
                sv.add(TestaStampe.Cam.tipo, codTipoIstat);
                modTesta.query().nuovoRecord(sv.getListaValori());
                eseguito = true;
            }// fine del blocco if

            /**
             * Se esistono dei record di testata stampe ISTAT anteriori o uguali al 31-12-2008
             * che non risultano stampati, li pone tutti come già stampati.
             */
            filtroTot = new Filtro();
            filtro = FiltroFactory.crea(TestaStampe.Cam.tipo.get(), codTipoIstat);
            filtroTot.add(filtro);
            filtro = FiltroFactory.crea(TestaStampe.Cam.stampato.get(), false);
            filtroTot.add(filtro);
            filtro = FiltroFactory.crea(
                    TestaStampe.Cam.data.get(), Filtro.Op.MINORE_UGUALE, trentunDic2008);
            filtroTot.add(filtro);
            quanti = modTesta.query().contaRecords(filtroTot);
            if (quanti > 0) {
                chiavi = modTesta.query().valoriChiave(filtroTot);
                for (int chiave : chiavi) {
                    modTesta.query().registra(chiave, TestaStampe.Cam.stampato.get(), true);
                }
                eseguito = true;
            }// fine del blocco if

            if (eseguito) {
                new MessaggioAvviso("Revisione dati 091008 eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 20-10-08
     * <p/>
     * - Tavola revisionata: pubblicasicurezza
     * - Scopo della revisione: Eliminazione del link alla Presenza e sostituzione con link al Cliente
     * - Condizioni di attivazione: la revisione viene attivata se ci sono dei record nella tavola
     * pubblicasicurezza e se tutti i record hanno il campo linkcliente = null.
     */
    private void revisioneUnaTantum201008() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        ConnessioneJDBC conn;
        int[] chiavi = new int[0];
        int cod;
        ResultSet rs;
        Object valore;
        int linkPresenza;
        Modulo modPS;
        Modulo modPresenza;
        int codCliente;
        boolean eseguito = false;
        int quanti;

        try {    // prova ad eseguire il codice

            /* variabili di uso comune */
            modPS = PsModulo.get();
            modPresenza = PresenzaModulo.get();

            /* recupera la connessione */
            conn = (ConnessioneJDBC)this.getConnessione();

            /* controllo se esiste la colonna */
            if (continua) {
                continua = conn.isEsisteColonna("pubblicasicurezza", "linkpresenza");
            }// fine del blocco if

            /* controllo se esiste la colonna */
            if (continua) {
                continua = conn.isEsisteColonna("pubblicasicurezza", "linkcliente");
            }// fine del blocco if

            /* recupero la lista dei codici record */
            if (continua) {
                chiavi = modPS.query().valoriChiave();
                continua = chiavi.length > 0;
            }// fine del blocco if

            /* controllo che tutti i valori di linkcliente siano nulli */
            if (continua) {
                rs = conn.esegueSelect(
                        "select linkcliente from pubblicasicurezza where linkcliente is not null");
                quanti = LibResultSet.quanteRighe(rs);
                continua = (quanti == 0);
            }// fine del blocco if

            /* spazzolo i record */
            if (continua) {
                for (int k = 0; k < chiavi.length; k++) {
                    cod = chiavi[k];

                    /* recupero il valore del campo linkpresenza */
                    rs = conn.esegueSelect(
                            "select linkpresenza from pubblicasicurezza where codice = " + cod);
                    valore = LibResultSet.getValoreCella(rs, 1, 1);
                    linkPresenza = Libreria.getInt(valore);

                    if (linkPresenza > 0) {

                        /* recupero il codice cliente */
                        codCliente = modPresenza.query()
                                .valoreInt(Presenza.Cam.cliente.get(), linkPresenza);

                        if (codCliente > 0) {
                            /* scrivo il codice cliente nel record*/
                            modPS.query().registra(cod, Ps.Cam.linkCliente.get(), codCliente);
                        }// fine del blocco if

                    }// fine del blocco if

                } // fine del ciclo for

                eseguito = true;

            }// fine del blocco if

            if (eseguito) {
                new MessaggioAvviso("Revisione dati 201008 eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 05-03-09
     * - pone a false tutti i valori del nuovo campo Camera.escludiplanning
     * <p/>
     */
    private void revisioneUnaTantum050309() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        boolean esegui;
        String stringa;
        ResultSet rs;
        int quanti;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* controllo se esiste la colonna */
            esegui = conn.isEsisteColonna("camera", "escludiplanning");

            /* controllo se contiene dei nulli */
            if (esegui) {
                rs = conn.esegueSelect(
                        "select escludiplanning from camera where escludiplanning is null");
                quanti = LibResultSet.quanteRighe(rs);
                esegui = (quanti > 0);
            }// fine del blocco if

            /* esegue - pone tutti i nulli a false */
            if (esegui) {
                stringa = "UPDATE camera SET escludiplanning = false WHERE escludiplanning IS NULL";
                conn.esegueUpdate(stringa);
                new MessaggioAvviso("Revisione dati 050309 eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Revisione dati una tantum del 20-03-09
     * - pone a false tutti i valori del nuovo campo Prenotazione.opzione
     * <p/>
     */
    private void revisioneUnaTantum200309() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        boolean esegui;
        String stringa;
        ResultSet rs;
        int quanti;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* controllo se esiste la colonna */
            esegui = conn.isEsisteColonna("prenotazioni", "opzione");

            /* controllo se contiene dei nulli */
            if (esegui) {
                rs = conn.esegueSelect(
                        "select opzione from prenotazioni where opzione is null");
                quanti = LibResultSet.quanteRighe(rs);
                esegui = (quanti > 0);
            }// fine del blocco if

            /* esegue - pone tutti i nulli a false */
            if (esegui) {
                stringa = "UPDATE prenotazioni SET opzione = false WHERE opzione IS NULL";
                conn.esegueUpdate(stringa);
                new MessaggioAvviso("Revisione dati 200309 eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Revisione dati una tantum del 24-03-09
     * - regola il valore di link per il campo linkCategoria di Anagrafica
     * - 1 è il valore del record di Clienti
     * <p/>
     */
    private void revisioneUnaTantum240309() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        boolean esegui;
        String stringa;
        ResultSet rs;
        int quanti;

        try {    // prova ad eseguire il codice

            conn = (ConnessioneJDBC)this.getConnessione();

            /* controllo se esiste la colonna */
            esegui = conn.isEsisteColonna("anagrafica", "linkcategoria");

            /* controllo se contiene dei nulli */
            if (esegui) {
                rs = conn.esegueSelect(
                        "select linkcategoria from anagrafica where linkcategoria is null");
                quanti = LibResultSet.quanteRighe(rs);
                esegui = (quanti > 0);
            }// fine del blocco if

            /* esegue - pone tutti i nulli a false */
            if (esegui) {
                stringa = "UPDATE anagrafica SET linkcategoria = 1 WHERE linkcategoria IS NULL";
                conn.esegueUpdate(stringa);
                new MessaggioAvviso("Revisione dati 240309 eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Revisione dati una tantum del 1-04-09
     * - modifica il valore dei primi 3 records di Categoria di Anagrafica
     * <p/>
     */
    private void revisioneUnaTantum010409() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        boolean eseguita = false;
        Modulo mod;
        String oldVal;
        String newVal;
        String campo = CatAnagrafica.Cam.sigla.get();
        ResultSet rs;
        int quanti;
        int codice;

        try {    // prova ad eseguire il codice

            /* controllo se esiste il modulo */
            mod = CatAnagraficaModulo.get();
            continua = (mod != null);

            if (continua) {
                quanti = mod.query().contaRecords();
                continua = (quanti > 2);
            }// fine del blocco if

            if (continua) {

                codice = CatAnagrafica.Tipo.cliente.getCodice();
                newVal = CatAnagrafica.Tipo.cliente.get();
                oldVal = mod.query().valoreStringa(campo, codice);
                if (!oldVal.equals(newVal)) {
                    if (mod.query().isEsisteRecord(codice)) {
                        mod.query().registra(codice, campo, newVal);
                        eseguita = true;
                    }// fine del blocco if
                }// fine del blocco if

                codice = CatAnagrafica.Tipo.fornitore.getCodice();
                newVal = CatAnagrafica.Tipo.fornitore.get();
                oldVal = mod.query().valoreStringa(campo, codice);
                if (!oldVal.equals(newVal)) {
                    if (mod.query().isEsisteRecord(codice)) {
                        mod.query().registra(codice, campo, newVal);
                        eseguita = true;
                    }// fine del blocco if
                }// fine del blocco if

                codice = CatAnagrafica.Tipo.agenzia.getCodice();
                newVal = CatAnagrafica.Tipo.agenzia.get();
                oldVal = mod.query().valoreStringa(campo, codice);
                if (!oldVal.equals(newVal)) {
                    if (mod.query().isEsisteRecord(codice)) {
                        mod.query().registra(codice, campo, newVal);
                        eseguita = true;
                    }// fine del blocco if
                }// fine del blocco if

                if (eseguita) {
                    new MessaggioAvviso("Revisione dati 1-04-09 eseguita.");
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Revisione dati una tantum del 08-04-09
     * - seleziona tutti gli indirizzi che hanno link anagrafica = null
     * - per ognuno, cerca nei clienti il cliente proprietario
     * - se lo trova, assegna il valore al campo linkanagrafica
     * - se non lo trova, elimina l'indirizzo perché è orfano e inutilizzato
     * <p/>
     */
    private void revisioneUnaTantum080409() {
        /* variabili e costanti locali di lavoro */
        boolean eseguita = false;

        try {    // prova ad eseguire il codice

            Modulo modCliente = ClienteAlbergoModulo.get();
            Modulo modIndirizzo = IndirizzoAlbergoModulo.get();
            Filtro filtro = FiltroFactory.crea(Indirizzo.Cam.anagrafica.get(), 0);
            int[] chiavi = modIndirizzo.query().valoriChiave(filtro);

            for (int k = 0; k < chiavi.length; k++) {
                int codIndirizzo = chiavi[k];
                filtro = FiltroFactory.crea(
                        ClienteAlbergo.Cam.indirizzoInterno.get(), codIndirizzo);
                int codCliente = modCliente.query().valoreChiave(filtro);

                /**
                 * se ha trovato il cliente che lo usa, lo assegna
                 * altrimenti è un indirizzo inutilizzato e lo cancella
                 */
                if (codCliente > 0) {
                    modIndirizzo.query()
                            .registra(codIndirizzo, Indirizzo.Cam.anagrafica.get(), codCliente);
                } else {
                    modIndirizzo.query().eliminaRecord(codIndirizzo);
                }// fine del blocco if-else
                eseguita = true;  // accende la prima volta
            } // fine del ciclo for

            if (eseguita) {
                new MessaggioAvviso("Revisione dati 080409 eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Revisione dati una tantum del 10-04-09.
     * <p/>
     * - esegue solo se la tavola sub-righe RCC (righecameracompo) è vuota <br>
     * - spazzola tutti i records di 'camera' e legge il valore del campo linkcomposizione <br>
     * - se non è nullo, costruisce un record della tavola 'righecameracompo' <br>
     */
    private void revisioneUnaTantum100409() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modCam;
        Modulo modRCC;
        int numRec;
        int[] codici = null;
        int codCam;
        int codCompo;
        int nuovoRec;

        try {    // prova ad eseguire il codice

            modCam = CameraModulo.get();
            modRCC = RCCModulo.get();
            continua = (modCam != null && modRCC != null);

            if (continua) {
                numRec = modRCC.query().contaRecords();
                continua = (numRec == 0);
            }// fine del blocco if

            if (continua) {
                codici = modCam.query().valoriChiave();
                continua = (codici.length > 0);
            }// fine del blocco if

            if (continua) {
                for (int k = 0; k < codici.length; k++) {
                    codCam = codici[k];
                    codCompo = modCam.query().valoreInt(Camera.Cam.composizione.get(), codCam);
                    if (codCompo > 0) {
                        nuovoRec = modRCC.query().nuovoRecord();
                        modRCC.query()
                                .registraRecordValore(nuovoRec, RCC.Cam.linkcamera.get(), codCam);
                        modRCC.query()
                                .registraRecordValore(nuovoRec, RCC.Cam.linkcompo.get(), codCompo);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            if (continua) {
                new MessaggioAvviso("Revisione dati 100409 eseguita.");
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Revisione dati una tantum del 24-04-09.
     * <p/>
     * spazzola tutti i clienti e assegna l'anno di nascita in base alla data di nascita<br>
     */
    private void revisioneUnaTantum240409() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;

        try {    // prova ad eseguire il codice

            ConnessioneJDBC conn = (ConnessioneJDBC)this.getConnessione();

            /* controllo se esiste la colonna */
            boolean esegui = conn.isEsisteColonna("anagrafica", "annoNato");

            /* pone eventuali nulli a zero */
            if (esegui) {
                ResultSet rs = conn.esegueSelect(
                        "select annoNato from anagrafica where annoNato is null");
                if (LibResultSet.quanteRighe(rs) > 0) {
                    String statement = "UPDATE anagrafica SET annoNato = 0 WHERE annoNato IS NULL";
                    conn.esegueUpdate(statement);
                }// fine del blocco if
            }// fine del blocco if

            /* esegue - scrive l'anno di nascita per tutti quelli dove è zero */
            if (esegui) {
                Modulo modCliente = ClienteAlbergoModulo.get();
                Filtro filtro = FiltroFactory.crea(ClienteAlbergo.Cam.annoNato.getNome(), 0);
                int[] codici = modCliente.query().valoriChiave(filtro);
                for (int k = 0; k < codici.length; k++) {
                    int codice = codici[k];
                    Date data = modCliente.query()
                            .valoreData(ClienteAlbergo.Cam.dataNato.get(), codice);

                    /* registra nuovamente la data di nascita per forzare il ricalcolo dell'anno */
                    if (Lib.Data.isValida(data)) {
                        modCliente.query()
                                .registra(codice, ClienteAlbergo.Cam.dataNato.get(), data);
                        eseguito = true;
                    }// fine del blocco if

                } // fine del ciclo for
            }// fine del blocco if

            if (eseguito) {
                new MessaggioAvviso("Revisione 240409 (sync anno nascita) eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Revisione dati una tantum del 25-05-09.
     * progressivo righe di PS
     * <p/>
     * A causa di errore trascinatosi per un po' nella gestione del registro di PS
     * Spazzola tutti i record di pubblicasicurezza che hanno progressivo = 0
     * Recupera il numero di PS dalla presenza linkata e lo copia nel progressivo
     */
    private void revisioneUnaTantum250509() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;

        try {    // prova ad eseguire il codice

            Modulo modRighePS = PsModulo.get();
            Modulo modPresenze = PresenzaModulo.get();
            Filtro filtro = FiltroFactory.crea(Ps.Cam.progressivo.get(), 0);
            int[] codici = modRighePS.query().valoriChiave(filtro);
            for (int k = 0; k < codici.length; k++) {
                int codRiga = codici[k];
                int codPres = modRighePS.query().valoreInt(Ps.Cam.linkPresenza.get(), codRiga);
                if (codPres > 0) {
                    int numPS = modPresenze.query().valoreInt(Presenza.Cam.ps.get(), codPres);
                    modRighePS.query().registra(codRiga, Ps.Cam.progressivo.get(), numPS);
                    eseguito = true;  // basta una volta
                }// fine del blocco if
            } // fine del ciclo for

            if (eseguito) {
                new MessaggioAvviso("Revisione 250509 (fix PS) eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Revisione dati una tantum del 29-05-09.
     * da TipoArrivo a ArrivoCon
     * <p/>
     * Il campo TipoArrivo è stato sostituito da ArrivoCon/PartenzaCon
     * Questa revisione assegna il valore a ArrivoCon in base al campo TipoArrivo
     * in tutte le tavole dove è applicabile.
     * Alla fine della transizione il campo Tipo Arrivo verrà eliminato.
     */
    private void revisioneUnaTantum290509() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        ResultSet rs;
        String stat;

        try {    // prova ad eseguire il codice

            ConnessioneJDBC conn = (ConnessioneJDBC)this.getConnessione();

            /* revisione presenze */
            rs = conn.esegueSelect("select codice from presenza where arrivocon is not null");
            if (LibResultSet.quanteRighe(rs) == 0) {
                conn.esegueUpdate("update presenza set arrivocon = 2 where tipoarrivo = 1");
                conn.esegueUpdate("update presenza set arrivocon = 3 where tipoarrivo = 2");
                conn.esegueUpdate("update presenza set arrivocon = 4 where tipoarrivo = 3");
                conn.esegueUpdate("update presenza set arrivocon = 1 where tipoarrivo = 4");
                eseguito = true;
            }// fine del blocco if

            /* revisione conti */
            rs = conn.esegueSelect("select codice from conto where arrivocon is not null");
            if (LibResultSet.quanteRighe(rs) == 0) {
                conn.esegueUpdate("update conto set arrivocon = 2 where tipoarrivo = 1");
                conn.esegueUpdate("update conto set arrivocon = 3 where tipoarrivo = 2");
                conn.esegueUpdate("update conto set arrivocon = 4 where tipoarrivo = 3");
                conn.esegueUpdate("update conto set arrivocon = 1 where tipoarrivo = 4");
                eseguito = true;
            }// fine del blocco if

            /* revisione periodi */
            rs = conn.esegueSelect("select codice from periodi where arrivocon is not null");
            if (LibResultSet.quanteRighe(rs) == 0) {
                conn.esegueUpdate("update periodi set arrivocon = 2 where tipoarrivo = 1");
                conn.esegueUpdate("update periodi set arrivocon = 3 where tipoarrivo = 2");
                conn.esegueUpdate("update periodi set arrivocon = 4 where tipoarrivo = 3");
                conn.esegueUpdate("update periodi set arrivocon = 1 where tipoarrivo = 4");
                eseguito = true;
            }// fine del blocco if


            /**
             * revisione periodi
             * assegna arrivo con e partenza con in base al trattamento
             * ma solo se non sono già valorizzati
             */
            if (eseguito) { // solo la prima volta!

                Modulo mod = PeriodoModulo.get();
                Filtro filtro = FiltroFactory.creaFalso(Periodo.Cam.arrivato);
                Query query = new QuerySelezione(mod);
                query.addCampo(mod.getCampoChiave());
                query.addCampo(Periodo.Cam.trattamento.get());
                query.addCampo(Periodo.Cam.arrivoCon.get());
                query.addCampo(Periodo.Cam.partenzaCon.get());

                query.setFiltro(filtro);
                Dati dati = mod.query().querySelezione(query);

                for (int k = 0; k < dati.getRowCount(); k++) {

                    /* recupero dati comuni */
                    int codTrat = dati.getIntAt(k, Periodo.Cam.trattamento.get());
                    Listino.PensioniPeriodo trat = Listino.PensioniPeriodo.get(codTrat);
                    int codPeriodo = dati.getIntAt(k, mod.getCampoChiave());


                    /* arrivo con */
                    int codArrivoCon = dati.getIntAt(k, Periodo.Cam.arrivoCon.get());
                    if (codArrivoCon == 0) {

                        if (trat!=null) {
                            Periodo.TipiAP arrivoCon;
                            switch (trat) {
                                case mezzaPensione:
                                    arrivoCon = Periodo.TipiAP.dinner;
                                    break;
                                case pensioneCompleta:
                                    arrivoCon = Periodo.TipiAP.lunch;
                                    break;
                                case pernottamento:
                                    arrivoCon = Periodo.TipiAP.room;
                                    break;
                                default : // caso non definito
                                    arrivoCon = Periodo.TipiAP.lunch;
                                    break;
                            } // fine del blocco switch

                            /* scrive il valore */
                            mod.query().registra(codPeriodo, Periodo.Cam.arrivoCon, arrivoCon.getCodice());
                        }// fine del blocco if

                    }// fine del blocco if


                    /* partenza con */
                    int codPartenzaCon = dati.getIntAt(k, Periodo.Cam.partenzaCon.get());
                    if (codPartenzaCon == 0) {

                        if (trat!=null) {
                            Periodo.TipiAP partenzaCon;
                            switch (trat) {
                                case mezzaPensione:
                                    partenzaCon = Periodo.TipiAP.breakfast;
                                    break;
                                case pensioneCompleta:
                                    partenzaCon = Periodo.TipiAP.breakfast;
                                    break;
                                case pernottamento:
                                    partenzaCon = Periodo.TipiAP.breakfast;
                                    break;
                                default : // caso non definito
                                    partenzaCon = Periodo.TipiAP.breakfast;
                                    break;
                            } // fine del blocco switch

                            /* scrive il valore */
                            mod.query().registra(codPeriodo, Periodo.Cam.partenzaCon, partenzaCon.getCodice());
                        }// fine del blocco if

                    }// fine del blocco if



                } // fine del ciclo for

                dati.close();

            }// fine del blocco if


            if (eseguito) {
                new MessaggioAvviso("Revisione 290509 (tipoArrivo->arrivoCon/partenzaCon) eseguita.");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }



    /**
     * Revisione dati una tantum del 03-09-09.
     * Attribuzione data Ultimo Soggiorno a tuti i Clienti
     * <p/>
     * Si attiva solo se il campo è nuovo (contiene tutti nulli)
     */
    private void revisioneUnaTantum030909() {

        ResultSet rs;
        boolean esegui=false;
        Modulo modPresenza;
        Modulo modClienti;
        Filtro filtro;
        Ordine ordine;


        /* controlla se sono tutti nulli */
        ConnessioneJDBC conn = this.getConnessione();
        rs = conn.esegueSelect("select ultsoggiorno from anagrafica where ultsoggiorno is not null");
        if (LibResultSet.quanteRighe(rs) == 0) {
            esegui = true;
        }// fine del blocco if

        if (esegui) {

            modPresenza = PresenzaModulo.get();

            /**
             * mette da parte il filtro base del modello e lo
             * azzera provvisoriamente per non nascondere le eventuali
             * presenze registrate su altra azienda
             */
            Filtro filtroModello = modPresenza.getModello().getFiltroModello();
            modPresenza.getModello().setFiltroModello(null);

            modClienti = ClienteAlbergoModulo.get();
            Campo campoArrivo = modPresenza.getCampo(Presenza.Cam.arrivo);

            int[] codici = ClienteAlbergoModulo.get().query().valoriChiave();

            for (int cod : codici) {
                filtro = FiltroFactory.crea(Presenza.Cam.cliente.get(), cod);
                ordine = new Ordine(campoArrivo);

                ArrayList valori = modPresenza.query().valoriCampo(campoArrivo, filtro, ordine);
                if (valori.size()>0) {
                    Object ultimo = valori.get(valori.size()-1);
                    Date dataUltimo = Libreria.getDate(ultimo);
                    modClienti.query().registra(cod, ClienteAlbergo.Cam.ultSoggiorno, dataUltimo);
                }// fine del blocco if
                
            } // fine del ciclo for

            /* ripristina il filtro base del modello */
            modPresenza.getModello().setFiltroModello(filtroModello);

            new MessaggioAvviso("Revisione 030909 (data ultimo soggiorno in Anagrafica) eseguita.");
        }// fine del blocco if


    }


} //fine della classe