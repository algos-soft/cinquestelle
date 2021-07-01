package it.algos.albergo.odg.odgriga;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Component;

/**
 * Renderer del campo camera di provenienza o destinazione
 */
final class RendererCameraLink extends RendererBase {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     *
     */
    public RendererCameraLink(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public Component getTableCellRendererComponent(JTable table,
                                                   Object oggetto,
                                                   boolean isSelected,
                                                   boolean b,
                                                   int riga,
                                                   int colonna) {
        /* variabili e costanti locali di lavoro */
        Component comp=null;
        String stringa = "";

        try { // prova ad eseguire il codice

//            int chiave = this.getChiaveRiga(riga, table);
//            int num = OdgRigaModulo.get().query().valoreInt(this.getCampoOsservato(), chiave);

            /* converte il codice camera in stringa */
            int num = Libreria.getInt(oggetto);
            if (num>0) {
                stringa = CameraModulo.get().query().valoreStringa(Camera.Cam.camera.get(), num);
            }// fine del blocco if

            comp = super.getTableCellRendererComponent(table, stringa, isSelected, b, riga, colonna);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


}