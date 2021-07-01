/**
 * Title:     TransferableBase
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03.03.2006
 */
package it.algos.base.draganddrop.transferables;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * Oggetto Transferable utilizzato per trasportare
 * i dati durante il DND.
 * <p/>
 */
public class TransferableBase implements Transferable {

    /**
     * i dati da trasferire
     */
    private Object data;

    /**
     * Flavor locale (usato per trasferimenti all'interno dell'applicazione)
     */
    private DataFlavor localFlavor;

    /**
     * Flavor seriale (usato per trasferimenti da/per l'esterno)
     */
    private DataFlavor serialFlavor;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param dati da trasferire
     * @param lf flavor locale
     * @param sf flavor seriale
     */
    public TransferableBase(Object dati, DataFlavor lf, DataFlavor sf) {

        super();

        this.setData(dati);
        this.setLocalFlavor(lf);
        this.setSerialFlavor(sf);

    }


    /**
     * Returns an object which represents the data to be transferred
     */
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {

        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }

        /* valore di ritorno */
        return this.getData();

    }


    /**
     * Returns an array of DataFlavor objects indicating
     * the flavors the data can be provided in
     */
    public DataFlavor[] getTransferDataFlavors() {
        /* variabili e costanti locali di lavoro */
        DataFlavor[] flavors = null;
        DataFlavor lf, sf;

        lf = this.getLocalFlavor();
        sf = this.getSerialFlavor();
        flavors = new DataFlavor[]{lf, sf};

        /* valore di ritorno */
        return flavors;
    }


    /**
     * Returns whether or not the specified data flavor
     * is supported for this object
     */
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if (this.getLocalFlavor().equals(flavor)) {
            return true;
        }
        if (this.getSerialFlavor().equals(flavor)) {
            return true;
        }
        return false;
    }


    private Object getData() {
        return data;
    }


    private void setData(Object data) {
        this.data = data;
    }


    private DataFlavor getLocalFlavor() {
        return localFlavor;
    }


    private void setLocalFlavor(DataFlavor localFlavor) {
        this.localFlavor = localFlavor;
    }


    private DataFlavor getSerialFlavor() {
        return serialFlavor;
    }


    private void setSerialFlavor(DataFlavor serialFlavor) {
        this.serialFlavor = serialFlavor;
    }
}
