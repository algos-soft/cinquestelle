/**
 * Title:        CDBBase.java
 * Package:      prove.nuovocampo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 20.46
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.db;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoAstratto;
import it.algos.base.costante.CostanteCarattere;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pref.Pref;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.PassaggiSelezione;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.Viste;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Regola le funzionalita di interazione dei Campi col database <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 20.46
 */
public abstract class CDBBase extends CampoAstratto

        implements Cloneable, CostanteCarattere, CampoDB {


    /**
     * flag - campo chiave primaria
     */
    protected boolean isChiavePrimaria = false;

    /**
     * flag - true per se il capo va indicizzato
     */
    protected boolean isIndicizzato = false;

    /**
     * flag - true se l'indice deve essere a valore unico
     * (significativo solo se il campo e' indicizzato)
     */
    protected boolean isUnico = false;

    /**
     * Lunghezza dell'indice per i campi Testo<br>
     * Misurato in numero di caratteri<br>
     * Indica il numero di caratteri che vengono indicizzati<br>
     * Significativo solo per i campi Testo e Blob<br>
     * Usato solo da alcuni database (postgres no, mysql si...)<br>
     * Regolato alla creazione e non piu' modificabile<br>
     * Per modificarlo, bisogna eliminare e ricreare l'indice.<br>
     */
    protected int lunghezzaIndice = 0;

    /**
     * flag - true per accettare valori nulli
     */
    protected boolean isAccettaValoreNullo = false;

    /**
     * flag - true per indicare che e' un campo relazionato con un altra tavola
     */
    protected boolean isLinkato = false;

    /**
     * flag per indicare se il campo esiste fisicamente sul DB
     * (alcuni campi, come il campo Sublista, possono non esistere
     * fisicamente sul DB)
     */
    protected boolean isCampoFisico = false;

    /**
     * flag - true per indicare che e' un campo fisso Algos sempre presente
     */
    protected boolean isFissoAlgos = false;

    /**
     * flag per indicare se il campo e' stato inizializzato
     */
    private boolean isRelazionato = false;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDBBase() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDBBase(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
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

        /** Regolazione dei valori di default */
        this.setAccettaValoreNullo(true);
        this.setChiavePrimaria(false);
        this.setCampoFisico(true);
        this.setFissoAlgos(false);
        this.setLinkato(false);
        this.setLunghezzaIndice(CampoDB.LUNGHEZZA_INDICE_DEFAULT);

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

        try { // prova ad eseguire il codice

            if (!this.isRelazionato()) {

                if (this.isCampoFisico()) {
                    riuscito = this.allineaCampo();
                }// fine del blocco if

                this.setRelazionato(riuscito);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Tenta di inizializzare il campo.
     */
    public void inizializza() {

        /* invoca il metodo (quasi) sovrascritto della superclasse */
        super.inizializzaCampoAstratto();
//        this.setInizializzato(true);

    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito' <br>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    public void avvia() {
    } /* fine del metodo */


    /**
     * Crea e/o allinea il campo sul DB.
     * <p/>
     * Chiamato dalle sottoclassi (solo quelle che implementano campi fisici)
     * durante la fase di inizializzazione, quanto tutto e' pronto
     * per effettuare questa operazione.
     *
     * @return true se l'operazione e' riuscita
     */
    protected boolean allineaCampo() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        Campo campo;
        Modulo modulo;
        Connessione conn;

        try {    // prova ad eseguire il codice

            if (!Pref.Cost.controlloCampi.is()) {
                continua = false;
            }// fine del blocco if

            /* controlla che sia un campo fisico */
            if (continua) {
                if (!this.isCampoFisico()) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if


            if (continua) {
                /* recupera il campo parente, il modulo e il database */
                campo = this.getCampoParente();
                modulo = campo.getModulo();
                conn = modulo.getConnessione();

                /* regola le caratteristiche del campo sul DB */
                if (conn != null) {
                    riuscito = conn.allineaCampo(campo);
                }// fine del blocco if
            } else {

                /* controllo campi omesso */
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
     * flag - campo chiave primaria
     */
    public void setChiavePrimaria(boolean isChiavePrimaria) {

        this.isChiavePrimaria = isChiavePrimaria;

        /** controlli sugli altri flag correlati */
        if (isChiavePrimaria) {

            /** se e' chiave primaria, deve essere obbligatoriamente NOT NULL */
            this.setAccettaValoreNullo(false);

            /** se e' chiave primaria, unique e' implicito e non deve
             *  essere attivato se no crea un indice inutile */
            this.setUnico(false);

        } /* fine del blocco if */

    } /* fine del metodo setter */


    /**
     * Usa un indice unico sul campo
     * <p/>
     *
     * @param isUnico - flag per usare un indice unico
     */
    public void setUnico(boolean isUnico) {
        if (isUnico) {
            this.setIndicizzato(true);
        }// fine del blocco if
        this.isUnico = isUnico;
    } /* fine del metodo setter */


    /**
     * flag - true per accettare valori nulli
     */
    public void setAccettaValoreNullo(boolean isAccettaValoreNullo) {
        this.isAccettaValoreNullo = isAccettaValoreNullo;
    } /* fine del metodo setter */


    /**
     * imposta il flag setCampoFisico del campo
     */
    public void setCampoFisico(boolean unFlag) {
        this.isCampoFisico = unFlag;
    } /* fine del metodo setter */


    /**
     * imposta il flag setFissoAlgos del campo
     */
    public void setFissoAlgos(boolean unFlag) {
        this.isFissoAlgos = unFlag;
    } /* fine del metodo setter */


    /**
     * Regola l'uso dell'indice sul campo
     * <p/>
     *
     * @param flag true se il campo deve essere indicizzato
     */
    public void setIndicizzato(boolean flag) {
        this.isIndicizzato = flag;
    } /* fine del metodo setter */


    /**
     * imposta il flag setLinkato del campo
     */
    public void setLinkato(boolean isLinkato) {
        this.isLinkato = isLinkato;
    } /* fine del metodo setter */


    /**
     * nome del modulo con il quale si stabilisce il legame
     */
    public void setNomeModuloLinkato(String unNomeModuloLinkato) {
    }


    /**
     * modulo con il quale si stabilisce il legame
     */
    public void setModuloLinkato(Modulo unModuloLinkato) {
    }


    public void setNomeCampoLinkChiave(String unNomeCampoLinkChiave) {
    }


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
    public void setAzioneDelete(Db.Azione azione) {
    }


    /**
     * Azione da intraprendere sulla tavola molti in caso
     * di modifica del record sulla tavola uno.
     * <p/>
     * Valori possibili (da interfaccia Db)<br>
     * - Db.Azione.noAction non fa nulla<br>
     * - Db.Azione.cascade modifica i record <br>
     * - Db.Azione.setNull pone il valore a NULL <br>
     * - Db.Azione.default assegna il valore di default<br>
     */
    public void setAzioneUpdate(Db.Azione azione) {
    }


    public void setNomeVistaLinkata(String unNomeVistaLink) {
    }


    public void setVistaLinkata(Viste vista) {
    }


    public void setNomeColonnaListaLinkata(String unNomeColonna) {
    }


    public void setNomeCampoValoriLinkato(String unNomeCampo) {
    }


    public void setCampoLinkato(Campi campo) {
    }


    public void setNomeCampoOrdineLinkato(String unNomeCampo) {
    }


    public void setRelazionePreferita(boolean isRelazionePreferita) {
    }


    public void setCampoPassaggio(Campo unCampo) {
    }


    public void setNomeEstratto(String nomeEstratto) {
    }


    public void setNomeEstratto(String nomeEstratto, int posizione) {
    }


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param ordine da assegnare
     */
    public void setOrdineElenco(Ordine ordine) {
    }


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param campo di ordinamento da assegnare
     */
    public void setOrdineElenco(Campo campo) {
    }


    public void addRelazioneCampo() {
    }


    /**
     * metodi setter implementati in CDBSub
     */
    public void setNomeCampoLinkRighe(String unNomeCampoLinkRighe) {
    }


    public void setNomeVistaSub(String unNomeVistaSub) {
    }


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
        return null;
    }

    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------


    /**
     * flag - campo chiave primaria
     */
    public boolean isChiavePrimaria() {
        return this.isChiavePrimaria;
    } /* fine del metodo getter */


    /**
     * flag - campo a valore unico
     */
    public boolean isUnico() {
        return this.isUnico;
    } /* fine del metodo getter */


    /**
     * flag - true per accettare valori nulli
     */
    public boolean isAccettaValoreNullo() {
        return this.isAccettaValoreNullo;
    } /* fine del metodo getter */


    /**
     * recupera il flag setCampoFisico del campo
     */
    public boolean isCampoFisico() {
        return this.isCampoFisico;
    } /* fine del metodo setter */


    /**
     * recupera il flag setFissoAlgos del campo
     */
    public boolean isFissoAlgos() {
        return this.isFissoAlgos;
    } /* fine del metodo setter */


    /**
     * recupera il flag setIndicizzato del campo
     */
    public boolean isIndicizzato() {
        return this.isIndicizzato;
    } /* fine del metodo setter */


    public int getLunghezzaIndice() {
        return lunghezzaIndice;
    }


    public void setLunghezzaIndice(int lunghezzaIndice) {
        this.lunghezzaIndice = lunghezzaIndice;
    }


    /**
     * recupera il flag setLinkato del campo
     */
    public boolean isLinkato() {
        return this.isLinkato;
    } /* fine del metodo setter */

//    /**
//     * flag per indicare se il campo e' inizializzato
//     */
//    protected void setInizializzato(boolean inizializzato) {
//        this.inizializzato = inizializzato;
//    }

//    public boolean isInizializzato() {
//        return inizializzato;
//    }


    /**
     * metodi getter implementati in CDBLinkato
     */
    public String getNomeModuloLinkato() {
        return null;
    }


    public Modulo getModuloLinkato() {
        return null;
    }


    public String getTavolaLinkata() {
        return null;
    }


    public Db.Azione getAzioneDelete() {
        return null;
    }


    public Db.Azione getAzioneUpdate() {
        return null;
    }


    public Campo getCampoLinkChiave() {
        return null;
    }


    public String getNomeColonnaListaLinkata() {
        return null;
    }


    public String getNomeCampoValoriLinkato() {
        return null;
    }


    /**
     * ritorna il campo che viene visualizzato nella scheda o nel combo
     * <p/>
     *
     * @return il campo visualizzato
     */
    public Campo getCampoValoriLinkato() {
        return null;
    }


    /**
     * assegna il campo che viene visualizzato nella scheda o nel combo
     * <p/>
     *
     * @param campo da visualizzare
     */
    public void setCampoValoriLinkato(Campo campo) {
    }


    /**
     * assegna un estratto che fornisce i valori linkati
     * <p/>
     * l'estratto ha la precedenza sul campo linkato
     *
     * @param estratto che fornisce una matrice doppia con codici e valori
     */
    public void setEstrattoLinkato(Estratti estratto) {
    }


    public Campo getCampoLinkVisibile() {
        return null;
    }


    public String getNomeVistaLink() {
        return null;
    }


    public Vista getVistaLink() {
        return null;
    }


    public boolean isRelazionePreferita() {
        return false;
    }


    /**
     * Ritorna il filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro base
     */
    public Filtro getFiltroBase() {
        return null;
    }


    /**
     * Assegna un filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public void setFiltroBase(Filtro filtro) {
    }


    /**
     * Ritorna il filtro corrente per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro corrente
     */
    public Filtro getFiltroCorrente() {
        return null;
    }


    /**
     * Assegna un filtro corrente per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public void setFiltroCorrente(Filtro filtro) {
    }


    /**
     * Ritorna il filtro completo (base+corrente) per la selezione
     * dei record linkati da visualizzare
     * <p/>
     *
     * @return il filtro completo
     */
    public Filtro getFiltro() {
        return null;
    }


    public PassaggiSelezione getPassaggiSelezione() {
        return null;
    }


    public String getNomeEstratto() {
        return "";
    }


    public String getNomeEstratto(int posizione) {
        return "";
    }


    public boolean isRelazionato() {
        return isRelazionato;
    }


    protected void setRelazionato(boolean relazionato) {
        isRelazionato = relazionato;
    }


    /**
     * metodi getter implementati in CDBSub
     */
    public Campo getCampoSubTesta() {
        return null;
    }


    public Campo getCampoSubRighe() {
        return null;
    }


    public String getNomeVistaSub() {
        return null;
    }


    public Vista getVistaSub() {
        return null;
    }


    public void setNomeModuloValori(String nomeModuloValori) {
    }


    public void setNomeMetodoValori(String nomeMetodoValori) {
    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     * Per fare una copia completa di questo oggetto occorre:
     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
     * gli stessi valori delle variabili originarie
     * Secondo copiare tutte le variabili che sono puntatori ad altri
     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     * originale (in genere tutti gli oggetti che vengono creati nella
     * classe col comando new)
     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     * esistenti nelle sottoclassi stesse
     *
     * @param unCampoParente CampoBase che cantiene questo CampoLogica
     */
    public CampoDB clonaCampo(Campo unCampoParente) {
        /* variabili e costanti locali di lavoro */
        CampoDB unCampo;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse Object */
            unCampo = (CampoDB)super.clone();
        } catch (CloneNotSupportedException unErrore) { // intercetta l'errore
            throw new InternalError();
        }// fine del blocco try-catch

        try { // prova ad eseguire il codice
            /* modifica il riferimento al campo parente */
            unCampo.setCampoParente(unCampoParente);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }

}




