/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 16-5-2007
 */

package it.algos.albergo.prenotazione.periodo.serviziospecifico;

import it.algos.albergo.tabelle.settore.Settore;
import it.algos.albergo.tabelle.settore.SettoreModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreFactory;
import it.algos.base.progetto.Progetto;
import it.algos.base.toolbar.ToolBar;

/**
 * Periodi di una prenotazione alberghiera.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 16-5-2007
 */
public final class ServizioSpecificoModulo extends ModuloBase implements ServizioSpecifico {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = ServizioSpecifico.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = ServizioSpecifico.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = ServizioSpecifico.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public ServizioSpecificoModulo() {
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
    public ServizioSpecificoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* selezione del modello (obbligatorio) */
        super.setModello(new ServizioSpecificoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        super.setTabella(true);
    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Anche eventuale creazione <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaNavigatori() {
        Navigatore nav;

        try { // prova ad eseguire il codice

            nav = NavigatoreFactory.listaScheda(this);
            nav.setUsaPannelloUnico(true);
            nav.setUsaStampaLista(false);
            nav.setUsaRicerca(false);
            nav.setRigheLista(3);
            nav.setUsaStatusBarLista(false);
            nav.setTipoIcona(ToolBar.ICONA_PICCOLA);
            nav.addSchedaCorrente(new ServizioSpecificoScheda(this));
            this.addNavigatore(nav, ServizioSpecifico.Nav.periodo.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    @Override
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new SettoreModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    @Override
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
            super.addModuloVisibile(Settore.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static ServizioSpecificoModulo get() {
        return (ServizioSpecificoModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new ServizioSpecificoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
