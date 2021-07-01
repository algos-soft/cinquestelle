/**
 * Title:     RigaListinoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-apr-2007
 */
package it.algos.albergo.listino.rigalistino;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.scheda.Scheda;
import it.algos.base.wrapper.WrapListino;

import java.util.ArrayList;
import java.util.Date;

/**
 * RigaListinoModulo - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author 24-apr-2007
 * @version 1.0 / 24-apr-2007
 */
public final class RigaListinoModulo extends ModuloBase implements RigaListino {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = RigaListino.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = RigaListino.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = RigaListino.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public RigaListinoModulo() {
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
    public RigaListinoModulo(AlberoNodo unNodo) {
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
        super.setModello(new RigaListinoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(RigaListinoModulo.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(RigaListinoModulo.TITOLO_MENU);

    }


    public boolean inizializza() {
        return super.inizializza();
    } // fine del metodo


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;

        try { // prova ad eseguire il codice

            /* modifica i bottoni della toolbar della lista */
            this.getNavigatoreDefault().setUsaFrecceSpostaOrdineLista(true);

            Navigatore nav = new RigaListinoNavListino(this);
            nav.setNomeChiave(RigaListino.Nav.navinlistino.toString());
//            scheda = new RigaFatturaScheda(this);
//            nav.addSchedaCorrente(scheda);
            this.addNavigatore(nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice

//            super.creaModulo(new IvaModulo());
//            super.creaModulo(new UMModulo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
//            super.addModuloVisibile(UM.NOME_MODULO);
//            super.addModuloVisibile(Iva.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Ritorna una lista di oggetti WrapListino dai dati delle righe.
     * <p/>
     * Ritorna un oggetto dati relativo alle righe di listino
     * che coprono il periodo richiesto per un dato codice di listino.
     * I dati sono ordinati per data di inizio periodo.
     *
     * @param codListino il codice di listino
     * @param dataInizio la data iniziale
     * @param dataFine la data finale
     *
     * @return una lista di oggetti WrapListino con le informazioni richieste.
     *         nulla se non sono stati trovati periodi o i periodi trovati non
     *         sono congrui (presentano buchi o sovrapposizioni, o non coprono
     *         tutto il periodo richiesto)
     */
    public ArrayList<WrapListino> getListaPeriodi(int codListino, Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        ArrayList<WrapListino> lista = null;
        Dati dati;

        try {    // prova ad eseguire il codice

            dati = this.getDatiPeriodi(codListino, dataInizio, dataFine);
            lista = this.creaListaPeriodi(dati);
            dati.close();

            /* controlla la congruità della lista e se non è congrua la annulla  */
            if (!this.checkListaPeriodi(lista, dataInizio, dataFine)) {
                lista = null;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Interroga il listino.
     * <p/>
     * Ritorna un oggetto dati relativo alle righe di listino
     * che coprono il periodo richiesto per un dato codice di listino.
     * I dati sono ordinati per data di inizio periodo.
     * L'oggetto Dati di ritorno contiene i campi:
     * - campo chiave della riga di listino
     * - codice del listino
     * - campo data inizio periodo
     * - campo data fine periodo
     * - campo prezzo della riga
     *
     * @param codListino il codice di listino
     * @param dataInizio la data iniziale
     * @param dataFine la data finale
     *
     * @return l'oggetto Dati
     */
    private Dati getDatiPeriodi(int codListino, Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        Filtro filtro;
        Filtro filtroListino;
        Filtro filtroInizio;
        Filtro filtroFine;
        Query query;
        Ordine ordine;

        try {    // prova ad eseguire il codice

            filtroListino = FiltroFactory.crea(Cam.listino.get(), codListino);
            filtroInizio = FiltroFactory.crea(Cam.dataInizio.get(),
                    Filtro.Op.MINORE_UGUALE,
                    dataFine);
            filtroFine = FiltroFactory.crea(Cam.dataFine.get(),
                    Filtro.Op.MAGGIORE_UGUALE,
                    dataInizio);
            filtro = new Filtro();
            filtro.add(filtroListino);
            filtro.add(filtroInizio);
            filtro.add(filtroFine);

            query = new QuerySelezione(this);
            query.addCampo(this.getCampoChiave());
            query.addCampo(Cam.listino.get());
            query.addCampo(Cam.dataInizio.get());
            query.addCampo(Cam.dataFine.get());
            query.addCampo(Cam.prezzo.get());
            query.setFiltro(filtro);

            /* ordine */
            ordine = new Ordine();
            ordine.add(Cam.dataInizio.get());
            query.setOrdine(ordine);

            dati = this.query().querySelezione(query);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dati;

    }


    /**
     * Ritorna una lista di oggetti WrapListino dai dati delle righe.
     * <p/>
     *
     * @param dati oggetto contenente i dati delle righe
     *
     * @return una lista di oggetti WrapListino
     */
    private ArrayList<WrapListino> creaListaPeriodi(Dati dati) {
        /* variabili e costanti locali di lavoro */
        ArrayList<WrapListino> lista = null;
        int codListino;
        Date dataInizio, dataFine;
        double prezzo;
        int codiceRiga;
        WrapListino wrap;
        Campo campoCodiceRiga;
        Campo campoListino;
        Campo campoDataInizio, campoDataFine;
        Campo campoPrezzo;

        try { // prova ad eseguire il codice

            lista = new ArrayList<WrapListino>();

            campoListino = this.getCampo(Cam.listino.get());
            campoDataInizio = this.getCampo(Cam.dataInizio.get());
            campoDataFine = this.getCampo(Cam.dataFine.get());
            campoPrezzo = this.getCampo(Cam.prezzo.get());
            campoCodiceRiga = this.getCampoChiave();

            for (int k = 0; k < dati.getRowCount(); k++) {

                codListino = dati.getIntAt(k, campoListino);
                dataInizio = (Date)dati.getValueAt(k, campoDataInizio);
                dataFine = (Date)dati.getValueAt(k, campoDataFine);
                prezzo = (Double)dati.getValueAt(k, campoPrezzo);
                codiceRiga = dati.getIntAt(k, campoCodiceRiga);
                wrap = new WrapListino(dataInizio, dataFine, codListino, prezzo, codiceRiga);
                lista.add(wrap);

            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;

    }


    /**
     * Controlla la congruita' della lista periodi rispetto a quanto richiesto.
     * <p/>
     * <p/>
     * 1) la lista non deve essere nulla ne' vuota
     * 2) I periodi devono essere in sequenza tra di loro
     * 3) I periodi devono coprire il range di date richiesto
     *
     * @param lista dei periodi da esaminare
     * @param d1 data iniziale richiesta
     * @param d2 data finale richiesta
     *
     * @return true se la lista soddisfa la richiesta
     */
    private boolean checkListaPeriodi(ArrayList<WrapListino> lista, Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        Date dList1;
        Date ultData = null;
        Date nextData;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* controlla che la lista non sia nulla */
            continua = lista != null;

            /* controlla che la lista non sia vuota */
            if (continua) {
                continua = (lista.size() > 0);
            }// fine del blocco if

            /*
            * controlla che la data di inizio di ogni periodo
            * sia pari alla data di fine del periodo precedente piu' uno.
            * se passa questo test tutti i periodi sono in sequenza.
            */
            if (continua) {

                WrapListino wl = lista.get(0);
                Date pd = wl.getPrimaData();
                ultData = Lib.Data.add(pd, -1);

                for (WrapListino wrapper : lista) {
                    dList1 = wrapper.getPrimaData();
                    nextData = Lib.Data.add(ultData, 1);
                    if (dList1.equals(nextData)) {
                        ultData = wrapper.getSecondaData();
                    } else {
                        continua = false;
                        break;
                    }// fine del blocco if-else
                }
            }// fine del blocco if

            /*
            * controlla che i periodi ritornati coprano tutto
            * il periodo richiesto
            */
            if (continua) {
                WrapListino wPrimo = lista.get(0);
                WrapListino wUltimo = lista.get(lista.size() - 1);
                Date dataPrima = wPrimo.getPrimaData();
                Date dataUltima = wUltimo.getSecondaData();
                continua = (Lib.Data.isPrecedenteUguale(d1, dataPrima));
                if (continua) {
                    continua = (Lib.Data.isPosterioreUguale(d2, dataUltima));
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static RigaListinoModulo get() {
        return (RigaListinoModulo)ModuloBase.get(RigaListinoModulo.NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new RigaListinoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
