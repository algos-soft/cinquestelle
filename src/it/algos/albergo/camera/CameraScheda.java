package it.algos.albergo.camera;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.scheda.SchedaDefault;

/**
 * Scheda specifiche del pacchetto Camera.
 * </p>
 * <p/>
 * Utilizza il set standard di campi, cioè tutti <br>
 * Intercetta la modifica del campo navigatore sub-righe RCC, per modificare il popup successivo <br>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author Gac
 * @version 1.0 / 9-apr-2009 ore 09:51
 */
public final class CameraScheda extends SchedaDefault implements Camera {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per la scheda
     */
    public CameraScheda(Modulo modulo) {
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
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia




    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Recupera il db <br>
     * Chiede al db i dati del record <br>
     * Regola i dati della scheda <br>
     */
    @Override public void avvia(int codice) {
        super.avvia(codice);
        this.ricaricaComboCompoStandard();
    }


    /**
     * Metodo eseguito quando lo stato backup/memoria di un campo cambia.
     * <p/>
     * Stato inteso come uguaglianza/differenza tra backup e memoria <br>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo che ha generato l'evento
     */
    @Override
    protected void eventoStatoModificato(Campo campo) {
        try { // prova ad eseguire il codice
            super.eventoStatoModificato(campo);

            /* ricarica il popup del campo composizione */
            if (this.isCampo(campo, Cam.righeCompo)) {
                this.ricaricaComboCompoStandard();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ricarica il popup del campo composizione standard.
     * </p>
     * Filtra solo le combinazioni possibili, elencate nella lista del campo nav righe <br>
     * Se la lista di composizioni possibili è vuota, il filtro deve essere nullo
     * per caricare tutti i valori nel popup <br>
     */
    private void ricaricaComboCompoStandard() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Campo campoPop = null;
        int codCam;
        int codCompo;

        try { // prova ad eseguire il codice
            codCam = this.getCodice();
            continua = (codCam > 0);

            if (continua) {
                campoPop = this.getCampo(Cam.composizione);
                continua = (campoPop != null);
            }// fine del blocco if

            if (continua) {
                CameraLib.ricaricaCompo(codCam, campoPop);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe