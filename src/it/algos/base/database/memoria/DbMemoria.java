/**
 * Title:     Memoria
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database.memoria;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.DbBase;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.query.selezione.QuerySelezione;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 15.47.12
 */
public final class DbMemoria extends DbBase {

    /**
     * Costruttore completo con parametri.
     */
    public DbMemoria() {
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
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche. <br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se
     * non riescono a portare a termine la propria inizializzazione specifica.<br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return true se l'inizializzazione ha avuto successo <br>
     */
    public boolean inizializza() {

        if (this.isInizializzato() == false) {

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

        }// fine del blocco if

        /* valore di ritorno */
        return true;
    }


    /**
     * Crea una nuova connessione a questo database.
     * <p/>
     *
     * @return la connessione appena creata
     */
    public Connessione creaConnessione() {
        return null;
    }


    /**
     * Carica una selezione di record.
     * <p/>
     *
     * @param query informazioni per effettuare la selezione
     *
     * @return un oggetto dati
     */
    public Dati caricaRecord(QuerySelezione query) {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;

        /* invoca il metodo sovrascritto */
        super.querySelezione(query, null);

        try { // prova ad eseguire il codice
            // todo da vedere...
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Carica un singolo record.
     * <p/>
     *
     * @param codice codice chiave del record
     * @param conn la connessione sulla quale effettuare la query
     *
     * @return un oggetto dati
     */
    public Dati caricaRecord(int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Dati unDato = null;

        try { // prova ad eseguire il codice
            // todo da vedere...
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unDato;
    }


    /**
     * Determina se un dato campo eisste sul database.
     * <p/>
     *
     * @param campo il campo da controllare
     * @param conn la connessione da utilizzare per il controllo
     *
     * @return true se il campo esiste
     */
    public boolean isEsisteCampo(Campo campo, Connessione conn) {
        // todo da fare...
        /* valore di ritorno */
        return false;
    }


    /**
     * Elimina una colonna dal database.
     * <p/>
     *
     * @param campo il campo corrispondente alla colonna da eliminare
     * @param conn la connessione da utilizzare per l'operazione
     *
     * @return true se riuscito.
     */
    public boolean eliminaColonna(Campo campo, Connessione conn) {
        return false;
    }


}// fine della classe
