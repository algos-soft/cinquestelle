package it.algos.albergo.stampeobbligatorie.ps;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2TablePrinter;
import com.wildcrest.j2printerworks.J2TextPrinter;
import com.wildcrest.j2printerworks.VerticalGap;
import it.algos.albergo.arrivipartenze.ArriviPartenzeLogica;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.PannelloObbligatorie;
import it.algos.albergo.stampeobbligatorie.PrinterObblig;
import it.algos.albergo.stampeobbligatorie.StampeObbLogica;
import it.algos.albergo.stampeobbligatorie.WrapRisposta;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.memoria.dati.DatiMemoria;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.tavola.Tavola;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModulo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.util.Date;

public final class PsLogica extends StampeObbLogica implements Ps {

    /**
     * Costruttore completo con parametri.
     * <br>
     *
     * @param pan pannello di riferimento
     */
    public PsLogica(PannelloObbligatorie pan) {
        super(pan);
    }// fine del metodo costruttore completo


    /**
     * Crea le righe per un record di testa
     * e completa il record di testa con i dati delle righe
     * <p/>
     *
     * @param codTesta codice del record di testa
     *
     * @return true se riuscito
     */
    protected boolean creaRighe(int codTesta) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date giorno;
        Filtro filtro = null;
        Modulo modTesta;
        Modulo modPs;
        Modulo modPresenze;
        int codPresenza;
        int[] codPresenze;
        int codCliente;
        int[] codClienti=new int[0];
        int numPersone;

        try { // prova ad eseguire il codice

            /* recupera il giorno */
            giorno = this.getGiornoTesta(codTesta);
            continua = (Lib.Data.isValida(giorno));

            /* recupera il filtro per le presenze arrivate nel giorno per l'azienda attiva */
            if (continua) {
                filtro = PresenzaModulo.getFiltroPresenzeArrivate(giorno);
            }// fine del blocco if

            /* recupera i codici dei corrispondenti clienti */
            if (continua) {
                modPresenze = PresenzaModulo.get();
                codPresenze = modPresenze.query().valoriChiave(filtro);
                codClienti = new int[codPresenze.length];
                for (int k = 0; k < codPresenze.length; k++) {
                    codPresenza = codPresenze[k];
                    codCliente = modPresenze.query().valoreInt(Presenza.Cam.cliente.get(), codPresenza);
                    codClienti[k] = codCliente;
                } // fine del ciclo for
            }// fine del blocco if


            /* spazzolo tutti i clienti arrivati e creo le righe */
            if (continua) {

                modPs = PsModulo.get();

                for (int cod : codClienti) {

                    SetValori sv = new SetValori(modPs);
                    sv.add(Ps.Cam.linkTesta,codTesta);
                    sv.add(Ps.Cam.linkCliente,cod);
                    modPs.query().nuovoRecord(sv.getListaValori());

                } // fine del ciclo for-each

            } // fine del blocco if

            /* completa il record di testa coi dati delle righe */
            if (continua) {
                numPersone = codClienti.length;
                modTesta = TestaStampeModulo.get();
                modTesta.query().registra(codTesta, TestaStampe.Cam.numArrivati.get(), numPersone);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Assegna i numeri progressivi alle righe.
     * <p/>
     *
     * @param codTesta codice del record di testa per il quale effettuare le regolazioni <br>
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    protected boolean assegnaProgressivi(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        int next = 0;
        int[] codici = null;
        Modulo modPS = null;
        Filtro filtro;
        Ordine ordine;
        Object ogg = null;
        int ultimo = 0;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (codTesta > 0);


            /* recupera il modulo Righe */
            if (continua) {
                modPS = this.getModRighe();
                continua = (modPS != null);
            } // fine del blocco if

            /**
             * recupera l'ultimo numero di PS dell'anno in corso
             * (0 se non ce ne sono ancora)
             */
            if (continua) {

                /* recupera l'anno di competenza del documento */
                Modulo modTesta = TestaStampeModulo.get();
                Date data = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);
                int anno = Lib.Data.getAnno(data);
                
                filtro = this.getFiltroTestaAnno(anno);    // tutti i record di testa di PS dell'anno di competenza

                ordine = new Ordine();
                ordine.add(Ps.Cam.progressivo.get());

                ogg = modPS.query().valoreUltimoRecord(Ps.Cam.progressivo.get(), filtro, ordine, conn);

            } // fine del blocco if

            if (continua) {
                if (ogg != null) {
                    ultimo = Libreria.getInt(ogg);
                } // fine del blocco if
                next = ultimo + 1;

                codici = this.getCodiciRighe(codTesta, conn);
                continua = (codici != null) && (codici.length > 0);
            } // fine del blocco if

            if (continua) {
                for (int cod : codici) {
                    modPS.query().registra(cod, Ps.Cam.progressivo.get(), next, conn);
                    next++;
                } // fine del ciclo for-each
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Effettua le movimentazioni propedeutiche alla prima stampa.
     * <p/>
     *
     * @param codTesta codice del record di testa
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    protected boolean movimenta(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date giorno;
        int codAzienda;

        try {    // prova ad eseguire il codice

            /* movimenta nella superclasse */
            continua = super.movimenta(codTesta, conn);

//            /* movimentazioni specifiche */
//            if (continua) {
//                giorno = super.getGiornoTesta(codTesta);
//                codAzienda = this.getCodAzienda();
//                continua = PresenzaModulo.registraPS(giorno, codAzienda, conn);
//            }// fine del blocco if
//
//            /**
//             * Ricopia i progressivi assegnati nelle righe
//             */
//            if (continua) {
////                todo da fare
//            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


//    /**
//     * Effettua le de-movimentazioni conseguenti all'annullamento.
//     * <p/>
//     * todo provvisorio fino a quando non togliamo il n. di PS dalle presenze
//     * cancella il n. di ps dalle relative presenze
//     *
//     * @param codiciTesta elenco dei codici record di testa che verranno cancellati
//     * @param conn la connessione da utilizzare
//     *
//     * @return true se riuscito
//     */
//    protected boolean demovimenta(int[] codiciTesta, Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        boolean continua = true;
//        int[] codiciRighe;
//        Modulo modRighe;
//        Modulo modPresenze;
//        Filtro filtro;
//        int codPresenza;
//
//        try { // prova ad eseguire il codice
//
//            modRighe = this.getModRighe();
//            modPresenze = PresenzaModulo.get();
//            for (int codTesta : codiciTesta) {
//
//                filtro = FiltroFactory.crea(Ps.Cam.linkTesta.get(), codTesta);
//                codiciRighe = modRighe.query().valoriChiave(filtro, conn);
//
//                for (int codRiga : codiciRighe) {
//                    codPresenza = modRighe.query().valoreInt(Ps.Cam.linkPresenza.get(), codRiga);
//                    continua = modPresenze.query().registra(codPresenza, Presenza.Cam.ps.get(), 0);
//
//                    if (!continua) {
//                        break;
//                    }// fine del blocco if
//
//                }
//
//                if (!continua) {
//                    break;
//                }// fine del blocco if
//
//            }
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return continua;
//
//    }


    /**
     * Crea il printer relativo a un giorno di stampa.
     * <p/>
     *
     * @param codTesta codice del record di testa
     * @param conn la connessione da utilizzare
     * @param primaStampa true per prima stampa false per ristampa
     *
     * @return il printer creato
     */
    protected PrinterObblig creaPrinter(int codTesta, Connessione conn, boolean primaStampa) {
//        /* variabili e costanti locali di lavoro */
//        PrinterObblig printer = null;
//        Date giorno;
//
//        try {    // prova ad eseguire il codice
//            giorno = super.getGiornoTesta(codTesta);
//            printer = this.creaPrinter(conn, giorno, primaStampa);
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return printer;


        /* variabili e costanti locali di lavoro */
        PrinterObblig printer = null;
        Tavola tavola;
        J2TablePrinter tablePrinter;
        Modulo modAzienda;
        EstrattoBase estratto;
        String testo;
        JLabel label;
        JTextArea area;
        J2TextPrinter intestazione;
        J2ComponentPrinter titolo;
        J2ComponentPrinter piede;
        J2FlowPrinter flowPrinter;
        Date giorno;

        try {    // prova ad eseguire il codice

            /* crea la tavola da stampare con i dati e recupera il TablePrinter */
            tavola = this.creaTavolaStampa(codTesta, conn);
            tablePrinter = tavola.getTablePrinter();
            tablePrinter.setHorizontalAlignment(J2TablePrinter.LEFT);

            /* crea il flowable per l'intestazione */
            modAzienda = AziendaModulo.get();
            estratto = modAzienda.getEstratto(Azienda.Est.intestazione, this.getCodAzienda());
            testo = estratto.getStringa();
            area = new JTextArea(testo);
            area.setFont(FontFactory.creaPrinterFont(Font.BOLD));
            intestazione = new J2TextPrinter(area);

            /* crea il flowable per il titolo */
            giorno = this.getGiornoTesta(codTesta);
            testo = "Elenco delle persone alloggiate il giorno " + Lib.Data.getStringa(giorno);
            label = new JLabel(testo);
            label.setFont(FontFactory.creaPrinterFont(Font.BOLD));
            titolo = new J2ComponentPrinter(label);
            titolo.setHorizontalAlignment(J2TablePrinter.LEFT);

            /* crea il flowable per il piede */
            testo =
                    "Data di consegna all'autorità di P.S. _____________   il ricevente _____________   il gestore _____________";
            label = new JLabel(testo);
            label.setFont(FontFactory.creaPrinterFont());
            piede = new J2ComponentPrinter(label);
            piede.setHorizontalAlignment(J2TablePrinter.LEFT);

            /* crea il Printer completo */
            flowPrinter = new J2FlowPrinter();
            flowPrinter.addFlowable(new VerticalGap());
            flowPrinter.addFlowable(intestazione);
            flowPrinter.addFlowable(new VerticalGap());
            flowPrinter.addFlowable(titolo);
            flowPrinter.addFlowable(new VerticalGap());
            flowPrinter.addFlowable(tablePrinter);
            flowPrinter.addFlowable(new VerticalGap(0.5));
            flowPrinter.addFlowable(piede);
            printer = new PrinterObblig(primaStampa);
            printer.addPageable(flowPrinter);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return printer;



    }


//    /**
//     * Crea il Printer per la stampa.
//     * <p/>
//     *
//     * @param conn la connessione da utilizzare
//     * @param giorno data di riferimento
//     * @param primaStampa true per prima stampa false per ristampa
//     *
//     * @return il printer creato
//     */
//    private PrinterObblig creaPrinter(Connessione conn, Date giorno, boolean primaStampa) {
//        /* variabili e costanti locali di lavoro */
//        PrinterObblig printer = null;
//        Tavola tavola;
//        J2TablePrinter tablePrinter;
//        Modulo modAzienda;
//        EstrattoBase estratto;
//        String testo;
//        JLabel label;
//        JTextArea area;
//        J2TextPrinter intestazione;
//        J2ComponentPrinter titolo;
//        J2ComponentPrinter piede;
//        J2FlowPrinter flowPrinter;
//
//        try {    // prova ad eseguire il codice
//
//            /* crea la tavola da stampare con i dati e recupera il TablePrinter */
//            tavola = this.creaTavolaStampa(conn, giorno);
//            tablePrinter = tavola.getTablePrinter();
//            tablePrinter.setHorizontalAlignment(J2TablePrinter.LEFT);
//
//            /* crea il flowable per l'intestazione */
//            modAzienda = AziendaModulo.get();
//            estratto = modAzienda.getEstratto(Azienda.Est.intestazione, this.getCodAzienda());
//            testo = estratto.getStringa();
//            area = new JTextArea(testo);
//            area.setFont(FontFactory.creaPrinterFont(Font.BOLD));
//            intestazione = new J2TextPrinter(area);
//
//            /* crea il flowable per il titolo */
//            testo = "Elenco delle persone alloggiate il giorno " + Lib.Data.getStringa(giorno);
//            label = new JLabel(testo);
//            label.setFont(FontFactory.creaPrinterFont(Font.BOLD));
//            titolo = new J2ComponentPrinter(label);
//            titolo.setHorizontalAlignment(J2TablePrinter.LEFT);
//
//            /* crea il flowable per il piede */
//            testo =
//                    "Data di consegna all'autorità di P.S. _____________   il ricevente _____________   il gestore _____________";
//            label = new JLabel(testo);
//            label.setFont(FontFactory.creaPrinterFont());
//            piede = new J2ComponentPrinter(label);
//            piede.setHorizontalAlignment(J2TablePrinter.LEFT);
//
//            /* crea il Printer completo */
//            flowPrinter = new J2FlowPrinter();
//            flowPrinter.addFlowable(new VerticalGap());
//            flowPrinter.addFlowable(intestazione);
//            flowPrinter.addFlowable(new VerticalGap());
//            flowPrinter.addFlowable(titolo);
//            flowPrinter.addFlowable(new VerticalGap());
//            flowPrinter.addFlowable(tablePrinter);
//            flowPrinter.addFlowable(new VerticalGap(0.5));
//            flowPrinter.addFlowable(piede);
//            printer = new PrinterObblig(primaStampa);
//            printer.addPageable(flowPrinter);
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return printer;
//    }


    /**
     * Crea una Tavola contenente i dati da stampare.
     * <p/>
     *
     * @param codTesta codice del record di testa
     * @param conn la connessione da utilizzare per la lettura dei dati delle presenze
     *
     * @return la tavola creata e riempita con i dati
     */
    private Tavola creaTavolaStampa(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Tavola tavola = null;
        Modulo modCliente;
        Modulo modCitta;
        Modulo modIndirizzo;
        Modulo modNazione;
        Query query;
        Filtro filtro;
        Ordine ordine;
        Dati dati;
        int numPS;
        int codCliente;
        int numOrdine;
        int codCitta;
        int codNazione;
        String nomeCitta;
        String siglaNazione;
        String luogonascita;
        Date datanascita;
        String luogoresidenza;
        int codIndirizzo;
        String nominativo;
        String nome;
        String cognome;
        Vista vista;
        TavolaModello modello;
        DatiMemoria datiMem;
        Modulo modRighe;

        Campo campoOrd;
        Campo campoPs;
        Campo campoNominativo;
        Campo campoLuogoNascita;
        Campo campoDataNascita;
        Campo campoLuogoResidenza;

        try {    // prova ad eseguire il codice

            /* recupera i moduli necessari */
            modCliente = ClienteAlbergoModulo.get();
            modCitta = CittaModulo.get();
            modIndirizzo = IndirizzoModulo.get();
            modNazione = NazioneModulo.get();

            /* costruisce i campi di stampa */
            campoOrd = CampoFactory.intero("#");
            campoOrd.setLarLista(20);
            campoPs = CampoFactory.intero("n. ps");
            campoPs.setLarLista(35);
            campoNominativo = CampoFactory.testo("cognome e nome");
            campoNominativo.setLarLista(140);
            campoLuogoNascita = CampoFactory.testo("luogo di nascita");
            campoLuogoNascita.setLarLista(160);
            campoDataNascita = CampoFactory.data("data di nascita");
            campoLuogoResidenza = CampoFactory.testo("luogo di residenza");
            campoLuogoResidenza.setLarLista(160);

            /* costruisce una vista con i campi */
            vista = new Vista();
            vista.addCampo(campoOrd);
            vista.addCampo(campoPs);
            vista.addCampo(campoNominativo);
            vista.addCampo(campoLuogoNascita);
            vista.addCampo(campoDataNascita);
            vista.addCampo(campoLuogoResidenza);

            /**
             * Recupera i dati ordinati delle righe da stampare
             */
            modRighe = this.getModRighe();
            query = new QuerySelezione(modRighe);
            query.addCampo(Ps.Cam.progressivo.get());
            query.addCampo(Ps.Cam.linkCliente.get());
            filtro = FiltroFactory.crea(Ps.Cam.linkTesta.get(), codTesta);
            ordine = new Ordine();
            ordine.add(Ps.Cam.progressivo.get());
            query.setFiltro(filtro);
            query.setOrdine(ordine);
            dati = modRighe.query().querySelezione(query, conn);

            /**
             * crea un oggetto Dati mantenuto in memoria
             * stesse colonne della vista
             * tante righe quante sono le presenze da stampare
             */
            datiMem = new DatiMemoria(vista.getCampi(), dati.getRowCount());

            /* spazzola i dati e riempie la memoria */
            for (int k = 0; k < dati.getRowCount(); k++) {

                /* numero d'ordine */
                numOrdine = k + 1;

                /* numero di P.S. */
                numPS = dati.getIntAt(k, Ps.Cam.progressivo.get());

                /* codice del cliente */
                codCliente = dati.getIntAt(k, Ps.Cam.linkCliente.get());

                /* nominativo (cognome e nome) */
                cognome = modCliente.query().valoreStringa(
                        Anagrafica.Cam.cognome.get(),
                        codCliente);
                nome = modCliente.query().valoreStringa(Anagrafica.Cam.nome.get(), codCliente);
                nominativo = Lib.Testo.concatSpace(cognome, nome);

                /* luogo di nascita (città e nazione) */
                codCitta = modCliente.query().valoreInt(
                        ClienteAlbergo.Cam.luogoNato.get(),
                        codCliente);
                nomeCitta = modCitta.query().valoreStringa(Citta.Cam.citta.get(), codCitta);
                codNazione = modCitta.query().valoreInt(Citta.Cam.linkNazione.get(), codCitta);
                siglaNazione = modNazione.query().valoreStringa(
                        Nazione.Cam.sigla2.get(),
                        codNazione);
                luogonascita = nomeCitta;
                if (Lib.Testo.isValida(siglaNazione)) {
                    luogonascita += " (" + siglaNazione + ")";
                }// fine del blocco if

                /* data di nascita */
                datanascita = modCliente.query().valoreData(
                        ClienteAlbergo.Cam.dataNato.get(),
                        codCliente);

                /* luogo di residenza (città e nazione) */
                codIndirizzo = ClienteAlbergoModulo.getCodIndirizzo(codCliente);
                codCitta = modIndirizzo.query().valoreInt(Indirizzo.Cam.citta.get(), codIndirizzo);
                nomeCitta = modCitta.query().valoreStringa(Citta.Cam.citta.get(), codCitta);
                codNazione = modCitta.query().valoreInt(Citta.Cam.linkNazione.get(), codCitta);
                siglaNazione = modNazione.query().valoreStringa(
                        Nazione.Cam.sigla2.get(),
                        codNazione);
                luogoresidenza = nomeCitta;
                if (Lib.Testo.isValida(siglaNazione)) {
                    luogoresidenza += " (" + siglaNazione + ")";
                }// fine del blocco if

                datiMem.setValueAt(k, campoOrd, numOrdine);
                datiMem.setValueAt(k, campoPs, numPS);
                datiMem.setValueAt(k, campoNominativo, nominativo);
                datiMem.setValueAt(k, campoLuogoNascita, luogonascita);
                datiMem.setValueAt(k, campoDataNascita, datanascita);
                datiMem.setValueAt(k, campoLuogoResidenza, luogoresidenza);

            } // fine del ciclo for

            dati.close();

            /* costruisce una Tavola con la vista e i dati */
            modello = new TavolaModello(vista);
            modello.setDati(datiMem);
            tavola = new Tavola(modello);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tavola;
    }




    /**
     * Verifica se un record di testa è stampabile.
     * <p/>
     * Chiamato dal bottone <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param codTesta codice del record di testa per il quale effettuare la/le ristampa/e <br>
     *
     * @return un wrapper contenente la risposta e il livello di errore
     */
    @Override
    protected WrapRisposta chkStampabile(int codTesta) {
        /* variabili e costanti locali di lavoro */
        WrapRisposta wrapper = null;
        String testo;
        Date dataPS;
        Date dataSuccessiva;
        boolean preced;
        int[] codRighe;
        boolean valida;

        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            wrapper = super.chkStampabile(codTesta);

            codRighe = this.getCodiciRighe(codTesta, Progetto.getConnessione());
            for (int codRiga : codRighe) {
                valida = isRigaValida(codRiga);
                if (!valida) {
                    testo = "La documentazione di alcuni clienti non è valida.";
                    wrapper.addErrore(false, testo);
                    break;
                }// fine del blocco if
            }

            /**
             * controlla se esistono arrivi previsti e non ancora confermati
             * per l'azienda corrente e in data uguale o precedente al giorno di stampa.
             * se esistono, avvisa e chiede conferma.
             */
            dataPS = this.getGiornoTesta(codTesta);
            dataSuccessiva = Lib.Data.add(dataPS, 1);
            preced = ArriviPartenzeLogica.isEsistonoArriviPrecedenti(dataSuccessiva);
            if (preced) {
                testo = "Ci sono degli arrivi ancora da confermare in data uguale\n";
                testo += "o precedente il " + Lib.Data.getStringa(dataPS) + ".\n";
                testo += "Se stampi il registro non potrai più confermarli prima del ";
                testo += Lib.Data.getStringa(dataSuccessiva) + ".\n";
                wrapper.addErrore(false, testo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return wrapper;
    }


    /**
     * Controlla se una riga di PS è valida.
     * <p/>
     *
     * @param codRiga codice della riga da controllare
     * @return vero se le info delle persone sono sufficienti
     */
    private static boolean isRigaValida(int codRiga) {
        /* variabili e costanti locali di lavoro */
        boolean valida = true;
        Modulo modPS;
        int codCliente;
        Date data;

        try {    // prova ad eseguire il codice

            modPS = PsModulo.get();
            codCliente = modPS.query().valoreInt(Ps.Cam.linkCliente.get(), codRiga);
            data = PsLogica.getDataTesta(codRiga);
            valida = ClienteAlbergoModulo.isValidoPS(codCliente, data);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }

    /**
     * Ritorna la data del record di testa corrispondente a una data riga.
     * <p/>
     * @param codRiga il codice chiave della riga
     * @return la data del corrispondente record di testa
     */
    public static Date getDataTesta(int codRiga) {
        /* variabili e costanti locali di lavoro */
        Date data  = null;
        Modulo modRighe;
        Modulo modTesta;
        int codTesta;

        try {    // prova ad eseguire il codice

            modRighe = PsModulo.get();
            codTesta = modRighe.query().valoreInt(Ps.Cam.linkTesta.get(), codRiga);
            modTesta = TestaStampeModulo.get();
            data = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }




    /**
     * Ritorna la parte di base per il nome del file pdf.
     * <p/>
     *
     * @return il nome base del file .pdf
     */
    protected String getBaseFilePdf() {
        return "PS";
    }


    /**
     * Ritorna il nome del campo linkato al record di testa
     * specifico di questa logica.
     * <p/>
     *
     * @return il nome del campo link
     */
    protected String getNomeCampoLinkTesta() {
        return Ps.Cam.linkTesta.get();
    }


    /**
     * Ritorna il codice del tipo di record di testa.
     * <p/>
     */
    @Override
    protected int getCodTipoRecord() {
        return TestaStampe.TipoRegistro.ps.getCodice();
    }


    /**
     * Ritorna il modulo delle righe.
     * <p/>
     */
    @Override
    protected Modulo getModRighe() {
        return PsModulo.get();
    }


}
