/**
 * Title:     PortaleDialogo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      12-dic-2004
 */
package it.algos.base.portale;

import it.algos.base.azione.Azione;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;
import it.algos.base.finestra.FinestraPalette;
import it.algos.base.form.Form;
import it.algos.base.gestore.Gestore;
import it.algos.base.gestore.GestoreBase;
import it.algos.base.gestore.GestoreDialogo;

/**
 * Contenitore del Dialogo con Eventi, Azioni e Finestra.
 * </p>
 * Questa classe : <ul>
 * <li> Gestisce un'istanza di <code>Dialogo</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 12-dic-2004 ore 8.04.46
 */
public final class PortaleDialogo extends PortaleBase {

    /**
     * dialogo di riferimento
     */
    private Dialogo dialogo = null;


    /**
     * Costruttore completo con parametri.
     */
    public PortaleDialogo(Dialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(null);

        /* regola le variabili di istanza coi parametri */
        this.setDialogo(dialogo);
        this.setCompMain((DialogoBase)dialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Gestore gestore = null;

        try { // prova ad eseguire il codice

            gestore = new GestoreBase();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche. <br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se
     * non riescono a portare a termine la propria inizializzazione specifica.<br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {

        /* invoca il metodo sovrascritto della superclasse */
        super.inizializza();

        /* marca come inizializzato */
        this.setInizializzato(true);
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi
     */
    public void avvia() {
        /* invoca il metodo sovrascritto della superclasse */
        super.avvia();
    }


    /**
     * Crea la finestra per questo Portale.
     * <p/>
     * Crea una finestra Portale <br>
     * Invocato dal ciclo inizia() <br>
     * Regola le tre funzionalit&agrave della Finestra: <ul>
     * <li> Aggiunge il Portale Navigatore </li>
     * <li> Regola il menu della Finestra </li>
     * <li> Regola la StatusBar della Finestra </li>
     * </ul>
     */
    protected void creaFinestra() {
        /* variabili e costanti locali di lavoro */
        Finestra unaFinestra;

        try {    // prova ad eseguire il codice

            /* crea l'istanza della finestra flottante */
            unaFinestra = new FinestraPalette();
            this.setFinestra(unaFinestra);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola le azioni del portale.
     * <p/>
     * Metodo invocato dal ciclo avvia <br>
     * Spazzola tutta la collezione di azioni <br>
     * Invoca il metodo avvia di ogni azione, passandogli il parametro <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void regolaAzioni() {
        try {    // prova ad eseguire il codice

            for (Azione azione : this.getAzioni().values()) {
                azione.setPortale(this);
                azione.avvia(this.getGestore());
            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge le azioni al portale.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Aggiunge ogni singola azione a questo portale (anche se non viene usata) <br>
     * La singola azione viene creata dal metodo delegato della superclasse, che
     * la clona dal Progetto <br>
     */
    protected void addAzioni() {
        try {    // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.addAzioni();

            /* gruppo di azioni sul testo dei campi */
            super.addAzione(Azione.CONFERMA_DIALOGO);
            super.addAzione(Azione.ANNULLA_DIALOGO);
            super.addAzione(Azione.DIALOGO);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public Dialogo getDialogo() {
        return dialogo;
    }


    private void setDialogo(Dialogo dialogo) {
        this.dialogo = dialogo;
    }


    /**
     * Ritorna il form contenuto nel Portale.
     * <p/>
     *
     * @return il form contenuto nel Portale, null se il portale
     *         non contiene un form
     */
    public Form getForm() {
        return this.getDialogo();
    }


    public GestoreDialogo getGestore() {
        return this.getDialogo().getGestore();
    }

//    /**
//     * Ritorna il componente grafico contenuto nel portale
//     * oltre alla toolbar
//     * <p/>
//     *
//     * @return il componente grafico
//     */
//    public JComponent getContenuto() {
//        /* variabili e costanti locali di lavoro */
//        JComponent comp = null;
//
//        try { // prova ad eseguire il codice
//            // todo da implementare
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//        /* valore di ritorno */
//        return comp;
//    }


}// fine della classe
