package concurrency;

import java.util.HashMap;
import java.util.Map;

/*
 * ClassName: ReadWriteLock
 * Description: A pessimistic lock and an implicit lock are used to prevent
 *              problems occur when at least one process wants to add, delete,
 *              or update the data in this class.
 */
class ReadWriteLock {

    private Map<Thread, Integer> readingThreads = new HashMap<>();

    private int writeAccesses = 0;
    private int writeRequests = 0;
    private Thread writingThread = null;

    /*
     * Parameters: none
     * Return:none
     * Description: Create a thread and check whether the thread can read.
     *              if not, put the thread to the thread pool and wait.
     * */
    synchronized void lockRead() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (!canGrantReadAccess(callingThread)) {
            wait();
        }

        readingThreads.put(callingThread,
                (getReadAccessCount(callingThread) + 1));
    }

    /*
     * Parameters: callingThread
     * Return:!hasWriteRequests
     * Description: Check whether the thread has read access.
     *              If no thread is writing, return true, otherwise return false
     *              If one thread is writing or reading but the it is the threads itself, return true
     * */
    private boolean canGrantReadAccess(Thread callingThread) {
        if (isWriter(callingThread)) return true;
        if (hasWriter()) return false;
        if (isReader(callingThread)) return true;
        return !hasWriteRequests();
    }

    /*
     * Parameters: none
     * Return: none
     * Description: unlock the read lock from the thread
     * */
    synchronized void unlockRead() {
        Thread callingThread = Thread.currentThread();
        //check whether the calling thread has the read lock
        if (!isReader(callingThread)) {
            throw new IllegalMonitorStateException("Thread does not hold a read lock");
        }
        int accessCount = getReadAccessCount(callingThread);
        if (accessCount == 1) {
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, (accessCount - 1));
        }
        notifyAll();
    }

    /*
     * Parameters: none
     * Return: none
     * Description: add the write lock to thread
     * */
    synchronized void lockWrite() throws InterruptedException {
        writeRequests++;
        Thread callingThread = Thread.currentThread();
        while (!canGrantWriteAccess(callingThread)) {
            wait();
        }
        writeRequests--;
        writeAccesses++;
        writingThread = callingThread;
    }

    /*
     * Parameters: none
     * Return: none
     * Description: unlock the write lock from the thread
     * */
    synchronized void unlockWrite() {
        if (!isWriter(Thread.currentThread())) {
            throw new IllegalMonitorStateException("Thread does not hold the write lock");
        }
        writeAccesses--;
        if (writeAccesses == 0) {
            writingThread = null;
        }
        notifyAll();
    }

    /*
     * Parameters: none
     * Return: none
     * Description: unlock the write lock from the thread
     * */
    synchronized void unlock() {
        Thread callingThread = Thread.currentThread();
        if (!(isReader(callingThread) || isWriter(callingThread))) {
            throw new IllegalMonitorStateException("Thread does not hold any lock");
        }
        readingThreads.remove(callingThread);
        writingThread = null;
        notifyAll();
    }

    /*
     * Parameters: callingThread
     * Return: boolean
     * Description: Check whether the calling thread has the write access
     *              If no thread is reading or writing, return true, otherwise return false
     *              If only one thread is reading but the user is the threads itself, return true
     * */
    private boolean canGrantWriteAccess(Thread callingThread) {
        if (isOnlyReader(callingThread)) return true;
        if (hasReaders()) return false;
        if (writingThread == null) return true;
        return isWriter(callingThread);
    }

    /*
     * Parameters: callingThread
     * Return: accessCount
     * Description: get the thread count in the readingThreads
     * */
    private int getReadAccessCount(Thread callingThread) {
        Integer accessCount = readingThreads.get(callingThread);
        if (accessCount == null) return 0;
        return accessCount;
    }

    /*
     * Parameters: none
     * Return: boolean
     * Description: check whether has readers in the readingThreads.
     * */
    private boolean hasReaders() {
        return readingThreads.size() > 0;
    }

    /*
     * Parameters: callingThread
     * Return: boolean
     * Description: check whether the calling thread in the readingThreads.
     * */
    private boolean isReader(Thread callingThread) {
        return readingThreads.get(callingThread) != null;
    }

    /*
     * Parameters: callingThread
     * Return: boolean
     * Description: check whether the calling thread is the only thread in the readingThreads.
     * */
    private boolean isOnlyReader(Thread callingThread) {
        return readingThreads.size() == 1 &&
                readingThreads.get(callingThread) != null;
    }

    /*
     * Parameters: none
     * Return: boolean
     * Description: check whether has writer in the writingThread.
     * */
    private boolean hasWriter() {
        return writingThread != null;
    }

    /*
     * Parameters: callingThread
     * Return: boolean
     * Description: check whether the callingThread in the writingThread.
     * */
    private boolean isWriter(Thread callingThread) {
        return writingThread == callingThread;
    }

    /*
     * Parameters: none
     * Return: boolean
     * Description: check whether write has requests
     * */
    private boolean hasWriteRequests() {
        return this.writeRequests > 0;
    }
}
