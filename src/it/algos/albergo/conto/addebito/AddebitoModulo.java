/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.conto.addebito;

import it.algos.albergo.Albergo;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.movimento.MovimentoModulo;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottoconto;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottocontoModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;

import java.awt.event.ActionEvent;

/**
 * Addebito - Contenitore dei riferimenti agli oggetti del package.
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
 * @version 1.0 / 2 feb 2006
 */
public class AddebitoModulo extends MovimentoModulo {


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public AddebitoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(Addebito.NOME_MODULO);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(Addebito.TITOLO_FINESTRA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public AddebitoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(Addebito.NOME_MODULO, unNodo);

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
        /* selezione del modello (obbligatorio) */
        super.setModello(new AddebitoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(Addebito.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(Addebito.TITOLO_MENU);

    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new ListinoModulo());
            super.creaModulo(new CameraModulo());
            super.creaModulo(new ClienteAlbergoModulo());
            super.creaModulo(new ContoModulo());
            super.creaModulo(new AlbSottocontoModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il Navigatore da visualizzare all'interno
     * della scheda del conto.
     * <p/>
     *
     * @return il Navigatore creato
     */
    protected Navigatore creaNavConto() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try { // prova ad eseguire il codice

            /* crea un navigatore per il conto */
            nav = new AddebitoNavConto(this);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    protected void regolaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        super.regolaNavigatori();

        try { // prova ad eseguire il codice

            /* cambia la scheda al navigatore di default */
            nav = this.getNavigatoreDefault();
            nav.addSchedaCorrente(new AddebitoScheda(this));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
            super.addModuloVisibile(Camera.NOME_MODULO);
            super.addModuloVisibile(ClienteAlbergo.NOME_MODULO);
            super.addModuloVisibile(AlbSottoconto.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Addebiti multipli.
     * </p>
     * Apre un dialogo per inserimenti multipli di addebiti <br>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     *
     * @param codConto eventuale conto di partenza, 0 per nessun conto
     */
    public void esegueAddebiti(int codConto) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        boolean chiuso;
        Modulo modConto;
        AddebitoDialogo dialogo;


        try { // prova ad eseguire il codice

            /* se c'è un conto di partenza controlla che non sia chiuso */
            if (continua) {
                if (codConto > 0) {
                    modConto = Albergo.Moduli.Conto();
                    chiuso = modConto.query().valoreBool(Conto.Cam.chiuso.get(), codConto);
                    continua = !chiuso;
                }// fine del blocco if
            }// fine del blocco if

            /* crea, regola e presenta il dialogo */
            if (continua) {
                dialogo = new AddebitoDialogo(this, codConto);
                dialogo.avvia();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Addebiti multipli.
     * </p>
     * Apre un dialogo per inserimenti multipli di addebiti <br>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    public void esegueAddebiti(ActionEvent unEvento) {
        this.esegueAddebiti(0);
    }// fine del metodo


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static AddebitoModulo get() {
        return (AddebitoModulo)ModuloBase.get(Addebito.NOME_MODULO);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new AddebitoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
