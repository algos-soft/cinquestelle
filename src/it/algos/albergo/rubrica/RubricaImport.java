package it.algos.albergo.rubrica;

import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.categoria.CatAnagrafica;
import it.algos.gestione.anagrafica.categoria.CatAnagraficaModulo;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModulo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.rubrica.RubricaModulo;

import java.util.ArrayList;
import java.util.List;

/**
 * Importazione della Rubrica da file CSV.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-ott-2007 ore 19.21.46
 */
public class RubricaImport extends DialogoAnnullaConferma {

    private ArrayList<String> colonne = null;


    /**
     * Costruttore base con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public RubricaImport(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            this.setTitolo("Importazione iniziale dei dati");
            this.setMessaggio("Questa operazione richiede alcuni minuti\nVuoi proseguire?");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Esegue l'importazione dei dati.
     * </p>
     */
    public void esegueImport() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        List<List<String>> lista;
        int quanti = 0;

        try { // prova ad eseguire il codice
            lista = Lib.Imp.importCSV();
            continua = (lista != null);

            if (continua) {
                quanti = lista.size();
                continua = (quanti > 1); // ci deve essere la riga titoli
            }// fine del blocco if

            if (continua) {

                this.elaboraTitoli(lista.get(0));

                System.out.println("Start import rubrica: " + (quanti - 1) + " records");
                for (int k = 1; k < lista.size(); k++) {
                    this.elaboraRiga(lista.get(k));
                    System.out.println(k + "/" + (quanti - 1));   // esclusa riga titoli
                } // fine del ciclo for

            }// fine del blocco if

            if (continua) {
                this.getModulo().caricaTutti();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Estrae i nomi delle colonne dalla prima riga.
     * </p>
     *
     * @param rigaTitoli - lista delle colonne della prima riga
     */
    private void elaboraTitoli(List<String> rigaTitoli) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaColonne;
        String tagSep = ",";
        int pos;

        try { // prova ad eseguire il codice

            /* spazzola i titoli per estrarre il solo nome
             * contengono anche il tipo di dati e la lunghezza) */
            listaColonne = new ArrayList<String>();
            for (String col : rigaTitoli) {
                if (col.contains(tagSep)) {
                    pos = col.indexOf(tagSep);
                    col = col.substring(0, pos);
                    listaColonne.add(col);
                }// fine del blocco if
            } // fine del ciclo for-each

            this.setColonne(listaColonne);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Elaborazione del singolo record.
     * </p>
     *
     * @param listaValori - lista di valori per una riga
     */
    private void elaboraRiga(List<String> listaValori) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaColonne;
        String[] campi;
        String nome;
        String valore;
        SetValori sv;
        int numColonne;
        char car = (char)236;
        String swrap = Character.toString(car) + "\n";
        String valCampo;

        try { // prova ad eseguire il codice

            /**
             * crea un array di stringhe tolte le virgolette
             * e ripulite da eventuali soft wrap
             */
            listaColonne = this.getColonne();
            numColonne = listaColonne.size();
            campi = new String[numColonne];
            for (int k = 0; k < numColonne; k++) {
                valCampo = listaValori.get(k);
                valCampo = valCampo.replaceAll(swrap, "");
                valCampo = Lib.Testo.levaVirgolette(valCampo);
                campi[k] = valCampo;
            } // fine del ciclo for

            /* pulisce tutti i valori di tutte le colonne */
            Colonna.reset();

            /* mette i valori dei campi nelle rispettive colonne */
            for (Colonna colonna : Colonna.values()) {
                for (int k = 0; k < campi.length; k++) {
                    nome = listaColonne.get(k);
                    if (nome.equals(colonna.getTitoloCsv())) {
                        valore = campi[k];
                        colonna.setValore(valore);
                        break;
                    }// fine del blocco if
                } // fine del ciclo for
            } // fine del ciclo for-each

            /* lancia il metodo di calcolo su ogni colonna */
            Colonna.calcAll();

            /**
             * recupera il codice della città dal nome
             * se non lo trova aggiunge il nome della città e la provincia
             * in coda alla colonna note
             */
            String nomeCitta = Colonna.localita.getStringa();
            int codCitta = getCodCitta(nomeCitta);
            if (codCitta <= 0) {
                if (Lib.Testo.isValida(nomeCitta)) {
                    String stringa = Colonna.note.getStringa();
                    stringa += "\n" + nomeCitta;
                    String strProv = Colonna.provincia.getStringa();
                    if (Lib.Testo.isValida(strProv)) {
                        stringa += " (" + strProv + ")";
                    }// fine del blocco if
                    Colonna.note.setValore(stringa);
                }// fine del blocco if
            }// fine del blocco if

            /* crea un set valori con tutti i campi da registrare sul database */
            sv = Colonna.creaSetValori();

            /* crea il record di Rubrica */
            Modulo mod = RubricaModulo.get();
            int codAnag = mod.query().nuovoRecord(sv.getListaValori());

            /* crea il record di Indirizzo */
            Modulo modIndirizzo = IndirizzoModulo.get();
            sv = new SetValori(modIndirizzo);
            sv.add(Indirizzo.Cam.anagrafica.get(), codAnag);
            sv.add(Indirizzo.Cam.indirizzo.get(), Colonna.indirizzo.getStringa());
            if (codCitta > 0) {
                sv.add(Indirizzo.Cam.citta.get(), codCitta);
            }// fine del blocco if
            sv.add(Indirizzo.Cam.cap.get(), Colonna.cap.getStringa());
            sv.add(Indirizzo.Cam.tipo.get(), 0);
            modIndirizzo.query().nuovoRecord(sv.getListaValori());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Regola il valore di legame del campo link categoria.
     * </p>
     * Controlla se esiste già il valore nella tavola collegata <br>
     * Se non esiste, lo crea <br>
     *
     * @param valore di testo proveniente dall'import
     *
     * @return il valore chiave del record trovato o creato
     */
    private static int regolaCategoria(Object valore) {
        /* variabili e costanti locali di lavoro */
        int codLink = 0;
        boolean continua;
        Modulo mod = null;
        boolean esiste;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(valore));

            if (continua) {
                mod = CatAnagraficaModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                esiste = mod.query().isEsisteRecord(CatAnagrafica.Cam.sigla.get(), valore);
                if (esiste) {
                    codLink = mod.query().valoreChiave(CatAnagrafica.Cam.sigla.get(), valore);
                } else {
                    codLink = mod.query().nuovoRecord(CatAnagrafica.Cam.sigla.get(), valore);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codLink;
    }// fine del metodo


    /**
     * Recupera il codice della città.
     * </p>
     *
     * @param nomeCitta della città
     *
     * @return il codice della città, 0 se non trovato
     */
    private static int getCodCitta(String nomeCitta) {
        /* variabili e costanti locali di lavoro */
        int codCitta = 0;
        Modulo mod;

        try { // prova ad eseguire il codice

            mod = CittaModulo.get();
            codCitta = mod.query().valoreChiave(Citta.Cam.citta.get(), nomeCitta);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCitta;

    }// fine del metodo


    private ArrayList<String> getColonne() {
        return colonne;
    }


    private void setColonne(ArrayList<String> colonne) {
        this.colonne = colonne;
    }


    /**
     * Classe interna Enumerazione.
     */
    private enum Colonna {

        nome("NOME", Anagrafica.Cam.ragSociale.get(), true),
        indirizzo("INDIRIZZO", Indirizzo.Cam.indirizzo.get(), false),
        cap("CAP", Indirizzo.Cam.cap.get(), false),
        localita("LOCALITA", Indirizzo.Cam.citta.get(), false),
        provincia("PROVINCIA", Citta.Cam.linkProvincia.get(), false),
        telefono("TELEFONO", Anagrafica.Cam.telefono.get(), true),
        fax("TELEFAX", Anagrafica.Cam.fax.get(), true),
        note("NOTE", Anagrafica.Cam.note.get(), true),
        categoria("GRUPPO", Anagrafica.Cam.categoria.get(), true) {
            protected void calc() {
                this.setValore(regolaCategoria(this.getValore()));
            }
        },
        societa("", Anagrafica.Cam.privatosocieta.get(), true) {
            protected void calc() {
                this.setValore(Anagrafica.Tipi.societa.getCodice());
            }
        },
        soggetto("", Anagrafica.Cam.soggetto.get(), true) {
            protected void calc() {
                this.setValore(Colonna.nome.getStringa());
            }
        };

        

//        titolo("TITOLO", Anagrafica.Cam.titolo.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaTitolo(this.getValore()));
//            }
//        },
//        nome("NOME", Anagrafica.Cam.nome.get(), true, true),
//        cognome("COGNOME", Anagrafica.Cam.cognome.get(), true, true),
//        soggetto("", Anagrafica.Cam.soggetto.get(), false, true) {
//            protected void calc() {
//                this.setValore(Colonna.cognome.getValore() + " " + Colonna.nome.getValore());
//            }
//        },
//        sesso("SESSO", Anagrafica.Cam.sesso.get(), true, true) {
//            protected void calc() {
//                Object val = this.getValore();
//
//                if (Lib.Testo.isValida(val)) {
//                    if (val.equals("F")) {
//                        val = 2;
//                    } else {
//                        val = 1;
//                    }// fine del blocco if-else
//                }// fine del blocco if
//                this.setValore(val);
//            }
//        },
//        luogoNato("LUOGONASCI", ClienteAlbergo.Cam.luogoNato.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaNascita(this.getValore()));
//            }
//        },
//        dataNato("DATANASCI", ClienteAlbergo.Cam.dataNato.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaData(this.getValore()));
//            }
//        },
//        nazioneNato("NAZ_NASCI", "", true, false),
//        provinciaNato("PROV_NASCI", "", true, false),
//
//        nazione("NAZIONE", Citta.Cam.linkNazione.get(), true, false),
//        regione("REGIONE", "", true, false),
//        cellulare("", Anagrafica.Cam.cellulare.get(), false, false),
//
//        dataDoc("RILDOC", ClienteAlbergo.Cam.dataDoc.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaData(this.getValore()));
//            }
//        },
//        codiceDoc("CODDOC", ClienteAlbergo.Cam.tipoDoc.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaTipoDocumento(this.getValore()));
//            }
//        },
//        autoritaDoc("AUTRILASCI", ClienteAlbergo.Cam.autoritaDoc.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaAutorita(this.getValore()));
//            }
//        },
//        numDoc("NUMDOC", ClienteAlbergo.Cam.numDoc.get(), true, true),
//        scadenzaDoc("SCADDOC", ClienteAlbergo.Cam.scadenzaDoc.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaData(this.getValore()));
//            }
//        },
//        creazioneAna("DATA_REGIS", "", true, false) {
//            protected void calc() {
//                this.setValore(regolaData(this.getValore()));
//            }
//        },
//        modificaAna("ULT_MOD", "", true, false) {
//            protected void calc() {
//                this.setValore(regolaData(this.getValore()));
//            }
//        },
//        lingua("LINGUA", ClienteAlbergo.Cam.lingua.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaLingua(this.getValore()));
//            }
//        },
//        posta("CORRISPOND", ClienteAlbergo.Cam.checkPosta.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaBool(this.getValore()));
//            }
//        },
//        famiglia("FAMIGLIA", ClienteAlbergo.Cam.checkFamiglia.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaBool(this.getValore()));
//            }
//        },
//        evidenza("EVIDENZ", ClienteAlbergo.Cam.checkEvidenza.get(), true, true) {
//            protected void calc() {
//                this.setValore(regolaBool(this.getValore()));
//            }
//        },
//        notePers("PERSONALE", ClienteAlbergo.Cam.notePersonali.get(), true, true),;
//

        /**
         * titolo tabella import
         */
        private String titoloCsv;

        /**
         * nome del campo
         */
        private String nomeCampo;

        /**
         * flag
         */
        private boolean db;

        /**
         * valore corrente
         */
        private Object valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param titoloCsv - titolo tabella import
         * @param nomeCampo - nome del campo
         * @param db - true se è una colonna della tavola di import, false se di altre tavole
         */
        Colonna(String titoloCsv, String nomeCampo, boolean db) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitoloCsv(titoloCsv);
                this.setNomeCampo(nomeCampo);
                this.setDb(db);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static void reset() {
            try { // prova ad eseguire il codice
                for (Colonna col : Colonna.values()) {
                    col.setValore(null);
                } // fine del ciclo for-each
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static void calcAll() {
            try { // prova ad eseguire il codice
                for (Colonna col : Colonna.values()) {
                    col.calc();
                } // fine del ciclo for-each
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static SetValori creaSetValori() {
            /* variabili e costanti locali di lavoro */
            SetValori sv = new SetValori(RubricaModulo.get());

            try { // prova ad eseguire il codice
                for (Colonna col : Colonna.values()) {
                    if (col.isDb()) {
                        String nome = col.getNomeCampo();
                        Object valore = col.getValore();
                        sv.add(nome, valore);
                    }// fine del blocco if
                } // fine del ciclo for-each
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return sv;
        }


        protected void calc() {
        }


        public String getStringa() {
            return Lib.Testo.getStringa(this.getValore());
        }


        public String getTitoloCsv() {
            return titoloCsv;
        }


        private void setTitoloCsv(String titoloCsv) {
            this.titoloCsv = titoloCsv;
        }


        public String getNomeCampo() {
            return nomeCampo;
        }


        private void setNomeCampo(String nomeCampo) {
            this.nomeCampo = nomeCampo;
        }


        public boolean isDb() {
            return db;
        }


        private void setDb(boolean db) {
            this.db = db;
        }


        public Object getValore() {
            return valore;
        }


        public void setValore(Object valore) {
            this.valore = valore;
        }
    }// fine della classe


}// fine della classe