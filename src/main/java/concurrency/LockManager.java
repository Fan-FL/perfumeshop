package concurrency;


import domain.DomainObject;

import java.util.HashMap;
import java.util.Map;

/*
 * ClassName: LockManager
 * Description: This class is a component that keeps track of which processes are locking which data items.
 *              Typically, it is implemented as a simple map from locks to owners, where owners are represented
 *              via their session ID. Code implementing business transactions should interaction only with lock
 *              managers, and never with locks themselves.
 */
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

    /*
     * Parameters: toLock
     * Return: none
     * Description: Acquire read lock on a single object
     * */
    public synchronized void acquireReadLock(DomainObject toLock)
            throws InterruptedException {
        getReadWriteLock(toLock).lockRead();
    }

    /*
     * Parameters: toLock
     * Return: none
     * Description: Acquire write lock on a single object
     * */
    public synchronized void acquireWriteLock(DomainObject toLock)
            throws InterruptedException {
        getReadWriteLock(toLock).lockWrite();
    }

    /*
     * Parameters: toLock
     * Return: none
     * Description: Removes read lock acquired on the object hence
     *              it is available for locking by other users
     * */
    public synchronized void releaseReadLock(DomainObject toLock) {
        getReadWriteLock(toLock).unlockRead();
    }

    /*
     * Parameters: toLock
     * Return: none
     * Description: Removes write lock acquired on the object hence
     *              it is available for locking by other users
     * */
    public synchronized void releaseWriteLock(DomainObject toLock) {
        getReadWriteLock(toLock).unlockWrite();
    }

    /*
     * Parameters: toLock
     * Return: none
     * Description: Removes all locks acquired on the object hence
     *              they are available for locking by other users
     * */
    public synchronized void releaseAllLocksOn(DomainObject toLock) {
        getReadWriteLock(toLock).unlock();
    }

    /*
     * Parameters: none
     * Return: none
     * Description: Removes all locks acquired on the all objects
     * */
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
