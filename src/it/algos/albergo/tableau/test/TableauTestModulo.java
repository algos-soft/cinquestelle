/**
 * Title:     TableauTest
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-feb-2009
 */
package it.algos.albergo.tableau.test;

import it.algos.albergo.risorse.RisorsaModulo;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsaModulo;
import it.algos.albergo.tableau.CellCamera;
import it.algos.albergo.tableau.CellPeriodoCamera;
import it.algos.albergo.tableau.CellPeriodoIF;
import it.algos.albergo.tableau.CellPeriodoRisorsa;
import it.algos.albergo.tableau.CellRisorsa;
import it.algos.albergo.tableau.CellRisorsaIF;
import it.algos.albergo.tableau.Tableau;
import it.algos.albergo.tableau.TableauDatamodel;
import it.algos.albergo.tableau.TipoRisorsaTableau;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.modulo.OnEditingFinished;
import it.algos.base.progetto.Progetto;

import java.util.ArrayList;
import java.util.Date;

/**
 * Test del Tableau.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-feb-2009 ore 9.11.17
 */
public class TableauTestModulo extends ModuloBase implements TableauDatamodel {

	private static String NOME_CHIAVE = "TableauTest";
	
    /**
     * Costruttore completo senza parametri.<br>
     */
    public TableauTestModulo() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo

    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public TableauTestModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore

    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_CHIAVE);
        
        /* selezione del modello (obbligatorio) */
        super.setModello(new TableauTestModello());

    }// fine del metodo inizia
    
    


    @Override
	public void avvia() {
		super.avvia();
	}

	/**
     * Crea la lista delle celle di camera.
     * <p/>
     */
    private ArrayList<CellRisorsaIF> creaListaCelleCamere() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CellRisorsaIF> lista = new ArrayList<CellRisorsaIF>();
        CellCamera cell;
        String nome;
        String composizione;
        int codice;

        try { // prova ad eseguire il codice

            for (int k = 0; k < 70; k++) {

                nome = "" + (k * 2 + 100);
                composizione = "M+" + k + "L";
                codice = k;
                cell = new CellCamera(nome, composizione, codice);
                lista.add(cell);

            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* variabili e costanti locali di lavoro */
        return lista;
    }


    public ArrayList<CellPeriodoIF> getCellePeriodo(int tipoRisorsa, Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CellPeriodoIF> lista=null;


        switch (tipoRisorsa) {
		case 0:
			lista = creaPeriodiCamera(dataInizio, dataFine);
			break;
		case 1:
			lista = creaPeriodiTavoli(dataInizio, dataFine);
			break;
		case 2:
			lista = new ArrayList<CellPeriodoIF>();
			break;
		default:
			break;
		}

        return lista;
    }
    
    
    private ArrayList<CellPeriodoIF> creaPeriodiCamera(Date dataInizio, Date dataFine){
        Date d1, d2;
        CellPeriodoCamera cell;

        ArrayList<CellPeriodoIF> lista = new ArrayList<CellPeriodoIF>();

        d1 = Lib.Data.creaData(10, 6, 2008);
        d2 = Lib.Data.creaData(18, 6, 2008);
        cell = new CellPeriodoCamera(8,
                "Rossi Mario",
                "Grandi Viaggi",
                
                0,
                0,
                d1,
                d2,
                false,
                false,
                Lib.Data.creaData(5,3,2009),
                true,
                false,
                2,
                1,
                "FB",
                "M+1L",
                1);
        lista.add(cell);

        d1 = Lib.Data.creaData(18, 6, 2008);
        d2 = Lib.Data.creaData(22, 6, 2008);
        cell = new CellPeriodoCamera(11, "Bianchi Edoardo", "", 0, 62, d1, d2,false, true, Lib.Data.creaData(2,5,2009), false, false, 2, 0, "HB", "2L", 2);
        lista.add(cell);

        d1 = Lib.Data.creaData(25, 6, 2008);
        d2 = Lib.Data.creaData(15, 7, 2008);
        cell = new CellPeriodoCamera(13, "Valbonesi Alessandro", "", 0, 19, d1, d2, true,false, Lib.Data.creaData(2,5,2009), true, false, 2, 2, "BB", "3L", 3);
        lista.add(cell);

        d1 = Lib.Data.creaData(15, 7, 2008);
        d2 = Lib.Data.creaData(23, 7, 2008);
        cell = new CellPeriodoCamera(19,"Valbonesi Alessandro", "", 13, 20, d1, d2, false,true, Lib.Data.creaData(2,5,2009), true, false, 2, 2, "BB", "3L", 3);
        lista.add(cell);

        d1 = Lib.Data.creaData(23, 7, 2008);
        d2 = Lib.Data.creaData(28, 7, 2008);
        cell = new CellPeriodoCamera(20, "Valbonesi Alessandro", "", 19, 0, d1, d2,false, true, Lib.Data.creaData(2,5,2009), true, false, 2, 2, "BB", "3L", 3);
        lista.add(cell);



        d1 = Lib.Data.creaData(27, 7, 2008);
        d2 = Lib.Data.creaData(3, 8, 2008);
        cell = new CellPeriodoCamera(16,
                "Brunelli Elio",
                "Ferrytours",
                8,
                93,
                d1,
                d2,
                false,
                true,
                Lib.Data.creaData(2,5,2009),
                false,
                false,
                1,
                1,
                "FB",
                "M",
                4);
        lista.add(cell);

        d1 = Lib.Data.creaData(8, 8, 2008);
        d2 = Lib.Data.creaData(11, 8, 2008);
        cell = new CellPeriodoCamera(17,"Caetani Mario", "",55, 0, d1, d2, false,true, Lib.Data.creaData(2,5,2009), true, true, 2, 2, "BB", "3L", 5);
        lista.add(cell);

        d1 = Lib.Data.creaData(10, 6, 2008);
        d2 = Lib.Data.creaData(3, 8, 2008);
        cell = new CellPeriodoCamera(21,"Serafini Carlo", "", 77, 0, d1, d2, false,true, Lib.Data.creaData(2,5,2009), true, true, 2, 2, "BB", "3L", 6);
        lista.add(cell);
        
        return lista;
    }
    
    
    private ArrayList<CellPeriodoIF> creaPeriodiTavoli(Date dataInizio, Date dataFine){
        Date d1, d2;
        CellPeriodoRisorsa cell;

        ArrayList<CellPeriodoIF> lista = new ArrayList<CellPeriodoIF>();


        d1 = Lib.Data.creaData(18, 6, 2008);
        d2 = Lib.Data.creaData(22, 6, 2008);
        cell = new CellPeriodoRisorsa(2, 0, 0, "Bianchi Edoardo", "106", d1, d2,false, true, Lib.Data.creaData(2,5,2009), false, false, 2, 0, "HB","nota1");
        lista.add(cell);

        d1 = Lib.Data.creaData(25, 6, 2008);
        d2 = Lib.Data.creaData(15, 7, 2008);
        cell = new CellPeriodoRisorsa(1, 0, 0, "Marini Giuseppe", "148", d1, d2, true,false, Lib.Data.creaData(2,5,2009), true, false, 2, 2, "BB","");
        lista.add(cell);

        d1 = Lib.Data.creaData(15, 7, 2008);
        d2 = Lib.Data.creaData(23, 7, 2008);
        cell = new CellPeriodoRisorsa(3, 0, 0, "Del Monaco Edoardo", "212", d1, d2, false,true, Lib.Data.creaData(2,5,2009), true, false, 2, 2, "BB","");
        lista.add(cell);

        d1 = Lib.Data.creaData(8, 8, 2008);
        d2 = Lib.Data.creaData(11, 8, 2008);
        cell = new CellPeriodoRisorsa(3, 0, 0, "Caetani Mario", "412", d1, d2, false,true, Lib.Data.creaData(2,5,2009), true, true, 2, 2, "BB","");
        lista.add(cell);

        d1 = Lib.Data.creaData(22, 7, 2008);
        d2 = Lib.Data.creaData(3, 8, 2008);
        cell = new CellPeriodoRisorsa(2, 0, 0, "Serafini Carlo", "308", d1, d2, false,true, Lib.Data.creaData(2,5,2009), true, true, 2, 2, "BB","");
        lista.add(cell);
        
        return lista;
    }



    public CellPeriodoIF getCellaPeriodo(int codTipoRisorsa, int codPeriodo) {
        return null;
    }

    
    @Override
	public ArrayList<CellRisorsaIF> getCelleRisorsa(int codTipo) {
        ArrayList<CellRisorsaIF> lista = new ArrayList<CellRisorsaIF>();
        
        if (codTipo==Tableau.ID_TIPO_RISORSE_CAMERA) {
            lista = creaListaCelleCamere();
		} else {
			switch (codTipo) {
			case 1:
		        lista.add(new CellRisorsa(1, "001","A"));
		        lista.add(new CellRisorsa(2, "002","A"));
		        lista.add(new CellRisorsa(3, "003","A"));
				break;
			case 2:
		        lista.add(new CellRisorsa(1, "001","B"));
		        lista.add(new CellRisorsa(2, "002","C"));
		        lista.add(new CellRisorsa(3, "003","C"));
		        lista.add(new CellRisorsa(4, "004","D"));
		        lista.add(new CellRisorsa(5, "005","D"));
		        lista.add(new CellRisorsa(6, "006","D"));
				break;

			default:
				break;
			}
			if (codTipo==1) {
				
			}
		}
        
		return lista;
	}

	@Override
	public TipoRisorsaTableau[] getTipiRisorsa() {
		
        ArrayList<TipoRisorsaTableau> tipi = new  ArrayList<TipoRisorsaTableau>();
        TipoRisorsaTableau tipoCamere = new TipoRisorsaTableau(Tableau.ID_TIPO_RISORSE_CAMERA,"Camere");
        tipi.add(tipoCamere);
        TipoRisorsaTableau[] aTipi = TipoRisorsaTableau.getAll();
        for(TipoRisorsaTableau tipo : aTipi){
            tipi.add(tipo);
        }

        return tipi.toArray(new TipoRisorsaTableau[0]);
	}


    /**
     * Ritorna l'elenco di tutti gli id di record sorgenti di cella contenuti
     * in una data prenotazione, relativi a un dato tipo di risorse.
     * <p/>
     * Nel caso di prenotazione di camere, ritorna gli id di periodo
     * Nel caso di prenotazione di risorse, ritorna gli id di RisorsaPeriodo di tutti i periodi
     * ecc...
     *
     * @param idPrenotazione l'id della prenotazione
     * @param tipoRisorse l'id del tipo di risorse correntemente selezionato
     * @return l'array con tutti gli id di record sorgenti contenuti nella prenotazione
     */
    public int[] getIdRecordSorgenti(int idPrenotazione, int tipoRisorse){
    	return new int[0];
    }

    public int getCodCliente(int codPeriodo) {
        return 0;
    }

	/**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static TableauTestModulo get() {
        return (TableauTestModulo)ModuloBase.get(NOME_CHIAVE);
    }

    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     */
    protected void creaModuli() {
        super.creaModulo(new TipoRisorsaModulo());
    }

    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     */
    protected void addModuliVisibili() {
        super.addModuloVisibile(TipoRisorsaModulo.NOME_MODULO);
    }

    
    public void creaTableau(){
        Date d1 = Lib.Data.creaData(20, 6, 2008);
        Tableau tb = new Tableau(this, d1, 9);
        //Tableau tb = new Tableau(this, null, 0);

        tb.avvia();
    }

    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new TableauTestModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

	@Override
	public void apriPrenotazione(int codPrenotazione, OnEditingFinished listener) {
	}


    
}// fine della classe