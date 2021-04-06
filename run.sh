#!/bin/bash

plaintext="The quick brown fox jumps over the desktop 1 2 3"
key="ThisIsA16ByteKey"

source venv/bin/activate

echo Plaintext is \"${plaintext}\"

python_encrypt=$(python python/AES.py encrypt "$key" "$plaintext")

echo Python encrypted to \"${python_encrypt}\"

java_decrypt=$(java -cp java AESCrypt decrypt "$key" "$python_encrypt")

echo Java decrypted to \"${java_decrypt}\"

java_encrypt=$(java -cp java AESCrypt encrypt "$key" "$plaintext")

echo Java encrypted to \"${java_encrypt}\"

python_decrypt=$(python python/AES.py decrypt "$key" "$java_encrypt")

echo Python decrypted to \"${python_decrypt}\"

