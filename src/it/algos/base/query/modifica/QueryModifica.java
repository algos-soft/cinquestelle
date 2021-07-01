/**
 * Title:     QueryModifica
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-nov-2004
 */

package it.algos.base.query.modifica;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.QueryBase;
import it.algos.base.query.campi.Campi;
import it.algos.base.query.campi.CampoQuery;

import java.util.ArrayList;

/**
 * Implementazione concreta di una Query per la modifica di records.
 * <p/>
 * Gestisce campi e filtro nella superclasse.
 * Gestisce il tipo di Query Modifica (INSERT/UPDATE/DELETE) in questa classe.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-nov-2004 ore 12.43.53
 */
public abstract class QueryModifica extends QueryBase {

    /**
     * Tipo di Query concreta (INSERT/UPDATE/DELETE)<br>
     * Costanti nella interfaccia Query.
     */
    private int tipoQuery = 0;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo il modulo di riferimento.
     * @param tipo il tipo di Query (Costanti nella interfaccia Query)
     */
    public QueryModifica(Modulo modulo, int tipo) {
        /** rimanda al costruttore della superclasse */
        super(modulo);

        /* regolazione delle variabili di istanza con i parametri */
        this.setTipoQuery(tipo);

        try {
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore */


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() {
    } /* fine del metodo inizia */

//    /**
//     * Converte i valori della Query da livello Business Logic
//     * a livello database.
//     * <p/>
//     * Sovrascrive (parzialmente) il metodo della superclasse.
//     * La QueryModifica deve convertire anche i valori dei campi.
//     * @param db il database a fronte del quale convertire i valori
//     */
//    public void bl2db(Db db) {
//        super.bl2db(db);
//        this.getCampi().bl2db(db);
//    }


    /**
     * Trasporta i valori della query da livello Memoria
     * a livello Database.
     * <p/>
     * Converte i valori da Memoria ad Archivio
     * Converte i valori da Archivio a Database
     */
    public void memoriaDb(Db db) {
        /* variabili e costanti locali di lavoro */
        Campi campi;
        ArrayList<CampoQuery> elementi;
        Campo campo;
        Object memoria;
        Object archivio;

        super.memoriaDb(db);

        try { // prova ad eseguire il codice
            campi = this.getCampi();
            elementi = campi.getListaElementi();
            for (CampoQuery cq : elementi) {
                memoria = cq.getValoreBl();
                campo = cq.getCampo();
                archivio = campo.getCampoDati().getArchivioDaMemoria(memoria);
                cq.setValoreBl(archivio);
                cq.bl2db(db);
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegna un valore a un campo.
     * <p/>
     *
     * @param campo al quale assegnare il valore
     * @param valore da assegnare
     */
    public void setValore(Campo campo, Object valore) {
        /* variabili e costanti locali di lavoro */
        Campi campi;

        try { // prova ad eseguire il codice
            campi = this.getCampi();
            campi.setValore(campo, valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    private void setTipoQuery(int tipoQuery) {
        this.tipoQuery = tipoQuery;
    }


    /**
     * Ritorna il tipo di QueryModifica
     * <p/>
     *
     * @return il tipo di QueryModifica
     */
    public int getTipoQuery() {
        return tipoQuery;
    }

}// fine della classe