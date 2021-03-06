package net.wpm.record.bytes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.openhft.chronicle.core.Memory;
import net.openhft.chronicle.core.OS;
import sun.misc.Cleaner;

/**
 * A piece of memory.
 * 
 * @author Nico
 *
 */
public class UnsafeBytes {
	private static Logger log = LoggerFactory.getLogger(UnsafeBytes.class);

	protected final long address;
	protected final long capacity;
	protected long used;
	
	protected final Cleaner cleaner;

	/**
	 * costs 1C 2B 0A 0P 0M 3N
	 * @param memory
	 * @param capacity
	 */
	public UnsafeBytes(Memory memory, long capacity) {
		log.trace("Allocate "+capacity+" bytes of memory.");

		this.used = 0;
		this.capacity = capacity;
		this.address = memory.allocate(capacity);
		memory.setMemory(address, capacity, (byte) 0);
		this.cleaner = Cleaner.create(this, new Deallocator(address, capacity));
	}	
	

	/**
	 * Starting address of this piece of memory
	 * 
	 * costs 0C 0B 0A 0P 0M 0N
	 * @return address
	 */
	public long freeAddress() {
		return address + used;
	}
	
	/**
	 * Reserve some bytes
	 * 
	 * costs 0C 0B 0A 0P 0M 0N
	 * @param size
	 */
	public void use(long size) {
		used = used + size;
	}
	
	/**
	 * How many bytes are still empty 
	 * 
	 * costs 0C 0B 0A 0P 0M 0N
	 * @return bytes remaining
	 */
	public int remaining() {	
		return (int)(capacity - used);
	}
	
	/**
	 * Does the amount of bytes fit in this piece of memory
	 * 
	 * costs 0C 1B 0A 0P 0M 0N
	 * @param size
	 * @return boolean
	 */
	public boolean hasCapacity(long size) {	
		return 0 <= size && ((used + size) <= capacity);
	}
	
	/**
	 * Release the underlying memory
	 * 
	 * costs 0C 8B 0A 0P 0M 0N
	 */
	public void release() {
		cleaner.clean();
		used = capacity;
	}
	
	/**
	 * Frees the memory.
	 * 
	 * @author Nico Hezel
	 */
	public static class Deallocator implements Runnable {
        private volatile long address, size;

        public Deallocator(long address, long size) {
            assert address != 0;
            this.address = address;
            this.size = size;
        }

        @Override
        public void run() {
            if (address == 0)
                return;
            address = 0;
            OS.memory().freeMemory(address, size);
        }
    }
}