package it.algos.albergo.lib;

import it.algos.albergo.listino.Listino;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.JTable;
import java.awt.Component;

/**
 * Renderer per il campo Tipo di Pasto.
 * <p/>
 * Lo fa vedere solo se il record è a mezza pensione
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  16-mar-2007 ore 10.25
 */
public class RendererPasto extends RendererBase {

    /**
     * Costruttore completo con parametri
     *
     * @param campo di riferimento
     */
    public RendererPasto(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);

        try {
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    public Component getTableCellRendererComponent(JTable table,
                                                   Object valore,
                                                   boolean selezionata,
                                                   boolean hasFocus,
                                                   int riga,
                                                   int colonna) {

        /* variabili e costanti locali di lavoro */
        Component comp = null;
        int codRecord;
        int codPens;
        int codPasto;
        boolean mezzaPens;
        Object newValore;

        try { // prova ad eseguire il codice

            /* recupera il codice del record */
            codRecord = this.getChiaveRiga(riga, table);

            /**
             * recupera il codice di pensione e
             * controlla se è mezza pensione
             */
            codPens = PresenzaModulo.get().query().valoreInt(
                    Presenza.Cam.pensione.get(),
                    codRecord);
            mezzaPens = (codPens == Listino.PensioniPeriodo.mezzaPensione.getCodice());

            /* se non è mezza pensione cambia il valore in stringa vuota */
            newValore = "";
            if (mezzaPens) {
                codPasto = Libreria.getInt(valore);
                newValore = Presenza.TipiPasto.getDescrizione(codPasto);
            }// fine del blocco if

            /* recupera il componente dalla superclasse con il nuovo valore */
            comp = super.getTableCellRendererComponent(table,
                    newValore,
                    selezionata,
                    hasFocus,
                    riga,
                    colonna);

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


}// fine della classe