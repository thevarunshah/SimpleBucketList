package com.thevarunshah.classes;

public class BucketItem {
	
	private String goal = "";
	private boolean done;
	
	public BucketItem(String goal){
		this.goal = goal;
		this.done = false;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	@Override
	public String toString(){
		
		return this.goal;
	}

}
