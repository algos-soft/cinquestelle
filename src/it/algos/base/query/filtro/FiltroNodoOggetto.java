/**
 * Title:     FiltroNodoOggetto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-ott-2004
 */
package it.algos.base.query.filtro;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;

/**
 * Contenuto di un nodo per l'albero filtri.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 15-ott-2004 ore 18.15.00
 */
public final class FiltroNodoOggetto extends Object implements Cloneable {

    /**
     * Clausola di unione con un eventuale filtro precedente<br>
     * (usa le costanti in interfaccia Operatore)
     */
    private String unione = "";

    /**
     * oggetto campo
     */
    private Campo campo = null;

    /**
     * Codice dell'operatore generico<br>
     * (usa le costanti in interfaccia Operatore)
     */
    private String operatore = "";

    /**
     * valore a livello di Business Logic per il filtro
     */
    private Object valoreBl = null;

    /**
     * valore a livello di Database per il filtro
     * Riempito al momento della esecuzione della Query.
     * Non convertiamo direttamente il valore Memoria
     * perche' il filtro non sarebbe piu' riutilizzabile.
     */
    private Object valoreDb = null;

    /**
     * Flag case sensitive (solo per operatori di testo)<br>
     * di default non e' case-sensitive
     */
    private boolean caseSensitive = false;

    /**
     * Flag - se attivato, inverte il risultato della ricerca
     */
    private boolean inverso = false;

    /**
     * Nome interno del campo<br>
     * da usare in alternativa all'oggetto campo<br>
     * risolto in fase di esecuzione della query
     * usando il modulo della query
     */
    private String nomeCampo = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public FiltroNodoOggetto() {
        /* rimanda al costruttore di questa classe */
    }// fine del metodo costruttore base


    /**
     * Risolve un oggetto che usa il nome di campo in un
     * oggetto che usa oggetto Campo.
     * <p/>
     * Il Campo viene recuperato da un dato modulo.
     *
     * @param modulo il modulo dal quale recuperare il campo
     */
    public void risolvi(Modulo modulo) {
        /** variabili e costanti locali di lavoro */
        Modello unModello = null;
        String unNomeCampo = null;
        Campo unCampo = null;

        try {                                   // prova ad eseguire il codice

            // Esegue solo se si tratta di un oggetto che usa il nome
            unNomeCampo = this.getNomeCampo();
            if (Lib.Testo.isValida(unNomeCampo)) {
                unModello = modulo.getModello();
                unNomeCampo = this.getNomeCampo();
                unCampo = unModello.getCampo(unNomeCampo);
                if (unCampo != null) {
                    this.setCampo(unCampo);
                    this.setNomeCampo(null); // quando risolto, elimina il nome
                } else {
                    throw new Exception("Campo " + unNomeCampo + " non trovato.");
                } /* fine del blocco if-else */
            } /* fine del blocco if */

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Riempie il valore Database dell'oggetto.
     * <p/>
     * Converte il valore da livello Business Logic a livello Database.
     *
     * @param db il database a fronte del quale convertire il valore
     */
    public void bl2db(Db db) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Object valoreBl = null;
        Object valoreDb = null;

        try {    // prova ad eseguire il codice
            campo = this.getCampo();
            if (campo != null) {
                valoreBl = this.getValoreBl();
                valoreDb = campo.bl2db(valoreBl, db);
                this.setValoreDb(valoreDb);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public String getUnione() {
        return unione;
    }


    public void setUnione(String unione) {
        this.unione = unione;
    }


    public Campo getCampo() {
        return campo;
    }


    public void setCampo(Campo campo) {
        this.campo = campo;
    }


    public String getOperatore() {
        return operatore;
    }


    public void setOperatore(String operatore) {
        this.operatore = operatore;
    }


    public Object getValoreBl() {
        return valoreBl;
    }


    public void setValoreBl(Object valore) {
        this.valoreBl = valore;
    }


    public Object getValoreDb() {
        return valoreDb;
    }


    private void setValoreDb(Object valoreDb) {
        this.valoreDb = valoreDb;
    }


    public boolean isCaseSensitive() {
        return caseSensitive;
    }


    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }


    public boolean isInverso() {
        return inverso;
    }


    public void setInverso(boolean inverso) {
        this.inverso = inverso;
    }


    public String getNomeCampo() {
        return nomeCampo;
    }


    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }


    /**
     * Ritorna una stringa rappresentante questo oggetto.
     * <p/>
     *
     * @return una stringa rappresentante il oggetto.
     */
    public String toString() {
        String stringa = "";

        try { // prova ad eseguire il codice

//            stringa += this.getUnione();

            if (this.getCampo() != null) {
                stringa += this.getCampo().getNomeInterno();
            } else {
                stringa += this.getNomeCampo();
            }// fine del blocco if-else

            stringa += this.getOperatore();

            if (this.getValoreBl() != null) {
                stringa += this.getValoreBl().toString();
            } else {
                stringa += "NULL";
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy).
     * <p/>
     * Clona questo oggetto.
     */
    public FiltroNodoOggetto clonaOggetto() {
        /* variabili e costanti locali di lavoro */
        FiltroNodoOggetto oggettoClone = null;

        try {    // prova ad eseguire il codice

            /* Clona questo nodo nella superclasse.
             * Viene creato un nodo nuovo, con riferimento
             * allo stesso User Object del nodo originale,
             * ma senza nodi Parent ne' Children*/
            oggettoClone = (FiltroNodoOggetto)super.clone();

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return oggettoClone;

    } /* fine del metodo */


}// fine della classe
