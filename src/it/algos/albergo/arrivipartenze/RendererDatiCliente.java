package it.algos.albergo.arrivipartenze;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.TavolaModello;
import it.algos.base.tavola.Tavola;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Component;
import java.util.Date;

/**
 * Renderer del campo Dati Cliente Validi
 */
final class RendererDatiCliente extends RendererSpunta {

    private ConfermaArrivoDialogo dialogo;

    private Icon iconaValido;

    private Icon iconaValidoDisabilitata;

    private Icon iconaNonValido;

    private Icon iconaNonValidoDisabilitata;

    private JLabel label;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     * @param dialogo di riferimento
     */
    public RendererDatiCliente(Campo campo, ConfermaArrivoDialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(campo);

        this.setDialogo(dialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try { // prova ad eseguire il codice

            this.setIconaValido(Lib.Risorse.getIconaBase("checkmarkgreen16"));
            this.setIconaValidoDisabilitata(Lib.Risorse.getIconaBase("checkmarkgray16"));
            this.setIconaNonValido(Lib.Risorse.getIconaBase("Danger16"));
            this.setIconaNonValidoDisabilitata(Lib.Risorse.getIconaBase("Danger16gray"));

            label = new JLabel();
            label.setOpaque(true);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            this.setLabel(label);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public Component getTableCellRendererComponent(JTable jTable,
                                                   Object o,
                                                   boolean isSelected,
                                                   boolean b1,
                                                   int riga,
                                                   int colonna) {
        /* variabili e costanti locali di lavoro */
        int codCliente;
        boolean spuntata;
        boolean datiValidi;
        JLabel label = null;
        Icon icona;
        Date data;
        boolean capoNotifica;

        try { // prova ad eseguire il codice

            /* recupera la JLabel */
            label = this.getLabel();

            /* recupera il codice cliente e controlla se il documento è valido */
            spuntata = this.isSpuntata(jTable, riga);
            codCliente = this.getCodCliente(jTable, riga);
            data = this.getDialogo().getDataArrivo();

            capoNotifica = this.getDialogo().isCapoNotifica(codCliente);
            datiValidi = ClienteAlbergoModulo.isValidoArrivo(codCliente, data, capoNotifica);

            /* decide quale icona usare */
            if (spuntata) {   // riga spuntata

                if (datiValidi) {   // documento valido
                    icona = this.getIconaValido();
                } else {    // documento non valido
                    icona = this.getIconaNonValido();
                }// fine del blocco if-else

            } else {    // riga non spuntata

                if (datiValidi) {     // documento valido
                    icona = this.getIconaValidoDisabilitata();
                } else {   // documento non valido
                    icona = this.getIconaNonValidoDisabilitata();
                }// fine del blocco if-else

            }// fine del blocco if-else

            label.setIcon(icona);

            /** regola i colori della label
             * in funzione del fatto che la riga sia
             * selezionata o meno*/
            label.setOpaque(true); // devo rimetterlo se no non funziona
            if (isSelected) {
                label.setBackground(jTable.getSelectionBackground());
                label.setForeground(jTable.getSelectionForeground());
            } else {
                label.setBackground(jTable.getBackground());
                label.setForeground(jTable.getForeground());
            }


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }


    /**
     * Ritorna il codice cliente relativo a una riga.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return il codice cliente
     */
    private int getCodCliente(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        int codcli = 0;
        boolean continua = true;
        Tavola tavola = null;
        TavolaModello modelloDati;
        Dati dati;

        try {    // prova ad eseguire il codice

            if (table == null) {
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                modelloDati = tavola.getModello();
                dati = modelloDati.getDati();
                codcli = dati.getIntAt(riga, ConfermaArrivoDialogo.Nomi.codCliente.get());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codcli;
    }


    private ConfermaArrivoDialogo getDialogo() {
        return dialogo;
    }


    private void setDialogo(ConfermaArrivoDialogo dialogo) {
        this.dialogo = dialogo;
    }


    private Icon getIconaValido() {
        return iconaValido;
    }


    private void setIconaValido(Icon iconaValido) {
        this.iconaValido = iconaValido;
    }


    private Icon getIconaValidoDisabilitata() {
        return iconaValidoDisabilitata;
    }


    private void setIconaValidoDisabilitata(Icon iconaValidoDisabilitata) {
        this.iconaValidoDisabilitata = iconaValidoDisabilitata;
    }


    private Icon getIconaNonValido() {
        return iconaNonValido;
    }


    private void setIconaNonValido(Icon iconaNonValido) {
        this.iconaNonValido = iconaNonValido;
    }


    private Icon getIconaNonValidoDisabilitata() {
        return iconaNonValidoDisabilitata;
    }


    private void setIconaNonValidoDisabilitata(Icon iconaNonValidoDisabilitata) {
        this.iconaNonValidoDisabilitata = iconaNonValidoDisabilitata;
    }


    private JLabel getLabel() {
        return label;
    }


    private void setLabel(JLabel label) {
        this.label = label;
    }


}
