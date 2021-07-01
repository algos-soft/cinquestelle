package it.algos.albergo.tableau;

import org.jgraph.graph.DefaultGraphCell;

import javax.swing.SwingConstants;

/**
 * Cella di tipo Griglia.
 * </p>
 */
class CellGriglia extends DefaultGraphCell {

    /**
     * orientamento della cella (SwingConstants.HORIZONTAL o SwingConstants.VERTICAL)
     */
    private int orientamento;

    /**
     * numero della riga o della colonna (0 per la prima)
     */
    private int numero;

    /**
     * tipo di linea da disegnare (solo se linea di giorno, Verticale)
     */
    private GrafoPrenotazioni.TipoLinea tipoLinea;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param orientamento SwingConstants.HORIZONTAL o SwingConstants.VERTICAL
     * @param numero di riga o di colonna (0 per la prima)
     * @param tipoLinea tipo di linea da disegnare
     */
    public CellGriglia(int orientamento, int numero, GrafoPrenotazioni.TipoLinea tipoLinea) {
        super();

        this.setOrientamento(orientamento);
        this.setNumero(numero);
        this.setTipoLinea(tipoLinea);

    }// fine del metodo costruttore completo

    
    public int getOrientamento() {
        return orientamento;
    }


    private void setOrientamento(int orientamento) {
        this.orientamento = orientamento;
    }


    public int getNumero() {
        return numero;
    }


    private void setNumero(int numero) {
        this.numero = numero;
    }


    private GrafoPrenotazioni.TipoLinea getTipoLinea() {
        return tipoLinea;
    }

    private void setTipoLinea(GrafoPrenotazioni.TipoLinea tipoLinea) {
        this.tipoLinea = tipoLinea;
    }

    /**
     * Ritorna true se è una cella orizzontale.
     * <p/>
     *
     * @return true se è una cella orizzontale
     */
    public boolean isOrizzontale() {
        return (this.getOrientamento() == SwingConstants.HORIZONTAL);
    }


    /**
     * Ritorna true se è una cella verticale.
     * <p/>
     *
     * @return true se è una cella verticale
     */
    public boolean isVerticale() {
        return (this.getOrientamento() == SwingConstants.VERTICAL);
    }


} // fine della classe