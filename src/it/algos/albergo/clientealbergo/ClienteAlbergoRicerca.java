/**
 * Title:     ClienteAlbergoRicerca
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-lug-2009
 */
package it.algos.albergo.clientealbergo;

import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergoModulo;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.ricerca.CampoRicerca;
import it.algos.base.ricerca.RicercaBase;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Ricerca specifica per il navigatore Clienti
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-lug-2009 ore 22.11.47
 */
public final class ClienteAlbergoRicerca extends RicercaBase {

    private CampoRicerca crSoggetto;

    private CampoRicerca crCapogruppo;

    private CampoRicerca crCorrispondenza;

    private CampoRicerca crFamiglia;

    private CampoRicerca crEvidenza;

    private CampoRicerca crLingua;

    private CampoRicerca crUltSogg;

    private CampoRicerca crNazione;


    /**
     * Costruttore completo con parametri. <br>
     */
    public ClienteAlbergoRicerca() {
        /* rimanda al costruttore della superclasse */
        super(ClienteAlbergoModulo.get());

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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
        Campo campo;

        try { // prova ad eseguire il codice

            crSoggetto = this.addCampoRicerca(Anagrafica.Cam.soggetto.get());
            crSoggetto.setEtichetta("Nome Cliente");
            crSoggetto.setLarghezza(180);

            crCapogruppo = this.addCampoRicerca(ClienteAlbergo.Cam.capogruppo.get());
            crCapogruppo.setEtichetta("Capogruppo");

            crCorrispondenza = this.addCampoRicerca(ClienteAlbergo.Cam.checkPosta.get());
            crCorrispondenza.setEtichetta("Corrispondenza");

            crEvidenza = this.addCampoRicerca(ClienteAlbergo.Cam.checkEvidenza.get());
            crEvidenza.setEtichetta("Evidenza");

            crFamiglia = this.addCampoRicerca(ClienteAlbergo.Cam.checkFamiglia.get());
            crFamiglia.setEtichetta("Famiglia");

            crLingua = this.addCampoRicerca(ClienteAlbergo.Cam.lingua.get());
            crLingua.setEtichetta("Lingua");
            crLingua.setLarghezza(110);

            campo = CittaModulo.get().getCampo(Citta.Cam.linkNazione);
            crNazione = new CampoRicerca(this, campo, null, false);
            crNazione.setEtichetta("Nazione");
            crNazione.setLarghezza(120);
            crNazione.setUsaPopUnioni(false);
            crNazione.inizializza();

            /* campo di ricerca speciale sulle presenze */
            campo = ClienteAlbergoModulo.get().getCampo(ClienteAlbergo.Cam.ultSoggiorno);
            crUltSogg = new CampoRicerca(this, campo, null, true);
            crUltSogg.setEtichetta("Ultimo soggiorno");
            crNazione.setUsaPopUnioni(false);
            crUltSogg.inizializza();



//            crUltSogg = this.addCampoRicerca(ClienteAlbergo.Cam.ultSoggiorno.get(),true);
//            crUltSogg.setEtichetta("Ultimo soggiorno");

//            /* campo di ricerca specializzato */
//            Campo campo = CampoFactory.data("dataPresente");
//            crDataPresente = new CampoRicercaPeriodo(this, campo);
//            crDataPresente.setEtichetta("Presente nel periodo");
//            this.addCampoRicerca(crDataPresente);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Compone la pagina del dialogo.
     * <p/>
     * Aggiunge sequenzialmente gli oggetti CampoRicerca
     * Sovrascritto dalle sottoclassi che non vogliono usare
     * la disposizione automatica dei campi uno sotto l'altro.
     */
    protected void creaPagina() {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try { // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            pan.add(crSoggetto);
            this.addPannello(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(crCapogruppo);
            pan.add(crCorrispondenza);
            this.addPannello(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(crEvidenza);
            pan.add(crFamiglia);
            this.addPannello(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(crLingua);
            pan.add(crNazione);
            this.addPannello(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(crUltSogg);
            this.addPannello(pan);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Recupera il filtro corrispondente a questa Ricerca.
     * <p/>
     * Filtra ulteriormente il risultato in base ai campi di ricerca speciali
     *
     * @return il filtro corrispondente alla ricerca
     */
    public Filtro getFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        /* filtro esclusi i campi speciali */
        filtro = super.getFiltro();

        /* modifica il filtro per tenere conto della condizione speciale */
        if (crUltSogg.isValorizzato()) {
            ArrayList<Integer> listaOut = new ArrayList<Integer>();
            int[] codici = ClienteAlbergoModulo.get().query().valoriChiave(filtro);
            for (int cod : codici) {
                if (this.isCompreso(cod)) {
                    listaOut.add(cod);
                }// fine del blocco if
            }

            if (listaOut.size()>0) {
                filtro = FiltroFactory.elenco(ClienteAlbergoModulo.get(), listaOut);
            } else {
                filtro = FiltroFactory.nessuno(ClienteAlbergoModulo.get());
            }// fine del blocco if-else

        }// fine del blocco if-else

        /* modifica il filtro per tenere conto della condizione speciale */
        if (crNazione.isValorizzato()) {

            ArrayList<Integer> listaOut = new ArrayList<Integer>();
            int[] codici = ClienteAlbergoModulo.get().query().valoriChiave(filtro);
            for (int cod : codici) {
                if (this.isNazione(cod)) {
                    listaOut.add(cod);
                }// fine del blocco if
            }

            if (listaOut.size()>0) {
                filtro = FiltroFactory.elenco(ClienteAlbergoModulo.get(), listaOut);
            } else {
                filtro = FiltroFactory.nessuno(ClienteAlbergoModulo.get());
            }// fine del blocco if-else

        }

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Controlla se la data di ultimo soggiorno di un cliente
     * è compresa nell'intervallo specificato.
     * <p/>
     * @param codCliente il codice cliente
     * @return true se è compresa
     */
    private boolean isCompreso (int codCliente) {
        /* variabili e costanti locali di lavoro */
        boolean compreso=false;

        Date dataUltSogg;

        try {    // prova ad eseguire il codice
            Date data1 = Libreria.getDate(crUltSogg.getCampo1().getValore());
            Date data2 = Libreria.getDate(crUltSogg.getCampo2().getValore());

            dataUltSogg = PresenzaModulo.getDataUltimoSoggiorno(codCliente);

            /* deve avere una data di ultimo soggiorno */
            if (Lib.Data.isValida(dataUltSogg)) {

                boolean passed=true; // comincio con true poi metto false appena qualcosa fallisce

                /* se c'è il limite inferiore deve essere uguale o successiva */
                if (passed) {
                    if (Lib.Data.isValida(data1)) {
                        if (!Lib.Data.isPosterioreUguale(data1, dataUltSogg)) {
                            passed=false;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                /* se c'è il limite superiore deve essere precedente o uguale */
                if (passed) {
                    if (Lib.Data.isValida(data2)) {
                        if (!Lib.Data.isPrecedenteUguale(data2, dataUltSogg)) {
                            passed=false;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                compreso=passed;

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return compreso;

    }


    /**
     * Controlla se la nazione di residenza di un cliente
     * è uguale a quella specificata nel campo Nazione.
     * <p/>
     * @param codCliente il codice cliente
     * @return true se è uguale
     */
    private boolean isNazione (int codCliente) {
        /* variabili e costanti locali di lavoro */
        boolean uguale=false;

        try {    // prova ad eseguire il codice

            int codRichiesto = Libreria.getInt(crNazione.getCampo1().getValore());
            int codIndirizzo = ClienteAlbergoModulo.getCodIndirizzo(codCliente);

            if (codIndirizzo!=0) {
                Modulo modIndirizzo = IndirizzoAlbergoModulo.get();
                Query query = new QuerySelezione(modIndirizzo);
                Filtro filtro = FiltroFactory.codice(modIndirizzo, codIndirizzo);
                Campo campo = CittaModulo.get().getCampo(Citta.Cam.linkNazione);
                query.setFiltro(filtro);
                query.addCampo(campo);
                Dati dati =modIndirizzo.query().querySelezione(query);
                int codNazione = dati.getIntAt(campo);
                dati.close();

                /* deve avere una nazione ed essere uguale*/
                if (codNazione!=0) {
                    uguale = (codNazione==codRichiesto);
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return uguale;

    }






}// fine della classe