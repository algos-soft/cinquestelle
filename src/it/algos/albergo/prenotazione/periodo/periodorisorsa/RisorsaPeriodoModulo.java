package it.algos.albergo.prenotazione.periodo.periodorisorsa;

import java.util.Date;

import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.risorse.RisorsaModulo;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;

public class RisorsaPeriodoModulo extends ModuloBase implements RisorsaPeriodo {


    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = RisorsaPeriodo.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = RisorsaPeriodo.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = RisorsaPeriodo.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public RisorsaPeriodoModulo() {
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
    public RisorsaPeriodoModulo(AlberoNodo unNodo) {
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
        super.setModello(new RisorsaPeriodoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);


    }


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
        Navigatore nav;

        try { // prova ad eseguire il codice
            super.creaNavigatori();

            /* crea un navigatore per il conto */
            nav = new NavInPeriodo(this);
            this.addNavigatore(nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
            //super.creaModulo(new PeriodoModulo());
            super.creaModulo(new RisorsaModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Ritorna l'ordine di visualizzazione delle risorse nel periodo.
     * <p/>
     * @return l'Ordine
     */
    public static Ordine getOrdineRisorse() {
        /* variabili e costanti locali di lavoro */
        Ordine ordine=new Ordine();

        try {    // prova ad eseguire il codice
            /* ordine fisso della lista */
            Modulo modTipoRisorsa = TipoRisorsaModulo.get();
            Campo campoOrdRisorsa = modTipoRisorsa.getCampoOrdine();
            if (campoOrdRisorsa!=null) {
                ordine.add(campoOrdRisorsa);
			}
            Modulo modRisorsePeriodo = RisorsaPeriodoModulo.get();
            Campo campoDataInizio = modRisorsePeriodo.getCampo(RisorsaPeriodo.Cam.dataInizio);
            if (campoDataInizio!=null) {
                ordine.add(campoDataInizio);
			}
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ordine;
    }


    /**
     * Crea un filtro che seleziona i periodi di risorse di un dato tipo 
     * che hanno almeno uno dei due estremi
     * che ricade all'interno dell'intervallo di date specificato
     * <p/>
     *
     * @param idTipoRisorsa id del tipo di risorsa
     * @param d1 data iniziale (compresa)
     * @param d2 data finale (compresa)
     * @return il filtro creato
     */
    public static Filtro getFiltroInteressati(int idTipoRisorsa, Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        // campi
        Campo p1 = RisorsaPeriodoModulo.get().getCampo(RisorsaPeriodo.Cam.dataInizio.get());
        Campo p2 = RisorsaPeriodoModulo.get().getCampo(RisorsaPeriodo.Cam.dataFine.get());

        /* l'inizio compreso tra le due date */
        Filtro f1 = new Filtro();
        filtro = FiltroFactory.crea(p1, Filtro.Op.MAGGIORE_UGUALE, d1);
        f1.add(filtro);
        filtro = FiltroFactory.crea(p1, Filtro.Op.MINORE_UGUALE, d2);
        f1.add(filtro);

        /* la fine compresa tra le due date */
        Filtro f2 = new Filtro();
        filtro = FiltroFactory.crea(p2, Filtro.Op.MAGGIORE_UGUALE, d1);
        f2.add(filtro);
        filtro = FiltroFactory.crea(p2, Filtro.Op.MINORE_UGUALE, d2);
        f2.add(filtro);

        /* inizia prima e finisce dopo (attraversa le due date) */
        Filtro f3 = new Filtro();
        filtro = FiltroFactory.crea(p1, Filtro.Op.MINORE_UGUALE, d1);
        f3.add(filtro);
        filtro = FiltroFactory.crea(p2, Filtro.Op.MAGGIORE_UGUALE, d2);
        f3.add(filtro);

        /* filtro sulle date */
        Filtro filtroDate = new Filtro();
        filtroDate.add(f1);
        filtroDate.add(Filtro.Op.OR, f2);
        filtroDate.add(Filtro.Op.OR, f3);

        /* filtro sul tipo risorsa specificato */
        Filtro filtroTipo = new Filtro();
        Campo campo = RisorsaPeriodoModulo.get().getCampo(RisorsaPeriodo.Cam.tipoRisorsa);
        filtroTipo = FiltroFactory.crea(campo, idTipoRisorsa);

        /* filtro finale */
        Filtro ftot = new Filtro();
        ftot.add(filtroDate);
        ftot.add(filtroTipo);

        /* valore di ritorno */
        return ftot;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static RisorsaPeriodoModulo get() {
        return (RisorsaPeriodoModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new RisorsaPeriodoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

}
