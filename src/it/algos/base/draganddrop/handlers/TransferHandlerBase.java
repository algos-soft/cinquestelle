/**
 * Title:     TransferHandlerBase
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03.03.2006
 */
package it.algos.base.draganddrop.handlers;

import it.algos.base.draganddrop.transferables.TransferableBase;
import it.algos.base.draganddrop.transferables.TransferableData;
import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Transfer Handler astratto di base.
 */
public abstract class TransferHandlerBase extends TransferHandler {

    /**
     * DataFlavor per trasferimenti all'interno dell'applicazione
     */
    private DataFlavor localFlavor;

    /**
     * Eventuale DataFlavor per trasferimenti all'esterno dell'applicazione.
     * <p/>
     * Il serialFlavor serve per trasferire i dati
     * all'esterno della applicazione.
     * In tal caso tutti gli oggetti trasportati devono
     * essere Serializable.
     * Se non si desidera trasportare gli oggetti all'esterno,
     * si puo' lasciare nullo.
     */
    private DataFlavor serialFlavor;

    /**
     * Codice azioni Dnd consentite
     * COPY, MOVE, COPY_OR_MOVE
     *
     * @see javax.swing.TransferHandler
     */
    private int azioneDnd;


    /**
     * Costruttore base con parametri
     *
     * @param stringaDfLocale la stringa per costruire il DataFlavor locale
     * @param dfSeriale il DataFlavor seriale (facoltativo)
     * @param azioneDnd il codice delle azioni dnd consentite
     * COPY, MOVE, COPY_OR_MOVE
     *
     * @see javax.swing.TransferHandler
     */
    protected TransferHandlerBase(String stringaDfLocale, DataFlavor dfSeriale, int azioneDnd) {

        /* variabili e costanti locali di lavoro */
        String stringaDF;
        DataFlavor dfLocale;

        try { // prova ad eseguire il codice

            stringaDF = DataFlavor.javaJVMLocalObjectMimeType + ";class=";
            stringaDF += stringaDfLocale;

            dfLocale = new DataFlavor(stringaDF);
            this.setLocalFlavor(dfLocale);
            this.setSerialFlavor(dfSeriale);
            this.setAzioneDnd(azioneDnd);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea l'oggetto Transferable per il componente.
     * Ritorna la rappresentazione dei dati da trasferire.
     */
    protected Transferable createTransferable(JComponent c) {
        /* variabili e costanti locali di lavoro */
        Transferable transferable = null;
        Object selezione;
        TransferableData dati;
        DataFlavor localDf;
        DataFlavor serialDf;

        try { // prova ad eseguire il codice

            /**
             * recupera la rappresentazione dei dati
             * attualmente selezionati nel componente di origine
             */
            selezione = this.getDatiSelezionati(c);

            /**
             * impacchetta i dati in un oggetto trasferibile
             * che trasporta anche il riferimento al componente
             * sorgente dei dati
             */
            dati = new TransferableData(selezione, c);

            /**
             * crea un Transferable con dati e flavors
             */
            localDf = this.getLocalFlavor();
            serialDf = this.getSerialFlavor();
            transferable = new TransferableBase(dati, localDf, serialDf);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return transferable;
    }


    /**
     * Ritorna la rappresentazione dei dati
     * attualmente selezionati nel componente di origine
     * <p/>
     * I dati selezionati possono essere rappresentati
     * con oggetti di qualsiasi classe, le classi che li usano
     * devono sapere di cosa si tratta ed effettuare gli
     * opportni casting.
     *
     * @param source il JComponent interessato
     *
     * @return la rappresentazione dei dati selezionati
     */
    protected abstract Object getDatiSelezionati(JComponent source);


    /**
     * Causes a transfer to a component from a clipboard or a DND drop operation.
     * The Transferable represents the data to be imported into the component.
     */
    public final boolean importData(JComponent c, Transferable t) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        Object dati = null;
        JComponent source = null;
        TransferableData td;

        /**
         * Controllo se e' possibile importare i dati pervenuti
         */
        if (continua) {
            if (!canImport(c, t.getTransferDataFlavors())) {
                continua = false;
            }
        }// fine del blocco if

        /**
         * Tenta prima di usare il local flavor,
         * poi tenta di usare il serial flavor.
         */
        if (continua) {
            try {
                if (this.hasLocalFlavor(t.getTransferDataFlavors())) {
                    dati = t.getTransferData(this.getLocalFlavor());
                } else if (this.hasSerialFlavor(t.getTransferDataFlavors())) {
                    dati = t.getTransferData(this.getSerialFlavor());
                } else {
                    continua = false;
                }
            } catch (UnsupportedFlavorException ufe) {
                System.out.println("importData: unsupported data flavor");
                return false;
            } catch (IOException ioe) {
                System.out.println("importData: I/O exception");
                continua = false;
            }
        }// fine del blocco if

        /**
         * Apre il pacchetto ed estrae dati e source
         */
        if (continua) {
            continua = false;
            if (dati != null) {
                if (dati instanceof TransferableData) {
                    td = (TransferableData)dati;
                    source = td.getSource();
                    dati = td.getData();
                    continua = true;
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        /**
         * Invoca il metodo astratto sovrascritto nelle sottoclassi
         */
        if (continua) {
            riuscito = this.importaDati(dati, c, source);
        }// fine del blocco if

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Chiamato al momento di importare i dati nell'oggetto.
     * <p/>
     * Da implementare obbligatoriamente nella classe specifica.
     * Non fare nulla e ritornare true se non si vuole effettuare il drop.
     *
     * @param oggettoDati da importare
     * @param dest il componente destinazione
     * @param source il componente sorgente
     *
     * @return true se riuscito
     */
    protected abstract boolean importaDati(Object oggettoDati, JComponent dest, JComponent source);


    /**
     * Determina se c'e' il DataFlavor locale tra quelli passati.
     */
    protected final boolean hasLocalFlavor(DataFlavor[] flavors) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        DataFlavor dfLocale;

        try { // prova ad eseguire il codice
            dfLocale = this.getLocalFlavor();

            if (dfLocale != null) {
                for (int i = 0; i < flavors.length; i++) {
                    if (flavors[i].equals(dfLocale)) {
                        esiste = true;
                        break;
                    }
                }
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Determina se c'e' il DataFlavor seriale tra quelli passati.
     */
    protected final boolean hasSerialFlavor(DataFlavor[] flavors) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        DataFlavor dfSeriale;

        try { // prova ad eseguire il codice
            dfSeriale = this.getSerialFlavor();

            if (dfSeriale != null) {
                for (int i = 0; i < flavors.length; i++) {
                    if (flavors[i].equals(dfSeriale)) {
                        esiste = true;
                        break;
                    }
                }
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Indicates whether a component would accept an import of
     * the given set of data flavors prior to actually attempting to import it.
     */
    public final boolean canImport(JComponent c, DataFlavor[] flavors) {
        if (hasLocalFlavor(flavors)) {
            return true;
        }
        if (hasSerialFlavor(flavors)) {
            return true;
        }
        return false;
    }


    /**
     * Determina le azioni di DND ammissibili sul componente.
     * <p/>
     * COPY, MOVE, COPY_OR_MOVE
     *
     * @see javax.swing.TransferHandler
     */
    public final int getSourceActions(JComponent c) {
        return this.getAzioneDnd();
    }


    protected DataFlavor getLocalFlavor() {
        return localFlavor;
    }


    private void setLocalFlavor(DataFlavor localFlavor) {
        this.localFlavor = localFlavor;
    }


    protected DataFlavor getSerialFlavor() {
        return serialFlavor;
    }


    private void setSerialFlavor(DataFlavor serialFlavor) {
        this.serialFlavor = serialFlavor;
    }


    protected int getAzioneDnd() {
        return azioneDnd;
    }


    private void setAzioneDnd(int azioneDnd) {
        this.azioneDnd = azioneDnd;
    }


}
