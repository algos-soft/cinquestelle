/**
 * Title:     FatturaScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-nov-2006
 */
package it.algos.gestione.fattura;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.campo.video.decorator.CVDCongelato;
import it.algos.base.campo.video.decorator.CVDecoratore;
import it.algos.base.campo.video.decorator.VideoFactory;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.evento.db.DbTriggerAz;
import it.algos.base.evento.db.DbTriggerEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.scheda.SchedaBase;
import it.algos.base.wrapper.CampoValore;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaModulo;
import it.algos.gestione.anagrafica.WrapDatiFatturazione;
import it.algos.gestione.fattura.fatt.FattModulo;
import it.algos.gestione.fattura.riga.RigaFattura;
import it.algos.gestione.fattura.riga.RigaFatturaModulo;
import it.algos.gestione.tabelle.contibanca.ContiBanca;
import it.algos.gestione.tabelle.contibanca.ContiBancaModulo;
import it.algos.gestione.tabelle.iva.Iva;
import it.algos.gestione.tabelle.iva.IvaModulo;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamento;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamentoModulo;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

/**
 * Scheda della Fattura.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 23 nov 2006
 */
public final class FattBaseScheda extends SchedaBase implements FattBase {

    private Pannello panConferma;

    private Pannello panPagamento;

    private Pannello panIvaSconto;

    private Pannello panTotali;


    /**
     * Costruttore completo.
     *
     * @param modulo di riferimento per la scheda
     */
    public FattBaseScheda(Modulo modulo) {

        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            /**
             * forza la dimensione della scheda che, avendo contenuti
             * di dimensione variabile, non può determinare correttamente
             * la propria dimensione all'inizializzazione
             */
            this.setPreferredSize(new Dimension(850, 600));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        Modello modello;


        try { // prova ad eseguire il codice

            /* inizializa nella superclasse */
            super.inizializza();

            /* si registra presso il modello della fattura */
            /* per ricevere gli eventi trigger */
            modulo = this.getModulo();
            modello = modulo.getModello();
            modello.addListener(Modello.Evento.trigger, new AzTriggerFattura());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    public void avvia(int codice) {
        super.avvia(codice);
        this.creaPanPagamento();
        this.creaPanTotali();
        this.regolaConferma();
        this.regolaCongelati();
        /* regola il contenuto del popup banche in funzione
         * del codice di pagamento e del cliente */
        this.regolaPopBanche(true, false);

    }


    protected void creaCampi() {

        super.creaCampi();

        /* elimina l'etichetta dal campo destinatario (prima di inizializzare!) */
        Campo campo = this.getCampo(Cam.destinatario.get());
        campo.decora().eliminaEtichetta();

    } /* fine del metodo */


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        Pagina pag1;
        Pagina pag2;
        Pagina pag3;
        Pannello pan;
        Pannello pan2;

        try { // prova ad eseguire il codice

            this.creaPanTotali();

            pag1 = this.addPagina("generale");
            pag2 = this.addPagina("contabile");
            pag3 = this.addPagina("trasporto");

            /* Pagina Generale */
            pag1.add(this.creaPanDocumento());
            pag1.add(Cam.righe);
            pag1.add(this.getPanTotali());

            /* Pagina Contabile */
            pag2.add(this.creaPanPagamento());
            pag2.add(this.creaPanIvaSconto());
            pag2.add(this.creaPanOpzioni());
            pag2.add(this.creaPanRiferimenti());
            pag2.add(Cam.estero);

            /* Pagina Trasporto */
            pag3.add(Cam.destinazione);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il pannello dati Cliente.
     * <p/>
     * (nome, p.i. / c.f.)
     *
     * @return il pannello creato
     */
    private Pannello creaPanDocumento() {
        /* variabili e costanti locali di lavoro */
        Pannello panTot = null;
        Pannello pan1;
        Pannello pan2;
        Pannello pan3;

        try {    // prova ad eseguire il codice

            /* pannello generale */
            panTot = PannelloFactory.orizzontale(this);

            /* pannello numero-data */
            pan1 = PannelloFactory.verticale(this);
            pan1.creaBordo();
            pan1.setGapMassimo(20);
            pan1.add(Cam.numeroDoc);
            pan1.add(Cam.dataDoc);
            panTot.add(pan1);

            /* pannello cliente */
            pan2 = PannelloFactory.verticale(this);
            pan2.creaBordo();
            pan2.setGapMassimo(6);
            pan2.add(Cam.cliente);
            pan2.add(Cam.destinatario);
            panTot.add(pan2);

            /* pannello conferma
             * (questo pannello viene anche registrato) */
            pan3 = PannelloFactory.verticale(this);
            pan3.creaBordo();
            pan3.setGapMassimo(20);
            this.setPanConferma(pan3);
            panTot.add(pan3);

            /* recupera l'altezza massima e fissa le dimensioni */
            int h1 = pan1.getPreferredSize().height;
            int h2 = pan2.getPreferredSize().height;
            int h3 = pan3.getPreferredSize().height;
            int hMax = Lib.Mat.getMax(h1, h2, h3);

            pan1.setPreferredHeigth(hMax);
            pan2.setPreferredHeigth(hMax);
            pan3.setPreferredSize(150, hMax);

            pan1.bloccaDim();
            pan2.bloccaDim();
            pan3.bloccaDim();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panTot;
    }


    /**
     * Crea il pannello codice iva / sconto.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanIvaSconto() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            /* se il pannello non esiste, lo crea ora */
            pan = this.getPanIvaSconto();
            if (pan == null) {
                pan = PannelloFactory.verticale(this);
                this.setPanIvaSconto(pan);
                pan.setUsaGapFisso(true);
                pan.setGapPreferito(6);
                pan.creaBordo(2);
            }// fine del blocco if

            /* svuota il pannello e aggiunge i componenti */
            pan.removeAll();
            pan.add(Cam.codIva);
            pan.add(Cam.percSconto);
            pan.getPanFisso().validate();
            this.repaint();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return this.getPanIvaSconto();
    }


    /**
     * Crea il pannello riferimenti nostri / cliente.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanRiferimenti() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.verticale(this);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(6);
            pan.creaBordo(2);
            pan.add(Cam.rifNostri);
            pan.add(Cam.rifCliente);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello dati pagamento.
     * <p/>
     * (condizioni di pagamento, scadenza)
     *
     * @return il pannello creato
     */
    private Pannello creaPanPagamento() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Pannello pan1 = null;
        Pannello pan2 = null;

        try {    // prova ad eseguire il codice

            /* se il pannello non esiste, lo crea ora */
            pan = this.getPanPagamento();
            if (pan == null) {
                pan = PannelloFactory.verticale(this);
                this.setPanPagamento(pan);
                pan.setUsaGapFisso(true);
                pan.setGapPreferito(6);
                pan.creaBordo(2);
            }// fine del blocco if

            pan1 = PannelloFactory.orizzontale(this);
            pan1.setUsaGapFisso(true);
            pan1.setGapPreferito(4);
            pan1.add(this.getCampo(FattBase.Cam.pagamento));
            pan1.add(this.getCampo(FattBase.Cam.dataScadenza));

            pan2 = PannelloFactory.orizzontale(this);
            pan2.setUsaGapFisso(true);
            pan2.setGapPreferito(4);
            pan2.add(this.getCampo(FattBase.Cam.contoBanca));
            pan2.add(this.getCampo(FattBase.Cam.coordBanca));

            /* svuota il pannello e aggiunge i componenti */
            pan.removeAll();
            pan.add(pan1);
            if (this.isUsaBanca()) {
                pan.add(pan2);
            }// fine del blocco if

            pan.getPanFisso().validate();
            this.repaint();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return this.getPanPagamento();

    }


    /**
     * Riempie il pannello Totali con i totali
     * pertinenti al documento corrente.
     * <p/>
     */
    private void creaPanTotali() {
        /* variabili e costanti locali di lavoro */
        Pannello panTotali = null;
        Pannello pan;

        try {    // prova ad eseguire il codice

            /* se il pannello non esiste, lo crea ora */
            panTotali = this.getPanTotali();
            if (panTotali == null) {
                /* pannello espandibile in orizzontale
                 * e allineato a destra */
                panTotali = PannelloFactory.verticale(this);
                this.setPanTotali(panTotali);
                panTotali.setAllineamento(Layout.ALLINEA_DX);
            }// fine del blocco if

            pan = PannelloFactory.orizzontale(this);

            /* se applica rivalsa, imponibile lordo e importo rivalsa */
            if ((Boolean)this.getValore(Cam.applicaRivalsa.get())) {
                pan.add(this.getCampo(Cam.imponibileLordo.get()));
                pan.add(this.getCampo(Cam.importoRivalsa.get()));
            }// fine del blocco if

            pan.add(this.getCampo(Cam.imponibileNetto.get()));
            pan.add(this.getCampo(Cam.importoIva.get()));

            /* se applica r.a., totale lordo e importo r.a. */
            if ((Boolean)this.getValore(Cam.applicaRA.get())) {
                pan.add(this.getCampo(Cam.totaleLordo.get()));
                pan.add(this.getCampo(Cam.importoRA.get()));
            }// fine del blocco if

            pan.add(this.getCampo(Cam.totaleNetto.get()));

            /* svuota il pannello e aggiunge i componenti */
            panTotali.removeAll();
            panTotali.add(pan);
            panTotali.sbloccaLarMax();
            panTotali.bloccaAltMax();
            this.getPanTotali().getPanFisso().validate();
            this.repaint();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Determina se il codice pagamento corrente prevede
     * l'ultilizzo di una banca.
     * <p/>
     *
     * @return true se usa la banca
     */
    private boolean isUsaBanca() {
        /* variabili e costanti locali di lavoro */
        boolean usaBanca = false;

        try {    // prova ad eseguire il codice
            usaBanca = (Boolean)this.getValore(Cam.usaBanca.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return usaBanca;
    }


    /**
     * Crea il pannello per le opzioni.
     * <p/>
     * (rivalsa INPS, ritenuta d'acconto)
     *
     * @return il pannello creato
     */
    private Pannello creaPanOpzioni() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.verticale(this);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(6);
            pan.creaBordo(2);
            pan.add(Cam.applicaRivalsa);
            pan.add(Cam.applicaRA);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Regola le etichette dei campi opzioni Rivalsa e R.A.
     * <p/>
     * Modifica le etichette in funzione dei valori percentuali
     * attualmente presenti nella scheda
     */
    private void regolaCampiOpzioni() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        double valDouble;
        int valInt;
        String testo;

        try { // prova ad eseguire il codice

            /* regola il campo Applica Rivalsa */
            testo = "Applica rivalsa INPS";
            campo = this.getCampo(FattBase.Cam.applicaRivalsa.get());
            if (this.getCodice() != 0) {
                valDouble = (Double)this.getValore(FattBase.Cam.percRivalsa.get());
                valInt = Libreria.getInt(valDouble * 100);
                testo += " " + valInt + "%";
            }// fine del blocco if-else
            campo.setTestoComponente(testo);

            /* regola il campo Applica R.A. */
            testo = "Applica R.A.";
            campo = this.getCampo(FattBase.Cam.applicaRA.get());
            if (this.getCodice() != 0) {
                valDouble = (Double)this.getValore(FattBase.Cam.percRA.get());
                valInt = Libreria.getInt(valDouble * 100);
                testo += " " + valInt + "%";
            }// fine del blocco if-else
            campo.setTestoComponente(testo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni dipendenti dallo stato del flag Confermato.
     * <p/>
     */
    private void regolaConferma() {
        /* variabili e costanti locali di lavoro */
        boolean confermato;
        Pannello pan;
        Campo campo;

        try { // prova ad eseguire il codice

            /* recupera lo stato */
            confermato = this.isConfermata();

            /* inserisce i campi nel pannello */
            pan = this.getPanConferma();
            if (pan != null) {
                pan.removeAll();
                if (confermato) {
                    pan.add(Cam.confermato);
                    pan.add(Cam.dataConferma);
                    pan.add(Cam.pagato);
                } else {
                    pan.add(Cam.confermato);
                }// fine del blocco if-else

                /* abilita / disabilita la scheda */
                this.setModificabile(!confermato);

                /* riabilita sempre il campo confermato */
                campo = this.getCampo(FattBase.Cam.confermato);
                campo.setModificabile(true);

                /* riabilita sempre il campo pagato */
                campo = this.getCampo(FattBase.Cam.pagato);
                campo.setModificabile(true);

                /* ridisegna il pannello conferma */
                pan.getPanFisso().revalidate();
                pan.getPanFisso().repaint();

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private void regolaCongelati() {
        /* variabili e costanti locali di lavoro */
        boolean congelato;
        CampoVideo campoVideo;
        CVDecoratore decoratore;
        CVDCongelato dec;

        try { // prova ad eseguire il codice
            congelato = this.isConfermata();

            /* traverso tutta la collezione */
            for (Campo campo : this.getCampi().values()) {
                campoVideo = campo.getCampoVideo();
//                if (campoVideo.isCongelato()) {

                decoratore = VideoFactory.getDecoratore(campoVideo, CVDCongelato.class);
                if (decoratore != null) {
                    dec = (CVDCongelato)decoratore;
                    dec.setFlag(congelato);
                    dec.avvia();
                }// fine del blocco if
//                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se la fattura corrente è confermabile
     * <p/>
     *
     * @return true se confermabile
     */
    private boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;
        FattBaseModello modello;
        int codice;

        try { // prova ad eseguire il codice
            codice = this.getCodice();
            modello = (FattBaseModello)this.getModulo().getModello();
            confermabile = modello.isConfermabile(codice);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Ritorna la motivazione per cui la scheda non è confermabile.
     * <p/>
     *
     * @return la motivazione, stringa vuota se confermabile
     */
    private String checkConfermabile() {
        /* variabili e costanti locali di lavoro */
        String spiega = "";
        FattBaseModello modello;
        int codice;

        try { // prova ad eseguire il codice
            codice = this.getCodice();
            modello = (FattBaseModello)this.getModulo().getModello();
            spiega = modello.checkConfermabile(codice);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return spiega;
    }


    private boolean isConfermata() {
        /* variabili e costanti locali di lavoro */
        boolean confermata = false;

        try { // prova ad eseguire il codice
            confermata = (Boolean)this.getCampo(Cam.confermato).getValore();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermata;
    }


    /**
     * Sincronizza i dati della scheda a seguito di
     * inserimento o modifica del cliente.
     * <p/>
     */
    private void syncDaCliente() {
        /* variabili e costanti locali di lavoro */
        FattModulo modFatt;
        int codCliente;
        int intero;
        double doppio;
        String testo;
        boolean flag = false;
        WrapDatiFatturazione wrapper;
        Anagrafica.ValoriOpzione opzione;

        try {    // prova ad eseguire il codice

            /* recupera alcune informazioni di uso comune */
            codCliente = (Integer)this.getValore(Cam.cliente.get());
            modFatt = FattModulo.get();

            /**
             * recupera i dati di fatturazione specifici del cliente
             * (non chiede al modulo Anagrafica ma al modulo Fattura, che
             * integra eventuali dati mancanti con i default di fattura)
             */
            wrapper = modFatt.getDatiFatturazione(codCliente);

            /* assegna la p.i. o c.f. */
            testo = wrapper.getPicf();
            this.setValore(Cam.picf.get(), testo);

            /* assegna codice pagamento */
            intero = wrapper.getCodPag();
            this.setValore(Cam.pagamento.get(), intero);

            /* assegna codice iva */
            intero = wrapper.getCodIva();
            this.setValore(Cam.codIva.get(), intero);

            /* assegna flag applica rivalsa */
            opzione = wrapper.getApplicaRivalsa();
            switch (opzione) {
                case si:
                    flag = true;
                    break;
                case no:
                    flag = false;
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
            this.setValore(Cam.applicaRivalsa.get(), flag);

            /* assegna flag applica r.a. */
            opzione = wrapper.getApplicaRA();
            switch (opzione) {
                case si:
                    flag = true;
                    break;
                case no:
                    flag = false;
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
            this.setValore(Cam.applicaRA.get(), flag);

            /* assegna la percentuale di r.a. */
            doppio = wrapper.getPercRA();
            this.setValore(Cam.percRA.get(), doppio);

            /* assegna l'etichetta destinatario */
            testo = wrapper.getEtichetta();
            this.setValore(Cam.destinatario.get(), testo);

            /* regola il contenuto del popup banche in funzione
             * del codice di pagamento e del cliente */
            this.regolaPopBanche(true, false);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il contenuto del popup banche | assegna la banca alla fattura
     * in funzione del cliente e del tipo di pagamento.
     * <p/>
     * Può eseguire una delle due azioni o entrambe a seconda dei parametri in ingresso.
     * <p/>
     * - Se manca il codice pagamento, svuota il popup | nessuna banca
     * - Se esiste il codice pagamento:
     * - Se il pagamento non usa la banca, svuota il popup | nessuna banca
     * - Se il pagamento usa la banca, controlla il tipo di banca:
     * - Se usa banca nostra, riempie con le banche nostre | assegna nostra banca preferita
     * - Se usa la banca cliente, riempie con le banche cliente | assegna banca preferita cliente
     *
     * @param regolaFiltro true per regolare il filtro del popup
     * @param assegnaBanca true per assegnare la banca alla fattura
     */
    private void regolaPopBanche(boolean regolaFiltro, boolean assegnaBanca) {
        /* variabili e costanti locali di lavoro */
        int codPaga;
        Filtro filtroVuoto;
        Filtro filtro = null;
        Campo campoChiaveConti;
        Campo campoConto;
        Modulo pagaModulo;
        ContiBancaModulo contoBancaModulo;
        AnagraficaModulo anagraficaModulo;
        boolean usaBanca;
        int qualeBanca;
        int codCliente;
        String nomeCampo;
        int codBanca = 0;

        try {    // prova ad eseguire il codice

            contoBancaModulo = ContiBancaModulo.get();
            campoChiaveConti = contoBancaModulo.getCampoChiave();
            campoConto = this.getCampo(Cam.contoBanca.get());
            filtroVuoto = FiltroFactory.crea(campoChiaveConti, -1);

            /* recupera il codice pagamento */
            codPaga = (Integer)this.getValore(Cam.pagamento.get());
            if (codPaga != 0) { // esiste cod pagamento

                /* controlla se il pagamento usa la banca */
                pagaModulo = TipoPagamentoModulo.get();
                nomeCampo = TipoPagamento.Cam.usaBanca.get();
                usaBanca = pagaModulo.query().valoreBool(nomeCampo, codPaga);
                if (usaBanca) { // il pagamento usa la banca
                    nomeCampo = TipoPagamento.Cam.qualeBanca.get();
                    qualeBanca = pagaModulo.query().valoreInt(nomeCampo, codPaga);

                    if (qualeBanca == 1) {  // banca nostra
                        filtro = FiltroFactory.crea(ContiBanca.Cam.soggetto.get(), 0);
                        codBanca = contoBancaModulo.getNostroContoPreferito();
                    } else {    // banca cliente
                        codCliente = (Integer)this.getValore(Cam.cliente.get());
                        filtro = FiltroFactory.crea(ContiBanca.Cam.soggetto.get(), codCliente);
                        anagraficaModulo = AnagraficaModulo.get();
                        codBanca = anagraficaModulo.getContoPreferito(codCliente);
                    }// fine del blocco if-else

                } else {   // il pagamento non usa la banca
                    filtro = filtroVuoto;
                    codBanca = 0;
                }// fine del blocco if-else

            } else { // manca cod pagamento
                filtro = filtroVuoto;
                codBanca = 0;
            }// fine del blocco if-else

            /* se richiesto regola il filtro del popup */
            if (regolaFiltro) {
                campoConto.setFiltroCorrente(filtro);
                campoConto.avvia();
            }// fine del blocco if

            /* se richiesto assegna la banca alla fattura */
            if (assegnaBanca) {
                campoConto.setValore(codBanca);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sincronizza i codici IVA delle righe con la scheda
     * <p/>
     * Propone ed eventualmente effettua la sincronizzazione
     * dei codici IVA delle righe con il codice IVA di default
     * della scheda.<br>
     * Invocato quando si modifica il codice IVA di default della scheda.<br>
     * Controlla se tutte le righe hanno lo stesso codice IVA<br>
     * - Se è lo stesso per tutte le righe, propone ed eventualmente esegue la
     * modifica delle righe
     * - Se non è lo stesso per tutte le righe, avvisa che non può effettuare
     * la modifica
     */
    private void syncIvaRighe() {
        /* variabili e costanti locali di lavoro */
        Modulo modIva;
        Filtro filtro;
        ArrayList valori;
        Integer lastVal = 0;
        Integer currVal = 0;
        boolean continua;
        boolean misti = false;
        boolean modifica = false;
        MessaggioDialogo dialogo;
        int risposta;
        int[] chiavi;
        int codIvaNuovo = 0;
        double percIvaNuovo;
        boolean esenteNuovo;
        int codIvaEsistente;
        ArrayList<CampoValore> valoriNuovi;
        CampoValore cv;
        Navigatore navRighe;

        try {    // prova ad eseguire il codice

            /* recupera il navigatore delle righe fattura */
            navRighe = this.getNavRighe();

            /* recupera la lista dei codici IVA delle righe fattura */
            filtro = FiltroFactory.crea(RigaFattura.Cam.fattura.get(), this.getCodice());
            valori = navRighe.query().valoriCampo(RigaFattura.Cam.codIva.get(), filtro);

            /* controlla se ci sono righe */
            continua = valori.size() > 0;

            /* controlla se i codici IVA sono misti */
            if (continua) {
                lastVal = (Integer)valori.get(0);
                for (Object valore : valori) {
                    if (valore instanceof Integer) {
                        currVal = (Integer)valore;
                        if (!currVal.equals(lastVal)) {
                            misti = true;
                            break;
                        }// fine del blocco if
                        lastVal = currVal;
                    }// fine del blocco if
                }// fine del blocco for
            }// fine del blocco if

            /* se i valori sono misti, messaggio di avviso
             * se i valori sono uniformi, e diversi dal nuovo valore, propone modifica */
            if (continua) {
                if (!misti) {

                    codIvaNuovo = (Integer)this.getValore(FattBase.Cam.codIva.get());

                    if (currVal != codIvaNuovo) {
                        dialogo = new MessaggioDialogo(
                                "Vuoi modificare il codice IVA anche nelle righe?");
                        risposta = dialogo.getRisposta();
                        if (risposta == 0) {
                            modifica = true;
                        }// fine del blocco if
                    }// fine del blocco if

                } else {
                    new MessaggioAvviso("Le righe contengono codici IVA diversi," +
                            "\neventualmente vanno modificati manualmente");
                }// fine del blocco if-else
            }// fine del blocco if

            /* se confermato modifica i codici IVA delle righe */
            if (modifica) {

                /* recupera codice iva, flag esente e percentuale iva nuovi */
                modIva = IvaModulo.get();
//                codIvaNuovo = (Integer)this.getValore(FattBase.Cam.codIva.get());
                percIvaNuovo = modIva.query().valoreDouble(Iva.Cam.valore.get(), codIvaNuovo);
//                esenteNuovo = modIva.query().valoreBool(Iva.Cam.fuoricampo.get(), codIvaNuovo);

                /* prepara una lista con i nuovi valori da registare */
                valoriNuovi = new ArrayList<CampoValore>();
                cv = new CampoValore(RigaFattura.Cam.codIva.get(), codIvaNuovo);
                valoriNuovi.add(cv);
                cv = new CampoValore(RigaFattura.Cam.percIva.get(), percIvaNuovo);
                valoriNuovi.add(cv);
//                cv = new CampoValore(RigaFattura.Cam.flagNonImponibile.get(), esenteNuovo);
//                valoriNuovi.add(cv);

                /* spazzola le righe e modifica quelle con codice iva diverso */
                chiavi = navRighe.query().valoriChiave(filtro);
                for (int chiave : chiavi) {
                    codIvaEsistente = navRighe.query().valoreInt(RigaFattura.Cam.codIva.get(),
                            chiave);
                    if (codIvaEsistente != codIvaNuovo) {
                        navRighe.query().registraRecordValori(chiave, valoriNuovi);
                    }// fine del blocco if
                }

                /* aggiorna la visualizzazione delle righe */
                navRighe.avvia();

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Sincronizza lo sconto delle righe con la scheda
     * <p/>
     * Propone ed eventualmente effettua la sincronizzazione
     * dei degli sconti delle righe con lo sconto di default della scheda.<br>
     * Invocato quando si modifica lo sconto di default della scheda.<br>
     * Controlla se tutte le righe hanno lo stesso sconto<br>
     * - Se è lo stesso per tutte le righe, propone ed eventualmente esegue la
     * modifica delle righe
     * - Se non è lo stesso per tutte le righe, avvisa che non può effettuare
     * la modifica
     */
    private void syncScontoRighe() {
        /* variabili e costanti locali di lavoro */
        Navigatore navRighe;
        Campo campoScontoRiga;
        Modulo modRighe;
        Filtro filtro;
        ArrayList valori;
        Double lastVal;
        Double currVal;
        boolean continua;
        boolean diversi = false;
        boolean modifica = false;
        MessaggioDialogo dialogo;
        int risposta;
        int[] chiavi;
        double scontoNuovo;
        Double scontoEsistente;
        Campo campoRighe;

        try {    // prova ad eseguire il codice

            /* recupera il navigatore delle righe fattura */
            navRighe = this.getNavRighe();

            /* recupera la lista dei codici IVA delle righe */
            modRighe = RigaFatturaModulo.get();
            campoScontoRiga = modRighe.getCampo(RigaFattura.Cam.percSconto.get());
            filtro = FiltroFactory.crea(RigaFattura.Cam.fattura.get(), this.getCodice());
            valori = navRighe.query().valoriCampo(campoScontoRiga, filtro);

            /* controlla se ci sono righe */
            continua = valori.size() > 0;

            /* controlla se la lista ha tutti valori uguali */
            if (continua) {
                lastVal = (Double)valori.get(0);
                for (Object valore : valori) {
                    if (valore instanceof Double) {
                        currVal = (Double)valore;
                        if (!currVal.equals(lastVal)) {
                            diversi = true;
                            break;
                        }// fine del blocco if
                        lastVal = currVal;
                    }// fine del blocco if
                }// fine del blocco for
            }// fine del blocco if

            /* se i valori sono diversi, messaggio
             * se i valori sono uguali, propone modifica */
            if (continua) {
                if (!diversi) {
                    dialogo = new MessaggioDialogo("Vuoi modificare lo sconto anche nelle righe?");
                    risposta = dialogo.getRisposta();
                    if (risposta == 0) {
                        modifica = true;
                    }// fine del blocco if
                } else {
                    new MessaggioAvviso("Le righe contengono sconti diversi," +
                            "\neventualmente vanno modificati manualmente");
                }// fine del blocco if-else
            }// fine del blocco if

            /* se confermato modifica gli sconti delle righe */
            if (modifica) {

                /* recupera il nuovo valore di sconto */
                scontoNuovo = (Double)this.getValore(FattBase.Cam.percSconto.get());

                /* spazzola le righe e modifica quelle con codice iva diverso */
                chiavi = navRighe.query().valoriChiave(filtro);
                for (int chiave : chiavi) {
                    scontoEsistente =
                            navRighe.query().valoreDouble(RigaFattura.Cam.percSconto.get(), chiave);
                    if (scontoEsistente != scontoNuovo) {
                        navRighe.query().registraRecordValore(chiave, campoScontoRiga, scontoNuovo);
                    }// fine del blocco if
                }

                /* aggiorna la visualizzazione delle righe */
                campoRighe = this.getCampo(FattBase.Cam.righe.get());
                campoRighe.avvia();


            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }

//    /**
//     * Aggiorna i totali della scheda per i campi non calcolati
//     * rileggendoli dal database.
//     * <p/>
//     * Legge i campi Imponibile Lordo, Importo iva Lordo<br>
//     * Questi campi vengono aggiornati dai trigger
//     * (I campi calcolati vengono aggiornati automaticamente)
//     */
//    public void aggiornaTotali() {
//        /* variabili e costanti locali di lavoro */
//        Navigatore navScheda;
//        int cod;
//        Double totImpo;
//        Double totIva;
//
//        Campo campoImp;
//        Campo campoIva;
//
//
//        try { // prova ad eseguire il codice
//
//            navScheda = this.getNavigatore();
//            cod = this.getCodice();
//
//            campoImp = this.getCampo(Fattura.Cam.imponibileLordo);
//            campoIva = this.getCampo(Fattura.Cam.importoIvaLordo);
//
//            totImpo = navScheda.query().valoreDouble(Fattura.Cam.imponibileLordo.get(), cod);
//            totIva = navScheda.query().valoreDouble(Fattura.Cam.importoIvaLordo.get(), cod);
//
//            campoImp.setValore(totImpo);
//            campoIva.setValore(totIva);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    @Override protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean flag;
        boolean confermato;
        Modulo modPaga;

        try { // prova ad eseguire il codice

            /* se cambia il cliente sincronizza la fattura in base ai dati cliente */
            if (campo.equals(this.getCampo(FattBase.Cam.cliente.get()))) {
                this.syncDaCliente();
            }// fine del blocco if

            /* se cambia il codice iva sincronizza la percentuale
             * e tenta di modificare anche le righe */
            if (campo.equals(this.getCampo(FattBase.Cam.codIva))) {
                IvaModulo modIva = IvaModulo.get();
                int codIva = (Integer)campo.getValore();
                Campo campoPercIvaTab = modIva.getCampo(Iva.Cam.valore.get());
                double percIva = modIva.query().valoreDouble(campoPercIvaTab, codIva);
                Campo campoPercIvaFt = this.getCampo(FattBase.Cam.percIva.get());
                campoPercIvaFt.setValore(percIva);
                this.syncIvaRighe();
            }// fine del blocco if

            /* se si vuole attivare il flag confermato chiede ulteriore conferma
             * se confermato registra la data conferma di oggi */
            if (campo.equals(this.getCampo(FattBase.Cam.confermato.get()))) {
                flag = (Boolean)campo.getValore();
                if (flag) {     // voglio confermare

                    boolean eseguito = false;

                    /* controlla se la scheda è modificata (escluso il campo Confermato)*/
                    boolean modificata = this.isModificata();
                    if (modificata) {
                        ArrayList<Campo> campiMod = null;
                        campiMod = this.getCampiModificati();
                        if (campiMod != null) {
                            if (campiMod.size() == 1) {
                                Campo unCampo = campiMod.get(0);
                                if (unCampo.equals(campo)) {
                                    modificata = false;
                                }// fine del blocco if
                            }// fine del blocco if
                        }// fine del blocco if

                    }// fine del blocco if

                    /* controlla se la scheda è modificata */
                    if (!modificata) {   // scheda non modificata
                        if (this.isConfermabile()) {  // scheda confermabile
                            confermato = DialogoFactory.getConferma("Conferma fattura",
                                    "Vuoi confermare la fattura?");
                            if (confermato) {
                                eseguito = true;
                            }// fine del blocco if
                        } else {  // scheda non confermabile
                            String testo = this.checkConfermabile();
                            new MessaggioAvviso("Questa fattura non è confermabile.\n" + testo);
                        }// fine del blocco if-else
                    } else {    // scheda modificata
                        new MessaggioAvviso("Bisogna prima registrare la scheda.");
                    }// fine del blocco if-else

                    /* operazioni conclusive */
                    if (eseguito) { // sono riuscito, assegno anche la data
                        this.setValore(Cam.dataConferma.get(), Lib.Data.getCorrente());
                    } else {  // non sono riuscito, ripristino il flag
                        campo.setValore(false);
                    }// fine del blocco if-else

                }// fine del blocco if-else
                this.regolaConferma();
            }// fine del blocco if

            /**
             * se cambia il flag applica rivalsa:
             * - registra il campo della fattura per aggiornare i totali
             * - ricostruisce il pannello totali
             */
            if (campo.equals(this.getCampo(FattBase.Cam.applicaRivalsa))) {
                this.registraCampo(campo);
                this.creaPanTotali();
            }// fine del blocco if

            /* se cambia il flag applica r.a. ricostruisce il pannello totali */
            if (campo.equals(this.getCampo(FattBase.Cam.applicaRA))) {
                this.creaPanTotali();
            }// fine del blocco if

            /* se cambia la percentuale di rivalsa registra il campo della fattura
             * per aggiornare i totali */
            if (campo.equals(this.getCampo(FattBase.Cam.percRivalsa))) {
                this.registraCampo(campo);
            }// fine del blocco if

            /* se cambia la percentuale iva registra il campo della fattura
             * per aggiornare i totali */
            if (campo.equals(this.getCampo(FattBase.Cam.percIva))) {
                this.registraCampo(campo);
            }// fine del blocco if

            /**
             * se cambia il codice pagamento:
             * - ricalcola la scadenza
             * - riassegna il valore al campo Usa Banca della fattura
             * - ricrea il pannello pagamento
             * - regola il filtro del popup Banche
             * - regola il valore del popup Banche
             */
            if (campo.equals(this.getCampo(FattBase.Cam.pagamento))) {

                boolean usaBanca = false;
                modPaga = TipoPagamentoModulo.get();
                Campo campoUsa = this.getCampo(FattBase.Cam.usaBanca);
                Campo campoDataScad = this.getCampo(FattBase.Cam.dataScadenza);
                Campo campoUsaBanca = modPaga.getCampo(TipoPagamento.Cam.usaBanca.get());
                int codPaga = (Integer)campo.getValore();
                if (codPaga != 0) {
                    usaBanca = modPaga.query().valoreBool(campoUsaBanca, codPaga);
                }// fine del blocco if
                campoUsa.setValore(usaBanca);
                campoDataScad.setValore(this.getDataScadenza());
                this.creaPanPagamento();

                /* regola il contenuto del popup banche in funzione
                 * del codice di pagamento e del cliente e assegna
                 * la banca da utilizzare */
                this.regolaPopBanche(true, true);

            }// fine del blocco if

            /**
             * se cambia il conto bancario, ricalcola le coordinate bancarie
             */
            if (campo.equals(this.getCampo(FattBase.Cam.contoBanca))) {
                this.syncCoordinate();
            }// fine del blocco if

            /**
             * se cambia il flag estero, ricalcola le coordinate bancarie
             */
            if (campo.equals(this.getCampo(FattBase.Cam.estero))) {
                this.syncCoordinate();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoUscitaCampoModificato(Campo campo) {
        try { // prova ad eseguire il codice

            /* se cambia lo sconto di default tenta di modificare anche le righe */
            if (campo.equals(this.getCampo(FattBase.Cam.percSconto.get()))) {
                this.syncScontoRighe();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void sincronizza() {

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* Regola le etichette dei campi opzioni Rivalsa e R.A. */
            this.regolaCampiOpzioni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna la data di scadenza in funzione della data del
     * documento e del codice di pagamento correnti.
     * <p/>
     *
     * @return la data di scadenza
     */
    private Date getDataScadenza() {
        /* variabili e costanti locali di lavoro */
        Date dataScad = Lib.Data.getVuota();
        Date dataDoc = null;
        TipoPagamentoModulo modPaga;
        int codPaga;

        try {    // prova ad eseguire il codice

            modPaga = TipoPagamentoModulo.get();
            codPaga = (Integer)this.getValore(FattBase.Cam.pagamento.get());
            if (codPaga != 0) {
                dataDoc = (Date)this.getValore(FattBase.Cam.dataDoc.get());
                dataScad = modPaga.getDataScadenza(codPaga, dataDoc);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dataScad;
    }


    /**
     * Assegna le coordinate bancarie in base al conto bancario
     * corrente e al flag Estero.
     * <p/>
     */
    private void syncCoordinate() {

        try {    // prova ad eseguire il codice

            String stringa = "";
            Campo campoConto = this.getCampo(Cam.contoBanca.get());
            int codice = (Integer)campoConto.getValore();
            if (codice != 0) {
                boolean estero = (Boolean)this.getValore(Cam.estero.get());
                ContiBancaModulo modConti = ContiBancaModulo.get();
                stringa = modConti.getStringaCoordinate(codice, estero);
            }// fine del blocco if
            Campo campoCoord = this.getCampo(Cam.coordBanca);
            campoCoord.setValore(stringa);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Registra il valore corrente di un campo della scheda sul database.
     * <p/>
     * Usa la connessione della scheda.
     *
     * @param campo del quale registrare il valore corrente
     */
    private void registraCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> lista;
        CampoValore cv;
        Connessione conn;

        try {    // prova ad eseguire il codice
            lista = new ArrayList<CampoValore>();
            cv = new CampoValore(campo, campo.getValore());
            lista.add(cv);
            conn = this.getConnessione();
            conn.registraRecordValori(this.getModulo(), this.getCodice(), lista);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera il navigatore delle righe di fattura.
     * <p/>
     *
     * @return il navigatore delle righe
     */
    private Navigatore getNavRighe() {
        /* variabili e costanti locali di lavoro */
        Campo campoRighe;
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            campoRighe = this.getCampo(FattBase.Cam.righe.get());
            nav = campoRighe.getNavigatore();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Azione eseguita quando viene creata/modificata/cancellata
     * una fattura.
     * <p/>
     * Se la fattura è stata modificata, aggiorna i valori
     * dei campi non calcolati in scheda
     */
    private class AzTriggerFattura extends DbTriggerAz {

        /**
         * Metodo invocato dal trigger.
         * <p/>
         *
         * @param evento evento che causa l'azione da eseguire <br>
         */
        public void dbTriggerAz(DbTriggerEve evento) {
            /* variabili e costanti locali di lavoro */
            Db.TipoOp tipo;
            ArrayList<CampoValore> valoriNew;
            Campo campo;
            CampoValore cv;
            double valore;


            try { // prova ad eseguire il codice

                /* recupera le informazioni dall'evento */
                tipo = evento.getTipo();
                valoriNew = evento.getValoriNew();

                /* campo imponibile lordo */
                if (tipo.equals(Db.TipoOp.modifica)) {
                    campo = getCampo(FattBase.Cam.imponibileLordo);
                    cv = Lib.Camp.getCampoValore(valoriNew, campo);
                    if (cv != null) {
                        valore = (Double)cv.getValore();
                        campo.setValore(valore);
                    }// fine del blocco if
                }// fine del blocco if

                /* campo importo iva */
                if (tipo.equals(Db.TipoOp.modifica)) {
                    campo = getCampo(FattBase.Cam.importoIva);
                    cv = Lib.Camp.getCampoValore(valoriNew, campo);
                    if (cv != null) {
                        valore = (Double)cv.getValore();
                        campo.setValore(valore);
                    }// fine del blocco if
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }

    }


    private Pannello getPanConferma() {
        return panConferma;
    }


    private void setPanConferma(Pannello panConferma) {
        this.panConferma = panConferma;
    }


    private Pannello getPanPagamento() {
        return panPagamento;
    }


    private void setPanPagamento(Pannello panPagamento) {
        this.panPagamento = panPagamento;
    }


    private Pannello getPanIvaSconto() {
        return panIvaSconto;
    }


    private void setPanIvaSconto(Pannello panIvaSconto) {
        this.panIvaSconto = panIvaSconto;
    }


    private Pannello getPanTotali() {
        return panTotali;
    }


    private void setPanTotali(Pannello panTotali) {
        this.panTotali = panTotali;
    }
} // fine della classe
