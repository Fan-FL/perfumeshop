package service;

import datasource.DataMapper;
import domain.DomainObject;

import java.util.ArrayList;
import java.util.List;

public class UnitOfWork {
    private static ThreadLocal current = new ThreadLocal();

    private List<DomainObject> newObjects = new ArrayList<DomainObject>();
    private List<DomainObject> dirtyObjects = new ArrayList<DomainObject>();
    private List<DomainObject> deletedObjects = new ArrayList<DomainObject>();

    public static void newCurrent() {
        setCurrent(new UnitOfWork());
    }

    public static void setCurrent(UnitOfWork uow) {
        current.set(uow);
    }

    public static UnitOfWork getCurrent() {
        return (UnitOfWork) current.get();
    }

    public void registerNew(DomainObject obj) {
        if(!dirtyObjects.contains(obj) && !deletedObjects.contains(obj) && !newObjects.contains
                (obj))
        newObjects.add(obj);
    }

    public void registerDirty(DomainObject obj) {
        if (!deletedObjects.contains(obj) && !dirtyObjects.contains(obj) && !newObjects.contains
                (obj)) {
            dirtyObjects.add(obj);
        }
    }

    public void registerDeleted(DomainObject obj) {
        if (newObjects.remove(obj)) return;
        dirtyObjects.remove(obj);
        if (!deletedObjects.contains(obj)) {
            deletedObjects.add(obj);
        }
    }

    public void commit() {
        for (DomainObject obj : newObjects) {
            DataMapper.getMapper(obj.getClass()).insert(obj);
        }
        for (DomainObject obj : dirtyObjects) {
            DataMapper.getMapper(obj.getClass()).update(obj);
        }
        for (DomainObject obj : deletedObjects) {
            DataMapper.getMapper(obj.getClass()).delete(obj);
        }
    }

    public void rollBack() {
        newObjects.clear();
        dirtyObjects.clear();
        deletedObjects.clear();
    }
}
