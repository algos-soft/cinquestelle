package it.algos.albergo.stampeobbligatorie;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.AlbergoPref;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibTesto;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.wrapper.SetValori;

import java.util.ArrayList;
import java.util.Date;

public class StampeObbLogica {

    /* pannello di riferimento per questa logica */
    private PannelloObbligatorie panObbligatorie;

    /* data di stampa corrente aggiornata ogni volta che si lancia una stampa o una ristampa */
    private Date dataStampaCorrente;


    /**
     * Costruttore completo con parametri.
     * <br>
     *
     * @param pan pannello di riferimento
     */
    public StampeObbLogica(PannelloObbligatorie pan) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setPanObbligatorie(pan);

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
     * Cancella i records delle stampe non ancora stampate.
     * <p/>
     * - Cancella tutti i records di testa non ancora stampati
     * (e di conseguenza le righe associate)
     * (in modo da essere allineato con Presenze) <br>
     */
    public void cancellaRecords() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modTesta;
        Filtro filtro;

        try { // prova ad eseguire il codice

            modTesta = this.getModTesta();
            continua = (modTesta != null);

            if (continua) {
                filtro = FiltroFactory.crea(TestaStampe.Cam.tipo.get(), this.getCodTipoRecord());
                filtro.add(FiltroFactory.creaFalso(TestaStampe.Cam.stampato));
                filtro.add(FiltroFactory.crea(TestaStampe.Cam.azienda.get(), this.getCodAzienda()));
                modTesta.query().eliminaRecords(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea i records di testa e delle righe.
     * <p/>
     * Opera a seconda del tipo richiesto (ps, notifica, istat) <br>
     * Chiamato all'apertura del dialogo <br>
     * Chiamato dopo una procedura di annulla() <br>
     * - Cancella tutti i records di testa (e righe associate) delle notifiche non ancora stampate
     * (in modo da essere allineato con Presenze) <br>
     * - Recupera la data successiva all'ultima stampa chiusa  <br>
     * (se non ce ne sono, usa la data del primo arrivo) <br>
     * - Recupera la data dell'ultimo arrivo  <br>
     * - Spazzola tutti i giorni tra le due date <br>
     * - Se per il giorno ci sono arrivi, invoca la procedura di creazione <br>
     */
    public void creaRecords() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Date ultimaStampaChiusa;
        Date dataInizioCreazione = null;
        Date dataFineCreazione = null;
        Modulo modPresenze;
        Date data;
        Date[] dateDaCreare = null;
        int codTesta;

        try { // prova ad eseguire il codice

            modPresenze = PresenzaModulo.get();
            continua = (modPresenze != null);

            /* determina la data di inizio creazione */
            if (continua) {
                ultimaStampaChiusa = this.getDataUltimaStampaChiusa();
                if (ultimaStampaChiusa == null) {
                    data = this.getDataPrimoArrivo();
                    if (Lib.Data.isValida(data)) {
                        dataInizioCreazione = data;
                    }// fine del blocco if-else
                } else {
                    dataInizioCreazione = Lib.Data.add(ultimaStampaChiusa, 1);
                }// fine del blocco if-else
                continua = Lib.Data.isValida(dataInizioCreazione);
            }// fine del blocco if

            /* determina la data di fine creazione */
            if (continua) {
                dataFineCreazione = this.getDataFineCreazione();
                continua = Lib.Data.isValida(dataFineCreazione);
            }// fine del blocco if

            /* crea un array di tutte le date comprese */
            if (continua) {
                dateDaCreare = Lib.Data.getDateComprese(dataInizioCreazione, dataFineCreazione);
                continua = (dateDaCreare != null) && (dateDaCreare.length > 0);
            }// fine del blocco if

            // se le date sono molte (le stampe non si fanno da tempo)
            // avvisa l'utente e chiede conferma
            if (continua) {
            	if (dateDaCreare.length>100) {
            		MessaggioDialogo dialogo = new MessaggioDialogo(panObbligatorie.getClass().getSimpleName()
            				+"\nAttenzione, "
            				+ "\nCi sono "+dateDaCreare.length+" date da elaborare."
            				+ "\nQuesta elaborazione potrebbe richiedere un tempo lungo."
            				+ "\nVuoi continuare ugualmente?");
            		continua = dialogo.isConfermato();
				}
			}
            
            
            /**
             * spazzola tutti i giorni tra le due date
             * per i giorni che hanno movimenti, crea il record di testa
             * e invoca la procedura di creazione delle righe
             */
            if (continua) {
                for (Date unaData : dateDaCreare) {
                    if (this.isMovimenti(unaData)) {

                        if (this.isEsisteTesta(unaData)) {
                            codTesta = this.getRecordTesta(unaData);
                        } else {
                            codTesta = this.creaRecordTesta(unaData);
                        } // fine del blocco if-else

                        if (codTesta > 0) {
                            continua = this.creaRighe(codTesta);
                            if (!continua) {
                                break;
                            }// fine del blocco if
                        } else {
                            new MessaggioAvviso("Errore nella creazione del record di testa");
                            break;
                        }// fine del blocco if-else


                    }// fine del blocco if
                } // fine del ciclo for-each
            } // fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna la data di fine creazione dei record di testa..
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @return la data di fine creazione
     */
    protected Date getDataFineCreazione() {
        return this.getDataUltimoArrivo();
    }


    /**
     * Determina se esistono dei movimenti significativi ai fini di questo registro
     * per la data indicata
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param data da controllare
     *
     * @return true se esistono movimenti
     */
    protected boolean isMovimenti(Date data) {
        return this.isArrivi(data);
    }


    /**
     * Crea il record di testa per un giorno.
     * <p/>
     *
     * @param giorno degli arrivi
     *
     * @return il codice del record creato, 0 se non creato
     */
    protected int creaRecordTesta(Date giorno) {
        /* variabili e costanti locali di lavoro */
        int codTesta = 0;
        SetValori sv;
        Modulo modTesta;

        try { // prova ad eseguire il codice

            modTesta = this.getModTesta();
            sv = new SetValori(modTesta);
            sv.add(TestaStampe.Cam.tipo, this.getCodTipoRecord());
            sv.add(TestaStampe.Cam.stampato, false);
            sv.add(TestaStampe.Cam.data, giorno);
            sv.add(TestaStampe.Cam.azienda, this.getCodAzienda());
            codTesta = modTesta.query().nuovoRecord(sv.getListaValori());
            if (codTesta < 0) {
                codTesta = 0;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codTesta;

    }


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
        return false;
    }


    /**
     * Recupera la data dell'ultima stampa chiusa.
     * <p/>
     *
     * @return la data dell'ultima stampa chiusa, null se non ce ne sono
     */
    protected Date getDataUltimaStampaChiusa() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        boolean continua;
        Modulo modTesta;
        Filtro filtro;
        Ordine ordine;
        Object valore;

        try {    // prova ad eseguire il codice

            modTesta = this.getModTesta();
            continua = (modTesta != null);

            if (continua) {
                filtro = this.getFiltroTesta();
                filtro.add(FiltroFactory.creaVero(TestaStampe.Cam.stampato.get()));
                ordine = new Ordine();
                ordine.add(TestaStampe.Cam.data.get());
                valore = modTesta.query().valoreUltimoRecord(TestaStampe.Cam.data.get(), filtro, ordine);
                if (valore != null) {
                    data = Libreria.getDate(valore);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Recupera la data del primo arrivo registrato.
     * <p/>
     *
     * @return la data del primo arrivo, null se non ce ne sono
     */
    protected Date getDataPrimoArrivo() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        boolean continua;
        Modulo modPresenze;
        Filtro filtro;
        Ordine ordine;
        Object valore;

        try {    // prova ad eseguire il codice

            modPresenze = PresenzaModulo.get();
            continua = (modPresenze != null);

            if (continua) {
                filtro = PresenzaModulo.getFiltroPresenzeArrivate(null, null);
                ordine = new Ordine();
                ordine.add(Presenza.Cam.entrata.get());
                valore = modPresenze.query()
                        .valorePrimoRecord(Presenza.Cam.entrata.get(), filtro, ordine);
                if (valore != null) {
                    data = Libreria.getDate(valore);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Determina se esistono arrivi per una certa data.
     * <p/>
     *
     * @param giorno data da controllare
     *
     * @return true se esistono arrivi per la data indicata
     */
    protected boolean isArrivi(Date giorno) {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        boolean continua;
        Modulo modPresenza;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            modPresenza = PresenzaModulo.get();
            continua = (modPresenza != null);

            if (continua) {
                filtro = PresenzaModulo.getFiltroPresenzeArrivate(giorno);
                esistono = modPresenza.query().isEsisteRecord(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


    /**
     * Determina se esistono partenze per una certa data.
     * <p/>
     *
     * @param giorno data da controllare
     *
     * @return true se esistono partenze per la data indicata
     */
    protected boolean isPartenze(Date giorno) {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        boolean continua;
        Modulo modPresenza;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            modPresenza = PresenzaModulo.get();
            continua = (modPresenza != null);

            if (continua) {
                filtro = PresenzaModulo.getFiltroPresenzePartite(giorno);
                esistono = modPresenza.query().isEsisteRecord(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


    /**
     * Recupera la data dell'ultimo arrivo registrato.
     * <p/>
     *
     * @return la data dell'ultimo arrivo, null se non ce ne sono
     */
    protected Date getDataUltimoArrivo() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        boolean continua;
        Modulo modPresenze;
        Filtro filtro;
        Ordine ordine;
        Object valore;

        try {    // prova ad eseguire il codice

            modPresenze = PresenzaModulo.get();
            continua = (modPresenze != null);

            if (continua) {
                filtro = PresenzaModulo.getFiltroPresenzeArrivate(null, null);
                ordine = new Ordine();
                ordine.add(Presenza.Cam.entrata.get());
                valore = modPresenze.query()
                        .valoreUltimoRecord(Presenza.Cam.entrata.get(), filtro, ordine);
                if (valore != null) {
                    data = Libreria.getDate(valore);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Recupera la data dell'ultima partenza registrata.
     * <p/>
     *
     * @return la data dell'ultima partenza, null se non ce ne sono
     */
    protected Date getDataUltimaPartenza() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        boolean continua;
        Modulo modPresenze;
        Filtro filtro;
        Ordine ordine;
        Object valore;

        try {    // prova ad eseguire il codice

            modPresenze = PresenzaModulo.get();
            continua = (modPresenze != null);

            if (continua) {
                filtro = PresenzaModulo.getFiltroPresenzePartite(null, null);
                ordine = new Ordine();
                ordine.add(Presenza.Cam.uscita.get());
                valore = modPresenze.query()
                        .valoreUltimoRecord(Presenza.Cam.uscita.get(), filtro, ordine);
                if (valore != null) {
                    data = Libreria.getDate(valore);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Determina se esiste il record di testa per un dato giorno
     * <p/>
     *
     * @param giorno la data di riferimento
     *
     * @return true se esiste il record di testa per il giorno specificato
     */
    protected boolean isEsisteTesta(Date giorno) {
        return (this.getRecordTesta(giorno) != 0);
    }


    /**
     * Stampa o ristampa i documenti di un giorno.
     * <p/>
     * Stampa il documento (o i documenti) a seconda del tipo richiesto (ps, notifica, istat) <br>
     * Metodo invocato dal bottone stampa <br>
     * Segna come stampato il record di testa <br>
     * Aggiorna i progressivi <br>
     *
     * @param codTesta    codice del record di testa per il quale effettuare la/le stampa/e <br>
     * @param primaStampa true per effettuare la prima stampa, false per effettuare la ristampa <br>
     *
     * @return vero se la stampa è stata confermata
     *         falso se la stampa è stata annullata
     */
    public boolean stampa(int codTesta, boolean primaStampa) {
        /* variabili e costanti locali di lavoro */
        boolean stampato = false;
        boolean continua = true;
        String messaggio;
        Modulo modTesta;
        Date dataStampa;
        WrapRisposta wrapper;
        MessaggioDialogo dialogo;
        String testo;
        int codAzienda;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            if (codTesta <= 0) {
                testo = "Il codice del record di testa non è valido.";
                new MessaggioAvviso(testo);
                continua = false;
            }// fine del blocco if

            /* controllo una sola azienda attiva */
            if (continua) {
                codAzienda = AlbergoModulo.getCodAzienda();
                if (codAzienda == 0) {
                    testo = "Le stampe obbligatorie vanno effettuate per una sola azienda alla volta.";
                    new MessaggioAvviso(testo);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se prima stampa, controllo se stampabile */
            if (continua) {
                if (primaStampa) {
                    wrapper = this.chkStampabile(codTesta);
                    if (wrapper.isErrore()) {
                        if (wrapper.isErroreCritico()) {
                            messaggio = "Stampa non effettuabile.\n";
                            messaggio += wrapper.getMessaggio();
                            new MessaggioAvviso(messaggio);
                            continua = false;
                        } else {
                            messaggio = wrapper.getMessaggio();
                            messaggio += "\nVuoi continuare ugualmente?";
                            dialogo = new MessaggioDialogo(messaggio);
                            continua = dialogo.isConfermato();
                        }// fine del blocco if-else
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* registra la data della stampa corrente */
            if (continua) {
                modTesta = this.getModTesta();
                dataStampa = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);
                this.setDataStampaCorrente(dataStampa);
            }// fine del blocco if

//            /* presenta e registra il dialogo di opzioni stampa */
//            if (continua) {
//                opzioni = new OpzioniStampa(this, primaStampa);
//                opzioni.avvia();
//                continua = opzioni.isConfermato();
//            }// fine del blocco if

            /* esegue la stampa o la ristampa */
            if (continua) {
                if (primaStampa) {
                    stampato = this.primaStampa(codTesta);
                } else {
                    stampato = this.ristampa(codTesta);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stampato;
    }


    /**
     * Stampa i documenti di un giorno per la prima volta.
     * <p/>
     * Segna come stampato il record di testa <br>
     * Aggiorna i progressivi <br>
     *
     * @param codTesta codice del record di testa per il quale effettuare la/le stampa/e <br>
     *
     * @return vero se la stampa è stata confermata
     *         falso se la stampa è stata interrotta
     */
    private boolean primaStampa(int codTesta) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        boolean inTransaction = false;
        Connessione conn;
        PrinterObblig printer = null;
        RisultatoStampa risultato=null;
        boolean confermaTrans=false;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = Progetto.getConnessione();
            continua = (conn != null);

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                inTransaction = true;
            }// fine del blocco if

            /* effettua le movimentazioni in transazione */
            if (continua) {
                continua = this.movimenta(codTesta, conn);
            }// fine del blocco if

            /* crea il Printer completo */
            if (continua) {
                printer = this.creaPrinter(codTesta, conn, true);   // crea un Printer di Prima Stampa
                continua = (printer != null);
            }// fine del blocco if

            /**
             * stampa il printer e determina se deve confermare la transazione
             * la transazione va confermata solo se ha effettuato con successo
             * una stampa definitiva
             */
            if (continua) {
                risultato = this.esegueStampa(printer);
                confermaTrans = (risultato.equals(RisultatoStampa.stampaDefinitivaEffettuata));
            }// fine del blocco if

            /* conclude la transazione */
            if (inTransaction) {
                if (confermaTrans) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Ristampa il documento di un giorno.
     * <p/>
     * Ristampa il documento (o i documenti) a seconda del tipo richiesto (ps, notifica, istat) <br>
     * Metodo invocato dal bottone stampa <br>
     * NON aggiorna i progressivi <br>
     *
     * @param codTesta codice del record di testa per il quale effettuare la/le ristampa/e <br>
     *
     * @return vero se la stampa è stata confermata
     *         falso se la stampa è stata interrotta
     */
    private boolean ristampa(int codTesta) {
        /* variabili e costanti locali di lavoro */
        boolean riuscita=false;
        boolean continua = false;
        PrinterObblig printer;
        Connessione conn;
        RisultatoStampa risultato;

        try { // prova ad eseguire il codice

            /* crea il Printer completo */
            conn = Progetto.getConnessione();
            printer = this.creaPrinter(codTesta, conn, false);   // crea un printer di Ristampa
            continua = (printer != null);

            /* stampa il printer con le opzioni desiderate */
            if (continua) {
                risultato = this.esegueStampa(printer);

                /* una ristampa può tornare solo i due casi seguenti  */
                switch (risultato) {
                    case ristampaEffettuata:
                        riuscita = true;
                        break;
                    case stampaFallita:
                        riuscita = false;
                        break;
                    default : // caso non definito
                        break;
                } // fine del blocco switch

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscita;
    }


    /**
     * Esegue una stampa già preparata.
     * <p/>
     *
     * Presenta la stampa in anteprima
     * Esegue la stampa di controllo o definitiva
     * su stampante o pdf
     *
     * @param printer da stampare
     *
     * @return il risultato della operazione descritto da un elemento dalla Enum RisultatoStampa:
     * - RisultatoStampa.stampaControlloEffettuata: ha effettuato con successo una prima stampa di controllo
     * - RisultatoStampa.stampaDefinitivaEffettuata: ha effettuato con successo una prima stampa definitiva
     * - RisultatoStampa.ristampaEffettuata: ha effettuato con successo una ristampa
     * - RisultatoStampa.stampaFallita: l'operazione di prima stampa o ristampa è fallita.
     *
     *         Se sono state richieste sia la stampa che il pdf, ritorna stampa effettuata solo
     *         se entrambe sono state eseguite correttamente.
     *
     *         Se ha richiesto sia la stampa che il pdf, per prima viene proposta la stampa
     *         se annulla la stampa, non viene generato nemmeno il pdf.
     */
    private RisultatoStampa esegueStampa(PrinterObblig printer) {
        /* variabili e costanti locali di lavoro */
        RisultatoStampa risultato=null;
        boolean riuscito = false;
        boolean continua = true;
        String nomeDir;
        String nomeFile;
        String pathCompleto;

        try {    // prova ad eseguire il codice

            /**
             * presenta l'anteprima - con inserimento parametri
             */
            printer.showPrintPreviewDialog();
            continua = printer.isEseguiStampa();

            /* se richiesto, esegue l'uscita su stampante */
            if (continua) {
                if (printer.isOutputStampante()) {
                    printer.setCrossPlatformDialogs(false);
                    printer.print();
                    riuscito = (!printer.isCanceled());
                    if (!riuscito) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* se richiesto, esegue l'uscita su pdf */
            if (continua) {
                if (printer.isOutputPdf()) {
                    nomeDir = AlbergoPref.Albergo.pathPdfPS.str();
                    nomeFile = this.getNomeFilePdf();
                    pathCompleto = nomeDir + "/" + nomeFile;
                    printer.printToPDF(pathCompleto);
                    riuscito = true;
                }// fine del blocco if
            }// fine del blocco if

            /* valore di ritorno */;
            if (riuscito) {
                if (printer.isPrimaStampa()) {
                    if (printer.isControllo()) {
                        risultato = RisultatoStampa.stampaControlloEffettuata;
                    } else {
                        risultato = RisultatoStampa.stampaDefinitivaEffettuata;
                    }// fine del blocco if-else
                } else {
                    risultato = RisultatoStampa.ristampaEffettuata;
                }// fine del blocco if-else
            } else {
                risultato = RisultatoStampa.stampaFallita;
            }// fine del blocco if-else



        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return risultato;
    }







    /**
     * Cancella .
     * <p/>
     * Filtra i records di testa in base al tipo richiesto (ps, notifica, istat) <br>
     * Ordina i records di testa per data di arrivo <br>
     * Trova l'ultimo record di testa che risulta stampato <br>
     * Recupera i records delle righe correlati al record di testa <br>
     * Cancella i records <br>
     * Segna come non stampato il record di testa <br>
     */
    public void annullaUltimo() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int ultimoRecord;
        MessaggioDialogo messaggio;
        String testo;
        Modulo modTesta;
        Date data = null;

        try { // prova ad eseguire il codice

            ultimoRecord = this.ultimoRecordStampato();
            continua = (ultimoRecord > 0);

            if (continua) {
                modTesta = this.getModTesta();
                data = modTesta.query().valoreData(TestaStampe.Cam.data.get(), ultimoRecord);
            }// fine del blocco if

            /* chiede conferma */
            if (continua) {
                testo = "Confermi l'annullamento della stampa del "
                        + Lib.Data.getStringa(data)
                        + "?";
                messaggio = new MessaggioDialogo(testo);
                continua = messaggio.isConfermato();
            }// fine del blocco if

            /* esegue */
            if (continua) {
                this.annulla();
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Cancella tutti i records di testa a partire
     * dall'ultimo record stampato fino alla fine.
     * <p/>
     * Le corrispondenti righe vengono eliminate per integrità referenziale.
     */
    private void annulla() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Connessione conn = null;
        boolean inTransaction = false;
        int ultimoStampato;
        int[] nonStampati;
        ArrayList<Integer> lista = new ArrayList<Integer>();
        int[] daAnnullare = null;
        Modulo modTesta;

        try { // prova ad eseguire il codice

            modTesta = this.getModTesta();
            continua = (modTesta != null);

            /* crea un array dei record di testa da annullare */
            if (continua) {
                nonStampati = this.getCodiciTestaDaStampare();
                ultimoStampato = this.ultimoRecordStampato();
                lista.add(ultimoStampato);
                for (int cod : nonStampati) {
                    lista.add(cod);
                }
                daAnnullare = new int[lista.size()];
                for (int k = 0; k < lista.size(); k++) {
                    daAnnullare[k] = lista.get(k);
                } // fine del ciclo for
                continua = (daAnnullare.length > 0);
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = Progetto.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                inTransaction = true;
            }// fine del blocco if

            /* effettua le eventualli de-movimentazioni specifiche */
            if (continua) {
                continua = this.demovimenta(daAnnullare, conn);
            } // fine del blocco if

            /**
             * cancella i record di testa
             * cancella le righe per integrità referenziale
             */
            if (continua) {
                for (int cod : daAnnullare) {
                    continua = modTesta.query().eliminaRecord(cod, conn);
                    if (!continua) {
                        break;
                    }// fine del blocco if
                }
            }// fine del blocco if

            /* conclude la transazione */
            if (inTransaction) {
                if (continua) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Effettua le movimentazioni propedeutiche alla prima stampa.
     * <p/>
     *
     * @param codTesta codice del record di testa
     * @param conn     la connessione da utilizzare
     *
     * @return true se riuscito
     */
    protected boolean movimenta(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Modulo modTesta = null;

        try {    // prova ad eseguire il codice

            /* assegna i progressivi alle righe */
            continua = this.assegnaProgressivi(codTesta, conn);

            if (continua) {
                modTesta = this.getModTesta();
                continua = (modTesta != null);
            } // fine del blocco if

            /* Segna come stampato il record di testa */
            if (continua) {
                continua = modTesta.query()
                        .registra(codTesta, TestaStampe.Cam.stampato.get(), true, conn);
            } // fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Effettua le de-movimentazioni conseguenti all'annullamento.
     * <p/>
     *
     * @param codiciTesta elenco dei codici record di testa che verranno cancellati
     * @param conn        la connessione da utilizzare
     *
     * @return true se riuscito
     */
    protected boolean demovimenta(int[] codiciTesta, Connessione conn) {
        return true;
    }


    /**
     * Crea il printer relativo a un giorno di stampa.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param codTesta codice del record di testa
     * @param conn     la connessione da utilizzare
     * @param primaStampa true per prima stampa false per ristampa
     *
     * @return il printer creato
     */
    protected PrinterObblig creaPrinter(int codTesta, Connessione conn, boolean primaStampa) {
        return null;
    }


    /**
     * Ritorna il nome del file pdf (senza path)
     * corrispondente al giorno corrente.
     * <p/>
     *
     * @return il nome del file
     */
    public String getNomeFilePdf() {
        /* variabili e costanti locali di lavoro */
        String nomeFile = "";
        Date dataArrivi;
        String stringaData;
        int anno;
        int mese;
        int giorno;
        String sAnno;
        String sMese;
        String sGiorno;

        try {    // prova ad eseguire il codice
            dataArrivi = this.getDataStampaCorrente();
            anno = Lib.Data.getAnno(dataArrivi);
            mese = Lib.Data.getNumeroMese(dataArrivi);
            giorno = Lib.Data.getNumeroGiorno(dataArrivi);

            sAnno = Lib.Testo.getStringa(anno);
            sAnno = Lib.Testo.pad(sAnno, '0', 4, LibTesto.Posizione.inizio);
            sMese = Lib.Testo.getStringa(mese);
            sMese = Lib.Testo.pad(sMese, '0', 2, LibTesto.Posizione.inizio);
            sGiorno = Lib.Testo.getStringa(giorno);
            sGiorno = Lib.Testo.pad(sGiorno, '0', 2, LibTesto.Posizione.inizio);

            stringaData = sAnno + sMese + sGiorno;
            nomeFile = stringaData + "_" + this.getBaseFilePdf() + ".pdf";

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nomeFile;
    }


    /**
     * Ritorna la parte di base per il nome del file pdf.
     * <p/>
     *
     * @return il nome base del file .pdf
     */
    protected String getBaseFilePdf() {
        return "BASE";
    }


    /**
     * Assegna i numeri progressivi alle righe.
     * <p/>
     * Opera a seconda del tipo richiesto (ps, notifica, istat) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param codTesta codice del record di testa per il quale effettuare le regolazioni <br>
     * @param conn     la connessione da utilizzare
     *
     * @return true se riuscito
     */
    protected boolean assegnaProgressivi(int codTesta, Connessione conn) {
        return true;
    }


    /**
     * Restituisce l'ultimo giorno stampato.
     * <p/>
     * Filtra i records di testa in base al tipo richiesto (ps, notifica, istat) <br>
     * Ordina i records di testa per data di arrivo <br>
     * Ricerca l'ultimo record col flag stampato uguale a vero <br>
     * Restituisce la data corrispondente <br>
     *
     * @return ultimo giorno stampato     
     */
    public Date ultimoGiornoStampato() {
        /* variabili e costanti locali di lavoro */
        Date ultimoGiorno = null;
        boolean continua;
        int codice = 0;
        Modulo modTesta;

        try { // prova ad eseguire il codice
            modTesta = this.getModTesta();
            continua = (modTesta != null);

            if (continua) {
                codice = this.ultimoRecordStampato();
                continua = (codice > 0);
            } // fine del blocco if

            if (continua) {
                ultimoGiorno = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codice);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ultimoGiorno;
    }


    /**
     * Restituisce l'ultimo record stampato.
     * <p/>
     * Filtra i records di testa in base al tipo richiesto (ps, notifica, istat) <br>
     * Ordina i records di testa per data di arrivo <br>
     * Ricerca l'ultimo record col flag stampato uguale a vero <br>
     * Restituisce il codice corrispondente <br>
     *
     * @return ultimo record stampato     
     */
    private int ultimoRecordStampato() {
        /* variabili e costanti locali di lavoro */
        int ultimoRecord = 0;
        boolean continua;
        int[] codici;

        try { // prova ad eseguire il codice
            codici = this.getCodiciTestaStampati();
            continua = (codici != null) && (codici.length > 0);

            if (continua) {
                ultimoRecord = codici[codici.length - 1];
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ultimoRecord;
    }


    /**
     * Restituisce i codici dei records di testa stampati/da stampare.
     * <p/>
     * Costruisce il filtro vero/falso sul flag di stampa, in funzione del parametro <br>
     * Filtra i records di testa in base al tipo richiesto (ps, notifica, istat) <br>
     * Ordina i records di testa per data di arrivo <br>
     *
     * @param stampati flag per costruire il filtro vero/falso
     *
     * @return codici dei records     
     */
    private int[] getCodiciTesta(boolean stampati) {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        boolean continua;
        Modulo modTesta;
        Ordine ordine;
        Campo campoArrivo = null;
        Filtro filtro;

        try { // prova ad eseguire il codice
            modTesta = this.getModTesta();
            continua = (modTesta != null);

            if (continua) {
                campoArrivo = TestaStampeModulo.get().getCampo(TestaStampe.Cam.data);
                continua = (campoArrivo != null);
            } // fine del blocco if

            if (continua) {
                ordine = new Ordine(campoArrivo);

                /* filtro per il tipo di record (ps, notifica, istat) */
                filtro = this.getFiltroTesta();

                if (stampati) {
                    filtro.add(FiltroFactory.creaVero(TestaStampe.Cam.stampato.get()));
                } else {
                    filtro.add(FiltroFactory.creaFalso(TestaStampe.Cam.stampato.get()));
                } // fine del blocco if-else

                codici = modTesta.query().valoriChiave(filtro, ordine);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Restituisce i codici dei records di testa già stampati.
     * <p/>
     * Filtra i records di testa in base al tipo richiesto (ps, notifica, istat) <br>
     * Ordina i records di testa per data di arrivo <br>
     *
     * @return codici dei records stampati     
     */
    private int[] getCodiciTestaStampati() {
        /* invoca il metodo delegato della classe */
        return this.getCodiciTesta(true);
    }


    /**
     * Restituisce i codici dei records di testa da stampare.
     * <p/>
     * Filtra i records di testa in base al tipo richiesto (ps, notifica, istat) <br>
     * Ordina i records di testa per data di arrivo <br>
     *
     * @return codici dei records da stampare     
     */
    private int[] getCodiciTestaDaStampare() {
        /* invoca il metodo delegato della classe */
        return this.getCodiciTesta(false);
    }


    /**
     * Restituisce i codici delle righe corispondenti al codice di testa.
     * <p/>
     * Filtra i records di testa in base al tipo richiesto (ps, notifica, istat) <br>
     *
     * @param codTesta codice chiave del record di testa
     * @param conn     la connessione da utilizzare
     *
     * @return codici delle righe
     */
    protected int[] getCodiciRighe(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        boolean continua;
        Modulo mod = null;
        String nomeCampoLink = "";

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (codTesta > 0);

            if (continua) {
                mod = this.getModRighe();
//                mod = NotificaModulo.get();
                continua = (mod != null);
            } // fine del blocco if

            if (continua) {
                nomeCampoLink = this.getNomeCampoLinkTesta();
                continua = Lib.Testo.isValida(nomeCampoLink);
            }// fine del blocco if

            if (continua) {
                codici = mod.query().valoriChiave(nomeCampoLink, codTesta, conn);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Ritorna il nome del campo linkato al record di testa
     * specifico di questa logica.
     * <p/>
     * Sovrascritto dalle sottoclassi specifiche
     *
     * @return il nome del campo link
     */
    protected String getNomeCampoLinkTesta() {
        return null;
    }


    protected int getRecordTesta(Date giorno) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Filtro filtro;
        Modulo modTesta;

        try { // prova ad eseguire il codice

            modTesta = this.getModTesta();

            if (modTesta != null) {
                filtro = FiltroFactory.crea(TestaStampe.Cam.tipo.get(), this.getCodTipoRecord());
                filtro.add(FiltroFactory.crea(TestaStampe.Cam.data.get(), giorno));
                filtro.add(FiltroFactory.crea(TestaStampe.Cam.azienda.get(), this.getCodAzienda()));
                codice = modTesta.query().valoreChiave(filtro);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    protected Date getGiornoTesta(int codTesta) {
        /* variabili e costanti locali di lavoro */
        Date giorno = null;
        Modulo modTesta;

        try { // prova ad eseguire il codice

            modTesta = this.getModTesta();

            if (modTesta != null) {
                giorno = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return giorno;
    }


    /**
     * Restituisce il primo giorno non ancora stampato.
     * <p/>
     * Filtra i records di testa in base al tipo richiesto (ps, notifica, istat) <br>
     * Ordina i records di testa per data di arrivo <br>
     * Ricerca il primo record col flag stampato uguale a falso <br>
     * Restituisce la data corrispondente <br>
     *
     * @return primo giorno da stampare     
     */
    public Date primoGiornoDaStampare() {
        /* variabili e costanti locali di lavoro */
        Date data = Lib.Data.getVuota();
        boolean continua;
        Modulo modTesta;
        int codice = 0;

        try { // prova ad eseguire il codice
            modTesta = TestaStampeModulo.get();
            continua = (modTesta != null);

            if (continua) {
                codice = this.primoRecordDaStampare();
                continua = (codice > 0);
            } // fine del blocco if

            if (continua) {
                data = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codice);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Restituisce il primo record non ancora stampato.
     * <p/>
     * Filtra i records di testa in base al tipo richiesto (ps, notifica, istat) <br>
     * Ordina i records di testa per data di arrivo <br>
     * Ricerca il primo record col flag stampato uguale a falso <br>
     * Restituisce il codice corrispondente <br>
     *
     * @return primo record da stampare     
     */
    public int primoRecordDaStampare() {
        /* variabili e costanti locali di lavoro */
        int primoRecord = 0;
        boolean continua;
        int[] codici;

        try { // prova ad eseguire il codice
            codici = this.getCodiciTestaDaStampare();
            continua = (codici != null) && (codici.length > 0);

            if (continua) {
                primoRecord = codici[0];
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return primoRecord;
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
    protected WrapRisposta chkStampabile(int codTesta) {
        return new WrapRisposta();
    }


    /**
     * Ritorna il codice del tipo di record di testa.
     * <p/>
     *
     * @return il codice del tipo di record di testa
     */
    protected int getCodTipoRecord() {
        return -1;
    }


    /**
     * Ritorna un filtro che isola i record di testa del
     * tipo corrente e della azienda corrente
     * <p/>
     *
     * @return il filtro creato
     */
    protected Filtro getFiltroTesta() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Modulo modTesta;
        Campo campoTipo;
        Campo campoAzienda;

        try { // prova ad eseguire il codice

            /**
             * uso gli oggetti campo anziché i nomi così funziona
             * anche se viene applicato a query di altri moduli
             */
            modTesta = this.getModTesta();
            campoTipo = modTesta.getCampo(TestaStampe.Cam.tipo);
            campoAzienda = modTesta.getCampo(TestaStampe.Cam.azienda);

            filtro = new Filtro();
            filtro.add(FiltroFactory.crea(campoTipo, this.getCodTipoRecord()));
            filtro.add(FiltroFactory.crea(campoAzienda, this.getCodAzienda()));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;

    }


    /**
     * Ritorna un filtro che isola i record di testa del
     * tipo corrente e della azienda corrente e di un dato anno
     * <p/>
     * @param anno l'anno di riferimento
     * @return il filtro creato
     */
    protected Filtro getFiltroTestaAnno(int anno) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroTesta;
        Date primoGennaio;
        Date trentunoDic;
        Campo campoData;
        Filtro filtroAnno;

        try { // prova ad eseguire il codice

            primoGennaio = Lib.Data.getPrimoGennaio(anno);
            trentunoDic = Lib.Data.getTrentunoDicembre(anno);
            campoData = TestaStampeModulo.get().getCampo(TestaStampe.Cam.data);
            filtroAnno = new Filtro();
            filtroAnno.add(FiltroFactory.crea(campoData,Filtro.Op.MAGGIORE_UGUALE,primoGennaio));
            filtroAnno.add(FiltroFactory.crea(campoData,Filtro.Op.MINORE_UGUALE,trentunoDic));

            filtroTesta = this.getFiltroTesta();

            filtro = new Filtro();
            filtro.add(filtroTesta);
            filtro.add(filtroAnno);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;

    }



    /**
     * Ritorna il modulo di testa.
     * <p/>
     *
     * @return il modulo di testa
     */
    protected Modulo getModTesta() {
        return TestaStampeModulo.get();
    }


    /**
     * Ritorna il modulo delle righe.
     * <p/>
     *
     * @return il modulo delle righe
     */
    protected Modulo getModRighe() {
        return null;
    }


    /**
     * Ritorna il codice della azienda di riferimento.
     * <p/>
     *
     * @return il codice della azienda di riferimento
     */
    protected int getCodAzienda() {
        return this.getDialogo().getCodAzienda();
    }


    /**
     * Ritorna il dialogo di riferimento.
     * <p/>
     *
     * @return il dialogo di riferimento
     */
    private StampeObbligatorieDialogo getDialogo() {
        /* variabili e costanti locali di lavoro */
        StampeObbligatorieDialogo dialogo = null;
        PannelloObbligatorie pan;

        try {    // prova ad eseguire il codice
            pan = this.getPanObbligatorie();
            dialogo = pan.getDialogo();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dialogo;
    }


    private PannelloObbligatorie getPanObbligatorie() {
        return panObbligatorie;
    }


    private void setPanObbligatorie(PannelloObbligatorie panObbligatorie) {
        this.panObbligatorie = panObbligatorie;
    }


    private Date getDataStampaCorrente() {
        return dataStampaCorrente;
    }


    private void setDataStampaCorrente(Date dataStampaCorrente) {
        this.dataStampaCorrente = dataStampaCorrente;
    }


    /**
     * Contrassegna tutti i records di testa non stampati
     * come già stampati.
     * <p/>
     * Non lo fa per quello del giorno corrente
     */
    public void setStampato() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int[] daStampare = null;
        Date data;

        try {    // prova ad eseguire il codice

            daStampare = this.getCodiciTestaDaStampare();
            continua = (daStampare.length > 0);

            if (continua) {
                Modulo modTesta = this.getModTesta();
                for (int cod : daStampare) {
                    data = modTesta.query().valoreData(TestaStampe.Cam.data.get(), cod);
                    if (!data.equals(AlbergoLib.getDataProgramma())) {
                        this.movimenta(cod, Progetto.getConnessione());
                    }// fine del blocco if
                }
            }// fine del blocco if

            this.getPanObbligatorie().getNavMaster().aggiornaLista();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Verifica se è possibile registrare/annullare un arrivo
     * per la data e l'azienda specificate.
     * <p/>
     *
     * Non devono essere stati stampati PS, Notifiche e ISTAT
     * per la data in esame e tutte le successive
     * @param data       data da controllare
     * @param codAzienda codice dell'azienda
     * @return true se è possibile, false se non è possibile
     */
    public static boolean isArrivoRegistrabile(Date data, int codAzienda) {
        /* variabili e costanti locali di lavoro */
        boolean possibile = false;

        try {    // prova ad eseguire il codice
            possibile = !isPSStampato(data, codAzienda);
            if (possibile) {
                possibile = !isNotificaStampato(data, codAzienda);
            }// fine del blocco if
            if (possibile) {
                possibile = !isISTATStampato(data, codAzienda);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return possibile;
    }


    /**
     * Verifica se è possibile registrare/annullare una partenza
     * per la data e l'azienda specificate.
     * <p/>
     * <p/>
     * Non deve essere stato stampato l'ISTAT
     * per la data in esame e tutte le successive
     * @param data       data da controllare
     * @param codAzienda codice dell'azienda
     * @return true se è possibile, false se non è possibile
     */
    public static boolean isPartenzaRegistrabile(Date data, int codAzienda) {
        /* variabili e costanti locali di lavoro */
        boolean possibile = false;

        try {    // prova ad eseguire il codice
            possibile = !isISTATStampato(data, codAzienda);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return possibile;
    }


    /**
     * Controlla se il registro di PS è già stato stampato
     * per una certa data e azienda.
     * <p/>
     *
     * @param data       data da controllare
     * @param codAzienda codice dell'azienda
     *
     * @return true se il registro di PS è già stato stampato
     */
    private static boolean isPSStampato(Date data, int codAzienda) {
        return isStampato(TestaStampe.TipoRegistro.ps, data, codAzienda);
    }

    /**
     * Controlla se le schede di notifica sono già state stampate
     * per una certa data e azienda.
     * <p/>
     *
     * @param data       data da controllare
     * @param codAzienda codice dell'azienda
     *
     * @return true se le schede di notifica sono già state stampate
     */
    private static boolean isNotificaStampato(Date data, int codAzienda) {
        return isStampato(TestaStampe.TipoRegistro.notifica, data, codAzienda);
    }


    /**
     * Controlla se il modulo ISTAT è già stato stampato
     * per una certa data e azienda.
     * <p/>
     *
     * @param data       data da controllare
     * @param codAzienda codice dell'azienda
     *
     * @return true se se il modulo ISTAT è già stato stampato
     */
    private static boolean isISTATStampato(Date data, int codAzienda) {
        return isStampato(TestaStampe.TipoRegistro.istat, data, codAzienda);
    }




    /**
     * Controlla se un tipo di registro è già stato stampato
     * per una certa data e azienda.
     * <p/>
     *
     * @param tipo       tipo di registro
     * @param data       data da controllare
     * @param codAzienda codice dell'azienda
     *
     * @return true se il registro di PS è già stato stampato
     */
    private static boolean isStampato(TestaStampe.TipoRegistro tipo, Date data, int codAzienda) {
        /* variabili e costanti locali di lavoro */
        boolean stampato = false;
        Date ultData;

        try {    // prova ad eseguire il codice
            ultData = getDataUltimaStampaChiusa(tipo, codAzienda);
            if (Lib.Data.isPosterioreUguale(data, ultData)) {
                stampato = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stampato;
    }


    /**
     * Ritorna la data dell'ultima stampa chiusa per un tipo di registro e una data azienda
     * <p/>
     *
     * @param tipo       tipo di registro
     * @param codAzienda codice dell'azienda
     *
     * @return la data dell'ultima stampa chiusa,
     *         data vuota se non ci sono stampe chiuse
     */
    public static Date getDataUltimaStampaChiusa(TestaStampe.TipoRegistro tipo, int codAzienda) {
        /* variabili e costanti locali di lavoro */
        Date ultData = Lib.Data.getVuota();
        Modulo modTesta;
        Filtro filtro;
        Ordine ordine;
        int codTipo;
        ArrayList valori;
        Object ogg;

        try {    // prova ad eseguire il codice

            modTesta = TestaStampeModulo.get();
            codTipo = tipo.getCodice();

            /* filtro per tutti i record stampati per il registro e l'azienda richiesti */
            filtro = new Filtro();
            filtro.add(TestaStampe.Cam.tipo.get(), Filtro.Op.UGUALE, codTipo);
            filtro.add(TestaStampe.Cam.azienda.get(), Filtro.Op.UGUALE, codAzienda);
            filtro.add(TestaStampe.Cam.stampato.get(), Filtro.Op.UGUALE, true);

            /* ordine per data del registro, prima la più recente */
            ordine = new Ordine();
            ordine.add(TestaStampe.Cam.data.get(), Operatore.DISCENDENTE);

            valori = modTesta.query().valoriCampo(TestaStampe.Cam.data.get(), filtro, ordine);
            if (valori.size() > 0) {
                ogg = valori.get(0);
                ultData = Libreria.getDate(ogg);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ultData;
    }


    /**
     * Codifica delle risposte di stampa.
     */
    private enum RisultatoStampa {

        stampaControlloEffettuata,
        stampaDefinitivaEffettuata,
        ristampaEffettuata,
        stampaFallita;

    }// fine della classe




}
