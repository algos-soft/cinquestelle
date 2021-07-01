/**
 * Title:     InterpreteBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-ott-2004
 */
package it.algos.base.database.interprete;

import it.algos.base.database.Db;
import it.algos.base.errore.Errore;

/**
 * Implementazione astratta di un Interprete.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-ott-2004 ore 10.24.25
 */
public abstract class InterpreteBase extends Object implements Interprete {

    /**
     * database proprietario di questo interprete
     */
    private Db database = null;


    /**
     * Costruttore completo.<br>
     *
     * @param db il database proprietario
     */
    public InterpreteBase(Db db) {
        /* rimanda al costruttore della superclasse */
        super();

        this.database = db;

        try { // prova ad eseguire il codice
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
     * Ritorna il database proprietario di questo Interprete
     * <p/>
     *
     * @return il database proprietario
     */
    public Db getDatabase() {
        return database;
    }

}// fine della classe
