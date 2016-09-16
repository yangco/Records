package net.wpm.record.bytes;

/**
 * ByteBuffer
 * https://github.com/OpenHFT/Chronicle-Bytes
 * https://github.com/real-logic/Agrona
 * http://insightfullogic.com/2015/Apr/18/agronas-threadsafe-offheap-buffers/
 * 
 * @author Nico
 */
public interface MemoryAccess {
	

	public static MemoryAccess build() {
		return UnsafeMemoryAdapter.getInstance();
	}
		
	/**
	 * Reserved a piece of memory
	 * 
	 * @param size
	 * @return
	 */
	public long reserve(int size);
		
	public void releaseAll();
	
	public long capacity();
	
	public boolean getBoolean(final long address);
	
	public void setBoolean(final long address, final boolean value);
	
	public byte getByte(final long address);
	
	public void setByte(final long address, final byte value);

	public short getShort(final long address);
	
	public void setShort(final long address, short value);
	
    public int getInt(final long address);

    public void setInt(final long address, final int value);
   
    public float getFloat(final long address);
	
    public void setFloat(final long address, float value);
   
    public long getLong(final long address);

    public void setLong(final long address, final long value);
    
    public double getDouble(final long address);
	
    public void setDouble(final long address, final double value);

	public void copy(long fromAddress, long toAddress, int length);

}
