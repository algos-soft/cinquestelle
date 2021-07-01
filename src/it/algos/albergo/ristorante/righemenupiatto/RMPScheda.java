/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-gen-2005
 */
package it.algos.albergo.ristorante.righemenupiatto;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.scheda.SchedaBase;

/**
 * Scheda della riga menu piatto usata nel menu.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su pi&ugrave; pagine, risulter&agrave;
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma &egrave; attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-gen-2005 ore 12.21.21
 */
public final class RMPScheda extends SchedaBase implements RMP {

    /**
     * Costruttore completo senza parametri.
     */
    public RMPScheda(Modulo modulo) {
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
    }// fine del metodo inizia


    public void inizializza() {
        super.inizializza();
    }


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            /* crea una pagina vuota col titolo */
            pagina = super.addPagina("Piatto");

            pan = new PannelloFlusso(this, Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.add(CAMPO_PIATTO);
            pan.add(CAMPO_PIATTO_CONGELATO);
            pagina.add(pan);

            pan = new PannelloFlusso(this, Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.add(CAMPO_CONTORNO);
            pan.add(CAMPO_CONTORNO_CONGELATO);
            pagina.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo campoCongelato;
        int codice;
        boolean congelato;

        if (campo.equals(Ristorante.Moduli.Rmp().getCampo(RMP.CAMPO_PIATTO))) {
            codice = (Integer)campo.getValore();
            congelato = this.isCongelato(codice);
            campoCongelato = this.getCampo(RMP.CAMPO_PIATTO_CONGELATO);
            campoCongelato.setValore(congelato);
        }// fine del blocco if

        if (campo.equals(Ristorante.Moduli.Rmp().getCampo(RMP.CAMPO_CONTORNO))) {
            codice = (Integer)campo.getValore();
            congelato = this.isCongelato(codice);
            campoCongelato = this.getCampo(RMP.CAMPO_CONTORNO_CONGELATO);
            campoCongelato.setValore(congelato);
        }// fine del blocco if


    }


    /**
     * Determina se un piatto e' congelato.
     * <p/>
     *
     * @param codice del piatto da controllare
     *
     * @return true se congelato
     */
    private boolean isCongelato(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean congelato = false;
        Modulo modulo = Ristorante.Moduli.Piatto();

        try {    // prova ad eseguire il codice
            congelato = modulo.query().valoreBool(Piatto.CAMPO_CONGELATO, codice);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return congelato;
    }


}// fine della classe
