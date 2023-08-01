deps:
	test -f ./bcprov-jdk18on.jar || wget https://bouncycastle.org/download/bcprov-jdk18on-176.jar -O bcprov-jdk18on.jar
	test -f ./bcpkix-jdk18on.jar || wget https://bouncycastle.org/download/bcpkix-jdk18on-176.jar -O bcpkix-jdk18on.jar
	test -f ./bcutil-jdk18on.jar || wget https://bouncycastle.org/download/bcutil-jdk18on-176.jar -O bcutil-jdk18on.jar

keys:
	openssl genrsa -out priv.key 2048
	openssl rsa -in priv.key -out pub.key -pubout -outform PEM

RSA.class: RSA.java
	javac -cp bcpkix-jdk18on.jar:bcprov-jdk18on.jar:bcutil-jdk18on.jar RSA.java

run: RSA.class
	java -cp bcpkix-jdk18on.jar:bcprov-jdk18on.jar:bcutil-jdk18on.jar:. RSA

native:
	native-image -cp bcpkix-jdk18on.jar:bcprov-jdk18on.jar:bcutil-jdk18on.jar:. RSA

metadata:
	java -agentlib:native-image-agent=config-output-dir=./META-INF/native-image -cp bcpkix-jdk18on.jar:bcprov-jdk18on.jar:bcutil-jdk18on.jar:. RSA
	java -agentlib:native-image-agent=config-merge-dir=./META-INF/native-image -cp bcpkix-jdk18on.jar:bcprov-jdk18on.jar:bcutil-jdk18on.jar:. RSA

native-no-fallback:
	native-image -cp bcpkix-jdk18on.jar:bcprov-jdk18on.jar:bcutil-jdk18on.jar:. --no-fallback --enable-all-security-services RSA

.PHONY: keys deps run metadata native
