package GUI;

import BusinessObjects.*;
import Commands.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.text.Highlighter.Highlight;
import BusinessObjects.Phrase;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTree;

public class TestSwing extends JFrame {

	protected JTree tree = null;
	protected DefaultMutableTreeNode root;
	protected DefaultTreeModel treeModel;

	// DefaultTreeModel treeModel = new DefaultTreeModel(tree);
	public ArrayList<String> arrBP = new ArrayList<String>();
	public ArrayList<String> arrSP = new ArrayList<String>();
	public ArrayList<String> arrAC = new ArrayList<String>();

	Repository repository = Repository.getInstance();

	private JPanel contentPane;
	DefaultMutableTreeNode BusinessProcess = null;
	DefaultMutableTreeNode Step = null;
	DefaultMutableTreeNode Action = null;

	Highlighter highlighter;
	JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestSwing frame = new TestSwing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestSwing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 970, 715);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(0, 27, 714, 300);
		textArea.setFont(new Font("Calibri", Font.ITALIC, 20));
		textArea.setSelectedTextColor(Color.red);
		contentPane.add(textArea);

		highlighter = textArea.getHighlighter();

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(textArea, popupMenu);

		//
		JMenuItem mntmHighlightVerb = new JMenuItem("Highlight Verb");
		JMenuItem mntmHighlightNoun = new JMenuItem("Highlight Noun");
		JMenuItem mntmAddBuisnessProcess = new JMenuItem("Add Business Process");
		JMenuItem mntmAddStep = new JMenuItem("Add Step");
		JMenuItem mntmAddAction = new JMenuItem("Add Action");

		mntmHighlightVerb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					HighlightSelectedWord();
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
			}

			public void HighlightSelectedWord() throws BadLocationException {

				highlighter.addHighlight(textArea.getSelectionStart(), textArea.getSelectionEnd(),
						new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));

			}

		});

		mntmHighlightNoun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				try {
					HighlightSelectedWord();
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
			}

			public void HighlightSelectedWord() throws BadLocationException {

				highlighter.addHighlight(textArea.getSelectionStart(), textArea.getSelectionEnd(),

						new DefaultHighlighter.DefaultHighlightPainter(Color.green));

			}

		});

		JFrame currentFrame = this;

		mntmAddBuisnessProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg2) {
				try {
					AddVerbNounPairAsBusinessProcess();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			public void AddVerbNounPairAsBusinessProcess() throws BadLocationException {

				// RequirementComponent component = new
				// BusinessProcess(getPhrase());
				Phrase tempPhrase = getPhrase();
				CreateBusinessProcessDialog bpDialog = new CreateBusinessProcessDialog((TestSwing)currentFrame,tempPhrase);
				// MappingDialog bpDialog = new MappingDialog(tempPhrase);
				bpDialog.setLocationRelativeTo(currentFrame);
				bpDialog.setVisible(true);
				//refresh();
				/*
				 * arrBP.add(textArea.getSelectedText()); MappingDialog bpDialog
				 * = new MappingDialog("bTxt", "bTxt", 1);
				 * bpDialog.setLocationRelativeTo(currentFrame);
				 * bpDialog.setVisible(true);
				 * 
				 */
			}

		});

		mntmAddStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg3) {
				try {
					AddVerbNounPairAsStep();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			public void AddVerbNounPairAsStep() throws BadLocationException {
				/*
				 * RequirementComponent component = new Step(getPhrase()); //
				 * MappingDialog stepDialog = new MappingDialog(component);
				 * CreateBusinessProcessDialog
				 * 
				 * stepDialog.setLocationRelativeTo(currentFrame);
				 * stepDialog.setVisible(true);
				 */

				/*
				 * arrSP.add(textArea.getSelectedText()); MappingDialog
				 * stepDialog = new MappingDialog("sTxt", "sTxt", 2);
				 * stepDialog.setLocationRelativeTo(currentFrame);
				 * stepDialog.setVisible(true);
				 */

			}
		});

		mntmAddAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg4) {
				try {
					AddVerbNounPairAsAction();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			public void AddVerbNounPairAsAction() throws BadLocationException {

				/*
				 * RequirementComponent component = new Action(getPhrase());
				 * //MappingDialog actionDialog = new MappingDialog(component);
				 * actionDialog.setLocationRelativeTo(currentFrame);
				 * actionDialog.setVisible(true);
				 */

				/*
				 * arrAC.add(textArea.getSelectedText()); MappingDialog
				 * actionDialog = new MappingDialog("aTxt", "aTxt", 3);
				 * actionDialog.setLocationRelativeTo(currentFrame);
				 * actionDialog.setVisible(true);
				 */

			}
		});

		popupMenu.add(mntmHighlightVerb);
		popupMenu.add(mntmHighlightNoun);
		popupMenu.add(mntmAddBuisnessProcess);
		popupMenu.add(mntmAddStep);
		popupMenu.add(mntmAddAction);

		JLabel lblInputDescription = new JLabel("Input Description");
		lblInputDescription.setBounds(0, 4, 144, 20);
		contentPane.add(lblInputDescription);

		JButton btnNewButton = new JButton("Display Profile Details");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("Course:SDP_CSE5322 \n Project:Requirement Extraction Tool");
			}
		});
		btnNewButton.setActionCommand("btnDisplayProfile");
		btnNewButton.setBounds(729, 23, 204, 29);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Import File");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				String FileName = null;
				int returnval = jfc.showOpenDialog(null);
				if (JFileChooser.APPROVE_OPTION == returnval) {
					File file = jfc.getSelectedFile();
					FileName = file.getAbsolutePath();
				}
				String strInputFile = new String();
				ImportFile objImpFile = new ImportFile();
				strInputFile = objImpFile.FetchInputData(FileName);
				if (strInputFile != null) {
					textArea.setText(strInputFile);
				} else {
					textArea.setText("");
				}
			}
		});

		btnNewButton_1.setBounds(729, 68, 204, 29);
		contentPane.add(btnNewButton_1);

		JButton btnGenerateRequirements = new JButton("Generate Requirement");
		btnGenerateRequirements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				new GenerateDialog().setVisible(true);;
				
				// contentPane.
				// treeModel.addTreeModelListener(new
				// TreeModelActionListener());

				/*
				 * tree.addTreeSelectionListener(new TreeSelectionListener() {
				 * 
				 * public void valueChanged(TreeSelectionEvent e) {
				 * DefaultMutableTreeNode selectedNode =
				 * (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				 * 
				 * System.out.println(tree.getSelectionPath());
				 * System.out.println(((RequirementComponent)selectedNode).
				 * getPhrase()); } });
				 */
			}

			private void createNodes() {
				BusinessProcess bp1 = new BusinessProcess(new Phrase("verb1", "noun1"));
				BusinessProcess bp2 = new BusinessProcess(new Phrase("verb2", "noun2"));
				BusinessProcess bp3 = new BusinessProcess(new Phrase("verb3", "noun3"));

				Step s1 = new Step(new Phrase("verb4", "noun4"));
				Step s2 = new Step(new Phrase("verb5", "noun5"));
				Step s3 = new Step(new Phrase("verb6", "noun6"));

				Action a1 = new Action(new Phrase("verbA1", "nounA1"));
				Action a2 = new Action(new Phrase("verbA1", "nounA1"));
				Action a3 = new Action(new Phrase("verbA1", "nounA1"));

				ListCommand cmd1 = new AddComponent(bp1, s1, 0);
				cmd1.execute();
				cmd1 = new AddComponent(bp1, s2, 1);
				cmd1.execute();
				cmd1 = new AddComponent(bp1, s3, 1);
				cmd1.execute();
				cmd1 = new AddComponent(s1, a1, 0);
				cmd1.execute();
				cmd1 = new AddComponent(s2, a2, 0);
				cmd1.execute();
				cmd1 = new AddComponent(s3, a3, 0);
				cmd1.execute();
				cmd1 = new AddComponent(repository, bp1, 0);
				cmd1.execute();
				cmd1 = new AddComponent(repository, bp2, 1);
				cmd1.execute();
				cmd1 = new AddComponent(repository, bp3, 1);
				cmd1.execute();

				for (int i = 0; i < arrBP.size(); i++) {
					BusinessProcess = new DefaultMutableTreeNode(arrBP.get(i));
					repository.add(BusinessProcess);

					BusinessProcess.setUserObject(arrBP);
					for (int j = 0; j < arrSP.size(); j++) {
						Step = new DefaultMutableTreeNode(arrSP.get(j));
						BusinessProcess.add(Step);

						for (int k = 0; k < arrAC.size(); k++) {
							Action = new DefaultMutableTreeNode(arrAC.get(k));
							Step.add(Action);
						}

					}

				}

			}
		});
		btnGenerateRequirements.setBounds(729, 113, 204, 29);
		contentPane.add(btnGenerateRequirements);

		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tree.setEditable(true);

			}
		});
		editButton.setBounds(726, 194, 204, 29);
		contentPane.add(editButton);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tree.setEditable(false);
			}
		});
		saveButton.setBounds(729, 235, 204, 29);
		contentPane.add(saveButton);
	}

	public void refreshTree() {
		if (tree == null) {
			treeModel = new DefaultTreeModel(Repository.getInstance());
			tree = new JTree(treeModel);
			contentPane.add(tree);
			tree.setBounds(10, 343, 704, 300);

			// createNodes();
		} else {
			// contentPane.getComponent(tree)

			treeModel.reload();
		}

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private Phrase getPhrase() throws BadLocationException {
		String verb = null, noun = null;
		for (Highlight highlight : highlighter.getHighlights()) {
			if (highlight.getStartOffset() >= textArea.getSelectionStart()
					&& highlight.getEndOffset() <= textArea.getSelectionEnd()) {
				if (((DefaultHighlighter.DefaultHighlightPainter) highlight.getPainter()).getColor() == Color.yellow) {
					verb = textArea.getText(highlight.getStartOffset(),
							highlight.getEndOffset() - highlight.getStartOffset());
				} else if (((DefaultHighlighter.DefaultHighlightPainter) highlight.getPainter())
						.getColor() == Color.green) {
					noun = textArea.getText(highlight.getStartOffset(),
							highlight.getEndOffset() - highlight.getStartOffset());
				}
			}
		}
		return new Phrase(verb, noun);
	}
}
