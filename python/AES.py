from Crypto.Cipher import AES
import base64
import sys
import os

# AES 'pad' byte array to multiple of BLOCK_SIZE bytes
def pad(byte_array):
    BLOCK_SIZE = 16
    pad_len = BLOCK_SIZE - len(byte_array) % BLOCK_SIZE
    return byte_array + (bytes([pad_len]) * pad_len)

# Remove padding at end of byte array
def unpad(byte_array):
    last_byte = byte_array[-1]
    return byte_array[0:-last_byte]

def encrypt(key, message):
    """
    Input String, return base64 encoded encrypted String
    """

    byte_array = message.encode("UTF-8")

    padded = pad(byte_array)
    # print("padded: {}".format(padded))
    # return

    # generate a random iv and prepend that to the encrypted result.
    # The recipient then needs to unpack the iv and use it.
    cipher = AES.new( key.encode("UTF-8"), AES.MODE_ECB)
    encrypted = cipher.encrypt(padded)
    # Note we PREPEND the unencrypted iv to the encrypted message
    return base64.b64encode(encrypted).decode("utf-8")

def decrypt(key, message):
    """
    Input encrypted bytes, return decrypted bytes, using iv and key
    """

    byte_array = base64.b64decode(message)

    messagebytes = byte_array[:] # encrypted message is the bit after the iv

    cipher = AES.new(key.encode("UTF-8"), AES.MODE_ECB)

    decrypted_padded = cipher.decrypt(messagebytes)

    decrypted = unpad(decrypted_padded)

    return decrypted

def main():
    do_encrypt = False
    if sys.argv[1] == "encrypt":
        do_encrypt = True

    key = sys.argv[2]
    message = sys.argv[3]

    if do_encrypt:
        print(encrypt(key,message))
    else:
        print(decrypt(key,message))

main()

