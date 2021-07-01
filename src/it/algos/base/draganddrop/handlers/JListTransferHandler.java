/**
 * Title:     JListTransferHandler
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03.03.2006
 */
package it.algos.base.draganddrop.handlers;

import it.algos.base.draganddrop.transferables.TransferableData;
import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

/**
 * Transfer Handler per il DND in una JList.
 */
public abstract class JListTransferHandler extends TransferHandlerBase {

    /**
     * Stringa per identificare il DataFlavor per trasferimenti
     * di dati eseguiti all'interno dell'applicazione
     */
    private static final String DF_LOCALE = "java.util.ArrayList";

    /**
     * Flag - accettazione di oggetti duplicati nella lista
     */
    private boolean accettaDuplicati;

    /**
     * Variabili di lavoro
     */
    int[] indices = null;

    int addIndex = -1; //Location where items were added

    int addCount = 0;  //Number of items added


    /**
     * Costruttore base con parametri
     *
     * @param azioneDrag il codice delle azioni Drag consentite
     * COPY, MOVE, COPY_OR_MOVE
     *
     * @see javax.swing.TransferHandler
     */
    public JListTransferHandler(int azioneDrag) {
        super(DF_LOCALE, null, azioneDrag);
        this.setAccettaDuplicati(true);
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
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        JList target;
        ArrayList alist;

        try { // prova ad eseguire il codice

            target = (JList)dest;
            alist = (ArrayList)oggettoDati;

            /*
             * se i dati non provengono dalla lista stessa
             * (quindi non e' uno spostamento)
             * effettua l'eventuale rimozione dei duplicati
             */
            if (source != null) {
                if (!source.equals(target)) {
                    if (!this.isAccettaDuplicati()) {
                        alist = this.filtraDuplicati(alist, dest);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            //We'll drop at the current selected index.
            int index = target.getSelectedIndex();

            //Prevent the user from dropping data back on itself.
            //For example, if the user is moving items #4,#5,#6 and #7 and
            //attempts to insert the items after item #5, this would
            //be problematic when removing the original items.
            //This is interpreted as dropping the same data on itself
            //and has no effect.
            if (source != null) {
                if (source.equals(target)) {
                    if (indices != null &&
                            index >= indices[0] - 1 &&
                            index <= indices[indices.length - 1]) {
                        indices = null;
                        continua = false;
                        riuscito = true;
                    }
                }
            }// fine del blocco if

            if (continua) {
                DefaultListModel listModel = (DefaultListModel)target.getModel();
                int max = listModel.getSize();
                if (index < 0) {
                    index = max;
                } else {
                    index++;
                    if (index > max) {
                        index = max;
                    }
                }
                addIndex = index;
                addCount = alist.size();
                for (int i = 0; i < alist.size(); i++) {
                    listModel.add(index++, alist.get(i));
                }
                riuscito = true;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    }


    /**
     * Elimina dalla lista da inserire gli oggetti
     * gia' presenti nella lista destinazione.
     * <p/>
     *
     * @param listaInArrivo dalla quale eliminare i duplicati
     * @param compDest componente destinazione per il controllo
     *
     * @return una nuova lista filtrata
     */
    private ArrayList<Object> filtraDuplicati(ArrayList listaInArrivo, JComponent compDest) {
        /* variabili e costanti locali di lavoro */
        JList lista;
        DefaultListModel listModel;
        ArrayList<Object> listaInUscita = null;

        try {    // prova ad eseguire il codice
            listaInUscita = new ArrayList<Object>();
            lista = (JList)compDest;
            listModel = (DefaultListModel)lista.getModel();

            for (Object oggetto : listaInArrivo) {
                if (oggetto != null) {
                    if (!listModel.contains(oggetto)) {
                        listaInUscita.add(oggetto);
                    }// fine del blocco if
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaInUscita;
    }


    /**
     * Invoked after data has been exported.
     * This method should remove the data that was transferred if
     * the action was MOVE
     */
    protected void exportDone(JComponent c, Transferable data, int action) {
        /* variabili e costanti locali di lavoro */
        JList lista = null;
        TransferableData tb;
        Object oggetto;

        if ((action == MOVE) && (indices != null)) {

            lista = (JList)c;

            if (lista != null) {

                DefaultListModel model = (DefaultListModel)lista.getModel();

                //If we are moving items around in the same list, we
                //need to adjust the indices accordingly since those
                //after the insertion point have moved.
                if (addCount > 0) {
                    for (int i = 0; i < indices.length; i++) {
                        if (indices[i] > addIndex) {
                            indices[i] += addCount;
                        }
                    }
                }

                for (int i = indices.length - 1; i >= 0; i--) {
                    model.remove(indices[i]);
                } // fine del ciclo for


            }// fine del blocco if

        }
        indices = null;
        addIndex = -1;
        addCount = 0;
    }


    /**
     * Crea l'oggetto Transferable per il componente.
     * Ritorna la rappresentazione dei dati da trasferire.
     */
    protected Transferable createTransferable(JComponent c) {
        /* variabili e costanti locali di lavoro */
        Transferable transferable = null;
        JList lista;

        try { // prova ad eseguire il codice

            transferable = super.createTransferable(c);

            /* memorizza per uso successivo */
            lista = (JList)c;
            indices = lista.getSelectedIndices();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return transferable;
    }


    private boolean isAccettaDuplicati() {
        return accettaDuplicati;
    }


    /**
     * Determina se questo handler permette di
     * duplicare i dati nella lista
     *
     * @param accettaDuplicati true per permettere le duplicazioni
     */
    public void setAccettaDuplicati(boolean accettaDuplicati) {
        this.accettaDuplicati = accettaDuplicati;
    }


}

