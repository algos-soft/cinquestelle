/**
 * Title:        CDBLinkato.java
 * Package:      it.algos.base.campo.db
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 5 agosto 2003 alle 18.01
 */

package it.algos.base.campo.db;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CDElenco;
import it.algos.base.costante.CostanteDB;
import it.algos.base.database.Db;
import it.algos.base.elenco.Elenco;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.PassaggioObbligato;
import it.algos.base.relazione.Relazione;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.Viste;

import java.util.ArrayList;

/**
 * Classe concreta per implementare un oggetto dalla classe astratta CDBBase;
 * <br>
 * Questa classe e' responsabile di: <br>
 * A - Racchiudere tutte le funzionalita' di interazione col database <br>
 * B - Mantiene il nome sql del campo (potrebbe essere diverso dal nome interno) <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  5 agosto 2003 ore 18.01
 */
public class CDBLinkato extends CDBBase implements CostanteDB {

    /**
     * nome di un altro modulo con cui si stabilisce il legame di link
     */
    private String unNomeModuloLinkato = null;

    /**
     * il modulo linkato
     */
    private Modulo unModuloLinkato = null;

    /**
     * nome interno del campo del modulo linkato con il quale questo campo
     * ha una relazione di tipo molti (questo campo) a uno (l'altro campo)
     */
    private String unNomeCampoLinkChiave = null;

    /**
     * il campo chiave del modulo linkato
     */
    private Campo unCampoLinkChiave = null;

    /**
     * nome interno del campo del modulo linkato che viene
     * visualizzato nella lista
     */
    private String nomeColonnaListaLinkata = "";

    /**
     * nome interno del campo del modulo linkato che viene
     * visualizzato nella scheda
     */
    private String nomeCampoSchedaLinkato = "";

    /**
     * campo del modulo linkato che viene
     * visualizzato nella scheda
     */
    private Campo campoValoriLinkato = null;

    /**
     * nome interno del campo del modulo linkato che viene
     * utilizzato per l'ordinamento (pubblico)
     */
    private String nomeCampoOrdineLinkato = "";

    /**
     * nome di una Vista associata a questo campo (facoltativa)
     * Se questo campo ha una Vista associata, in fase di espansione
     * delle Viste il campo viene sostituito dai campi della Vista associata.
     * Se non ha una Vista associata, nelle Viste viene lasciato
     * il campo originale
     */
    private String unNomeVistaLink = "";

    /**
     * Oggetto Vista per la visualizzazione dei dati nella lista
     * Viene clonato dall'originale del Modello durante
     * la fase di inizializzazione del campo
     */
    private Vista unaVistaLink = null;

    /**
     * Integrita' referenziale in caso di cancellazione
     * Azione da intraprendere sulla tavola molti in caso
     * di cancellazione del record di testa
     * Possibili valori:
     * - AZIONE_NO_ACTION non fa nulla ma non permette di cancellare il record di testa
     * - AZIONE_CASCADE cancella automaticamente i record linkati
     * - AZIONE_SET_NULL attribuisce il valore null al campo link dei record linkati
     * - AZIONE_SET_DEFAULT attribuisce il valore di default al campo link dei record linkati
     */
    private Db.Azione azioneDelete = null;

    /**
     * Integrita' referenziale in caso di modifica
     * Azione da intraprendere sulla tavola molti in caso
     * di modifica del valore di link sul record di testa
     * Possibili valori:
     * - AZIONE_NO_ACTION non fa nulla ma non permette di modificare
     * il valore del campo chiave sul record di testa
     * - AZIONE_CASCADE modifica automaticamente i record linkati
     * - AZIONE_SET_NULL attribuisce il valore null al campo link dei record linkati
     * - AZIONE_SET_DEFAULT attribuisce il valore di default al campo link dei record linkati
     * (AZIONE_NO_ACTION, AZIONE_CASCADE, AZIONE_SET_NULL, AZIONE_SET_DEFAULT)
     */
    private Db.Azione azioneUpdate = null;


    /**
     * Flag per indicare se la relazione instaurata da questo campo
     * costituisce la relazione preferita tra la tavola di questo campo
     * e la tavola collegata.
     * Serve per risolvere automaticamente le ambiguita' in caso
     * di piu' campi di una tavola che hanno una relazione con un'altra
     * tavola.
     * Ha senso solo se due tavole sono messe in relazione su più di un campo.
     * Per default questo flag e' uguale a false.
     * Un solo campo del modello puo' avere questo flag = true
     */
    private boolean isRelazionePreferita = false;

    /**
     * filtro base sui records della lista valori
     * se nullo non usa filtro
     */
    private Filtro filtroBase = null;

    /**
     * filtro corrente sui records della lista valori
     * se nullo non usa filtro
     */
    private Filtro filtroCorrente = null;

    /**
     * Ordine di visualizzazione dei valori recuperati
     * (normalmente per gli elenchi link di Combo e Radio)
     */
    private Ordine ordine = null;

    private Estratti estratto;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDBLinkato() {
        /* rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDBLinkato(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /* messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* regola il flag setLinkato a true */
        this.setLinkato(true);

//        /*
//         * Di default non tocca il record linkato
//         * quando si cancella il record di testa.
//         */
//        this.setAzioneDelete(Db.Azione.noAction);
//
//        /**
//         * di default modifica automaticamente il valore del campo
//         * linkato quando si modifica il valore del campo chiave
//         */
//        this.setAzioneUpdate(Db.Azione.cascata);

        /* se il campo e' linkato, di default la colonna e' invisibile in lista */
        this.unCampoParente.setVisibileVistaDefault(false);

    } /* fine del metodo inizia */


    /**
     * Relaziona il campo.
     * <p/>
     * Metodo invocato dal ciclo inizia del Progetto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return true se riuscito
     */
    public boolean relaziona() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        boolean continua;

        try { // prova ad eseguire il codice

            /* controlla se è già relazionato */
            continua = (!this.isRelazionato());

            /* tenta di regolare i riferimenti al campo linkato */
            if (continua) {
                if (this.isLinkato()) {
                    continua = this.regolaLink();
                    riuscito = continua;
                }// fine del blocco if
            }// fine del blocco if

            /* crea e/o regola il campo sul DB. */
            if (continua) {
                continua = super.allineaCampo();
                riuscito = continua;
            }// fine del blocco if

            /* aggiunge la relazione descritta da questo campo
             * alla tavola relazioni di progetto. */
            if (continua) {
                if (this.isLinkato()) {
                    this.addRelazioneCampo();
                    this.setRelazionato(true);
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean continua;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            /* regolazione successiva dei link */
            continua = (this.getModuloLinkato() == null);
            if (continua) {
                this.regolaLink();
            }// fine del blocco if

            /* regola il campo/vista da visualizzare in lista */
            this.regolaCampoListaLinkato();

            /* regola il campo da visualizzare in scheda */
            this.regolaCampoSchedaLinkato();

            /* assegna la eventuale vista linkata */
            this.regolaVistaLinkata();

            /* assegna l'ordine del campo linkato */
            this.regolaOrdine();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Assegna l'ordine del campo linkato.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Assegna l'ordine con la seguente priorità
     * 1. usa l'oggetto ordine assegnato
     * 2. usa il nome del campo ordine assegnato (campo del modulo linkato)
     * 3. usa l'ordine pubblico del campo valori linkato
     * 4. usa l'ordine privato del campo valori linkato
     */
    private void regolaOrdine() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        String nomeCampoOrdine = "";
        String nomeCampo = "";
        Ordine ordine = null;
        Campo campo;
        Navigatore nav;
        Lista lista;

        try { // prova ad eseguire il codice

            /* prova con l'oggetto ordine eventualmente assegnato */
            if (continua) {
                ordine = this.getOrdine();
                if (ordine != null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* prova con il nome del campo ordine eventualmente assegnato
             * (deve essere un campo del modulo linkato)*/
            if (continua) {
                nomeCampo = this.getNomeCampoOrdineLinkato();
                if (Lib.Testo.isValida(nomeCampo)) {
                    if (this.isEsisteCampoLinkato(nomeCampoOrdine)) {
                        campo = this.getModuloLinkato().getCampo(nomeCampo);
                        ordine = new Ordine();
                        ordine.add(campo);
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* controlla se il nav corrente del modulo linkato
 * è ordinabile (visualizza le frecce di ordinamento)
 * in tal caso utilizza il campo ordine del modulo linkato */
            if (continua) {
                nav = this.getModuloLinkato().getNavigatoreCorrente();
                if (nav != null) {
                    lista = nav.getLista();
                    if (lista != null) {
                        if (lista.isOrdinamentoManuale()) {
                            campo = this.getModuloLinkato().getCampoOrdine();
                            ordine = new Ordine();
                            ordine.add(campo);
                            continua = false;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* prova con l'ordine pubblico del campo valori linkato */
            if (continua) {
                campo = this.getCampoValoriLinkato();
                if (campo != null) {
                    ordine = campo.getCampoLista().getOrdinePubblico();
                    if (ordine != null) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* prova con l'ordine privato del campo valori linkato */
            if (continua) {
                campo = this.getCampoValoriLinkato();
                if (campo != null) {
                    ordine = campo.getCampoLista().getOrdinePrivato();
                    if (ordine != null) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* assegna l'ordine definitivo */
            this.setOrdineElenco(ordine);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge la relazione descritta da questo campo alla
     * tavola delle relazioni del Progetto
     */
    public void addRelazioneCampo() {

        /** Variabili e costanti locali di lavoro */
        Relazione unaRelazione = null;

        try {    // prova ad eseguire il codice

            if (isCampoFisico()) {

                /* Costruisce un nuovo oggetto Relazione */
                unaRelazione = new Relazione(this.getCampoParente());

                /* Aggiunge la relazione alla Tavola Relazioni del Progetto */
                Progetto.getIstanza().addRelazione(unaRelazione);

            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Regola i riferimenti al modulo e al campo al quale questo
     * campo e' linkato.
     * <p/>
     *
     * @return true se riuscito
     */
    private boolean regolaLink() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        String nomeModuloLinkato = null;
        Modulo moduloLinkato = null;
        Modello modelloLinkato = null;
        String nomeCampoChiave = null;
        Campo campoChiave = null;
        boolean continua = true;
        String errore = null;

        try {    // prova ad eseguire il codice

            /* recupera il nome del modulo linkato */
            if (continua) {
                nomeModuloLinkato = this.getNomeModuloLinkato();
            }// fine del blocco if

            /* recupera il modulo linkato dalla collezione */
            if (continua) {
                moduloLinkato = Progetto.getModulo(nomeModuloLinkato);
                if (moduloLinkato == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* assegna il riferimento al modulo linkato */
            if (continua) {
                this.setModuloLinkato(moduloLinkato);
                modelloLinkato = moduloLinkato.getModello();
            }// fine del blocco if

            /* recupera il nome del campo link chiave (campo puntato)
             * se il campo link chiave non e' specificato, usa il campo
             * chiave del modulo linkato */
            if (continua) {
                nomeCampoChiave = this.getNomeCampoLinkChiave();
                if (Lib.Testo.isVuota(nomeCampoChiave)) {
                    nomeCampoChiave = modelloLinkato.getNomeCampoChiave();
                }// fine del blocco if
            }// fine del blocco if

            /* verifica che il campo link chiave esista nel modello linkato */
            if (continua) {
                if (!modelloLinkato.isEsisteCampo(nomeCampoChiave)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il campo link chiave dal modulo linkato */
            if (continua) {
                campoChiave = modelloLinkato.getCampo(nomeCampoChiave);
            }// fine del blocco if

            /* verifica che il campo link chiave sia relazionato */
            if (continua) {
                if (!campoChiave.getCampoDB().isRelazionato()) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* assegna il riferimento al campo link chiave */
            if (continua) {
                this.setCampoLinkChiave(campoChiave);
            }// fine del blocco if

            /* regola il valore di ritorno */
            if (continua) {
                riuscito = true;
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Costruisce un messaggio di link fallito.<br>
     *
     * @param unTesto il motivo del fallimento.
     */
    private String messaggioLinkFallito(String unTesto) {
        /* variabili e costanti locali di lavoro */
        String unMessaggio = "";
        String nomeCampo = "";
        String nomeModuloLinkato = "";

        nomeCampo = this.getCampoParente().getChiaveCampo();
        nomeModuloLinkato = this.getNomeModuloLinkato();

        unMessaggio =
                "Campo: " +
                        nomeCampo +
                        "\n" +
                        "Modulo linkato: " +
                        nomeModuloLinkato +
                        "\n" +
                        "Problema: " +
                        unTesto;

        /* valore di ritorno */
        return unMessaggio;
    } // fine del metodo


    /**
     * Assegna al campo la eventuale vista linkata.<br>
     */
    private boolean regolaVistaLinkata() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        boolean continua;
        Modulo mod;
        Modello modello = null;
        String unNomeVista = "";
        Vista unaVista;
        String errore;

        try {    // prova ad eseguire il codice

            /* recupera il modulo linkato e il nome della Vista */
            mod = this.getModuloLinkato();
            continua = (mod != null);

            /* Controlla se il nome e' valido */
            if (continua) {
                modello = mod.getModello();
                unNomeVista = this.getNomeVistaLink();
                continua = Lib.Testo.isValida(unNomeVista);
            }// fine del blocco if


            if (continua) {

                /* recupera la Vista dal Modello usando il nome */
                unaVista = modello.getVista(unNomeVista);

                /** controlla se la vista esiste */
                if (unaVista != null) {
                    /* assegna la vista linkata al campo */
                    this.setVistaLinkata(unaVista);
                } else {
                    riuscito = false;
                    errore = "Vista " + unNomeVista;
                    errore += " non trovata nel modulo ";
                    errore += mod.getNomeChiave();
                    throw new Exception(errore);
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Carica dal database una lista di codici ed una di valori.
     * <p/>
     * Controlla se esiste un estratto <br>
     * Utilizza il modulo ed il campo visibile selezionati
     * nelle variabili di istanza <br>
     * Se attivo un filtro, lo usa; altrimenti seleziona tutti i record <br>
     *
     * @return matriceDoppia lista dei valori e dei codici
     */
    public MatriceDoppia caricaListaValori() {
        /** variabili e costanti locali di lavoro */
        MatriceDoppia matriceDoppia = null;
        boolean continua;
        boolean isEstratto;
        String nomeMod;
        Modulo unModulo;
        Filtro filtro;
        Ordine ordine;
        Campo campo;
        Estratti estratto;
        EstrattoBase estrattoBase;


        try {    // prova ad eseguire il codice
            estratto = this.getEstratto();
            isEstratto = (estratto != null);
            continua = !isEstratto;

            if (isEstratto) {
                nomeMod = estratto.getNomeModulo();
                unModulo = Progetto.getModulo(nomeMod);
                estrattoBase = unModulo.getModello().getEstratto(estratto);
                matriceDoppia = estrattoBase.getMatrice();
            }// fine del blocco if


            if (continua) {
                /* recupera il campo link visibile */
                campo = this.getCampoValoriLinkato();

                /* se non e' nullo, carica la corrispondente lista valori */
                if (campo != null) {

                    /* recupera i riferimenti */
                    unModulo = this.getModuloLinkato();

                    /* recupera il filtro per la lista valori */
                    filtro = this.getFiltro();

                    /* ordine */
                    ordine = this.getOrdine();

                    /* interroga il modulo linkato */
                    matriceDoppia = unModulo.query().valoriDoppi(campo, filtro, ordine);

                } /* fine del blocco if */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return matriceDoppia;
    }


    /**
     * Carica dal database una lista di valori <br>
     * utilizza la tavola ed il campo visibile selezionati
     * nella variabili di istanza <br>
     * il campo visibile deve essere di tipo testo <br>
     *
     * @return unaLista lista dei valori di testo
     */
    public ArrayList caricaLista() {
        /** variabili e costanti locali di lavoro */
        ArrayList valori = null;
        Modulo moduloLinkato = null;
        String nomeCampoLinkVisibile = null;

        try {    // prova ad eseguire il codice

            /* recupera il nome del campo link visibile */
            nomeCampoLinkVisibile = this.getNomeCampoValoriLinkato();

            /* se non e' nullo, carica la corrispondente lista valori */
            if (Lib.Testo.isValida(nomeCampoLinkVisibile)) {

                /* recupera i riferimenti */
                moduloLinkato = this.getModuloLinkato();

                /* interroga il modulo e recupera il risultato */
                valori = moduloLinkato.query().valoriCampo(nomeCampoLinkVisibile);

            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return valori;
    } /* fine del metodo */

//    /**
//     * Recupera dal database il codice del campo chiave.
//     * <p>
//     * Utilizza la tavola, il campo chiave ed il campo visibile
//     * selezionati nella variabili di istanza<br>
//     * Il campo visibile deve essere di tipo testo
//     *
//     * @param testo testo da ricercare nel campo visibile
//     *
//     * @return unCodice valore del codice chiave = -1 se non trovato
//     */
//    public int recuperaCodiceChiave(String testo) {
//        /** variabili e costanti locali di lavoro */
//        int unCodice = 0;
//        String unaTavola = "";
//        String unCampo = "";
//        String unFiltro = "";
//        DBOld unDB = null;
//
//        try {	// prova ad eseguire il codice
//            unCodice = -1; // valore codificato (?)
//
//            /** recupera la tavola */
//            unaTavola
//                    = this.getCampoLinkVisibile()
//                    .getCampoDB()
//                    .getNomeTavolaSql();
//
//            /** recupera il nome del campo */
//            unCampo
//                    = this.getCampoLinkVisibile().getCampoDB().getNomeCampoSql();
//
//            unFiltro = unCampo + "='" + testo + "'";
//
//            unDB = unCampoParente.getModulo().getDatabase();
//
//            unCodice = unDB.recuperaCodiceChiave(unaTavola, unFiltro);
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            /** mostra il messaggio di errore */
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /** valore di ritorno */
//        return unCodice;
//    } /* fine del metodo */


    /**
     * nome di un altro modulo con il quale si stabilisce il legame
     */
    public void setNomeModuloLinkato(String unNomeModuloLinkato) {
        this.unNomeModuloLinkato = unNomeModuloLinkato;
    } /* fine del metodo */


    /**
     * modulo con il quale si stabilisce il legame
     */
    public void setModuloLinkato(Modulo unModuloLinkato) {
        this.unModuloLinkato = unModuloLinkato;
    } /* fine del metodo */


    /**
     * nome interno del campo del modulo linkato con il quale questo campo
     * ha una relazione di tipo molti (questo campo) a uno (l'altro campo)
     */
    public void setNomeCampoLinkChiave(String unNomeCampoLinkChiave) {
        this.unNomeCampoLinkChiave = unNomeCampoLinkChiave;
    } /* fine del metodo */


    /**
     * Campo del modulo linkato con il quale questo campo
     * ha una relazione di tipo molti (questo campo) a uno (l'altro campo)
     */
    private void setCampoLinkChiave(Campo unCampoLinkChiave) {
        this.unCampoLinkChiave = unCampoLinkChiave;
    } /* fine del metodo */


    /**
     * Azione da intraprendere sulla tavola molti in caso
     * di cancellazione del record sulla tavola uno.
     * <p/>
     * Valori possibili (da interfaccia Db)<br>
     * - Db.Azione.noAction non fa nulla<br>
     * - Db.Azione.cascade elimina i record<br>
     * - Db.Azione.setNull pone il valore a NULL<br>
     * - Db.Azione.setDefault assegna il valore di default<br>
     */
    public void setAzioneDelete(Db.Azione azioneDelete) {
        this.azioneDelete = azioneDelete;
    }


    /**
     * Azione da intraprendere sulla tavola molti in caso
     * di modifica del record sulla tavola uno.
     * <p/>
     * Valori possibili (da interfaccia Db)<br>
     * - Db.Azione.noAction non fa nulla<br>
     * - Db.Azione.cascade elimina i record<br>
     * - Db.Azione.setNull pone il valore a NULL<br>
     * - Db.Azione.setDefault assegna il valore di default<br>
     */
    public void setAzioneUpdate(Db.Azione azioneUpdate) {
        this.azioneUpdate = azioneUpdate;
    }


    /**
     * vista linkata
     */
    private void setVistaLinkata(Vista unaVistaLink) {
        this.unaVistaLink = unaVistaLink;
    } /* fine del metodo */


    /**
     * imposta il flag setRelazionePreferita del campo
     */
    public void setRelazionePreferita(boolean unFlag) {
        this.isRelazionePreferita = unFlag;
    } /* fine del metodo setter */


    /**
     * Crea un passaggio obbligato per raggiungere la tavola di destinazione
     * di questo campo dblinkato, tramite un dato campo. <br>
     */
    private PassaggioObbligato creaPassaggio(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        PassaggioObbligato passaggioObbligato = null;
        String unaTavola = "";

        try {    // prova ad eseguire il codice
            unaTavola = unCampo.getCampoDB()
                    .getModuloLinkato()
                    .getModello()
                    .getTavolaArchivio();

            passaggioObbligato = new PassaggioObbligato(unaTavola, unCampo);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return passaggioObbligato;
    } // fine del metodo


    /**
     * Regola il primo ed unico campo di passaggio.
     * </p>
     * Cancella tuti gli eventuali elementi dell'elenco <br>
     * Aggiunge il campo <br>
     */
    public void setCampoPassaggio(Campo unCampo) {
        try {    // prova ad eseguire il codice
            this.getPassaggiSelezione().reset();
            this.getPassaggiSelezione().addPassaggio(this.creaPassaggio(unCampo));
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Recupera l'Estratto dal Modulo.
     * </p>
     * Recupera il codice del record <br>
     * Restituisce l'estratto corrrispondente ad un determinato record <br>
     */
    public EstrattoBase getEstratto(int posizione) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        CDElenco unCampoDati = null;
        Object video = null;
        Integer numero = null;
        int posRecord = 0;
        int codiceRecord = 0;
        Elenco unElenco = null;
        boolean continua = false;

        try {    // prova ad eseguire il codice
            unCampoDati = (CDElenco)unCampoParente.getCampoDati();
            video = unCampoDati.getVideo();
            continua = (video != null);

            /* controllo di validita' */
            if (continua) {
                numero = (Integer)video;
                posRecord = numero.intValue();
                unElenco = unCampoDati.getElenco();
                continua = (unElenco != null);
            }// fine del blocco if

            if (continua) {
                codiceRecord = unElenco.getCodice(posRecord);
                continua = (codiceRecord > 0);
            }// fine del blocco if

            if (continua) {

                // todo da implementare - alex 28-01-05
                //
//                estratto
//                        = QueryFactoryOld.estrattoModulo(
//                                this.getModuloLinkato(),
//                                this.getNomeEstratto(posizione),
//                                codiceRecord);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    } // fine del metodo


    /**
     * Regola il campo lista linkato.
     * <br>
     * se e' stata specificata la Vista usa la vista
     * altrimenti se e' stato specificato usa il campo lista linkato
     * altrimenti usa il campo Sigla
     * altrimenti usa se stesso
     */
    private void regolaCampoListaLinkato() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        String nomeVista;
        String nomeCampoLista;
        String nomeCampoSigla;

        try {    // prova ad eseguire il codice
            nomeVista = this.getNomeVistaLink();
            nomeCampoLista = this.getNomeColonnaListaLinkata();

            /* prova con la Vista */
            if (continua) {
                if (Lib.Testo.isValida(nomeVista)) {
                    if (this.getModuloLinkato().isEsisteVista(nomeVista)) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* prova con il campo Lista linkato */
            if (continua) {
                if (Lib.Testo.isValida(nomeCampoLista)) {
                    if (this.isEsisteCampoLinkato(nomeCampoLista)) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* prova con il campo Sigla */
            if (continua) {
                nomeCampoSigla = Modello.NOME_CAMPO_SIGLA;
                if (this.isEsisteCampoLinkato(nomeCampoSigla)) {
                    this.setNomeColonnaListaLinkata(nomeCampoSigla);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    }


    /**
     * Regola il campo scheda linkato.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * se e' stata specificato un campo, usa il campo specificato
     * altrimenti usa il campo Sigla
     * altrimenti usa se stesso
     */
    private void regolaCampoSchedaLinkato() {
        /* variabili e costanti locali di lavoro */
        String nomeCampo = "";
        Campo campo = null;
        boolean continua = true;
        Modulo mod;

        try {    // prova ad eseguire il codice

            /* prova con il campo eventualmente specificato */
            if (this.getCampoValoriLinkato() != null) {
                campo = this.getCampoValoriLinkato();
                continua = false;
            }// fine del blocco if

            /* prova con il nome di campo eventualmente specificato */
            if (continua) {
                nomeCampo = this.getNomeCampoValoriLinkato();
                if (Lib.Testo.isValida(nomeCampo)) {
                    if (this.isEsisteCampoLinkato(nomeCampo)) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* prova con il campo sigla */
            if (continua) {
                nomeCampo = Modello.NOME_CAMPO_SIGLA;
                if (this.isEsisteCampoLinkato(nomeCampo)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* prova con il campo descrizione */
            if (continua) {
                nomeCampo = Modello.NOME_CAMPO_DESCRIZIONE;
                if (this.isEsisteCampoLinkato(nomeCampo)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* prova con il campo codice */
            if (continua) {
                nomeCampo = Modello.NOME_CAMPO_CHIAVE;
                if (this.isEsisteCampoLinkato(nomeCampo)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se non l'ha ancora trovato, recupera l'oggetto campo */
            if (campo == null) {
                this.setNomeCampoValoriLinkato(nomeCampo);
                mod = this.getModuloLinkato();
                continua = (mod != null);
            }// fine del blocco if

            /* assegna il campo valori linkato */
            if (continua) {
                campo = this.getModuloLinkato().getCampo(nomeCampo);
                this.setCampoValoriLinkato(campo);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } // fine del metodo


    /**
     * Restituisce il valore del campo linkato per un singolo record.
     * <p/>
     * Recupera il campo linkato <br>
     * Esegue la query per il codice record richiesto <br>
     *
     * @param codice del record
     *
     * @return valore del campo per il record richiesto
     */
    public Object getValoreCampoLink(int codice) {
        /* variabili e costanti locali di lavoro */
        Object valore = "";
        boolean continua;
        Campo campo;
        Modulo mod;

        try {    // prova ad eseguire il codice

            campo = this.getCampoValoriLinkato();
            continua = campo != null;

            if (continua) {
                mod = this.getModuloLinkato();
                valore = mod.query().valoreCampo(campo, codice);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return valore;
    }


    /**
     * Controlla l'esistenza di un campo nel modulo linkato.
     * <br>
     *
     * @param unaChiave nomeInterno chiave per recuperare il campo dalla collezione
     *
     * @return vero se il campo esiste nella collezione del modulo linkato
     */
    private boolean isEsisteCampoLinkato(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Modulo mod;

        try { // prova ad eseguire il codice
            mod = this.getModuloLinkato();
            if (mod != null) {
                esiste = mod.isEsisteCampo(unaChiave);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * ritorna il nome chiave del modulo linkato
     */
    public String getNomeModuloLinkato() {
        return this.unNomeModuloLinkato;
    } /* fine del metodo getter */


    /**
     * ritorna il modulo linkato
     */
    public Modulo getModuloLinkato() {
        return this.unModuloLinkato;
    } /* fine del metodo getter */


    /**
     * ritorna la tavola linkata
     */
    public String getTavolaLinkata() {
        return this.getModuloLinkato().getModello().getTavolaArchivio();
    } /* fine del metodo getter */


    /**
     * ritorna il nome interno del campo del modulo linkato
     * al quale questo campo e' collegato
     */
    private String getNomeCampoLinkChiave() {
        return this.unNomeCampoLinkChiave;
    } /* fine del metodo getter */


    /**
     * ritorna il campo del modulo linkato
     * al quale questo campo e' collegato
     */
    public Campo getCampoLinkChiave() {
        return this.unCampoLinkChiave;
    } /* fine del metodo getter */


    /**
     * nome interno del campo del modulo linkato che viene visualizzato nella lista
     */
    public void setNomeColonnaListaLinkata(String nomeColonnaListaLinkata) {
        this.nomeColonnaListaLinkata = nomeColonnaListaLinkata;
    } /* fine del metodo setter */


    /**
     * ritorna il nome interno del campo del modulo linkato
     * che viene visualizzato nella lista
     */
    public String getNomeColonnaListaLinkata() {
        return this.nomeColonnaListaLinkata;
    } /* fine del metodo getter */


    /**
     * ritorna il nome della Vista (serie di campi di un'altro modello)
     * che vengono visualizzati nella lista
     */
    public String getNomeVistaLink() {
        return this.unNomeVistaLink;
    } /* fine del metodo setter */


    /**
     * nome della Vista (serie di campi di un'altro modello)
     * che vengono visualizzati nella lista
     */
    public void setNomeVistaLinkata(String unNomeVistaLink) {
        this.unNomeVistaLink = unNomeVistaLink;
    } /* fine del metodo setter */


    /**
     * nome della Vista (serie di campi di un'altro modello)
     * che vengono visualizzati nella lista
     */
    public void setVistaLinkata(Viste vista) {
        /* invoca il metodo sovrascritto della classe */
        this.setNomeVistaLinkata(vista.toString());
    } /* fine del metodo setter */


    /**
     * Azione da intraprendere sulla tavola molti in caso
     * di cancellazione del record sulla tavola uno
     */
    public Db.Azione getAzioneDelete() {
        /* variabili e costanti locali di lavoro */
        Db.Azione azione = null;
        Modello mod;

        try { // prova ad eseguire il codice
            azione = this.azioneDelete;

            if (azione == null) {
                mod = this.getCampoParente().getModulo().getModello();
                azione = mod.getAzioneDelete();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return azione;
    }


    /**
     * Azione da intraprendere sulla tavola molti in caso
     * di modifica del record sulla tavola uno
     */
    public Db.Azione getAzioneUpdate() {
        /* variabili e costanti locali di lavoro */
        Db.Azione azione = null;
        Modello mod;

        try { // prova ad eseguire il codice
            azione = this.azioneUpdate;

            if (azione == null) {
                mod = this.getCampoParente().getModulo().getModello();
                azione = mod.getAzioneUpdate();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return azione;
    }


    /**
     * ritorna il campo del modulo linkato
     * che viene visualizzato nella scheda
     */
    public Campo getCampoLinkVisibile() {
        /** variabili e costanti locali di lavoro */
        Modulo unModuloLinkato = null;
        String unNomeCampoLinkVisibile = "";
        Campo unCampoLinkVisibile = null;

        try {
            // prova ad eseguire il codice
            unModuloLinkato = this.getModuloLinkato();
            unNomeCampoLinkVisibile = this.getNomeCampoValoriLinkato();
            unCampoLinkVisibile = unModuloLinkato.getModello().getCampo(unNomeCampoLinkVisibile);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return unCampoLinkVisibile;
    } /* fine del metodo getter */


    /**
     * ritorna la Vista (serie di campi di un'altro modello)
     * che vengono visualizzati nella lista
     */
    public Vista getVistaLink() {
        return this.unaVistaLink;
    } /* fine del metodo setter */


    /**
     * recupera il flag setRelazionePreferita del campo
     */
    public boolean isRelazionePreferita() {
        return this.isRelazionePreferita;
    } /* fine del metodo setter */


    /**
     * Ritorna il filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro base
     */
    public Filtro getFiltroBase() {
        return filtroBase;
    }


    /**
     * Assegna un filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public void setFiltroBase(Filtro filtro) {
        this.filtroBase = filtro;
    }


    /**
     * Ritorna il filtro corrente per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro corrente
     */
    public Filtro getFiltroCorrente() {
        return filtroCorrente;
    }


    /**
     * Assegna un filtro corrente per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public void setFiltroCorrente(Filtro filtro) {
        this.filtroCorrente = filtro;
    }


    /**
     * Ritorna il filtro completo (base+corrente) per la selezione
     * dei record linkati da visualizzare
     * <p/>
     *
     * @return il filtro completo
     */
    public Filtro getFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroBase;
        Filtro filtroCorrente;

        try {    // prova ad eseguire il codice
            
            filtroBase = this.getFiltroBase();
            filtroCorrente = this.getFiltroCorrente();

            if ((filtroBase!=null) || (filtroCorrente!=null)) {
                filtro = new Filtro();
                if (filtroBase != null) {
                    filtro.add(filtroBase);
                }// fine del blocco if
                if (filtroCorrente != null) {
                    filtro.add(filtroCorrente);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    private Ordine getOrdine() {
        return ordine;
    }


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param ordine da assegnare
     */
    public void setOrdineElenco(Ordine ordine) {
        this.ordine = ordine;
    }


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param campo di ordinamento da assegnare
     */
    public void setOrdineElenco(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Ordine ordine;

        try { // prova ad eseguire il codice
            ordine = new Ordine();
            ordine.add(campo);
            this.setOrdineElenco(ordine);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * nome interno del campo del modulo linkato che viene visualizzato nella scheda
     */
    public void setNomeCampoValoriLinkato(String nomeCampoSchedaLinkato) {
        this.nomeCampoSchedaLinkato = nomeCampoSchedaLinkato;
    } /* fine del metodo */


    /**
     * nome interno del campo del modulo linkato che viene visualizzato nella scheda
     */
    public void setCampoLinkato(Campi campo) {
        /* invoca il metodo sovrascritto della superclasse */
        this.setNomeCampoValoriLinkato(campo.get());
    } /* fine del metodo */


    /**
     * ritorna il nome interno del campo del modulo linkato
     * che viene visualizzato nella scheda
     */
    public String getNomeCampoValoriLinkato() {
        return this.nomeCampoSchedaLinkato;
    } /* fine del metodo getter */


    /**
     * ritorna il campo che viene visualizzato nella scheda o nel combo
     * <p/>
     *
     * @return il campo visualizzato
     */
    public Campo getCampoValoriLinkato() {
        return campoValoriLinkato;
    }


    /**
     * assegna il campo che viene visualizzato nella scheda o nel combo
     * <p/>
     *
     * @param campo da visualizzare
     */
    public void setCampoValoriLinkato(Campo campo) {
        this.campoValoriLinkato = campo;
    }


    /**
     * nome interno del campo del modulo linkato per l'ordinamento.
     * <p/>
     * usa l'ordine pubblico del campo
     */
    public void setNomeCampoOrdineLinkato(String nomeCampoOrdineLinkato) {
        this.nomeCampoOrdineLinkato = nomeCampoOrdineLinkato;
    } /* fine del metodo */


    private String getNomeCampoOrdineLinkato() {
        return nomeCampoOrdineLinkato;
    }


    public Estratti getEstratto() {
        return estratto;
    }


    /**
     * assegna un estratto che fornisce i valori linkati
     * <p/>
     * l'estratto ha la precedenza sul campo linkato
     *
     * @param estratto che fornisce una matrice doppia con codici e valori
     */
    public void setEstrattoLinkato(Estratti estratto) {
        this.estratto = estratto;
    }
}// fine della classe
