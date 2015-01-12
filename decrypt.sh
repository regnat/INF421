#!/bin/sh

# decrypt.sh

usage () {
  cat <<EOF
  Usage : $0 keysFile cryptedMessage
  
  Try to decrypt cryptedMessage using one of the keys in keysFile
  where each line in keysFile is in the form
  pubkey = firstFactor x secondFactor

EOF
}

if [[ $1 -eq "--usage" ]] || [[ $1 -eq "--help" ]]; then
  usage
  exit 0
fi

PUBFILE=$1
MESSAGE=$2

KEYGENCOMMAND="java genKeys"

echo "Generating asn1 files from the keys..."
rm -r ./keys/*
$KEYGENCOMMAND 
echo "Done"

if [[ ! -z $MESSAGE ]]; then
  echo "Trying do decrypt the file... "
  for FILE in keys/*
  do
    openssl asn1parse -genconf $FILE -out ${FILE}.der
    openssl rsa -in ${FILE}.der -inform der -text -check
    openssl rsa -inform der -outform pem -in ${FILE}.der -out ${FILE}.pem
    openssl rsautl -inkey ${FILE}.pem -decrypt -in $MESSAGE -out files/decrypted.txt
    if [[ ! -z $(cat files/decrypted.txt) ]]
    then
      echo "Trouvé !"
      break
    fi
  done
  echo "Done"
fi
cat files/decrypted.txt
