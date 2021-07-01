package it.algos.albergo.clientealbergo;

import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.TavolaModello;
import it.algos.base.progetto.Progetto;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Renderer per il campo Camera.
 * <p/>
 * Se la presenza è "provvisoria" (in partenza oggi)
 * mostra la camera in colore diverso
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  16-mar-2007 ore 10.25
 */
class RendererUltSoggiorno extends RendererBase {

    /**
     * Costruttore completo con parametri
     *
     * @param campo di riferimento
     */
    public RendererUltSoggiorno(Campo campo) {
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
        int codCliente = 0;
        boolean flag = false;
        Tavola tavola;
        TavolaModello modello;
        JLabel label;
        String testo;
        Date data=null;

        try { // prova ad eseguire il codice

            /* recupera il codice del record */
            if (table != null) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                    modello = tavola.getModello();
                    codCliente = modello.getChiave(riga);
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il valore del campo data */
            if (codCliente > 0) {
                data = PresenzaModulo.getDataUltimoSoggiorno(codCliente);
            }// fine del blocco if

            /* recupera il componente dalla superclasse */
            comp = super.getTableCellRendererComponent(table,
                    valore,
                    selezionata,
                    hasFocus,
                    riga,
                    colonna);

            /* regola il testo del componente */
            if (comp != null) {
                if (comp instanceof JLabel) {
                    label = (JLabel)comp;
                    testo="";
                    if (Lib.Data.isValida(data)) {
                        SimpleDateFormat df = Progetto.getDateFormat();
                        testo = df.format(data);
                    }// fine del blocco if
                    label.setText(testo);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


}// fine della classe