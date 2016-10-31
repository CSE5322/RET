package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import BusinessObjects.*;

public class MappingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	BusinessProcess currentBP;
	Step currentStep;

	JPanel buttonPanel, optionsPanel;
	JLabel lblVerb, lblNoun, lblSequenceNumber, lblParentBusinessProcess, lblParentStep;
	JTextField txtVerb, txtNoun;
	JComboBox<String> cbSequenceNumber, cbParentBusinessProcess, cbParentStep;
	Font font;

	private JLabel lblSentance;

	/**
	 * Create the dialog.
	 */	
	public MappingDialog(Phrase phrase) {

		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		font = new Font("Dialog", Font.BOLD,14);

		{
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Add");
				okButton.setActionCommand("OK");
				buttonPanel.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}

		{
			optionsPanel = new JPanel();
			optionsPanel.setLayout(new GridLayout(0,2,5,10));
			getContentPane().add(optionsPanel, BorderLayout.WEST);	

			{
				
				lblVerb = new JLabel("Verb : ");
				lblNoun = new JLabel("Noun : ");
				txtVerb = new JTextField(phrase.getVerb());
				txtVerb.setFont(font);				
				txtVerb.setOpaque(false);
				txtNoun = new JTextField(phrase.getNoun());
				txtNoun.setFont(font);
				txtNoun.setOpaque(false);			
				
				optionsPanel.add(lblVerb);
				optionsPanel.add(txtVerb);
				optionsPanel.add(lblNoun);
				optionsPanel.add(txtNoun);

				lblSequenceNumber = new JLabel("Sequence Number : ");
				cbSequenceNumber = new JComboBox<>();

				if(component instanceof BusinessProcess)
				{					
					for(int i = 1; i <= Repository.getInstance().getChildCount()+1; i++)
						cbSequenceNumber.addItem(Integer.toString(i));
					if(component.getParent()==null)
						cbSequenceNumber.setSelectedIndex(cbSequenceNumber.getItemCount()-1);
					else
						cbSequenceNumber.setSelectedIndex(Repository.getInstance().getIndex((BusinessProcess)component));
				}

				if(component instanceof Step || component instanceof Action)
				{
					lblParentBusinessProcess = new JLabel("Parent Business Process : ");
					ArrayList<BusinessProcess> businessProcessList = Repository.getInstance().getBusinessProcessList();
					String[] bpArr = new String[Repository.getInstance().getChildCount()];
					for(int i=0; i<Repository.getInstance().getChildCount(); i++)
						bpArr[i] = businessProcessList.get(i).getPhrase().getSentence();
					cbParentBusinessProcess = new JComboBox<>(bpArr);		
					optionsPanel.add(lblParentBusinessProcess);
					optionsPanel.add(cbParentBusinessProcess);

					if(component instanceof Step)
					{
						for(int i = 1; i <= Repository.getInstance().getChildAt(0).getChildCount()+1; i++)
							cbSequenceNumber.addItem(Integer.toString(i));
						if(component.getParent()==null)
							cbSequenceNumber.setSelectedIndex(cbSequenceNumber.getItemCount()-1);
						else
						{
							BusinessProcess parentBP = (BusinessProcess)component.getParent();
							cbSequenceNumber.setSelectedIndex(parentBP.getIndex((Step)component));
							cbParentBusinessProcess.setSelectedIndex(Repository.getInstance().getIndex(parentBP));
						}

						cbParentBusinessProcess.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg) {
								currentBP = Repository.getInstance().getBusinessProcessAt(cbParentBusinessProcess.getSelectedIndex());
								cbSequenceNumber.removeAllItems();
								for(int i = 1; i <= currentBP.getChildCount()+1; i++)
									cbSequenceNumber.addItem(Integer.toString(i));
								cbSequenceNumber.setSelectedIndex(cbSequenceNumber.getItemCount()-1);
							}
						});
					}								

					if(component instanceof Action)
					{
						lblParentStep = new JLabel("Parent Step : ");
						ArrayList<Step> stepList = Repository.getInstance().getBusinessProcessAt(0).getStepsList();
						String[] stepArr = new String[stepList.size()];
						for(int i=0; i<stepList.size(); i++)
							stepArr[i] = stepList.get(i).getPhrase().getSentence();
						cbParentStep = new JComboBox<>(stepArr);		
						optionsPanel.add(lblParentStep);
						optionsPanel.add(cbParentStep);

						currentBP = Repository.getInstance().getBusinessProcessAt(0);
						for(int i = 1; i <= currentBP.getChildAt(0).getChildCount()+1; i++)
							cbSequenceNumber.addItem(Integer.toString(i));
						if(component.getParent()==null)
							cbSequenceNumber.setSelectedIndex(cbSequenceNumber.getItemCount()-1);
						else
						{
							Step parentStep = (Step)component.getParent();
							BusinessProcess parentBP = (BusinessProcess)parentStep.getParent();
							cbSequenceNumber.setSelectedIndex(parentStep.getIndex((Action)component));
							cbParentStep.setSelectedIndex(parentBP.getIndex(parentStep));
							cbParentBusinessProcess.setSelectedIndex(Repository.getInstance().getIndex(parentBP));							
						}

						cbParentBusinessProcess.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg) {							
								currentBP = Repository.getInstance().getBusinessProcessAt(cbParentBusinessProcess.getSelectedIndex());														
								cbParentStep.removeAllItems();
								for (Step step : currentBP.getStepsList())
									cbParentStep.addItem(step.getPhrase().getSentence());	
							}
						});

						cbParentStep.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg) {
								if(cbParentStep.getSelectedIndex() >= 0)
								{
									currentStep = currentBP.getStepAt(cbParentStep.getSelectedIndex());
									cbSequenceNumber.removeAllItems();
									for(int i = 1; i <= currentStep.getChildCount()+1; i++)
										cbSequenceNumber.addItem(Integer.toString(i));
									cbSequenceNumber.setSelectedIndex(cbSequenceNumber.getItemCount()-1);
								}
							}
						});
					}
				}
				optionsPanel.add(lblSequenceNumber);
				optionsPanel.add(cbSequenceNumber);				
			}			

		}
		this.pack();
	}

}