/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      13-giu-2006
 */
package it.algos.albergo.conto;


import it.algos.albergo.camera.Camera;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.campo.video.decorator.CVDBottone2stati;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Libreria;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.gestione.anagrafica.cliente.Cliente;

/**
 * Oggetto selettore di un conto aperto.
 * Permette all'utente di selezionare un conto seguendo criteri diversi.
 * <p/>
 * todo per adesso è bloccato su selezione per Conto, è da rivedere completamente
 * todo alex 27-04-07
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 13-giu-2006 ore 10.47.10
 */
public final class ContoSelettore extends PannelloFlusso {

    /* codice del conto correntemente selezionato */
    private int codConto;

    private Pannello panSwitch;

    private Campo campoSelezione;

    private Campo popConto;

    private Campo popCamera;

    private Campo popCliente;

    private static int LARGHEZZA_POP = 200;


    /**
     * Costruttore completo
     * <p/>
     */
    public ContoSelettore() {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

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
        Campo campoSelezione;
        Campo popConto;
        Campo popCamera;
        Campo popCliente;
        Pannello pan;
        Filtro filtroCompleto;
        Filtro filtro;

        try { // prova ad eseguire il codice

            /* regolazioni di questo pannello */
            this.setGapMassimo(8);
            this.setGapPreferito(4);
            this.creaBordo("conto");

            /* campo tipo selezione (per camera o per cliente) */
            campoSelezione = CampoFactory.radioInterno("selezione conto");
            this.setCampoSelezione(campoSelezione);
            campoSelezione.setValoriInterni(Conto.Selezione.getElenco());
            campoSelezione.setValoreIniziale(Conto.Selezione.camera.getCodice());
            campoSelezione.setUsaNonSpecificato(false);
            campoSelezione.setOrientamentoComponenti(Layout.ORIENTAMENTO_ORIZZONTALE);
            campoSelezione.decora().lucchetto(true);
//            campoSelezione.avvia();
//            this.add(campoSelezione);  // todo disabilitato 4-4-2007 da rivedere

            /* campo popup per conto */
            popConto = CampoFactory.comboLinkPop("camera");
            this.setPopConto(popConto);
            popConto.setNomeModuloLinkato(Conto.NOME_MODULO);
            popConto.setNomeCampoValoriLinkato(Conto.Cam.sigla.get());
            popConto.setLarScheda(LARGHEZZA_POP);
            popConto.decora().eliminaEtichetta();
            popConto.decora().lucchetto();
            popConto.setUsaNuovo(false);

            /* regola il filtro per vedere solo i conti aperti dell'azienda attiva */
            filtroCompleto = new Filtro();
            ContoModulo modConto = ContoModulo.get();
            filtro = modConto.getFiltroAzienda();
            filtroCompleto.add(filtro);
            filtro = FiltroFactory.crea(Conto.Cam.chiuso.get(), false);
            filtroCompleto.add(filtro);
            popConto.setFiltroCorrente(filtroCompleto);

            /* campo popup per camera */
            popCamera = CampoFactory.comboLinkPop("camera");
            this.setPopCamera(popCamera);
            popCamera.setNomeModuloLinkato(Camera.NOME_MODULO);
            popCamera.setNomeCampoValoriLinkato(Camera.CAMPO_SIGLA);
            popCamera.setLarScheda(LARGHEZZA_POP);
            popCamera.decora().eliminaEtichetta();
            popCamera.decora().lucchetto();
            popCamera.setUsaNuovo(false);
//            popCamera.avvia();

            /* campo popup per cliente */
            popCliente = CampoFactory.comboLinkSel("cliente");
            this.setPopCliente(popCliente);
            popCliente.setNomeModuloLinkato(Conto.NOME_MODULO);
            popCliente.addColonnaCombo(ClienteAlbergo.NOME_MODULO, Cliente.Cam.soggetto.get());
            popCliente.setLarScheda(LARGHEZZA_POP);
            popCliente.decora().eliminaEtichetta();
            popCliente.decora().lucchetto();
//            popCliente.avvia();

            /* aggiunge il listener al campo radio */
            campoSelezione.addListener(new RadioModificato());

            /* pannello contenitore dei due popup alternati */
            pan = new PannelloFlusso();
            this.setPanSwitch(pan);
            this.regolaPanSwitch();
            this.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    public void avvia() {
        this.getCampoSelezione().avvia();
        this.getPopConto().avvia();
        this.getPopCamera().avvia();
        this.getPopCliente().avvia();
        this.setCodiceConto(this.getCodConto());
    }// fine del metodo avvia


    /**
     * .
     * <p/>
     */
    private void regolaPanSwitch() {
        /* variabili e costanti locali di lavoro */
        Campo campoSel;
        Campo campoConto;
        Campo campoCamera;
        Campo campoCliente;
        Campo campo = null;
        Pannello panSwitch;
        int intero = 0;
        Object valore;
        boolean continua;
        Conto.Selezione tipo = null;


        try { // prova ad eseguire il codice
            campoSel = this.getCampoSelezione();
            campoConto = this.getPopConto();
            campoCamera = this.getPopCamera();
            campoCliente = this.getPopCliente();
            panSwitch = this.getPanSwitch();

            valore = campoSel.getValore();
            continua = (valore != null);

            if (continua) {
                intero = Libreria.getInt(valore);
                continua = (intero > 0);
            }// fine del blocco if

            if (continua) {
                tipo = Conto.Selezione.getTipoCodice(intero);
                continua = (tipo != null);
            }// fine del blocco if

            if (continua) {

                // todo per adesso è bloccato su conto
                tipo = Conto.Selezione.conto;

                switch (tipo) {
                    case conto:
                        campo = campoConto;
                        break;
                    case camera:
                        campo = campoCamera;
                        break;
                    case cliente:
                        campo = campoCliente;
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

            panSwitch.removeAll();
            panSwitch.add(campo);
            panSwitch.getPanFisso().validate();
            panSwitch.getPanFisso().repaint();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il codice del conto selezionato
     * <p/>
     *
     * @return il codice del conto selezionato, 0 se non selezionato.
     */
    public int getCodiceConto() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Campo campo = null;
        int codSel;
        int codConto;
        Conto.Selezione tipoSel;

        try {    // prova ad eseguire il codice
            codSel = (Integer)this.getCampoSelezione().getValore();

            // todo per adesso è bloccato su conto
            codSel = Conto.Selezione.conto.getCodice();

            if (codSel > 0) {
                tipoSel = Conto.Selezione.getTipoCodice(codSel);
                switch (tipoSel) {
                    case conto:
                        campo = this.getPopConto();
                        break;
                    case camera:
                        campo = this.getPopCamera();
                        break;
                    case cliente:
                        campo = this.getPopCliente();
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

                codConto = (Integer)campo.getValore();
                if (codConto > 0) {
                    codice = codConto;
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Predispone il selettore su un dato conto.
     * <p/>
     *
     * @param codice del conto da selezionare
     */
    public void setCodiceConto(int codice) {
        try {    // prova ad eseguire il codice
            this.setCodConto(codice);
            this.getPopConto().setValore(0);
            this.getPopCamera().setValore(0);
            this.getPopCliente().setValore(0);
            this.getPopConto().setValore(codice);
            this.getPopCamera().setValore(codice);
            this.getPopCliente().setValore(codice);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Blocca il selettore del conto.
     * <p/>
     * chiude il lucchetto
     */
    public void blocca() {
        /* variabili e costanti locali di lavoro */
        Campo popCamera;
        Campo popCliente;
        Campo popConto;
        CampoVideo cv;
        CVDBottone2stati cvd;

        try {    // prova ad eseguire il codice


            popCamera = this.getPopCamera();
            cv = popCamera.getCampoVideo();
            if (cv instanceof CVDBottone2stati) {
                cvd = (CVDBottone2stati)cv;
                cvd.setStato(true);
            }// fine del blocco if

            popCliente = this.getPopCliente();
            cv = popCliente.getCampoVideo();
            if (cv instanceof CVDBottone2stati) {
                cvd = (CVDBottone2stati)cv;
                cvd.setStato(true);
            }// fine del blocco if

            popConto = this.getPopConto();
            cv = popConto.getCampoVideo();
            if (cv instanceof CVDBottone2stati) {
                cvd = (CVDBottone2stati)cv;
                cvd.setStato(true);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Azione di modifica del valore di un campo
     */
    private class RadioModificato extends CampoMemoriaAz {

        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            regolaPanSwitch();
        }
    } // fine della classe interna


    private int getCodConto() {
        return codConto;
    }


    private void setCodConto(int codConto) {
        this.codConto = codConto;
    }


    private Pannello getPanSwitch() {
        return panSwitch;
    }


    private void setPanSwitch(Pannello panSwitch) {
        this.panSwitch = panSwitch;
    }


    private Campo getCampoSelezione() {
        return campoSelezione;
    }


    private void setCampoSelezione(Campo campoSelezione) {
        this.campoSelezione = campoSelezione;
    }


    private Campo getPopConto() {
        return popConto;
    }


    private void setPopConto(Campo popConto) {
        this.popConto = popConto;
    }


    private Campo getPopCamera() {
        return popCamera;
    }


    private void setPopCamera(Campo popCamera) {
        this.popCamera = popCamera;
    }


    private Campo getPopCliente() {
        return popCliente;
    }


    private void setPopCliente(Campo popCliente) {
        this.popCliente = popCliente;
    }


}// fine della classe
