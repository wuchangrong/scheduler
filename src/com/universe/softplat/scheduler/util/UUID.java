package com.universe.softplat.scheduler.util;

import java.io.*;
import java.security.*;

public final class UUID implements Serializable, Comparable{
	
    private static final long serialVersionUID = -4856846361193249489L;
    private final long mostSigBits;
    private final long leastSigBits;
    private transient int a;
    private transient int b;
    private volatile transient long c;
    private transient int d;
    private transient long e;
    private transient int f;
    private static volatile SecureRandom g = null;	

    private UUID(byte abyte0[]){
        a = -1;
        b = -1;
        c = -1L;
        d = -1;
        e = -1L;
        f = -1;
        long l = 0L;
        long l1 = 0L;
        for(int i = 0; i < 8; i++)
            l = l << 8 | (long)(abyte0[i] & 255);

        for(int j = 8; j < 16; j++)
            l1 = l1 << 8 | (long)(abyte0[j] & 255);

        mostSigBits = l;
        leastSigBits = l1;
    }

    public UUID(long l, long l1){
        a = -1;
        b = -1;
        c = -1L;
        d = -1;
        e = -1L;
        f = -1;
        mostSigBits = l;
        leastSigBits = l1;
    }

    public static UUID randomUUID(){
        SecureRandom securerandom;
        if((securerandom = g) == null)
            g = securerandom = new SecureRandom();
        byte abyte0[] = new byte[16];
        securerandom.nextBytes(abyte0);
        abyte0[6] &= 15;
        abyte0[6] |= 64;
        abyte0[8] &= 63;
        abyte0[8] |= 128;
        return new UUID(abyte0);
    }

    public static UUID nameUUIDFromBytes(byte abyte0[]){
        MessageDigest messagedigest;
        try
        {
            messagedigest = MessageDigest.getInstance("MD5");
        }
        catch(NoSuchAlgorithmException _ex)
        {
            throw new InternalError("MD5 not supported");
        }
        byte abyte1[];
        (abyte1 = messagedigest.digest(abyte0))[6] &= 15;
        abyte1[6] |= 48;
        abyte1[8] &= 63;
        abyte1[8] |= 128;
        return new UUID(abyte1);
    }

    public static UUID fromString(String s)
    {
        String as[];
        if((as = s.split("-")).length != 5)
            throw new IllegalArgumentException("Invalid UUID string: " + s);
        for(int i = 0; i < 5; i++)
            as[i] = "0x" + as[i];

        long l;
        l = (l = (l = (l = (l = Long.decode(as[0]).longValue()) << 16) | Long.decode(as[1]).longValue()) << 16) | Long.decode(as[2]).longValue();
        long l1;
        l1 = (l1 = (l1 = Long.decode(as[3]).longValue()) << 48) | Long.decode(as[4]).longValue();
        return new UUID(l, l1);
    }

    public final long getLeastSignificantBits()
    {
        return leastSigBits;
    }

    public final long getMostSignificantBits()
    {
        return mostSigBits;
    }

    public final int version()
    {
        if(a < 0)
            a = (int)(mostSigBits >> 12 & 15L);
        return a;
    }

    public final int variant()
    {
        if(b < 0)
            if(leastSigBits >>> 63 == 0L)
                b = 0;
            else
            if(leastSigBits >>> 62 == 2L)
                b = 2;
            else
                b = (int)(leastSigBits >>> 61);
        return b;
    }

    public final long timestamp()
    {
        if(version() != 1)
            throw new UnsupportedOperationException("Not a time-based UUID");
        long l;
        if((l = c) < 0L)
        {
            l = (l = (l = (mostSigBits & 4095L) << 48) | (mostSigBits >> 16 & 65535L) << 32) | mostSigBits >>> 32;
            c = l;
        }
        return l;
    }

    public final int clockSequence()
    {
        if(version() != 1)
            throw new UnsupportedOperationException("Not a time-based UUID");
        if(d < 0)
            d = (int)((leastSigBits & 4611404543450677248L) >>> 48);
        return d;
    }

    public final long node()
    {
        if(version() != 1)
            throw new UnsupportedOperationException("Not a time-based UUID");
        if(e < 0L)
            e = leastSigBits & 281474976710655L;
        return e;
    }

    public final String toString()
    {
        return a(mostSigBits >> 32, 8) + "-" + a(mostSigBits >> 16, 4) + "-" + a(mostSigBits, 4) + "-" + a(leastSigBits >> 48, 4) + "-" + a(leastSigBits, 12);
    }

    private static String a(long l, int i)
    {
        long l1;
        return Long.toHexString((l1 = 1L << i * 4) | l & l1 - 1L).substring(1);
    }

    public final int hashCode()
    {
        if(f == -1)
            f = (int)(mostSigBits >> 32 ^ mostSigBits ^ leastSigBits >> 32 ^ leastSigBits);
        return f;
    }

    public final boolean equals(Object obj)
    {
        if(!(obj instanceof UUID))
            return false;
        if(((UUID)obj).variant() != variant())
            return false;
        UUID uuid = (UUID)obj;
        return mostSigBits == uuid.mostSigBits && leastSigBits == uuid.leastSigBits;
    }

    public final int compareTo(UUID uuid)
    {
        if(mostSigBits < uuid.mostSigBits)
            return -1;
        if(mostSigBits > uuid.mostSigBits)
            return 1;
        if(leastSigBits < uuid.leastSigBits)
            return -1;
        return leastSigBits <= uuid.leastSigBits ? 0 : 1;
    }

    private void readObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        objectinputstream.defaultReadObject();
        a = -1;
        b = -1;
        c = -1L;
        d = -1;
        e = -1L;
        f = -1;
    }

    public final int compareTo(Object obj)
    {
        UUID uuid = (UUID)obj;
        if(mostSigBits < uuid.mostSigBits)
            return -1;
        if(mostSigBits > uuid.mostSigBits)
            return 1;
        if(leastSigBits < uuid.leastSigBits)
            return -1;
        return leastSigBits <= uuid.leastSigBits ? 0 : 1;
    }
}