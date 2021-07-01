package it.algos.albergo.arrivipartenze;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoLogica;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.ContoNavPartenze;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.StampeObbLogica;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;

import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di conferma di un arrivo. </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-ott-2007 ore 19.21.46
 */
public class ConfermaPartenzaDialogo extends DialogoAnnullaConferma {

    /* codice del periodo da confermare */

    private int codPeriodo;

    private boolean interrotto;

    private MouseListener listenerPresenze;

    private ContoNavPartenze navConti;


    private static final String NOME_CAMPO_DATA = "partenza";

    private static final String NOME_CAMPO_CAMERA = "camera";

    private static final String NOME_CAMPO_PERSONE = "persone";

    private static final String NOME_CAMPO_CONTI = "conti";


    /**
     * Costruttore base con parametri.
     * <p/>
     *
     * @param codice del periodo da confermare
     */
    public ConfermaPartenzaDialogo(int codice) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.setCodPeriodo(codice);
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
            this.setTitolo("Conferma partenza");
            this.getBottoneAnnulla().setText("Ritorna");
            this.getBottoneConferma().setText("Conferma partenza");

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
        Campo campo;
        Pannello pan;

        try { // prova ad eseguire il codice

            /* crea il navigatore conti */
            ContoNavPartenze nav = new ContoNavPartenze(this);
            this.setNavConti(nav);

            /* crea e registra i campi del dialogo */
            this.creaCampi();

            /* regola i valori iniziali dei campi del dialogo */
            this.regolaValoriIniziali();

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            campo = this.getCampo(NOME_CAMPO_DATA);
            pan.add(campo);
            campo = this.getCampo(NOME_CAMPO_CAMERA);
            pan.add(campo);
            this.addComponente(pan.getPanFisso());

            /* pannello navigatori */
            pan = PannelloFactory.orizzontale(this);

            pan.add(this.getCampo(NOME_CAMPO_PERSONE));
            pan.add(this.getCampo(NOME_CAMPO_CONTI));
            this.addComponente(pan.getPanFisso());

            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */

    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    @Override
    public void avvia() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            super.avvia();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo avvia

    /**
     * Regola i valori iniziali dei campi del dialogo.
     * <p/>
     */
    private void regolaValoriIniziali() {
        /* variabili e costanti locali di lavoro */
        int codCamera = 0;
        Date dataPartenza = null;
        boolean continua;
        int codPeriodo;
        Modulo modPeriodo = null;
        Query query;
        Filtro filtro;
        Dati dati;

        try {    // prova ad eseguire il codice

            codPeriodo = this.getCodPeriodo();
            continua = (codPeriodo > 0);

            /* recupera modulo Periodo */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            /* costruisce la query sul periodo e recupera i dati */
            if (continua) {
                query = new QuerySelezione(modPeriodo);
                filtro = FiltroFactory.codice(modPeriodo, codPeriodo);
                query.addCampo(Periodo.Cam.camera.get());
                query.addCampo(Periodo.Cam.partenzaPrevista.get());
                query.setFiltro(filtro);
                dati = modPeriodo.query().querySelezione(query);
                codCamera = dati.getIntAt(Periodo.Cam.camera.get());
                dataPartenza = dati.getDataAt(Periodo.Cam.partenzaPrevista.get());
                dati.close();
            }// fine del blocco if

            /* registra i dati nel dialogo */
            if (continua) {
                this.setCodCamera(codCamera);
                this.setDataPartenza(dataPartenza);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }

    /**
     * Creazione dei campi.
     * </p>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* data partenza */
            campo = CampoFactory.data(NOME_CAMPO_DATA);
            this.addCampoCollezione(campo);

            /* camera */
            campo = CampoFactory.comboLinkSel(NOME_CAMPO_CAMERA);
            campo.setNomeModuloLinkato(Camera.NOME_MODULO);
            campo.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            campo.setUsaNuovo(false);
            campo.setLarScheda(70);
            campo.setAbilitato(false);
            this.addCampoCollezione(campo);

            /* elenco delle persone presenti nella camera */
            nav = PresenzaModulo.get().getNavigatore(Presenza.Nav.clienteparentela.get());
            nav.setFiltroBase(this.getFiltroPresenze());
            nav.setFiltroCorrente(null);
            campo = CampoFactory.navigatore(NOME_CAMPO_PERSONE, nav);
            this.addCampoCollezione(campo);
            listenerPresenze = new MouseListenerPersone();
            nav.getLista().getTavola().addMouseListener(listenerPresenze);

            /* elenco dei conti */
            nav = this.getNavConti();
            nav.setFiltroBase(this.getFiltroConti());
            nav.setFiltroCorrente(null);
            campo = CampoFactory.navigatore(NOME_CAMPO_CONTI, nav);
            this.addCampoCollezione(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia

    /**
     * Ritorna il filtro per le presenze da mostrare nel navigatore.
     * <p/>
     * Controlla se nella camera ci sono presenze "provvisorie" (con flag partenzaOggi acceso).
     * Se ce ne sono, seleziona solo quelle
     * Altrimenti, seleziona tutte le presenze nella camera
     *
     * @return il filtro per le presenze da visualizzare
     */
    public Filtro getFiltroPresenze() {
        /* variabili e costanti lo cali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice

//            int codCamera = this.getCodCamera();
//            filtro = PresenzaModulo.getFiltroAperteSmart(codCamera);

            int codPeriodo = this.getCodPeriodo();
            filtro = new Filtro();
            filtro.add(PresenzaModulo.getFiltroPresenzePeriodo(codPeriodo));
            filtro.add(FiltroFactory.creaFalso(Presenza.Cam.chiuso));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }

    /**
     * Ritorna il filtro per i conti da visualizzare
     * <p/>
     * Seleziona tutti i conti relativi alle presenze in partenza
     *
     * @return il filtro per i conti
     */
    private Filtro getFiltroConti() {
        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//        boolean continua;
//        Filtro filtroConto;
//        Modulo modPresenze;
//        Modulo modConti = null;
//        ArrayList lista = null;
//        int codConto;
//        Filtro filtroPers = null;
//
//        try { // prova ad eseguire il codice
//            modPresenze = PresenzaModulo.get();
//            continua = (modPresenze != null);
//
//            if (continua) {
//                modConti = ContoModulo.get();
//                continua = (modConti != null);
//            }// fine del blocco if
//
//            if (continua) {
//                filtroPers = this.getFiltroPresenze();
//            }// fine del blocco if
//
//            if (continua) {
//                lista = modPresenze.query().valoriDistinti(Presenza.Cam.conto.get(), filtroPers);
//                continua = (lista != null) && (lista.size() > 0);
//            }// fine del blocco if
//
//            if (continua) {
//                filtro = new Filtro();
//                for (Object ogg : lista) {
//                    codConto = Libreria.getInt(ogg);
//                    filtroConto = FiltroFactory.codice(modConti, codConto);
//                    filtro.add(Filtro.Op.OR, filtroConto);
//                } // fine del ciclo for-each
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;

        /* valore di ritorno */
        return ContoModulo.getFiltroContiPeriodo(this.getCodPeriodo());
    }

    private Filtro getFiltroContiAperti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroAperti;
        Filtro filtroConti;

        try { // prova ad eseguire il codice
            filtro = new Filtro();

            filtroConti = this.getFiltroConti();
            if (filtroConti != null) {
                filtro.add(filtroConti);
            }// fine del blocco if

            filtroAperti = FiltroFactory.creaFalso(Conto.Cam.chiuso.get());
            filtro.add(filtroAperti);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }

    /**
     * Ritorna i codici delle presenze in partenza.
     * <p/>
     *
     * @return i codici delle presenze in partenza
     */
    private int[] getCodPresenzePartenza() {
        /* variabili e costanti locali di lavoro */
        int[] codici = new int[0];
        Filtro filtro;
        Modulo modPresenza;

        try {    // prova ad eseguire il codice
            modPresenza = PresenzaModulo.get();
            filtro = this.getFiltroPresenze();
            codici = modPresenza.query().valoriChiave(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }

    private Navigatore getNavPersone() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        boolean continua;
        Campo campo;

        try { // prova ad eseguire il codice

            campo = this.getCampo(NOME_CAMPO_PERSONE);
            continua = (campo != null);

            if (continua) {
                nav = campo.getNavigatore();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }

    private ContoNavPartenze getNavConti() {
        return navConti;
    }


    private void setNavConti(ContoNavPartenze nav) {
        this.navConti = nav;
    }

    /**
     * Determina se il dialogo e' confermabile o registrabile.
     * <p/>
     *
     * @return true se confermabile / registrabile
     */
    @Override
    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;

        try { // prova ad eseguire il codice
            confermabile = super.isConfermabile();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }

    /**
     * Invocato quando si preme il bottone conferma.
     * <p/>
     */
    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int[] lista = null;
        Filtro filtro;
        ContoModulo mod;
        ContoLogica.Chiusura chiusura;
        Date dataPartenza;
        ArrayList<Integer> aziende = new ArrayList<Integer>();
        boolean registrabile;

        try { // prova ad eseguire il codice
            mod = ContoModulo.get();
            continua = (mod != null);


            /**
             * controlla che non ci siano partenze precedenti
             * ancora da confermare ed eventualmente chiede
             * conferma
             */
            if (continua) {
                continua = ArriviPartenzeLogica.checkPartenze(this.getCodPeriodo());
            }// fine del blocco if

            /**
             * Recupera l'elenco univoco delle aziende relative alle presenze in partenza
             * (dovrebbe essere sempre una sola)
             */
            if (continua) {
                Modulo modPresenza = PresenzaModulo.get();
                int[] codPres = this.getCodPresenzePartenza();
                for (int cod : codPres) {
                    int codAz = modPresenza.query().valoreInt(Presenza.Cam.azienda.get(), cod);
                    if (!aziende.contains(codAz)) {
                        aziende.add(codAz);
                    }// fine del blocco if
                }
            }// fine del blocco if

            /**
             * Controlla che i registri non siano stati
             * già stampati per la data di partenza
             */
            if (continua) {
                dataPartenza = this.getDataPartenza();
                for (int cod : aziende) {
                    registrabile = StampeObbLogica.isPartenzaRegistrabile(dataPartenza, cod);
                    if (!registrabile) {
                        String testo
                                = ArriviPartenzeLogica.getMessaggioPartenzeNonConfermabili(cod);
                        new MessaggioAvviso(testo);
                        continua = false;
                        break;
                    }// fine del blocco if
                }
            }// fine del blocco if

            if (continua) {
                filtro = this.getFiltroContiAperti();
                lista = mod.query().valoriChiave(filtro);
                continua = (lista != null);
            }// fine del blocco if

            if (continua) {

                for (int cod : lista) {
                    chiusura = mod.chiusuraConto(cod, true);
                    if (chiusura == ContoLogica.Chiusura.interrotto) {
                        this.setInterrotto(true);
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

                this.chiudiDialogo();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    /**
     * Ritorna il codice della camera.
     * <p/>
     *
     * @return codice camera
     */
    public int getCodCamera() {
        /* variabili e costanti locali di lavoro */
        int codCamera = 0;
        int codPeriodo;
        boolean continua;
        Modulo modPeriodo = null;

        try { // prova ad eseguire il codice

            codPeriodo = this.getCodPeriodo();
            continua = (codPeriodo > 0);

            /* recupera modulo Periodo */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            /* recupera il codice della camera */
            if (continua) {
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCamera;
    }


    /**
     * Doppio clic su una persona.
     * <p/>
     *
     * @param id l'id della presenza cliccata
     */
    private void doubleClickPresenza(int id) {
        /* variabili e costanti locali di lavoro */
        int codCli;
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = PresenzaModulo.get();
            codCli = mod.query().valoreInt(Presenza.Cam.cliente.get(), id);
            mod = ClienteAlbergoModulo.get();
            mod.presentaRecord(codCli);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Chiude un conto.
     * <p/>
     *
     * @param id l'id del conto da chiudere
     */
    public void chiudiConto(int id) {
        try {    // prova ad eseguire il codice
            ContoModulo mod = ContoModulo.get();
            ContoLogica.Chiusura c = mod.chiusuraConto(id,false);
            if (c==ContoLogica.Chiusura.confermato) {
                this.getNavConti().aggiornaLista();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Apre un conto in scheda.
     * <p/>
     *
     * @param id l'id del conto da far vedere
     */
    public void vaiConto(int id) {
        /* variabili e costanti locali di lavoro */
        boolean confermato;

        confermato = ContoModulo.get().presentaRecord(id);
        if (confermato) {
            this.getNavConti().aggiornaLista();
        }// fine del blocco if

    }

    /**
     * Invocato quando si preme il bottone annulla.
     * <p/>
     */
    @Override
    protected void eventoAnnulla() {
        this.setInterrotto(true);
        super.eventoAnnulla();
    }

    /**
     * ritorna true se il dialogo e' stato confermato
     */
    @Override
    public boolean isConfermato() {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;

        try { // prova ad eseguire il codice
            confermato = (!this.isInterrotto());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;

    }

    @Override
    protected boolean chiudiDialogo() {
        /* variabili e costanti locali di lavoro */
        boolean chiuso;

        chiuso=  super.chiudiDialogo();

        /**
         * alla chiusura del dialogo
         * rimuove i listeners che aveva aggiunto ai navigatori
         * (i navigatori sono sempre gli stessi!)
         */
        if (chiuso) {
            JTable table;

            table = this.getNavPersone().getLista().getTavola();
            table.removeMouseListener(listenerPresenze);

//            table = this.getNavConti().getLista().getTavola();
//            table.removeMouseListener(listenerConti);

        }// fine del blocco if

        /* valore di ritorno */
        return chiuso;
    }

    /**
     * Assegna il codice camera.
     * <p/>
     *
     * @param codCamera codice della camera
     */
    private void setCodCamera(int codCamera) {
        this.setValore(NOME_CAMPO_CAMERA, codCamera);
    }

    /**
     * Assegna la data di partenza.
     * <p/>
     *
     * @param data data di partenza
     */
    private void setDataPartenza(Date data) {
        this.setValore(NOME_CAMPO_DATA, data);
    }

    public int getCodPeriodo() {
        return codPeriodo;
    }

    public Date getDataPartenza() {
        return this.getData(NOME_CAMPO_DATA);
    }

    private void setCodPeriodo(int codPeriodo) {
        this.codPeriodo = codPeriodo;
    }

    private boolean isInterrotto() {
        return interrotto;
    }

    private void setInterrotto(boolean interrotto) {
        this.interrotto = interrotto;
    }

    /**
     * Listener del doppio clic sulla lista persone</p>
     */
    private final class MouseListenerPersone extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() >= 2) {
                int id = getNavPersone().getLista().getChiaveSelezionata();
                doubleClickPresenza(id);
            }
        }
    } // fine della classe 'interna'




}// fine della classe