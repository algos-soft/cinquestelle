package it.algos.albergo.statogiornaliero;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.renderer.RendererNumero;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;

/**
 * Renderer dei campi Pasto
 * Mostra il punto di domanda in base al contenuto di un altro campo booleano
 */
class RendererPasto extends RendererNumero {


    private Campo campoBool;
    private JLabel labelBoh;

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     * @param campoBool campo booleano osservato
     */
    public RendererPasto(Campo campo, Campo campoBool) {
        /* rimanda al costruttore della superclasse */
        super(campo);

        try { // prova ad eseguire il codice

            this.campoBool=campoBool;

            /* regolazioni iniziali di riferimenti e variabili */
            labelBoh=new JLabel("?");
            labelBoh.setOpaque(true);
            labelBoh.setHorizontalAlignment(SwingConstants.CENTER);


        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    public Component getTableCellRendererComponent(
            JTable jTable,
            Object o,
            boolean isSelected,
            boolean b1,
            int riga,
            int colonna) {
        /* variabili e costanti locali di lavoro */
        Component comp = null;

        try { // prova ad eseguire il codice

            int chiave = super.getChiaveRiga(riga, jTable);
            Modulo mod = campoBool.getModulo();
            boolean boh = mod.query().valoreBool(campoBool, chiave);

            if (!boh) {
                comp = super.getTableCellRendererComponent(jTable, o, isSelected, b1, riga, colonna);
            } else {
                comp=labelBoh;

                /** regola i colori del componente
                 * in funzione del fatto che la riga sia
                 * selezionata o meno*/
                if (isSelected) {
                    comp.setBackground(jTable.getSelectionBackground());
                } else {
                    comp.setBackground(jTable.getBackground());
                }
                comp.setForeground(Color.red);


            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


}