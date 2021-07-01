package it.algos.albergo.tableau;

import java.awt.Color;
import java.awt.Font;

import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Componente grafico per visualizzare una cella nella 
 * barra delle risorse.
 * Visualizza il nome della risorsa e un dettaglio.
 */
public class CompRisorsaDefault extends CompRisorsaAbs {

    /* Font per il nome della risorsa */
    static final Font FONT_RISORSA = FontFactory.creaScreenFont(Font.BOLD, 14f);

    /* Font per il dettaglio */
    static final Font FONT_DETTAGLIO = FontFactory.creaScreenFont(10f);

    /* label per il nome */
    private JLabel labelNome;
    
    /* label per il dettaglio */
    private JLabel labelDettaglio;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public CompRisorsaDefault(Color color) {
        super(color);
        this.inizia();
    }// fine del metodo costruttore completo

    
    private void inizia() {
    	
        /* pannello completo */
    	PannelloFlusso pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
        pan.setUsaGapFisso(true);
        pan.setGapPreferito(4);
        pan.setAllineamento(Layout.ALLINEA_CENTRO);
        this.add(pan);

        /* distanziatore di sinistra */
        pan.add(Box.createHorizontalStrut(4));

        /* label risorsa */
        labelNome = new JLabel();
        labelNome.setFont(FONT_RISORSA);
        labelNome.setOpaque(false);
        labelNome.setHorizontalAlignment(SwingConstants.LEFT);
        pan.add(labelNome);

        /* label dettaglio */
        labelDettaglio = new JLabel();
        labelDettaglio.setFont(FONT_DETTAGLIO);
        labelDettaglio.setOpaque(false);
        labelDettaglio.setHorizontalAlignment(SwingConstants.LEFT);
        pan.add(labelDettaglio);
        
    }

	/**
	 * Adds a value to the map maintained by the component
	 * @param values the map of values
	 */
	public void set(String key, Object value){
		
		if (key.equals("name")) {
			labelNome.setText((String)value);
		}
		
		if (key.equals("dett")) {
			labelDettaglio.setText((String)value);
		}

	}

}
