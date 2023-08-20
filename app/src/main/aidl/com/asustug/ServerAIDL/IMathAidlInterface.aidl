// IMathAidlInterface.aidl
package com.asustug.ServerAIDL;

// Declare any non-default types here with import statements

interface IMathAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(int a, int b);
    int sub(int a, int b);
    int multiply(int a, int b);
    int divide(int a, int b);
}