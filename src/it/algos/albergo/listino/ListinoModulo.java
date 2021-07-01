/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      31-mag-2006
 */
package it.algos.albergo.listino;

import it.algos.albergo.listino.rigalistino.RigaListino;
import it.algos.albergo.listino.rigalistino.RigaListinoModulo;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottoconto;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottocontoModulo;
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
import it.algos.base.wrapper.WrapListino;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Listino - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li> Regola il riferimento al Modello specifico (obbligatorio) </li>
 * <li> Regola i titoli di Menu e Finestra del Navigatore </li>
 * <li> Regola eventualmente alcuni aspetti specifici del Navigatore </li>
 * <li> Crea altri eventuali <strong>Moduli</strong> indispensabili per il
 * funzionamento di questo modulo </li>
 * <li> Rende visibili nel Menu gli altri moduli </li>
 * <li> Regola eventuali funzionalit&agrave; specifiche del Navigatore </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public class ListinoModulo extends ModuloBase implements Listino {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Listino.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Listino.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Listino.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = ListinoModulo.TITOLO_FINESTRA;

    /**
     * filtro per separare i tipi di listino
     */
    private Filtro filtroTipo;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public ListinoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(ListinoModulo.NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(ListinoModulo.NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public ListinoModulo(String chiave, AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(chiave, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public ListinoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(ListinoModulo.NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* selezione del modello (obbligatorio) */
        super.setModello(new ListinoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(ListinoModulo.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(ListinoModulo.TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);
    }


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe Progetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Inizializza il gestore , prima di tutto (servono i Comandi per
     * inzializzare i Campi) <br>
     * Tenta di inizializzare il modulo <br>
     * Prima inizializza il modello, se e' riuscito
     * inizializza anche gli altri oggetti del modulo <br>
     *
     * @return true se il modulo e' stato inizializzato
     */
    @Override
    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean inizializzato;

        inizializzato = super.inizializza();

        try { // prova ad eseguire il codice
            this.filtraNavigatori();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return inizializzato;
    } // fine del metodo


    /**
     * Crea il filtro dei listini.
     * </p>
     * Metodo invocato direttamente dal ciclo inizia delle sottoclassi <br>
     */
    protected void creaFiltro(Listino.ModoPrezzo tipo) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        try { // prova ad eseguire il codice
            filtro = FiltroFactory.crea(Cam.modoPrezzo.get(), tipo.getCodice());
            this.setFiltroTipo(filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new AlbSottocontoModulo());
            super.creaModulo(new RigaListinoModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
//            /* modifica i bottoni della toolbar della lista */
//            nav = this.getNavigatoreDefault();
//            nav.setUsaFrecceSpostaOrdineLista(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected void regolaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* regola il navigatore di default */
            nav = this.getNavigatoreDefault();
            nav.setUsaPannelloUnico(true);
            nav.setUsaDuplicaRecord(true);
            nav.setUsaFrecceSpostaOrdineLista(true);
            nav.addSchedaCorrente(new ListinoScheda(this));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
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
            super.addModuloVisibile(AlbSottoconto.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiorna i navigatori del modulo
     * <p/>
     * I Navigatori utilizzano il filtro indicato <br>
     */
    private void filtraNavigatori() {
        LinkedHashMap<String, Navigatore> navigatori;

        try { // prova ad eseguire il codice

            navigatori = this.getNavigatori();
            for (Navigatore nav : navigatori.values()) {
                nav.setFiltroBase(this.getFiltroTipo());
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il prezzo dal listino relativo a un dato
     * listino e a una certa data.
     * <p/>
     *
     * @param codListino codice del listino
     * @param data di riferimento
     *
     * @return il prezzo di listino richiesto
     */
    public static double getPrezzo(int codListino, Date data) {
        /* variabili e costanti locali di lavoro */
        Modulo modListino;
        double prezzoOut = 0;
        boolean continua = true;
        Modulo modRighe;
        Query query;
        Filtro filtro;
        Filtro filtroTot;
        Dati dati;
        Campo campoTipo;
        Campo campoPrezzoFisso;
        Campo campoPrezzoRiga;
        int codTipo = 0;
        double prezzo = 0;

        try {    // prova ad eseguire il codice

            /* recupera le variabili di uso comune */
            modListino = ListinoModulo.get();
            campoTipo = modListino.getCampo(Cam.modoPrezzo.get());
            campoPrezzoFisso = modListino.getCampo(Cam.prezzo.get());

            /* recupera il tipo di listino (fisso o variabile) e il prezzo fisso */
            if (continua) {
                query = new QuerySelezione(modListino);
                query.addCampo(campoTipo);
                query.addCampo(campoPrezzoFisso);
                filtro = FiltroFactory.codice(modListino, codListino);
                query.setFiltro(filtro);
                dati = modListino.query().querySelezione(query);
                if (dati.getRowCount() > 0) {
                    codTipo = dati.getIntAt(campoTipo);
                    prezzo = dati.getDoubleAt(campoPrezzoFisso);
                } else {
                    continua = false;
                }// fine del blocco if-else
                dati.close();
            }// fine del blocco if

            /* se il listino è di tipo fisso, ritorna il valore fisso,
             * altrimenti cerca nelle righe */
            if (continua) {
                if (codTipo == ModoPrezzo.fisso.getCodice()) {
                    prezzoOut = prezzo;
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* controlla che la data sia valorizzata */
            if (continua) {
                if (Lib.Data.isVuota(data)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* cerca nelle righe in base alla data */
            if (continua) {
                modRighe = RigaListinoModulo.get();
                campoPrezzoRiga = modRighe.getCampo(RigaListino.Cam.prezzo.get());
                filtroTot = new Filtro();
                filtro = FiltroFactory.crea(RigaListino.Cam.listino.get(), codListino);
                filtroTot.add(filtro);
                filtro = FiltroFactory.crea(RigaListino.Cam.dataInizio.get(),
                        Filtro.Op.MINORE_UGUALE,
                        data);
                filtroTot.add(filtro);
                filtro = FiltroFactory.crea(RigaListino.Cam.dataFine.get(),
                        Filtro.Op.MAGGIORE_UGUALE,
                        data);
                filtroTot.add(filtro);
                query = new QuerySelezione(modRighe);
                query.addCampo(campoPrezzoRiga);
                query.setFiltro(filtroTot);

                dati = modRighe.query().querySelezione(query);
                if (dati.getRowCount() == 1) {
                    prezzoOut = dati.getDoubleAt(0, campoPrezzoRiga);
                }// fine del blocco if-else
                dati.close();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return prezzoOut;
    }


    /**
     * Interroga il listino.
     * <p/>
     * Ritorna i periodi di listino che coprono il periodo indicato
     * per un dato sottoconto.
     *
     * @param codListino il codice di listino
     * @param dataInizio la data iniziale
     * @param dataFine la data finale
     * @param tagliaInizio flag per interrompere la data iniziale del listino alla data indicata
     * @param tagliaFine flag per interrompere la data finale del listino alla data indicata
     *
     * @return una lista di oggetti WrapListino con le informazioni richieste.
     *         null se non sono stati trovati periodi o i periodi trovati non
     *         sono congrui (presentano buchi o sovrapposizioni, o non coprono
     *         tutto il periodo richiesto)
     */
    public static ArrayList<WrapListino> getPrezzi(int codListino,
                                                   Date dataInizio,
                                                   Date dataFine,
                                                   boolean tagliaInizio,
                                                   boolean tagliaFine) {
        /* variabili e costanti locali di lavoro */
        ArrayList<WrapListino> lista = null;
        Modulo modListino;
        int codModo;
        RigaListinoModulo modRighe;
        WrapListino wrap;
        double prezzo;
        Date data;


        try {    // prova ad eseguire il codice

            modListino = ListinoModulo.get();
            /**
             * Se il modo listino è fisso, gira la chiamata al metodo preposto
             * e crea una lista con un solo oggetto WrapListino
             */
            codModo = modListino.query().valoreInt(Cam.modoPrezzo.get(), codListino);
            if (codModo == ModoPrezzo.fisso.getCodice()) {
                prezzo = ListinoModulo.getPrezzo(codListino, null);
                wrap = new WrapListino(dataInizio, dataFine, codListino, prezzo, codListino);
                lista = new ArrayList<WrapListino>();
                lista.add(wrap);
            }// fine del blocco if

            /**
             * Se il modo listino è variabile, interroga le righe
             * e crea una lista di oggetti WrapListino
             */
            if (codModo == ModoPrezzo.variabile.getCodice()) {
                modRighe = RigaListinoModulo.get();
                lista = modRighe.getListaPeriodi(codListino, dataInizio, dataFine);

                /* taglia date iniziali e finali se richiesto */
                if (lista != null) {

                    for (WrapListino wrapper : lista) {
                        if (tagliaInizio) {
                            data = wrapper.getPrimaData();
                            if (Lib.Data.isPosteriore(data, dataInizio)) {
                                wrapper.setPrimaData(dataInizio);
                            }// fine del blocco if
                        }// fine del blocco if

                        if (tagliaFine) {
                            data = wrapper.getSecondaData();
                            if (Lib.Data.isPrecedente(data, dataFine)) {
                                wrapper.setSecondaData(dataFine);
                            }// fine del blocco if
                        }// fine del blocco if

                    } // fine del ciclo for-each
                }// fine del blocco if


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
     * Ritorna i periodi di listino che coprono il periodo indicato
     * per un dato sottoconto.
     * I periodi sono tagliati sugli estremi di data richiesti.
     *
     * @param codListino il codice di listino
     * @param dataInizio la data iniziale
     * @param dataFine la data finale
     *
     * @return una lista di oggetti WrapListino con le informazioni richieste.
     *         null se non sono stati trovati periodi o i periodi trovati non
     *         sono congrui (presentano buchi o sovrapposizioni, o non coprono
     *         tutto il periodo richiesto)
     */
    public static ArrayList<WrapListino> getPrezzi(int codListino, Date dataInizio, Date dataFine) {
        /* invoca il metodo delegato della classe */
        return getPrezzi(codListino, dataInizio, dataFine, true, true);
    }


    /**
     * Ritorna l'elenco dei codici dei record disponibili per addebiti giornalieri.
     * <p/>
     *
     * @return l'elenco dei codici dei record di Listino ordinati sul campo Ordine
     */
    public ArrayList<Integer> getGiornalieri() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> giornalieri = new ArrayList<Integer>();
        Filtro filtro;
        Ordine ordine;
        Campo campo;
        int[] codici;

        try {    // prova ad eseguire il codice

            /* crea il filtro */
            campo = this.getCampo(Cam.giornaliero.get());
            filtro = FiltroFactory.crea(campo, true);

            /* crea l'ordine */
            ordine = new Ordine();
            ordine.add(this.getCampoOrdine());

            codici = this.query().valoriChiave(filtro, ordine);
            for (int cod : codici) {
                giornalieri.add(cod);
            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return giornalieri;
    }


    public Filtro getFiltroTipo() {
        return filtroTipo;
    }


    protected void setFiltroTipo(Filtro filtroTipo) {
        this.filtroTipo = filtroTipo;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static ListinoModulo get() {
        return (ListinoModulo)ModuloBase.get(ListinoModulo.NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new ListinoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
