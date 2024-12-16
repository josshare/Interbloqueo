# Deadlock in Contacts Management System

## What is a Deadlock?
A deadlock occurs when two or more threads are blocked forever, waiting for each other. In this example, we've created a deadlock scenario in the contacts management system.

## How the Deadlock Works
1. We have two threads trying to add contacts to two different lists (contactos1 and contactos2)
2. Thread1 executes `addContactWithDeadlock1`:
   - Acquires lock1
   - Tries to acquire lock2
3. Thread2 executes `addContactWithDeadlock2`:
   - Acquires lock2
   - Tries to acquire lock1
4. Result: Both threads are stuck waiting for each other's locks

## How to Fix the Deadlock
There are several ways to fix this deadlock:

1. **Lock Ordering**:
   - Always acquire locks in the same order in both methods
   - Example fix:
   ```java
   public void addContactWithDeadlock1() {
       synchronized (lock1) {
           synchronized (lock2) {
               // Add contacts
           }
       }
   }

   public void addContactWithDeadlock2() {
       synchronized (lock1) {
           synchronized (lock2) {
               // Add contacts
           }
       }
   }
   ```

2. **Use a Single Lock**:
   - Instead of using two locks, use a single lock for both operations
   - Example:
   ```java
   private final Object lock = new Object();

   public void addContact() {
       synchronized (lock) {
           // Add to both lists
       }
   }
   ```

3. **Use Higher-Level Concurrency Utilities**:
   - Use `java.util.concurrent` utilities like ReentrantLock with tryLock()
   - This allows setting timeouts and handling lock acquisition failures

4. **Redesign the System**:
   - Consider if you really need two separate lists
   - Consider using thread-safe collections like CopyOnWriteArrayList
   - Consider if the operations can be made atomic

## Best Practices to Avoid Deadlocks
1. Avoid nested synchronization
2. Keep critical sections small
3. Use timeouts when acquiring locks
4. Maintain consistent lock ordering
5. Use higher-level concurrency utilities when possible
6. Consider using deadlock detection tools during development