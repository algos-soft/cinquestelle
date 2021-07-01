package it.algos.albergo.tableau;

import java.awt.Component;

/**
 * Componente grafico per una camera nella barra delle camere
 * Usato dal renderer delle celle
 *
 * @author Alessandro Valbonesi
 * @date 15/05/2014
 */
public interface CompRisorsaIF {
	
	/**
	 * Sets a value in the component
	 * @param key the key
	 * @param value the value
	 */
	public void set(String key, Object value);
	
	/**
	 * @return the graphic component
	 */
	public Component getComponent();
	
}
