/**
 * Title:     Filtro
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-ott-2004
 */
package it.algos.base.query.filtro;

import it.algos.base.albero.AlberoModello;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.util.ArrayList;

/**
 * Implementazione di un filtro dati.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 15-ott-2004 ore 10.06
 */
public final class Filtro extends AlberoModello {

    /**
     * clausola di unione di default
     */
    private static final String UNIONE_DEFAULT = Operatore.AND;

    /**
     * nome per eventuale popup (facoltativo)
     */
    private String nome;


    /**
     * Costruttore base senza parametri
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public Filtro() {
        super();

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo.
     * <p/>
     * Costruisce un filtro contenente il filtro dato.
     *
     * @param filtro il filtro da aggiungere.
     */
    public Filtro(Filtro filtro) {

        super();

        try {    // prova ad eseguire il codice
            this.inizia();
            this.add(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Costruttore completo.
     * <p/>
     * Costruisce un filtro aggiungendo un nuovo filtro.
     *
     * @param campo l'oggetto Campo
     * @param op l'operatore (v. Operatore)
     * @param valore il valore
     */
    public Filtro(Campo campo, String op, Object valore) {

        super();

        try {    // prova ad eseguire il codice
            this.inizia();
            this.add(campo, op, valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Costruttore completo.
     * <p/>
     * Costruisce un filtro aggiungendo un nuovo filtro.
     *
     * @param nomeCampo il nome interno del campo
     * @param op l'operatore (v. Operatore)
     * @param valore il valore
     */
    public Filtro(String nomeCampo, String op, Object valore) {

        super();

        try {    // prova ad eseguire il codice
            this.inizia();
            this.add(nomeCampo, op, valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }



    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     */
    private void inizia() {
        /* crea il nodo Root del Filtro */
        this.creaRoot();
    }// fine del metodo inizia


    /**
     * Aggiunge il nodo Root a questo filtro.
     * <p/>
     */
    private void creaRoot() {
        /* variabili e costanti locali di lavoro */
        FiltroNodo nodo;
        FiltroNodoOggetto oggetto;

        try {    // prova ad eseguire il codice

            /* aggiunge il nodo root al filtro */
            nodo = new FiltroNodo();
            oggetto = new FiltroNodoOggetto();
            nodo.setUserObject(oggetto);
            this.addNodo(nodo, null);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un filtro a questo filtro, con una data clausola di unione
     * <p/>
     * Il filtro viene aggiunto solo se e' valido.
     *
     * @param unione la clausola di unione (v. Operatore)
     * @param filtro il filtro da aggiungere
     */
    public void add(String unione, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        FiltroNodo rootFiltroAggiunto = null;
        FiltroNodo rootClone = null;
        FiltroNodoOggetto oggettoClone = null;

        try {    // prova ad eseguire il codice

            /* controlla che il filtro sia valido */
            if (isFiltroValido(filtro)) {

                /* crea un clone del nodo root dell'albero da aggiungere
                 * il clone contiene il nodo root e tutti i rami sottostanti */
                rootFiltroAggiunto = filtro.getRootFiltro();
                rootClone = (FiltroNodo)rootFiltroAggiunto.clonaNodo();

                /* crea un clone dell'oggetto nodo e vi sostituisce
                 * la clausola di unione */
                oggettoClone = rootClone.getOggetto().clonaOggetto();
                oggettoClone.setUnione(unione);

                /* assegna l'oggetto clone al nodo clone */
                rootClone.setUserObject(oggettoClone);

                /* aggiunge il nodo clonato al nodo root di questo filtro */
                this.addNodo(rootClone, this.getRootFiltro());

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un filtro a questo filtro, con la clausola
     * di unione di default (AND)
     * <p/>
     *
     * @param filtro il filtro da aggiungere
     */
    public void add(Filtro filtro) {
        this.add(UNIONE_DEFAULT, filtro);
    }


    /**
     * Aggiunge un filtro a questo filtro.
     * <p/>
     * Usa l'oggetto Campo
     *
     * @param unione la clausola di unione (v. Operatore)
     * @param campo l'oggetto Campo
     * @param op l'operatore (v. Operatore)
     * @param valore il valore
     */
    public void add(String unione, Campo campo, String op, Object valore) {
        this.addNodo(unione, campo, op, valore);
    }


    /**
     * Aggiunge un filtro a questo filtro.
     * <p/>
     * Usa l'oggetto Campo
     * Usa la clausola di unione di default (AND).
     *
     * @param campo il campo
     * @param op l'operatore (v. Operatore)
     * @param valore il valore
     */
    public void add(Campo campo, String op, Object valore) {
        this.add(UNIONE_DEFAULT, campo, op, valore);
    }


    /**
     * Aggiunge un filtro a questo filtro.
     * <p/>
     * Usa il nome del Campo
     *
     * @param unione la clausola di unione (v. Operatore)
     * @param nomeCampo il nome del campo
     * @param op l'operatore (v. Operatore)
     * @param valore il valore
     */
    public void add(String unione, String nomeCampo, String op, Object valore) {
        this.addNodo(unione, nomeCampo, op, valore);
    }


    /**
     * Aggiunge un filtro a questo filtro.
     * <p/>
     * Usa il nome del Campo
     * Usa la clausola di unione di default (AND).
     *
     * @param nomeCampo il nome del campo
     * @param op l'operatore (v. Operatore)
     * @param valore il valore
     */
    public void add(String nomeCampo, String op, Object valore) {
        this.add(UNIONE_DEFAULT, nomeCampo, op, valore);
    }


    /**
     * Aggiunge un nodo finale al nodo root di questo filtro.
     * <p/>
     * L'opeazione e' possibile solo se Il nodo root
     * non ha children o tutti i children sono foglie.<br>
     * In questo modo sono sicuro che sotto a un nodo ci siano sempre,
     * in ordine, prima tutte le foglie poi tutti gli altri nodi.
     *
     * @param unione la clausola di unione (v. Operatore)
     * @param campo l'oggetto Campo (Campo) o il nome interno del campo (String)
     * @param op l'operatore (v. Operatore)
     * @param valore il valore
     */
    private void addNodo(String unione, Object campo, String op, Object valore) {
        /* variabili e costanti locali di lavoro */
        int quantiNonFoglia = 0;
        String t = "";

        try {    // prova ad eseguire il codice

            /* controlla che il nodo root non abbia children o tutti
             * i children siano foglie - in caso contrario e' un errore */
            quantiNonFoglia = this.getNodoRoot().getNonLeafChildrenCount();
            if (quantiNonFoglia > 0) {
                t += "Impossibile aggiungere filtri semplici perche'\n";
                t += "il filtro contiene gia' filtri composti.\n";
                t += "Aggiungere i filtri semplici prima di quelli composti.";
                throw new Exception(t);
            }// fine del blocco if

            /* crea un nuovo oggetto per il nodo */
            FiltroNodoOggetto oggettoNodo = new FiltroNodoOggetto();
            oggettoNodo.setUnione(unione);

            /* assegna Campo o nome campo a seconda della classe del parametro */
            if (campo instanceof Campo) {
                oggettoNodo.setCampo((Campo)campo);
            }// fine del blocco if
            if (campo instanceof String) {
                oggettoNodo.setNomeCampo((String)campo);
            }// fine del blocco if

            oggettoNodo.setOperatore(op);
            oggettoNodo.setValoreBl(valore);

            /* crea un nuovo nodo finale contenente l'oggetto */
            FiltroNodo nodo = new FiltroNodo(oggettoNodo);
            nodo.setAllowsChildren(false); // nodo finale

            /* aggiunge il nuovo nodo al nodo root di questo filtro */
            this.addNodo(nodo, this.getRootFiltro());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Regola il flag case-sensitive del filtro.
     * <p/>
     * Il flag viene regolato su tutti i filtri contenuti.
     * Se si vuole regolare per un solo filtro, occorre farlo
     * prima di aggiungerlo.
     * Questa opzione e' significativa solo per campi testo.
     *
     * @param flag true per attivare l'opzione caseSensitive
     */
    public void setCaseSensitive(boolean flag) {
        /* variabili e costanti locali di lavoro */
        ArrayList oggettiFinali = null;
        FiltroNodoOggetto oggettoNodo = null;

        try {    // prova ad eseguire il codice
            oggettiFinali = this.getOggettiFinali();
            for (int k = 0; k < oggettiFinali.size(); k++) {
                oggettoNodo = (FiltroNodoOggetto)oggettiFinali.get(k);
                oggettoNodo.setCaseSensitive(flag);
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il flag inverso dell'intero filtro.
     * <p/>
     * Il flag viene regolato sul nodo Root del filtro.
     *
     * @param flag true per attivare l'opzione inverso
     */
    public void setInverso(boolean flag) {
        this.getRootFiltro().getOggetto().setInverso(flag);
    }


    /**
     * Ritorna la lista degli oggetti finali del filtro.
     * <p/>
     *
     * @return la lista degli oggetti finali
     *         (oggetti di tipo FiltroNodoOggetto)
     */
    public ArrayList getOggettiFinali() {
        /* variabili e costanti locali di lavoro */
        ArrayList oggettiFinali = null;
        ArrayList foglieFinali = null;
        FiltroNodo nodo = null;
        FiltroNodoOggetto oggettoNodo = null;

        try {    // prova ad eseguire il codice
            oggettiFinali = new ArrayList();
            foglieFinali = this.getNodoRoot().getLeafChildren();
            for (int k = 0; k < foglieFinali.size(); k++) {
                nodo = (FiltroNodo)foglieFinali.get(k);
                if (nodo.isFinalLeaf()) {
                    oggettoNodo = nodo.getOggetto();
                    oggettiFinali.add(oggettoNodo);
                }// fine del blocco if

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return oggettiFinali;
    }


    /**
     * Risolve un filtro che usa nomi di campo in un
     * filtro che usa solo oggetti Campo.
     * <p/>
     * I Campi vengono recuperati da un dato modulo.
     *
     * @param modulo il modulo dal quale recuperare i campi
     */
    public void risolvi(Modulo modulo) {
        /** variabili e costanti locali di lavoro */
        ArrayList oggetti = null;
        FiltroNodoOggetto oggetto = null;

        try {                                   // prova ad eseguire il codice

            /* recupera tutti gli oggetti dei nodi
             * (l'ordine non e' rilevante)*/
            oggetti = this.getOggettiPreorder();

            /* delega all'oggetto la risoluzione di se stesso */
            for (int k = 0; k < oggetti.size(); k++) {
                oggetto = (FiltroNodoOggetto)oggetti.get(k);
                oggetto.risolvi(modulo);
            } // fine del ciclo for

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Riempie il valore Archivio degli oggetti.
     * <p/>
     * Converte il valore da livello Business Logic
     * a livello Database.
     * Il valore Business Logic non viene modificato.
     *
     * @param db il database a fronte del quale convertire i valori
     */
    public void bl2db(Db db) {
        /** variabili e costanti locali di lavoro */
        ArrayList oggetti = null;
        FiltroNodoOggetto oggetto = null;

        try {                                   // prova ad eseguire il codice

            /* recupera tutti gli oggetti dei nodi
             * (l'ordine non e' rilevante)*/
            oggetti = this.getOggettiPreorder();

            /* delega all'oggetto la conversione di se stesso */
            for (int k = 0; k < oggetti.size(); k++) {
                oggetto = (FiltroNodoOggetto)oggetti.get(k);
                oggetto.bl2db(db);
            } // fine del ciclo for

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    }


    /**
     * Ritorna un elenco di tutti i campi coinvoli in questo Filtro.
     * <p/>
     * L'elenco e' affidabile solo dopo che il filtro e' stato risolto.
     *
     * @return un elenco di oggetti Campo coinvolti nel filtro.
     */
    public ArrayList getCampi() {
        /* variabili e costanti locali di lavoro */
        ArrayList campi = null;
        ArrayList oggetti = null;
        Campo campo = null;
        FiltroNodoOggetto oggetto = null;

        try {    // prova ad eseguire il codice

            /* crea la lista per il risultato */
            campi = new ArrayList();

            /* recupera l'oggetto di tutti i nodi */
            oggetti = this.getOggettiPreorder();

            /* spazzola gli oggetti ed estrae i campi */
            for (int k = 0; k < oggetti.size(); k++) {
                oggetto = (FiltroNodoOggetto)oggetti.get(k);
                campo = oggetto.getCampo();
                if (campo != null) {
                    campi.add(campo);
                }// fine del blocco if

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Ritorna il numero di filtri finali di questo filtro.
     * <p/>
     * Il numero corrisponde al numero di foglie dell'albero che non
     * hanno e non possono avere figli.<br>
     * Ogni foglia e' un filtro finale (unione-campo-operatore-valore)<br>
     *
     * @return il numero di filtri finali.
     */
    public int getSize() {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        ArrayList nodi = null;
        AlberoNodo nodo = null;

        try { // prova ad eseguire il codice
            nodi = this.getNodi();
            for (int k = 0; k < nodi.size(); k++) {
                nodo = (AlberoNodo)nodi.get(k);
                if (nodo.isFinalLeaf()) {
                    quanti = quanti + 1;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Controlla se un filtro e' valido.
     * <p/>
     * Un filtro e' valido se non e' nullo o vuoto.
     *
     * @param filtro da controllare
     *
     * @return true se e' valido
     */
    private static boolean isFiltroValido(Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        boolean continua;

        try { // prova ad eseguire il codice
            continua = filtro != null;

            if (continua) {
                continua = filtro.getSize() > 0;
            }// fine del blocco if

            if (continua) {
                valido = true;
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla se questo filtro e' valido.
     * <p/>
     * Il filtro e' valido se non e' nullo o vuoto.
     *
     * @return true se e' valido
     */
    public boolean isValido() {
        return Filtro.isFiltroValido(this);
    }


    /**
     * Ritorna il nodo root di questo filtro.
     * <p/>
     * Recupera il nodo root dell'albero ed effettua
     * il casting a FiltroNodo.
     *
     * @return il nodo root del filtro.
     */
    public FiltroNodo getRootFiltro() {
        /* valore di ritorno */
        return (FiltroNodo)this.getNodoRoot();
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override public String toString() {
        return this.getNome();
    }


    /**
     * Classe 'interna'.
     */
    public interface Op {

        /**
         * codifica per la clausola di unione tra filtri di tipo AND
         */
        public static final String AND = Operatore.AND;

        /**
         * codifica per la clausola di unione tra filtri di tipo OR
         */
        public static final String OR = Operatore.OR;

        /**
         * codifica per la clausola di unione tra filtri di tipo AND NOT
         */
        public static final String AND_NOT = Operatore.AND_NOT;

        /* codifica operatori */

        /**
         * codifica per l'operatore UGUALE (=)
         */
        public static final String UGUALE = Operatore.UGUALE;

        /**
         * codifica per l'operatore DIVERSO (!=)
         */
        public static final String DIVERSO = Operatore.DIVERSO;

        /**
         * codifica per l'operatore MAGGIORE (>)
         */
        public static final String MAGGIORE = Operatore.MAGGIORE;

        /**
         * codifica per l'operatore MINORE (<)
         */
        public static final String MINORE = Operatore.MINORE;

        /**
         * codifica per l'operatore MAGGIORE UGUALE (>=)
         */
        public static final String MAGGIORE_UGUALE = Operatore.MAGGIORE_UGUALE;

        /**
         * codifica per l'operatore MINORE UGUALE (<=)
         */
        public static final String MINORE_UGUALE = Operatore.MINORE_UGUALE;

        /**
         * codifica per l'operatore COMINCIA CON
         */
        public static final String COMINCIA = Operatore.COMINCIA;

        /**
         * codifica per l'operatore FINISCE CON
         */
        public static final String FINISCE = Operatore.FINISCE;

        /**
         * codifica per l'operatore CONTIENE
         */
        public static final String CONTIENE = Operatore.CONTIENE;

        /**
         * codifica per l'operatore MASCHERA
         */
        public static final String MASCHERA = Operatore.MASCHERA;

        /**
         * codifica per l'operatore IN
         */
        public static final String IN = Operatore.IN;

    } // fine della classe 'interna'


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che possono essere lanciati dal campo <br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum OperatoreNew {

        /* qualsiasi cosa accade nel campo */
        uguale("uguale"),

        /* quando il campo prende il fuoco */
        maggiore("maggiore"),

        /* quando il campo perde il fuoco */
        minore("minore"),

        /* quando il campo perde il fuoco dopo una modifica  */
        diverso("diverso");


        /**
         * interfaccia listener per l'evento
         */
        private String nome;


        /**
         * /**
         * Costruttore completo con parametri.
         */
        OperatoreNew(String nome) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(nome);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        private String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


    }// fine della classe Enum


}// fine della classe

