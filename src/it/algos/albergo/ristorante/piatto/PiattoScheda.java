/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      18-gen-2005
 */
package it.algos.albergo.ristorante.piatto;

import it.algos.albergo.ristorante.categoria.CategoriaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.scheda.SchedaBase;

/**
 * Presentazione grafica di un singolo record di Piatto.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su piu' pagine, risultera'
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma e' attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 18-gen-2005 ore 17.48.35
 */
public final class PiattoScheda extends SchedaBase implements Piatto {

    /**
     * riferimenti alla pagine delle lingue
     */
    private Pagina[] pagine = null;


    /**
     * Costruttore completo senza parametri.
     */
    public PiattoScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
            /* crea l'array delle pagine */
            this.setPagine(new Pagina[4]);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    public void inizializza() {
        super.inizializza();
    }


    /**
     * .
     * <p/>
     */
    public void avvia(int codice) {
        super.avvia(codice);
    }


    /**
     * disegna le varie pagine in lingua
     */
    private void creaPagineLingua() {
        /* variabili e costanti locali di lavoro */
        Pagina[] pagine;

        try {    // prova ad eseguire il codice

            /* recupera l'array delle pagine */
            pagine = this.getPagineScheda();

            /* crea una pagina completa col titolo */
            pagine[ITALIANO] = super.addPagina(ETICHETTA_LINGUA[ITALIANO], SET_ITALIANO);
            pagine[TEDESCO] = super.addPagina(ETICHETTA_LINGUA[TEDESCO], SET_TEDESCO);
            pagine[INGLESE] = super.addPagina(ETICHETTA_LINGUA[INGLESE], SET_INGLESE);
            pagine[FRANCESE] = super.addPagina(ETICHETTA_LINGUA[FRANCESE], SET_FRANCESE);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * crea e disegna la pagina della ricetta
     */
    private void creaPaginaRicetta() {
        /* variabili e costanti locali di lavoro */
        String unTitolo = "ricetta";

        try {    // prova ad eseguire il codice
            /* crea una pagina completa col titolo */
            super.addPagina(unTitolo, SET_RICETTA);
        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaPagine() {

        try {    // prova ad eseguire il codice

            /* crea tutte le pagine in lingua (italiano compreso) */
            this.creaPagineLingua();

//            /* aggiunge alcuni campi alla pagina italiano */
//            this.disegnaPaginaItaliano();
//
//            /* aggiunge il nome italiano del piatto alle pagine in lungua */
////            this.disegnaPagineLingua();
//

            /* disegna la pagina per la ricetta */
            this.creaPaginaRicetta();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void sincronizza() {
        super.sincronizza();
        this.regolaCampoCarne();
    }


    /**
     * Abilita la possibilita' di scelta carne/pesce.
     * <p/>
     * Abilita il campo in funzione della
     * categoria di piatto selezionata nel popup <br>
     */
    private void regolaCampoCarne() {
        Object valore;
        int codCategoria;
        boolean suddivisibile;
        Campo unCampoCarne = null;

        try { // prova ad eseguire il codice

            valore = this.getValore(Piatto.CAMPO_CATEGORIA);
            codCategoria = Libreria.getInt(valore);
            suddivisibile = CategoriaModulo.isSuddivisibile(codCategoria);
            unCampoCarne = this.getCampo(Piatto.CAMPO_CARNE);
            unCampoCarne.setModificabile(suddivisibile);
            if (!suddivisibile) {
                unCampoCarne.setValore(0);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    private Pagina[] getPagineScheda() {
        return pagine;
    }


    private void setPagine(Pagina[] pagine) {
        this.pagine = pagine;
    }

}// fine della classe
