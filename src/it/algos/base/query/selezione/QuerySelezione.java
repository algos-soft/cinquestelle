/**
 * Title:     QuerySelezione
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-ott-2004
 */

package it.algos.base.query.selezione;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.QueryBase;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;

/**
 * Implementazione concreta di una Query per la selezione di records.
 * <p/>
 * Gestisce campi e filtro nella superclasse
 * Gestisce ordine e relazioni in questa classe
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-ott-2004 ore 12.43.53
 */
public final class QuerySelezione extends QueryBase {

    /**
     * Contenitore dei campi di ordinamento
     */
    private Ordine ordine = null;

//    /** Contenitore per i passaggi obbligati tra le relazioni */
//    private PassaggiSelezione passaggi = null;

    /**
     * abilita l'uso del filtro hard
     * il filtro hard e' un filtro che viene aggiunto a tutte le query
     * di default e' attivo
     */
    private boolean usaFiltroHard;

    /**
     * restituisce la lista univoca dei valori distinti dei campi.
     */
    private boolean valoriDistinti;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo il modulo di riferimento.
     */
    public QuerySelezione(Modulo modulo) {
        /** rimanda al costruttore della superclasse */
        super(modulo);

        try {
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore */


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() {
        this.ordine = new Ordine();
//        this.passaggi = new PassaggiSelezione();

        /* attiva il filtro hard di default */
        this.setUsaFiltroHard(true);

    } /* fine del metodo inizia */


    /**
     * Risolve questa query.
     * <p/>
     * Converte tutti gli oggetti che usano nomi di campo
     * in oggetti che usano solo oggetti campo,
     * in base al modulo di questa Query.
     */
    public void risolvi() {
        /* variabili e costanti locali di lavoro */
        Ordine ordine;
        Filtro filtro;
        Filtro filtroHard;
        Filtro filtroFinale;

        try { // prova ad eseguire il codice

            /* risolve campi e filtro nella superclasse */
            super.risolvi();

            /*
             * crea il filtro definitivo
             * composto da:
             * - un filtro di base hard-coded (es. visibile amministratore)
             * - il filtro della query (in lista: base + corrente)
             */
            if (this.isUsaFiltroHard()) {
                filtroHard = this.getFiltroHard();
                if (filtroHard != null) {
                    filtro = this.getFiltro();
                    filtroFinale = new Filtro();
                    filtroHard.risolvi(this.getModulo());
                    filtroFinale.add(filtroHard);
                    filtroFinale.add(filtro);
                    this.setFiltro(filtroFinale);
                }// fine del blocco if
            }// fine del blocco if

            /* risolve l'ordine in questa classe */
            ordine = this.getOrdine();
            if (ordine != null) {
                ordine.risolvi(this.getModulo());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Restituisce il filtro hard-coded di progetto
     * per tutte le query.
     * <p/>
     * Se il Progetto e' ancora in fase di preparazione,
     * restituisce null
     */
    private Filtro getFiltroHard() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Modulo modulo;
        Modello modello=null;
        boolean continua;

        try { // prova ad eseguire il codice

            modulo = this.getModulo();
            continua = (modulo!=null);

            if (continua) {
                modello = modulo.getModello();
                continua = (modello!=null);
            }// fine del blocco if

            if (continua) {
                filtro = modello.getFiltroModello();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    } /* fine del metodo */


    /**
     * Aggiunge un ordine alla query.
     * <p/>
     *
     * @param campo il campo da aggiungere
     */
    public void addOrdine(Campo campo) {
        this.getOrdine().add(campo);
    } /* fine del metodo */

//    /**
//     * Aggiunge un Passaggio Obbligato.
//     * <p>
//     * @param passaggio il passaggio obbligato
//     */
//    public void addPassaggio(PassaggioObbligato passaggio) {
//        this.getPassaggi().addPassaggio(passaggio);
//    } /* fine del metodo */
//
//    /**
//     * Aggiunge un Passaggio Obbligato.
//     * <p>
//     * @param modulo il modulo di destinazione
//     * @param campo il Campo dal quale passare obbligatoriamente
//     */
//    public void addPassaggio(Modulo modulo, Campo campo) {
//        this.getPassaggi().addPassaggio(modulo, campo);
//    } /* fine del metodo */


    public Ordine getOrdine() {
        return ordine;
    }


    /**
     * Assegna un Ordine alla Query
     */
    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    /**
     * Assegna un Ordine alla Query
     * <p>
     * @param campo di ordinamento
     */
    public void setOrdine(Campo campo){
        this.setOrdine(new Ordine(campo));
    }


//    public PassaggiSelezione getPassaggi() {
//        return passaggi;
//    }
//
//
//    private void setPassaggi(PassaggiSelezione passaggi) {
//        this.passaggi = passaggi;
//    }


    private boolean isUsaFiltroHard() {
        return usaFiltroHard;
    }


    /**
     * attiva il filtro hard
     * <p/>
     * il filtro hard e' un filtro che viene automaticamente aggiunto
     * alla query query.
     * di default e' attivo.
     *
     * @param usaFiltroHard flag di attivazione
     */
    public void setUsaFiltroHard(boolean usaFiltroHard) {
        this.usaFiltroHard = usaFiltroHard;
    }


    public boolean isValoriDistinti() {
        return valoriDistinti;
    }


    /**
     * Restituisce la lista univoca dei valori distinti per tutti i campi
     * della query.
     * <p/>
     * Significativo solo per QuerySelezione.
     * Se la query contiene un solo campo, restituisce solo i valori diversi
     * Se la query contiene più di un campo, restituisce le righe dove le combinazioni
     * dei valori sono diverse (comportamento standard SQL)
     *
     * @param flag per restituire solo i valori distinti
     */
    public void setValoriDistinti(boolean flag) {
        this.valoriDistinti = flag;
    }

}