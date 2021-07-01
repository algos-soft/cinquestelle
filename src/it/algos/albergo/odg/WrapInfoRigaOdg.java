package it.algos.albergo.odg;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.compoaccessori.WrapCompoAccessorio;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.odg.odgtesta.Odg;
import it.algos.albergo.odg.odgtesta.OdgModulo;
import it.algos.albergo.odg.odgzona.OdgZona;
import it.algos.albergo.odg.odgzona.OdgZonaModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;

import java.util.ArrayList;
import java.util.Date;

/**
 * Wrapper per le informazioni sullo stato
 * di una camera in un giorno
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 20-giu-2009 ore 11:13
 */
class WrapInfoRigaOdg {

    /**
     * Numero di giorni di visione in avanti per decidere come
     * preparare una camera quando si libera:
     * - se c'è una entrata prevista entro questo numero di
     * giorni, la prepara per l'entrata successiva.
     * - se l'entrata successiva non è prevista o è prevista oltre questo
     * numero di giorni, la prepara in modo standard.
     */
    private int NUM_GG_VISIONE_AVANTI =7;

    /**
     * Numero di giorni di visione all'indietro per decidere se e come
     * preparare una camera quando si occupa:
     * - se c'è un periodo uscito entro questo numero di giorni, tengo buona
     * la preparazione già prevista all'uscita e assumo che la camera
     * sia già pronta con la preparazione corretta.
     * - se non ci sono periodi usciti in precedenza o l'ultima uscita è
     * più lontana di questo numero di giorni, preparo la camera come
     * richiesto dall'entrata.
     */
    private int NUM_GG_VISIONE_INDIETRO =30;

    /* codice della camera in oggetto */
    private int codCamera;

    /* codice della riga di zona di riferimento */
    private int codRigaZona;

    /* data di analisi */
    private Date data;

    /* true se c'è una uscita */
    private boolean uscita;

    /* la causale di uscita */
    private Periodo.CausaleAP causaleUscita;

    /* il riferimento al periodo in uscita */
    private int codPeriUscita;

    /* true se c'è una entrata */
    private boolean entrata;

    /* la causale di entrata */
    private Periodo.CausaleAP causaleEntrata;

    /* il riferimento al periodo in entrata */
    private int codPeriEntrata;

    /* true se è una fermata - la camera è occupata a cavallo */
    private boolean fermata;

    /* il riferimento al periodo della fermata */
    private int codPeriFermata;

    /* true se esce per partenza il giorno dopo */
    private boolean parteDomani;

    /* true se esce per cambio il giorno dopo */
    private boolean cambiaDomani;

    /* true se la camera va preparata per la chiusura */
    private boolean chiudere;

    /* true se la camera è da preparare */
    private boolean daFare;

    /* codice della composizione precedente */
    private int codCompoPrecedente;

    /* codice del tipo di composizione */
    private int codComposizione;

    /* note di preparazione */
    private String note = "";

    /* lista contenente l'elenco degli accessori necessari alla preparazione */
    private ArrayList<WrapCompoAccessorio> listaAccessori=new ArrayList<WrapCompoAccessorio>();

    /**
     * dato provvisorio di lavoro - registrato dopo la query
     * per renderlo disponibile agli altri metodi senza doverlo
     * passare tutte le volte come parametro
     */
    Dati dati;


    /**
     * Costruttore con parametri.
     *
     * @param codRigaZona - codice della riga di zona ri riferimento
     * @param codCamera - codice della camera
     */
    public WrapInfoRigaOdg(int codRigaZona, int codCamera) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCodRigaZona(codRigaZona);
        this.setCodCamera(codCamera);

        try { // prova ad eseguire il codice
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
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        Ordine ordine;
        Modulo modPeriodo = PeriodoModulo.get();

        try { // prova ad eseguire il codice

            /* regola la data di riferimento */
            Date dataOdg = OdgModulo.get().query().valoreData(Odg.Cam.data.get(), this.getCodOdg());
            this.setData(dataOdg);

            /**
             * Carica i dati dei periodi che intersecano il giorno e la camera.
             * I periodi trovati possono essere nessuno, uno o due.
             * Sono due se si ha una entrata e una uscita nello stesso giorno.
             *
             * ->  |  | <--.. nessuno
             * ..--|--|--->   uno
             *   --|> |       uno
             *     | <|--     uno
             * <---|><|---..  due
             */
            filtro = new Filtro();
            filtro.add(FiltroFactory.crea(Periodo.Cam.camera.get(), this.getCodCamera()));
            filtro.add(PeriodoModulo.getFiltroInteressati(this.getData(), this.getData()));
            filtro.add(PrenotazioneModulo.getFiltroValide());

            /* aggiunge il filtro azienda come da Odg */
            int codAzienda = this.getCodAzienda();
            if (codAzienda!=0) {
                Campo campoAz = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.azienda);
                Filtro filtroAzienda = FiltroFactory.crea(campoAz, codAzienda);
                filtro.add(filtroAzienda);
            }// fine del blocco if

            /**
             * Ordine per data di arrivo
             * così se sono due c'è sempre prima l'uscita e poi l'entrata
             */
            ordine = new Ordine(modPeriodo.getCampo(Periodo.Cam.arrivoPrevisto.get()));

            /**
             * Costruisce la query completa
             */
            Query query = new QuerySelezione(modPeriodo);
            query.addCampo(PeriodoModulo.get().getCampoChiave());
            query.addCampo(Periodo.Cam.causaleArrivo.get());
            query.addCampo(Periodo.Cam.causalePartenza.get());
            query.addCampo(Periodo.Cam.arrivoPrevisto.get());
            query.addCampo(Periodo.Cam.partenzaPrevista.get());
            query.addCampo(Periodo.Cam.preparazione.get());
            query.addCampo(Periodo.Cam.linkProvenienza.get());
            query.addCampo(Periodo.Cam.linkDestinazione.get());
            query.addCampo(PrenotazioneModulo.get().getCampo(Prenotazione.Cam.azienda));
            query.setFiltro(filtro);
            query.setOrdine(ordine);
            Dati dati = modPeriodo.query().querySelezione(query);

            // todo solo per debug
            String nomeCam = CameraModulo.get().query().valoreStringa(Camera.Cam.camera.get(), this.getCodCamera());


            /**
             * registra provvisoriament l'oggetto Dati per non
             * doverlo passare a tutti i metodi chiamati
             */
            this.setDati(dati);

            /**
             * valorizzazione delle variabili del wrapper
             *
             * ATTENZIONE: queste funzioni possono avvalersi delle regolazioni
             * effettuate dalle funzioni precedenti, quindi prima di cambiarne
             * l'ordine valutare bene le conseguenze!!
             */
            this.regolaEntrata();
            this.regolaUscita();
            this.regolaFermata();
            this.regolaParteDomani();
            this.regolaCambiaDomani();
            this.regolaChiudere();
            this.regolaDaFare();
            this.regolaCompoPrecedente();
            this.regolaComposizione();
            this.regolaAccessori();
            
            dati.close();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola le variabili relative all'entrata (flag, causale, cod periodo).
     * <p/>
     */
    private void regolaEntrata() {
        try {    // prova ad eseguire il codice
            Dati dati = this.getDati();

            if (dati.getRowCount() >0) {

                /**
                 * Se ci sono più periodi identifica quello relativa all'entrata
                 * che è sempre il secondo
                 */
                int riga = 0;
                if (dati.getRowCount() == 2) {
                    riga = 1;
                }// fine del blocco if

                Date dataEntrata = dati.getDataAt(riga, Periodo.Cam.arrivoPrevisto.get());
                int codCausale = dati.getIntAt(riga, Periodo.Cam.causaleArrivo.get());
                int codPeriodo = dati.getIntAt(riga, PeriodoModulo.get().getCampoChiave());
                Periodo.CausaleAP causale = Periodo.CausaleAP.getCausale(codCausale);

                if (dataEntrata.equals(this.getData())) {
                    this.setEntrata(true);
                    this.setCausaleEntrata(causale);
                    this.setCodPeriEntrata(codPeriodo);
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola le variabili relative all'uscita (flag, causale, cod periodo).
     * <p/>
     */
    private void regolaUscita() {
        try {    // prova ad eseguire il codice

            Dati dati = this.getDati();

            if (dati.getRowCount()>0) {

                Date dataUscita = dati.getDataAt(0, Periodo.Cam.partenzaPrevista.get());
                int codCausale = dati.getIntAt(0, Periodo.Cam.causalePartenza.get());
                int codPeriodo = dati.getIntAt(0, PeriodoModulo.get().getCampoChiave());
                Periodo.CausaleAP causale = Periodo.CausaleAP.getCausale(codCausale);

                if (dataUscita.equals(this.getData())) {
                    this.setUscita(true);
                    this.setCausaleUscita(causale);
                    this.setCodPeriUscita(codPeriodo);
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }



    /**
     * Regola la variabile booleana Fermata.
     * <p/>
     */
    private void regolaFermata() {
        try {    // prova ad eseguire il codice
            Dati dati = this.getDati();
            if (dati.getRowCount() == 1) {
                Date dataArrivo = dati.getDataAt(0,Periodo.Cam.arrivoPrevisto.get());
                Date dataPartenza = dati.getDataAt(0,Periodo.Cam.partenzaPrevista.get());
                int codPeriodo = dati.getIntAt(0, PeriodoModulo.get().getCampoChiave());
                if (Lib.Data.isPrecedente(this.getData(), dataArrivo)) {
                    if (Lib.Data.isPosteriore(this.getData(), dataPartenza)) {
                        this.setFermata(true);
                        this.setCodPeriFermata(codPeriodo);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }




    /**
     * Regola la variabile booleana Parte Domani.
     * <p/>
     */
    private void regolaParteDomani() {
        try {    // prova ad eseguire il codice

            Dati dati = this.getDati();

            if (dati.getRowCount() >0) {

                /**
                 * Se ci sono più periodi identifica quello relativa all'entrata
                 * che è sempre il secondo
                 */
                int riga = 0;
                if (dati.getRowCount() == 2) {
                    riga = 1;
                }// fine del blocco if

                Date dataPartenza = dati.getDataAt(riga, Periodo.Cam.partenzaPrevista.get());
                int codCausale = dati.getIntAt(riga, Periodo.Cam.causalePartenza.get());
                Periodo.CausaleAP causale = Periodo.CausaleAP.getCausale(codCausale);
                if (causale.equals(Periodo.CausaleAP.normale)) {
                    Date domani = Lib.Data.add(this.getData(), 1);
                    if (dataPartenza.equals(domani)) {
                        this.setParteDomani(true);
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola la variabile booleana Cambia Domani.
     * <p/>
     */
    private void regolaCambiaDomani() {
        try {    // prova ad eseguire il codice
            Dati dati = this.getDati();

            if (dati.getRowCount() >0) {
                /**
                 * Se ci sono più periodi identifica quello relativa all'entrata
                 * che è sempre il secondo
                 */
                int riga = 0;
                if (dati.getRowCount() == 2) {
                    riga = 1;
                }// fine del blocco if

                Date dataPartenza = dati.getDataAt(riga, Periodo.Cam.partenzaPrevista.get());
                int codCausale = dati.getIntAt(riga, Periodo.Cam.causalePartenza.get());
                Periodo.CausaleAP causale = Periodo.CausaleAP.getCausale(codCausale);
                if (causale.equals(Periodo.CausaleAP.cambio)) {
                    Date domani = Lib.Data.add(this.getData(), 1);
                    if (dataPartenza.equals(domani)) {
                        this.setCambiaDomani(true);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola la variabile booleana Chiudere.
     * <p/>
     */
    private void regolaChiudere() {
        this.setChiudere(false);  // per ora solo manuale
    }


    /**
     * Regola la variabile booleana DaFare.
     * <p/>
     * La camera è da fare se:
     * - c'è una uscita
     * - c'è una entrata e la precedente uscita non c'è o è più lontana
     * del numero di giorni di visione all'indietro configurato
     */
    private void regolaDaFare() {
        /* variabili e costanti locali di lavoro */
        boolean fare=false;

        try {    // prova ad eseguire il codice

            /**
             * se c'è una uscita, è da fare
             * se no controlla se c'è una entrata
             */
            if (this.isUscita()) {
                fare=true;
            } else {

                /**
                 * se c'è una entrata controlla l'ultima uscita precedente
                 * se non c'è o è troppo lontana, è da fare
                 */
                if (this.isEntrata()) {
                    int codPeriodo = this.getCodPeriodoPrecedente(this.getData());
                    if (codPeriodo>0) {
                        if (this.isUscitoTroppoTempoFa(codPeriodo)) {
                            fare=true;  // l'ultima uscita è troppo lontana; è da fare
                        }// fine del blocco if
                    } else {
                        fare=true;  // non ci sono uscite precedenti, è da fare
                    }// fine del blocco if-else
                }// fine del blocco if

            }// fine del blocco if-else

            /* regola il flag */
            this.setDaFare(fare);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola la variabile int Composizione Precedente.
     * <p/>
     * Recupera la composizione dell'ultimo periodo uscito
     * da oggi guardando indietro fino al massimo consentito.
     * Se non trova nulla lascia vuoto.
     */
    private void regolaCompoPrecedente () {
        /* variabili e costanti locali di lavoro */
        Modulo modPeri = PeriodoModulo.get();


        try {    // prova ad eseguire il codice
            int codPeriodo = this.getCodPeriodoPrecedente(this.getData());
            if (codPeriodo>0) {
                if (!this.isUscitoTroppoTempoFa(codPeriodo)) {
                    int codCompo = modPeri.query().valoreInt(Periodo.Cam.preparazione.get(), codPeriodo);
                    this.setCodCompoPrecedente(codCompo);
                }// fine del blocco if
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se un periodo è uscito da troppo tempo
     * rispetto alla data di analisi e in funzione del massimo
     * tempo di visione all'indietro configurato.
     * <p/>
     * @param codPeriodo il codice del periodo
     * @return true se l'uscita è avvenuta da troppo tempo
     */
    private boolean isUscitoTroppoTempoFa (int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean troppoTempo  = false;

        try {    // prova ad eseguire il codice
            Modulo modPeriodo = PeriodoModulo.get();
            Date dataUscita = modPeriodo.query().valoreData(Periodo.Cam.partenzaPrevista.get(), codPeriodo);
            int giorni = Lib.Data.diff(this.getData(), dataUscita);
            if (giorni> NUM_GG_VISIONE_INDIETRO) {
                troppoTempo=true;  // l'ultima uscita è troppo lontana; è da fare
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return troppoTempo;
    }


    /**
     * Regola la variabile Composizione e le note.
     * <p/>
     * La composizione si regola sempre.
     * - Se è una fermata, è la composizione del periodo attualmente occupante - senza note
     * - Se è una uscita o una entrata (o entrambe), è presa dal successiovo periodo
     *   entrante (che in caso di uscita/entrata è il giorno stesso) - con note periodo entrante
     *   se il periodo entrante non c'è o è troppo lontano, composizione standard - senza note.
     * - Se non è nessuno dei precedenti (la camera è libera) è la composizione
     *   precedente (se presente, altrimenti è standard) - senza note
     */
    private void regolaComposizione() {
        /* variabili e costanti locali di lavoro */
        Modulo modPeriodo = PeriodoModulo.get();
        int codPrep=0;
        String notePrep="";

        try {    // prova ad eseguire il codice

            if (this.isFermata()) {      // è una fermata

                int codPeri = this.getCodPeriFermata();
                codPrep = PeriodoModulo.get().query().valoreInt(Periodo.Cam.preparazione.get(), codPeri);
                notePrep = "";

            } else { // uscita o entrata o entrambe o vuota

                /**
                 * se ci sono arrivi successivi prepara per il primo arrivo successivo
                 * se non è troppo lontano nel tempo
                 */
                int codPeriSucc = this.getCodPeriodoSuccessivo(this.getData());
                if (codPeriSucc>0) {
                    Date arrivo = modPeriodo.query().valoreData(Periodo.Cam.arrivoPrevisto.get(), codPeriSucc);
                    if (Lib.Data.diff(arrivo, this.getData())<= NUM_GG_VISIONE_AVANTI) {
                        codPrep = modPeriodo.query().valoreInt(Periodo.Cam.preparazione.get(), codPeriSucc);
                        if (this.isUscita() || this.isEntrata()) {
                            notePrep = modPeriodo.query().valoreStringa(Periodo.Cam.noteprep.get(), codPeriSucc);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                /**
                 * se non è riuscito a determinare una preparazione,
                 * esegue la preparazione standard.
                 */
                if (codPrep==0) {
                    codPrep = CameraModulo.getComposizioneStandard(this.getCodCamera());
                }// fine del blocco if

            }// fine del blocco if-else

            /* regola le variabili */
            this.setCodComposizione(codPrep);
            this.setNote(notePrep);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il codice del primo periodo che occupa la camera corrente
     * in data uguale o successiva a quella indicata
     * <p/>
     * @param data da controllare
     * @return il codice del periodo successivo, 0 se non ce ne sono
     */
    private int getCodPeriodoSuccessivo (Date data) {
        /* variabili e costanti locali di lavoro */
        int codPeri = 0;
        Modulo modPeriodo = PeriodoModulo.get();

        try {    // prova ad eseguire il codice

            /**
             * isola tutti i periodi che occupano la camera con entrata uguale
             * o successiva alla data in esame, ordinati per data di entrata
             */
            Filtro filtro = new Filtro();
            Filtro filtroArrivi = PeriodoModulo.getFiltroEntrate(data, null);
            filtro.add(filtroArrivi);
            Filtro filtroCamera = FiltroFactory.crea(Periodo.Cam.camera.get(),this.getCodCamera());
            filtro.add(filtroCamera);

            Ordine ordine = new Ordine(modPeriodo.getCampo(Periodo.Cam.arrivoPrevisto));

            int[] chiavi = modPeriodo.query().valoriChiave(filtro, ordine);
            if (chiavi.length>0) {
                codPeri=chiavi[0];
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPeri;
    }





    /**
     * Recupera il codice dell'ultimo periodo della camera che termina
     * (per uscita o cambio) in data precedente o uguale alla data specificata.
     * <p/>
     * @param data da controllare
     * @return codice del periodo, 0 se non ce ne sono
     */
    private int getCodPeriodoPrecedente (Date data) {
        /* variabili e costanti locali di lavoro */
        int codPeriodo  = 0;
        Modulo modPeriodo = PeriodoModulo.get();

        try {    // prova ad eseguire il codice
            Filtro filtro = new Filtro();
            filtro.add(PeriodoModulo.getFiltroUscite(null, data));
            filtro.add(FiltroFactory.crea(Periodo.Cam.camera.get(), this.getCodCamera()));
            Ordine ordine = new Ordine(modPeriodo.getCampo(Periodo.Cam.partenzaPrevista));
            int[] chiavi = modPeriodo.query().valoriChiave(filtro, ordine);
            if (chiavi.length > 0) {
                codPeriodo = chiavi[(chiavi.length)-1];   //l'ultimo
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPeriodo;
    }


    /**
     * Regola la lista degli accessori necessari .
     * <p/>
     * Gli accessori si usano solo se la camera è da preparare o e una fermata.
     * Se la camera è da preparare, recupera gli accessori in base alla composizione.
     * Se è una fermata, considera anche la rotazione.
     */
    private void regolaAccessori () {
        /* variabili e costanti locali di lavoro */
        ArrayList<WrapCompoAccessorio> lista;

        try {    // prova ad eseguire il codice

            if (this.isDaFare() || this.isFermata()) {

                if (this.isDaFare()) {
                    int codCompo = this.getCodComposizione();
                    if (codCompo>0) {
                        lista = CompoCameraModulo.getAccessori(codCompo);
                        this.setListaAccessori(lista);
                    }// fine del blocco if
                }// fine del blocco if

                if (this.isFermata()) {
                    this.regolaAccessoriFermata();
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola la lista degli accessori in caso di fermata.
     * <p/>
     */
    private void regolaAccessoriFermata() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codCompo=0;
        ArrayList<WrapCompoAccessorio> lista;
        ArrayList<WrapCompoAccessorio> listaOut = new ArrayList<WrapCompoAccessorio>();

        try {    // prova ad eseguire il codice

            /**
             * recupera il numero di giorni passati dall'entrata
             */
            Dati dati = this.getDati();
            Date dataEntrata = dati.getDataAt(0, Periodo.Cam.arrivoPrevisto.get());
            int giorniPassati = Lib.Data.diff(this.getData(), dataEntrata);
            continua = giorniPassati>0;

            /**
             * Recupera la composizione dal periodo
             */
            if (continua) {
                codCompo = dati.getIntAt(0, Periodo.Cam.preparazione.get());
                continua = codCompo>0;
            }// fine del blocco if


            /**
             * Recupera la lista degli accessori relativi alla composizione
             * corrente con relativo tempo di rotazione.
             * Spazzola la lista e aggiunge quelli dove i giorni passati
             * sono multipli dei giorni di rotazione a una lista di uscita.
             */
            if (continua) {
                lista = CompoCameraModulo.getAccessori(codCompo);
                for(WrapCompoAccessorio wrapper : lista){
                    int giorniRot = wrapper.getGgrotazione();
                    if (giorniRot>0) {
                        if (Lib.Mat.isMultiplo(giorniPassati, giorniRot)) {
                            listaOut.add(wrapper);
                        }// fine del blocco if
                    }// fine del blocco if
                }
                continua = listaOut.size()>0;
            }// fine del blocco if

            /* registra la lista */
            if (continua) {
                this.setListaAccessori(listaOut);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }




    /**
     * Ritorna true se è un Arrivo
     * <p>
     * @return true se è un arrivo
     */
    public boolean isArrivo() {
        return (this.isEntrata() && (this.getCausaleEntrata().equals(Periodo.CausaleAP.normale)));
    }

    /**
     * Ritorna true se è una Partenza
     * <p>
     * @return true se è una partenza
     */
    public boolean isPartenza() {
        return (this.isUscita() && this.getCausaleUscita().equals(Periodo.CausaleAP.normale));
    }

    /**
     * Ritorna true se è un Cambio
     * <p>
     * @return true se è una partenza
     */
    public boolean isCambio() {
        return ((this.getCodCamProvenienza()!=0) || (this.getCodCamDestinazione()!=0));
    }


    /**
     * Ritorna il codice della camera di provenienza in caso di cambio in entrata
     * <p>
     * @return il codice della camera di provenienza
     */
    public int getCodCamProvenienza() {
        /* variabili e costanti locali di lavoro */
        int codCamera =0;

        try { // prova ad eseguire il codice
            if (this.isEntrata() && this.getCausaleEntrata().equals(Periodo.CausaleAP.cambio)) {
                int codPeri = this.getCodPeriEntrata();
                int codPeriProv = PeriodoModulo.get().query().valoreInt(Periodo.Cam.linkProvenienza.get(), codPeri);
                codCamera = PeriodoModulo.get().query().valoreInt(Periodo.Cam.camera.get(), codPeriProv);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCamera;
    }


    /**
     * Ritorna il codice della camera di destinazione in caso di cambio in uscita
     * <p>
     * @return il codice della camera di destinazione
     */
    public int getCodCamDestinazione() {
        /* variabili e costanti locali di lavoro */
        int codCamera =0;

        try { // prova ad eseguire il codice
            if (this.isUscita() && this.getCausaleUscita().equals(Periodo.CausaleAP.cambio)) {
                int codPeri = this.getCodPeriUscita();
                int codPeriDest = PeriodoModulo.get().query().valoreInt(Periodo.Cam.linkDestinazione.get(), codPeri);
                codCamera = PeriodoModulo.get().query().valoreInt(Periodo.Cam.camera.get(), codPeriDest);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCamera;
    }



    /**
     * Ritorna il codice del primo periodo
     * <p>
     * @return il codice del primo periodo
     */
    public int getCodPeriodo1() {
        /* variabili e costanti locali di lavoro */
        int codice=0;

        try { // prova ad eseguire il codice

            if (this.isUscita() && this.isEntrata()) {
                codice = this.getCodPeriUscita();
            } else {

                if (this.isUscita()) {
                    codice = this.getCodPeriUscita();
                }// fine del blocco if

                if (this.isEntrata()) {
                    codice = this.getCodPeriEntrata();
                }// fine del blocco if

                if (this.isFermata()) {
                    codice = this.getCodPeriFermata();
                }// fine del blocco if
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Ritorna il codice del secondo periodo
     * <p>
     * @return il codice del secondo periodo
     */
    public int getCodPeriodo2() {
        /* variabili e costanti locali di lavoro */
        int codice=0;

        try { // prova ad eseguire il codice
            if (this.isUscita() && this.isEntrata()) {
                codice = this.getCodPeriEntrata();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Recupera il codice azienda dal relativo Odg.
     * <p/>
     * @return il codice azienda dell'Odg (0 per tutte le aziende)
     */
    private int getCodAzienda() {
        /* variabili e costanti locali di lavoro */
        int codAzienda=0;

        try {    // prova ad eseguire il codice
            int codOdg = this.getCodOdg();
            codAzienda = OdgModulo.get().query().valoreInt(Odg.Cam.azienda.get(), codOdg);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codAzienda;
    }

    /**
     * Recupera il codice dell'Odg di riferimento.
     * <p/>
     * @return il codice il codice dell'Odg di riferimento
     */
    private int getCodOdg() {
        /* variabili e costanti locali di lavoro */
        int codOdg=0;

        try {    // prova ad eseguire il codice
            int codRZ = this.getCodRigaZona();
            codOdg = OdgZonaModulo.get().query().valoreInt(OdgZona.Cam.odg.get(), codRZ);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codOdg;
    }


    /**
     * Ritorna una stringa con la sigla della composizione precedente
     * <p>
     * @return la sigla della composizione precedente
     */
    public String getSiglaCompoPrecedente() {
        /* variabili e costanti locali di lavoro */
        String sigla = "";

        try { // prova ad eseguire il codice
            int codCompo = this.getCodCompoPrecedente();
            if (codCompo>0) {
                sigla = CompoCameraModulo.get().query().valoreStringa(CompoCamera.Cam.sigla.get(), codCompo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return sigla;

    }




    private int getCodCamera() {
        return codCamera;
    }


    private void setCodCamera(int codCamera) {
        this.codCamera = codCamera;
    }


    private int getCodRigaZona() {
        return codRigaZona;
    }


    private void setCodRigaZona(int codRigaZona) {
        this.codRigaZona = codRigaZona;
    }


    private Date getData() {
        return data;
    }


    private void setData(Date data) {
        this.data = data;
    }


    private boolean isUscita() {
        return uscita;
    }


    private void setUscita(boolean uscita) {
        this.uscita = uscita;
    }


    private Periodo.CausaleAP getCausaleUscita() {
        return causaleUscita;
    }


    private void setCausaleUscita(Periodo.CausaleAP causaleUscita) {
        this.causaleUscita = causaleUscita;
    }


    private int getCodPeriUscita() {
        return codPeriUscita;
    }


    private void setCodPeriUscita(int codPeriUscita) {
        this.codPeriUscita = codPeriUscita;
    }


    private boolean isEntrata() {
        return entrata;
    }


    private void setEntrata(boolean entrata) {
        this.entrata = entrata;
    }


    private Periodo.CausaleAP getCausaleEntrata() {
        return causaleEntrata;
    }


    private void setCausaleEntrata(Periodo.CausaleAP causaleEntrata) {
        this.causaleEntrata = causaleEntrata;
    }


    private int getCodPeriEntrata() {
        return codPeriEntrata;
    }


    private void setCodPeriEntrata(int codPeriEntrata) {
        this.codPeriEntrata = codPeriEntrata;
    }

    public boolean isFermata() {
        return fermata;
    }


    private void setFermata(boolean fermata) {
        this.fermata = fermata;
    }


    private int getCodPeriFermata() {
        return codPeriFermata;
    }


    private void setCodPeriFermata(int codPeriFermata) {
        this.codPeriFermata = codPeriFermata;
    }


    public boolean isParteDomani() {
        return parteDomani;
    }


    private void setParteDomani(boolean parteDomani) {
        this.parteDomani = parteDomani;
    }


    public boolean isCambiaDomani() {
        return cambiaDomani;
    }


    private void setCambiaDomani(boolean cambiaDomani) {
        this.cambiaDomani = cambiaDomani;
    }


    public boolean isChiudere() {
        return chiudere;
    }


    private void setChiudere(boolean chiudere) {
        this.chiudere = chiudere;
    }


    public boolean isDaFare() {
        return daFare;
    }


    private void setDaFare(boolean daFare) {
        this.daFare = daFare;
    }


    private int getCodCompoPrecedente() {
        return codCompoPrecedente;
    }


    private void setCodCompoPrecedente(int codCompoPrecedente) {
        this.codCompoPrecedente = codCompoPrecedente;
    }


    public int getCodComposizione() {
        return codComposizione;
    }


    private void setCodComposizione(int codComposizione) {
        this.codComposizione = codComposizione;
    }


    public String getNote() {
        return note;
    }


    private void setNote(String note) {
        this.note = note;
    }


    public ArrayList<WrapCompoAccessorio> getListaAccessori() {
        return listaAccessori;
    }


    private void setListaAccessori(ArrayList<WrapCompoAccessorio> listaAccessori) {
        this.listaAccessori = listaAccessori;
    }


    private Dati getDati() {
        return dati;
    }


    private void setDati(Dati dati) {
        this.dati = dati;
    }

}// fine della classe