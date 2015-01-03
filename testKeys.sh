#!/bin/sh

num=0
for FILE in keys/*
do
    num=$(( $num + 1 ))
    openssl asn1parse -genconf $FILE -out ${FILE}.der
    openssl rsa -in ${FILE}.der -inform der -text -check
    openssl rsa -inform der -outform pem -in ${FILE}.der -out ${FILE}.pem
    openssl rsautl -inkey ${FILE}.pem -decrypt -in testKey0.txt -out decrypted.${num}.txt
done
