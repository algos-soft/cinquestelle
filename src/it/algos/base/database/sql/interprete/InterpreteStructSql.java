/**
 * Title:     InterpreteStructSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-nov-2004
 */
package it.algos.base.database.sql.interprete;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.modulo.Modulo;

/**
 * Interprete di comandi per un database Sql.
 * </p>
 * Un Interprete Sql e' delegato a trasformare gli oggetti astratti
 * (query, filtri, ecc...) in comandi specifici per il database Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-nov-2004 ore 8.22.03
 */
public interface InterpreteStructSql extends InterpreteSql {

    /**
     * Modifica il tipo di una colonna.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param keyword la keyword Sql per il tipo della colonna
     *
     * @return true se la colonna e' stata modificata con successo
     */
    public abstract boolean modificaTipoColonna(String tavola, String colonna, String keyword);


    /**
     * Assegna o rimuove la caratteristica NOT NULL da una colonna.
     * <p/>
     *
     * @param campo il campo del quale regolare la colonna
     * @param flag true per assegnare, false per rimuovere la caratteristica
     *
     * @return true se riuscito
     */
    public abstract boolean regolaNotNull(Campo campo, boolean flag);


    /**
     * Elimina la eventuale caratteristica Primary Key dalla tavola.
     * <p/>
     *
     * @param tavola la tavola dalla quale eliminare la caratteristica
     *
     * @return true se riuscito, o se la tavola non aveva Primary Key
     */
    public abstract boolean eliminaPrimaryKey(String tavola);


    /**
     * Elimina una Foreign Key.
     * <p/>
     *
     * @param tavola il nome della tavola proprietaria della fkey
     * @param nomeFkey il nome della fKey da eliminare
     *
     * @return true se la fKey e' stata eliminata correttamente
     */
    public abstract boolean eliminaForeignKey(String tavola, String nomeFkey);


    /**
     * Elimina un indice.
     * <p/>
     *
     * @param tavola la tavola dalla quale eliminare l'indice
     * @param nomeIndice il nome dell'indice da eliminare
     *
     * @return true se l'indice e' stato eliminato correttamente
     */
    public abstract boolean eliminaIndice(String tavola, String nomeIndice);


    /**
     * Controlla se una tavola esiste sul database.
     * <p/>
     * La ricerca della tavola e' case-sensitive.
     *
     * @param nomeTavola il nome della tavola da cercare
     * @param conn la connessione da utilizzare per il controllo
     *
     * @return true se la tavola esiste, altrimenti false
     */
    public abstract boolean isEsisteTavola(String nomeTavola, Connessione conn);


    /**
     * Determina se una colonna esiste sul database.
     * <p/>
     * la ricerca della tavola e del campo sono case-sensitive
     *
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se la colonna esiste
     */
    public abstract boolean isEsisteColonna(String tavola, String colonna, Connessione conn);


    /**
     * Determina se un dato campo esiste sul database.
     * <p/>
     *
     * @param campo il campo da controllare
     * @param conn la connessione da utilizzare per il controllo
     *
     * @return true se il campo esiste
     */
    public abstract boolean isEsisteCampo(Campo campo, Connessione conn);


    /**
     * Crea una tavola sul database.
     * <p/>
     * Viene aggiunto il campo chiave, per ora non regolato.
     *
     * @param modulo di riferimento
     * @param conn la connessione da utilizzare
     *
     * @return true se gia' esistente o creata correttamente
     *         false se ha tentato di crearla ma non e' riuscito.
     */
    public abstract boolean creaTavola(Modulo modulo, Connessione conn);


    /**
     * Crea una colonna del database in base a un campo.
     * <p/>
     * Crea la colonna del tipo appropriato al campo.
     * Non effettua altre regolazioni (caratteristiche, indici ecc...)
     *
     * @param campo il campo per il quale creare la colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se la colonna e' stata creata correttamente
     */
    public abstract boolean creaColonna(Campo campo, Connessione conn);


    /**
     * Elimina una colonna dal database.
     * <p/>
     *
     * @param campo il campo corrispondente alla colonna da eliminare
     * @param conn la connessione da utilizzare per l'operazione
     *
     * @return true se riuscito.
     */
    public abstract boolean eliminaColonna(Campo campo, Connessione conn);


}// fine della interfaccia
