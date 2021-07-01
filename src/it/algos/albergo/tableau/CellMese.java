package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import java.awt.geom.Rectangle2D;
import java.util.Map;

/**
 * Cella di tipo Mese.
 * </p>
 */
class CellMese extends DefaultGraphCell {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param nome del mese
     * @param posizione nella barra orizzontale (0 per la prima posizione)
     * @param quantiGG quanti giorni deve essere lunga la cella
     */
    CellMese(String nome, int posizione, int quantiGG) {

        super(null); // non ha ancora lo UserObject

        /* crea e registra uno UserObject con i dati ricevuti */
        UserObjectMese uo = new UserObjectMese();
        uo.setNome(nome);
        this.setUserObject(uo);
        this.inizia(posizione, quantiGG);

    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     * <br>
     * @param posizione nella barra (0 per la prima posizione)
     * @param quantiGG quanti giorni deve essere lunga la cella
     */
    private void inizia(int posizione, int quantiGG) {
        /* variabili e costanti locali di lavoro */
        int hCell = Tableau.H_CELLE_MESE;
        int wCell = Tableau.H_MODULE*quantiGG-1;
        int x = posizione * Tableau.H_MODULE+1; // +1 per lasciare un filo vuoto tra uno e l'altro
        int y = 0;

        try { // prova ad eseguire il codice

            Map map = this.getAttributes();
            GraphConstants.setBounds(map, new Rectangle2D.Double(x, y, wCell, hCell));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

} // fine della classe