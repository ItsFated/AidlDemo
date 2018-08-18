package com.im_hero.aidldemo.service;

import android.app.Service;
import android.os.Binder;

/**
 * @author Jason
 * @version 1.0
 */
public class ServiceBinder<T extends Service> extends Binder {
    private T service;

    ServiceBinder(T service) {
        this.service = service;
    }

    public T getService() {
        return service;
    }
}
