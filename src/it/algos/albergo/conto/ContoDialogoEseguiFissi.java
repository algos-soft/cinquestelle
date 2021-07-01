/**
 * Title:     ContoDialogoEseguiFissi
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-giu-2006
 */
package it.algos.albergo.conto;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.costante.CostanteColore;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;

import javax.swing.JLabel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di impostazione della esecuzione addebiti fissi.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 15.00.06
 */
public final class ContoDialogoEseguiFissi extends DialogoAnnullaConferma {

    /* nomi dei campi del dialogo */
    private static final String nomeDataIni = "dal";

    private static final String nomeDataFine = "al";

    private static final String nomeOpzioni = "opzioni";

    ArrayList<Integer> codiciConto;


    /**
     * Costruttore completo con parametri
     * <p/>
     *
     * @param codiciConto elenco dei conti selezionati nel navigatore
     */
    public ContoDialogoEseguiFissi(ArrayList<Integer> codiciConto) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setCodiciConto(codiciConto);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
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
        String titolo = "Esecuzione addebiti fissi";
        int quantiConti;
        int cod;

        String sigla = "";
        String info;
        Color colore;
        JLabel labelInfo;

        Campo campoDataInizio;
        Campo campoDataFine;
        Date dataProposta;
        Modulo modConto;
        Pannello panInfo;
        Pannello panDate;
        Pannello panOperazione = null;
        String stringa;

        Campo campoOpzioni;

        try { // prova ad eseguire il codice


            this.setTitolo(titolo);

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
                info = "Vengono addebitati tutti i conti aperti";
                colore = CostanteColore.GIALLO;
            }// fine del blocco if-else
            panInfo = PannelloFactory.orizzontale(null);
            panInfo.setOpaque(false);
            labelInfo = new JLabel(info);
            labelInfo.setForeground(colore);
            panInfo.add(labelInfo);

            /* pannello date */
            dataProposta = AlbergoLib.getDataProgramma();
            campoDataInizio = CampoFactory.data(nomeDataIni);
            campoDataInizio.decora().obbligatorio();
            campoDataInizio.setValore(dataProposta);
            campoDataFine = CampoFactory.data(nomeDataFine);
            campoDataFine.decora().obbligatorio();
            campoDataFine.setValore(dataProposta);
            panDate = PannelloFactory.orizzontale(this.getModulo());
            panDate.creaBordo("Periodo da addebitare");
            panDate.add(campoDataInizio);
            panDate.add(campoDataFine);

            /* pannello tipo operazione */
            if (quantiConti > 0) {
                if (quantiConti == 1) {  // un solo conto
                    stringa = "Addebita solo il conto " + sigla;
                } else {  // piu' conti
                    stringa = "Addebita solo i " + quantiConti + " conti selezionati";
                }// fine del blocco if-else
                campoOpzioni = CampoFactory.radioInterno(nomeOpzioni);
                campoOpzioni.decora().obbligatorio();
                ArrayList<String> valori = new ArrayList<String>();
                valori.add(stringa);
                valori.add("Addebita tutti i conti aperti");
                campoOpzioni.setValoriInterni(valori);
                campoOpzioni.setUsaNonSpecificato(false);

                panOperazione = PannelloFactory.orizzontale(null);
                panOperazione.creaBordo("Opzioni di addebito");
                panOperazione.add(campoOpzioni);

            }// fine del blocco if

            /* aggiunge i pannelli al dialogo */
            this.addPannello(panInfo);
            this.addPannello(panDate);
            if (panOperazione != null) {
                this.addPannello(panOperazione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;
        Date d1, d2;

        try { // prova ad eseguire il codice
            confermabile = super.isConfermabile();

            /* controllo che le date siano in sequenza */
            if (confermabile) {
                d1 = this.getDataInizio();
                d2 = this.getDataFine();
                confermabile = Lib.Data.isSequenza(d1, d2);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;

    }


    private ArrayList<Integer> getCodiciConto() {
        return codiciConto;
    }


    private void setCodiciConto(ArrayList<Integer> codiciConto) {
        this.codiciConto = codiciConto;
    }


    /**
     * Ritorna la data di inizio impostata nel dialogo.
     * <p/>
     */
    public Date getDataInizio() {
        return (Date)this.getCampo(nomeDataIni).getValore();
    }


    /**
     * Ritorna la data di fine impostata nel dialogo.
     * <p/>
     */
    public Date getDataFine() {
        return (Date)this.getCampo(nomeDataFine).getValore();
    }

//    /**
//     * Ritorna il valore dell'opzione di sincronizzazione singolo conto.
//     * <p/>
//     *
//     * @return true se e' selezionata l'opzione di addebito di un singolo conto
//     */
//    public boolean isOpzioneSingolo() {
//        /* variabili e costanti locali di lavoro */
//        boolean singolo = false;
//        int opzione;
//
//        try { // prova ad eseguire il codice
////            if (this.getCodConto() > 0) {
////                opzione = (Integer)this.getCampo(nomeOpzioni).getValore();
////                if (opzione == 1) {
////                    singolo = true;
////                }// fine del blocco if
////            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return singolo;
//    }


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
            campo = this.getCampo(nomeOpzioni);
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
            campo = this.getCampo(nomeOpzioni);
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


}// fine della classe
