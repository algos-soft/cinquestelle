/**
 * Title:     ContoDialogoControllo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-giu-2006
 */
package it.algos.albergo.conto;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.costante.CostanteColore;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.JLabel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di inserimento degli addebiti.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 15.00.06
 */
public final class ContoDialogoControllo extends DialogoAnnullaConferma {

    /* chiave campo data inizio controllo */
    private static final String DATA_INIZIO = "dal";

    /* chiave campo data fine controllo */
    private static final String DATA_FINE = "al";

    /* chiave campo tipo di controllo */
    private static final String TIPO_CONTROLLO = "tipo di controllo";

    /* chiave campo opzioni di controllo */
    private static final String OPZIONI_CONTROLLO = "opzioni di controllo";

    ArrayList<Integer> codiciConto;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param codiciConto elenco dei conti selezionati nel navigatore
     */
    public ContoDialogoControllo(ArrayList<Integer> codiciConto) {
        /* rimanda al costruttore della superclasse */

        this.setCodiciConto(codiciConto);
        this.setModulo(Albergo.Moduli.conto.getModulo());

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
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
        String messaggio;

        try { // prova ad eseguire il codice

            messaggio = "Evidenzia tutti i conti che non hanno " + "almeno un addebito per giorno";
            this.setTitolo("Controllo addebiti mancanti");
            this.setMessaggio(messaggio);

            this.creaDialogo();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Dialogo utente per la creazione di un nuovo conto.
     * <p/>
     */
    private void creaDialogo() {
        Campo campoDataInizio;
        Campo campoDataFine;
        Campo campoTipoControllo;
        int quantiConti;
        int cod;
        Pannello panInfo;
        JLabel labelInfo;
        String info;
        String sigla = "";
        String stringa;
        Color colore;
        Modulo modConto;
        Pannello panDate;
        Pannello panOpzioni;
        Pannello panTipoControllo = null;
        Campo campoSelControllo;
        ArrayList<String> valori;

        try { // prova ad eseguire il codice

            modConto = Albergo.Moduli.Conto();
            quantiConti = this.getCodiciConto().size();

            /* pannello info selezione */
            if (quantiConti > 0) {
                if (quantiConti == 1) { //un conto
                    cod = this.getCodiciConto().get(0);
                    sigla = modConto.query().valoreStringa(Conto.Cam.sigla.get(), cod);
                    info = "Conto selezionato: " + sigla;
                    colore = CostanteColore.ROSSO;
                } else {    // piu' conti
                    info = "Conti selezionati: " + quantiConti;
                    colore = CostanteColore.ROSSO;
                }// fine del blocco if-else
            } else {
                info = "Controllo: tuti i conti aperti";
                colore = CostanteColore.GIALLO;
            }// fine del blocco if-else
            panInfo = PannelloFactory.orizzontale(null);
            panInfo.setOpaque(false);
            labelInfo = new JLabel(info);
            labelInfo.setForeground(colore);
            panInfo.add(labelInfo);

            /* pannello date */
            campoDataInizio = CampoFactory.data(DATA_INIZIO);
            campoDataInizio.decora().obbligatorio();
            campoDataInizio.setValore(AlbergoLib.getDataProgramma());
            campoDataFine = CampoFactory.data(DATA_FINE);
            campoDataFine.decora().obbligatorio();
            campoDataFine.setValore(campoDataInizio.getValore());
            panDate = PannelloFactory.orizzontale(this);
            panDate.creaBordo("Periodo da controllare");
            panDate.add(campoDataInizio);
            panDate.add(campoDataFine);

            /* pannello opzioni */
            campoTipoControllo = CampoFactory.radioInterno(TIPO_CONTROLLO);
            campoTipoControllo.decora().obbligatorio();
            campoTipoControllo.decora().eliminaEtichetta();
            valori = new ArrayList<String>();
            valori.add("Controllo addebiti pensione");
            valori.add("Controllo addebiti extra");
            campoTipoControllo.setValoriInterni(valori);
            campoTipoControllo.setUsaNonSpecificato(false);

            panOpzioni = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            panOpzioni.creaBordo("Tipo di controllo");
            panOpzioni.add(campoTipoControllo);

            /* pannello tipo controllo */
            if (quantiConti > 0) {

                if (quantiConti == 1) {  // un solo conto
                    stringa = "Controlla solo il conto " + sigla;
                } else {  // piu' conti
                    stringa = "Controlla solo i " + quantiConti + " conti selezionati";
                }// fine del blocco if-else

                campoSelControllo = CampoFactory.radioInterno(OPZIONI_CONTROLLO);
                campoSelControllo.decora().obbligatorio();
                campoSelControllo.decora().eliminaEtichetta();
                valori = new ArrayList<String>();
                valori.add(stringa);
                valori.add("Controlla tutti i conti aperti");
                campoSelControllo.setValoriInterni(valori);
                campoSelControllo.setUsaNonSpecificato(false);

                panTipoControllo = PannelloFactory.orizzontale(null);
                panTipoControllo.creaBordo("Opzioni di controllo");
                panTipoControllo.add(campoSelControllo);
            }// fine del blocco if

            /* aggiunge i pannelli al dialogo */
            this.addPannello(panInfo);
            this.addPannello(panDate);
            this.addPannello(panOpzioni);
            if (panTipoControllo != null) {
                this.addPannello(panTipoControllo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;

        try { // prova ad eseguire il codice

            confermabile = super.isConfermabile();

            /* controlla che le due date siano in sequenza */
            if (confermabile) {
                confermabile = Lib.Data.isSequenza(this.getDataInizio(), this.getDataFine());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo cambiato
     */
    @Override protected void eventoMemoriaModificata(Campo campo) {
    }


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoUscitaCampoModificato(Campo campo) {
    }


    protected void eventoUscitaCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo campoDataInizio;
        Campo campoDataFine;

        campoDataInizio = this.getCampo(DATA_INIZIO);
        campoDataFine = this.getCampo(DATA_FINE);

        if (campo.equals(campoDataInizio)) {
            campoDataFine.setValore(campoDataInizio.getValore());
        }// fine del blocco if
    }


    /**
     * Ritorna la data di inizio inserita nel dialogo.
     * <p/>
     *
     * @return la data di inizio
     */
    public Date getDataInizio() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Campo campo;

        /* valore di ritorno */
        try {    // prova ad eseguire il codice
            campo = this.getCampo(DATA_INIZIO);
            if (campo != null) {
                data = (Date)campo.getValore();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna la data di fine inserita nel dialogo.
     * <p/>
     *
     * @return la data di fine
     */
    public Date getDataFine() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Campo campo;

        /* valore di ritorno */
        try {    // prova ad eseguire il codice
            campo = this.getCampo(DATA_FINE);
            if (campo != null) {
                data = (Date)campo.getValore();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna il tipo di controllo selezionato.
     * <p/>
     *
     * @return il tipo di controllo
     */
    public int getTipoControllo() {
        /* variabili e costanti locali di lavoro */
        int tipo = 0;
        Campo campo;

        /* valore di ritorno */
        try {    // prova ad eseguire il codice
            campo = this.getCampo(TIPO_CONTROLLO);
            if (campo != null) {
                tipo = (Integer)campo.getValore();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    /**
     * Ritorna il valore dell'opzione Solo Quelli selezionati.
     * <p/>
     */
    public boolean isSoloSelezionati() {
        /* variabili e costanti locali di lavoro */
        boolean soloSelezionati = false;
        int sel;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(OPZIONI_CONTROLLO);
            if (campo != null) {
                sel = (Integer)campo.getValore();
                if (sel == 1) {
                    soloSelezionati = true;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return soloSelezionati;
    }


    private ArrayList<Integer> getCodiciConto() {
        return codiciConto;
    }


    private void setCodiciConto(ArrayList<Integer> codiciConto) {
        this.codiciConto = codiciConto;
    }


}// fine della classe
