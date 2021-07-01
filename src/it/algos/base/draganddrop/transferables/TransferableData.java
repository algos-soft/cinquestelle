/**
 * Title:     TransferableData
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03.03.2006
 */
package it.algos.base.draganddrop.transferables;

import javax.swing.*;

/**
 * Wrapper attorno ai dati da trasferire.
 * <p/>
 * Serve per memorizzare il componente sorgente oltre ai dati
 * in modo che quando arriva a destinazione si sappia da chi
 * e' partito.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 03.03.2006 ore 20:24:27
 */
public class TransferableData {

    /**
     * Il componente dal quale i dati vengono trasferiti
     */
    private JComponent source;

    /**
     * I dati da trasferire
     */
    private Object data;


    /**
     * Costruttore completo senza parametri.
     */
    public TransferableData(Object data, JComponent source) {
        this.setData(data);
        this.setSource(source);
    }


    public JComponent getSource() {
        return source;
    }


    private void setSource(JComponent source) {
        this.source = source;
    }


    public Object getData() {
        return data;
    }


    private void setData(Object data) {
        this.data = data;
    }
}
