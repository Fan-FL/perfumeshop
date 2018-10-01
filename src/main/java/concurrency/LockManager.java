package concurrency;


import domain.DomainObject;

import java.util.HashMap;
import java.util.Map;

public class LockManager {

    private final Map<DomainObject, ReadWriteLock> lockMap;

    private static LockManager manager;

    private LockManager() {
        lockMap = new HashMap<>();
    }

    public static LockManager getInstance() {
        if (LockManager.manager == null) {
            LockManager.manager = new LockManager();
        }
        return LockManager.manager;
    }

    public synchronized void acquireReadLock(DomainObject toLock)
            throws InterruptedException {
        getReadWriteLock(toLock).lockRead();
    }

    public synchronized void acquireWriteLock(DomainObject toLock)
            throws InterruptedException {
        getReadWriteLock(toLock).lockWrite();
    }

    public synchronized void releaseReadLock(DomainObject toLock) {
        getReadWriteLock(toLock).unlockRead();
    }

    public synchronized void releaseWriteLock(DomainObject toLock) {
        getReadWriteLock(toLock).unlockWrite();
    }

    public synchronized void releaseAllLocksOn(DomainObject toLock) {
        getReadWriteLock(toLock).unlock();
    }

    public synchronized void releaseAllLocks() {
        for (Map.Entry<DomainObject, ReadWriteLock> entry : lockMap.entrySet()) {
            entry.getValue().unlock();
        }
    }

    private ReadWriteLock getReadWriteLock(DomainObject toLock) {
        ReadWriteLock lock = lockMap.get(toLock);
        if (lock == null) {
            lockMap.putIfAbsent(toLock, new ReadWriteLock());
            lock = lockMap.get(toLock);
        }
        return lock;
    }
}
