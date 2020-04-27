package com.angel.dataway.config;

import net.hasor.core.ApiBinder;
import net.hasor.core.AppContext;
import net.hasor.core.DimModule;
import net.hasor.core.TypeSupplier;
import net.hasor.db.JdbcModule;
import net.hasor.db.Level;
import net.hasor.spring.SpringModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.function.Supplier;

@DimModule
@Component
public class DataWayModule implements SpringModule{
    @Autowired
    private DataSource dataSource = null;
    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        // .DataSource form Spring boot into Hasor
        apiBinder.installModule(new JdbcModule(Level.Full, this.dataSource));
    }

    @Override
    public void onStart(AppContext appContext) throws Throwable {

    }

    @Override
    public void onStop(AppContext appContext) throws Throwable {

    }

    @Override
    public TypeSupplier springTypeSupplier(ApiBinder apiBinder) {
        return null;
    }

    @Override
    public <T> Supplier<T> getSupplierOfType(ApiBinder apiBinder, Class<T> targetType) {
        return null;
    }

    @Override
    public <T> Supplier<T> getSupplierOfName(ApiBinder apiBinder, String beanName) {
        return null;
    }
}
