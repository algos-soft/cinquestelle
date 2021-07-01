package it.algos.albergo.risorse;

import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsa;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.tavola.Tavola;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class RisorsaModulo extends ModuloBase implements Risorsa {


    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Risorsa.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Risorsa.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Risorsa.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    public RisorsaModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    public RisorsaModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* selezione del modello (obbligatorio) */
        super.setModello(new RisorsaModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);
        
        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        super.setTabella(true);
    }
    
    
    protected void regolaNavigatori() {
    	// assegna la scheda da usare
        Navigatore nav = this.getNavigatoreDefault();
        nav.addSchedaCorrente(new RisorsaScheda(this));
    }



    @Override
	public boolean inizializza() {
    	boolean cont = super.inizializza();
    	
    	// aggiunge una colonna per il renderer colore
    	if (cont) {
			Navigatore nav = getNavigatoreDefault();
			Tavola tavola = nav.getLista().getTavola();
			
			int numColonne = tavola.getColumnCount();
			TableColumn column = new TableColumn(numColonne+1, 20, new ColorCellRenderer(), null);
			tavola.addColumn(column);

		}
    	return cont;
	}
    
    class ColorCellRenderer implements TableCellRenderer{

    	JPanel pan;
    	
		public ColorCellRenderer() {
			super();
			pan = new JPanel();
			pan.setPreferredSize(new Dimension(10,10));
			pan.setBackground(Color.red);
			pan.setOpaque(true);
			
		}


		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			if (table instanceof Tavola) {
				Tavola tavola = (Tavola)table;
				int chiave = tavola.getLista().getChiave(row);
				int numColore = RisorsaModulo.get().query().valoreInt(Risorsa.Cam.colore.get(), chiave);
				Color color;
				if (numColore!=0) {
					color = new Color(numColore);
				} else {
					color = Risorsa.COLORE_DEFAULT_CELLE;
				}
				pan.setBackground(color);
			}
			
			return pan;
		}
    	
    }


	/**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    @Override
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new TipoRisorsaModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    @Override
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
            super.addModuloVisibile(TipoRisorsa.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static RisorsaModulo get() {
        return (RisorsaModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new RisorsaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

}
