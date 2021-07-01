/**
 * Title:     TreeTransferHandler
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03.03.2006
 */
package it.algos.base.draganddrop.handlers;

import javax.swing.*;

/**
 * Transfer Handler per il DND in un albero.
 */
public abstract class JTreeTransferHandler extends TransferHandlerBase {

    /* Stringa per identificare il DataFlavor per trasferimenti
     * di dati eseguiti all'interno dell'applicazione */
    private static final String DF_LOCALE = "java.util.ArrayList";


    /**
     * Costruttore base con parametri
     *
     * @param azioneDrag il codice delle azioni Drag consentite
     * COPY, MOVE, COPY_OR_MOVE
     *
     * @see javax.swing.TransferHandler
     */
    public JTreeTransferHandler(int azioneDrag) {
        super(DF_LOCALE, null, azioneDrag);
    }


    /**
     * Chiamato al momento di importare i dati nell'oggetto.
     * <p/>
     * Non fare nulla e ritornare true se non si vuole effettuare il drop.
     *
     * @param oggettoDati da importare
     * @param dest il componente destinazione
     * @param source il componente sorgente
     *
     * @return true se riuscito
     */
    protected boolean importaDati(Object oggettoDati, JComponent dest, JComponent source) {
        return true;
    }


}
