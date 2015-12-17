package kr.co.opensns.ksbiz.socialbot.util;

public interface Observable {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}
