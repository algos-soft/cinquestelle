/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-mar-2006
 */
package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.scheda.Scheda;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.wrapper.SetValori;

/**
 * Navigatore dei periodi nella prenotazione.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Gestisce la business-logic della GUI </li>
 * <li> Riceve le invocazioni da una classe di tipo <code>Gestore</code> </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 18.14.31
 */
public final class PeriodoNavPrenotazione extends NavigatoreLS implements Periodo {


    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public PeriodoNavPrenotazione(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

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
            this.setNomeChiave(Nav.prenotazione.toString());
            this.setUsaPannelloUnico(true);
            this.setUsaFinestraPop(true);
            this.setRigheLista(6);
            this.setUsaRicerca(false);
            this.setUsaStampaLista(true);
            this.setUsaStatusBarLista(false);
            this.setTipoIcona(ToolBar.ICONA_PICCOLA);
            this.setAggiornamentoTotaliContinuo(true);

            this.getLista().setOrdinabile(false);

            this.setNomeVista(Periodo.Vis.prenotazione.toString());


            this.addSchedaCorrente(new PeriodoScheda(this.getModulo()));

//            this.setUsaTransazioni(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.<br>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se non
     * riescono a portare a termine la propria inizializzazione specifica.<br>
     */
    @Override
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Ordine ordine;

        try { // prova ad eseguire il codice

            super.inizializza();

            campo = this.getModulo().getCampo(Periodo.Cam.arrivoPrevisto);
            ordine = new Ordine(campo);
            this.getLista().setOrdine(ordine);

            /* rende la finestra della scheda non modale */
            this.getPortaleScheda().getFinestraDialogo().setModal(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizializza


    @Override
    /**
     * Assegna al nuovo periodo le note di preparazione
     *  come da anagrafica cliente
     */
    protected boolean nuovoRecordAnte(SetValori set) {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice
            Campo campo = this.getCampoPilota();
            if (campo!=null) {
                Scheda scheda = campo.getScheda();
                if (scheda!=null) {
                    int codCliente = scheda.getInt(Prenotazione.Cam.cliente.get());
                    String notePrep = ClienteAlbergoModulo.get().query().valoreStringa(ClienteAlbergo.Cam.noteprep.get(), codCliente);
                    set.add(Periodo.Cam.noteprep, notePrep);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return super.nuovoRecordAnte(set);
    }



    /**
     * Invocato prima di eliminare un record esistente
     * <p/>
     * Metodo sovrascritto dalle sottoclassi.
     *
     * @param codice del record che sta per essere eliminato
     *
     * @return true se si può procedere
     */
    protected boolean eliminaRecordAnte(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        int causaleArrivo;
        int causalePartenza;
        int codCausaleCambio = CausaleAP.cambio.getCodice();
        boolean arrivato;
        MessaggioDialogo messaggio;
        String testo;

        try { // prova ad eseguire il codice

            continua = super.eliminaRecordAnte(codice);

            /* se il periodo è già arrivato non si può eliminare */
            if (continua) {
                arrivato = this.query().valoreBool(Periodo.Cam.arrivato.get(), codice);
                if (arrivato) {
                    testo = "Non è possibile eliminare il periodo.\n";
                    testo += "L'arrivo è già stato confermato.";
                    new MessaggioAvviso(testo);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se il periodo è collegato ad altri chiede conferma */
            if (continua) {
                causaleArrivo = this.query().valoreInt(Periodo.Cam.causaleArrivo.get(), codice);
                causalePartenza = this.query().valoreInt(Periodo.Cam.causalePartenza.get(), codice);
                if ((causaleArrivo == codCausaleCambio) | (causalePartenza == codCausaleCambio)) {
                    testo = "Attenzione! Esistono dei cambi camera.\n";
                    testo += "Confermi l'eliminazione del periodo?";
                    messaggio = new MessaggioDialogo(testo);
                    if (!messaggio.isConfermato()) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;

    }


    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di conseguenza <br>
     * <p/>
     */
    @Override
    public void sincronizza() {
        super.sincronizza();
    }



}// fine della classe
