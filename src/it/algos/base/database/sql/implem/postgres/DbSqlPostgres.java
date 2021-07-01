/**
 * Title:     SqlPostgres
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-ott-2004
 */
package it.algos.base.database.sql.implem.postgres;

import it.algos.base.database.sql.DbSqlBase;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;

/**
 * Database di tipo PostgreSql
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-ott-2004 ore 17.18.52
 */
public final class DbSqlPostgres extends DbSqlBase {

    /**
     * opzione SET NOT NULL per il comando ALTER COLUMN
     */
    private String setNotNull = " SET NOT NULL ";

    /**
     * opzione DROP NOT NULL per il comando ALTER COLUMN
     */
    private String dropNotNull = " DROP NOT NULL ";

    /**
     * clausola DROP CONSTRAINT per il comando ALTER TABLE
     */
    private String dropConstraint = " DROP CONSTRAINT ";


    /**
     * Costruttore completo.
     * <p/>
     * Non avendo modificatore, puo' essere invocato solo da
     * una classe interna al proprio package.<br>
     * Viene invocato da CreaIstanza.
     */
    DbSqlPostgres() {
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
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* regola il nome della classe driver */
        this.setNomeClasseDriver("org.postgresql.Driver");

        /* regola il subprotocollo jdbc */
        this.setSubprotocolloJDBC("postgresql");

        /* regola la porta di default */
        this.setPortaDefault(5432);

        /* regola il database di default */
        this.setNomeDbDefault("");

        /* regola l'utente di default */
        this.setLoginDefault("postgres");

        /* regola la password di default */
        this.setPasswordDefault("");

        /*
         * Regolazione finale dei dati del database prima della inizializzazione.
         * Deve essere invocato obbligatoriamente da ogni
         * sottoclasse concreta alla fine del metodo Inizia().
         */
        super.regolaDatiDb();


    }// fine del metodo inizia


    /**
     * Regola gli interpreti per il Db.
     * <p/>
     * Crea e assegna al Db le istanze delle classi che dovranno
     * trasformare gli oggetti in comandi Sql.<br>
     * Sovrascrive il metodo della superclasse.
     */
    protected void regolaInterpreti() {

        /* invoca il metodo della superclasse, che assegna interpreti di default */
        super.regolaInterpreti();

        /* sostituisce l'interprete per la struttura */
        this.setInterpreteStruct(new InterpreteStructPostgres(this));
    } // fine del metodo


    /**
     * Riempie la collezione dei tipi di dati per il Db.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     * <p/>
     * Per Postgres, sostituisce il tipo Testo generico con un tipo Testo specifico.
     * <p/>
     * Riempie la collezione dei tipi archivio con gli oggetti
     * TipoDati supportati dal database.<br>
     * - chiave: classe Java gestita dal tipo archivio
     * - valore: oggetto TipoDati che gestisce la classe
     */
    protected void creaTipiDati() {

        /* variabili e costanti locali di lavoro */
        Integer chiave = null;

        try { // prova ad eseguire il codice

            /* crea i tipi standard nella superclasse */
            super.creaTipiDati();

            /* sostituisce il tipo Testo nella collezione */
            chiave = new Integer(TipoDati.TIPO_TESTO);
            this.getTipiDati().put(chiave, new TipoDatiPostgresTesto(this));

            /* sostituisce il tipo Booleano nella collezione */
            chiave = new Integer(TipoDati.TIPO_BOOLEANO);
            this.getTipiDati().put(chiave, new TipoDatiPostgresBooleano(this));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public String getSetNotNull() {
        return setNotNull;
    }


    public String getDropNotNull() {
        return dropNotNull;
    }


    public String getDropConstraint() {
        return dropConstraint;
    }

}// fine della classe
