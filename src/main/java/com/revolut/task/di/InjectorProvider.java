package com.revolut.task.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.task.di.ApplicationInjectorModule;

public class InjectorProvider {
    private static Injector injector;

    static {
        injector = Guice.createInjector(new ApplicationInjectorModule());
    }

    public static Injector provide() {
        return injector;
    }
}
