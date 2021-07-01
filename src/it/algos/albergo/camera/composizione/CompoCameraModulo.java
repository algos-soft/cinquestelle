/**
 * Copyright(c): 2008
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 5 mar 2008
 */

package it.algos.albergo.camera.composizione;

import it.algos.albergo.camera.accessori.Accessori;
import it.algos.albergo.camera.accessori.AccessoriModulo;
import it.algos.albergo.camera.compoaccessori.CompoAccessori;
import it.algos.albergo.camera.compoaccessori.CompoAccessoriModulo;
import it.algos.albergo.camera.compoaccessori.WrapCompoAccessorio;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;

import java.util.ArrayList;

/**
 * Modulo Camposizione Camera.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 5 mar 2008
 */
public final class CompoCameraModulo extends ModuloBase {


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public CompoCameraModulo() {

        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(CompoCamera.NOME_MODULO);

    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public CompoCameraModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(CompoCamera.NOME_MODULO, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            /* selezione del modello (obbligatorio) */
            super.setModello(new CompoCameraModello());

            /* regola il titolo della finestra del navigatore */
            super.setTitoloFinestra(CompoCamera.TITOLO_FINESTRA);

            /* regola il titolo di questo modulo nei menu di altri moduli */
            super.setTitoloMenu(CompoCamera.TITOLO_FINESTRA);

            /* regola il nome pubblico utilizzato nella lista dai popup esterni */
            super.setNomePubblico("composizione");

            /* regola il flag tabella */
            this.setTabella(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }



    


    /**
     * Restituisce il numero totale di letti standard di una composizione.
     * <p/>
     *
     * @param codCompo codice della composizione
     *
     * @return il numero di letti della composizione
     */
    public static int getNumLetti(int codCompo) {
        /* variabili e costanti locali di lavoro */
        int numLetti = 0;
        int numAdulti;
        int numBambini;

        try { // prova ad eseguire il codice

            numAdulti = CompoCameraModulo.getNumLettiAdulti(codCompo);
            numBambini = CompoCameraModulo.getNumLettiBambini(codCompo);
            numLetti = numAdulti + numBambini;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numLetti;
    }


    /**
     * Restituisce il numero di letti per adulti di una composizione.
     * <p/>
     *
     * @param codCompo codice della composizione
     *
     * @return il numero di letti per adulti della composizione
     */
    public static int getNumLettiAdulti(int codCompo) {
        /* variabili e costanti locali di lavoro */
        int numLetti = 0;
        Modulo modCompo;

        try { // prova ad eseguire il codice

            modCompo = CompoCameraModulo.get();
            numLetti = modCompo.query().valoreInt(CompoCamera.Cam.numadulti.get(), codCompo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numLetti;
    }


    /**
     * Restituisce il numero di letti per bambini di una composizione.
     * <p/>
     *
     * @param codCompo codice della composizione
     *
     * @return il numero di letti per adulti della composizione
     */
    public static int getNumLettiBambini(int codCompo) {
        /* variabili e costanti locali di lavoro */
        int numLetti = 0;
        Modulo modCompo;

        try { // prova ad eseguire il codice

            modCompo = CompoCameraModulo.get();
            numLetti = modCompo.query().valoreInt(CompoCamera.Cam.numbambini.get(), codCompo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numLetti;
    }


    /**
     * Restituisce l'elenco degli accessori per una
     * composizione con le relative informazioni aggiuntive (q.tà etc).
     * <p/>
     * @param codCompo codice della composizione
     * @return lista di oggetti WrapCompoAccessorio con le informazioni
     */
    public static ArrayList<WrapCompoAccessorio> getAccessori(int codCompo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<WrapCompoAccessorio> lista  = new ArrayList<WrapCompoAccessorio>();
        Filtro filtro;
        Modulo modCompoAcc;

        try {    // prova ad eseguire il codice
            modCompoAcc = CompoAccessoriModulo.get();
            filtro = FiltroFactory.crea(CompoAccessori.Cam.composizione.get(), codCompo);
            Query query = new QuerySelezione(modCompoAcc);
            query.addCampo(CompoAccessori.Cam.accessorio);
            query.addCampo(CompoAccessori.Cam.quantita);
            Campo campoRotazione = AccessoriModulo.get().getCampo(Accessori.Cam.rotazionegg);
            query.addCampo(campoRotazione);
            query.setOrdine(AccessoriModulo.get().getCampoOrdine());
            query.setFiltro(filtro);
            Dati dati = modCompoAcc.query().querySelezione(query);
            for (int k = 0; k < dati.getRowCount(); k++) {
                int codAcc = dati.getIntAt(k, CompoAccessori.Cam.accessorio.get());
                int qta = dati.getIntAt(k, CompoAccessori.Cam.quantita.get());
                int gg = dati.getIntAt(k, campoRotazione);
                WrapCompoAccessorio wrapper = new WrapCompoAccessorio(codAcc, qta, gg);
                lista.add(wrapper);
            } // fine del ciclo for

            dati.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }



    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        super.addModuloVisibile(Accessori.NOME_MODULO);
    }// fine del metodo

    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static CompoCameraModulo get() {
        return (CompoCameraModulo)ModuloBase.get(CompoCamera.NOME_MODULO);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new CompoCameraModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe