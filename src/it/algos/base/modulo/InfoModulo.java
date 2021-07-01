/**
 * Title:        QueryModulo.java
 * Package:      it.algos.base.query.modulo
 * Description:  Abstract Data Types per il record dati
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 maggio 2003 alle 18.07
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.modulo;

import it.algos.base.errore.Errore;
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.wrapper.EstrattoBase;

import java.util.ArrayList;

/**
 * Contenitore di informazioni in andata e in ritorno
 * per lo scambio di comandi e informazioni tra moduli
 * <p/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 maggio 2003 ore 18.07
 */
public final class InfoModulo {

    /** ==== Sezione flags ==== */

    /**
     * richiesta di una lista di valori di un campo
     */
    private boolean isRichiestaListaCampo = false;

    /**
     * richiesta di una lista di valori di un campo, piu codice
     */
    private boolean isRichiestaListaCampoConCodice = false;

    /**
     * richiesta di creazione di un nuovo record
     */
    private boolean isRichiestaNuovoRecord = false;

    /**
     * richiesta di eliminazione records
     */
    private boolean isRichiestaEliminaRecords = false;

    /**
     * richiesta del valore di un campo
     */
    private boolean isRichiestaValoreCampo = false;

    /**
     * richiesta del codice per il valore di un campo
     */
    private boolean isRichiestaCodice = false;

    /**
     * richiesta della vista pubblica (? - alex)
     */
    private boolean isRichiestaVistaPubblica = false;

    /**
     * richiesta di duplicazione di un record
     */
    private boolean isRichiestaDuplicaRecord = false;

    /**
     * richiesta di un estratto
     */
    private boolean isRichiestaEstratto = false;

    /** ==== Sezione variabili di lavoro comuni (generiche) ==== */

    /**
     * un nome interno di campo
     */
    private String nomeCampoLista = "";

    /**
     * un nome interno di campo ordine
     */
    private String nomeCampoOrdine = "";

    /**
     * una lista di nomi interni di campo
     */
    private ArrayList listaNomiCampi = null;

//    /** un filtroOld semplice di selezione records */
//    private FiltroSemplice unFiltro = null;

//    /** un pacchetto filtroOld di selezione records */
//    private FiltroComposto unPacchettoFiltro = null;


    /**
     * un pacchetto di campi di ordinamento
     */
    private Ordine unOrdine = null;


    /**
     * una lista di valori di campo
     */
    private ArrayList listaValoriCampo = null;

    /**
     * un numero di records
     */
    private int quantiRecords = 0;

    /**
     * una lista di oggetti QueryValore (coppie campo-lista valori)
     */
    private ArrayList listaQueryValore = null;

    /**
     * un nome interno di campo
     */
    private String nomeCampoValore = "";

    /**
     * un codice chiave di record
     */
    private int codiceChiave = 0;

    /**
     * un valore di campo
     */
    private Object valoreCampo = null;

    /**
     * un nome di vista pubblica (? - alex)
     */
    private String nomeVistaPubblica = "";

    /**
     * una vista pubblica (? - alex)
     */
    private ArrayList vistaPubblica = null;

    /**
     * matrice doppia per contenere una lista ed i suoi codici
     */
    private MatriceDoppia unaMatriceDoppia = null;

    /* estratto di dati di un record */
    private EstrattoBase estratto = null;

    /** ==== Sezione possibili valori di ritorno ==== */

    /**
     * un Intero
     */
    private int unRitornoIntero = 0;

    /**
     * un Testo
     */
    private String unRitornoTesto = null;

    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------


    /**
     * Costruttore completo senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public InfoModulo() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo */

    /** ==== Sezione regolazione flags ==== */

    /**
     * richiesta di una lista di valori di un campo
     */
    public void isRichiestaListaCampo(boolean isRichiestaListaCampo) {
        this.isRichiestaListaCampo = isRichiestaListaCampo;
    } /* fine del metodo setter */


    /**
     * richiesta di una lista di valori di un campo, piu codici
     */
    public void isRichiestaListaCampoConCodice(boolean isRichiestaListaCampoConCodice) {
        this.isRichiestaListaCampoConCodice = isRichiestaListaCampoConCodice;
    } /* fine del metodo setter */


    /**
     * richiesta di creazione di un nuovo record
     */
    public void setRichiestaNuovoRecord(boolean isRichiestaNuovoRecord) {
        this.isRichiestaNuovoRecord = isRichiestaNuovoRecord;
    } /* fine del metodo setter */


    /**
     * richiesta di eliminazione records
     */
    public void isRichiestaEliminaRecords(boolean isRichiestaEliminaRecords) {
        this.isRichiestaEliminaRecords = isRichiestaEliminaRecords;
    } /* fine del metodo setter */


    /**
     * richiesta del valore di un campo
     */
    public void isRichiestaValoreCampo(boolean isRichiestaValoreCampo) {
        this.isRichiestaValoreCampo = isRichiestaValoreCampo;
    } /* fine del metodo setter */


    /**
     * richiesta del codice per il valore di un campo
     */
    public void isRichiestaCodice(boolean isRichiestaCodice) {
        this.isRichiestaCodice = isRichiestaCodice;
    } /* fine del metodo setter */


    /**
     * richiesta della vista pubblica
     */
    public void isRichiestaVistaPubblica(boolean isRichiestaVistaPubblica) {
        this.isRichiestaVistaPubblica = isRichiestaVistaPubblica;
    } /* fine del metodo setter */


    /**
     * richiesta di duplicazione di un record
     */
    public void setRichiestaDuplicaRecord(boolean isRichiestaDuplicaRecord) {
        this.isRichiestaDuplicaRecord = isRichiestaDuplicaRecord;
    } /* fine del metodo setter */


    /**
     * richiesta di un estratto
     */
    public void setRichiestaEstratto(boolean richiestaEstratto) {
        isRichiestaEstratto = richiestaEstratto;
    }

    /** ==== Sezione regolazione variabili di lavoro comuni ==== */

    /**
     * un nome interno di campo
     */
    public void setNomeCampoLista(String nomeCampoLista) {
        this.nomeCampoLista = nomeCampoLista;
    } /* fine del metodo setter */


    /**
     * un nome interno di campo ordine
     */
    public void setNomeCampoOrdine(String nomeCampoOrdine) {
        this.nomeCampoOrdine = nomeCampoOrdine;
    } /* fine del metodo setter */


    /**
     * una lista di nomi interni di campo
     */
    public void setListaNomiCampo(ArrayList listaNomiCampi) {
        this.listaNomiCampi = listaNomiCampi;
    } /* fine del metodo setter */

//    /** un filtroOld di selezione records */
//    public void setFiltro(FiltroSemplice unFiltro) {
//        this.unFiltro = unFiltro;
//    } /* fine del metodo setter */

//    /** un pacchetto filtroOld di selezione records */
//    public void setPacchettoFiltro(FiltroComposto unPacchettoFiltro) {
//        this.unPacchettoFiltro = unPacchettoFiltro;
//    } /* fine del metodo setter */
//
//    /** un filtroOld completo di selezione records */
//    public void setFiltroSelezione(FiltroCompleto unFiltroSelezione) {
//        this.unFiltroSelezione = unFiltroSelezione;
//    } /* fine del metodo setter */




    /**
     * un pacchetto per l'ordinamento
     */
    public void setOrdine(Ordine unOrdine) {
        this.unOrdine = unOrdine;
    } /* fine del metodo setter */



    /**
     * una lista di valori di campo
     */
    public void setListaValoriCampo(ArrayList listaValoriCampo) {
        this.listaValoriCampo = listaValoriCampo;
    } /* fine del metodo setter */


    /**
     * un numero di records
     */
    public void setQuantiRecords(int quantiRecords) {
        this.quantiRecords = quantiRecords;
    } /* fine del metodo setter */


    /**
     * una lista di oggetti QueryValore (coppie campo-lista valori)
     */
    public void setListaQueryValore(ArrayList listaQueryValore) {
        this.listaQueryValore = listaQueryValore;
    } /* fine del metodo setter */


    /**
     * un nome interno di campo
     */
    public void setNomeCampoValore(String nomeCampoValore) {
        this.nomeCampoValore = nomeCampoValore;
    }


    /**
     * un codice chiave di record
     */
    public void setCodiceChiave(int codiceChiave) {
        this.codiceChiave = codiceChiave;
    }


    /**
     * un valore di campo
     */
    public void setValoreCampo(Object valoreCampo) {
        this.valoreCampo = valoreCampo;
    }


    /**
     * un nome di vista pubblica (? - alex)
     */
    public void setNomeVistaPubblica(String nomeVistaPubblica) {
        this.nomeVistaPubblica = nomeVistaPubblica;
    } /* fine del metodo setter */


    /**
     * una vista pubblica (? - alex)
     */
    public void setVistaPubblica(ArrayList vistaPubblica) {
        this.vistaPubblica = vistaPubblica;
    } /* fine del metodo setter */


    /**
     * un ritorno Intero
     */
    public void setRitornoIntero(int unRitornoIntero) {
        this.unRitornoIntero = unRitornoIntero;
    } /* fine del metodo setter */


    /**
     * un ritorno Testo
     */
    public void setRitornoTesto(String unRitornoTesto) {
        this.unRitornoTesto = unRitornoTesto;
    } /* fine del metodo setter */


    /**
     * matrice doppia per contenere una lista ed i suoi codici
     */
    public void setMatriceDoppia(MatriceDoppia unaMatriceDoppia) {
        this.unaMatriceDoppia = unaMatriceDoppia;
    } /* fine del metodo setter */


    /**
     * estratto di dati di un record
     */
    public void setEstratto(EstrattoBase estratto) {
        this.estratto = estratto;
    }

    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------

    /** ==== Sezione restituzione flags ==== */

    /**
     * richiesta di una lista di valori di un campo
     */
    public boolean isRichiestaListaCampo() {
        return this.isRichiestaListaCampo;
    } /* fine del metodo getter */


    /**
     * richiesta di una lista di valori di un campo, piu codici
     */
    public boolean isRichiestaListaCampoConCodice() {
        return this.isRichiestaListaCampoConCodice;
    } /* fine del metodo getter */


    /**
     * richiesta di creazione di un nuovo record
     */
    public boolean isRichiestaNuovoRecord() {
        return this.isRichiestaNuovoRecord;
    } /* fine del metodo getter */


    /**
     * richiesta di eliminazione records
     */
    public boolean isRichiestaEliminaRecords() {
        return this.isRichiestaEliminaRecords;
    } /* fine del metodo getter */


    /**
     * richiesta del valore di un campo
     */
    public boolean isRichiestaValoreCampo() {
        return this.isRichiestaValoreCampo;
    } /* fine del metodo getter */


    /**
     * richiesta del codice per il valore di un campo
     */
    public boolean isRichiestaCodice() {
        return this.isRichiestaCodice;
    } /* fine del metodo getter */


    /**
     * richiesta della vista pubblica
     */
    public boolean isRichiestaVistaPubblica() {
        return this.isRichiestaVistaPubblica;
    } /* fine del metodo getter */


    /**
     * richiesta di duplicazione di un record
     */
    public boolean isRichiestaDuplicaRecord() {
        return this.isRichiestaDuplicaRecord;
    } /* fine del metodo getter */


    /**
     * richiesta di un estratto
     */
    public boolean isRichiestaEstratto() {
        return isRichiestaEstratto;
    }

    /** ==== Sezione restituzione variabili di lavoro comuni ==== */

    /**
     * un nome interno di campo
     */
    public String getNomeCampoLista() {
        return this.nomeCampoLista;
    } /* fine del metodo getter */


    /**
     * un nome interno di campo ordine
     */
    public String getNomeCampoOrdine() {
        return this.nomeCampoOrdine;
    } /* fine del metodo getter */


    /**
     * una lista di nomi interni di campo
     */
    public ArrayList getListaNomiCampo() {
        return this.listaNomiCampi;
    } /* fine del metodo getter */

//    /** un filtroOld di selezione records */
//    public FiltroSemplice getFiltro() {
//        return this.unFiltro;
//    } /* fine del metodo getter */

//    /** un pacchetto filtroOld di selezione records */
//    public FiltroComposto getPacchettoFiltro() {
//        return this.unPacchettoFiltro;
//    } /* fine del metodo getter */
//
//    /** un filtroOld completo di selezione records */
//    public FiltroCompleto getFiltroSelezione() {
//        return this.unFiltroSelezione;
//    } /* fine del metodo getter */


    /**
     * un pacchetto per l'ordinamento
     */
    public Ordine getOrdine() {
        return this.unOrdine;
    } /* fine del metodo getter */


    /**
     * una lista di valori di campo
     */
    public ArrayList getListaValoriCampo() {
        return this.listaValoriCampo;
    } /* fine del metodo getter */


    /**
     * un numero di records
     */
    public int getQuantiRecords() {
        return this.quantiRecords;
    } /* fine del metodo getter */


    /**
     * una lista di oggetti QueryValore (coppie campo-lista valori)
     */
    public ArrayList getListaQueryValore() {
        return this.listaQueryValore;
    } /* fine del metodo getter */


    /**
     * un nome interno di campo
     */
    public String getNomeCampoValore() {
        return this.nomeCampoValore;
    } /* fine del metodo getter */


    /**
     * un codice chiave di record
     */
    public int getCodiceChiave() {
        return this.codiceChiave;
    } /* fine del metodo getter */


    /**
     * un valore di campo
     */
    public Object getValoreCampo() {
        return this.valoreCampo;
    } /* fine del metodo getter */


    /**
     * un nome di vista pubblica (? - alex)
     */
    public String getNomeVistaPubblica() {
        return this.nomeVistaPubblica;
    } /* fine del metodo getter */


    /**
     * una vista pubblica (? - alex)
     */
    public ArrayList getVistaPubblica() {
        return this.vistaPubblica;
    } /* fine del metodo getter */


    /**
     * un ritorno Intero
     */
    public int getRitornoIntero() {
        return this.unRitornoIntero;
    } /* fine del metodo getter */


    /**
     * un ritorno Testo
     */
    public String getRitornoTesto() {
        return this.unRitornoTesto;
    } /* fine del metodo getter */


    /**
     * matrice doppia per contenere una lista ed i suoi codici
     */
    public MatriceDoppia getMatriceDoppia() {
        return this.unaMatriceDoppia;
    } /* fine del metodo getter */


    /**
     * estratto di dati di un record
     */
    public EstrattoBase getEstratto() {
        return estratto;
    }


}