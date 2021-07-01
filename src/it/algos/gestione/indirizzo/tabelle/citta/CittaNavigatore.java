/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-mar-2006
 */
package it.algos.gestione.indirizzo.tabelle.citta;

import it.algos.base.azione.AzSpecifica;
import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.awt.event.ActionEvent;

/**
 * Navigatore di default modulo città.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 18.14.31
 */
public class CittaNavigatore extends NavigatoreLS {

    /**
     * riferimento all'azione di riclassificazione città
     */
    private Azione azRiclassifica;

    /**
     * dialogo di riclassificazione
     */
    private CittaDialogoRiclassificazione dialogoRiclassifica;


    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public CittaNavigatore(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

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

            this.setUsaPannelloUnico(true);
            this.setUsaFrecceSpostaOrdineLista(false);
            this.setUsaRicerca(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Azione azione;

        super.inizializza();

        try { // prova ad eseguire il codice
            azione = new AzRiclassifica();
            this.setAzRiclassifica(azione);
            this.addAzione(azione);
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizializza


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        int quante;
        boolean abilita;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* abilitazione dell'azione Riclassifica
            * deve essere selezionata almeno una riga*/
            azione = this.getAzRiclassifica();
            quante = this.getLista().getQuanteRigheSelezionate();
            abilita = (quante > 0);
            azione.setEnabled(abilita);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Esegue la riclassificazione per le città correntemente selezionate in lista.
     * <p/>
     */
    private void riclassifica() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Filtro filtroSelezionate;
        Filtro filtroVerificate;
        Filtro filtro;
        int quanti;
        CittaDialogoRiclassificazione dialogo = null;
        int[] sostituire;
        int sostitutiva;
        boolean riuscito;

        try {    // prova ad eseguire il codice

            /* controlla che tra le città selezionate non ce ne siano di ufficiali */
            filtroSelezionate = this.getLista().getFiltroSelezionati();
            filtroVerificate = FiltroFactory.creaVero(Citta.Cam.verificato.get());
            filtro = new Filtro();
            filtro.add(filtroSelezionate);
            filtro.add(filtroVerificate);
            quanti = this.query().contaRecords(filtro);
            if (quanti > 0) {
                new MessaggioAvviso("Non si possono riclassificare città già verificate.");
                continua = false;
            }// fine del blocco if

            /* presenta il dialogo di riclassificazione */
            if (continua) {

                /* recupera il dialogo di riclassificazione (lazy initialization)*/
                dialogo = this.getDialogoRiclassifica();
                dialogo.setFiltroDaRiclassificare(filtroSelezionate);
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            /* esegue la riclassificazione */
            if (continua) {
                sostituire = dialogo.getCodCittaSostituire();
                sostitutiva = dialogo.getCodCittaSostitutiva();
                riuscito = CittaModulo.riclassifica(sostituire, sostitutiva);
                if (riuscito) {
                    this.aggiornaLista();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    private Azione getAzRiclassifica() {
        return azRiclassifica;
    }


    private void setAzRiclassifica(Azione azRiclassifica) {
        this.azRiclassifica = azRiclassifica;
    }


    private CittaDialogoRiclassificazione getDialogoRiclassifica() {
        /* variabili e costanti locali di lavoro */
        CittaDialogoRiclassificazione dialogo;

        try { // prova ad eseguire il codice
            dialogo = this.dialogoRiclassifica;
            if (dialogo == null) {
                dialogo = new CittaDialogoRiclassificazione(this.getModulo());
                this.setDialogoRiclassifica(dialogo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dialogoRiclassifica;
    }


    private void setDialogoRiclassifica(CittaDialogoRiclassificazione dialogoRiclassifica) {
        this.dialogoRiclassifica = dialogoRiclassifica;
    }


    /**
     * Riclassifica tutte le città correntemente selezionate.
     * </p>
     */
    private final class AzRiclassifica extends AzSpecifica {

        /**
         * Costruttore senza parametri.
         */
        public AzRiclassifica() {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili*/
            super.setIconaMedia("recycle24");
            super.setTooltip("Riclassifica le città selezionate");
            super.setUsoLista(true);
        }// fine del metodo costruttore senza parametri


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                riclassifica();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'azione interna'



}// fine della classe