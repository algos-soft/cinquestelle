package it.algos.gestione.tabelle.valuta;
/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-apr-2007
 */

import it.algos.base.dialogo.DialogoImport;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;
import java.util.Date;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-apr-2007 ore 10.10.46
 */
public final class ValutaCambio extends DialogoImport {

    private boolean singolo = false;

    private int codice = 0;


    /**
     * Costruttore .
     *
     * @param modulo di riferimento
     */
    public ValutaCambio(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        this(modulo, 0);
    }// fine del metodo costruttore


    /**
     * Costruttore completo.
     *
     * @param modulo di riferimento
     * @param codice del record
     */
    public ValutaCambio(Modulo modulo, int codice) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */
        this.setSingolo(codice != 0);
        this.setCodice(codice);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.creaDialogo();
    }// fine del metodo inizia


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    @Override
    public void avvia() {
    }// fine del metodo avvia


    /**
     * Creazione dialogo.
     * <p/>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void creaDialogo() {
        /* variabili e costanti locali di lavoro */
        String titolo;
        String azione;
        String messaggio;

        try { // prova ad eseguire il codice
            /* testi */
            if (this.isSingolo()) {
                messaggio = "Controllo del cambio di questa valuta";
            } else {
                messaggio = "Controllo dei principali cambi correnti";
            }// fine del blocco if-else

            titolo = "Cambio valute";
            azione = "Cambio";

            this.setTitolo(titolo);
            this.getBottoneConferma().setText(azione);
            this.setMessaggio(messaggio);

            super.avvia();

            /* esegue l'operazione */
            if (this.isConfermato()) {

                if (this.isSingolo()) {
                    this.cambiaSingolaValuta();
                } else {
//                    this.cambiaValute();
                    this.cambioForbes();
                }// fine del blocco if-else

                /* mostra le modifiche in lista*/
                super.caricaRecords();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera dal sito di Forbes i valori di cambio correnti per alcune valute.
     * <p/>
     * Metodo invocato dal bottone <br>
     */
    private void cambioForbes() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<String> lista;
        String[] parte;
        double val;

        try { // prova ad eseguire il codice
            /* chiude la scheda (se aperta) */
            this.getModulo().getNavigatoreCorrente().chiudeScheda();

            /* recupera la lista delle valute in ordine alfabetico di codice */
            lista = this.getCambiForbes();

            continua = (Lib.Array.isValido(lista));

            if (continua) {
                /* traverso tutta la collezione */
                for (String stringa : lista) {
                    parte = stringa.split(SEP);
                    val = new Float(parte[1]);
                    this.regolaCambio(parte[0], val);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera la lista dei cambi.
     * <p/>
     * Recupera i dati dalla pagina http://www.forbes.com/currencies/ <br>
     * Divisi in sottopagine:
     * http://www.forbes.com/feeds/currencies/currency_ah_eur.html <br>
     * http://www.forbes.com/feeds/currencies/currency_ir_eur.html <br>
     * http://www.forbes.com/feeds/currencies/currency_sz_eur.html <br>
     * Per ogni valuta, crea un elemento della lista con codice e valore (separati da SEP) <br>
     *
     * @return lista dei cambi disponibili sul sito Cnn
     */
    private ArrayList<String> getCambiForbes() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaOut = null;
        ArrayList<String> listaA;
        ArrayList<String> listaB;
        ArrayList<String> listaC;
        String sitoA = "http://www.forbes.com/feeds/currencies/currency_ah_eur.html";
        String sitoB = "http://www.forbes.com/feeds/currencies/currency_ir_eur.html";
        String sitoC = "http://www.forbes.com/feeds/currencies/currency_sz_eur.html";

        try { // prova ad eseguire il codice
            /* istanza di ritorno */
            listaOut = new ArrayList<String>();

            /* primo gruppo */
            listaA = this.getListaForbes(sitoA);

            /* secondo gruppo */
            listaB = this.getListaForbes(sitoB);

            /* secondo gruppo */
            listaC = this.getListaForbes(sitoC);

            /* traverso tutta la collezione */
            for (String riga : listaA) {
                listaOut.add(riga);
            } // fine del ciclo for-each

            /* traverso tutta la collezione */
            for (String riga : listaB) {
                listaOut.add(riga);
            } // fine del ciclo for-each

            /* traverso tutta la collezione */
            for (String riga : listaC) {
                listaOut.add(riga);
            } // fine del ciclo for-each
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Recupera la lista dei cambi.
     * <p/>
     * Recupera i dati dalle sottopagine di http://www.forbes.com/currencies/ <br>
     * Per ogni valuta, crea un elemento della lista con codice e valore (separati da SEP) <br>
     *
     * @return lista dei cambi disponibili sul sito
     */
    private ArrayList<String> getListaForbes(String sito) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaOut = null;
        boolean continua;
        String testo;
        String cambio;
        String tag = "   </tr>\n   <tr>\n\n   <tr>";
        String[] righe = null;

        try { // prova ad eseguire il codice
            /* istanza di ritorno */
            listaOut = new ArrayList<String>();

            /* recupera il contenuto significativo della pagina */
            testo = this.getTestoForbes(sito);
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                righe = testo.split(tag);
                continua = (righe != null) && (righe.length > 0);
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (String riga : righe) {
                    cambio = this.gerCambio(riga);
                    listaOut.add(cambio);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Recupera il valore di un cambio.
     * <p/>
     *
     * @return costruisce una stringa con codice e valore
     */
    private String gerCambio(String riga) {
        /* variabili e costanti locali di lavoro */
        String cambio = "";
        String tagCodIni = "<b>";
        String tagCodEnd = "</b>";
        int posCodIni;
        int posCodEnd;
        String codice;
        String tagValIni = "RIGHT\">&nbsp;";
        String tagValEnd = "</td>";
        int posValIni;
        int posValEnd;
        String valore;

        try { // prova ad eseguire il codice
            posCodIni = riga.indexOf(tagCodIni);
            posCodIni += tagCodIni.length();
            posCodEnd = riga.indexOf(tagCodEnd, posCodIni);
            codice = riga.substring(posCodIni, posCodEnd);

            posValIni = riga.indexOf(tagValIni);
            posValIni += tagValIni.length();
            posValEnd = riga.indexOf(tagValEnd, posValIni);
            valore = riga.substring(posValIni, posValEnd);

            cambio = codice + SEP + valore;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cambio;
    }


    /**
     * Recupera il contenuto dal sito.
     * <p/>
     *
     * @return contenuto significativo del sito
     */
    private String getTestoForbes(String sito) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        String testoGrezzo;
        String tagContenuto = "<td style=\"vertical-align:top;\" class=\"curname\">";
        String tag = "<tr>";
        int posIni = 0;
        int posEnd = 0;

        try { // prova ad eseguire il codice

            /* recupera il testo grezzo della pagina */
            testoGrezzo = Lib.Web.getPagina(sito);
            continua = (Lib.Testo.isValida(testoGrezzo));

            if (continua) {
                posIni = testoGrezzo.indexOf(tagContenuto);
                continua = (posIni != -1);
            }// fine del blocco if

            if (continua) {
                posEnd = testoGrezzo.lastIndexOf(tagContenuto);
                continua = (posEnd != -1);
            }// fine del blocco if

            if (continua) {
                posIni = testoGrezzo.lastIndexOf(tag, posIni);
                posEnd = testoGrezzo.indexOf(tag, posEnd);
                posEnd = testoGrezzo.indexOf(tag, posEnd);
                continua = (posIni > 0) && (posEnd > 0) && (posEnd > posIni);
            }// fine del blocco if

            if (continua) {
                testo = testoGrezzo.substring(posIni, posEnd);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Recupera dal sito della cnn i valori di cambio correnti per alcune valute.
     * <p/>
     * Metodo invocato dal bottone <br>
     * <p/>
     * Recupera i dati da una pagina di cnn <br>
     */
    private void cambiaValute() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<String> lista;
        String[] parte;
        double val;

        try { // prova ad eseguire il codice
            /* chiude la scheda (se aperta) */
            this.getModulo().getNavigatoreCorrente().chiudeScheda();

            /* recupera la lista delle valute in ordine alfabetico di codice */
            lista = this.getCambiCnn();
//            lista = this.getCambiXeTabella();
            lista = this.getCambiXe(lista);

            continua = (Lib.Array.isValido(lista));

            if (continua) {
                /* traverso tutta la collezione */
                for (String stringa : lista) {
                    parte = stringa.split(SEP);
                    val = new Float(parte[1]);
                    this.regolaCambio(parte[0], val);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera dal sito il valore di cambio corrente per la valuta selezionata.
     * <p/>
     * Metodo invocato dal bottone <br>
     * <p/>
     * Recupera i dati da una pagina <br>
     *
     * @deprecated
     */
    private void cambiaSingolaValuta() {
        /* variabili e costanti locali di lavoro */
        int cod;
        String codIso;
        double valore;

        try { // prova ad eseguire il codice
            cod = this.getCodice();

            codIso = this.getModulo().query().valoreStringa(Valuta.Cam.codiceIso.get(), cod);

            valore = this.getCambioDinamico(codIso);

            /* registra le modifiche al record */
            this.regolaCambio(codIso, valore);

            /* aggiorna la scheda */
            this.getModulo().getNavigatoreCorrente().annullaModifiche();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera la lista dei cambi.
     * <p/>
     * Recupera i dati dalla pagina http://money.cnn.com/data/currencies/ <br>
     * Per ogni valuta, crea un elemento della lista con codice e valore (separtati da virgola) <br>
     *
     * @return lista dei cambi disponibili sul sito Cnn
     */
    private ArrayList<String> getCambiCnn() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaOut = null;
        ArrayList<String> lista;
        String europa = "http://money.cnn.com/data/currencies/europe/";
        double euro;
        double val;
        String[] parte;

        try { // prova ad eseguire il codice
            /* ciclo per valute europee */
            lista = this.getCambi(europa);

            /* aggiunge il dollaro */
            lista = this.dollaro(lista);

            /* concambio dell'euro */
            euro = this.getEuro(lista);

            /* traverso tutta la collezione */
            listaOut = new ArrayList<String>();
            for (String stringa : lista) {
                parte = stringa.split(SEP);
                val = new Float(parte[1]);
                val = val / euro;
                listaOut.add(parte[0] + SEP + val);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Recupera la lista dei cambi.
     * <p/>
     * Recupera i dati dalla pagina http://www.xe.com <br>
     * Per ogni valuta, crea un elemento della lista con codice e valore (separati da virgola) <br>
     *
     * @return lista dei cambi disponibili sul sito Cnn
     */
    private ArrayList<String> getCambiXe(ArrayList<String> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaOut = null;
        String pagina = "http://www.xe.com";
        boolean continua;
        String contenuto;
        String testo;
        String stringa;
        String tagIni = "<td align=\"left\" class=\"currencyA\"> 1&nbsp;EUR&nbsp;= </td>";
        String tagEnd = "</tr>";
        String tag = "</td>";
        String fine = ">";
        String[] valute =
                {"USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "RUB", "CNY", "ZAR", "MXN"};
        String[] valori;
        int pos;
        String codice;

        try { // prova ad eseguire il codice
            listaOut = new ArrayList<String>();
            valori = new String[valute.length];

            contenuto = Lib.Web.getPagina(pagina);
            continua = (Lib.Testo.isValida(contenuto));

            /* cerca le parole chiave */
            if (continua) {
                testo = Lib.Testo.estrae(contenuto, tagIni, tagEnd);
                valori = testo.split(tag);
                continua = (valori.length > 0);
            }// fine del blocco if

            if (continua) {
                for (int k = 0; k < valute.length; k++) {
                    stringa = valori[k];
                    pos = stringa.indexOf(fine);
                    if (pos != -1) {
                        pos += fine.length();
                        stringa = stringa.substring(pos).trim();
                        listaOut.add(valute[k] + SEP + stringa);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            /* utilizza anche i valori della cnn */
            for (String cnn : listaIn) {
                valori = cnn.split(tag);
                codice = valori[0];

                /* traverso tutta la collezione */
                for (String cod : valute) {
                    if (!cod.equals(codice)) {
                        listaOut.add(cnn);
                    }// fine del blocco if
                } // fine del ciclo for-each
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Recupera la lista dei cambi.
     * <p/>
     * LISTA PROIBITRA ???' TABELLA SU RICHIESTA
     * Recupera i dati dalla pagina http://www.xe.com/ict/ <br>
     * Per ogni valuta, crea un elemento della lista con codice e valore (separati da virgola) <br>
     *
     * @return lista dei cambi disponibili sul sito Cnn
     */
    private ArrayList<String> getCambiXeTabella() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        ArrayList<String> listaEur;
        ArrayList<String> listaAsia;
        String pagina = "http://www.xe.com/ict/";
        boolean continua;
        String contenuto;
        String tagIni = "</td><td class=\"price\">";
        String tagEnd = "</td>";
        String valore;
        int posIni;
        int posEnd;
        String nomeCnn;

        try { // prova ad eseguire il codice
            lista = new ArrayList<String>();

            contenuto = Lib.Web.getPagina(pagina);
            continua = (Lib.Testo.isValida(contenuto));

            /* cerca le parole chiave */
            if (continua) {
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera la lista dei cambi.
     * <p/>
     * Recupera i dati dalla pagina http://money.cnn.com/data/currencies/ <br>
     * Per ogni valuta, crea un elemento della lista con codice e valore (separtati da virgola) <br>
     *
     * @param pagina del sito
     *
     * @return lista dei cambi disponibili sulla singola pagina
     */
    private ArrayList<String> getCambi(String pagina) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        boolean continua;
        String contenuto;
        String tagIni = "</td><td class=\"price\">";
        String tagEnd = "</td>";
        String valore;
        int posIni;
        int posEnd;
        String nomeCnn;

        try { // prova ad eseguire il codice
            lista = new ArrayList<String>();

            contenuto = Lib.Web.getPagina(pagina);
            continua = (Lib.Testo.isValida(contenuto));

            /* cerca le parole chiave */
            if (continua) {
                /* traverso tutta la collezione */
                for (Valuta.Quindici valuta : Valuta.Quindici.values()) {
                    nomeCnn = valuta.getNome();
                    if (Lib.Testo.isValida(nomeCnn)) {
                        nomeCnn += tagIni;
                        posIni = contenuto.indexOf(nomeCnn);
                        if (posIni != -1) {
                            posIni += nomeCnn.length();
                            posEnd = contenuto.indexOf(tagEnd, posIni + 1);
                            if (posEnd != -1) {
                                valore = contenuto.substring(posIni, posEnd);
                                lista.add(valuta.getCodice() + SEP + valore);
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Valore del concambio euro.
     * <p/>
     *
     * @param lista in ingresso
     *
     * @return concambio euro/dollaro
     */
    private double getEuro(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        double euro = 0;
        String[] parte;

        try { // prova ad eseguire il codice

            /* traverso tutta la collezione */
            for (String stringa : lista) {
                parte = stringa.split(SEP);
                if (parte[0].equals(Valuta.Quindici.eur.getCodice())) {
                    euro = new Double(parte[1]);
                    break;
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return euro;
    }


    /**
     * Inserisce il valore del dollaro.
     * <p/>
     * I valori iniziali vengono presi dal dollaro
     * che quindi compare solo come inverso dell'Euro <br>
     *
     * @param listaIn ingresso
     *
     * @return lista convertita
     */
    private ArrayList<String> dollaro(ArrayList<String> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaOut = null;

        try { // prova ad eseguire il codice
            listaOut = listaIn;

            listaOut.add(Valuta.Quindici.usd.getCodice() + SEP + "1");
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Registra il valore di cambio del singolo record.
     * <p/>
     *
     * @param codIso della valuta
     * @param valore di cambio attuale
     */
    private void regolaCambio(String codIso, double valore) {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        ArrayList<CampoValore> campi;
        CampoValore campoCambio;
        CampoValore campoData;
        Date data;
        int cod;

        try { // prova ad eseguire il codice

            try { // prova ad eseguire il codice
                data = Lib.Data.getCorrente();

                mod = this.getModulo();

                cod = mod.query().valoreChiave(Valuta.Cam.codiceIso.get(), codIso);

                campoCambio = new CampoValore(Valuta.Cam.cambio.get(), valore);
                campoData = new CampoValore(Valuta.Cam.dataCambio.get(), data);

                campi = new ArrayList<CampoValore>();
                campi.add(campoCambio);
                campi.add(campoData);
                mod.query().registraRecordValori(cod, campi);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera il singolo valore.
     * <p/>
     * Recupera i dati dalla pagina http://www.oanda.com/convert/classic/ <br>
     *
     * @param codIso della valuta
     *
     * @return valore dal sito
     *
     * @deprecated
     */
    private double getCambioDinamico(String codIso) {
        /* variabili e costanti locali di lavoro */
        double valore = 0;
        boolean continua;
        String pagina = "http://www.oanda.com/convert/classic";
        String contenuto;
        String tagIni = "</td><td class=\"price\">";
        String tagEnd = "</td>";
        int posIni;
        int posEnd;
        String nomeCnn;

        try { // prova ad eseguire il codice

            contenuto = Lib.Web.getPagina(pagina);
            continua = (Lib.Testo.isValida(contenuto));

            /* cerca le parole chiave */
            if (continua) {

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    private boolean isSingolo() {
        return singolo;
    }


    private void setSingolo(boolean singolo) {
        this.singolo = singolo;
    }


    private int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }
}// fine della classe
