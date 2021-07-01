/**
 * Title:     QueryFactory
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      4-nov-2004
 */
package it.algos.base.query;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.util.Funzione;
import it.algos.base.errore.Errore;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.campi.CampoQuery;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.modifica.QueryDelete;
import it.algos.base.query.modifica.QueryUpdate;
import it.algos.base.query.selezione.QuerySelezione;

/**
 * Factory per la creazione di oggetti query .
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> <br>
 * <li> Fornisce i metodi statici di creazione degli oggetti di questo
 * package </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 4-nov-2004 ore 15.28.14
 */
public abstract class QueryFactory {

    /**
     * Costruttore semplice senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public QueryFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore semplice


    /**
     * Crea una query per recuperare un record dal codice. <br>
     * <p/>
     *
     * @param modulo modulo di riferimento
     * @param codice codice chiave del record
     *
     * @return unOggetto oggetto appena creato
     */
    public static Query codice(Modulo modulo, int codice) {
        /* variabili e costanti locali di lavoro */
        Query query = null;
        Modello modello = null;
        Campo campoChiave = null;

        try { // prova ad eseguire il codice
            modello = modulo.getModello();
            campoChiave = modello.getCampoChiave();
            query = new QuerySelezione(modulo);
            query.setCampi(modello.getCampiFisici());
            query.setFiltro(FiltroFactory.crea(campoChiave, codice));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return query;
    }// fine del metodo


    /**
     * Crea una query per eliminare un record di un Modulo.
     * <p/>
     *
     * @param modulo modulo di riferimento
     * @param codice codice chiave del record
     *
     * @return unOggetto oggetto appena creato
     */
    public static Query elimina(Modulo modulo, int codice) {
        /* variabili e costanti locali di lavoro */
        Query query = null;
        Campo campoChiave = null;
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            campoChiave = modulo.getCampoChiave();
            filtro = FiltroFactory.crea(campoChiave, codice);
            query = QueryFactory.elimina(modulo, filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return query;
    }// fine del metodo


    /**
     * Crea una query per eliminare diversi record di un Modulo.
     * <p/>
     *
     * @param modulo modulo di riferimento
     * @param filtro filtro per la selezione dei record da eliminare
     *
     * @return unOggetto oggetto appena creato
     */
    public static Query elimina(Modulo modulo, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Query query = null;

        try { // prova ad eseguire il codice
            query = new QueryDelete(modulo);
            query.setFiltro(filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return query;
    }// fine del metodo


    /**
     * Crea una query per modificare il valore di un campo di un record.
     * <p/>
     *
     * @param campo il campo da modificare
     * @param codice codice chiave del record
     * @param valore il valore da assegnare
     *
     * @return unOggetto oggetto appena creato
     */
    public static Query modifica(Campo campo, int codice, Object valore) {
        /* variabili e costanti locali di lavoro */
        Query query = null;
        Modulo modulo;
        Modello modello;
        Campo campoChiave;

        try { // prova ad eseguire il codice
            modulo = campo.getModulo();
            modello = modulo.getModello();
            campoChiave = modello.getCampoChiave();
            query = new QueryUpdate(modulo);
            query.addCampo(campo, valore);
            query.setFiltro(FiltroFactory.crea(campoChiave, codice));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return query;
    }// fine del metodo


    /**
     * Crea una query per contare il numero di records non nulli in un modulo.
     * <p/>
     * (conta sul campo chiave)
     *
     * @param modulo il modulo per il quale contare i records
     *
     * @return unOggetto la query appena creata
     */
    public static Query conta(Modulo modulo) {
        return conta(modulo.getModello().getCampoChiave());
    }// fine del metodo


    /**
     * Crea una query per contare il numero di records non nulli in un campo.
     * <p/>
     *
     * @param campo il campo da contare
     *
     * @return unOggetto la query appena creata
     */
    public static Query conta(Campo campo) {
        return conta(campo, (Filtro)null);
    }// fine del metodo


    /**
     * Crea una query per contare il numero di records non
     * nulli in un campo per un dato filtro.
     * <p/>
     *
     * @param campo il campo da contare
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return unOggetto la query appena creata
     */
    public static Query conta(Campo campo, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Query query = null;
        Modulo mod = null;
        CampoQuery cq;

        try { // prova ad eseguire il codice
            continua = (campo != null);

            if (continua) {
                mod = campo.getModulo();
                continua = (mod != null);
            } else {
                int a = 87;
            }// fine del blocco if-else

            if (continua) {
                query = new QuerySelezione(mod);
                cq = query.addCampo(campo);
                cq.addFunzione(Funzione.COUNT);
                if (filtro != null) {
                    query.setFiltro(filtro);
                }// fine del blocco if
            } else {
                int a = 87;
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return query;
    }// fine del metodo


    /**
     * Somma i valori di un campo.
     *
     * @param campo il campo da sommare
     * @param filtro il filtro da applicare, null per non specificato
     *
     * @return la somma dei valori presenti nel campo
     */
    public static Query somma(Campo campo, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Query query = null;
        Modulo modulo;
        CampoQuery cq;

        try { // prova ad eseguire il codice
            modulo = campo.getModulo();
            query = new QuerySelezione(modulo);
            cq = query.addCampo(campo);
            cq.addFunzione(Funzione.SUM);
            query.setFiltro(filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return query;
    }


}// fine della classe
