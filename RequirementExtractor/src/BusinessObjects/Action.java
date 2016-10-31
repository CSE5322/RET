package BusinessObjects;

import javax.swing.tree.TreeNode;

public class Action extends RequirementComponent {
	
	
	
	public Action(Phrase phrase)
	{
		
		super(phrase);
	}
	public String toString()
	{
		String verb=this.getPhrase().getVerb();
		String noun=this.getPhrase().getNoun();
		//TODO 
		return "System will allow user to" + verb + " " + noun;
	}
	
    public TreeNode getChildAt(int index) {
        
            throw new ArrayIndexOutOfBoundsException("node has no children");
     
    }

    /**
     * Returns the number of children of this node.
     *
     * @return  an int giving the number of children of this node
     */
    public int getChildCount() {
       
            return 0;
       
    }
	 
}
