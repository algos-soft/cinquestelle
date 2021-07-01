/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-nov-2007
 */
package it.algos.albergo.clientealbergo;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.logica.CLEstrattoInterno;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.gestione.indirizzo.Indirizzo;

/**
 * Logica del campo Estratto Link
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 21-nov-2007 ore 14.45.41
 */
public final class CLIndirizzo extends CLEstrattoInterno {

    /* codice del cliente corrente */
    private int codCliente;

    /* codice del capogruppo corrente, 0 se è un capogruppo */
    private int codCapoGruppo;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLIndirizzo(Campo unCampoParente) {
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

        try { // prova ad eseguire il codice
            this.setUsaNuovoModifica(false);   // usa bottoni nuovo e modifica separati
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di avvio.
     * <p/>
     * Se il campo è in una scheda, prende il codice
     * della scheda e lo usa come riferimento al record esterno
     * (lo mette in memoria del campo)
     */
    @Override public void avvia() {
        super.avvia();
    } /* fine del metodo */


    /**
     * Creazione di un record esterno sulla tavola Indirizzi
     * <p>
     * Oltre alla creazione standard, assegna anche il codice cliente
     * al campo  indirizzo.linkanagrafica.
     * In tal modo l'indirizzo può essere usato anche normalmente
     * (es. da Rubrica)
     */
    @Override
    protected int creaRecordEsterno(Modulo modulo, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codRecord=0;

        try { // prova ad eseguire il codice
            codRecord = super.creaRecordEsterno(modulo,conn);
            this.getModuloEsterno().query().registra(codRecord, Indirizzo.Cam.anagrafica.get(), this.getCodCliente());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codRecord;

    }


    /**
     * Ritorna il codice del record esterno per il quale recuperare l'estratto.
     * <p/>
     *
     * @param codInterno il codice interno del record interno mantenuto dal campo
     * (il codice dell'indirizzo)
     *
     * @return il codice del record esterno
     */
    protected int getCodRecordEsterno(int codInterno) {
        /* variabili e costanti locali di lavoro */
        int codEsterno = 0;
        int codCapo;
        Modulo modulo;
        Campo campoParente;

        try { // prova ad eseguire il codice

            /* normale codice dell'indirizzo del cliente */
            codEsterno = super.getCodRecordEsterno(codInterno);

            /**
             * se non è il capogruppo e non ha un indirizzo proprio,
             * usa l'indirizzo del capogruppo 
             */
            if (!this.isCapoGruppo()) {
                if (!this.isEsisteIndirizzo(codEsterno)) {
                    codCapo = this.getCodCapoGruppo();
                    campoParente = this.getCampoParente();
                    modulo = campoParente.getModulo();
                    codEsterno = modulo.query().valoreInt(campoParente,
                            codCapo,
                            this.getConnessione());
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codEsterno;
    }


    /**
     * Controlla se esiste un indirizzo linkato a un dato cliente
     * <p/>
     *
     * @param codice del cliente
     *
     * @return true se esiste un indirizzo linkato al cliente
     */
    private boolean isEsisteIndirizzo(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Modulo mod;
        Connessione conn;
        String nomeCampo;

        try { // prova ad eseguire il codice

            mod = this.getModuloEsterno();
            nomeCampo = this.getCampoParente().getCampoDB().getNomeCampoValoriLinkato();
            conn = this.getConnessione();
            esiste = mod.query().isEsisteRecord(nomeCampo, codice, conn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Determina se l'anagrafica corrente è Capogruppo
     * <p/>
     *
     * @return true se è capogruppo
     */
    private boolean isCapoGruppo() {
        /* variabili e costanti locali di lavoro */
        boolean capoGruppo = false;

        try { // prova ad eseguire il codice
            if (this.getCodCapoGruppo() == 0) {
                capoGruppo = true;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return capoGruppo;
    }


    protected int getCodRecordEsterno() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;

        try { // prova ad eseguire il codice
            if (this.isCapoGruppo()) {
                codice = super.getCodRecordEsterno();
            } else {
                codice = this.getRifEsterno();
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;

    }


    private int getCodCliente() {
        return codCliente;
    }


    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }


    private int getCodCapoGruppo() {
        return codCapoGruppo;
    }


    /**
     * Assegna il codice del capogruppo
     * <p/>
     *
     * @param codice del capogruppo
     */
    public void setCodCapoGruppo(int codice) {
        this.codCapoGruppo = codice;
    }
}// fine della classe