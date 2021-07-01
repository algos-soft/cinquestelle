package it.algos.albergo.odg.odgriga;
/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-giu-2009
 */

import it.algos.albergo.camera.compoaccessori.WrapCompoAccessorio;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.odg.OdgLogica;
import it.algos.albergo.odg.odgaccessori.OdgAcc;
import it.algos.albergo.odg.odgaccessori.OdgAccModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.scheda.SchedaDefault;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Scheda specifica di una Riga di Odg
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alkex
 * @version 1.0    / 18-giu-2009 ore 15.17.14
 */
public final class OdgRigaScheda extends SchedaDefault implements OdgRiga {

    /* label con nome del cliente */
    private JLabel labelCliente;

    /* bottone per andare alla prenotazione */
    private JButton botVaiPren;

    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public OdgRigaScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
        try { // prova ad eseguire il codice

            /* crea e registra la label che mostra il nome del cliente */
            JLabel label = new JLabel();
            this.setLabelCliente(label);

            /* crea e registra il bottone per andare alla prenotazione */
            JButton bot = new JButton("Vai a prenotazione");
            Icon icona = PrenotazioneModulo.get().getIcona("prenotazione24");
            bot.setIcon(icona);
            bot.setOpaque(false);
            bot.setFocusable(false);
            bot.addActionListener(new AzVaiPren());
            this.setBotVaiPren(bot);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try { // prova ad eseguire il codice

            /* aggiunge  una pagina al libro con il set di default */

            Pannello pan1 = PannelloFactory.orizzontale(this);
            pan1.setAllineamento(Layout.ALLINEA_BASSO);
            pan1.setGapFisso(10);
            pan1.add(Cam.camera);
            pan1.add(Cam.arrivo);
            pan1.add(Cam.partenza);
            pan1.add(Cam.fermata);

            Pannello pan2 = PannelloFactory.orizzontale(this);
            pan2.setAllineamento(Layout.ALLINEA_BASSO);
            pan2.setGapFisso(10);
            pan2.add(Cam.cambio);
            pan2.add(Cam.cambioDa);
            pan2.add(Cam.cambioA);

            Pannello pan3 = PannelloFactory.orizzontale(this);
            pan3.setGapFisso(10);
            pan3.add(Cam.parteDomani);
            pan3.add(Cam.cambiaDomani);
            pan3.add(Cam.chiudere);

            Pannello pan4 = PannelloFactory.orizzontale(this);
            pan4.setAllineamento(Layout.ALLINEA_BASSO);
            pan4.setGapFisso(10);
            pan4.add(Cam.dafare);
            pan4.add(Cam.compoprecedente);
            pan4.add(Cam.composizione);
            pan4.add(Cam.note);

            Pannello pan5 = PannelloFactory.orizzontale(this);
            pan5.setAllineamento(Layout.ALLINEA_CENTRO);
            pan5.setGapFisso(10);
            pan5.add(this.getBotVaiPren());
            pan5.add(this.getLabelCliente());


            Pannello panSx = PannelloFactory.verticale(this);
            panSx.add(pan1);
            panSx.add(pan2);
            panSx.add(pan3);
            panSx.add(pan4);
            panSx.add(pan5);

            pagina = this.addPagina("generale");
            Pannello panTot = PannelloFactory.orizzontale(this);
            panTot.add(panSx);
            panTot.add(new JSeparator(JSeparator.VERTICAL));
            panTot.add(Cam.righeAccessori);
            pagina.add(panTot);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    @Override
    public void avvia(int codice) {
        try { // prova ad eseguire il codice

            super.avvia(codice);
            
            /* regola il nome del cliente */
            String nome = this.getNomeCliente();
            this.getLabelCliente().setText(nome);

            /* abilita il bottone Vai Prenotazione */
            int codPren = this.getCodPrenotazione();
            this.getBotVaiPren().setEnabled(codPren>0);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    

    @Override
    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice

            /**
             * se il campo Da Fare si spegne,
             * elimina gli accessori
             */
            if (this.isCampo(campo, Cam.dafare)) {
                if (!this.getBool(Cam.dafare.get())) {
                    this.eliminaAccessori();
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se il campo Da Fare si accende, ed esiste
             * la preparazione, rigenera gli accessori
             */
            if (this.isCampo(campo, Cam.dafare)) {
                if (this.getBool(Cam.dafare.get())) {
                    this.rigeneraAccessori();
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se la preparazione cambia, e il campo
             * Da Fare è acceso, rigenera gli accessori
             */
            if (this.isCampo(campo, Cam.composizione)) {
                if (this.getBool(Cam.dafare.get())) {
                    this.rigeneraAccessori();
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    @Override
    protected void eventoUscitaCampoModificato(Campo campo) {

        try { // prova ad eseguire il codice

//            /**
//             * se modifica le Note Preparazione, le trascrive nel periodo
//             */
//            if (this.isCampo(campo, Cam.note)) {
//                int codPeri = this.getCodPeriodo();
//                if (codPeri>0) {
//                    String nota = this.getString(Cam.note.get());
//                    PeriodoModulo.get().query().registra(codPeri, Periodo.Cam.noteprep.get(), nota);
//                }// fine del blocco if
//            }// fine del blocco if
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    @Override
    /**
     * Eseguito alla registrazione del record
     * <p>
     */
    protected boolean registraRecord() {
        /* variabili e costanti locali di lavoro */
        boolean registrato=false;

        try { // prova ad eseguire il codice

            /**
             * Ricopia le note Preparazione nel Periodo.
             * Disabiitato perché poco utile e crea confusione.
             * Alex 20-07-09
             */
//            if (!this.getBool(OdgRiga.Cam.fermata.get())) {
//                int codPeri = this.getCodPeriodo();
//                if (codPeri>0) {
//                    String nota = this.getString(Cam.note.get());
//                    PeriodoModulo.get().query().registra(codPeri, Periodo.Cam.noteprep.get(), nota);
//                }// fine del blocco if
//            }// fine del blocco if


            registrato = super.registraRecord();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return registrato;
    }


    /**
     * Ritorna il codice periodo relativo alla scheda corrente.
     * <p/>
     * @return il codice periodo
     */
    private int getCodPeriodo() {
        /* variabili e costanti locali di lavoro */
        int codPeriodo=0;
        int codPeriodo1, codPeriodo2;
        Modulo modRiga = OdgRigaModulo.get();


        try {    // prova ad eseguire il codice

            int codice = this.getCodice();
            if (codice>0) {
                codPeriodo2 = modRiga.query().valoreInt(Cam.periodo2.get(), codice);
                if (codPeriodo2>0) {
                    codPeriodo = codPeriodo2;
                } else {
                    codPeriodo1 = modRiga.query().valoreInt(Cam.periodo1.get(), codice);
                    if (codPeriodo1!=0) {
                        codPeriodo=codPeriodo1;
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPeriodo;
    }


    /**
     * Ritorna il codice prenotazione relativo alla scheda corrente.
     * <p/>
     * @return il codice prenotazione
     */
    private int getCodPrenotazione() {
        /* variabili e costanti locali di lavoro */
        int codPren=0;
        int codPeri;

        try {    // prova ad eseguire il codice
            codPeri = this.getCodPeriodo();
            if (codPeri>0) {
                codPren = PeriodoModulo.get().query().valoreInt(Periodo.Cam.prenotazione.get(), codPeri);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPren;
    }

    /**
     * Ritorna il nome del cliente relativo alla scheda corrente.
     * <p/>
     * @return il codice prenotazione
     */
    private String getNomeCliente() {
        /* variabili e costanti locali di lavoro */
        String nome="";
        int codPren;
        int codCli;

        try {    // prova ad eseguire il codice
            codPren = this.getCodPrenotazione();
            if (codPren>0) {
                codCli = PrenotazioneModulo.get().query().valoreInt(Prenotazione.Cam.cliente.get(), codPren);
                if (codCli>0) {
                    nome = ClienteAlbergoModulo.get().query().valoreStringa(Anagrafica.Cam.soggetto.get(), codCli);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    }


    /**
     * Elimina la lista degli accessori senza chiedere.
     * <p/>
     */
    private void eliminaAccessori() {
        try {    // prova ad eseguire il codice
            Modulo mod = OdgAccModulo.get();
            mod.query().eliminaRecords(this.getFiltroAccessori());
            this.getNavAccessori().aggiornaLista();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rigenera la lista degli accessori.
     * <p/>
     * (- Se esistono già dei record chiede conferma)
     * - Elimina i record esistenti
     * - genera i nuovi record
     */
    private void rigeneraAccessori() {
        try {    // prova ad eseguire il codice
            this.eliminaAccessori();
            this.creaAccessori();
            this.getNavAccessori().aggiornaLista();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea gli accessori per questa composizione.
     * <p/>
     */
    private void creaAccessori () {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codCompo;

        try {    // prova ad eseguire il codice
            codCompo = this.getInt(Cam.composizione.get());
            continua = (codCompo>0);
            if (continua) {
                ArrayList<WrapCompoAccessorio> lista = CompoCameraModulo.getAccessori(codCompo);
                OdgLogica.creaAccessori(this.getCodice(), lista);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }





    /**
     * Ritorna il filtro che seleziona gli accessori di questa scheda.
     * <p/>
     * Il filtro è applicabile al modulo OdgAccModulo.
     *
     * @return il filtro che seleziona gli accessori
     */
    private Filtro getFiltroAccessori() {
        return FiltroFactory.crea(OdgAcc.Cam.rigaOdg.get(), this.getCodice());
    }


    /**
     * Ritorna il navigatore degli accessori contenuto nella scheda.
     * <p/>
     *
     * @return il navigatore
     */
    private Navigatore getNavAccessori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            Campo campo = this.getCampo(Cam.righeAccessori);
            nav = campo.getNavigatore();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }

    /**
     * Apre la prenotazione relativa a questa scheda.
     * <p/>
     */
    private void vaiPrenotazione() {
        try {    // prova ad eseguire il codice
            int cod = this.getCodPrenotazione();
            if (cod>0) {
                PrenotazioneModulo.get().presentaRecord(cod);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }



    private JLabel getLabelCliente() {
        return labelCliente;
    }


    private void setLabelCliente(JLabel labelCliente) {
        this.labelCliente = labelCliente;
    }


    private JButton getBotVaiPren() {
        return botVaiPren;
    }


    private void setBotVaiPren(JButton botVaiPren) {
        this.botVaiPren = botVaiPren;
    }


    /**
     * Azione Vai Prenotazione
     * </p>
     */
    public final class AzVaiPren implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            vaiPrenotazione();
        }
    } // fine della classe 'interna'

}// fine della classe