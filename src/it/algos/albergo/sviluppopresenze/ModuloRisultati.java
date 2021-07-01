package it.algos.albergo.sviluppopresenze;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modulo.ModuloMemoria;
import it.algos.base.navigatore.Navigatore;

import java.util.ArrayList;

/**
 * Modulo memoria per mantenere i risultati dello sviluppo
 * </p>
 */
class ModuloRisultati extends ModuloMemoria {

    /**
     * Costruttore completo
     * <p/>
     * Usa un nome automatico casuale per il modulo
     * Rende visibili in vista default tutti i campi specifici
     *
     * @param campi campi specifici del modulo (oltre ai campi standard)
     */
    public ModuloRisultati(ArrayList<Campo> campi) {

        super(campi, false);

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
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public boolean prepara() {
        /* variabili e costanti locali di lavoro */
        boolean preparato=false;

        try { // prova ad eseguire il codice

            preparato = super.prepara();

            /* la prima colonna (suddivisione) va sempre ordinata
             * nell'ordine naturale di creazione */
            Campo campo = this.getCampo(Nomi.sigla.get());
            Campo campoOrd = this.getCampoOrdine();
            campo.setOrdinePrivato(campoOrd);

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
        Navigatore nav = this.getNavigatoreDefault();
//        nav.setUsaToolBarLista(false);
        nav.setUsaNuovo(false);
        nav.setUsaModifica(false);
        nav.setUsaRicerca(false);
        nav.setUsaElimina(false);
        nav.setUsaStampaLista(false);
        nav.setUsaSelezione(true);
        nav.setRigheLista(10);
        super.creaNavigatori();
    }


    /**
     * Elimina tutti i records e aggiorna la lista
     * <p/>
     */
    public void svuotaRisultati() {
        this.query().eliminaRecords();
        this.aggiornaLista();
    }


    /**
     * Aggiorna la lista dei risultati
     * <p/>
     */
    public void aggiornaLista() {
        this.getNavigatoreDefault().aggiornaLista();
    }



    /**
     * Controlla se esistono dei dati nella tabella dei risultati.
     * <p/>
     * Utilizzato per abilitare i bottoni
     *
     * @return true se esistono dei dati
     */
    public boolean isEsistonoDati() {
        /* variabili e costanti locali di lavoro */
        boolean esistono=false;
        int quanti;

        try { // prova ad eseguire il codice
            quanti = this.query().contaRecords();
            esistono = (quanti>0);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }

    /**
     * Ritorna i dati della tabella risultati
     * <p/>
     * @return il modello dati dei risultati
     */
    public Dati getDatiRisultati() {
        /* variabili e costanti locali di lavoro */
        Dati dati=null;
        Navigatore nav;
        Lista lista;
        TavolaModello modello;


        try { // prova ad eseguire il codice
            nav = this.getNavigatoreDefault();
            if (nav!=null) {
                lista = nav.getLista();
                if (lista!=null) {
                    modello = lista.getModello();
                    if (modello!=null) {
                        dati=modello.getDati();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Ritorna il numero di righe correntemente selezionate nella tabella dei risultati.
     * <p/>
     *
     * @return true se esistono dei dati
     */
    public int getQuanteRigheSelezionate() {
        return this.getLista().getQuanteRigheSelezionate();
    }
    


//    /**
//     * .
//     * <p/>
//     */
//    public Lista getLista () {
//        /* variabili e costanti locali di lavoro */
//        Lista lista  = null;
//
//        try {    // prova ad eseguire il codice
//            lista = this.getNavigatoreDefault();
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return ;
//}




} // fine della classe