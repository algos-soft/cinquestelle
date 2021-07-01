/**
 * Title:        InitSequenziale.java
 * Package:      it.algos.base.campo.inizializzatore
 * Description:  Inizializzatore sequenziale basato sui dati esistenti
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 22 settembre 2003 alle 12.55
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------

package it.algos.base.campo.inizializzatore;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.util.Funzione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.campi.CampoQuery;
import it.algos.base.query.selezione.QuerySelezione;

/**
 * Inizializzatore sequenziale.
 * Ritorna un valore progressivo pari al massimo valore esistente
 * sul DB per il campo, incrementato di 1
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  22 settembre 2003 ore 12.55
 */
class InitSequenziale extends InitBase {

    /**
     * Campo di riferimento
     */
    private Campo campo = null;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param campo il campo di riferimento da inizializzare
     */
    public InitSequenziale(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setCampo(campo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Ritorna il valore di inizializzazione per il campo.
     * <p/>
     * Il valore ritornato e' a livello di Memoria
     *
     * @param conn la connessione da utilizzare per eventuali query
     */
    public Object getValore(Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int unValoreInt = 0;

        try { // prova ad eseguire il codice

            /* recupera il massimo valore per il campo
             * (potrebbe essere zero, es. quando la tavola e' vuota) */
            unValoreInt = this.getMax(conn);

            /* lo incrementa di 1*/
            unValoreInt++;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unValoreInt;
    }


    /**
     * Ritorna il valore massimo esistente sul DB per il campo.
     * <p/>
     * Non uso modulo.query().valoreMassimo perche' usa la query standard
     * che ha il filtro hard-coded e potrebbe non vedere alcuni dati
     *
     * @param conn la connessione da utilizzare per eventuali query
     */
    private int getMax(Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int max = 0;
        Campo campo = null;
        Modulo modulo = null;

        QuerySelezione query = null;
        CampoQuery cq = null;
        Dati dati = null;

        try { // prova ad eseguire il codice

            /* recupera il campo */
            campo = this.getCampo();

            /* recupera il modulo */
            modulo = campo.getModulo();

            /* crea una QuerySelezione per il Modulo */
            query = new QuerySelezione(modulo);
            query.setUsaFiltroHard(false);

            /* aggiunge il campo con la funzione MAX */
            cq = query.addCampo(campo);
            cq.addFunzione(Funzione.MAX);

            /* esegue la query */
            dati = modulo.query().querySelezione(query, conn);

            /* recupera il risultato */
            max = dati.getIntAt(0, 0);

            /* chiude l'oggetto dati */
            dati.close();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return max;
    }


    private Campo getCampo() {
        return campo;
    }


    private void setCampo(Campo campo) {
        this.campo = campo;
    }

}
