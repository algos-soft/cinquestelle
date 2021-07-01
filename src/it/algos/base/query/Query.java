/**
 * Title:     Query
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-nov-2004
 */
package it.algos.base.query;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.campi.Campi;
import it.algos.base.query.campi.CampoQuery;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.PassaggiSelezione;
import it.algos.base.query.selezione.PassaggioObbligato;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interfaccia rappresentante una Query generica
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-nov-2004 ore 14.23.30
 */
public interface Query {

    /**
     * Codifica per una QueryModifica di tipo INSERT
     */
    public static final int TIPO_INSERT = 1;

    /**
     * Codifica per una QueryModifica di tipo UPDATE
     */
    public static final int TIPO_UPDATE = 2;

    /**
     * Codifica per una QueryModifica di tipo DELETE
     */
    public static final int TIPO_DELETE = 3;


    /**
     * Risolve la query.
     * <p/>
     * Converte tutti gli oggetti che usano nomi di campo
     * in oggetti che usano solo oggetti campo,
     * in base al modulo di questa Query.
     */
    public abstract void risolvi();


    /**
     * Trasporta i valori della query da livello Memoria
     * a livello Database.
     * <p/>
     * Sovrascritto dalle sottoclassi.<br>
     * Converte i valori da Memoria ad Archivio
     * Converte i valori da Archivio a Database
     *
     * @param db il database per il del quale convertire i valori
     */
    public abstract void memoriaDb(Db db);

//    /**
//     * Converte i valori della Query da livello Memoria a livello Archivio.
//     * <p/>
//     * @param db il database a fronte del quale convertire i valori
//     */
//    public abstract void bl2db(Db db);

//    /**
//     * Converte i valori della Query da livello Memoria a livello Archivio
//     * <p/>
//     */
//    public abstract void memoriaArchivio();


    /**
     * Aggiunge un campo alla lista dei campi.
     * <p/>
     *
     * @param campo il Campo da aggiungere
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public abstract CampoQuery addCampo(Campo campo);


    /**
     * Aggiunge un campo alla lista dei campi.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public abstract CampoQuery addCampo(String nomeCampo);

    /**
     * Aggiunge un campo alla lista dei campi.
     * <p/>
     *
     * @param campo dalla Enum Campi
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery addCampo(it.algos.base.wrapper.Campi campo);


    /**
     * Aggiunge un campo con valore alla lista dei campi.
     * <p/>
     *
     * @param campo il campo
     * @param valore il valore per il campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public abstract CampoQuery addCampo(Campo campo, Object valore);


    /**
     * Aggiunge un campo con valore alla lista dei campi.
     * <p/>
     *
     * @param nomeCampo il nome del campo
     * @param valore il valore per il campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public abstract CampoQuery addCampo(String nomeCampo, Object valore);


    /**
     * Assegna l'elenco completo dei campi.
     * <p/>
     * Usa una ArrayList in ingresso
     *
     * @param campi lista di oggetti Campo o nomi campo
     */
    public abstract void setCampi(ArrayList campi);


    /**
     * Assegna l'elenco completo dei campi.
     * <p/>
     * Usa una HashMap in ingresso
     *
     * @param campi collezione di oggetti Campo o nomi campo
     */
    public abstract void setCampi(HashMap campi);


    /**
     * Assegna un valore a un campo.
     * <p/>
     *
     * @param campo al quale assegnare il valore
     * @param valore da assegnare
     */
    public abstract void setValore(Campo campo, Object valore);


    /**
     * Ritorna l'elenco dei Campi della query.
     * <p/>
     *
     * @return l'elenco dei campi
     */
    public abstract ArrayList getListaCampi();


    /**
     * Ritorna l'elenco degli elementi della Query.
     * <p/>
     *
     * @return l'elenco degli elementi della Query (oggetti CampoQuery)
     */
    public abstract ArrayList getListaElementi();


    /**
     * Aggiunge un filtro al filtro.
     * <p/>
     * Usa la clausola di unione specificata<br>
     *
     * @param unione la clausola di unione
     * @param filtro il filtro da aggiungere
     */
    public abstract void addFiltro(String unione, Filtro filtro);


    /**
     * Aggiunge un filtro al filtro.
     * <p/>
     * Usa la clausola di unione di default (AND)
     *
     * @param filtro il filtro da aggiungere
     */
    public abstract void addFiltro(Filtro filtro);


    /**
     * Ritorna il filtro della Query
     * <p/>
     *
     * @return il filtro della Query
     */
    public abstract Filtro getFiltro();


    /**
     * Assegna un filtro alla Query
     */
    public abstract void setFiltro(Filtro filtro);


    /**
     * Ritorna l'oggetto PassaggiSelezione
     * <p/>
     *
     * @return l'oggetto PassaggiSelezione
     */
    public abstract PassaggiSelezione getPassaggi();


    /**
     * attiva il filtro hard sulle QuerySelezione
     * <p/>
     * il filtro hard e' un filtro che viene automaticamente aggiunto
     * alla query selezione.
     * di default e' attivo.
     * Metodo sovrascritto dalle sottoclassi.
     *
     * @param usaFiltroHard flag di attivazione
     */
    public abstract void setUsaFiltroHard(boolean usaFiltroHard);


    /**
     * Restituisce la lista univoca dei valori distinti per tutti i campi
     * della query.
     * <p/>
     * Significativo solo per QuerySelezione.
     * Se la query contiene un solo campo, restituisce solo i valori diversi
     * Se la query contiene più di un campo, restituisce le righe dove le combinazioni
     * dei valori sono diverse (comportamento standard SQL)
     *
     * @param flag per restituire solo i valori distinti
     */
    public abstract void setValoriDistinti(boolean flag);


    /**
     * Aggiunge un ordine alla query.
     * <p/>
     *
     * @param campo il campo da aggiungere
     */
    public abstract void addOrdine(Campo campo);


    /**
     * Assegna un Ordine alla Query
     * <p>
     * @param ordine l'ordine da assegnare
     */
    public abstract void setOrdine(Ordine ordine);

    /**
     * Assegna un Ordine alla Query
     * <p>
     * @param campo di ordinamento
     */
    public abstract void setOrdine(Campo campo);



    /**
     * Aggiunge un Passaggio Obbligato.
     * <p/>
     * Implementato nelle classi specifiche
     *
     * @param passaggio il passaggio obbligato
     */
    public abstract void addPassaggio(PassaggioObbligato passaggio);


    /**
     * Aggiunge un Passaggio Obbligato.
     * <p/>
     * Implementato nelle classi specifiche
     *
     * @param modulo il modulo di destinazione
     * @param campo il Campo dal quale passare obbligatoriamente
     */
    public abstract void addPassaggio(Modulo modulo, Campo campo);


    public abstract Modulo getModulo();


    /**
     * Ritorna l'oggetto contenitore dei campi della Query
     * <p/>
     *
     * @return il contenitore dei campi della Query.
     */
    public abstract Campi getCampi();


}// fine della interfaccia
