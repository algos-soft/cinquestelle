/**
 * Title:     IndirizzoAlbergoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-ott-2007
 */
package it.algos.albergo.clientealbergo.indirizzoalbergo;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.TestoAlgos;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModello;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;
import it.algos.gestione.indirizzo.tabelle.via.Via;
import it.algos.gestione.indirizzo.tabelle.via.ViaModulo;

import javax.swing.JTextArea;
import java.awt.BorderLayout;

/**
 * Modello dati del modulo IndirizzoAlbergo.
 * <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-mag-2004 ore 7.58.25
 */
public final class IndirizzoAlbergoModello extends IndirizzoModello implements IndirizzoAlbergo {

    /**
     * Costruttore completo senza parametri.<br>
     */
    public IndirizzoAlbergoModello() {
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
     * Regolazioni immediate di riferimenti e variabili. <p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            /* regola il nome della tavola */
            this.setTavolaArchivio(IndirizzoAlbergo.NOME_TAVOLA);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Creazione dei campi di questo modello (oltre a quelli base).
     * <br>
     * i campi verranno visualizzati nell'ordine di inserimento <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        /* i campi verranno visualizzati nell'ordine di inserimento */
        try {

            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* rimuove il campo tipo di indirizzo */
            this.removeCampo(Indirizzo.Cam.tipo);

            /* rende invisibile lista il campo sigla della via */
            unCampo = this.getCampo(Indirizzo.Cam.via.get());
            if (unCampo != null) {
                unCampo.setVisibileVistaDefault(false);
            }// fine del blocco if

            /* rende visibile lista il campo indirizzo */
            unCampo = this.getCampo(Indirizzo.Cam.indirizzo.get());
            if (unCampo != null) {
                unCampo.setVisibileVistaDefault();
            }// fine del blocco if

            /* rende visibile lista il campo cap */
            unCampo = this.getCampo(Indirizzo.Cam.cap.get());
            if (unCampo != null) {
                unCampo.setVisibileVistaDefault();
            }// fine del blocco if

            /* rende visibile lista il campo città */
            unCampo = this.getCampo(Indirizzo.Cam.citta.get());
            if (unCampo != null) {
                unCampo.setVisibileVistaDefault();
            }// fine del blocco if

            /* disabilita l'uso del campo "preferito" */
            this.setUsaCampoPreferito(false);

            /* rende non visibile il campo ordine */
            super.setCampoOrdineVisibileLista(false);

        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaViste();

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected Vista creaVistaDefault() {
        /* variabili e costanti locali di lavoro */
        Vista vista = null;
        Campo campo;
        Modulo modulo;
        VistaElemento elem;

        try { // prova ad eseguire il codice
            vista = new Vista(this.getModulo());
            vista.addCampo(Indirizzo.Cam.anagrafica.get());
            vista.addCampo(Indirizzo.Cam.indirizzo.get());
            vista.addCampo(Indirizzo.Cam.cap.get());
            vista.addCampo(Indirizzo.Cam.citta.get());

            /* campo provincia */
            modulo = ProvinciaModulo.get();
            campo = modulo.getCampo(Provincia.Cam.nomeCorrente.get());
            if (campo != null) {
                elem = vista.addCampo(campo);
                elem.setLarghezzaColonna(120);
            }// fine del blocco if

            /* campo nazione */
            modulo = NazioneModulo.get();
            campo = modulo.getCampo(Nazione.Cam.nazione.get());
            if (campo != null) {
                elem = vista.addCampo(campo);
                elem.setLarghezzaColonna(70);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return vista;

    } /* fine del metodo */


    /**
     * Restituisce un estratto dell'indirizzo da visualizzare nella scheda del cliente
     * </p>
     * Controlla che il tipo di estratto richiesto sia un pannello <br>
     *
     * @param chiave con cui effettuare la ricerca (codice dell'indirizzo)
     * @param conn la connessione da utilizzare per le query
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstIndirizzoCliente(Object chiave, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        int cod;

        Modulo modulo;
        Query query;
        Filtro filtro;
        Connessione connessione;
        Dati dati;

        Campo campoVia = ViaModulo.get().getCampo(Via.Cam.sigla.get());
        Campo campoIndirizzo1 = this.getCampo(Indirizzo.Cam.indirizzo);
        Campo campoIndirizzo2 = this.getCampo(Indirizzo.Cam.indirizzo2);
        Campo campoCap = this.getCampo(Indirizzo.Cam.cap);
        Campo campoNote = this.getCampo(Indirizzo.Cam.note);
        Campo campoLocalita = CittaModulo.get().getCampo(Citta.Cam.citta.get());
        Campo campoProvincia = ProvinciaModulo.get().getCampo(Provincia.Cam.nomeCorrente.get());
        Campo campoNazione = NazioneModulo.get().getCampo(Nazione.Cam.nazione.get());

        String via;
        String indirizzo1;
        String indirizzo2;
        String cap;
        String localita;
        String provincia;
        String nazione;
        String note;

        String rigaLocalita;
        String rigaProvNaz;

        String testo;

        JTextArea area;
        Pannello pan;

        try { // prova ad eseguire il codice

            /* recupera il modulo */
            modulo = this.getModulo();

            /* recupera il codice del record */
            cod = Libreria.getInt(chiave);

            /* recupera la connessione da utilizzare */
            if (conn != null) {
                connessione = conn;
            } else {
                connessione = modulo.getConnessione();
            }// fine del blocco if-else

            /* esegue la query sul database */
            query = new QuerySelezione(this.getModulo());
            query.addCampo(campoVia);
            query.addCampo(campoIndirizzo1);
            query.addCampo(campoIndirizzo2);
            query.addCampo(campoCap);
            query.addCampo(campoNote);
            query.addCampo(campoLocalita);
            query.addCampo(campoProvincia);
            query.addCampo(campoNazione);
            filtro = FiltroFactory.codice(modulo, cod);
            query.setFiltro(filtro);
            dati = this.query().querySelezione(query, connessione);

            /* recupera i dati dall'indirizzo */
            via = dati.getStringAt(campoVia);
            indirizzo1 = dati.getStringAt(campoIndirizzo1);
            indirizzo2 = dati.getStringAt(campoIndirizzo2);
            localita = dati.getStringAt(campoLocalita);
            cap = dati.getStringAt(campoCap);
            note = dati.getStringAt(campoNote);
            provincia = dati.getStringAt(campoProvincia);
            nazione = dati.getStringAt(campoNazione);
            dati.close();

            /* costruisce il testo */
            rigaLocalita = Lib.Testo.concatSpace(cap, localita);
            if (Lib.Testo.isValida(nazione)) {
                nazione = "(" + nazione + ")";
            }// fine del blocco if
            rigaProvNaz = Lib.Testo.concatSpace(provincia, nazione);
            indirizzo1 = Lib.Testo.concatSpace(via, indirizzo1);
            testo = Lib.Testo.concatReturn(indirizzo1, indirizzo2, rigaLocalita, rigaProvNaz);

            /* costruisce l'area di testo */
            area = new JTextArea(testo);
            TestoAlgos.setField(area);
            area.setEditable(false);
            area.setOpaque(false);

            /* costruisce il pannello grafico */
            pan = new PannelloBase();
            pan.getPanFisso().setLayout(new BorderLayout());
            pan.add(area);
            pan.setPreferredSize(250, 60);

            /* crea l'estratto col pannello */
            unEstratto = new EstrattoBase(pan, EstrattoBase.Tipo.pannello);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce un estratto dell'indirizzo da visualizzare nella scheda di notifica.
     * </p>
     * Controlla che il tipo di estratto richiesto sia una stringa <br>
     *
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstIndirizzoNotifica(Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        int cod;

        Modulo modulo;
        Query query;
        Filtro filtro;
        Connessione connessione;
        Dati dati;

        Campo campoVia = ViaModulo.get().getCampo(Via.Cam.sigla.get());
        Campo campoIndirizzo1 = this.getCampo(Indirizzo.Cam.indirizzo);
        Campo campoCap = this.getCampo(Indirizzo.Cam.cap);
        Campo campoNote = this.getCampo(Indirizzo.Cam.note);
        Campo campoLocalita = CittaModulo.get().getCampo(Citta.Cam.citta.get());
        Campo campoSiglaPro = ProvinciaModulo.get().getCampo(Provincia.Cam.sigla.get());
        Campo campoProvincia = ProvinciaModulo.get().getCampo(Provincia.Cam.nomeCorrente.get());
        Campo campoNazione = NazioneModulo.get().getCampo(Nazione.Cam.sigla2.get());

        String via;
        String indirizzo;
        String cap;
        String localita;
        String siglaPro;
        String provincia;
        String nazione;

        String rigaLocalita;
        String rigaProvNaz;

        String testo;

        try { // prova ad eseguire il codice

            /* recupera il modulo */
            modulo = this.getModulo();

            /* recupera il codice del record */
            cod = Libreria.getInt(chiave);

            /* recupera la connessione da utilizzare */
            connessione = modulo.getConnessione();

            /* esegue la query sul database */
            query = new QuerySelezione(this.getModulo());
            query.addCampo(campoVia);
            query.addCampo(campoIndirizzo1);
            query.addCampo(campoCap);
            query.addCampo(campoNote);
            query.addCampo(campoLocalita);
            query.addCampo(campoSiglaPro);
            query.addCampo(campoProvincia);
            query.addCampo(campoNazione);
            filtro = FiltroFactory.codice(modulo, cod);
            query.setFiltro(filtro);
            dati = this.query().querySelezione(query, connessione);

            /* recupera i dati dall'indirizzo */
            via = dati.getStringAt(campoVia);
            indirizzo = dati.getStringAt(campoIndirizzo1);
            localita = dati.getStringAt(campoLocalita);
            cap = dati.getStringAt(campoCap);
            siglaPro = dati.getStringAt(campoSiglaPro);
            provincia = dati.getStringAt(campoProvincia);
            nazione = dati.getStringAt(campoNazione);
            dati.close();

            /* costruisce il testo */
            rigaLocalita = Lib.Testo.concatSpace(localita, cap);
            if (Lib.Testo.isValida(siglaPro)) {
                siglaPro = "(" + siglaPro + ")";
            }// fine del blocco if

            if (localita.equals(provincia)) {
                rigaProvNaz = Lib.Testo.concatSpace(siglaPro, nazione);
            } else {
                rigaProvNaz = Lib.Testo.concatSpace(provincia, siglaPro, nazione);
            } // fine del blocco if-else

            indirizzo = Lib.Testo.concatSpace(via, indirizzo);
            testo = indirizzo + " - " + rigaLocalita + " " + rigaProvNaz;

            /* crea l'estratto col pannello */
            unEstratto = new EstrattoBase(testo, EstrattoBase.Tipo.stringa);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce la cittadinanza.
     * </p>
     * Controlla che il tipo di estratto richiesto sia una stringa <br>
     *
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstCittadinanza(Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        int cod;
        Modulo modulo;
        Query query;
        Filtro filtro;
        Connessione connessione;
        Dati dati;
        String nazione;
        Campo campoNazione = NazioneModulo.get().getCampo(Nazione.Cam.nazione.get());


        try { // prova ad eseguire il codice

            /* recupera il modulo */
            modulo = this.getModulo();

            /* recupera il codice del record */
            cod = Libreria.getInt(chiave);

            /* recupera la connessione da utilizzare */
            connessione = modulo.getConnessione();

            /* esegue la query sul database */
            query = new QuerySelezione(this.getModulo());
            query.addCampo(campoNazione);
            filtro = FiltroFactory.codice(modulo, cod);
            query.setFiltro(filtro);
            dati = this.query().querySelezione(query, connessione);

            /* recupera i dati dall'indirizzo */
            nazione = dati.getStringAt(campoNazione);
            dati.close();

            /* crea l'estratto col pannello */
            unEstratto = new EstrattoBase(nazione, EstrattoBase.Tipo.stringa);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }




    /**
     * Restituisce l'indirizzo su una sola riga.
     * </p>
     * Controlla che il tipo di estratto richiesto sia una stringa <br>
     *
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstUnaRiga(Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        int cod;
        Modulo modulo;
        Query query;
        Filtro filtro;
        Connessione connessione;
        Dati dati;
        String indirizzo1;
        String indirizzo2;
        String cap;
        String localita;
        String provincia;
        String nazione;
        Campo campoIndirizzo1 = this.getModulo().getCampo(Indirizzo.Cam.indirizzo.get());
        Campo campoIndirizzo2 = this.getModulo().getCampo(Indirizzo.Cam.indirizzo2.get());
        Campo campoCAP = this.getModulo().getCampo(Indirizzo.Cam.cap.get());
        Campo campoCitta = CittaModulo.get().getCampo(Citta.Cam.citta);
        Campo campoProvincia = ProvinciaModulo.get().getCampo(Provincia.Cam.sigla.get());
        Campo campoNazione = NazioneModulo.get().getCampo(Nazione.Cam.nazione.get());


        try { // prova ad eseguire il codice

            /* recupera il modulo */
            modulo = this.getModulo();

            /* recupera il codice del record */
            cod = Libreria.getInt(chiave);

            /* recupera la connessione da utilizzare */
            connessione = modulo.getConnessione();

            /* esegue la query sul database */
            query = new QuerySelezione(this.getModulo());
            query.addCampo(campoIndirizzo1);
            query.addCampo(campoIndirizzo2);
            query.addCampo(campoCAP);
            query.addCampo(campoCitta);
            query.addCampo(campoProvincia);
            query.addCampo(campoNazione);
            filtro = FiltroFactory.codice(modulo, cod);
            query.setFiltro(filtro);
            dati = this.query().querySelezione(query, connessione);

            /* recupera i dati dall'indirizzo */
            indirizzo1 = dati.getStringAt(campoIndirizzo1);
            indirizzo2 = dati.getStringAt(campoIndirizzo2);
            cap = dati.getStringAt(campoCAP);
            localita = dati.getStringAt(campoCitta);
            provincia = dati.getStringAt(campoProvincia);
            nazione = dati.getStringAt(campoNazione);
            dati.close();

            String stringa = Lib.Testo.concatSpace(indirizzo1, indirizzo2, cap, localita, provincia, nazione);

            /* crea l'estratto stringa */
            unEstratto = new EstrattoBase(stringa, EstrattoBase.Tipo.stringa);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    @Override public boolean inizializza(Modulo unModulo) {
        return super.inizializza(unModulo);

    } /* fine del metodo */


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Utilizza la connessione fornita per effettuare le query <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param tipo tipo di estratto desiderato
     * @param chiave con cui effettuare la ricerca
     * @param conn la connessione da utilizzare per le query
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti tipo, Object chiave, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((IndirizzoAlbergo.Est)tipo) {
                case indirizzoCliente:
                    unEstratto = this.getEstIndirizzoCliente(chiave, conn);
                    break;
                case indirizzoNotifica:
                    unEstratto = this.getEstIndirizzoNotifica(chiave);
                    break;
                case cittadinanza:
                    unEstratto = this.getEstCittadinanza(chiave);
                    break;
                case indirizzoRiga:
                    unEstratto = this.getEstUnaRiga(chiave);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Utilizza la connessione fornita per effettuare le query <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param tipo tipo di estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti tipo, Object chiave) {
        return getEstratto(tipo, chiave, (Connessione)null);
    }

}// fine della classe
