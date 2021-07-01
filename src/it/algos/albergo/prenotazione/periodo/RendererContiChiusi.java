package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Component;
import java.util.ArrayList;

/**
 * Classe 'interna'
 * <p/>
 * Renderer del campo Conto Chiuso nel Navigatore Partenze
 */
final class RendererContiChiusi extends RendererBase {

    private Icon iconaPagati;   //icona per tutti i conti pagati

    private Icon iconaNonPagati;   //icona per tutti i conti non pagati

    private Icon iconaMixed;    //icona per alcuni conti pagati ed altri non pagati

    private Icon iconaNone;     //icona per nessun conto associato

    private JLabel label;

    private Modulo modConto;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    public RendererContiChiusi(Campo campo) {
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
        Icon ico;

        try { // prova ad eseguire il codice

            modConto = ContoModulo.get();

            //regola le icone
            ico=modConto.getIcona("conti_pagati_ok");
            this.setIconaPagati(ico);
            ico=modConto.getIcona("conti_pagati_no");
            this.setIconaNonPagati(ico);
            ico=modConto.getIcona("conti_pagati_mixed");
            this.setIconaMixed(ico);
            ico=modConto.getIcona("conti_inesistenti");
            this.setIconaNone(ico);
            
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
        int codPeriodo;
        boolean continua;
        JLabel label = null;
        Icon icona;
        int quantiTot=0;
        int quantiChiusi=0;
        int quantiAperti=0;


        try { // prova ad eseguire il codice

            /* recupera la JLabel */
            label = this.getLabel();

            /* icona di default */
            icona=this.getIconaNone();

            /* recupera il periodo */
            codPeriodo = this.getCodPeriodo(jTable, riga);
            continua = codPeriodo>0;

            /* recupera i conti legati al periodo */
            if (continua) {
                Filtro filtro = ContoModulo.getFiltroContiPeriodo(codPeriodo);
                ArrayList lista = modConto.query().valoriCampo(Conto.Cam.chiuso.get(), filtro);
                for(Object val : lista){
                    if (val instanceof Boolean) {
                        boolean b = (Boolean)val;
                        if (b) {
                            quantiChiusi++;
                        } else {
                            quantiAperti++;
                        }// fine del blocco if-else
                        quantiTot++;
                    }// fine del blocco if
                }
            }// fine del blocco if

            /* decide quale icona usare */
            if (continua) {

                if (quantiTot>0) {
                    if ((quantiAperti>0) & (quantiChiusi>0)) {
                        icona=this.getIconaMixed();
                    }// fine del blocco if

                    if ((quantiAperti>0) & (quantiChiusi==0)) {
                        icona=this.getIconaNonPagati();
                    }// fine del blocco if

                    if ((quantiAperti==0) & (quantiChiusi>0)) {
                        icona=this.getIconaPagati();
                    }// fine del blocco if

                } else {
                    icona=this.getIconaNone();
                }// fine del blocco if-else

            }// fine del blocco if


            /* assegna l'icona alla jlabel */
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
     * Ritorna il codice periodo relativo a una riga.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return il codice periodo
     */
    private int getCodPeriodo(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
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
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPeriodo;
    }

    private Icon getIconaPagati() {
        return iconaPagati;
    }

    private void setIconaPagati(Icon iconaPagati) {
        this.iconaPagati = iconaPagati;
    }

    private Icon getIconaNonPagati() {
        return iconaNonPagati;
    }

    private void setIconaNonPagati(Icon iconaNonPagati) {
        this.iconaNonPagati = iconaNonPagati;
    }

    private Icon getIconaMixed() {
        return iconaMixed;
    }

    private void setIconaMixed(Icon iconaMixed) {
        this.iconaMixed = iconaMixed;
    }

    private Icon getIconaNone() {
        return iconaNone;
    }

    private void setIconaNone(Icon iconaNone) {
        this.iconaNone = iconaNone;
    }

    private JLabel getLabel() {
        return label;
    }

    private void setLabel(JLabel label) {
        this.label = label;
    }


}