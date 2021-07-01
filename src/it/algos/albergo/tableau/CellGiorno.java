package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import java.awt.geom.Rectangle2D;
import java.util.Date;
import java.util.Map;

/**
 * Cella di tipo Giorno.
 * </p>
 */
class CellGiorno extends DefaultGraphCell {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param data del giorno
     * @param posizione nella barra orizzontale (0 per la prima posizione)
     */
    CellGiorno(Date data, int posizione) {

        super(null); // non ha ancora lo UserObject

        /* crea e registra uno UserObject con i dati ricevuti */
        UserObjectGiorno uo = new UserObjectGiorno();
        uo.setData(data);
        this.setUserObject(uo);
        this.inizia(posizione);

    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     * <br>
     * @param posizione nella barra (0 per la prima posizione)
     */
    private void inizia(int posizione) {
        /* variabili e costanti locali di lavoro */
        int hCell = Tableau.H_CELLE_GIORNO;
        int wCell = Tableau.H_MODULE;
        int x = posizione * wCell;
//        x = x + (Tableau.GRID_SIZE / 2);
        int y = 0;

        try { // prova ad eseguire il codice

            Map map = this.getAttributes();
            GraphConstants.setBounds(map, new Rectangle2D.Double(x, y, wCell, hCell));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

} // fine della classe
