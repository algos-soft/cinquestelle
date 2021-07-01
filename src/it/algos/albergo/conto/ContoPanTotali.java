/**
 * Title:     ContoPanTotali
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      12-giu-2007
 */
package it.algos.albergo.conto;

import it.algos.albergo.conto.pagamento.Pagamento;
import it.algos.albergo.listino.Listino;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Pannello totali di un conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 12-giu-2007 ore 11.17.06
 */
public final class ContoPanTotali extends PannelloFlusso {

    /* chiave campo totale pensione */
    private static final String CAMPO_TOT_PENS = "pensione";

    /* chiave campo totale extra */
    private static final String CAMPO_TOT_EXTRA = "extra";

    /* chiave campo totale conto */
    private static final String CAMPO_TOT_CONTO = "importo conto";

    /* chiave campo totale sconti */
    private static final String CAMPO_TOT_SCONTI = "sconto";

    /* chiave campo totale netto */
    private static final String CAMPO_TOT_NETTO = "netto a pagare";

    /* chiave campo totale caparre */
    private static final String CAMPO_TOT_CAPARRE = "caparre";

    /* chiave campo totale acconti */
    private static final String CAMPO_TOT_ACCONTI = "acconti";

    /* chiave campo totale saldi */
    private static final String CAMPO_TOT_SALDI = "saldi";

    /* chiave campo totale pagato */
    private static final String CAMPO_TOT_PAGATO = "pagato";

    /* chiave campo totale non pagato */
    private static final String CAMPO_TOT_DOVUTO = "non pagato";

    /* chiave campo totale sospeso */
    private static final String CAMPO_TOT_SOSPESO = "sospeso";

    /* mappa dei campi */
    private HashMap<String, Campo> campi;

    /* ultimo codice conto */
    private int codConto;

    /* utilizzo del pulsante Aggiorna */
    private boolean usaAggiorna;


    /**
     * Costruttore base senza parametri.
     * <p/>
     * Non utilizza il pulsante Aggiorna
     */
    public ContoPanTotali() {
        /* rimanda al costruttore di questa classe */
        this(false);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo
     * <p/>
     *
     * @param flag per utilizzare il pulsante Aggiorna
     */
    public ContoPanTotali(boolean flag) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

        try { // prova ad eseguire il codice

            /* regolazioni iniziali di riferimenti e variabili */
            this.setUsaAggiorna(flag);

            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Pannello panTot;
        Pannello panSotto;
        Pannello panSotto1;
        Pannello panSotto2;
        Pannello panBottone;
        Pannello pan;
        JButton bottone;

        try { // prova ad eseguire il codice

            campi = new HashMap<String, Campo>();
            this.creaCampi();


            this.creaBordo("Situazione conto");
            this.setUsaGapFisso(true);
            this.setGapPreferito(6);

            /* pannello a due colonne */
            panTot = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panTot.setUsaGapFisso(true);
            panTot.setGapPreferito(10);

            /* pannello colonna sx */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(6);
            pan.add(this.getEtiCampo(CAMPO_TOT_PENS, 80));
            pan.add(this.getEtiCampo(CAMPO_TOT_EXTRA, 80));
            pan.add(this.getEtiCampo(CAMPO_TOT_CONTO, 80));
            pan.add(this.getEtiCampo(CAMPO_TOT_SCONTI, 80));
            panTot.add(pan);

            /* pannello colonna dx */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(6);
            pan.add(this.getEtiCampo(CAMPO_TOT_CAPARRE, 60));
            pan.add(this.getEtiCampo(CAMPO_TOT_ACCONTI, 60));
            pan.add(this.getEtiCampo(CAMPO_TOT_SALDI, 60));
//            pan.sbloccaDimMax();
            panTot.add(pan);

            /* pannello sotto generale */
            panSotto = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            panSotto.setUsaGapFisso(true);
            panSotto.setGapPreferito(6);

            /* pannello sotto con totali a pagare e pagato */
            panSotto1 = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panSotto1.setUsaGapFisso(true);
            panSotto1.setGapPreferito(10);
            panSotto1.add(this.getEtiCampo(CAMPO_TOT_NETTO, 80));
            panSotto1.add(this.getEtiCampo(CAMPO_TOT_PAGATO, 60));

            /* pannello sotto con totale dovuto e sospesi */
            panSotto2 = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panSotto2.setUsaGapFisso(true);
            panSotto2.setGapPreferito(10);
            panSotto2.add(this.getEtiCampo(CAMPO_TOT_DOVUTO, 80));
            panSotto2.add(this.getEtiCampo(CAMPO_TOT_SOSPESO, 60));

            panSotto.add(panSotto1);
            panSotto.add(panSotto2);

            /* aggiunge i pannelli a questo pannello */
            this.add(panTot);
            this.add(new JSeparator(SwingConstants.HORIZONTAL));
            this.add(panSotto);

            /* aggiunge eventualmente il bottone Aggiorna */
            if (this.isUsaAggiorna()) {
                bottone = new JButton("Aggiorna");
                bottone.setFocusable(false);
                bottone.addActionListener(new AzioneAggiorna());
                panBottone = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
                panBottone.setAllineamento(Layout.ALLINEA_DX);
//                panBottone.add(Box.createVerticalStrut(10));
                panBottone.add(bottone);
                panBottone.sbloccaLarMax();
//                panSotto2.add(panBottone);
                panSotto.add(panBottone);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Crea un pannello orizzontale contenente una etichetta
     * a larghezza fissa e un campo.
     * <p/>
     *
     * @param nomeCampo il nome del campo
     * @param larEti la larghezza fissa dell'etichetta
     *
     * @return il pannello con etichetta e campo
     */
    private JComponent getEtiCampo(String nomeCampo, int larEti) {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        Campo campo;
        JLabel label;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(nomeCampo);
            label = new JLabel(nomeCampo);
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            TestoAlgos.setEtichetta(label);
            Lib.Comp.setPreferredWidth(label, larEti);
            Lib.Comp.bloccaLarghezza(label);
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(6);
            pan.add(label);
            pan.add(campo.getPannelloCampo());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Avvia il pannello con un dato conto.
     * <p/>
     * carica i totali del conto nei campi.
     *
     * @param codConto di riferimento
     */
    public void avvia(int codConto) {

        try { // prova ad eseguire il codice

            /* assegna il codice del conto */
            this.setCodConto(codConto);

            /* sincronizza l'interfaccia */
            this.sync();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Sincronizza e visualizza i totali.
     * <p/>
     */
    public void sync() {
        /* variabili e costanti locali di lavoro */
        int codConto;
        double totPens;
        double totExtra;
        double totConto;
        double totSconto;
        double totNetto;
        double totCaparre;
        double totAcconti;
        double totSaldi;
        double totDovuto;
        double totPagato;
        double totSospesi;
        Campo campo;
        Connessione conn;

        try { // prova ad eseguire il codice

            codConto = this.getCodConto();

            /* recupera i valori */
//            totPens = ContoModulo.getTotAddebiti(codConto, Listino.AmbitoPrezzo.pensione, null);
            totPens = ContoModulo.getTotAddebiti(codConto,
                    Listino.AmbitoPrezzo.Tipo.pensioni,
                    null);
            totExtra = ContoModulo.getTotAddebiti(codConto, Listino.AmbitoPrezzo.Tipo.extra, null);
            totSconto = ContoModulo.getTotSconti(codConto, null);
            totCaparre = ContoModulo.getTotPagamenti(codConto,
                    Pagamento.TitoloPagamento.caparra,
                    null);
            totAcconti = ContoModulo.getTotPagamenti(codConto,
                    Pagamento.TitoloPagamento.acconto,
                    null);
            totSaldi = ContoModulo.getTotPagamenti(codConto, Pagamento.TitoloPagamento.saldo, null);
            totSospesi = ContoModulo.getTotSospesi(codConto, null);

            /* regola i valori calcolati */
            totConto = totPens + totExtra;
            totNetto = totConto - totSconto;
            totPagato = totCaparre + totAcconti + totSaldi;
            totDovuto = totNetto - totPagato;

            /* aggiorna i campi */
            this.setImporto(CAMPO_TOT_PENS, totPens);
            this.setImporto(CAMPO_TOT_EXTRA, totExtra);
            this.setImporto(CAMPO_TOT_CONTO, totConto);
            this.setImporto(CAMPO_TOT_SCONTI, totSconto);
            this.setImporto(CAMPO_TOT_NETTO, totNetto);
            this.setImporto(CAMPO_TOT_CAPARRE, totCaparre);
            this.setImporto(CAMPO_TOT_ACCONTI, totAcconti);
            this.setImporto(CAMPO_TOT_SALDI, totSaldi);
            this.setImporto(CAMPO_TOT_PAGATO, totPagato);
            this.setImporto(CAMPO_TOT_DOVUTO, totDovuto);
            this.setImporto(CAMPO_TOT_SOSPESO, totSospesi);

            /* regola la visibilità del campo sospeso - visibile solo se !=  0*/
            campo = this.getCampo(CAMPO_TOT_SOSPESO);
            campo.setVisibile(totSospesi != 0);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Crea i campi necessari.
     * <p/>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Color colore1;
        Color colore2;
        Color colore3;
        Color colore4;
        Color colore5;

        try {    // prova ad eseguire il codice

            colore1 = new Color(255, 238, 180);  // giallo chiaro
            colore2 = new Color(255, 238, 32);   // giallo scuro
            colore3 = new Color(70, 223, 0);     // verde
            colore4 = new Color(255, 50, 50);     // rosso
            colore5 = new Color(189, 140, 191);     // violetto

            campo = CampoFactory.valuta(CAMPO_TOT_PENS);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore1);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_EXTRA);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore1);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_CONTO);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore2);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_SCONTI);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore2);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_NETTO);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore2);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_CAPARRE);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore3);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_ACCONTI);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore3);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_SALDI);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore3);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_PAGATO);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore3);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_DOVUTO);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore5);
            this.addCampo(campo);

            campo = CampoFactory.valuta(CAMPO_TOT_SOSPESO);
            campo.decora().eliminaEtichetta();
            campo.setBackgroundColor(colore4);
            this.addCampo(campo);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un campo.
     * <p/>
     *
     * @param campo da aggiungere
     */
    private void addCampo(Campo campo) {
        try {    // prova ad eseguire il codice
            campo.setAbilitato(false);
            campo.avvia();
            campi.put(campo.getNomeInterno(), campo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Recupera un campo.
     * <p/>
     *
     * @param chiave del campo
     *
     * @return il campo
     */
    private Campo getCampo(String chiave) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try {    // prova ad eseguire il codice
            campo = campi.get(chiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Ritorna l'importo data la chiave del campo.
     * <p/>
     *
     * @param chiaveCampo la chiave del campo
     *
     * @return l'importo del campo
     */
    private double getImporto(String chiaveCampo) {
        /* variabili e costanti locali di lavoro */
        double importo = 0;
        Campo campo;
        Object valore;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(chiaveCampo);
            valore = campo.getValore();
            importo = Libreria.getDouble(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return importo;
    }


    /**
     * Assegna un importo a un campo.
     * <p/>
     *
     * @param chiaveCampo la chiave del campo
     * @param importo da assegnare
     */
    private void setImporto(String chiaveCampo, double importo) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(chiaveCampo);
            campo.setValore(importo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Azione per eseguire il ricalcolo del riepilogo
     * </p>
     */
    private final class AzioneAggiorna implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            avvia(getCodConto());
        }
    } // fine della classe 'interna'


    /**
     * Ritorna il totale dovuto.
     * <p/>
     */
    public double getTotaleDovuto() {
        return this.getImporto(CAMPO_TOT_DOVUTO);
    }


    private int getCodConto() {
        return codConto;
    }


    private void setCodConto(int codConto) {
        this.codConto = codConto;
    }


    private boolean isUsaAggiorna() {
        return usaAggiorna;
    }


    private void setUsaAggiorna(boolean usaAggiorna) {
        this.usaAggiorna = usaAggiorna;
    }


}// fine della classe
