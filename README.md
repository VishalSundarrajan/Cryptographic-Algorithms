# Cryptographic-Algorithms
This repo consists of some of the most common symmetric and asymmetric algorithms

## Public key Encryption(PKE)/Asymmetric Key Algorithm

1) Alice distributes its public key to bob
2) Bob encrypts messages to Alice with the distributed key
3) Alice decrypts the message with her private key

### Running the code files

### Setting up the Environment

#### Installation requirments

1. [Java](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 

#### Build and Compile

Building the code files:

`javac Alice.java`

`javac Bob.java`

Start Bob before Alice because Bob has to receive Alice's public key,

`java Bob`

`java Alice`

## Secret/Symmetric Key Algorithm

1) Same key is used in both the sides for encryption and decryption.(Since the same key has been used, both the encryption and decryption is carried out at the same end. Modes of operation:CBC)

### Running the code

#### Build and Compile

Building the code files:

`javac DESAlgorithm.java`

Run the file

`java DESAlgorithm`
