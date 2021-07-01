/**
 * Copyright(c): 2008
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 5 mar 2008
 */

package it.algos.albergo.camera.composizione;

import it.algos.albergo.camera.compoaccessori.CompoAccessori;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;

/**
 * Tracciato record della tavola Composizione Camera.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 5 mar 2008
 */
public final class CompoCameraModello extends ModelloAlgos implements CompoCamera {


    /**
     * Costruttore completo senza parametri.
     */
    public CompoCameraModello() {
        /* rimanda al costruttore della superclasse */
        super();

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
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(CompoCamera.NOME_TAVOLA);
    }


    /**
     * Creazione dei campi.
     * <p/>
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo sigla */
            unCampo = CampoFactory.testo(Cam.sigla);
            unCampo.setLarghezza(60);
            this.addCampo(unCampo);

            /* campo descrizione */
            unCampo = CampoFactory.testo(Cam.descrizione);
            unCampo.setLarghezza(170);
            this.addCampo(unCampo);

            /* campo numero letti per adulti */
            unCampo = CampoFactory.intero(Cam.numadulti);
            unCampo.setLarghezza(40);
            this.addCampo(unCampo);

            /* campo numero letti per bambini */
            unCampo = CampoFactory.intero(Cam.numbambini);
            unCampo.setLarghezza(40);
            this.addCampo(unCampo);

            /* campo navigatore sub-lista accessori */
            unCampo = CampoFactory.navigatore(Cam.righeAccessori, CompoAccessori.NOME_MODULO, CompoAccessori.Nav.navInComposizione);
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param estratto codifica dell'estratto desiderato
     * @param chiave   con cui effettuare la ricerca
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti estratto, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((Est)estratto) {
                case descrizione:
                    unEstratto = this.getEstratto(estratto, chiave, Cam.descrizione.get());
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }

} // fine della classe