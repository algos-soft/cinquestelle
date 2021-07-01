/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      17-gen-2005
 */
package it.algos.albergo.ristorante.categoria;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.modulo.ModuloMemoria;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreFactory;
import it.algos.base.progetto.Progetto;

import java.util.ArrayList;

/**
 * Categoria - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li> Regola il riferimento al Modello specifico (obbligatorio) </li>
 * <li> Regola i titoli di Menu e Finestra del Navigatore </li>
 * <li> Regola eventualmente alcuni aspetti specifici del Navigatore </li>
 * <li> Crea altri eventuali <strong>Moduli</strong> indispensabili per il
 * funzionamento di questo modulo </li>
 * <li> Rende visibili nel Menu gli altri moduli </li>
 * <li> Regola eventuali funzionalit&agrave; specifiche del Navigatore </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 17-gen-2005 ore 15.48.30
 */
public final class CategoriaModulo extends ModuloBase implements Categoria {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Categoria.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Categoria.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Categoria.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default) <br>
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public CategoriaModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public CategoriaModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

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

        /* selezione del modello (obbligatorio) */
        super.setModello(new CategoriaModello());

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* selezione della scheda (facoltativo) */
        super.addSchedaNavCorrente(new CategoriaScheda(this));

    }// fine del metodo inizia


//    /**
//     * .
//     * <p/>
//     */
//    public boolean inizializza() {
//        /* variabili e costanti locali di lavoro */
//        boolean flag = false;
//
//        try { // prova ad eseguire il codice
//            flag = super.inizializza();
//
//            Navigatore nav = this.getNavigatoreDefault();
//            ToolBar tb = nav.getPortaleScheda().getToolBar();
//            Icon icona = Lib.Risorse.getIconaBase("info24");
//
//            JButton bot = new JButton(icona);
//            bot.setOpaque(false);
//            bot.setBorderPainted(false);
//            bot.setContentAreaFilled(false); // serve per bug su XP
//            bot.setToolTipText("Apre lo storico del cliente");
//
//            bot.setBorder(null);
//            tb.getToolBar().add(Box.createHorizontalGlue());
//            tb.getToolBar().add(bot);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return flag;
//    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        Navigatore nav = null;

        try { // prova ad eseguire il codice

            nav = this.getNavigatoreDefault();
            nav.setUsaFrecceSpostaOrdineLista(true);
            nav.setUsaPannelloUnico(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void avvia() {
        super.avvia();
    } /* fine del metodo */


    /**
     * Controlla se una categoria è suddivisibile in carne / pesce...
     * <p/>
     *
     * @param codice della categoria
     *
     * @return true se è suddivisibile
     */
    public static boolean isSuddivisibile(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean suddivisibile = false;
        Modulo modulo;
        Object valore;

        try {    // prova ad eseguire il codice

            modulo = CategoriaModulo.get();
            valore = modulo.query().valoreCampo(Categoria.CAMPO_CARNE_PESCE, codice);
            suddivisibile = Libreria.getBool(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return suddivisibile;
    }


    /**
     * .
     * <p/>
     */
    private void test2() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Campo campo;

        try {    // prova ad eseguire il codice

            ArrayList<Campo> campi = new ArrayList<Campo>();

            campo = CampoFactory.comboLinkPop("linkcategoria");
            campo.setNomeModuloLinkato(Categoria.NOME_MODULO);
            campo.setVisibileVistaDefault(false);
            campi.add(campo);

            campo = CampoFactory.sigla();
            campo.setVisibileVistaDefault();
            campi.add(campo);

            campo = CampoFactory.descrizione();
            campo.setVisibileVistaDefault();
            campi.add(campo);

            Modulo modulo = new ModuloMemoria("pippo", campi);
            modulo.avvia();
            nav = modulo.getNavigatoreDefault();
            nav.apriNavigatore();


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * .
     * <p/>
     */
    private void test3() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Db db;
        Connessione conn;

        try {    // prova ad eseguire il codice

            db = this.getModello().creaDbMemoria("pippo");
            conn = db.creaConnessione();
            nav = NavigatoreFactory.listaScheda(this);
            nav.setConnessione(conn);
            nav.avvia();
            nav.apriNavigatore();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static CategoriaModulo get() {
        return (CategoriaModulo)ModuloBase.get(CategoriaModulo.NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {

        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new CategoriaModulo();

        /*
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);

    }// fine del metodo main


}// fine della classe
