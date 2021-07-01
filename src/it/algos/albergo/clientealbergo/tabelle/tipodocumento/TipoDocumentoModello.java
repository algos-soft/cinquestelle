/**
 * Title:     TipoDocumentoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      4-mag-2004
 */
package it.algos.albergo.clientealbergo.tabelle.tipodocumento;

import it.algos.albergo.clientealbergo.tabelle.autorita.Autorita;
import it.algos.albergo.nazionealbergo.NazioneAlbergo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.validatore.ValidatoreFactory;

/**
 * Tracciato record della tavola TipoDocumentoModello.
 * <br>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Mantiene il nome della tavola di archivo dove sono registrati tutti i
 * dati (records) del modello </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) </li>
 * <li> Un eventuale file di dati iniziali va regolato come percorso e nomi dei
 * campi presenti </li>
 * <li> Regola i titoli delle finestre lista e scheda
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 4-mag-2004 ore 9.10.14
 */
public final class TipoDocumentoModello extends ModelloAlgos implements TipoDocumento {

    /**
     * Costruttore completo senza parametri.<br>
     */
    public TipoDocumentoModello() {
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
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TipoDocumento.NOME_TAVOLA);
    }// fine del metodo inizia


    /**
     * Creazione dei campi di questo modello (oltre a quelli base).
     * <br>
     * i campi verranno visualizzati nell'ordine di inserimento <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        /* i campi verranno visualizzati nell'ordine di inserimento */
        try {
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo sigla */
            unCampo = CampoFactory.sigla();
            this.addCampo(unCampo);

            /* campo descrizione */
            unCampo = CampoFactory.descrizione();
            this.addCampo(unCampo);

            /* campo link autorità di rilascio */
            unCampo = CampoFactory.comboLinkPop(Cam.autorita);
            unCampo.setNomeModuloLinkato(Autorita.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setNomeColonnaListaLinkata(Autorita.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(Autorita.Cam.sigla.get());
            this.addCampo(unCampo);

            /* campo validità in anni */
            unCampo = CampoFactory.intero(Cam.validoAnni);
            this.addCampo(unCampo);
            
            /* campo codicePS */
            unCampo = CampoFactory.testo(Cam.codicePS);
            unCampo.setLarLista(80);
            unCampo.getCampoDati().setValidatore(ValidatoreFactory.testoLunMax(5));
            this.addCampo(unCampo);


            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista(); //

        } catch (Exception unErrore) { // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */

}// fine della classe
