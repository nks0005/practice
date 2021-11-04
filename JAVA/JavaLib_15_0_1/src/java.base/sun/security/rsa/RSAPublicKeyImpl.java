/*
 * Copyright (c) 2003, 2020, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package sun.security.rsa;

import java.io.IOException;
import java.math.BigInteger;

import java.security.*;
import java.security.spec.*;
import java.security.interfaces.*;

import sun.security.util.*;
import sun.security.x509.X509Key;

import sun.security.rsa.RSAUtil.KeyType;

/**
 * RSA public key implementation for "RSA", "RSASSA-PSS" algorithms.
 *
 * Note: RSA keys must be at least 512 bits long
 *
 * @see RSAPrivateCrtKeyImpl
 * @see RSAPrivateKeyImpl
 * @see RSAKeyFactory
 *
 * @since   1.5
 * @author  Andreas Sterbenz
 */
public final class RSAPublicKeyImpl extends X509Key implements RSAPublicKey {

    @java.io.Serial
    private static final long serialVersionUID = 2644735423591199609L;
    private static final BigInteger THREE = BigInteger.valueOf(3);

    private BigInteger n;       // modulus
    private BigInteger e;       // public exponent

    private transient KeyType type;

    // optional parameters associated with this RSA key
    // specified in the encoding of its AlgorithmId
    // must be null for "RSA" keys.
    private transient AlgorithmParameterSpec keyParams;

    /**
     * Generate a new RSAPublicKey from the specified encoding.
     * Used by SunPKCS11 provider.
     */
    public static RSAPublicKey newKey(byte[] encoded)
            throws InvalidKeyException {
        return new RSAPublicKeyImpl(encoded);
    }

    /**
     * Generate a new RSAPublicKey from the specified type and components.
     * Used by SunPKCS11 provider.
     */
    public static RSAPublicKey newKey(KeyType type,
            AlgorithmParameterSpec params, BigInteger n, BigInteger e)
            throws InvalidKeyException {
        return new RSAPublicKeyImpl(type, params, n, e);
    }

    /**
     * Construct a RSA key from the specified type and components. Used by
     * RSAKeyFactory and RSAKeyPairGenerator.
     */
    RSAPublicKeyImpl(KeyType type, AlgorithmParameterSpec keyParams,
            BigInteger n, BigInteger e) throws InvalidKeyException {

        RSAKeyFactory.checkRSAProviderKeyLengths(n.bitLength(), e);
        checkExponentRange(n, e);

        this.n = n;
        this.e = e;

        try {
            // validate and generate algid encoding
            algid = RSAUtil.createAlgorithmId(type, keyParams);
        } catch (ProviderException pe) {
            throw new InvalidKeyException(pe);
        }

        this.type = type;
        this.keyParams = keyParams;

        try {
            // generate the key encoding
            DerOutputStream out = new DerOutputStream();
            out.putInteger(n);
            out.putInteger(e);
            byte[] keyArray =
                new DerValue(DerValue.tag_Sequence,
                             out.toByteArray()).toByteArray();
            setKey(new BitArray(keyArray.length*8, keyArray));
        } catch (IOException exc) {
            // should never occur
            throw new InvalidKeyException(exc);
        }
    }

    /**
     * Construct a key from its encoding. Used by RSAKeyFactory.
     */
    RSAPublicKeyImpl(byte[] encoded) throws InvalidKeyException {
        if (encoded == null || encoded.length == 0) {
            throw new InvalidKeyException("Missing key encoding");
        }
        decode(encoded); // this sets n and e value
        RSAKeyFactory.checkRSAProviderKeyLengths(n.bitLength(), e);
        checkExponentRange(n, e);

        try {
            // check the validity of oid and params
            Object[] o = RSAUtil.getTypeAndParamSpec(algid);
            this.type = (KeyType) o[0];
            this.keyParams = (AlgorithmParameterSpec) o[1];
        } catch (ProviderException e) {
            throw new InvalidKeyException(e);
        }
    }

    // pkg private utility method for checking RSA modulus and public exponent
    static void checkExponentRange(BigInteger mod, BigInteger exp)
            throws InvalidKeyException {
        // the exponent should be smaller than the modulus
        if (exp.compareTo(mod) >= 0) {
            throw new InvalidKeyException("exponent is larger than modulus");
        }

        // the exponent should be at least 3
        if (exp.compareTo(THREE) < 0) {
            throw new InvalidKeyException("exponent is smaller than 3");
        }
    }

    // see JCA doc
    @Override
    public String getAlgorithm() {
        return type.keyAlgo;
    }

    // see JCA doc
    @Override
    public BigInteger getModulus() {
        return n;
    }

    // see JCA doc
    @Override
    public BigInteger getPublicExponent() {
        return e;
    }

    // see JCA doc
    @Override
    public AlgorithmParameterSpec getParams() {
        return keyParams;
    }

    /**
     * Parse the key. Called by X509Key.
     */
    protected void parseKeyBits() throws InvalidKeyException {
        try {
            DerInputStream in = new DerInputStream(getKey().toByteArray());
            DerValue derValue = in.getDerValue();
            if (derValue.tag != DerValue.tag_Sequence) {
                throw new IOException("Not a SEQUENCE");
            }
            DerInputStream data = derValue.data;
            n = data.getPositiveBigInteger();
            e = data.getPositiveBigInteger();
            if (derValue.data.available() != 0) {
                throw new IOException("Extra data available");
            }
        } catch (IOException e) {
            throw new InvalidKeyException("Invalid RSA public key", e);
        }
    }

    // return a string representation of this key for debugging
    @Override
    public String toString() {
        return "Sun " + type.keyAlgo + " public key, " + n.bitLength()
               + " bits" + "\n  params: " + keyParams + "\n  modulus: " + n
               + "\n  public exponent: " + e;
    }

    @java.io.Serial
    protected Object writeReplace() throws java.io.ObjectStreamException {
        return new KeyRep(KeyRep.Type.PUBLIC,
                        getAlgorithm(),
                        getFormat(),
                        getEncoded());
    }
}
