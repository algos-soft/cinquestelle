/**
 * Title:     RiepilogoNew
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-lug-2009
 */
package it.algos.albergo.arrivipartenze.riepilogo;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import javax.swing.JSeparator;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Riepilogo presenze nel dialogo Arrivi e Partenze
 * </p>
 * E' in grado di mostrare il riepilogo per un solo giorno
 * o per un range, adattando automaticamente la modalità
 * di visualizzazione e le etichette visualizzate in funzione
 * del periodo riepilogato (passato, oggi, futuro, a cavallo
 * rispetto alla data corrente del programma).
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-lug-2009 ore 21.54.24
 */
public final class RiepilogoNew extends PannelloFlusso {

    /* data di inizio periodo */
    private Date data1;

    /* data di fine periodo */
    private Date data2;


    /* riporto presenze precedenti alla data di inizio analisi */
    private RiepilogoRiga rigaPrecedenti;

    /* arrivi nel periodo */
    private RiepilogoRiga rigaArrivi;

    /* partenze nel periodo */
    private RiepilogoRiga rigaPartenze;

    /* totale dopo i movimenti */
    private RiepilogoRiga rigaFinali;

    /**
     * Totale presenti al momento.
     * Tiene conto dei movimenti già eseguiti.
     * Significativo solo su visualizzazione della singola
     * giornata del giorno corrente, e visualizzato solo in tale caso.
     */
    private RiepilogoRiga rigaAdesso;


    /* bordo con il titolo: mantengo il riferimento per poter cambiare il titolo */
    private TitledBorder titBordo;

    /* separatore prima dell'ultimo bottone, ne tengo riferimento
     * per renderlo visibile / invisibile */
    private JSeparator separatore;

    /**
     * Ultimo Calcolatore di Movimenti utilizzato per mostrare
     * i dati correntemente visualizzati
     */
    private CalcMovimenti calcMovimenti;


    /**
     * Costruttore completo con parametri. <br>
     *
//     * @param un descrizione del parametro
     */
    public RiepilogoNew() {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

        /* regola le variabili di istanza coi parametri */
//        this.set(un);


        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* non cambia dimensione quando una riga viene resa invisibile */
            this.setConsideraComponentiInvisibili(true);
            this.setGapMinimo(2);
            this.setGapPreferito(5);
            this.setGapMassimo(10);

            /* aggiunge le righe con le azioni per i bottoni */
            this.add(this.setRigaPrecedenti(new RiepilogoRiga(this, new AzPrecedenti())));
            this.add(this.setRigaArrivi(new RiepilogoRiga(this, new AzArrivi())));
            this.add(this.setRigaPartenze(new RiepilogoRiga(this, new AzPartenze())));
            this.add(this.setRigaFinali(new RiepilogoRiga(this, new AzFinali())));

            JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
            this.setSeparatore(sep);
            this.add(sep);

            this.add(this.setRigaAdesso(new RiepilogoRiga(this, new AzAdesso())));

            /* registra il titled border per poter poi cambiare il titolo*/
            Border bordo = this.creaBordo("");
            if (bordo instanceof CompoundBorder) {
                CompoundBorder cbordo = (CompoundBorder)bordo;
                bordo = cbordo.getOutsideBorder();
                if (bordo instanceof TitledBorder) {
                    TitledBorder tbordo = (TitledBorder)bordo;
                    this.setTitBordo(tbordo);
                }// fine del blocco if
            }// fine del blocco if

            /* aggiornamento iniziale */
            Date data = Lib.Data.getVuota();
            this.aggiorna(data, data);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    /**
     * Aggiorna il riepilogo.
     * <p/>
     * @param data1 data di inizio
     * @param data2 data di fine
     */
    public void aggiorna (Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            /* registra le date */
            this.setData1(data1);
            this.setData2(data2);

            /* regola il titolo */
            this.setTitolo(this.getTitolo());

            /* riga presenti adesso (e separatore) - visibili solo se è oggi */
            this.getSeparatore().setVisible(this.isOggi());
            this.getRigaAdesso().setVisible(this.isOggi());

            /* Aggiorna i valori del riepilogo */
            this.aggiornaValori();


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiorna i valori del riepilogo.
     * <p/>
     * Recupera i presenti in albergo il giorno precedente alla dataIni <br>
     * Recupera gli arrivi previsti nel periodo <br>
     * Recupera le partenze previste nel periodo <br>
     * Recupera i presenti in albergo alla dataEnd <br>
     * Aggiorna i numeri nelle varie label <br>
     */
    private void aggiornaValori() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            Date dataIni = this.getData1();
            Date dataEnd = this.getData2();

            if (Lib.Data.isValida(dataIni) && (Lib.Data.isValida(dataEnd) )) {

                /**
                 * Calcola i presenti al momento attuale
                 */
                Filtro filtro;
                filtro = PresenzaModulo.getFiltroPresenze(AlbergoLib.getDataProgramma());
                filtro.add(FiltroFactory.creaFalso(Presenza.Cam.bambino));
                int adessoAdu = PresenzaModulo.get().query().contaRecords(filtro);
                filtro = PresenzaModulo.getFiltroPresenze(AlbergoLib.getDataProgramma());
                filtro.add(FiltroFactory.creaVero(Presenza.Cam.bambino));
                int adessoBam = PresenzaModulo.get().query().contaRecords(filtro);

                /**
                 * Crea e registra un calcolatore di movimenti sull'intervallo
                 * specificato e ne recupera i risultati
                 */
                CalcMovimenti calc = CalcFactory.creaCalcolatore(dataIni, dataEnd);
                this.setCalcMovimenti(calc);

                /* aggiorna le righe */
                this.getRigaPrecedenti().aggiorna(calc.getTestoPrec(), calc.getNumPrecAd(),calc.getNumPrecBa(), calc.isSensatoPresPrec());
                this.getRigaArrivi().aggiorna(calc.getTestoArrivi(), calc.getNumArriviAd(),calc.getNumArriviBa(), calc.isSensatoPresArrivi());
                this.getRigaPartenze().aggiorna(calc.getTestoPartenze(), calc.getNumPartenzeAd(),calc.getNumPartenzeBa(), calc.isSensatoPresPartenze());
                this.getRigaFinali().aggiorna(calc.getTestoFinale(), calc.getNumFinaleAd(),calc.getNumFinaleBa(), calc.isSensatoPresFinali());
                this.getRigaAdesso().aggiorna("Presenti adesso", adessoAdu,adessoBam, true);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }



    /**
     * Ritorna il titolo del riepilogo.
     * <p/>
     * @return il titolo
     */
    private String getTitolo () {
        /* variabili e costanti locali di lavoro */
        String titolo  = "";

        try {    // prova ad eseguire il codice

            titolo="Riepilogo ";
            if (this.isRange()) {
                titolo+=Lib.Data.getDataBrevissima(this.getData1());
                titolo+=" / ";
                titolo+=Lib.Data.getDataBrevissima(this.getData2());
            } else {
                titolo+=Lib.Data.getDataBrevissima(this.getData1());
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return titolo;
    }


    /**
     * Determina se è un range.
     * <p/>
     * @return true se è range
     */
    boolean isRange () {
        /* variabili e costanti locali di lavoro */
        boolean range  = true;

        if (this.getData1().equals(this.getData2())) {
            range=false;
        }// fine del blocco if

        /* valore di ritorno */
        return range;
}


    /**
     * Ritorna true se è la data (unica) di oggi.
     * <p/>
     * @return true se oggi
     */
    private boolean isOggi () {
        /* variabili e costanti locali di lavoro */
        boolean oggi  = false;

        try {    // prova ad eseguire il codice
            if (!this.isRange()) {
                if (this.getData1().equals(AlbergoLib.getDataProgramma())) {
                    oggi=true;
                }// fine del blocco if
            }// fine del blocco if

                    ;
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return oggi;
    }





    /**
     * Assegna un titolo al bordo.
     * <p/>
     * @param titolo il titolo
     */
    private void setTitolo(String titolo) {
        this.getTitBordo().setTitle(titolo);
    }


    /**
     * Mostra presenti al giorno precedente.
     * <p>
     * Arrivati prima del giorno e partiti dopo o non partiti
     */
    private void precedenti () {
        CalcMovimenti calc = this.getCalcMovimenti();
        if (calc!=null) {
            Filtro filtro = calc.getFiltroPrecPres();
            if (filtro!=null) {
                this.apriNavPresenze(filtro);
            }// fine del blocco if
        }// fine del blocco if
    }

    /**
     * Mostra arrivi avvenuti nel periodo.
     */
    private void arrivi () {
        CalcMovimenti calc = this.getCalcMovimenti();
        if (calc!=null) {
            Filtro filtro = calc.getFiltroArriviPres();
            if (filtro!=null) {
                this.apriNavPresenze(filtro);
            }// fine del blocco if
        }// fine del blocco if
    }

    /**
     * Mostra partenze avvenute nel periodo.
     */
    private void partenze () {
        CalcMovimenti calc = this.getCalcMovimenti();
        if (calc!=null) {
            Filtro filtro = calc.getFiltroPartenzePres();
            if (filtro!=null) {
                this.apriNavPresenze(filtro);
            }// fine del blocco if
        }// fine del blocco if
    }

    /**
     * Mostra finali.
     */
    private void finali () {
        CalcMovimenti calc = this.getCalcMovimenti();
        if (calc!=null) {
            Filtro filtro = calc.getFiltroFinalePres();
            if (filtro!=null) {
                this.apriNavPresenze(filtro);
            }// fine del blocco if
        }// fine del blocco if
    }

    /**
     * Mostra adesso.
     */
    private void adesso () {
        Filtro filtro = PresenzaModulo.getFiltroPresenze(this.getData1());
        this.apriNavPresenze(filtro);
    }


    /**
     * Applica un filtro al navigatore Presenze e lo rende visibile.
     * <p/>
     * @param filtro da applicare
     */
    private void apriNavPresenze(Filtro filtro) {
        Modulo modPres = PresenzaModulo.get();
        Navigatore nav = modPres.getNavigatoreCorrente();
        nav.avvia();
        nav.setFiltroCorrente(filtro);
        nav.apriNavigatore();
    }





    Date getData1() {
        return data1;
    }


    private void setData1(Date data1) {
        this.data1 = data1;
    }


    Date getData2() {
        return data2;
    }


    private void setData2(Date data2) {
        this.data2 = data2;
    }


    private RiepilogoRiga getRigaPrecedenti() {
        return rigaPrecedenti;
    }


    private RiepilogoRiga setRigaPrecedenti(RiepilogoRiga riga) {
        this.rigaPrecedenti = riga;
        return riga;
    }


    private RiepilogoRiga getRigaArrivi() {
        return rigaArrivi;
    }


    private RiepilogoRiga setRigaArrivi(RiepilogoRiga riga) {
        this.rigaArrivi = riga;
        return riga;
    }


    private RiepilogoRiga getRigaPartenze() {
        return rigaPartenze;
    }


    private RiepilogoRiga setRigaPartenze(RiepilogoRiga riga) {
        this.rigaPartenze = riga;
        return riga;
    }


    private RiepilogoRiga getRigaFinali() {
        return rigaFinali;
    }


    private RiepilogoRiga setRigaFinali(RiepilogoRiga riga) {
        this.rigaFinali = riga;
        return riga;
    }


    private RiepilogoRiga getRigaAdesso() {
        return rigaAdesso;
    }


    private RiepilogoRiga setRigaAdesso(RiepilogoRiga riga) {
        this.rigaAdesso = riga;
        return riga;
    }


    private TitledBorder getTitBordo() {
        return titBordo;
    }


    private void setTitBordo(TitledBorder titBordo) {
        this.titBordo = titBordo;
    }


    private JSeparator getSeparatore() {
        return separatore;
    }


    private void setSeparatore(JSeparator separatore) {
        this.separatore = separatore;
    }


    private CalcMovimenti getCalcMovimenti() {
        return calcMovimenti;
    }


    private void setCalcMovimenti(CalcMovimenti calcMovimenti) {
        this.calcMovimenti = calcMovimenti;
    }


    /**
     * Azione del bottone Precedenti
     */
    class AzPrecedenti implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            precedenti();
        }
    } // fine della classe 'interna'

    /**
     * Azione del bottone Arrivi
     */
    class AzArrivi implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            arrivi();
        }
    } // fine della classe 'interna'

    /**
     * Azione del bottone Partenze
     */
    class AzPartenze implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            partenze();
        }
    } // fine della classe 'interna'

    /**
     * Azione del bottone Finali
     */
    class AzFinali implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            finali();
        }
    } // fine della classe 'interna'

    /**
     * Azione del bottone Adesso
     */
    class AzAdesso implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            adesso();
        }
    } // fine della classe 'interna'


}// fine della classe
