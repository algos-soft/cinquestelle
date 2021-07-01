/**
 * Title:     CampoQuery
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-gen-2005
 */
package it.algos.base.query.campi;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;

import java.util.ArrayList;

/**
 * Classe interna rappresentante un campo di una Query
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 15-gen-2005 ore 15.15.08
 */
public class CampoQuery extends Object implements Cloneable {

    /**
     * Campo
     */
    private Campo campo = null;

    /**
     * Nome interno del campo<br>
     * Usato in alternativa all'oggetto Campo <br>
     * Viene risolto al momento dell'esecuzione della Query
     * usando il modulo della Query.<br>
     */
    private String nomeCampo = null;

    /**
     * Funzione da applicare al campo<br>
     * Classe Funzione da implementare in db...
     * La funzione deve poter contenere un elenco di funzioni,
     * un po' come il Filtro...
     * Le funzioni devono poter essere usate sia nei campi
     * che nei filtri...
     * campi della classe:
     * - Simbolo (String) il simbolo della funzione
     * - Aggregante (boolean) true se la funzione produce una aggregazione, false se e' scalare
     * (per controllo: se una colonna di una query contiene una funzione aggregante,
     * anche tutte le altre colonne devono avere una funzione aggregante
     * se no il DB da' un errore)
     * - TipoOutputDiverso (boolean) true se la funzione produce un
     * tipo dati diverso dall'input
     * - TipoDatiOutput (TipoDati) tipo dati del risultato della funzione
     * solo se diverso dal tipo di input
     * Funzioni aggreganti (un solo risultato per tutte le righe)
     * - COUNT -> tipo output numerico
     * - MIN   -> tipo output = tipo input
     * - MAX   -> tipo output = tipo input
     * - SUM   -> tipo output numerico
     * - AVG   -> tipo output numerico
     * <p/>
     * Funzioni scalari (un risultato per riga)
     * - LENGTH -> tipo output numerico
     * - UPPER  -> tipo output = tipo input
     * - LOWER  -> tipo output = tipo input
     * <p/>
     * Funzioni "speciali" (variano il numero righe risultato)
     * vedere come gestirle...
     * - DISTINCT -> tipo output = tipo input, puo' esserci una sola colonna con DISTINCT in una query
     * <p/>
     * <p/>
     * Elenco ordinato di codici chiave delle eventuale funzioni da
     * applicare a questo campo.<br>
     * Valori delle chiavi da interfaccia Funzione.<br>
     * Se presenti, le funzioni vengono applicate nell'ordine
     * inverso di inserimento.<br>
     * Es. se la lista ha 2 elementi, 1=SUM e 2=AVG, la funzione risultante
     * sara' AVG(SUM(CAMPO)).<br>
     * Quindi ogni funzione aggiunta usera' quelle gia'
     * presenti come argomento.
     */
//        private FunzioneDb funzione = null;

    /**
     * Elenco ordinato di codici chiave delle eventuale funzioni da
     * applicare a questo campo.<br>
     * Valori delle chiavi da interfaccia Funzione.<br>
     * Se presenti, le funzioni vengono applicate nell'ordine
     * inverso di inserimento.<br>
     * Es. se la lista ha 2 elementi, 1=SUM e 2=AVG, la funzione risultante
     * sara' AVG(SUM(CAMPO)).<br>
     * Quindi ogni funzione aggiunta usera' quelle gia'
     * presenti come argomento.
     */
    private ArrayList funzioni = null;

    /**
     * Valore a livello di di Business Logic del campo da registrare
     * Significativo solo per query di tipo modifica.
     */
    private Object valoreBl = null;

    /**
     * Valore a livello di database del campo da registrare
     * Significativo solo per query di tipo modifica.
     * Riempito al momento della esecuzione della Query.
     * Non convertiamo direttamente il valore Memoria
     * perche' la query non sarebbe piu' riutilizzabile.
     */
    private Object valoreDb = null;


    /**
     * Costruttore completo.
     * <p/>
     */
    public CampoQuery() {
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() {
        try {    // prova ad eseguire il codice
            this.funzioni = new ArrayList();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge una funzione all'elenco funzioni di questo campo.
     * <p/>
     *
     * @param funzione il codice chiave della funzione da aggiungere
     * (da interfaccia Funzione)
     */
    public void addFunzione(String funzione) {
        try {    // prova ad eseguire il codice
            this.funzioni.add(funzione);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


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
     * Converte il valore del campo da livello Business Logic
     * a livello Database.
     * <p/>
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
//
//    /**
//     * Converte i valori del campo da livello Memoria a livello Archivio
//     * <p/>
//     */
//    public void memoriaArchivio() {
//        /** variabili e costanti locali di lavoro */
//        Campo campo = null;
//
//        try { // prova ad eseguire il codice
//
//            campo = this.getCampo();
//            if (campo != null) {
//                campo.setMemoria(this.getValoreBl());
//                campo.getCampoDati().memoriaArchivio();
//                this.setValoreBl(campo.getCampoDati().getArchivio());
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     */
    public CampoQuery clona() {
        /** variabili e costanti locali di lavoro */
        CampoQuery e = null;

        try {    // prova ad eseguire il codice

            /** invoca il metodo sovrascritto della superclasse Object */
            e = (CampoQuery)super.clone();

        } catch (CloneNotSupportedException unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
            throw new InternalError();
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return e;

    } /* fine del metodo */


    public Campo getCampo() {
        return campo;
    }


    public void setCampo(Campo campo) {
        this.campo = campo;
    }


    public String getNomeCampo() {
        return nomeCampo;
    }


    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }


    public ArrayList getFunzioni() {
        return funzioni;
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

} /* fine della classe */
