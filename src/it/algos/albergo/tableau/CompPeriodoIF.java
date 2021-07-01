package it.algos.albergo.tableau;

import java.awt.Component;

/**
 * Componente grafico per un periodo nel grafo dei periodi
 * Usato dal renderer delle celle
 *
 * @author Alessandro Valbonesi
 * @date 15/05/2014
 */
public interface CompPeriodoIF {
	
	/**
	 * @return the graphic component
	 */
	public Component getComponent();
	
	/**
	 * Puts a value in the component
	 * @param key the key
	 * @param value the value
	 */
	public void put(String key, Object value);
	
	/**
	 * Returns a value from the value map
	 * @param key the key
	 * @return the value
	 */
	public Object get(String key);


	/**
	 * Finalizza l'aspetto del componente UI dopo che ha tutti i valori
	 * @param graph GrafoPrenotazioni di riferimento
	 * @param uo UserObjectPeriodo di riferimento
	 */
	public void pack(GrafoPrenotazioni graph, UserObjectPeriodo uo);


}
