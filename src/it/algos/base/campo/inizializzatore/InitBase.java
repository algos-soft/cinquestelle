/**
 * Title:        InitBase.java
 * Package:      it.algos.base.campo.inizializzatore
 * Description:  Inizializzatore Base Astratto
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 28 agosto 2003 alle 12.43
 */

package it.algos.base.campo.inizializzatore;

import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Implementare un oggetto astratto Inizializzatore
 * da associare al campo <br>
 * Se al campo viene associato un oggetto Inizializzatore, alla creazione
 * del nuovo record il campo viene inizializzato tramite una funzione
 * specifica a seconda del tipo di inizializzatore scelto.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  28 agosto 2003 alle 12.43
 */
abstract class InitBase extends Object implements Init {

//    /**
//     * Campo di riferimento
//     */
//    private Campo campo = null;

    /**
     * Eventuale valore fisso di inizializzazione
     */
    Object valoreFisso = null;


    /**
     * Costruttore completo.
     * <p/>
     */
    public InitBase() {

        /* rimanda al costruttore della superclasse */
        super();

        /* regolazioni iniziali di riferimenti e variabili */
//        this.setCampo(campo);

        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Ritorna il valore di inizializzazione per il campo.
     * <p/>
     * Il valore ritornato e' a livello di Memoria
     */
    public Object getValore() {
        return null;
    }


    /**
     * Ritorna il valore di inizializzazione per il campo.
     * <p/>
     * Il valore ritornato e' a livello di Memoria
     *
     * @param conn la connessione da utilizzare per eventuali query
     */
    public Object getValore(Connessione conn) {
        return this.getValore();
    }


    /**
     * Controlla se questo inizializzatore supporta l'utilizzo delle transazioni.
     * <p/>
     * Significativo solo per gli inizializzatori del campo chiave
     *
     * @return true se supporta le transazioni
     */
    public boolean isSupportaTransazioni() {
        return false;
    }


    /**
     * Controlla se questo inizializzatore necessita del modulo Contatori.
     * <p/>
     *
     * @return true se necessita del modulo Contatori
     */
    public boolean isNecessitaContatori() {
        return false;
    }


    protected Object getValoreFisso() {
        return valoreFisso;
    }


    protected void setValoreFisso(Object valoreFisso) {
        this.valoreFisso = valoreFisso;
    }

}