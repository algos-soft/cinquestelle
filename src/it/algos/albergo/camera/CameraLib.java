package it.algos.albergo.camera;

import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.camera.righecameracompo.RCC;
import it.algos.albergo.camera.righecameracompo.RCCModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;

/**
 * Classe statica di libreria per il package Camera
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author Gac
 * @version 1.0 / 11-apr-2009 ore 09:41
 */
public final class CameraLib {

    /**
     * Ricarica il popup del campo composizione.
     * </p>
     * Filtra solo le combinazioni possibili, elencate nella lista del campo nav righe <br>
     * Se la lista di composizioni possibili è vuota, il filtro deve essere nullo
     * per caricare tutti i valori nel popup <br>
     *
     * @param cod codice del record di Camera
     * @param campoPop campo del popup che mostra le composizioni possibili per la camera
     */
    public static void ricaricaCompo(int cod, Campo campoPop) {
        /* variabili e costanti locali di lavoro */
        Filtro filtroPop;

        try { // prova ad eseguire il codice

            filtroPop = getFiltroComposizioniPossibili(cod);
            campoPop.setFiltroBase(filtroPop);
            campoPop.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce il filtro che seleziona le possibili composizioni per una data camera.
     * <p/>
     * Il filtro si applica al modulo CompoCameraModulo
     *
     * @param codCamera codice della camera
     *
     * @return il filtro che seleziona le composizioni possibili,
     * null se non sono state definite composizioni possibili
     */
    static Filtro getFiltroComposizioniPossibili(int codCamera) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro=null;

        try { // prova ad eseguire il codice

            Filtro filtroCod = FiltroFactory.crea(RCC.Cam.linkcamera.get(), codCamera);
            Modulo modRighe = RCCModulo.get();
            ArrayList codici = modRighe.query().valoriCampo(RCC.Cam.linkcompo.get(), filtroCod);
            if (codici.size() > 0) {
                Modulo modCompo = CompoCameraModulo.get();
                filtro = FiltroFactory.elenco(modCompo, codici);
            } else {
                filtro = null;
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;

    }



}// fine della classe