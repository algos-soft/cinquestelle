package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.base.wrapper.TestoAlgos;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Component;

/**
 * Renderer per il campo Codice Residenza
 * Fa vedere il nome della provincia se italiano
 * o il nome della nazione se straniero
 */
class RendererResidenza extends RendererBase {

    private JLabel label;

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    RendererResidenza(Campo campo) {
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

            label = new JLabel();
            label.setOpaque(true);
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setVerticalAlignment(JLabel.CENTER);
            TestoAlgos.setLista(label);
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
        JLabel label = null;
        int codResidenza;
        int chiaveRiga;
        int codTipoRiga;
        ISTAT.TipoRiga tipoRiga;
        Modulo modIstat;
        Modulo modResidenza;
        boolean continua;
        Icon icona = null;
        String testo;

        try { // prova ad eseguire il codice

            /* recupera la JLabel */
            label = this.getLabel();
            continua = (label != null);

            /* regola il testo della label in funzione del valore */
            if (continua) {
                testo="";
                codResidenza = Libreria.getInt(o);
                chiaveRiga = super.getChiaveRiga(riga, jTable);
                modIstat = ISTATModulo.get();
                codTipoRiga = modIstat.query().valoreInt(ISTAT.Cam.tipoRiga.get(), chiaveRiga);
                tipoRiga = ISTAT.TipoRiga.getTipoRiga(codTipoRiga);
                if (tipoRiga!=null) {
                    switch (tipoRiga) {
                        case italiano:
                            modResidenza = ProvinciaModulo.get();
                            testo = modResidenza.query().valoreStringa(Provincia.Cam.nomeCorrente.get(), codResidenza);
                            break;
                        case straniero:
                            modResidenza = NazioneModulo.get();
                            testo = modResidenza.query().valoreStringa(Nazione.Cam.nazione.get(), codResidenza);
                            break;
                        case nonspecificato:
                            testo = "?";
                            break;
                        default : // caso non definito
                            break;
                    } // fine del blocco switch
                }// fine del blocco if

                label.setText(testo);

            } // fine del blocco if

            /** regola i colori della label
             * in funzione del fatto che la riga sia
             * selezionata o meno*/
            if (continua) {
                label.setIcon(icona);
                label.setOpaque(true); // devo rimetterlo se no non funziona
                if (isSelected) {
                    label.setBackground(jTable.getSelectionBackground());
                    label.setForeground(jTable.getSelectionForeground());
                } else {
                    label.setBackground(jTable.getBackground());
                    label.setForeground(jTable.getForeground());
                }
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }


    protected JLabel getLabel() {
        return label;
    }


    private void setLabel(JLabel label) {
        this.label = label;
    }
}