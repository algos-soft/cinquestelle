/**
 * Title:     ListinoScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-apr-2007
 */
package it.algos.albergo.listino;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;

/**
 * Scheda specifica del Listino.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 26-apr-2007
 */
public class ListinoScheda extends SchedaBase implements Listino {

    /* pannello switch per prezzo fisso o variabile */
    private Pannello panPrezzi;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo di riferimento
     */
    public ListinoScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setPreferredSize(600, 597);
    }


    public void avvia(int codice) {
        super.avvia(codice);
        this.regolaTipoVisibile();
        this.regolaPanPrezzi();
    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        Pagina pag;

        try { // prova ad eseguire il codice

            /* crea la pagina e aggiunge campi e pannelli */
            pag = this.addPagina("generale");
            pag.add(this.creaPan1());
            pag.add(Cam.sottoconto.get());
            pag.add(this.creaPanPops());
            pag.add(Cam.giornaliero.get());
            pag.add(this.creaPanPrezzi());
            pag.add(Cam.disattivato.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Crea il pannello 1.
     * <p/>
     *
     * @return il pannello 1
     */
    private Pannello creaPan1() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice
            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.sigla.get());
            pan.add(Cam.descrizione.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello popups.
     * <p/>
     *
     * @return il pannello popups
     */
    private Pannello creaPanPops() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice
            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.ambitoPrezzo.get());
            pan.add(Cam.tipoPrezzo.get());
            pan.add(Cam.modoPrezzo.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea e registra il pannello prezzi.
     * <p/>
     * Questo pannello contiene alternativamente il campo prezzo
     * o il campo navigatore periodi a seconda del tipo di
     * prezzo fisso o variabile
     *
     * @return il pannello prezzi creato
     */
    private Pannello creaPanPrezzi() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice
            pan = PannelloFactory.verticale(this);
            this.setPanPrezzi(pan);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Regola la visibilità del campo Tipo Prezzo in funzione dell'ambito del prezzo.
     * <p/>
     * Se l'ambito prezzo è extra, nasconde il campo Tipo.
     * Altimenti, mostra il campo Tipo.
     */
    private void regolaTipoVisibile() {
        /* variabili e costanti locali di lavoro */
        int ambitoPrezzo;
        boolean visibile;
        Campo campoTipo;
        Object oggetto;

        try {    // prova ad eseguire il codice

            /* recupera l'ambito del prezzo */
            oggetto = this.getValore(Cam.ambitoPrezzo.get());
            ambitoPrezzo = Libreria.getInt(oggetto);

            /* se extra, nasconde il campo tipo */
            visibile = true;
            if (ambitoPrezzo == AmbitoPrezzo.extra.getCodice()) {
                visibile = false;
            }// fine del blocco if

            /* regola la visibilità del campo */
            campoTipo = this.getCampo(Cam.tipoPrezzo.get());
            campoTipo.setVisibile(visibile);

            /* regola l'obbligatorietà del campo */
            if (visibile) {
                if (!campoTipo.isObbligatorio()) {
                    campoTipo.decora().obbligatorio();
                }// fine del blocco if
            } else {
                if (campoTipo.isObbligatorio()) {
                    campoTipo.decora().eliminaObbligatorio();
                }// fine del blocco if
            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    @Override protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */

        /* se si modifica l'ambito del prezzo,
         * regola la visibilità del campo tipo di prezzo */
        if (campo.equals(this.getCampo(Cam.ambitoPrezzo.get()))) {
            this.regolaTipoVisibile();
        }// fine del blocco if

        /* se si modifica il modo prezzo (fisso o variabile),
         * inserisce il campo appropriato nel pannello prezzi */
        if (campo.equals(this.getCampo(Cam.modoPrezzo.get()))) {
            this.regolaPanPrezzi();
        }// fine del blocco if


    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean disattivato;
        Campo campo;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* se disabiltato disabilita la scheda - lascia abilitato il flag */
            disattivato = this.getBool(Cam.disattivato.get());
            this.setModificabile(!disattivato);
            campo = this.getCampo(Cam.disattivato);
            campo.setModificabile(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il contenuto del pannello Prezzi.
     * <p/>
     * Inserisce nel pannello switch il campo appropriato
     * in funzione del modo prezzo corrente (fisso o variabile)
     */
    private void regolaPanPrezzi() {
        /* variabili e costanti locali di lavoro */
        Object valore;
        int codModo;
        ModoPrezzo modo;
        Campo campo = null;
        Pannello panPrezzi;

        try {    // prova ad eseguire il codice

            /* recupera il campo da utilizzare in funzione del modo prezzo */
            valore = this.getValore(Cam.modoPrezzo.get());
            codModo = Libreria.getInt(valore);
            modo = ModoPrezzo.getValore(codModo);
            if (modo != null) {
                switch (modo) {
                    case fisso:
                        campo = this.getCampo(Cam.prezzo.get());
                        break;
                    case variabile:
                        campo = this.getCampo(Cam.righe.get());
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

            /* svuota il pannello e inserisce il campo */
            panPrezzi = this.getPanPrezzi();
            panPrezzi.removeAll();
            if (campo != null) {

                /* fissa la larghezza massima del campo */
                Lib.Comp.bloccaLarMax(campo.getPannelloCampo());

                /* aggiunge il campo appropriato al pannello (che cambia dimensione) */
                panPrezzi.add(campo);

                /* forza la scheda ad effettuare il layout dei componenti */
                this.validate();

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    private Pannello getPanPrezzi() {
        return panPrezzi;
    }


    private void setPanPrezzi(Pannello panPrezzi) {
        this.panPrezzi = panPrezzi;
    }


}// fine della classe
