package it.algos.albergo.preventivo;

import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.pensioni.PanAddebiti;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoLogica;
import it.algos.albergo.storico.Storico;
import it.algos.base.campo.base.Campo;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.evento.pannello.PanModificatoAz;
import it.algos.base.evento.pannello.PanModificatoEve;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;

import javax.swing.Icon;
import java.awt.Dimension;
import java.util.Date;

/**
 * Dialogo di conferma di un arrivo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-ott-2007 ore 19.21.46
 */
public class PreventivoDialogo extends DialogoAnnullaConferma {

    /* pannello dati prenotazione */
    private PanPrenotazione panPrenotazione;

    /* pannello gestione storico */
    private Storico panStorico;

    /* pannello gestione addebiti */
    private PanAddebiti panAddebiti;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public PreventivoDialogo() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* titolo del dialogo */
            this.setTitolo("Nuovo preventivo");

            /* testo del bottone di conferma */
            this.getBottoneConferma().setText("Prenotazione");


            this.setPanPrenotazione(new PanPrenotazione(this));
            this.setPanStorico(new Storico());
            this.getPanStorico().setPreferredSize(new Dimension(450, 300));
            this.getPanStorico().avvia(0);
            this.setPanAddebiti(new PanAddebiti());
            this.getPanAddebiti().addListener(PannelloBase.Evento.modifica, new AzPanOpzioniModificato());

            this.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    @Override
    public void inizializza() {
        Campo campo;
        Pannello panDialogo;
        Pannello panSinistra;
        Pannello panDestra;

        try { // prova ad eseguire il codice

            /* pannello generale e laterali (contenuti) */
            panDialogo = PannelloFactory.orizzontale(this);
            panSinistra = PannelloFactory.verticale(this);
            panDestra = PannelloFactory.verticale(this);

            panSinistra.add(this.getPanPrenotazione());
            panSinistra.add(this.getPanStorico());
            panDestra.add(this.getPanAddebiti());

            /* aggiunge il pannello-contenitore al dialogo */
            panDialogo.add(panSinistra);
            panDialogo.add(panDestra);
            this.addComponente(panDialogo.getPanFisso());

            super.inizializza();

            this.avvia();
            if (this.isConfermato()) {
                creaPrenotazione();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    @Override
    protected void eventoMemoriaModificata(Campo campo) {
        try { // prova ad eseguire il codice

            /**
             * se cambia la camera regola il popup delle composizioni possibili
             * e preseleziona la composizione standard
             */
            if (this.isCampo(campo, PanPrenotazione.Nome.camera.get())) {
                this.regolaPopComposizione(campo.getInt());
            }// fine del blocco if


            /* se modifico la composizione regola le persone */
            if (this.isCampo(campo, PanPrenotazione.Nome.preparazione.get())) {
                int codCompo = this.getInt(PanPrenotazione.Nome.preparazione.get());
                int numAdulti = CompoCameraModulo.getNumLettiAdulti(codCompo);
                int numBambini = CompoCameraModulo.getNumLettiBambini(codCompo);
                this.setValore(PanPrenotazione.Nome.adulti.get(), numAdulti);
                this.setValore(PanPrenotazione.Nome.bambini.get(), numBambini);

//                todo sarebbe da implementare anche qui, vedi PeriodoScheda
//                this.checkAddebiti();

            }// fine del blocco if


            /* se cambia il numero di persone aggiorno il pannello addebiti */
            if (this.isCampo(campo, PanPrenotazione.Nome.adulti.get(), PanPrenotazione.Nome.bambini.get())) {
                int adulti = this.getInt(PanPrenotazione.Nome.adulti.get());
                int bambini = this.getInt(PanPrenotazione.Nome.bambini.get());
                this.getPanAddebiti().setNumPersone(adulti+bambini);
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
    @Override protected void eventoUscitaCampoModificato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int codice;
        Date data;


        try { // prova ad eseguire il codice

            /* se cambia il cliente aggiorno lo storico */
            if (this.isCampo(campo, PanPrenotazione.Nome.cliente.get())) {
                codice = this.getInt(PanPrenotazione.Nome.cliente.get());
                this.getPanStorico().avvia(codice);
            }// fine del blocco if

            /* se cambia la data di arrivo aggiorno il pannello addebiti */
            if (this.isCampo(campo, PanPrenotazione.Nome.arrivo.get())) {
                data = this.getData(PanPrenotazione.Nome.arrivo.get());
                this.getPanAddebiti().setDataInizioGiorni(data);
            }// fine del blocco if

            /* se cambia la data di partenza aggiorno il pannello addebiti */
            if (this.isCampo(campo, PanPrenotazione.Nome.partenza.get())) {
                data = this.getData(PanPrenotazione.Nome.partenza.get());
                data = Lib.Data.add(data, -1);
                this.getPanAddebiti().setDataFineGiorni(data);
            }// fine del blocco if

            /**
             * se modifico data inizio, data fine o camera controlla la disponibilità
             * se non disponibile visualizza un messaggio e ripristina il backup del campo
             */
            if (this.isCampo(campo, PanPrenotazione.Nome.arrivo.get(), PanPrenotazione.Nome.partenza.get(), PanPrenotazione.Nome.camera.get())) {
                if (!this.isPeriodoValido()) {
                    int codPeriodo = this.getCodPeriodoSovrapposto();
                    String messaggio = PeriodoLogica.getMessaggioOccupato(codPeriodo);
                    new MessaggioAvviso("Periodo non disponibile\n" + messaggio);
                    campo.getCampoDati().restoreBackup();
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Controlla che il periodo corrente non si sovrapponga
     * (come camera e date) ad un altro periodo.
     * <p/>
     *
     * @return periodo valido
     */
    private boolean isPeriodoValido() {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        Date dataIni;
        Date dataEnd;
        int codCam;

        try { // prova ad eseguire il codice
            dataIni = Libreria.getDate(this.getValore(Periodo.Cam.arrivoPrevisto.get()));
            dataEnd = Libreria.getDate(this.getValore(Periodo.Cam.partenzaPrevista.get()));
            codCam = Libreria.getInt(this.getValore(Periodo.Cam.camera.get()));

            valido = isPeriodoValido(dataIni, dataEnd, codCam, 0);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla che un periodo non si sovrapponga (come camera e date) ad un altro periodo.
     * <p/>
     *
     * @param dataIni data di inizio periodo
     * @param dataEnd data di fine periodo
     * @param codCam codice della camera
     * @param codEscludi codice dell'eventuale periodo da escludere dalla ricerca
     *
     * @return periodo valido
     */
    private static boolean isPeriodoValido(Date dataIni, Date dataEnd, int codCam, int codEscludi) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        int codSovrapposto;

        try { // prova ad eseguire il codice

            codSovrapposto = PeriodoLogica.getCodPeriodoSovrapposto(dataIni,
                    dataEnd,
                    codCam,
                    codEscludi);

            if (codSovrapposto == 0) {
                valido = true;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Ritorna il codice dell'eventuale periodo che si sovrappone a quello corrente.
     * <p/>
     * Se ci fosse più di 1 periodo sovrapposto (in teoria non dovrebbe essere possibile),
     * torna il codice del primo periodo sovrapposto trovato.
     * Se non ci sono periodi sovrapposti, ritorna 0.
     *
     * @return il codice dell'eventuale primo periodo sovrapposto a quello corrente,
     *         0 se non ce ne sono
     */
    private int getCodPeriodoSovrapposto() {
        /* variabili e costanti locali di lavoro */
        int codSovrapposto = 0;
        Date dataIni;
        Date dataEnd;
        int codCam;

        try { // prova ad eseguire il codice
            dataIni = Libreria.getDate(this.getValore(Periodo.Cam.arrivoPrevisto.get()));
            dataEnd = Libreria.getDate(this.getValore(Periodo.Cam.partenzaPrevista.get()));
            codCam = Libreria.getInt(this.getValore(Periodo.Cam.camera.get()));
            codSovrapposto = PeriodoLogica.getCodPeriodoSovrapposto(dataIni, dataEnd,codCam,0);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codSovrapposto;

    }


    /**
     * Regola il popup composizione in base a una data camera.
     * <p/>
     * @param codCamera il codice della camera
     * Assegna
     */
    private void regolaPopComposizione (int codCamera) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice
            Filtro filtroComposizioni = CameraModulo.getFiltroComposizioniPossibili(codCamera);
            Campo campoCompo = this.getCampo(PanPrenotazione.Nome.preparazione.get());
            campoCompo.setFiltroBase(filtroComposizioni);
            campoCompo.avvia();

            int codStandard = CameraModulo.getComposizioneStandard(codCamera);
            campoCompo.setValore(codStandard);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }





    /**
     * Sincronizzazione della scheda/dialogo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override public void sincronizza() {

        try { // prova ad eseguire il codice

            super.sincronizza();

            /* Regola l'icona del bottone Crea Prenotazione */
            this.sincronizzaConferma();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola l'icona del bottone Crea Prenotazione
     */
    public void sincronizzaConferma() {
        /* variabili e costanti locali di lavoro */
        Icon iconaValido = Lib.Risorse.getIconaBase("Conferma24");
        Icon iconaErrore = Lib.Risorse.getIconaBase("chiudischeda24");
        Icon icona;

        try { // prova ad eseguire il codice

            if (this.isValido()) {
                icona = iconaValido;
            } else {
                icona = iconaErrore;
            }// fine del blocco if-else
            this.getBottoneConferma().setIcon(icona);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Determina se il dialogo e' confermabile o registrabile.
     * <p/>
     * Questo dialogo è sempre confermabile, i controlli sono
     * spostati a valle della conferma.
     *
     * @return true se confermabile / registrabile
     */
    @Override public boolean isConfermabile() {
        return true;
    }


    /**
     * Invocato quando si preme il bottone conferma o registra.
     * <p/>
     */
    @Override
    public void confermaRegistra() {
        if (this.isValido()) {
            super.confermaRegistra();
        } else {
            this.visualizzaErrore();
        }// fine del blocco if-else
    }


    /**
     * Mostra un messaggio con gli errori.
     * <p/>
     */
    private void visualizzaErrore() {
        /* variabili e costanti locali di lavoro */
        String errPren;
        String errAdd;
        String errore;

        try { // prova ad eseguire il codice

            errPren = this.getPanPrenotazione().getErrore().getTesto();
            errAdd = this.getPanAddebiti().getErrore().getTesto();
            errore = Lib.Testo.concatReturn("Impossibile creare la prenotazione", errPren, errAdd);
            new MessaggioAvviso(errore);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Controlla se il dialogo è valido.
     * <p/>
     *
     * @return true se valido
     */
    private boolean isValido() {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        boolean validoPren;
        boolean validoAddebiti;

        try {    // prova ad eseguire il codice
            validoPren = this.getPanPrenotazione().getErrore().isValido();
            validoAddebiti = this.getPanAddebiti().getErrore().isValido();
            valido = (validoPren && validoAddebiti);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Crea la nuova prenotazione in base ai dati impostati nel dialogo.
     * <p/>
     * 
     */
    private void creaPrenotazione() {
        /* variabili e costanti locali di lavoro */
        boolean valido;

        try { // prova ad eseguire il codice
            new MessaggioAvviso("Funzione disponibile prossimamente...");
            int a = 87;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }



    public void setDataInizio(Date data) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
//            campo = this.getCampo(NOME_CAMPO_DATA_ARRIVO);
//
//            if (campo != null) {
//                campo.setValore(data);
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private PanPrenotazione getPanPrenotazione() {
        return panPrenotazione;
    }


    private void setPanPrenotazione(PanPrenotazione panPrenotazione) {
        this.panPrenotazione = panPrenotazione;
    }


    private Storico getPanStorico() {
        return panStorico;
    }


    private void setPanStorico(Storico panStorico) {
        this.panStorico = panStorico;
    }


    private PanAddebiti getPanAddebiti() {
        return panAddebiti;
    }


    private void setPanAddebiti(PanAddebiti panAddebiti) {
        this.panAddebiti = panAddebiti;
    }


    /**
     * Azione pannello opzioni conto modificato
     */
    private class AzPanOpzioniModificato extends PanModificatoAz {

        public void panModificatoAz(PanModificatoEve unEvento) {
            sincronizzaConferma();
        }

    } // fine della classe interna

}// fine della classe