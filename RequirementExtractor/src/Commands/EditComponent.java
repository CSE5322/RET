package Commands;
import BusinessObjects.Action;
import BusinessObjects.BusinessProcess;
import BusinessObjects.Repository;
import BusinessObjects.RequirementComponent;
import BusinessObjects.Step;

public class EditComponent extends ListCommand {
	RequirementComponent oldCom;
	RequirementComponent newCom;
    int position;
	
	public EditComponent(RequirementComponent oldCom,RequirementComponent newCom,int position) {
				this.newCom = newCom;
                this.oldCom = oldCom;
	}
	
	public boolean execute()

	{
		
		RemoveComponent remove = new RemoveComponent(oldCom);
		remove.execute();
		
		AddComponent add = new AddComponent(newCom.getParent(),newCom, position);
		add.execute();
		
		return true;
	}

}