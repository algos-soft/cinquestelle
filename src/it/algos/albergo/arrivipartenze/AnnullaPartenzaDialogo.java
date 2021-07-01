package it.algos.albergo.arrivipartenze;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.ContoNavAnnullaPartenza;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.StampeObbLogica;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di annullamento di una partenza prevista.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-giu-2008 ore 09.21.46
 */
public class AnnullaPartenzaDialogo extends DialogoAnnullaConferma {

    private static final String NOME_CAMPO_CAMERA = "camera";

    private static final String NOME_CAMPO_DATA = "data di avvenuta partenza";

    private static final String NOME_CAMPO_PERSONE = "clienti partiti";

    private static final String NOME_CAMPO_CONTI = "conti";

    /* codice del periodo da annullare */
    private int codPeriodo;

    /* navigatore dei conti */
    private ContoNavAnnullaPartenza navConti;


    /**
     * Costruttore base con parametri.
     * <p/>
     *
     * @param codPeriodo codice del periodo da annullare
     */
    public AnnullaPartenzaDialogo(int codPeriodo) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setCodPeriodo(codPeriodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            this.setTitolo("Annullamento partenza");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


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
    @Override
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Pannello pan;

        try { // prova ad eseguire il codice

            /* crea il navigatore conti */
            ContoNavAnnullaPartenza nav = new ContoNavAnnullaPartenza();
            this.setNavConti(nav);

            /* crea e registra i campi del dialogo */
            this.creaCampi();

            /* aggiunge graficamente i campi */
            campo = this.getCampo(NOME_CAMPO_CAMERA);
            this.addCampo(campo);
            campo = this.getCampo(NOME_CAMPO_DATA);
            this.addCampo(campo);

            pan = PannelloFactory.orizzontale(this);
            pan.add(this.getCampo(NOME_CAMPO_PERSONE));
            pan.add(this.getCampo(NOME_CAMPO_CONTI));
            this.addPannello(pan);

            /* rimanda alla superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * </p>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* camera */
            campo = CampoFactory.testo(NOME_CAMPO_CAMERA);
            campo.setAbilitato(false);
            campo.setLarghezza(60);
            campo.setValore(this.getNomeCamera());
            this.addCampoCollezione(campo);

            /* data di partenza */
            campo = CampoFactory.data(NOME_CAMPO_DATA);
            campo.decora().legenda("(solo se diversa da quella prevista)");
            campo.setAbilitato(true);
            campo.setValore(this.getDataPartenzaProposta());
            this.addCampoCollezione(campo);

            /* elenco delle persone partite */
            nav = this.getNavPartiti();
            campo = CampoFactory.navigatore(NOME_CAMPO_PERSONE, nav);
            this.addCampoCollezione(campo);

            /* elenco dei relativi conti */
            nav = this.getNavConti();
            campo = CampoFactory.navigatore(NOME_CAMPO_CONTI, nav);
            this.addCampoCollezione(campo);

            /* carica i dati nei navigatori */
            this.updateNavPartiti();
            this.updateNavConti();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Ritorna il filtro che seleziona le presenze delle quali
     * annullare la partenza.
     * <p/>
     * - Seleziona tutte le presenze chiuse per partenza
     * per la camera, la data e l'azienda correnti
     *
     * @return il filtro
     */
    private Filtro getFiltroPresenze() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Date giorno;
        int codCamera;

        try {    // prova ad eseguire il codice

            giorno = this.getDataPartenza();
            codCamera = this.getCodCamera();
            filtro = PresenzaModulo.getFiltroPresenzePartite(giorno, codCamera);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }

    /**
     * Ritorna i codici delle presenze delle quali
     * annullare la partenza.
     * <p/>
     *
     * @return l'elenco dei codici
     */
    private int[] getCodiciPresenze() {
        /* variabili e costanti locali di lavoro */
        int[] codici=new int[0];
        Filtro filtro = null;
        Modulo modPresenza = PresenzaModulo.get();

        try {    // prova ad eseguire il codice
            filtro = this.getFiltroPresenze();
            codici = modPresenza.query().valoriChiave(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }



    /**
     * Ritorna il filtro che seleziona i conti relativi alle presenze
     * delle quali si vuole annullare la partenza.
     * <p/>
     *
     * @return il filtro
     */
    private Filtro getFiltroConti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtroConti = null;
        Filtro filtroPresenze;
        Modulo modPresenza;
        int[] presenze;
        int codConto;
        ArrayList<Integer> conti;
        Filtro filtro;

        try {    // prova ad eseguire il codice

            /* costruisce la lista delle presenze in partenza */
            modPresenza = PresenzaModulo.get();
            filtroPresenze = this.getFiltroPresenze();
            presenze = modPresenza.query().valoriChiave(filtroPresenze);

            /* spazzola le presenze e crea una lista univoca dei corrispondenti conti */
            conti = new ArrayList<Integer>();
            for (int codPres : presenze) {
                codConto = modPresenza.query().valoreInt(Presenza.Cam.conto.get(), codPres);
                if (!conti.contains(codConto)) {
                    conti.add(codConto);
                }// fine del blocco if
            }

            /* spazzola la lista dei conti e crea il filtro */
            filtroConti = new Filtro();
            //se non metto questo, se non ce ne sono me li da tutti!!
            filtroConti.add(FiltroFactory.codice(ContoModulo.get(), -1));
            for (int cod : conti) {
                filtro = FiltroFactory.codice(ContoModulo.get(), cod);
                filtroConti.add(Filtro.Op.OR, filtro);
            }


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroConti;
    }


    /**
     * Ritorna il filtro che seleziona i conti chiusi relativi alle presenze
     * delle quali si vuole annullare la partenza.
     * <p/>
     *
     * @return il filtro per isolare i conti chiusi
     */
    private Filtro getFiltroContiChiusi() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroConti;
        Filtro filtroChiusi;

        try { // prova ad eseguire il codice
            filtroConti = this.getFiltroConti();
            filtroChiusi = FiltroFactory.creaVero(Conto.Cam.chiuso.get());
            filtro = new Filtro();
            filtro.add(filtroConti);
            filtro.add(filtroChiusi);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;

    }


    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        int[] codPresenze;
        String testo;
        Modulo modPresenza = PresenzaModulo.get();

        try { // prova ad eseguire il codice


            /* controllo che si possano ancora movimentare le partenze */
            if (continua) {
                Date dataPartenza = this.getDataPartenza();
                codPresenze = this.getCodiciPresenze();
                for (int cod : codPresenze) {
                    int codAzienda = modPresenza.query().valoreInt(Presenza.Cam.azienda.get(), cod);
                    boolean movimentabile = StampeObbLogica.isPartenzaRegistrabile(dataPartenza, codAzienda);
                    if (!movimentabile) {
                        testo = ArriviPartenzeLogica.getMessaggioPartenzeNonConfermabili(codAzienda);
                        new MessaggioAvviso(testo);
                        continua = false;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

//            /**
//             * controlla che tutti i conti non abbiano addebiti
//             * se hanno degli addebiti avvisa che non verranno cancellati
//             * e chiede conferma
//             */
//            codPresenze = this.getPresenze();
//            codiciConto = PresenzaModulo.getContiAperti(codPresenze);
//
//            for (int cod : codiciConto) {
//                totAddebiti = ContoModulo.getTotAddebiti(cod, null, Progetto.getConnessione());
//                if (totAddebiti != 0) {
//                    continua = false;
//                    testo = "Attenzione! Esistono degli addebiti su conti aperti!\n";
//                    testo += "I conti che hanno degli addebiti non verranno cancellati.\n";
//                    testo += "Vuoi continuare ugualmente?";
//                    messaggio = new MessaggioDialogo(testo);
//                    if (messaggio.isConfermato()) {
//                        continua = true;
//                    }// fine del blocco if
//                    break;
//                }// fine del blocco if
//            }

            /* se ha passato i controli rimanda alla superclasse */
            if (continua) {
                super.eventoConferma();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna l'elenco dei codici di presenza per questo periodo.
     * <p/>
     *
     * @return i codici di presenza
     */
    public int[] getPresenze() {
        /* variabili e costanti locali di lavoro */
        int[] codici = new int[0];
        Modulo modulo;
        Filtro filtro;

        try { // prova ad eseguire il codice
            filtro = getFiltroPresenze();
            modulo = PresenzaModulo.get();
            codici = modulo.query().valoriChiave(filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Ritorna l'elenco dei codici dei conti da riaprire.
     * <p/>
     *
     * @return i codici di conto
     */
    public int[] getContiChiusi() {
        /* variabili e costanti locali di lavoro */
        int[] codici = new int[0];
        Modulo modulo;
        Filtro filtro;

        try { // prova ad eseguire il codice
            filtro = getFiltroContiChiusi();
            modulo = ContoModulo.get();
            codici = modulo.query().valoriChiave(filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codici;

    }


    /**
     * Ritorna il codice della camera.
     * <p/>
     *
     * @return il codice della camera
     */
    private int getCodCamera() {
        /* variabili e costanti locali di lavoro */
        int codCamera = 0;
        Modulo modPeriodo;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            codCamera = modPeriodo.query()
                    .valoreInt(Periodo.Cam.camera.get(), this.getCodPeriodo());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codCamera;
    }


    /**
     * Ritorna la data di partenza proposta.
     * <p/>
     * Viene recuperata dal periodo
     *
     * @return il codice della camera
     */
    private Date getDataPartenzaProposta() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Modulo modPeriodo;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            data = modPeriodo.query()
                    .valoreData(Periodo.Cam.partenzaPrevista.get(), this.getCodPeriodo());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna il nome della camera.
     * <p/>
     *
     * @return il nome della camera
     */
    private String getNomeCamera() {
        /* variabili e costanti locali di lavoro */
        String nomeCamera = "";
        int codCamera;
        Modulo modCamera;

        try {    // prova ad eseguire il codice
            modCamera = CameraModulo.get();
            codCamera = this.getCodCamera();
            nomeCamera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nomeCamera;
    }


    /**
     * Ritorna la data di partenza impostata nel dialogo.
     * <p/>
     *
     * @return la data di partenza
     */
    private Date getDataPartenza() {
        return this.getData(NOME_CAMPO_DATA);
    }


    /**
     * Assegna la data di partenza al campo del dialogo.
     * <p/>
     *
     * @param data la data di partenza
     */
    private void setDataPartenza(Date data) {
        this.setValore(NOME_CAMPO_DATA, data);
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo cambiato
     */
    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* se cambia la data */
            if (campo.equals(this.getCampo(NOME_CAMPO_DATA))) {
                this.updateNavPartiti();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiorna il navigatore Partiti.
     * <p/>
     */
    private void updateNavPartiti() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            nav = this.getNavPartiti();
            filtro = this.getFiltroPresenze();
            nav.setFiltroBase(filtro);
            nav.setFiltroCorrente(null);
            nav.aggiornaLista();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Aggiorna il navigatore Conti.
     * <p/>
     */
    private void updateNavConti() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            nav = this.getNavConti();
            filtro = this.getFiltroConti();
            nav.setFiltroBase(filtro);
            nav.setFiltroCorrente(null);
            nav.aggiornaLista();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il navigatore che visualizza le presenze partite.
     * <p/>
     *
     * @return il navigatore
     */
    private Navigatore getNavPartiti() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            nav = PresenzaModulo.get().getNavigatore(Presenza.Nav.annullaPartenza.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }




    /**
     * Ritorna il codice del periodo del quale annullare la partenza.
     * <p/>
     *
     * @return il codice del periodo
     */
    public int getCodPeriodo() {
        return codPeriodo;
    }


    private void setCodPeriodo(int codPeriodo) {
        this.codPeriodo = codPeriodo;
    }

    private ContoNavAnnullaPartenza getNavConti() {
        return navConti;
    }


    private void setNavConti(ContoNavAnnullaPartenza nav) {
        this.navConti = nav;
    }




}// fine della classe