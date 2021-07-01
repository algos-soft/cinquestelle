/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-mar-2006
 */
package it.algos.albergo.conto;

import it.algos.albergo.Albergo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.conto.addebitofisso.AddebitoFissoModulo;
import it.algos.albergo.conto.movimento.Movimento;
import it.algos.albergo.conto.pagamento.Pagamento;
import it.algos.albergo.conto.pagamento.PagamentoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.pensioni.WrapAddebiti;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.logica.LogicaBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progressbar.OperazioneMonitorabile;
import it.algos.base.progressbar.ProgressBar;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.stampa.Printer;
import it.algos.base.stampa.Stampa;
import it.algos.base.stampa.stampabile.Stampabile;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.SetValori;

import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Date;

import com.wildcrest.j2printerworks.Flowable;
import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2Printer;

/**
 * Business logic del package Conto.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 15.00.06
 */
public final class ContoLogica extends LogicaBase implements Conto {


    public static final String CAMPO_CAMERA = "camera";

    public static final String CAMPO_CLIENTE = "cliente";


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public ContoLogica(ContoModulo modulo) {
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
    }// fine del metodo inizia


    /**
     * Creazione di un nuovo conto.
     * </p>
     *
     * @param wrapConto contenente le informazioni
     * @param conn connessione da utilizzare (se null utilizza quella del modulo Conto)
     *
     * @return il codice del conto creato
     */
    int nuovoConto(WrapConto wrapConto, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ContoModulo modConto;
        Modulo modPagamenti;
        int codConto = 0;
        String sigla = "";

        Date dataApertura = null;
        int codCamera = 0;
        int codCliente = 0;
        int codAzienda = 0;
        Date dataInizioValidita = null;
        Date dataFineValidita = null;
        int arrivoCon = 0;

        SetValori setVal;
        ArrayList<CampoValore> listaCV;

        WrapAddebiti wrapAddebiti;
        double caparra = 0;
        int mezzoCaparra = 0;
        int ricevutaCaparra = 0;
        Date dataCaparra = null;
        int codPagam;
        int codPeriodo = 0;

        try { // prova ad eseguire il codice

            modConto = (ContoModulo)this.getModulo();
            continua = modConto != null;

            /* recupera i dati dal wrapper */
            if (continua) {
                dataApertura = wrapConto.getDataApertura();
                codCamera = wrapConto.getCodCamera();
                codCliente = wrapConto.getCodCliente();
                codAzienda = wrapConto.getCodAzienda();
                dataInizioValidita = wrapConto.getDataInizioValidita();
                dataFineValidita = wrapConto.getDataFineValidita();
                arrivoCon = wrapConto.getArrivoCon();
                caparra = wrapConto.getCaparra();
                mezzoCaparra = wrapConto.getMezzoCaparra();
                ricevutaCaparra = wrapConto.getRicevutaCaparra();
                dataCaparra = wrapConto.getDataCaparra();
                codPeriodo = wrapConto.getCodPeriodo();
                sigla = ContoModulo.creaSigla(codCamera, codCliente);
            }// fine del blocco if

            /* crea il record di Conto */
            if (continua) {
                setVal = new SetValori(modConto);
                setVal.add(Conto.Cam.dataApertura.get(), dataApertura);
                setVal.add(Conto.Cam.camera.get(), codCamera);
                setVal.add(Conto.Cam.pagante.get(), codCliente);
                setVal.add(Conto.Cam.azienda.get(), codAzienda);
                setVal.add(Conto.Cam.validoDal.get(), dataInizioValidita);
                setVal.add(Conto.Cam.validoAl.get(), dataFineValidita);

                if (arrivoCon>0) {
                    setVal.add(Conto.Cam.arrivoCon.get(), arrivoCon);
                }// fine del blocco if

                setVal.add(Conto.Cam.sigla.get(), sigla);
                setVal.add(Conto.Cam.periodo.get(), codPeriodo);
                listaCV = setVal.getListaValori();
                codConto = modConto.query().nuovoRecord(listaCV, conn);
                continua = codConto > 0;
            }// fine del blocco if

            /* crea gli addebiti continuativi */
            if (continua) {
                wrapAddebiti = wrapConto.getWrapAddebiti();
                if (wrapAddebiti != null) {
                    continua = wrapAddebiti.write(codConto, conn);
                }// fine del blocco if
            }// fine del blocco if

            /* crea l'eventuale registrazione di pagamento per caparra */
            if (continua) {
                if (caparra != 0) {
                    modPagamenti = PagamentoModulo.get();
                    if (modPagamenti != null) {
                        setVal = new SetValori(modPagamenti);
                        setVal.add(Pagamento.Cam.conto.get(), codConto);
                        setVal.add(Pagamento.Cam.data.get(), dataCaparra);
                        setVal.add(Pagamento.Cam.titolo.get(),
                                Pagamento.TitoloPagamento.caparra.getCodice());
                        setVal.add(Pagamento.Cam.mezzo.get(), mezzoCaparra);
                        setVal.add(Pagamento.Cam.ricevuta.get(), ricevutaCaparra);
                        setVal.add(Pagamento.Cam.importo.get(), caparra);
                        listaCV = setVal.getListaValori();
                        codPagam = modPagamenti.query().nuovoRecord(listaCV, conn);
                        continua = (codPagam > 0);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* se fallito azzera il codice conto in uscita */
            if (!continua) {
                codConto = 0;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codConto;
    }


    /**
     * Aggiunge una serie di addebiti continuativi a un conto.
     * <p/>
     *
     * @param codConto codice del conto
     * @param wrapAddebiti contenente i dati degli addebiti
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    boolean creaAddebiti(int codConto, WrapAddebiti wrapAddebiti, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        boolean continua;

        ArrayList<WrapAddebiti.Elemento> elementi;
        ArrayList<WrapAddebiti.Dettaglio> dettagli;
        int codListino;
        Date data1;
        Date data2;
        int quantita;
        double prezzo;
        Modulo modAddebitoFisso;
        SetValori setVal;
        ArrayList<CampoValore> listaCV;
        int codAddFisso;

        try {    // prova ad eseguire il codice

            continua = (codConto > 0) && (wrapAddebiti != null);

            /* crea gli addebiti continuativi */
            if (continua) {

                elementi = wrapAddebiti.getElementi();

                if (elementi != null) {

                    modAddebitoFisso = AddebitoFissoModulo.get();

                    for (WrapAddebiti.Elemento elemento : elementi) {

                        codListino = elemento.getCodListino();
                        dettagli = elemento.getDettagli();

                        for (WrapAddebiti.Dettaglio dettaglio : dettagli) {

                            data1 = dettaglio.getData1();
                            data2 = dettaglio.getData2();
                            quantita = dettaglio.getQuantita();
                            prezzo = dettaglio.getPrezzo();

                            setVal = new SetValori(modAddebitoFisso);
                            setVal.add(Addebito.Cam.conto.get(), codConto);
                            setVal.add(Addebito.Cam.listino.get(), codListino);
                            setVal.add(AddebitoFisso.Cam.dataInizioValidita.get(), data1);
                            setVal.add(AddebitoFisso.Cam.dataFineValidita.get(), data2);
                            setVal.add(Addebito.Cam.quantita.get(), quantita);
                            setVal.add(Addebito.Cam.prezzo.get(), prezzo);
                            listaCV = setVal.getListaValori();
                            codAddFisso = modAddebitoFisso.query().nuovoRecord(listaCV, conn);
                            if (codAddFisso <= 0) {
                                riuscito = false;
                                break;
                            }// fine del blocco if
                        }
                    }
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


//    /**
//     * Creazione del dialogo nuovo conto.
//     * </p>
//     * Metodo invocato dal modulo <br>
//     */
//    public void nuovoConto() {
//        /* variabili e costanti locali di lavoro */
//        ContoModulo modulo;
//        ContoDialogoApertura dialogo;
//
//        try { // prova ad eseguire il codice
//
//            /* disabilitato alex 23-06-08 */
//            new MessaggioAvviso("Funzione disabilitata - usare il bottone standard Nuovo Record");
//            if (false) {
//                modulo = (ContoModulo)this.getModulo();
//                dialogo = new ContoDialogoApertura(modulo);
//                this.setDialogoApertura(dialogo);
//                dialogo.avvia();
//            }// fine del blocco if
//
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }






    /**
     * Chiusura del conto selezionato.
     * </p>
     * Apre un dialogo <br>
     *
     * @param codice del conto da chiudere
     * @param usatoDaPartenza flag per aggiungere un bottone specifico
     *
     * @return un oggetto indicante il tipo di chiusura
     */
    Chiusura chiusuraConto(int codice, boolean usatoDaPartenza) {
        /* variabili e costanti locali di lavoro */
        Chiusura chiusura = null;
        boolean continua;
        boolean riuscito;
        boolean chiuso;
        ContoDialogoChiusura dialogo = null;
        ArrayList<CampoValore> campi;
        CampoValore cv;
        Campo campo;
        Object valore;
        String risposta;

        try { // prova ad eseguire il codice

            /* controllo esistenza codice */
            continua = (codice > 0);

            /* controllo che non sia gia' chiuso */
            if (continua) {
                chiuso = this.getModulo().query().valoreBool(Conto.Cam.chiuso.get(), codice);
                continua = (!chiuso);
            }// fine del blocco if

            /* mostra il dialogo di chiusura */
            if (continua) {
                dialogo = new ContoDialogoChiusura(codice);
                if (usatoDaPartenza) {
                    dialogo.setChisuraPartenza();
                }// fine del blocco if
                dialogo.avvia();

                if (dialogo.isConfermato()) {
                    continua = true;
                } else {
                    continua = false;
                    risposta = dialogo.getBottonePremuto();
                    chiusura = Chiusura.get(risposta);
                }// fine del blocco if-else
            }// fine del blocco if

            /* effettua le registrazioni di pagamento */
            if (continua) {
                continua = dialogo.creaPagamenti();
            }// fine del blocco if

            /* esegue la chiusura del conto */
            if (continua) {
                campi = new ArrayList<CampoValore>();
                campo = Albergo.Moduli.conto.getCampo(Conto.Cam.chiuso.get());
                valore = true;
                cv = new CampoValore(campo, valore);
                campi.add(cv);
                campo = Albergo.Moduli.conto.getCampo(Conto.Cam.dataChiusura.get());
                valore = dialogo.getDataChiusura();
                cv = new CampoValore(campo, valore);
                campi.add(cv);
                campo = Albergo.Moduli.conto.getCampo(Conto.Cam.partEffettiva.get());
                valore = dialogo.getDataChiusura();
                cv = new CampoValore(campo, valore);
                campi.add(cv);
                riuscito = this.getModulo().query().registraRecordValori(codice, campi);

                if (riuscito) {
                    chiusura = Chiusura.confermato;
                } else {
                    chiusura = Chiusura.nonChiuso;
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiusura;
    }


    /**
     * Incasso sospesi per il conto selezionato.
     * </p>
     *
     * @param codice del conto per il quale incassare i sospesi
     *
     * @return true se riuscito
     */
    public boolean incassoSospesi(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        double totSospesi;
        ContoDialogoIncassoSospesi dialogo = null;

        try { // prova ad eseguire il codice

            /* controllo esistenza codice */
            continua = (codice > 0);

            /* controllo che il conto abbia dei sospesi */
            if (continua) {
                totSospesi = ContoModulo.getTotSospesi(codice, null);
                if (totSospesi == 0) {
                    new MessaggioAvviso("Questo conto non ha sospesi da incassare.");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* mostra il dialogo di incasso sospesi */
            if (continua) {
                dialogo = new ContoDialogoIncassoSospesi(codice);
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            /* effettua le registrazioni di pagamento e di cancellazione
             * ed eventuale rigenerazione sospesi */
            if (continua) {
                continua = dialogo.creaPagamenti();
            }// fine del blocco if

            /* aggiorna la lista del navigatore corrente */
            if (continua) {
                this.getModulo().getNavigatoreCorrente().aggiornaLista();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Stampa di un conto.
     * </p>
     *
     * @param codice del conto da stampare
     * @param tipo di stampa (completo, solo pensione, solo extra)
     * @param raggruppa true per raggruppare le voci di pensione
     */
    public void stampaConto(int codice, Conto.TipiStampa tipo, boolean raggruppa) {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

//            Stampabile stampa = new ContoStampaOld(codice, tipo, raggruppa);
//            Stampa.eseguiStampa(stampa);
            
            // crea un pageable con dentro un printable e lo stampa in preview
        	// alex Ago-2013
        	Pageable pageable = new ContoPageable(new ContoPrintable(codice, tipo, raggruppa));
            Printer printer = new Printer();
            printer.setPageable(pageable);
            printer.showPrintPreviewDialog();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controllo addebiti mancanti.
     * </p>
     * Carica in lista i conti che non hanno addebiti
     * del tipo specificato per il periodo specificato.
     * <p/>
     * Opera solo per l'azienda corrente
     *
     * @param dataInizio la data inizio controllo
     * @param dataFine la data fine controllo
     * @param tipo 1=pensione, 2=extra
     * @param codiciConto elenco dei conti da controllare
     */
    public void controlloAddebitiMancanti(Date dataInizio,
                                          Date dataFine,
                                          int tipo,
                                          ArrayList<Integer> codiciConto) {
        /* variabili e costanti locali di lavoro */
        OpCheckMancanti operazione;
        Navigatore nav;
        ProgressBar pb;
        Modulo modConto;

        try { // prova ad eseguire il codice

            modConto = Albergo.Moduli.Conto();
            nav = modConto.getNavigatoreCorrente();
            pb = nav.getProgressBar();

            operazione = new OpCheckMancanti(pb, dataInizio, dataFine, tipo, codiciConto);
            operazione.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Esecuzione controllo addebiti 'mancanti'
     * </p>
     * Monitorata da ProgressBar
     */
    public final class OpCheckMancanti extends OperazioneMonitorabile {

        private Date dataInizio;

        private Date dataFine;

        private int tipo;

        private ArrayList<Integer> codiciConto;

        private int quanti;

        private ArrayList<Integer> codContiDanger;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param pb la progress bar
         * @param dataInizio la data inizio esecuzione
         * @param dataFine la data fine esecuzione
         * @param tipo di controllo 1=pensione, 2=extra
         * @param codiciConto i codici dei conti per i quali eseguire l'operazione
         */
        public OpCheckMancanti(ProgressBar pb,
                               Date dataInizio,
                               Date dataFine,
                               int tipo,
                               ArrayList<Integer> codiciConto) {
            /* rimanda al costruttore della superclasse */
            super(pb);

            this.dataInizio = dataInizio;
            this.dataFine = dataFine;
            this.tipo = tipo;
            this.codiciConto = codiciConto;

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
            this.setMessaggio("Controllo addebiti");
            this.setBreakAbilitato(true);
            codContiDanger = new ArrayList<Integer>();
        }


        public void start() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Date> dateUniche;
            boolean passed;

            try { // prova ad eseguire il codice

                /* carica l'elenco dei conti a rischio */
                for (int cod : codiciConto) {
                    quanti++;
                    dateUniche = caricaAddebitiPeriodo(cod, tipo, dataInizio, dataFine);
                    passed = checkDateUniche(dateUniche, dataInizio, dataFine);
                    if (!passed) {
                        codContiDanger.add(cod);
                    }// fine del blocco if

                    /* controllo interruzione */
                    if (super.isInterrompi()) {
                        break;
                    }// fine del blocco if

                }

                /* visualizza il risultato */
                this.displayRisultato();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Visualizza il risultato della operazione.
         * <p/>
         */
        private void displayRisultato() {
            /* variabili e costanti locali di lavoro */
            String stringa;
            Modulo modConto;
            Navigatore nav;
            Lista lista;

            try {    // prova ad eseguire il codice

                modConto = Albergo.Moduli.Conto();
                nav = modConto.getNavigatoreCorrente();

                if (tipo == 1) {
                    stringa = "di pensione";
                } else {
                    stringa = "extra";
                }// fine del blocco if-else

                /* visualizza l'esito dell'operazione */
                if (codContiDanger.size() > 0) {
                    new MessaggioAvviso("Controllo fallito!" +
                            "\nCi sono conti senza un addebito " +
                            stringa +
                            " per giorno nel periodo specificato." +
                            "\nI conti da controllare verranno caricati in lista.");
                    Filtro filtroTot = new Filtro();
                    for (int cod : codContiDanger) {
                        Filtro filtro = FiltroFactory.crea(modConto.getCampoChiave(), cod);
                        filtroTot.add(Filtro.Op.OR, filtro);
                    }

                    lista = nav.getLista();
                    lista.setFiltroCorrente(filtroTot);
                    nav.aggiornaLista();
                } else {
                    new MessaggioAvviso("Controllo passato con successo." +
                            "\nTutti i conti esaminati hanno almeno un addebito " +
                            stringa +
                            " per giorno nel " +
                            "periodo specificato.");
                }// fine del blocco if-else
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        public int getMax() {
            return this.codiciConto.size();
        }


        public int getCurrent() {
            return quanti;
        }


        /**
         * Ritorna l'elenco dei conti trovati mancanti di qualche addebito.
         * <p/>
         *
         * @return la lista dei codici conto trovati
         */
        public ArrayList<Integer> getContiTrovati() {
            /* valore di ritorno */
            return this.codContiDanger;
        }


    } // fine della classe 'interna'


    /**
     * Carica la lista degli addebiti di un dato conto per un dato periodo.
     * <p/>
     * La lista ritornata contiene tutte le istanze uniche
     * delle date di addebito ordinate per data.
     *
     * @param codConto di riferimento
     * @param tipo 1=pensione, 2=extra
     * @param d1 la data inizio controllo
     * @param d2 la data fine controllo
     *
     * @return la lista degli addebiti
     */
    private ArrayList<Date> caricaAddebitiPeriodo(int codConto, int tipo, Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Date> dateOut = null;
        ArrayList<Object> listaUnica;
        ArrayList<Object> listaDateAddebiti;
        Modulo modAddebito;
        Filtro filtroConto;
        Filtro filtroDataIni;
        Filtro filtroDataFine;
        Filtro filtroPeriodo;
        Filtro filtroTipi;
        Filtro filtroAddebiti;
        Ordine ordine;
        Date data;
        boolean tipoAmbito;

        try {    // prova ad eseguire il codice

            dateOut = new ArrayList<Date>();

            modAddebito = Albergo.Moduli.Addebito();

            filtroConto = FiltroFactory.crea(Addebito.Cam.conto.get(), codConto);

            filtroDataIni = FiltroFactory.crea(Addebito.Cam.data.get(),
                    Filtro.Op.MAGGIORE_UGUALE,
                    d1);
            filtroDataFine = FiltroFactory.crea(Addebito.Cam.data.get(),
                    Filtro.Op.MINORE_UGUALE,
                    d2);
            filtroPeriodo = new Filtro();
            filtroPeriodo.add(filtroDataIni);
            filtroPeriodo.add(filtroDataFine);

            /* determina il valore del campo Fisso*/
            tipoAmbito = (tipo == 1);

            filtroAddebiti = new Filtro();
            filtroAddebiti.add(filtroConto);
            filtroAddebiti.add(filtroPeriodo);

            filtroTipi = Listino.AmbitoPrezzo.getFiltroListino(tipoAmbito);
            filtroAddebiti.add(filtroTipi);

            ordine = new Ordine();
            ordine.add(Addebito.Cam.data.get());

            listaDateAddebiti = modAddebito.query()
                    .valoriCampo(Addebito.Cam.data.get(), filtroAddebiti, ordine);
            listaUnica = Lib.Array.listaUnica(listaDateAddebiti);

            for (Object oggetto : listaUnica) {
                if (oggetto instanceof Date) {
                    data = (Date)oggetto;
                    dateOut.add(data);
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dateOut;
    }


    /**
     * Controlla che una lista di date uniche e ordinate
     * contenga tutte le date tra d1 e d2.
     * Per passare deve anche contenere almeno una data.
     * <p/>
     *
     * @param date lista di date uniche e ordinate
     * @param d1 la prima data
     * @param d2 la seconda data
     *
     * @return true se contiene tutte le date
     */
    private boolean checkDateUniche(ArrayList<Date> date, Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        boolean passed = false;
        Date primaData;
        Date ultimaData;
        int quanteDate;
        int quantiGiorni;

        try {    // prova ad eseguire il codice

            /* controlla che contenga almeno una data */
            passed = (date.size() > 0);

            /* controlla che la prima data sia uguale a d1 */
            if (passed) {
                primaData = date.get(0);
                passed = (primaData.equals(d1));
            }// fine del blocco if

            /* controlla che l'ultima data sia uguale a d2 */
            if (passed) {
                ultimaData = date.get(date.size() - 1);
                passed = (ultimaData.equals(d2));
            }// fine del blocco if

            /* controlla che il numero di elementi
             * sia uguale al numero dei giorni */
            if (passed) {
                quantiGiorni = Lib.Data.diff(d2, d1);
                quanteDate = date.size();
                passed = (quanteDate == quantiGiorni + 1);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return passed;
    }

    

    
    /**
     * Toglie il flag caparra accreditata alla Prenotazione relativa a un dato conto.
     * @param idConto l'id del conto da verificare
     * @param conn connessione da utilizzare per le query
     */
    public static void RemoveFlagCaparraAccreditata(int idConto, Connessione conn){
        Campo campoIdPeriodo = ContoModulo.get().getCampo(Conto.Cam.periodo);
        int idPeriodo = ContoModulo.get().query().valoreInt(campoIdPeriodo, idConto, conn);
        if (idPeriodo!=0) {
            Campo campoIdPren = PeriodoModulo.get().getCampo(Periodo.Cam.prenotazione);
            int idPren = PeriodoModulo.get().query().valoreInt(campoIdPren, idPeriodo, conn);
            PrenotazioneModulo.get().query().registra(idPren, Prenotazione.Cam.caparraAccreditata.get(), false, conn);
		}
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Chiusura {

        interrotto("Annulla partenza"),
        nonChiuso("Non chiudere il conto"),
        confermato("Chiudi il conto");

        /**
         * titolo da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         */
        Chiusura(String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static Chiusura get(String nome) {
            /* variabili e costanti locali di lavoro */
            Chiusura chiusura = null;

            try { // prova ad eseguire il codice
                for (Chiusura ele : values()) {
                    if (ele.toString().equals(nome)) {
                        chiusura = ele;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return chiusura;
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }

    }// fine della classe


}// fine della classe
