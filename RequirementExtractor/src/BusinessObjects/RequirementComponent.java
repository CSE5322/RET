package BusinessObjects;

import javax.swing.tree.DefaultMutableTreeNode;

public class RequirementComponent  extends DefaultMutableTreeNode{
	
private Phrase phrase;

public RequirementComponent(Phrase p)
{
	this.phrase=p;
}

public Phrase getPhrase() {
	return phrase;
}

public void setPhrase(Phrase phrase) {
	this.phrase = phrase;
}

}
