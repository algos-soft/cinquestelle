/**
 * Title:     DialogoImpostaData
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      7-lug-2009
 */
package it.algos.albergo;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.util.jdatepicker.JDatePicker;

import javax.swing.JLabel;
import java.util.Date;

/**
 * Dialogo per l'impostazione della data del programma
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 7-lug-2009 ore 17.30.57
 */
public final class DialogoImpostaData extends DialogoAnnullaConferma {

    /* nome del campo opzioni */
    private static final String NOME_CAMPO_OPZIONI = "Opzioni";

    /* data passata nel costruttore */
    private Date dataInput;

    /* JLabel pr mostrare la data di sistema */
    private JLabel labelData;

    /* pickr per la data */
    JDatePicker picker;

    /* pannello placeholder */
    private Pannello panPlaceholder;


    /**
     * Costruttore completo senza parametri.
     * <p>
     * @param data preimpostata
     * per segnalare al dialogo di usare la data di sistema,
     * passare una data nulla
     */
    public DialogoImpostaData(Date data) {
        /* rimanda al costruttore della superclasse */
        super();

        /* registrazione dei parametri */
        this.setDataInput(data);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Date data = null;

        try { // prova ad eseguire il codice

            this.setPreferredSize(440, 350);

            this.setTitolo("Impostazione data corrente");

            this.setMessaggio(
                    "Impostazione della data corrente del programma.\n\n"
                            + "Tutte le funzioni del programma fanno riferimento a questa data.\n"
                            + "Modificandola, è possibile operare in data diversa da quella di oggi.");

            /* crea e registra il DatePicker */
            JDatePicker picker = new JDatePicker();
            picker.setOpaque(false);
            this.setPicker(picker);

            /* crea e registra la JLabel per visualizzare la data del computer */
            JLabel label = new JLabel("ecco la jlabel");
            Date currDate = Lib.Data.getCorrente();
            String testo = Lib.Data.getGiorno(currDate);
            testo = Lib.Testo.primaMaiuscola(testo);
            testo = testo + " "+Lib.Data.getDataEstesa(currDate);
            label.setText(testo);
            this.setLabelData(label);

            /* crea e aggiunge il campo radio con le due opzioni */
            campo = CampoFactory.radioInterno(NOME_CAMPO_OPZIONI);
            campo.setValoriInterni("Usa la data corrente del computer,Imposta la data manualmente");
            campo.setLarScheda(250);
            campo.decora().eliminaEtichetta();
            campo.setValore(1);
            this.addCampo(campo);

            /* crea, registra e aggiunge il pannello placeholder */
            Pannello placecholder = PannelloFactory.verticale(this);
            placecholder.setAllineamento(Layout.ALLINEA_CENTRO);
            this.setPanPlaceholder(placecholder);
            placecholder.setPreferredHeigth(30);
            Lib.Comp.bloccaAltezza(placecholder.getPanFisso());
            Lib.Comp.sbloccaLarMax(placecholder.getPanFisso());
            this.addPannello(placecholder);

            /**
             * regola il valore iniziale del campo selettore
             * e del picker di data
             */
            data = this.getDataInput();
            if (Lib.Data.isValida(data)) {
                this.getPicker().setDate(data);
                campo.setValore(2);
            } else {
                campo.setValore(1);
            }// fine del blocco if-else


            /* assegna il contenuto al pannello switch */
            this.regolaPanSwitch();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Ritorna true se deve usare la data di sistema.
     * <p/>
     * @return true se deve usare la data del computer
     * false se deve usare la data specificata dall'utente
     */
    public boolean isUsaDataSistema() {
        /* variabili e costanti locali di lavoro */
        boolean usa=false;

        try {    // prova ad eseguire il codice
            int valore = this.getInt(NOME_CAMPO_OPZIONI);
            usa = (valore==1);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Ritorna la data correntemente inserita nel picker.
     * <p/>
     * @return la data inserita
     */
    public Date getData() {
        return this.getPicker().getDate();
    }


    @Override
    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;

        try { // prova ad eseguire il codice
            confermabile = super.isConfermabile();

            /* se non usa la data di sistema deve avere specificao una data valida */
            if (confermabile) {
                if (!this.isUsaDataSistema()) {
                    confermabile = Lib.Data.isValida(this.getData());
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    @Override
    protected void eventoMemoriaModificata(Campo campo) {

        super.eventoMemoriaModificata(campo);

        try { // prova ad eseguire il codice

            /* regola il contenuto del pannello placeholder */
            if (this.isCampo(campo, NOME_CAMPO_OPZIONI)) {
                this.regolaPanSwitch();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regola il contenuto del pannello placeholder
     * in funzionde della opzione correntemente impostata.
     * <p/>
     */
    private void regolaPanSwitch () {
        try {    // prova ad eseguire il codice
            Pannello pan = this.getPanPlaceholder();
            pan.removeAll();
            if (this.isUsaDataSistema()) {
                pan.add(this.getLabelDataCorrente());
            } else {
                pan.add(this.getPicker());
            }// fine del blocco if-else
            pan.getPanFisso().validate();
            pan.getPanFisso().repaint();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {

        /* crea una istanza di se stessa */
        DialogoImpostaData dialogo = new DialogoImpostaData(null);
        dialogo.avvia();

    }// fine del metodo main


    private Date getDataInput() {
        return dataInput;
    }


    private void setDataInput(Date dataInput) {
        this.dataInput = dataInput;
    }


    private JLabel getLabelDataCorrente() {
        return labelData;
    }


    private void setLabelData(JLabel labelData) {
        this.labelData = labelData;
    }


    private Pannello getPanPlaceholder() {
        return panPlaceholder;
    }


    private void setPanPlaceholder(Pannello panPlaceholder) {
        this.panPlaceholder = panPlaceholder;
    }


    private JDatePicker getPicker() {
        return picker;
    }


    public void setPicker(JDatePicker picker) {
        this.picker = picker;
    }
}// fine della classe
