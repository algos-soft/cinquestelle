/**
 * Title:     Campi
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-ott-2004
 */

package it.algos.base.query.campi;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Contenitore dei campi per una Query.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-ott-2004 ore 12.43.53
 */
public final class Campi extends Object {

    /**
     * Query di riferimento
     */
    private Query query = null;

    /**
     * Mappa degli elementi campo
     * (oggetti di classe CampoQuery)
     * Chiave: chiave del campo
     * Valore: oggetto CampoQuery
     */
    private LinkedHashMap<String, CampoQuery> mappaElementi = null;


    /**
     * Costruttore completo
     * <p/>
     */
    public Campi(Query query) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setQuery(query);

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() {
        this.mappaElementi = new LinkedHashMap<String, CampoQuery>();
    } /* fine del metodo inizia */


    /**
     * Aggiunge un campo all'elenco.
     * <p/>
     *
     * @param campo il campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery add(Campo campo) {
        return this.addElemento(campo, null);
    } /* fine del metodo */


    /**
     * Aggiunge un campo all'elenco usando il nome.
     * <p/>
     *
     * @param nomeCampo il nome del campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery add(String nomeCampo) {
        return this.addElemento(nomeCampo, null);
    } /* fine del metodo */

    /**
     * Aggiunge un campo all'elenco usando l'elemento della Enum.
     * <p/>
     *
     * @param campo dalla Enum Campi
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery add(it.algos.base.wrapper.Campi campo) {
        String nomeCampo = campo.get();
        return this.add(nomeCampo);
    } /* fine del metodo */


    /**
     * Aggiunge un campo con valore all'elenco.
     * <p/>
     *
     * @param campo il campo
     * @param valore il valore per il campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery add(Campo campo, Object valore) {
        return this.addElemento(campo, valore);
    } /* fine del metodo */


    /**
     * Aggiunge un campo con valore all'elenco usando il nome.
     * <p/>
     *
     * @param nomeCampo il nome del campo
     * @param valore il valore per il campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery add(String nomeCampo, Object valore) {
        return this.addElemento(nomeCampo, valore);
    } /* fine del metodo */


    /**
     * Aggiunge un elemento CampoQuery all'elenco.
     * <p/>
     *
     * @param campo il campo da aggiungere
     * @param valore eventuale valore per il campo
     *
     * @return l'elemento aggiunto
     */
    private CampoQuery addElemento(Campo campo, Object valore) {
        /* variabili e costanti locali di lavoro */
        CampoQuery e = null;
        String chiave;

        try {    // prova ad eseguire il codice
            e = new CampoQuery();
            e.setCampo(campo);
            e.setValoreBl(valore);
            chiave = campo.getChiaveCampo();
            this.getMappaElementi().put(chiave, e);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return e;
    }


    /**
     * Aggiunge un elemento campo all'elenco.
     * <p/>
     *
     * @param nomeCampo il nome del campo da aggiungere
     * @param valore eventuale valore per il campo
     *
     * @return l'elemento aggiunto
     */
    private CampoQuery addElemento(String nomeCampo, Object valore) {
        /* variabili e costanti locali di lavoro */
        CampoQuery e = null;
        Modulo modulo;
        Campo campo;
        String chiave;

        try {    // prova ad eseguire il codice
            e = new CampoQuery();
            e.setNomeCampo(nomeCampo);
            e.setValoreBl(valore);

            modulo = this.getQuery().getModulo();
            campo = modulo.getCampo(nomeCampo);
            chiave = campo.getChiaveCampo();

            this.getMappaElementi().put(chiave, e);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return e;
    }


    /**
     * Risolve un questo oggetto Campi.
     * <p/>
     * Dopo la risoluzione tutti gli elementi fanno riferimento
     * a oggetti Campo e non piu' nomi di campo.<br>
     * I Campi vengono recuperati dal modulo fornito.
     *
     * @param modulo il modulo dal quale recuperare i campi
     */
    public void risolvi(Modulo modulo) {
        /** variabili e costanti locali di lavoro */
        ArrayList elementi = null;
        CampoQuery e = null;

        try {                                   // prova ad eseguire il codice

            /* recupera tutti gli elementi */
            elementi = this.getListaElementi();

            /* delega all'oggetto la risulozione di se stesso */
            for (int k = 0; k < elementi.size(); k++) {
                e = (CampoQuery)elementi.get(k);
                e.risolvi(modulo);
            } // fine del ciclo for

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Resetta l'oggetto.
     * <p/>
     * Svuota l'elenco degli elementi.
     */
    public void reset() {
        this.getMappaElementi().clear();
    }


    /**
     * Ritorna l'elenco dei campi.
     * <p/>
     *
     * @return una lista di oggetti Campo o nomi di campo
     */
    public ArrayList getCampi() {
        return getCampiValori(true, false);
    }


    /**
     * Ritorna l'elenco dei valori Memoria dei campi.
     * <p/>
     *
     * @return una lista di oggetti valore
     */
    public ArrayList getValori() {
        return getCampiValori(false, false);
    }


    /**
     * Ritorna l'elenco dei valori Archivio dei campi.
     * <p/>
     *
     * @return una lista di oggetti valore
     */
    public ArrayList getValoriArchivio() {
        return getCampiValori(false, true);
    }


    /**
     * Ritorna l'elenco dei campi o dei valori.
     * <p/>
     *
     * @param campi true per ottenere i Campi, false per ottenere i Valori
     * - se campi = true, una lista di oggetti Campo o nomi di campo
     * - se campi = false, una lista di valori
     * @param archivio significativo solo in caso di Valori
     * - true ritorna i valori a livello Archivio
     * - true ritorna i valori a livello Memoria
     *
     * @return una lista di oggetti
     */
    private ArrayList getCampiValori(boolean campi, boolean archivio) {
        /* variabili e costanti locali di lavoro */
        ArrayList oggetti = null;
        ArrayList elementi = null;
        CampoQuery e = null;
        Object o = null;

        try {    // prova ad eseguire il codice
            oggetti = new ArrayList();
            elementi = this.getListaElementi();
            for (int k = 0; k < elementi.size(); k++) {
                e = (CampoQuery)elementi.get(k);

                if (campi) {    // campi

                    if (e.getCampo() != null) {
                        o = e.getCampo();
                    } else {
                        o = e.getNomeCampo();
                    }// fine del blocco if-else

                } else {    // valori

                    if (archivio) {
                        o = e.getValoreDb();
                    } else {
                        o = e.getValoreBl();
                    }// fine del blocco if-else

                }// fine del blocco if-else

                /* aggiunge l'oggetto alla lista da ritornare */
                oggetti.add(o);

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return oggetti;
    }


    /**
     * Modifica il valore di un elemento.
     * <p/>
     *
     * @param campo dell'elemento
     * @param valore da assegnare
     */
    public void setValore(Campo campo, Object valore) {
        /* variabili e costanti locali di lavoro */
        String chiave;
        LinkedHashMap<String, CampoQuery> mappa;
        CampoQuery elemento;

        try {    // prova ad eseguire il codice
            mappa = this.getMappaElementi();
            chiave = campo.getChiaveCampo();
            elemento = mappa.get(chiave);
            elemento.setValoreBl(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    private Query getQuery() {
        return query;
    }


    private void setQuery(Query query) {
        this.query = query;
    }


    /**
     * Restituisce la lista degli elementi Campo.
     * <p/>
     *
     * @return un elenco contenente gli elementi Campo (oggetti CampoQuery)
     */
    public ArrayList<CampoQuery> getListaElementi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoQuery> lista = new ArrayList<CampoQuery>();
        LinkedHashMap<String, CampoQuery> mappa;

        try { // prova ad eseguire il codice
            mappa = this.getMappaElementi();
            for (CampoQuery cq : mappa.values()) {
                lista.add(cq);
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    } /* fine del metodo getter */


    /**
     * Restituisce la mappa degli elementi CampoQuery.
     * <p/>
     *
     * @return la mappa degli elementi CampoQuery
     */
    private LinkedHashMap<String, CampoQuery> getMappaElementi() {
        return this.mappaElementi;
    } /* fine del metodo getter */


}// fine della classe

