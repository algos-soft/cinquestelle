package it.algos.albergo.stampeobbligatorie;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.ModuloMemoria;
import it.algos.base.navigatore.Navigatore;

import java.util.ArrayList;

/**
 * Classe 'interna'. </p>
 */
public abstract class ModuloInterno extends ModuloMemoria {

    /* riferimento al navigatore proprietario */
    private Navigatore navMaster;


    /**
     * Costruttore completo
     * <p/>
     * Usa un nome automatico casuale per il modulo
     * Rende visibili in vista default tutti i campi specifici
     *
     * @param campi campi specifici del modulo (oltre ai campi standard)
     * @param navMaster navigatore proprietario
     */
    public ModuloInterno(ArrayList<Campo> campi, Navigatore navMaster) {

        super(campi, false);
        this.setNavMaster(navMaster);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.getModello().setUsaCampoPreferito(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public boolean prepara() {
        /* variabili e costanti locali di lavoro */
        boolean preparato=false;
        Campo campo;

        try { // prova ad eseguire il codice

            preparato = super.prepara();

            /* cambia il titolo del campo Preferito */
            campo = this.getCampoPreferito();
            if (campo!=null) {
                campo.setTitoloColonna("capo");
                campo.setLarLista(50);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return preparato;
    }


    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            super.inizializza();
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


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
        super.creaNavigatori();
    }


    /**
     * Aggiorna la lista di dettaglio in base alla
     * selezione corrente della lista Master.
     * <p/>
     */
    public void aggiornaLista() {
    }


    /**
     * Ritorna la lista del Navigatore Master.
     * <p/>
     *
     * @return la lista del Navigatore Master
     */
    protected Lista getListaMaster() {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;
        Navigatore nav;

        try {    // prova ad eseguire il codice
            nav = this.getNavMaster();
            if (nav != null) {
                lista = nav.getLista();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    protected Navigatore getNavMaster() {
        return navMaster;
    }


    private void setNavMaster(Navigatore navMaster) {
        this.navMaster = navMaster;
    }



} // fine della classe