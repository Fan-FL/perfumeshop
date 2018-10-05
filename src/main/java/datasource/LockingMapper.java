package datasource;

import concurrency.LockManager;
import domain.DomainObject;


public class LockingMapper implements IMapper{
    private IMapper impl;
    private LockManager lm;
    public LockingMapper(IMapper impl) {
        this.impl = impl;
        this.lm = LockManager.getInstance();
    }

    @Override
    public int insert(DomainObject obj) {
        try {
            this.lm.acquireWriteLock(obj);
            System.out.println("lock insert");
            int id = this.impl.insert(obj);
            this.lm.releaseWriteLock(obj);
            return id;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void update(DomainObject obj) {
        try {
            this.lm.acquireWriteLock(obj);
            System.out.println("lock update");
            this.impl.update(obj);
            this.lm.releaseWriteLock(obj);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(DomainObject obj) {
        try {
            this.lm.acquireWriteLock(obj);
            System.out.println("lock delete");
            this.impl.delete(obj);
            this.lm.releaseWriteLock(obj);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DomainObject findById(int id) {
        try {
            DomainObject obj = this.impl.findById(id);
            this.lm.acquireReadLock(obj);
            System.out.println("lock read");
            obj = this.impl.findById(id);
            this.lm.releaseReadLock(obj);
            return obj;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
