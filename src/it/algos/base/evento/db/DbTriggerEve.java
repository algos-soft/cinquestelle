/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3 feb 2007
 */

package it.algos.base.evento.db;

import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;

/**
 * Evento di tipo DbTrigger
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3 feb 2007
 */
public final class DbTriggerEve extends DbEve {

    private Connessione conn;

    private Db.TipoOp tipo;

    private int codice;

    private ArrayList<CampoValore> valoriNew;

    private ArrayList<CampoValore> valoriOld;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param tipo di operazione sul db (nuovo, modifica, elimina)
     * @param conn connessione utilizzata per effettuare l'operazione
     * @param codice del record interessato
     * @param valoriNew dei campi del record interessato dopo l'operazione
     * @param valoriOld dei campi del record interessato prima dell'operazione
     */
    public DbTriggerEve(Db.TipoOp tipo,
                        Connessione conn,
                        Integer codice,
                        ArrayList<CampoValore> valoriNew,
                        ArrayList<CampoValore> valoriOld) {
        /* rimanda al costruttore della superclasse */
        super("");  // null non funziona!!

        try { // prova ad eseguire il codice

            this.setTipo(tipo);
            this.setConn(conn);
            this.setCodice(codice);
            this.setValoriNew(valoriNew);
            this.setValoriOld(valoriOld);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public Connessione getConn() {
        return conn;
    }


    private void setConn(Connessione conn) {
        this.conn = conn;
    }


    public Db.TipoOp getTipo() {
        return tipo;
    }


    private void setTipo(Db.TipoOp tipo) {
        this.tipo = tipo;
    }


    public int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }


    public ArrayList<CampoValore> getValoriNew() {
        return valoriNew;
    }


    private void setValoriNew(ArrayList<CampoValore> valori) {
        this.valoriNew = valori;
    }


    public ArrayList<CampoValore> getValoriOld() {
        return valoriOld;
    }


    private void setValoriOld(ArrayList<CampoValore> valoriOld) {
        this.valoriOld = valoriOld;
    }
} // fine della classe
