package it.algos.albergo.tableau;

import it.algos.albergo.risorse.Risorsa;

import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;

/**
 * Classe astratta rappresentante un componente grafico 
 * visualizzato in una cella della barra delle risorse
 */
public abstract class CompRisorsaAbs extends PanGradientRounded implements CompRisorsaIF {

    /**
     * Costruttore completo senza parametri.<br>
     */
    public CompRisorsaAbs() {
        this(Risorsa.COLORE_DEFAULT_CELLE);
    }
    
    /**
     * Costruttore con colore.<br>
     */
    public CompRisorsaAbs(Color color) {
        super(color, SwingConstants.VERTICAL);
    }


	/**
	 * @return the graphic component
	 */
	public Component getComponent(){
		return this;
	}


}
