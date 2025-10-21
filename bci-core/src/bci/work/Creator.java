package bci.work;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Creator implements Serializable, Comparable<Creator> {

	private final String _name;
	private List<Work> _works = new ArrayList<>();

	public Creator(String name) {
		_name = name;
	}

	public void addWork(Work work) {
		_works.add(work);
	}

	public void removeWork(Work work) {
		_works.remove(work);
	}

    public String getName(){
        return _name;
    }

    public List<Work> getWorks(){
        return _works;
    }

    public boolean isEmpty(){
        return _works.isEmpty();
    }

    @Override
    public int compareTo(Creator creator){
        return _name.compareTo(creator._name);
    }

}
