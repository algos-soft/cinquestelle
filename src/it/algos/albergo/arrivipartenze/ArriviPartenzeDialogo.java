package it.algos.albergo.arrivipartenze;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.arrivipartenze.riepilogo.RiepilogoNew;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.evento.CambioDataAz;
import it.algos.albergo.evento.CambioDataEve;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.prenotazione.periodo.PeriodoNavArrivi;
import it.algos.albergo.prenotazione.periodo.PeriodoNavCambi;
import it.algos.albergo.prenotazione.periodo.PeriodoNavDialogo;
import it.algos.albergo.prenotazione.periodo.PeriodoNavPartenze;
import it.algos.albergo.preventivo.PreventivoDialogo;
import it.algos.base.bottone.BottoneAzione;
import it.algos.base.bottone.BottoneFactory;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.costante.CostanteColore;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.evento.db.DbTriggerAz;
import it.algos.base.evento.db.DbTriggerEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloMemoria;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe astratta per i moduli di creazione dei singoli files. </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-ott-2007 ore 19.21.46
 */
public class ArriviPartenzeDialogo extends DialogoBase {

    private static final String NOME_CAMPO_DAL = "dal";

    private static final String NOME_CAMPO_AL = "al";

    private PeriodoNavDialogo navArrivi;

    private PeriodoNavDialogo navPartenze;

    private ModuloMemoria memoria;

    /**
     * JLabel informativa azienda
     */
    private JLabel infoLabel;

    /**
     * Pannello comandi manuali
     */
    private Pannello panComandi;

    /**
     * Pannello specializzato contenente il riepilogo presenze
     */
    private RiepilogoNew riepilogo;

    /**
     * Costruttore senza parametri.
     * <p/>
     */
    public ArriviPartenzeDialogo() {
        this(null);
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore base con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public ArriviPartenzeDialogo(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
        Modulo mod;
        JLabel label;
        Modello modello;
        Pannello pan;
        RiepilogoNew riepilogo;

        try { // prova ad eseguire il codice

            this.setTitolo(ArriviPartenze.TITOLO_FINESTRA);
            this.getDialogo().setModal(false);

            /* aggiunge i bottoni al dialogo */
            this.addBottoneStampa();
            this.addBottoneChiudi();

            this.creaCampi();
            this.creaNavigatori();

            /**
             * Aggiunge la label informativa sull'azienda in uso
             */
            label = new JLabel();
            label.setForeground(CostanteColore.BLU);
            this.setInfoLabel(label);
            this.getPannelloComandi().add(label);

            /* crea il modulo memoria per i cambi */
            this.creaMemoria();

            /* crea il pannello con i comandi manuali */
            pan = this.creaPanComandi();
            this.setPanComandi(pan);

            /* crea e registra il pannello Riepilogo Presenze */
            riepilogo = new RiepilogoNew();
            this.setRiepilogo(riepilogo);

            /* si registra presso il modulo albergo per  */
            /* essere informato quando cambia l' azienda */
            mod = Progetto.getModulo(Albergo.NOME_MODULO);
            if (mod != null) {
                mod.addListener(AlbergoModulo.Evento.cambioAzienda, new AzioneCambioAzienda());
                mod.addListener(AlbergoModulo.Evento.cambioData, new AzioneCambioData());
            }// fine del blocco if

            /* si registra presso il modulo Prenotazione per  */
            /* essere informato quando viene creato/modificato/eliminato un record */
            mod = PrenotazioneModulo.get();
            if (mod != null) {
                modello = mod.getModello();
                modello.addListener(Modello.Evento.trigger, new AzPrenotazioni());
            }// fine del blocco if

            /* si registra presso il modulo Periodo per  */
            /* essere informato quando viene creato/modificato/eliminato un record */
            mod = PeriodoModulo.get();
            if (mod != null) {
                modello = mod.getModello();
                modello.addListener(Modello.Evento.trigger, new AzPeriodi());
            }// fine del blocco if

            /* si registra presso il modulo Clienti per  */
            /* essere informato quando viene creato/modificato/eliminato un record */
            mod = ClienteAlbergoModulo.get();
            if (mod != null) {
                modello = mod.getModello();
                modello.addListener(Modello.Evento.trigger, new AzClienti());
            }// fine del blocco if


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
        Navigatore nav;
        CompNav cnav;
        Pannello pan;

        try { // prova ad eseguire il codice

            /* costruzione grafica */

            /* pannello con le 2 date */
            pan = PannelloFactory.orizzontale(null);
            pan.add(this.getCampo(NOME_CAMPO_DAL));
            pan.add(this.getCampo(NOME_CAMPO_AL));
            this.addPannello(pan);

            /* pannello con Navigatore Arrivi e Navigatore Partenze */
            pan = PannelloFactory.orizzontale(null);
            nav = this.getNavArrivi();
            cnav = new CompNav(nav, "Arrivi");
            pan.add(cnav);
            nav = this.getNavPartenze();
            cnav = new CompNav(nav, "Partenze");
            pan.add(cnav);
            this.addPannello(pan);

            /* pannello orizzontale con nav cambi, riepilogo e comandi */
            pan = PannelloFactory.orizzontale(null);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            nav = this.getNavCambi();
            cnav = new CompNav(nav, "Cambi camera");
            pan.add(cnav);
            pan.add(this.getRiepilogo());
            pan.add(this.getPanComandi());
            this.addPannello(pan);


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
        Date dataOggi;

        try { // prova ad eseguire il codice

            super.avvia();

            dataOggi = AlbergoLib.getDataProgramma();
            this.setData1(dataOggi);
            this.setData2(dataOggi);
            this.aggiornaDialogo();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo avvia


    /**
     * Pannello coi bottoni per le operazioni manuali.
     * </p>
     * @return il pannello comandi creato
     */
    private Pannello creaPanComandi() {
        /* variabili e costanti locali di lavoro */
        Pannello panTot = null;
        BottoneAzione ba;
        int wBot=130;

        try { // prova ad eseguire il codice

            panTot = PannelloFactory.verticale(null);
            panTot.setUsaGapFisso(true);
            panTot.setGapPreferito(8);
            panTot.creaBordo("Operazioni manuali");

            panTot.add(Box.createVerticalGlue());

            ba = BottoneFactory.crea(this, "preventivo", "Preventivo");
            Lib.Comp.setPreferredWidth(ba, wBot);
            Lib.Comp.bloccaDim(ba);
            panTot.add(ba);

            ba = BottoneFactory.crea(this, "arrivo", "Arrivo");
            Lib.Comp.setPreferredWidth(ba, wBot);
            Lib.Comp.bloccaDim(ba);
            panTot.add(ba);

            ba = BottoneFactory.crea(this, "partenza", "Partenza");
            Lib.Comp.setPreferredWidth(ba, wBot);
            Lib.Comp.bloccaDim(ba);
            panTot.add(ba);

            ba = BottoneFactory.crea(this, "cambio", "Cambio");
            Lib.Comp.setPreferredWidth(ba, wBot);
            Lib.Comp.bloccaDim(ba);
            panTot.add(ba);

            panTot.add(Box.createVerticalGlue());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return panTot;
    }


    /**
     * Presenta un dialogo per preventivo / prenotazione.
     * <p/>
     */
    public void preventivo(ActionEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        PrenotazioneModulo mod;
        String titolo = "";
        String messaggio = "";
        Date oggi = null;
        PreventivoDialogo preventivo = null;

        try {    // prova ad eseguire il codice

            mod = PrenotazioneModulo.get();
            continua = (mod != null);

            if (continua) {
                preventivo = new PreventivoDialogo();
            }// fine del blocco if

            if (continua) {
                if (preventivo.isConfermato()) {
                    this.aggiornaDialogo();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Movimentazione manuale di un arrivo.
     * <p/>
     *
     * @param unEvento l'evento
     */
    public void arrivo(ActionEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito;

        try { // prova ad eseguire il codice
            eseguito = ArriviPartenzeLogica.arrivoManuale();
            if (eseguito) {
                this.aggiornaDialogo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Movimentazione manuale di una partenza.
     * <p/>
     *
     * @param unEvento l'evento
     */
    public void partenza(ActionEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito;

        try { // prova ad eseguire il codice
            eseguito = ArriviPartenzeLogica.partenzaManuale();
            if (eseguito) {
                this.aggiornaDialogo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Movimentazione manuale di un cambio.
     * <p/>
     *
     * @param unEvento l'evento
     */
    public void cambio(ActionEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito;

        try { // prova ad eseguire il codice
            eseguito = ArriviPartenzeLogica.cambioManuale();
            if (eseguito) {
                this.aggiornaDialogo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione del modulo memoria per i cambi.
     * </p>
     */
    private void creaMemoria() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        ArrayList<Campo> lista;
        ModuloMemoria mem;
        Navigatore nav;

        try { // prova ad eseguire il codice
            lista = new ArrayList<Campo>();

            campo = CampoFactory.checkBox(Nomi.effettuato.get());
            campo.setVisibileVistaDefault(true);
            lista.add(campo);
            campo = CampoFactory.data(Nomi.data.get());
            campo.setVisibileVistaDefault(true);
            lista.add(campo);
            campo = CampoFactory.testo(Nomi.cameraProvenienza.get());
            campo.setTitoloColonna("dalla");
            campo.setLarLista(50);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campo.setVisibileVistaDefault(true);
            lista.add(campo);
            campo = CampoFactory.testo(Nomi.cameraDestinazione.get());
            campo.setTitoloColonna("alla");
            campo.setLarLista(50);
            campo.setAllineamentoLista(SwingConstants.CENTER);
            campo.setVisibileVistaDefault(true);
            lista.add(campo);
            campo = CampoFactory.testo(Nomi.cliente.get());
            campo.setLarLista(120);
            campo.setVisibileVistaDefault(true);
            lista.add(campo);
            campo = CampoFactory.intero(Nomi.persone.get());
            campo.setTitoloColonna("pers");
            campo.setRenderer(new RendererNumCambi(campo));
            campo.setVisibileVistaDefault(true);
            lista.add(campo);
            campo = CampoFactory.intero(Nomi.prenotazione.get());
            campo.setVisibileVistaDefault(false);
            lista.add(campo);
            campo = CampoFactory.intero(Nomi.codPeriodo.get());
            campo.setVisibileVistaDefault(false);
            lista.add(campo);
            campo = CampoFactory.testo(Nomi.preparazione.get());
            campo.setVisibileVistaDefault(false);
            lista.add(campo);
            campo = CampoFactory.testo(Nomi.note.get());
            campo.setVisibileVistaDefault(false);
            lista.add(campo);


            mem = new ModuloMemoria(lista, false);
            this.setMemoria(mem);
            nav = new PeriodoNavCambi(mem, this);
            mem.addNavigatoreCorrente(nav);

            mem.inizializza();
            mem.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione dei campi.
     * </p>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
            campo = CampoFactory.data(NOME_CAMPO_DAL);
            campo.decora().etichettaSinistra();
            this.addCampoCollezione(campo);

            campo = CampoFactory.data(NOME_CAMPO_AL);
            campo.decora().etichettaSinistra();
            this.addCampoCollezione(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }




//    /**
//     * Aggunta dei pannelli manuale e prospetto.
//     * </p>
//     * Crea un pannello per affiancare i due pannelli <br>
//     */
//    private void addManualeProspetto() {
//        /* variabili e costanti locali di lavoro */
//        boolean continua;
//        Date dataIni;
//        Date dataEnd;
//        String inizio = "";
//        String fine = "";
//        Pannello panDoppio;
//        Pannello panMan;
//        Pannello panPro = null;
//        String titolo;
//
//        try { // prova ad eseguire il codice
//            panMan = this.creaPanComandi();
//
//            dataIni = this.getData1();
//            dataEnd = this.getData2();
//            continua = (Lib.Clas.isValidi(dataIni, dataEnd));
//
//            if (continua) {
//                inizio = Lib.Data.getDataBrevissima(dataIni);
//                fine = Lib.Data.getDataBrevissima(dataEnd);
//
//                panPro = RiepilogoLogica.getRiepilogo(dataIni, dataEnd);
//                continua = (panPro != null);
//
//            } // fine del blocco if
//
//            if (continua) {
//
//                if (inizio.equals(fine)) {
//                    titolo = "Riepilogo " + inizio;
//                } else {
//                    titolo = "Riepilogo " + inizio + " / " + fine;
//                } // fine del blocco if-else
//                panPro.creaBordo(titolo);
//
//                panDoppio = this.getManualeProspetto();
//                panDoppio.removeAll();
//                panDoppio.add(panMan);
//                panDoppio.add(panPro);
//
//                this.addPannello(panDoppio);
//            } // fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Creazione dei Navigatori.
     * </p>
     * Regola qui l'altezza delle liste, per essere sicuro che siano alte uguali <br>
     */
    private void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        int righe = 10;

        try { // prova ad eseguire il codice
            nav = this.setNavArrivi(new PeriodoNavArrivi(this));
            nav.setRigheLista(righe);
            nav.inizializza();
            nav.avvia();

            nav = this.setNavPartenze(new PeriodoNavPartenze(this));
            nav.setRigheLista(righe);
            nav.inizializza();
            nav.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna la label descrittiva dell'azienda corrente.
     * <p/>
     */
    private void aggiornaLabelAzienda () {
        /* variabili e costanti locali di lavoro */
        AlbergoModulo mod;
        JLabel label;
        String descAzienda;

        try {    // prova ad eseguire il codice
            /* aggiorna la stringa info azienda nel dialogo */
            mod = AlbergoModulo.get();
            if (mod != null) {
                label = this.getInfoLabel();
                if (label != null) {
                    descAzienda = mod.getDescAziendaAttiva();
                    label.setText(descAzienda);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

}





    /**
     * Aggiorna il contenuto dei componenti visualizzati.
     * <p/>
     * Solo se il dialogo è visibile
     */
    private void aggiornaDialogo() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Date data1;
        Date data2;
        Filtro filtro;
        Navigatore nav;
        Ordine ordine;
        Modulo modPeriodo;
        RiepilogoNew riepilogo;

        try {    // prova ad eseguire il codice

            /* controlla se il dialogo è visibile */
            continua = this.isVisible();

            if (continua) {
                /* recupera le date dal dialogo */
                data1 = this.getData1();
                data2 = this.getData2();

                /* aggiorna arrivi */
                filtro = PeriodoModulo.getFiltroArrivi(data1, data2);
                ordine = PeriodoModulo.getOrdineArrivi();
                nav = this.getNavArrivi();
                nav.setFiltroCorrente(filtro);
                nav.getLista().setOrdine(ordine);
                nav.aggiornaLista();

                /* aggiorna partenze */
                filtro = PeriodoModulo.getFiltroPartenze(data1, data2);
                ordine = PeriodoModulo.getOrdinePartenze();
                nav = this.getNavPartenze();
                nav.setFiltroCorrente(filtro);
                nav.getLista().setOrdine(ordine);
                nav.aggiornaLista();

                /* aggiorna cambi */
                modPeriodo = PeriodoModulo.get();
                filtro = PeriodoModulo.getFiltroCambiUscita(data1, data2);
                int[] periodi = modPeriodo.query().valoriChiave(filtro);
                this.riempiNavCambi(periodi);
                nav = this.getNavCambi();
                nav.aggiornaLista();

                /* aggiorna il riepilogo presenze */
                riepilogo = this.getRiepilogo();
                riepilogo.aggiorna(data1,data2);


            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Riempie i dati del navigatore dei cambi.
     * <p/>
     *
     * @param periodi elenco dei periodi con cambio in uscita
     */
    private void riempiNavCambi(int[] periodi) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modPeriodo;
        Modulo modCambi = null;

        try { // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();
            continua = modPeriodo != null;

            if (continua) {
                modCambi = this.getMemoria();
                continua = modCambi != null;
            }// fine del blocco if

            if (continua) {
                modCambi.query().eliminaRecords();
            }// fine del blocco if

            if (continua) {
                for(int codPeriodo : periodi){
                    this.addCambio(codPeriodo);
                }
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    /**
     * Riempie il navigatore dei cambi.
     * <p/>
     * @param codPeriodo1 codice del periodo (con cambio in uscita) da aggiungere
     */
    private void addCambio(int codPeriodo1) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modPeriodi;
        Query query;
        Filtro filtro;
        Dati dati;
        int codPeriodo2=0;
        boolean effettuato = false;
        boolean chiusoProvenienza=false;
        boolean arrivatoDestinazione=false;
        Date data = null;
        String cameraProvenienza = "";
        String cameraDestinazione = "";
        String cliente = "";
        int persone = 0;
        int codPrep = 0;
        String prep = "";
        String note = "";
        Modulo mem;
        int codMemoria;
        int codCameraProv = 0;
        int codCameraDest = 0;
        int codPrenotazione = 0;
        int codCliente = 0;
        Modulo modCamera;
        Modulo modPren;
        Modulo modCliente;
        Modulo modPreparazione;

        try { // prova ad eseguire il codice

            modPeriodi = PeriodoModulo.get();
            continua = (modPeriodi!=null);

            /* recupera i dati relativi al periodo di provenienza */
            if (continua) {
                query = new QuerySelezione(modPeriodi);
                filtro = FiltroFactory.codice(modPeriodi, codPeriodo1);
                query.setFiltro(filtro);
                query.addCampo(Periodo.Cam.linkDestinazione.get());
                query.addCampo(Periodo.Cam.partito.get());
                query.addCampo(Periodo.Cam.partenzaPrevista.get());
                query.addCampo(Periodo.Cam.camera.get());
                query.addCampo(Periodo.Cam.prenotazione.get());
                query.addCampo(Periodo.Cam.persone.get());
                dati = modPeriodi.query().querySelezione(query);

                codPeriodo2 = dati.getIntAt(Periodo.Cam.linkDestinazione.get());
                chiusoProvenienza = dati.getBoolAt(Periodo.Cam.partito.get());
                data = dati.getDataAt(Periodo.Cam.partenzaPrevista.get());
                codCameraProv = dati.getIntAt(Periodo.Cam.camera.get());
                codPrenotazione = dati.getIntAt(Periodo.Cam.prenotazione.get());
                persone = dati.getIntAt(Periodo.Cam.persone.get());

                dati.close();
                continua = (codPeriodo2>0);

            }// fine del blocco if


            /* recupera i dati relativi al periodo di destinazione */
            if (continua) {
                query = new QuerySelezione(modPeriodi);
                filtro = FiltroFactory.codice(modPeriodi, codPeriodo2);
                query.setFiltro(filtro);
                query.addCampo(Periodo.Cam.arrivato.get());
                query.addCampo(Periodo.Cam.camera.get());
                query.addCampo(Periodo.Cam.preparazione.get());
                query.addCampo(Periodo.Cam.note.get());
                dati = modPeriodi.query().querySelezione(query);

                arrivatoDestinazione = dati.getBoolAt(Periodo.Cam.arrivato.get());
                codCameraDest = dati.getIntAt(Periodo.Cam.camera.get());
                codPrep = dati.getIntAt(Periodo.Cam.preparazione.get());
                note = dati.getStringAt(Periodo.Cam.note.get());

                dati.close();

            }// fine del blocco if


            /* verifica se il cambio è già stato effettuato */
            if (continua) {
                effettuato = (chiusoProvenienza && arrivatoDestinazione);
            }// fine del blocco if

            /* recupera le stringhe delle due camere */
            if (continua) {
                modCamera = CameraModulo.get();
                if (modCamera != null) {
                    cameraProvenienza = modCamera.query()
                            .valoreStringa(Camera.Cam.camera.get(), codCameraProv);
                    cameraDestinazione = modCamera.query()
                            .valoreStringa(Camera.Cam.camera.get(), codCameraDest);
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il nome del cliente intestatario della prenotazione */
            if (continua) {
                modPren = PrenotazioneModulo.get();
                if (modPren != null) {
                    codCliente = modPren.query()
                            .valoreInt(Prenotazione.Cam.cliente.get(), codPrenotazione);
                }// fine del blocco if
                modCliente = ClienteAlbergoModulo.get();
                if (modCliente != null) {
                    cliente = modCliente.query()
                            .valoreStringa(Anagrafica.Cam.soggetto.get(), codCliente);
                }// fine del blocco if
            }// fine del blocco if

            /* recupera la stringa di preparazione camera */
            if (continua) {
                modPreparazione = CompoCameraModulo.get();
                if (modPreparazione != null) {
                    prep = modPreparazione.query()
                            .valoreStringa(CompoCamera.Cam.sigla.get(), codPrep);
                }// fine del blocco if
            }// fine del blocco if

            /* aggiunge il record al modulo memoria */
            if (continua) {
                mem = this.getMemoria();
                codMemoria = mem.query().nuovoRecord();
                mem.query().registra(codMemoria, Nomi.effettuato.get(), effettuato);
                mem.query().registra(codMemoria, Nomi.data.get(), data);
                mem.query().registra(codMemoria, Nomi.cameraProvenienza.get(), cameraProvenienza);
                mem.query().registra(codMemoria, Nomi.cameraDestinazione.get(), cameraDestinazione);
                mem.query().registra(codMemoria, Nomi.cliente.get(), cliente);
                mem.query().registra(codMemoria, Nomi.persone.get(), persone);
                mem.query().registra(codMemoria, Nomi.prenotazione.get(), codPrenotazione);
                mem.query().registra(codMemoria, Nomi.preparazione.get(), prep);
                mem.query().registra(codMemoria, Nomi.note.get(), note);
                mem.query().registra(codMemoria, Nomi.codPeriodo.get(), codPeriodo1);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    /**
     * Invocato quando si preme il bottone stampa.
     * <p/>
     */
    protected void eventoStampa() {
        /* variabili e costanti locali di lavoro */
        Date dataInizio;
        Date dataFine;

        try { // prova ad eseguire il codice

            dataInizio = this.getData1();
            dataFine = this.getData2();
            StampaMovimenti.stampaCompleta(dataInizio, dataFine);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Eseguito quando cambia l'azienda attiva.
     * <p/>
     */
    private void cambioAzienda() {

        try {    // prova ad eseguire il codice

            /* aggiorna la stringa info azienda nel dialogo */
            this.aggiornaLabelAzienda();

            /* aggiorna il dialogo (anche se non visibile) */
            this.aggiornaDialogo();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Invocato al cambio di data del programma.
     * <p/>
     */
    private void cambioData () {
        try {    // prova ad eseguire il codice

            /* aggiorna la stringa info azienda nel dialogo */
            this.aggiornaLabelAzienda();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Eseguito quando vengono modificate le prenotazioni.
     * <p/>
     * Invocato dalla gestione eventi
     */
    private void prenotazioniModificate() {
        if (this.isVisible()) {
            this.aggiornaDialogo();
        }// fine del blocco if
    }


    /**
     * Eseguito quando vengono modificati i periodi.
     * <p/>
     * Solo se il dialogo è visibile
     * Invocato dalla gestione eventi
     */
    private void periodiModificati() {
        if (this.isVisible()) {
            this.aggiornaDialogo();
        }// fine del blocco if
    }


    /**
     * Eseguito quando vengono modificati i clienti.
     * <p/>
     * Invocato dalla gestione eventi
     */
    private void clientiModificati() {
        if (this.isVisible()) {
            this.aggiornaDialogo();
        }// fine del blocco if
    }


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    @Override
    protected void eventoUscitaCampoModificato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Date data1;
        Date data2;

        try { // prova ad eseguire il codice

            /* la seconda data uguale alla prima */
            if (campo.equals(this.getCampo(NOME_CAMPO_DAL))) {
                this.setData2(this.getData1());
            }// fine del blocco if

            /* se modifico una data aggiorno i navigatori */
            if (campo.equals(this.getCampo(NOME_CAMPO_DAL)) || campo.equals(this.getCampo(
                    NOME_CAMPO_AL))) {
                data1 = this.getData1();
                data2 = this.getData2();

                if (!Lib.Data.isVuota(data1)) {
                    if (!Lib.Data.isVuota(data2)) {
                        this.aggiornaDialogo();
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Azione per cambiare azienda
     */
    private class AzioneCambioAzienda extends CambioAziendaAz {

        /**
         * cambioAziendaAz, da CambioAziendaLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void cambioAziendaAz(CambioAziendaEve unEvento) {
            cambioAzienda();
        }
    } // fine della classe interna

    /**
     * Azione al cambio della data programma
     */
    private class AzioneCambioData extends CambioDataAz {

        /**
         * Esegue l'azione
         * <p>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void cambioDataAz(CambioDataEve unEvento) {
            cambioData();
        }
    } // fine della classe interna



    /**
     * Azione eseguita quando si modifica una prenotazione
     */
    private class AzPrenotazioni extends DbTriggerAz {

        /**
         * dbTriggerAz, da DbTriggerLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        @Override public void dbTriggerAz(DbTriggerEve unEvento) {
            prenotazioniModificate();
        }

    } // fine della classe interna


    /**
     * Azione eseguita quando si modifica un periodo
     */
    private class AzPeriodi extends DbTriggerAz {

        /**
         * dbTriggerAz, da DbTriggerLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        @Override public void dbTriggerAz(DbTriggerEve unEvento) {
            periodiModificati();
        }

    } // fine della classe interna


    /**
     * Azione eseguita quando si modifica un cliente
     */
    private class AzClienti extends DbTriggerAz {

        /**
         * dbTriggerAz, da DbTriggerLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        @Override public void dbTriggerAz(DbTriggerEve unEvento) {
            clientiModificati();
        }

    } // fine della classe interna


    /**
     * Ritorna la prima data del dialogo.
     * <p/>
     *
     * @return la prima data
     */
    public Date getData1() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Object valore;

        try {    // prova ad eseguire il codice
            valore = this.getValore(NOME_CAMPO_DAL);
            data = Libreria.getDate(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna la prima data del dialogo.
     * <p/>
     *
     * @return la prima data
     */
    public Date getData2() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Object valore;

        try {    // prova ad eseguire il codice
            valore = this.getValore(NOME_CAMPO_AL);
            data = Libreria.getDate(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Assegna la prima data.
     * <p/>
     * @param data la prima data
     */
    private void setData1(Date data) {
        this.setValore(NOME_CAMPO_DAL, data);
    }


    /**
     * Assegna la seconda data.
     * <p/>
     * @param data la seconda data
     */
    private void setData2(Date data) {
        this.setValore(NOME_CAMPO_AL, data);
    }


    private PeriodoNavDialogo getNavArrivi() {
        return navArrivi;
    }


    private PeriodoNavDialogo setNavArrivi(PeriodoNavDialogo navArrivi) {
        this.navArrivi = navArrivi;
        return this.getNavArrivi();
    }


    private PeriodoNavDialogo getNavPartenze() {
        return navPartenze;
    }


    private PeriodoNavDialogo setNavPartenze(PeriodoNavDialogo navPartenze) {
        this.navPartenze = navPartenze;
        return this.getNavPartenze();
    }


    private Navigatore getNavCambi() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        ModuloMemoria mem;

        try { // prova ad eseguire il codice
            mem = this.getMemoria();

            if (mem != null) {
                nav = mem.getNavigatoreCorrente();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    private ModuloMemoria getMemoria() {
        return memoria;
    }


    private void setMemoria(ModuloMemoria memoria) {
        this.memoria = memoria;
    }


    private JLabel getInfoLabel() {
        return infoLabel;
    }


    private void setInfoLabel(JLabel infoLabel) {
        this.infoLabel = infoLabel;
    }


    private Pannello getPanComandi() {
        return panComandi;
    }


    private void setPanComandi(Pannello panComandi) {
        this.panComandi = panComandi;
    }


    private RiepilogoNew getRiepilogo() {
        return riepilogo;
    }


    private void setRiepilogo(RiepilogoNew riepilogo) {
        this.riepilogo = riepilogo;
    }


    /**
     * Nomi dei campi del modulo memoria.
     */
    public enum Nomi {

        effettuato("ok"),
        data("data"),
        cameraProvenienza("cameraprovenienza"),
        cameraDestinazione("cameradestinazione"),
        cliente("cliente"),
        persone("persone"),
        prenotazione("prenotazione"),
        preparazione("prep"),
        note("note"),
        codPeriodo("periodo");

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


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }

    }// fine della classe


    /**
     * Pannello contenente etichetta e Navigatore..
     * </p>
     *
     * @author Guido Andrea Ceresa ed Alessandro Valbonesi
     * @author gac
     * @version 1.0 / 15-gen-2008 ore  15:59
     */
    private final class CompNav extends PannelloFlusso {

        /**
         * variabile di tipo navigatore
         * navigatore di riferimento
         */
        private Navigatore navigatore;

        /**
         * etichetta visibile
         */
        private JLabel label;


        /**
         * Costruttore con parametri.
         *
         * @param navigatore - navigatore di riferimento
         * @param label - etichetta visibile
         */
        public CompNav(Navigatore navigatore, String label) {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_VERTICALE);

            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNavigatore(navigatore);
                this.setLabel(new JLabel(label));

                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni iniziali di riferimenti e variabili.
         * </p>
         * Metodo invocato direttamente dal costruttore (init) <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {

            try { // prova ad eseguire il codice
                this.setUsaGapFisso(true);
                this.setGapPreferito(5);
                this.add(this.getLabel());
                this.add(this.getNavigatore().getPortaleNavigatore());
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }// fine del metodo inizia


        /**
         * metodo getter
         *
         * @return navigatore
         */
        private Navigatore getNavigatore() {
            return navigatore;
        } // fine del metodo getter


        /**
         * metodo setter
         *
         * @param navigatore - navigatore di riferimento
         */
        private void setNavigatore(Navigatore navigatore) {
            this.navigatore = navigatore;
        } // fine del metodo setter


        /**
         * metodo getter
         *
         * @return label
         */
        private JLabel getLabel() {
            return label;
        } // fine del metodo getter


        /**
         * metodo setter
         *
         * @param label - etichetta visibile
         */
        private void setLabel(JLabel label) {
            this.label = label;
        } // fine del metodo setter
    }// fine della classe




    /**
     * Renderer per il campo Numero Persone In Cambio / Cambiate
     * </p>
     */
    private final class RendererNumCambi extends RendererBase {

        /**
         * Costruttore completo con parametri. <br>
         *
         * @param campo di riferimento
         */
        public RendererNumCambi(Campo campo) {
            /* rimanda al costruttore della superclasse */
            super(campo);
        }// fine del metodo costruttore completo


        public Component getTableCellRendererComponent(JTable table,
                                                       Object valore,
                                                       boolean selezionata,
                                                       boolean hasFocus,
                                                       int riga,
                                                       int colonna) {

            /* variabili e costanti locali di lavoro */
            Component comp = null;
            int codRecord;
            int quanti;
            int codPeriodo;
            Modulo modMem;

            try { // prova ad eseguire il codice
                codRecord = this.getChiaveRiga(riga, table);
                modMem = getMemoria();
                codPeriodo = modMem.query().valoreInt(Nomi.codPeriodo.get(), codRecord);
                quanti = PeriodoModulo.getNumPersoneCambio(codPeriodo, Periodo.TipoPersona.persona);
                comp = super.getTableCellRendererComponent(table,
                        quanti,
                        selezionata,
                        hasFocus,
                        riga,
                        colonna);
            } catch (Exception unErrore) {
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return comp;
        }
    } // fine della classe 'interna'

}// fine della classe
