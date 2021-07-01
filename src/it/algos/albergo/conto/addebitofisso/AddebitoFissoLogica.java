/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-mar-2006
 */
package it.algos.albergo.conto.addebitofisso;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoDialogoEseguiFissi;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.logica.LogicaBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.progetto.Progetto;
import it.algos.base.progressbar.OperazioneMonitorabile;
import it.algos.base.progressbar.ProgressBar;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.WrapListino;

import java.util.ArrayList;
import java.util.Date;

/**
 * Business logic del package addebito.
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
public class AddebitoFissoLogica extends LogicaBase implements AddebitoFisso {

    private DialogoAggiungiFissi dialogoAddFissi;

    private ContoDialogoEseguiFissi dialogoSyncFissi;

//    private static final String dataIni = "dal";
//
//    private static final String dataFine = "al";


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo
     */
    public AddebitoFissoLogica(Modulo modulo) {
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
     * Crea gli addebiti giornalieri per un dato conto
     * <p/>
     * Vengono generati gli addebiti giornalieri per coprire un dato periodo.
     * Gli addebiti vengono generati per ogni riga di sottoconto selezionata
     * nel pannello, chiedendo al listino fisso di fornire le righe necessarie.
     * L'addebito viene creato solo se sono stati inseriti
     * tutti i dati necessari <br>
     *
     * @param pannello contenente le righe di sottoconto selezionate
     * @param codConto il codice del conto pr il quale generare gli addebiti fissi
     * @param dataInizio la data iniziale del periodo da coprire
     * @param dataFine la data finale del periodo da coprire
     */
    public void creaAddebitiGiornalieri(AddebitoFissoPannello pannello,
                                        int codConto,
                                        Date dataInizio,
                                        Date dataFine) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Modulo modAddFisso;
        ArrayList<CampoValore> campiFissi;
        Campo campoQuery;
        CampoValore campoVal;
        ArrayList<WrapListino> lista;
        ArrayList<AddebitoFissoPannello.Riga> righeDialogo = null;
        int quantita;

        try { // prova ad eseguire il codice

            modAddFisso = this.getModulo();
            campiFissi = new ArrayList<CampoValore>();

            /* recupera dal dialogo il valore obbligatorio del conto */
            if (continua) {
                campoQuery = modAddFisso.getCampo(Addebito.Cam.conto.get());
                campoVal = new CampoValore(campoQuery, codConto);
                campiFissi.add(campoVal);
            }// fine del blocco if

            /* recupera dal dialogo il pacchetto di righe selezionate */
            if (continua) {
                righeDialogo = pannello.getRigheSelezionate();
            }// fine del blocco if

            /* crea il pacchetto delle righe di addebito fisso da creare */
            if (continua) {

                /* traverso tutta la collezione delle righe selezionate nel pannello */
                for (AddebitoFissoPannello.Riga riga : righeDialogo) {
                    lista = ListinoModulo.getPrezzi(riga.getCodListino(),
                            dataInizio,
                            dataFine,
                            true,
                            false);
                    quantita = riga.getQuantita();
                    for (WrapListino wrapper : lista) {
                        this.creaAddebitoFisso(codConto, dataInizio, wrapper, quantita);
                    }
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Crea un addebito fisso.
     * <p/>
     *
     * @param codConto codice del conto
     * @param dataInizio la data di inizio validità
     * @param wrapper oggetto WrapListino
     * @param quantita da addebitare
     */
    private void creaAddebitoFisso(int codConto,
                                   Date dataInizio,
                                   WrapListino wrapper,
                                   int quantita) {
        /* variabili e costanti locali di lavoro */
        Modulo modAddFisso;
        ArrayList<CampoValore> campi;
        Campo campoQuery;
        CampoValore campoVal;
        Object valore;
        Date unaData;
        int max;
        int next;

        try { // prova ad eseguire il codice

            modAddFisso = Albergo.Moduli.addebitoFisso.getModulo();

            /* determina il prossimo numero d'ordine all'interno del conto */
            Filtro filtro = FiltroFactory.crea(Addebito.Cam.conto.get(), codConto);
            max = modAddFisso.query().valoreMassimo(modAddFisso.getCampoOrdine(), filtro);
            next = max + 1;

            campi = new ArrayList<CampoValore>();

            /* aggiungo il campo Ordine */
            campoQuery = modAddFisso.getCampoOrdine();
            campoVal = new CampoValore(campoQuery, next);
            campi.add(campoVal);

            /* aggiungo la data di inizio periodo restituitami dal listino */
            campoQuery = modAddFisso.getCampo(AddebitoFisso.Cam.dataInizioValidita.get());
            valore = wrapper.getPrimaData();
            campoVal = new CampoValore(campoQuery, valore);
            campi.add(campoVal);

            campoQuery = modAddFisso.getCampo(AddebitoFisso.Cam.dataFineValidita.get());
            valore = wrapper.getSecondaData();
            campoVal = new CampoValore(campoQuery, valore);
            campi.add(campoVal);

            campoQuery = modAddFisso.getCampo(Addebito.Cam.quantita.get());
            valore = quantita;
            campoVal = new CampoValore(campoQuery, valore);
            campi.add(campoVal);

            campoQuery = modAddFisso.getCampo(Addebito.Cam.prezzo.get());
            valore = wrapper.getPrezzo();
            campoVal = new CampoValore(campoQuery, valore);
            campi.add(campoVal);

            campoQuery = modAddFisso.getCampo(Addebito.Cam.listino.get());
            valore = wrapper.getCodListino();
            campoVal = new CampoValore(campoQuery, valore);
            campi.add(campoVal);

            /* aggiunge il campo codice del conto */
            campoQuery = modAddFisso.getCampo(Addebito.Cam.conto.get());
            campoVal = new CampoValore(campoQuery, codConto);
            campi.add(campoVal);

            modAddFisso.query().nuovoRecord(campi);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea gli addebiti in base agli addebiti fissi
     * </p>
     * Opera per un dato periodo e un dato elenco di conti.
     *
     * @param dataInizio la data inizio esecuzione
     * @param dataFine la data fine esecuzione
     * @param codiciConto i codici dei conti per i quali eseguire l'operazione
     */
    public void creaAddebiti(Date dataInizio, Date dataFine, ArrayList<Integer> codiciConto) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Date dataCorr;
        Modulo modulo;
        Navigatore nav;
        ProgressBar pb;
        OpAddebiti operazione;

        try { // prova ad eseguire il codice

            /* controllo di sicurezza che le date siano in sequenza */
            continua = Lib.Data.isSequenza(dataInizio, dataFine);

            /* esecuzione operazione */
            if (continua) {
                modulo = Albergo.Moduli.Conto();
                nav = modulo.getNavigatoreCorrente();
                pb = nav.getProgressBar();
                operazione = new OpAddebiti(pb, dataInizio, dataFine, codiciConto);
                operazione.avvia();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Esecuzione addebiti fissi
     * </p>
     * Monitorata da ProgressBar
     */
    public final class OpAddebiti extends OperazioneMonitorabile {

        private Date dataInizio;

        private Date dataFine;

        private ArrayList<Integer> codiciConto;

        private int quanti;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param pb la progress bar
         * @param dataInizio la data inizio esecuzione
         * @param dataFine la data fine esecuzione
         * @param codiciConto i codici dei conti per i quali eseguire l'operazione
         */
        public OpAddebiti(ProgressBar pb,
                          Date dataInizio,
                          Date dataFine,
                          ArrayList<Integer> codiciConto) {
            /* rimanda al costruttore della superclasse */
            super(pb);

            this.dataInizio = dataInizio;
            this.dataFine = dataFine;
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
            this.setMessaggio("Addebiti in corso");
            this.setBreakAbilitato(true);
        }


        public void start() {
            /* variabili e costanti locali di lavoro */
            Date dataCorr;

            try { // prova ad eseguire il codice
                for (int codConto : codiciConto) {
                    quanti++;
                    dataCorr = dataInizio;
                    while (Lib.Data.isPrecedenteUguale(dataFine, dataCorr)) {
                        creaAddebitiGiornoConto(dataCorr, codConto);
                        dataCorr = Lib.Data.add(dataCorr, 1);

                        /* interruzione nella superclasse */
                        if (super.isInterrompi()) {
                            break;
                        }// fine del blocco if

                    }// fine del blocco while
                }// fine del blocco for
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        public int getMax() {
            return this.codiciConto.size();
        }


        public int getCurrent() {
            return quanti;
        }


    } // fine della classe 'interna'


    /**
     * Esegue tutti gli addebiti di una giornata per un dato conto.
     * </p>
     * Opera solo se il conto e' aperto
     *
     * @param codConto il codice del conto per il quale eseguire l'operazione
     */
    private void creaAddebitiGiornoConto(Date data, int codConto) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Filtro filtro;
        int[] interi;
        ArrayList<Integer> codici = null;
        Modulo modAddFisso;
        Modulo modConto;
        boolean chiuso;

        try { // prova ad eseguire il codice

            /* controllo se il conto e' aperto */
            modConto = Albergo.Moduli.Conto();
            chiuso = modConto.query().valoreBool(Conto.Cam.chiuso.get(), codConto);
            continua = (!chiuso);

            if (continua) {

                /* filtro che isola gli addebiti fissi da eseguire
                 * per il giorno e il conto dati */
                filtro = this.getFiltroFissiGiornoConto(data, codConto);
                continua = filtro != null;

                /* crea elenco dei codici addebito fisso da elaborare */
                if (continua) {
                    modAddFisso = Albergo.Moduli.AddebitoFisso();
                    interi = modAddFisso.query().valoriChiave(filtro);
                    codici = Lib.Array.creaLista(interi);
                }// fine del blocco if

                /* crea un addebito effettivo per ogni addebito fisso */
                if (codici != null) {
                    for (int cod : codici) {
                        this.creaSingoloAddebito(cod, data);
                    } // fine del ciclo for-each
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un singolo addebito.
     * </p>
     *
     * @param cod codice del record di addebito fisso da addebitare
     * @param data del giorno di addebito
     */
    private int creaSingoloAddebito(int cod, Date data) {
        /* variabili e costanti locali di lavoro */
        int nuovoRecord = 0;
        boolean continua;
        Dati dati;
        int codConto = 0;
        int codListino = 0;
        int quantita = 0;
        double prezzo = 0.0;
        Campo campoConto = null;
        Campo campoListino = null;
        Campo campoQuantita = null;
        Campo campoPrezzo = null;
        Campo campoData = null;
        Campo campoFissoConto = null;
        Campo campoFissoListino = null;
        Campo campoFissoQuantita = null;
        Campo campoFissoPrezzo = null;
        ArrayList<CampoValore> campi = null;
        Modulo modAddebito = null;
        Modulo modAddebitoFisso = null;

        try { // prova ad eseguire il codice

            modAddebito = Progetto.getModulo(Addebito.NOME_MODULO);
            modAddebitoFisso = Progetto.getModulo(AddebitoFisso.NOME_MODULO);

            /* carica tutti i dati dall'addebito fisso */
            dati = modAddebitoFisso.query().caricaRecord(cod);
            continua = dati != null;

            /* recupera i campi da leggere e da scrivere */
            if (continua) {

                /* campi del modulo Addebito Fisso (da leggere) */
                campoFissoConto = modAddebitoFisso.getCampo(Addebito.Cam.conto.get());
                campoFissoListino = modAddebitoFisso.getCampo(Addebito.Cam.listino.get());
                campoFissoQuantita = modAddebitoFisso.getCampo(Addebito.Cam.quantita.get());
                campoFissoPrezzo = modAddebitoFisso.getCampo(Addebito.Cam.prezzo.get());

                /* campi del modulo Addebito (da scrivere) */
                campoConto = modAddebito.getCampo(Addebito.Cam.conto.get());
                campoListino = modAddebito.getCampo(Addebito.Cam.listino.get());
                campoQuantita = modAddebito.getCampo(Addebito.Cam.quantita.get());
                campoPrezzo = modAddebito.getCampo(Addebito.Cam.prezzo.get());
                campoData = modAddebito.getCampo(Addebito.Cam.data.get());

            }// fine del blocco if

            /* legge i dati dal record di addebito fisso */
            if (continua) {
                codConto = dati.getIntAt(campoFissoConto);
                codListino = dati.getIntAt(campoFissoListino);
                quantita = dati.getIntAt(campoFissoQuantita);
                prezzo = (Double)dati.getValueAt(0, campoFissoPrezzo);
                dati.close();
            }// fine del blocco if

            /* crea un nuovo record in addebito */
            if (continua) {
                campi = new ArrayList<CampoValore>();
                campi.add(new CampoValore(campoConto, codConto));
                campi.add(new CampoValore(campoListino, codListino));
                campi.add(new CampoValore(campoQuantita, quantita));
                campi.add(new CampoValore(campoPrezzo, prezzo));
                campi.add(new CampoValore(campoData, data));
                nuovoRecord = modAddebito.query().nuovoRecord(campi);
                continua = nuovoRecord > 0;
            }// fine del blocco if

            /* aggiorna la data di sincronizzazione in addebito fisso */
            if (continua) {
                this.getModulo().query().registraRecordValore(cod, Cam.dataSincro.get(), data);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nuovoRecord;
    }


    /**
     * Crea un filtro che isola gli addebiti fissi da eseguire
     * per una certa data e per un dato conto.
     * </p>
     *
     * @param data la data del giorno da addebitare
     * @param codConto il codice del conto per il quale eseguire l'operazione
     *
     * @return il filtro applicabile al modulo AddebitoFisso
     */
    private Filtro getFiltroFissiGiornoConto(Date data, int codConto) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroDate = null;
        Filtro filtroInizio = null;
        Filtro filtroSincro;
        Filtro filtroVuota;
        Filtro filtroIntervallo;
        Filtro filtroFine;
        Filtro filtroConto = null;
        Modulo modConto;
        Date dataVuota;

        try { // prova ad eseguire il codice

            modConto = Progetto.getModulo(Conto.NOME_MODULO);

            filtroDate = new Filtro();

            filtroInizio = FiltroFactory.crea(Cam.dataInizioValidita.get(),
                    Filtro.Op.MINORE_UGUALE,
                    data);
            filtroSincro = FiltroFactory.crea(Cam.dataSincro.get(), Filtro.Op.MINORE, data);
            dataVuota = Lib.Data.getVuota();
            filtroVuota = FiltroFactory.crea(Cam.dataSincro.get(), dataVuota);

            filtroFine = FiltroFactory.crea(Cam.dataFineValidita.get(),
                    Filtro.Op.MAGGIORE_UGUALE,
                    data);
            filtroIntervallo = new Filtro();
            filtroIntervallo.add(filtroSincro);
            filtroIntervallo.add(filtroFine);

            filtroDate.add(filtroIntervallo);
            filtroDate.add(Filtro.Op.OR, filtroVuota);

            /* filtro per il conto */
            filtroConto = FiltroFactory.codice(modConto, codConto);

            filtro = new Filtro();
            filtro.add(filtroInizio);
            filtro.add(filtroDate);
            filtro.add(filtroConto);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    private ContoDialogoEseguiFissi getDialogoSyncFissi() {
        return dialogoSyncFissi;
    }


    private void setDialogoSyncFissi(ContoDialogoEseguiFissi dialogo) {
        this.dialogoSyncFissi = dialogo;
    }


    private DialogoAggiungiFissi getDialogoAddFissi() {
        return dialogoAddFissi;
    }


    protected void setDialogoAddFissi(DialogoAggiungiFissi dialogoAddFissi) {
        this.dialogoAddFissi = dialogoAddFissi;
    }


    /**
     * Dialogo di aggiunta addebiti fissi da addebiti fissi
     * </p>
     */
    public abstract class DialogoAggiungiFissi extends DialogoAnnullaConferma {

        private AddebitoFissoPannello panServizi;

        /* nomi dei campi del dialogo */
        protected static final String nomeDataIni = "dal";

        protected static final String nomeDataFine = "al";

        protected static final String nomeConto = "conto";


        /**
         * Costruttore completo con parametri
         * <p/>
         */
        public DialogoAggiungiFissi() {
            /* rimanda al costruttore della superclasse */
            super();


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


        public boolean isConfermabile() {
            /* variabili e costanti locali di lavoro */
            boolean confermabile = false;
            Date d1, d2;

            try { // prova ad eseguire il codice
                confermabile = super.isConfermabile();

                /* controllo che le date siano in sequenza */
                if (confermabile) {
                    d1 = this.getDataInizio();
                    d2 = this.getDataFine();
                    confermabile = Lib.Data.isSequenza(d1, d2);
                }// fine del blocco if

                /* controllo che almeno una riga sia selezionata */
                if (confermabile) {
                    confermabile = getPanServizi().getRigheSelezionate().size() > 0;
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return confermabile;

        }


        protected void eventoMemoriaModificata(Campo campo) {
        }


        protected void eventoUscitaCampoModificato(Campo campo) {

            if (campo.equals(this.getCampo(nomeDataIni))) {
                Date data = (Date)campo.getValore();
                this.getPanServizi().setDataPrezzi(data);
            }// fine del blocco if

        }


        public void eventoConferma() {
            if (this.checkListino()) {
                super.eventoConferma();
            }// fine del blocco if
        }


        /**
         * Controlla che il listino sia in grado di fornire i dati..
         * <p/>
         * Invocato quando si preme il pulsante Registra.
         * Delega il controllo al pannello Servizi
         * Se non passa viene visualizzato un errore
         *
         * @return true se passati
         */
        private boolean checkListino() {
            /* variabili e costanti locali di lavoro */
            boolean ok = false;
            Date dataInizio;
            Date dataFine;
            AddebitoFissoPannello panServizi;

            try {    // prova ad eseguire il codice
                dataInizio = this.getDataInizio();
                dataFine = this.getDataFine();
                panServizi = this.getPanServizi();
                ok = panServizi.checkListino(dataInizio, dataFine);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return ok;
        }


        public AddebitoFissoPannello getPanServizi() {
            return panServizi;
        }


        protected void setPanServizi(AddebitoFissoPannello pan) {
            this.panServizi = pan;
        }


        /**
         * Ritorna la data di inizio impostata nel dialogo.
         * <p/>
         */
        public Date getDataInizio() {
            return (Date)this.getCampo(nomeDataIni).getValore();
        }


        /**
         * Ritorna la data di fine impostata nel dialogo.
         * <p/>
         */
        public Date getDataFine() {
            return (Date)this.getCampo(nomeDataFine).getValore();
        }


        /**
         * Ritorna il codice conto impostato nel dialogo.
         * <p/>
         */
        public int getConto() {
            return (Integer)this.getCampo(nomeConto).getValore();
        }


    } // fine della classe 'interna'


    /**
     * Dialogo di aggiunta addebiti fissi chiamato dal conto
     * </p>
     */
    public final class DialogoAggiungiFissiConto extends DialogoAggiungiFissi {

        private int codConto;


        /**
         * Costruttore completo con parametri
         * <p/>
         */
        public DialogoAggiungiFissiConto(int codConto) {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setCodConto(codConto);

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
            /* variabili e costanti locali di lavoro */
            Campo campoDataInizio;
            Campo campoDataFine;
            Campo campoConto;
            Date dataIniziale;
            Date dataFinale;
            int numPersone;
            ContoModulo modConto;


            String titolo = "Esecuzione addebiti fissi";
            AddebitoFissoPannello pannello;
            Date dataInizio;
            Pannello panDate;
            Campo campoStato;
            Filtro filtro;
            int codConto;
            int codCamera;


            try { // prova ad eseguire il codice

                /* recupera dati generali */
                modConto = Albergo.Moduli.Conto();
                codConto = this.getCodConto();
                codCamera = modConto.query().valoreInt(Conto.Cam.camera.get(), codConto);
                numPersone = CameraModulo.getNumLetti(codCamera);

                /* crea il pannello servizi e vi registra la camera
           * per avere l'anteprima dei prezzi*/
                pannello = new AddebitoFissoPannello();
                this.setPanServizi(pannello);
                pannello.setNumPersone(numPersone);
                this.setTitolo(titolo);

//                /* regola date suggerite */
//                dataFinale = Progetto.getDataCorrente();
//                dataInizio = Lib.Data.add(dataFinale, -1);

                /* pannello date */
                campoDataInizio = CampoFactory.data(nomeDataIni);
                campoDataInizio.decora().obbligatorio();
//                campoDataInizio.setValore(dataInizio);

                campoDataFine = CampoFactory.data(nomeDataFine);
                campoDataFine.decora().obbligatorio();
//                campoDataFine.setValore(dataFinale);

                /* conto */
                campoConto = CampoFactory.comboLinkSel(nomeConto);
                campoConto.setNomeModuloLinkato(Conto.NOME_MODULO);
                campoConto.setLarScheda(180);
                campoConto.decora().obbligatorio();
                campoConto.decora().etichetta("conto da addebitare");
                campoConto.setUsaNuovo(false);
                campoConto.setUsaModifica(false);

                /* inizializza e assegna il valore (se non inizializzo
                 * il combo mi cambia il campo dati e il valore si perde)*/
                campoConto.inizializza();
                campoConto.setValore(this.getCodConto());

                /* filtro per vedere solo i conti aperti dell'azienda corrente */
                campoStato = modConto.getCampo(Conto.Cam.chiuso.get());
                filtro = FiltroFactory.crea(campoStato, false);
                filtro.add(modConto.getFiltroAzienda());
                campoConto.getCampoDB().setFiltroCorrente(filtro);

                panDate = PannelloFactory.orizzontale(this.getModulo());
                panDate.creaBordo("Periodo da addebitare");
                panDate.add(campoDataInizio);
                panDate.add(campoDataFine);
                panDate.add(campoConto);

                this.addPannello(panDate);
                this.addPannello(this.getPanServizi());

                this.regolaCamera();

                /* recupera la data iniziale (oggi) */
                dataIniziale = AlbergoLib.getDataProgramma();

                /* recupera la data di partenza prevista dal conto */
                modConto = ContoModulo.get();
                dataFinale = modConto.query().valoreData(Conto.Cam.validoAl.get(),
                        this.getCodConto());

                /* recupera il numero di persone dal conto */
                numPersone = modConto.query().valoreInt(Conto.Cam.numPersone.get(),
                        this.getCodConto());

                /* regola date suggerite */
                campoDataInizio = this.getCampo(nomeDataIni);
                campoDataInizio.setValore(dataIniziale);

                campoDataFine = this.getCampo(nomeDataFine);
                campoDataFine.setValore(dataFinale);

                /* conto */
                campoConto = this.getCampo(nomeConto);
                campoConto.setAbilitato(false);

                /* regola la data iniziale di riferimento per l'anteprima dei prezzi */
                Date data = (Date)campoDataInizio.getValore();
                this.getPanServizi().setDataPrezzi(data);

                /* regola il numero di persone dal conto */
                this.getPanServizi().setNumPersone(numPersone);


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * .
         * <p/>
         */
        private void regolaCamera() {
            /* variabili e costanti locali di lavoro */
            int codCamera;
            ContoModulo modConto;
            AddebitoFissoPannello pannello;
            int numPersone;

            try {    // prova ad eseguire il codice
                modConto = Albergo.Moduli.Conto();

                /* regola la camera del pannello inserimento addebiti fissi */
                codConto = this.getCodConto();
                codCamera = modConto.query().valoreInt(Conto.Cam.camera.get(), codConto);
                numPersone = CameraModulo.getNumLetti(codCamera);

                pannello = this.getPanServizi();
                pannello.setNumPersone(numPersone);

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Metodo eseguito quando un campo perde il fuoco.
         * <p/>
         * Metodo sovrascritto nelle sottoclassi <br>
         *
         * @param campo interessato
         */
        @Override
        protected void eventoUscitaCampo(Campo campo) {

            try { // prova ad eseguire il codice

                if (campo.getNomeInterno().equals(nomeConto)) {
                    this.setCodConto((Integer)campo.getValore());
                    this.regolaCamera();
                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        protected int getCodConto() {
            return codConto;
        }


        private void setCodConto(int codConto) {
            this.codConto = codConto;
        }

    } // fine della classe 'interna'


}// fine della classe
