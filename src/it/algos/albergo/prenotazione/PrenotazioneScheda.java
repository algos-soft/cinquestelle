package it.algos.albergo.prenotazione;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto.Cam;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoLogica;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.scheda.Scheda;
import it.algos.base.scheda.SchedaDefault;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Scheda specifica di una Prenotazione
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-ott-2007 ore 16.42.46
 */
public final class PrenotazioneScheda extends SchedaDefault implements Prenotazione {

    private Campo costoGiornaliero;

    /**
     * Bottone per aprire lo storico del cliente
     */
    private JButton botStorico;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public PrenotazioneScheda(Modulo modulo) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Campo costoGiornaliero;

        try { // prova ad eseguire il codice
            costoGiornaliero = CampoFactory.intero("costo giornaliero");
            costoGiornaliero.getCampoDB().setCampoFisico(false);
            costoGiornaliero.setAbilitato(false);
            costoGiornaliero.inizializza();
            this.setCostoGiornaliero(costoGiornaliero);
            this.addCampoCollezione(costoGiornaliero);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    @Override
    public void inizializza() {
        super.inizializza();

        /* aggiunge il bottone Storico alla scheda */
        JButton bot = AlbergoLib.addBotInfoScheda(this);
        bot.addActionListener(new AzInfoStorico());
        this.setBotStorico(bot);

    }

    

    @Override
	public void avvia() {
		super.avvia();
		
        // GESTIONE MULTIAZIENDA DISABLED 10-2017 ALEX
        Campo campo = getCampo(Cam.azienda);
        campo.setValore(1);
        campo.setVisibile(false);
        // END DISABLED

	}


	/**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;
        Pannello panSopra;
        Pannello panSx;
        Pannello panDx;
        Pannello panSx1;
        Pannello panSx2;
        Pannello panSx3;
        Pannello panSx4;
        Pannello panDx1;
        Pannello panDx2;
        Pannello panSx5;

        try { // prova ad eseguire il codice
            /* crea la pagina Generale */
            pag = super.addPagina("generale");

            panSopra = PannelloFactory.orizzontale(this);

            panSx = PannelloFactory.verticale(this);
            panDx = PannelloFactory.verticale(this);

            panSx1 = PannelloFactory.orizzontale(this);
            panSx1.setAllineamento(Layout.ALLINEA_BASSO);
            panSx1.add(Cam.cliente);
            panSx1.add(Cam.dataPrenotazione);
            panSx1.add(Cam.dataScadenza);

            panSx4 = PannelloFactory.orizzontale(this);
            panSx4.setAllineamento(Layout.ALLINEA_BASSO);
            panSx4.add(Cam.opzione);
            panSx4.add(Cam.confermata);
            panSx4.add(Cam.disdetta);
            panSx4.add(Cam.chiusa);

            panSx5 = PannelloFactory.verticale(this);
            panSx5.creaBordo("caparra");
            panSx2 = PannelloFactory.orizzontale(this);
            panSx2.setAllineamento(Layout.ALLINEA_CENTRO);
            panSx2.add(Cam.caparra);
            panSx2.add(Cam.dataCaparra);
            panSx2.add(Cam.mezzoCaparra);
            panSx2.add(Cam.numRF);
            panSx5.add(panSx2);

            panSx3 = PannelloFactory.orizzontale(this);
            panSx3.setAllineamento(Layout.ALLINEA_CENTRO);
            panSx3.add(Cam.nostraConferma);
            panSx3.add(Cam.caparraAccreditata);
            panSx5.add(panSx3);

//            panSx2.add(this.getCostoGiornaliero());

            panDx1 = PannelloFactory.orizzontale(this);
            panDx1.add(Cam.note);

            panDx2 = PannelloFactory.orizzontale(this);
            panDx2.add(Cam.canale);
            panDx2.add(Cam.azienda);

            panSx.add(panSx1);
            panSx.add(panSx4);
            panSx.add(panSx5);
            panSx.add(Lib.Comp.createVerticalFiller(60));

            panDx.add(panDx1);
            panDx.add(panDx2);
            panSx.add(Lib.Comp.createVerticalFiller(60));

            panSopra.add(panSx);
            panSopra.add(panDx);

            pag.add(panSopra);
            pag.add(Cam.periodi);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna l'elenco dei codici dei periodi di questa prenotazione.
     * <p/>
     *
     * @return l'elenco dei codici periodo
     */
    private int[] getCodPeriodi() {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        Modulo modPeriodo;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            filtro = FiltroFactory.crea(Periodo.Cam.prenotazione.get(), this.getCodice());
            codici = modPeriodo.query().valoriChiave(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean chiusa;
        boolean disdetta;
        int[] codPeriodi;
        Modulo modPeriodo;
        Date dataIni;
        Date dataEnd;
        int codCamera;
        int codSovrapposto;
        String testo;
        boolean accetta;
        MessaggioDialogo messaggio;
        Date data;
        int delta;

        try { // prova ad eseguire il codice

            /* se si spegne Opzione, mette la data della prenotazione
             * alla data di oggi e la scadenza di conseguenza */
            if (this.isCampo(campo, Cam.opzione)) {
                if (!this.getBool(Cam.opzione.get())) {
                    data = AlbergoLib.getDataProgramma();
                    this.setValore(Cam.dataPrenotazione.get(), data);
                    delta = PrenotazionePref.Prenotazione.validita.intero();
                    data = Lib.Data.add(data, delta);
                    this.setValore(Cam.dataScadenza.get(), data);
                }// fine del blocco if
            }// fine del blocco if


            /**
             * se si accende opzione toglie automaticamente confermata e disdetta
             */
            if (this.isCampo(campo, Cam.opzione)) {
                if (Libreria.getBool(this.getValore(campo))) {
                    this.setValore(Cam.confermata.get(), false);
                    this.setValore(Cam.disdetta.get(), false);
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se si accende confermata toglie automaticamente opzione e disdetta
             */
            if (this.isCampo(campo, Cam.confermata)) {
                if (Libreria.getBool(this.getValore(campo))) {
                    this.setValore(Cam.opzione.get(), false);
                    this.setValore(Cam.disdetta.get(), false);
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se si accende disdetta toglie automaticamente confermata
             * e mette automaticamente chiusa
             */
            if (this.isCampo(campo, Cam.disdetta)) {
                if (Libreria.getBool(this.getValore(campo))) {
                    this.setValore(Cam.confermata.get(), false);
                    this.setValore(Cam.chiusa.get(), true);
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se si vuole togliere il flag chiusa e la prenotazione è disdetta,
             * cerca di togliere la disdetta
             */
            if (this.isCampo(campo, Cam.chiusa)) {
                chiusa = Libreria.getBool(this.getValore(campo));
                if (!chiusa) {
                    this.setValore(Prenotazione.Cam.disdetta.get(), false);
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se si vuole togliere la disdetta controlla prima che
             * le camere siano disponibili - se non lo sono non lo consente
             */
            if (this.isCampo(campo, Cam.disdetta)) {
                accetta = true;
                disdetta = Libreria.getBool(this.getValore(campo));
                if (!disdetta) {
                    codPeriodi = this.getCodPeriodi();
                    modPeriodo = PeriodoModulo.get();
                    for (int cod : codPeriodi) {
                        dataIni = modPeriodo.query().valoreData(
                                Periodo.Cam.arrivoPrevisto.get(),
                                cod);
                        dataEnd = modPeriodo.query().valoreData(
                                Periodo.Cam.partenzaPrevista.get(),
                                cod);
                        codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), cod);
                        codSovrapposto = PeriodoLogica.getCodPeriodoSovrapposto(
                                dataIni,
                                dataEnd,
                                codCamera,
                                cod);
                        if (codSovrapposto != 0) {
                            testo = PeriodoLogica.getMessaggioOccupato(codSovrapposto);
                            new MessaggioAvviso("Impossibile togliere la disdetta:\n" + testo);
                            accetta = false;
                            break;
                        }// fine del blocco if
                    }  // fine del ciclo for
                }// fine del blocco if

                if (!accetta) {
                    this.setValore(campo, true);
                }// fine del blocco if

            }// fine del blocco if

            /**
             * se si vuole togliere il flag Caparra Accreditata chiede conferma
             */
            if (this.isCampo(campo, Cam.caparraAccreditata)) {
                if (!Libreria.getBool(this.getValore(campo))) {
                    testo = "Se spegni il flag Caparra Accreditata, accertati di aver\n";
                    testo += "eliminato il relativo accredito dal conto.\n";
                    testo += "Spegnendo questo flag la caparra verrà accreditata al cliente\n";
                    testo += "la prossima volta che aprirai un conto da questa prenotazione.\n\n";
                    testo += "Vuoi veramente spegnere il flag?";
                    messaggio = new MessaggioDialogo(testo);
                    if (!messaggio.isConfermato()) {
                        campo.setValore(true);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* quando si accende il flag opzione azzera la data di scadenza */
            if (this.isCampo(campo, Cam.opzione)) {
                if (Libreria.getBool(this.getValore(campo))) {
                    this.setValore(Cam.dataScadenza.get(), Lib.Data.getVuota());
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoUscitaCampoModificato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Date data;
        double caparra;
        MessaggioDialogo messaggio;
        Filtro filtro;

        try { // prova ad eseguire il codice

            /**
             * se modifica la caparra, la caparra ha un valore,
             * e manca la data di ricevimento, mette la data
             * di oggi
             */
            if (this.isCampo(campo, Cam.caparra)) {
                caparra = this.getDouble(Cam.caparra.get());
                if (caparra != 0) {
                    data = this.getData(Cam.dataCaparra.get());
                    if (Lib.Data.isVuota(data)) {
                        this.setValore(Cam.dataCaparra.get(), AlbergoLib.getDataProgramma());
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se modifica la caparra, la caparra è valorizzata, e la prenotazione
             * non è confermata, propone la conferma
             */
            if (this.isCampo(campo, Cam.caparra)) {
                caparra = this.getDouble(Cam.caparra.get());
                if (caparra != 0) {
                    if (!this.getBool(Cam.confermata.get())) {
                        messaggio = new MessaggioDialogo(
                                "E' stata ricevuta la caparra. Vuoi confermare la prenotazione?");
                        if (messaggio.isConfermato()) {
                            this.setValore(Cam.confermata.get(), true);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


            /**
             * Se è il cliente controlla che non abbia altre prenotazioni attive
             * in tal caso avvisa (senza bloccare)
             */
            if (this.isCampo(campo, Cam.cliente)) {
                filtro = this.getFiltroPrenAttive();
                if (PrenotazioneModulo.get().query().isEsisteRecord(filtro)) {
                    String testo = "Attenzione! Esiste già una prenotazione per questo cliente!";
                    new MessaggioAvviso(testo);
                }// fine del blocco if
            }// fine del blocco if



        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna un filtro che isola tutte le prenotazioni attive
     * (non chiuse e non disdette)
     * dell'azienda corrente.
     * <p/>
     * @return il filtro
     */
    private Filtro getFiltroPrenAttive () {
        /* variabili e costanti locali di lavoro */
        Filtro filtroTot = null;
        int codPren;
        int codCliente;
        Filtro filtroCliente = null;
        Filtro filtroNoQuestaPren = null;
        Filtro filtroNoChiuse = null;
        Filtro filtroNoDisdette = null;
        Filtro filtroAzienda;

        try {    // prova ad eseguire il codice

            codPren = this.getCodice();
            codCliente = this.getInt(Prenotazione.Cam.cliente.get());

            filtroCliente = FiltroFactory.crea(Prenotazione.Cam.cliente.get(), codCliente);
            filtroNoQuestaPren = FiltroFactory.codice(PrenotazioneModulo.get(), codPren);
            filtroNoQuestaPren.setInverso(true);
            filtroNoChiuse = FiltroFactory.creaFalso(Prenotazione.Cam.chiusa);
            filtroNoDisdette = FiltroFactory.creaFalso(Prenotazione.Cam.disdetta);
            filtroAzienda = PrenotazioneModulo.get().getFiltroAzienda();

            filtroTot = new Filtro();
            filtroTot.add(filtroCliente);
            filtroTot.add(filtroNoQuestaPren);
            filtroTot.add(filtroNoChiuse);
            filtroTot.add(filtroNoDisdette);
            filtroTot.add(filtroAzienda);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }





    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean chiusa;
        Object valore;
        Campo campoChiusa;
        Campo campoGiornaliero;
        boolean accreditata;
        Campo campo;

        try { // prova ad eseguire il codice
            super.sincronizza();
            
            // GESTIONE MULTIAZIENDA DISABLED 10-2017 ALEX
            Campo cAzienda = getCampo(Cam.azienda);
            if(cAzienda!=null){
                cAzienda.setValore(1);
                cAzienda.setVisibile(false);
            }
            // END DISABLED


            /* recupera il valore del flag chiuso */
            campoChiusa = this.getCampo(Cam.chiusa.get());
            valore = campoChiusa.getValore();
            chiusa = Libreria.getBool(valore);

            /* abilita o disabilita tutta la scheda in funzione del flag Prenotazione Chiusa */
            this.setModificabile(!chiusa);

            /* riabilita il flag Prenotazione Chiusa */
            campoChiusa.setModificabile(true);

            /* recupera il valore del valore giornaliero */
            campoGiornaliero = this.getCostoGiornaliero();
            valore = this.getMediaGiornaliera();
            campoGiornaliero.setValore(valore);

            /* se il flag CaparraAccreditata è acceso
            * blocca i campi della caparra */
            if (!chiusa) {
                accreditata = this.getBool(Cam.caparraAccreditata.get());
                campo = this.getCampo(Cam.caparra);
                campo.setModificabile(!accreditata);
                campo = this.getCampo(Cam.dataCaparra);
                campo.setModificabile(!accreditata);
                campo = this.getCampo(Cam.mezzoCaparra);
                campo.setModificabile(!accreditata);
                campo = this.getCampo(Cam.numRF);
                campo.setModificabile(!accreditata);
                campo = this.getCampo(Cam.nostraConferma);
                campo.setModificabile(!accreditata);
            }// fine del blocco if

            /* se il flag opzione è acceso la data di scadenza è disabilitata e viceversa */
            if (!chiusa) {
                Campo unCampo = this.getCampo(Prenotazione.Cam.dataScadenza);
                boolean flag = this.getBool(Cam.opzione.get());
                unCampo.setModificabile(!flag);
            }// fine del blocco if

            /* se il flag confermata è acceso il flag opzione è disabilitato */
            if (this.getBool(Prenotazione.Cam.confermata.get())) {
                Campo unCampo = this.getCampo(Prenotazione.Cam.opzione);
                unCampo.setModificabile(false);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il valore medio giornaliero della prenotazione.
     * <p/>
     *
     * @return il valore
     */
    private double getMediaGiornaliera() {
        /* variabili e costanti locali di lavoro */
        double valore = 0.0;
        int giorni;
        double importo;
        Campo campo;
        Navigatore nav;
        Lista lista;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(Cam.periodi.get());
            nav = campo.getNavigatore();
            lista = nav.getLista();

            giorni = Libreria.getInt(lista.getTotale(Periodo.Cam.giorni.get()));
            importo = lista.getTotale(Periodo.Cam.valore.get());

            if (giorni > 0) {
                valore = importo / giorni;
            }// fine del blocco if

            valore = Lib.Mat.arrotonda(valore, 0);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    private Campo getCostoGiornaliero() {
        return costoGiornaliero;
    }


    private void setCostoGiornaliero(Campo costoGiornaliero) {
        this.costoGiornaliero = costoGiornaliero;
    }

    private JButton getBotStorico() {
        return botStorico;
    }


    private void setBotStorico(JButton botStorico) {
        this.botStorico = botStorico;
    }



    /**
     * Richiama i controlli della scheda specifica.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected Controlli[] getControlli() {
        return Controllo.values();
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Controllo implements Controlli {

        /* date inizio e fine in sequenza */
        date("le date di prenotazione e scadenza non sono nella corretta sequenza") {
            public boolean isValido(Scheda scheda) {
                /* variabili e costanti locali di lavoro */
                boolean valido = true;
                Date dataIni;
                Date dataEnd;

                try { // prova ad eseguire il codice
                    dataIni = Libreria.getDate(scheda.getValore(Cam.dataPrenotazione.get()));
                    dataEnd = Libreria.getDate(scheda.getValore(Cam.dataScadenza.get()));
                    if (!Lib.Data.isVuota(dataIni) && (!Lib.Data.isVuota(dataEnd))) {
                        valido = Lib.Data.isPosterioreUguale(dataIni, dataEnd);
                    }// fine del blocco if
                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch

                /* valore di ritorno */
                return valido;
            }
        },

        /* periodi di prenotazione */
        periodi("non è stato prenotato nessun periodo") {
            public boolean isValido(Scheda scheda) {
                /* variabili e costanti locali di lavoro */
                boolean valido = true;
                Campo unCampo;
                int codPrenotaz;
                Modulo modPeriodi;
                int totPeriodi;
                Filtro unFiltro;

                try { // prova ad eseguire il codice
                    codPrenotaz = scheda.getCodice();
                    modPeriodi = PeriodoModulo.get();
                    unFiltro = FiltroFactory.crea(Periodo.Cam.prenotazione.get(), codPrenotaz);
                    totPeriodi = modPeriodi.query().contaRecords(unFiltro);
                    valido = (totPeriodi > 0);
                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch

                /* valore di ritorno */
                return valido;
            }
        };

//        /* sovrapposizione a un periodo già prenotato */
//        periodo("la camera è già occupata nel periodo indicato") {
//            public boolean isValido(Scheda scheda) {
//                return ((PeriodoScheda)scheda).isPeriodoValido();
//            }
//        };

        /**
         * messaggio di avviso
         */
        private String messaggio;


        /**
         * Costruttore completo con parametri.
         *
         * @param messaggio di avviso
         */
        Controllo(String messaggio) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setMessaggio(messaggio);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public abstract boolean isValido(Scheda scheda);


        public String getMessaggio(Scheda scheda) {
            return messaggio;
        }


        private void setMessaggio(String messaggio) {
            this.messaggio = messaggio;
        }


    }// fine della classe


    /**
     * Azione Informazioni Storiche
     * </p>
     */
    private final class AzInfoStorico implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            /* variabili e costanti locali di lavoro */
            int codice;

            try { // prova ad eseguire il codice
                codice = getInt(Prenotazione.Cam.cliente.get());
                if (codice>0) {
                    ClienteAlbergoModulo.showStorico(codice);
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }
    } // fine della classe 'interna'


}// fine della classe