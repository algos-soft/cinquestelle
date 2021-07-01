/**
 * Title:     Ordine
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-nov-2004
 */

package it.algos.base.query.ordine;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;

import java.util.ArrayList;

/**
 * Implementazione di un ordinamento di campi.
 * <p/>
 * Un ordinamento e' composto da un elenco ordinato di campi,
 * ognuno con le proprie caratteristiche di ordinamento:<br>
 * - verso di ordinamento<br>
 * - flag case-sensitive se testo<br>
 * <p/>
 * La classe interna Elemento mantiene il riferimento al campo
 * (o al nome del campo) e le caratteristiche di ordinamento.
 * <p/>
 * Questa classe mantiene una lista ordinata di oggetti di classe Elemento.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-nov-2004 ore 8.47.39
 */
public final class Ordine implements Cloneable {

    /**
     * Elenco degli elementi di ordinamento
     * (oggetti di classe interna Elemento)
     */
    private ArrayList elementi = null;

    /**
     * Operatore di default (Ascendente)
     */
    private String operatoreDefault = null;

    /**
     * Flag case-sensitive di default (false)
     */
    private boolean caseSensitiveDefault = false;


    /**
     * Costruttore completo
     * <p/>
     */
    public Ordine() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param campo di ordinamento
     */
    public Ordine(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
            this.add(campo);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() {
        try { // prova ad eseguire il codice
            this.elementi = new ArrayList();
            this.operatoreDefault = Operatore.ASCENDENTE;
            this.caseSensitiveDefault = false;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param campo il campo
     * @param operatore l'operatore di ordinamento (v. interfaccia Operatore)
     * @param caseSensitive flag case-sensitive
     */
    public void add(Campo campo, String operatore, boolean caseSensitive) {
        this.addOrdine(campo, operatore, caseSensitive);
    } /* fine del metodo */


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param campo il campo
     * @param caseSensitive flag case-sensitive
     * Usa l'operatore di default (Ascendente)
     */
    public void add(Campo campo, boolean caseSensitive) {
        this.addOrdine(campo, this.getOpDefault(), caseSensitive);
    } /* fine del metodo */


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param campo il campo
     * @param operatore l'operatore di ordinamento (v. interfaccia Operatore)
     * Usa il flag case-sensitive di default (false)
     */
    public void add(Campo campo, String operatore) {
        this.addOrdine(campo, operatore, this.getCaseDefault());
    } /* fine del metodo */


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param campo il campo
     * Usa l'operatore di default (Ascendente)
     * Usa il flag case-sensitive di default (false)
     */
    public void add(Campo campo) {
        this.addOrdine(campo, this.getOpDefault(), this.getCaseDefault());
    } /* fine del metodo */


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     * @param operatore l'operatore di ordinamento (v. interfaccia Operatore)
     * @param caseSensitive flag case-sensitive
     */
    public void add(String nomeCampo, String operatore, boolean caseSensitive) {
        this.addOrdine(nomeCampo, operatore, caseSensitive);
    } /* fine del metodo */


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     * @param caseSensitive flag case-sensitive
     * Usa l'operatore di default (Ascendente)
     */
    public void add(String nomeCampo, boolean caseSensitive) {
        this.addOrdine(nomeCampo, this.getOpDefault(), caseSensitive);
    } /* fine del metodo */


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     * @param operatore l'operatore di ordinamento (v. interfaccia Operatore)
     * Usa il flag case-sensitive di default (false)
     */
    public void add(String nomeCampo, String operatore) {
        this.addOrdine(nomeCampo, operatore, this.getCaseDefault());
    } /* fine del metodo */


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     * Usa l'operatore di default (Ascendente)
     * Usa il flag case-sensitive di default (false)
     */
    public void add(String nomeCampo) {
        this.addOrdine(nomeCampo, this.getOpDefault(), this.getCaseDefault());
    } /* fine del metodo */


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param campo il campo
     * @param operatore l'operatore di ordinamento (v. interfaccia Operatore)
     * @param caseSensitive flag case-sensitive
     */
    private void addOrdine(Campo campo, String operatore, boolean caseSensitive) {
        /* variabili e costanti locali di lavoro */
        Elemento e = null;

        try {    // prova ad eseguire il codice
            e = new Elemento();
            e.setCampo(campo);
            e.setOperatore(operatore);
            e.setCaseSensitive(caseSensitive);
            this.getElementi().add(e);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un ordine all'elenco.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     * @param operatore l'operatore di ordinamento (v. interfaccia Operatore)
     * @param caseSensitive flag case-sensitive
     */
    private void addOrdine(String nomeCampo, String operatore, boolean caseSensitive) {
        /* variabili e costanti locali di lavoro */
        Elemento e = null;

        try {    // prova ad eseguire il codice
            e = new Elemento();
            e.setNomeCampo(nomeCampo);
            e.setOperatore(operatore);
            e.setCaseSensitive(caseSensitive);
            this.getElementi().add(e);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il numero di elementi contenuti in questo Ordine.
     * <p/>
     *
     * @return il numero di elementi contenuti
     */
    public int getSize() {
        return this.getElementi().size();
    } /* fine del metodo */


    /**
     * Pulisce l'ordine.
     * <p/>
     * Svuota l'elenco degli Elementi
     */
    public void reset() {
        this.getElementi().clear();
    } /* fine del metodo */


    /**
     * Ritorna un l'elemento dell'Ordine corrispondente all'indice dato.
     * <p/>
     *
     * @param indice l'indice dell'elemento richiesto (0 per il primo)
     *
     * @return l'elemento richiesto
     */
    public Elemento getElemento(int indice) {
        /* variabili e costanti locali di lavoro */
        Elemento e = null;
        int i = 0;

        try {    // prova ad eseguire il codice
            if (indice >= 0) {
                if (indice < this.getSize()) {
                    e = (Elemento)this.getElementi().get(i);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return e;
    }


    /**
     * Risolve un Ordine che usa nomi di campo in un
     * Ordine che usa solo oggetti Campo.
     * <p/>
     * I Campi vengono recuperati da un dato modulo.
     *
     * @param modulo il modulo dal quale recuperare i campi
     */
    public void risolvi(Modulo modulo) {
        /** variabili e costanti locali di lavoro */
        ArrayList elementi = null;
        Elemento e = null;

        try {                                   // prova ad eseguire il codice

            /* recupera tutti gli elementi dell'Ordine */
            elementi = this.getElementi();

            /* delega all'oggetto la risulozione di se stesso */
            for (int k = 0; k < elementi.size(); k++) {
                e = (Elemento)elementi.get(k);
                e.risolvi(modulo);
            } // fine del ciclo for

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Restituisce l'elenco dei campi ordine contenuti in questo Ordine.
     * <p/>
     * L'elenco e' affidabile solo dopo che l'Ordine e' stato risolto.
     *
     * @return una lista di oggetti Campo
     */
    public ArrayList getCampi() {
        /** variabili e costanti locali di lavoro */
        ArrayList unaListaCampi = new ArrayList();
        ArrayList elementi = null;
        Elemento e = null;
        Campo campo = null;

        try {    // prova ad eseguire il codice

            /** recupero l'elenco degli elementi ordine */
            elementi = this.getElementi();

            /** spazzola il pacchetto di oggetty QueryOrdine */
            for (int k = 0; k < elementi.size(); k++) {

                /** estrae il singolo oggetto QueryOrdine */
                e = (Elemento)elementi.get(k);
                /** estrae il campo dall'oggetto QueryOrdine */
                campo = e.getCampo();

                /** aggiunge il campo alla lista da ritornare */
                if (campo != null) {
                    unaListaCampi.add(campo);
                }// fine del blocco if

            } /* fine del blocco for */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return unaListaCampi;
    } /* fine del metodo getter */


    public ArrayList getElementi() {
        return elementi;
    }


    private void setElementi(ArrayList elementi) {
        this.elementi = elementi;
    }


    private String getOpDefault() {
        return operatoreDefault;
    }


    private boolean getCaseDefault() {
        return caseSensitiveDefault;
    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting.
     * <p/>
     */
    public Ordine clona() {
        /** variabili e costanti locali di lavoro */
        Ordine unOrdine = null;
        ArrayList elencoElementiOriginale = null;
        ArrayList elencoElementiClonato = null;
        Elemento eOriginale = null;
        Elemento eClonato = null;

        try {    // prova ad eseguire il codice

            /** invoca il metodo sovrascritto della superclasse Object */
            unOrdine = (Ordine)super.clone();

            /** clona gli Elementi */
            elencoElementiOriginale = this.getElementi();
            elencoElementiClonato = new ArrayList();
            for (int k = 0; k < elencoElementiOriginale.size(); k++) {

                eOriginale = (Elemento)elencoElementiOriginale.get(k);

                eClonato = eOriginale.clona();

                elencoElementiClonato.add(eClonato);
            } /* fine del blocco for */

            /* assegna gli elementi clonati all'Ordine clonato */
            unOrdine.setElementi(elencoElementiClonato);

        } catch (CloneNotSupportedException unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
            throw new InternalError();
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unOrdine;

    } /* fine del metodo */


    /**
     * Classe interna rappresentante un elemento di un Ordine
     * <p/>
     */
    public class Elemento extends Object implements Cloneable {

        /**
         * Campo di ordinamento
         */
        private Campo campo = null;

        /**
         * Nome interno del campo di ordinamento <br>
         * Usato in alternativa all'oggetto Campo <br>
         * Viene risolto al momento dell'esecuzione della Query
         * usando il modulo della Query.<br>
         */
        private String nomeCampo = null;

        /**
         * Codice operatore generico di ordinamento
         * (costanti ASC o DESC in interfaccia Operatore)
         */
        private String operatore = "";


        /**
         * Flag - true per ordinamento case-sensitive
         * Significativo solo per campi di tipo testo.
         * Di default e' false (ordinamento case-insensitive)
         */
        private boolean caseSensitive = false;


        /**
         * Costruttore completo.
         * <p/>
         */
        public Elemento() {
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
         * Ritorna una copia profonda dell'oggetto (deep copy) col casting
         */
        public Elemento clona() {
            /** variabili e costanti locali di lavoro */
            Elemento e = null;

            try {    // prova ad eseguire il codice

                /** invoca il metodo sovrascritto della superclasse Object */
                e = (Elemento)super.clone();

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


        public String getOperatore() {
            return operatore;
        }


        public void setOperatore(String operatore) {
            this.operatore = operatore;
        }


        public boolean isCaseSensitive() {
            return caseSensitive;
        }


        public void setCaseSensitive(boolean caseSensitive) {
            this.caseSensitive = caseSensitive;
        }

    } /* fine della classe interna */

    //-------------------------------------------------------------------------
}// fine della classe
//-----------------------------------------------------------------------------

