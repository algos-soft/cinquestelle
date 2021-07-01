package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodoModulo;
import it.algos.albergo.prenotazione.periodo.periodorisorsa.RisorsaPeriodoModulo;
import it.algos.albergo.prenotazione.periodo.serviziospecifico.ServizioSpecificoModulo;
import it.algos.albergo.prenotazione.periodo.serviziperiodo.ServizioPeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;

import java.util.Date;

/**
 * Periodi di una prenotazione alberghiera.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 16-5-2007
 */
public final class PeriodoModulo extends ModuloBase implements Periodo {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Periodo.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Periodo.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Periodo.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public PeriodoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public PeriodoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* selezione del modello (obbligatorio) */
        super.setModello(new PeriodoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

//        this.setUsaTransazioni(true);

    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new CameraModulo());
            super.creaModulo(new AddebitoPeriodoModulo());
            super.creaModulo(new ServizioPeriodoModulo());
            super.creaModulo(new ServizioSpecificoModulo());
            super.creaModulo(new RisorsaPeriodoModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Anche eventuale creazione <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice
            super.creaNavigatori();

            nav = new PeriodoNavPrenotazione(this);
            this.addNavigatoreCorrente(nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea eventuali azioni specifiche di questo modulo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Le azioni vengono aggiunte al navigatore corrente <br>
     * Le azioni vengono aggiunte alla toolbar della lista <br>
     */
    @Override
    protected void creaAzioni() {
        super.creaAzioni();
    }


//    /**
//     * Ritorna un elenco di celle di periodo corrispondente
//     * a un intervallo di date fornito
//     * <p/>
//     *
//     * @param d1 data iniziale
//     * @param d2 data finale
//     *
//     * @return la lista delle celle
//     */
//    public ArrayList<CellPeriodo> getCellePeriodoOld(Date d1, Date d2) {
//        /* variabili e costanti locali di lavoro */
//        ArrayList<CellPeriodo> listaCelle = new ArrayList<CellPeriodo>();
//        Filtro filtro;
//        Filtro filtroDate;
//        Filtro filtroAzienda;
//        Filtro filtroNoDisdettte;
//        Query query;
//        Dati dati;
//        CellPeriodo cella;
//
//        String cliente;
//        String agenzia;
//        int camera;
//        int camProvenienza;
//        int camDestinazione;
//        Date dataInizio;
//        Date dataFine;
//        boolean confermata;
//        Date scadenza;
//        int numAd;
//        int numBa;
//        String trattamento;
//        String preparazione;
//        int codPeriodo;
//
//        int codProvenienza, codDestinazione;
//        boolean arrivato, partito;
//        int codTrattamento;
//
//        try { // prova ad eseguire il codice
//
//            Modulo modCliente = ClienteAlbergoModulo.get();
//            Modulo modPrenotazione = PrenotazioneModulo.get();
//            Modulo modCompoCamera = CompoCameraModulo.get();
//
//
//            Campo campoNomeCliente = modCliente.getCampo(Anagrafica.Cam.soggetto);
//            Campo campoCamera = this.getCampo(Periodo.Cam.camera);
//            Campo campoLinkProv = this.getCampo(Periodo.Cam.linkProvenienza);
//            Campo campoLinkDest = this.getCampo(Periodo.Cam.linkDestinazione);
//            Campo campoDataInizio = this.getCampo(Periodo.Cam.arrivoPrevisto);
//            Campo campoDataFine = this.getCampo(Periodo.Cam.partenzaPrevista);
//            Campo campoConfermata = modPrenotazione.getCampo(Prenotazione.Cam.confermata);
//            Campo campoScadenza = modPrenotazione.getCampo(Prenotazione.Cam.dataScadenza);
//            Campo campoArrivato = this.getCampo(Periodo.Cam.arrivato);
//            Campo campoPartito = this.getCampo(Periodo.Cam.partito);
//            Campo campoNumAd = this.getCampo(Periodo.Cam.adulti);
//            Campo campoNumBa = this.getCampo(Periodo.Cam.bambini);
//            Campo campoCodTratt = this.getCampo(Periodo.Cam.pensione);
//            Campo campoPrep = modCompoCamera.getCampo(CompoCamera.Cam.sigla);
//            Campo campoChiave = this.getCampoChiave();
//
//            /* crea il filtro dei periodi interessati */
//            filtroDate = PeriodoModulo.getFiltroInteressati(d1, d2);
//            filtroAzienda = PrenotazioneModulo.get().getFiltroAzienda();
//            Campo campoDisd = modPrenotazione.getCampo(Prenotazione.Cam.annullata);
//            filtroNoDisdettte = FiltroFactory.crea(campoDisd, false);
//            filtro = new Filtro();
//            filtro.add(filtroDate);
//            filtro.add(filtroAzienda);
//            filtro.add(filtroNoDisdettte);
//
//            /* crea ed esegue la query */
//            query = new QuerySelezione(this);
//            query.addCampo(campoNomeCliente);
//            query.addCampo(campoCamera);
//            query.addCampo(campoLinkProv);
//            query.addCampo(campoLinkDest);
//            query.addCampo(campoDataInizio);
//            query.addCampo(campoDataFine);
//            query.addCampo(campoConfermata);
//            query.addCampo(campoScadenza);
//            query.addCampo(campoArrivato);
//            query.addCampo(campoPartito);
//            query.addCampo(campoNumAd);
//            query.addCampo(campoNumBa);
//            query.addCampo(campoCodTratt);
//            query.addCampo(campoPrep);
//            query.addCampo(campoChiave);
//            query.setFiltro(filtro);
//            dati = this.query().querySelezione(query);
//
//            /* spazzola i periodi e crea le celle */
//            for (int k = 0; k < dati.getRowCount(); k++) {
//
//                cliente = dati.getStringAt(k, campoNomeCliente);
//                agenzia = "";
//                camera = dati.getIntAt(k, campoCamera);
//                codProvenienza = dati.getIntAt(k, campoLinkProv);
//                codDestinazione = dati.getIntAt(k, campoLinkDest);
//                dataInizio = dati.getDataAt(k, campoDataInizio);
//                dataFine = dati.getDataAt(k, campoDataFine);
//                confermata = dati.getBoolAt(k, campoConfermata);
//                scadenza = dati.getDataAt(k, campoScadenza);
//                arrivato = dati.getBoolAt(k, campoArrivato);
//                partito = dati.getBoolAt(k, campoPartito);
//                numAd = dati.getIntAt(k, campoNumAd);
//                numBa = dati.getIntAt(k, campoNumBa);
//                codTrattamento = dati.getIntAt(k, campoCodTratt);
//                preparazione = dati.getStringAt(k, campoPrep);
//                codPeriodo = dati.getIntAt(k, campoChiave);
//
//                /* recupera eventuale camera provenienza */
//                camProvenienza = 0;
//                if (codProvenienza != 0) {
//                    camProvenienza = this.query().valoreInt(Periodo.Cam.camera.get(), codProvenienza);
//                }// fine del blocco if
//
//                /* recupera eventuale camera destinazione */
//                camDestinazione = 0;
//                if (codDestinazione != 0) {
//                    camDestinazione = this.query().valoreInt(Periodo.Cam.camera.get(), codDestinazione);
//                }// fine del blocco if
//
//                /* stringa trattamento */
//                trattamento = Listino.PensioniPeriodo.getSigla(codTrattamento);
//
//                /* crea la cella */
//                cella = new CellPeriodo(
//                        cliente, agenzia, camera, camProvenienza, camDestinazione,
//                        dataInizio,
//                        dataFine,
//                        confermata,
//                        scadenza,
//                        arrivato,
//                        partito,
//                        numAd,
//                        numBa,
//                        trattamento,
//                        preparazione,
//                        codPeriodo);
//
//                /* aggiunge alla lista */
//                listaCelle.add(cella);
//
//            } // fine del ciclo for
//            dati.close();
//
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return listaCelle;
//    }






    /**
     * Apre per modifica la prenotazione relativa a un periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     * @return true se la prenotazione è stata modificata
     */
    public static boolean apriPrenotazione(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean registrato = false;
        int codPrenotazione;
        Modulo modPren;

        try { // prova ad eseguire il codice
            codPrenotazione = PeriodoModulo.get().query().valoreInt(Periodo.Cam.prenotazione.get(), codPeriodo);
            modPren = PrenotazioneModulo.get();
            registrato = modPren.presentaRecord(codPrenotazione);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return registrato;
    }




    /**
     * Ritorna il codice cliente relativo a un periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     * @return il codice del relativo cliente
     */
    public static int getCodCliente(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        int codCliente = 0;
        int codPren;
        Modulo modPren;

        try { // prova ad eseguire il codice
            codPren = PeriodoModulo.get().query().valoreInt(Periodo.Cam.prenotazione.get(), codPeriodo);
            modPren = PrenotazioneModulo.get();
            codCliente = modPren.query().valoreInt(Prenotazione.Cam.cliente.get(), codPren);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return codCliente;
    }


    /**
     * Ritorna il numero di persone in arrivo o arrivate
     * relativamente a un elenco di periodi.
     * <p/>
     * Se il periodo non è IN ritorna il numero di persone previste
     * Se il periodo è IN ritorna il numero di persone effettivamente arrivate
     *
     * @param codPeriodi elenco dei periodi
     * @return il numero di persone in arrivo/arrivate
     */
    public static int getNumPersoneArrivo(int[] codPeriodi) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;

        try { // prova ad eseguire il codice
            for (int cod : codPeriodi) {
                quanti += getNumPersoneArrivo(cod, TipoPersona.persona);
            } // fine del ciclo for-each
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;

    }


    /**
     * Ritorna il numero di persone in arrivo o arrivate
     * relativamente a un periodo.
     * <p/>
     * Se il periodo non è IN ritorna il numero di persone previste
     * Se il periodo è IN ritorna il numero di persone effettivamente arrivate
     *
     * @param codPeriodo il codice del periodo
     * @return il numero di persone in arrivo/arrivate
     */
    public static int getNumPersoneArrivo(int codPeriodo) {
        return getNumPersoneArrivo(codPeriodo, TipoPersona.persona);
    }


    /**
     * Ritorna il numero di adulti in arrivo o arrivati
     * relativamente a un periodo.
     * <p/>
     * Se il periodo non è IN ritorna il numero di adulti previste
     * Se il periodo è IN ritorna il numero di adulti effettivamente arrivati
     *
     * @param codPeriodo il codice del periodo
     * @return il numero di adulti in arrivo/arrivati
     */
    public static int getNumAdultiArrivo(int codPeriodo) {
        return getNumPersoneArrivo(codPeriodo, TipoPersona.adulto);
    }


    /**
     * Ritorna il numero di bambini in arrivo o arrivati
     * relativamente a un periodo.
     * <p/>
     * Se il periodo non è IN ritorna il numero di bambini previsti
     * Se il periodo è IN ritorna il numero di bambini effettivamente arrivati
     *
     * @param codPeriodo il codice del periodo
     * @return il numero di bambini in arrivo/arrivati
     */
    public static int getNumBambiniArrivo(int codPeriodo) {
        return getNumPersoneArrivo(codPeriodo, TipoPersona.bambino);
    }


    /**
     * Ritorna il numero di persone in partenza o partite relativamente a un elenco di periodi.
     * <p/>
     * Se il periodo non è IN ritorna il numero di persone previste
     * Se il periodo è IN ritorna il numero di persone effettivamente partite
     *
     * @param codPeriodi elenco dei periodi
     * @return il numero di persone in partenza/partite
     */
    public static int getNumPersonePartenza(int[] codPeriodi) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;

        try { // prova ad eseguire il codice
            for (int cod : codPeriodi) {
                quanti += getNumPersonePartenza(cod);
            } // fine del ciclo for-each
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;

    }


    /**
     * Ritorna il numero di persone in partenza o partite
     * relativamente a un periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     * @return il numero di persone in partenza/partite
     */
    public static int getNumPersonePartenza(int codPeriodo) {
        return getNumPersonePartenza(codPeriodo, TipoPersona.persona);
    }


    /**
     * Ritorna il numero di adulti in partenza o partiti
     * relativamente a un periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     * @return il numero di adulti in partenza/partiti
     */
    public static int getNumAdultiPartenza(int codPeriodo) {
        return getNumPersonePartenza(codPeriodo, TipoPersona.adulto);
    }


    /**
     * Ritorna il numero di bambini in partenza o partiti
     * relativamente a un periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     * @return il numero di bambini in partenza/partiti
     */
    public static int getNumBambiniPartenza(int codPeriodo) {
        return getNumPersonePartenza(codPeriodo, TipoPersona.bambino);
    }


    /**
     * Ritorna il numero di persone in arrivo o arrivate
     * relativamente a un periodo.
     * <p/>
     * Se il periodo non è IN ritorna il numero di persone previste
     * Se il periodo è IN ritorna il numero di persone effettivamente arrivate
     *
     * @param codPeriodo il codice del periodo
     * @param tipo       per selezionare persone / adulti / bambini
     * @return il numero di persone in arrivo/arrivate
     */
    private static int getNumPersoneArrivo(int codPeriodo, TipoPersona tipo) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        boolean arrivato;
        Filtro filtro;
        Modulo modPeriodo;
        Modulo modPresenza;
        String nomeCampo = "";

        try { // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            modPresenza = PresenzaModulo.get();

            arrivato = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codPeriodo);

            if (arrivato) {
                filtro = getFiltroPresenzeArrivo(codPeriodo);
                switch (tipo) {
                    case persona:
                        break;
                    case adulto:
                        filtro.add(FiltroFactory.creaFalso(Presenza.Cam.bambino));
                        break;
                    case bambino:
                        filtro.add(FiltroFactory.creaVero(Presenza.Cam.bambino));
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
                quanti = modPresenza.query().contaRecords(filtro);


            } else {   // non arrivato
                switch (tipo) {
                    case persona:
                        nomeCampo = Periodo.Cam.persone.get();
                        break;
                    case adulto:
                        nomeCampo = Periodo.Cam.adulti.get();
                        break;
                    case bambino:
                        nomeCampo = Periodo.Cam.bambini.get();
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
                quanti = modPeriodo.query().valoreInt(nomeCampo, codPeriodo);
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;

    }


    /**
     * Ritorna un filtro che seleziona le presenze arrivate
     * relativamente a un periodo già arrivato.
     * <p/>
     * Ha senso solo se il periodo è già arrivato.
     *
     * @param codPeriodo codice del periodo
     * @return l'elenco dei codici delle presenza in partenza/partite
     */
    public static Filtro getFiltroPresenzeArrivo(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Modulo modPeriodo;
        int camera;
        Date dataArrivo;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            camera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
            dataArrivo = modPeriodo.query().valoreData(Periodo.Cam.arrivoEffettivo.get(), codPeriodo);
            filtro = PresenzaModulo.getFiltroPresenzeArrivate(dataArrivo);
            filtro.add(PresenzaModulo.getFiltroCamera(camera));
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna il numero di persone in partenza o partite
     * relativamente a un periodo.
     * <p/>
     * Se il periodo non è IN ritorna il numero di persone previste
     * Se il periodo è IN ma non è OUT ritorna il numero di persone
     * presenti nella camera e non ancora partite (presenze attuali)
     * Se il periodo è IN e anche OUT ritorna il numero di persone
     * partite dalla camera nella data della partenza effettiva
     *
     * @param codPeriodo il codice del periodo
     * @param tipo       per selezionare persone / adulti / bambini
     * @return il numero di persone in partenza/partite
     */
    public static int getNumPersonePartenza(int codPeriodo, TipoPersona tipo) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        boolean arrivato;
        Filtro filtro;
        Modulo modPeriodo;
        Modulo modPresenza;
        String nomeCampo = null;

        try { // prova ad eseguire il codice

            /* se è arrivato legge il valore dalle presenze */
            modPeriodo = PeriodoModulo.get();
            modPresenza = PresenzaModulo.get();

            arrivato = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codPeriodo);

            if (arrivato) {
                filtro = getFiltroPresenzePartenza(codPeriodo);
                switch (tipo) {
                    case persona:
                        break;
                    case adulto:
                        filtro.add(FiltroFactory.creaFalso(Presenza.Cam.bambino));
                        break;
                    case bambino:
                        filtro.add(FiltroFactory.creaVero(Presenza.Cam.bambino));
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
                quanti = modPresenza.query().contaRecords(filtro);


            } else {   // non arrivato

                switch (tipo) {
                    case persona:
                        nomeCampo = Periodo.Cam.persone.get();
                        break;
                    case adulto:
                        nomeCampo = Periodo.Cam.adulti.get();
                        break;
                    case bambino:
                        nomeCampo = Periodo.Cam.bambini.get();
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
                quanti = modPeriodo.query().valoreInt(nomeCampo, codPeriodo);

            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;

    }


    /**
     * Ritorna un filtro che seleziona le presenze in partenza o partite
     * relativamente a un periodo già arrivato.
     * <p/>
     * Ha senso solo se il periodo è già arrivato.
     *
     * @param codPeriodo codice del periodo
     * @return l'elenco dei codici delle presenza in partenza/partite
     */
    public static Filtro getFiltroPresenzePartenza(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Modulo modPeriodo;
        boolean partito;
        int camera;
        Date dataPartenza;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            partito = modPeriodo.query().valoreBool(Periodo.Cam.partito.get(), codPeriodo);
            camera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
            if (partito) {
                dataPartenza = modPeriodo.query().valoreData(Periodo.Cam.partenzaEffettiva.get(), codPeriodo);
                filtro = PresenzaModulo.getFiltroPresenzePartite(dataPartenza);
                filtro.add(PresenzaModulo.getFiltroCamera(camera));
            } else {
                filtro = PresenzaModulo.getFiltroAperteSmart(camera);
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna il numero di persone in cambio o cambiate
     * relativamente a un periodo.
     * <p/>
     * Se il periodo non è IN ritorna il numero di persone previste
     * Se il periodo è IN ma non è OUT fa vedere il numero di persone
     * presenti nella camera e non ancora partite (presenze attuali)
     * Se il periodo è IN e anche OUT fa vedere il numero di persone
     * partite con cambio dalla camera nella data della partenza effettiva
     *
     * @param codPeriodo il codice del periodo
     * @param tipo       per selezionare persone / adulti / bambini
     * @return il numero di persone in cambio/cambiate
     */
    public static int getNumPersoneCambio(int codPeriodo, TipoPersona tipo) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        boolean entrato;
        boolean uscito;
        Date dataPartenza;
        int codCamera;
        Filtro filtro;
        Modulo modPresenza;
        Modulo modPeriodo;
        String nomeCampo = "";

        try { // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();
            modPresenza = PresenzaModulo.get();
            entrato = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codPeriodo);

            /**
             * Se già entrato, usa il valore effettivo dalle presenze
             * Se non entrato, usa il valore previsionale dal periodo
             */
            if (entrato) {    // già entrato, dati dalle Presenze

                uscito = modPeriodo.query().valoreBool(Periodo.Cam.partito.get(), codPeriodo);
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);

                /**
                 * Se già uscito, conta le partenze con cambio dalla camera
                 * alla data effettiva di uscita.
                 * Se non uscito, conta le presenze attuali nella camera.
                 */
                if (uscito) {    // già uscito
                    dataPartenza = modPeriodo.query().valoreData(
                            Periodo.Cam.partenzaEffettiva.get(),
                            codPeriodo);
                    filtro = PresenzaModulo.getFiltroPresenzeUsciteCambio(dataPartenza);
                    filtro.add(PresenzaModulo.getFiltroCamera(codCamera));

                } else {         // non ancora uscito
                    filtro = PresenzaModulo.getFiltroAperteSmart(codCamera);
                }// fine del blocco if-else

                switch (tipo) {
                    case persona:
                        break;
                    case adulto:
                        filtro.add(FiltroFactory.creaFalso(Presenza.Cam.bambino.get()));
                        break;
                    case bambino:
                        filtro.add(FiltroFactory.creaVero(Presenza.Cam.bambino.get()));
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

                quanti = modPresenza.query().contaRecords(filtro);

            } else {   // non entrato, dati dal Periodo

                switch (tipo) {
                    case persona:
                        nomeCampo = Periodo.Cam.persone.get();
                        break;
                    case adulto:
                        nomeCampo = Periodo.Cam.adulti.get();
                        break;
                    case bambino:
                        nomeCampo = Periodo.Cam.bambini.get();
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

                quanti = modPeriodo.query().valoreInt(nomeCampo, codPeriodo);

            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;

    }


    /**
     * Ritorna un filtro che seleziona le presenze in cambio o cambiate
     * relativamente a un periodo già entrato.
     * <p/>
     * Ha senso solo se il periodo è già entrato.
     *
     * @param codPeriodo codice del periodo
     * @return l'elenco dei codici delle presenza in cambio/cambiate
     */
    public static Filtro getFiltroPresenzeCambio(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Modulo modPeriodo;
        boolean uscito;
        int camera;
        Date dataPartenza;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            uscito = modPeriodo.query().valoreBool(Periodo.Cam.partito.get(), codPeriodo);
            camera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
            if (uscito) {
                dataPartenza = modPeriodo.query().valoreData(Periodo.Cam.partenzaEffettiva.get(), codPeriodo);
                filtro = PresenzaModulo.getFiltroPresenzeUsciteCambio(dataPartenza);
                filtro.add(PresenzaModulo.getFiltroCamera(camera));
            } else {
                filtro = PresenzaModulo.getFiltroAperteSmart(camera);
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Unisce due periodi.
     * <p/>
     * Devo trovare due e solo due records selezionati <br>
     * I due periodi devono avere la data di unione coincidente (anzi 1 giorno di differenza) <br>
     */
    public void unisciPeriodi() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Date inizioUno = null;
        Date inizioDue = null;
        Date fineUno = null;
        Date fineDue = null;
        Date inizio = null;
        Date fine = null;
        Connessione conn = null;
        int[] selezionati;
        int codPrimo = 0;
        int codSecondo = 0;
        Date cambio;

        try { // prova ad eseguire il codice
            /* devo trovare due e solo due records selezionati */
            selezionati = this.getNavigatoreCorrente().getLista().getChiaviSelezionate();
            continua = (selezionati.length == 2);

            if (continua) {
                conn = this.getNavigatoreCorrente().getConnessione();
            }// fine del blocco if

            /* carica le date dei due records */
            if (continua) {
                inizioUno = this.query().valoreData(Cam.arrivoPrevisto.get(), selezionati[0], conn);
                inizioDue = this.query().valoreData(Cam.arrivoPrevisto.get(), selezionati[1], conn);
                fineUno = this.query().valoreData(Cam.partenzaPrevista.get(), selezionati[0], conn);
                fineDue = this.query().valoreData(Cam.partenzaPrevista.get(), selezionati[1], conn);
            }// fine del blocco if

            /* ordine temporale */
            if (continua) {
                if (Lib.Data.isPosteriore(inizioUno, inizioDue)) {
                    codPrimo = selezionati[0];
                    codSecondo = selezionati[1];
                    inizio = inizioDue;
                    fine = fineUno;
                } else {
                    codPrimo = selezionati[1];
                    codSecondo = selezionati[0];
                    inizio = inizioUno;
                    fine = fineDue;
                }// fine del blocco if-else
            }// fine del blocco if

            /* controllo data coincidente (a meno di un giorno) */
            if (continua) {
                cambio = Lib.Data.add(fine, 1);
                continua = (cambio.equals(inizio));
            }// fine del blocco if

            /* registro i link incrociati */
            if (continua) {
                this.query().registra(codSecondo, Cam.linkProvenienza.get(), codPrimo, conn);
                this.query().registra(codPrimo, Cam.linkDestinazione.get(), codSecondo, conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Divide il periodo per un cambio camera.
     * <p/>
     */
    public void tagliaPeriodo() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Navigatore nav;
        int numRec;
        Lista lista = null;
        Date dataIni = null;
        Date dataEnd = null;
        Date dataCambio = null;
        int codRec = 0;
        int linkCam = 0;
        Connessione conn;
        TaglioPeriodoDialogo dialogo = null;
        boolean riuscito;
        int camIni;
        int camEnd;

        try { // prova ad eseguire il codice
            nav = this.getNavigatore(Periodo.Nav.prenotazione.get());
            continua = (nav != null);

            if (continua) {
                lista = nav.getLista();
                continua = (lista != null);
            }// fine del blocco if

            if (continua) {
                numRec = lista.getQuanteRigheSelezionate();
                continua = (numRec == 1);
            }// fine del blocco if

            if (continua) {
                codRec = lista.getChiaveSelezionata();
                continua = (codRec > 0);
            }// fine del blocco if

            if (continua) {
                conn = nav.getConnessione();
                dataIni = this.query().valoreData(Cam.arrivoPrevisto.get(), codRec, conn);
                dataEnd = this.query().valoreData(Cam.partenzaPrevista.get(), codRec, conn);
                linkCam = this.query().valoreInt(Cam.camera.get(), codRec, conn);
            }// fine del blocco if

            if (continua) {
                dialogo = new TaglioPeriodoDialogo();
                dialogo.setCodPeriodo(codRec);
                dialogo.setInizio(dataIni);
                dialogo.setFine(dataEnd);
                dialogo.setCam(linkCam);
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            if (continua) {
                dataCambio = dialogo.getCambio();
                camIni = dialogo.getCam();
                camEnd = dialogo.getCamDue();
                riuscito = this.eseguiTaglio(codRec, dataCambio, camIni, camEnd);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Taglia in due un periodo.
     * <p/>
     */
    private boolean eseguiTaglio(int codUno, Date dataCambio, int camIni, int camEnd) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        int codDue;

        try { // prova ad eseguire il codice
            codDue = this.query().duplicaRecord(codUno, true);
            continua = (codDue > 0);

            /* modifica il primo periodo */
            if (continua) {
                query().registraRecordValore(codUno, Cam.partenzaPrevista.get(), dataCambio);
                query().registraRecordValore(codUno, Cam.camera.get(), camIni);
                query().registraRecordValore(
                        codUno,
                        Cam.causalePartenza.get(),
                        CausaleAP.cambio.getCodice());
                query().registraRecordValore(codUno, Cam.linkDestinazione.get(), codDue);
            }// fine del blocco if

            /* modifica il secondo periodo */
            if (continua) {
//                dataCambio = Lib.Data.add(dataCambio, 1);
                query().registraRecordValore(codDue, Cam.arrivoPrevisto.get(), dataCambio);
                query().registraRecordValore(codDue, Cam.camera.get(), camEnd);
                query().registraRecordValore(
                        codDue,
                        Cam.causaleArrivo.get(),
                        CausaleAP.cambio.getCodice());
                query().registraRecordValore(codDue, Cam.linkProvenienza.get(), codUno);
                query().registraRecordValore(
                        codDue,
                        Cam.causalePartenza.get(),
                        CausaleAP.normale.getCodice());
                query().registraRecordValore(codDue, Cam.linkDestinazione.get(), 0);
            }// fine del blocco if

            /* regola le date prezzi dei priodi */
            if (continua) {
                this.regolaAddebiti(codUno);
                this.regolaAddebiti(codDue);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Regola gli addebiti del periodo.
     * <p/>
     */
    private void regolaAddebiti(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Filtro filtro;
        int[] codici = null;
        Date dataIniPer = null;
        Date dateEndPer = null;
        Date dataIniAdd;
        Date dateEndAdd;
        Modulo modAdd = null;

        try { // prova ad eseguire il codice
            continua = (codPeriodo > 0);

            if (continua) {
                modAdd = AddebitoPeriodoModulo.get();
                continua = (modAdd != null);
            }// fine del blocco if

            if (continua) {
                filtro = FiltroFactory.crea(AddebitoPeriodo.Cam.periodo.get(), codPeriodo);
                codici = modAdd.query().valoriChiave(filtro);
                continua = (codici != null) && (codici.length > 0);
            }// fine del blocco if

            if (continua) {
                dataIniPer = this.query().valoreData(Cam.arrivoPrevisto.get(), codPeriodo);
                dateEndPer = this.query().valoreData(Cam.partenzaPrevista.get(), codPeriodo);
            }// fine del blocco if

            if (continua) {
                for (int cod : codici) {
                    dataIniAdd =
                            modAdd.query().valoreData(
                                    AddebitoFisso.Cam.dataInizioValidita.get(),
                                    cod);
                    dateEndAdd = modAdd.query().valoreData(
                            AddebitoFisso.Cam.dataFineValidita.get(),
                            cod);

                    if (Lib.Data.isPeriodoEscluso(dataIniAdd, dateEndAdd, dataIniPer, dateEndPer)) {
                        modAdd.query().eliminaRecord(cod);
                    }// fine del blocco if

                    if (Lib.Data.isPrecedente(dataIniPer, dataIniAdd)) {
                        modAdd.query().registra(
                                cod,
                                AddebitoFisso.Cam.dataInizioValidita.get(),
                                dataIniPer);
                    }// fine del blocco if

                    if (Lib.Data.isPosteriore(dateEndPer, dateEndAdd)) {
                        modAdd.query().registra(
                                cod,
                                AddebitoFisso.Cam.dataFineValidita.get(),
                                dateEndPer);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sposta il momento di cambio camera.
     * <p/>
     */
    public void spostaCambio() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Navigatore nav;
        int numRec;
        Lista lista = null;
        Date ini = null;
        Date end = null;
        int codRec = 0;
        int linkCam = 0;
        Connessione conn;
        TaglioPeriodoDialogo dialogo;

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il codice azienda relativo a un periodo.
     * <p/>
     *
     * @param codPeriodo codice del periodo
     * @return codice azienda
     */
    public static int getCodAzienda(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        int codAzienda = 0;
        int codPrenotazione;
        Modulo modPeriodo;
        Modulo modPrenotazione;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            codPrenotazione = modPeriodo.query().valoreInt(
                    Periodo.Cam.prenotazione.get(),
                    codPeriodo);
            modPrenotazione = PrenotazioneModulo.get();
            codAzienda = modPrenotazione.query().valoreInt(
                    Prenotazione.Cam.azienda.get(),
                    codPrenotazione);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codAzienda;
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * gli arrivi per un dato periodo.
     * <p/>
     * Si basa su tutte le prenotazioni valide per l'azienda attiva.
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     * @return il filtro per gli arrivi
     */
    public static Filtro getFiltroArrivi(Date data1, Date data2) {
        return PeriodoLogica.getFiltroArrivi(data1, data2);
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * le entrate (per arrivo o cambio) per un dato periodo.
     * <p/>
     * Si basa su tutte le prenotazioni valide per l'azienda attiva.
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     * @return il filtro per le entrate
     */
    public static Filtro getFiltroEntrate(Date data1, Date data2) {
        return PeriodoLogica.getFiltroEntrate(data1, data2);
    }


    /**
     * Numnero di persone previste in arrivo nel periodo.
     * <p/>
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     * @return numero di persone previste in arrivo nel periodo
     */
    public static int getPersoneArrivo(Date data1, Date data2) {
        return PeriodoLogica.getPersoneArrivo(data1, data2);
    }


    /**
     * Numnero di persone previste in partenza nel periodo.
     * <p/>
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     * @return numero di persone previste in partenza nel periodo
     */
    public static int getPersonePartenza(Date data1, Date data2) {
        return PeriodoLogica.getPersonePartenza(data1, data2);
    }


    /**
     * Costruisce un ordine per la visualizzazione degli arrivi
     * <p/>
     *
     * @return l'ordine per la visualizzazione degli arrivi
     *         (per data arrivo previsto e nome della camera)
     */
    public static Ordine getOrdineArrivi() {
        return PeriodoLogica.getOrdineArrivi();
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * le partenze per un dato periodo.
     * <p/>
     * Si basa su tutte le prenotazioni valide per l'azienda attiva.
     *
     * @param data1 data inizio (null per cominciare dalla prima)
     * @param data2 data fine (null per arrivare all'ultima)
     * @return il filtro per le partenze
     */
    public static Filtro getFiltroPartenze(Date data1, Date data2) {
        return PeriodoLogica.getFiltroPartenze(data1, data2);
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * le uscite (per partenza o cambio) per un dato intervallo di tempo.
     * <p/>
     * Include le uscite relative alle prenotazioni valide
     *
     * @param data1 data inizio (null per cominciare dalla prima)
     * @param data2 data fine (null per arrivare all'ultima)
     * @return il filtro per le uscite
     */
    public static Filtro getFiltroUscite(Date data1, Date data2) {
        return PeriodoLogica.getFiltroUscite(data1, data2);
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * le partenze in una certa data.
     * <p/>
     *
     * @param data data partenza
     * @return il filtro per le partenze
     */
    public static Filtro getFiltroPartenze(Date data) {
        return PeriodoLogica.getFiltroPartenze(data, data);
    }


    /**
     * Restituisce le partenze previste per un dato periodo.
     * <p/>
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     * @return le partenze previste nel periodo
     */
    public static int[] getPartenze(Date data1, Date data2) {
        return PeriodoLogica.getPartenze(data1, data2);
    }


    /**
     * Costruisce un ordine per la visualizzazione delle partenze
     * <p/>
     *
     * @return l'ordine per la visualizzazione delle partenze
     *         (per data partenza prevista e nome della camera)
     */
    public static Ordine getOrdinePartenze() {
        return PeriodoLogica.getOrdinePartenze();
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * tutti i periodi validi con cambio in uscita per un dato
     * intervallo di tempo e per l'azienda attiva.
     * <p/>
     * Seleziona i cambi in uscita confermati e non confermati
     *
     * @param data1 data inizio (null per cominciare dal primo)
     * @param data2 data fine (null per arrivare all'ultimo)
     * @return il filtro per i cambi in uscita
     */
    public static Filtro getFiltroCambiUscita(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroData;
        Filtro filtroCambio;

        try { // prova ad eseguire il codice

            /* filtro vuoto */
            filtro = new Filtro();

            /* filtro prenotazioni valide */
            filtro.add(PrenotazioneModulo.getFiltroValide());

            /* filtro azienda attiva */
            filtro.add(PrenotazioneModulo.get().getFiltroAzienda());

            /* filtro causale uscita = cambio */
            filtroCambio = FiltroFactory.crea(
                    Periodo.Cam.causalePartenza.get(),
                    Periodo.CausaleAP.cambio.getCodice());
            filtro.add(filtroCambio);

            /* filtro data iniziale, se specificata */
            if (!Lib.Data.isVuota(data1)) {
                filtroData = FiltroFactory.crea(
                        Periodo.Cam.partenzaPrevista.get(),
                        Filtro.Op.MAGGIORE_UGUALE,
                        data1);
                filtro.add(filtroData);
            }// fine del blocco if

            /* filtro data finale, se specificata */
            if (!Lib.Data.isVuota(data2)) {
                filtroData = FiltroFactory.crea(
                        Periodo.Cam.partenzaPrevista.get(),
                        Filtro.Op.MINORE_UGUALE,
                        data2);
                filtro.add(filtroData);
                filtro.add(filtroCambio);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;


    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * tutti i periodi validi con cambio in uscita per un dato
     * giorno e per l'azienda attiva.
     * <p/>
     * Seleziona i cambi in uscita confermati e non confermati
     *
     * @param data il giorno da controllare
     * @return il filtro per i cambi in uscita
     */
    public static Filtro getFiltroCambiUscita(Date data) {
        /* valore di ritorno */
        return getFiltroCambiUscita(data, data);
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * tutti i periodi aperti (che hanno il flag IN ma non hanno il flag OUT)
     * <p/>
     *
     * @return il filtro per i periodi aperti
     */
    public static Filtro getFiltroAperti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice

            /* filtro vuoto */
            filtro = new Filtro();

            /* filtro flag arrivato = true */
            filtro.add(FiltroFactory.creaVero(Periodo.Cam.arrivato.get()));

            /* filtro flag partito = false */
            filtro.add(FiltroFactory.creaFalso(Periodo.Cam.partito.get()));

            /* filtro azienda corrente */
            filtro.add(PrenotazioneModulo.get().getFiltroAzienda());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;


    }


    /**
     * Crea un filtro seleziona i periodi che hanno almeno uno dei due estremi
     * che ricade all'interno dell'intervallo di date specificato
     * <p/>
     *
     * @param d1 data iniziale (compresa)
     * @param d2 data finale (compresa)
     * @return il filtro creato
     */
    public static Filtro getFiltroInteressati(Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        Filtro ftot = null;
        Filtro filtro;
        Filtro f1, f2, f3;
        Campo p1, p2;

        try {    // prova ad eseguire il codice

            p1 = PeriodoModulo.get().getCampo(Periodo.Cam.arrivoPrevisto.get());
            p2 = PeriodoModulo.get().getCampo(Periodo.Cam.partenzaPrevista.get());

            /* l'inizio compreso tra le due date */
            f1 = new Filtro();
            filtro = FiltroFactory.crea(p1, Filtro.Op.MAGGIORE_UGUALE, d1);
            f1.add(filtro);
            filtro = FiltroFactory.crea(p1, Filtro.Op.MINORE_UGUALE, d2);
            f1.add(filtro);

            /* la fine compresa tra le due date */
            f2 = new Filtro();
            filtro = FiltroFactory.crea(p2, Filtro.Op.MAGGIORE_UGUALE, d1);
            f2.add(filtro);
            filtro = FiltroFactory.crea(p2, Filtro.Op.MINORE_UGUALE, d2);
            f2.add(filtro);

            /* inizia prima e finisce dopo (attraversa le due date) */
            f3 = new Filtro();
            filtro = FiltroFactory.crea(p1, Filtro.Op.MINORE_UGUALE, d1);
            f3.add(filtro);
            filtro = FiltroFactory.crea(p2, Filtro.Op.MAGGIORE_UGUALE, d2);
            f3.add(filtro);

            ftot = new Filtro();
            ftot.add(f1);
            ftot.add(Filtro.Op.OR, f2);
            ftot.add(Filtro.Op.OR, f3);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ftot;
    }





    /**
     * Ritorna il codice prenotazione relativo a un dato periodo
     * i cambi per un dato periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     * @return il codice della relativa prenotazione
     */
    public static int getCodPrenotazione(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        int codPrenotazione = 0;
        Modulo modPeriodo;

        try { // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            if (modPeriodo != null) {
                codPrenotazione = modPeriodo.query().valoreInt(Cam.prenotazione.get(), codPeriodo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codPrenotazione;
    }





    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static PeriodoModulo get() {
        return (PeriodoModulo) ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new PeriodoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
