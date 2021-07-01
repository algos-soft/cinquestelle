package it.algos.base.pref;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.ArrayList;
import java.util.Date;

/**
 * Wrapper delle preferenze.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 * <p/>
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-feb-2007 ore 22.24.49
 */
public final class PrefWrap {


    /**
     * Alias per precedenti nomi della preferenza eventualmente registrati.
     */
    private String alias;

    /**
     * Sigla della preferenza.
     */
    private String sigla;

    /**
     * Descrizione completa della preferenza.
     */
    private String descrizione;

    /**
     * Tipo della preferenza.
     */
    private Pref.TipoDati tipoDati;

    /**
     * Livello di accessibilità della preferenza.
     */
    private Pref.Utente livello;

    /**
     * Valori possibili per la preferenza, espressi in un combo box.
     */
    private ArrayList<PrefTipo> lista;

    /**
     * Valore standard della preferenza.
     */
    private Object standard;

    /**
     * Valore corrente della preferenza.
     */
    private Object valore;

    /**
     * Nota visibile all'utente.
     */
    private String nota;

    /**
     * Nota visibile solo al programmatore.
     */
    private String notaProg;

    /**
     * Preferenza visibile nel dialogo di modifica.
     */
    private boolean visibile;

    /**
     * Mostra il valore di default nel dialogo di modifica.
     */
    private boolean mostraDefault;

    /**
     * Mostra la preferenza nel setup iniziale del programma.
     */
    private boolean setup;

    /**
     * Preferenza comune a più computer.
     */
    private boolean comune = false;

    /**
     * Chiave parametro (argomento).
     * <p/>
     * Utilizzato per la Enum Cost <br>
     */
    private String chiave;

    public final static String SEP_AREA = "@ret@";

    /**
     * Flag per memorizzare tutti i valori precedentemente utilizzati.
     */
    private boolean usaValoriMultipli = false;

    /**
     * Valori precedentemente utilizzati.
     * <p/>
     * Ha senso solo se il precedente flag è uguale a vero <br>
     */
    private ArrayList<String> valoriUsati;


    public String getAlias() {
        return alias;
    }


    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String getSigla() {
        return sigla;
    }


    public void setSigla(String sigla) {
        this.sigla = sigla;
    }


    public String getDescrizione() {
        return descrizione;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public Pref.TipoDati getTipoDati() {
        return tipoDati;
    }


    public void setTipoDati(Pref.TipoDati tipoDati) {
        this.tipoDati = tipoDati;
    }


    public Pref.Utente getLivello() {
        return livello;
    }


    public void setLivello(Pref.Utente livello) {
        this.livello = livello;
    }


    public ArrayList<PrefTipo> getLista() {
        return lista;
    }


    public void setLista(ArrayList<PrefTipo> lista) {
        this.lista = lista;
    }


    public Object getStandard() {
        return standard;
    }


    public String getChiave() {
        return chiave;
    }


    public void setChiave(String chiave) {
        this.chiave = chiave;
    }


    /**
     * Inserisce il valore standard per questa preferenza.
     * <p/>
     * Controlla se il tipo dati esistente è un Pref.TipoDati.combo <br>
     * Nel caso aggiunge 1 al valore ordinale selezionato <br>
     *
     * @param standard per la prefernza
     */
    public void setStandard(Object standard) {
        /* variabili e costanti locali di lavoro */
        Pref.TipoDati tipo;
        Integer pos;

        try { // prova ad eseguire il codice

            tipo = this.getTipoDati();

            if (tipo != null) {
                if (tipo == Pref.TipoDati.combo) {
                    if (standard instanceof Integer) {
                        pos = (Integer)standard;
                        pos++;
                        standard = pos;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* regola il valore delle variabile */
            this.standard = standard;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Se il valore corrente coincide con quello di default
     */
    public boolean isStandard() {
        /* variabili e costanti locali di lavoro */
        boolean coincide = false;
        Pref.TipoDati tipo;

        try { // prova ad eseguire il codice
            tipo = this.getTipoDati();

            switch (tipo) {
                case stringa:
                    coincide = (this.getValore().equals(this.getStandard()));
                    break;
                case intero:
                    coincide = (this.getValore() == this.getStandard());
                    break;
                case booleano:
                    coincide = (this.getValore() == this.getStandard());
                    break;
                case data:
                    coincide = (this.getValore() == this.getStandard());
                    break;
                case combo:
                    int val1;
                    int val2;
                    val1 = (Integer)this.getValore();
                    val2 = (Integer)this.getStandard();
                    coincide = (val1 == val2);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return coincide;
    }


    /**
     * Se il valore corrente non coincide con quello di default
     */
    public boolean isNotStandard() {
        return !isStandard();
    }


    public Object getValore() {
        return valore;
    }


    public void setValore(Object valore) {
        this.valore = valore;
    }


    public String getNota() {
        return nota;
    }


    public void setNota(String nota) {
        this.nota = nota;
    }


    public String getNotaProg() {
        return notaProg;
    }


    public void setNotaProg(String notaProg) {
        this.notaProg = notaProg;
    }


    public boolean isVisibile() {
        return visibile;
    }


    public void setVisibile(boolean visibile) {
        this.visibile = visibile;
    }


    public boolean isMostraDefault() {
        return mostraDefault;
    }


    public void setMostraDefault(boolean mostraDefault) {
        this.mostraDefault = mostraDefault;
    }


    public boolean isSetup() {
        return setup;
    }


    public void setSetup(boolean setup) {
        this.setup = setup;
    }


    public boolean isComune() {
        return comune;
    }


    public void setComune(boolean comune) {
        this.comune = comune;
    }


    public ArrayList<String> getValoriUsati() {
        return valoriUsati;
    }


    public void setValoriUsati(ArrayList<String> valoriUsati) {
        this.valoriUsati = valoriUsati;
    }


    public boolean isUsaValoriMultipli() {
        return usaValoriMultipli;
    }


    public void setUsaValoriMultipli(boolean usaValoriMultipli) {
        this.usaValoriMultipli = usaValoriMultipli;
    }


    /**
     * Ritorna una rappresentazione in formato stringa del valore
     * <p/>
     *
     * @return la stringa
     */
    @Override public String toString() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Object valore;


        try { // prova ad eseguire il codice

            valore = this.getValore();

            switch (this.getTipoDati()) {
                case stringa:
                case intero:
                case doppio:
                case booleano:
                    stringa = valore.toString();
                    break;
                case data:
                    stringa = Lib.Data.getStringa((Date)valore);
                    break;
                case ora:
                    stringa = valore.toString();
                    break;
                case time:
                    stringa = valore.toString();
                    break;
                case combo:
                case radio:
                    stringa = valore.toString();
                    break;
                case area:
                    stringa = valore.toString();
                    stringa = stringa.replaceAll("\\n", SEP_AREA);
                    break;
                default: // caso non definito
                    stringa = "";
                    break;
            } // fine del blocco switch
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }

}// fine della classe
