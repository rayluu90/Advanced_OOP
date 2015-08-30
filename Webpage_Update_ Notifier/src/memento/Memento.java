package memento;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Memento<E> implements Serializable {
	private List<E> subjectContainer;

	public Memento( List<E> aState ) {
		this.subjectContainer = aState;
	}
	
	public List<E> getState() {
		return this.subjectContainer;
	}
}
