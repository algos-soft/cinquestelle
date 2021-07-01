package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Component;

/**
 * Classe 'interna'
 * <p/>
 * Renderer del campo Informazioni Arrivo
 */
final class RendererInfo extends RendererBase {

    private Icon iconaValido;

    private Icon iconaNonValido;

    private JLabel label;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    public RendererInfo(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);

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
            this.setIconaNonValido(Lib.Risorse.getIconaBase("Danger16"));

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
        int codPrenotazione;
        boolean confermata;
        JLabel label = null;
        Icon icona;

        try { // prova ad eseguire il codice

            /* recupera la JLabel */
            label = this.getLabel();

            /* recupera la prenotazione e controlla se è confermata */
            codPrenotazione = this.getCodPrenotazione(jTable, riga);
            confermata = PrenotazioneModulo.isConfermata(codPrenotazione);

            /* decide quale icona usare */
            if (confermata) {   // riga spuntata
                icona = this.getIconaValido();
            } else {    // riga non spuntata
                icona = this.getIconaNonValido();
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
     * Ritorna il codice prenotazione relativo a una riga.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return il codice prenotazione
     */
    private int getCodPrenotazione(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        int codPrenotazione = 0;
        int codPeriodo = 0;
        Modulo modPeriodo = null;
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

            /* recupera il modulo Periodo */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            /* recupera il codice Periodo */
            if (continua) {
                modelloDati = tavola.getModello();
                dati = modelloDati.getDati();
                codPeriodo = dati.getIntAt(riga, modPeriodo.getCampoChiave());
                continua = (codPeriodo > 0);
            }// fine del blocco if

            /* recupera il codice Prenotazione */
            if (continua) {
                codPrenotazione = modPeriodo.query()
                        .valoreInt(Periodo.Cam.prenotazione.get(), codPeriodo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPrenotazione;
    }


    private Icon getIconaValido() {
        return iconaValido;
    }


    private void setIconaValido(Icon iconaValido) {
        this.iconaValido = iconaValido;
    }


    private Icon getIconaNonValido() {
        return iconaNonValido;
    }


    private void setIconaNonValido(Icon iconaNonValido) {
        this.iconaNonValido = iconaNonValido;
    }


    private JLabel getLabel() {
        return label;
    }


    private void setLabel(JLabel label) {
        this.label = label;
    }


}