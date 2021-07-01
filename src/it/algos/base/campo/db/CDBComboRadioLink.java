/**
 * Title:     CDBComboRadio
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      11-mag-2004
 */
package it.algos.base.campo.db;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 11-mag-2004 ore 16.15.52
 */
public final class CDBComboRadioLink extends CDBLinkato {


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public CDBComboRadioLink() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDBComboRadioLink(Campo unCampoParente) {
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
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setAzioneDelete(Db.Azione.setNull);
    }// fine del metodo inizia

//    /**
//     * Relaziona il campo.
//     * <p/>
//     * Metodo invocato dal ciclo inizia del Progetto <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     *
//     *
//     * @return true se riuscito
//     */
//    public boolean relaziona() {
//        /* variabili e costanti locali di lavoro */
//        boolean riuscito = true;
//
//        try { // prova ad eseguire il codice
//
//            if (!this.isRelazionato()) {
//
//                /* invoca il metodo sovrascritto della superclasse */
//                riuscito = super.relaziona();
//
//                if (riuscito) {
//                    this.setRelazionato(riuscito);
//                }// fine del blocco if
//
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return riuscito;
//    }


}// fine della classe
