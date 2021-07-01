package it.algos.base.palette;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.pannello.PannelloFlusso;

/**
 * ToolBar della Palette.
 * <p/>
 * </ul>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  7 settembre 2003 ore 11.30
 */
public final class PaletteToolBar extends PannelloFlusso {

    /* palette di riferimento */
    private Palette palette;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param palette di riferimento
     * @param orientamento Layout.HORIZONTAL o Layout.VERTICAL
     */
    public PaletteToolBar(Palette palette, int orientamento) {
        
        /* rimanda al costruttore della superclasse */
        super(orientamento);

        this.setPalette(palette);

        /* regolazioni iniziali di riferimenti e variabili */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
//        this.setFloatable(false);
        this.setUsaGapFisso(true);
        this.setGapPreferito(0);
        this.setAllineamento(Layout.ALLINEA_CENTRO);
    } /* fine del metodo inizia */


    private Palette getPalette() {
        return palette;
    }


    private void setPalette(Palette palette) {
        this.palette = palette;
    }
}// fine della classe