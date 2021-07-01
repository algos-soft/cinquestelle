/**
 * Title:     Sudd_Camera
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;

import java.util.ArrayList;
import java.util.Date;

/**
 * Tipo di suddivisione per Camera
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-feb-2009 ore 8.29.49
 */
class Sudd_Camera extends SuddivisioneBase {

    /**
     * Costruttore completo.
     * <p/>
     */
    Sudd_Camera() {
        super("Per Camera", "Camera", false);
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
            Modulo modCamere = CameraModulo.get();
            Campo campoChiave = modCamere.getCampoChiave();
            Campo campoSigla = modCamere.getCampo(Camera.Cam.camera.get());
            Ordine ordine = new Ordine(campoSigla);
            Query query = new QuerySelezione(modCamere);
            query.setOrdine(ordine);
            query.addCampo(campoChiave);
            query.addCampo(campoSigla);
            Dati dati = modCamere.query().querySelezione(query);
            for (int k = 0; k < dati.getRowCount(); k++) {
                int chiave = dati.getIntAt(k, campoChiave);
                String sigla = dati.getStringAt(k, campoSigla);
                String desc = sigla;
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
            chiave = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);

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