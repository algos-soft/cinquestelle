/**
 * Title:        Init.java
 * Package:      it.algos.base.campo.inizializzatore
 * Description:  Interfaccia comune agli Inizializzatori
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 22 settembre 2003 alle 14.38
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------

package it.algos.base.campo.inizializzatore;

import it.algos.base.database.connessione.Connessione;

/**
 * Fornitore di valori iniziali per i campi.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  22 settembre 2003 ore 14.38
 */
public interface Init {

    /**
     * Ritorna il valore di inizializzazione per il campo.
     * <p/>
     * Il valore ritornato e' a livello di Memoria
     */
    public abstract Object getValore();


    /**
     * Ritorna il valore di inizializzazione per il campo.
     * <p/>
     * Il valore ritornato e' a livello di Memoria
     *
     * @param conn la connessione da utilizzare per eventuali query
     */
    public abstract Object getValore(Connessione conn);


    /**
     * Controlla se questo inizializzatore supporta l'utilizzo delle transazioni.
     * <p/>
     * Significativo solo per gli inizializzatori del campo chiave
     *
     * @return true se supporta le transazioni
     */
    public abstract boolean isSupportaTransazioni();


    /**
     * Controlla se questo inizializzatore necessita del modulo Contatori.
     * <p/>
     * Significativo solo per gli inizializzatori del campo chiave
     *
     * @return true se necessita del modulo Contatori
     */
    public abstract boolean isNecessitaContatori();

}