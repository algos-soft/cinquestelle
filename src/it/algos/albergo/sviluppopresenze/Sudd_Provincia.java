/**
 * Title:     Sudd_Provincia
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergoModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Tipo di suddivisione per Provincia
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-feb-2009 ore 8.29.49
 */
class Sudd_Provincia extends SuddivisioneBase {

    /**
     * Costruttore completo.
     * <p/>
     */
    Sudd_Provincia() {
        super("Per Provincia", "Provincia", false);
    }// fine del metodo costruttore completo


    /**
     * Crea una lista di elementi suddivisi per questo tipo di suddivisione.
     * <p/>
     * @param estremita eventuali estremità della suddivisione (non necessariamente utilizzato)
     * @return un array degli elementi suddivisi, ogni elemento con [chiave - sigla - descrizione]
     */
    public WrapSuddivisione[] creaSuddivisione(Object estremita){
        /* variabili e costanti locali di lavoro */
        WrapSuddivisione[] matrice = new WrapSuddivisione[0];
        ArrayList<WrapSuddivisione> lista = new ArrayList<WrapSuddivisione>();
        WrapSuddivisione wrapper;

        try {    // prova ad eseguire il codice

            /* crea la lista */
            Modulo modProvincia = ProvinciaModulo.get();
            Campo campoChiave = modProvincia.getCampoChiave();
            Campo campoSigla = modProvincia.getCampo(Provincia.Cam.sigla.get());
            Campo campoDescrizione = modProvincia.getCampo(Provincia.Cam.nomeCorrente.get());
            Ordine ordine = new Ordine(campoSigla);
            Query query = new QuerySelezione(modProvincia);
            query.setOrdine(ordine);
            query.addCampo(campoChiave);
            query.addCampo(campoSigla);
            query.addCampo(campoDescrizione);
            Dati dati = modProvincia.query().querySelezione(query);
            for (int k = 0; k < dati.getRowCount(); k++) {
                int chiave = dati.getIntAt(k, campoChiave);
                String sigla = dati.getStringAt(k, campoSigla);
                String desc = dati.getStringAt(k, campoDescrizione);
                wrapper = new WrapSuddivisione(chiave, sigla, desc);
                lista.add(wrapper);
            } // fine del ciclo for
            dati.close();

            /* riempie l'array */
            matrice = new WrapSuddivisione[lista.size()];
            for (int k = 0; k < lista.size(); k++) {
                 matrice[k]= lista.get(k);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return matrice;

    }

    /**
     * Recupera la chiave di suddivisione relativa a un periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     *
     * @return la chiave di suddivisione
     */
    public int getChiavePeriodo(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        int chiave = 0;

        try {    // prova ad eseguire il codice

            /* recupera il numero di prenotazione */
            Modulo modPeriodo = PeriodoModulo.get();
            int codPren = modPeriodo.query().valoreInt(Periodo.Cam.prenotazione.get(), codPeriodo);

            /* recupera il codice del cliente */
            Modulo modPren = PrenotazioneModulo.get();
            int codCliente = modPren.query().valoreInt(Prenotazione.Cam.cliente.get(), codPren);

            /* recupera il codice dell'indirizzo del cliente */
            int codIndirizzo = ClienteAlbergoModulo.getCodIndirizzo(codCliente);

            /* recupera il codice città */
            Modulo modIndirizzo = IndirizzoAlbergoModulo.get();
            int codCitta = modIndirizzo.query().valoreInt(Indirizzo.Cam.citta.get(), codIndirizzo);

            /* recupera il codice provincia */
            Modulo modCitta = CittaModulo.get();
            chiave = modCitta.query().valoreInt(Citta.Cam.linkProvincia.get(), codCitta);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }

    /**
     * Recupera la chiave di suddivisione nella quale un dato giorno rientra
     * <p>
     * Significativo solo per suddivisioni di tipo temporale
     * @param data la data da analizzare
     * @return la chiave di mappa
     */
    public int getChiaveGiorno(Date data){
        return 0;
    }



}// fine della classe