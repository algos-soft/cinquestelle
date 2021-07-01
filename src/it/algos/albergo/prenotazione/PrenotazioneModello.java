/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 16-5-2007
 */

package it.algos.albergo.prenotazione;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.albergo.tabelle.canaleprenotazione.Canale;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.WrapFiltri;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.tabelle.mezzopagamento.MezzoPagamento;

import java.util.ArrayList;
import java.util.Date;

/**
 * Tracciato record della tavola Periodi.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 16-5-2007
 */
public final class PrenotazioneModello extends ModelloAlgos implements Prenotazione {

    /**
     * Costruttore completo senza parametri.
     */
    public PrenotazioneModello() {
        /* rimanda al costruttore della superclasse */
        super();

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
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(Prenotazione.NOME_TAVOLA);
    }


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli
     * @see it.algos.base.modello.ModelloAlgos#creaCampi
     * @see it.algos.base.campo.base.CampoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo azienda */
            unCampo = CampoFactory.comboLinkPop(Cam.azienda);
            unCampo.setNomeModuloLinkato(Azienda.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Azienda.CAMPO_SIGLA);
            unCampo.decora().obbligatorio();
            unCampo.setLarScheda(80);
            unCampo.setUsaNonSpecificato(false);
            this.addCampo(unCampo);

            /* campo cliente */
            unCampo = CampoFactory.comboLinkSel(Cam.cliente);
            unCampo.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Anagrafica.Cam.soggetto.get());
            unCampo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            unCampo.setLarghezza(150);
            unCampo.decora().obbligatorio();
            unCampo.setUsaModifica(true);
            unCampo.setRicercabile(true);
            unCampo.addColonnaCombo(ClienteAlbergo.Cam.parentela.get());
            unCampo.addColonnaCombo(ModelloAlgos.NOME_CAMPO_PREFERITO);
            this.addCampo(unCampo);

            /* campo data prenotazione  */
            unCampo = CampoFactory.data(Cam.dataPrenotazione);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo data scadenza  */
            unCampo = CampoFactory.data(Cam.dataScadenza);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo opzione  */
            unCampo = CampoFactory.checkBox(Cam.opzione);
            unCampo.setTestoComponente("opzione");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo prenotazione confermata  */
            unCampo = CampoFactory.checkBox(Cam.confermata);
            unCampo.setTestoComponente("confermata");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo prenotazione disdetta  */
            unCampo = CampoFactory.checkBox(Cam.disdetta);
            unCampo.setTestoComponente("disdetta");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo prenotazione chiusa  */
            unCampo = CampoFactory.checkBox(Cam.chiusa);
            unCampo.setTestoComponente("chiusa");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo caparra  */
            unCampo = CampoFactory.valuta(Cam.caparra);
            unCampo.setLarghezza(60);
            this.addCampo(unCampo);

            /* campo data ricevimento caparra  */
            unCampo = CampoFactory.data(Cam.dataCaparra);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo mezzo ricevimento caparra  */
            unCampo = CampoFactory.comboLinkPop(Cam.mezzoCaparra);
            unCampo.setNomeModuloLinkato(MezzoPagamento.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(MezzoPagamento.CAMPO_SIGLA);
            unCampo.setUsaNonSpecificato(true);
            unCampo.setUsaNuovo(true);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setLarghezza(100);
            this.addCampo(unCampo);

            /* campo numero ricevuta fiscale per caparra  */
            unCampo = CampoFactory.intero(Cam.numRF);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo caparra accreditata  */
            unCampo = CampoFactory.checkBox(Cam.caparraAccreditata);
            unCampo.setLarghezza(150);
            this.addCampo(unCampo);

            /* campo nostra conferma al cliente  */
            unCampo = CampoFactory.testo(Cam.nostraConferma);
            unCampo.setLarghezza(100);
            this.addCampo(unCampo);

            /* campo canale di ricevimento della prenotazione  */
            unCampo = CampoFactory.comboLinkSel(Cam.canale);
            unCampo.setNomeModuloLinkato(Canale.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Canale.Cam.sigla.get());
            unCampo.setUsaNonSpecificato(true);
            unCampo.setUsaNuovo(true);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setLarghezza(60);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo navigatore dei periodi prenotati */
            unCampo = CampoFactory.navigatore(Cam.periodi.get(),
                    Periodo.NOME_MODULO,
                    Periodo.Nav.prenotazione.get());
            unCampo.decora().etichetta("periodi prenotati");
            this.addCampo(unCampo);

            /* campo note  */
            unCampo = CampoFactory.testoArea(Cam.note);
            unCampo.setLarScheda(330);
            unCampo.setNumeroRighe(12);
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>) per
     * individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.costante.CostanteModulo#VISTA_BASE_DEFAULT
     * @see it.algos.base.costante.CostanteModello#NOME_CAMPO_SIGLA
     */
    @Override
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        VistaElemento elem;

        try { // prova ad eseguire il codice


            /* crea la vista per lo Storico Cliente */
            vista = this.creaVista(Prenotazione.Vis.storico.get());
            elem = vista.addCampo(Prenotazione.Cam.dataPrenotazione.get());
            elem.setTitoloColonna("data");
            elem = vista.addCampo(Prenotazione.Cam.cliente.get());
            elem.setTitoloColonna("cliente");
            vista.addCampo(Prenotazione.Cam.caparra.get());
            elem = vista.addCampo(Prenotazione.Cam.dataCaparra.get());
            elem.setTitoloColonna("ricevuta il");
            elem = vista.addCampo(Prenotazione.Cam.nostraConferma.get());
            elem.setTitoloColonna("ns. conferma");
            elem = vista.addCampo(Prenotazione.Cam.disdetta.get());
            elem.setTitoloColonna("disd");


            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione dei filtri per i popup.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    @Override
    protected void regolaFiltriPop() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        WrapFiltri popFiltri;
        Date oggi;
        Filtro filtro1;
        Filtro filtro2;
        String testo = "Tutte";

        try { // prova ad eseguire il codice

            /* crea il popup conti aperti / chiusi */
            popFiltri = super.addPopFiltro();
            popFiltri.setTesto(testo);
            popFiltri.setTitolo(Prenotazione.Pop.aperte.get());

            filtro = FiltroFactory.creaFalso(Prenotazione.Cam.chiusa);
            popFiltri.add(filtro, "Aperte");

            filtro = FiltroFactory.creaVero(Prenotazione.Cam.chiusa);
            popFiltri.add(filtro, "Chiuse");

            filtro = FiltroFactory.creaFalso(Prenotazione.Cam.chiusa);
            filtro.add(FiltroFactory.creaVero(Prenotazione.Cam.confermata));
            popFiltri.add(filtro, "Confermate");

            filtro = FiltroFactory.creaFalso(Prenotazione.Cam.chiusa);
            filtro.add(FiltroFactory.creaFalso(Prenotazione.Cam.confermata));
            popFiltri.add(filtro, "Da confermare");

            filtro = FiltroFactory.creaVero(Prenotazione.Cam.opzione);
            popFiltri.add(filtro, "Opzioni");

            filtro = FiltroFactory.creaVero(Prenotazione.Cam.disdetta);
            popFiltri.add(filtro, "Disdette");

            /* scadute ad oggi */
            /* superata la data di scadenza; non confermate, non annullate e non chiuse */
            oggi = AlbergoLib.getDataProgramma();
            filtro = FiltroFactory.crea(Prenotazione.Cam.dataScadenza.get(),
                    Filtro.Op.MINORE,
                    oggi);
            filtro.add(FiltroFactory.creaFalso(Prenotazione.Cam.confermata));
            filtro.add(FiltroFactory.creaFalso(Prenotazione.Cam.disdetta));
            filtro.add(FiltroFactory.creaFalso(Prenotazione.Cam.chiusa));
            popFiltri.add(filtro, "Scadute");



            /* crea il popup degli anni */
            int annoCorrente = Lib.Data.getAnnoCorrente();
            int annoPrecedente = annoCorrente - 1;
            Date dataGennaioCorrente = Lib.Data.getPrimoGennaio();
            Date dataGennaioPrecedente = Lib.Data.getPrimoGennaio(annoPrecedente);
            Date dataDicembrePrecedente = Lib.Data.getTrentunoDicembre(annoPrecedente);
            Date dataDicembreCorrente = Lib.Data.getTrentunoDicembre(annoCorrente);

            popFiltri = super.addPopFiltro();
            popFiltri.setTesto(testo);

            filtro1 = FiltroFactory.crea(Prenotazione.Cam.dataPrenotazione.get(),
                    Filtro.Op.MAGGIORE_UGUALE,
                    dataGennaioCorrente);
            filtro2=FiltroFactory.crea(Prenotazione.Cam.dataPrenotazione.get(),
                    Filtro.Op.MINORE_UGUALE,dataDicembreCorrente);
            filtro = new Filtro();
            filtro.add(filtro1);
            filtro.add(filtro2);
            popFiltri.add(filtro, "Anno corrente");

            filtro1 = FiltroFactory.crea(Prenotazione.Cam.dataPrenotazione.get(),
                    Filtro.Op.MAGGIORE_UGUALE,
                    dataGennaioPrecedente);
            filtro2 = FiltroFactory.crea(Prenotazione.Cam.dataPrenotazione.get(),
                    Filtro.Op.MINORE_UGUALE,
                    dataDicembrePrecedente);

            filtro = new Filtro();
            filtro.add(filtro1);
            filtro.add(filtro2);
            popFiltri.add(filtro, "Anno precedente");

            popFiltri.setTitolo(Prenotazione.Pop.anni.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Metodo invocato prima della creazione di un nuovo record.
     * <p/>
     * Puo' modificare i valori che stanno per essere registrati<br>
     * Viene sovrascritto dalle classi specifiche <br>
     * Le eventuali modifiche vanno fatte sulla lista che viene
     * passata come parametro.
     *
     * @param lista array coppia campo-valore contenente i
     * dati che stanno per essere registrati
     * @param conn la connessione utilizzata per effettuare la query
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    @Override
    protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        CampoValore cv;
        Date dataIni;
        Date dataScad;
        int delta;

        try { // prova ad eseguire il codice

            /* regola la data di prenotazione pari alla data programma */
            campo = this.getCampo(Cam.dataPrenotazione.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv != null) {
                cv.setValore(AlbergoLib.getDataProgramma());
            }// fine del blocco if

            /* regola la data di scadenza in funzione della data prenotazione */
            campo = this.getCampo(Cam.dataPrenotazione.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv != null) {
                dataIni = Libreria.getDate(cv.getValore());

                campo = this.getCampo(Cam.dataScadenza.get());
                cv = Lib.Camp.getCampoValore(lista, campo);
                if (cv != null) {
                    delta = PrenotazionePref.Prenotazione.validita.intero();
                    dataScad = Lib.Data.add(dataIni, delta);
                    cv.setValore(dataScad);
                }// fine del blocco if
            }// fine del blocco if

            /* regola l'azienda */
            campo = this.getCampo(Cam.azienda.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo);
            }// fine del blocco if-else
            cv.setValore(AziendaModulo.getCodAziendaPrincipale());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return true;
    } // fine del metodo

} // fine della classe
