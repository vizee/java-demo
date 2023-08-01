#!/bin/bash

set -e

openssl genrsa -out priv.key 2048
openssl rsa -in priv.key -out pub.key -pubout -outform PEM
