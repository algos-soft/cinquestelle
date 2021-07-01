/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-mar-2005
 */
package it.algos.albergo.ristorante.righemenupiatto;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.albergo.ristorante.piatto.PiattoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.logica.LogicaBase;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

/**
 * Repository di logiche specifiche del modulo RMP.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-mar-2005 ore 23.33.25
 */
public class RMPLogica extends LogicaBase {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo proprietario di questa logica.
     */
    public RMPLogica(Modulo modulo) {
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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Regola l'ordine di un record all'interno di un menu.
     * <p/>
     * Assegna il valore del campo ordine di un record nell'ambito del
     * proprio menu, in modo che cada per ultimo nella propria categoria
     * tra i piatti soggetti a comanda.<br>
     *
     * @param codRiga codice della riga da regolare
     */
    public void regolaOrdineInMenu(int codRiga) {

        /** variabili e costanti locali di lavoro */
        Modulo moduloPiatto = null;
        Modulo moduloRighe = null;

        Campo campoOrdineRiga = null;

        int codPiatto = 0;
        int codiceMenu = 0;
        int codiceCategoria = 0;

        Filtro filtro = null;

        int ordineMassimo = 0;
        Integer nuovoOrdine = null;
        int valoreOrdine = 0;
        Integer valoreNuovo = null;

        Object valore = null;
        int[] chiavi = null;
        int chiave = 0;

        try { // prova ad eseguire il codice

            /* regolazione variabili di lavoro */
            moduloRighe = this.getModulo();
            moduloPiatto = this.getModuloPiatto();
            campoOrdineRiga = this.getModulo().getCampoOrdine();

            /* recupera il codice del piatto dalla riga */
            valore = moduloRighe.query().valoreCampo(RMP.CAMPO_PIATTO, codRiga);
            codPiatto = Libreria.objToInt(valore);

            /* recupera il codice del menu dalla riga */
            valore = moduloRighe.query().valoreCampo(RMP.CAMPO_MENU, codRiga);
            codiceMenu = Libreria.objToInt(valore);

            /* recupera il codice categoria dal piatto */
            valore = moduloPiatto.query().valoreCampo(Piatto.CAMPO_CATEGORIA, codPiatto);
            codiceCategoria = Libreria.objToInt(valore);

            /* crea un filtro per ottenere tutte le righe comandabili di
             * questa categoria esistenti nel menu */
            filtro = this.filtroRigheCategoriaComandabili(codiceMenu, codiceCategoria);

            /* aggiunge un filtro per escludere la riga corrente */
            filtro.add(this.filtroEscludiRiga(codRiga));

            /* determina il massimo numero d'ordine tra le righe selezionate dal filtro */
            ordineMassimo = this.getModulo().query().valoreMassimo(campoOrdineRiga, filtro);

            /* determina il nuovo numero d'ordine da assegnare alla riga */
            if (ordineMassimo != 0) {
                nuovoOrdine = new Integer(ordineMassimo + 1);
            } else {
                nuovoOrdine = new Integer(1);
            } /* fine del blocco if-else */

            /* apre un "buco" nella categoria spostando verso il basso di una
      * posizione tutte le righe di questa categoria che hanno un numero
      * d'ordine uguale o superiore al numero d'ordine della nuova riga
      * (sono le righe non comandabili) */

            /* crea un filtro per selezionare le righe non comandabili della categoria */
            filtro = filtroRigheCategoriaNonComandabili(codiceMenu, codiceCategoria);

            /* aggiunge un filtro per escludere la riga corrente */
            filtro.add(this.filtroEscludiRiga(codRiga));

            /* recupera le chiavi dei record selezionati dal filtro */
            chiavi = this.getModulo().query().valoriChiave(filtro);

            /* spazzola le chiavi */
            for (int k = 0; k < chiavi.length; k++) {
                chiave = chiavi[k];
                valore = this.getModulo().query().valoreCampo(campoOrdineRiga, chiave);
                valoreOrdine = Libreria.objToInt(valore);
                valoreNuovo = new Integer(valoreOrdine + 1);
                this.getModulo().query().registraRecordValore(chiave, campoOrdineRiga, valoreNuovo);
            } // fine del ciclo for

            /* assegna il nuovo ordine alla riga mettendola nel buco */
            this.getModulo().query().registraRecordValore(codRiga, campoOrdineRiga, nuovoOrdine);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea un filtro per caricare tutte le righe comandabili
     * per un dato menu e una data categoria.
     * <p/>
     *
     * @param codiceMenu il codice del menu
     * @param codiceCategoria il codice della categoria
     *
     * @return il filtro richiesto
     */
    private Filtro filtroRigheCategoriaComandabili(int codiceMenu, int codiceCategoria) {
        return this.filtroRigheCategoria(codiceMenu, codiceCategoria, true);
    } // fine del metodo


    /**
     * Crea un filtro per caricare tutte le righe non comandabili
     * per un dato menu e una data categoria.
     * <p/>
     *
     * @param codiceMenu il codice del menu
     * @param codiceCategoria il codice della categoria
     *
     * @return il filtro richiesto
     */
    private Filtro filtroRigheCategoriaNonComandabili(int codiceMenu, int codiceCategoria) {
        return this.filtroRigheCategoria(codiceMenu, codiceCategoria, false);
    } // fine del metodo


    /**
     * Crea un filtro per caricare tutte le righe di un dato menu
     * per una data categoria e per un dato valore del flag comandabile.
     * <p/>
     *
     * @param codiceMenu il codice del menu
     * @param codiceCategoria il codice della categoria
     * @param comandabile true per le righe comandabili, false non comandabili
     *
     * @return il filtro per selezionare le righe richieste
     */
    private Filtro filtroRigheCategoria(int codiceMenu, int codiceCategoria, boolean comandabile) {

        /* variabili e costanti locali di lavoro */
        Filtro filtroOut = null;
        Filtro filtro = null;
        Modulo moduloPiatto = null;
        Campo campoLinkMenu = null;
        Campo campoLinkCategoria = null;
        Campo campoPiattoComandabile = null;

        try {    // prova ad eseguire il codice
            moduloPiatto = Progetto.getModulo(Ristorante.MODULO_PIATTO);
            campoLinkMenu = this.getModulo().getCampo(RMP.CAMPO_MENU);
            campoLinkCategoria = moduloPiatto.getCampo(Piatto.CAMPO_CATEGORIA);
            campoPiattoComandabile = moduloPiatto.getCampo(Piatto.CAMPO_COMANDA);

            filtroOut = new Filtro();
            filtro = FiltroFactory.crea(campoLinkMenu, codiceMenu);
            filtroOut.add(filtro);
            filtro = FiltroFactory.crea(campoLinkCategoria, codiceCategoria);
            filtroOut.add(filtro);
            filtro = FiltroFactory.crea(campoPiattoComandabile, comandabile);
            filtroOut.add(filtro);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroOut;
    } // fine del metodo


    /**
     * Crea un filtro per escludere una riga di menu.
     * <p/>
     *
     * @param codice il codice della riga da escludere
     *
     * @return il filtro per escludere la riga
     */
    private Filtro filtroEscludiRiga(int codice) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Campo campo = null;

        try {    // prova ad eseguire il codice
            campo = this.getModulo().getCampoChiave();
            filtro = FiltroFactory.crea(campo, Operatore.DIVERSO, codice);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna il modulo Piatto.
     * <p/>
     *
     * @return il modulo Piatto
     */
    private PiattoModulo getModuloPiatto() {
        return (PiattoModulo)Progetto.getModulo(Ristorante.MODULO_PIATTO);
    }

}// fine della classe
