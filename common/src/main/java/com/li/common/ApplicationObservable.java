package com.li.common;

import java.util.Observable;

/**
 * Created by liweifa on 2016/11/10.
 */

public class ApplicationObservable extends Observable {
    public void exitNotify() {
        setChanged();
        notifyObservers();
        deleteObservers();
    }


}
