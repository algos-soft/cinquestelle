/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-nov-2007
 */
package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CDBLinkato;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.scheda.Scheda;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;

/**
 * Logica del campo Estratto Link
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-nov-2007 ore 14.45.41
 */
public class CLEstrattoEsterno extends CLEstratto {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLEstrattoEsterno(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Regolazioni di avvio.
     * <p/>
     * Se il campo è in una scheda, prende il codice
     * della scheda e lo usa come riferimento al record esterno
     * (lo mette in memoria del campo)
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;
        int codice;
        Campo parente;
        boolean continua;

        try { // prova ad eseguire il codice
            super.avvia();

            parente = this.getCampoParente();
            scheda = parente.getScheda();
            continua = scheda != null;
            if (continua) {
                codice = scheda.getCodice();
                this.setRifEsterno(codice);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Ritorna il codice del record esterno per il quale recuperare l'estratto.
     * <p/>
     * Per l'estratto link, cerca il record linkato nel modulo esterno
     *
     * @param codRecordInterno il codice interno del record interno mantenuto dal campo
     *
     * @return il codice del record esterno
     */
    protected int getCodRecordEsterno(int codRecordInterno) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua = true;
        String nomeCampoLink = "";
        CDBLinkato campoDB = null;
        Modulo modulo = null;

        try { // prova ad eseguire il codice

            /* se il codice del record interno è zero, ritorna zero */
            continua = (codRecordInterno > 0);

            /* recupera il campo DB */
            if (continua) {
                campoDB = (CDBLinkato)this.getCampoParente().getCampoDB();
                continua = (campoDB != null);
            }// fine del blocco if

            /* recupera il nome del campo linkato */
            if (continua) {
                nomeCampoLink = campoDB.getNomeCampoValoriLinkato();
                continua = Lib.Testo.isValida(nomeCampoLink);
            }// fine del blocco if

            /* recupera il modulo esterno */
            if (continua) {
                modulo = this.getModuloEsterno();
            }// fine del blocco if

            /* recupera il codice del record linkato */
            if (continua) {
                codice = modulo.query().valoreChiave(nomeCampoLink,
                        codRecordInterno,
                        this.getConnessione());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Crea un nuovo record sul modulo esterno.
     * <p/>
     * Inserisce il link al campo
     *
     * @param modulo esterno sul quale creare il record
     * @param conn la connessione da utilizzare
     *
     * @return il codice del record creato, <=0 se non creato
     */
    protected int creaRecordEsterno(Modulo modulo, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua;
        ArrayList<CampoValore> valori;
        CampoValore cv;
        Campo campo;
        int rifEsterno = 0;

        try { // prova ad eseguire il codice

            campo = this.getCampoLinkEsterno();
            continua = campo != null;

            /* recupera il numero di riferimento per il record esterno
             * dalla memoria del campo */
            if (continua) {
                rifEsterno = this.getRifEsterno();
                continua = (rifEsterno != 0);
            }// fine del blocco if

            /* crea il nuovo record già linkato */
            if (continua) {
                cv = new CampoValore(campo, rifEsterno);
                valori = new ArrayList<CampoValore>();
                valori.add(cv);
                codice = modulo.query().nuovoRecord(valori, conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Ritorna il campo link del modulo esterno.
     * <p/>
     *
     * @return il campo link del modulo esterno
     */
    private Campo getCampoLinkEsterno() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        boolean continua;
        String nome;
        Modulo modulo = null;
        CDBLinkato cdb;

        try {    // prova ad eseguire il codice

            cdb = (CDBLinkato)this.getCampoParente().getCampoDB();

            nome = cdb.getNomeCampoValoriLinkato();
            continua = Lib.Testo.isValida(nome);

            if (continua) {
                modulo = this.getModuloEsterno();
                continua = (modulo != null);
            }// fine del blocco if

            if (continua) {
                campo = modulo.getCampo(nome);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


}// fine della classe