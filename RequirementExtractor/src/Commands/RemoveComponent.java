package Commands;

import BusinessObjects.Action;
import BusinessObjects.BusinessProcess;
import BusinessObjects.Repository;
import BusinessObjects.RequirementComponent;
import BusinessObjects.Step;

public class RemoveComponent {
	
	RequirementComponent child;
	
	public RemoveComponent(RequirementComponent child) {
		this.child=child;
	}
	
	
	public boolean execute()
	{
		Object parent = child.getParent();
		
		if(parent instanceof Repository )
		{
			((Repository) parent).getBusinessProcessList().remove(child);
			
			return true;
		}
		else if(parent instanceof BusinessProcess)
		{
			((BusinessProcess)parent).getStepsList().remove(child);
			return true;
		}
		else if(parent instanceof Step)
		{
			((Step)parent).getActionsList().remove(child);
			return true;
		}
			
		return false;
	}

}