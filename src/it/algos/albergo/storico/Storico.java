package it.algos.albergo.storico;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.evento.listasingola.ListaSelModAz;
import it.algos.base.evento.listasingola.ListaSelModEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.listasingola.ListaSingola;
import it.algos.base.listasingola.ListaSingolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Oggetto grafico e logico delegato a navigare nelle informazioni
 * storiche relative a un singolo cliente o a un gruppo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-mar-2009
 */
public final class Storico extends PannelloFlusso {

    /* codice del cliente attualmente osservato */
    private int codCliente;

    /* lista per gestione elenco anni */
    private ListaSingola listaAnni;

    /* Pannello placeholder per Gruppo Clienti  */
    private PanTitolato panGruppo;

    /* Pannello placeholder per Lista Presenze  */
    private PanTitolato panPresenze;

    /* Pannello placeholder per Lista Prenotazioni  */
    private PanTitolato panPrenotazioni;

    /* Pannello placeholder per Lista Conti  */
    private PanTitolato panConti;

    /* campo opzioni solo questo cliente o tutto il gruppo */
    private Campo campoOpzioni;

    /* navigatore Gruppo Clienti */
    private NavStorico navGruppo;

    /* navigatore Presenze */
    private NavStorico navPresenze;

    /* navigatore Prenotazioni */
    private NavStorico navPrenotazioni;

    /* navigatore Conti */
    private NavStorico navConti;



    /**
     * Costruttore completo.
     * <p/>
     */
    public Storico() {

        super(Layout.ORIENTAMENTO_VERTICALE);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        PanTitolato pan;
        Pannello panTesta;
        Pannello panListe;
        Campo campoOpzioni;

        try { // prova ad eseguire il codice

//            this.creaBordo("Storico presenze");
//            this.creaBordo(5);

            this.creaListaAnni();

            /* crea il campo Opzioni */
            campoOpzioni = CampoFactory.radioInterno("Mostra");
            this.setCampoOpzioni(campoOpzioni);
            campoOpzioni.setValoriInterni("Gruppo,Cliente");
            campoOpzioni.setOrientamentoComponenti(Layout.ORIENTAMENTO_ORIZZONTALE);
            campoOpzioni.decora().eliminaEtichetta();
            campoOpzioni.avvia();
            campoOpzioni.addListener(new AzOpzioniModificato());
            campoOpzioni.setValore(1);

            /* costruzione grafica */

            pan = new PanTitolato("Gruppo");
            this.setPanGruppo(pan);

            pan = new PanTitolato("Presenze");
            this.setPanPresenze(pan);

            pan = new PanTitolato("Prenotazioni");
            this.setPanPrenotazioni(pan);

            pan = new PanTitolato("Conti");
            this.setPanConti(pan);

            panTesta = PannelloFactory.orizzontale(null);
            pan = new PanTitolato("Anno");
            pan.setComponente(this.getListaAnni());
            panTesta.add(pan);
            panTesta.add(this.getPanGruppo());

            panListe = PannelloFactory.verticale(null);
            panListe.add(this.getPanPresenze());
            panListe.add(this.getPanPrenotazioni());
            panListe.add(this.getPanConti());

            this.add(campoOpzioni);
            this.add(panTesta);
            this.add(panListe);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Popola questo oggetto con lo storico di un cliente.
     * <p/>
     *
     * @param codCliente da monitorare, 0 per svuotare lo storico
     */
    public void avvia(int codCliente) {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        Navigatore nav;
        Filtro filtro;
        Ordine ordine;

        try { // prova ad eseguire il codice

            /* registra il codice cliente */
            this.setCodCliente(codCliente);

            /**
             * Assegna al navigatore Gruppo il filtro per selezionare
             * tutti i membri del gruppo e l'ordine appropriato
             */
            nav = this.getNavGruppo();
            filtro = ClienteAlbergoModulo.getFiltroGruppo(codCliente);
            nav.setFiltroBase(filtro);
            ordine = AlbergoLib.getOrdineGruppo();
            nav.getLista().setOrdine(ordine);
            nav.avvia();

            /**
             * inserisce graficamente i Portali Navigatore
             * negli appositi pannello placeholder
             * Lo devo fare ad ogni avvio se no il navigatore sparisce
             */
            this.inserisciNavigatori();

            /**
             * assegna inizialmente ai Navigatori
             * un filtro che non seleziona alcun record
             * e riavvia i navigatori
             */
            nav = this.getNavPresenze();
            mod = nav.getModulo();
            nav.setFiltroCorrente(FiltroFactory.nessuno(mod));
            nav.avvia();

            nav = this.getNavPrenotazioni();
            mod = nav.getModulo();
            nav.setFiltroCorrente(FiltroFactory.nessuno(mod));
            nav.avvia();

            nav = this.getNavConti();
            mod = nav.getModulo();
            nav.setFiltroCorrente(FiltroFactory.nessuno(mod));
            nav.avvia();

            /* carica l'elenco degli anni */
            this.reloadAnni();

            /* all'avvio mostra sempre tutto il gruppo */
            this.getCampoOpzioni().setValore(1);

            /**
             * Seleziona il primo anno (il più recente) nella lista degli anni
             */
            ListaSingola listaAnni = this.getListaAnni();
            listaAnni.getLista().setSelectedIndex(0);
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Inserisce il navigatore nell'apposito pannello.
     * <p/>
     */
    private void inserisciNavigatori() {
        /* variabili e costanti locali di lavoro */
        PanTitolato pan;
        NavStorico nav;

        try {    // prova ad eseguire il codice

            pan = this.getPanGruppo();
            nav = this.getNavGruppo();
            nav.setStorico(this);
            pan.setNavigatore(nav);

            pan = this.getPanPresenze();
            nav = this.getNavPresenze();
            nav.setStorico(this);
            pan.setNavigatore(nav);

            pan = this.getPanPrenotazioni();
            nav = this.getNavPrenotazioni();
            nav.setStorico(this);
            pan.setNavigatore(nav);

            pan = this.getPanConti();
            nav = this.getNavConti();
            nav.setStorico(this);
            pan.setNavigatore(nav);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ricarica la lista degli Anni in base alle impostazioni correnti.
     * <p/>
     */
    private void reloadAnni() {
        /* variabili e costanti locali di lavoro */
        int codCliente;
        int[] anni;
        ListaSingolaModello modello;


        try {    // prova ad eseguire il codice

            /* svuota l'elenco degli anni */
            this.removeAnni();
            int b=88;

            /* riempie l'elenco degli anni */
            codCliente = this.getCodCliente();
            if (codCliente > 0) {

                anni = this.getAnniPresenza();
                this.addAnni(anni);

                anni = this.getAnniPrenotazione();
                this.addAnni(anni);

                anni = this.getAnniConto();
                this.addAnni(anni);

            }// fine del blocco if

            /* ordina la lista degli anni in ordine decrescente */
            modello = this.getListaAnni().getModello();
            ArrayList listaAnni = modello.getElementi();
            Collections.sort(listaAnni);
            Collections.reverse(listaAnni);
            modello.setElementi(listaAnni);
            int a = 87;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Visualizza lo storico relativo al cliente, alle opzioni e agli anni selezionati.
     * <p/>
     */
    private void showStorico() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            filtro = this.creaFiltroPresenze();
            nav = this.getNavPresenze();
            nav.setFiltroCorrente(filtro);
            nav.aggiornaLista();

            filtro = this.creaFiltroPrenotazioni();
            nav = this.getNavPrenotazioni();
            nav.setFiltroCorrente(filtro);
            nav.aggiornaLista();

            filtro = this.creaFiltroConti();
            nav = this.getNavConti();
            nav.setFiltroCorrente(filtro);
            nav.aggiornaLista();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Doppio clic su una riga di una lista di un Navigatore.
     * <p/>
     * Apre la scheda in finestra modale.
     *
     * @param modulo del navigatore cliccato
     * @param codice del record cliccato
     */
    public void doppioClic(Modulo modulo, int codice) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice
            modulo.presentaRecord(codice);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea il filtro per visualizzare lo storico Presenze.
     * <p/>
     *
     * @return il filtro creato
     */
    private Filtro creaFiltroPresenze() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroClienti;
        Filtro filtroAnni;

        try {    // prova ad eseguire il codice

            filtroClienti = this.creaFiltroClienti();
            filtroAnni = this.creaFiltroPresenzeAnni();

            filtro = new Filtro();
            filtro.add(filtroClienti);
            filtro.add(filtroAnni);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea il filtro per visualizzare lo storico Prenotazioni.
     * <p/>
     *
     * @return il filtro creato
     */
    private Filtro creaFiltroPrenotazioni() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroClienti;
        Filtro filtroAnni;

        try {    // prova ad eseguire il codice

            filtroClienti = this.creaFiltroClienti();
            filtroAnni = this.creaFiltroPrenotazioniAnni();

            filtro = new Filtro();
            filtro.add(filtroClienti);
            filtro.add(filtroAnni);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea il filtro per visualizzare lo storico Conti.
     * <p/>
     *
     * @return il filtro creato
     */
    private Filtro creaFiltroConti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroClienti;
        Filtro filtroAnni;

        try {    // prova ad eseguire il codice

            filtroClienti = this.creaFiltroClienti();
            filtroAnni = this.creaFiltroContiAnni();

            filtro = new Filtro();
            filtro.add(filtroClienti);
            filtro.add(filtroAnni);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea il filtro per isolare i record relativi ai clienti.
     * <p/>
     *
     * @return il filtro Clienti
     */
    private Filtro creaFiltroClienti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtroTot = null;
        Filtro filtro = null;
        int codCliente;
        boolean tuttoGruppo;
        int[] clienti;
        int cod;
        Modulo modClienti;

        try {    // prova ad eseguire il codice

            modClienti = ClienteAlbergoModulo.get();
            codCliente = this.getCodCliente();
            tuttoGruppo = this.isTuttoIlGruppo();

            /* crea un array con il solo cliente o con tutto il gruppo */
            if (tuttoGruppo) {
                clienti = ClienteAlbergoModulo.getCodMembri(codCliente);
            } else {
                clienti = new int[1];
                clienti[0] = codCliente;
            }// fine del blocco if-else

            /* crea un filtro nullo, spazzola i codici
            * e se ce ne sono di validi (>0) crea un filtro con i codici */
            filtroTot = null;
            for (int k = 0; k < clienti.length; k++) {
                cod = clienti[k];

                if (cod>0) {

                    /* lazy creation */
                    if (filtroTot==null) {
                        filtroTot =  new Filtro();
                    }// fine del blocco if

                    filtro = FiltroFactory.codice(modClienti, cod);
                    filtroTot.add(Filtro.Op.OR, filtro);

                }// fine del blocco if

            } // fine del ciclo for

            /* se è ancora nullo, crea un filtro per non selezionare alcun record */
            if (filtroTot==null) {
                filtroTot = FiltroFactory.nessuno(modClienti);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }


    /**
     * Crea il filtro per isolare i record di Presenza relativi agli anni
     * selezionati nella lista Anni.
     * <p/>
     *
     * @return il filtro Anni
     */
    private Filtro creaFiltroPresenzeAnni() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtro1;
        Filtro filtro2;
        Filtro unFiltro;
        int[] anni;
        Date primoGennaio;
        Date trentunDicembre;

        try {    // prova ad eseguire il codice

            filtro = new Filtro();
            anni = this.getAnniSelezionati();
            for (int anno : anni) {
                primoGennaio = Lib.Data.getPrimoGennaio(anno);
                trentunDicembre = Lib.Data.getTrentunoDicembre(anno);
                filtro1 = FiltroFactory.crea(
                        Presenza.Cam.entrata.get(), Filtro.Op.MAGGIORE_UGUALE, primoGennaio);
                filtro2 = FiltroFactory.crea(
                        Presenza.Cam.entrata.get(), Filtro.Op.MINORE_UGUALE, trentunDicembre);
                unFiltro = new Filtro();
                unFiltro.add(filtro1);
                unFiltro.add(filtro2);

                filtro.add(Filtro.Op.OR, unFiltro);

            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea il filtro per isolare i record di Prenotazioni relativi agli anni
     * selezionati nella lista Anni.
     * <p/>
     *
     * @return il filtro Anni
     */
    private Filtro creaFiltroPrenotazioniAnni() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtro1;
        Filtro filtro2;
        Filtro unFiltro;
        int[] anni;
        Date primoGennaio;
        Date trentunDicembre;

        try {    // prova ad eseguire il codice

            filtro = new Filtro();
            anni = this.getAnniSelezionati();
            for (int anno : anni) {
                primoGennaio = Lib.Data.getPrimoGennaio(anno);
                trentunDicembre = Lib.Data.getTrentunoDicembre(anno);
                filtro1 =
                        FiltroFactory.crea(Prenotazione.Cam.dataPrenotazione.get(),
                                Filtro.Op.MAGGIORE_UGUALE,
                                primoGennaio);
                filtro2 =
                        FiltroFactory.crea(Prenotazione.Cam.dataPrenotazione.get(),
                                Filtro.Op.MINORE_UGUALE,
                                trentunDicembre);
                unFiltro = new Filtro();
                unFiltro.add(filtro1);
                unFiltro.add(filtro2);
                filtro.add(Filtro.Op.OR, unFiltro);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea il filtro per isolare i record di Conto relativi agli anni
     * selezionati nella lista Anni.
     * <p/>
     *
     * @return il filtro Anni
     */
    private Filtro creaFiltroContiAnni() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtro1;
        Filtro filtro2;
        Filtro unFiltro;
        int[] anni;
        Date primoGennaio;
        Date trentunDicembre;

        try {    // prova ad eseguire il codice

            filtro = new Filtro();
            anni = this.getAnniSelezionati();
            for (int anno : anni) {
                primoGennaio = Lib.Data.getPrimoGennaio(anno);
                trentunDicembre = Lib.Data.getTrentunoDicembre(anno);
                filtro1 = FiltroFactory.crea(Conto.Cam.dataApertura.get(), Filtro.Op.MAGGIORE_UGUALE, primoGennaio);
                filtro2 = FiltroFactory.crea(Conto.Cam.dataApertura.get(), Filtro.Op.MINORE_UGUALE, trentunDicembre);
                unFiltro = new Filtro();
                unFiltro.add(filtro1);
                unFiltro.add(filtro2);
                filtro.add(Filtro.Op.OR, unFiltro);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea e registra la lista per gli anni.
     * <p/>
     */
    private void creaListaAnni() {
        /* variabili e costanti locali di lavoro */
        ListaSingola listaAnni;

        try {    // prova ad eseguire il codice
            listaAnni = new ListaSingola();
            this.setListaAnni(listaAnni);
            listaAnni.setPreferredWidth(60);
            listaAnni.setPreferredHeigth(65);
            listaAnni.bloccaLarMax();

            listaAnni.addListener(new AzSelLista());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna l'elenco univoco degli anni di presenza del cliente/gruppo.
     * <p/>
     *
     * @return l'elenco univoco degli anni di presenza ordinato dall'alto in basso
     */
    private int[] getAnniPresenza() {
        /* variabili e costanti locali di lavoro */
        int[] anni = null;
        Filtro filtro;
        Filtro filtroClienti;
        Filtro filtroAzienda;
        Ordine ordine;
        Modulo modPres;
        ArrayList valori;
        Date[] date;
        Date data;
        Object ogg;

        try {    // prova ad eseguire il codice

            /* crea un filtro che isola tutte le presenze dei clienti */
            filtro=this.creaFiltroClienti();

            /* crea un ordine per data di entrata */
            ordine = new Ordine();
            ordine.add(Presenza.Cam.entrata.get());

            /* costruisce l'elenco di tutte le date di entrata */
            modPres = PresenzaModulo.get();
            valori = modPres.query().valoriCampo(Presenza.Cam.entrata.get(), filtro, ordine);
            date = new Date[valori.size()];
            for (int k = 0; k < valori.size(); k++) {
                ogg = valori.get(k);
                data = Libreria.getDate(ogg);
                date[k] = data;
            } // fine del ciclo for

            /* recupera l'array univoco degli anni */
            anni = this.getAnniUnivoci(date);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return anni;
    }


    /**
     * Ritorna l'elenco univoco degli anni di Prenotazione del cliente/gruppo.
     * <p/>
     *
     * @return l'elenco univoco degli anni di Prenotazione
     */
    private int[] getAnniPrenotazione() {
        /* variabili e costanti locali di lavoro */
        int[] anni = null;
        Filtro filtro;
        Ordine ordine;
        Modulo mod;
        ArrayList valori;
        Date[] date;
        Date data;
        Object ogg;

        try {    // prova ad eseguire il codice

            /* crea un filtro che isola tutte le Prenotazioni dei clienti */
            filtro=this.creaFiltroClienti();

            /* crea un ordine per data di prenotazione */
            ordine = new Ordine();
            ordine.add(Prenotazione.Cam.dataPrenotazione.get());

            /* costruisce l'elenco di tutte le date di prenotazione */
            mod = PrenotazioneModulo.get();
            valori = mod.query().valoriCampo(Prenotazione.Cam.dataPrenotazione.get(), filtro, ordine);
            date = new Date[valori.size()];
            for (int k = 0; k < valori.size(); k++) {
                ogg = valori.get(k);
                data = Libreria.getDate(ogg);
                date[k] = data;
            } // fine del ciclo for

            /* recupera l'array univoco degli anni */
            anni = this.getAnniUnivoci(date);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return anni;
    }


    /**
     * Ritorna l'elenco univoco degli anni di Conto del cliente/gruppo.
     * <p/>
     *
     * @return l'elenco univoco degli anni di Conto
     */
    private int[] getAnniConto() {
        /* variabili e costanti locali di lavoro */
        int[] anni = null;
        Filtro filtro;
        Ordine ordine;
        Modulo mod;
        ArrayList valori;
        Date[] date;
        Date data;
        Object ogg;

        try {    // prova ad eseguire il codice

            /* crea un filtro che isola tutte i Conti dei clienti */
            filtro=this.creaFiltroClienti();

            /* crea un ordine per data di apertura conto */
            ordine = new Ordine();
            ordine.add(Conto.Cam.dataApertura.get());

            /* costruisce l'elenco di tutte le date di apertura */
            mod = ContoModulo.get();
            valori = mod.query().valoriCampo(Conto.Cam.dataApertura.get(), filtro, ordine);
            date = new Date[valori.size()];
            for (int k = 0; k < valori.size(); k++) {
                ogg = valori.get(k);
                data = Libreria.getDate(ogg);
                date[k] = data;
            } // fine del ciclo for

            /* recupera l'array univoco degli anni */
            anni = this.getAnniUnivoci(date);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return anni;
    }



    /**
     * Crea un array univoco di anni da un array di date.
     * <p/>
     * @param date l'array di date
     * @return l'array di anni
     */
    private int[] getAnniUnivoci(Date[] date) {
        /* variabili e costanti locali di lavoro */
        int[] anni=new int[0];
        ArrayList<Integer> listaAnni;
        int anno;

        try {    // prova ad eseguire il codice

            /* spazzola l'elenco e crea l'elenco univoco degli anni */
            listaAnni = new ArrayList<Integer>();
            for (Date unaData : date) {
                anno = Lib.Data.getAnno(unaData);
                if (!listaAnni.contains(anno)) {
                    listaAnni.add(anno);
                }// fine del blocco if
            }

            /* trasforma la lista in array */
            anni = new int[listaAnni.size()];
            for (int k = 0; k < listaAnni.size(); k++) {
                anni[k] = listaAnni.get(k);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return anni;
    }





    /**
     * Aggiunge un anno alla lista anni.
     * <p/>
     * L'anno viene aggiunto solo se non è già esistente
     *
     * @param anno da aggiungere
     */
    private void addAnno(int anno) {
        /* variabili e costanti locali di lavoro */
        ListaSingola listaAnni;
        ListaSingolaModello modello;
        ArrayList lista;
        Integer intAnno;

        try {    // prova ad eseguire il codice

            listaAnni = this.getListaAnni();
            modello = listaAnni.getModello();

            lista = modello.getElementi();
            intAnno = new Integer(anno);
            if (!lista.contains(intAnno)) {
                modello.addElement(intAnno);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Aggiunge un elenco di anni alla lista anni.
     * <p/>
     * Vengono aggiunti solo gli anni che non sono già esistenti
     *
     * @param anni elenco di da aggiungere
     */
    private void addAnni(int[] anni) {
        try {    // prova ad eseguire il codice
            for (int anno : anni) {
                this.addAnno(anno);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Svuota la lista degli anni.
     * <p/>
     */
    private void removeAnni() {
        /* variabili e costanti locali di lavoro */
        ListaSingola listaAnni;
        ListaSingolaModello modello;

        try {    // prova ad eseguire il codice
            listaAnni = this.getListaAnni();
            modello = listaAnni.getModello();
            modello.removeAllElements();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il navigatore del Gruppo.
     * <p/>
     *
     * @return il Navigatore del Gruppo
     */
    private NavStorico getNavGruppo() {
        /* variabili e costanti locali di lavoro */
        NavStorico nav = null;
        ClienteAlbergoModulo modulo;

        try {    // prova ad eseguire il codice

            if (this.navGruppo == null) {
                modulo = ClienteAlbergoModulo.get();

                if (modulo != null) {
                    nav = modulo.getNavStorico();
                    this.setNavGruppo(nav);
                }// fine del blocco if

            }// fine del blocco if

            nav = this.navGruppo;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    private void setNavGruppo(NavStorico navGruppo) {
        this.navGruppo = navGruppo;
    }


    /**
     * Ritorna il navigatore delle Presenze.
     * <p/>
     *
     * @return il Navigatore delle Presenze
     */
    private NavStorico getNavPresenze() {
        /* variabili e costanti locali di lavoro */
        NavStorico nav = null;
        PresenzaModulo modulo;

        try {    // prova ad eseguire il codice

            if (this.navPresenze == null) {
                modulo = PresenzaModulo.get();

                if (modulo != null) {
                    nav = modulo.getNavStorico();
                    this.setNavPresenze(nav);
                }// fine del blocco if

            }// fine del blocco if

            nav = this.navPresenze;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    private void setNavPresenze(NavStorico navPresenze) {
        this.navPresenze = navPresenze;
    }


    /**
     * Ritorna il navigatore delle Prenotazioni.
     * <p/>
     *
     * @return il Navigatore delle Prenotazioni
     */
    private NavStorico getNavPrenotazioni() {
        /* variabili e costanti locali di lavoro */
        NavStorico nav = null;
        PrenotazioneModulo modulo;

        try {    // prova ad eseguire il codice

            if (this.navPrenotazioni == null) {
                modulo = PrenotazioneModulo.get();

                if (modulo != null) {
                    nav = modulo.getNavStorico();
                    this.setNavPrenotazioni(nav);
                }// fine del blocco if

            }// fine del blocco if

            nav = this.navPrenotazioni;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    private void setNavPrenotazioni(NavStorico navPrenotazioni) {
        this.navPrenotazioni = navPrenotazioni;
    }


    /**
     * Ritorna il navigatore dei Conti.
     * <p/>
     *
     * @return il Navigatore dei Conti
     */
    private NavStorico getNavConti() {
        /* variabili e costanti locali di lavoro */
        NavStorico nav = null;
        ContoModulo modulo;

        try {    // prova ad eseguire il codice

            if (this.navConti == null) {
                modulo = ContoModulo.get();

                if (modulo != null) {
                    nav = modulo.getNavStorico();
                    this.setNavConti(nav);
                }// fine del blocco if

            }// fine del blocco if

            nav = this.navConti;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    private void setNavConti(NavStorico navConti) {
        this.navConti = navConti;
    }


    /**
     * Ritorna l'elenco degli anni selezionati nella lista Anni.
     * <p/>
     *
     * @return l'elenco degli anni
     */
    private int[] getAnniSelezionati() {
        /* variabili e costanti locali di lavoro */
        int[] anni = null;
        ListaSingola listaAnni;
        ArrayList oggetti;
        Object ogg;
        int anno;

        try {    // prova ad eseguire il codice
            listaAnni = this.getListaAnni();
            oggetti = listaAnni.getOggettiSelezionati();
            anni = new int[oggetti.size()];
            for (int k = 0; k < oggetti.size(); k++) {
                ogg = oggetti.get(k);
                anno = Libreria.getInt(ogg);
                anni[k] = anno;
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return anni;
    }


    /**
     * Seleziona un elenco di anni nella lista Anni.
     * <p/>
     *
     * @param anni l'elenco degli anni
     */
    private void setAnniSelezionati(int[] anni) {
        /* variabili e costanti locali di lavoro */
        ListaSingola listaAnni;
        ListaSingolaModello modello;
        ArrayList oggetti;
        ArrayList<Integer> elemAnni;

        Object ogg;
        int anno;

        ArrayList<Integer> indici;
        int indice;
        int[] inds;

        try {    // prova ad eseguire il codice

            /* recupera la lista di interi degli anni */
            listaAnni = this.getListaAnni();
            modello = listaAnni.getModello();
            oggetti = modello.getElementi();
            elemAnni = new ArrayList<Integer>();
            for (int k = 0; k < oggetti.size(); k++) {
                ogg = oggetti.get(k);
                anno = Libreria.getInt(ogg);
                elemAnni.add(anno);
            } // fine del ciclo for

            /**
             * Spazzola la lista di anni passata, li cerca e crea una lista dei
             * corrispondenti indici
             */
            indici = new ArrayList<Integer>();
            for (int unAnno : anni) {
                indice = elemAnni.indexOf(unAnno);
                if (indice >= 0) {
                    indici.add(indice);
                }// fine del blocco if
            }

            /* trasforma in array */
            inds = new int[indici.size()];
            for (int k = 0; k < indici.size(); k++) {
                inds[k] = indici.get(k);
            } // fine del ciclo for

            /* seleziona gli elementi nella lista */
            listaAnni.getLista().setSelectedIndices(inds);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il valore dell'opzione Solo Questo o Tutto il Gruppo.
     * <p/>
     *
     * @return true se Tutto il Gruppo, false se Solo Questo
     */
    private boolean isTuttoIlGruppo() {
        /* variabili e costanti locali di lavoro */
        boolean flag = false;
        Campo campo;
        int scelta;

        try {    // prova ad eseguire il codice
            campo = this.getCampoOpzioni();
            scelta = campo.getInt();
            if (scelta == 1) {
                flag = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return flag;
    }


    /**
     * Selezione lista anni modificata
     * <p/>
     */
    private void selListaAnniModificata() {
        this.showStorico();
    }


    /**
     * Opzioni Tutto il Gruppo o Solo Questo Cliente modificate
     * <p/>
     */
    private void opzioniModificate() {
        /* variabili e costanti locali di lavoro */
        int opzione;
        int[] anni;
        ListaSingola listaAnni;
        int[] selIdx;

        try { // prova ad eseguire il codice
            opzione = this.getCampoOpzioni().getInt();
            if (opzione != 0) {

                /* memorizza gli annni selezionati */
                anni = this.getAnniSelezionati();

                /* ricarica la lista degli anni */
                this.reloadAnni();

                /* riseleziona gli anni precedentemente selezionati (se ci sono ancora) */
                this.setAnniSelezionati(anni);

                /* se non ha selezionato nessun anno, seleziona il primo */
                listaAnni = this.getListaAnni();
                selIdx = listaAnni.getLista().getSelectedIndices();
                if (selIdx.length == 0) {
                    listaAnni.getLista().setSelectedIndex(0);
                }// fine del blocco if

                this.showStorico();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Azione selezione lista anni modificata
     * </p>
     */
    private final class AzSelLista extends ListaSelModAz {

        public void listaSelModAz(ListaSelModEve unEvento) {
            selListaAnniModificata();
        }
    } // fine della classe 'interna'


    /**
     * Azione campo Opzioni modificato
     * </p>
     */
    private final class AzOpzioniModificato extends CampoMemoriaAz {

        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            opzioniModificate();
        }
    } // fine della classe 'interna'


    private int getCodCliente() {
        return codCliente;
    }


    private void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }


    private ListaSingola getListaAnni() {
        return listaAnni;
    }


    private void setListaAnni(ListaSingola listaAnni) {
        this.listaAnni = listaAnni;
    }


    private PanTitolato getPanGruppo() {
        return panGruppo;
    }


    private void setPanGruppo(PanTitolato panGruppo) {
        this.panGruppo = panGruppo;
    }


    private PanTitolato getPanPresenze() {
        return panPresenze;
    }


    private void setPanPresenze(PanTitolato panPresenze) {
        this.panPresenze = panPresenze;
    }


    private PanTitolato getPanPrenotazioni() {
        return panPrenotazioni;
    }


    private void setPanPrenotazioni(PanTitolato panPrenotazioni) {
        this.panPrenotazioni = panPrenotazioni;
    }


    private PanTitolato getPanConti() {
        return panConti;
    }


    private void setPanConti(PanTitolato panConti) {
        this.panConti = panConti;
    }


    private Campo getCampoOpzioni() {
        return campoOpzioni;
    }


    private void setCampoOpzioni(Campo campoOpzioni) {
        this.campoOpzioni = campoOpzioni;
    }

    
}// fine della classe