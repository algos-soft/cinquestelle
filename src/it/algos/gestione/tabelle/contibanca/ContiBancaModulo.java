/**
 * Title:     ContiBancaModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-feb-2007
 */
package it.algos.gestione.tabelle.contibanca;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
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
import it.algos.base.query.selezione.QuerySelezione;

/**
 * IvaModulo - Contenitore dei riferimenti agli oggetti del package.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-feb-2007 ore 10.37.00
 */
public final class ContiBancaModulo extends ModuloBase implements ContiBanca {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = ContiBanca.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = ContiBanca.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = ContiBanca.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = ContiBancaModulo.TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public ContiBancaModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(ContiBancaModulo.NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(ContiBancaModulo.NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public ContiBancaModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(ContiBancaModulo.NOME_CHIAVE, unNodo);

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
        /* selezione del modello (obbligatorio) */
        super.setModello(new ContiBancaModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(ContiBancaModulo.TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(ContiBancaModulo.TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);
    }// fine del metodo inizia


    public boolean inizializza() {
        this.setNomeNavigatoreCorrente(ContiBanca.Nav.contiNostri.get());
        return super.inizializza();
    } // fine del metodo


    public void avvia() {
        super.avvia();
    } /* fine del metodo */


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

            /* navigatore per i conti bancari nella scheda anagrafica */
            nav = new NavInAnagrafica(this);
            this.addNavigatore(nav, ContiBanca.Nav.contiAnagrafica.get());

            /* navigatore per i nostri conti bancari */
            nav = new NavNostriConti(this);
            this.addNavigatore(nav, ContiBanca.Nav.contiNostri.get());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected void regolaNavigatori() {
        this.getNavigatoreDefault().setUsaPreferito(false);
    }


    protected void inizializzaNavigatori() {
        super.inizializzaNavigatori();
    }/* fine del metodo */


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
    }// fine del metodo


    /**
     * restituisce la stringa rappresentante le coordinate bancarie
     * di un dato conto.
     * <p/>
     * La stringa è composta da nome banca + coordinate bancarie
     * Per la parte Coordinate:
     * - per Italia, usa CIN+ABI+CAB+CC
     * -per Estero, usa IBAN + BIC
     *
     * @param codice del conto
     * @param estero flag per estero
     * @param conn la connessione da utilizzare per le query
     *
     * @return la stringa rappresentante le coordinate
     */
    public String getStringaCoordinate(int codice, boolean estero, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String strCoord = "";
        Query query;
        Filtro filtro;
        Campo campoNome;
        Campo campoCin;
        Campo campoAbi;
        Campo campoCab;
        Campo campoCC;
        Campo campoIban;
        Campo campoBic;

        String nome;
        String cin;
        String abi;
        String cab;
        String cc;
        String iban;
        String bic;

        Dati dati;

        try {    // prova ad eseguire il codice
            campoNome = this.getCampo(Cam.nomeBanca.get());
            campoCin = this.getCampo(Cam.cin.get());
            campoAbi = this.getCampo(Cam.abi.get());
            campoCab = this.getCampo(Cam.cab.get());
            campoCC = this.getCampo(Cam.conto.get());
            campoIban = this.getCampo(Cam.iban.get());
            campoBic = this.getCampo(Cam.bic.get());

            filtro = FiltroFactory.codice(this, codice);
            query = new QuerySelezione(this);
            query.addCampo(campoNome);
            query.addCampo(campoCin);
            query.addCampo(campoAbi);
            query.addCampo(campoCab);
            query.addCampo(campoCC);
            query.addCampo(campoIban);
            query.addCampo(campoBic);
            query.setFiltro(filtro);

            dati = this.query().querySelezione(query, conn);

            nome = dati.getStringAt(0, campoNome);
            cin = dati.getStringAt(0, campoCin);
            abi = dati.getStringAt(0, campoAbi);
            cab = dati.getStringAt(0, campoCab);
            cc = dati.getStringAt(0, campoCC);
            iban = dati.getStringAt(0, campoIban);
            bic = dati.getStringAt(0, campoBic);

            dati.close();

            strCoord += nome;
            if (estero) {
                if (Lib.Testo.isValida(iban)) {
                    strCoord += " IBAN " + iban;
                }// fine del blocco if
                if (Lib.Testo.isValida(bic)) {
                    strCoord += " BIC " + bic;
                }// fine del blocco if
            } else {
                if (Lib.Testo.isValida(cin)) {
                    strCoord += " CIN " + cin;
                }// fine del blocco if
                if (Lib.Testo.isValida(abi)) {
                    strCoord += " ABI " + abi;
                }// fine del blocco if
                if (Lib.Testo.isValida(cab)) {
                    strCoord += " CAB " + cab;
                }// fine del blocco if
                if (Lib.Testo.isValida(cc)) {
                    strCoord += " CC " + cc;
                }// fine del blocco if
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return strCoord;
    }


    /**
     * Ritorna il codice del nostro conto bancario preferito.
     * <p/>
     *
     * @return il codice del nostro conto preferito, 0 se non esiste
     */
    public int getNostroContoPreferito() {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            filtro = FiltroFactory.crea(Cam.soggetto.get(), 0);
            filtro.add(FiltroFactory.crea(this.getCampoPreferito(), true));
            cod = this.query().valoreChiave(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * restituisce la stringa rappresentante le coordinate bancarie
     * di un dato conto.
     * <p/>
     * Utilizza la connessione dem modulo.
     * La stringa è composta da nome banca + coordinate bancarie
     * Per la parte Coordinate:
     * - per Italia, usa CIN+ABI+CAB+CC
     * -per Estero, usa IBAN + BIC
     *
     * @param codice del conto
     * @param estero flag per estero
     */
    public String getStringaCoordinate(int codice, boolean estero) {
        return this.getStringaCoordinate(codice, estero, this.getConnessione());
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static ContiBancaModulo get() {
        return (ContiBancaModulo)ModuloBase.get(ContiBancaModulo.NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new ContiBancaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

}// fine della classe
