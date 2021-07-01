package it.algos.albergo.presenza;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.TavolaModello;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;

/**
 * Renderer per il campo Camera.
 * <p/>
 * Se la presenza è "provvisoria" (in partenza oggi)
 * mostra la camera in colore diverso
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  16-mar-2007 ore 10.25
 */
class RendererCamera extends RendererBase {

    /**
     * Costruttore completo con parametri
     *
     * @param campo di riferimento
     */
    public RendererCamera(Campo campo) {
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
        int codRecord = 0;
        boolean flag = false;
        Tavola tavola;
        TavolaModello modello;
        JLabel label;
        String testo;

        try { // prova ad eseguire il codice

            /* recupera il codice del record */
            if (table != null) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                    modello = tavola.getModello();
                    codRecord = modello.getChiave(riga);
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il valore del campo booleano */
            if (codRecord > 0) {
                flag = PresenzaModulo.get().query().valoreBool(Presenza.Cam.provvisoria.get(),
                        codRecord);
            }// fine del blocco if

            /* recupera il componente dalla superclasse */
            comp = super.getTableCellRendererComponent(table,
                    valore,
                    selezionata,
                    hasFocus,
                    riga,
                    colonna);

            /* regola testo e colore del componente in funzione del flag */
            if (comp != null) {
                if (comp instanceof JLabel) {
                    label = (JLabel)comp;
                    testo = label.getText();

                    if (flag) {
                        label.setForeground(Color.red);
                        label.setText("(" + testo + ")");
                    } else {
                        label.setForeground(table.getForeground());
                        label.setText(testo);
                    }// fine del blocco if-else

                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


}// fine della classe