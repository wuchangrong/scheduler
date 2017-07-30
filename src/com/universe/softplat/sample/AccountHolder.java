package com.universe.softplat.sample;


public class AccountHolder
{

    public AccountHolder()
    {
    }

    public static void setAccessorAccount(String s)
    {
        accessorHolder.set(s);
    }

    public static String getAccessorAccount()
    {
        return (String)accessorHolder.get();
    }

    private static ThreadLocal accessorHolder = new ThreadLocal();

}
