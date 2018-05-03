package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	public static MenjacnicaInterface menjacnica = new Menjacnica();
	public static MenjacnicaGUI gp;
	public static Menjacnica sistem;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistem = new Menjacnica();
					gp = new MenjacnicaGUI();
					gp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void prikaziIzvrsiZamenuGUI() {
		JTable table = gp.getTable();
		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(gp,
					model.vratiValutu(table.getSelectedRow()));
			prozor.setLocationRelativeTo(gp.getContentPane());
			prozor.setVisible(true);
		}
	}
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(gp.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(gp.getContentPane(),
				"Autor: Nikola Madic , Verzija 1.1", "program Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(gp.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				GUIKontroler.sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void prikaziSveValute() {
		JTable table = gp.getTable();
		MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
		model.staviSveValuteUModel(GUIKontroler.sistem.vratiKursnuListu());

	}
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(gp.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				GUIKontroler.sistem.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI(gp);
		prozor.setLocationRelativeTo(gp.getContentPane());
		prozor.setVisible(true);
	}
	public static void prikaziObrisiKursGUI() {
			JTable table = gp.getTable();
		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(gp,
					model.vratiValutu(table.getSelectedRow()));
			prozor.setLocationRelativeTo(gp.getContentPane());
			prozor.setVisible(true);
		}
	}
	public static void unesiKurs(String naziv,String skraceniNaziv,int sifra,double prodajniKurs,double kupovniKurs,double srednjiKurs) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajniKurs);
			valuta.setKupovni(kupovniKurs);
			valuta.setSrednji(srednjiKurs);
			
			// Dodavanje valute u kursnu listu
			GUIKontroler.sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			GUIKontroler.prikaziSveValute();
			
			//Zatvaranje DodajValutuGUI prozora
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
}
