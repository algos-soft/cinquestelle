/**
 * Title:     TipoDocumentoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      4-mag-2004
 */
package it.algos.albergo.clientealbergo.tabelle.tipodocumento;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;

import java.util.Date;

/**
 * TipoDocumentoModulo - Contenitore dei riferimenti agli oggetti del package.
 * <br>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 4-mag-2004 ore 9.11.02
 */
public final class TipoDocumentoModulo extends ModuloBase implements TipoDocumento {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = TipoDocumento.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = TipoDocumento.TITOLO_FINESTRA;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public TipoDocumentoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public TipoDocumentoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        this(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Costruttore completo <br>
     *
     * @param unNomeModulo nome interno del modulo
     * @param unNodo nodo dell'albero moduli
     */
    public TipoDocumentoModulo(String unNomeModulo, AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(unNomeModulo, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }/* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try {    // prova ad eseguire il codice
            /* selezione del modello (obbligatorio) */
            super.setModello(new TipoDocumentoModello());

            /* flag - (facoltativo) - le tabelle usano finestre piu' piccole */
            super.setTabella(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Ritorna la data di scadenza per un certo tipo di
     * documento con una certa data di emissione.
     * <p/>
     *
     * @param codTipoDoc l'id del tipo di documento
     * @param dataEmissione la data di emissione
     *
     * @return la data di scadenza, null se non calcolabile
     */
    public static Date getDataScadenza(int codTipoDoc, Date dataEmissione) {
        /* variabili e costanti locali di lavoro */
        Date dataScadenza = null;
        boolean continua;
        int giorno;
        int mese;
        int anno;
        int anniValidita;
        int annoScadenza;
        Modulo mod;

        try {    // prova ad eseguire il codice
            continua = (codTipoDoc != 0) && (dataEmissione != null);

            if (continua) {
                giorno = Lib.Data.getNumeroGiorno(dataEmissione);
                mese = Lib.Data.getNumeroMese(dataEmissione);
                anno = Lib.Data.getAnno(dataEmissione);

                mod = TipoDocumentoModulo.get();
                anniValidita = mod.query().valoreInt(Cam.validoAnni.get(), codTipoDoc);

                annoScadenza = anno + anniValidita;
                dataScadenza = Lib.Data.creaData(giorno, mese, annoScadenza);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dataScadenza;
    }


    /**
     * Ritorna l'autorità emittente per un certo tipo di documento.
     * <p/>
     *
     * @param codTipoDoc l'id del tipo di documento
     *
     * @return l'id dell'autorità emitetnte, 0 se non associata in tabella
     */
    public static int getAutoritaEmittente(int codTipoDoc) {
        /* variabili e costanti locali di lavoro */
        int autorita = 0;
        boolean continua;
        Modulo mod;

        try {    // prova ad eseguire il codice
            continua = (codTipoDoc != 0);
            if (continua) {
                mod = TipoDocumentoModulo.get();
                autorita = mod.query().valoreInt(Cam.autorita.get(), codTipoDoc);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return autorita;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static TipoDocumentoModulo get() {
        return (TipoDocumentoModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new TipoDocumentoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main


}// fine della classe
