import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;

public class Main extends JFrame {

	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=230,227
	 */
	private final ButtonGroup radioGroup = new ButtonGroup();

	private static class ActionsComandsRadio {
		static final String FIRSTUPPER = "firstUpper";
		static final String UPPER = "upper";
		static final String LOWER = "lower";

	}
	  String[] exceptionsToUpper = {"para","da","das","de","do","dos","desse","dessa","desses","dessas",
			  					"dele","dela","deles","delas","deste","desta","destes","destas","disto",
			  					"disso","daquilo","daquele","daquela","daquelas","daqueles","daqui","dali",
			  					"em","no","na","nos","nas","nele","nela","nelas","neles","nisso","naquele","naquela","naquelas","naqueles",
			  					"num","pelo","pela","pelos","pelas","à","às","um","uma","uns","umas","ao","e","como",
			  					"muito","muita","muitos","muitas"};
	

	public static void main(String[] args) {
	
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					frame.setTitle("Preguiça de Ajeitar Texto na Mão");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		
		Arrays.sort(exceptionsToUpper);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInput = new JLabel("Entrada");
		lblInput.setBounds(30, 6, 60, 15);
		contentPane.add(lblInput);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 25, 420, 66);
		contentPane.add(scrollPane);

		JTextArea txtAreaInput = new JTextArea();
		
		

		scrollPane.setViewportView(txtAreaInput);
		txtAreaInput.setLineWrap(true);

		JLabel lblOutput = new JLabel("Saída");
		lblOutput.setBounds(30, 100, 60, 15);
		contentPane.add(lblOutput);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(30, 114, 420, 66);
		contentPane.add(scrollPane_1);

		JTextArea txtAreaOutput = new JTextArea();
		
		txtAreaOutput.setLineWrap(true);
		scrollPane_1.setViewportView(txtAreaOutput);

		JRadioButton radioUpperCase = new JRadioButton("TODAS MAIÚSCULAS");
		radioUpperCase.setSelected(true);

		radioUpperCase.setBounds(30, 180, 224, 18);
		contentPane.add(radioUpperCase);

		JRadioButton radioLowerCase = new JRadioButton("todas minusculas");
		radioLowerCase.setBounds(30, 200, 224, 18);
		contentPane.add(radioLowerCase);

		JRadioButton radioFirstUpper = new JRadioButton("Primeiras Maiusculas para Titulos");
		radioFirstUpper.setBounds(30, 220, 344, 18);
		contentPane.add(radioFirstUpper);

		JButton btnCopyOutuPut = new JButton("Copiar");
	
		btnCopyOutuPut.setBounds(360, 250, 100, 27);
		contentPane.add(btnCopyOutuPut);

		radioFirstUpper.setActionCommand(ActionsComandsRadio.FIRSTUPPER);
		radioLowerCase.setActionCommand(ActionsComandsRadio.LOWER);
		radioUpperCase.setActionCommand(ActionsComandsRadio.UPPER);
		
		radioGroup.add(radioFirstUpper);
		radioGroup.add(radioLowerCase);
		radioGroup.add(radioUpperCase);

		
		txtAreaInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				writeOutput(txtAreaInput, txtAreaOutput);
			}
		});

		radioFirstUpper.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writeOutput(txtAreaInput, txtAreaOutput);

			}
		});
		radioLowerCase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writeOutput(txtAreaInput, txtAreaOutput);

			}
		});
		radioUpperCase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writeOutput(txtAreaInput, txtAreaOutput);

			}
		});
		
		btnCopyOutuPut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringSelection stringSelection = new StringSelection(txtAreaOutput.getText());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
			}
		});

	}

	private void writeOutput(JTextArea inputText, JTextArea outputText) {

		System.out.println(radioGroup.getSelection().getActionCommand());

		switch (radioGroup.getSelection().getActionCommand()) {
		case ActionsComandsRadio.LOWER:
			outputText.setText(inputText.getText().toLowerCase());

			break;
		case ActionsComandsRadio.UPPER:
			outputText.setText(inputText.getText().toUpperCase());
			break;

		case ActionsComandsRadio.FIRSTUPPER:
			outputText.setText(getTextWithFirstLetterOfEveryWordOnUpperCase(inputText.getText()));
			break;
		default:
			break;
		}
		

	}
	private String getTextWithFirstLetterOfEveryWordOnUpperCase(String text) {
		text = text.toLowerCase();
		System.out.println(text);
		Pattern patternTherm = Pattern.compile("^[a-záàâãéèêíïóôõöúçñ]+|[\t \n][a-záàâãéèêíïóôõöúçñ]+|[a-záàâãéèêíïóôõöúçñ]+$",Pattern.UNICODE_CASE); 
		Matcher matcherTherm = patternTherm.matcher(text);
		
		Pattern patternWord = Pattern.compile("[a-záàâãéèêíïóôõöúçñ]+",Pattern.UNICODE_CASE); 
		
		
	
		while(matcherTherm.find()) {
			String findedString = matcherTherm.group();	
			
			Matcher matcherWord = patternWord.matcher(findedString);
			matcherWord.find();
			
			String word = matcherWord.group();
			
		
			if(Arrays.binarySearch(exceptionsToUpper, word) < 0)
				text = text.replace(findedString,     findedString.replace(word, word.substring(0, 1).toUpperCase() +word.substring(1) ))      ;
			
		}
			
					
		
		return text;
		
	}
}
